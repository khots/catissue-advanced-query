
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.dao.AbstractDAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

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
	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();
		//for saving workflow when click execute


		// Get the Query information
        Long queryId=Long.valueOf(request.getParameter("queryId"));

		//String queryTitle=request.getParameter("queryTitle");
		String operation = request.getParameter(Constants.OPERATION);
		String state = request.getParameter("state");
		Writer writer = response.getWriter();
		if (operation != null && "execute".equals(operation.trim()))
		{
		    // Fetch the current workflow
	        String workflowId=request.getParameter("workflowId");

	        Workflow workflow = null;
            AbstractDAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
            dao.openSession(null);
	        try
	        {
	            // Get the workflow
	            workflow = (Workflow) dao.retrieve(Workflow.class.getName(), Long.valueOf(workflowId));



	        //get the current selected projectId
	        int project_id = 0;
	        if (request.getParameter(Constants.SELECTED_PROJECT) != null
	                && !(request.getParameter(Constants.SELECTED_PROJECT))
	                        .equals(""))
	        {
	            project_id = (Integer.valueOf((request
	                    .getParameter(Constants.SELECTED_PROJECT).toString())));
	        }

	        // Get the current User Id
	        SessionDataBean sessionData = (SessionDataBean) request.getSession().getAttribute(Constants.SESSION_DATA);
	        Long userId = sessionData.getUserId();

            // Create a workflow details object
            // FIXME - CiderWorkFlowdetails cannot be accessed from here. Need a
            // good enough way to create the workflow details object specific to
            // Cider and AdvancedQuery.
            CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
                    project_id, userId.intValue(), workflow);


			 workflowBizLogic = (WorkflowBizLogic) BizLogicFactory.getInstance()
					.getBizLogic(Constants.WORKFLOW_BIZLOGIC_ID);

			int queryExecId = Integer.valueOf(request.getParameter("executionLogId"));
			//TO DO
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

					if(request.getParameter("removeExecutedCount").equals("true"))
					{
						resultObject.put("removeExecutedCount", "removeExecutedCount");
					}
					AbstractQueryManager qManager = AbstractQueryManagerFactory.getDefaultAbstractQueryManager();
					qManager.cancel(queryExecId);
					//Count resultCount=workflowBizLogic.getCount(queryExecId);

					response.setContentType(Constants.CONTENT_TYPE_TEXT);
					writer.write(resultObject
							.toString());
				}
				catch (Exception e) {

				        resultObject.append("execption","execption");

			        	//resultObject.append("executionLogId", queryExecId);

				        response.setContentType(Constants.CONTENT_TYPE_TEXT);
					writer.write(resultObject
							.toString());
					
				}
			
			
			}
			else
			{
				Count resultCount= null;
				JSONObject jsonObject = null;
				AbstractQueryUIManager qUIManager	= AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
				if (queryExecId == 0)
				{
					//queryExecId = workflowBizLogic.executeGetCountQuery(queryId, request);
				    Map<Long, Integer> executionIdMap = workflowBizLogic
	                        .executeGetCountQuery(workflowdetails, queryId, request);
				    Set<Long> titleset=executionIdMap.keySet();
				    Iterator<Long>  iterator=titleset.iterator();
					while(iterator.hasNext())
					{
						Long query=iterator.next();

					     resultCount=workflowBizLogic.getCount(executionIdMap.get(query));
	                    executionQueryResults.add(createResultJSON(query, resultCount.getCount(),
	                            resultCount.getStatus(), resultCount.getQuery_exection_id()));
	                    boolean hasFewRecords = false;
	                    if(project_id > 0)
	                    {
	                    	hasFewRecords = qUIManager.checkTooFewRecords
	                    	(Long.valueOf(project_id), resultCount);
	                    }
					    if(hasFewRecords)
					    {
					    	jsonObject = createResultJSON(query,0,
		                            resultCount.getStatus(), resultCount.getQuery_exection_id());
					    }
					    else
					    {	
					    	jsonObject = createResultJSON(query, resultCount.getCount(),
		                            resultCount.getStatus(), resultCount.getQuery_exection_id());
					    }
					    executionQueryResults.add(jsonObject);
					}
				}
				else
				{
					 resultCount=workflowBizLogic.getCount(queryExecId);
						executionQueryResults.add(createResultJSON(queryId, resultCount.getCount(),
								resultCount.getStatus(), resultCount.getQuery_exection_id()));
						boolean hasFewRecords = false;
	                    if(project_id > 0)
	                    {
	                    	hasFewRecords = qUIManager.checkTooFewRecords
		                    (Long.valueOf(project_id), resultCount);
	                    }
						if(hasFewRecords)
					    {
							jsonObject = createResultJSON(queryId,0,
									resultCount.getStatus(), resultCount.getQuery_exection_id());
						}
						else
						{
							jsonObject = createResultJSON(queryId, resultCount.getCount(),
									resultCount.getStatus(), resultCount.getQuery_exection_id());
						}
						executionQueryResults.add(jsonObject);
				}
				response.setContentType(Constants.CONTENT_TYPE_TEXT);
				writer.write(new JSONObject().put("executionQueryResults", executionQueryResults)
						.toString());
			}
	        }
	        catch (Exception e) {
	        	Logger.out.debug(e.getMessage(),e);
				try {
						response.setContentType("text/xml");
						writer.write(Constants.QUERY_EXCEPTION);
					} 
				catch (IOException e1) {
					Logger.out.debug(e1.getMessage(),e1);
				}
			}
	        finally
	        {
	            dao.closeSession();
	        }



		}
	        
	    

		return null;
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
    private JSONObject createResultJSON(Long queryId, int resultCount,
            String status, int executionLogId)
    {
        JSONObject resultObject = null;
        resultObject = new JSONObject();
        try
        {
            resultObject.append("queryId", queryId);
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
