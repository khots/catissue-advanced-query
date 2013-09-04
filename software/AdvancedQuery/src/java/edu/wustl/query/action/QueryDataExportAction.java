package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;

import edu.wustl.common.action.SecureAction;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.SendFile;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionForm.QueryAdvanceSearchForm;
import edu.wustl.query.bizlogic.ExportQueryBizLogic;
import edu.wustl.query.bizlogic.QueryDataExportService;
import edu.wustl.query.dto.QueryExportDTO;
import edu.wustl.query.querysuite.QueryDataExportTask;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryDetails;

public class QueryDataExportAction extends SecureAction {

	public ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		
		SessionDataBean sessionDataBean = (SessionDataBean)session.getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		
		QueryExportDTO queryExportDTO = new QueryExportDTO(); 
		queryExportDTO.setCsmUserId(sessionDataBean.getCsmUserId());
		
		//
		// file name is not empty when user clicks on link to download exported data
		//
		String fileName =(String) request.getParameter("fileName");
		if(fileName != null && !fileName.isEmpty()){
			queryExportDTO.setFileName(fileName);
			QueryDataExportTask dataExportTask = new QueryDataExportTask(queryExportDTO);
			String filePath = dataExportTask.getActualFilePath();
			SendFile.sendFileToClient(response, filePath, AQConstants.SEARCH_RESULT,
					"application/download", false);
			return null;
		}
		
		QueryDetails queryDetails = new QueryDetails(session);
		queryExportDTO.setQueryDetails(queryDetails);
		
		QueryAdvanceSearchForm searchForm = (QueryAdvanceSearchForm) form;
		queryExportDTO.setFormValues(searchForm.getValues());
		QueryDataExportService instance = QueryDataExportService.getInstance();
	
		queryExportDTO.setExportAll(Boolean.parseBoolean(request.getParameter(AQConstants.CHECK_ALL_ACROSS_ALL_PAGES)));
		if(queryExportDTO.isExportAll())
		{
			if(!instance.isAlreadyInProgress(queryExportDTO)){
				initQueryExportDTO (session, response, queryExportDTO);
				instance.exportAllData(queryExportDTO);
				response.getWriter().write(ApplicationProperties.getValue("query.data.export.initiated.message"));
			} else {
				response.getWriter().write(ApplicationProperties.getValue("query.data.export.already.in.progress"));
			}
		
		} else {
			initQueryExportDTO (session, response, queryExportDTO);
			List<List<String>> dataList = parseJson(request);
			instance.exportClientSideData(queryExportDTO, response, dataList);
			QueryDataExportTask dataExportTask = new QueryDataExportTask(queryExportDTO);
			String filePath = dataExportTask.getActualFilePath();
			SendFile.sendFileToClient(response, filePath, AQConstants.SEARCH_RESULT,
					"application/download");
		}
		return null;
	}
	
	private void initQueryExportDTO(HttpSession session, HttpServletResponse response, QueryExportDTO queryExportDTO) 
			throws MultipleRootsException, DAOException 
	{		
		List<String> columnList = (List<String>) session.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);
		queryExportDTO.setColumnList(columnList);
		
		queryExportDTO.setFileName(UUID.randomUUID().toString());
		getExportList(session, queryExportDTO);
		
		if(queryExportDTO.isExportAll()){
		
			setQuerySessionData(session, queryExportDTO);
			
			String isSimpleSearch = (String) session.getAttribute(AQConstants.IS_SIMPLE_SEARCH);	
			queryExportDTO.setSimpleSearch(isSimpleSearch);
		
			List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>) session
					.getAttribute(AQConstants.SAVE_TREE_NODE_LIST);
			queryExportDTO.setRootOutputTreeNodeList(rootOutputTreeNodeList);
		}
	}
	
	/**
	 * @param session session
	 * @return exportList
	 * @throws MultipleRootsException MultipleRootsException
	 */
	private void getExportList(HttpSession session, QueryExportDTO queryExportDTO)
			throws MultipleRootsException
	{
		IParameterizedQuery query = (IParameterizedQuery) session.getAttribute(AQConstants.QUERY_OBJECT);
		List<List<String>> exportList = new ArrayList<List<String>>();
		if(query != null)
		{
			ExportQueryBizLogic exportBizLogic = new ExportQueryBizLogic();
			exportBizLogic.exportDetails(query, exportList);
			queryExportDTO.setQueryName(query.getName());
			queryExportDTO.setExportList(exportList);
		}
	}
	
	private List<List<String>> parseJson(HttpServletRequest request) throws JSONException
	{
		List<List<String>> dataList = new ArrayList<List<String>>();
		
		String jsonDataStr = request.getParameter("jsonData");
		JSONArray jsonArray = new JSONArray(jsonDataStr);
		
		for(int i = 0; i < jsonArray.length(); i++){ 
			String row =  jsonArray.getString(i);
			row = row.replace("[", "").replace("]", "");
			String [] cols = row.split(",") ;
			List<String> colList = new ArrayList<String>(Arrays.asList(cols));
			dataList.add(colList);
		}
		
		return dataList;
	}
	
	private void setQuerySessionData(HttpSession session, QueryExportDTO queryExportDTO) throws MultipleRootsException
	{
		int actualNoOfRec = 0;
		int recordsPerPage;
		boolean isDefinedView = false;
		SelectedColumnsMetadata selectedColumnsMetadata =
				(SelectedColumnsMetadata)session.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		
		if(selectedColumnsMetadata != null)
		{
			actualNoOfRec = selectedColumnsMetadata.getActualTotalRecords();
			isDefinedView = selectedColumnsMetadata.isDefinedView();
		}

		queryExportDTO.setDefinedView(isDefinedView);
	
		if (queryExportDTO.isExportAll())
		{
			if(actualNoOfRec == 0)
			{
				String totalRecords = (String) session.getAttribute(AQConstants.FETCH_RECORD_SIZE);
				recordsPerPage = Integer.parseInt(totalRecords);
			}
			else
			{
				recordsPerPage = actualNoOfRec; 
			}
		}
		else
		{
			if(actualNoOfRec ==0)
			{
				String recordsPerPageStr = (String) session.getAttribute(AQConstants.RESULTS_PER_PAGE);
				recordsPerPage = Integer.parseInt(recordsPerPageStr); 
			}
			else	
			{
				QuerySessionData querySessionData = (QuerySessionData) session.getAttribute(AQConstants.QUERY_SESSION_DATA);
				recordsPerPage = querySessionData.getRecordsPerPage();
			}
		}	
		
		QuerySessionData querySessionData = (QuerySessionData) session 
				.getAttribute(AQConstants.QUERY_SESSION_DATA);
	
		if(session.getAttribute(AQConstants.QUERY_WITH_FILTERS) != null) {
			querySessionData.setSql((String)session.getAttribute(AQConstants.QUERY_WITH_FILTERS));
		}
		
		querySessionData.setRecordsPerPage(recordsPerPage);	
		queryExportDTO.setQuerySessionData(querySessionData);
	}
}
