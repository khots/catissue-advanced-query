package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.util.global.Constants;


public class LoadingVocabulariesAction extends AbstractQueryBaseAction
{

	/**
	 * This method Will show the loading message on the UI for VI
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	   String entityId= request.getParameter(Constants.ENTITY_ID);
	   request.getSession().setAttribute(Constants.ENTITY_NAME, entityId);
	     return mapping.findForward("success");
		
	}
}
