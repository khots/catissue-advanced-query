
package edu.wustl.query.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.AQConstants;

/**
 * @author Chetan Patil
 */
public class FetchQueryAction extends Action
{
	/**
	 * This action is used to fetch saved query and load the parameterized conditions.
	 * @param actionMapping mapping
	 * @param actionForm form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = AQConstants.FAILURE;
		Long queryId = null;
		SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
		if (request.getAttribute("queryId") == null)
		{
			queryId = saveQueryForm.getQueryId();
			if (queryId == null)
			{
				target = AQConstants.SUCCESS;
				setActionError(request, "Query identifier is not valid.");
			}
			else
			{
				IBizLogic bizLogic = BizLogicFactory.getInstance().getBizLogic(
						AQConstants.QUERY_INTERFACE_BIZLOGIC_ID);
				try
				{
					List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
							.getName(), "id", queryId);
					if (queryList != null && !queryList.isEmpty())
					{
						target = getAppropriateTarget(request, saveQueryForm,
								queryList);
					}
				}
				catch (BizLogicException daoException)
				{
					setActionError(request, daoException.getMessage());
				}
			}
		}
		else
		{
			queryId = (Long) request.getAttribute("queryId");
			String htmlContent = saveQueryForm.getQueryString();
			request.setAttribute(AQConstants.HTML_CONTENTS, htmlContent);
			target = AQConstants.SUCCESS;
		}
		return actionMapping.findForward(target);
	}

	/**
	 * @param request request
	 * @param saveQueryForm saveQueryForm
	 * @param queryList queryList
	 * @return target
	 */
	private String getAppropriateTarget(HttpServletRequest request,
			SaveQueryForm saveQueryForm, List<IParameterizedQuery> queryList)
	{
		String target;
		IParameterizedQuery parameterizedQuery = queryList.get(0);
		request.getSession().setAttribute(AQConstants.QUERY_OBJECT, parameterizedQuery);
		if (parameterizedQuery.getParameters().isEmpty())
		{
			target = AQConstants.EXECUTE_QUERY;
		}
		else
		{
			Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer,
			ICustomFormula>();
			String htmlContents = new SavedQueryHtmlProvider().getHTMLForSavedQuery(
					parameterizedQuery, false, AQConstants.EXECUTE_QUERY_PAGE,
					customFormulaIndexMap);
			request.setAttribute(AQConstants.HTML_CONTENTS, htmlContents);
			request.getSession().setAttribute(
					AQConstants.CUSTOM_FORMULA_INDEX_MAP,
					customFormulaIndexMap);
			saveQueryForm.setQueryString(htmlContents);
			target = AQConstants.SUCCESS;
		}
		return target;
	}
	/**
	 * Sets errors in request.
	 * @param request http request
	 * @param errorMessage message to be displayed.
	 */
	private void setActionError(HttpServletRequest request, String errorMessage)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}
}
