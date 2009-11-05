
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

public class DeleteQueryAction extends AbstractQueryBaseAction
{

	/**
	 * Action Handler for Deletes Save query from database 
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = edu.wustl.common.util.global.Constants.FAILURE;

		String queryIdStr = request.getParameter(Constants.QUERY_ID);
		HttpSession session = request.getSession();
		String queryDeletedInLastRequest = (String) session
				.getAttribute(Constants.QUERY_ALREADY_DELETED);
		Long queryId = Long.parseLong(queryIdStr);
		if (queryId != null && !queryIdStr.equalsIgnoreCase(queryDeletedInLastRequest))
		{

			session.setAttribute(Constants.QUERY_ALREADY_DELETED, queryIdStr);
			IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
			IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);
			//				IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
			//						.getValue("app.bizLogicFactory"), "getBizLogic",
			//						Constants.QUERY_INTERFACE_BIZLOGIC_ID);
			//DAO hibernateDao=null;
			try
			{
				Object object = bizLogic.retrieve(ParameterizedQuery.class.getName(), queryId);
				if (object == null)
				{
					session.removeAttribute(Constants.QUERY_ALREADY_DELETED);

				}
				else
				{
					IParameterizedQuery parameterizedQuery = (ParameterizedQuery) object;
					bizLogic.delete(parameterizedQuery);
					target = Constants.SUCCESS;
					ActionErrors errors = Utility.setActionError(ApplicationProperties
							.getValue("query.deletedSuccessfully.message"), "errors.item");
					saveErrors(request, errors);
					session.setAttribute(Constants.QUERY_ALREADY_DELETED, queryIdStr);
				}
			}
			catch (BizLogicException bizException)
			{

				ActionErrors errors = Utility.setActionError(bizException.getMessage(),
						"errors.item");
				saveErrors(request, errors);

			}

		}
		return actionMapping.findForward(target);
	}

	/**
	 * Set Error Action
	 * @param request
	 * @param errorMessage
	 */

}
