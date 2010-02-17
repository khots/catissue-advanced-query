/**
 * DashBoard action
 */

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
 * This action retrieves queries for dash-board.
 * @author deepti_shelar
 * @author chetan_patil
 */
public class RetrieveQueryAction extends SecureAction
{
	/**
	 * This method is used to fetch and execute query.
	 * @param actionMapping mapping
	 * @param actionForm form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	public ActionForward executeSecureAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		cleanUpSession(request);
		ActionForward actionForward = null;
		if(AbstractEntityCache.isCacheReady)
		{
			DashboardBizLogic dashboardBizLogic = new DashboardBizLogic();
			String pageOf = (String) request.getParameter(AQConstants.PAGE_OF);
			SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
			request.setAttribute("queryOption", pageOf);

			SessionDataBean sessionDataBean = (SessionDataBean) request.getSession()
			.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
			if(pageOf ==null)
			{
				pageOf = "allQueries";
			}
			dashboardBizLogic.setDashboardQueries(sessionDataBean, saveQueryForm);
			Collection<IParameterizedQuery> queries = getQueries(pageOf,
					saveQueryForm);
			if(queries == null)
			{
				saveQueryForm.setParameterizedQueryCollection(new ArrayList<IParameterizedQuery>());
				queries = new ArrayList<IParameterizedQuery>();
			}
			else
			{
				saveQueryForm.setParameterizedQueryCollection(queries);
				Map<Long,DashBoardBean> dashBoardDetails = dashboardBizLogic.
				getDashBoardDetails(queries,sessionDataBean.getUserId().toString());
				saveQueryForm.setDashBoardDetailsMap(dashBoardDetails);
			}
			createMessage(request, queries);
			actionForward = actionMapping.findForward(AQConstants.SUCCESS);
			request.setAttribute(AQConstants.POPUP_MESSAGE, ApplicationProperties
					.getValue("query.confirmBox.message"));
		}
		else
		{
			ActionErrors errors = new ActionErrors();
			String errorMessage = ApplicationProperties.getValue("entityCache.error");
			ActionError error = new ActionError("query.errors.item",errorMessage);
			errors.add(ActionErrors.GLOBAL_ERROR, error);
			saveErrors(request, errors);
			actionForward = actionMapping.findForward(AQConstants.CACHE_ERROR);
		}
		return actionForward;
	}
	/**
	 * Gets the appropriate list of queries as per the request
	 * @param pageOf page of
	 * @param saveQueryForm action form
	 * @return queries collection
	 */
	private Collection<IParameterizedQuery> getQueries(String pageOf,
			SaveQueryForm saveQueryForm)
			{
		Collection<IParameterizedQuery> queries = new ArrayList<IParameterizedQuery>();
		if("allQueries".equals(pageOf))
		{
			queries = saveQueryForm.getAllQueries();
		}
		else if("sharedQueries".equals(pageOf))
		{
			queries = saveQueryForm.getSharedQueries();
		}
		else if("myQueries".equals(pageOf))
		{
			queries = saveQueryForm.getMyQueries();
		}
		return queries;
	}
	/**
	 * Creates a message to be shown on UI.
	 * @param request request
	 * @param queries queries
	 * @throws BizLogicException exception object
	 */
	private void createMessage(HttpServletRequest request,
		Collection<IParameterizedQuery> queries) throws BizLogicException {
		String message = queries.size() + "";
		ActionMessages actionMessages = new ActionMessages();
		String action = (String) request.getParameter("actions");
		String saveQueryMessage = "";
		if("save".equalsIgnoreCase(action))
		{
			IParameterizedQuery query = (IParameterizedQuery)request.getSession().getAttribute(AQConstants.QUERY_OBJECT);
			saveQueryMessage = MessageFormat.format(ApplicationProperties.getValue("query.saved.success"), query.getName()+" ");
			ActionMessage actionMessage = new ActionMessage("query.saved.success", saveQueryMessage);
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		}
		ActionMessage actionMessage = new ActionMessage("query.resultFound.message", message);
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		saveMessages(request, actionMessages);
	}
	/**
	 * Cleans up data from session.
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
		session.removeAttribute(DAGConstant.TQUIMap);
		session.removeAttribute(DAGConstant.JQUIMap);
		session.setAttribute(AQConstants.SAVED_QUERY, AQConstants.FALSE);
	}
}