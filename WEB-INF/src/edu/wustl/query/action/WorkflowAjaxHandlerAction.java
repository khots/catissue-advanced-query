package edu.wustl.query.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.util.global.Constants;


public class WorkflowAjaxHandlerAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String operation=request.getParameter(Constants.OPERATION);
		String queryIndex=request.getParameter("queryId");
		Writer writer=response.getWriter();
		if(operation!=null && "execute".equals(operation.trim()))
		{
			//Get all query ids
			Long queryId=1l;
			//
			WorkflowBizLogic workflowBizLogic=(WorkflowBizLogic)BizLogicFactory.getInstance().getBizLogic(Constants.WORKFLOW_BIZLOGIC_ID);
			Long resultCount=workflowBizLogic.executeGetCountQuery(queryId);
			
			List<JSONObject> executionQueryResults =new ArrayList<JSONObject>();
			executionQueryResults.add(createResultJSON(queryId,queryIndex,resultCount));
			
			response.setContentType("text/xml");
			writer.write(new JSONObject().put("executionQueryResults", executionQueryResults).toString());
		}
		
		return null;
	}

	private JSONObject createResultJSON(Long queryId,String queryIndex, Long resultCount)
	{
		JSONObject resultObject = null;
		resultObject = new JSONObject();
		try
		{
			resultObject.append("queryId", queryId);
			resultObject.append("queryIndex", queryIndex);
			resultObject.append("queryResult", resultCount.longValue());
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return resultObject;
	}
}
