package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.db2.jcc.b.re;

import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.DAO;
import edu.wustl.query.domain.Workflow;


public class RetrieveWorkflowAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DefaultBizLogic defaultBizLogic=new DefaultBizLogic();
		List workflowList=defaultBizLogic.retrieve(Workflow.class.getName());
		request.setAttribute("workflowList", workflowList);
		
		return mapping.findForward("success");
	}

}
