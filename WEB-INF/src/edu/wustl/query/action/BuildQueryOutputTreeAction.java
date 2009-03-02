
package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.util.Utility;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.ResultsViewIQueryCreationUtil;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ViewType;

/**
 * This class is invoked when user clicks on a node from the tree. It loads the data required for tree formation.
 * @author deepti_shelar
 *
 */
public class BuildQueryOutputTreeAction extends Action
{
	
	private IQuery generateIQuery(OutputTreeDataNode labelTreeDataNode,Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap
			,EntityInterface rootEntity,IQuery patientDataQuery) throws Exception
	{
		
		IConstraints constraints = patientDataQuery.getConstraints(); 
		
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
		
		//update the IQuery with conditions on all children
		ResultsViewIQueryCreationUtil.updateGeneratedQuery(generatedQuery,parentChildrenMap,labelTreeDataNode,patientDataQuery);

		
		//Now add all parents
		List<IExpression> parentsList = ResultsViewIQueryCreationUtil.getAllParentsHierarchy(constraints,
				 labelTreeDataNode);
			   
		 //Now Add All the parents of the main Entity to form the IQuery, except Person
		 //Person will always be the root entity of the iQuery with parameterized conditions
		 //add all the other parents Hierarchy
		 if((parentsList != null) && (!parentsList.isEmpty()))
		 {
			 generatedQuery = ResultsViewIQueryCreationUtil.addAllParentsHierarchyToIQuery(m_queryObject, parentsList);
		 }
		 
		 if(!labelTreeDataNode.getOutputEntity().getDynamicExtensionsEntity().getName().equalsIgnoreCase("Person"))
		 {
			 //If clicked label node is not Person, only then add Person as Root entity of the Query 
			 generatedQuery = ResultsViewIQueryCreationUtil.addPersonAsRootToIQuery(m_queryObject, generatedQuery);
		 }
		
		 ((Query)generatedQuery).setType(QueryType.GET_DATA.type);

		
		return generatedQuery; 
	}
	
	
	/**
	 * This method loads the data required for Query Output tree. 
	 * With the help of QueryOutputTreeBizLogic it generates a string which will be then passed to client side and tree is formed accordingly. 
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@SuppressWarnings("unchecked")
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		
		//Here i get the Primary key such as "UPI" on tree node clicked 
		String nodeId = request.getParameter(Constants.TREE_NODE_ID);
		NodeId node = new NodeId(nodeId);
		
		//This gives first UPI
		String rootData = node.getRootData();
		
		// Tree No
		//String treeNo = node.getTreeNo();
		
		//Unique parent Node ID
		String uniqueParentNode = node.getUniqueParentNodeId();
		
		//Unique Current NOde Id
		String uniqueCurrentNodeId = node.getUniqueCurrentNodeId();
		
		//primary key Data
		String [] primaryKeyData = node.getParentData();
		
		//Gives the primary key values for current node
		String [] primaryKeyValues = node.getCurrentNodeData();
		
		
		//Here i will get the rootOutPut node
		//Check if Any of the direct children of root node is a main expression
		//If there is any main expression, only then i need to retrieve the main expression according to value of the primary key
	    	
		/*String labelNode = nodeId.substring(nodeId.lastIndexOf(Constants.NODE_SEPARATOR) + 2,
				nodeId.length());
		String[] splitIds = labelNode.split(Constants.UNDERSCORE);
		String treeNo = splitIds[0];
		String treeNodeId = splitIds[1];
		String uniqueCurrentNodeId = treeNo + "_" + treeNodeId;*/
		
		//Get the query Execution id
		int queryExecutionID =   (Integer)session.getAttribute("queryExecutionId");
		
		//Get the unique node Id map
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map)session.getAttribute(Constants.PATIENT_QUERY_UNIQUE_ID_MAP);
		
		//Get the patient data IQuery 
		IQuery patientDataQuery  = (IQuery)session.getAttribute(Constants.PATIENT_DATA_QUERY);
		
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

			//Here we generate the iQuery
			IQuery generatedQuery = generateIQuery(labelTreeDataNode,parentChildrenMap,rootEntity,patientDataQuery);
			
			//Here we get the list of primary key indexes in the Output attribute list of generated IQuery
			List<Integer> primaryKeyIndexesList = getPrimaryKeysIndexes(
					rootEntity, generatedQuery);
			
			AbstractQueryUIManager abstractQueryUIManager =AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
			
			DataQueryResultsBean dataQueryResultsBean = null;
			if(rootData.equalsIgnoreCase(Constants.NULL_ID))
			{
				dataQueryResultsBean =  abstractQueryUIManager.getData(queryExecutionID, ViewType.TREE_VIEW);
			}
			else
			{
				dataQueryResultsBean = abstractQueryUIManager.getData(queryExecutionID, rootData ,ViewType.TREE_VIEW);
			}
			 
			List<List<Object>>  dataList = dataQueryResultsBean.getAttributeList();
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
				    		primaryKeySetData +=  labelNodeDataList.get(j) + "@@";
				    	}
				    	else
				    	{
				    		if(labelNodeDataList.get(j)== null)
				    		{
				    			displayData += ""; 
				    		}
				    		else
				    		{
				    			displayData += labelNodeDataList.get(j)+" ";
				    		}
				    		
				    	}
				    }
					
				    String dataNodeId = "";
				    String upiStr = Constants.NULL_ID;
				    if(rootData.equalsIgnoreCase(Constants.NULL_ID))
				    {
				    	//This is the case of click of root node 
				        String []strs =  primaryKeySetData.split("@@");
				        upiStr = strs[0];
				        dataNodeId = upiStr + Constants.NODE_SEPARATOR + uniqueParentNode +  Constants.NODE_SEPARATOR + uniqueCurrentNodeId;
				    }
				    else
				    {
				    	upiStr =  rootData;
				    	dataNodeId = upiStr + Constants.NODE_SEPARATOR + uniqueParentNode +  Constants.NODE_SEPARATOR + uniqueCurrentNodeId+ "_"+ primaryKeySetData;
				    }

				    String displayName = Constants.TREE_NODE_FONT +  displayData +Constants.TREE_NODE_FONT_CLOSE;	  
				    String parentId = nodeId;
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.append("identifier", dataNodeId);
					jsonObject.append("displayName",displayName);
					jsonObject.append("parentId",parentId);
					
					jsonObjectList.add(jsonObject);
				}
			}
		}
		else
		{
			//String  primaryKeyString = nodeId.substring(0, nodeId.indexOf(Constants.NODE_SEPARATOR));
			//String [] primaryKeyValues = primaryKeyString.split("#");
		    
		    //Get the root node for which main entities children need to be shown
		   
			//From the primary key values of the node clicked, generate the parent primary key attributes for label Node
			String labelNodeParentPrimaryKey = "";
			if(primaryKeyValues == null && rootData != null)
			{
				labelNodeParentPrimaryKey = rootData + "@@";
			}
			else
			{
				for(int i=0; i< primaryKeyValues.length; i++)
				{
					labelNodeParentPrimaryKey += labelNodeParentPrimaryKey + primaryKeyValues[i]+"@@";
				}
			}
			OutputTreeDataNode labelTreeDataNode =  uniqueIdNodesMap.get(uniqueCurrentNodeId);		    
		    int rootNodeExpd = labelTreeDataNode.getExpressionId();
		    if(patientDataQuery != null)
		    {
		    	IConstraints constraints = patientDataQuery.getConstraints();
				IExpression expression = constraints.getExpression(rootNodeExpd);
				EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
				List<EntityInterface> mainEntityList = QueryAddContainmentsUtil.getAllMainObjects(patientDataQuery);
			   
				//Check if the entity, for which children nodes are to be shown , is main entity or not 
				if(mainEntityList.contains(entity))
			    {
			        //For main OutputTreeDataNode, get the map of all parent/children map 
			    	Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap = IQueryParseUtil.getParentChildrensForaMainNode(labelTreeDataNode);
			    	if(!parentChildrenMap.isEmpty())
			    	{
			    		Set<OutputTreeDataNode> mainEntitiesKeySet = parentChildrenMap.keySet();
				    	Iterator<OutputTreeDataNode> keySetItr = mainEntitiesKeySet.iterator();
				    	while(keySetItr.hasNext())
				    	{
				    		OutputTreeDataNode mainEntityTreeDataNode = keySetItr.next();
				    		if(mainEntityTreeDataNode.getExpressionId() != labelTreeDataNode.getExpressionId())
					    	{
					    		EntityInterface mainEntity = mainEntityTreeDataNode.getOutputEntity().getDynamicExtensionsEntity();
					    		if(mainEntityList.contains(mainEntity))
					    		{
					    			IQuery generatedQuery = generateIQuery(mainEntityTreeDataNode,parentChildrenMap,mainEntity,patientDataQuery);
					    			
					    		   	AbstractQueryUIManager abstractQueryUIManager =AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
					    		   	DataQueryResultsBean dataQueryResultsBean = abstractQueryUIManager.getData(queryExecutionID, rootData ,ViewType.TREE_VIEW); 
					    			List<List<Object>>  dataList = dataQueryResultsBean.getAttributeList();
					   				
					   				//Here create the Label Node
					   				String displayName = Utility.getDisplayLabel(mainEntity.getName()) + " (" + dataList.size() + ")";
					   				
					   				String labelNodeId = getUniqueNodeID(
											uniqueIdNodesMap,
											mainEntityTreeDataNode);
					   				
					   				String dataNodeId = rootData + Constants.NODE_SEPARATOR + uniqueCurrentNodeId + Constants.UNDERSCORE + labelNodeParentPrimaryKey + Constants.NODE_SEPARATOR +labelNodeId + Constants.UNDERSCORE + Constants.LABEL_TREE_NODE;
					   				displayName = Constants.TREE_NODE_FONT + displayName + Constants.TREE_NODE_FONT_CLOSE;
					   				
					   				JSONObject jsonObject = new JSONObject();
					   				jsonObject.append("identifier", dataNodeId);
									jsonObject.append("displayName",displayName);
									jsonObject.append("parentId",nodeId.trim());
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


	private String getUniqueNodeID(
			Map<String, OutputTreeDataNode> uniqueIdNodesMap,
			OutputTreeDataNode mainEntityTreeDataNode) {
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
		return labelNodeId;
	}

	/**
	 * 
	 * @param rootEntity
	 * @param generatedQuery
	 * @return
	 */
	private List<Integer> getPrimaryKeysIndexes(EntityInterface rootEntity,
			IQuery generatedQuery) {
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
		return primaryKeyIndexesList;
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
	
	/**
	 * 
	 * @param expression
	 * @param rootNode
	 * @param query
	 * @return
	 */
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
