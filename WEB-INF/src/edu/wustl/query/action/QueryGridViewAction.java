
package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.util.global.Constants;

/**
 * This action is called when user clicks on Search button after forming the query object.This class loads required grid data in session/request.
 * And then it forwards control to simpleSearchDataView.jsp.
 * @author deepti_shelar
 */
public class QueryGridViewAction extends AbstractQueryBaseAction
{

	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		int pageNum = edu.wustl.common.util.global.Constants.START_PAGE;
		request.setAttribute(Constants.PAGE_NUMBER, Integer.toString(pageNum));
		List dataList = (List) session.getAttribute(Constants.PAGINATION_DATA_LIST);
		request.setAttribute(Constants.PAGINATION_DATA_LIST, dataList);
		List<String> columnsList = (List<String>) session
				.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);
		request.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, columnsList);
		session.setAttribute(Constants.PAGINATION_DATA_LIST, null);
		String pageOf = request.getParameter(edu.wustl.common.util.global.Constants.PAGEOF);
		request.setAttribute(edu.wustl.common.util.global.Constants.PAGEOF, pageOf);
		return mapping.findForward(Constants.SUCCESS);
	}
}
