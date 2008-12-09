/**
 * 
 */

package edu.wustl.query.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * @author chetan_patil
 * @created Sep 14, 2007, 9:53:15 AM
 */
public class ExecuteQueryAction extends Action
{

	/**
	 * This action process the input from the UI and executes parameterized query with the input values.
	 */
	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = Constants.FAILURE;
		//request.setAttribute(Constants.IS_SAVED_QUERY, Constants.TRUE);
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(Constants.EXPORT_DATA_LIST);
		session.removeAttribute(Constants.ENTITY_IDS_MAP);
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) session
				.getAttribute(Constants.QUERY_OBJECT);
		IParameterizedQuery parameterizedQuery1 = new DyExtnObjectCloner().clone(parameterizedQuery);

		String conditionstr = request.getParameter("conditionList");
		String rhsList = request.getParameter(QueryModuleConstants.STR_TO_FORM_TQ);
		session.setAttribute(Constants.IS_SAVED_QUERY, Constants.TRUE);
		Map<Integer, ICustomFormula> customFormulaIndexMap = (Map<Integer, ICustomFormula>) session
				.getAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP);
		session.removeAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP);
		if (conditionstr != null)
		{
			CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
			String errorMessage = bizLogic.setInputDataToQuery(conditionstr, parameterizedQuery1
					.getConstraints(), null, parameterizedQuery);
			errorMessage = bizLogic.setInputDataToTQ(parameterizedQuery1,
					Constants.EXECUTE_QUERY_PAGE, rhsList, customFormulaIndexMap);
			if (errorMessage.trim().length() > 0)
			{
				ActionErrors errors = Utility.setActionError(errorMessage, "errors.item");
				saveErrors(request, errors);
				target = Constants.INVALID_CONDITION_VALUES;
				request.setAttribute("queryId", parameterizedQuery.getId());
			}
		}

	 if(!(target.equals(Constants.INVALID_CONDITION_VALUES))){
		String errorMessage = QueryModuleUtil.executeQuery(request, parameterizedQuery1);
		session.setAttribute(Constants.QUERY_OBJECT, parameterizedQuery);

		target = checkError(request,errorMessage);
	 } 
		return actionMapping.findForward(target);
	}

	/**
	 * This Method checks for any error present and if present it will save the error
	 * @param request
	 * @param errorMessage
	 * @return
	 */
	private String checkError(HttpServletRequest request, 
			final String errorMessage)
	{
		String target =	Constants.FAILURE;
		if (errorMessage == null)
		{
			target = Constants.SUCCESS;
		}
		else if (errorMessage.equalsIgnoreCase(Constants.TREE_NODE_LIMIT_EXCEEDED_RECORDS))
		{
			target = Constants.TREE_NODE_LIMIT_EXCEEDED_RECORDS;
		}
		else
		{
			ActionErrors errors = Utility.setActionError(errorMessage, "errors.item"); 
				saveErrors(request, errors);
		}
		return target;
	}
}