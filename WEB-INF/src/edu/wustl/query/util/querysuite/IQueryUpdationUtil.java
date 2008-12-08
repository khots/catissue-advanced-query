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
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;


public abstract class IQueryUpdationUtil
{
	private static org.apache.log4j.Logger logger =Logger.getLogger(IQueryUpdationUtil.class);
	public static Map<EntityInterface, List<EntityInterface>> getAllConatainmentObjects(IQuery query,HttpSession session)
	{
		HashMap <EntityInterface, List<EntityInterface>> containmentMap = new HashMap<EntityInterface, List<EntityInterface>>();
		HashMap <EntityInterface, List<EntityInterface>> partentChildEntityMap =  new HashMap<EntityInterface, List<EntityInterface>>(); 
		HashMap <EntityInterface,Integer>entityExpressionIdMap = new HashMap<EntityInterface, Integer>();
		List<EntityInterface> mainEntityList = getAllMainObjects(query);
		IConstraints constraints = query.getConstraints();
		for (IExpression expression : constraints)
		{
			ArrayList <EntityInterface> mainEntityContainmentList = new ArrayList<EntityInterface>(); 

			//Get the Entity associated with Expression
			EntityInterface mainEntity = expression.getQueryEntity().getDynamicExtensionsEntity();
			entityExpressionIdMap.put(mainEntity, expression.getExpressionId());
			
			//Check if the Entity present as expression and not a main object
			boolean ifMainObject = checkIfMainObject(mainEntity, mainEntityList); 
			if(ifMainObject)
			{
				getMainEntityContainments(partentChildEntityMap, mainEntityContainmentList,mainEntity);
			}
			if((mainEntityContainmentList != null) && (!mainEntityContainmentList.isEmpty()))
			{
				containmentMap.put(mainEntity, mainEntityContainmentList);
			}
		}
		session.setAttribute(Constants.CONTAINMENT_OBJECTS_MAP, containmentMap);
		session.setAttribute(Constants.ENTITY_EXPRESSION_ID_MAP,entityExpressionIdMap);
		return partentChildEntityMap;
	}

	private static void getMainEntityContainments(Map<EntityInterface, List<EntityInterface>> partentChildEntityMap,List<EntityInterface> mainEntityContainmentList, EntityInterface mainEntity)
	{
		//For each Entity, get all Associations
		List <EntityInterface> list = getContainmentAssociations(mainEntity);
		List <EntityInterface> uniqueEntityList = new ArrayList<EntityInterface>(); 
		Iterator< EntityInterface> itr = list.iterator();
		while(itr.hasNext())
		{
			EntityInterface entity = itr.next();
			if(!mainEntityContainmentList.contains(entity))
			{
				mainEntityContainmentList.add(entity);
				uniqueEntityList.add(entity);
			}
		}
		//mainEntityContainmentList.addAll(list);
		partentChildEntityMap.put(mainEntity, uniqueEntityList);
		
		//Now for each containment entity, get further containments till it ends
		if(!mainEntityContainmentList.isEmpty())
		{
			getChildEntityContainment(partentChildEntityMap, mainEntityContainmentList);
		}
	}

	private static void getChildEntityContainment(Map<EntityInterface, List<EntityInterface>> partentChildEntityMap,List<EntityInterface> mainEntityContainmentList)
	{
		List<EntityInterface> list;
		int count=0;
		while(count<mainEntityContainmentList.size())
		{
			EntityInterface containmentEntity = mainEntityContainmentList.get(count);
			list = getContainmentAssociations(containmentEntity);
			List <EntityInterface> uniqueEntityList = new ArrayList<EntityInterface>(); 
			Iterator< EntityInterface> itr = list.iterator();
			while(itr.hasNext())
			{
				EntityInterface entity = itr.next();
				if(!mainEntityContainmentList.contains(entity))
				{
					mainEntityContainmentList.add(entity);
					uniqueEntityList.add(entity);
				}
			}
				//mainEntityContainmentList.addAll(list);
			partentChildEntityMap.put(containmentEntity, uniqueEntityList);
			count++;
		}
	}
	
	private static List<EntityInterface> getContainmentAssociations(EntityInterface entity)
	{ 
		//List to which all containment entities are added
		ArrayList <EntityInterface> containmentEntitiesList = new ArrayList<EntityInterface>();
		Collection <AssociationInterface>associationColl = entity.getAllAssociations();
		//For each association check the association type
		Iterator<AssociationInterface> itr = associationColl.iterator();
		while(itr.hasNext())
		{
			AssociationInterface association = itr.next();
	        RoleInterface targetRole = association.getTargetRole();
	        if(targetRole.getAssociationsType().getValue().equals(
					Constants.CONTAINTMENT_ASSOCIATION))
	        {
	        	//Then get the target entity and put it in Map  
	        	EntityInterface targetEntity = association.getTargetEntity();
	        	containmentEntitiesList.add(targetEntity);
	        }
		}
	   return containmentEntitiesList;	
	}
	
	private static List<EntityInterface> getAllMainObjects(IQuery query)
	{
		IConstraints constraints = query.getConstraints();

		List<EntityInterface> mainEntityList = new ArrayList<EntityInterface>();
		//For each Expression in the Query Constraints
		for (IExpression expression : constraints)
		{
			EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

		    mainEntityList = QueryCSMUtil.getAllMainEntities(entity,mainEntityList);

		}
	  return mainEntityList;	
	}
	
	private static boolean checkIfMainObject(EntityInterface entity, List<EntityInterface> mainEntityList)
	{
		boolean isMainEntity = false;
        for(int i=0; i<mainEntityList.size(); i++)
        {
        	if(entity.getId().toString().equals(mainEntityList.get(i).getId().toString()))
        	{
        		isMainEntity = true;
        		break;
        	}
        }
		return isMainEntity;
	}
	
	public static void addConatinmentObjectsToIquery(IQuery query,HttpSession session,IQuery queryClone)
	{
		IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
		HashMap <EntityInterface,Integer>entityExpressionIdMap = (HashMap <EntityInterface,Integer>)session.getAttribute(Constants.ENTITY_EXPRESSION_ID_MAP);

		//As we can't edit query directly, so we create ClientQueryBuilder object
		queryObject.setQuery(query);
		
		//Not Constraints are retrieved from query clone as we will be modifying the original Query Instance 
		IConstraints constraints = queryClone.getConstraints();
		HashMap <EntityInterface, List<EntityInterface>> containmentMap  = (HashMap<EntityInterface, List<EntityInterface>>)session.getAttribute(Constants.CONTAINMENT_OBJECTS_MAP);
		if((containmentMap != null) && (!containmentMap.isEmpty()))
		{
			addContainmentToIQuery(queryObject, entityExpressionIdMap, constraints, containmentMap);
		}
		session.setAttribute(Constants.QUERY_OBJECT,query);
		session.setAttribute(Constants.ENTITY_EXPRESSION_ID_MAP,entityExpressionIdMap);
	}

	private static void addContainmentToIQuery(IClientQueryBuilderInterface queryObject,Map<EntityInterface, Integer> entityExpressionIdMap, IConstraints constraints,Map<EntityInterface, List<EntityInterface>> containmentMap)
	{
		Set <EntityInterface> keySet = containmentMap.keySet();
		Iterator<EntityInterface> keySetItr = keySet.iterator();
		while(keySetItr.hasNext())
		{
			EntityInterface mainEntityKey = keySetItr.next();
			List<EntityInterface> containedObjList = 	containmentMap.get(mainEntityKey);
			if((containedObjList != null) && (!containedObjList.isEmpty()))
			{
				addExpressionsToIQuery(queryObject, entityExpressionIdMap, constraints,
						containedObjList);
			}
		}
	}

	private static void addExpressionsToIQuery(IClientQueryBuilderInterface queryObject,Map<EntityInterface, Integer> entityExpressionIdMap, IConstraints constraints,List<EntityInterface> containedObjList)
	{
		for(int i=0; i<containedObjList.size() ; i++)
		{
			EntityInterface containmentEntity = containedObjList.get(i);
			if(!isEnityPresentInQuery(constraints,containmentEntity))
			{
				//Only then add as constraint to ClientQueryBuilder object
				int expressionId = ((ClientQueryBuilder) queryObject).addExpression(containmentEntity);
				//This expression id is necessary for creating Associations among containment Entities
				entityExpressionIdMap.put(containmentEntity, Integer.valueOf(expressionId));
			}
		}
	}
 
    private static boolean isEnityPresentInQuery(IConstraints constraints, EntityInterface containmentEntity)
    {
		boolean isEntityPresent = false; 
		for (IExpression expression : constraints)
		{
			EntityInterface queryEntity = expression.getQueryEntity().getDynamicExtensionsEntity();
		    if(queryEntity.getId().toString().equalsIgnoreCase(containmentEntity.getId().toString()))
		    {
		    	isEntityPresent = true;
		    	break;
		    }
		}	
      return isEntityPresent; 
    }
    
    public static void addLinks(Map <EntityInterface, List<EntityInterface>>containedObjectsMap,HttpSession session, IQuery queryClone)
    {
    	IQuery query = (IQuery)session.getAttribute(Constants.QUERY_OBJECT);
    	HashMap <EntityInterface,Integer>entityExpressionIdMap = (HashMap <EntityInterface,Integer>)session.getAttribute(Constants.ENTITY_EXPRESSION_ID_MAP);
    	IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
    	queryObject.setQuery(query);
    		
    	//Not Constraints are retrieved from query clone as we will be modifying the original Query Instance
    	addPathFromParentToChild(containedObjectsMap, queryClone, entityExpressionIdMap,queryObject);
    }
	private static void addPathFromParentToChild(Map<EntityInterface, List<EntityInterface>> containedObjectsMap,IQuery queryClone, Map<EntityInterface, Integer> entityExpressionIdMap,IClientQueryBuilderInterface queryObject)
	{
		IConstraints constraints = queryClone.getConstraints();
    	Set <EntityInterface> parentEntitySet = containedObjectsMap.keySet();
	    Iterator<EntityInterface> itr = parentEntitySet.iterator();
	    while(itr.hasNext())
	    {
	    	EntityInterface parentEntity = itr.next();
	    	List<EntityInterface> childEntitiesList = containedObjectsMap.get(parentEntity);
	    	Iterator<EntityInterface> childEntityItr = childEntitiesList.iterator();
	    	while(childEntityItr.hasNext())
	    	{
	    		EntityInterface childEntity = childEntityItr.next();
	    		if(checkIfToAddPath(constraints,parentEntity,childEntity))
	    		{
	    			addPath(parentEntity,childEntity, queryObject,entityExpressionIdMap);
	    		}	
	    	}
	    }
	}
	private static boolean checkIfToAddPath(IConstraints constraints, EntityInterface parentEntity, EntityInterface childEntity)
	{
		boolean isChildEntityPresent = isEnityPresentInQuery(constraints,childEntity);
		boolean isParentEntityPresent = isEnityPresentInQuery(constraints,parentEntity);
		boolean  ifPathToAddPath =  false;
		if((isParentEntityPresent) && (isChildEntityPresent))
		{
			ifPathToAddPath= false;
		}
		else
		{
			ifPathToAddPath = true;	
		}
		return ifPathToAddPath;
	}
    
    private static void addPath(EntityInterface parentEntity, EntityInterface childEntity, IClientQueryBuilderInterface queryObject,Map <EntityInterface,Integer>entityExpressionIdMap)
    {
    	Map<AmbiguityObject, List<IPath>> map = null;
		AmbiguityObject ambiguityObject = null;
		ambiguityObject = new AmbiguityObject(parentEntity,childEntity);
		IPathFinder pathFinder = new CommonPathFinder();
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject,pathFinder);
		map = resolveAmbigity.getPathsForAllAmbiguities();
		List<IPath> pathList = map.get(ambiguityObject);
		int parentExpressionId = entityExpressionIdMap.get(parentEntity).intValue();
		int childExpressionId = entityExpressionIdMap.get(childEntity).intValue();
		if (!queryObject.isPathCreatesCyclicGraph(parentExpressionId, childExpressionId, pathList.get(0)))
		{
			linkTwoNodes(parentExpressionId, childExpressionId, pathList.get(0),queryObject);
		}
    }
    private static void linkTwoNodes(int parentExpressionId, int childExpressionId, final IPath path,IClientQueryBuilderInterface queryObject)
    {
    	try
    	{
        	//Adding the link between the two entities 
        	List<Integer> intermediateExpressions = queryObject.addPath(parentExpressionId, childExpressionId,path);
        	PathLink link = new PathLink();
			link.setAssociationExpressions(intermediateExpressions);
			link.setSourceExpressionId(parentExpressionId);
			link.setDestinationExpressionId(childExpressionId);
			link.setPath(path);
			updateQueryObject(link, parentExpressionId, queryObject);
    	}
    	catch(CyclicException e)
    	{
    		logger.trace(e);
    	}
    }
    
    private static void updateQueryObject(PathLink link, int parentExpressionId,IClientQueryBuilderInterface queryObject)
    {
    	String operator = "";
    	int destId = link.getLogicalConnectorExpressionId();
    	queryObject.setLogicalConnector(parentExpressionId, destId, edu.wustl.cab2b.client.ui.query.Utility.getLogicalOperator(operator), false);
    }
}
