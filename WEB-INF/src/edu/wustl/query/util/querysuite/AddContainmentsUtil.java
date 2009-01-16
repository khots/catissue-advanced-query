
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;

/**
 * 
 * @author Baljeet Singh
 *
 */
public abstract class AddContainmentsUtil
{
   private static org.apache.log4j.Logger logger = Logger.getLogger(IQueryUpdationUtil.class);

	/**
	 * This method updates IQuery for containments of all main Entities present in IQuery 
	 * @param session
	 * @param query
	 */
	public static void updateIQueryForContainments(HttpSession session, IQuery query)
	{
		if (query != null)
		{
			Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap = IQueryUpdationUtil
					.getAllConatainmentObjects(query, session,false);

			//Update the IQuery with containment objects......add only those containment objects which are not present in IQuery
			IQueryUpdationUtil.addConatinmentObjectsToIquery(query, session);

			//Add the link/association among parent and containment entities
			IQueryUpdationUtil.addLinks(eachExpressionParentChildMap, session);

		}
	}

	/**
	 * This method updates IQuery for containments of a main Entity present in IQuery 
	 * @param session
	 * @param query
	 * @param entityId
	 */
	public static void updateIQueryForContainments(HttpSession session, IQuery query,
			String entityId)
	{
		EntityInterface entity = EntityCache.getCache().getEntityById(Long.valueOf(entityId));

		//Get the Root entity of the IQuery
		QueryDetails queryDetailsObject = new QueryDetails(session);
		OutputTreeDataNode rootSelectedObject = queryDetailsObject.getRootOutputTreeNodeList().get(
				0);

		//Check if the path exists between Root entity of the IQuery and main Entity added
		List<IPath> pathsList = getPathList(entity, rootSelectedObject);
		if (pathsList.isEmpty())
		{
			logger.info("There exists no path between root entity and main entity added");
		}
		else
		{
			IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
			Map<EntityInterface, Integer> entityExpressionIdMap = addContainmentsForAddedEntity(
					session, query, entity, queryObject);

			//Now add links Among Root entity of the IQuery and all main entities added on Define Results View Page
			List<EntityInterface> mainEntityList = (List<EntityInterface>) session
					.getAttribute(Constants.MAIN_ENTITY_LIST);
			if ((mainEntityList != null) && (!mainEntityList.contains(entity)))
			{
				mainEntityList.add(entity);
			}
			int rootExpressionId = rootSelectedObject.getExpressionId();
			int mainEntityExpId = entityExpressionIdMap.get(entity);
			IQueryUpdationUtil.linkTwoNodes(rootExpressionId, mainEntityExpId, pathsList.get(0),
					queryObject);
		}
	}

	
	/**
	 * This method returns the list of paths among root entity of IQuery and
	 * main entity added  
	 * @param entity
	 * @param rootSelectedObject
	 * @return
	 */
	private static List<IPath> getPathList(EntityInterface entity,
			OutputTreeDataNode rootSelectedObject)
	{
		List<IPath> pathsList;
		IOutputEntity outputEntity = rootSelectedObject.getOutputEntity();
		EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
		IPathFinder pathFinder = new CommonPathFinder();
		AmbiguityObject ambiguityObject = new AmbiguityObject(rootEntity, entity);
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject, pathFinder);

		Map<AmbiguityObject, List<IPath>> map = resolveAmbigity.getPathsForAllAmbiguities();
		pathsList = map.get(ambiguityObject);
		return pathsList;
	}

	/**
	 * This method updates IQuery for containments of added main Entity
	 * @param session
	 * @param query
	 * @param entity
	 * @param queryObject
	 * @return
	 */
	private static Map<EntityInterface, Integer> addContainmentsForAddedEntity(HttpSession session,
			IQuery query, EntityInterface entity, IClientQueryBuilderInterface queryObject)
	{
		queryObject.setQuery(query);
		int expressionId = ((ClientQueryBuilder) queryObject).addExpression(entity);

		//This will update the all add Limit Expressions List to add containments
		updateAddLimitExpressionsList(session, expressionId);

		//Get the containments of main Entity Added
		Map<Integer, HashMap<EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap = IQueryUpdationUtil
				.getAllConatainmentObjects(query, session,false);

		//Now add only the containments of main Entity added 
		HashMap<EntityInterface, Integer> entityExpressionIdMap = new HashMap<EntityInterface, Integer>();
		entityExpressionIdMap.put(entity, expressionId);

		//Get the map containing list of all containments of each main expression
		Map<Integer, List<EntityInterface>> eachExpressionContainmentMap = (Map<Integer, List<EntityInterface>>) session
				.getAttribute("mainEntityExpressionsMap");

		//Get the containment list if main entity added
		List<EntityInterface> containmentEntitiesList = eachExpressionContainmentMap
				.get(expressionId);

		//Add the containments to iQuery
		IQueryUpdationUtil.addExpressionsToIQuery(queryObject, entityExpressionIdMap,
				containmentEntitiesList, expressionId);

		//Now update the mainExpEntityExpressionIdMap for Main Entity Added
		Map<Integer, HashMap<EntityInterface, Integer>> mainExpEntityExpressionIdMap = (Map<Integer, HashMap<EntityInterface, Integer>>) session
				.getAttribute("mainExpEntityExpressionIdMap");
		mainExpEntityExpressionIdMap.put(expressionId, entityExpressionIdMap);

		//Now add link/Associations among parent children containments
		HashMap<EntityInterface, List<EntityInterface>> parentChildrenMap = eachExpressionParentChildMap
				.get(expressionId);

		//We can use above entityExpressionIdMap directly 
		entityExpressionIdMap = mainExpEntityExpressionIdMap.get(expressionId);
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
				IQueryUpdationUtil.addPath(parentEntity, childEntity, queryObject,
						entityExpressionIdMap);
			}
		}
		return entityExpressionIdMap;
	}

	/**
	 * This method updates list of main expressions added from "ADD LIMIT" page
	 * @param session
	 * @param expressionId
	 */
	@SuppressWarnings("unchecked")
	private static void updateAddLimitExpressionsList(HttpSession session, int expressionId)
	{
		//Get the list of all expression added from ADD Limit on DAG
		List<Integer> expressionIdsList = (List<Integer>) session
				.getAttribute("allLimitExpressionIds");

		//The null case will be only when we have saved query 
		if (expressionIdsList == null)
		{
			expressionIdsList = new ArrayList<Integer>();
		}
		//Add the main Entity to List
		expressionIdsList.add(Integer.valueOf(expressionId));
	}

}
