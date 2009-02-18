
package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * 
 * @author ravindra_jain
 * @created November 26, 2008	
 */
public class WorkflowAction extends Action
{

	/**
	 * This action is used for processing Work flow object
	 */
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.s
	 * truts.action.ActionMapping, org.apache.struts.action.ActionForm,
	 *  javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (request.getParameter(Constants.OPERATION) != null && (request.getParameter("id") != null || request.getAttribute(Constants.WORKFLOW_ID)!=null)
				&& (!"".equals(request.getParameter(Constants.ID))))
		{

			request.setAttribute(Constants.OPERATION, edu.wustl.common.util.global.Constants.EDIT);
		}
	   else
		{
			request.setAttribute(Constants.OPERATION, Constants.ADD);
			System.out.println("");
		}
		if (request.getParameter(Constants.ID) != null && (!request.getParameter(Constants.ID).equals("")))
		{
			request.setAttribute(Constants.ID, request.getParameter(Constants.ID));
		}
		setProjectList(request);
		if(request.getAttribute(Constants.WORKFLOW_ID)!=null)
		{
			request.setAttribute(Constants.WORKFLOW_ID,  request.getAttribute(Constants.WORKFLOW_ID));
		}
		return mapping.findForward(Constants.SUCCESS);
	}

	private void setProjectList(HttpServletRequest request) throws QueryModuleException
	{
		//Retrieve the Project list
		
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				Constants.SESSION_DATA);
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		List<NameValueBean> projectList = qUIManager.getObjects(sessionDataBean.getUserId());

		if (projectList != null)
		{
			request.setAttribute(Constants.PROJECT_NAME_VALUE_BEAN, projectList);
		}
	}
}
