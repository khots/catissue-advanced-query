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
import edu.wustl.query.util.querysuite.CiderQueryUIManager;

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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Writer writer = response.getWriter();
		int queryExecID = 0;
		if((Integer.valueOf(request.getParameter("executionId")))==-1)	//If its the first request
		{
			queryExecID =(Integer)request.getSession().getAttribute("query_exec_id");
			request.getSession().removeAttribute("query_exec_id");
		}
		else
		{
			queryExecID=Integer.valueOf(request.getParameter("executionId"));
		}

		//retrieve count with query execution id
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
		Count countObject =((CiderQueryUIManager)qUIManager).getCount(queryExecID);

		//create json object by count object adding Query status, count and execution id
		JSONObject resultObject = createResultJSON(countObject);
		response.setContentType("text/xml");
		writer.write(new JSONObject().put("resultObject", resultObject).toString());

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
			resultObject.append("queryCount", countObject.getCount());
			resultObject.append("status", countObject.getStatus());
			resultObject.append("executionId", countObject.getQuery_exection_id());
		}
		catch (JSONException e)
		{
			Logger.out.info("error in initializing json object " + e);
		}
		return resultObject;
	}
}