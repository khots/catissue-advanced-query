/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.util.global.AQConstants;

/**
 * Action Handler for Deleting Saved query from database
 * @author deepti_shelar
 */
public class DeleteQueryAction extends Action
{
	/**
	 * This method deletes the query from database.
	 * @param actionMapping mapping
	 * @param actionForm form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = AQConstants.FAILURE;

		String queryIdStr = request.getParameter(AQConstants.QUERYS_ID);
		HttpSession session = request.getSession();
		String queryDelInLastReq = (String) session
				.getAttribute(AQConstants.QUERY_ALREADY_DELETED);
		Long queryId = Long.parseLong(queryIdStr);
		if (queryId != null && !queryIdStr.equalsIgnoreCase(queryDelInLastReq))
		{
			session.setAttribute(AQConstants.QUERY_ALREADY_DELETED, queryIdStr);
			IBizLogic bizLogic = BizLogicFactory.getInstance().getBizLogic
			(AQConstants.QUERY_INTERFACE_BIZLOGIC_ID);
			try
			{
				Object object = bizLogic.retrieve(ParameterizedQuery.class.getName(), queryId);
				if (object == null)
				{
					session.removeAttribute(AQConstants.QUERY_ALREADY_DELETED);
				}
				else
				{
					target = deleteQuery(request, object);
					session.setAttribute(AQConstants.QUERY_ALREADY_DELETED, queryIdStr);
				}
			}
			catch (BizLogicException daoException)
			{
				setActionError(request, daoException.getMessage());
			}
		}
		return actionMapping.findForward(target);
	}
	/**
	 * Deletes the query
	 * @param request request object
	 * @param queryIdStr query id
	 * @param object query object
	 * @return target to be forwarded
	 * @throws BizLogicException exception object
	 */
	private String deleteQuery(HttpServletRequest request,
			 Object object)
			throws BizLogicException {
		IBizLogic bizLogic = BizLogicFactory.getInstance().getBizLogic
		(AQConstants.QUERY_INTERFACE_BIZLOGIC_ID);
		IParameterizedQuery query = (ParameterizedQuery) object;
		String queryName = query.getName();
		bizLogic.delete(query, AQConstants.HIBERNATE_DAO);
		String target = AQConstants.SUCCESS;
		String message = queryName;
		setActionError(request,message);
		return target;
	}

	/**
	 * Set Error Action
	 * @param request
	 * @param errorMessage
	 */
	private void setActionError(HttpServletRequest request, String errorMessage)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("query.deletedSuccessfully.message", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}
}
