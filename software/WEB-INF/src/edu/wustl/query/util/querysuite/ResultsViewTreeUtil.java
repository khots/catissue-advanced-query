
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.common.query.category.CategoryProcessorUtility;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IArithmeticOperand;
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
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.utils.ConstraintsObjectBuilder;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 *
 * @author baljeet_dhindhwal
 *
 */

public class ResultsViewTreeUtil
{

	/**
	 * This method returns all parent and immediate containment children map.
	 * @param mainEntity main entity for which parent child map is required
	 * @return partentChildEntityMap
	 */
	public static Map<QueryableObjectInterface, List<QueryableObjectInterface>> getAllParentChildrenMap(
			QueryableObjectInterface mainEntity)
	{
		HashMap<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap = new HashMap<QueryableObjectInterface, List<QueryableObjectInterface>>();
		ArrayList<QueryableObjectInterface> mainEntityContainmentList = new ArrayList<QueryableObjectInterface>();
		QueryAddContainmentsUtil.getMainEntityContainments(partentChildEntityMap,
				mainEntityContainmentList, mainEntity);
		return partentChildEntityMap;
	}

	/**
	 * This method returns parent and immediate tagged child entity for Results View.
	 * @param partentChildEntityMap parent Child Entity Map
	 * @param rootEntity root entity
	 * @return taggedEntitiesParentChildMap
	 */
	public static Map<QueryableObjectInterface, List<QueryableObjectInterface>> getTaggedEntitiesParentChildMap(
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap,
			QueryableObjectInterface rootEntity)
	{

		Map<QueryableObjectInterface, List<QueryableObjectInterface>> taggedEntitiesParentChildMap = new HashMap<QueryableObjectInterface, List<QueryableObjectInterface>>();
		Set<QueryableObjectInterface> keySet = partentChildEntityMap.keySet();
		for (QueryableObjectInterface keyEntity : keySet)
		{
			List<QueryableObjectInterface> taggedListForMainEntity = new ArrayList<QueryableObjectInterface>();
			//this is the case when the tag is also applied on the root Entity
			if (keyEntity.isTagPresent(Constants.TAGGED_VALUE_RESULTVIEW)
					&& rootEntity.getId().longValue() == keyEntity.getId().longValue())
			{
				taggedListForMainEntity.add(keyEntity);
			}
			List<QueryableObjectInterface> childrenEntities = partentChildEntityMap.get(keyEntity);
			for (QueryableObjectInterface childEntity : childrenEntities)
			{
				if (checkForResultViewTag(rootEntity, childEntity))
				{
					taggedListForMainEntity.add(childEntity);
				}
			}
			taggedEntitiesParentChildMap.put(keyEntity, taggedListForMainEntity);
		}
		return taggedEntitiesParentChildMap;
	}

	/**
	 * This method checks if an entity is tagged for Results View.
	 * @param rootEntity root entity
	 * @param childEntity child entity
	 * @return isTagPresent
	 */
	private static boolean checkForResultViewTag(QueryableObjectInterface rootEntity,
			QueryableObjectInterface childEntity)
	{
		boolean isTagPresent = false;
		String tagValue = childEntity.getTaggedValue(Constants.TAGGED_VALUE_RESULTVIEW);
		String[] split = tagValue.split(",");
		for (String entityName : split)
		{
			if (entityName.equals(rootEntity.getName()))
			{
				isTagPresent = true;
				break;
			}
		}
		return isTagPresent;
	}

	/**
	 * This method returns a map Containing complete path in Hierarchy for each tagged entity.
	 * @param taggedEntitiesParentChildMap tagged Entities Parent Child Map
	 * @param parentChilddrenMap parent Children Map
	 * @return eachTaggedEntityPathMap
	 */
	public static Map<QueryableObjectInterface, List<QueryableObjectInterface>> getPathsMapForTaggedEntity(
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> taggedEntitiesParentChildMap,
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> parentChilddrenMap)
	{
		Map<QueryableObjectInterface, List<QueryableObjectInterface>> eachTaggedEntityPathMap = new HashMap<QueryableObjectInterface, List<QueryableObjectInterface>>();
		Set<QueryableObjectInterface> keySet = taggedEntitiesParentChildMap.keySet();
		for (QueryableObjectInterface entity : keySet)
		{
			List<QueryableObjectInterface> taggedEntityList = taggedEntitiesParentChildMap
					.get(entity);
			if (taggedEntityList != null && !taggedEntityList.isEmpty())
			{
				for (QueryableObjectInterface taggedEntity : taggedEntityList)
				{
					List<QueryableObjectInterface> pathList = new ArrayList<QueryableObjectInterface>();
					pathList.add(taggedEntity);
					getPathListForTaggedEntity(taggedEntity, pathList, parentChilddrenMap);
					eachTaggedEntityPathMap.put(taggedEntity, pathList);
				}
			}
		}
		return eachTaggedEntityPathMap;
	}

	/**
	 * This method returns list stating complete path for a  tagged entity.
	 * @param taggedEntity tagged Entity for which path is required
	 * @param pathList path List
	 * @param parentChilddrenMap parent Children Map
	 * @return pathList
	 */
	private static List<QueryableObjectInterface> getPathListForTaggedEntity(
			QueryableObjectInterface taggedEntity, List<QueryableObjectInterface> pathList,
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> parentChilddrenMap)
	{
		Set<QueryableObjectInterface> keySet = parentChilddrenMap.keySet();
		for (QueryableObjectInterface mainEntity : keySet)
		{
			List<QueryableObjectInterface> containmentList = parentChilddrenMap.get(mainEntity);
			if (containmentList.contains(taggedEntity))
			{
				pathList.add(mainEntity);
				getPathListForTaggedEntity(mainEntity, pathList, parentChilddrenMap);
			}
		}
		return pathList;
	}

	/**
	 * This method adds all tagged entities to IQuery for Results View.
	 * @param eachTaggedEntityPathMap eachTaggedEntityPathMap map
	 * @param queryBuilder query builder object
	 * @param rootEntity root entity
	 * @param rootExpId root expression id
	 * @param expressionIdMap expression Id Map
	 * @return taggedEntityPathExprMap
	 */
	public static Map<QueryableObjectInterface, List<Integer>> addAllTaggedEntitiesToIQuery(
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> eachTaggedEntityPathMap,
			IClientQueryBuilderInterface queryBuilder, QueryableObjectInterface rootEntity,
			int rootExpId, Map<Integer, Integer> expressionIdMap)
	{
		Map<QueryableObjectInterface, List<Integer>> taggedEntityPathExprMap = new HashMap<QueryableObjectInterface, List<Integer>>();

		//All Intermediate nodes as well tagged entities should be added like containment
		Set<QueryableObjectInterface> taggedEntitiesKeySet = eachTaggedEntityPathMap.keySet();
		for (QueryableObjectInterface taggedEntity : taggedEntitiesKeySet)
		{
			List<QueryableObjectInterface> pathList = eachTaggedEntityPathMap.get(taggedEntity);
			List<Integer> pathExpressionsList = new ArrayList<Integer>();
			for (int i = pathList.size() - 1; i >= 0; i--)
			{
				QueryableObjectInterface pathEntity = pathList.get(i);
				if (pathEntity.equals(rootEntity))
				{
					pathExpressionsList.add(rootExpId);
				}
				else
				{
					updatePathExpressionsList(pathEntity, queryBuilder, pathExpressionsList);
				}
			}
			taggedEntityPathExprMap.put(taggedEntity, pathExpressionsList);
		}
		return taggedEntityPathExprMap;
	}

	/**
	 * This method adds the entity with rule to IQuery.
	 * @param queryBuilder query builder object
	 * @param rootEntity root entity
	 * @param iRule IRule to add to the query
	 * @return rootExpId
	 */
	public static int addExpressionToIQuery(IConstraintsObjectBuilderInterface queryBuilder,
			QueryableObjectInterface rootEntity, IRule iRule)
	{
		int rootExpId;
		if (iRule == null)
		{
			rootExpId = queryBuilder.addExpression(rootEntity);
		}
		else
		{
			rootExpId = queryBuilder.addExpression(iRule, rootEntity);
		}
		return rootExpId;
	}

	/**
	 * This method updates the path expressions list if the tagged entity is already present in query.
	 * @param pathEntity path entity
	 * @param queryBuilder query builder object
	 * @param pathExpressionsList path Expressions List
	 */
	private static void updatePathExpressionsList(QueryableObjectInterface pathEntity,
			IClientQueryBuilderInterface queryBuilder, List<Integer> pathExpressionsList)
	{
		IQuery query = queryBuilder.getQuery();
		IConstraints constraints = query.getConstraints();
		boolean isMatchFound = false;
		for (IExpression expression : constraints)
		{
			if (expression.getQueryEntity().getDynamicExtensionsEntity().equals(pathEntity))
			{
				pathExpressionsList.add(expression.getExpressionId());
				isMatchFound = true;
				break;
			}
		}
		//If no match is found with any of the expression, only then add the expression to query
		if (!isMatchFound)
		{
			//add new expression to Query and add that expression id to pathExpressionsList
			int expressionId = addExpressionToIQuery((ConstraintsObjectBuilder) queryBuilder,
					pathEntity, null);
			pathExpressionsList.add(expressionId);
		}
	}

	/**
	 * This method creates associations among added tagged entities to form complete graph.
	 * @param taggedEntityPathExprMap taggedEntity vs PathExpressions Map
	 * @param queryBuilder query Builder object
	 */
	public static void addLinksAmongExpressionsAdded(
			Map<QueryableObjectInterface, List<Integer>> taggedEntityPathExprMap,
			IClientQueryBuilderInterface queryBuilder)
	{
		IQuery query = queryBuilder.getQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph graph = constraints.getJoinGraph();

		Set<QueryableObjectInterface> taggedEntityKeySet = taggedEntityPathExprMap.keySet();
		for (QueryableObjectInterface taggedEntity : taggedEntityKeySet)
		{
			List<Integer> pathExpressionsList = taggedEntityPathExprMap.get(taggedEntity);
			if ((pathExpressionsList != null) && (!pathExpressionsList.isEmpty()))
			{
				for (int i = 0; i < pathExpressionsList.size() - Constants.ONE; i++)
				{
					int parentExpId = pathExpressionsList.get(i);
					int childExpId = pathExpressionsList.get(i + Constants.ONE);
					IExpression parentExpression = constraints.getExpression(parentExpId);
					IExpression childExpresion = constraints.getExpression(childExpId);
					IIntraModelAssociation association = (IIntraModelAssociation) (graph
							.getAssociation(parentExpression, childExpresion));
					if (association == null)
					{
						List<IExpression> parentHierarchyList = getAllParentsHierarchy(constraints,
								childExpId);

						//If association is null, only then add the association
						IPath path = getIPath(parentExpression.getQueryEntity()
								.getDynamicExtensionsEntity(), childExpresion.getQueryEntity()
								.getDynamicExtensionsEntity());
						if (!parentHierarchyList.contains(parentExpression)
								&& !queryBuilder.isPathCreatesCyclicGraph(parentExpId, childExpId,
										path))
						{
							QueryAddContainmentsUtil.linkTwoNodes(parentExpId, childExpId, path,
									queryBuilder);
						}
					}
				}
			}
		}

	}

	/**
	 * This method adds tagged entitie's tagged attributes to IQuery for output view.
	 * @param taggedEntityPathExprMap taggedEntity vs PathExpressions Map
	 * @param queryBuilder query Builder object
	 */
	public static void addTaggedOutputAttributesToQuery(
			Map<QueryableObjectInterface, List<Integer>> taggedEntityPathExprMap,
			IClientQueryBuilderInterface queryBuilder)
	{
		//For each tagged Entity ,get the tagged Attributes and add them to Ioutput Attribute list
		ParameterizedQuery query = (ParameterizedQuery) queryBuilder.getQuery();
		List<IOutputAttribute> outputAttributeList = query.getOutputAttributeList();
		IConstraints constraints = query.getConstraints();
		Set<QueryableObjectInterface> taggedEntityKeySet = taggedEntityPathExprMap.keySet();
		for (QueryableObjectInterface taggedEntity : taggedEntityKeySet)
		{
			List<Integer> pathExpressionsIdsList = taggedEntityPathExprMap.get(taggedEntity);
			int taggedEntityExpId = pathExpressionsIdsList
					.get((pathExpressionsIdsList.size() - Constants.ONE));

			Collection<QueryableAttributeInterface> attributeCollection = taggedEntity
					.getAllAttributes();
			for (QueryableAttributeInterface attribute : attributeCollection)
			{
				IExpression expression = constraints.getExpression(taggedEntityExpId);
				if (attribute.isTagPresent(Constants.TAGGED_VALUE_RESULTVIEW)
						&& !Utility.isPresentInOutputAttrList(query, attribute, expression))
				{
					//If the Attribute is tagged ,then Create IOutPut attribute and add to list
					outputAttributeList.add(new OutputAttribute(expression, attribute));
				}
			}
		}
		//Setting the IOutPut Attribute List to Query
		//query.setOutputAttributeList(outputAttributeList);
	}

	/**
	 * This method retrieves path between two entities.
	 * @param parentEntity parent entity
	 * @param childEntity child entity
	 * @return IPath object
	 */
	public static IPath getIPath(QueryableObjectInterface parentEntity,
			QueryableObjectInterface childEntity)
	{
		//Now Add path among expressions added to query
		Map<AmbiguityObject, List<IPath>> map = null;
		AmbiguityObject ambiguityObject = null;
		ambiguityObject = new AmbiguityObject(parentEntity.getEntity(), childEntity.getEntity());
		IPathFinder pathFinder = new CommonPathFinder();
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject, pathFinder);
		map = resolveAmbigity.getPathsForAllAmbiguities();
		List<IPath> pathList = map.get(ambiguityObject);
		return pathList.get(0);
	}

	/**
	 * This method updates newly generated IQuery with all limits added by user and with tagged entities.
	 * @param generatedQuery generated query
	 * @param parentChildrenMap parent vs children map
	 * @param mainEntityTreeDataNode main Entity Tree Data Node
	 * @param parentQuery parent query
	 * @param expressionIdMap expressionId Map
	 * @throws MultipleRootsException Multiple Roots Exception
	 */
	public static void updateGeneratedQuery(IQuery generatedQuery,
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap,
			OutputTreeDataNode mainEntityTreeDataNode, IQuery parentQuery,
			Map<Integer, Integer> expressionIdMap) throws MultipleRootsException
	{
		IConstraintsObjectBuilderInterface queryBuilder = new ConstraintsObjectBuilder();
		queryBuilder.setQuery(generatedQuery);
		IConstraints constraints = generatedQuery.getConstraints();
		IJoinGraph graph = constraints.getJoinGraph();
		IExpression rootExpression = graph.getRoot();
		int rootExpressionId = rootExpression.getExpressionId();
		List<ICustomFormula> customFormulaList = new ArrayList<ICustomFormula>();

		//This rootExpression gives the root expression of the Query
		QueryableObjectInterface rootEntity = rootExpression.getQueryEntity()
				.getDynamicExtensionsEntity();
		List<OutputTreeDataNode> childrenList = parentChildrenMap.get(mainEntityTreeDataNode);
		List<OutputTreeDataNode> mainEntityChildrenNode = new ArrayList<OutputTreeDataNode>();

		//Add these direct children to IQuery with rule
		//Get the constraints from old patient Data query.
		//This is required to get the rules added on children nodes
		IConstraints parentQueryConstraints = parentQuery.getConstraints();
		Map<OutputTreeDataNode, Integer> childrenExpIdsMap = new HashMap<OutputTreeDataNode, Integer>();
		if (childrenList != null && !childrenList.isEmpty())
		{
			mainEntityChildrenNode.addAll(childrenList);
			for (OutputTreeDataNode childNode : childrenList)
			{
				int childExpressionId = childNode.getExpressionId();
				IExpression childExpression = parentQueryConstraints
						.getExpression(childExpressionId);
				IRule iRule = Utility
						.createNewRuleFromOldRule(getRuleFromExpression(childExpression));
				for (IExpressionOperand expressionOperand : childExpression)
				{
					if (expressionOperand instanceof ICustomFormula)
					{
						customFormulaList.add((ICustomFormula) expressionOperand);
					}
				}

				//Now get the Entity,from child node
				QueryableObjectInterface childEntity = childNode.getOutputEntity()
						.getDynamicExtensionsEntity();

				//Now add that expression to generated  IQuery
				int expressionId = addExpressionAndRuleToQuery(queryBuilder, iRule, childEntity);
				expressionIdMap.put(childExpressionId, expressionId);

				//This map is used for retrieving the expression id a children that is added to iQuery
				childrenExpIdsMap.put(childNode, expressionId);

				//Now add link among entities
				IPath path = getIPath(rootEntity, childEntity);
				if (!queryBuilder.isPathCreatesCyclicGraph(rootExpressionId, expressionId, path))
				{
					QueryAddContainmentsUtil.linkTwoNodes(rootExpressionId, expressionId, path,
							queryBuilder);
				}
			}
			//Now for each children, add further children
			for (int i = 0; i < mainEntityChildrenNode.size(); i++)
			{
				OutputTreeDataNode childNode = mainEntityChildrenNode.get(i);
				int childRootExpId = childrenExpIdsMap.get(childNode);
				QueryableObjectInterface childRootEntity = childNode.getOutputEntity()
						.getDynamicExtensionsEntity();
				List<OutputTreeDataNode> childrenOutPutList = parentChildrenMap.get(childNode);
				if (childrenOutPutList != null && !childrenOutPutList.isEmpty())
				{
					mainEntityChildrenNode.addAll(childrenOutPutList);
					for (OutputTreeDataNode outputNode : childrenOutPutList)
					{
						int expId = outputNode.getExpressionId();
						IExpression childExpression = parentQueryConstraints.getExpression(expId);
						IRule iRule = Utility
								.createNewRuleFromOldRule(getRuleFromExpression(childExpression));
						QueryableObjectInterface childEntity = outputNode.getOutputEntity()
								.getDynamicExtensionsEntity();
						int expressionId = addExpressionAndRuleToQuery(queryBuilder, iRule,
								childEntity);
						expressionIdMap.put(expId, expressionId);
						for (IExpressionOperand expressionOperand : childExpression)
						{
							if (expressionOperand instanceof ICustomFormula)
							{
								customFormulaList.add((ICustomFormula) expressionOperand);
							}
						}
						IPath path = getIPath(childRootEntity, childEntity);

						//Adding to Map
						childrenExpIdsMap.put(outputNode, expressionId);
						if (!queryBuilder.isPathCreatesCyclicGraph(childRootExpId, expressionId,
								path))
						{
							QueryAddContainmentsUtil.linkTwoNodes(childRootExpId, expressionId,
									path, queryBuilder);
						}
					}
				}
			}
		}
		addAllCustomFormulasToQuery(customFormulaList, expressionIdMap, queryBuilder);
	}

	/**
	 * It will create the newCustomFormulas on the basis on CustomFormulas in the customFormulaList.
	 * And will add those to the formed Query in the queryBuilder.
	 * @param customFormulaList containing Old Custom Formulas
	 * @param expressionIdMap old Expression Id v/s new Expression id map
	 * @param queryBuilder in which to new Query is formed
	 */
	private static void addAllCustomFormulasToQuery(List<ICustomFormula> customFormulaList,
			Map<Integer, Integer> expressionIdMap, IConstraintsObjectBuilderInterface queryBuilder)
	{
		IParameterizedQuery newQuery = (IParameterizedQuery) queryBuilder.getQuery();
		for (ICustomFormula oldCustomFormula : customFormulaList)
		{
			//This is two node tq
			if (oldCustomFormula.getLhs().getOperand(0) instanceof IExpressionAttribute)
			{
				createCustomFormula(expressionIdMap, newQuery, oldCustomFormula);
			}
			else
			{
				//this is single node TQ
				createSingleNodeCustomFormula(expressionIdMap, newQuery, oldCustomFormula);
			}
		}
	}

	/**
	 * It will create a single node custom formula same as the given oldCustomFormula
	 * @param expressionIdMap expressionId Map
	 * @param newQuery new generated query
	 * @param oldCustomFormula old Custom Formula
	 */
	private static void createSingleNodeCustomFormula(Map<Integer, Integer> expressionIdMap,
			IParameterizedQuery newQuery, ICustomFormula oldCustomFormula)
	{
		IArithmeticOperand oldLhsOperand1 = oldCustomFormula.getLhs().getOperand(0);

		IExpressionAttribute oldLhsOperand2 = (IExpressionAttribute) oldCustomFormula.getLhs()
				.getOperand(Constants.ONE);
		int lhsExpressionId2 = expressionIdMap
				.get(oldLhsOperand2.getExpression().getExpressionId());
		IExpression lhsExpression2 = newQuery.getConstraints().getExpression(lhsExpressionId2);
		IArithmeticOperand lhsOperand2 = getNewOperand(oldLhsOperand2, lhsExpression2);
		ICustomFormula newCustomFormula = CategoryProcessorUtility.formNewCustomFormula(
				oldCustomFormula, oldLhsOperand1, lhsOperand2);
		lhsExpression2.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				newCustomFormula);
	}

	/**
	 * It will create the CustomFormula From the oldCustomFormula Which is formed with two different Expressions.
	 * and add it to the  newQuery.
	 * (Due to bug#12646 if this condition occurs it will ignore that CustomFormula, thus Results return will
	 * be without the TQ conditions)
	 * @param expressionIdMap expressionId Map
	 * @param newQuery new generated query
	 * @param oldCustomFormula old Custom Formula
	 */
	private static void createCustomFormula(Map<Integer, Integer> expressionIdMap,
			IParameterizedQuery newQuery, ICustomFormula oldCustomFormula)
	{
		IExpressionAttribute oldLhsOperand1 = (IExpressionAttribute) oldCustomFormula.getLhs()
				.getOperand(0);
		IExpressionAttribute oldLhsOperand2 = (IExpressionAttribute) oldCustomFormula.getLhs()
				.getOperand(Constants.ONE);
		Integer lhsExpressionId1 = expressionIdMap.get(oldLhsOperand1.getExpression()
				.getExpressionId());
		Integer lhsExpressionId2 = expressionIdMap.get(oldLhsOperand2.getExpression()
				.getExpressionId());
		IExpression lhsExpression1 = newQuery.getConstraints().getExpression(lhsExpressionId1);
		if (lhsExpressionId2 != null)
		{
			IExpression lhsExpression2 = newQuery.getConstraints().getExpression(lhsExpressionId2);
			IArithmeticOperand lhsOperand1 = getNewOperand(oldLhsOperand1, lhsExpression1);
			IArithmeticOperand lhsOperand2 = getNewOperand(oldLhsOperand2, lhsExpression2);
			ICustomFormula newCustomFormula = CategoryProcessorUtility.formNewCustomFormula(
					oldCustomFormula, lhsOperand1, lhsOperand2);
			lhsExpression1.addOperand(QueryObjectFactory
					.createLogicalConnector(LogicalOperator.And), newCustomFormula);
		}
	}

	/**
	 * It will create the new operand which is from the Expression added to the newQuery object
	 * For the old Operand.
	 * @param operand operand
	 * @param newExpression expression to be processed
	 * @return newOperand of type IArithmeticOperand
	 */
	private static IArithmeticOperand getNewOperand(IExpressionAttribute operand,
			IExpression newExpression)
	{
		IExpressionAttribute newOperand = null;
		QueryableAttributeInterface queryableAttribute = operand.getAttribute();
		if (operand instanceof IDateOffsetAttribute)
		{
			newOperand = QueryObjectFactory.createDateOffsetAttribute(newExpression,
					queryableAttribute, ((IDateOffsetAttribute) operand).getTimeInterval());
		}
		else if (operand instanceof ExpressionAttribute)
		{
			newOperand = QueryObjectFactory.createExpressionAttribute(newExpression, operand
					.getAttribute());

		}

		return newOperand;
	}

	/**
	 * This method returns the rule associated with an expression.
	 * @param childExpression child expression
	 * @return iRule
	 */
	public static IRule getRuleFromExpression(IExpression childExpression)
	{
		IRule iRule = null;
		for (IExpressionOperand expressionOperand : childExpression)
		{
			if (expressionOperand instanceof IRule)
			{
				iRule = (IRule) expressionOperand;
				break;
			}
		}
		return iRule;
	}

	/**
	 * This method adds an expression and rule to iQuery.
	 * @param queryBuilder query builder object
	 * @param iRule IRule object
	 * @param childEntity child entity
	 * @return expressionId
	 */
	private static int addExpressionAndRuleToQuery(IConstraintsObjectBuilderInterface queryBuilder,
			IRule iRule, QueryableObjectInterface childEntity)
	{
		int expressionId;
		expressionId = addExpressionToIQuery(queryBuilder, childEntity, iRule);
		return expressionId;
	}

	/**
	 * This method returns the all parents hierarchy of a Main entity From IQuery generated by User.
	 * @param constraints query constraints
	 * @param mainEntityTreeDataNode main Entity Tree Data Node
	 * @return parentsList
	 */
	public static List<IExpression> getAllParentsHierarchy(IConstraints constraints,
			OutputTreeDataNode mainEntityTreeDataNode)
	{
		int mainEntityOldExpId = mainEntityTreeDataNode.getExpressionId();
		IExpression oldPatientDataQueryExp = constraints.getExpression(mainEntityOldExpId);
		IJoinGraph oldPatientDataQueryGraph = constraints.getJoinGraph();

		//Get the complete parent child Hierarchy, till root is reached

		//Extract this as Method
		List<IExpression> parentsList = oldPatientDataQueryGraph
				.getParentList(oldPatientDataQueryExp);
		if (parentsList != null && !parentsList.isEmpty())
		{
			//Iterate through full hierarchy till root is reached
			for (int i = 0; i < parentsList.size(); i++)
			{
				IExpression parentExp = parentsList.get(i);
				List<IExpression> listOfParents = oldPatientDataQueryGraph.getParentList(parentExp);
				if ((listOfParents != null) && (!listOfParents.isEmpty()))
				{
					parentsList.addAll(listOfParents);
				}
			}
		}
		return parentsList;
	}

	/**
	 * Overriding method for expression iDs to get the Parent Hierarchy.
	 * @param constraints query constraints
	 * @param mainEntityOldExpId old expression id of main entity
	 * @return parentsList
	 */
	public static List<IExpression> getAllParentsHierarchy(IConstraints constraints,
			int mainEntityOldExpId)
	{
		IExpression oldPatientDataQueryExp = constraints.getExpression(mainEntityOldExpId);
		IJoinGraph oldPatientDataQueryGraph = constraints.getJoinGraph();

		//Get the complete parent child Hierarchy, till root is reached

		//Extract this as Method
		List<IExpression> parentsList = oldPatientDataQueryGraph
				.getParentList(oldPatientDataQueryExp);
		if (parentsList != null && !parentsList.isEmpty())
		{
			//Iterate through full hierarchy till root is reached
			for (int i = 0; i < parentsList.size(); i++)
			{
				IExpression parentExp = parentsList.get(i);
				List<IExpression> listOfParents = oldPatientDataQueryGraph.getParentList(parentExp);
				if ((listOfParents != null) && (!listOfParents.isEmpty()))
				{
					parentsList.addAll(listOfParents);
				}
			}
		}
		return parentsList;
	}

	/**
	 * Returns an entity From Cache by entity name.
	 * @param entityName entity name for the entity to be fetched
	 * @param entityGroups entity groups
	 * @return entity
	 */
	public static EntityInterface getEntityFromCache(String entityName,
			Collection<EntityGroupInterface> entityGroups)
	{
		EntityInterface entity = null;
		Iterator<EntityGroupInterface> entityGroupsItr = entityGroups.iterator();
		while (entityGroupsItr.hasNext())
		{
			EntityGroupInterface entityGroup = entityGroupsItr.next();
			entity = entityGroup.getEntityByName(entityName);
			if (entity != null)
			{
				break;
			}
		}
		return entity;
	}

}
