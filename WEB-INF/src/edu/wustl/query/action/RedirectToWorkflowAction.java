
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.wustl.query.util.global.Constants;

/**
 * @author chitra_garg
 * removes the workflow related data from session . 
 * This is called when navigates back to the 
 * workflow after adding a query.
 */
public class RedirectToWorkflowAction extends Action
{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String workflowId = (String) session.getAttribute(Constants.WORKFLOW_ID);
		String workflowName = (String) session.getAttribute(Constants.WORKFLOW_NAME);
		request.setAttribute(Constants.WORKFLOW_ID, (String) session
				.getAttribute(Constants.WORKFLOW_ID));

		if (workflowId != null && workflowName != null)
		{
			session.removeAttribute(Constants.WORKFLOW_ID);
			session.removeAttribute(Constants.WORKFLOW_NAME);

		}
		return (mapping.findForward(Constants.QUERYWIZARD));
	}

}
