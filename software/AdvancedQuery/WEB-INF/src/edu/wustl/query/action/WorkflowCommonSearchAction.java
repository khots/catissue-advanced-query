
package edu.wustl.query.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.cider.util.global.SessionUtil;
import edu.wustl.common.action.CommonSearchAction;
import edu.wustl.common.exception.ApplicationException;
import edu.wustl.common.util.Utility;
import edu.wustl.query.actionforms.WorkflowForm;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;

/**
 * This class checks to see if the logged in user has privilege to view the
 * requested workflow and only then displays the workflow to the user.
 * @author niharika_sharma
 *
 */
public class WorkflowCommonSearchAction extends CommonSearchAction
{


	/**
	 * Overrides the execute method of Action class.
	 * Retrieves and populates the information to be edited.
	 * @param mapping	ActionMapping
	 * @param form	ActionForm
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 * @return ActionForward
	 * @exception IOException Generic exception
	 * @exception ApplicationException Application Exception.
	 * */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException,
			ApplicationException
	{
		ActionForward actionForward = null;
		String obj = request.getParameter(edu.wustl.common.util.global.Constants.SYSTEM_IDENTIFIER);

		Long identifier= Long.valueOf(Utility.toLong(obj));

		if (identifier.longValue() == 0)
		{
			identifier = (Long) request
					.getAttribute(edu.wustl.common.util.global.Constants.SYSTEM_IDENTIFIER);
		}

		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		if (identifier != null
				&& workflowBizLogic.hasPrivilegeToView(Workflow.class.getName(), identifier,
						SessionUtil.getSessionData()))
		{
			actionForward = super.execute(mapping, form, request, response);
		}
		else
		{
			response.sendRedirect(Constants.ACCESS_DENIED_URL);
		}
		request.setAttribute("queryIdForRow", ((WorkflowForm)form).getQueryIdForRow());
		return actionForward;
	}
}
