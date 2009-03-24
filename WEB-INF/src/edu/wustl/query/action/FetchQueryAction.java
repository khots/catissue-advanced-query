
package edu.wustl.query.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleConstants;

/**
 * 
 * @author Chetan Patil
 *
 */
public class FetchQueryAction extends Action
{

	/**
	 * This action fetch saved query and loads the parameterized conditions
	 */
	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = Constants.FAILURE;
		Long queryId = null;
		SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
		if (request.getAttribute(Constants.QUERY_ID) == null)
		{
			queryId = saveQueryForm.getQueryId();

			Logger.out.debug("Fetching query having identifier as " + queryId);
			if (queryId == null)
			{
				target = Constants.SUCCESS;
				ActionErrors errors = Utility.setActionError(Constants.QUERY_IDENTIFIER_NOT_VALID,
						"errors.item");
				saveErrors(request, errors);
			}

			else
			{
				target = fetchQuery(request, queryId, saveQueryForm);
			}

		}
		else
		{
			queryId = (Long) request.getAttribute("queryId");
			String htmlContent = saveQueryForm.getQueryString();
			request.setAttribute(Constants.HTML_CONTENTS, htmlContent);
			target = Constants.SUCCESS;
		}

		return actionMapping.findForward(target);
	}

	private String fetchQuery(HttpServletRequest request, Long queryId, SaveQueryForm saveQueryForm)
			throws BizLogicException, PVManagerException
	{
		String target = Constants.FAILURE;
		IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
				.getValue("app.bizLogicFactory"), "getBizLogic",
				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		try
		{
			final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
					.getName(), Constants.ID, queryId);
			if (queryList != null && !queryList.isEmpty())
			{
				IParameterizedQuery parameterizedQuery = queryList.get(0);
				HttpSession session = request.getSession();
				session.setAttribute(Constants.QUERY_OBJECT, parameterizedQuery);
				//					Map<IExpression, Collection<IParameterizedCondition>> expressionIdConditionCollectionMap = QueryUtility
				//							.getAllParameterizedConditions(parameterizedQuery);

				if (parameterizedQuery.getParameters().isEmpty())
				{
					ParameterizedQuery query = (ParameterizedQuery) parameterizedQuery;
					if (query.getType().equalsIgnoreCase(Constants.QUERY_TYPE_GET_COUNT))
					{
						target = "editCountQuery";
						session.setAttribute(Constants.Query_Type, Constants.QUERY_TYPE_GET_COUNT);
					}
					else
					{
						target = "editDataQuery";
						session.setAttribute(Constants.Query_Type, Constants.QUERY_TYPE_GET_DATA);
					}
					session.setAttribute("queryName", query.getName());
					
					request.setAttribute(Constants.CURRENT_PAGE, "editQuery");
					
				}
				else
				{
					Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
					String htmlContents = new SavedQueryHtmlProvider().getHTMLForSavedQuery(
							parameterizedQuery, false, Constants.EXECUTE_QUERY_PAGE,
							customFormulaIndexMap);
					request.setAttribute(Constants.HTML_CONTENTS, htmlContents);
					session.setAttribute(
							QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP, customFormulaIndexMap);
					saveQueryForm.setQueryString(htmlContents);
					target = Constants.SUCCESS;
				}
			}
		}
		catch (DAOException daoException)
		{
			ActionErrors errors = Utility.setActionError(daoException.getMessage(), "errors.item");
			saveErrors(request, errors);

		}
		return target;
	}

	/**
	 * 
	 * @param request
	 * @param errorMessage
	 */

}
