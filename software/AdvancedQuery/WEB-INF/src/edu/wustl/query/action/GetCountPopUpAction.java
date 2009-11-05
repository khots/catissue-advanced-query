
package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

/**
 * Action class to load the data required for GetCountPopUp.jsp (Used to show the Count of QueryResults)
 * @author pallavi_mistry
 *
 */
public class GetCountPopUpAction extends AbstractQueryBaseAction
{

	private static final int ONE = 1;

	/**
	 * Sets the Project list and currently selected project in the form
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
		CategorySearchForm categorySearchForm = (CategorySearchForm) form;

		//get the userId from session data bean
		SessionDataBean sessionData = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		String projectId = request.getParameter("projectId");
		String projectIdForExecuteQuery = request.getParameter(Constants.SELECTED_PROJECT);
		String queryName = request.getParameter(Constants.QUERY_TITLE);
		categorySearchForm.setQueryTitle(queryName);
		HttpSession session = request.getSession();
		Long userId = sessionData.getUserId();
		if (userId == null)
		{
			userId = Long.valueOf(ONE);
		}


		//Retrieve the Project list
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		List<NameValueBean> projectList = qUIManager.getObjects(userId);
		if (projectList != null)
		{
			categorySearchForm.setProjectsNameValueBeanList(projectList);
		}

		//retrieve the Selected Project from the GetCounts.jsp
		String selectedProject = (String)session.getAttribute(
				Constants.SELECTED_PROJECT);

		//this is  done for execution of my query..... need to change flow
		if(projectId != null)
		{
			selectedProject = projectId;
		}
		else if(projectIdForExecuteQuery!=null)
		{

			selectedProject =projectIdForExecuteQuery;
		}
		categorySearchForm.setCurrentSelectedProject(selectedProject);
		// set privilege in session based on project id and user id.
		qUIManager.getPrivilege(request);
       	return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
}
