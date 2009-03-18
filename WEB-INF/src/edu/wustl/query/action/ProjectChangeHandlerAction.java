package edu.wustl.query.action;

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

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

public class ProjectChangeHandlerAction extends Action {

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http. HttpServletResponse)
	 */
	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();
		Writer writer = response.getWriter();
		
		  String workflowId=request.getParameter("workflowId");
		  String projectId =request.getParameter(Constants.SELECTED_PROJECT);
		  WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();
		  Long userId=((SessionDataBean) request.getSession().getAttribute(Constants.SESSION_DATA)).getUserId();
		  Map<Long, Integer> map=workflowBizLogic.generateQueryExecIdMap(userId,
				  Long.valueOf(workflowId),  Long.valueOf(projectId));
          AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
          .getDefaultAbstractUIQueryManager();
          executionQueryResults=generateExecutionQueryResults(map,
		             workflowBizLogic,  qUIManager,
		            Integer.valueOf(projectId),userId);

          response.setContentType(Constants.CONTENT_TYPE_TEXT);
          writer.write(new JSONObject().put("executionQueryResults",
                  executionQueryResults).toString());
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
    
    /**
     * Private method used to generate the List of JSON objects.
     *
     * @param executionIdMap
     *            Execution Id Map
     * @param workflowBizLogic
     *            Instance of BizLogic to be used.
     * @param qUIManager
     *            Instance of the Query UI Manager.
     * @param projectId
     *            Project Id
     * @return The List of JSON Objects
     * @throws QueryModuleException
     *             if error while executing the query.
     */
	private List<JSONObject> generateExecutionQueryResults(Map<Long, Integer> executionIdMap,
            WorkflowBizLogic workflowBizLogic, AbstractQueryUIManager qUIManager,
            int projectId,Long userId) throws QueryModuleException
    {
        Count resultCount = null;
        JSONObject jsonObject = null;
        List<JSONObject> executionQueryResults = new ArrayList<JSONObject>();

	    Set<Long> titleset = executionIdMap.keySet();
        Iterator<Long> iterator = titleset.iterator();
        while (iterator.hasNext())
        {
            Long query = iterator.next();

            resultCount = workflowBizLogic
                    .getCount(executionIdMap.get(query));
//            executionQueryResults.add(createResultJSON(query,
//                    resultCount.getCount(), resultCount
//                            .getStatus(), resultCount
//                            .getQueryExectionId()));
            boolean hasFewRecords = false;
            if (projectId > 0)
            {
                hasFewRecords = qUIManager.checkTooFewRecords(
                        Long.valueOf(projectId), resultCount,userId);
            }
            if (hasFewRecords)
            {
                jsonObject = createResultJSON(query, 0,
                        resultCount.getStatus(), resultCount
                                .getQueryExectionId());
            } else
            {
                jsonObject = createResultJSON(query,
                        resultCount.getCount(), resultCount
                                .getStatus(), resultCount
                                .getQueryExectionId());
            }
            executionQueryResults.add(jsonObject);
        }
        return executionQueryResults;
	}

}
