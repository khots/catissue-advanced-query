
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.dao.AbstractDAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

/**
 * @author niharika_sharma
 *
 */
public class WorkflowAjaxHandlerAction extends Action
{
	private static org.apache.log4j.Logger logger =Logger.getLogger(RecentQueriesAjaxHandlerAction.class);
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http. HttpServletResponse)
	 */
	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WorkflowBizLogic workflowBizLogic=null;
		//for saving workflow when click execute

		// Get the Query information
		Long queryId=(long)-1;
		String queryIdStr = request.getParameter("queryId");
		if (queryIdStr != null && !"".equals(queryIdStr.trim()))
        {
            queryId=Long.valueOf(queryIdStr);
        }

		//String queryTitle=request.getParameter("queryTitle");
		String operation = request.getParameter(Constants.OPERATION);
		String state = request.getParameter("state");
		Writer writer = response.getWriter();
		if (operation != null && "execute".equals(operation.trim()))
		{
		    // Fetch the current workflow
	        String workflowId=request.getParameter("workflowId");
	        AbstractDAO dao= null;
	        try
	        {

                workflowBizLogic = (WorkflowBizLogic) BizLogicFactory
                        .getInstance().getBizLogic(
                                Constants.WORKFLOW_BIZLOGIC_ID);

                String queryExecIdStr = request.getParameter("executionLogId");
                int queryExecId = -1;
                if (queryExecIdStr != null && !"".equals(queryExecIdStr.trim())) {
                    queryExecId = Integer.valueOf(queryExecIdStr);
                }
                // TO DO
                // WorkflowBizLogic--->executeGetCountQuery
                // WorkflowBizLogic--->getCount
                List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();

                if (state != null && state.equals("cancel"))
                {
                    JSONObject resultObject = null;
                    resultObject = new JSONObject();
                    resultObject.append("queryId", queryId);
                    try
                    {

                        if (request.getParameter("removeExecutedCount").equals(
                                "true"))
                        {
                            resultObject.put("removeExecutedCount",
                                    "removeExecutedCount");
                        }
                        AbstractQueryManager qManager = AbstractQueryManagerFactory
                                .getDefaultAbstractQueryManager();
                        qManager.cancel(queryExecId);
                        // Count
                        // resultCount=workflowBizLogic.getCount(queryExecId);

                        response.setContentType(Constants.CONTENT_TYPE_TEXT);
                        writer.write(resultObject.toString());
                    } catch (Exception e)
                    {

                        resultObject.append("execption", "execption");

                        // resultObject.append("executionLogId", qu>>>>>> .r5829
                        response.setContentType(Constants.CONTENT_TYPE_TEXT);
                        writer.write(resultObject.toString());

                    }

                }
                else
                {

                    Count resultCount = null;
                    JSONObject jsonObject = null;
                    AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
                    .getDefaultAbstractUIQueryManager();

                    // Get the current User Id
                    SessionDataBean sessionData = (SessionDataBean) request
                            .getSession().getAttribute(Constants.SESSION_DATA);
                    Long userId = sessionData.getUserId();

                    // get the current selected projectId
                    int project_id = 0;
                    if (request.getParameter(Constants.SELECTED_PROJECT) != null
                            && !(request.getParameter(Constants.SELECTED_PROJECT))
                                    .equals(""))
                    {
                        project_id = (Integer.valueOf((request
                                .getParameter(Constants.SELECTED_PROJECT))));
                    }
                    QueryPrivilege privilege = null;
                    if(request.getSession().getAttribute(Constants.QUERY_PRIVILEGE)!=null)
            		{
            			privilege =(QueryPrivilege)request.getSession().getAttribute(Constants.QUERY_PRIVILEGE);
            		}
            		// Get the executionType
                    String execType = request
                            .getParameter(Constants.REQ_ATTRIB_EXECUTION_TYPE);
                    if (execType != null
                            && Constants.EXECUTION_TYPE_WORKFLOW
                                    .equalsIgnoreCase(execType.trim()))
                    {
                        Workflow workflow = null;
                        dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
                        dao.openSession(null);


                        // Get the workflow
                        workflow = (Workflow) dao.retrieve(Workflow.class.getName(),
                                Long.valueOf(workflowId));



                        // Create a workflow details object
                        // FIXME - CiderWorkFlowdetails cannot be accessed from here.
                        // Need a
                        // good enough way to create the workflow details object
                        // specific to
                        // Cider and AdvancedQuery.
                        CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
                                project_id, userId.intValue(), workflow,(CiderQueryPrivilege)privilege);



                        Map<Long, Integer> executionIdMap = workflowBizLogic
                                .runWorkflow(workflowdetails,
                                        request);
                        executionQueryResults=
                               Utility.generateExecutionQueryResults(
                                        executionIdMap, workflowBizLogic,
                                        qUIManager,privilege);
                    }
                    else
                    {
                        // normal query execution
                        if (queryExecId == 0)
                        {
                            Workflow workflow = null;
                            dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
                            dao.openSession(null);

                            // Get the workflow
                            workflow = (Workflow) dao.retrieve(Workflow.class.getName(),
                                    Long.valueOf(workflowId));

                            CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
                                    project_id, userId.intValue(), workflow,(CiderQueryPrivilege)privilege);

                            // queryExecId =
                            // workflowBizLogic.executeGetCountQuery(queryId,
                            // request);
                            Map<Long, Integer> executionIdMap = workflowBizLogic
                                    .executeGetCountQuery(workflowdetails, queryId,
                                            request);
                            executionQueryResults=
                            	Utility.generateExecutionQueryResults(
                                            executionIdMap, workflowBizLogic,
                                            qUIManager,privilege);

                        } else
                        {
                            // First sleep for say 5 seconds
                            try
                            {
                                Thread.sleep(5000);
                            }
                            catch (InterruptedException ie)
                            {
                            	logger.debug(ie.getMessage(),ie);
                            }

                            resultCount = workflowBizLogic.getCount(queryExecId,privilege);
                            if (project_id > 0)
                            {
                            	qUIManager.auditTooFewRecords(resultCount,privilege);
                            }
                            jsonObject = Utility.createResultJSON(queryId,
                                    resultCount.getCount(), resultCount
                                            .getStatus(), resultCount
                                            .getQueryExectionId());
                            executionQueryResults.add(jsonObject);
                        }
                    }
                    response.setContentType(Constants.CONTENT_TYPE_TEXT);
                    
                    JSONObject resultObject = null;
                    resultObject = new JSONObject();
                    resultObject.put("executionQueryResults", executionQueryResults);
                    resultObject.put("projectId", request.getParameter(Constants.SELECTED_PROJECT));
                    writer.write(new JSONObject().put("result", resultObject).toString());
                    
//                    writer.write(new JSONObject().put("executionQueryResults",
//                            executionQueryResults).toString());

                }
            }
	        catch (Exception e) {
	        	logger.debug(e.getMessage(),e);
				try {
						response.setContentType("text/xml");
						writer.write(Constants.QUERY_EXCEPTION);
					}
				catch (IOException e1) {
					logger.debug(e1.getMessage(),e1);
				}
			}
	        finally
	        {
	            if (dao != null)
                {
                    dao.closeSession();
                }
	        }



		}



		return null;
	}
}