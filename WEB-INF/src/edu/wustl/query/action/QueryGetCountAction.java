package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * Action is called when user clicks on QueryGetCount link on search tab.
 * @author pallavi_mistry
 *
 */
public class QueryGetCountAction extends Action
{

	/**
	 * This method loads the data required for GetCounts.jsp
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
		HttpSession session = request.getSession();
		CategorySearchForm searchForm = (CategorySearchForm) form;
		session.removeAttribute(Constants.QUERY_OBJECT);
		session.removeAttribute(Constants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(Constants.IS_SAVED_QUERY);
		session.removeAttribute(edu.wustl.common.util.global.Constants.IS_SIMPLE_SEARCH);
		session.removeAttribute(DAGConstant.ISREPAINT);
		session.removeAttribute(DAGConstant.TQUIMap);
		session.removeAttribute(Constants.EXPORT_DATA_LIST);
		session.removeAttribute(Constants.ENTITY_IDS_MAP);
		session.removeAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP);
		session.removeAttribute(Constants.MAIN_EXPRESSION_TO_ADD_CONTAINMENTS);
		session.removeAttribute(Constants.ALL_ADD_LIMIT_EXPRESSIONS);
		session.removeAttribute(Constants.MAIN_EXPRESSIONS_ENTITY_EXP_ID_MAP);
		session.removeAttribute(Constants.MAIN_ENTITY_LIST);
		
		searchForm = QueryModuleUtil.setDefaultSelections(searchForm);

		//Added a Default session data bean......Need to be removed when there query will have login

		SessionDataBean sessionBean = (SessionDataBean) session
				.getAttribute(Constants.SESSION_DATA);
		Long userId = sessionBean.getUserId();
		if (sessionBean == null)
		{
			// HttpSession newSession = request.getSession(true);

			String ipAddress = request.getRemoteAddr();
			SessionDataBean sessionData = new SessionDataBean();

			sessionData.setUserName("admin@admin.com");
			sessionData.setIpAddress(ipAddress);
			sessionData.setUserId(userId);
			sessionData.setFirstName("admin@admin.com");
			sessionData.setLastName("admin@admin.com");
			sessionData.setAdmin(true);
			sessionData.setSecurityRequired(false);

			session.setAttribute(Constants.SESSION_DATA, sessionData);
		}
		
		//Retrieve the Project list
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
		List<NameValueBean> projectList = qUIManager.getObjects(userId);

		if(projectList != null)   
		{	 
			searchForm.setProjectsNameValueBeanList(projectList);
		}
		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
}
