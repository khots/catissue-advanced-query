
package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.action.SecureAction;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.ExportReport;
import edu.wustl.common.util.SendFile;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionForm.QueryAdvanceSearchForm;
import edu.wustl.query.bizlogic.ExportQueryBizLogic;
import edu.wustl.query.bizlogic.SpreadsheetDenormalizationBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryDetails;

public class SpreadsheetExportAction extends SecureAction
{
	/**
	 * This class exports the data of a spreadsheet to a file.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward object
	 */
	public ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		QueryAdvanceSearchForm searchForm = (QueryAdvanceSearchForm) form;
		HttpSession session = request.getSession();
		String isChkAllAcrossAll = (String) request.getParameter(AQConstants.CHECK_ALL_ACROSS_ALL_PAGES);
		List<List<String>> exportList = getExportList(session);
		//Extracting map from form bean which gives the serial numbers of selected rows
		Map map = searchForm.getValues();
		Object[] obj = map.keySet().toArray();
		List<String> columnList = (List<String>) session.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);
		List<List<String>> dataList = getDataList(request, session,isChkAllAcrossAll);
		QuerySessionData querySessionData = (QuerySessionData)session.getAttribute(AQConstants.QUERY_SESSION_DATA);
		QueryDetails queryDetails = new QueryDetails(session);
		boolean isDefineView = false;
		SelectedColumnsMetadata selectedColumnsMetadata =
			(SelectedColumnsMetadata)session.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		if(selectedColumnsMetadata != null && selectedColumnsMetadata.isDefinedView())
		{
			isDefineView = true;
			SpreadsheetDenormalizationBizLogic denormalizationBizLogic = new SpreadsheetDenormalizationBizLogic();
			dataList = denormalizationBizLogic.scanIQuery(queryDetails, dataList, selectedColumnsMetadata, querySessionData);
		}
		List tmpColumnList = new ArrayList();
		List tmpDataList = populateTemporaryList(columnList, dataList,tmpColumnList);
		columnList = tmpColumnList;
		dataList = tmpDataList;
		//Mandar 06-Apr-06 Bugid:1165 : Extra ID columns end. Adding first row(column names) to exportData
		if(!isDefineView)
		{
			exportList.add(columnList);
		}
		List<String> idIndexList = new ArrayList<String>();
		int columnsSize = columnList.size();
		Map<Integer, List<String>> entityIdsMap = (Map<Integer, List<String>>) session
		.getAttribute(AQConstants.ENTITY_IDS_MAP);
		if (isDefineView || (isChkAllAcrossAll != null && isChkAllAcrossAll.equalsIgnoreCase("true")))
		{
			for (int counter = 0; counter < dataList.size(); counter++)
			{
				List<String> list = dataList.get(counter);
				if(!isDefineView)
				{
					List<String> subList = list.subList(0, columnsSize);
					exportList.add(subList);
				}
				else
				{
					exportList.add(list);
				}
				populateIndexList(idIndexList, entityIdsMap, counter);
			}
		}
		else
		{
			for (int counter = 0; counter < obj.length; counter++)
			{
				int indexOf = obj[counter].toString().indexOf("_") + 1;
				int index = Integer.parseInt(obj[counter].toString().substring(indexOf));
				List<String> list = dataList.get(index);
				List<String> subList = list.subList(0, columnsSize);
				populateIndexList(idIndexList, entityIdsMap, index);
				exportList.add(subList);
			}

		}
		exportAndSend(response, session, exportList, idIndexList, entityIdsMap);
		return null;
	}

	/**
	 * @param session session
	 * @return exportList
	 * @throws MultipleRootsException MultipleRootsException
	 */
	private List<List<String>> getExportList(HttpSession session)
			throws MultipleRootsException
	{
		IParameterizedQuery query = (IParameterizedQuery) session.getAttribute(AQConstants.QUERY_OBJECT);
		List<List<String>> exportList = new ArrayList<List<String>>();
		if(query != null)
		{
			ExportQueryBizLogic exportBizLogic = new ExportQueryBizLogic();
			exportBizLogic.exportDetails(query,exportList);
		}
		return exportList;
	}

	/**
	 * @param idIndexList index list
	 * @param entityIdsMap map
	 * @param counter counter
	 */
	private void populateIndexList(List<String> idIndexList,
			Map<Integer, List<String>> entityIdsMap, int counter)
	{
		if (entityIdsMap != null && !entityIdsMap.isEmpty())
		{
			List<String> entityIdList = entityIdsMap.get(counter);
			idIndexList.addAll(entityIdList);
		}
	}

	/**
	 * @param columnList column List
	 * @param dataList dataList
	 * @param tmpColumnList column list
	 * @return tmpDataList
	 */
	private List populateTemporaryList(List<String> columnList,
			List<List<String>> dataList, List tmpColumnList)
	{
		int idColCount = setIdColumnCount(columnList);
		// remove ID columns
		for (int cnt = 0; cnt < (columnList.size() - idColCount); cnt++)
		{
			tmpColumnList.add(columnList.get(cnt));
		}
		// datalist filtration for ID data.
		List tmpDataList = new ArrayList();
		for (int dataListCnt = 0; dataListCnt < dataList.size(); dataListCnt++)
		{
			List tmpList = (List) dataList.get(dataListCnt);
			List tmpNewList = new ArrayList();
			for (int cnt = 0; cnt < (tmpList.size() - idColCount); cnt++)
			{
				tmpNewList.add(tmpList.get(cnt));
			}
			tmpDataList.add(tmpNewList);
		}
		return tmpDataList;
	}

	/**
	 * @param request request
	 * @param session session
	 * @param isChkAllAcrossAll if all check boxes are checked
	 * @return dataList
	 * @throws DAOException DAOException
	 */
	private List<List<String>> getDataList(HttpServletRequest request,
			HttpSession session, String isChkAllAcrossAll) throws DAOException {
		String pageNo = (String) request.getParameter(AQConstants.PAGE_NUMBER);
		String recordsPerPageStr = (String) session.getAttribute(AQConstants.RESULTS_PER_PAGE);
		if (pageNo != null)
		{
			request.setAttribute(AQConstants.PAGE_NUMBER, pageNo);
		}
		int recordsPerPage = Integer.valueOf(recordsPerPageStr);
		int pageNum = Integer.valueOf(pageNo);
		if (isChkAllAcrossAll != null
				&& isChkAllAcrossAll.equalsIgnoreCase("true"))
		{
			Integer totalRecords = (Integer) session.getAttribute(AQConstants.TOTAL_RESULTS);
			recordsPerPage = totalRecords;
			pageNum = 1;
		}
		QuerySessionData querySessionData = (QuerySessionData) session
				.getAttribute(edu.wustl.common.util.global.Constants.QUERY_SESSION_DATA);
		List dataList1 = Utility.getPaginationDataList(request, getSessionData(request),
				recordsPerPage, pageNum, querySessionData);
		List<List<String>> dataList = (List<List<String>>) session
				.getAttribute(AQConstants.EXPORT_DATA_LIST);
		if (dataList == null)
		{
			dataList = dataList1;
		}
		return dataList;
	}

	/**
	 * @param columnList column List
	 * @return idColCount
	 */
	private int setIdColumnCount(List<String> columnList)
	{
		int idColCount = 0;
		for (int cnt = 0; cnt < columnList.size(); cnt++)
		{
			String columnName = (String) columnList.get(cnt);
			Logger.out.debug(columnName + " : " + columnName.length());
			if (columnName.trim().equalsIgnoreCase("ID"))
			{
				idColCount++;
			}
		}
		return idColCount;
	}
	/**
	 *
	 * @param response response
	 * @param session session
	 * @param exportList exportList
	 * @param idIndexList idIndexList
	 * @param entityIdsMap entityIdsMap
	 * @throws IOException IOException
	 */
	private void exportAndSend(HttpServletResponse response,
			HttpSession session, List<List<String>> exportList,
			List<String> idIndexList, Map<Integer, List<String>> entityIdsMap)
			throws IOException
	{
		ExportReport report;
		String appName = CommonServiceLocator.getInstance().getAppHome();
		String path = appName + System.getProperty("file.separator");
		String csvfileName = path + AQConstants.SEARCH_RESULT;
		String zipFileName = path + session.getId() + AQConstants.ZIP_FILE_EXTENSION;
		String fileName = path + session.getId() + AQConstants.CSV_FILE_EXTENSION;
		if (entityIdsMap != null && !entityIdsMap.isEmpty())
		{
			report = new ExportReport(path, csvfileName, zipFileName);
			report.writeDataToZip(exportList, AQConstants.DELIMETER, idIndexList);
			SendFile.sendFileToClient(response, zipFileName, AQConstants.EXPORT_ZIP_NAME,
					"application/download");
		}
		else
		{
			report = new ExportReport(fileName);
			report.writeData(exportList, AQConstants.DELIMETER);
			report.closeFile();
			SendFile.sendFileToClient(response, fileName, AQConstants.SEARCH_RESULT,
					"application/download");
		}
	}
}
