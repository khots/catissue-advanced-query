
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.util.global.Constants;

/**
 * Class to validate query title from Save query page.
 * @author ravindra_jain
 */
public class ValidateQueryTitleAjaxHandlerAction extends AbstractQueryBaseAction
{

	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long iQueryId = null;
		String queryTitle = request.getParameter(Constants.QUERY_TITLE);
		String queryId = request.getParameter(Constants.QUERY_ID);
		if (queryId != null && !queryId.equals("") && !queryId.equalsIgnoreCase("null"))
		{
			iQueryId = Long.valueOf(queryId);
		}
		String writeresponse = null;
		if (ValidateQueryBizLogic.isXMLCharacter(queryTitle))
		{
			writeresponse = ApplicationProperties.getValue("error.querytitle.invalid.char");
		}
		else
		{
			writeresponse = ValidateQueryBizLogic.validateForDuplicateQueryName(queryTitle,
					iQueryId);
		}
		if (writeresponse == null)
		{
			writeresponse = "";
		}
		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		response.getWriter().write(writeresponse);
		return null;
	}
}
