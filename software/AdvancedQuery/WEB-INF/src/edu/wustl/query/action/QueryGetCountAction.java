
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
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleActionUtil;

/**
 * Action is called when user clicks on QueryGetCount link on search tab.
 * @author pallavi_mistry
 *
 */
public class QueryGetCountAction extends AbstractQueryBaseAction
{

	private static org.apache.log4j.Logger logger = Logger.getLogger(QueryGetCountAction.class);

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
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		CategorySearchForm searchForm = (CategorySearchForm) form;
		String currentPage = (String) request.getAttribute("currentPage");
		if ((Constants.EDIT_QUERY.equalsIgnoreCase(currentPage)))
		{
			IParameterizedQuery parameterizedQuery = (IParameterizedQuery) request.getSession()
					.getAttribute(Constants.QUERY_OBJECT);
			request.setAttribute("isQuery", "true");
			searchForm.setQueryTitle(parameterizedQuery.getName());
			logger.info("In QueryGetCountQueryAction Query opened in Edit mode : "
					+ parameterizedQuery.getId());
		}
		else
		{
			
			session.removeAttribute(Constants.QUERY_OBJECT);
			session.removeAttribute(Constants.SELECTED_COLUMN_META_DATA);
			session.removeAttribute(Constants.IS_SAVED_QUERY);
			session.removeAttribute(Constants.IS_SIMPLE_SEARCH);
			session.removeAttribute(DAGConstant.ISREPAINT);
			session.removeAttribute(DAGConstant.TQUIMap);
			session.removeAttribute(Constants.EXPORT_DATA_LIST);
			session.removeAttribute(Constants.ENTITY_IDS_MAP);
			session.removeAttribute(Constants.MAIN_ENTITY_EXPRESSIONS_MAP);
			session.removeAttribute(Constants.MAIN_EXPR_TO_ADD_CONTAINMENTS);
			session.removeAttribute(Constants.ALL_ADD_LIMIT_EXPRESSIONS);
			session.removeAttribute(Constants.MAIN_EXP_ENTITY_EXP_ID_MAP);
			session.removeAttribute(Constants.MAIN_ENTITY_LIST);
			session.removeAttribute(Constants.Query_Type);
		}
		
		if(request.getParameter(Constants.PAGE_OF)==null||(request.getParameter(Constants.PAGE_OF)!=null&&
				!request.getParameter(Constants.PAGE_OF).equals(Constants.PAGE_OF_WORKFLOW)))
		{
			session.removeAttribute(Constants.EXECUTED_FOR_WFID);
			session.removeAttribute(Constants.WORKFLOW_NAME);
			session.removeAttribute(Constants.WORKFLOW_ID);
		}
		searchForm = QueryModuleActionUtil.setDefaultSelections(searchForm);
		String pageOf = request.getParameter(Constants.PAGE_OF);
		if (pageOf != null && pageOf.equals(Constants.PAGE_OF_WORKFLOW))
		{
			request.setAttribute(Constants.IS_WORKFLOW, Constants.TRUE);
			String workflowName = (String) request.getSession().getAttribute(
					Constants.WORKFLOW_NAME);
			request.setAttribute(Constants.WORKFLOW_NAME, workflowName);
		}
		//Added a Default session data bean......Need to be removed when there query will have login

		SessionDataBean sessionBean = (SessionDataBean) session
				.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
		Long userId = null;
		if (sessionBean == null)
		{
			// HttpSession newSession = request.getSession(true);
			userId = 1L;
			String ipAddress = request.getRemoteAddr();
			SessionDataBean sessionData = new SessionDataBean();

			sessionData.setUserName("admin@admin.com");
			sessionData.setIpAddress(ipAddress);
			sessionData.setUserId(userId);
			sessionData.setFirstName("admin@admin.com");
			sessionData.setLastName("admin@admin.com");
			sessionData.setAdmin(true);
			sessionData.setSecurityRequired(false);
			session.setAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA, sessionData);
		}
		else
		{
			userId = sessionBean.getUserId();
		}

		QueryType qtype = QueryType.GET_COUNT;
		session.setAttribute(Constants.Query_Type, qtype.type);
		//Retrieve the Project list
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		List<NameValueBean> projectList = qUIManager.getObjects(userId);

		if (projectList != null)
		{
			searchForm.setProjectsNameValueBeanList(projectList);
		}

		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
}
