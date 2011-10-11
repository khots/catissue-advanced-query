
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.cab2b.common.cache.AbstractEntityCache;
import edu.wustl.common.action.SecureAction;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * Action is called when user clicks on Advance Search link on search menu.
 * @author deepti_shelar
 */
public class QueryWizardAction extends SecureAction
{

	/**
	 * This method loads the data required for categorySearch.jsp.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	public ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward actionForward = null;
		if(AbstractEntityCache.isCacheReady)
		{
			HttpSession session = request.getSession();
			CategorySearchForm searchForm = (CategorySearchForm) form;
			session.removeAttribute(AQConstants.QUERY_OBJECT);
			session.removeAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
			session.removeAttribute(AQConstants.IS_SAVED_QUERY);
			session.removeAttribute(AQConstants.IS_SIMPLE_SEARCH);
			session.removeAttribute(DAGConstant.ISREPAINT);
			session.removeAttribute(DAGConstant.TQUIMap);
			session.removeAttribute(DAGConstant.JQUIMap);
			session.removeAttribute(AQConstants.EXPORT_DATA_LIST);
			session.removeAttribute(AQConstants.ENTITY_IDS_MAP);
			session.removeAttribute(AQConstants.AUDIT_EVENT_ID);
			session.removeAttribute("savedQuery");
			session.removeAttribute("savedQueryProcessed");
			searchForm = QueryModuleUtil.setDefaultSelections(searchForm);
			actionForward = mapping.findForward(edu.wustl.query.util.global.AQConstants.SUCCESS);
		}
		else
		{
			ActionErrors errors = new ActionErrors();
			String errorMessage = ApplicationProperties.getValue("entityCache.error");
			ActionError error = new ActionError("query.errors.item",errorMessage);
			errors.add(ActionErrors.GLOBAL_ERROR, error);
			saveErrors(request, errors);
			actionForward = mapping.findForward(edu.wustl.query.util.global.AQConstants.FAILURE);
		}

		//Added a Default session data bean......Need to be removed when there query will have login

		/*SessionDataBean sessionBean = (SessionDataBean) session
				.getAttribute(Constants.SESSION_DATA);
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
			sessionData.setAdmin(false);
			sessionData.setSecurityRequired(true);

			session.setAttribute(Constants.SESSION_DATA, sessionData);
		}*/
		return actionForward;
	}
}
