
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.util.global.AQConstants;

/**
 * This is a action called when user clicks on search button from addlimits .jsp. The result data lists are already
 * stored in session through an applet action ViewSearchResultsAction. This class just forwards the control to ViewSearchResults.jsp.
 * @author deepti_shelar
 *
 */
public class ViewSearchResultsJSPAction extends Action
{

	/**
	 * This method loads the data required for ViewSearchResults.jsp
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = AQConstants.SUCCESS;
		CategorySearchForm actionForm = (CategorySearchForm) form;
		String nextOperation = actionForm.getNextOperation();
		if (nextOperation != null && nextOperation.equalsIgnoreCase(AQConstants.SHOW_ERROR_PAGE))
		{
			target = AQConstants.FAILURE;
		}
		return mapping.findForward(target);
	}
}
