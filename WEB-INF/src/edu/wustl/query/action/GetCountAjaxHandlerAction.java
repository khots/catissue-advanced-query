package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

/**
 * This is a action class to handle the Azax call for Get Count from GetCountPopUp.jsp (called at onLoad of jsp)
 */
public class GetCountAjaxHandlerAction extends Action
{

	/**
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try {
			boolean abortExecution	= Boolean.valueOf(request.getParameter(Constants.ABORT_EXECUTION));
			int queryExecID 		= 0;
			if((Integer.valueOf(request.getParameter(Constants.EXECUTION_ID)))==-1)	//If its the first request
			{
				queryExecID =(Integer)request.getSession().getAttribute(Constants.QUERY_EXEC_ID);
				request.getSession().removeAttribute(Constants.QUERY_EXEC_ID);
			}
			else
			{
				queryExecID=Integer.valueOf(request.getParameter(Constants.EXECUTION_ID));
			}
			//If Abort Execution is not clicked, then only keep on getting the count, else call the cancel()
			if(!abortExecution)
			{
				boolean isNewQuery = Boolean.valueOf(request.getParameter(Constants.IS_NEW_QUERY));
				if(isNewQuery)
				{
					//retrieve the Selected Project from the GetCountPopUp.jsp
					String selectedProject = request.getParameter(Constants.SELECTED_PROJECT);
					request.setAttribute(Constants.SELECTED_PROJECT,selectedProject);
					
					IQuery query = (IQuery)request.getSession().getAttribute(DAGConstant.QUERY_OBJECT);
					AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this.getClass(),request,query);
					queryExecID	= qUIManager.searchQuery(null);
				}
				
				Writer writer = response.getWriter();
				//retrieve count with query execution id
				AbstractQueryUIManager qUIManager	= AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
				Count countObject = qUIManager.getCount(queryExecID);

				//create json object by count object adding Query status, count and execution id
				JSONObject resultObject = createResultJSON(countObject);
				response.setContentType("text/xml");
				writer.write(new JSONObject().put(Constants.RESULT_OBJECT, resultObject).toString());
			}
			else
			{
				//call cancel() of ciderquerymanager stopping the Query Execution 
				AbstractQueryManager qManager = AbstractQueryManagerFactory.getDefaultAbstractQueryManager();
				qManager.cancel(queryExecID);
			}
			
		}
		//if some exception occurs anywhere while handling the Ajax call (doing getCount, aborting the execution), 
		//a Constants.QUERY_EXCEPTION string will be sent in response to indicate the query-execution-failure.
		catch (Exception e) 
		{
			try {
				Writer writer = response.getWriter();
				response.setContentType("text/xml");
				writer.write(Constants.QUERY_EXCEPTION);
				} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Creates a json object and appends query count, status, execution id to it.
	 * @param countObject
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private JSONObject createResultJSON(Count countObject) 
	{
		JSONObject resultObject = new JSONObject();
		try
		{
			resultObject.append(Constants.QUERY_COUNT, countObject.getCount());
			resultObject.append(Constants.GET_COUNT_STATUS, countObject.getStatus());
			resultObject.append(Constants.EXECUTION_ID, countObject.getQuery_exection_id());
		}
		catch (JSONException e)
		{
			Logger.out.info("error in initializing json object " + e);
		}
		return resultObject;
	}
}