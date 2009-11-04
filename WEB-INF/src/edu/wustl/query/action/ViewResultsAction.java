
package edu.wustl.query.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.category.CategoryProcessor;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.tree.QueryTreeNodeData;
import edu.wustl.common.util.Utility;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;

/**
 * This class handles the tree generation of the view results action
 * @author baljeet_dhindhwal
 */

public class ViewResultsAction extends AbstractQueryBaseAction
{

	@Override
	@SuppressWarnings("unchecked")
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		Long queryExecutionID = 0L;
		String dataQueryId = request.getParameter("dataQueryId");
		String workflowId = request.getParameter(Constants.WORKFLOW_ID);
		String workflowName = request.getParameter("workflowName");
		Long iqueryId = Long.valueOf(0);
		if (dataQueryId != null && !dataQueryId.equals(""))
		{
			iqueryId = Long.valueOf(dataQueryId);
		}
		String qid = request.getParameter(Constants.EXECUTION_ID_OF_QUERY);
		if (qid != null)
		{
			queryExecutionID = Long.valueOf(qid);
		}
		//	 	IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
		//				.getValue("app.bizLogicFactory"), "getBizLogic",
		//				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);

		//Retrieve the "Get Patient Data" Query depending on the iQuery ID
		final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
				.getName(), Constants.ID, iqueryId);

		IQuery getPatientDataQuery = null;
		if (queryList != null && !queryList.isEmpty())
		{
			getPatientDataQuery = queryList.get(0);
		}
		String allQueriesConitionStr = request.getParameter(Constants.QUERY_CONDITION_STRING);

		Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap = (Map<Long, Map<Integer, ICustomFormula>>) session
				.getAttribute(Constants.QUERY_CUSTOM_FORMULA_MAP);
		//Creating a new jason Object
		IQueryUpdationUtil.updateQueryForParameters(getPatientDataQuery, allQueriesConitionStr,
				eachQueryCFMap);
		//process the category query & create the outpuTreeNodeList which is put into the session
		IQuery updatedQuery = updateCategoryQuery(getPatientDataQuery,request);
		//Updating the updated query again for default values before execution

		List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>) session
				.getAttribute(Constants.SAVE_TREE_NODE_LIST);
		OutputTreeDataNode rootNode = rootOutputTreeNodeList.get(0);
		IOutputEntity outputEntity = rootNode.getOutputEntity();
		QueryableObjectInterface rootEntity = outputEntity.getDynamicExtensionsEntity();

		Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap = IQueryParseUtil
				.getParentChildrensForaMainNode(rootNode);

		//Here populate the new query details object
		QueryDetails queryDetails = new QueryDetails();
		queryDetails.setCurrentSelectedObject(rootNode);
		queryDetails.setQuery(updatedQuery);
		queryDetails.setParentChildrenMap(parentChildrenMap);
		// Data Query ID set in session, to log query id in Audit Log
		session.setAttribute(Constants.DATA_QUERY_ID, iqueryId);
		AbstractQueryUIManager abstractQueryUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request,
						updatedQuery);
		AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
				.getDefaultViewIQueryGenerator();
		QueryPrivilege privilege = new QueryPrivilege();
		if (session.getAttribute(Constants.QUERY_PRIVILEGE) != null)
		{
			privilege = (QueryPrivilege) (session.getAttribute(Constants.QUERY_PRIVILEGE));
		}
		IQuery generatedQuery = queryGenerator.createIQueryForTreeView(queryDetails, privilege
				.isSecurePrivilege());

		//IQuery generatedQuery = ResultsViewTreeUtil.generateIQuery(rootNode,parentChildrenMap,rootEntity,getPatientDataQuery);
		abstractQueryUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);

		//Get Data for generated IQuery
		//DataQueryResultsBean  dataQueryResultsBean = abstractQueryUIManager.executeDataQuery(queryExecutionID, ViewType.TREE_VIEW);
		//List<Object> dataList = dataQueryResultsBean.getDataList();

		// Get UPI count for count query
		Count upiCount = abstractQueryUIManager.getCount(queryExecutionID, privilege);

		//set person upi count in session if too few records (<10) and user does not have privilege to view those records.
		if (privilege.isSecurePrivilege() && upiCount.getCount() < Variables.resultLimit)
		{
			session.setAttribute(Constants.PERSON_UPI_COUNT, upiCount.getCount());
			//dataList = new ArrayList<Object>();
		}

		//Get the unique node Id map
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session
		.getAttribute(Constants.ID_NODES_MAP);

		//Setting some session attributes
		//session.setAttribute(Constants.ID_NODES_MAP, uniqueIdNodesMap);
		session.setAttribute(Constants.WORKFLOW_ID, workflowId);
		session.setAttribute(Constants.EXECUTED_FOR_WFID,workflowId);
		session.setAttribute(Constants.WORKFLOW_NAME, workflowName);
		session.setAttribute(Constants.EXECUTION_ID_OF_QUERY, queryExecutionID);
		session.setAttribute(Constants.DATA_ROOT_OUTPUT_LIST, rootOutputTreeNodeList);
		session.setAttribute(Constants.PATIENT_DATA_QUERY, updatedQuery);
		session.setAttribute(Constants.EXECUTED_FOR_PROJECT, request
				.getParameter(Constants.EXECUTED_FOR_PROJECT));
		Integer expLimit = Integer.valueOf(Variables.maxtreeExpansionLimit);
		session.setAttribute(Constants.TREE_EXPANSION_LIMIT,expLimit);

		String labelNodeId = getUniqueNodeID(rootNode, uniqueIdNodesMap);
		Vector<QueryTreeNodeData> treeDataVector = new Vector<QueryTreeNodeData>();
		String displayName = Utility.getDisplayLabel(rootEntity.getName());
		QueryTreeNodeData labelNode = getResultsTreeLabelNode(rootEntity, labelNodeId, displayName);
		treeDataVector.add(labelNode);

		//Note, tree no is set hardcoded
		Long noOfTrees = Long.valueOf(1);
		session.setAttribute(Constants.NO_OF_TREES, noOfTrees);
		String key = Constants.TREE_DATA + "_" + 0;
		session.setAttribute(key, treeDataVector);
		return mapping.findForward(Constants.SUCCESS);
	}

	/**
	 * It will convert the category query into normal Query & then
	 * put the rootOutputTreeNode & uniqueNodeIdMap in the session for use.
	 * @param getPatientDataQuery data Query
	 * @param request httpRequest
	 * @return updated Query
	 * @throws QueryModuleException exception.
	 */
	private IQuery updateCategoryQuery(IQuery getPatientDataQuery,HttpServletRequest request) throws QueryModuleException
	{
		CategoryProcessor processor = new CategoryProcessor(getPatientDataQuery);
		IQuery generatedQuery = processor.processCategory();
		edu.wustl.query.util.global.Utility.createRootOutPutTree(generatedQuery, request);
		return generatedQuery;
	}
	/**
	 *
	 * @param rootNode
	 * @param uniqueIdNodesMap
	 * @return
	 */
	private String getUniqueNodeID(OutputTreeDataNode rootNode,
			Map<String, OutputTreeDataNode> uniqueIdNodesMap)
	{
		String labelNodeId = "";

		//Set<String> uniqueKeySet = uniqueIdNodesMap.keySet();
		//Iterator<String> keyItr = uniqueKeySet.iterator();
		Iterator<Map.Entry<String, OutputTreeDataNode>> entryItr = uniqueIdNodesMap.entrySet()
				.iterator();
		while (entryItr.hasNext())
		{
			Map.Entry<String, OutputTreeDataNode> entry = entryItr.next();

			OutputTreeDataNode treeDataNode = entry.getValue(); //uniqueIdNodesMap.get(keyId);
			if (treeDataNode.getExpressionId() == rootNode.getExpressionId())
			{
				labelNodeId = entry.getKey();
				break;
			}
		}
		return labelNodeId;
	}

	/**
	 *
	 * @param labelEntity
	 * @param labelNodeId
	 * @param displayName
	 * @return
	 */
	private QueryTreeNodeData getResultsTreeLabelNode(QueryableObjectInterface labelEntity,
			String labelNodeId, String displayName)
	{
		String[] strs = labelNodeId.split(Constants.NODE_DATA_SEPARATOR);
		String treeNo = strs[0];
		String name = labelEntity.getName();
		String nodeId = Constants.NULL_ID + Constants.NODE_SEPARATOR + treeNo
				+ Constants.NODE_DATA_SEPARATOR + Constants.NULL_ID + Constants.NODE_DATA_SEPARATOR
				+ Constants.NULL_ID + Constants.NODE_SEPARATOR + labelNodeId + Constants.NODE_DATA_SEPARATOR
				+ Constants.LABEL_TREE_NODE;
		//displayName = "<font size='2' color='#297cc7' face='Arial'><b>" + displayName
		//	+ "</b></font>";
		QueryTreeNodeData treeNode = new QueryTreeNodeData();
		treeNode.setIdentifier(nodeId);
		treeNode.setObjectName(name);
		treeNode.setDisplayName(displayName);
		treeNode.setParentIdentifier(Constants.ZERO_ID);
		treeNode.setParentObjectName("");
		return treeNode;
	}
}
