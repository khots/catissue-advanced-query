package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.util.global.Constants;


public class LoadingParametrsAction extends AbstractQueryBaseAction
{
	/**
	 * This method Will show the loading message on the UI for VI
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	   String entityId= request.getParameter("queryIdString");
	   request.setAttribute("queryIdString", entityId);
	   request.setAttribute(Constants.WORKFLOW_ID, request.getParameter(Constants.WORKFLOW_ID));
	    request.setAttribute(Constants.SELECTED_PROJECT, request.getParameter(Constants.SELECTED_PROJECT));
	    request.setAttribute("queryType", request.getParameter("queryType"));
	     return mapping.findForward("success");
		
	}
}
