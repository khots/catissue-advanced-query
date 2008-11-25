/**
 * 
 */

package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.util.global.Constants;

/**
 * @author supriya_dankh
 *
 */
public class QueryMessageAction extends Action
{

	/**
	 * This method loads the html for addlimits section.This html is the replaced with the div data with the help of Ajax script.
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
		StringBuffer message = new StringBuffer((String) request.getSession().getAttribute(
				Constants.NO_MAIN_OBJECT_IN_QUERY));
		 message = message.append("<li><font color='blue' family='arial,helvetica,verdana,sans-serif'>");
		 message = message.append(message);
		 message = message.append("</font></li>");
		request.getSession().removeAttribute(Constants.NO_MAIN_OBJECT_IN_QUERY);
		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		response.getWriter().write(message.toString());
		return null;
	}

}
