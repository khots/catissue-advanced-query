package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.util.global.Constants;;

public class ShowResetCountPopUpAction extends Action 
{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setAttribute("index",request.getParameter("index"));
		return mapping.findForward(Constants.SUCCESS);
	
	}
}
