
package edu.wustl.query.action;

import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import edu.wustl.common.beans.QueryWorkflowBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author chitra_garg
 *         This class will update the PQ from Workflow . Input to this class
 *         will be query ids.
 */
public class UpdatePQForWorkflowAction extends AbstractQueryBaseAction
{

	/**
	 * logger
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(UpdatePQForWorkflowAction.class);

	@SuppressWarnings("unchecked")
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map<Long, Long> execIdMap = new HashMap<Long, Long>();
		Long workflowId = Long.valueOf(request.getParameter("workflowId"));
		String allQueriesConitionStr = request.getParameter("allQueriesCondStr");
		Long project_id = getProjectId(request);

		HttpSession session = request.getSession();

		Map<QueryWorkflowBean, IParameterizedQuery> allSessionUpdatedQueries = (HashMap<QueryWorkflowBean, IParameterizedQuery>) session
				.getAttribute("allSessionUpdatedQueries");

		AbstractQueryUIManager QUIManager = null;
		Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap = (Map<Long, Map<Integer, ICustomFormula>>) session
				.getAttribute("eachQueryCFMap");
		//Creating a new jason Object
		JSONObject jsonObject = new JSONObject(allQueriesConitionStr);
		Iterator<Object> keyItr = jsonObject.keys();
		String errorMsg = "";
		/*
		 * This map is used to modify workflow object used
		 * while generating workflow details.
		 * */
		Map<Long, IParameterizedQuery> toUpdateWorkflow = new HashMap<Long, IParameterizedQuery>();

		while (keyItr.hasNext())
		{
			//As Query id is the Key,
			//so retrieve the corresponding the query condition object
			String queryId = keyItr.next().toString();
			JSONObject innerObj = (JSONObject) jsonObject.get(queryId);
			Iterator<Object> innerObjKeyItr = innerObj.keys();

			QueryWorkflowBean queryWorkflowBean = new QueryWorkflowBean();
			queryWorkflowBean.setQueryId(Long.valueOf(queryId));
			queryWorkflowBean.setWorkflowId(workflowId);
			queryWorkflowBean.setProjectId(project_id);
			IParameterizedQuery parameterizedQuery = (IParameterizedQuery) allSessionUpdatedQueries
					.get(queryWorkflowBean);
			if (parameterizedQuery != null)
			{
				errorMsg = updateParameterizedQuery(eachQueryCFMap, queryId, innerObj,
						innerObjKeyItr, parameterizedQuery);
				if (!(errorMsg.length() > 0))
				{
					IParameterizedQuery queryClone = new DyExtnObjectCloner()
							.clone(parameterizedQuery);

					//Now remove the empty conditions from the Query
					//Utility.removeEmptyCoditionsFromQuery(queryClone);
					toUpdateWorkflow.put(queryClone.getId(), queryClone);
					//Updating the updated query again for default values before execution
					QUIManager = AbstractQueryUIManagerFactory
							.configureDefaultAbstractUIQueryManager(this.getClass(), request,
									queryClone);
					ParameterizedQuery query = (ParameterizedQuery) QUIManager.getAbstractQuery()
							.getQuery();
					Long query_exec_id = QUIManager.searchQuery();
					execIdMap.put(query.getId(), query_exec_id);

					QUIManager.insertParametersForExecution(query_exec_id, parameterizedQuery);
					parameterizedQuery.setId(query.getId());

				}
				else
				{
					break;
				}
			}
		}
		Workflow workflow = generateWorkflow(workflowId, toUpdateWorkflow);
		if (!(errorMsg.length() > 0))
		{
			CiderQueryPrivilege privilege = generatPrivilege(request, execIdMap, workflow);
			AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
					.getDefaultAbstractUIQueryManager();

			Writer writer = response.getWriter();
			List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();
			executionQueryResults = edu.wustl.query.util.global.Utility
					.generateExecutionQueryResults(execIdMap, new WorkflowBizLogic(), qUIManager,
							privilege);

			Utility.createResponseForWf(request, writer, executionQueryResults);
		}
		else
		{
			response.getWriter().write(new JSONObject().put("error", errorMsg).toString());
		}
		// To Do handle reponse ..
		return null;
	}

	/**Method to update parameterized query.
	 * @param eachQueryCFMap query CF map
	 * @param queryId query id
	 * @param innerObj inner object
	 * @param innerObjKeyItr inner object iterator
	 * @param parameterizedQuery parameterized query
	 * @return String response
	 * @throws JSONException JSON exception
	 */
	private String updateParameterizedQuery(Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap,
			String queryId, JSONObject innerObj, Iterator<Object> innerObjKeyItr,
			IParameterizedQuery parameterizedQuery) throws JSONException
	{
		String conditionString = "";
		String rhsList = "";
		String errorMessage = "";

		while (innerObjKeyItr.hasNext())
		{
			String innerKey = innerObjKeyItr.next().toString();
			if (innerKey.equalsIgnoreCase("queryConditions"))
			{
				//This is normal conditions String
				conditionString = innerObj.get(innerKey).toString();
			}
			else
			{
				//else this is temporal condition string
				rhsList = innerObj.get(innerKey).toString();
			}
		}

		CreateQueryObjectBizLogic createQueryBizLogic = new CreateQueryObjectBizLogic();
		if (conditionString != null)
		{

			//Here updating Query for normal Conditions
			errorMessage = createQueryBizLogic.setInputDataToQuery(conditionString,
					parameterizedQuery.getConstraints(), null, parameterizedQuery);
			logger.info(errorMessage);
			//   error.append(errorMessage);
			if (!(errorMessage.length() > 0)) //if error does not exists
			{
				Map<Integer, ICustomFormula> customFormulaIndexMap = eachQueryCFMap.get(Long
						.valueOf(queryId));
				errorMessage = createQueryBizLogic.setInputDataToTQ(parameterizedQuery,
						Constants.EXECUTE_QUERY_PAGE, rhsList, customFormulaIndexMap);
			}

		}
		return errorMessage;
	}

	/**Method to get project id from request.
	 * @param request HttpServletRequest object
	 * @return Long project id
	 */
	private Long getProjectId(HttpServletRequest request)
	{
		Long project_id = null;
		if (request.getParameter(Constants.SELECTED_PROJECT) != null
				&& !(request.getParameter(Constants.SELECTED_PROJECT)).equals(""))
		{
			project_id = (Long.valueOf((request.getParameter(Constants.SELECTED_PROJECT))));
		}
		return project_id;
	}

	/**Method to generate privilege and to generate execution id map.
	 * @param request HttpServletRequest object
	 * @param execIdMap execution id map
	 * @param workflow workflow object
	 * @return privilege
	 * @throws DAOException DAO exception
	 * @throws BizLogicException Biz Logic Exception
	 * @throws QueryModuleException Query Module exception
	 * @throws SQLException SQL Exception
	 */
	private CiderQueryPrivilege generatPrivilege(HttpServletRequest request,
			Map<Long, Long> execIdMap, Workflow workflow) throws DAOException, BizLogicException,
			QueryModuleException, SQLException
	{
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		HttpSession session = request.getSession();
		WorkflowBizLogic workflowBizLogic = (WorkflowBizLogic) BizLogicFactory.getInstance()
				.getBizLogic(Constants.WORKFLOW_BIZLOGIC_ID);

		CiderQueryPrivilege privilege = null;
		privilege = Utility.getPrivilegeFromSession(request);

		Long project_id = getProjectId(request);
		Long projId = null;
		if (project_id > 0)
		{
			projId = project_id;
		}

		SessionDataBean sessionData = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		Long userId = sessionData.getUserId();

		CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(project_id, userId,
				workflow, privilege);

		String execType = request.getParameter(Constants.REQ_ATTRIB_EXECUTION_TYPE);
		if (execType != null && Constants.EXECUTION_TYPE_WORKFLOW.equalsIgnoreCase(execType.trim()))// for workflow
		{

			generatePreExecutionIdMap(execIdMap, session);

			Map<Long, Long> executionIdMap = workflowBizLogic.executeAllCompositeQueries(
					workflowdetails, execIdMap);
			execIdMap.putAll(executionIdMap);
		}
		else
		{
			// For CQ
			String queryIdStr = request.getParameter("queryId");
			final List<AbstractQuery> queryList = bizLogic.retrieve(AbstractQuery.class.getName(),
					Constants.ID, Long.valueOf(queryIdStr));

			if (queryList.get(0) instanceof ICompositeQuery)
			{
				edu.wustl.common.querysuite.queryobject.impl.AbstractQuery query = queryList.get(0);

				// TODO
				// Create objects to remove request parameter from
				// workflowBizLogic .executeGetCountQuery

				AbstractQueryUIManager qUIManager = null;
				edu.wustl.common.query.AbstractQuery ciderQuery = null;
				if (query instanceof IParameterizedQuery)
				{
					qUIManager = AbstractQueryUIManagerFactory
							.configureDefaultAbstractUIQueryManager(this.getClass(), request,
									(IQuery) query);
				}
				else
				{

					ciderQuery = new CiderQuery(query, 0L, null, userId, projId, request
							.getRemoteAddr(), workflow.getId(), privilege);
				}
				// replaced with
				Map<Long, Long> executionIdMap = workflowBizLogic.executeGetCountQuery(
						workflowdetails, ciderQuery, qUIManager, query);
				// ends

				execIdMap.putAll(executionIdMap);

			}
		}
		return privilege;
	}

	/**
	 * To set the already executed Queries.
	 * These queries are not parameterized.
	 * We need to populate this because execution of wf/CQ
	 * needs all the executed queries
	 * @param execIdMap  execution id map
	 * @param session HttpSession object
	 * @throws DAOException DAO Exception
	 * @throws SQLException SQL Exception
	 */
	private void generatePreExecutionIdMap(Map<Long, Long> execIdMap, HttpSession session)
			throws DAOException, SQLException
	{

		if (session.getAttribute(Constants.EXECID_MAPFORNOTPQ) != null)
		{
			Map<Long, Long> preExecIdMap = (Map<Long, Long>) session
					.getAttribute(Constants.EXECID_MAPFORNOTPQ);

			if (preExecIdMap != null && !preExecIdMap.isEmpty())
			{
				execIdMap.putAll(preExecIdMap);
			}
		}
	}

	/**
	 * modifies the workflow according to the new conditions.
	 * @param workflowId Long workflow id
	 * @param toUpdateWorkflow map of to update workflow
	 * @return Workflow object
	 * @throws DAOException DAO Exception
	 */
	private Workflow generateWorkflow(Long workflowId,
			Map<Long, IParameterizedQuery> toUpdateWorkflow) throws DAOException
	{

		Workflow workflow = Utility.retrieveWorkflow(workflowId);
		List<WorkflowItem> workflowItemList = workflow.getWorkflowItemList();
		for (WorkflowItem workflowItem : workflowItemList)
		{
			Long queryID = workflowItem.getQuery().getId();
			IParameterizedQuery parameterizedQuery = toUpdateWorkflow.get(queryID);
			if (parameterizedQuery != null)
			{
				workflowItem.setQuery(parameterizedQuery);

			}

		}

		return workflow;
	}

}
