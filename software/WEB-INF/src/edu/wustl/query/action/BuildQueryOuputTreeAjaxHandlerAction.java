/**
 *
 */

package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.query.pvmanager.impl.MedLookUpManager;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.queryexecutionmanager.DataQueryResultStatus;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.DEUtility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ViewType;

/**
 * @author rinku_rohra
 *
 */
public class BuildQueryOuputTreeAjaxHandlerAction extends AbstractQueryBaseAction
{
	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(BuildQueryOuputTreeAjaxHandlerAction.class);

	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String nodeId = (String) session.getAttribute(Constants.TREE_NODE_ID);
		String nodeType = (String) session.getAttribute(Constants.TREE_NODE_TYPE);
		Thread.sleep(Variables.ajaxCallSleepTime);
		if (nodeType != null && nodeType.equals(Constants.LABEL_TREE_NODE))
		{
			getDataForLableNode(request, response);
		}
		else
		{
			getDataforDataNode(nodeId, request, response);
		}
		return null;
	}

	/**
	 * @param request http request
	 * @param response http response
	 * @throws QueryModuleException query exception
	 * @throws JSONException json exception
	 * @throws IOException IO exception
	 */
	private void getDataForLableNode(HttpServletRequest request, HttpServletResponse response)
			throws QueryModuleException, JSONException, IOException
	{

		HttpSession session = request.getSession();
		Long dataQueryExecId = (Long) session.getAttribute(Constants.TREEVIEW_DQ_EXECUTION_ID);
		String responseString = "";
		if (dataQueryExecId != null)
		{
			AbstractQueryUIManager abstrQUIMangr;
			abstrQUIMangr = AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();

			DataQueryResultsBean dataQResultBean = getDataqueryResultsBean(dataQueryExecId,
					abstrQUIMangr);

			boolean isstatusmessage = checkForStatusMessage(dataQResultBean);

			if (isstatusmessage)
			{
				responseString = setResponseString(dataQResultBean);

			}
			else
			{

				List<List<Object>> dataList = dataQResultBean.getAttributeList();
				responseString = generateResponseString(session, dataQResultBean, dataList);
			}
		}
		response.getWriter().write(responseString);
	}

	/**
	 * @param dataQResultBean bean
	 * @return isstatusmessage
	 */
	private boolean checkForStatusMessage(DataQueryResultsBean dataQResultBean)
	{
		boolean isstatusmessage = false;

		if (dataQResultBean == null
				|| (dataQResultBean.getResultStatus().equals(DataQueryResultStatus.NO_MORE_RECORDS)
						|| dataQResultBean.getResultStatus().equals(
								DataQueryResultStatus.NO_RECORDS_FOUND)
						|| dataQResultBean.getResultStatus().equals(
						DataQueryResultStatus.WAIT_FOR_NEXT_RECORD) || dataQResultBean
						.getResultStatus().equals(DataQueryResultStatus.TOO_FEW_RECORDS)))
		{

			isstatusmessage = true;
		}

		return isstatusmessage;
	}

	/**
	 * @param session http session.
	 * @param dataQResultBean data result bean
	 * @param dataList data list
	 * @return response string
	 * @throws JSONException json exception
	 */
	private String generateResponseString(HttpSession session,
			DataQueryResultsBean dataQResultBean, List<List<Object>> dataList) throws JSONException
	{
		String responseString;
		QueryableObjectInterface rootEntity = (QueryableObjectInterface) session
		.getAttribute(Constants.ROOT_ENTITY);
		if(rootEntity.isTagPresent(Constants.TAG_HIDE_CHILDREN_NODES))
		{
			responseString = Constants.RESPONSE_COMPLETED;
		}
		else
		{
			if (dataList == null || dataList.isEmpty())
			{
				responseString = dataQResultBean.getResultStatus().name();
			}
			else
			{
				responseString = createTreeNode(session, dataQResultBean, dataList);
			}
		}
		return responseString;
	}

	/**
	 * @param session http session
	 * @param dataQResultBean data result bean
	 * @param dataList data list
	 * @return response string
	 * @throws JSONException json exception
	 */
	private String createTreeNode(HttpSession session, DataQueryResultsBean dataQResultBean,
			List<List<Object>> dataList) throws JSONException
	{
		String responseString;
		String nodeId = (String) session.getAttribute(Constants.TREE_NODE_ID);
		QueryableObjectInterface rootEntity = (QueryableObjectInterface) session
				.getAttribute("rootEntity");
		IQuery generatedQuery = (IQuery) session.getAttribute("generatedQuery");
		List<IOutputAttribute> outputAttributesList = ((ParameterizedQuery) generatedQuery)
				.getOutputAttributeList();
		List<Integer> primryKeyIndList = getPrimaryKeysIndexes(rootEntity, generatedQuery);
		QueryPrivilege privilege = getPrivilege(session);
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		Iterator<List<Object>> itr = dataList.iterator();
		while (itr.hasNext())
		{

			List<Object> labelNodeDataList = itr.next();
			StringBuffer primaryKeySetData = new StringBuffer("");
			// creating primary key data set
			createPrimaryKeyData(primryKeyIndList, labelNodeDataList, primaryKeySetData);
			List<Object> newList = arrangeAttributes(outputAttributesList, labelNodeDataList);
			StringBuffer displayData = new StringBuffer("");
			AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
					.getDefaultViewIQueryGenerator();
			// Separating data to be displayed in the results tree
			separateResultsViewData(newList, displayData);
			displayData = queryGenerator.getFormattedOutputForTreeView(displayData, rootEntity,
					privilege.isSecurePrivilege());
			// Creating the Tree node Id
			JSONObject jsonObject = createJasonObject(dataQResultBean, nodeId, primaryKeySetData,
					displayData);
			/*
			 * response.getWriter().write( new JSONObject().put("childrenNodes",
			 * jsonObject).toString());
			 */
			jsonObjectList.add(jsonObject);
		}
		responseString = new JSONObject().put("childrenNodes", jsonObjectList).toString();
		return responseString;
	}

	/**
	 * @param dataQResultBean data result bean
	 * @param nodeId node identifier
	 * @param primaryKeySetData primary key data
	 * @param displayData data to be displayed on tree
	 * @return json object
	 * @throws JSONException json exception
	 */
	private JSONObject createJasonObject(DataQueryResultsBean dataQResultBean, String nodeId,
			StringBuffer primaryKeySetData, StringBuffer displayData) throws JSONException
	{
		NodeId node = new NodeId(nodeId);
		String dataNodeId = createTreeNodeId(node.getRootData(), node.getUniqueParentNodeId(), node
				.getUniqueCurrentNodeId(), primaryKeySetData);
		String displayName = displayData.toString();
		String parentId = nodeId;
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("identifier", dataNodeId);
		jsonObject.append("displayName", displayName);
		jsonObject.append("parentId", parentId);
		jsonObject.append("status", dataQResultBean.getResultStatus().name());
		return jsonObject;
	}

	/**
	 * This method gets privilege from session.
	 * @param session http session
	 * @return query privilege
	 */
	private QueryPrivilege getPrivilege(HttpSession session)
	{
		QueryPrivilege privilege = new QueryPrivilege();
		if (session.getAttribute(Constants.QUERY_PRIVILEGE) != null)
		{
			privilege = (QueryPrivilege) (session.getAttribute(Constants.QUERY_PRIVILEGE));
		}
		return privilege;
	}

	/**
	 * @param dataQResultBean data result bean
	 * @return response string
	 */
	private String setResponseString(DataQueryResultsBean dataQResultBean)
	{
		String responseString = "";
		if (dataQResultBean != null
				&& dataQResultBean.getResultStatus().equals(
						DataQueryResultStatus.WAIT_FOR_NEXT_RECORD))
		{
			try
			{
				// Thread.sleep(1000);
				responseString = dataQResultBean.getResultStatus().name();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
			}
		}
		else
		{
			if (dataQResultBean == null)
			{
				responseString = "wait";

			}
			else
			{
				responseString = dataQResultBean.getResultStatus().name();
			}
		}
		return responseString;
	}

	/**
	 *
	 * @param rootEntity root entity
	 * @param generatedQuery IQuery generated
	 * @return list of primary key indexes.
	 */
	private List<Integer> getPrimaryKeysIndexes(QueryableObjectInterface rootEntity,
			IQuery generatedQuery)
	{
		List<Integer> primaryKeyIndexesList = new ArrayList<Integer>();
		// Populate the primary Key indexes
		List<AttributeInterface> primaryKeyList = rootEntity.getEntity()
				.getPrimaryKeyAttributeCollection();
		//
		List<IOutputAttribute> outputAttributesList = ((ParameterizedQuery) generatedQuery)
				.getOutputAttributeList();

		for (int i = 0; i < primaryKeyList.size(); i++)
		{
			AttributeInterface attribute = primaryKeyList.get(i);
			preparePrimaryKeyIndexList(primaryKeyIndexesList, outputAttributesList, attribute);
		}
		return primaryKeyIndexesList;
	}

	/**
	 * @param primaryKeyIndexesList primary key indexes
	 * @param outputAttributesList output attributes
	 * @param attribute attribute
	 */
	private void preparePrimaryKeyIndexList(List<Integer> primaryKeyIndexesList,
			List<IOutputAttribute> outputAttributesList, AttributeInterface attribute)
	{
		for (int j = 0; j < outputAttributesList.size(); j++)
		{
			IOutputAttribute outputAttribute = outputAttributesList.get(j);
			if ((DEUtility.istagPresent(attribute, "Inherited") || outputAttribute.getAttribute()
					.getId() == attribute.getId())
					&& attribute.getEntity().equals(
							outputAttribute.getExpression().getQueryEntity()
									.getDynamicExtensionsEntity().getEntity()))
			{
				primaryKeyIndexesList.add(j);
				break;
			}

		}
	}

	/**
	 *
	 * @param primaryKeyIndexesList primary key index
	 * @param labelNodeDataList label node data list
	 * @param primaryKeySetData primary key data
	 */
	private void createPrimaryKeyData(List<Integer> primaryKeyIndexesList,
			List<Object> labelNodeDataList, StringBuffer primaryKeySetData)
	{
		for (int j = 0; j < labelNodeDataList.size(); j++)
		{
			if (primaryKeyIndexesList.contains(j))
			{
				primaryKeySetData.append(labelNodeDataList.get(j)).append("@@");
			}
		}
	}

	/**
	 * @param outputAttributesList output attributes
	 * @param oldList old list
	 * @return list of new objects
	 */
	private List<Object> arrangeAttributes(List<IOutputAttribute> outputAttributesList,
			List<Object> oldList)
	{
		List<Object> list = new ArrayList<Object>();
		// map that contains the data to be displayed and the position is
		// considered as key.
		Map<Integer, Object> resultOrderVsValue = getresultOrderVsValueMap(outputAttributesList,
				oldList);
		int ind = 0;
		// add data in new list in the order to be displayed in tree view.
		while (resultOrderVsValue.get(ind) != null)
		{
			list.add(resultOrderVsValue.get(ind));
			ind++;
		}
		// adding remaining data in new list like Age, Deid
		for (ind = outputAttributesList.size(); ind < oldList.size(); ind++)
		{
			list.add(oldList.get(ind));
		}
		return list;
	}

	/**
	 * @param outputAttributesList output attributes
	 * @param oldList old list
	 * @return result order map
	 */
	private Map<Integer, Object> getresultOrderVsValueMap(
			List<IOutputAttribute> outputAttributesList, List<Object> oldList)
	{
		Map<Integer, Object> resultOrderVsValue = new HashMap<Integer, Object>();
		for (int counter = 0; counter < outputAttributesList.size(); counter++)
		{
			QueryableAttributeInterface attribute = outputAttributesList.get(counter)
					.getAttribute();
			String value = setOldListNgetValue(outputAttributesList, oldList, counter, attribute);
			if (!"".equals(value))
			{
				resultOrderVsValue.put(Integer.parseInt(value), oldList.get(counter));
			}
		}
		return resultOrderVsValue;
	}

	/**
	 * @param outputAttributesList output attributes
	 * @param oldList old list of objects
	 * @param counter counter
	 * @param attribute attribute
	 * @return value
	 */
	private String setOldListNgetValue(List<IOutputAttribute> outputAttributesList,
			List<Object> oldList, int counter, QueryableAttributeInterface attribute)
	{
		String value = attribute.getTaggedValue(Constants.TAGGED_VALUE_RESULTVIEW);
		if (attribute.getName().equals(Constants.ID)
				&& attribute.getActualEntity().getName().equals(Constants.MED_ENTITY_NAME))
		{
			String conceptName = "";
			if (oldList.get(counter) != null)
			{
				conceptName = MedLookUpManager.instance().getConceptName(
					outputAttributesList.get(counter), (String) (oldList.get(counter)));
			}
			oldList.set(counter, conceptName);
			value = outputAttributesList.get(counter).getExpression().getQueryEntity()
					.getDynamicExtensionsEntity()
					.getTaggedValue(Constants.TAGGED_VALUE_RESULTORDER);
		}
		return value;
	}

	/**
	 * @param labelNodeDataList label node data list
	 * @param displayData display data
	 */
	private void separateResultsViewData(List<Object> labelNodeDataList, StringBuffer displayData)
	{
		for (int j = 0; j < labelNodeDataList.size(); j++)
		{
			if (labelNodeDataList.get(j) == null)
			{
				displayData.append(' ').append(Constants.DEFAULT_CONDITIONS_SEPARATOR);
			}
			else
			{
				displayData.append(labelNodeDataList.get(j).toString().trim()).append(
						Constants.DEFAULT_CONDITIONS_SEPARATOR);
			}
		}
	}

	/**
	 *
	 * @param rootData root data
	 * @param uniqueParentNode parent node
	 * @param uniqueCurrentNodeId current node id
	 * @param primaryKeySetData primary data
	 * @return data node id
	 */
	private String createTreeNodeId(String rootData, String uniqueParentNode,
			String uniqueCurrentNodeId, StringBuffer primaryKeySetData)
	{
		String dataNodeId = "";
		String upiStr = Constants.NULL_ID;
		if (rootData.equalsIgnoreCase(Constants.NULL_ID))
		{
			// This is the case of click of root node
			String[] strs = primaryKeySetData.toString().split("@@");
			upiStr = strs[0];
			dataNodeId = upiStr + Constants.NODE_SEPARATOR + uniqueParentNode
					+ Constants.NODE_SEPARATOR + uniqueCurrentNodeId;
		}
		else
		{
			upiStr = rootData;
			dataNodeId = upiStr + Constants.NODE_SEPARATOR + uniqueParentNode
					+ Constants.NODE_SEPARATOR + uniqueCurrentNodeId
					+ Constants.NODE_DATA_SEPARATOR + primaryKeySetData;
		}
		return dataNodeId;
	}

	/**
	 * @param dataQueryExecutionID query execution id
	 * @param abstractQueryUIManager UI manager
	 * @return data result bean
	 * @throws QueryModuleException query exception
	 */
	private DataQueryResultsBean getDataqueryResultsBean(Long dataQueryExecutionID,
			AbstractQueryUIManager abstractQueryUIManager) throws QueryModuleException
	{

		DataQueryResultsBean dataQueryResultsBean = null;

		dataQueryResultsBean = abstractQueryUIManager.getNextRecord(dataQueryExecutionID);

		return dataQueryResultsBean;
	}

	/**
	 * @param nodeId node identifier
	 * @param request http request
	 * @param response http response
	 * @throws Exception exception
	 */
	private void getDataforDataNode(String nodeId, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		NodeId node = new NodeId(nodeId);
		String rootData = node.getRootData();
		String[] primaryKeyValues = node.getCurrentNodeData();
		HttpSession session = request.getSession();
		String reponseString = "completed";

		String labelNodeParentPrimaryKey = getClickedDataNodeKey(rootData, primaryKeyValues);

		Map<String, List<OutputTreeDataNode>> parentChildrenListMap =
			(Map<String, List<OutputTreeDataNode>>) session
				.getAttribute(Constants.CHILDRENLISTMAP);
		if (parentChildrenListMap != null)
		{
			List<OutputTreeDataNode> childrenList = parentChildrenListMap.get(nodeId);

			if (childrenList != null && !childrenList.isEmpty())
			{
				OutputTreeDataNode mainEntityTreeDataNode = childrenList.get(0);
				childrenList.remove(0);
				session.setAttribute(Constants.CHILDRENLISTMAP, parentChildrenListMap);
				reponseString = getData(nodeId, request, labelNodeParentPrimaryKey,
						mainEntityTreeDataNode);
			}
		}
		response.getWriter().write(reponseString);
	}

	/**
	 * @param nodeId node identifier
	 * @param request http request
	 * @param labelNodeParentPrimaryKey parent primary key
	 * @param mainEntityTreeDataNode main entity tree data node
	 * @return response string
	 * @throws QueryModuleException query exception
	 * @throws JSONException json exception
	 */
	private String getData(String nodeId, HttpServletRequest request,
			String labelNodeParentPrimaryKey, OutputTreeDataNode mainEntityTreeDataNode)
			throws QueryModuleException, JSONException
	{
		String reponseString;
		NodeId node = new NodeId(nodeId);
		final HttpSession session = request.getSession();
		IQuery patientDataQuery = (IQuery) session.getAttribute(Constants.PATIENT_DATA_QUERY);
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map) session
				.getAttribute(Constants.ID_NODES_MAP);
		OutputTreeDataNode labelTreeDataNode = uniqueIdNodesMap.get(node.getUniqueCurrentNodeId());
		Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap = IQueryParseUtil
				.getParentChildrensForaMainNode(labelTreeDataNode);
		List<QueryableObjectInterface> mainEntityList = IQueryUpdationUtil
				.getAllMainObjects(patientDataQuery);
		QueryableObjectInterface mainEntity = mainEntityTreeDataNode.getOutputEntity()
				.getDynamicExtensionsEntity();

		if (mainEntityTreeDataNode.getExpressionId() != labelTreeDataNode.getExpressionId()
				&& mainEntityList.contains(mainEntity))
		{

			// Here populate the new query details object
			QueryDetails queryDetails = new QueryDetails();
			queryDetails.setCurrentSelectedObject(mainEntityTreeDataNode);
			queryDetails.setQuery(patientDataQuery);
			queryDetails.setParentChildrenMap(parentChildrenMap);

			int dataListSize = getDataSizeOfLabelNode(request, node.getRootData(), queryDetails);
			String displayName = Utility.getDisplayLabel(mainEntity.getName()) + " ("
					+ dataListSize + ")";
			String labelNodeId = getUniqueNodeID(uniqueIdNodesMap, mainEntityTreeDataNode);
			String dataNodeId = node.getRootData() + Constants.NODE_SEPARATOR
					+ node.getUniqueCurrentNodeId() + Constants.NODE_DATA_SEPARATOR
					+ labelNodeParentPrimaryKey + Constants.NODE_SEPARATOR + labelNodeId
					+ Constants.NODE_DATA_SEPARATOR + Constants.LABEL_TREE_NODE;
			// displayName = displayName + "</span>";
			reponseString = getDataListResponse(nodeId, displayName, dataNodeId);
		}
		else
		{
			reponseString = "wait";
		}
		return reponseString;
	}

	/**
	 * @param nodeId node identifier
	 * @param displayName display name
	 * @param dataNodeId data node id
	 * @return response string
	 * @throws JSONException json exception
	 */
	private String getDataListResponse(String nodeId, String displayName, String dataNodeId)
			throws JSONException
	{
		String reponseString;
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("identifier", dataNodeId);
		jsonObject.append("displayName", displayName);
		jsonObject.append("parentId", nodeId.trim());
		jsonObjectList.add(jsonObject);
		reponseString = new JSONObject().put("childrenNodes", jsonObjectList).toString();
		return reponseString;
	}

	/**
	 * @param request http request
	 * @param rootData root data
	 * @param queryDetails query details
	 * @return data list size
	 * @throws QueryModuleException query exception
	 */
	private int getDataSizeOfLabelNode(HttpServletRequest request, String rootData,
			QueryDetails queryDetails) throws QueryModuleException
	{

		HttpSession session = request.getSession();
		Long queryExecutionID = (Long) session.getAttribute("queryExecutionId"); // count
		// query
		// execution
		// Id
		AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
				.getDefaultViewIQueryGenerator();
		QueryPrivilege privilege = getPrivilege(session);
		IQuery generatedQuery = queryGenerator.createIQueryForTreeView(queryDetails, privilege
				.isSecurePrivilege());

		// IQuery generatedQuery =
		// ResultsViewTreeUtil.generateIQuery(mainEntityTreeDataNode
		// ,parentChildrenMap,mainEntity,patientDataQuery);
		AbstractQueryUIManager abstractQueryUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
		Long dataQueryExcutionID = abstractQueryUIManager.executeDataQuery(queryExecutionID,
				rootData, ViewType.TREE_VIEW);
		DataQueryResultStatus dataQueryStatus;
		int dataListSize = 0;
		if (dataQueryExcutionID != null)
		{
			do
			{

				DataQueryResultsBean dataQueryResultsBean = abstractQueryUIManager
						.getNextRecord(dataQueryExcutionID);
				List<List<Object>> dataList = dataQueryResultsBean.getAttributeList();
				if (dataList != null)
				{
					dataListSize += dataList.size();
				}
				dataQueryStatus = dataQueryResultsBean.getResultStatus();

			}
			while (!(dataQueryStatus.equals(DataQueryResultStatus.LAST_RECORD)
					|| dataQueryStatus.equals(DataQueryResultStatus.NO_MORE_RECORDS)
					|| dataQueryStatus.equals(DataQueryResultStatus.NO_RECORDS_FOUND)));
		}
		return dataListSize;
	}

	/**
	 * @param rootData root data
	 * @param primaryKeyValues primary key values
	 * @return label node primary keys
	 */
	private String getClickedDataNodeKey(String rootData, String[] primaryKeyValues)
	{
		StringBuffer labelNodeParentPrimaryKey = new StringBuffer();
		if (primaryKeyValues == null)
		{
			if (rootData != null)
			{
				labelNodeParentPrimaryKey.append(rootData).append("@@");
			}
		}
		else
		{
			for (int i = 0; i < primaryKeyValues.length; i++)
			{
				labelNodeParentPrimaryKey.append(labelNodeParentPrimaryKey).append(
						primaryKeyValues[i]).append("@@");
			}
		}
		return labelNodeParentPrimaryKey.toString();
	}

	/**
	 * @param uniqueIdNodesMap unique id map
	 * @param mainEntityTreeDataNode main entity tree node
	 * @return label node id
	 */
	private String getUniqueNodeID(Map<String, OutputTreeDataNode> uniqueIdNodesMap,
			OutputTreeDataNode mainEntityTreeDataNode)
	{
		String labelNodeId = "";
		// Set<String> uniqueKeySet = uniqueIdNodesMap.keySet();
		// Iterator<String> keyItr = uniqueKeySet.iterator();

		Iterator<Map.Entry<String, OutputTreeDataNode>> enntryItr = uniqueIdNodesMap.entrySet()
				.iterator();

		while (enntryItr.hasNext())
		{
			Map.Entry<String, OutputTreeDataNode> entry = enntryItr.next();
			// String keyId = keyItr.next();
			OutputTreeDataNode treeDataNode = entry.getValue(); // uniqueIdNodesMap
			// .get(keyId);
			if (treeDataNode.getExpressionId() == mainEntityTreeDataNode.getExpressionId())
			{
				labelNodeId = entry.getKey();
				break;
			}
		}
		return labelNodeId;
	}
}
