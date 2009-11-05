
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.DAOUtil;

/**
 * @author niharika_sharma
 *
 */
public class WorkflowAjaxHandlerAction extends AbstractQueryBaseAction
{

	private static final long ONE = -1L;
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(WorkflowAjaxHandlerAction.class);

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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WorkflowBizLogic workflowBizLogic = null;
		// for saving workflow when click execute
		HttpSession session = request.getSession();
		// Get the Query information
		Long queryId = ONE;
		String queryIdStr = request.getParameter("queryId");
		if (queryIdStr != null && !"".equals(queryIdStr.trim()))
		{
			queryId = Long.valueOf(queryIdStr);
		}

		// String queryTitle=request.getParameter("queryTitle");
		String operation = request.getParameter(Constants.OPERATION);
		String state = request.getParameter("state");
		Writer writer = response.getWriter();
		if (operation != null && "execute".equals(operation.trim()))
		{
			// Fetch the current workflow
			Long workflowId = Long.valueOf(request.getParameter("workflowId"));
			HibernateDAO dao = null;
			try
			{

				workflowBizLogic = (WorkflowBizLogic) BizLogicFactory.getInstance().getBizLogic(
						Constants.WORKFLOW_BIZLOGIC_ID);

				String queryExecIdStr = request.getParameter("executionLogId");
				Long queryExecId = ONE;
				if (queryExecIdStr != null && !"".equals(queryExecIdStr.trim()))
				{
					queryExecId = Long.valueOf(queryExecIdStr);
				}

				List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();

				if (state != null && state.equals("cancel"))
				{
					JSONObject resultObject = new JSONObject();
					resultObject.append("queryId", queryId);
					try
					{

						if (request.getParameter("removeExecutedCount").equals("true"))
						{
							resultObject.put("removeExecutedCount", "removeExecutedCount");
						}
						AbstractQueryManager qManager = AbstractQueryManagerFactory
								.getDefaultAbstractQueryManager();
						qManager.cancel(queryExecId);
						// Count
						// resultCount=workflowBizLogic.getCount(queryExecId);

						response.setContentType(Constants.CONTENT_TYPE_TEXT);
						writer.write(resultObject.toString());
					}
					catch (Exception e)
					{

						resultObject.append("execption", "execption");

						// resultObject.append("executionLogId", qu>>>>>> .r5829
						response.setContentType(Constants.CONTENT_TYPE_TEXT);
						writer.write(resultObject.toString());
						throw e;
					}

				}
				else
				{
					AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
							.getDefaultAbstractUIQueryManager();

					// Get the current User Id
					SessionDataBean sessionData = (SessionDataBean) request.getSession()
							.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
					Long userId = sessionData.getUserId();

					// get the current selected projectId
					Long project_id = null;
					if (request.getParameter(Constants.SELECTED_PROJECT) != null
							&& !(request.getParameter(Constants.SELECTED_PROJECT)).equals(""))
					{
						project_id = (Long.valueOf((request
								.getParameter(Constants.SELECTED_PROJECT))));
					}
					QueryPrivilege privilege = null;
					if (session.getAttribute(Constants.QUERY_PRIVILEGE) != null)
					{
						privilege = (QueryPrivilege) session
								.getAttribute(Constants.QUERY_PRIVILEGE);
					}
					// Get the executionType
					String execType = request.getParameter(Constants.REQ_ATTRIB_EXECUTION_TYPE);
					if (execType != null
							&& Constants.EXECUTION_TYPE_WORKFLOW.equalsIgnoreCase(execType.trim()))
					{
						Workflow workflow = null;
						// dao =
						// DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO
						// );
						// dao.openSession(null);

						dao = DAOUtil.getHibernateDAO(sessionData);

						// Get the workflow
						workflow = (Workflow) dao
								.retrieveById(Workflow.class.getName(), workflowId);

						// Create a workflow details object
						// FIXME - CiderWorkFlowdetails cannot be accessed from
						// here.
						// Need a
						// good enough way to create the workflow details object
						// specific to
						// Cider and AdvancedQuery.
						CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(project_id,
								userId, workflow, (CiderQueryPrivilege) privilege);

						Map<Long, Long> executionIdMap = workflowBizLogic
								.runWorkflow(workflowdetails);
						executionQueryResults = Utility.generateExecutionQueryResults(
								executionIdMap, workflowBizLogic, qUIManager, privilege);
					}
					else
					{
						// normal query execution
						if (queryExecId == 0)
						{
							dao = DAOUtil.getHibernateDAO(sessionData);

							// Get the workflow
							Workflow workflow = (Workflow) dao.retrieveById(Workflow.class.getName(),
									workflowId);

							CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
									project_id, userId, workflow, (CiderQueryPrivilege) privilege);

							// queryExecId =
							// workflowBizLogic.executeGetCountQuery(queryId,
							// request);

							final List<AbstractQuery> queryList = workflowBizLogic.retrieve(
									AbstractQuery.class.getName(), Constants.ID, queryId);
							AbstractQuery query = queryList.get(0);
							// TODO
							// Create objects to remove request parameter from
							// workflowBizLogic .executeGetCountQuery
							edu.wustl.common.query.AbstractQuery ciderQuery = null;
							privilege = Utility.getPrivilegeFromSession(request);
							if (query instanceof IParameterizedQuery)
							{
								qUIManager = AbstractQueryUIManagerFactory
										.configureDefaultAbstractUIQueryManager(this.getClass(),
												request, (IQuery) query);

							}
							else
							{

								Long projId = null;
								if (project_id > 0)
								{
									projId = project_id;
								}

								ciderQuery = new CiderQuery(query, 0L, null, userId, projId,
										request.getRemoteAddr(), workflowId,
										(CiderQueryPrivilege) privilege);
							}
							// replaced with
							Map<Long, Long> executionIdMap = workflowBizLogic.executeGetCountQuery(
									workflowdetails, ciderQuery, qUIManager, query);
							// ends

							// TODO replace with new
							// Map<Long, Long> executionIdMap = workflowBizLogic
							// .executeGetCountQuery(workflowdetails, queryId,
							// request);
							executionQueryResults = Utility.generateExecutionQueryResults(
									executionIdMap, workflowBizLogic, qUIManager, privilege);

						}
						else
						{
							// First sleep for say 5 seconds
							try
							{
								Thread.sleep(Variables.ajaxCallSleepTime);
							}
							catch (InterruptedException ie)
							{
								logger.debug(ie.getMessage(), ie);
							}

							Count resultCount = workflowBizLogic.getCount(queryExecId, privilege);
							if (project_id > 0)
							{
								qUIManager.auditTooFewRecords(resultCount, privilege);
							}
							JSONObject jsonObject = Utility.createResultJSON(queryId, resultCount.getCount(),
									resultCount.getStatus(), resultCount.getQueryExectionId());
							executionQueryResults.add(jsonObject);
						}
					}
					response.setContentType(Constants.CONTENT_TYPE_TEXT);

					Utility.createResponseForWf(request, writer, executionQueryResults);

					// writer.write(new
					// JSONObject().put("executionQueryResults",
					// executionQueryResults).toString());

				}
			}
			catch (Exception e)
			{
				logger.debug(e.getMessage(), e);

				try
				{
					response.setContentType("text/xml");
					writer.write(Constants.QUERY_EXCEPTION);
				}
				catch (IOException e1)
				{
					logger.debug(e1.getMessage(), e1);
					throw e1;
				}
				throw e;
			}
			finally
			{
				if (dao != null)
				{
					DAOUtil.closeHibernateDAO(dao);
				}
			}

		}

		return null;
	}
}
