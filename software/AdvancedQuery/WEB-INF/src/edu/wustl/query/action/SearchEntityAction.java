package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.flex.dag.DAGConstant;


public class SearchEntityAction extends AbstractQueryBaseAction
{
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		session.removeAttribute(DAGConstant.QUERY_OBJECT);
		session.removeAttribute("allLimitExpressionIds");
		return mapping.findForward("");
	}
}
