
package edu.wustl.query.util.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.CategoryInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;

/**
 * helper class that helps build parameterized queries of various configurations
 *
 * @author ravindra_jain
 * @author juber patel
 *
 * @created 4th December, 2008
 *
 */
public class QueryBuilder
{

	public static EntityManagerInterface entityManager = EntityManager.getInstance();

	/**Method to add condition to given expression.
	 * @param expression expression to which condition should get added
	 * @param attributeName attribute on which condition is defined
	 * @param operator operator for condition of type var-args
	 * @param values values of condition
	 */
	public static void addCondition(IExpression expression, String attributeName,
			RelationalOperator operator, String... values)
	{
		QueryableAttributeInterface attribute = findAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity(), attributeName);

		ICondition condition = QueryObjectFactory.createCondition(attribute, operator, Arrays
				.asList(values));
		getRule(expression).addCondition(condition);
	}

	/**Method to add condition to given expression.
	 * @param expression expression to which condition should get added
	 * @param attributeName attribute on which condition is defined
	 * @param operator operator for condition
	 * @param values values of condition of type list
	 */
	public static void addCondition(IExpression expression, String attributeName,
			RelationalOperator operator, List<String> values)
	{
		QueryableAttributeInterface attribute = findAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity(), attributeName);

		ICondition condition = QueryObjectFactory.createCondition(attribute, operator, values);
		getRule(expression).addCondition(condition);

	}

	/**Method to add parameterized condition to query
	 * @param query query object
	 * @param expression expression with which condition is associated
	 * @param attributeName attribute name on which condition is defined
	 * @param operator operator for the condition
	 */
	public static void addParametrizedCondition(IParameterizedQuery query, IExpression expression,
			String attributeName, RelationalOperator operator)
	{
		QueryableAttributeInterface attribute = findAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity(), attributeName);

		List<String> list = new ArrayList<String>();
		list.add("");
		ICondition condition = QueryObjectFactory.createCondition(attribute, operator, list);
		getRule(expression).addCondition(condition);

		IParameter<ICondition> parameter = QueryObjectFactory.createParameter(condition,
				attributeName);
		query.getParameters().add(parameter);

	}

	/**Method to create expression and to add it to query.
	 * @param constraints query constraints
	 * @param parent parent expression if any
	 * @param expressionType expression type (Category or entity name)
	 * @param isCategory is expression for category
	 * @return expression
	 * @throws DynamicExtensionsSystemException Dynamic Extensions SystemException
	 * @throws DynamicExtensionsApplicationException Dynamic Extensions ApplicationException
	 * @throws CyclicException Cyclic Exception
	 */
	public static IExpression createExpression(IConstraints constraints, IExpression parent,
			String expressionType, boolean isCategory) throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException, CyclicException
	{
		QueryableObjectInterface queryableObject = null;
		if (isCategory)
		{
			CategoryInterface category = getCategoryByname(expressionType);
			queryableObject = QueryableObjectUtility.createQueryableObject(category);
		}
		else
		{
			EntityInterface entity = entityManager.getEntityByName(expressionType);
			queryableObject = QueryableObjectUtility.createQueryableObject(entity);
		}
		IQueryEntity queryEntity = QueryObjectFactory.createQueryEntity(queryableObject);
		IExpression expression = constraints.addExpression(queryEntity);
		expression.setInView(true);

		List<ICondition> conditions = new ArrayList<ICondition>();
		IRule rule = QueryObjectFactory.createRule(conditions);
		expression.addOperand(rule);

		if (parent != null)
		{
			connectExpressions(parent, expression, LogicalOperator.And, constraints.getJoinGraph());
		}

		return expression;
	}

	/**Method to get category by name.
	 * @param expressionType category name
	 * @return category
	 * @throws DynamicExtensionsSystemException Dynamic Extensions System Exception
	 * @throws DynamicExtensionsApplicationException Dynamic Extensions Application Exception
	 */
	private static CategoryInterface getCategoryByname(String expressionType)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		CategoryInterface category = null;

		for (CategoryInterface categoryInterface : EntityCache.getInstance().getAllCategories())
		{
			if (expressionType.equals(categoryInterface.getName()))
			{
				category = categoryInterface;
				break;
			}
		}

		return category;
	}

	/**Method to get IRule from expression.
	 * @param expression expression from which IRule is required
	 * @return IRule object if present
	 */
	private static IRule getRule(IExpression expression)
	{
		Iterator<IExpressionOperand> iterator = expression.iterator();
		while (iterator.hasNext())
		{
			IExpressionOperand operand = iterator.next();
			if (operand instanceof IRule)
			{
				return (IRule) operand;
			}
		}

		return null;
	}

	/**Method to connect two expressions.
	 * @param source source expression
	 * @param target target expression
	 * @param logicalOperator logical operator between two expressions
	 * @param joinGraph join graph of query
	 * @throws DynamicExtensionsSystemException Dynamic Extensions SystemException
	 * @throws DynamicExtensionsApplicationException Dynamic Extensions ApplicationException
	 * @throws CyclicException Cyclic Exception
	 */
	public static void connectExpressions(IExpression source, IExpression target,
			LogicalOperator logicalOperator, IJoinGraph joinGraph)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException
	{
		IConnector<LogicalOperator> connector = QueryObjectFactory
				.createLogicalConnector(logicalOperator);

		source.addOperand(connector, target);

		// Add association to Join graph.
		AssociationInterface association = getAssociation(entityManager.getAssociations(source
				.getQueryEntity().getDynamicExtensionsEntity().getRootQueryableObject().getId(),
				target.getQueryEntity().getDynamicExtensionsEntity().getRootQueryableObject()
						.getId()), target.getQueryEntity().getDynamicExtensionsEntity()
				.getRootQueryableObject().getEntity());
		IIntraModelAssociation imAssociation = QueryObjectFactory
				.createIntraModelAssociation(association);
		joinGraph.putAssociation(source, target, imAssociation);

	}

	/**Method to get association for the given entity.
	 * @param associations all associations
	 * @param targetEntity entity for which associations are required
	 * @return theAssociation required association if present
	 */
	public static AssociationInterface getAssociation(
			Collection<AssociationInterface> associations, EntityInterface targetEntity)
	{
		for (AssociationInterface theAssociation : associations)
		{
			if (theAssociation == null || theAssociation.getTargetEntity() == null)
			{
				//System.out.println("FFOOOOO");
			}
			else if (theAssociation.getTargetEntity().equals(targetEntity))
			{
				return theAssociation;
			}
		}

		return null;
	}

	/**Method to find an expression for given entity.
	 * @param entityName entity for which expression is required
	 * @param root root expression
	 * @param joinGraph join graph of query
	 * @return wantedExpression
	 */
	public static IExpression findExpression(String entityName, IExpression root,
			IJoinGraph joinGraph)
	{
		if (root.getQueryEntity().getDynamicExtensionsEntity().getName().equals(entityName))
		{
			return root;
		}

		for (IExpression child : joinGraph.getChildrenList(root))
		{
			IExpression wantedExpression = findExpression(entityName, child, joinGraph);
			if (wantedExpression != null)
			{
				return wantedExpression;
			}
		}

		return null;
	}

	/**Method to add OutputAttribute to output attribute list.
	 * @param outputAttributes output Attribute list
	 * @param expression associated expression
	 * @param attributeName attribute name for which OutputAttribute should be created
	 */
	public static void addOutputAttribute(List<IOutputAttribute> outputAttributes,
			IExpression expression, String attributeName)
	{
		QueryableAttributeInterface attribute = findAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity(), attributeName);
		outputAttributes.add(new OutputAttribute(expression, attribute));
	}

	/**
	 * To search attribute in the Entity.
	 *
	 * @param entity The Dynamic Extension Entity
	 * @param attributeName The name of the attribute to search.
	 * @return The corresponding attribute.
	 */
	public static QueryableAttributeInterface findAttribute(QueryableObjectInterface entity,
			String attributeName)
	{
		Collection<QueryableAttributeInterface> attributes = entity.getEntityAttributesForQuery();
		for (QueryableAttributeInterface attribute : attributes)
		{
			if (attribute.getName().equals(attributeName))
			{
				return attribute;
			}
		}

		return null;
	}

	/**
	 * It will add the condition on the attribute with the name as given attributeName in the
	 * QueryableObject of the expression.
	 * @param expression expression
	 * @param attributeName attribute Name
	 */
	public static void addBasicConditionsOnCategory(IExpression expression, String attributeName)
	{
		addCondition(expression, attributeName, RelationalOperator.IsNotNull);

	}

	/**
	 * This method creates queryable object for an entity or a category.
	 * @param expressionType name of expression
	 * @param isCategory if it is category or not.
	 * @return queryable object
	 * @throws DynamicExtensionsSystemException Dynamic Extensions System Exception
	 * @throws DynamicExtensionsApplicationException Dynamic Extensions Application Exception
	 */
	public static QueryableObjectInterface createQueryableObject(String expressionType,
			boolean isCategory) throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException
	{
		QueryableObjectInterface queryableObject = null;
		if (isCategory)
		{
			CategoryInterface category = getCategoryByname(expressionType);
			queryableObject = QueryableObjectUtility.createQueryableObject(category);
		}
		else
		{
			EntityInterface entity = entityManager.getEntityByName(expressionType);
			queryableObject = QueryableObjectUtility.createQueryableObject(entity);
		}
		return queryableObject;
	}

	/**
	 * To getEntityByName.
	 * @param entityName entityName
	 * @return EntityInterface EntityInterface
	 * @throws DynamicExtensionsSystemException DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException DynamicExtensionsApplicationException
	 */
	public static EntityInterface getEntityByName(String entityName)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		return entityManager.getEntityByName(entityName);
	}
}
