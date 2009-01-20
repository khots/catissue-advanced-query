package edu.wustl.query.action;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.cider.util.CiderQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;


public class RecentQueriesAjaxHandlerAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Writer writer = response.getWriter();
		int queryExecutionId=Integer.valueOf(request.getParameter("executionLogId"));
		String index =request.getParameter("index");

		Count countObject = populateCountObject(queryExecutionId);

		//create json object by count object adding Query status, count and execution id
		JSONObject resultObject = createResultJSON(countObject,index);
		response.setContentType("text/xml");
		writer.write(new JSONObject().put("resultObject", resultObject).toString());
		
		return null;
		
	}

	private Count populateCountObject(int queryExecutionId) throws QueryModuleException
	{
		//retrieve count with query execution id
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
		Count countObject =((CiderQueryUIManager)qUIManager).getCount(queryExecutionId);
		return countObject;
	}
	
	/**
	 * Creates a json object and appends query count, status, execution id to it.
	 * @param countObject
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private JSONObject createResultJSON(Count countObject,String index) 
	{
		JSONObject resultObject = new JSONObject();
		try
		{
			resultObject.append("queryCount",countObject.getCount());
			resultObject.append("status", countObject.getStatus());
			resultObject.append("executionId", countObject.getQuery_exection_id());
			resultObject.append("index", index);
		}
		catch (JSONException e)
		{
			Logger.out.info("error in initializing json object " + e);
		}
		return resultObject;
	}

}
