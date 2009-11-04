package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.action.BaseAction;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.filter.IUrlAccessValidator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;

/**
 * This class is added as a Base class for all the action classes in Query.
 * It checks whether a user is authorized to perform a particular action or not.
 * @author rukhsana_sameer
 *
 */
public abstract class AbstractQueryBaseAction extends BaseAction
{
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			 HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ActionForward actionForward = null;
		IUrlAccessValidator urlAccessValidator =
			 (IUrlAccessValidator)Utility.getObject(Variables.urlAccessValidator);
		if(urlAccessValidator.isAuthorized(request))
		{
				actionForward = executeBaseAction(mapping, form, request, response);
		}
		else
		{
				response.sendRedirect(Constants.ACCESS_DENIED_URL);
		}
		return actionForward;
	}
	
	/**
	 * To be implemented by child classes containing the actual display logic
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract ActionForward executeBaseAction(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
