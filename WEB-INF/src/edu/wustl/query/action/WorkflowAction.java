package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.util.global.Constants;
import edu.wustl.query.actionForm.WorkflowForm;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;

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
		HttpSession session = request.getSession();
		final WorkflowForm workflowForm = (WorkflowForm) form;
		
		Workflow workflow = new Workflow();
		workflow.setName(workflowForm.getName());
		
		WorkflowBizLogic bizLogic = (WorkflowBizLogic)BizLogicFactory.getInstance().getBizLogic(edu.wustl.query.util.global.Constants.WORKFLOW_BIZLOGIC_ID);
		bizLogic.insert(workflow, getSessionData(request), Constants.HIBERNATE_DAO);
		
		return mapping.findForward(Constants.SUCCESS);
	}
	
	
	public SessionDataBean getSessionData(HttpServletRequest request) 
    {
		Object obj = request.getSession().getAttribute(Constants.SESSION_DATA);
		 
		if(obj == null)
		{
			obj = request.getSession().getAttribute(Constants.TEMP_SESSION_DATA);
		}
		if(obj!=null)
		{
			SessionDataBean sessionData = (SessionDataBean) obj;
			return  sessionData;
		} 
		return null;
	}
}

