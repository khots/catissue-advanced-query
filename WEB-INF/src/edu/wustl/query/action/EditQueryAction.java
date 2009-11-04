
package edu.wustl.query.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.query.category.CategoryProcessorUtility;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.actionforms.SaveQueryForm;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.enums.Privilege;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * This action class is invoked when user clicks on the "Edit Query" link from "My Queries". 
 * @author baljeet_dhindhwal
 *
 */

public class EditQueryAction extends AbstractQueryBaseAction
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(ExecuteQueryAction.class);

	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{     
		String target = Constants.FAILURE;
		Object queryString = request.getAttribute(Constants.QUERY_ID);

		if (queryString == null) {
			Long queryId = null;
			SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
			queryId = saveQueryForm.getQueryId();
			boolean hasPrivilege = true;
			logger
					.info("Fetching query in EditQueryAction having identifier as "
							+ queryId);
			if (queryId == null) {
				target = Constants.SUCCESS;
				ActionErrors errors = Utility.setActionError(
						Constants.QUERY_IDENTIFIER_NOT_VALID, "errors.item");
				saveErrors(request, errors);
			} else {
				hasPrivilege = hasPrivilege(request, queryId);
			}
			
			target = getResponce(request, response, queryId,
					hasPrivilege);
		}
		return actionMapping.findForward(target);
	}

	/**
	 * This method will check if user logged in has privilege on the Query edited or not.
	 * @param request
	 * @param queryId
	 * @param hasPrivilege
	 * @return
	 */
	private boolean hasPrivilege(HttpServletRequest request, Long queryId) 
	{
		boolean hasPrivilege = true;
		String objectId  = ParameterizedQuery.class.getName()+"_"+queryId;
		SessionDataBean sessionDataBean = Utility.getSessionData(request);
		Privilege queryPrivilege = Privilege.EXECUTE_MANAGED_QUERY;
		hasPrivilege = Utility.hasPrivilege(sessionDataBean.getUserName(), objectId,queryPrivilege.privilege);
		return hasPrivilege;
	}

	/*
	 * This method will redirect to proper page according to user privileges.
	 */
	private String getResponce(HttpServletRequest request,
			HttpServletResponse response,Long queryId,
			boolean hasPrivilege) throws BizLogicException, PVManagerException,
			IOException {
		String target = Constants.FAILURE;
		if(hasPrivilege)
		{
		   target = fetchQuery(request, queryId);
		}
		else
		{
			response.sendRedirect(Constants.ACCESS_DENIED_URL);
		}
		return target;
	}
	/**
	 * 
	 * @param request
	 * @param queryId
	 * @return
	 * @throws BizLogicExceptiTon
	 * @throws PVManagerException
	 */
	@SuppressWarnings("unchecked")
	private String fetchQuery(HttpServletRequest request, Long queryId) throws BizLogicException,
			PVManagerException
	{
		String target = Constants.FAILURE;
		IBizLogic bizLogic = AbstractFactoryConfig.getInstance().getBizLogicFactory().getBizLogic(
				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		try
		{
			final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
					.getName(), Constants.ID, queryId);
			if (queryList != null && !queryList.isEmpty())
			{
				target = setDataInRequest(request, queryList);
			}
		}
		catch (BizLogicException daoException)
		{
			ActionErrors errors = Utility.setActionError(daoException.getMessage(), "errors.item");
			saveErrors(request, errors);
		}
		catch (QueryModuleException e)
		{
			ActionErrors errors = Utility.setActionError(e.getMessage(), "errors.item");
			saveErrors(request, errors);
		}
		return target;
	}

	private String setDataInRequest(HttpServletRequest request,
			final List<IParameterizedQuery> queryList)
			throws QueryModuleException {
		String target;
		IParameterizedQuery parameterizedQuery = queryList.get(0);
		HttpSession session = request.getSession();
		session.setAttribute(Constants.QUERY_OBJECT, parameterizedQuery);

		ParameterizedQuery query = (ParameterizedQuery) parameterizedQuery;
		if (query.getType().equalsIgnoreCase(Constants.QUERY_TYPE_GET_COUNT))
		{
			target = Constants.EDIT_COUNT_QUERY_TARGET;
			session.setAttribute(Constants.Query_Type, Constants.QUERY_TYPE_GET_COUNT);
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			QuerySharingStatus sharingStatus = queryBizLogic.getSharingStatus(
					parameterizedQuery, Utility.getSessionData(request).getUserId());
			request.setAttribute(Constants.SHARING_STATUS, sharingStatus.name());
		}
		else
		{
			target = Constants.EDIT_DATA_QUERY_TARGET;
			session.setAttribute(Constants.Query_Type, Constants.QUERY_TYPE_GET_DATA);
		}
		boolean isCategoryQuery = CategoryProcessorUtility.isCategoryQuery(parameterizedQuery);
		request.setAttribute(Constants.IS_Category, isCategoryQuery);
		request.setAttribute(Constants.IS_EDITED_QUERY, true);
		request.setAttribute(Constants.CURRENT_PAGE, "editQuery");
		return target;
	}

}
