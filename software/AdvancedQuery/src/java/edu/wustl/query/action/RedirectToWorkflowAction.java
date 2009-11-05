
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

/**
 * @author chitra_garg
 * removes the workflow related data from session .
 * This is called when navigates back to the
 * workflow after adding a query.
 */
public class RedirectToWorkflowAction extends AbstractQueryBaseAction
{
	 /**
    * This action is used for processing Work flow object
    *
    * @param mapping
    *            ActionMapping.
    * @param form
    *            ActionForm.
    * @param request
    *            HttpServletRequest.
    * @param response
    *            HttpServletResponse.
    * @return ActionForward ActionFowrward.
    * @throws Exception
    *             Exception.
    *
    * @see org.apache.struts.action.Action#execute(org.apache.s
    *      truts.action.ActionMapping, org.apache.struts.action.ActionForm,
    *      javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */

	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String workflowId = (String) session.getAttribute(Constants.EXECUTED_FOR_WFID);
		String workflowName = (String) session.getAttribute(Constants.WORKFLOW_NAME);
		cancelDataQueryExecutionThread(session);
		request.setAttribute(Constants.WORKFLOW_ID, workflowId);
		if (workflowId != null)
		{
			request.setAttribute(edu.wustl.common.util.global.Constants.SYSTEM_IDENTIFIER, Long
					.valueOf(workflowId));
		}

		removeSessionData(session, workflowId, workflowName);
		// added for get Count ->>>latest project Id
		String executedForProject = (String) session.getAttribute(Constants.EXECUTED_FOR_PROJECT);
		if (executedForProject != null)
		{
			request.setAttribute(Constants.EXECUTED_FOR_PROJECT, Long.valueOf(executedForProject));
			session.removeAttribute(Constants.EXECUTED_FOR_PROJECT);

		}
		return mapping.findForward(Constants.QUERYWIZARD);
	}

	/**
	 * removes session data
	 * @param session HttpSession
	 * @param workflowId workflowId
	 * @param workflowName workflowName
	 */
	private void removeSessionData(HttpSession session, String workflowId,
			String workflowName)
	{
		if (workflowId != null && workflowName != null)
		{
			session.removeAttribute(Constants.EXECUTED_FOR_WFID);
			session.removeAttribute(Constants.WORKFLOW_NAME);
			if(session.getAttribute(Constants.WORKFLOW_ID)!=null
				&&!session.getAttribute(Constants.WORKFLOW_ID).equals(""))
			{
				session.removeAttribute(Constants.WORKFLOW_ID);
			}
		}
	}
	
	private void cancelDataQueryExecutionThread(HttpSession session)
	{
		Long  sprdshtviewDQExId = (Long) session
		  .getAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID);
		Long  treeviewDQExId = (Long) session 
		.getAttribute(Constants.TREEVIEW_DQ_EXECUTION_ID);
		 AbstractQueryUIManager queryUIManager = AbstractQueryUIManagerFactory
			.getDefaultAbstractUIQueryManager();
		
		 if(sprdshtviewDQExId!=null)
		 {
			queryUIManager.cancelDataQuery(sprdshtviewDQExId);
		    session.removeAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID);
		 }
		 if(treeviewDQExId!=null)
		 {
			queryUIManager.cancelDataQuery(treeviewDQExId);
		    session.removeAttribute(Constants.TREEVIEW_DQ_EXECUTION_ID);
		 }
	
	}

}
