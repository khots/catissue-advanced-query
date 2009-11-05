package edu.wustl.query.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
/**
 * This action class updates a Query for Parameterized Conditions before execution.
 * @author baljeet_dhindhwal
 *
 */
public class UpdateParameterizedConditionsAction extends AbstractQueryBaseAction
{
	 /**
     * This action is used for processing Work flow object
     *
     * @param actionMapping
     *            ActionMapping.
     * @param actionForm
     *            ActionForm.
     * @param request
     *            HttpServletRequest.
     * @param response
     *            HttpServletResponse.
     * @return ActionForward ActionFowrward.
     * @throws Exception
     *             Exception.
     *
     * @see org.apache.struts.action.Action#execute(org.apache.s
     *      truts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.getSession().removeAttribute(Constants.QUERY_EXEC_ID);
		HttpSession session = request.getSession();
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) session
				.getAttribute(Constants.QUERY_OBJECT);

		String conditionstr = request.getParameter(Constants.PARAM_CONDITION_LIST);
        String writeResponse = "Get Count";
		//Updating Query for Parameterized Conditions on temporal conditions
		String rhsList = request.getParameter(Constants.TEMPORAL_PARAM_CONDITON_LIST);
		session.setAttribute(Constants.IS_SAVED_QUERY, Constants.TRUE);
		Map<Integer, ICustomFormula> customFormulaIndexMap = (Map<Integer, ICustomFormula>) session
				.getAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP);
		

		//Updating Query for Parameterized Conditions on normal attributes
		StringBuilder errorMessage = new StringBuilder("");
		if (conditionstr != null)
		{
			CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
			String msg = "";
			msg = bizLogic.setInputDataToQuery(conditionstr, parameterizedQuery
					.getConstraints(), null, parameterizedQuery);
			errorMessage.append(msg);
			msg = bizLogic.setInputDataToTQ(parameterizedQuery,
					Constants.EXECUTE_QUERY_PAGE, rhsList, customFormulaIndexMap);
			errorMessage.append(msg);
		}
		if(errorMessage.toString().equals(""))
		{
			session.removeAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP);
			IParameterizedQuery queryClone = new DyExtnObjectCloner().clone(parameterizedQuery);
			//Now remove the empty conditions from the IQuery
			//Utility.removeEmptyCoditionsFromQuery(queryClone);
			AbstractQueryUIManager QUIManager = null;
			QUIManager = AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this
					.getClass(), request, queryClone);

			//Here once query is  updated , get it's execution id
			Long query_exec_id = QUIManager.searchQuery();
			request.getSession().setAttribute("query_exec_id", query_exec_id);

			//Need to clone again as query id is get cleaned in  insertParametersForExecution
			//IParameterizedQuery queryClone1 = new DyExtnObjectCloner().clone(parameterizedQuery);
			QUIManager.insertParametersForExecution(query_exec_id,parameterizedQuery);
			parameterizedQuery.setId(queryClone.getId());
		}
		else
		{
			writeResponse = errorMessage.toString();
		}
		response.getWriter().write(writeResponse);
		return null;
	}

}
