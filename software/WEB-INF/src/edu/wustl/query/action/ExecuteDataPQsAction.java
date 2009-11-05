
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This action is executed when the Query is executed. It will check weather the Query
 * is parameterized or not & accordingly form the response.
 * @author pavan_kalantri
 *
 */
public class ExecuteDataPQsAction extends AbstractQueryBaseAction
{

	/**
	 * This method Create the Html required for showing the parameterized attribute when executing the
	 * Query.
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

		String queryIdString = "";
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.HTML_CONTENTS);
		session.removeAttribute(Constants.QUERY_ID_STRING);

		Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap =
			new HashMap<Long, Map<Integer, ICustomFormula>>();
		queryIdString = request.getParameter(Constants.QUERY_ID);
		session.removeAttribute(Constants.ENTITY_IDS_MAP);
		Map<Long, IParameterizedQuery> queryIdMap = new HashMap<Long, IParameterizedQuery>();
		IParameterizedQuery parameterizedQuery = Utility.retrieveQuery(Long.valueOf(queryIdString));
		if (parameterizedQuery.getParameters() != null
				&& !parameterizedQuery.getParameters().isEmpty())
		{
			//String htmlContents = createHTML(eachQueryCFMap, parameterizedQuery,request);
			//session.setAttribute(Constants.QUERY_CUSTOM_FORMULA_MAP, eachQueryCFMap);
			//if (htmlContents != null && !htmlContents.equals(""))
			//{
			//	session.setAttribute(Constants.HTML_CONTENTS, htmlContents);
				session.setAttribute(Constants.QUERY_ID_STRING, queryIdString);
				queryIdMap.put(Long.valueOf(queryIdString), parameterizedQuery);
				session.setAttribute(Constants.QUERY_ID_MAP, queryIdMap);
			//}
			generateResponse(response, request, queryIdString, true);
		}
		else
		{
			generateResponse(response, request, queryIdString, false);
		}

		return null;
	}

	/**
	 * generates the response for the work flow.
	 * @param response http response
	 * @param request http request
	 * @param queryIdString query id
	 * @param isParametrized true if query is parameterized
	 * @throws IOException exception
	 * @throws QueryModuleException query exception
	 * @throws JSONException json exception
	 */
	private void generateResponse(HttpServletResponse response, HttpServletRequest request,
			String queryIdString, boolean isParametrized) throws IOException, QueryModuleException,
			JSONException
	{

		Writer writer = response.getWriter();
		JSONObject resultObject = null;
		resultObject = new JSONObject();
		resultObject.put(Constants.PROJECT_ID, request.getParameter(Constants.SELECTED_PROJECT));
		resultObject.put("queryIdString", queryIdString);
		resultObject.put("isParametrized", isParametrized);
		writer.write(new JSONObject().put("result", resultObject).toString());
	}

	

}
