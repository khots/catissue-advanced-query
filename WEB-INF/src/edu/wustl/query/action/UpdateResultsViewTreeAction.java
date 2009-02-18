package edu.wustl.query.action;

import java.io.IOException;
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
import org.json.JSONException;
import org.json.JSONObject;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.util.Utility;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.ResultsViewIQueryCreationUtil;
import edu.wustl.query.viewmanager.ViewType;


public class UpdateResultsViewTreeAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		//Here i get the Primary key such as "UPI" on tree node clicked 
		String nodeId = request.getParameter("nodeId");
		
		//Here i will get the rootOutPut node
		//Check if Any of the direct children of root node is a main expression
		//If there is any main expression, only then i need to retrieve the main expression according to value of the primary key
	    	
		String labelNode = nodeId.substring(nodeId.lastIndexOf(Constants.NODE_SEPARATOR) + 2,
				nodeId.length());
		String[] splitIds = labelNode.split(Constants.UNDERSCORE);
		String treeNo = splitIds[0];
		String treeNodeId = splitIds[1];
		String uniqueCurrentNodeId = treeNo + "_" + treeNodeId;
		
		HttpSession session = request.getSession();
		
		//Get the query Execution id
		int queryExecutionID =   (Integer)session.getAttribute("queryExecutionId");
		
		//Get the unique node Id map
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map)session.getAttribute("uniqueIDNodeMap");
		
		//Get the patient data IQuery 
		IQuery patientDataQuery  = (IQuery)session.getAttribute("patientDataQuery");
		
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		if(nodeId.endsWith(Constants.LABEL_TREE_NODE))
		{
			//If node Id ends with Label, then it's label node
			OutputTreeDataNode labelTreeDataNode =  uniqueIdNodesMap.get(uniqueCurrentNodeId);
			IOutputEntity outputEntity = labelTreeDataNode.getOutputEntity();
			EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
			
			//Here we got the root node
			//Now for labelTreeDataNode, get All the parent /children Map from root 
			Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap = IQueryParseUtil.getParentChildrensForaMainNode(labelTreeDataNode);
			
			//Now form the IQuery, to get the results
	    	//Get the parent child map for containment of a  main Entity
	    	Map<EntityInterface, List<EntityInterface>> partentChildEntityMap = ResultsViewIQueryCreationUtil.getAllParentChildrenMap(rootEntity);
	    	
	    	//Once u got the parent child map, get the parent child map for tagged entities for results view, and populate the list for tagged entities
	    	Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = ResultsViewIQueryCreationUtil.getTaggedEntitiesParentChildMap(partentChildEntityMap,rootEntity);
	   
	    	//Here we get path list from Root Entity to parent of Tagged Entity for results view 
			Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = ResultsViewIQueryCreationUtil.getPathsMapForTaggedEntity(taggedEntitiesParentChildMap,partentChildEntityMap);

			//Now Add all entities related to a tagged entity to IQuery
			IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
			IQuery generatedQuery =  ResultsViewIQueryCreationUtil.formIQuery(labelTreeDataNode,eachTaggedEntityPathMap,patientDataQuery,m_queryObject); 				
			
			//update the IQuery with conditions on all childrens
			ResultsViewIQueryCreationUtil.updateGeneratedQuery(generatedQuery,parentChildrenMap,labelTreeDataNode,patientDataQuery);
			
			//Now add all parents
			IConstraints constraints = patientDataQuery.getConstraints(); 
			List<IExpression> parentsList = getAllParentsHierarchy(constraints,
					 labelTreeDataNode);
				   
			 //Now Add All the parents of the main Entity to form the IQuery, except Person
			 //Person will always be the root entity of the iQuery with parameterized conditions
			 //add all the other parents Hierarchy
			 if((parentsList != null) && (!parentsList.isEmpty()))
			 {
				 generatedQuery = addAllParentsHierarchyToIQuery(m_queryObject, parentsList);
			 }
			 
			 if(!labelTreeDataNode.getOutputEntity().getDynamicExtensionsEntity().getName().equalsIgnoreCase("Person"))
			 {
				 //If clicked label node is not Person, only then add Person as Root entity of the Query 
				 generatedQuery = addPersonAsRootToIQuery(m_queryObject, generatedQuery);
 			 }
			
			 ((Query)generatedQuery).setType(QueryType.GET_DATA.type);
			 
//			 PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
//			String generatedNewQuery = passTwoQueryGenerator.generateQuery(generatedQuery); 
			
			/*List<Object> personUPIsList = new ArrayList<Object>();
			personUPIsList.add("000000000000000001000823");
			personUPIsList.add("000000000000000001006129");
			personUPIsList.add("000000000000000001008822");
			personUPIsList.add("000000000000000001012028");
			personUPIsList.add("000000000000000001013329");
			personUPIsList.add("000000000000000001013620");
			personUPIsList.add("000000000000000001015328");
			personUPIsList.add("000000000000000001015523");
			*/
//			CiderQuery ciderQuery = new CiderQuery();
//			ciderQuery.setQueryString(generatedNewQuery);
//			ciderQuery.setQuery(generatedQuery);

//			CiderQueryManager ciderManager =new CiderQueryManager();
//			DataQueryResultsBean resultBean = ciderManager.execute(ciderQuery, personUPIsList, queryExecutionID);
//			List<List<Object>>  dataList = resultBean.getAttributeList();
  
			//System.out.println("The dataList size is:"+dataList.size());
			//Now create the jason Objects for response
			//Iterator<List<Object>> itr = dataList.iterator();
			List <Integer> primaryKeyIndexesList = new ArrayList<Integer>();

			//Populate the primary Key indexes
			List <AttributeInterface> primaryKeyList = rootEntity.getPrimaryKeyAttributeCollection();
			//
			List <IOutputAttribute> outputAttributesList = ((ParameterizedQuery)generatedQuery).getOutputAttributeList();
			
			for(int i=0; i<outputAttributesList.size(); i++)
			{
				IOutputAttribute outputAttribute = outputAttributesList.get(i);
				for(int j=0; j<primaryKeyList.size(); j++)
				{
					AttributeInterface attribute = primaryKeyList.get(j);	
					if(outputAttribute.getAttribute().getId() == attribute.getId())
					{
						primaryKeyIndexesList.add(i);
					    break;
					}
				}
			}
			
			AbstractQueryUIManager abstractQueryUIManager =AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
			DataQueryResultsBean dataQueryResultsBean = abstractQueryUIManager.getData(queryExecutionID, ViewType.TREE_VIEW);
			List<List<Object>>  dataList = dataQueryResultsBean.getAttributeList();
			//Till here i get the primary key indexes in the result set
			
			if(dataList.size() >0)
			{
				for(int i=0; i< dataList.size(); i++)
				{
					List <Object> labelNodeDataList = dataList.get(i);
					String displayData = ""; 
					String primaryKeySetData = "";
				    for(int j=0; j<labelNodeDataList.size(); j++)
				    {
				    	if(primaryKeyIndexesList.contains(j))
				    	{
				    		primaryKeySetData +=  labelNodeDataList.get(j) + "#";
				    	}
				    	else
				    	{
				    		displayData += labelNodeDataList.get(j)+" ";
				    	}
				    }
					
					//String dataNodeId = 0 + "_" + Constants.NULL_ID + Constants.NODE_SEPARATOR 
					String dataNodeId = primaryKeySetData + Constants.NODE_SEPARATOR + uniqueCurrentNodeId+ "_"+ "Data";
					
				    String displayName = Constants.TREE_NODE_FONT +  displayData +Constants.TREE_NODE_FONT_CLOSE;	  
				    String parentId = nodeId;
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.append("identifier", dataNodeId);
					jsonObject.append("objName", "");
					jsonObject.append("displayName",displayName);
					jsonObject.append("parentId",parentId);
					jsonObject.append("parentObjName","");
					
					jsonObjectList.add(jsonObject);
				}
			}
		}
		else
		{
			//String  primaryKeyString = nodeId.substring(nodeId.indexOf(Constants.NODE_SEPARATOR)+2, nodeId.lastIndexOf(Constants.NODE_SEPARATOR));
		    
			String  primaryKeyString = nodeId.substring(0, nodeId.indexOf(Constants.NODE_SEPARATOR));
			String [] primaryKeyValues = primaryKeyString.split("#");
		    
		    //Get the root node for which main entities children need to be shown
		    OutputTreeDataNode rootTreeDataNode =  uniqueIdNodesMap.get(uniqueCurrentNodeId);		    
		    int rootNodeExpd = rootTreeDataNode.getExpressionId();
		    if(patientDataQuery != null)
		    {
		    	IConstraints constraints = patientDataQuery.getConstraints();
				IExpression expression = constraints.getExpression(rootNodeExpd);
				EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
				List<EntityInterface> mainEntityList = QueryAddContainmentsUtil.getAllMainObjects(patientDataQuery);
			   
				//Check if the entity, for which children nodes are to be shown , is main entity or not 
				if(mainEntityList.contains(entity))
			    {
					
					//This step is not required now as , we get the OutputTreeDataNode from unique node ID map
					//If it main entity then get the OutputTreeDataNode from rootOutputTreeNodeList
			    	//OutputTreeDataNode mainOutputTreeDataNode = getMainOutPutTreeDataNode(expression,rootTreeDataNode,patientDataQuery);
			   
			        //For main OutputTreeDataNode, get the map of all parent/children map 
			    	Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap = IQueryParseUtil.getParentChildrensForaMainNode(rootTreeDataNode);
		       
			    	if(!parentChildrenMap.isEmpty())
			    	{

			    		Set<OutputTreeDataNode> mainEntitiesKeySet = parentChildrenMap.keySet();
				    	Iterator<OutputTreeDataNode> keySetItr = mainEntitiesKeySet.iterator();
				    	while(keySetItr.hasNext())
				    	{
				    		OutputTreeDataNode mainEntityTreeDataNode = keySetItr.next();
				    		if(mainEntityTreeDataNode.getExpressionId() != rootTreeDataNode.getExpressionId())
					    	{
					    		EntityInterface mainEntity = mainEntityTreeDataNode.getOutputEntity().getDynamicExtensionsEntity();
					    		if(mainEntityList.contains(mainEntity))
					    		{
					    			//Only if it a main entity
					    			//Now create the IQuery , for main Entity for tagged attributes for results view
							    	
							    	//Get the parent child map for containment of a  main Entity
							    	Map<EntityInterface, List<EntityInterface>> partentChildEntityMap = ResultsViewIQueryCreationUtil.getAllParentChildrenMap(mainEntity);
							    	
							    	//Once u got the parent child map, get the parent child map for tagged entities for results view, and populate the list for tagged entities
							    	Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = ResultsViewIQueryCreationUtil.getTaggedEntitiesParentChildMap(partentChildEntityMap,mainEntity);
							   
							    	//Here we get path list from Root Entity to parent of Tagged Entity for results view 
									Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = ResultsViewIQueryCreationUtil.getPathsMapForTaggedEntity(taggedEntitiesParentChildMap,partentChildEntityMap);
					 
									//Now Add all entities related to a tagged entity to IQuery
									IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
									IQuery generatedQuery =  ResultsViewIQueryCreationUtil.formIQuery(mainEntityTreeDataNode,eachTaggedEntityPathMap,patientDataQuery,m_queryObject); 				
							       
							       //Now update the generated Query with children and conditions on each children
									ResultsViewIQueryCreationUtil.updateGeneratedQuery(generatedQuery,parentChildrenMap,mainEntityTreeDataNode,patientDataQuery);
								   
								   //After updating the IQuery , with conditions added by user, Add all the parents (till person as Root)of main entity to IQuery
                                   
								   //From old get Patient Data Query, get all the parents of the main Entity
								   //Get the Expression id of the mainEntityTreeDataNode
								   List<IExpression> parentsList = getAllParentsHierarchy(constraints,
										mainEntityTreeDataNode);
								   
								  //Now Add All the parents of the main Entity to form the IQuery, except Person
								  //Person will always be the root entity of the iQuery with parameterized conditions
								  //add all the other parents Hierarchy
								  if((parentsList != null) && (!parentsList.isEmpty()))
								  {
									  generatedQuery = addAllParentsHierarchyToIQuery(m_queryObject, parentsList);
								  }
								   //Add person as root entity with parameterized condition on PersonUpi
								   generatedQuery = addPersonAsRootToIQuery(m_queryObject, generatedQuery);
							 	   
							 	   //Set Query type
								   ((Query)generatedQuery).setType(QueryType.GET_DATA.type);
								   
//								   PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
//						    	   String xquery = passTwoQueryGenerator.generateQuery(m_queryObject.getQuery());  
//					    		   System.out.println("Generated XQuery is:"+xquery);
					    		   
					    		   
					    		   	//Populate the primary key values
					    		   	List<Object> personUPIsList = new ArrayList<Object>();
					    		   	for(int i=0; i< primaryKeyValues.length; i++)
					    		   	{
					    			   personUPIsList.add(primaryKeyValues[i]);
					    		   	}
//					    		   	CiderQuery ciderQuery = new CiderQuery();
//					   				ciderQuery.setQueryString(xquery);
//					   				ciderQuery.setQuery(m_queryObject.getQuery());

//					   				CiderQueryManager ciderManager =new CiderQueryManager();
//					   				
//					   				DataQueryResultsBean resultBean = ciderManager.execute(ciderQuery, personUPIsList, queryExecutionID);
					   				//List<List<Object>>  dataList = resultBean.getAttributeList();
					    		   	
					    		   	AbstractQueryUIManager abstractQueryUIManager =AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
					    		   	DataQueryResultsBean dataQueryResultsBean = abstractQueryUIManager.getData(queryExecutionID, ViewType.TREE_VIEW); 
					    			List<List<Object>>  dataList = dataQueryResultsBean.getAttributeList();
					     
					   				System.out.println("The dataList size is:"+dataList.size());
					   				
					   				
					   				//Here create the Label Node
					   				String displayName = Utility.getDisplayLabel(mainEntity.getName()) + " (" + dataList.size() + ")";
					   				
					   				String labelNodeId = "";
					   				
					   				Set<String> uniqueKeySet = uniqueIdNodesMap.keySet();
					   				Iterator<String> keyItr = uniqueKeySet.iterator();
					   				while(keyItr.hasNext())
					   				{
					   					String keyId = keyItr.next();
					   					OutputTreeDataNode treeDataNode = uniqueIdNodesMap.get(keyId);
					   					if(treeDataNode.getExpressionId() == mainEntityTreeDataNode.getExpressionId())
					   					{
					   						labelNodeId = keyId;
					   						break;
					   					}
					   				}
					   				
					   				String dataNodeId = 0 + "_" + Constants.NULL_ID + Constants.NODE_SEPARATOR + labelNodeId + Constants.UNDERSCORE + Constants.LABEL_TREE_NODE;
					   				displayName = Constants.TREE_NODE_FONT + displayName + Constants.TREE_NODE_FONT_CLOSE;
					   				
					   				JSONObject jsonObject = new JSONObject();
					   				jsonObject.append("identifier", dataNodeId);
									jsonObject.append("objName", "");
									jsonObject.append("displayName",displayName);
									jsonObject.append("parentId",nodeId);
									jsonObject.append("parentObjName","");
									
									jsonObjectList.add(jsonObject);
					    		}
					    	}	
				    	}
			    		
			    	}
			    }
		    }
		
		}
	    
		if(nodeId != null)
		{
			setResponse(response, jsonObjectList);
		}
		return null;
	}

	private IQuery addPersonAsRootToIQuery(IClientQueryBuilderInterface m_queryObject,
			IQuery generatedQuery) throws MultipleRootsException
	{
		//Now again get the root node of the Query, as it might be updated by adding the new parents  
		  IConstraints generatedQueryconstraints = generatedQuery.getConstraints();
		  IJoinGraph graph = generatedQueryconstraints.getJoinGraph();
		  IExpression rootExpression = graph.getRoot();
		  int rootExpressionId = rootExpression.getExpressionId();
		  EntityInterface rootEntity = rootExpression.getQueryEntity().getDynamicExtensionsEntity();
		  
		  //Get the Person Entity
		   Collection <EntityGroupInterface> entityGroups =  EntityCache.getCache().getEntityGroups();
		   EntityInterface  personEntity = getEntityFromCache("Person", entityGroups);
		   AttributeInterface personAttributes = personEntity.getAttributeByName("personUpi");
		   List<AttributeInterface> personAttributesList =  new Vector<AttributeInterface>();
		   personAttributesList.add(personAttributes);
		   List<String> personAttributeOperators = new ArrayList<String>();
		   personAttributeOperators.add(RelationalOperator.Equals.getStringRepresentation());
		   List <String> conditionList = new ArrayList<String>();
		   List<List<String>>  personConditionValues = new ArrayList<List<String>>();
		   conditionList.add("");
		   personConditionValues.add(conditionList);
		   
		   //Now Added the person as in the Query
		   int personExpId = m_queryObject.addRule(personAttributesList,personAttributeOperators,personConditionValues,personEntity);
		   
		   //Add link from Person to Root entity of the Query, making Person as root entity
		   IPath path = getIPath(personEntity,rootEntity);
		   if (!m_queryObject.isPathCreatesCyclicGraph(personExpId, rootExpressionId, path))
		   {
		    	QueryAddContainmentsUtil.linkTwoNodes(personExpId, rootExpressionId, path, m_queryObject);
		   } 
		   
		   //Adding parameterized conditions on Person
		   ICondition condition = QueryObjectFactory.createCondition(personAttributes, RelationalOperator.Equals, conditionList);
		    
			//Now create parameter
		    IParameter<ICondition> parameter = QueryObjectFactory.createParameter(condition,"personUpi");
		    
		    IParameterizedQuery pQuery = (IParameterizedQuery)m_queryObject.getQuery();
		    pQuery.getParameters().add(parameter);
	
	        return m_queryObject.getQuery();
	}

	private IQuery addAllParentsHierarchyToIQuery(IClientQueryBuilderInterface m_queryObject,
			List<IExpression> parentsList) throws MultipleRootsException
	{
		
		for(int i=0; i< parentsList.size(); i++)
		{
			IExpression parentExp = parentsList.get(i);
			EntityInterface parentEntity =  parentExp.getQueryEntity().getDynamicExtensionsEntity();
			  
			 //get the rule Associated with each parent from old get patient data Query
			IRule parentRule =  getRuleFromExpression(parentExp); 
			if(!parentEntity.getName().equalsIgnoreCase("Person"))
			{
				//If the parent expression is not person, then add to the Query 
				IQuery updatedNewQuery = m_queryObject.getQuery();
				  
				IConstraints generatedQueryconstraints = updatedNewQuery.getConstraints();
				IJoinGraph graph = generatedQueryconstraints.getJoinGraph();
				IExpression rootExpression = graph.getRoot();
				int rootExpressionId = rootExpression.getExpressionId();
				EntityInterface rootEntity = rootExpression.getQueryEntity().getDynamicExtensionsEntity();
			  
				//Now Add the parent as the root entity of Query
				int parentExpressionId = 0;
				if(parentRule != null)
				{
					parentExpressionId = m_queryObject.addExpression(parentRule, parentEntity);
			    }
				 else
				 {
					parentExpressionId = m_queryObject.addExpression(parentEntity);
				 }
				 
				 IPath path = getIPath(parentEntity,rootEntity); 
			     
			     //Adding the path from parent to previous root entity of the generated Query
				  if (!m_queryObject.isPathCreatesCyclicGraph(parentExpressionId, rootExpressionId, path))
			 	   {
			 	    	QueryAddContainmentsUtil.linkTwoNodes(parentExpressionId, rootExpressionId, path, m_queryObject);
			 	   } 
			  }
		  }
		
		return m_queryObject.getQuery();
	}

	private List<IExpression> getAllParentsHierarchy(IConstraints constraints,
			OutputTreeDataNode mainEntityTreeDataNode)
	{
		int mainEntityOldExpId = mainEntityTreeDataNode.getExpressionId();
		   IExpression oldPatientDataQueryExp = constraints.getExpression(mainEntityOldExpId);
		   IJoinGraph oldPatientDataQueryGraph = constraints.getJoinGraph();
		   
		   //Get the complete parent child Hierarchy, till root is reached 
		  
		   //Extract this as Method
		   List <IExpression>  parentsList =oldPatientDataQueryGraph.getParentList(oldPatientDataQueryExp);
		   if(parentsList != null && !parentsList.isEmpty())
		   {
			   //Iterate through full hierarchy till root is reached
			   for(int i=0; i< parentsList.size(); i++)
			   {
				   IExpression parentExp = parentsList.get(i);
				   List <IExpression> listOfParents = oldPatientDataQueryGraph.getParentList(parentExp);
				   if((listOfParents != null) && (!listOfParents.isEmpty()))
				   {
					   parentsList.addAll(listOfParents);
				   }
			   }
		   }
		return parentsList;
	}
   
	/**
	 * This method is used to set the response
	 * @param response
	 * @param arrayList
	 * @throws IOException
	 * @throws JSONException
	 */
	public void setResponse(HttpServletResponse response, List<JSONObject> arrayList)
			throws IOException, JSONException
	{
		response.flushBuffer();
		response.getWriter().write(new JSONObject().put("childrenNodes", arrayList).toString());
	}
	
	private EntityInterface getEntityFromCache(String entityName, Collection<EntityGroupInterface> entityGroups)
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
	}
	
	
	
	private void updateGeneratedQuery(IQuery generatedQuery,Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap,OutputTreeDataNode mainEntityTreeDataNode,IQuery parentQuery) throws MultipleRootsException
	{
		IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
		m_queryObject.setQuery(generatedQuery);
		
		//Map<Integer,List<Integer>> parentChildExpIdsMap = new HashMap<Integer,List<Integer>>();
		
		//Now add children added by user to IQuery with conditions
		IConstraints constraints = generatedQuery.getConstraints();
		IJoinGraph graph = constraints.getJoinGraph();
	 	IExpression rootExpression = graph.getRoot();
	 	int rootExpressionId = rootExpression.getExpressionId();
	 	//This rootExpression gives the root expression of the Query
	 	EntityInterface rootEntity = rootExpression.getQueryEntity().getDynamicExtensionsEntity();
	 	
	 	List<OutputTreeDataNode> childrenList = parentChildrenMap.get(mainEntityTreeDataNode);
	 	//Add these direct children to IQuery with rule
	 
        //Get the constraints from old patient Data query. This is required to get the rules added on children nodes 	 	
	 	IConstraints parentQueryConstraints  = parentQuery.getConstraints();
	 	Map <OutputTreeDataNode,Integer> childrenExpIdsMap =  new HashMap<OutputTreeDataNode, Integer>();
	 	for(OutputTreeDataNode childNode : childrenList)
	 	{
	 		int childExpressionId = childNode.getExpressionId();
	 		IExpression childExpression = parentQueryConstraints.getExpression(childExpressionId);
	 		
	 		IRule iRule = getRuleFromExpression(childExpression);
	 		
	 		//Now get the Entity,from child node 
	 		EntityInterface childEntity =  childNode.getOutputEntity().getDynamicExtensionsEntity();
	 		
	 		//Now add that expression to generated  IQuery
	 		int expressionId = addExpressionAndRuleToQuery(m_queryObject, iRule, childEntity);
	 		
	 		
	 		//This map is used for retrieving the expression id a children that is added to iQuery
	 		childrenExpIdsMap.put(childNode, expressionId);
	 		
	 		//Now add link among entities
	 		IPath path = getIPath(rootEntity,childEntity);
	 		if (!m_queryObject.isPathCreatesCyclicGraph(rootExpressionId, expressionId, path))
	 		{
	 	    	QueryAddContainmentsUtil.linkTwoNodes(rootExpressionId, expressionId, path, m_queryObject);
	 		}
	 		
	 		
	 		//expressionIdsList.add(expressionId);
	 	}
	 	//parentChildExpIdsMap.put(rootExpressionId, expressionIdsList);
	 	
	 	//Now for each children, add further children
	 	for(OutputTreeDataNode childNode : childrenList)
	 	{
	 		int childRootExpId = childrenExpIdsMap.get(childNode);
	 		EntityInterface childRootEntity = childNode.getOutputEntity().getDynamicExtensionsEntity();
	 		
	 		List <OutputTreeDataNode> childrenOutPutList = parentChildrenMap.get(childNode);
	 		//List<Integer> expIdsList = new ArrayList<Integer>();
	 		if(childrenOutPutList != null  && !childrenOutPutList.isEmpty())
	 		{  
	 			for(OutputTreeDataNode outputNode : childrenOutPutList)
	 			{
	 				
	 				int expId =  outputNode.getExpressionId();
	 				IExpression childExpression = parentQueryConstraints.getExpression(expId);
	 				IRule iRule = getRuleFromExpression(childExpression);
	 				EntityInterface childEntity =  outputNode.getOutputEntity().getDynamicExtensionsEntity();
	 				int expressionId = addExpressionAndRuleToQuery(m_queryObject, iRule, childEntity);
	 			
	 				IPath path = getIPath(childRootEntity,childEntity);
	 		 		if (!m_queryObject.isPathCreatesCyclicGraph(childRootExpId, expressionId, path))
	 		 		{
	 		 	    	QueryAddContainmentsUtil.linkTwoNodes(childRootExpId, expressionId, path, m_queryObject);
	 		 		}
	 				
	 				//expIdsList.add(expressionId);
	 			}
	 			//parentChildExpIdsMap.put(childNode.getExpressionId(),expIdsList);
	 		}
	 	}
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

	private int addExpressionAndRuleToQuery(IClientQueryBuilderInterface m_queryObject,
			IRule iRule, EntityInterface childEntity)
	{
		int expressionId;
		if(iRule != null)
		{
			expressionId = m_queryObject.addExpression(iRule, childEntity);
		}
		else
		{
			expressionId = m_queryObject.addExpression(childEntity);
		}
		return expressionId;
	}

	private IRule getRuleFromExpression(IExpression childExpression)
	{
		IRule iRule = null;
		for (IExpressionOperand expressionOperand : childExpression)
		{
			if(expressionOperand instanceof IRule)
			{
				iRule = (IRule)expressionOperand;
				break;
			}
		}
		return iRule;
	}
	
	
	public OutputTreeDataNode getMainOutPutTreeDataNode(IExpression expression, OutputTreeDataNode rootNode,IQuery query)
	{
		List<OutputTreeDataNode> childrenNodes = rootNode.getChildren();
		OutputTreeDataNode mainOutputTreeDataNode = null;
		if(rootNode.getExpressionId() == expression.getExpressionId())
		{
			mainOutputTreeDataNode = rootNode;
		}
		else
		{
			boolean isMatchFound = false;
			for(OutputTreeDataNode outputNode : childrenNodes)
			{
			   if(outputNode.getExpressionId()==expression.getExpressionId())
			   {
				   isMatchFound = true;
				   mainOutputTreeDataNode = outputNode;
			   }
			}
			
			if(!isMatchFound)
			{
				//If Match is not found under direct children, then iterate further leaf nodes
				//The rootNodeMap contains the all the main entities with their containments entities  
				Map<Integer, List<OutputTreeDataNode>> rootNodeMap =  IQueryParseUtil.separateAllChildren(rootNode, query);
				
				//This list contains all main entities present in the query
				List <OutputTreeDataNode> mainEntitiesTreeDataNodesList = rootNodeMap.get(rootNode.getExpressionId());
				
				//Any main entity that is present in query  must match any of the entity in this list
				for(OutputTreeDataNode outputNode : mainEntitiesTreeDataNodesList)
				{
					if(outputNode.getExpressionId()==expression.getExpressionId())
					{
						mainOutputTreeDataNode = outputNode;
					}
				}
			}
			
		}
		return mainOutputTreeDataNode;
	}
	
}
