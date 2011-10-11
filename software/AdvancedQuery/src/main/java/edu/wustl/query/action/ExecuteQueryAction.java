/**
 *
 */

package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.global.Constants;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * @author chetan_patil
 * This action process the input from the UI and executes parameterized query with the input values.
 * @created September 14, 2007, 9:53:15 AM
 */
public class ExecuteQueryAction extends Action
{
	/**
	 * This method executes the saved query.
	 * @param actionMapping mapping
	 * @param actionForm form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward actionForward = null;
		String target = AQConstants.FAILURE;
		boolean flag = true;
		cleanUpSession(request);
		HttpSession session = request.getSession();
		IParameterizedQuery query = (IParameterizedQuery) session
				.getAttribute(AQConstants.QUERY_OBJECT);
		IParameterizedQuery cloneQuery = new DyExtnObjectCloner().clone(query);
		String conditionstr = request.getParameter("conditionList");
		String rhsList = request.getParameter(AQConstants.STR_TO_FORM_TQ);
		session.setAttribute(AQConstants.IS_SAVED_QUERY, AQConstants.TRUE);
		Map<Integer, ICustomFormula> cFIndexMap = (Map<Integer, ICustomFormula>) session
				.getAttribute(AQConstants.CUSTOM_FORMULA_INDEX_MAP);
		session.removeAttribute(AQConstants.CUSTOM_FORMULA_INDEX_MAP);
		if (conditionstr != null)
		{
			CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
			String errorMessage = bizLogic.setInputDataToQuery(conditionstr, cloneQuery
					.getConstraints(), null, query);
			errorMessage = bizLogic.setInputDataToTQ(cloneQuery,
					AQConstants.EXECUTE_QUERY_PAGE, rhsList, cFIndexMap);
			if (errorMessage.trim().length() > 0)
			{
				ActionErrors errors = new ActionErrors();
				ActionError error = new ActionError("query.errors.item", errorMessage);
				errors.add(ActionErrors.GLOBAL_ERROR, error);
				saveErrors(request, errors);
				target = AQConstants.INVALID_CONDITION_VALUES;
				request.setAttribute("queryId", query.getId());
				flag = false;
				actionForward = actionMapping.findForward(target);
			}
		}
		if(flag)
		{
			actionForward = executeQuery(actionMapping, request,
					session, query, cloneQuery);
		}
		return actionForward;
	}

	/**
	 * Execute the query.
	 * @param actionMapping actionMapping
	 * @param request request
	 * @param session session
	 * @param query query
	 * @param cloneQuery cloneQuery
	 * @return actionForward
	 */
	private ActionForward executeQuery(ActionMapping actionMapping,
			HttpServletRequest request, HttpSession session,
			IParameterizedQuery query, IParameterizedQuery cloneQuery)
	{
		String target = AQConstants.FAILURE;
		ActionForward actionForward;
		String errorMessage = QueryModuleUtil.executeQuery(request, cloneQuery);
		session.setAttribute(AQConstants.QUERY_OBJECT, query);
		if ("".equals(errorMessage))
		{
			target = AQConstants.SUCCESS;
		}
		else
		{
			setErrorMessage(request, errorMessage);
		}
		actionForward =  actionMapping.findForward(target);
		return actionForward;
	}

	/**
	 * Set appropriate error messages.
	 * @param request request
	 * @param errorMessage error Message
	 */
	private void setErrorMessage(HttpServletRequest request, String errorMessage)
	{
		List<String> xssViolationErrors =
		(ArrayList<String>)request.getAttribute(Constants.VIOLATING_PROPERTY_NAMES);
		if(xssViolationErrors!=null && xssViolationErrors.size() == 1
				&& xssViolationErrors.contains("queryString"))
		{
			request.removeAttribute(Constants.VIOLATING_PROPERTY_NAMES);
		}
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("query.errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}
	/**
	 * Cleans up data from session.
	 * @param request request object
	 */
	private void cleanUpSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.removeAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(AQConstants.EXPORT_DATA_LIST);
		session.removeAttribute(AQConstants.ENTITY_IDS_MAP);
		session.removeAttribute(AQConstants.AUDIT_EVENT_ID);
	}
}