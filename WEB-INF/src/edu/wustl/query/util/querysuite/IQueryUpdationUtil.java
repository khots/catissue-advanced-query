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
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;


public abstract class IQueryUpdationUtil
{
	private static org.apache.log4j.Logger logger =Logger.getLogger(IQueryUpdationUtil.class);
	
	
	private static boolean checkIfToAddContainmnets(int mainExpId,Set<Integer> keySet)
	{
		boolean isToAddContainments = true;
        Iterator<Integer> itr = keySet.iterator();
        while(itr.hasNext())
        {
        	int expId = itr.next().intValue();
        	if(mainExpId == expId)
        	{
        		isToAddContainments = false;
                break;
        	}
        }
	   return isToAddContainments;
	}
	
	
	public static Map<Integer, HashMap <EntityInterface, List<EntityInterface>>> getAllConatainmentObjects(IQuery query,HttpSession session)
	{
		//HashMap <EntityInterface, List<EntityInterface>> containmentMap = new HashMap<EntityInterface, List<EntityInterface>>();
		//HashMap <EntityInterface,Integer>entityExpressionIdMap = new HashMap<EntityInterface, Integer>();
		
		Map<Integer, HashMap <EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap = new HashMap<Integer, HashMap<EntityInterface,List<EntityInterface>>> (); 
		
		List<Integer> expressionIdsList =  (List<Integer>)session.getAttribute(Constants.ALL_ADD_LIMIT_EXPRESSIONS);
		List <Integer> mainExpressionIds = new ArrayList<Integer>();
		
		Map <Integer,List<EntityInterface>> eachExpressionContainmentMap = (HashMap<Integer,List<EntityInterface>>)session.getAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP);
		if(eachExpressionContainmentMap == null)
		{
			eachExpressionContainmentMap = new HashMap<Integer,List<EntityInterface>>();
		}
		
		//Get the list if all main Entities present in main query
		List<EntityInterface> mainEntityList = getAllMainObjects(query);
		
		//Find out all Main expressions present in IQuery
		IConstraints constraints = query.getConstraints();
		Iterator<Integer> itr = expressionIdsList.iterator();
		while(itr.hasNext())
		{
		    int expId = itr.next().intValue();
		    IExpression expression = constraints.getExpression(expId);
		    EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
			
			//Check if this entity is main Entity or not
			boolean ifMainObject = checkIfMainObject(entity, mainEntityList); 
			if(ifMainObject)
			{
				mainExpressionIds.add(Integer.valueOf(expression.getExpressionId()));
			}
		}
		
		//Here we got the list of all main  Expressions, now we need to find out for expression we need to add Containments
		 List <Integer> expressionsToAddContainments = new ArrayList<Integer>();
		 if(eachExpressionContainmentMap == null || eachExpressionContainmentMap.isEmpty())
		 {
			 //This is first time case
			 expressionsToAddContainments.addAll(mainExpressionIds);
		 }
		 else
		 {
			 //This is the case of Previous ->Next, by this time containments of previous main entities are added  
			 Iterator<Integer> mainExpsItr = mainExpressionIds.iterator();
			 while(mainExpsItr.hasNext())
			 {
				 int mainExpId =  mainExpsItr.next().intValue();
				 boolean isToAddContainments = checkIfToAddContainmnets(mainExpId,eachExpressionContainmentMap.keySet());
				 if(isToAddContainments)
				 {
					 expressionsToAddContainments.add((Integer.valueOf(mainExpId)));
				 }
			 }
		 }
		
		 //For each expression id in the  expressionsToAddContainments, get It's Containments
         Iterator<Integer> expsToAddItr =  expressionsToAddContainments.iterator();
         while(expsToAddItr.hasNext())
         {
        	int expId = expsToAddItr.next().intValue();
 		    IExpression expression = constraints.getExpression(expId);
 		    EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
 		    HashMap <EntityInterface, List<EntityInterface>> partentChildEntityMap =  new HashMap<EntityInterface, List<EntityInterface>>();
 		    ArrayList <EntityInterface> mainEntityContainmentList = new ArrayList<EntityInterface>(); 
 		    getMainEntityContainments(partentChildEntityMap, mainEntityContainmentList,entity);
           
 		    if((mainEntityContainmentList != null) && (!mainEntityContainmentList.isEmpty()))
			{
				eachExpressionContainmentMap.put(Integer.valueOf(expId),mainEntityContainmentList);
			}
           
 		    eachExpressionParentChildMap.put(Integer.valueOf(expId),partentChildEntityMap);
         }
		session.setAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP,eachExpressionContainmentMap);
		session.setAttribute(Constants.MAIN_EXPRESSION_TO_ADD_CONTAINMENTS,expressionsToAddContainments);
        session.setAttribute(Constants.MAIN_ENTITY_LIST, mainEntityList);
		
        //return partentChildEntityMap;
        return eachExpressionParentChildMap;   
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
			if(!uniqueEntityList.isEmpty())
			{
				partentChildEntityMap.put(containmentEntity, uniqueEntityList);
			}
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
	        if(targetRole.getAssociationsType().getValue().equals(Constants.CONTAINTMENT_ASSOCIATION))
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
	 * @param query
	 * @return
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

		    mainEntityList = QueryCSMUtil.getAllMainEntities(entity,mainEntityList);
		    if(mainEntityList.contains(entity))
		     queryMainEntityList.add(entity);
		    

		}
	  return queryMainEntityList;	
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
	
	public static void addConatinmentObjectsToIquery(IQuery query,HttpSession session)
	{
		IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
		
		Map <Integer,HashMap <EntityInterface,Integer>> mainExpEntityExpressionIdMap = (Map <Integer,HashMap <EntityInterface,Integer>>)session.getAttribute(Constants.MAIN_EXPRESSIONS_ENTITY_EXP_ID_MAP);
		if(mainExpEntityExpressionIdMap == null)
		{
			mainExpEntityExpressionIdMap = new HashMap<Integer, HashMap<EntityInterface,Integer>>();
		}
		//As we can't edit query directly, so we create ClientQueryBuilder object
		queryObject.setQuery(query);
		
		//Not Constraints are retrieved from query clone as we will be modifying the original Query Instance 
		IConstraints constraints = query.getConstraints();
		//HashMap <EntityInterface, List<EntityInterface>> containmentMap  = (HashMap<EntityInterface, List<EntityInterface>>)session.getAttribute(Constants.CONTAINMENT_OBJECTS_MAP);
		Map <Integer,List<EntityInterface>> eachExpressionContainmentMap =	 (Map <Integer,List<EntityInterface>>)session.getAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP);
		
		//List <Integer> expressionsToAddContainments = (List <Integer>)session.getAttribute("expressionsToAddContainments");
		
		if((eachExpressionContainmentMap != null) && (!eachExpressionContainmentMap.isEmpty()))
		{
			List <Integer> expressionsToAddContainments = (List <Integer>)session.getAttribute(Constants.MAIN_EXPRESSION_TO_ADD_CONTAINMENTS);
			
			//For each Main expression, add the containments
			Set <Integer> mainExpkeySet =  eachExpressionContainmentMap.keySet();
			Iterator< Integer> mainExpKeySetItr = mainExpkeySet.iterator();
			while(mainExpKeySetItr.hasNext())
			{
				Integer mainExpId = mainExpKeySetItr.next();
				
				if(expressionsToAddContainments.contains(mainExpId))
				{
					HashMap <EntityInterface,Integer>entityExpressionIdMap = new HashMap<EntityInterface, Integer>();
					
					IExpression expression = constraints.getExpression(mainExpId);
				    EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

				    //Add main Exp and expressionId to Map 
				    entityExpressionIdMap.put(entity,mainExpId);
					
					List <EntityInterface> containmentEntitiesList = eachExpressionContainmentMap.get(mainExpId); 
				    addContainmentToIQuery(queryObject, entityExpressionIdMap, constraints, containmentEntitiesList);				
				
				    //Adding 
				    mainExpEntityExpressionIdMap.put(mainExpId, entityExpressionIdMap);
				}
			}
		}
		session.setAttribute(Constants.MAIN_EXPRESSIONS_ENTITY_EXP_ID_MAP,mainExpEntityExpressionIdMap);
	}
	
/*	private static boolean isToAddMainExpression(Integer mainExpId,List <Integer> expressionsToAddContainments)
	{
		 boolean isToAddMainExp = false;
		 Iterator<Integer> itr = expressionsToAddContainments.iterator();
		 while(itr.hasNext())
		 {
			 int expId = itr.next().intValue();
			 if(mainExpId.intValue()==expId)
			 {
				 isToAddMainExp = true;
				 break;
			 }
		 }
		 return isToAddMainExp;
	}*/

	private static void addContainmentToIQuery(IClientQueryBuilderInterface queryObject,Map<EntityInterface, Integer> entityExpressionIdMap, IConstraints constraints,List <EntityInterface> containmentEntitiesList)
	{
		if((containmentEntitiesList != null) && (!containmentEntitiesList.isEmpty()))
		{
			addExpressionsToIQuery(queryObject, entityExpressionIdMap, constraints,
					containmentEntitiesList);
		}
	}

	
	//Made public as this method is also invoked from Action class also
	public static void addExpressionsToIQuery(IClientQueryBuilderInterface queryObject,Map<EntityInterface, Integer> entityExpressionIdMap, IConstraints constraints,List<EntityInterface> containedObjList)
	{
		for(int i=0; i<containedObjList.size() ; i++)
		{
			EntityInterface containmentEntity = containedObjList.get(i);
				
			//No need to check if Entity is present in query as as we are adding only containment only for those main exps which are not present in query 
			int expressionId = ((ClientQueryBuilder) queryObject).addExpression(containmentEntity);
			
			//This expression id is necessary for creating Associations among containment Entities
			entityExpressionIdMap.put(containmentEntity, Integer.valueOf(expressionId));
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
    
    public static void addLinks(Map<Integer, HashMap <EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap,HttpSession session)
    {
    	IQuery query = (IQuery)session.getAttribute(Constants.QUERY_OBJECT);
    	Map <Integer,HashMap <EntityInterface,Integer>> mainExpEntityExpressionIdMap  = (Map <Integer,HashMap <EntityInterface,Integer>>)session.getAttribute("mainExpEntityExpressionIdMap");
    	
    	//HashMap <EntityInterface,Integer>entityExpressionIdMap = (HashMap <EntityInterface,Integer>)session.getAttribute(Constants.ENTITY_EXPRESSION_ID_MAP);
    	IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
    	queryObject.setQuery(query);
    		
    	//No Constraints are retrieved from query clone as we will be modifying the original Query Instance
    	addPathFromParentToChild(eachExpressionParentChildMap,queryObject,mainExpEntityExpressionIdMap);
    }
	private static void addPathFromParentToChild(Map<Integer, HashMap <EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap,IClientQueryBuilderInterface queryObject,Map <Integer,HashMap <EntityInterface,Integer>> mainExpEntityExpressionIdMap)
	{
        Map<EntityInterface,Integer> entityExpressionIdMap = null;
		Set <Integer> mainExprKeySet = eachExpressionParentChildMap.keySet();
		Iterator<Integer>  mainExpsItr = mainExprKeySet.iterator();
		while(mainExpsItr.hasNext())
		{
			Integer mainExpId = mainExpsItr.next();
			HashMap <EntityInterface, List<EntityInterface>> parentChildrenMap = eachExpressionParentChildMap.get(mainExpId);
			
			//Setting the Entities and Expression Id Map
			entityExpressionIdMap = mainExpEntityExpressionIdMap.get(mainExpId);
			Set <EntityInterface> parentEntitySet = parentChildrenMap.keySet();
			Iterator<EntityInterface> itr = parentEntitySet.iterator();
			while(itr.hasNext())
			{
				EntityInterface parentEntity = itr.next();
				List<EntityInterface> childEntitiesList = parentChildrenMap.get(parentEntity);
				Iterator<EntityInterface> childEntityItr = childEntitiesList.iterator();
				while(childEntityItr.hasNext())
		    	{
		    		EntityInterface childEntity = childEntityItr.next();
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
    
    //This method is made public as it is also invoked from action class
	public static void addPath(EntityInterface parentEntity, EntityInterface childEntity, IClientQueryBuilderInterface queryObject,Map <EntityInterface,Integer>entityExpressionIdMap)
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
    
    /**
     * 
     * @param parentExpressionId
     * @param childExpressionId
     * @param path
     * @param queryObject
     */
    
    //This method is marked public as it is also invoked from DefineSearchResultsView action class 
    public static void linkTwoNodes(int parentExpressionId, int childExpressionId, final IPath path,IClientQueryBuilderInterface queryObject)
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
    
   public static Map <EntityInterface, List<EntityInterface>> getAllContainmentsOfEntity(EntityInterface entity, IQuery query, HttpSession session)
   {
	   HashMap <EntityInterface, List<EntityInterface>> containmentMap = (HashMap <EntityInterface, List<EntityInterface>>)session.getAttribute(Constants.CONTAINMENT_OBJECTS_MAP);
	   if(containmentMap == null)
	   {
		   containmentMap = new HashMap<EntityInterface, List<EntityInterface>>();
	   }
	   HashMap <EntityInterface, List<EntityInterface>> partentChildEntityMap =  new HashMap<EntityInterface, List<EntityInterface>>(); 
	   ArrayList <EntityInterface> mainEntityContainmentList = new ArrayList<EntityInterface>();
	   
	   //Check if Entity present in query....add entity to query and It's containment only if it is not present n query
	   IConstraints constraints = query.getConstraints();
	   boolean isEntityPresent = isEnityPresentInQuery(constraints,entity);
	   if(!isEntityPresent)
	   {	   
	  
		   //This method gives the containment of the Entity as well as it's children in complete hierarchy 
		   getMainEntityContainments(partentChildEntityMap, mainEntityContainmentList,entity);
	   
		   if((mainEntityContainmentList != null) && (!mainEntityContainmentList.isEmpty()))
		   {
			   containmentMap.put(entity, mainEntityContainmentList);
		   }
		 //Set these attributes in session and return the parent child relationship map to create associations among the containments in IQuery  
		session.setAttribute(Constants.CONTAINMENT_OBJECTS_MAP, containmentMap);  
	   }
	   
	   return partentChildEntityMap;
   }
	
}
