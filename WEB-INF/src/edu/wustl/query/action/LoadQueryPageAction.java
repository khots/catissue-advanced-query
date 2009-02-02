package edu.wustl.query.action;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Constants;


public class LoadQueryPageAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	Map map = (Map) request.getAttribute("forwardToHashMap");
    	 HttpSession session = request.getSession();
    	 session.setAttribute(Constants.WORKFLOW_NAME, Utility.toString(map.get(Constants.WORKFLOW_NAME)));//setAttribute(Constants.WORKFLOW_NAME,Utility.toString(map.get(Constants.WORKFLOW_NAME)));
    	 session.setAttribute(Constants.WORKFLOW_ID,Utility.toString(map.get(Constants.WORKFLOW_ID)));
		return mapping.findForward(request.getParameter("nextPageOf"));
       
    }
}
