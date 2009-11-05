package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.query.actionforms.WorkflowForm;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author chitra_garg
 *This class creates the template workflow.
 */
public class WorkflowTemplateAction extends AbstractQueryBaseAction
{
	/**
	 * The actual execute method.
	 *
	 * @param mapping
	 *            Action Mapping.
	 * @param form
	 *            Action Form.
	 * @param request
	 *            HttpServletRequest object.
	 * @param response
	 *            HttpServletResponse object.
	 * @return The Action Forward object.
	 * @throws BizLogicException
	 *             BizLogicException.
	 * @throws  QueryModuleException QueryModuleException
	 * @see org.apache.struts.action.Action#execute
	 *      (org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	   @Override
	    protected ActionForward executeBaseAction(final ActionMapping mapping,
	            final ActionForm form, final HttpServletRequest request,
	            final HttpServletResponse response) throws BizLogicException, QueryModuleException
	    {
		   final List<WorkflowItem> workflowItemList=Utility.generateTemplateItems(request);
		   final Workflow workflow=new Workflow();
		   workflow.setWorkflowItemList(workflowItemList);
		  final WorkflowForm workflowForm=(WorkflowForm) form;
		  workflowForm.populateWorklfow(workflow);
		  workflow.setId(null);
		   return mapping.findForward(Constants.SUCCESS);
	    }

}
