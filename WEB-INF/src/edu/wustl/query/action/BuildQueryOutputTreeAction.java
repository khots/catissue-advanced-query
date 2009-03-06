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
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ViewType;

/**
 * This class is invoked when user clicks on a node from the tree. It loads the data required for tree formation.
 * @author deepti_shelar
 */
public class BuildQueryOutputTreeAction extends Action
{
	
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String nodeId = request.getParameter(Constants.TREE_NODE_ID);
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		if(nodeId.endsWith(Constants.LABEL_TREE_NODE))
		{
			processLabelNodeClick(nodeId, request,jsonObjectList);
		}
		else
		{
             processDataNodeClick(nodeId,request,jsonObjectList);
		}
		//Set the response
		setResponse(response, jsonObjectList);
		return null;
	}


/**
 * 
 * @param rootData
 * @param primaryKeyValues
 * @return
 */
private String getClickedDataNodeKey(String rootData, String[] primaryKeyValues) 
{
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
	return labelNodeParentPrimaryKey;
}

/**
 * 
 * @param rootData
 * @param uniqueParentNode
 * @param uniqueCurrentNodeId
 * @param primaryKeySetData
 * @return
 */
private String createTreeNodeId(String rootData, String uniqueParentNode,
		String uniqueCurrentNodeId, StringBuffer primaryKeySetData) 
{
	String dataNodeId = "";
	String upiStr = Constants.NULL_ID;
	if(rootData.equalsIgnoreCase(Constants.NULL_ID))
	{
		//This is the case of click of root node 
	    String []strs =  primaryKeySetData.toString().split("@@");
	    upiStr = strs[0];
	    dataNodeId = upiStr + Constants.NODE_SEPARATOR + uniqueParentNode +  Constants.NODE_SEPARATOR + uniqueCurrentNodeId;
	}
	else
	{
		upiStr =  rootData;
		dataNodeId = upiStr + Constants.NODE_SEPARATOR + uniqueParentNode +  Constants.NODE_SEPARATOR + uniqueCurrentNodeId+ "_"+ primaryKeySetData;
	}
	return dataNodeId;
}

/**
 * 
 * @param primaryKeyIndexesList
 * @param labelNodeDataList
 * @param displayData
 * @param primaryKeySetData
 */
private void separateResultsViewData(List<Integer> primaryKeyIndexesList,
		List<Object> labelNodeDataList, StringBuffer displayData,
		StringBuffer primaryKeySetData) {
	for(int j=0; j<labelNodeDataList.size(); j++)
	{
		if(primaryKeyIndexesList.contains(j))
		{
			//primaryKeySetData +=  labelNodeDataList.get(j) + "@@";
			primaryKeySetData.append(labelNodeDataList.get(j)+"@@");
		}
		else
		{
			if(labelNodeDataList.get(j)== null)
			{
				displayData.append("");
			}
			else
			{
				//displayData += labelNodeDataList.get(j)+" ";
				displayData.append(labelNodeDataList.get(j)+" ");
			}
		}
	}
}


/**
 * 
 * @param nodeId
 * @param request
 * @param jsonObjectList
 * @throws Exception
 */
@SuppressWarnings("unchecked")
private void processDataNodeClick(String nodeId,HttpServletRequest request,List<JSONObject> jsonObjectList) throws Exception
{
	NodeId node = new NodeId(nodeId);
	String rootData = node.getRootData();
	String uniqueCurrentNodeId = node.getUniqueCurrentNodeId();
	String [] primaryKeyValues = node.getCurrentNodeData();
	HttpSession session = request.getSession();
	
	String labelNodeParentPrimaryKey = getClickedDataNodeKey(rootData,primaryKeyValues);
	
	//Retrieve the required session attributes
	int queryExecutionID =   (Integer)session.getAttribute("queryExecutionId");
	Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map)session.getAttribute(Constants.PATIENT_QUERY_UNIQUE_ID_MAP);
	IQuery patientDataQuery  = (IQuery)session.getAttribute(Constants.PATIENT_DATA_QUERY);
    
	OutputTreeDataNode labelTreeDataNode =  uniqueIdNodesMap.get(uniqueCurrentNodeId);		    
    int rootNodeExpd = labelTreeDataNode.getExpressionId();
    if(patientDataQuery != null)
    {
    	IConstraints constraints = patientDataQuery.getConstraints();
		IExpression expression = constraints.getExpression(rootNodeExpd);
		EntityInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		List<EntityInterface> mainEntityList = QueryAddContainmentsUtil.getAllMainObjects(patientDataQuery);
	   
		//Check if the entity, for which children nodes are to be shown , is main entity or not 
		Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap = IQueryParseUtil.getParentChildrensForaMainNode(labelTreeDataNode);
		if((mainEntityList.contains(entity)) && (!parentChildrenMap.isEmpty()))
	    {
	        //For main OutputTreeDataNode, get the map of all parent/children map 
	    	Set<OutputTreeDataNode> mainEntitiesKeySet = parentChildrenMap.keySet();
		    Iterator<OutputTreeDataNode> keySetItr = mainEntitiesKeySet.iterator();
		    while(keySetItr.hasNext())
		    {
		    	OutputTreeDataNode mainEntityTreeDataNode = keySetItr.next();
		    	EntityInterface mainEntity = mainEntityTreeDataNode.getOutputEntity().getDynamicExtensionsEntity();
		    	if(mainEntityTreeDataNode.getExpressionId() != labelTreeDataNode.getExpressionId() && mainEntityList.contains(mainEntity))
			    {
			    	
		    		//Here populate the new query details object
		    		QueryDetails queryDetails = new QueryDetails();
		    		queryDetails.setCurrentSelectedObject(mainEntityTreeDataNode);
		    		queryDetails.setQuery(patientDataQuery);
		    		queryDetails.setParentChildrenMap(parentChildrenMap);

		    		AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
		    		.getDefaultViewIQueryGenerator();
		    		IQuery generatedQuery = queryGenerator.createIQueryForTreeView(queryDetails);
		    		
		    		//IQuery generatedQuery = ResultsViewTreeUtil.generateIQuery(mainEntityTreeDataNode,parentChildrenMap,mainEntity,patientDataQuery);
			    	AbstractQueryUIManager abstractQueryUIManager =AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
			    	DataQueryResultsBean dataQueryResultsBean = abstractQueryUIManager.getData(queryExecutionID, rootData ,ViewType.TREE_VIEW); 
			    	List<List<Object>>  dataList = dataQueryResultsBean.getAttributeList();
			   				
			   		String displayName = Utility.getDisplayLabel(mainEntity.getName()) + " (" + dataList.size() + ")";
			   		String labelNodeId = getUniqueNodeID(uniqueIdNodesMap,mainEntityTreeDataNode);
			   		String dataNodeId = rootData + Constants.NODE_SEPARATOR + uniqueCurrentNodeId + Constants.UNDERSCORE + labelNodeParentPrimaryKey + Constants.NODE_SEPARATOR +labelNodeId + Constants.UNDERSCORE + Constants.LABEL_TREE_NODE;
			   		displayName = "<span class=\"content_txt\">" + displayName + "</span>";
			   				
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

	
/**
 * 	
 * @param nodeId
 * @param request
 * @param jsonObjectList
 * @throws Exception
 */

@SuppressWarnings("unchecked")
private void processLabelNodeClick(String nodeId,HttpServletRequest request,List<JSONObject> jsonObjectList) throws Exception
{
	NodeId node = new NodeId(nodeId);
	String rootData = node.getRootData();
	String uniqueCurrentNodeId = node.getUniqueCurrentNodeId();
	String uniqueParentNode = node.getUniqueParentNodeId();
	HttpSession session = request.getSession();
		
	//Retrieve the required session attributes
	int queryExecutionID =   (Integer)session.getAttribute("queryExecutionId");
	Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map<String, OutputTreeDataNode>)session.getAttribute(Constants.PATIENT_QUERY_UNIQUE_ID_MAP);
	IQuery patientDataQuery  = (IQuery)session.getAttribute(Constants.PATIENT_DATA_QUERY);
		
	//If node Id ends with Label, then it's label node
	OutputTreeDataNode labelTreeDataNode =  uniqueIdNodesMap.get(uniqueCurrentNodeId);
	IOutputEntity outputEntity = labelTreeDataNode.getOutputEntity();
	EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
		
	//Now for labelTreeDataNode, get All the parent /children Map from root 
	Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap = IQueryParseUtil.getParentChildrensForaMainNode(labelTreeDataNode);

	QueryDetails queryDetails = new QueryDetails();
	queryDetails.setCurrentSelectedObject(labelTreeDataNode);
	queryDetails.setQuery(patientDataQuery);
	queryDetails.setParentChildrenMap(parentChildrenMap);
	
	//Here we generate the iQuery
	AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
	.getDefaultViewIQueryGenerator();
	IQuery generatedQuery = queryGenerator.createIQueryForTreeView(queryDetails);
	
	//IQuery generatedQuery = ResultsViewTreeUtil.generateIQuery(labelTreeDataNode,parentChildrenMap,rootEntity,patientDataQuery);
		
	//Here we get the list of primary key indexes in the Output attribute list of generated IQuery
	List<Integer> primaryKeyIndexesList = getPrimaryKeysIndexes(rootEntity, generatedQuery);
		
	AbstractQueryUIManager abstractQueryUIManager =AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);

	DataQueryResultsBean dataQueryResultsBean = getDataqueryResultsBean(rootData, queryExecutionID,abstractQueryUIManager);;
	List<List<Object>>  dataList = dataQueryResultsBean.getAttributeList();
	if(dataList.size() >0)
	{
		for(int i=0; i< dataList.size(); i++)
		{
			List <Object> labelNodeDataList = dataList.get(i);
			StringBuffer displayData = new StringBuffer(""); 
			StringBuffer primaryKeySetData = new StringBuffer("");
			   
			//Separating the primary key data and Data to be displayed in the results tree
			separateResultsViewData(primaryKeyIndexesList,labelNodeDataList, displayData, primaryKeySetData);
				
			//Creating the Tree node Id
			String dataNodeId = createTreeNodeId(rootData,uniqueParentNode, uniqueCurrentNodeId,primaryKeySetData);
            String displayName = "<span class=\"content_txt\">"+  displayData +"</span>";	  
			String parentId = nodeId;
			JSONObject jsonObject = new JSONObject();
			jsonObject.append("identifier", dataNodeId);
			jsonObject.append("displayName",displayName);
			jsonObject.append("parentId",parentId);
			
			//populating the json object list
			jsonObjectList.add(jsonObject);
			}
		}
	}

	/**
	 * @param rootData
	 * @param queryExecutionID
	 * @param abstractQueryUIManager
	 * @return
	 * @throws QueryModuleException
	 */
	private DataQueryResultsBean getDataqueryResultsBean(String rootData, int queryExecutionID,
			AbstractQueryUIManager abstractQueryUIManager)throws QueryModuleException 
	{
		DataQueryResultsBean dataQueryResultsBean = null;
		if(rootData.equalsIgnoreCase(Constants.NULL_ID))
		{
			dataQueryResultsBean =  abstractQueryUIManager.getData(queryExecutionID, ViewType.TREE_VIEW);
		}
		else
		{
			dataQueryResultsBean = abstractQueryUIManager.getData(queryExecutionID, rootData ,ViewType.TREE_VIEW);
		}
		return dataQueryResultsBean;
	}
	


	/**
	 * 
	 * @param uniqueIdNodesMap
	 * @param mainEntityTreeDataNode
	 * @return
	 */
	private String getUniqueNodeID
	(
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
	
}
