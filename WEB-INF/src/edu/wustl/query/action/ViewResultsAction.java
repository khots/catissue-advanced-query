package edu.wustl.query.action;

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

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.tree.QueryTreeNodeData;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.ResultsViewIQueryCreationUtil;
import edu.wustl.query.viewmanager.ViewType;

/**
 * 
 * @author baljeet_dhindhwal
 *
 */

public class ViewResultsAction extends Action 
{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{ 
		HttpSession session = request.getSession();
		int queryExecutionID = 0; 
		String dataQueryId = request.getParameter("dataQueryId");
		String workflowId= request.getParameter(Constants.WORFLOW_ID);
		Long iqueryId = Long.valueOf(0);
		if(dataQueryId != null && !dataQueryId.equals(""))
		{
			iqueryId = Long.valueOf(dataQueryId);
		}
		session.setAttribute(Constants.DATA_QUERY_ID,iqueryId);
		session.setAttribute(Constants.WORFLOW_ID, workflowId);
		
		//Get the  Query Execution Id  
		String qid = (String) request.getParameter("queryExecutionId");
		if(qid!=null)
		{
			queryExecutionID =Integer.parseInt(qid);
			session.setAttribute("queryExecutionId", queryExecutionID);
		}
	 	IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
				.getValue("app.bizLogicFactory"), "getBizLogic",
				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
	 	
	 	//Retrieve the "Get Patient Data" Query depending on the iQuery ID 
	 	final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
				.getName(), Constants.ID, iqueryId);
	 	
	 	IQuery getPatientDataQuery = null;
	 	if (queryList != null && !queryList.isEmpty())
	 	{
	 		getPatientDataQuery = queryList.get(0);
	 	}
	 	List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>)session.getAttribute(Constants.SAVE_TREE_NODE_LIST);
		session.setAttribute(Constants.PATIENT_QUERY_ROOT_OUT_PUT_NODE_LIST,rootOutputTreeNodeList);
		session.setAttribute(Constants.PATIENT_DATA_QUERY,getPatientDataQuery);
		OutputTreeDataNode rootNode = rootOutputTreeNodeList.get(0);
		IOutputEntity outputEntity = rootNode.getOutputEntity();
		EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
		
		Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap = 
			IQueryParseUtil.getParentChildrensForaMainNode(rootNode);
		
		//Now Create IQuery From Main Entity , adding containments till attributes with tagged values are found
		//Get the parent child map for containment of a  main Entity
		Map<EntityInterface, List<EntityInterface>> partentChildEntityMap = 
			ResultsViewIQueryCreationUtil.getAllParentChildrenMap(rootEntity); 
		
		//Once u got the parent child map, get the parent child map for tagged entities for results view, and populate the list for tagged entities
		Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = 
			ResultsViewIQueryCreationUtil.getTaggedEntitiesParentChildMap(partentChildEntityMap,rootEntity);
		
		//Here we get path list from Root Entity to parent of Tagged Entity for results view 
		Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = 
			ResultsViewIQueryCreationUtil.getPathsMapForTaggedEntity(taggedEntitiesParentChildMap,partentChildEntityMap);
		
		//Now Add all entities related to a tagged entity to IQuery
		IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
		IQuery generatedIQuery = ResultsViewIQueryCreationUtil.
		formIQuery(rootNode,eachTaggedEntityPathMap,getPatientDataQuery,m_queryObject);
		
		//Now update the formed IQuery with given conditions on children   
		ResultsViewIQueryCreationUtil.updateGeneratedQuery
		(generatedIQuery,parentChildrenMap,rootNode,getPatientDataQuery);
		
		//Set Query type as Data Query
		((Query)generatedIQuery).setType(QueryType.GET_DATA.type);
		AbstractQueryUIManager abstractQueryUIManager =
			AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedIQuery);
		
		//Get Data 
		DataQueryResultsBean  dataQueryResultsBean = 
			abstractQueryUIManager.getData(queryExecutionID, ViewType.TREE_VIEW);
		List<List<Object>> dataList = dataQueryResultsBean.getAttributeList();
		
		//Get the unique node Id map
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = QueryObjectProcessor
		.getAllChildrenNodes(rootOutputTreeNodeList);
		
		//Setting some session attributes
		session.setAttribute(Constants.PATIENT_QUERY_UNIQUE_ID_MAP, uniqueIdNodesMap);
	   
		String labelNodeId = getUniqueNodeID(rootNode, uniqueIdNodesMap);
		Vector<QueryTreeNodeData> treeDataVector = new Vector<QueryTreeNodeData>();
		String displayName = Utility.getDisplayLabel(rootEntity.getName()) + " (" + dataList.size() + ")";
		QueryTreeNodeData labelNode = getResultsTreeLabelNode(rootEntity,labelNodeId,displayName);	
		treeDataVector.add(labelNode);
	    
	    //Note, tree no is set hardcoded 
	    Long noOfTrees =  Long.valueOf(1);
	    session.setAttribute(Constants.NO_OF_TREES,noOfTrees);
	    String key = Constants.TREE_DATA + "_" + 0;
	    session.setAttribute(key,treeDataVector);
		
		return mapping.findForward(Constants.SUCCESS);
	}


	private String getUniqueNodeID(OutputTreeDataNode rootNode,
			Map<String, OutputTreeDataNode> uniqueIdNodesMap) {
		String labelNodeId = "";
		
		Set<String> uniqueKeySet = uniqueIdNodesMap.keySet();
		Iterator<String> keyItr = uniqueKeySet.iterator();
		while(keyItr.hasNext())
		{
			String keyId = keyItr.next();
			OutputTreeDataNode treeDataNode = uniqueIdNodesMap.get(keyId);
			if(treeDataNode.getExpressionId() == rootNode.getExpressionId())
			{
				labelNodeId = keyId;
				break;
			}
		}
		return labelNodeId;
	}

	private QueryTreeNodeData getResultsTreeLabelNode(EntityInterface labelEntity, String labelNodeId, String displayName)
	{
		String [] strs = labelNodeId.split("_");
		String treeNo = strs[0];
		String name = labelEntity.getName();
		String nodeId = Constants.NULL_ID + Constants.NODE_SEPARATOR +  treeNo + Constants.UNDERSCORE+ Constants.NULL_ID
		+Constants.UNDERSCORE + Constants.NULL_ID +Constants.NODE_SEPARATOR+labelNodeId + Constants.UNDERSCORE + Constants.LABEL_TREE_NODE;
		displayName = Constants.TREE_NODE_FONT + displayName + Constants.TREE_NODE_FONT_CLOSE;
		QueryTreeNodeData treeNode = new QueryTreeNodeData();
		treeNode.setIdentifier(nodeId);
		treeNode.setObjectName(name);
		treeNode.setDisplayName(displayName);
		treeNode.setParentIdentifier(Constants.ZERO_ID);
		treeNode.setParentObjectName("");
		return treeNode;
	}
}   
	
    
	
	
	
	


