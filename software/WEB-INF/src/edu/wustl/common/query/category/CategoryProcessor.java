/**
 * 
 */

package edu.wustl.common.query.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.CategoryAssociationInterface;
import edu.common.dynamicextensions.domaininterface.CategoryEntityInterface;
import edu.wustl.cab2b.client.ui.dag.PathLink;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.querableobject.QueryableCategory;
import edu.wustl.common.querysuite.querableobject.QueryableCategoryAttribute;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateOffsetAttribute;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.utils.ConstraintsObjectBuilder;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.global.Constants;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class will handle the transformation of the Query Formed with the Category to the
 * Query which will be only on the Entities.
 * @author pavan_kalantri
 * @author vijay_pande
 *
 */
public class CategoryProcessor
{

	private final IQuery query;
	private IQuery generatedQuery;
	private final IConstraintsObjectBuilderInterface m_queryObject;
	private final IPathFinder m_pathFinder;
	private final Map<Integer, Integer> oldVsNewExpressionId = new HashMap<Integer, Integer>();
	private final Map<Integer, Map<CategoryEntityInterface, IExpression>> oldExprIdVsCatEntityExprMap = new HashMap<Integer, Map<CategoryEntityInterface, IExpression>>();
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(CategoryProcessor.class);

	/**
	 * Parameterized constructor.
	 * @param query which will act as a original Query
	 */
	public CategoryProcessor(IQuery query)
	{
		this.query = query;
		m_queryObject = new ConstraintsObjectBuilder();
		generatedQuery = m_queryObject.getQuery();
		m_pathFinder = new CommonPathFinder();
	}

	/**
	 * It will check weather the Query is created with the category or with the Normal Entity , if
	 * it is created with the Category will process it & transform it to the Query which will be only
	 * of the Entities.
	 * and if the Query is Of entities already it will return the same Query as it is.
	 * @return generated Query with only Entities.
	 * @throws QueryModuleException Query Module Exception
	 */
	public IQuery processCategory() throws QueryModuleException
	{
		try
		{
			if (CategoryProcessorUtility.isCategoryQuery(query))
			{
				logger.info("Updating category query");
				setDefaultValues();
				addAllExpressions();
				logger.info("category query updated");
			}
			else
			{
				generatedQuery = query;
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.GENERIC_EXCEPTION);
		}
		return generatedQuery;
	}

	/**
	 * It will set the default values of the generated Query with those of the
	 * original query.
	 */
	private void setDefaultValues()
	{
		IConstraints constraints = QueryObjectFactory.createConstraints();
		generatedQuery.setConstraints(constraints);
		generatedQuery.setType(query.getType());
		generatedQuery.setId(query.getId());
		generatedQuery.setCreatedBy(query.getCreatedBy());
		generatedQuery.setCreatedDate(query.getCreatedDate());
		generatedQuery.setName(query.getName());
		generatedQuery.setDescription(query.getDescription());
		generatedQuery.setIsSystemGenerated(query.getIsSystemGenerated());

	}

	/**
	 * It will add the Expression in the generatedQuery for each expression in the original Query
	 * i.e it will create Expression for each CategoryEntity which is present in the Category
	 * from which this Expression is created.
	 * e.g. If category Test is Created from A,B,C entities then one expression of Test category is
	 * devided in 3 expression on A,B,C respectively.
	 * @throws CyclicException Cyclic Exception
	 * @throws QueryModuleException Query Module Exception
	 */
	private void addAllExpressions() throws CyclicException, QueryModuleException
	{
		int rootExpressionId;
		for (IExpression expression : query.getConstraints())
		{
			QueryableCategory queryableCategory = (QueryableCategory) expression.getQueryEntity()
					.getDynamicExtensionsEntity();
			rootExpressionId = createExpression(expression, queryableCategory
					.getRootCategoryElement());
			createChildExpession(expression, queryableCategory.getRootCategoryElement(),
					queryableCategory, rootExpressionId);
			oldVsNewExpressionId.put(expression.getExpressionId(), rootExpressionId);
		}
		processCustomFormula();
		processOutputAttributes();
		addPathBetweenCategories();
	}

	/**
	 * this method will process the output Attributes present in the original query
	 * to form the new Query.
	 * @throws QueryModuleException if multiple roots exist.
	 */
	private void processOutputAttributes() throws QueryModuleException
	{
		List<IOutputAttribute> outputAttributeList = new ArrayList<IOutputAttribute>();
		for (IOutputAttribute outputAttribtue : ((IParameterizedQuery) query)
				.getOutputAttributeList())
		{
			IOutputAttribute attr = createNewOutputAttribute(outputAttribtue);
			outputAttributeList.add(attr);
		}
		((IParameterizedQuery) generatedQuery).setOutputAttributeList(outputAttributeList);

	}

	/**
	 * It will create a newoutputAttribute with the attribute from which the outputAttribute
	 * with the CategoryAttribute is formed, so that it can be converted into the entity Query.
	 * @param outputAttribtue attribute with categoryAttribute.
	 * @return newOutputAttribtue with the normal attribute.
	 * @throws QueryModuleException exception.
	 */
	private IOutputAttribute createNewOutputAttribute(IOutputAttribute outputAttribtue)
			throws QueryModuleException
	{
		QueryableCategoryAttribute categoryAttribute = (QueryableCategoryAttribute) outputAttribtue
				.getAttribute();
		IExpression newExpression = getNewExpressionForEntity(outputAttribtue.getExpression(),
				categoryAttribute.getCategoryEntity());
		QueryableAttributeInterface quearyableEntityAttr = categoryAttribute
				.getRootQueryableAttribte();
		return new OutputAttribute(categoryAttribute.getDisplayName(), newExpression,
				quearyableEntityAttr);
	}

	/**
	 * It will process the CustomFormula Present in the query & transform according to the generated Query.
	 * @throws QueryModuleException Query Module Exception
	 */
	private void processCustomFormula() throws QueryModuleException
	{
		for (IExpression expression : query.getConstraints())
		{
			for (ICustomFormula oldCustomFormula : QueryUtility.getCustomFormulas(expression))
			{
				createNewCustomFormula(oldCustomFormula);
			}

		}

	}

	/**
	 * It will check weather the CustomFormula is on single Expression or Two expressions and accordingly
	 * create new customFormula based on the oldCustomFormula & add it to the generatedQuery.
	 * @param oldCustomFormula old Custom Formula
	 * @throws QueryModuleException Query Module Exception
	 */
	private void createNewCustomFormula(ICustomFormula oldCustomFormula)
			throws QueryModuleException
	{
		//This is two node tq
		if (oldCustomFormula.getLhs().getOperand(0) instanceof IExpressionAttribute)
		{
			createCustomFormula(oldCustomFormula);

		}
		else
		{
			//this is single node TQ
			createSingleNodeCustomFormula(oldCustomFormula);
		}

	}

	/**
	 * It will create the CustomFormula From the oldCustomFormula,
	 * Which is formed with two different Expressions.
	 * @param oldCustomFormula old Custom Formula
	 * @throws QueryModuleException Query Module Exception
	 */
	private void createCustomFormula(ICustomFormula oldCustomFormula) throws QueryModuleException
	{
		IExpressionAttribute oldLhsOperand1 = (IExpressionAttribute) oldCustomFormula.getLhs()
				.getOperand(Constants.ZERO);
		IExpressionAttribute oldLhsOperand2 = (IExpressionAttribute) oldCustomFormula.getLhs()
				.getOperand(Constants.ONE);
		QueryableCategoryAttribute queryableAttribute = (QueryableCategoryAttribute) oldLhsOperand1
				.getAttribute();
		IExpression newExpression = getNewExpressionForEntity(oldLhsOperand1.getExpression(),
				queryableAttribute.getCategoryEntity());
		IArithmeticOperand lhsOperand1 = getNewOperand(oldLhsOperand1);
		IArithmeticOperand lhsOperand2 = getNewOperand(oldLhsOperand2);
		formNewCustomFormula(oldCustomFormula, lhsOperand1, lhsOperand2, newExpression);

	}

	/**
	 * It will return the new Expression created for the categoryEntity in the new Query for the
	 * oldExpression of the Category.
	 * @param oldExpression old expression
	 * @param categoryEntity category entity
	 * @return Expression formed for the categoryEntity in oldExpression's category.
	 * @throws QueryModuleException Query Module Exception
	 */
	private IExpression getNewExpressionForEntity(IExpression oldExpression,
			CategoryEntityInterface categoryEntity) throws QueryModuleException
	{
		IExpression expression = null;
		Map<CategoryEntityInterface, IExpression> catEntityVsExpr = oldExprIdVsCatEntityExprMap
				.get(oldExpression.getExpressionId());
		if (catEntityVsExpr == null)
		{
			throw new QueryModuleException("Expression Not Found For Category Entity "
					+ categoryEntity.getName());
		}
		else
		{
			expression = catEntityVsExpr.get(categoryEntity);
		}
		return expression;

	}

	/**
	 * It will create the single Node CustomFormula From the oldCustomFormula,
	 * Which is single Node custom Formula.
	 * @param oldCustomFormula old Custom Formula
	 * @throws QueryModuleException Query Module Exception
	 */
	private void createSingleNodeCustomFormula(ICustomFormula oldCustomFormula)
			throws QueryModuleException
	{
		IArithmeticOperand oldLhsOperand1 = oldCustomFormula.getLhs().getOperand(0);

		IExpressionAttribute oldLhsOperand2 = (IExpressionAttribute) oldCustomFormula.getLhs()
				.getOperand(Constants.ONE);
		QueryableCategoryAttribute queryableAttribute = (QueryableCategoryAttribute) oldLhsOperand2
				.getAttribute();
		IExpression newExpression = getNewExpressionForEntity(oldLhsOperand2.getExpression(),
				queryableAttribute.getCategoryEntity());
		IArithmeticOperand lhsOperand2 = getNewOperand(oldLhsOperand2);
		formNewCustomFormula(oldCustomFormula, oldLhsOperand1, lhsOperand2, newExpression);
	}

	/**
	 * It will form the newCustomFormulaby adding the  lhsOperand1 ,lhsOperand2, taking the
	 * rhs and Operator from the oldCustomFormula then it will add the newCustomFormula in the newExpression.
	 * @param oldCustomFormula from which new Custom Formula is to form.
	 * @param lhsOperand1 lhs operand which is to be added in the newCustomFormula
	 * @param lhsOperand2 lhs operand which is to be added in the newCustomFormula
	 * @param newExpression in which the new custom Formula should be Added.
	 */
	private void formNewCustomFormula(ICustomFormula oldCustomFormula,
			IArithmeticOperand lhsOperand1, IArithmeticOperand lhsOperand2,
			IExpression newExpression)
	{
		ICustomFormula newCustomFormula = CategoryProcessorUtility.formNewCustomFormula(
				oldCustomFormula, lhsOperand1, lhsOperand2);
		newExpression.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				newCustomFormula);
		CategoryProcessorUtility.addOutputTermsToQuery(generatedQuery, newCustomFormula, "test");
	}

	/**
	 * It will create the new operand which is from normal attribute.
	 * e.g if the operand is based on Category Attribute A which is derived from normal attribute B.
	 *  Then it will create the newOperad created From B.
	 * @param operand operand
	 * @return newOperand of type IArithmeticOperand
	 * @throws QueryModuleException Query Module Exception
	 */
	private IArithmeticOperand getNewOperand(IExpressionAttribute operand)
			throws QueryModuleException
	{
		IExpressionAttribute newOperand = null;
		QueryableCategoryAttribute queryableAttribute = (QueryableCategoryAttribute) operand
				.getAttribute();
		IExpression newExpression = getNewExpressionForEntity(operand.getExpression(),
				queryableAttribute.getCategoryEntity());
		if (operand instanceof IDateOffsetAttribute)
		{
			newOperand = QueryObjectFactory.createDateOffsetAttribute(newExpression,
					queryableAttribute.getRootQueryableAttribte(), ((IDateOffsetAttribute) operand)
							.getTimeInterval());
		}
		else if (operand instanceof ExpressionAttribute)
		{
			newOperand = QueryObjectFactory.createExpressionAttribute(newExpression, operand
					.getAttribute().getRootQueryableAttribte());

		}

		return newOperand;
	}

	/**
	 * It will add the Path between the Root Expressions Created From the two categories Expression.
	 * e.g if in Query there is a path added between the Expression A & B created From category then
	 * This method will add the same path in the Expressions which are added in the generatedQuery from the
	 * RootExpression of these Two categories.
	 * @throws CyclicException Cyclic Exception
	 */
	private void addPathBetweenCategories() throws CyclicException
	{
		IConstraints constraints = query.getConstraints();
		for (Integer oldExpressionId : oldVsNewExpressionId.keySet())
		{

			IExpression expression = constraints.getExpression(oldExpressionId);
			int numberOfOperands = expression.numberOfOperands();
			for (int i = 0; i < numberOfOperands; i++)

			{
				IExpressionOperand operand = expression.getOperand(i);
				if (operand instanceof IExpression)
				{
					copyPath(oldExpressionId, operand);
				}
			}
		}
	}

	/**
	 * It will retrieve the Path present in the given Query between the oldExpressionId & operand, then it
	 * will create the new path object from the associationList retrieve from previous path.
	 * & will add that path between the new Expressions created for those oldExpressions.
	 * @param oldExpressionId old expression id
	 * @param operand operand
	 * @throws CyclicException Cyclic Exception
	 */
	private void copyPath(Integer oldExpressionId, IExpressionOperand operand)
			throws CyclicException
	{
		IConstraints constraints = query.getConstraints();
		IJoinGraph graph = constraints.getJoinGraph();
		List<IIntraModelAssociation> associationList = new ArrayList<IIntraModelAssociation>();
		IIntraModelAssociation association = (IIntraModelAssociation) graph.getAssociation(
				constraints.getExpression(oldExpressionId), (IExpression) operand);
		associationList.add(association);
		IPath path = m_pathFinder.getPathForAssociations(associationList);
		linkNode(oldVsNewExpressionId.get(oldExpressionId), oldVsNewExpressionId
				.get(((IExpression) operand).getExpressionId()), path);
	}

	/**
	 * This method will create the Expression for the categoryEntity which is connected to the
	 * parentCategoryEntity &then will call it self recursively to create
	 * Expressions of its own child & so on.
	 * @param expression old Expression on the Category
	 * @param parentCategoryEntity whose child entities are to be processed
	 * @param queryableCategory from which the old Expression was created.
	 * @param sourceExpressionId expression Id of the Expression created for parentCategoryEntity.
	 * @throws CyclicException Cyclic Exception
	 */
	private void createChildExpession(IExpression expression,
			CategoryEntityInterface parentCategoryEntity, QueryableCategory queryableCategory,
			int sourceExpressionId) throws CyclicException
	{
		for (CategoryAssociationInterface categoryAssociation : parentCategoryEntity
				.getCategoryAssociationCollection())
		{
			CategoryEntityInterface targetCategoryEntity = categoryAssociation
					.getTargetCategoryEntity();
			if (targetCategoryEntity != null)
			{
				int expressionId = createExpression(expression, targetCategoryEntity);
				createChildExpession(expression, targetCategoryEntity, queryableCategory,
						expressionId);
				IPath path = queryableCategory.getPath(parentCategoryEntity, targetCategoryEntity);
				linkNode(sourceExpressionId, expressionId, path);
			}

		}
	}

	/**
	 * It will create the Expression on the Entity from which the categoryEntity is created.
	 * @param oldExpression old expression
	 * @param categoryEntity category entity
	 * @return expression id
	 */
	private int createExpression(IExpression oldExpression, CategoryEntityInterface categoryEntity)
	{
		QueryableObjectInterface queryableObject = QueryableObjectUtility
				.createQueryableObject(categoryEntity.getEntity());
		IQueryEntity queryEntity = QueryObjectFactory.createQueryEntity(queryableObject);
		IExpression newExpression = generatedQuery.getConstraints().addExpression(queryEntity);
		newExpression.setInView(oldExpression.isInView());
		newExpression.setIsSystemGenerated(oldExpression.getIsSystemGenerated());
		IRule oldRule = CategoryProcessorUtility.getRule(oldExpression);
		IRule newRule = QueryObjectFactory.createRule();
		newExpression.addOperand(newRule);
		processRule(categoryEntity, oldRule, newRule);
		updateCategoryEntityExpressionMap(oldExpression, newExpression, categoryEntity);
		return newExpression.getExpressionId();
	}

	/**
	 * It will add the newExpression created For the categoryEntity in the oldExprIdVsCatEntityExprMap
	 * against the oldExpressions Id.
	 * @param oldExpression the original Expression with Category
	 * @param newExpression formed for the categoryEntity in the oldExpression's category.
	 * @param categoryEntity  category entity
	 */
	private void updateCategoryEntityExpressionMap(IExpression oldExpression,
			IExpression newExpression, CategoryEntityInterface categoryEntity)
	{
		Map<CategoryEntityInterface, IExpression> catEntityVsExpr = oldExprIdVsCatEntityExprMap
				.get(oldExpression.getExpressionId());
		if (catEntityVsExpr == null)
		{
			catEntityVsExpr = new HashMap<CategoryEntityInterface, IExpression>();
			oldExprIdVsCatEntityExprMap.put(oldExpression.getExpressionId(), catEntityVsExpr);
		}
		catEntityVsExpr.put(categoryEntity, newExpression);

	}

	/**
	 * It will search weather there is any condition present in the oldRule on the
	 * Attribute of the CategoryEntity, if present it will add the same condition
	 * in the new Rule else will not.
	 * @param categoryEntity category entity
	 * @param oldRule old rule
	 * @param newRule new rule
	 */
	private void processRule(CategoryEntityInterface categoryEntity, IRule oldRule, IRule newRule)
	{
		for (ICondition condition : oldRule)
		{
			QueryableCategoryAttribute Queryableattribute = (QueryableCategoryAttribute) condition
					.getAttribute();
			if (Queryableattribute.getCategoryEntity().equals(categoryEntity))
			{
				ICondition newCondition = QueryObjectFactory.createCondition(Queryableattribute
						.getRootQueryableAttribte(), condition.getRelationalOperator(), condition
						.getValues());
				newRule.addCondition(newCondition);
			}
		}
	}

	/**
	 * It will link the two expression with the sourceExpressionId & destExpressionId with the
	 * given path.
	 * @param sourceExpressionId source Expression Id
	 * @param destExpressionId destination Expression Id
	 * @param path path object
	 * @throws CyclicException Cyclic Exception
	 */
	private void linkNode(final int sourceExpressionId, final int destExpressionId, IPath path)
			throws CyclicException
	{
		if (path != null)
		{
			List<Integer> intermediateExpressions = m_queryObject.addPath(sourceExpressionId,
					destExpressionId, path);
			PathLink link = new PathLink();
			link.setAssociationExpressions(intermediateExpressions);
			link.setDestinationExpressionId(destExpressionId);
			link.setSourceExpressionId(sourceExpressionId);
			link.setPath(path);

			m_queryObject.setLogicalConnector(sourceExpressionId, link
					.getLogicalConnectorExpressionId(), edu.wustl.cab2b.client.ui.query.Utility
					.getLogicalOperator("AND"), false);
		}
	}

}
