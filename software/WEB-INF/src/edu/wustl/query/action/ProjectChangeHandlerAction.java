
package edu.wustl.query.action;

import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

/**
 * @author chitra_garg
 * reset UI on project change
 *
 */
public class ProjectChangeHandlerAction extends AbstractQueryBaseAction
{
	 /**
     * This action is used for processing Work flow object
     *
     * @param mapping
     *            ActionMapping.
     * @param form
     *            ActionForm.
     * @param request
     *            HttpServletRequest.
     * @param response
     *            HttpServletResponse.
     * @return ActionForward ActionFowrward.
     * @throws Exception
     *             Exception.
     *
     * @see org.apache.struts.action.Action#execute(org.apache.s
     *      truts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<JSONObject> executionQueryResults; //= new ArrayList<JSONObject>();
		Writer writer = response.getWriter();
		String workflowId = request.getParameter("workflowId");
		String projectId = request.getParameter(Constants.SELECTED_PROJECT);
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		Long userId = ((SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA)).getUserId();
		Map<Long, Long> map = workflowBizLogic.generateQueryExecIdMap(userId, Long
				.valueOf(workflowId), Long.valueOf(projectId));
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		QueryPrivilege privilege = qUIManager.getPrivilege(request);
		executionQueryResults = Utility.generateExecutionQueryResults(map, workflowBizLogic,
				qUIManager, privilege);

		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		writer.write(new JSONObject().put("executionQueryResults", executionQueryResults)
				.toString());
		return null;

	}
}
