package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sun.org.apache.xerces.internal.impl.XMLEntityManager.Entity;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.IUpdateAddLimitUIInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.common.queryengine.Cab2bQuery;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.tree.QueryTreeNodeData;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.QueryModuleSqlUtil;


public class ViewResultsAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		int queryExecutionID = 2; //need to get From request
	 	Long iqueryId = Long.valueOf(255);//need to get From request
	 	IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
				.getValue("app.bizLogicFactory"), "getBizLogic",
				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
	 	//Retrieve the "Get Patient Data" Query depending on the iQuery ID 
	 	final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
				.getName(), Constants.ID, iqueryId);
	 	
	 	IQuery query = null;
		//Retrieve the IQuery from database on the basis of  IQueryId
	 	if (queryList != null && !queryList.isEmpty())
	 	{
	 		query = queryList.get(0);
	 	}

	 	Long queryId = Long.parseLong(request.getParameter("dataQueryId"));
	 	session.setAttribute("dataQueryId", queryId);
	 	//Pass that IQuery to PassTwoXQueryGenerator to parse it and populate the rootNodeOutput list
		PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
		String generatedQuery = passTwoQueryGenerator.generateQuery(query); 
		
		//Get the root out put node list , which gives the root node
		List<OutputTreeDataNode> rootOutputTreeNodeList = passTwoQueryGenerator.getRootOutputTreeNodeList();
		IOutputEntity outputEntity = rootOutputTreeNodeList.get(0).getOutputEntity();
		EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
		
		//Now Create IQuery From Main Entity , adding containments till attributes with tagged values are found
		//Get the parent child map for containment of a  main Entity
		Map<EntityInterface, List<EntityInterface>> partentChildEntityMap = getAllParentChildrenMap(rootEntity); 
		
		//Once u got the parent child map, get the parent child map for tagged entities for results view, and populate the list for tagged entities
		Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = getTaggedEntitiesParentChildMap(partentChildEntityMap);
		
		//Here we get path list from Root Entity to parent of Tagged Entity for results view 
		Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = getPathsMapForTaggedEntity(taggedEntitiesParentChildMap,partentChildEntityMap);
		
		//Now Add all entities related to a tagged entity to IQuery
		query =  formIQuery(rootEntity,eachTaggedEntityPathMap);
		
//		String generatedNewQuery = passTwoQueryGenerator.generateQuery(query); 
		
		List <List<String>> personUPIsList =  IQueryUpdationUtil.getPersonUpis(4);
		
		//This happens something when "Person" is the root entity
		
		Vector<QueryTreeNodeData> treeDataVector = new Vector<QueryTreeNodeData>();
		
		
		//Note : Root node of tree, Query execution id will be the node id  
		String displayName = Utility.getDisplayLabel(rootEntity.getName()) + " (" + personUPIsList.size() + ")";
		QueryTreeNodeData labelNode = getResultsTreeLabelNode(rootEntity,queryExecutionID,displayName);	
		treeDataVector.add(labelNode);
		
	    
		// So now Add Result Node under tree
		Iterator <List<String>> upiListItr = personUPIsList.iterator();
	    while(upiListItr.hasNext())
	    {
	        List<String> upiList = 	upiListItr.next();
	        String personUPI = upiList.get(0);
	        QueryTreeNodeData resultrTreeNode = new QueryTreeNodeData();
	        
	        resultrTreeNode.setIdentifier(personUPI);
	        resultrTreeNode.setObjectName("UPI");
	        
	        displayName = "AAA_AAA" + personUPI;
	        resultrTreeNode.setDisplayName(displayName);
	        
	        //node Id from Person label node is set parent of each node
	        resultrTreeNode.setParentIdentifier(labelNode.getIdentifier().toString());
	        resultrTreeNode.setParentObjectName("");
	        treeDataVector.add(resultrTreeNode);
	    }
		
		//Setting some session attributes
	    
	    //Note, tree no is set hardcoded 
	    Long noOfTrees =  Long.valueOf(1);
	    session.setAttribute(Constants.NO_OF_TREES,noOfTrees);
	    String key = Constants.TREE_DATA + "_" + 0;
	    session.setAttribute(key,treeDataVector);
		return mapping.findForward(Constants.SUCCESS);
	}

	/*private EntityInterface getEntityFromCache(String entityName, Collection<EntityGroupInterface> entityGroups)
	{
		EntityInterface entity = null;
		Iterator<EntityGroupInterface> entityGroupsItr =   entityGroups.iterator();
       	while(entityGroupsItr.hasNext())
       	{
       		EntityGroupInterface entityGroup = entityGroupsItr.next();
       		entity =  entityGroup.getEntityByName(entityName);
       		if(entity != null)
       		{
       			break;
       		}
       	}
       	return entity;
	}*/
	
	private QueryTreeNodeData getResultsTreeLabelNode(EntityInterface labelEntity, int labelNodeId, String displayName)
	{
		String name = labelEntity.getName();
		String nodeId = 0 + "_" + Constants.NULL_ID + Constants.NODE_SEPARATOR + labelNodeId + Constants.UNDERSCORE + Constants.LABEL_TREE_NODE;
		displayName = Constants.TREE_NODE_FONT + displayName + Constants.TREE_NODE_FONT_CLOSE;
		QueryTreeNodeData treeNode = new QueryTreeNodeData();
		treeNode.setIdentifier(nodeId);
		treeNode.setObjectName(name);
		treeNode.setDisplayName(displayName);
		treeNode.setParentIdentifier(Constants.ZERO_ID);
		treeNode.setParentObjectName("");
		return treeNode;
	}
	
	private IPath getIPath(EntityInterface parentEntity,EntityInterface childEntity)
	{
		 //Now Add path among expressions added to query
	    Map<AmbiguityObject, List<IPath>> map = null;
		AmbiguityObject ambiguityObject = null;
		ambiguityObject = new AmbiguityObject(parentEntity, childEntity);
		IPathFinder pathFinder = new CommonPathFinder();
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject, pathFinder);
		map = resolveAmbigity.getPathsForAllAmbiguities();
		List<IPath> pathList = map.get(ambiguityObject);
		return pathList.get(0);
	}
	
	private Map<EntityInterface, List<EntityInterface>> getAllParentChildrenMap(EntityInterface mainEntity)
	{
		HashMap<EntityInterface, List<EntityInterface>> partentChildEntityMap = new HashMap<EntityInterface, List<EntityInterface>>();
		ArrayList<EntityInterface> mainEntityContainmentList = new ArrayList<EntityInterface>();
		QueryAddContainmentsUtil.getMainEntityContainments(partentChildEntityMap, mainEntityContainmentList, mainEntity);
		return partentChildEntityMap;
	}
	
	private Map<EntityInterface, List<EntityInterface>> getTaggedEntitiesParentChildMap(Map<EntityInterface, List<EntityInterface>> partentChildEntityMap)
	{
		
		Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = new HashMap<EntityInterface, List<EntityInterface>>();
		Set <EntityInterface> keySet =partentChildEntityMap.keySet();
	    for(EntityInterface keyEntity : keySet)
	    {
	    	List <EntityInterface> taggedEntitiesListForMainEntity = new ArrayList<EntityInterface>(); 
	    	List<EntityInterface> childrenEntities = partentChildEntityMap.get(keyEntity);
	     	for(EntityInterface childEntity : childrenEntities)
	     	{
	     		if(edu.wustl.query.util.global.Utility.istagPresent(childEntity,"resultview"))
		    	{
	     			taggedEntitiesListForMainEntity.add(childEntity);
		    	}
	     	}
	     	taggedEntitiesParentChildMap.put(keyEntity, taggedEntitiesListForMainEntity);
	    }
	    return taggedEntitiesParentChildMap;
	}
	
	private Map<EntityInterface, List<EntityInterface>> getPathsMapForTaggedEntity(Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap,Map<EntityInterface, List<EntityInterface>> parentChilddrenMap)
	{
		Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = new HashMap<EntityInterface, List<EntityInterface>>();
		Set <EntityInterface> keySet =  taggedEntitiesParentChildMap.keySet();
		for(EntityInterface entity : keySet)
		{
			List <EntityInterface> taggedEntityList = taggedEntitiesParentChildMap.get(entity);
            if(taggedEntityList != null && !taggedEntityList.isEmpty())
            {
            	for(EntityInterface taggedEntity : taggedEntityList)
            	{
            		List <EntityInterface> pathList = new ArrayList<EntityInterface>();
            		pathList.add(taggedEntity);
            		getPathListForTaggedEntity(taggedEntity,pathList,parentChilddrenMap);
            		eachTaggedEntityPathMap.put(taggedEntity, pathList);
            	}
            }
		}
		return eachTaggedEntityPathMap;
	}
 	private List<EntityInterface> getPathListForTaggedEntity(EntityInterface taggedEntity,List <EntityInterface> pathList,Map<EntityInterface, List<EntityInterface>> parentChilddrenMap)
 	{
 		Set<EntityInterface> keySet  = parentChilddrenMap.keySet();
 		for(EntityInterface mainEntity : keySet)
 		{
 			List <EntityInterface> containmentList = parentChilddrenMap.get(mainEntity);
 			if(containmentList.contains(taggedEntity))
 			{
 				pathList.add(mainEntity);
 				getPathListForTaggedEntity(mainEntity,pathList,parentChilddrenMap);
 			}
 		}
 		return pathList;
 	}
 	
 	private IQuery formIQuery(EntityInterface rootEntity,Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap)
 	{
 		//In IQuery, root entity should have a parametarized condition on primary key
 		List <AttributeInterface> primaryAttributesList = rootEntity.getPrimaryKeyAttributeCollection();
	    List<String> attributeOperators = new ArrayList<String>();
	    List <String>conditionList = new ArrayList<String>();
	    List<List<String>>  conditionValues = new ArrayList<List<String>>();
 		if(rootEntity.getName().equalsIgnoreCase("Person"))
 		{
 			//Only in case of Person Entity, it will be parametrized condition else not
 			for(int i=0; i<primaryAttributesList.size(); i++)
 			{
 				 attributeOperators.add("Equals");
 				 conditionList.add("?");
 			}
 			 conditionValues.add(conditionList);
 		}
 		else
 		{
 			
 		}
 		
 		IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
	    int rootExpId = m_queryObject.addRule(primaryAttributesList,attributeOperators,conditionValues,rootEntity);
	    
	    Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap = new HashMap<EntityInterface, List<Integer>>(); 
	    
 		//All Intermediate nodes as well tagged entities should be added like containment
 		Set <EntityInterface> taggedEntitiesKeySet = eachTaggedEntityPathMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntitiesKeySet)
 		{
 			List<EntityInterface> pathList = eachTaggedEntityPathMap.get(taggedEntity);
 			List <Integer> pathExpressionsList =  new ArrayList<Integer>();
 			for(int i= pathList.size()-1; i >=0; i--)
 			{
 				EntityInterface pathEntity = pathList.get(i);
 				if(pathEntity.getName().equalsIgnoreCase(rootEntity.getName()))
 				{
 					pathExpressionsList.add(rootExpId);
 				}
 				else
 				{
 					updatePathExpressionsList(pathEntity,m_queryObject, pathExpressionsList);
 				}
 			}
 			eachTaggedEntityPathExpressionsMap.put(taggedEntity,pathExpressionsList);
 		}
 		
 		//Now u have eachTaggedEntityPathExpressionsMap, so create path among added expressions
 		addLinksAmongExpressionsAdded(eachTaggedEntityPathExpressionsMap,m_queryObject); 
 		
 		//Only tagged attributes should be added as IOutPutAttribute List
 		addTaggedOutputAttributesToQuery(eachTaggedEntityPathExpressionsMap,m_queryObject);
 		
 		return m_queryObject.getQuery();
 	}
 	
 	private void addTaggedOutputAttributesToQuery(Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap,IClientQueryBuilderInterface m_queryObject)
	{
 		//For each tagged Entity ,get the tagged Attributes and add them to Ioutput Attribute list
 		ParameterizedQuery query = (ParameterizedQuery)m_queryObject.getQuery();
 		List <IOutputAttribute> outputAttributeList =  new ArrayList<IOutputAttribute>();
 		Cab2bQuery cab2bQuery = (Cab2bQuery)query;
	    IConstraints constraints = query.getConstraints();
 		Set <EntityInterface> taggedEntityKeySet= eachTaggedEntityPathExpressionsMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntityKeySet)
 		{
 			List<Integer> pathExpressionsIdsList = eachTaggedEntityPathExpressionsMap.get(taggedEntity);
 			int taggedEntityExpId = pathExpressionsIdsList.get((pathExpressionsIdsList.size()-1));
 			
 			Collection <AttributeInterface> attributeCollection= taggedEntity.getAllAttributes();
 			for(AttributeInterface attribute : attributeCollection)
 			{
 				if(edu.wustl.query.util.global.Utility.istagPresent(attribute,"resultview"))
 				{
 					//If the Attribute is tagged ,then Create IOutPut attribute and add to list
 					outputAttributeList.add(new OutputAttribute(constraints.getExpression(taggedEntityExpId),attribute));    					
 				}
 			}
 		}
 		
 	   //Setting the IOUtPut Attribute List to Query 
 	  cab2bQuery.setOutputAttributeList(outputAttributeList);
	}
		
 	
 	private void addLinksAmongExpressionsAdded(Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap,IClientQueryBuilderInterface m_queryObject )
 	{
 		IQuery query =  m_queryObject.getQuery();
 		IConstraints constraints = query.getConstraints();
 		IJoinGraph graph = constraints.getJoinGraph();
 		
 		Set <EntityInterface> taggedEntityKeySet= eachTaggedEntityPathExpressionsMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntityKeySet)
 		{
 			List <Integer> pathExpressionsList = eachTaggedEntityPathExpressionsMap.get(taggedEntity);
 		    if((pathExpressionsList != null) && (!pathExpressionsList.isEmpty()))
 		    {
 		    	for(int i=0; i<pathExpressionsList.size()-1; i++)
 		    	{
                    int parentExpId = pathExpressionsList.get(i);
                    int childExpId = pathExpressionsList.get(i+1);
 		    		
 		    		IExpression parentExpression = constraints.getExpression(parentExpId);
                    IExpression childExpresion = constraints.getExpression(childExpId);
                    IIntraModelAssociation association = (IIntraModelAssociation) (graph.getAssociation(
                    		parentExpression, childExpresion));
                    if(association == null)
                    {
                    	//If association is null, only then add the association
                    	IPath path = getIPath(parentExpression.getQueryEntity().getDynamicExtensionsEntity(),childExpresion.getQueryEntity().getDynamicExtensionsEntity());
                	    if (!m_queryObject.isPathCreatesCyclicGraph(parentExpId, childExpId, path))
                		{
                	    	QueryAddContainmentsUtil.linkTwoNodes(parentExpId, childExpId, path, m_queryObject);
                		}
                    }
                    
 		    	}
 		    }
 		}
 		
 	}
 	
 	private void updatePathExpressionsList(EntityInterface pathEntity ,IClientQueryBuilderInterface m_queryObject,List <Integer> pathExpressionsList)
 	{
 		IQuery query = m_queryObject.getQuery();
 		IConstraints constraints = query.getConstraints();
 		boolean isMatchFound = false ;
 		for(IExpression expression : constraints)
 		{
 			if(expression.getQueryEntity().getDynamicExtensionsEntity().getName().equals(pathEntity.getName()))
 			{
 				pathExpressionsList.add(expression.getExpressionId());
 				isMatchFound = true;
 			}
 		}
 		//If no match is found with any of the expression, only then add the expression to query
 		if(!isMatchFound)
		{
				//add new expression to Query and add that expression id to pathExpressionsList
				int expressionId = ((ClientQueryBuilder) m_queryObject).addExpression(pathEntity);
				pathExpressionsList.add(expressionId);
		}
 	}
}   
	
    
	
	
	
	


