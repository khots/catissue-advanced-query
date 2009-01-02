
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.wustl.query.util.global.Constants;

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
		if (request.getParameter(Constants.OPERATION) != null)
		{

			request.setAttribute(Constants.OPERATION, "edit");
		}
		else
		{
			request.setAttribute(Constants.OPERATION, Constants.ADD);
		}
		if (request.getParameter("id") != null && (!request.getParameter("id").equals("")))
		{
			request.setAttribute("id", request.getParameter("id"));
		}
		return mapping.findForward(Constants.SUCCESS);
	}
}
