
package edu.wustl.query.action;

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

import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.queryexecutionmanager.DataQueryResultStatus;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author rinku_rohra
 *
 */
public class ShowGridAjaxHandlerAction extends AbstractQueryBaseAction
{

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String responseString = Constants.EMPTY_STRING;
		HttpSession session = request.getSession();
		String status = (String) request.getSession().getAttribute(Constants.STATUS);
		String recordsFrom = request.getParameter(Constants.GET_RECORDS_FROM);
		if (status != null && status.equals(DataQueryResultStatus.LAST_RECORD.toString())
				&& (recordsFrom == null || !recordsFrom.equals(Constants.ACTION)))
		{
			responseString = DataQueryResultStatus.NO_MORE_RECORDS.toString();
			request.getSession().removeAttribute(Constants.STATUS);
		}

		else
		{

			if (recordsFrom != null && recordsFrom.equals(Constants.ACTION))
			{
				Thread.sleep(Variables.gridDisplaySleepTime);
				responseString = getRemainingRecords(request);
			}

			else
			{
				Long dataQEId = (Long) session.getAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID);
				IQuery query = (IQuery) session.getAttribute("GeneratedQuery");
				AbstractQueryUIManager queryUIManager = AbstractQueryUIManagerFactory
						.configureDefaultAbstractUIQueryManager(this.getClass(), request, query);
				Thread.sleep(Variables.ajaxCallSleepTime);
				responseString = getResponseString(request, dataQEId, queryUIManager);
			}
		}

		response.getWriter().write(responseString);
		return null;
	}

	/** This method calls the createresponseString Method.
	 * @param request HttpServletRequest object
	 * @param dataQEId data query execution id
	 * @param queryUIManager query ui manager
	 * @return responseString
	 * @throws QueryModuleException Query Module Exception
	 * @throws JSONException JSON Exception
	 * @throws InterruptedException Interrupted Exception
	 */
	private String getResponseString(HttpServletRequest request, Long dataQEId,
			AbstractQueryUIManager queryUIManager) throws QueryModuleException, JSONException,
			InterruptedException
	{
		String responseString;
		DataQueryResultsBean dataQueryResultBean = queryUIManager.getNextRecord(dataQEId);
		if (dataQueryResultBean != null
				&& !(dataQueryResultBean.getResultStatus().equals(
						DataQueryResultStatus.NO_MORE_RECORDS)
						|| dataQueryResultBean.getResultStatus().equals(
								DataQueryResultStatus.NO_RECORDS_FOUND)
						|| dataQueryResultBean.getResultStatus().equals(
								DataQueryResultStatus.WAIT_FOR_NEXT_RECORD) || dataQueryResultBean
						.getResultStatus().equals(DataQueryResultStatus.TOO_FEW_RECORDS)))
		{

			String status = dataQueryResultBean.getResultStatus().toString();
			if (status.equals(DataQueryResultStatus.LAST_RECORD.toString()))
			{
				request.getSession().setAttribute(Constants.STATUS,
						DataQueryResultStatus.LAST_RECORD.toString());

			}
			List<List<Object>> dataList = dataQueryResultBean.getAttributeList();
			if (dataList != null && !dataList.isEmpty())
			{

				responseString = createResponseString(request, dataList);
			}
			else
			{
				responseString = dataQueryResultBean.getResultStatus().name();
			}
		}
		else
		{
			responseString = getReponse(dataQueryResultBean);
		}
		return responseString;
	}

	/** this Method generates the responseString.
	 * @param request HttpServletRequest object
	 * @param dataList data list
	 * @return responseString
	 * @throws JSONException JSON Exception
	 */
	private String createResponseString(HttpServletRequest request, List<List<Object>> dataList)
			throws JSONException
	{
		String responseString;
		List<List<Object>> tempList = new ArrayList<List<Object>>();
		if (dataList.size() > Constants.TEN)
		{
			tempList.addAll(dataList.subList(Constants.ZERO, Constants.TEN));
			dataList.removeAll(tempList);
			request.getSession().setAttribute(Constants.REMAINING_RECORDS, dataList);
			responseString = getRecordsString(request, tempList);

			responseString = Constants.NOT_ALL + Constants.RESPONSE_SEPRATOR + responseString;
		}

		else
		{
			responseString = getRecordsString(request, dataList);
			responseString = Constants.ALL + Constants.RESPONSE_SEPRATOR + responseString;
		}
		return responseString;
	}

	/** This methods generates the responseString for Records.
	 * @param request HttpServletRequest
	 * @param dataList data list
	 * @return responseString
	 * @throws JSONException JSON Exception
	 */
	private String getRecordsString(HttpServletRequest request, List<List<Object>> dataList)
			throws JSONException
	{

		String responseString;
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		Iterator<List<Object>> itr = dataList.iterator();
		while (itr.hasNext())
		{
			JSONObject jsonObject = new JSONObject();
			List<Object> labelNodeDataList = itr.next();

			String record = createRecordString(labelNodeDataList, request);
			if (record != null)
			{
				//response.flushBuffer();
				jsonObject.append("row", record);
				jsonObjectList.add(jsonObject);
			}
		}
		responseString = new JSONObject().put("records", jsonObjectList).toString();
		return responseString;
	}

	/** Gets the ResponseString for Status.
	 * @param dataQueryResultBean DataQueryResultsBean object
	 * @return responseString
	 * @throws InterruptedException Interrupted Exception
	 */
	private String getReponse(DataQueryResultsBean dataQueryResultBean) throws InterruptedException
	{

		String responseString;

		if (dataQueryResultBean != null
				&& dataQueryResultBean.getResultStatus().equals(
						DataQueryResultStatus.WAIT_FOR_NEXT_RECORD))
		{
			//  Thread.sleep(1000);
			responseString = Constants.RESPONSE_WAIT;
		}
		else
		{
			if (dataQueryResultBean == null)
			{
				responseString = Constants.RESPONSE_WAIT;
			}
			else
			{
				responseString = dataQueryResultBean.getResultStatus().name();
			}
		}
		return responseString;
	}

	/** This Method generates a String in the grid row format.
	 * @param dataList data list 
	 * @param request HttpServletRequest object
	 * @return temp
	 */
	private String createRecordString(List<Object> dataList, HttpServletRequest request)
	{
		String temp = "";
		if (dataList != null)
		{
			HttpSession session = request.getSession();
			StringBuffer prepareRecord = new StringBuffer("");
			// Patch ID: SimpleSearchEdit_10
			// Calling utility method by passing the hyperlink map. 
			// Changing delimeter for the dhtml grid
			Map hyperlinkColumnMap = (Map) session.getAttribute(Constants.HYPERLINK_COLUMN_MAP);
			if (hyperlinkColumnMap == null)
			{
				hyperlinkColumnMap = new HashMap();
			}

			List row = dataList;
			int count;

			for (count = Constants.ZERO; count < (row.size() - Constants.ONE); count++)
			{
				prepareRecord.append(
						Utility.toNewGridFormatWithHref(row, hyperlinkColumnMap, count))
						.append(' ').append(Constants.DHTMLXGRID_DELIMETER);
			}
			prepareRecord.append(Utility.toNewGridFormatWithHref(row, hyperlinkColumnMap, count));
			prepareRecord.append("");
			temp = prepareRecord.toString();
		}
		return temp;
	}

	/** This method gets the remaining records from session.
	 * @param request HttpServletRequest object
	 * @return responseString
	 * @throws JSONException JSON Exception
	 */
	private String getRemainingRecords(HttpServletRequest request) throws JSONException
	{
		List<List<Object>> dataList = (List<List<Object>>) request.getSession().getAttribute(
				Constants.REMAINING_RECORDS);

		request.getSession().removeAttribute(Constants.REMAINING_RECORDS);
		return createResponseString(request, dataList);
	}

}
