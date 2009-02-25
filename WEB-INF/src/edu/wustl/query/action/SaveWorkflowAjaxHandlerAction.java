
package edu.wustl.query.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.global.Constants;

public class SaveWorkflowAjaxHandlerAction extends Action
{

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		Writer writer = response.getWriter();
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		StringBuffer errormessage = new StringBuffer();

		Workflow workflow = new Workflow();
		setWorkflow(request, workflow);
		String workflowId = insertWorkflow(request, workflowBizLogic, workflow, errormessage);
		String requestForQueryTitle = request.getParameter("queryTitle");

		List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();

		List<WorkflowItem> workflowItemList = workflow.getWorkflowItemList();
		if (workflowItemList != null && workflow.getId() != null)
		{
			for (int i = 0; i < workflowItemList.size(); i++)
			{
				WorkflowItem workflowItem = workflowItemList.get(i);
				IAbstractQuery abstractQuery = workflowItem.getQuery();
				String queryTitle;
				if (abstractQuery instanceof ParameterizedQuery)
				{
					queryTitle = ((ParameterizedQuery) abstractQuery).getName();
				}
				else
				{
					queryTitle = ((CompositeQuery) abstractQuery).getName();
				}

				executionQueryResults.add(createResultJSON(queryTitle, String.valueOf(abstractQuery
						.getId())));
			}
		}

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("queryTitle", requestForQueryTitle);
		jsonResponse.put("executionQueryResults", executionQueryResults);
		jsonResponse.put("errormessage", errormessage);
		jsonResponse.put("workflowId", workflowId);

		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		writer.write(jsonResponse.toString());
		return null;
	}

	private String insertWorkflow(HttpServletRequest request, WorkflowBizLogic workflowBizLogic,
			Workflow workflow, StringBuffer errormessage) throws UserNotAuthorizedException
	{

		String workflowId = request.getParameter("workflowId");
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				Constants.SESSION_DATA);
		try
		{
			if (workflowId == null || workflowId.equals(""))
			{
				workflowBizLogic.insert(workflow, sessionDataBean, Constants.HIBERNATE_DAO);
			}
			else
			{
				workflow.setId(Long.valueOf(workflowId));
				workflowBizLogic.update(workflow, null, Constants.HIBERNATE_DAO, sessionDataBean);
			}
			workflowId = String.valueOf(workflow.getId());
		}
		catch (BizLogicException e)
		{
			errormessage.append(e.getMessage());
			Logger.out.error(e.getMessage(), e);
		}
		//		if(workflowId!=null)
		//		{
		//			workflowId=String.valueOf(workflow.getId());
		//		}
		return workflowId;
	}

	private void setWorkflow(HttpServletRequest request, Workflow workflow)
	{

		String operands = request.getParameter("operands");
		String operators = request.getParameter("operators");
		String workflowName = request.getParameter("workflowName");
		String querytitles = request.getParameter("displayQueryTitle");
		String identifier = request.getParameter("identifier");
		String expressions= request.getParameter("expression");

		workflow.setName(workflowName);
		String[] operandsArray = operands.split(",");
		String[] operatorsArray = operators.split(",");
		String[] displayQueryTitle = querytitles.split(",");
		String[] identifierArray = identifier.split(",");
		String[] expressionsArray = expressions.split(",");
		List<WorkflowItem> workflowItemList = new ArrayList<WorkflowItem>();
		int numberOfRows = 0;

		if (operators != null)
		{
			numberOfRows = operatorsArray.length;
		}
		for (int i = 0; i < numberOfRows; i++)
		{
//			IAbstractQuery query = workflow.getQuery(operatorsArray[i], operandsArray[i],
//					displayQueryTitle[i],expressionsArray[i]);
            IAbstractQuery query = workflow.getQuery(expressionsArray[i]);
            query.setName(displayQueryTitle[i]);
			WorkflowItem workflowItem = new WorkflowItem();
			if (!identifierArray[i].contains("_"))
			{
				query.setId(Long.parseLong(identifierArray[i]));
			}
			workflowItem.setQuery(query);
			workflowItem.setPosition(i);
			workflowItemList.add(workflowItem);

		}
		workflow.setCreatedOn(new Date());
		workflow.setWorkflowItemList(workflowItemList);

	}

	/**
	 * @param queryId =Query identifier for which execute request sent
	 * @param errormessage
	 * @param workflowId
	 * @param queryIndex=row number where results to be displayed
	 * @param resultCount=value of result count for query
	 * @returns jsonObject
	 *
	 * creates the jsonObject for input parameters
	 */
	private JSONObject createResultJSON(String queryTitle, String id)
	{
		JSONObject resultObject = null;
		resultObject = new JSONObject();
		try
		{
			resultObject.append("queryTitle", queryTitle);
			resultObject.append("queryId", id);
		}
		catch (JSONException e)
		{
			Logger.out.info("error in initializing json object " + e);
		}

		return resultObject;
	}
}
