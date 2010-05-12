
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.action.SecureAction;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * When user is done with selecting the columns to be shown in results, this action is invoked.
 * This action creates meta data for selected columns and keeps it in session for further reference.
 *
 * @author deepti_shelar
 *
 */
public class ConfigureGridViewAction extends SecureAction
{
	/**
	 * creates meta data for selected columns and keeps it in session for further reference.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	protected ActionForward executeSecureAction(ActionMapping mapping,
		ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		CategorySearchForm categorySearchForm = (CategorySearchForm) form;
		HttpSession session = request.getSession();
		Map<String, IOutputTerm> outputTermsColumns =
			(Map<String, IOutputTerm>)session.getAttribute(AQConstants.OUTPUT_TERMS_COLUMNS);
		IQuery query = (IQuery)session.getAttribute(AQConstants.QUERY_OBJECT);
		QueryDetails queryDetailsObj = new QueryDetails(session);
		SelectedColumnsMetadata selectedColumnsMetadata =
			(SelectedColumnsMetadata)session.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		OutputTreeDataNode currentSelectedObject = selectedColumnsMetadata.getCurrentSelectedObject();
		QuerySessionData querySessionData =
			(QuerySessionData) session.getAttribute(AQConstants.QUERY_SESSION_DATA);
		String recordsPerPageStr = (String) session.getAttribute(AQConstants.RESULTS_PER_PAGE);
		int recordsPerPage = Integer.valueOf(recordsPerPageStr);

		boolean hasConditionOnIdentifiedData = Utility.isConditionOnIdentifiedField(query);

		DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
		QueryOutputSpreadsheetBizLogic queryOutputSpreadsheetBizLogic =
			new QueryOutputSpreadsheetBizLogic();

		String sql = querySessionData.getSql();
		session.removeAttribute(AQConstants.EXPORT_DATA_LIST);
		session.removeAttribute(AQConstants.ENTITY_IDS_MAP);

		Map spreadSheetDataMap = new HashMap();
		List<String> definedColumnsList = new ArrayList<String>();
		List<NameValueBean> selectedCNVBList =
			categorySearchForm.getSelColNVBeanList();
		String operation = (String) request.getParameter(AQConstants.OPERATION);
		if (operation==null)
		{
			operation = AQConstants.FINISH;
		}
		if (operation.equalsIgnoreCase(AQConstants.FINISH))
		{
			selectedColumnsMetadata.setDefinedView(true);
			Map<Long, QueryResultObjectDataBean> queryResultObjecctDataMap =
				new HashMap<Long, QueryResultObjectDataBean>();
			defineGridViewBizLogic.getSelectedColumnsMetadata(categorySearchForm,
					queryDetailsObj, selectedColumnsMetadata,query.getConstraints());
			StringBuffer selectedColumnNames = new StringBuffer();
			String nodeData = getClickedNodeData(sql);
			definedColumnsList = defineGridViewBizLogic.getSelectedColumnList(categorySearchForm,
				selectedColumnsMetadata.getSelectedAttributeMetaDataList(), selectedColumnNames,
				queryResultObjecctDataMap, queryDetailsObj, outputTermsColumns,nodeData);
			spreadSheetDataMap.put(AQConstants.SPREADSHEET_COLUMN_LIST, definedColumnsList);
			// gets the message and sets it in the session.
			String sqlForSelectedColumns =
			defineGridViewBizLogic.createSQLForSelectedColumn(selectedColumnNames.toString(), sql);
			querySessionData = queryOutputSpreadsheetBizLogic.getQuerySessionData(queryDetailsObj,
					recordsPerPage, 0, spreadSheetDataMap, sqlForSelectedColumns,
					queryResultObjecctDataMap, hasConditionOnIdentifiedData);
			selectedCNVBList = categorySearchForm.getSelColNVBeanList();
			session.setAttribute(AQConstants.DEFINE_VIEW_RESULT_MAP,
					queryResultObjecctDataMap);
			spreadSheetDataMap.put(AQConstants.DEFINE_VIEW_RESULT_MAP,
					queryResultObjecctDataMap);
			spreadSheetDataMap.put(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP,
					session.getAttribute(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP));
		}
		else if (operation.equalsIgnoreCase(AQConstants.BACK))
		{
			Map<Long, QueryResultObjectDataBean> queryResultObjecctDataMap =
			populateMapForBackOperation(session, selectedColumnsMetadata, spreadSheetDataMap);
			selectedCNVBList =
				selectedColumnsMetadata.getSelColNVBeanList();
			categorySearchForm.setSelColNVBeanList(selectedCNVBList);
			definedColumnsList = (List<String>)
			session.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);
			spreadSheetDataMap.put(AQConstants.SPREADSHEET_COLUMN_LIST, definedColumnsList);
			querySessionData = queryOutputSpreadsheetBizLogic.getQuerySessionData(queryDetailsObj,
					recordsPerPage, 0, spreadSheetDataMap, sql, queryResultObjecctDataMap,
					hasConditionOnIdentifiedData);
		}
		else if (operation.equalsIgnoreCase(AQConstants.RESTORE))
		{
			Map<Long, QueryResultObjectDataBean> queryResultObjecctDataMap =
				new HashMap<Long, QueryResultObjectDataBean>();
			selectedColumnsMetadata.setDefinedView(false);
			defineGridViewBizLogic.getColumnsMetadataForSelectedNode
			(currentSelectedObject,selectedColumnsMetadata,query.getConstraints());
			StringBuffer selectedColumnNames = new StringBuffer();
			//Restoring to the default view.
			selectedColumnsMetadata.setSelectedOutputAttributeList(new ArrayList<IOutputAttribute>());
			String nodeData = getClickedNodeData(sql);
			definedColumnsList = defineGridViewBizLogic.getSelectedColumnList(categorySearchForm,
			selectedColumnsMetadata.getSelectedAttributeMetaDataList(),selectedColumnNames,
			queryResultObjecctDataMap, queryDetailsObj,outputTermsColumns,nodeData);
			String sqlForSelectedColumns = defineGridViewBizLogic.createSQLForSelectedColumn
			(selectedColumnNames.toString(), sql);
			spreadSheetDataMap.put(AQConstants.SPREADSHEET_COLUMN_LIST, definedColumnsList);
			querySessionData = queryOutputSpreadsheetBizLogic.getQuerySessionData(queryDetailsObj,
					recordsPerPage, 0, spreadSheetDataMap, sqlForSelectedColumns,
					queryResultObjecctDataMap, hasConditionOnIdentifiedData);
			selectedCNVBList = null;
			spreadSheetDataMap.put(AQConstants.DEFINE_VIEW_RESULT_MAP,
					queryResultObjecctDataMap);
			spreadSheetDataMap.put(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP,
					queryResultObjecctDataMap);
		}
		spreadSheetDataMap.put(AQConstants.SPREADSHEET_COLUMN_LIST, definedColumnsList);
		selectedColumnsMetadata.setSelColNVBeanList(selectedCNVBList);
		selectedColumnsMetadata.setCurrentSelectedObject(currentSelectedObject);
		spreadSheetDataMap.put(AQConstants.QUERY_SESSION_DATA, querySessionData);
		spreadSheetDataMap.put(AQConstants.SELECTED_COLUMN_META_DATA,selectedColumnsMetadata);
		spreadSheetDataMap.put(AQConstants.MAIN_ENTITY_MAP, queryDetailsObj.getMainEntityMap());
		QueryModuleUtil.setGridData(request, spreadSheetDataMap);
		return mapping.findForward(AQConstants.SUCCESS);
	}

	/**
	 * @param session session
	 * @param selectedColumnsMetadata selectedColumnsMetadata
	 * @param spreadSheetDataMap spreadSheetDataMap
	 * @return queryResultObjecctDataMap
	 */
	private Map<Long, QueryResultObjectDataBean> populateMapForBackOperation(
			HttpSession session,
			SelectedColumnsMetadata selectedColumnsMetadata,
			Map spreadSheetDataMap)
	{
		Map<Long, QueryResultObjectDataBean> queryResultObjecctDataMap =
			(Map<Long, QueryResultObjectDataBean>)session.getAttribute
			(AQConstants.DEFINE_VIEW_RESULT_MAP);
		spreadSheetDataMap.put(AQConstants.DEFINE_VIEW_RESULT_MAP,
				queryResultObjecctDataMap);
		if(!selectedColumnsMetadata.isDefinedView())
		{
			selectedColumnsMetadata.setDefinedView(false);
			queryResultObjecctDataMap = (Map<Long, QueryResultObjectDataBean>)
			session.getAttribute(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP);
			spreadSheetDataMap.put(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP,
					queryResultObjecctDataMap);
		}
		return queryResultObjecctDataMap;
	}

	/**
	 * Get the data for the node that the user clicks.
	 * @param sql query
	 * @return clickedData clickedData
	 */
	private String getClickedNodeData(String sql)
	{
		int index = sql.lastIndexOf('=');
		String clickedData = "";
		if(index != -1)
		{
			clickedData = sql.substring(index +1,sql.length());
		}
		return clickedData;
	}
}
