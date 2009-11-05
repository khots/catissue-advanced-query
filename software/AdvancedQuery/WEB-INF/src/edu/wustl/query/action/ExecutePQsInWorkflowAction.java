
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author chitra_garg
 *
 */
public class ExecutePQsInWorkflowAction extends AbstractQueryBaseAction
{

	/**
	 * logger
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(ExecutePQsInWorkflowAction.class);

	@SuppressWarnings({"unchecked"})
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			// underscore separated QueryId List for which pop-up will be opened
			// This will exclude CQ .. and Queries Containing no parameters
			String queryIdString = "";
			HttpSession session = request.getSession();
			// To Do need to refactor again --- by baljeet
			session.removeAttribute(Constants.HTML_CONTENTS);
			session.removeAttribute("queriesIdString");
			session.removeAttribute(Constants.EXECID_MAPFORNOTPQ);
			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();

			SessionDataBean sessionData = (SessionDataBean) request.getSession().getAttribute(
					edu.wustl.common.util.global.Constants.SESSION_DATA);

			Long userId = sessionData.getUserId();
			CiderQueryPrivilege privilege = Utility.getPrivilegeFromSession(request);
			Workflow workflow = Utility.retrieveWorkflow(Long.valueOf(request
					.getParameter(Constants.WORKFLOW_ID)));

			CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails((Long.valueOf(request
					.getParameter(Constants.SELECTED_PROJECT))), userId, workflow, privilege);

			Map<Long, Long> execIdMap = new HashMap<Long, Long>();

			Set<Long> queriesIdsSet = null;
			//Set<Long> notPQIdsSet = new HashSet<Long>();
			Set<Long> cqIdsSet = (HashSet<Long>) session.getAttribute(Constants.PQ_ID_SET);
			session.removeAttribute(Constants.ENTITY_IDS_MAP);

			Map<Long, IParameterizedQuery> queryIdMap = new HashMap<Long, IParameterizedQuery>();
			if (cqIdsSet != null && !cqIdsSet.isEmpty())
			{
				queriesIdsSet = cqIdsSet;
				Iterator<Long> queriesIdsItr = queriesIdsSet.iterator();
				while (queriesIdsItr.hasNext())
				{
					Long queryId = queriesIdsItr.next();
					IParameterizedQuery parameterizedQuery = Utility.retrieveQuery(queryId);
					boolean isParametrized = isParametrized(parameterizedQuery);
					if (isParametrized)
					{
						queryIdString = queryIdString + queryId + "_";
						queryIdMap.put(parameterizedQuery.getId(), parameterizedQuery);
					}
					else
					{
						// if Query Contains no parameters then no need to open
						// pop-up for it
						// end execution can directly started
						// queriesIdsSet.remove(queryId);
						//notPQIdsSet.add(queryId);
						// Start execution and put in map
						// To Show Count in Work flow
						startExecution(request, execIdMap, queryId, parameterizedQuery);
					}
				}
				//				if (notPQIdsSet.size() != 0)
				//				{
				//					queriesIdsSet.removeAll(notPQIdsSet);
				//				}
				if (!"".equals(queryIdString))
				{
					queryIdString = queryIdString.substring(0, queryIdString.lastIndexOf('_'));
				}
				String execType = request.getParameter(Constants.REQ_ATTRIB_EXECUTION_TYPE);
				if (execType != null
						&& Constants.EXECUTION_TYPE_WORKFLOW.equalsIgnoreCase(execType.trim())
						&& queryIdString.equals(""))// for workflow
				{

					Map<Long, Long> executionIdMap = workflowBizLogic.executeAllCompositeQueries(
							workflowdetails, execIdMap);
					execIdMap.putAll(executionIdMap);
				}
				else if ("".equals(queryIdString))
				{
					//if none of the dependet queries are parametrized then control won't go 
					//in UpdatePQForWorkflowAction So need to start CQ execution 
					// here only  for #13765
					String queryIdStr = request.getParameter("queryId");
					final List<AbstractQuery> queryList = workflowBizLogic.retrieve(
							AbstractQuery.class.getName(), Constants.ID, Long.valueOf(queryIdStr));

					executeCQ(request, userId, privilege, workflowdetails, execIdMap, queryList);
				}
			}
			session.setAttribute(Constants.QUERY_ID_MAP, queryIdMap);
			responseForHTMLContents(queryIdString, session, queriesIdsSet);
			generateResponse(request, response, queryIdString, execIdMap);
			session.setAttribute(Constants.EXECID_MAPFORNOTPQ, execIdMap);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage(), e);
			throw e;
		}
		return null;
	}

	/**Method to execute CQs.
	 * @param request HttpServletRequest object
	 * @param userId user id
	 * @param privilege project privilege
	 * @param workflowdetails workflow details object
	 * @param execIdMap execution id map
	 * @param queryList query list
	 * @throws BizLogicException Biz logic exception
	 */
	private void executeCQ(HttpServletRequest request, Long userId, CiderQueryPrivilege privilege,
			CiderWorkFlowDetails workflowdetails, Map<Long, Long> execIdMap,
			final List<AbstractQuery> queryList) throws BizLogicException
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		if (queryList.get(0) instanceof ICompositeQuery)
		{
			edu.wustl.common.querysuite.queryobject.impl.AbstractQuery query = queryList.get(0);

			// TODO
			// Create objects to remove request parameter from
			// workflowBizLogic .executeGetCountQuery

			AbstractQueryUIManager qUIManager = null;

			Long projId = null;
			if ((Long.valueOf(request.getParameter(Constants.SELECTED_PROJECT))) > 0)
			{
				projId = (Long.valueOf(request.getParameter(Constants.SELECTED_PROJECT)));
			}

			edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
					userId, projId, request.getRemoteAddr(), (Long.valueOf(request
							.getParameter(Constants.WORKFLOW_ID))), privilege);

			// replaced with
			Map<Long, Long> executionIdMap = workflowBizLogic.executeGetCountQuery(workflowdetails,
					ciderQuery, qUIManager, query);
			// ends

			execIdMap.putAll(executionIdMap);

		}
	}

	/**
	 * generates response for query id string and query execution id map.
	 * @param request HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @param queryIdString query Id String.
	 * @param execIdMap execution Id Map.
	 * @throws IOException IOException.
	 * @throws QueryModuleException QueryModuleException.
	 * @throws JSONException JSONException.
	 */
	private void generateResponse(HttpServletRequest request, HttpServletResponse response,
			String queryIdString, Map<Long, Long> execIdMap) throws IOException,
			QueryModuleException, JSONException
	{
		if (execIdMap.isEmpty())//execIdMap.size() != 0)
		{
			Writer writer = response.getWriter();
			JSONObject resultObject = new JSONObject();
			resultObject.put(Constants.QUERY_ID_STRING, queryIdString);
			writer.write(new JSONObject().put("result", resultObject).toString());
		}
		else
		{
			generateResponse(response, request, execIdMap, queryIdString);
		}
	}

	/**
	 * generates response if the HTML contents are not empty.
	 * @param queryIdString  query Id String.
	 * @param session HttpSession.
	 * @param queriesIdsSet html Contents .
	 */
	private void responseForHTMLContents(String queryIdString, HttpSession session,
			Set<Long> queriesIdsSet)
	{
		if (queriesIdsSet != null && !queriesIdsSet.isEmpty())
		{
			session.setAttribute(Constants.QUERY_ID_STRING, queryIdString);
		}
	}

	/**
	 * generates the response for the work flow.
	 *
	 * @param response HttpServletResponse
	 * @param request HttpServletRequest
	 * @param execIdMap execution Id Map
	 * @param queryIdString query Id String
	 * @throws IOException IOException
	 * @throws QueryModuleException QueryModuleException
	 * @throws JSONException JSONException.
	 */
	private void generateResponse(HttpServletResponse response, HttpServletRequest request,
			Map<Long, Long> execIdMap, String queryIdString) throws IOException,
			QueryModuleException, JSONException
	{
		Writer writer = response.getWriter();
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		QueryPrivilege privilege = Utility.getPrivilegeFromSession(request);
		List<JSONObject> executionQueryResults = edu.wustl.query.util.global.Utility
				.generateExecutionQueryResults(execIdMap, new WorkflowBizLogic(), qUIManager,
						privilege);

		JSONObject resultObject = new JSONObject();
		resultObject.put("executionQueryResults", executionQueryResults);
		resultObject.put("projectId", request.getParameter(Constants.SELECTED_PROJECT));
		resultObject.put("queryIdString", queryIdString);
		// resultObject.put(Constants.HTML_CONTENTS, htmlContents);
		writer.write(new JSONObject().put("result", resultObject).toString());
	}

	/**
	 * This method starts the execution of parameterized Query containing no conditions.
	 * @param request HttpServletRequest
	 * @param execIdMap execution Id Map
	 * @param queryId query Id
	 * @param parameterizedQuery Query
	 * @throws QueryModuleException QueryModuleException
	 * @throws SqlException SqlException
	 * @throws MultipleRootsException MultipleRootsException.
	 */
	private void startExecution(HttpServletRequest request, Map<Long, Long> execIdMap,
			Long queryId, IParameterizedQuery parameterizedQuery) throws QueryModuleException,
			MultipleRootsException, SqlException
	{

		AbstractQueryUIManager QUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request,
						parameterizedQuery);

		Long query_exec_id = QUIManager.searchQuery();
		execIdMap.put(queryId, query_exec_id);
	}

	/**
	 * returns true if query Contains Parameterized Conditions.
	 * @param parameterizedQuery query object
	 * @return query parameterizedQuery or not
	 * @throws BizLogicException BizLogicException
	 */
	private boolean isParametrized(IParameterizedQuery parameterizedQuery) throws BizLogicException
	{
		boolean isParametrized = true;
		if (parameterizedQuery.getParameters().isEmpty())
		{
			isParametrized = false;
		}
		return isParametrized;

	}

}
