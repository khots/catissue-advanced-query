
package edu.wustl.query.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.util.global.Constants;

/**
 * This class is used to show the Tooltip on the Concepts
 * @author amit_doshi
 *
 */
public class ShowVITooltipAction extends AbstractQueryBaseAction
{

	/**
	 * This method handles the various AJAX request for VI tooltip.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @return ActionForward actionForward
	 * @throws IOException -IOException
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ActionForward target = null;
		String conceptCode = request.getParameter(Constants.CON_CODE);
		if (conceptCode != null)
		{
			Map<String, String> codeVsTooltip = (HashMap) request.getSession().getAttribute(
					Constants.CODE_VS_TOOLTIP);

			response.getWriter().write(codeVsTooltip.get(conceptCode));
		}
		return target;
	}
}
