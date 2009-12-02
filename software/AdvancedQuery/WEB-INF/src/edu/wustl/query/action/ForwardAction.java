package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.action.BaseAction;
import edu.wustl.query.util.global.AQConstants;
/**
 * This method forwards the request to next action/page.
 * @author deepti_shelar
 */
public class ForwardAction  extends BaseAction
{
	/**
	 * This method forwards the request to next action/page.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(AQConstants.SUCCESS);
	}
}
