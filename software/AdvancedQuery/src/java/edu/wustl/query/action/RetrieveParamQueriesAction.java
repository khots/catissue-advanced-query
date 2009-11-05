
package edu.wustl.query.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.DAOUtil;

/**
 * @author chitra_garg
 *
 */
public class RetrieveParamQueriesAction extends AbstractQueryBaseAction
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(RetrieveParamQueriesAction.class);

	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		// session.getAttribute("queriesIdString");
		// session.getAttribute(Constants.HTML_CONTENTS);
		String queryIdStr = request.getParameter(Constants.QUERY_ID);
		Long queryId = getQueryIdFromRequest(queryIdStr);
		Long project_id = getProjectIDFromRequest(request);
		Set<Long> notExecutedLeafNode; //=new HashSet<Long>();
		String workflowId = request.getParameter(Constants.WORKFLOW_ID);

		session.removeAttribute(Constants.HTML_CONTENTS);
		session.removeAttribute(Constants.QUERY_ID_STRING);
		session.removeAttribute(Constants.QUERY_ID_MAP);
		session.removeAttribute(Constants.PQ_ID_SET);

		HibernateDAO dao = null;

		try
		{
			dao = DAOUtil.getHibernateDAO(null);
			Workflow workflow = (Workflow) dao.retrieveById(Workflow.class.getName(), Long
					.valueOf(workflowId));
			CiderQueryPrivilege privilege = Utility.getPrivilegeFromSession(request);
			Long projectIdVal = null;
			if (project_id > 0)
			{
				projectIdVal = Long.valueOf(project_id);
			}

			SessionDataBean sessionData = (SessionDataBean) request.getSession().getAttribute(
					edu.wustl.common.util.global.Constants.SESSION_DATA);
			Long userId = sessionData.getUserId();

			CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(project_id, userId,
					workflow, privilege);

			String execType = request.getParameter(Constants.REQ_ATTRIB_EXECUTION_TYPE);
			if (execType != null&& Constants.EXECUTION_TYPE_WORKFLOW.
					equalsIgnoreCase(execType.trim()))// execute workflow
			{
				notExecutedLeafNode = workflowdetails.getDependencyGraph()
						.getQueryIdsOFAllLeafNodes();

			}
			else
			{
				edu.wustl.common.querysuite.queryobject.impl.AbstractQuery query =
				(edu.wustl.common.querysuite.queryobject.impl.AbstractQuery) dao
				.retrieveById(edu.wustl.common.querysuite.queryobject.impl.AbstractQuery.class
										.getName(), Long.valueOf(queryId));

				AbstractQuery ciderQuery = new CiderQuery(query, 0L, "", userId,
						projectIdVal,request.getRemoteAddr(),
						Long.valueOf(workflowId), privilege);
				Set<Long> leafNodes = new HashSet<Long>();
				notExecutedLeafNode = workflowdetails.getDependencyGraph()
						.getAllNotExecutedLeafNodes(ciderQuery, leafNodes);

			}
			generateResponse(response, session, queryIdStr, notExecutedLeafNode);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw e;
		}
		finally
		{
			if (dao != null)
			{
				DAOUtil.closeHibernateDAO(dao);
			}
		}

		return null;
	}

	/**Method to generate response.
	 * @param response HttpServletResponse object
	 * @param session HttpSession object
	 * @param queryIdStr query id string
	 * @param notExecutedLNode set of not executed node
	 * @throws JSONException JSON exception
	 * @throws IOException IO exception
	 */
	private void generateResponse(HttpServletResponse response, HttpSession session,
			String queryIdStr, Set<Long> notExecutedLNode) throws JSONException, IOException
	{
		if (notExecutedLNode.isEmpty())
		{
			// if not executed leaf nodes are are not  present then set flag
			// not  show popup . when not show popup is set query is
			//directly   send for execution.
			//This is basically to distinguish CQ Containing all executed PQs
			//So we have no need to open popup
			//And CQ Execution call will be sent through UI ...
			//on receiving this response

			createRespone(response, Constants.NOT_SHOW_POP_UP, queryIdStr);
		}
		else
		{
			// if not executed leaf nodes are are present then set the
			// leafNodes in session and also set flag show popup
			//

			session.setAttribute(Constants.PQ_ID_SET, notExecutedLNode);
			createRespone(response, Constants.SHOW_POP_UP, queryIdStr);
		}
	}

	/**Method to get query id.
	 * @param queryIdStr string value
	 * @return Long query id
	 */
	private Long getQueryIdFromRequest(String queryIdStr)
	{
		Long query = 0l;
		if (queryIdStr != null && !"".equals(queryIdStr.trim()))
		{
			query = Long.valueOf(queryIdStr);
		}
		return query;
	}

	/**Method to get project id from request.
	 * @param request HttpServletRequest object
	 * @return Long project id
	 */
	private Long getProjectIDFromRequest(HttpServletRequest request)
	{
		Long project_id = 0l;
		if (request.getParameter(Constants.SELECTED_PROJECT) != null
				&& !(request.getParameter(Constants.SELECTED_PROJECT)).equals(""))
		{
			project_id = (Long.valueOf((request.getParameter(Constants.SELECTED_PROJECT))));
		}
		return project_id;
	}

	/**
	 * Creates JSON response.
	 * @param response HttpServletResponse object
	 * @param showPopup String value
	 * @param queryId query id
	 * @throws JSONException JSON Exception
	 * @throws IOException IO Exception
	 */
	private void createRespone(HttpServletResponse response, String showPopup, String queryId)
			throws JSONException, IOException
	{
		response.setContentType("text/xml");
		JSONObject resultObject = new JSONObject();
		resultObject.put(Constants.SHOW_POP_UP, showPopup);
		resultObject.put(Constants.QUERY_ID, queryId);
		Writer writer = response.getWriter();
		writer.write(resultObject.toString());
	}

}
