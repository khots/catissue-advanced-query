
package edu.wustl.query.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * When the user searches or saves a query , the query is checked for the conditions like DAG should not be empty , is there 
 * at least one node in view on define view page and does the query contain the main object. If all the conditions are satisfied 
 * further process is done else corresponding error message is shown.
 * 
 * @author shrutika_chintal
 *
 */
public class ValidateDefineViewAction extends AbstractQueryBaseAction
{
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		IQuery query = (IQuery)session.getAttribute(Constants.QUERY_OBJECT);
		String validationMessage = Utility.getValidationMessage(request,query);
		String workflow=request.getParameter(Constants.IS_WORKFLOW);
		if(Constants.TRUE.equals(workflow))
		{
			request.setAttribute(Constants.IS_WORKFLOW,Constants.TRUE);
			String workflowName= (String)request.getSession().getAttribute(Constants.WORKFLOW_NAME);
			request.setAttribute(Constants.WORKFLOW_NAME,workflowName);
		 }
		if (validationMessage == null)
		{
			validationMessage=Constants.DEFINE_VIEW_MSG;
		}
		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		response.getWriter().write(validationMessage);
		return null;  
	}
}
