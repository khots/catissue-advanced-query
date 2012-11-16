
package edu.wustl.query.action;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import edu.wustl.cab2b.common.cache.AbstractEntityCache;
import edu.wustl.common.action.SecureAction;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.beans.DashBoardBean;
import edu.wustl.query.bizlogic.DashboardBizLogic;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.AQConstants;

/**
 * The Class ShowQueryDashboardAction.
 * @author nitesh_marwaha
 *
 */
public class ShowQueryDashboardAction extends SecureAction
{

	@Override
	/**
	 * This method is used to fetch queries and display on the dashboard.
	 * @param actionMapping mapping
	 * @param actionForm form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 * @see edu.wustl.common.action.SecureAction#executeSecureAction(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		cleanUpSession(request);
		saveToken(request);
		ActionForward actionForward;
		if (AbstractEntityCache.isCacheReady)
		{
			DashboardBizLogic dashboardBizLogic = new DashboardBizLogic();
			String pageOf = (String) request.getParameter(AQConstants.PAGE_OF);
			SaveQueryForm saveQueryForm = (SaveQueryForm) form;
			request.setAttribute("queryOption", pageOf);

			SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
					edu.wustl.common.util.global.Constants.SESSION_DATA);
			if (pageOf == null)
			{
				pageOf = "myQueries";
			}
			
			
			request.setAttribute("queryOption", pageOf);
			Collection<IParameterizedQuery> queries = getQueries(pageOf, sessionDataBean);

			if (queries == null)
			{
				saveQueryForm.setParameterizedQueryCollection(new ArrayList<IParameterizedQuery>());
				queries = new ArrayList<IParameterizedQuery>();
			}
			else
			{
				saveQueryForm.setParameterizedQueryCollection(queries);
				Map<Long, DashBoardBean> dashBoardDetails = dashboardBizLogic.getDashBoardDetails(
						queries, sessionDataBean.getUserId().toString());
				saveQueryForm.setDashBoardDetailsMap(dashBoardDetails);
			}
			createMessage(request, queries);
			actionForward = mapping.findForward(AQConstants.SUCCESS);
			request.setAttribute(AQConstants.POPUP_MESSAGE, ApplicationProperties
					.getValue("query.confirmBox.message"));
			request.setAttribute(AQConstants.POPUP_HEADER,
					ApplicationProperties.getValue("queryfolder.app.popuptitle"));
			request.setAttribute(AQConstants.POPUP_DELETE_QUERY_MESSAGE,
					ApplicationProperties.getValue("queryfolder.confirmBox.querydelete.message"));
			request.setAttribute(AQConstants.POPUP_ASSIGN_MESSAGE,
					ApplicationProperties.getValue("queryfolder.alertBox.message"));
			request.setAttribute(AQConstants.POPUP_ASSIGN_QMESSAGE,
					ApplicationProperties.getValue("queryfolder.alertBox.query.message"));
			request.setAttribute(AQConstants.POPUP_TEXT,
					ApplicationProperties.getValue("queryfolder.app.newfolder.text"));
			request.setAttribute(AQConstants.POPUP_DELETE_QUERY_FOLDER_MESSAGE,
					ApplicationProperties.getValue("queryfolder.confirmBox.folderdelete.message"));
		
		}
		else
		{
			actionForward = getActionForward(mapping, request);
		}
		return actionForward;
	}

	/**
	 * Gets the action forward.
	 *
	 * @param actionMapping the action mapping
	 * @param request the request
	 *
	 * @return the action forward
	 */
	private ActionForward getActionForward(ActionMapping actionMapping, HttpServletRequest request)
	{
		ActionForward actionForward;
		ActionErrors errors = new ActionErrors();
		String errorMessage = ApplicationProperties.getValue("entityCache.error");
		ActionError error = new ActionError("query.errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
		actionForward = actionMapping.findForward(AQConstants.CACHE_ERROR);
		return actionForward;
	}

	/**
	 * Gets the appropriate list of queries as per the request.
	 *
	 * @param pageOf page of
	 * @param sessionDataBean the session data bean
	 *
	 * @return queries collection
	 * @throws BizLogicException
	 */
	private Collection<IParameterizedQuery> getQueries(String pageOf,
			SessionDataBean sessionDataBean) throws BizLogicException
	{
		Collection<IParameterizedQuery> queries = new ArrayList<IParameterizedQuery>();
		DashboardBizLogic dashboardBizLogic = new DashboardBizLogic();
		if ("allQueries".equals(pageOf))
		{
			queries = dashboardBizLogic.getAllQueries(sessionDataBean.getCsmUserId(), sessionDataBean.getUserName());
		}
		else if ("sharedQueries".equals(pageOf))
		{
			queries = dashboardBizLogic.getSharedQueries(sessionDataBean.getCsmUserId(), sessionDataBean.getUserName());
		}
		else if ("myQueries".equals(pageOf))
		{
			queries = dashboardBizLogic.getMyQueries(sessionDataBean.getCsmUserId());
		}
		return queries;
	}

	/**
	 * Creates a message to be shown on UI.
	 *
	 * @param request request
	 * @param queries queries
	 *
	 * @throws BizLogicException exception object
	 */
	private void createMessage(HttpServletRequest request, Collection<IParameterizedQuery> queries)
			throws BizLogicException
	{
		String message = Integer.toString(queries.size());
		ActionMessages actionMessages = new ActionMessages();
		String action = (String) request.getParameter("actions");
		String saveQueryMessage;
		if ("save".equalsIgnoreCase(action))
		{
			IParameterizedQuery query = (IParameterizedQuery) request.getSession().getAttribute(
					AQConstants.QUERY_OBJECT);
			saveQueryMessage = MessageFormat.format(ApplicationProperties
					.getValue("query.saved.success"), query.getName() + " ");
			ActionMessage actionMessage = new ActionMessage("query.saved.success", saveQueryMessage);
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		}
		ActionMessage actionMessage = new ActionMessage("query.resultFound.message", message);
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		saveMessages(request, actionMessages);
	}

	/**
	 * Cleans up data from session.
	 *
	 * @param request request
	 */
	private void cleanUpSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.removeAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(AQConstants.SAVE_GENERATED_SQL);
		session.removeAttribute(AQConstants.SAVE_TREE_NODE_LIST);
		session.removeAttribute(AQConstants.ID_NODES_MAP);
		session.removeAttribute(AQConstants.MAIN_ENTITY_MAP);
		session.removeAttribute(AQConstants.EXPORT_DATA_LIST);
		session.removeAttribute(AQConstants.ENTITY_IDS_MAP);
		session.removeAttribute(DAGConstant.TQUIMAP);
		session.removeAttribute(DAGConstant.JQUIMAP);
		session.setAttribute(AQConstants.SAVED_QUERY, AQConstants.FALSE);
	}

}
