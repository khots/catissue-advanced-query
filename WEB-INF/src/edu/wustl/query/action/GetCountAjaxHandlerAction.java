
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This is a action class to handle the Azax call for Get Count from GetCountPopUp.jsp (called at onLoad of jsp)
 */
public class GetCountAjaxHandlerAction extends AbstractQueryBaseAction
{

	/**
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		Long queryExecID = 0L;
		try
		{
			boolean abortExecution = Boolean.valueOf(request
					.getParameter(Constants.ABORT_EXECUTION));
			Writer writer = response.getWriter();
			if ((Integer.valueOf(request.getParameter(Constants.EXECUTION_ID))) == -1) //If its the first request
			{
				Object queryId_obj = request.getSession().getAttribute(Constants.QUERY_EXEC_ID);
				if (queryId_obj == null)
				{
					writer.write(Constants.WAIT);
					return null;
				}
				queryExecID = (Long) queryId_obj;
				request.getSession().removeAttribute(Constants.QUERY_EXEC_ID);
			}
			else
			{
				queryExecID = Long.valueOf(request.getParameter(Constants.EXECUTION_ID));
			}
			//If Abort Execution is not clicked, then only keep on getting the count, else call the cancel()
			if (!abortExecution)
			{
				JSONObject resultObject = getCountResponse(request, queryExecID);
				response.setContentType("text/xml");
				writer
						.write(new JSONObject().put(Constants.RESULT_OBJECT, resultObject)
								.toString());
			}
			else
			{
				//call cancel() of ciderquerymanager stopping the Query Execution 
				AbstractQueryManager qManager = AbstractQueryManagerFactory
						.getDefaultAbstractQueryManager();
				qManager.cancel(queryExecID);
			}
			try
			{
				Thread.sleep(Variables.ajaxCallSleepTime);
			}
			catch (InterruptedException ie)
			{
				Logger.out.debug(ie.getMessage(), ie);
			}

		}
		//if some exception occurs anywhere while handling the Ajax call (doing getCount, aborting the execution), 
		//a Constants.QUERY_EXCEPTION string will be sent in response to indicate the query-execution-failure.
		catch (Exception e)
		{
			Logger.out.debug(e.getMessage(), e);
			try
			{
				Writer writer = response.getWriter();
				response.setContentType("text/xml");
				writer.write(Constants.QUERY_EXCEPTION);
			}
			catch (IOException e1)
			{
				Logger.out.debug(e1.getMessage(), e1);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param queryExecID
	 * @return
	 * @throws QueryModuleException
	 * @throws JSONException
	 * @throws DAOException 
	 */
	private JSONObject getCountResponse(HttpServletRequest request, Long queryExecID)
			throws QueryModuleException, JSONException, DAOException
	{
		Long queryExID = queryExecID;
		HttpSession session = request.getSession();
		String queryTitle = null;
		QueryPrivilege privilege = new QueryPrivilege();
		boolean isNewQuery = Boolean.valueOf(request.getParameter(Constants.IS_NEW_QUERY));
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		if (isNewQuery) //if a new getcount query is fired from the pop-up
		{
			//retrieve the Selected Project from the GetCountPopUp.jsp
			String selectedProject_value = request.getParameter(Constants.SELECTED_PROJECT);
			if (selectedProject_value != null)
			{
				session.setAttribute(Constants.SELECTED_PROJECT, selectedProject_value);
			}
			qUIManager.getPrivilege(request);
			IQuery query = (IQuery) request.getSession().getAttribute(DAGConstant.QUERY_OBJECT);
			queryTitle = query.getName();
		
			IParameterizedQuery queryClone = (IParameterizedQuery) new DyExtnObjectCloner()
					.clone(query);
			//Utility.removeEmptyCoditionsFromQuery((IParameterizedQuery)queryClone);
			qUIManager = AbstractQueryUIManagerFactory.configureDefaultAbstractUIQueryManager(this
					.getClass(), request, queryClone);
			queryExID = qUIManager.searchQuery();
			if(request.getParameter(Constants.EXECUTE_QUERY)!=null&&
					!request.getParameter(Constants.EXECUTE_QUERY).equals(""))
			{
				qUIManager.insertParametersForExecution(queryExID, query);
			}
			query.setId(queryClone.getId());
		}

		if (request.getSession().getAttribute(Constants.QUERY_PRIVILEGE) != null)
		{
			privilege = (QueryPrivilege) (session.getAttribute(Constants.QUERY_PRIVILEGE));
		}
		//retrieve count with query execution id
		Count countObject = qUIManager.getCount(queryExID, privilege);
		qUIManager.auditTooFewRecords(countObject, privilege);
		JSONObject resultObject = null;
		resultObject = createResultJSON(countObject, countObject.getCount());
		if (isNewQuery)
		{
			resultObject.append(Constants.QUERY_TITLE, queryTitle);
		}
		return resultObject;
	}

	/**
	 * Creates a json object and appends query count, status, execution id to it.
	 * @param countObject
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private JSONObject createResultJSON(Count countObject, Long count)
	{
		JSONObject resultObject = new JSONObject();
		try
		{
			resultObject.append(Constants.QUERY_COUNT, count);
			resultObject.append(Constants.GET_COUNT_STATUS, countObject.getStatus());
			resultObject.append(Constants.EXECUTION_ID, countObject.getQueryExectionId());
		}
		catch (JSONException e)
		{
			Logger.out.debug("error in initializing json object " + e);
		}
		return resultObject;
	}
}
