
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleActionUtil;

/**
 * Action is called when user clicks on QueryWizard link on search tab.
 * @author deepti_shelar
 */
public class QueryWizardAction extends AbstractQueryBaseAction
{

	/**
	 * This method loads the data required for categorySearch.jsp
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
		QueryModuleActionUtil.setDefaultSelections(searchForm);
		String pageOf = request.getParameter(Constants.PAGE_OF);
		if (pageOf != null && pageOf.equals(Constants.PAGE_OF_WORKFLOW))
		{
			request.setAttribute(Constants.IS_WORKFLOW, Constants.TRUE);
			String workflowName = (String) request.getSession().getAttribute(
					Constants.WORKFLOW_NAME);
			request.setAttribute(Constants.WORKFLOW_NAME, workflowName);
		}

		if(request.getParameter(Constants.PAGE_OF)==null||(request.getParameter(Constants.PAGE_OF)!=null&&
				!request.getParameter(Constants.PAGE_OF).equals(Constants.PAGE_OF_WORKFLOW)))
		{
			session.removeAttribute(Constants.EXECUTED_FOR_WFID);
			session.removeAttribute(Constants.WORKFLOW_NAME);
			session.removeAttribute(Constants.WORKFLOW_ID);
		}
		QueryType qtype = QueryType.GET_DATA;
		session.setAttribute(Constants.Query_Type, qtype.type);

		//Added a Default session data bean......Need to be removed when there query will have login

		SessionDataBean sessionBean = (SessionDataBean) session
				.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
		if (sessionBean == null)
		{
			// HttpSession newSession = request.getSession(true);
			Long userId = Long.valueOf((1));
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

		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
}
