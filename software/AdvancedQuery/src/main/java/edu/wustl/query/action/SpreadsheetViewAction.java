/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

/*
 * Created on Aug 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.action.SecureAction;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionForm.QueryAdvanceSearchForm;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;

/**
 * @author gautam_shetty
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpreadsheetViewAction extends SecureAction
{

	/**
	 * This method get call for simple search as well as advanced search.
	 * This method also get call when user clicks on page no of Pagination tag
	 * from simple search result page as well as advanced search result page.
	 * Each time it calculates the paginationList to be displayed by grid control
	 * by getting pageNum from request object.
	 * @Override
	 */
	protected ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		/**
		 * Name: Deepti
		 * Description: Query performance issue. Instead of saving complete query results in
		 * session, result will be fetched for each result page navigation.
		 * object of class QuerySessionData will be saved session, which will contain the required
		 * information for query execution while navigation through query result pages.
		 *
		 *  Here, extending this class from BaseAction
		 */
		HttpSession session = request.getSession();
		//changes are done for check all
		String pageOf = "";
		String checkallpages = "";
		String checkall = (String) request.getParameter(AQConstants.CHECK_ALL_PAGES);
		if (checkall == null || checkall.equals(""))
		{
			checkallpages = (String) session.getAttribute(AQConstants.CHECK_ALL_PAGES);
		}
		else
		{
			checkallpages = checkall;
		}
		session.setAttribute(AQConstants.CHECK_ALL_PAGES, checkallpages);
		String isAjax = (String) request.getParameter("isAjax");

		if (isAjax != null && isAjax.equals("true"))
		{
			response.setContentType("text/html");
			response.getWriter().write(checkallpages);
			pageOf = null;
		}
		else
		{
			pageOf = setSpreadSheetView(request, session);
			request.setAttribute(AQConstants.PAGEOF, pageOf);
		}
		return mapping.findForward(pageOf);
	}

	private String setSpreadSheetView(HttpServletRequest request, HttpSession session)
			throws DAOException
	{
		String pageOf;
		QuerySessionData querySessionData = (QuerySessionData) session
				.getAttribute(edu.wustl.common.util.global.Constants.QUERY_SESSION_DATA);

		pageOf = (String) request.getAttribute(AQConstants.PAGEOF);
		if (pageOf == null)
		{
			pageOf = (String) request.getParameter(AQConstants.PAGEOF);
		}
		if (request.getAttribute(AQConstants.IDENTIFIER_FIELD_INDEX) == null)
		{
			String idIndex = request.getParameter(AQConstants.IDENTIFIER_FIELD_INDEX);
			if (idIndex != null && !idIndex.equals(""))
			{
				request.setAttribute(AQConstants.IDENTIFIER_FIELD_INDEX, Integer
						.valueOf(idIndex));
			}
		}
		Logger.out.debug("Pageof in spreadsheetviewaction.........:" + pageOf);
		setSpreadSheetData(request, session, querySessionData);

		return pageOf;
	}

	private void setSpreadSheetData(HttpServletRequest request, HttpSession session,
			QuerySessionData querySessionData) throws DAOException
	{

		Object defaultViewAttr = request.getAttribute(AQConstants.SPECIMENT_VIEW_ATTRIBUTE);
		if (defaultViewAttr != null)// When the Default specimen view Check box is
			//checked or unchecked, this will be evaluated.
		{
			List list = (List) request.getAttribute(AQConstants.SPREADSHEET_DATA_LIST);
			List columnNames = (List) request.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);
			session.setAttribute(AQConstants.SPREADSHEET_COLUMN_LIST, columnNames);
			request.setAttribute(AQConstants.SPREADSHEET_DATA_LIST, list);
		}
		String pagination = request.getParameter("isPaging");
		setPaginationData(request, session, querySessionData, pagination);
		int pageNum = AQConstants.START_PAGE;

		List paginationList = null, columnList = null;

		//Get the SPREADSHEET_DATA_LIST and SPREADSHEET_COLUMN_LIST from the session.
		columnList = (List) session.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);

		pageNum = getPageNum(request, session);

		Integer recordsPerPage = getRecordsPerPage(request, session);
		if (pagination != null && pagination.equalsIgnoreCase("true"))
		{
			paginationList = Utility.getPaginationDataList(request, getSessionData(request),
					recordsPerPage, pageNum, querySessionData);
			request.setAttribute(AQConstants.PAGINATION_DATA_LIST, paginationList);
			Utility.setGridData(paginationList, columnList, request);
		}

		request.setAttribute(AQConstants.SPREADSHEET_COLUMN_LIST, columnList);
		request.setAttribute(AQConstants.PAGE_NUMBER, Integer.toString(pageNum));

		session.setAttribute(AQConstants.RESULTS_PER_PAGE, recordsPerPage.toString());
	}

	private Integer getRecordsPerPage(HttpServletRequest request, HttpSession session)
	{
		String recordsPerPageStr;
		if (request.getParameter(AQConstants.RESULTS_PER_PAGE) == null)
		{
			if (request.getAttribute(AQConstants.RESULTS_PER_PAGE) == null)
			{
				recordsPerPageStr = (String) session.getAttribute(AQConstants.RESULTS_PER_PAGE);
			}
			else
			{
				recordsPerPageStr = request.getAttribute(AQConstants.RESULTS_PER_PAGE).toString();
			}

		}
		else
		{
			recordsPerPageStr = request.getParameter(AQConstants.RESULTS_PER_PAGE);
		}
		return Integer.valueOf(recordsPerPageStr);
	}

	private void setPaginationData(HttpServletRequest request, HttpSession session,
			QuerySessionData querySessionData, String pagination)
	{
		List list = null;
		if (pagination == null || pagination.equals("false"))
		{

			list = (List) request.getAttribute(AQConstants.SPREADSHEET_DATA_LIST);
			List columnNames = (List) request.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);

			session.setAttribute(AQConstants.SPREADSHEET_COLUMN_LIST, columnNames);
			request.setAttribute(AQConstants.PAGINATION_DATA_LIST, list);
			Utility.setGridData(list, columnNames, request);
			session.setAttribute(AQConstants.TOTAL_RESULTS, querySessionData
					.getTotalNumberOfRecords());
			QueryAdvanceSearchForm advanceSearchForm = (QueryAdvanceSearchForm) request
					.getAttribute("advanceSearchForm");
			if (advanceSearchForm != null)
			{
				session.setAttribute("advanceSearchForm", advanceSearchForm);
			}
		}
	}

	private int getPageNum(HttpServletRequest request, HttpSession session)
	{
		int pageNum;
		if (request.getParameter(AQConstants.PAGE_NUMBER) == null)
		{
			if (session.getAttribute(AQConstants.PAGE_NUMBER) == null)
			{
				pageNum = AQConstants.START_PAGE;
			}
			else
			{
				pageNum = Integer.parseInt(session.getAttribute(AQConstants.PAGE_NUMBER).toString());
			}
		}
		else
		{
			pageNum = Integer.parseInt(request.getParameter(AQConstants.PAGE_NUMBER));
		}
		return pageNum;
	}

}
