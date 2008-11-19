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

import edu.wustl.common.action.BaseAction;
import edu.wustl.common.dao.QuerySessionData;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.actionForm.AdvanceSearchForm;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;


/**
 * @author gautam_shetty
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpreadsheetViewAction extends BaseAction
{

	/**
	 * This method get call for simple search as well as advanced search.
	 * This method also get call when user clicks on page no of Pagination tag 
	 * from simple search result page as well as advanced search result page.
	 * Each time it calculates the paginationList to be displayed by grid control
	 * by getting pageNum from request object.
	 * @Override   
	 */
	protected ActionForward executeAction(ActionMapping mapping, ActionForm form,
			 HttpServletRequest request, HttpServletResponse response) throws Exception
	{ 
		/**
		 * Name: Deepti
		 * Description: Query performance issue. Instead of saving complete query results in session, resultd will be fetched for each result page navigation.
		 * object of class QuerySessionData will be saved session, which will contain the required information for query execution while navigating through query result pages.
		 * 
		 *  Here, extending this class from BaseAction  
		 */
		HttpSession session = request.getSession();
    	//changes are done for check all 
		String pageOf="";
		String checkallpages ="";
		String checkall = (String)request.getParameter(Constants.CHECK_ALL_PAGES);
     	if(checkall == null || checkall.equals(""))
    	{
     		checkallpages = (String)session.getAttribute(Constants.CHECK_ALL_PAGES);
    	}
    	else
    	{
    		checkallpages = checkall;
    	}
    	session.setAttribute(Constants.CHECK_ALL_PAGES, checkallpages);
    	String isAjax = (String)request.getParameter("isAjax");
     	
    	if(isAjax != null && isAjax.equals("true"))
     	{
     		response.setContentType("text/html");
			response.getWriter().write(checkallpages);
			pageOf=null;
     	}
    	else
    	{
    	   pageOf = setSpreadSheetView(request, session);
		//session.setAttribute(Constants.RESULTS_PER_PAGE,recordsPerPage);
		//Set the result per page attribute in the request to be uesd by pagination Tag.
		//      Prafull:Commented this can be retrived directly from constants on jsp, so no need to save it in request.
		//        request.setAttribute(Constants.RESULTS_PER_PAGE,Integer.toString(Constants.NUMBER_RESULTS_PER_PAGE_SEARCH));
		request.setAttribute(Constants.PAGEOF, pageOf);
     }	
		return mapping.findForward(pageOf);
  }





	private String setSpreadSheetView( HttpServletRequest request,  HttpSession session)
			throws DAOException
	{
		String pageOf;
		QuerySessionData querySessionData = (QuerySessionData) session
				.getAttribute(edu.wustl.common.util.global.Constants.QUERY_SESSION_DATA);

		   pageOf = (String) request.getAttribute(Constants.PAGEOF);
		    if (pageOf == null)
		   {
		 	 pageOf = (String) request.getParameter(Constants.PAGEOF);
	       }
		   if (request.getAttribute(Constants.IDENTIFIER_FIELD_INDEX) == null)
		  {
			  String identifierFieldIndex = request.getParameter(Constants.IDENTIFIER_FIELD_INDEX);
			 if (identifierFieldIndex != null && !identifierFieldIndex.equals(""))
			{
				request.setAttribute(Constants.IDENTIFIER_FIELD_INDEX, Integer.valueOf(
						identifierFieldIndex));
			}
		  }
		Logger.out.debug("Pageof in spreadsheetviewaction.........:" + pageOf);
		setSpreadSheetData(request, session, querySessionData);
		
		return pageOf;
	}


	


	private void setSpreadSheetData( HttpServletRequest request, HttpSession session,
			 QuerySessionData querySessionData) throws DAOException
	{
	
		Object defaultViewAttribute = request.getAttribute(Constants.SPECIMENT_VIEW_ATTRIBUTE);
		if (defaultViewAttribute != null)// When the Default specimen view Check box is checked or unchecked, this will be evaluated.
		{
			 List list = (List) request.getAttribute(Constants.SPREADSHEET_DATA_LIST);
			 List columnNames = (List) request.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);
			 session.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, columnNames);
			 request.setAttribute(Constants.SPREADSHEET_DATA_LIST, list);
		}
		String pagination = request.getParameter("isPaging");
		setPaginationData(request, session, querySessionData, pagination);
		int pageNum = Constants.START_PAGE;

		
		List paginationDataList = null, columnList = null;

		//Get the SPREADSHEET_DATA_LIST and SPREADSHEET_COLUMN_LIST from the session.
		columnList = (List) session.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);

		pageNum = getPageNum(request, session);
		
		Integer recordsPerPage =  getRecordsPerPage(request,session);
		if (pagination != null && pagination.equalsIgnoreCase("true"))
		{
			paginationDataList = Utility.getPaginationDataList(request, getSessionData(request), recordsPerPage, pageNum, querySessionData);
			request.setAttribute(Constants.PAGINATION_DATA_LIST,paginationDataList);
			Utility.setGridData(paginationDataList,columnList, request);
		}
		
		request.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, columnList);
		request.setAttribute(Constants.PAGE_NUMBER, Integer.toString(pageNum));

		session.setAttribute(Constants.RESULTS_PER_PAGE, recordsPerPage.toString());
	}





	private Integer getRecordsPerPage(HttpServletRequest request,HttpSession session)
	{
		String recordsPerPageStr;
		if (request.getParameter(Constants.RESULTS_PER_PAGE) == null)
		{
			if (request.getAttribute(Constants.RESULTS_PER_PAGE) == null)
			{
				recordsPerPageStr = (String) session.getAttribute(Constants.RESULTS_PER_PAGE);//Integer.parseInt(XMLPropertyHandler.getValue(Constants.NO_OF_RECORDS_PER_PAGE));
			}
			else
			{
				recordsPerPageStr = request.getAttribute(Constants.RESULTS_PER_PAGE).toString();
			}
			
		}
		else 
		{
			recordsPerPageStr = request.getParameter(Constants.RESULTS_PER_PAGE);
		}
		return Integer.valueOf(recordsPerPageStr);
	}





	private void setPaginationData(HttpServletRequest request, HttpSession session,
			 QuerySessionData querySessionData, String pagination)
	{
		List list = null;
		if (pagination == null || pagination.equals("false"))
		{

			list = (List) request.getAttribute(Constants.SPREADSHEET_DATA_LIST);
			List columnNames = (List) request.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);

			//Set the SPREADSHEET_DATA_LIST and SPREADSHEET_COLUMN_LIST in the session.
			//Next time when user clicks on pages of pagination Tag, it get the same list from the session
			//and based on current page no, it will calculate paginationDataList to be displayed by grid control.
			session.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, columnNames);
			request.setAttribute(Constants.PAGINATION_DATA_LIST, list);
			Utility.setGridData( list,columnNames, request);
			session.setAttribute(Constants.TOTAL_RESULTS, querySessionData
					.getTotalNumberOfRecords());
			AdvanceSearchForm advanceSearchForm = (AdvanceSearchForm) request
					.getAttribute("advanceSearchForm");
			if (advanceSearchForm != null)
			{
				session.setAttribute("advanceSearchForm", advanceSearchForm);
			}
		}
	}


	private int getPageNum( HttpServletRequest request, HttpSession session)
	{
		int pageNum; 
		if (request.getParameter(Constants.PAGE_NUMBER) == null)
		{
			if(session.getAttribute(Constants.PAGE_NUMBER) == null)
			{
				pageNum= Constants.START_PAGE;
			}
			else
			{
				pageNum = Integer.parseInt(session.getAttribute(Constants.PAGE_NUMBER).toString());
			}
		}
		else 
		{
			pageNum = Integer.parseInt(request.getParameter(Constants.PAGE_NUMBER));
		}
		return pageNum;
	}

}
