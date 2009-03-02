/**
 * 
 */

package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * @author chetan_patil
 * @created Sep 14, 2007, 9:53:15 AM
 */
public class FetchAndExecuteQueryAction extends Action
{

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		String target = Constants.FAILURE;
		String actionErrorsKey="errors.item";

		try
		{
			SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
			Long queryId = saveQueryForm.getQueryId();
			IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
					.getValue("app.bizLogicFactory"), "getBizLogic",
					Constants.QUERY_INTERFACE_BIZLOGIC_ID);
			Object object = bizLogic.retrieve(ParameterizedQuery.class.getName(), queryId);

			if (object == null)
			{
				ActionErrors errors = Utility.setActionError(Constants.NO_RESULT_FOUND,actionErrorsKey);
				saveErrors(request, errors);
			}
			else
			{
				target = executeQuery(request, object);
			}
		}
		catch (NumberFormatException numberFormatException)
		{
			ActionErrors errors = Utility.setActionError(Constants.QUERY_IDENTIFIER_NOT_VALID,actionErrorsKey);
			saveErrors(request, errors);
			
		}
		catch (DAOException daoException)
		{
			ActionErrors errors = Utility.setActionError(daoException.getMessage(),actionErrorsKey);
			saveErrors(request, errors);
			
		}

		return actionMapping.findForward(target);
	}

	/**
	 * Method to execute a query
	 * @param request
	 * @param object
	 * @return
	 */
	private String executeQuery(HttpServletRequest request, Object object)
	{
		String target = Constants.FAILURE;
		IParameterizedQuery parameterizedQuery = (ParameterizedQuery) object;
		HttpSession session = request.getSession();
		session.setAttribute(Constants.QUERY_OBJECT, parameterizedQuery);

		String errorMessage = QueryModuleUtil.executeQuery(request, parameterizedQuery);
		if (errorMessage == null)
		{
			target = Constants.SUCCESS;
		}
		else if (errorMessage.equalsIgnoreCase(Constants.TREE_NODE_LIMIT_EXCEEDED_RECORDS))
		{
			target = Constants.TREE_NODE_LIMIT_EXCEEDED_RECORDS;
			
		}
		else
		{
			ActionErrors errors = Utility.setActionError(errorMessage,"errors.item");
			saveErrors(request, errors);
		}
	  return target;
	}

	

}
