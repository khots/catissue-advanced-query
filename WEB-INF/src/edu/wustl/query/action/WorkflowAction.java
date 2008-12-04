package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.actionForm.WorkflowForm;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * 
 * @author ravindra_jain
 * @created November 26, 2008	
 */
public class WorkflowAction extends Action
{
	
	/**
	 * This action is used for processing Workflow object
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		System.out.println("In Workflow Action");
		final WorkflowForm workflowForm = (WorkflowForm) form;
		
		Workflow workflow = new Workflow();
		workflow.setName(workflowForm.getName());
		
		WorkflowBizLogic bizLogic = (WorkflowBizLogic)BizLogicFactory.getInstance().getBizLogic(Constants.WORKFLOW_BIZLOGIC_ID);
		bizLogic.insert(workflow, Utility.getSessionData(request), Constants.HIBERNATE_DAO);
		
		return mapping.findForward(Constants.SUCCESS);
	}
}

