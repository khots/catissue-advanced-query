
package edu.wustl.query.action;

import java.io.Writer;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;

/**
 * @author niharika_sharma
 *
 */
public class WorkflowAjaxHandlerAction extends Action
{

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, 
	 * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http. HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String operation = request.getParameter(Constants.OPERATION);

		String id = request.getParameter("ID");
		String queryIndex = request.getParameter("queryId");

		if (request.getSession().getAttribute("idList") == null)
		{

			request.getSession().setAttribute("idList", new HashSet<String>());
		}

		String state = request.getParameter("state");
		if (state != null && state.equals("cancel"))
		{
			HashSet<String> idList = (HashSet<String>) request.getSession().getAttribute("idList");
			idList.remove(queryIndex);
		}

		if (state != null && state.equals("start"))
		{
			HashSet<String> idList = (HashSet<String>) request.getSession().getAttribute("idList");
			idList.add(queryIndex);
		}

		Writer writer = response.getWriter();
		if (operation != null && "execute".equals(operation.trim()))
		{
			//Get all query ids
			Long queryId = Long.valueOf(id);

			WorkflowBizLogic workflowBizLogic = (WorkflowBizLogic) BizLogicFactory.getInstance()
					.getBizLogic(Constants.WORKFLOW_BIZLOGIC_ID);
			int queryExecId = Integer.valueOf(request.getParameter("executionLogId"));
			if (queryExecId == 0)
			{

				queryExecId = workflowBizLogic.executeGetCountQuery(queryId, request);
			}

			HashMap<String, Count> resultCountMap = workflowBizLogic.getCount(
					(HashSet<String>) request.getSession().getAttribute("idList"), queryExecId);

			String[] keyArray = (String[]) resultCountMap.keySet().toArray(
					new String[resultCountMap.keySet().toArray().length]);

			List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();
			for (int i = 0; i < keyArray.length; i++)
			{
				Count count = resultCountMap.get(keyArray[i]);
				executionQueryResults.add(createResultJSON(queryId, keyArray[i], count.getCount(),
						count.getStatus(), count.getQuery_exection_id()));
			}

			response.setContentType(Constants.CONTENT_TYPE_TEXT);
			writer.write(new JSONObject().put("executionQueryResults", executionQueryResults)
					.toString());

		}

		return null;
	}

	/**
	 * @param queryId =Query identifier for which execute request sent   
	 * @param queryIndex=row number where results to be displayed   
	 * @param resultCount=value of result count for query
	 * @returns jsonObject 
	 * 
	 * creates the jsonObject for input parameters
	 */
	private JSONObject createResultJSON(Long queryId, String queryIndex, int resultCount,
			String status, int executionLogId)
	{
		JSONObject resultObject = null;
		resultObject = new JSONObject();
		try
		{
			resultObject.append("queryId", queryId);
			resultObject.append("queryIndex", queryIndex);
			resultObject.append("queryResult", resultCount);
			resultObject.append("status", status);
			resultObject.append("executionLogId", executionLogId);
		}
		catch (JSONException e)
		{
			Logger.out.info("error in initializing json object " + e);
		}

		return resultObject;
	}
}
