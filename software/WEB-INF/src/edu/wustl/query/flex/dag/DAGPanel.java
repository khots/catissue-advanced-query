
package edu.wustl.query.flex.dag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.cab2b.client.ui.dag.PathLink;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.category.CategoryProcessorUtility;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.query.queryobject.locator.Position;
import edu.wustl.common.query.queryobject.locator.QueryNodeLocator;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IAssociation;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.associations.impl.IntraModelAssociation;
import edu.wustl.common.querysuite.metadata.associations.impl.ModelAssociation;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.metadata.path.Path;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConnector;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateLiteral;
import edu.wustl.common.querysuite.queryobject.IDateOffsetAttribute;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.INumericLiteral;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.DateLiteral;
import edu.wustl.common.querysuite.queryobject.impl.DateOffsetAttribute;
import edu.wustl.common.querysuite.queryobject.impl.DateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.impl.Expression;
import edu.wustl.common.querysuite.queryobject.impl.ExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.querysuite.queryobject.impl.NumericLiteral;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.querysuite.queryobject.impl.Rule;
import edu.wustl.common.querysuite.utils.ConstraintsObjectBuilder;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.Collections;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.HtmlProvider;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.util.querysuite.TemporalQueryUtility;


/**
 *The class is responsible controlling all activities of Flex DAG
 *
 *@author aniket_pandit
 */

/**
 * @author rinku_rohra
 *
 */
public class DAGPanel
{
	/**
	 * Default Serial Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/** logger for Dag Panel. **/
	private static org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(DAGPanel.class);
	/** logger for Dag Panel. **/
	private IConstraintsObjectBuilderInterface mQueryObject;
	/** Path finder object. **/
	private IPathFinder mPathFinder;
	/** expression in query. **/
	private IExpression expression ;
	/** variable to store paths. **/
	private Map<String, IPath> mPathMap = new HashMap<String, IPath>();
	/** position map. **/
	private Map<Integer, Position> positionMap ;
	/** Http session. **/
	private HttpSession session ;
	/**
	 * dag panel Constructor.
	 * @param pathFinder path finder
	 */
	public DAGPanel(IPathFinder pathFinder)
	{
		mPathFinder = pathFinder;
		HttpServletRequest request = flex.messaging.FlexContext.getHttpRequest();
		session = request.getSession();
	}

	/**
	 * This method creates the expression to be added in query and
	 * returns corresponding DAG Node.
	 * @param expressionId Id of the Expression
	 * @param isOutputView type of the view for the node
	 * @return DagNode : DAG Node object
	 */
	private DAGNode createNode(int expressionId, boolean isOutputView)
	{
		IExpression expression = mQueryObject.getQuery().getConstraints().getExpression(
				expressionId);
		IQueryEntity constraintEntity = expression.getQueryEntity();
		DAGNode dagNode = new DAGNode();
		/*dagNode.setNodeName(edu.wustl.cab2b.common.util.Utility.getOnlyEntityName(constraintEntity
				.getDynamicExtensionsEntity().getEntity()));*/
		dagNode.setNodeName(QueryableObjectUtility.getQueryableObjectName(constraintEntity
				.getDynamicExtensionsEntity()));
		dagNode.setExpressionId(expression.getExpressionId());
		if (isOutputView)
		{
			dagNode.setNodeType(DAGConstant.VIEW_ONLY_NODE);
		}
		else
		{
			dagNode.setToolTip(expression);
		}
		return dagNode;
	}

	/**
	 *
	 * @param strToCreateQueryObject string to create Query object
	 * @param entityName Name of entity
	 * @param mode add or edit
	 * @return DAG Node Object
	 */
	public DAGNode createQueryObject(String strToCreateQueryObject, String entityName, String mode)
	{
		DAGNode node = null;
		String qtype = (String) session.getAttribute(Constants.Query_Type);
		List<Integer> expressionIdsList = (List<Integer>) session
				.getAttribute("allLimitExpressionIds");
		if (expressionIdsList == null)
		{
			expressionIdsList = new ArrayList<Integer>();
		}
		IQuery query = setQueryToObjectBuilder();
		String querytype=query.getType();
		if(querytype==null)
		{
		  ((Query) query).setType(qtype);
		}
		session.setAttribute(DAGConstant.QUERY_OBJECT, query);
		try
		{
			Long entityId = Long.parseLong(entityName);
			QueryableObjectInterface queryableObject = QueryableObjectUtility
					.getQueryableObjectFromCache(entityId);
			CreateQueryObjectBizLogic queryBizLogic = new CreateQueryObjectBizLogic();
			node = createQueryObjectLogic(strToCreateQueryObject, mode,queryableObject,
					queryBizLogic);
		}
		catch (DynamicExtensionsSystemException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (DynamicExtensionsApplicationException e)
		{
			logger.error(e.getMessage(), e);
		}
		expressionIdsList.add(Integer.valueOf(node.getExpressionId()));
		return node;
	}

	/**
	 * This method sets query to mQueryObject.
	 * @return IQuery object
	 */
	private IQuery setQueryToObjectBuilder()
	{
		IQuery query = (IQuery) session.getAttribute(DAGConstant.QUERY_OBJECT);
		// Get existing Query object from server
		Long userId = ((SessionDataBean) session
				.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA)).getUserId();
		if (query == null)
		{
			mQueryObject = new ConstraintsObjectBuilder();
			query = mQueryObject.getQuery();
			query.setCreatedDate(new Date());
			query.setUpdationDate(new Date());
			query.setCreatedBy(userId);
			query.setUpdatedBy(userId);
		}
		else
		{
			mQueryObject.setQuery(query);
		}
		return query;
	}

	/**
	 * @param strToCreateQueryObject String to create Query Object
	 * @param mode it specifies weather it is new Query or Edit Query mode.
	 * @param entity entity
	 * @param queryBizLogic Query BizLogic
	 * @return DagNode
	 * @throws DynamicExtensionsSystemException DE exception
	 * @throws DynamicExtensionsApplicationException DE exception
	 */
	private DAGNode createQueryObjectLogic(String strToCreateQueryObject, String mode,
			 QueryableObjectInterface entity, CreateQueryObjectBizLogic queryBizLogic)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		Map ruleDetailsMap;
		int expressionId;
		DAGNode node=null;
		if (strToCreateQueryObject.equalsIgnoreCase(""))
		{
			if (Constants.EDIT_MODE.equals(mode))
			{
				Rule rule = ((Rule) (expression.getOperand(0)));
				updateExistingConditionsInRule(rule, new ArrayList<ICondition>());
				expressionId = expression.getExpressionId();
			}
			else
			{
				IRule rule = QueryObjectFactory.createRule();
				expressionId = mQueryObject.addExpression(rule, entity);
			}
			node = createNode(expressionId, false);
		}
		else
		{
			ruleDetailsMap = queryBizLogic.getRuleDetailsMap(strToCreateQueryObject, entity
					.getEntityAttributesForQuery());
			node = updateRuleInExpression(mode, entity,ruleDetailsMap);
		}
		return node;
	}
	/**
	 * This method updates rules in expression.
	 * @param mode add or edit
	 * @param entity Entity for which rules to be added
	 * @param ruleDetailsMap map for rule details
	 * @return DAG Node object
	 */
	private DAGNode updateRuleInExpression(String mode, QueryableObjectInterface entity, Map ruleDetailsMap)
	{
		DAGNode node;
		int expressionId;
		List<QueryableAttributeInterface> attributes = (List<QueryableAttributeInterface>) ruleDetailsMap
				.get(Constants.ATTRIBUTES);
		List<String> attributeOperators = (List<String>) ruleDetailsMap
				.get(Constants.ATTRIBUTE_OPERATORS);
		List<List<String>> conditionValues = (List<List<String>>) ruleDetailsMap
				.get(Constants.ATTR_VALUES);
		String errMsg = (String) ruleDetailsMap.get(Constants.ERROR_MESSAGE);
		if ("".equals(errMsg))
		{
			if (Constants.EDIT_MODE.equals(mode))
			{
				Rule rule = ((Rule) (expression.getOperand(0)));
				//rule.removeAllConditions();
				List<ICondition> conditionsList = ((ConstraintsObjectBuilder) mQueryObject)
						.getConditions(attributes, attributeOperators, conditionValues);
				updateExistingConditionsInRule(rule, conditionsList);
				expressionId = expression.getExpressionId();
				node = createNode(expressionId, false);
			}
			else
			{
				expressionId = mQueryObject.addRule(attributes, attributeOperators,
						conditionValues, entity);
				node = createNode(expressionId, false);
			}
			node.setErrorMsg(errMsg);
		}
		else
		{
			node = new DAGNode();
			node.setErrorMsg(errMsg);
		}
		return node;
	}

	/**
	 * This method will update the Conditions in the rule according to the
	 * new Conditions specified in the conditionList.
	 * If the condition is removed in the new List which is used as parameter before then
	 * it will put an empty condition in place of that condition,
	 * else will remove that condition.
	 * @param rule existing rule
	 * @param conditionsList list of the conditions
	 */
	private void updateExistingConditionsInRule(Rule rule, List<ICondition> conditionsList)
	{
		// whatever conditions do we need to add to the rule are added in this list & finally we
		// add conditions in this list to the rule, removing all its conditions.
		List<ICondition> conditionsToAdd = new ArrayList<ICondition>();
		for (ICondition condition : rule)
		{
			ICondition newCondition = edu.wustl.query.util.global.Utility
					.getSimilarConditionFromConditionList(condition, conditionsList);
			if (newCondition == null)
			{
				/**
				 * old condition not present in new conditions then check weather
				 * it has associated parameter
				 */
				IParameter<?> parameter = edu.wustl.query.util.global.Utility
						.getParameterForCondition(condition,
							(IParameterizedQuery) mQueryObject.getQuery());
				if (parameter == null)
				{ // parameter not present , it means remove the condition
					continue;
				}
				else
				{
					/**
					 *  parameter present & condition removed  hence
					 *  update the condition to empty condition
					 */
					edu.wustl.query.util.global.Utility.
						updateConditionToEmptyCondition(condition);
				}
			}
			else
			{	//old condition present in the edited conditions.
				condition.setIsSystemGenerated(newCondition.getIsSystemGenerated());
				condition.setRelationalOperator(newCondition.getRelationalOperator());
				condition.setValue(newCondition.getValue());
				condition.setValues(newCondition.getValues());
			}
			conditionsToAdd.add(condition);
		}
		//now add the new conditions added in this expression
		addNewConditionsToRule(rule, conditionsList, conditionsToAdd);
	}

	/**
	 * This method adds the new conditions to rule.
	 * @param rule IRule present in query.
	 * @param conditionsList list of conditions
	 * @param conditionsToAdd conditions to be added in rule.
	 */
	private void addNewConditionsToRule(Rule rule, List<ICondition> conditionsList,
			List<ICondition> conditionsToAdd)
	{
		//now add the new conditions added in this expression
		for (ICondition condition : conditionsList)
		{
			ICondition newCondition = edu.wustl.query.util.global.Utility.
				getSimilarConditionFromConditionList(condition,conditionsToAdd);
			if (newCondition == null)
			{	//condition not already added hence add it to the list
				conditionsToAdd.add(condition);
			}
		}
		rule.removeAllConditions();
		for (ICondition condition : conditionsToAdd)
		{
			rule.addCondition(condition);
		}
	}

	/**
	 * Sets ClientQueryBuilder object.
	 * @param queryObject constraintObjectBuilder Object
	 */
	public void setQueryObject(IConstraintsObjectBuilderInterface queryObject)
	{
		mQueryObject = queryObject;
	}

	/**
	 * Sets Expression.
	 * @param expression expression to set
	 */
	public void setExpression(IExpression expression)
	{
		this.expression = expression;
	}

	/**
	 * Links two nodes.
	 * @param sourceNode source node
	 * @param destNode destination node
	 * @param paths List of paths present between them
	 * @return list of dag paths
	 */
	public List<DAGPath> linkNode(final DAGNode sourceNode, final DAGNode destNode,
			List<IPath> paths)
	{
		List<DAGPath> dagPathList = null;
		if (paths != null && !paths.isEmpty())
		{
			dagPathList = new ArrayList<DAGPath>();
			int sourceExpressionId = sourceNode.getExpressionId();
			int destExpressionId = destNode.getExpressionId();
			if (!mQueryObject.isPathCreatesCyclicGraph(sourceExpressionId, destExpressionId, paths
					.get(0)))
			{
				for (int i = 0; i < paths.size(); i++)
				{
					IPath path = paths.get(i);
					createLogForPah(path);
					path = clonePath(path);
					linkTwoNode(sourceNode, destNode, path);
					String pathStr = Long.valueOf(path.getPathId()).toString();
					DAGPath dagPath = new DAGPath();
					dagPath.setToolTip(getPathDisplayString(path));
					dagPath.setId(pathStr);
					dagPath.setSourceExpId(sourceNode.getExpressionId());
					dagPath.setDestinationExpId(destNode.getExpressionId());
					dagPathList.add(dagPath);
					String key = pathStr + "_" + sourceNode.getExpressionId() + "_"
							+ destNode.getExpressionId();
					mPathMap.put(key, path);
				}
			}
		}
		return dagPathList;
	}

	/**
	 * It will create a long for the path to be added.
	 * @param path path to log
	 */
	private void createLogForPah(IPath path)
	{
		logger.info("In link node method of DagPanel.java ");
		logger.info("PAth Id id : "+ path.getPathId());
		logger.info("source Entity : "+ path.getSourceEntity());
		logger.info("destination Entity : "+ path.getTargetEntity());
		for(IAssociation association : 	path.getIntermediateAssociations())
		{
			logger.info("Intramodel Association Id : "+((ModelAssociation)association).getId());
		}

	}

	/**
	 * Gets list of paths between two nodes.
	 * @param sourceNode source node
	 * @param destNode destination node
	 * @return paths between the source & destination node.
	 */
	public List<IPath> getPaths(DAGNode sourceNode, DAGNode destNode)
	{
		Map<AmbiguityObject, List<IPath>> map = null;
		AmbiguityObject ambiguityObject = null;
		try
		{
			IQuery query = mQueryObject.getQuery();
			IConstraints constraints = query.getConstraints();
			int expressionId = sourceNode.getExpressionId();
			IExpression expression = constraints.getExpression(expressionId);
			IQueryEntity sourceEntity = expression.getQueryEntity();
			expressionId = destNode.getExpressionId();
			expression = constraints.getExpression(expressionId);
			IQueryEntity destinationEntity = expression.getQueryEntity();

			ambiguityObject = new AmbiguityObject(sourceEntity.getDynamicExtensionsEntity()
					.getRootQueryableObject().getEntity(), destinationEntity
					.getDynamicExtensionsEntity().getRootQueryableObject().getEntity());
			//			ResolveAmbiguity resolveAmbigity = new ResolveAmbiguity(
			//			ambiguityObject, m_pathFinder);
			DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject,
					mPathFinder);
			map = resolveAmbigity.getPathsForAllAmbiguities();

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return map.get(ambiguityObject);
	}

	/**
	 * This method gets attribute by identifier.
	 * @param query IQuery object
	 * @param nodeExpressionId node expression id
	 * @param firstAttributeId attribute identifier
	 * @return queryable attribute
	 */
	private QueryableAttributeInterface getAttributeIdentifier(IQuery query, int nodeExpressionId,
			String firstAttributeId)
	{
		Long identifier = Long.valueOf(firstAttributeId);
		IConstraints constraints = query.getConstraints();
		IExpression expression = constraints.getExpression(nodeExpressionId);
		IQueryEntity sourceEntity = expression.getQueryEntity();
		QueryableAttributeInterface srcAttributeByIdentifier = null;
		QueryableObjectInterface dynamicExtensionsEntity = sourceEntity.getDynamicExtensionsEntity();
		srcAttributeByIdentifier = dynamicExtensionsEntity.getAttributeByIdentifier(identifier);
		return srcAttributeByIdentifier;
	}

	/**
	 * This method forms custom formula for single node TQ.
	 * @param node from which to form the customFormula
	 * @param operation specifies weather it is add or edit operation.
	 * @return SingleNodeCustomFormulaNode
	 */
	public SingleNodeCustomFormulaNode formSingleNodeFormula(SingleNodeCustomFormulaNode node,
			String operation)
	{
		//Forming custom Formula
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		SingalNodeTemporalQuery singalNodeTq = new SingalNodeTemporalQuery();
		int nodeExpressionId = node.getNodeExpressionId();
		singalNodeTq.setEntityExpressionId(nodeExpressionId);
		IExpression entityExpression = constraints.getExpression(nodeExpressionId);
		singalNodeTq.setEntityIExpression(entityExpression);
		//Setting Attribute by ID
		singalNodeTq.setAttributeById(getAttributeIdentifier(query, node.getNodeExpressionId(),
				node.getAttributeID()));
		//Setting attribute type
		singalNodeTq.setAttributeType(node.getAttributeType());
		//Setting Arithmetic Operator
		singalNodeTq.setArithOp(getArithmeticOperator(node.getSelectedArithmeticOp()));
		//Setting Relational Operator
		singalNodeTq.setRelOp(getRelationalOperator(node.getSelectedLogicalOp()));
		singalNodeTq.setICon(QueryObjectFactory
				.createArithmeticConnector(singalNodeTq.getArithOp()));
		//Creating LHS Literals
		singalNodeTq.createLeftLiterals(node.getLhsTimeValue(), node.getLhsTimeInterval());
		//Create Expressions
		singalNodeTq.createExpressions();
		checkSelectedLogicalOperator(node, operation,singalNodeTq, entityExpression);
		String oprs = setOperation(node.getOperation());
		if (oprs != null)
		{
			node.setOperation(oprs);
		}
		return node;
	}

	/**
	 *
	 * @param node for single node custom formula.
	 * @param operation edit/add.
	 * @param singalNodeTq SingalNodeTemporalQuery
	 * @param entityExpression expression
	 */
	private void checkSelectedLogicalOperator(SingleNodeCustomFormulaNode node, String operation,
			SingalNodeTemporalQuery singalNodeTq, IExpression entityExpression)
	{
		IQuery query = mQueryObject.getQuery();
		if (!(node.getSelectedLogicalOp().equals(DAGConstant.NULL_STRING)))
		{
			if(singalNodeTq.getRelOp().getStringRepresentation().equalsIgnoreCase(Constants.Between))
			{
				singalNodeTq.createBothLiterals(node.getTimeInterval1(), node.getTimeValue1(), node
						.getTimeInterval2(), node.getTimeValue2());
			}
			else
			{
				//Creating RHS Literals
				singalNodeTq.createRightLiterals(node.getTimeValue1(), node.getTimeInterval1());
			}

			//Create LHS Terms and RHS Terms
			singalNodeTq.createLHSAndRHS();
			ICustomFormula customFormula = createSingleNodeCustomFormula(singalNodeTq, operation,
					node.getName());
			if (operation.equalsIgnoreCase(edu.wustl.common.util.global.Constants.ADD))
			{
				CustomFormulaUIBean bean = createTQUIBean(customFormula, null, node, null);
				bean.setCalculatedResult(false);
				populateUIMap(node.getName(), bean);
				entityExpression.addOperand(getAndConnector(), customFormula);
				entityExpression.setInView(true);
			}
			else if (operation.equalsIgnoreCase(edu.wustl.common.util.global.Constants.EDIT))
			{
				updateSingleNodeCN(node);
			}
			CategoryProcessorUtility.addOutputTermsToQuery(query, customFormula, node
					.getCustomColumnName());
		}
	}

	/*private void createSingleNodeCP(SingleNodeCustomFormulaNode node, String operation,
			IQuery query, SingalNodeTemporalQuery singalNodeTq)
	{
		singalNodeTq.createOnlyLHS();
		IOutputTerm outputTerm = createOutputTerm(operation, node.getName());
		outputTerm.setTerm(singalNodeTq.getLhsTerm());
		singalNodeTq.setRhsTimeInterval(singalNodeTq.getTimeInterval(node.getTimeInterval()));
		outputTerm.setTimeInterval(singalNodeTq.getTimeInterval(node.getCcInterval()));
		String tqColumnName = "";
		if (node.getCcInterval() != DAGConstant.NULL_STRING)
		{
			tqColumnName = node.getCustomColumnName() + " (" + node.getCcInterval() + ")";
		}
		else
		{
			tqColumnName = node.getCustomColumnName();
		}

		if (operation.equals(Constants.ADD))
		{
			CustomFormulaUIBean bean = createTQUIBean(null, null, node, outputTerm);
			bean.setCalculatedResult(true);
			//String crNodeName = "c_" + node.getName();
			populateUIMap(node.getName(), bean);
			outputTerm.setName(tqColumnName);
			query.getOutputTerms().add(outputTerm);
		}
		else
		{
			outputTerm.setName(tqColumnName);
			updateSingleNodeCN(node);
		}
	}*/


	/**
	 * It will update the custom formula.
	 * @param node node to be updated
	 */
	@SuppressWarnings("unchecked")
	private void updateSingleNodeCN(SingleNodeCustomFormulaNode node)
	{
		String nodeId = node.getName();
		Map<String, CustomFormulaUIBean> tqUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute(DAGConstant.TQUIMap);
		if (tqUIMap != null)
		{
			CustomFormulaUIBean uiBean = tqUIMap.get(nodeId);
			if (uiBean != null && uiBean.getSingleNode() != null)
			{
				uiBean.setSingleNode(node);
			}
		}
	}

	/**
	 * It will form the two node temporal query using the given node.
	 * @param node node using which to form the customFormuala
	 * @param operation specifies weather it is a add or edit operation.
	 * @return the custom Formula
	 */
	public CustomFormulaNode formTemporalQuery(CustomFormulaNode node, String operation)
	{
		IQuery query = mQueryObject.getQuery();
		TwoNodesTemporalQuery tqBean = setValuesInTQBean(node);
		if(tqBean.getRelOp().getStringRepresentation().equalsIgnoreCase(Constants.Between))
		{
			tqBean.createBothLiterals(node.getTimeInterval1(), node.getTimeValue1(),
					node.getTimeInterval2(), node.getTimeValue2());
		}
		else
		{
			tqBean.createLiterals(node.getTimeInterval1(), node.getTimeValue1());
		}
		tqBean.createLHSAndRHS();
		ICustomFormula customFormula = createCustomFormula(tqBean, operation, node.getName());
		if (operation.equalsIgnoreCase(edu.wustl.common.util.global.Constants.ADD))
		{
			CustomFormulaUIBean bean = createTQUIBean(customFormula, node, null, null);
			bean.setCalculatedResult(false);
			populateUIMap(node.getName(), bean);
			tqBean.getSrcIExpression().addOperand(getAndConnector(), customFormula);
			tqBean.getSrcIExpression().setInView(true);
		}
		else if (operation.equalsIgnoreCase(edu.wustl.common.util.global.Constants.EDIT))
		{
			updateTwoNodesCN(node);
		}
		CategoryProcessorUtility.addOutputTermsToQuery(query, customFormula, node.getCustomColumnName());
		String oprs = setOperation(node.getOperation());
		if (oprs != null)
		{
			node.setOperation(oprs);
		}
		return node;
	}

	/**
	 * This method creates bean for two node TQ.
	 * @param node custom formula node.
	 * @return TwoNodesTemporalQuery
	 */
	private TwoNodesTemporalQuery setValuesInTQBean(CustomFormulaNode node)
	{
		TwoNodesTemporalQuery tqBean = new TwoNodesTemporalQuery();
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		int srcExpressionId = node.getFirstNodeExpId();
		tqBean.setSrcExpressionId(srcExpressionId);
		IExpression srcIExpression = constraints.getExpression(srcExpressionId);
		//Setting the src IExpression
		tqBean.setSrcIExpression(srcIExpression);
		tqBean.setSrcAttributeById(getAttributeIdentifier(query, node.getFirstNodeExpId(), node
				.getFirstSelectedAttrId()));
		int destExpressionId = node.getSecondNodeExpId();
		tqBean.setDestExpressionId(destExpressionId);
		tqBean.setDestAttributeById(getAttributeIdentifier(query, node.getSecondNodeExpId(), node
				.getSecondSelectedAttrId()));
		//Setting the dest IExpression
		tqBean.setDestIExpression(constraints.getExpression(destExpressionId));
		//Setting the attribute Types
		tqBean.setFirstAttributeType(node.getFirstSelectedAttrType());
		tqBean.setSecondAttributeType(node.getSecondSelectedAttrType());
		//Setting the Arithmetic operator
		tqBean.setArithOp(getArithmeticOperator(node.getSelectedArithmeticOp()));
		//Setting the Relational Ops
		RelationalOperator relationalOp = getRelationalOperator(node.getSelectedLogicalOp());
		tqBean.setRelOp(relationalOp);
		setQAttrInterval(node, tqBean);

		//Creating all expressions
		tqBean.createExpressions();
		tqBean.setICon(QueryObjectFactory.createArithmeticConnector(tqBean.getArithOp()));
		return tqBean;
	}

	/**
	 * This method sets the time interval in temporal query bean.
	 * @param node CustomFormulaNode
	 * @param tqBean TwoNodesTemporalQuery
	 */
	private void setQAttrInterval(CustomFormulaNode node, TwoNodesTemporalQuery tqBean)
	{
		//Setting the qAttrInterval1
		if (node.getQAttrInterval1().equals(DAGConstant.NULL_STRING))
		{
			tqBean.setQAttrInterval1(null);
		}
		else
		{
			tqBean.setQAttrTInterval1(node.getQAttrInterval1());
		}
		//Setting the qAttrInterval2
		if (node.getQAttrInterval2().equals(DAGConstant.NULL_STRING))
		{
			tqBean.setQAttrInterval2(null);
		}
		else
		{
			tqBean.setQAttrTInterval2(node.getQAttrInterval2());
		}
	}

	/**
	 * It will update the two node custom Formula.
	 * @param node node whose custom formula to be updated.
	 */
	private void updateTwoNodesCN(CustomFormulaNode node)
	{
		String nodeId = node.getName();
		Map<String, CustomFormulaUIBean> tqUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute(DAGConstant.TQUIMap);
		if (tqUIMap != null)
		{
			CustomFormulaUIBean uiBean = tqUIMap.get(nodeId);
			if (uiBean != null && uiBean.getTwoNode() != null)
			{
				uiBean.setTwoNode(node);
			}
		}
	}

	/*private IOutputTerm createOutputTerm(String operation, String nodeId)
	{
		if (operation.equals(Constants.ADD))
		{
			return QueryObjectFactory.createOutputTerm();
		}
		else
		{
			return getExistingOutputTerm(nodeId);
		}
	}*/

	/*private IOutputTerm getExistingOutputTerm(String nodeId)
	{
		IOutputTerm outputTerm = null;
		HttpServletRequest request = flex.messaging.FlexContext.getHttpRequest();
		HttpSession session = request.getSession();
		Map<String, CustomFormulaUIBean> TQUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute("TQUIMap");
		if (TQUIMap != null)
		{
			CustomFormulaUIBean customFormulaUIBean = TQUIMap.get(nodeId);
			if (customFormulaUIBean.isCalculatedResult())
			{
				outputTerm = customFormulaUIBean.getOutputTerm();
			}
		}
		return outputTerm;
	}*/

	/**
	 * This method creates new UI bean.
	 * @param customFormula ICustomFormula
	 * @param twoNode CustomFormulaNode
	 * @param singleNode SingleNodeCustomFormulaNode
	 * @param outputTerm IOutputTerm
	 * @return CustomFormulaUIBean
	 */
	private CustomFormulaUIBean createTQUIBean(ICustomFormula customFormula,
			CustomFormulaNode twoNode, SingleNodeCustomFormulaNode singleNode,
			IOutputTerm outputTerm)
	{
		return new CustomFormulaUIBean(customFormula, twoNode, singleNode, outputTerm);
	}

	/**
	 * Setting the corresponding operation.
	 * @param nodeOperation operation
	 * @return operation string.
	 */
	private String setOperation(String nodeOperation)
	{
		String isRepaint = (String) session.getAttribute(DAGConstant.ISREPAINT);
		String operation = null;
		if (isRepaint == null)
		{
			session.setAttribute(DAGConstant.ISREPAINT, DAGConstant.ISREPAINT_FALSE);
		}
		else
		{
			if (!(isRepaint.equals(DAGConstant.ISREPAINT_FALSE)))
			{
				if ((isRepaint.equals(DAGConstant.ISREPAINT_TRUE))
						&& (nodeOperation.equals(DAGConstant.EDIT_OPERATION)))
				{
					operation = DAGConstant.REPAINT_EDIT;
				}
				else
				{
					operation = DAGConstant.REPAINT_CREATE;
				}
			}
		}
		return operation;
	}

	/**
	 * It will create the singleNode custom formula.
	 * @param singleNodeTq SingalNodeTemporalQuery object using which to form the Custom formula
	 * @param operation weather add or edit custom formula.
	 * @param nodeId in case of edit operation to update the existing custom formula.
	 * @return ICustomFormula
	 */
	private ICustomFormula createSingleNodeCustomFormula(SingalNodeTemporalQuery singleNodeTq,
			String operation, String nodeId)
	{
		ICustomFormula customFormula = null;
		if (operation.equalsIgnoreCase(edu.wustl.common.util.global.Constants.ADD))
		{
			customFormula = getSingleNodeCustomFormula(QueryObjectFactory.createCustomFormula(),
					singleNodeTq);
		}
		else
		{
			customFormula = getSingleNodeCustomFormula(getExistingCustomFormula(nodeId), singleNodeTq);
		}
		return customFormula;
	}

	/**
	 * This method gets custom formula for single node TQ.
	 * @param customFormula ICustomFormula
	 * @param singleNodeTq SingalNodeTemporalQuery
	 * @return ICustomFormula
	 */
	private ICustomFormula getSingleNodeCustomFormula(ICustomFormula customFormula,
			SingalNodeTemporalQuery singleNodeTq)
	{
		customFormula.setLhs(singleNodeTq.getLhsTerm());
		customFormula.getAllRhs().clear();

		ArrayList<ITerm> rhsTermsList = singleNodeTq.getRhsTermsList();
		if (!rhsTermsList.isEmpty())
		{
			for (int i = 0; i < rhsTermsList.size(); i++)
			{
				customFormula.addRhs(rhsTermsList.get(i));
			}
		}

		customFormula.setOperator(singleNodeTq.getRelOp());
		return customFormula;
	}

	/**
	 * It will create the custom formula.
	 * @param tqBean TwoNodesTemporalQuery bean object
	 * @param operation weather add or edit custom formula.
	 * @param nodeId in case of edit operation to update the existing custom formula.
	 * @return ICustomFormula
	 */
	private ICustomFormula createCustomFormula(TwoNodesTemporalQuery tqBean, String operation,
			String nodeId)
	{
		ICustomFormula customFormula = null;
		if (operation.equalsIgnoreCase(edu.wustl.common.util.global.Constants.ADD))
		{
			customFormula = getCustomFormula(QueryObjectFactory.createCustomFormula(), tqBean);
		}
		else
		{
			customFormula = getCustomFormula(getExistingCustomFormula(nodeId), tqBean);
		}
		return customFormula;
	}

	/**
	 * This method populates UI map for temporal query.
	 * @param formulaId custom formula id
	 * @param customFormulaUIBean CustomFormulaUIBean
	 */
	private void populateUIMap(String formulaId, CustomFormulaUIBean customFormulaUIBean)
	{
		Map<String, CustomFormulaUIBean> tqUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute(DAGConstant.TQUIMap);
		if (tqUIMap == null)
		{
			tqUIMap = new HashMap<String, CustomFormulaUIBean>();
			session.setAttribute(DAGConstant.TQUIMap, tqUIMap);
		}
		tqUIMap.put(formulaId, customFormulaUIBean);
	}

	/**
	 * This method sets lhs and rhs to custom formula.
	 * @param customFormula will update the given custom formula with the LHS & RhS.
	 * @param tqBean bean used to update the Formuala
	 * @return customFormula
	 */
	private ICustomFormula getCustomFormula(ICustomFormula customFormula,
			TwoNodesTemporalQuery tqBean)
	{
		customFormula.setLhs(tqBean.getLhsTerm());
		customFormula.getAllRhs().clear();
		ArrayList <ITerm> rhsTermsList = tqBean.getRhsTermsList();
		if(!rhsTermsList.isEmpty())
		{
			for(int i=0; i<rhsTermsList.size(); i++)
			{
				customFormula.addRhs(rhsTermsList.get(i));
			}
		}
		customFormula.setOperator(tqBean.getRelOp());
		return customFormula;
	}

	/**
	 *
	 * @param formulaId custom formula id
	 * @return ICustomFormula
	 */
	public ICustomFormula getExistingCustomFormula(String formulaId)
	{
		ICustomFormula customFormula = null;
		Map<String, CustomFormulaUIBean> tqUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute("TQUIMap");
		if (tqUIMap != null)
		{
			CustomFormulaUIBean customFormulaUIBean = tqUIMap.get(formulaId);
			if (!customFormulaUIBean.isCalculatedResult())
			{
				customFormula = customFormulaUIBean.getCustomFormula();
				deleteOutputTerm(customFormulaUIBean);
			}
		}
		return customFormula;
	}

	/**
	 * @param customFormulaUIBean bean object
	 */
	private void deleteOutputTerm(CustomFormulaUIBean customFormulaUIBean)
	{
		IQuery query = mQueryObject.getQuery();
		List<IOutputTerm> outputTerms = query.getOutputTerms();
		IOutputTerm termToDelete = null;
		for (IOutputTerm term : outputTerms)
		{
			if (customFormulaUIBean.getCustomFormula().getLhs().equals(term.getTerm()))
			{
				termToDelete = term;
				break;
			}
		}
		if (termToDelete != null)
		{
			query.getOutputTerms().remove(termToDelete);
		}
	}

	/**
	 *
	 * @param relationalOp relational operator
	 * @return RelationalOperator
	 */
	private RelationalOperator getRelationalOperator(String relationalOp)
	{
		RelationalOperator relOp = null;
		for (RelationalOperator operator : RelationalOperator.values())
		{
			if ((operator.getStringRepresentation().equals(relationalOp)))
			{
				relOp = operator;
				break;
			}
		}
		return relOp;
	}

	/**
	 *
	 * @param arithmeticOp arithmetic operator
	 * @return ArithmeticOperator
	 */
	private ArithmeticOperator getArithmeticOperator(String arithmeticOp)
	{
		ArithmeticOperator arithOp = null;
		for (ArithmeticOperator operator : ArithmeticOperator.values())
		{
			if (operator.mathString().equals(arithmeticOp))
			{
				arithOp = operator;
				break;
			}
		}
		return arithOp;
	}

	/**
	 * @return IConnector
	 */
	private static IConnector<LogicalOperator> getAndConnector()
	{
		return QueryObjectFactory.createLogicalConnector(LogicalOperator.And);
	}

	/**
	 * @param dagNode Dag node to be validated.
	 * @return boolean
	 */
	public boolean checkForNodeValidAttributes(DAGNode dagNode)
	{
		return checkIfValidNode(dagNode);
	}

	/**
	 *
	 * @param linkedNodeList list of dag nodes to be validated
	 * @return String
	 */
	public String checkForValidAttributes(List<DAGNode> linkedNodeList)
	{
		String error = "";
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		DAGNode sourceNode = linkedNodeList.get(0);
		DAGNode destinationNode = linkedNodeList.get(1);
		int sourceExpressionId = sourceNode.getExpressionId();
		IExpression sourceExpression = constraints.getExpression(sourceExpressionId);
		/**
		 * Checking if source node has a Date attribute
		 */
		boolean isSourceNodeValid = checkIfValidNode(sourceNode);
		boolean isDestNodeValid = checkIfValidNode(destinationNode);
		int destExpressionId = destinationNode.getExpressionId();
		IExpression destExpression = constraints.getExpression(destExpressionId);
		if(isSourceNodeValid && isDestNodeValid)
		{
			error = checkValidQuantitativeNode(sourceExpression, destExpression);
		}
		else
		{
			error = DAGConstant.ERROR_NODES_INVALID;
		}
		return error;

	}

	/**
	 * This method checks for validity for quantitative attributes.
	 * @param sourceExpression source expression
	 * @param destExpression destination expression
	 * @return error message
	 */
	private String checkValidQuantitativeNode(IExpression sourceExpression,
			IExpression destExpression)
	{
		String error;
		IQueryEntity sourceEntity = sourceExpression.getQueryEntity();
		IQueryEntity destEntity = destExpression.getQueryEntity();
		boolean areAllSourceAttrQuantitative = areAllQuantativeAtributes(sourceEntity
				.getDynamicExtensionsEntity().getAttributeCollection());
		boolean areAllDestAttrQuantitative = areAllQuantativeAtributes(destEntity
				.getDynamicExtensionsEntity().getAttributeCollection());
		if(areAllSourceAttrQuantitative && areAllDestAttrQuantitative)
		{
			error = checkQuantitativeAttr(sourceExpression, destExpression, sourceEntity,
					destEntity);
		}
		else if(areAllSourceAttrQuantitative || areAllDestAttrQuantitative)
		{
			error = DAGConstant.ENTITY_NAME_INVALID;
		}
		else
		{
			error = Constants.TRUE;
		}
		return error;
	}
	/**
	 *
	 * @param sourceExpression source expression
	 * @param destExpression destination expression
	 * @param sourceEntity source entity
	 * @param destEntity destination entity
	 * @return error message
	 */
	private String checkQuantitativeAttr(IExpression sourceExpression, IExpression destExpression,
			IQueryEntity sourceEntity, IQueryEntity destEntity)
	{
		String error;
		if(destEntity.getDynamicExtensionsEntity().getName().equals
				(sourceEntity.getDynamicExtensionsEntity().getName()))
		{
				//If all attributes of the Entity are tagged,
				//then it should have parent, else it is not valid
				//for TQ
				error = checkQuantitativeAttributesParent(sourceExpression, destExpression);
		}
		else
		{
			error = DAGConstant.ENTITY_NAME_INVALID;
		}
		return error;
	}
	/**
	 *
	 * @param sourceExpression source expression
	 * @param destExpression dest expression
	 * @return error message
	 */

	// TODO multiple parents for TQ
	private String checkQuantitativeAttributesParent(IExpression sourceExpression,
			IExpression destExpression)
	{
		String error = "";
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		List <IExpression> sourceParentList = constraints.getJoinGraph().getParentList(sourceExpression);
		List <IExpression> destParentList = constraints.getJoinGraph().getParentList(destExpression);
		if((!sourceParentList.isEmpty() && !destParentList.isEmpty() ))
		{
			error = checkIfParentSame(sourceParentList, destParentList);
		}
		else
		{
			error = DAGConstant.PARENT_INVALID;
		}
		return error;
	}

	/**
	 *
	 * @param sourceParentList list of expression
	 * @param destParentList list of expression
	 * @return error message
	 */
	private String checkIfParentSame(List<IExpression> sourceParentList,
			List<IExpression> destParentList)
	{
		String error = "";
		boolean areNodesValid = false;
		for (int i=0;i<sourceParentList.size() && !areNodesValid;i++)
		{
			IExpression sourceExpr = sourceParentList.get(i);
			String sourceEntityParentName = sourceExpr.getQueryEntity()
					.getDynamicExtensionsEntity().getName();
			for (IExpression destExpr : destParentList)
			{
				String destEntityParentName = destExpr.getQueryEntity()
				.getDynamicExtensionsEntity().getName();
				if(sourceEntityParentName.equals(destEntityParentName))
				{
					error = Constants.TRUE;
					areNodesValid = true;
					break;
				}
				else
				{
					error = DAGConstant.ENTITY_NAME_INVALID;
				}
			}
		}
		return error;
	}

	/**
	 * Checks if node in DAG is valid.
	 * @param sourceNode dag node
	 * @return isValid
	 */
	private boolean checkIfValidNode(DAGNode sourceNode)
	{
		boolean isValid = false;
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		int expressionId = sourceNode.getExpressionId();
		IExpression expression = constraints.getExpression(expressionId);

		/**
		 * Checking if source node has a Date attribute
		 */
		IQueryEntity sourceEntity = expression.getQueryEntity();
		Collection<QueryableAttributeInterface> sourceAttributeCollection = sourceEntity
				.getDynamicExtensionsEntity().getEntityAttributesForQuery();

		for (QueryableAttributeInterface attribute : sourceAttributeCollection)
		{
			isValid = checkSrcNodeDateAttribute(attribute);
			if(isValid)
			{
				break; //This node is valid for TQ
			}
		}
		return isValid;
	}

	/**
	 * This method checks if all attributes in an entity are quantitative.
	 * @param entityAttributeCollection attribute collection.
	 * @return areAllAttributesQuantative
	 */
	private boolean areAllQuantativeAtributes(
			Collection<QueryableAttributeInterface> entityAttributeCollection)
	 {
		 boolean areAllAttributesQuantative = true;
		 for (QueryableAttributeInterface attribute : entityAttributeCollection)
		 {
			 if(!attribute.isTagPresent(Constants.TAG_QUANTITATIVE_ATTRIBUTE))
			 {
				 areAllAttributesQuantative = false;
				 break;
			 }
		 }
		 return areAllAttributesQuantative;
	 }

	/**
	 * Checks if node valid for TQ.
	 * @param attribute QueryableAttributeInterface
	 * @return isValid
	 */
	private boolean checkSrcNodeDateAttribute(QueryableAttributeInterface attribute)
	{
		boolean isValid = false;
		String dataType = attribute.getDataType();
		if ((dataType.equals(Constants.DATE_TYPE)
				|| edu.wustl.query.util.global.Utility.isNumber(dataType))
				&& (!attribute.getName().equals(Constants.ID)))
		{
			isValid = true;
		}
		return isValid;
	}

	/**
	 * This method gets attribute collection for an expression.
	 * @param nodeExpId expression id.
	 * @return attribute collection.
	 */
	private Collection<QueryableAttributeInterface> getAttributeCollection(int nodeExpId)
	{
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		IExpression expression = constraints.getExpression(nodeExpId);
		IQueryEntity sourceEntity = expression.getQueryEntity();
		Collection<QueryableAttributeInterface> sourceAttributeCollection = sourceEntity
				.getDynamicExtensionsEntity().getEntityAttributesForQuery();
		return sourceAttributeCollection;

	}

	/**
	 * This method gets query data for single node TQ.
	 * @param sourceExpId expression id.
	 * @param nodeName node name
	 * @return query data map.
	 */
	public Map<String, Object> getSingleNodeQueryData(int sourceExpId, String nodeName)
	{
		Map<String, Object> queryDataMap = new HashMap<String, Object>();
		Map<String, List<String>> nodeAttributesMap = new HashMap<String, List<String>>();

		//Setting the Entity Name as Label
		String nodeNameLabel = Utility.getDisplayLabel(nodeName);
		List<String> entityLabelsList = new ArrayList<String>();
		entityLabelsList.add(nodeNameLabel);

		//Getting the Entity's data Map
		Collection<QueryableAttributeInterface> nodeAttributeCollection =
				getAttributeCollection(sourceExpId);
		populateMap(nodeAttributesMap, nodeAttributeCollection);
		List<String> timeIntervalList = TemporalQueryUtility.getTimeIntervals();
		List<String> arithmeticOperaorsList = getArithmeticOperators();
		List<String> relationalOperatorsList = TemporalQueryUtility.getRelationalOperators();

		queryDataMap.put(Constants.ARITHMETIC_OPERATORS, arithmeticOperaorsList);
		queryDataMap.put(Constants.SECOND_NODE_ATTRIBUTES, nodeAttributesMap);
		queryDataMap.put(Constants.RELATIONAL_OPERATORS, relationalOperatorsList);
		queryDataMap.put(Constants.TIME_INTERVALS_LIST, timeIntervalList);
		queryDataMap.put(Constants.ENTITY_LABEL_LIST, entityLabelsList);
		return queryDataMap;
	}

	/**
	 * This method gets query data for source and destination expressions.
	 * @param sourceExpId source expression id.
	 * @param destExpId destination expression id.
	 * @param sourceNodeName source node name.
	 * @param destNodeName destination node name.
	 * @return query data map.
	 */
	public Map<String, Object> getQueryData(int sourceExpId, int destExpId, String sourceNodeName,
			String destNodeName)
	{
		Map<String, Object> queryDataMap = new HashMap<String, Object>();
		Map<String, List<String>> sourceNodeAttributesMap = new HashMap<String, List<String>>();
		Map<String, List<String>> destNodeAttributesMap = new HashMap<String, List<String>>();
		List<String> entityLabelsList = getEntityLabelsList(sourceNodeName, destNodeName);
		Collection<QueryableAttributeInterface> sourceAttributeCollection =
				getAttributeCollection(sourceExpId);
		populateMap(sourceNodeAttributesMap, sourceAttributeCollection);
		Collection<QueryableAttributeInterface> destAttributeCollection =
			getAttributeCollection(destExpId);
		List<String> timeIntervalList = TemporalQueryUtility.getTimeIntervals();
		populateMap(destNodeAttributesMap, destAttributeCollection);
		List<String> arithmeticOperaorsList = getArithmeticOperators();
		List<String> relationalOperatorsList = TemporalQueryUtility.getRelationalOperators();
		queryDataMap.put(Constants.FIRST_NODE_ATTRIBUTES, sourceNodeAttributesMap);
		queryDataMap.put(Constants.ARITHMETIC_OPERATORS, arithmeticOperaorsList);
		queryDataMap.put(Constants.SECOND_NODE_ATTRIBUTES, destNodeAttributesMap);
		queryDataMap.put(Constants.RELATIONAL_OPERATORS, relationalOperatorsList);
		queryDataMap.put("timeIntervals", timeIntervalList);
		queryDataMap.put("entityList", entityLabelsList);

		return queryDataMap;
	}

	/**
	 * This method gets display labels for nodes in DAG.
	 * @param srcNodeName source node
	 * @param destNodeName destination node name
	 * @return list of display labels.
	 */
	private List<String> getEntityLabelsList(String srcNodeName, String destNodeName)
	{
		List<String> entityList = new ArrayList<String>();

		String nodeName1 = Utility.getDisplayLabel(srcNodeName);
		String nodeName2 = Utility.getDisplayLabel(destNodeName);

		entityList.add(0, nodeName1);
		entityList.add(1, nodeName2);
		return entityList;
	}

	/**
	 * This method populates map with query data that is needed for validating
	 * nodes.
	 * @param destNodeAttributesMap attribute map for node
	 * @param destAttributeCollection attribute collection
	 */
	private void populateMap(Map<String, List<String>> destNodeAttributesMap,
			Collection<QueryableAttributeInterface> destAttributeCollection)
	{
		/**
		 * Storing all attributes of destination entity having DataType as Date
		 */
		for (QueryableAttributeInterface attribute : destAttributeCollection)
		{
			if (attribute.isTagPresent(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
				|| attribute.getQueryEntity().isTagPresent(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
				|| attribute.getQueryEntity().isTagPresent(Constants.TAG_HIDE_ATTRIBUTES))
			{
				continue;
			}
			processDataType(destNodeAttributesMap, attribute);
   		}
	}

	/**
	 * This method checks data type of attribute.
	 * @param destNodeAttributesMap attribute query data map.
	 * @param attribute queryable attribute.
	 */
	private void processDataType(Map<String, List<String>> destNodeAttributesMap,
			QueryableAttributeInterface attribute)
	{
		List<String> destNodeList;
		String destDataType = attribute.getDataType();
		String isRangeAttribute = Constants.FALSE;
		if(attribute.isTagPresent(Constants.TAG_QUANTITATIVE_ATTRIBUTE))
		{
			isRangeAttribute = Constants.TRUE;
		}
		if (destDataType.equals(Constants.DATE_TYPE))
		{
			destNodeList = new ArrayList<String>();
			//Putting attribute name and attribute data type in Map
			destNodeList.add(0, attribute.getId().toString());
			destNodeList.add(1, attribute.getDataType());
			destNodeList.add(2,isRangeAttribute);
			destNodeAttributesMap.put(attribute.getDisplayName(), destNodeList);
		}
		else if ((edu.wustl.query.util.global.Utility.isNumber(attribute.getDataType()))
					&& (!attribute.getName().equals(Constants.ID)))
		{
			destNodeList = new ArrayList<String>();
			destNodeList.add(0, attribute.getId().toString());
			destNodeList.add(1, Constants.INTEGER_TYPE);
			destNodeList.add(2,isRangeAttribute);
			destNodeAttributesMap.put(attribute.getDisplayName(), destNodeList);
		}
	}

	/**
	 * This method gets arithmetic operators.
	 * @return list of operators.
	 */
	private List<String> getArithmeticOperators()
	{
		List<String> arithmeticOperaorsList = new ArrayList<String>();
		/**
		 * Getting all arithmetic operators
		 */
		for (ArithmeticOperator operator : ArithmeticOperator.values())
		{
			if ((!operator.mathString().equals("")) && (!operator.mathString().equals("*"))
					&& (!operator.mathString().equals("/")))
			{
				arithmeticOperaorsList.add(operator.mathString());
			}
		}
		return arithmeticOperaorsList;
	}

	/**
	 * This method removes the Custom formula from query on delete of custom Node.
	 * @param customNodeId custom formula node id.
	 */
	public void removeCustomFormula(String customNodeId)
	{
		IQuery query = mQueryObject.getQuery();
		Map<String, CustomFormulaUIBean> tqUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute("TQUIMap");
		if (tqUIMap != null)
		{
			CustomFormulaUIBean customFormulaUIBean = tqUIMap.get(customNodeId);
			ICustomFormula customFormula = customFormulaUIBean.getCustomFormula();
			if (customFormula != null && !customFormulaUIBean.isCalculatedResult())
			{
				IConstraints constraints = query.getConstraints();
				deleteOutputTerm(customFormulaUIBean);
				for (IExpression expression2 : constraints)
				{
					expression2.removeOperand(customFormula);
				}
			}
			else
			{
				removeTerm(query, customFormulaUIBean);
			}
			tqUIMap.remove(customNodeId);
		}
	}
	/**
	 * This method removes uptput term.
	 * @param query query object
	 * @param customFormulaUIBean custom formula bean
	 */
	private void removeTerm(IQuery query, CustomFormulaUIBean customFormulaUIBean)
	{
		IOutputTerm termToDelete = customFormulaUIBean.getOutputTerm();
		if (termToDelete != null)
		{
			query.getOutputTerms().remove(termToDelete);
		}
	}

	/**
	 * Link two nodes in DAG.
	 * @param sourceNode souce node
	 * @param destNode destination node
	 * @param path path between nodes
	 */
	private void linkTwoNode(final DAGNode sourceNode, final DAGNode destNode, final IPath path)
	{
		try
		{
			int sourceexpressionId = sourceNode.getExpressionId();
			int destexpressionId = destNode.getExpressionId();
			List<Integer> intermediateExpressions = mQueryObject.addPath(sourceexpressionId,
					destexpressionId, path);
			PathLink link = new PathLink();
			link.setAssociationExpressions(intermediateExpressions);
			link.setDestinationExpressionId(destexpressionId);
			link.setSourceExpressionId(sourceexpressionId);
			link.setPath(path);
			updateQueryObject(link, sourceNode);
		}
		catch (CyclicException e)
		{
			logger.error(e.getMessage(), e);

		}
	}

	/**
	 * Updates query object.
	 * @param link path link
	 * @param sourceNode source node
	 */
	private void updateQueryObject(PathLink link, DAGNode sourceNode)
	{
		//TODO required to modify code logic will not work for multiple association
		int sourceexpressionId = sourceNode.getExpressionId();
		//IExpressionId destexpressionId = new ExpressionId(destNode
		//.getExpressionId());

		// If the first association is added, put operator between attribute condition and association
		String operator = null;
		// if (sourcePort == null) {
		operator = sourceNode.getOperator();
		//} else { // Get the logical operator associated with previous association
		//   operator = sourceNode.getLogicalOperator(sourcePort);
		// }

		// Get the expressionId between which to add logical operator
		int destId = link.getLogicalConnectorExpressionId();

		mQueryObject.setLogicalConnector(sourceexpressionId, destId,
				edu.wustl.cab2b.client.ui.query.Utility.getLogicalOperator(operator), false);

		// Put appropriate parenthesis
		// The code is required for multiple associations
		//if (sourcePort != null) {
		//IExpressionId previousExpId = link.getLogicalConnectorExpressionId();
		//mQueryObject.addParantheses(sourceexpressionId, previousExpId, destId);
		// }
	}

	/**
	 * Gets display path string.
	 * @param path between nodes
	 * @return display string for Path
	 */
	public static String getPathDisplayString(IPath path)
	{
		List<IAssociation> pathList = path.getIntermediateAssociations();
		String text = edu.wustl.cab2b.common.util.Utility.getOnlyEntityName(path
				.getSourceEntity());
		for (int i = 0; i < pathList.size(); i++)
		{
			text = text.concat(">>");
			IAssociation association = pathList.get(i);
			if (association instanceof IIntraModelAssociation)
			{
				IIntraModelAssociation iAssociation = (IIntraModelAssociation) association;
				AssociationInterface dynamicExtensionsAssociation = iAssociation
						.getDynamicExtensionsAssociation();
				String role = "(" + dynamicExtensionsAssociation.getTargetRole().getName() + ")";
				text = text.concat(role + ">>");
			}
			text = text.concat(edu.wustl.cab2b.common.util.Utility.getOnlyEntityName(association
					.getTargetEntity()));
		}
		logger.debug(text);
		return text;

	}

	/**
	 * Generates sql query.
	 * @return error code.
	 */
	public int search()
	{
		QueryModuleError status = QueryModuleError.SUCCESS;
		IQuery query = mQueryObject.getQuery();
		Long queryExecId = 0L;
		HttpServletRequest request = flex.messaging.FlexContext.getHttpRequest();
		boolean isRulePresentInDag = edu.wustl.query.util.global.Utility.checkIfRulePresentInDag(query);
		try
		{
			if (isRulePresentInDag)
			{
				request.getSession().removeAttribute(Constants.QUERY_EXEC_ID);
				AbstractQueryUIManager uiManager = AbstractQueryUIManagerFactory
				 .configureDefaultAbstractUIQueryManager(this.getClass(), request, query);
				queryExecId = uiManager.searchQuery();
				// no need to insert parameters when executed form dag
				//QUIManager.insertParametersForExecution(query_exec_id,queryClone);
				request.getSession().setAttribute("query_exec_id", queryExecId);

			}
			else
			{
				status = QueryModuleError.EMPTY_DAG;
			}
		}
		catch (QueryModuleException e)
		{
			status = e.getKey();
		}
//		catch (DAOException e)
//		{
//			QueryModuleException queryModExp = new QueryModuleException(e.getMessage(), e,
//					QueryModuleError.SQL_EXCEPTION);
//			status=queryModExp.getKey();
//		}
		return status.getErrorCode();

	}

	/**
	 *
	 * @param customNodeName node name
	 * @param keySet Set
	 * @return isContains
	 */
	private boolean isKeySetContainsNodeName(String customNodeName, Set keySet)
	{
		boolean isContains = false;
		Iterator keySetItr = keySet.iterator();
		while (keySetItr.hasNext())
		{
			String key = (String) keySetItr.next();
			if (customNodeName.equals(key))
			{
				isContains = true;
				break;
			}
		}
		return isContains;
	}

	/**
	 *
	 * @param nodeName node name
	 * @param tqUIMap map
	 * @return custom node name
	 */
	private String getCustomNodeName(String nodeName, Map<String, CustomFormulaUIBean> tqUIMap)
	{
		String customNodeName = " ";
		int customNodeNumber = 1;
		boolean isContains = false;
		Set<String> keySet = tqUIMap.keySet();

		if (keySet.isEmpty())
		{
			//This is the Initial case
			customNodeName = nodeName + "_" + customNodeNumber;
		}
		else
		{
			while (customNodeNumber <= keySet.size())
			{
				customNodeName = nodeName + "_" + customNodeNumber;
				isContains = isKeySetContainsNodeName(customNodeName, keySet);
				if (isContains)
				{
					customNodeNumber++;
				}
				else
				{
					break;
				}
			}
		}
		if (customNodeNumber == (keySet.size() + 1) && isContains)
		{
			//By this time, customNodeNumber already exceeds the length of the KeySet,
			//so new  customNodeName is
			customNodeName = nodeName + "_" + customNodeNumber;
		}
		return customNodeName;
	}

	/**
	 * Repaints DAG in edit mode.
	 * @return map
	 */
	public Map<String, Object> repaintDAG()
	{
		IQuery query = (IQuery) session.getAttribute(DAGConstant.QUERY_OBJECT);
		mQueryObject.setQuery(query);
		IConstraints constraints = query.getConstraints();
		positionMap = new QueryNodeLocator(400, query).getPositionMap();
		String isRepaint = (String) session.getAttribute(DAGConstant.ISREPAINT);
		if ((isRepaint == null) || (isRepaint.equals(DAGConstant.ISREPAINT_FALSE)))
		{
			session.setAttribute(DAGConstant.ISREPAINT, DAGConstant.ISREPAINT_TRUE);
		}
		Set<Integer> visibleExpression = new HashSet<Integer>();
		for (IExpression expression : constraints)
		{
			if (expression.isVisible())
			{
				visibleExpression.add(Integer.valueOf(expression.getExpressionId()));
			}
		}
		List<DAGNode> nodeList  = createNodeList(constraints, visibleExpression);
		Map<String, Object> nodeMap = repaintTQ(visibleExpression);
		nodeMap.put(DAGConstant.DAG_NODE_LIST, nodeList);
		return nodeMap;

	}

    /**
     * This method creates list of DAG nodes.
     * @param constraints in IQuery object.
     * @param visibleExpression expression ids.
     * @return list of DAG nodes.
     */
	private List<DAGNode> createNodeList(IConstraints constraints,
			Set<Integer> visibleExpression)
	{
		List<DAGNode> nodeList = new ArrayList<DAGNode>();
		for(Integer expressionId : visibleExpression)
		{
			IExpression exp = constraints.getExpression(expressionId.intValue());
			IQueryEntity constraintEntity = exp.getQueryEntity();
			String nodeDisplayName = QueryableObjectUtility.getQueryableObjectName(constraintEntity
					.getDynamicExtensionsEntity());
			DAGNode dagNode = new DAGNode();
			dagNode.setExpressionId(exp.getExpressionId());
			dagNode.setNodeName(nodeDisplayName);
			dagNode.setToolTip(exp);
			Position position = positionMap.get(exp.getExpressionId());
			setNodeType(exp,dagNode, position);
			nodeform(expressionId, dagNode, constraints, new ArrayList<IIntraModelAssociation>());
			int numOperands = exp.numberOfOperands();
			int numOperator = numOperands - 1;
			for (int i = 0; i < numOperator; i++)
			{
				String operator = exp.getConnector(i, i + 1).getOperator().toString();
				dagNode.setOperatorList(operator.toUpperCase());
			}
			nodeList.add(dagNode);
		}
		return nodeList;
	}

   /**
    * This method repaints TQ in edit mode.
    * @param visibleExpression expression ids.
    * @return node map
    */
	private Map<String, Object> repaintTQ(Set<Integer> visibleExpression)
	{
		List<CustomFormulaNode> customNodeList = new ArrayList<CustomFormulaNode>();
		List<SingleNodeCustomFormulaNode> snCustomNodeList =
			new ArrayList<SingleNodeCustomFormulaNode>();
		Map<String, CustomFormulaUIBean> tqUIMap = (Map<String, CustomFormulaUIBean>) session
				.getAttribute(DAGConstant.TQUIMap);
		if (tqUIMap == null)
		{
			tqUIMap = new HashMap<String, CustomFormulaUIBean>();
			repaintFromSavedQuery(customNodeList, snCustomNodeList,visibleExpression,
					tqUIMap);
		}
		else
		{
			repaintFromSessionQuery(customNodeList, snCustomNodeList,
					visibleExpression, tqUIMap);
		}
		Map<String, Object> nodeMap = new HashMap<String, Object>();
		nodeMap.put(DAGConstant.CUSTOM_FORMULA_NODE_LIST, customNodeList);
		nodeMap.put(DAGConstant.SINGLE_NODE_CF_NODE_LIST, snCustomNodeList);
		return nodeMap;
	}

	/**
	 * this method sets type of node.
	 * @param exp IExpression
	 * @param dagNode DAGNode
	 * @param position Position
	 */
	private void setNodeType(IExpression exp ,DAGNode dagNode, Position position)
	{
		if (position != null)
		{
			dagNode.setX(position.getX());
			dagNode.setY(position.getY());
		}

		//Commented out .....Baljeet
		/*if (!exp.containsRule())
		{
			dagNode.setNodeType(DAGConstant.VIEW_ONLY_NODE);
		}*/
		if (!exp.isInView())
		{
			dagNode.setNodeType(DAGConstant.CONSTRAINT_ONLY_NODE);
		}
	}

	/**
	 * This method retrieves the custom formulas from session query.
	 * @param customNodeList custom formula node list
	 * @param snCustomNodeList single node list
	 * @param visibleExpression visible expression ids
	 * @param tqUIMap TQ map
	 */
	private void repaintFromSessionQuery(List<CustomFormulaNode> customNodeList,
			List<SingleNodeCustomFormulaNode> snCustomNodeList, Set<Integer> visibleExpression,
			Map<String, CustomFormulaUIBean> tqUIMap)
	{
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		//This is the case of session query , So populate the lists fron Map
		for (Integer expressionId : visibleExpression)
		{
			IExpression exp = constraints.getExpression(expressionId.intValue());
			if (exp.containsCustomFormula())
			{
				Set<ICustomFormula> customFormulas = QueryUtility.getCustomFormulas(exp);
				if (!customFormulas.isEmpty())
				{
					for (ICustomFormula customFormula : customFormulas)
					{
					 repaintTQ(customNodeList, snCustomNodeList, tqUIMap, customFormula);
					}
				}
			}
		}
	}

	/**
	 * This method is invoked when DAG is loaded with a saved query.
	 * @param customNodeList list of custom nodes
	 * @param snCustomNodeList single node custom node list
	 * @param tqUIMap TQ map
	 * @param customFormula custom formula
	 */
	private void repaintTQ(List<CustomFormulaNode> customNodeList,
			List<SingleNodeCustomFormulaNode> snCustomNodeList,
			Map<String, CustomFormulaUIBean> tqUIMap, ICustomFormula customFormula)
	{
		Set keySet = tqUIMap.keySet();
		Iterator keySetItr = keySet.iterator();
		while (keySetItr.hasNext())
		{
			String key = (String) keySetItr.next();
			String[] ids = key.split("_");
			if (!ids[0].equals("c"))
			{
				//This is the case of TQ
				String id1 = ids[0];
				String id2 = ids[1];
				if (id1.equals(id2))
				{
					sessionQSingleNodeCNode(snCustomNodeList,tqUIMap, customFormula,
							key);
				}
				else
				{
					sessionQTwoNodesCNode(customNodeList,tqUIMap, customFormula, key);
				}
			}
		}
	}

	/**
	 * This method identifies only two nodes Custom formulas from a session Query.
	 * @param customNodeList list CustomFormulaNode
	 * @param tqUIMap TQ map
	 * @param customFormula ICustomFormula
	 * @param key TQ key
	 */
	private void sessionQTwoNodesCNode(List<CustomFormulaNode> customNodeList,
			Map<String, CustomFormulaUIBean> tqUIMap, ICustomFormula customFormula, String key)
	{
		//This is the case for Multiple Node TQ
		CustomFormulaUIBean beanObj = tqUIMap.get(key);
		CustomFormulaNode customNode = null;
		if ((!beanObj.isCalculatedResult()) && (beanObj.getCustomFormula().equals(customFormula)))
		{
			customNode = beanObj.getTwoNode();
			if (customNode != null)
			{
				addInfoToTNCNode(key, customNode);
			}
		}
		if (customNode != null)
		{
			removeFromTwoNodesList(customNodeList, customNode.getName());
			customNodeList.add(customNode);
		}

	}

	/**
	 * This method removes node from custom nodes list.
	 * @param customNodeList list of custom nodes
	 * @param customNodeName custom node name
	 */
	private void removeFromTwoNodesList(List<CustomFormulaNode> customNodeList,
			String customNodeName)
	{
		for (int i = 0; i < customNodeList.size(); i++)
		{
			CustomFormulaNode cNode = customNodeList.get(i);
			if (cNode.getName().equals(customNodeName))
			{
				customNodeList.remove(i);
				break;
			}
		}
	}

	/**
	 * This method adds info for two node custom node.
	 * @param key string
	 * @param customNode CustomFormulaNode
	 */
	private void addInfoToTNCNode(String key, CustomFormulaNode customNode)
	{
		IQuery query = mQueryObject.getQuery();
		String customColumnName;
		customNode.setName(key);
		customColumnName = setCustomColumnName(query);
		customNode.setCustomColumnName(customColumnName);
		customNode.setOperation(DAGConstant.REPAINT_OPERATION);
	}

	/**
	 *
	 * @param snCustomNodeList list of custom nodes
	 * @param tqUIMap map of TQ
	 * @param customFormula ICustomFormula
	 * @param key String
	 */
	private void sessionQSingleNodeCNode(List<SingleNodeCustomFormulaNode> snCustomNodeList,
			Map<String, CustomFormulaUIBean> tqUIMap, ICustomFormula customFormula, String key)
	{
		IQuery query = mQueryObject.getQuery();
		//This is the case for Single node TQ
		SingleNodeCustomFormulaNode singleNodeCF = null;
		CustomFormulaUIBean beanObj = tqUIMap.get(key);

		//when node is not a calculated result node and custom fromulas are equal
		if ((!beanObj.isCalculatedResult()) && beanObj.getCustomFormula().equals(customFormula))
		{
			singleNodeCF = beanObj.getSingleNode();
			if (singleNodeCF != null)
			{
				addInfoToSNCNode(query, key, singleNodeCF);
			}
		}
		if (singleNodeCF != null)
		{
			removeFromSingleNode(snCustomNodeList, singleNodeCF.getName());
			snCustomNodeList.add(singleNodeCF);
		}

	}

	/**
	 * This method removes a single node custom formula from list
	 * containing single node custom formula list.
	 * @param snCustomNodeList list of custom nodes.
	 * @param cfNodeName node name
	 */
	private void removeFromSingleNode(List<SingleNodeCustomFormulaNode> snCustomNodeList,
			String cfNodeName)
	{
		for (int i = 0; i < snCustomNodeList.size(); i++)
		{
			SingleNodeCustomFormulaNode node = snCustomNodeList.get(i);
			if (node.getName().equals(cfNodeName))
			{
				snCustomNodeList.remove(i);
				break;
			}
		}
	}

	/**
	 * Adds info for single node custom node.
	 * @param query IQuery
	 * @param key string
	 * @param singleNodeCF single node custom formula
	 */
	private void addInfoToSNCNode(IQuery query, String key,
				SingleNodeCustomFormulaNode singleNodeCF)
	{
		String customColumnName;
		singleNodeCF.setName(key);
		customColumnName = setCustomColumnName(query);
		singleNodeCF.setCustomColumnName(customColumnName);
		singleNodeCF.setOperation(DAGConstant.REPAINT_OPERATION);
	}

	/**
	 * Repaints DAG from saved query.
	 * @param customNodeList list of custom nodes.
	 * @param snCustomNodeList single node custom list
	 * @param visibleExpression visible expression ids.
	 * @param tqUIMap TQ map
	 */
	private void repaintFromSavedQuery(List<CustomFormulaNode> customNodeList,
			List<SingleNodeCustomFormulaNode> snCustomNodeList,Set<Integer> visibleExpression,
			Map<String, CustomFormulaUIBean> tqUIMap)
	{
		IQuery query = mQueryObject.getQuery();
		IConstraints constraints = query.getConstraints();
		//Then this is the case of saved Query, so populate the map with Saved Query
		for (Integer expressionId : visibleExpression)
		{
			IExpression exp = constraints.getExpression(expressionId.intValue());
			if (exp.containsCustomFormula())
			{
				checkingCustomFormulas(customNodeList, snCustomNodeList, tqUIMap, exp);
			}
		}
		session.setAttribute(DAGConstant.TQUIMap, tqUIMap);
	}

	/**
	 * This method identifies all custom formulas in Query.
	 * @param customNodeList list of custom formula nodes
	 * @param snCustomNodeList single node custom node list
	 * @param tqUIMap TQ map
	 * @param exp expression in query object
	 */
	private void checkingCustomFormulas(List<CustomFormulaNode> customNodeList,
			List<SingleNodeCustomFormulaNode> snCustomNodeList,
			Map<String, CustomFormulaUIBean> tqUIMap, IExpression exp)
	{
		Set<ICustomFormula> customFormulas = QueryUtility.getCustomFormulas(exp);
		if (!customFormulas.isEmpty())
		{
			for (ICustomFormula customFormula : customFormulas)
			{
				Set<IExpression> expressionSet = QueryUtility
						.getExpressionsInFormula(customFormula);
				if ((!expressionSet.isEmpty()) && (expressionSet.size() == 2))
				{
					CustomFormulaNode customNode = populateCustomNodeInfo(customFormula, exp);
					if (customNode != null)
					{
					 savedQTwoNodesCNode(customNodeList,tqUIMap, customFormula, customNode);
					}
				}
				else if ((!expressionSet.isEmpty()) && (expressionSet.size() == 1))
				{
					savedQSingleNodeCNode(snCustomNodeList, tqUIMap, exp, customFormula);
				}
			}
		}
	}

	/**
	 * This method retrieves all single node custom formulas from query.
	 * @param snCustomNodeList list of single node custom formula nodes.
	 * @param tqUIMap TQ map.
	 * @param exp expression in query
	 * @param customFormula custom formula in query.
	 */
	private void savedQSingleNodeCNode(List<SingleNodeCustomFormulaNode> snCustomNodeList,
			Map<String, CustomFormulaUIBean> tqUIMap, IExpression exp,
			ICustomFormula customFormula)
	{
		IQuery query = mQueryObject.getQuery();
		SingleNodeCustomFormulaNode singleNodeCF = populateSingleNodeInfo(customFormula, exp);
		String singleNodeName = getCustomNodeName(singleNodeCF.getName(), tqUIMap);
		singleNodeCF.setName(singleNodeName);
		String customColumnName = setCustomColumnName(query);
		singleNodeCF.setCustomColumnName(customColumnName);
		singleNodeCF.setOperation(DAGConstant.REPAINT_OPERATION);

		//Node Limit is Set to "Add Limit" as it's a custom formula node
		//and not the calculated result node
		singleNodeCF.setNodeView(DAGConstant.ADD_LIMIT_VIEW);
		snCustomNodeList.add(singleNodeCF);

		//Setting the node In the Map
		CustomFormulaUIBean bean = createTQUIBean(customFormula, null, singleNodeCF, null);
		tqUIMap.put(singleNodeName, bean);
	}

	/**
	 * This method retrieves all two nodes custom formulas from query.
	 * @param customNodeList list of custom nodes
	 * @param tqUIMap TQ map
	 * @param customFormual custom formula
	 * @param customNode custom formula node
	 */
	private void savedQTwoNodesCNode(List<CustomFormulaNode> customNodeList,
			Map<String, CustomFormulaUIBean> tqUIMap, ICustomFormula customFormual,
			CustomFormulaNode customNode)
	{
		 IQuery query = mQueryObject.getQuery();
		//Setting the custom Column Name
		String customColumnName = setCustomColumnName(query);
		String name = getCustomNodeName(customNode.getName(), tqUIMap);
		customNode.setName(name);
		customNode.setCustomColumnName(customColumnName);
		customNode.setOperation(DAGConstant.REPAINT_OPERATION);

		//Node Limit is Set to "Add Limit" as it's a custom formula node and not the calculated result node
		customNode.setNodeView(DAGConstant.ADD_LIMIT_VIEW);
		customNodeList.add(customNode);

		//Setting the node In the Map
		CustomFormulaUIBean bean = createTQUIBean(customFormual, customNode, null, null);
		tqUIMap.put(name, bean);
	}

	/**
	 * This method populated info related to two nodes custom formula.
	 * @param customFormula custom formula
	 * @param exp expression in query
	 * @return SingleNodeCustomFormulaNode
	 */
	private SingleNodeCustomFormulaNode populateSingleNodeInfo(ICustomFormula customFormula,
			IExpression exp)
	{
		// TODO Auto-generated method stub
		SingleNodeCustomFormulaNode singleCNode = new SingleNodeCustomFormulaNode();
		//See how the name set is done
		singleCNode.setName(exp.getExpressionId() + "_" + exp.getExpressionId());
		singleCNode.setNodeExpressionId(exp.getExpressionId());
		//Setting the Entity Name
		String entityName = Utility.getDisplayLabel(exp.getQueryEntity().
				getDynamicExtensionsEntity().getName());
		singleCNode.setEntityName(entityName);
		//Seting the Arithmetic and Relational Operator
		ITerm lhs = customFormula.getLhs();
		IConnector<ArithmeticOperator> connector = lhs.getConnector(0, 1);
		singleCNode.setSelectedArithmeticOp(connector.getOperator().mathString());
		RelationalOperator relOperator = customFormula.getOperator();
		singleCNode.setSelectedLogicalOp(relOperator.getStringRepresentation());

		//Getting LHS Date Picker time Value
		/*IArithmeticOperand  lhsOpersand =  lhs.getOperand(0);
		if(lhsOpersand instanceof IDateLiteral)
		{
			IDateLiteral dateLit = (IDateLiteral)lhsOpersand;
			singleCNode.setLhsTimeValue(getDateInGivenFormat(dateLit.getDate()));
			singleCNode.setLhsTimeInterval(DAGConstant.NULL_STRING);
		}*/
		//Handling RHS part
		List<ITerm> allRhs = customFormula.getAllRhs();
		if (!allRhs.isEmpty())
		{
			setDateOperand(singleCNode, allRhs);
		}

		//Handling LHS
		for (IArithmeticOperand element : lhs)
		{
			setArithmeticOperand(singleCNode, element);
		}
		return singleCNode;
	}

	/**
	 * This method sets arithmetic operand.
	 * @param singleCNode single custom node
	 * @param element arithmetic operand
	 */
	private void setArithmeticOperand(SingleNodeCustomFormulaNode singleCNode,
			IArithmeticOperand element)
	{
		if (element instanceof DateLiteral)
		{
			IDateLiteral dateLit = (IDateLiteral) element;
			singleCNode.setLhsTimeValue(getDateInGivenFormat(dateLit.getDate()));
			singleCNode.setLhsTimeInterval(DAGConstant.NULL_STRING);

		}
		else if (element instanceof DateOffsetAttribute)
		{
			//This is the case of offset Attribute,
			//means having datePicker as left operand and dateoffset attribute as 2nd operand
			IDateOffsetAttribute dateOffSetAttr = (IDateOffsetAttribute) element;
			QueryableAttributeInterface attribute = dateOffSetAttr.getAttribute();
			singleCNode.setAttributeID(attribute.getId().toString());
			singleCNode.setAttributeName(attribute.getName());
			singleCNode.setAttributeType(attribute.getDataType());
			singleCNode.setQAttrInterval(dateOffSetAttr.getTimeInterval().name() + "s");
		}
		else if (element instanceof ExpressionAttribute)
		{
			IExpressionAttribute expAttr = (IExpressionAttribute) element;
			QueryableAttributeInterface attribute = expAttr.getAttribute();

			singleCNode.setAttributeID(attribute.getId().toString());
			singleCNode.setAttributeName(attribute.getName());
			singleCNode.setAttributeType(attribute.getDataType());
			singleCNode.setQAttrInterval(DAGConstant.NULL_STRING);
		}
		//			else
		//			{
		//				throw new RuntimeException("Should not occur.....");
		//			}
	}

	/**
	 * This method sets date operand.
	 * @param singleCNode SingleNodeCustomFormulaNode
	 * @param allRhs list of terms
	 */
	private void setDateOperand(SingleNodeCustomFormulaNode singleCNode, List<ITerm> allRhs)
	{
		ITerm term = allRhs.get(0);
		IArithmeticOperand operand = term.getOperand(0);
		if(allRhs.size() == 1)
		{
			setCustomFormulaFirstOperand(singleCNode, operand);
		}
		else
		{
			//then this is the case of "Between" operator having two RHSs
			DateOffsetLiteral dateOffSetLit = null;
			//Get the first rhs
			if(operand instanceof DateOffsetLiteral)
			{
				dateOffSetLit = (DateOffsetLiteral) operand;
				singleCNode.setTimeValue1(dateOffSetLit.getOffset());
				singleCNode.setTimeInterval1(dateOffSetLit.getTimeInterval().toString() + "s");
			}
			term = allRhs.get(1);
			operand = term.getOperand(0);
			if(operand instanceof DateOffsetLiteral)
			{
				dateOffSetLit = (DateOffsetLiteral) operand;
				singleCNode.setTimeValue2(dateOffSetLit.getOffset());
				singleCNode.setTimeInterval2(dateOffSetLit.getTimeInterval().toString() + "s");
			}
		}
	}

	/**
	 * This method sets custom formula for first operand.
	 * @param singleCNode SingleNodeCustomFormulaNode
	 * @param operand IArithmeticOperand
	 */
	private void setCustomFormulaFirstOperand(SingleNodeCustomFormulaNode singleCNode,
			IArithmeticOperand operand)
	{

		if (operand instanceof DateOffsetLiteral)
		{
			DateOffsetLiteral dateOffSetLit = (DateOffsetLiteral) operand;
			singleCNode.setTimeValue1(dateOffSetLit.getOffset());
			singleCNode.setTimeInterval1(dateOffSetLit.getTimeInterval().toString() + "s");
		}
		else if (operand instanceof DateLiteral)
		{
			IDateLiteral dateLit = (DateLiteral) operand;

			if (dateLit.getDate() == null)
			{
				singleCNode.setTimeValue1("");
			}
			else
			{
				singleCNode.setTimeValue1(getDateInGivenFormat(dateLit.getDate()));
			}
		}
	}

	/**
	 * This method sets column name.
	 * @param query IQuery
	 * @return column name
	 */
	private String setCustomColumnName(IQuery query)
	{
		List<IOutputTerm> outputTermList = query.getOutputTerms();
		IOutputTerm outputTerm = outputTermList.get(0);

		String columnName = outputTerm.getName();
		//As custom column name consists of column Name , ( and Time Interval ),
		//so we need to parse it to get the exact column name
		int index = columnName.lastIndexOf('(');
		return columnName.substring(0, index);

	}

	/**
	 * This method populates custom node.
	 * @param customFormula custom formula
	 * @param srcExp expression
	 * @return custom formula node.
	 */
	private CustomFormulaNode populateCustomNodeInfo(ICustomFormula customFormula,
			IExpression srcExp)
	{
		CustomFormulaNode cNode = new CustomFormulaNode();
		StringBuilder customNodeId = getCustomNodeId(srcExp);
		cNode.setFirstNodeExpId(srcExp.getExpressionId());
		ITerm lhs = customFormula.getLhs();
		IConnector<ArithmeticOperator> connector = lhs.getConnector(0, 1);
		cNode.setSelectedArithmeticOp(connector.getOperator().mathString());
		RelationalOperator relOperator = customFormula.getOperator();
		cNode.setSelectedLogicalOp(relOperator.getStringRepresentation());
		setRHStoCustomNode(customFormula, cNode);
		for (IArithmeticOperand element : customFormula.getLhs())
		{
			setDateExpressionAttribute(srcExp, cNode, element);
		}
		String entityName = Utility.getDisplayLabel(srcExp.getQueryEntity()
				.getDynamicExtensionsEntity().getName());
		cNode.setFirstNodeName(entityName);
		Set<IExpression> containingExpressions = QueryUtility.getExpressionsInFormula(customFormula);
		for (IExpression exp : containingExpressions)
		{
			if (!exp.equals(srcExp))
			{
				cNode.setSecondNodeExpId(exp.getExpressionId());
				customNodeId.append(exp.getExpressionId());
				entityName = Utility.getDisplayLabel(exp.getQueryEntity()
						.getDynamicExtensionsEntity().getName());
				cNode.setSecondNodeName(entityName);
			}
		}
		cNode.setName(customNodeId.toString());
		return cNode;
	}
	/**
	 * This method sets rhs to custom node.
	 * @param customFormula custom formula in TQ
	 * @param cNode custom formula node to be modified.
	 */
	private void setRHStoCustomNode(ICustomFormula customFormula, CustomFormulaNode cNode)
	{
		List<ITerm> allRhs = customFormula.getAllRhs();
		if (!allRhs.isEmpty())
		{
			if(allRhs.size() == 1)
			{
				setInfoForNonBetweenOp(cNode, allRhs);
			}
			else
			{
				setInfoForBetweenOp(cNode, allRhs);
			}
		}
	}

	/**
	 * This method gets custom node id.
	 * @param srcExp expression
	 * @return custom ndoe id.
	 */
	private StringBuilder getCustomNodeId(IExpression srcExp)
	{
		StringBuilder customNodeId = new StringBuilder();
		customNodeId.append(srcExp.getExpressionId());
		customNodeId.append('_');
		return customNodeId;
	}

	/**
	 * This method sets info for between operator.
	 * @param cNode Custom Formula Node to be updated
	 * @param allRhs List of all RHSs
	 */
	private void setInfoForBetweenOp(CustomFormulaNode cNode, List<ITerm> allRhs)
	{
		//This is the case of "Between" operator , having two RHHs
		//And both the operands should be of type DateOffsetLiteral
		//so no need to check for operand of type DateLiteral
		//Getting first operand
		ITerm  term = allRhs.get(0);
		if(term != null)
		{
			setValuesForFirstRHSOperand(cNode, term);
		}
		//Getting second operand
		term = allRhs.get(1);
		if(term != null)
		{
			setValuesForSecondRHSOperand(cNode, term);
		}
	}

	/**
	 * Sets value fo second RHS operand.
	 * @param cNode Custom Formula Node to be updated
	 * @param term corresponding RHS term
	 */
	private void setValuesForSecondRHSOperand(CustomFormulaNode cNode,
			ITerm term)
	{
		IArithmeticOperand operand = term.getOperand(0);
		if(operand != null)
		{
			if (operand instanceof DateOffsetLiteral)
			{
				DateOffsetLiteral dateOffSetLit = (DateOffsetLiteral) operand;
				cNode.setTimeValue2(dateOffSetLit.getOffset());
				cNode.setTimeInterval2(dateOffSetLit.getTimeInterval().toString() + "s");
			}
			else if (operand instanceof DateLiteral)
			{
				//This is the case of Both Date Literals
				setDateLiteralToCustomNode(cNode, operand);
			}
			else
			{
				//This is the case of INT Literal
				INumericLiteral intLiteral = (NumericLiteral)operand;
				cNode.setTimeValue2(intLiteral.getNumber());
				cNode.setTimeInterval2(DAGConstant.NULL_STRING);
			}
		}
	}

	/**
	 * Sets date literal to custom node.
	 * @param cNode CustomFormulaNode
	 * @param operand IArithmeticOperand
	 */
	private void setDateLiteralToCustomNode(CustomFormulaNode cNode, IArithmeticOperand operand)
	{
		DateLiteral dateLit = (DateLiteral) operand;
		if (dateLit.getDate() == null)
		{
			cNode.setTimeValue2("");
			cNode.setTimeInterval2(DAGConstant.NULL_STRING);
		}
		else
		{
			cNode.setTimeValue2(getDateInGivenFormat(dateLit.getDate()));
			cNode.setTimeInterval2(DAGConstant.NULL_STRING);
		}
	}

	/**
	 * Sets value for first RHS operand.
	 * @param cNode Custom Formula Node to be updated
	 * @param term  term corresponding RHS term
	 */
	private void setValuesForFirstRHSOperand(CustomFormulaNode cNode, ITerm term)
	{
		IArithmeticOperand operand = term.getOperand(0);
		if(operand!=null)
		{
			if (operand instanceof DateOffsetLiteral)
			{
				DateOffsetLiteral dateOffSetLit = (DateOffsetLiteral) operand;
				cNode.setTimeValue1(dateOffSetLit.getOffset());
				cNode.setTimeInterval1(dateOffSetLit.getTimeInterval().toString() + "s");
			}
			else if (operand instanceof DateLiteral)
			{
				//This is the case of Both Date Literals
				setDateLiteralFirstOperand(cNode, operand);
			}
			else
			{
				//This is the case of INT Literal
				INumericLiteral intLiteral = (NumericLiteral)operand;
				cNode.setTimeValue1(intLiteral.getNumber());
				cNode.setTimeInterval1(DAGConstant.NULL_STRING);
			}
		}
	}

	/**
	 * Sets date literal for first operand.
	 * @param cNode CustomFormulaNode
	 * @param operand IArithmeticOperand
	 */
	private void setDateLiteralFirstOperand(CustomFormulaNode cNode, IArithmeticOperand operand)
	{
		DateLiteral dateLit;
		dateLit = (DateLiteral) operand;
		if (dateLit.getDate() == null)
		{
			cNode.setTimeValue1("");
			cNode.setTimeInterval1(DAGConstant.NULL_STRING);
		}
		else
		{
			cNode.setTimeValue1(getDateInGivenFormat(dateLit.getDate()));
			cNode.setTimeInterval1(DAGConstant.NULL_STRING);
		}
	}

	/**
	 * Sets info for non-Between operators.
	 * @param cNode Custom Formula Node to be updated
	 * @param allRhs List of all RHSs
	 */
	private void setInfoForNonBetweenOp(CustomFormulaNode cNode,
			List<ITerm> allRhs)
	{
		ITerm term = allRhs.get(0);
		IArithmeticOperand operand = term.getOperand(0);
		if (operand instanceof DateOffsetLiteral)
		{
			DateOffsetLiteral dateOffSetLit = (DateOffsetLiteral) operand;
			cNode.setTimeValue1(dateOffSetLit.getOffset());
			cNode.setTimeInterval1(dateOffSetLit.getTimeInterval().toString() + "s");
		}
		else if (operand instanceof DateLiteral)
		{
			DateLiteral dateLit = (DateLiteral) operand;
			if (dateLit.getDate() == null)
			{
				cNode.setTimeValue1("");
			}
			else
			{
				cNode.setTimeValue1(getDateInGivenFormat(dateLit.getDate()));
				cNode.setTimeInterval1(DAGConstant.NULL_STRING);
			}
		}
		else
		{
			NumericLiteral literal = (NumericLiteral)operand;
			cNode.setTimeValue1(literal.getNumber());
			cNode.setTimeInterval1(DAGConstant.NULL_STRING);
		}
	}


	/**
	 * Sets date expression attribute.
	 * @param srcExp Source Expression
	 * @param cNode Custom Formula Node
	 * @param element Arithmetic Operand
	 */
	private void setDateExpressionAttribute(IExpression srcExp, CustomFormulaNode cNode,
			IArithmeticOperand element)
	{
		if (element instanceof DateOffsetAttribute)
		{
			setDateOffset(srcExp, cNode, element);
		}
		else if (element instanceof ExpressionAttribute)
		{
			setExpressionAttribute(srcExp, cNode, element);
		}
	}

	/**
	 * Sets expression attribute to custom node.
	 * @param srcExp source expression
	 * @param cNode custom node
	 * @param element arithmetic operand
	 */
	private void setExpressionAttribute(IExpression srcExp, CustomFormulaNode cNode,
			IArithmeticOperand element)
	{
		ExpressionAttribute expAttr = (ExpressionAttribute) element;
		QueryableAttributeInterface attribute = expAttr.getAttribute();
		String dataType = getAttributeDataType(attribute);

		if (expAttr.getExpression().getExpressionId() == srcExp.getExpressionId())
		{
			cNode.setFirstSelectedAttrId(attribute.getId().toString());
			cNode.setFirstSelectedAttrName(attribute.getDisplayName());
			cNode.setFirstSelectedAttrType(dataType);
			cNode.setQAttrInterval1(DAGConstant.NULL_STRING);
		}
		else
		{
			cNode.setSecondSelectedAttrId(attribute.getId().toString());
			cNode.setSecondSelectedAttrName(attribute.getDisplayName());
			cNode.setSecondSelectedAttrType(dataType);
			cNode.setQAttrInterval2(DAGConstant.NULL_STRING);
		}
	}

	/**
	 * Sets date offset.
	 * @param srcExp expression
	 * @param cNode custom formula node
	 * @param element arithmetic operand
	 */
	private void setDateOffset(IExpression srcExp, CustomFormulaNode cNode,
			IArithmeticOperand element)
	{
		DateOffsetAttribute dateOffSetAttr = (DateOffsetAttribute) element;
		QueryableAttributeInterface attribute = dateOffSetAttr.getAttribute();
		String dataType = getAttributeDataType(attribute);
		if (dateOffSetAttr.getExpression().getExpressionId() == srcExp.getExpressionId())
		{
			cNode.setFirstSelectedAttrId(attribute.getId().toString());
			cNode.setFirstSelectedAttrName(attribute.getName());
			cNode.setFirstSelectedAttrType(dataType);
			cNode.setQAttrInterval1(dateOffSetAttr.getTimeInterval().name() + "s");
		}
		else
		{
			cNode.setSecondSelectedAttrId(attribute.getId().toString());
			cNode.setSecondSelectedAttrName(attribute.getName());
			cNode.setSecondSelectedAttrType(dataType);
			cNode.setQAttrInterval2(dateOffSetAttr.getTimeInterval().name() + "s");
		}
	}

	/**
	 * Gets date in MM/dd/yyyy format.
	 * @param date Date
	 * @return Required Date Pattern
	 */
	private String getDateInGivenFormat(java.sql.Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.format(date);
	}

	/**
	 * Gets attribute data type.
	 * @param attribute Attribute
	 * @return Returns data type of attribute
	 */
	private String getAttributeDataType(QueryableAttributeInterface attribute)
	{
		String dataType = attribute.getDataType();
		if (dataType.equals(Constants.DATE_TYPE))
		{
			dataType = Constants.DATE_TYPE;
		}
		else
		{
			if (edu.wustl.query.util.global.Utility.isNumber(dataType)
					&& (!attribute.getName().equals(Constants.ID)))
			{
				dataType = Constants.INTEGER_TYPE;
			}
		}
		return dataType;
	}

	/**
	 *
	 * @param expressionId expression id
	 * @param node dag node
	 * @param constraints in query
	 * @param intraModelAssociationList intra model association list
	 */
	private void nodeform(int expressionId, DAGNode node, IConstraints constraints,
			List<IIntraModelAssociation> intraModelAssociationList)
	{
		IJoinGraph graph = constraints.getJoinGraph();
		IExpression expression = constraints.getExpression(expressionId);
		List<IExpression> childList = graph.getChildrenList(expression);
		for (IExpression exp : childList)
		{
			/*	Code to get IPath Object*/
			IIntraModelAssociation association = (IIntraModelAssociation) (graph.getAssociation(
					expression, exp));
			//Added By Baljeet.....
			intraModelAssociationList.add(association);
			if (exp.isVisible())
			{
				IPath pathObj = mPathFinder.getPathForAssociations(intraModelAssociationList);
				long pathId = pathObj.getPathId();
				DAGNode dagNode = new DAGNode();
				dagNode.setExpressionId(exp.getExpressionId());
				dagNode.setNodeName(QueryableObjectUtility.getQueryableObjectName(exp
						.getQueryEntity().getDynamicExtensionsEntity()));
				dagNode.setToolTip(exp);
				/*	Adding Dag Path in each visible list which have childrens*/
				String pathStr = Long.valueOf(pathId).toString();
				DAGPath dagPath = new DAGPath();
				dagPath.setToolTip(getPathDisplayString(pathObj));
				dagPath.setId(pathStr);
				dagPath.setSourceExpId(node.getExpressionId());
				dagPath.setDestinationExpId(dagNode.getExpressionId());
				String key = pathStr + "_" + node.getExpressionId() + "_"
						+ dagNode.getExpressionId();
				mPathMap.put(key, pathObj);
				node.setDagpathList(dagPath);
				node.setAssociationList(dagNode);
				intraModelAssociationList.clear();
			}
			//Commented out .......Baljeet
			else
			{
				nodeform(exp.getExpressionId(), node, constraints, intraModelAssociationList);
			}
		}
	}

	/**
	 * Updates logical operator.
	 * @param parentExpId expression id
	 * @param parentIndex index
	 * @param operator logical operator
	 */

	public void updateLogicalOperator(int parentExpId, int parentIndex, String operator)
	{
		int parentExpressionId = parentExpId;
		IQuery query = mQueryObject.getQuery();
		IExpression parentExpression = query.getConstraints().getExpression(parentExpressionId);
		LogicalOperator logicOperator = edu.wustl.cab2b.client.ui.query.Utility
				.getLogicalOperator(operator);
		int childIndex = parentIndex + 1;
		parentExpression.setConnector(parentIndex, childIndex, QueryObjectFactory
				.createLogicalConnector(logicOperator));
		mQueryObject.setQuery(query);

	}

	/**
	 * This method edits the limits when edited from DAG.
	 * @param expId expression id.
	 * @param pageOf page of
	 * @return map
	 * @throws PVManagerException pv exception
	 */
	public Map editAddLimitUI(int expId,String pageOf) throws PVManagerException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int expressionId = expId;
		IExpression expression = mQueryObject.getQuery().getConstraints().getExpression(
				expressionId);

		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

		//added by amit_doshi to reset the edited entity in session for VI pop up
		session.setAttribute(Constants.ENTITY_NAME, entity.getId());

		List<ICondition> conditions = new ArrayList<ICondition>();
		if (expression.numberOfOperands() > 0)
		{
			Rule rule = ((Rule) (expression.getOperand(0)));
			conditions = Collections.list(rule);
		}
		HtmlProvider generateHTMLBizLogic = new HtmlProvider(null);
		String html = generateHTMLBizLogic.generateHTML(entity, conditions, pageOf);
		GenerateHTMLDetails generateHTMLDetails = generateHTMLBizLogic.getGenerateHTMLDetails();
		session.setAttribute(Constants.ENUMRATED_ATTRIBUTE,
				generateHTMLDetails.getEnumratedAttributeMap());
		map.put(DAGConstant.HTML_STR, html);
		map.put(DAGConstant.EXPRESSION, expression);
		return map;
	}

	/**
	 * Adds node to output view.
	 * @param entityIdString entity ids
	 * @return dag node
	 */
	public DAGNode addNodeToOutPutView(String entityIdString)
	{
		DAGNode node = null;
		if (!entityIdString.equalsIgnoreCase(""))
		{
			Long entityId = Long.parseLong(entityIdString);
			QueryableObjectInterface entity = QueryableObjectUtility
			.createQueryableObject(EntityCache.getCache().getEntityById(entityId));
			int expressionId = ((ConstraintsObjectBuilder) mQueryObject).addExpression(entity);
			node = createNode(expressionId, true);
		}
		return node;
	}


	/**
	 * This method gets query object from session and sets to query builder.
	 */
	public void restoreQueryObject()
	{
		HttpServletRequest request = flex.messaging.FlexContext.getHttpRequest();
		session = request.getSession();
		IQuery query = (IQuery) session.getAttribute(DAGConstant.QUERY_OBJECT);
		mQueryObject.setQuery(query);
	}

	/**
	 * Checks if node deletable.
	 * @param expId expression id.
	 * @return message
	 */
	public String isDeletableNode(int expId)
	{
	    String message="";
	    Boolean isAttrpresent = isAttributePresent(expId);
		if(isAttrpresent && "".equals(message))
		{
		   message = Constants.REMOVE_SELECTED_ATTRIBUTES;
		}
		return message;
	}

	/**
	 * Deletes expression from query.
	 * @param expId expression id.
	 */
	public void deleteExpression(int expId)
	{
		  int expressionId = expId;
		   mQueryObject.removeExpression(expressionId);
	}

	/**
	 * This Method removes the output Attributes associated with given expression Id to be remove.
	 * @param expId expression id.
	 * @return isPresent
	 */
	private Boolean isAttributePresent(int expId)
	{
		IParameterizedQuery query = (IParameterizedQuery)(mQueryObject.getQuery());
		boolean isPresent = false;
		 List<IOutputAttribute> outputAttributeList = query.getOutputAttributeList();
		// List <IOutputAttribute> newoutputAttributeList = new ArrayList<IOutputAttribute>();
		 Iterator<IOutputAttribute> iterator = outputAttributeList.iterator();
		 int atrrExp;
		 while(iterator.hasNext())
		  {
			  OutputAttribute attribute= (OutputAttribute)iterator.next();
			  atrrExp= attribute.getExpression().getExpressionId();
			   if(atrrExp ==expId)
			   {
				   isPresent= true;
				   break;
				   //newoutputAttributeList.add(attribute);
			   }
		  }
		return isPresent;
	}

	/**
	 * Adds expression to view.
	 * @param expId expression id.
	 */
	public void addExpressionToView(int expId)
	{
		int expressionId = expId;
		Expression expression = (Expression) mQueryObject.getQuery().getConstraints()
				.getExpression(expressionId);
		expression.setInView(true);
	}

	/**
	 * delete expression from view.
	 * @param expId expression id
	 */
	public void deleteExpressionFormView(int expId)
	{
		int expressionId = expId;
		Expression expression = (Expression) mQueryObject.getQuery().getConstraints()
				.getExpression(expressionId);
		expression.setInView(false);
	}

	/**
	 * This method deletes path between two nodes.
	 * @param pathName name of path
	 * @param linkedNodeList list of dag node
	 */
	public void deletePath(String pathName, List<DAGNode> linkedNodeList)
	{
		IPath path = mPathMap.remove(pathName);
		int sourceexpressionId = linkedNodeList.get(0).getExpressionId();
		int destexpressionId = linkedNodeList.get(1).getExpressionId();
		List<IAssociation> associations = path.getIntermediateAssociations();
		IConstraints constraints = mQueryObject.getQuery().getConstraints();
		JoinGraph graph = (JoinGraph) constraints.getJoinGraph();
		IExpression srcExpression = constraints.getExpression(sourceexpressionId);
		IExpression destExpression = constraints.getExpression(destexpressionId);
		List<IExpression> expressions = graph.getIntermediateExpressions(srcExpression,
				destExpression, associations);
		// If the association is direct association, remove the respective association
		if (expressions.isEmpty())
		{
			mQueryObject.removeAssociation(sourceexpressionId, destexpressionId);
		}
		else
		{
			for (IExpression intermediateExpression : expressions)
			{
				mQueryObject.removeExpression(intermediateExpression.getExpressionId());
			}
		}
	}

	/**
	 * This method clone path.
	 * @param pathIn IPath
	 * @return cloned path
	 */
	private Path clonePath(IPath pathIn)
	{
		//Path newPath = (Path)path;
		Path newPath = new Path();
		Path path = (Path) pathIn;
		newPath.setSourceEntity(path.getSourceEntity());
		newPath.setSourceEntityId(path.getSourceEntity().getId());
		newPath.setTargetEntity(path.getTargetEntity());
		newPath.setTargetEntityId(path.getTargetEntity().getId());
		newPath.setIntermediatePaths(path.getIntermediatePaths());
		newPath.setPathId(path.getPathId());
		List<IAssociation> newAssociationLidt = new ArrayList<IAssociation>();
		for (IAssociation association : path.getIntermediateAssociations())
		{
			IntraModelAssociation newAssociation=new IntraModelAssociation();
			newAssociation.setDynamicExtensionsAssociation(((IntraModelAssociation) association)
					.getDynamicExtensionsAssociation());
			newAssociationLidt.add(newAssociation);
		}
		newPath.setIntermediateAssociations(newAssociationLidt);
		return newPath;
	}
}
