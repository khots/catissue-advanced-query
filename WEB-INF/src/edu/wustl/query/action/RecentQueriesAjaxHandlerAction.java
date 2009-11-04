
package edu.wustl.query.action;

import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author chitra_garg
 *handles ajax call for recent queries
 */
public class RecentQueriesAjaxHandlerAction extends AbstractQueryBaseAction
{

	private static org.apache.log4j.Logger logger = Logger
			.getLogger(RecentQueriesAjaxHandlerAction.class);

	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Writer writer = response.getWriter();
		Long queryExecutionId = Long.valueOf(request.getParameter(Constants.EXECUTION_LOG_ID));
		boolean hasPrivilege = Boolean.valueOf(request.getParameter(Constants.QUERY_PRIVILEGE));
		String cancelExecution = request.getParameter(Constants.CANCEL_EXECUTION);
		QueryPrivilege privilege = new QueryPrivilege();
		privilege.setSecurePrivilege(hasPrivilege);
		String index = request.getParameter(Constants.INDEX);
		if (cancelExecution != null && !cancelExecution.equals(""))
		{
			cancelExecution(queryExecutionId);
		}
		Count countObject = populateCountObject(queryExecutionId, privilege);
		//create json object by count object adding Query status, count and execution id
		JSONObject resultObject = createResultJSON(countObject, index, hasPrivilege);
		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		writer.write(new JSONObject().put(Constants.RESULT_OBJECT, resultObject).toString());

		return null;

	}

	/**
	 * cancel Query execution
	 * @param queryExecutionId query id to cancel execution
	 * @throws SQLException SQLException.
	 */
	private void cancelExecution(Long queryExecutionId) throws SQLException
	{
		AbstractQueryManager qManager = AbstractQueryManagerFactory
				.getDefaultAbstractQueryManager();
		qManager.cancel(queryExecutionId);

	}

	/**
	 *
	 * @param privilege  QueryPrivilege
	 * @param queryExecutionId query execution id
	 * @return count object for given execution id
	 * @throws QueryModuleException QueryModuleException
	 */
	private Count populateCountObject(Long queryExecutionId, QueryPrivilege privilege)
			throws QueryModuleException
	{
		//retrieve count with query execution id
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		Count countObject = qUIManager.getCount(queryExecutionId, privilege);
		try
		{
			Thread.sleep(Variables.ajaxCallSleepTime);
		}
		catch (InterruptedException ie)
		{
			logger.debug(ie.getMessage(), ie);
		}
		return countObject;
	}

	/**
	 * Creates a json object and appends query count, status, execution id to it.
	 * @param countObject Count
	 * @param index row on UI
	 * @param  hasPrivilege privilege
	 * @return JSONObject.
	 */
	private JSONObject createResultJSON(Count countObject, String index, boolean hasPrivilege)
	{
		JSONObject resultObject = new JSONObject();
		try
		{
			resultObject.append(Constants.QUERY_COUNT, countObject.getCount());
			resultObject.append(Constants.GET_COUNT_STATUS, countObject.getStatus());
			resultObject.append(Constants.EXECUTION_ID, countObject.getQueryExectionId());
			resultObject.append(Constants.INDEX, index);
			resultObject.append(Constants.QUERY_PRIVILEGE, hasPrivilege);
		}
		catch (JSONException e)
		{
			logger.error("error in initializing json object " + e);
		}
		return resultObject;
	}

}
