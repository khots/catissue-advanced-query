
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

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.DefinedQueryUtil;
import edu.wustl.query.util.querysuite.QueryModuleSearchQueryUtil;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * 
 * @author deepti_shelar
 *
 */
public class OpenDecisionMakingPageAction extends Action
{

	/**
	 * This method loads define results jsp.
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
		CategorySearchForm actionForm = (CategorySearchForm) form;
		HttpSession session = request.getSession();
		String pageOf = (String)request.getParameter(Constants.PAGE_OF);
		  request.setAttribute(Constants.PAGE_OF, pageOf);
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		SessionDataBean sessionDataBean = (SessionDataBean) session.getAttribute(Constants.SESSION_DATA);
		String noOfResults = (String) session
				.getAttribute(Constants.TREE_NODE_LIMIT_EXCEEDED_RECORDS);
		String option = actionForm.getOptions();
		String forward=Constants.SUCCESS;
		QueryModuleSearchQueryUtil QMSearchQuery = new QueryModuleSearchQueryUtil(request, query);
		//inserts Defined Query
		DefinedQueryUtil definedQueryUtil=new DefinedQueryUtil();
		ParameterizedQuery parameterizedQuery=(ParameterizedQuery)query;
		parameterizedQuery.setName(actionForm.getQueryTitle());
		definedQueryUtil.insertQuery(parameterizedQuery,sessionDataBean,false);
		if (option == null)
		{
			 ActionErrors errors = new ActionErrors();
			 ActionError addMsg = new ActionError("query.decision.making.message", noOfResults,
					Variables.maximumTreeNodeLimit);
			errors.add(ActionErrors.GLOBAL_ERROR, addMsg);
			saveErrors(request, errors);
			
		}
	   else
	   {
		   final boolean isRulePresentInDag = QueryModuleUtil.checkIfRulePresentInDag(query);
			if (isRulePresentInDag)
			{
				QMSearchQuery.searchQuery(option);
			}
			forward=Constants.VIEW_ALL_RECORDS;
	   }
		return mapping.findForward(forward);
	}

}
