package edu.wustl.query.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.global.Constants;

/**
 * @author chitra_garg
 * Save Workflow before query execution
 *
 */
public class SaveWorkflowAjaxHandlerAction extends AbstractQueryBaseAction 
{
	 /**
     * This action is used for processing Work flow object
     *
     * @param mapping
     *            ActionMapping.
     * @param form
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

	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{

		Writer writer = response.getWriter();
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		StringBuffer errormessage = new StringBuffer();

		Workflow workflow = new Workflow();
		setWorkflow(request, workflow);
		String workflowId = insertWorkflow(request, workflowBizLogic, workflow,
				errormessage);
		String requestForQueryTitle = request.getParameter("requestForQueryTitle");

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

				executionQueryResults.add(createResultJSON(queryTitle, String
						.valueOf(abstractQuery.getId())));
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

	/**
	 * @param request HttpServletRequest
	 * @param workflowBizLogic WorkflowBizLogic
	 * @param workflow Work-flow
	 * @param errormessage StringBuffer
	 * @return workflowId
	 */
	private String insertWorkflow(HttpServletRequest request,
			WorkflowBizLogic workflowBizLogic, Workflow workflow,
			StringBuffer errormessage)
			{
		String workflowId = request.getParameter("id");
		SessionDataBean sessionDataBean = (SessionDataBean) request
				.getSession().getAttribute(
						edu.wustl.common.util.global.Constants.SESSION_DATA);
		// for created by
		workflow.setCreatedBy(sessionDataBean.getUserId());
		try
		{
			if (workflowId == null || workflowId.equals(""))
			{
				// workflowBizLogic.insert(workflow, sessionDataBean,
				// Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, sessionDataBean);
			}
			else
			{
				workflow.setId(Long.valueOf(workflowId));
				// workflowBizLogic.update(workflow, null,
				// Constants.HIBERNATE_DAO, sessionDataBean);
				workflowBizLogic.update(workflow, null, sessionDataBean);

			}
			workflowId = String.valueOf(workflow.getId());
		}
		catch (BizLogicException e)
		{
			String [] valueArr=e.toMsgValuesArray();
			errormessage.append(valueArr[0]);
			Logger.out.error(e.getMessage(), e);
		}
		return workflowId;
	}

	/**
	 * set the workflow related
	 * parameters using request
	 * @param request HttpServletRequest
	 * @param workflow Workflow
	 */
	private void setWorkflow(HttpServletRequest request, Workflow workflow)
	{
		String workflowName = request.getParameter("name");
		String[] identifierArray  = request.getParameterValues("queryIdForRow");
		String[] expressionsArray  = request.getParameterValues("expression");
		//String[] workflowItemsId  = request.getParameterValues("workflowItemId");
		String description = request.getParameter("wfDescription");

		workflow.setName(workflowName);

		// changed for query name containing ","
		String[] displayQueryTitle = request
				.getParameterValues("displayQueryTitle");
	//	String[] identifierArray = identifier.split(",");
	//	String[] expressionsArray = expressions.split(",");
		List<WorkflowItem> workflowItemList = new ArrayList<WorkflowItem>();
		int numberOfRows = expressionsArray.length;
		for (int i = 0; i < numberOfRows; i++)
		{
			// IAbstractQuery query = workflow.getQuery(operatorsArray[i],
			// operandsArray[i],
			// displayQueryTitle[i],expressionsArray[i]);
			IAbstractQuery query = workflow.getQuery(expressionsArray[i]);
			query.setName(displayQueryTitle[i]);
			WorkflowItem workflowItem = new WorkflowItem();
//			if(workflowItemsId[i]!=null&&!workflowItemsId[i].equals(""))
//			{
//				workflowItem.setId(Long.valueOf(workflowItemsId[i]));
//			}
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
		workflow.setDescription(description);

	}

	/**
	 * @param queryTitle query Title
	 * @param identifier query id
	 * @return jsonObject JSONObject
	 * creates the jsonObject for input parameters
	 */
	private JSONObject createResultJSON(String queryTitle, String identifier)
	{
		JSONObject resultObject = null;
		resultObject = new JSONObject();
		try
		{
			resultObject.append("queryTitle", queryTitle);
			resultObject.append("queryId", identifier);
		}
		catch (JSONException e)
		{
			Logger.out.info("error in initializing json object " + e);
		}

		return resultObject;
	}
}
