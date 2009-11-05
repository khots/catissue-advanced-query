package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.query.actionforms.WorkflowForm;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author ravindra_jain
 * @created November 26, 2008
 */
public class WorkflowAction extends AbstractQueryBaseAction
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
     * @throws BizLogicException
     *             Business Logic Exception.
     * @throws QueryModuleException
     *             Query Module Exception.
     *
     * @see org.apache.struts.action.Action#execute(org.apache.s
     *      truts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected ActionForward executeBaseAction(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws BizLogicException, QueryModuleException
    {
    	WorkflowForm workflowForm=((WorkflowForm)form);
        HttpSession session = request.getSession();
        setQueryIdsforRow(request, workflowForm);
        // in case of back to workflow the person upi count in session should be
        // removed
        CiderQueryPrivilege privilege = new CiderQueryPrivilege();
        session.removeAttribute(Constants.PERSON_UPI_COUNT);
        if (request.getParameter(Constants.OPERATION) != null
                && (request.getParameter("id") != null || request
                        .getAttribute(Constants.WORKFLOW_ID) != null)
                && (!"".equals(request.getParameter(Constants.ID))))
        {

            request.setAttribute(Constants.OPERATION,
                    edu.wustl.common.util.global.Constants.EDIT);
            String workflowId = request.getParameter("id");
            if (workflowId == null)
            {
                /*
                 * for the get patient data/get Count flow the is no id set in
                 * request.getParameter so retrieve form request.getAttribute
                 */
                workflowId = (String) request
                        .getAttribute(Constants.WORKFLOW_ID);
            }
            SessionDataBean sessionDataBean = (SessionDataBean) request
                    .getSession()
                    .getAttribute(
                            edu.wustl.common.util.global.Constants.SESSION_DATA);
            // added for get Count ->>>latest project Id
            Long latestProjectId;
            if (request.getAttribute(Constants.EXECUTED_FOR_PROJECT) == null)
            {
                latestProjectId = getLatestProjectId(Long.valueOf(workflowId),
                        sessionDataBean.getUserId());
            } else
            {
                latestProjectId = (Long) request
                        .getAttribute(Constants.EXECUTED_FOR_PROJECT);
            }
            if (latestProjectId != null && latestProjectId > 0)
            {
                session.setAttribute(Constants.SELECTED_PROJECT, latestProjectId.toString());
                AbstractQueryUIManager queryUIManager = AbstractQueryUIManagerFactory.getDefaultAbstractUIQueryManager();
                privilege = (CiderQueryPrivilege)queryUIManager.getPrivilege(request);
            }
            setQueryExecutionid(form, sessionDataBean,
                    Long.valueOf(workflowId), latestProjectId);
        }
        else
        {
            request.setAttribute(Constants.OPERATION,
                    edu.wustl.common.util.global.Constants.ADD);
        }
        if (request.getParameter(Constants.ID) != null
                && (!request.getParameter(Constants.ID).equals("")))
        {
            request.setAttribute(Constants.ID, request
                    .getParameter(Constants.ID));
        }
        setProjectList(request);
        if (request.getAttribute(Constants.WORKFLOW_ID) != null)
        {
            request.setAttribute(Constants.WORKFLOW_ID, request
                    .getAttribute(Constants.WORKFLOW_ID));
        }
        replaceSingleQuote(workflowForm);
        session.removeAttribute(Constants.QUERY_PRIVILEGE);
        session.setAttribute(Constants.QUERY_PRIVILEGE, privilege);
        return mapping.findForward(Constants.SUCCESS);
    }

	/**
	 * This method set the query id in form
	 *This is required for composite query.
	 * @param request HttpServletRequest
	 * @param workflowForm WorkflowForm
	 */
	private void setQueryIdsforRow(HttpServletRequest request,
			WorkflowForm workflowForm)
	{
		if(request.getAttribute("queryIdForRow")!=null)
        {
        	String[] queryIdForRow =(String[]) request.getAttribute("queryIdForRow");
        	workflowForm.setQueryIdForRow(queryIdForRow);
        }
	}

	/**
	 * this method replaces "'" with "\'"
	 * having single Quote in title creates problem while
	 * evaluating it on UI.
	 * @param workflowForm WorkflowForm
	 */
	private void replaceSingleQuote(WorkflowForm workflowForm)
	{
		if(workflowForm.getDisplayQueryTitle()!=null)
        {
        	String[] displayQueryTitle=workflowForm.getDisplayQueryTitle();
        	for(int i=0;i<displayQueryTitle.length;i++)
        	{
        		String temp = displayQueryTitle[i].replaceAll("'","\\\\'");
        		displayQueryTitle[i]= temp;
        	}
        }
	}

	/**
     * Get the latest project Id
     *
     * @param projectId
     *            The workflowId
     * @param userId
     *            The userId
     * @return The Latest projectId
     * @throws BizLogicException
     *             Business Logic Exception.
     */
    private Long getLatestProjectId(Long projectId, Long userId)
            throws BizLogicException
    {
        WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
        return workflowBizLogic.getLatestProject(projectId, userId);
    }

	/**
     * Sets the project list in the given request object.
     *
     * @param request
     *            HttpServletRequest object.
     * @throws QueryModuleException
     *             Query Module Exception.
     */
    private void setProjectList(HttpServletRequest request)
            throws QueryModuleException
    {
        // Retrieve the Project list

        SessionDataBean sessionDataBean = (SessionDataBean) request
                .getSession().getAttribute(
                        edu.wustl.common.util.global.Constants.SESSION_DATA);
        AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
                .getDefaultAbstractUIQueryManager();
        List<NameValueBean> projectList = qUIManager.getObjects(sessionDataBean
                .getUserId());

        if (projectList != null)
        {
            request
                    .setAttribute(Constants.PROJECT_NAME_VALUE_BEAN,
                            projectList);
        }
    }

	/**
     * Sets the Query Execution Ids.
     *
     * @param form
     *            Action Form.
     * @param sessionDataBean
     *            Session Data Bean.
     * @param workflowId
     *            Workflow Id.
     * @param projectId
     *            Project Id.
     * @throws BizLogicException
     *             Business Logic Exception.
     */
    private void setQueryExecutionid(ActionForm form,
            SessionDataBean sessionDataBean, Long workflowId, Long projectId)
            throws BizLogicException
    {
        WorkflowForm workflowForm = (WorkflowForm) form;
        WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
        List<Long> queryexecutionId = workflowBizLogic.generateExecutionIdMap(
                workflowId, sessionDataBean.getUserId(), projectId);

        Long[] queryExeId = new Long[queryexecutionId.size()];
        queryexecutionId.toArray(queryExeId);
        workflowForm.setQueryExecId(queryExeId);
        workflowForm.setExecutedForProject(projectId);
    }
}
