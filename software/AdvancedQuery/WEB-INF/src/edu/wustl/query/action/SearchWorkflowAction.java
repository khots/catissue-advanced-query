package edu.wustl.query.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.exception.ApplicationException;
import edu.wustl.common.util.global.Constants;
import edu.wustl.query.actionforms.WorkflowForm;

/**
 * @author chitra_garg
 *
 */
public class SearchWorkflowAction extends Action
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
     * @throws IOException
     *            IOException.
     * @throws ApplicationException
     *             ApplicationException.
     *
     * @see org.apache.struts.action.Action#execute(org.apache.s
     *      truts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException,
			ApplicationException
	{
		WorkflowForm workflowForm=(WorkflowForm)form;
		Long wfId=workflowForm.getId();
		String target=Constants.FAILURE;
		if(wfId!=null)
		{
			target=Constants.SUCCESS;
			request.setAttribute(Constants.SYSTEM_IDENTIFIER, workflowForm.getId());
		}


		return mapping.findForward(target);

	}

}
