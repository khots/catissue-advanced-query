
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.RoleInterface;
import edu.wustl.cab2b.client.ui.dag.PathLink;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * 
 * @author Baljeet Singh
 *
 */
public abstract class IQueryUpdationUtil
{

	private static org.apache.log4j.Logger logger = Logger.getLogger(IQueryUpdationUtil.class);

	/**
	 * This method check whether to add containments or not for an entity
	 * @param mainExpId
	 * @param keySet
	 * @return true/false
	 */
	private static boolean checkIfToAddContainmnets(int mainExpId, Set<Integer> keySet)
	{
		boolean isToAddContainments = true;
		Iterator<Integer> itr = keySet.iterator();
		while (itr.hasNext())
		{
			int expId = itr.next().intValue();
			if (mainExpId == expId)
			{
				isToAddContainments = false;
				break;
			}
		}
		return isToAddContainments;
	}

	/**
	 * This method returns containments for all main Entities in Query   
	 * @param query
	 * @param session
	 * @return eachExpressionParentChildMap
	 */
	public static Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> getAllConatainmentObjects(
			IQuery query, HttpSession session,boolean isDefaultConditionPresent)
	{
		Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap = new HashMap<Integer, HashMap<EntityInterface, List<EntityInterface>>>();
		List<Integer> expressionIdsList = getAddLimitExpressionList(session);
		if(expressionIdsList.isEmpty() && isDefaultConditionPresent)
		{
			IConstraints constraints = query.getConstraints();
			for (IExpression expression : constraints)
			{
				expressionIdsList.add(expression.getExpressionId());
			}
		}
		List<Integer> mainExpressionIds = new ArrayList<Integer>();
		Map<Integer, List<EntityInterface>> eachExpressionContainmentMap = getExpressionsContainmentMap(session);

		//Get the list if all main Entities present in main query
		List<EntityInterface> mainEntityList = getAllMainObjects(query);

		//Find out all Main expressions present in IQuery
		List<Integer> expressionsToAddContainments = new ArrayList<Integer>();
		if (expressionIdsList != null && (!expressionIdsList.isEmpty()))
		{
			getMainExpressionsList(query, expressionIdsList, mainExpressionIds, mainEntityList);

			//Here we got the list of all main  Expressions, now we need to find out for expression we need to add Containments
			if(isDefaultConditionPresent)
			{
				expressionsToAddContainments.addAll(mainExpressionIds);
			}
			else
			{
				getExpsToAddContainments(mainExpressionIds, eachExpressionContainmentMap,
					expressionsToAddContainments);
			}
		}
		//For each expression id in the  expressionsToAddContainments, get It's Containments
		updateAllMaps(query, eachExpressionParentChildMap, eachExpressionContainmentMap,
				expressionsToAddContainments,isDefaultConditionPresent);

		session.setAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP, eachExpressionContainmentMap);
		session.setAttribute(Constants.MAIN_EXPRESSION_TO_ADD_CONTAINMENTS,
				expressionsToAddContainments);
		session.setAttribute(Constants.MAIN_ENTITY_LIST, mainEntityList);
		return eachExpressionParentChildMap;
	}

	/**
	 * This method updates all maps required for getAllConatainmentObjects method
	 * @param query
	 * @param eachExpressionParentChildMap
	 * @param eachExpressionContainmentMap
	 * @param expressionsToAddContainments
	 */
	private static void updateAllMaps(
			IQuery query,
			Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap,
			Map<Integer, List<EntityInterface>> eachExpressionContainmentMap,
			List<Integer> expressionsToAddContainments,boolean isDefaultConditionPresent)
	{
		Iterator<Integer> expsToAddItr = expressionsToAddContainments.iterator();
		while (expsToAddItr.hasNext())
		{
			int expId = expsToAddItr.next().intValue();
			IConstraints constraints = query.getConstraints();
			IExpression expression = constraints.getExpression(expId);
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
			HashMap<EntityInterface, List<EntityInterface>> partentChildEntityMap = new HashMap<EntityInterface, List<EntityInterface>>();
			ArrayList<EntityInterface> mainEntityContainmentList = new ArrayList<EntityInterface>();
			getMainEntityContainments(partentChildEntityMap, mainEntityContainmentList, entity,isDefaultConditionPresent);

			if ((mainEntityContainmentList != null) && (!mainEntityContainmentList.isEmpty()))
			{
				eachExpressionContainmentMap.put(Integer.valueOf(expId), mainEntityContainmentList);
			}

			eachExpressionParentChildMap.put(Integer.valueOf(expId), partentChildEntityMap);
		}
	}

	/**
	 * This method returns List containing all expressions for Limits are added from "Add Limit" page.
	 * @param session
	 * @return expressionIdsList
	 */
	@SuppressWarnings("unchecked")
	private static List<Integer> getAddLimitExpressionList(HttpSession session)
	{
		List<Integer> expressionIdsList = (List<Integer>) session
				.getAttribute(Constants.ALL_ADD_LIMIT_EXPRESSIONS);
		if (expressionIdsList == null)
		{
			expressionIdsList = new ArrayList<Integer>();
		}
		return expressionIdsList;
	}

	/**
	 * This method updates list containing expressions for which Containments are to be added  
	 * @param mainExpressionIds
	 * @param eachExpressionContainmentMap
	 * @param expressionsToAddContainments
	 */
	private static void getExpsToAddContainments(List<Integer> mainExpressionIds,
			Map<Integer, List<EntityInterface>> eachExpressionContainmentMap,
			List<Integer> expressionsToAddContainments)
	{
		if (eachExpressionContainmentMap == null || eachExpressionContainmentMap.isEmpty())
		{
			//This is first time case
			expressionsToAddContainments.addAll(mainExpressionIds);
		}
		else
		{
			//This is the case of Previous ->Next, by this time containments of previous main entities are added  
			Iterator<Integer> mainExpsItr = mainExpressionIds.iterator();
			while (mainExpsItr.hasNext())
			{
				int mainExpId = mainExpsItr.next().intValue();
				boolean isToAddContainments = checkIfToAddContainmnets(mainExpId,
						eachExpressionContainmentMap.keySet());
				if (isToAddContainments)
				{
					expressionsToAddContainments.add((Integer.valueOf(mainExpId)));
				}
			}
		}
	}

	/**
	 * This method updates list for all main expressions present in Query 
	 * @param query
	 * @param expressionIdsList
	 * @param mainExpressionIds
	 * @param mainEntityList
	 * @return
	 */
	private static void getMainExpressionsList(IQuery query, List<Integer> expressionIdsList,
			List<Integer> mainExpressionIds, List<EntityInterface> mainEntityList)
	{
		IConstraints constraints = query.getConstraints();
		Iterator<Integer> itr = expressionIdsList.iterator();
		while (itr.hasNext())
		{
			int expId = itr.next().intValue();
			IExpression expression = constraints.getExpression(expId);
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

			//Check if this entity is main Entity or not
			boolean ifMainObject = checkIfMainObject(entity, mainEntityList);
			if (ifMainObject)
			{
				mainExpressionIds.add(Integer.valueOf(expression.getExpressionId()));
			}
		}
	}

	/**
	 * 
	 * @param session
	 * @return eachExpressionContainmentMap
	 */
	private static Map<Integer, List<EntityInterface>> getExpressionsContainmentMap(
			HttpSession session)
	{
		Map<Integer, List<EntityInterface>> eachExpressionContainmentMap = (HashMap<Integer, List<EntityInterface>>) session
				.getAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP);
		if (eachExpressionContainmentMap == null)
		{
			eachExpressionContainmentMap = new HashMap<Integer, List<EntityInterface>>();
		}
		return eachExpressionContainmentMap;
	}

	/**
	 * This method returns all containments for a Main entity present in Query
	 * @param partentChildEntityMap
	 * @param mainEntityContainmentList
	 * @param mainEntity
	 */
	private static void getMainEntityContainments(
			Map<EntityInterface, List<EntityInterface>> partentChildEntityMap,
			List<EntityInterface> mainEntityContainmentList, EntityInterface mainEntity,
			boolean isDefaultConditionPresent)
	{
		//For each Entity, get all Associations
		List<EntityInterface> list = getContainmentAssociations(mainEntity);
		List<EntityInterface> uniqueEntityList = new ArrayList<EntityInterface>();
		Iterator<EntityInterface> itr = list.iterator();
		while (itr.hasNext())
		{
			EntityInterface entity = itr.next();
			if (!mainEntityContainmentList.contains(entity))
			{
				boolean hasDefaultCondition = Utility.istagPresent(entity,Constants.TAGGED_VALUE_DEFAULT_CONDITION);
				if(hasDefaultCondition)
				{
					mainEntityContainmentList.add(entity);
					uniqueEntityList.add(entity);
				}
			}
		}
		partentChildEntityMap.put(mainEntity, uniqueEntityList);

		//Now for each containment entity, get further containments till it ends
		if (!mainEntityContainmentList.isEmpty())
		{
			getChildEntityContainment(partentChildEntityMap, mainEntityContainmentList,isDefaultConditionPresent);
		}
	}

	/**
	 * This method returns all containments for a child entity of a main Entity present in Query
	 * @param partentChildEntityMap
	 * @param mainEntityContainmentList
	 */
	private static void getChildEntityContainment(
			Map<EntityInterface, List<EntityInterface>> partentChildEntityMap,
			List<EntityInterface> mainEntityContainmentList,
			boolean isDefaultConditionPresent)
	{
		List<EntityInterface> list;
		int count = 0;
		while (count < mainEntityContainmentList.size())
		{
			EntityInterface containmentEntity = mainEntityContainmentList.get(count);
			list = getContainmentAssociations(containmentEntity);
			List<EntityInterface> uniqueEntityList = new ArrayList<EntityInterface>();
			Iterator<EntityInterface> itr = list.iterator();
			while (itr.hasNext())
			{
				EntityInterface entity = itr.next();
				if (!mainEntityContainmentList.contains(entity))
				{
					boolean hasDefaultCondition = true;
					if(isDefaultConditionPresent)
					{
						hasDefaultCondition = Utility.istagPresent(entity,Constants.TAGGED_VALUE_DEFAULT_CONDITION);
					}
					if(hasDefaultCondition)
					{
						mainEntityContainmentList.add(entity);
						uniqueEntityList.add(entity);
					}
				}
			}
			if (!uniqueEntityList.isEmpty())
			{
				partentChildEntityMap.put(containmentEntity, uniqueEntityList);
			}
			count++;
		}
	}

	/**
	 * This method returns list containing all containment entities of a entity
	 * @param entity
	 * @return containmentEntitiesList
	 */

	private static List<EntityInterface> getContainmentAssociations(EntityInterface entity)
	{
		//List to which all containment entities are added
		ArrayList<EntityInterface> containmentEntitiesList = new ArrayList<EntityInterface>();
		Collection<AssociationInterface> associationColl = entity.getAllAssociations();

		//For each association check the association type
		Iterator<AssociationInterface> itr = associationColl.iterator();
		while (itr.hasNext())
		{
			AssociationInterface association = itr.next();
			RoleInterface targetRole = association.getTargetRole();
			if (targetRole.getAssociationsType().getValue().equals(
					Constants.CONTAINTMENT_ASSOCIATION))
			{
				//Then get the target entity and put it in Map  
				EntityInterface targetEntity = association.getTargetEntity();
				containmentEntitiesList.add(targetEntity);
			}
		}
		return containmentEntitiesList;
	}

	/**
	 * This method is made public as it is also invoked from action class
	 * This Method returns all main objects present in IQuery 
	 * @param query
	 * @return queryMainEntityList
	 */

	public static List<EntityInterface> getAllMainObjects(IQuery query)
	{
		IConstraints constraints = query.getConstraints();

		List<EntityInterface> mainEntityList = new ArrayList<EntityInterface>();

		List<EntityInterface> queryMainEntityList = new ArrayList<EntityInterface>();

		//For each Expression in the Query Constraints
		for (IExpression expression : constraints)
		{
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

			mainEntityList = QueryCSMUtil.getAllMainEntities(entity, mainEntityList);
			if (mainEntityList.contains(entity))
			{
				queryMainEntityList.add(entity);
			}

		}
		return queryMainEntityList;
	}

	/**
	 * This method checks if this entity is main Entity or not
	 * @param entity
	 * @param mainEntityList
	 * @return
	 */
	public static boolean checkIfMainObject(EntityInterface entity,
			List<EntityInterface> mainEntityList)
	{
		boolean isMainEntity = false;
		for (int i = 0; i < mainEntityList.size(); i++)
		{
			if (entity.getId().toString().equals(mainEntityList.get(i).getId().toString()))
			{
				isMainEntity = true;
				break;
			}
		}
		return isMainEntity;
	}

	/**
	 * This method adds all containments entities of a main entity to Query 
	 * @param query
	 * @param session
	 */
	public static void addConatinmentObjectsToIquery(IQuery query, HttpSession session)
	{
		IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();

		Map<Integer, HashMap<EntityInterface, Integer>> mainExpEntityExpressionIdMap = getExpressionEntityMap(session);
		//As we can't edit query directly, so we create ClientQueryBuilder object
		queryObject.setQuery(query);

		//Not Constraints are retrieved from query clone as we will be modifying the original Query Instance 
		IConstraints constraints = query.getConstraints();
		Map<Integer, List<EntityInterface>> eachExpressionContainmentMap = (Map<Integer, List<EntityInterface>>) session
				.getAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP);
		if ((eachExpressionContainmentMap != null) && (!eachExpressionContainmentMap.isEmpty()))
		{
			List<Integer> expressionsToAddContainments = (List<Integer>) session
					.getAttribute(Constants.MAIN_EXPRESSION_TO_ADD_CONTAINMENTS);

			//For each Main expression, add the containments
			Set<Integer> mainExpkeySet = eachExpressionContainmentMap.keySet();
			Iterator<Integer> mainExpKeySetItr = mainExpkeySet.iterator();
			while (mainExpKeySetItr.hasNext())
			{
				Integer mainExpId = mainExpKeySetItr.next();

				if (expressionsToAddContainments.contains(mainExpId))
				{
					HashMap<EntityInterface, Integer> entityExpressionIdMap = new HashMap<EntityInterface, Integer>();
					IExpression expression = constraints.getExpression(mainExpId);
					EntityInterface entity = expression.getQueryEntity()
							.getDynamicExtensionsEntity();

					//Add main Exp and expressionId to Map 
					entityExpressionIdMap.put(entity, mainExpId);

					List<EntityInterface> containmentEntitiesList = eachExpressionContainmentMap
							.get(mainExpId);
					addContainmentToIQuery(queryObject, entityExpressionIdMap,
							containmentEntitiesList, mainExpId.intValue());

					//Adding 
					mainExpEntityExpressionIdMap.put(mainExpId, entityExpressionIdMap);
				}
			}
		}
		session.setAttribute(Constants.MAIN_EXPRESSIONS_ENTITY_EXP_ID_MAP,
				mainExpEntityExpressionIdMap);
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	private static Map<Integer, HashMap<EntityInterface, Integer>> getExpressionEntityMap(
			HttpSession session)
	{
		Map<Integer, HashMap<EntityInterface, Integer>> mainExpEntityExpressionIdMap = (Map<Integer, HashMap<EntityInterface, Integer>>) session
				.getAttribute(Constants.MAIN_EXPRESSIONS_ENTITY_EXP_ID_MAP);
		if (mainExpEntityExpressionIdMap == null)
		{
			mainExpEntityExpressionIdMap = new HashMap<Integer, HashMap<EntityInterface, Integer>>();
		}
		return mainExpEntityExpressionIdMap;
	}

	/**
	 * 
	 * @param queryObject
	 * @param entityExpressionIdMap
	 * @param containmentEntitiesList
	 * @param mainExpId
	 */
	private static void addContainmentToIQuery(IClientQueryBuilderInterface queryObject,
			Map<EntityInterface, Integer> entityExpressionIdMap,
			List<EntityInterface> containmentEntitiesList, int mainExpId)
	{
		if ((containmentEntitiesList != null) && (!containmentEntitiesList.isEmpty()))
		{
			addExpressionsToIQuery(queryObject, entityExpressionIdMap, containmentEntitiesList,
					mainExpId);
		}
	}

	/**
	 * 
	 * @param queryObject
	 * @param entityExpressionIdMap
	 * @param containedObjList
	 * @param mainExpId
	 */
	//Made public as this method is also invoked from Action class also
	public static void addExpressionsToIQuery(IClientQueryBuilderInterface queryObject,
			Map<EntityInterface, Integer> entityExpressionIdMap,
			List<EntityInterface> containedObjList, int mainExpId)
	{
		IQuery query = queryObject.getQuery();
		List<IExpression> mainExpChildrenList = getChildrenExpressions(mainExpId, query);

		//By this u got all children of main entity .  

		for (int i = 0; i < containedObjList.size(); i++)
		{
			EntityInterface containmentEntity = containedObjList.get(i);

			//Now check if the containmentEntity is already added in the hierarchy of main expression, if it is present then do not add containment entity

			boolean isToAddContainment = checkIfToAddContainment(mainExpChildrenList,
					containmentEntity, entityExpressionIdMap);

			if (isToAddContainment)
			{
				int expressionId = ((ClientQueryBuilder) queryObject)
						.addExpression(containmentEntity);

				//This expression id is necessary for creating Associations among containment Entities
				entityExpressionIdMap.put(containmentEntity, Integer.valueOf(expressionId));
			}
		}
	}

	/**
	 * This method checks if a entity is to be added to IQuery or not 
	 * @param mainExpChildrenList
	 * @param containmentEntity
	 * @param entityExpressionIdMap
	 * @return
	 */
	private static boolean checkIfToAddContainment(List<IExpression> mainExpChildrenList,
			EntityInterface containmentEntity, Map<EntityInterface, Integer> entityExpressionIdMap)
	{
		boolean isToAddContainment = true;
		for (int j = 0; j < mainExpChildrenList.size(); j++)
		{
			IExpression childExp = mainExpChildrenList.get(j);
			EntityInterface entity = childExp.getQueryEntity().getDynamicExtensionsEntity();
			if (containmentEntity.getId().toString().equals(entity.getId().toString())
					&& childExp.containsRule())
			{
				isToAddContainment = false;
				//Not that map is also modified
				entityExpressionIdMap.put(entity, childExp.getExpressionId());
				break;
			}
		}
		return isToAddContainment;
	}

	/**
	 * This method returns all children expressions of a expression  
	 * @param mainExpId
	 * @param query
	 * @return mainExpChildrenList
	 */
	private static List<IExpression> getChildrenExpressions(int mainExpId, IQuery query)
	{
		IConstraints constraints = query.getConstraints();
		IJoinGraph graph = constraints.getJoinGraph();
		IExpression mainExpression = constraints.getExpression(mainExpId);

		//Got the children of main Expression for which containments are to be added 
		List<IExpression> mainExpChildrenList = graph.getChildrenList(mainExpression);

		//For each child of main Entity, get the further children
		for (int count = 0; count < mainExpChildrenList.size(); count++)
		{
			IExpression childExp = mainExpChildrenList.get(count);
			List<IExpression> childrenList = graph.getChildrenList(childExp);
			if ((childrenList != null) && (!childrenList.isEmpty()))
			{
				mainExpChildrenList.addAll(childrenList);
			}
		}
		return mainExpChildrenList;
	}

	/**
	 * This method add links among parent and children containments for all main entities added
	 * @param eachExpressionParentChildMap
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public static void addLinks(
			Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap,
			HttpSession session,IQuery query)
	{
		//IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		Map<Integer, HashMap<EntityInterface, Integer>> mainExpEntityExpressionIdMap = (Map<Integer, HashMap<EntityInterface, Integer>>) session
				.getAttribute("mainExpEntityExpressionIdMap");

		//HashMap <EntityInterface,Integer>entityExpressionIdMap = (HashMap <EntityInterface,Integer>)session.getAttribute(Constants.ENTITY_EXPRESSION_ID_MAP);
		IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
		queryObject.setQuery(query);

		//No Constraints are retrieved from query clone as we will be modifying the original Query Instance
		addPathFromParentToChild(eachExpressionParentChildMap, queryObject,
				mainExpEntityExpressionIdMap);
	}

	/**
	 * This method add links among parent and children containments
	 * @param eachExpressionParentChildMap
	 * @param queryObject
	 * @param mainExpEntityExpressionIdMap
	 */
	private static void addPathFromParentToChild(
			Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap,
			IClientQueryBuilderInterface queryObject,
			Map<Integer, HashMap<EntityInterface, Integer>> mainExpEntityExpressionIdMap)
	{
		Map<EntityInterface, Integer> entityExpressionIdMap = null;
		Set<Integer> mainExprKeySet = eachExpressionParentChildMap.keySet();
		Iterator<Integer> mainExpsItr = mainExprKeySet.iterator();
		while (mainExpsItr.hasNext())
		{
			Integer mainExpId = mainExpsItr.next();
			HashMap<EntityInterface, List<EntityInterface>> parentChildrenMap = eachExpressionParentChildMap
					.get(mainExpId);

			//Setting the Entities and Expression Id Map
			entityExpressionIdMap = mainExpEntityExpressionIdMap.get(mainExpId);
			Set<EntityInterface> parentEntitySet = parentChildrenMap.keySet();
			Iterator<EntityInterface> itr = parentEntitySet.iterator();
			while (itr.hasNext())
			{
				EntityInterface parentEntity = itr.next();
				List<EntityInterface> childEntitiesList = parentChildrenMap.get(parentEntity);
				Iterator<EntityInterface> childEntityItr = childEntitiesList.iterator();
				while (childEntityItr.hasNext())
				{
					EntityInterface childEntity = childEntityItr.next();
					addPath(parentEntity, childEntity, queryObject, entityExpressionIdMap);
				}
			}
		}
	}

	/**
	 * This method add path among two entities
	 * @param parentEntity
	 * @param childEntity
	 * @param queryObject
	 * @param entityExpressionIdMap
	 */
	//This method is made public as it is also invoked from action class
	public static void addPath(EntityInterface parentEntity, EntityInterface childEntity,
			IClientQueryBuilderInterface queryObject,
			Map<EntityInterface, Integer> entityExpressionIdMap)
	{
		Map<AmbiguityObject, List<IPath>> map = null;
		AmbiguityObject ambiguityObject = null;
		ambiguityObject = new AmbiguityObject(parentEntity, childEntity);
		IPathFinder pathFinder = new CommonPathFinder();
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject, pathFinder);
		map = resolveAmbigity.getPathsForAllAmbiguities();
		List<IPath> pathList = map.get(ambiguityObject);
		int parentExpressionId = entityExpressionIdMap.get(parentEntity).intValue();
		int childExpressionId = entityExpressionIdMap.get(childEntity).intValue();
		if (!queryObject.isPathCreatesCyclicGraph(parentExpressionId, childExpressionId, pathList
				.get(0)))
		{
			linkTwoNodes(parentExpressionId, childExpressionId, pathList.get(0), queryObject);
		}
	}

	/**
	 * This method add path among two entities
	 * @param parentExpressionId
	 * @param childExpressionId
	 * @param path
	 * @param queryObject
	 */

	//This method is marked public as it is also invoked from DefineSearchResultsView action class 
	public static void linkTwoNodes(int parentExpressionId, int childExpressionId,
			final IPath path, IClientQueryBuilderInterface queryObject)
	{
		try
		{
			//Adding the link between the two entities 
			List<Integer> intermediateExpressions = queryObject.addPath(parentExpressionId,
					childExpressionId, path);
			PathLink link = new PathLink();
			link.setAssociationExpressions(intermediateExpressions);
			link.setSourceExpressionId(parentExpressionId);
			link.setDestinationExpressionId(childExpressionId);
			link.setPath(path);
			updateQueryObject(link, parentExpressionId, queryObject);
		}
		catch (CyclicException e)
		{
			logger.trace(e);
		}
	}

	/**
	 * This method updates IQuery for added path among two entities  
	 * @param link
	 * @param parentExpressionId
	 * @param queryObject
	 */
	private static void updateQueryObject(PathLink link, int parentExpressionId,
			IClientQueryBuilderInterface queryObject)
	{
		String operator = "";
		int destId = link.getLogicalConnectorExpressionId();
		queryObject.setLogicalConnector(parentExpressionId, destId,
				edu.wustl.cab2b.client.ui.query.Utility.getLogicalOperator(operator), false);
	}
}