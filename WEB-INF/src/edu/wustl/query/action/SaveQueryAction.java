/**
 * 
 */

package edu.wustl.query.action;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import edu.wustl.cab2b.common.queryengine.ICab2bQuery;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.actionforms.SaveQueryForm;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.DefinedQueryUtil;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import gov.nih.nci.security.exceptions.CSException;

/**
 * This class saves the Query in Dag into database.
 * 
 * @author chetan_patil
 * @created Sep 11, 2007, 3:50:16 PM
 */
public class SaveQueryAction extends AbstractQueryBaseAction
{
	private static org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(SaveQueryAction.class);
	@Override
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		String target = edu.wustl.common.util.global.Constants.FAILURE;
		if (query == null)
		{
			// Handle null query 
			final String errorMsg = ApplicationProperties.getValue("query.noLimit.error");
			setActionError(request, errorMsg);
		}
		else
		{
			/**
			 * Name: Abhishek Mehta
			 * Reviewer Name : Deepti 
			 * Bug ID: 5661
			 * Patch ID: 5661_3
			 * See also: 1-4 
			 * Description : Calling bizlogic insert and update
			 */

			IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query,
					actionForm, request);
			if (parameterizedQuery != null)
			{
				target = saveQuery(request, parameterizedQuery, actionForm);
				String isworkflow = request.getParameter(Constants.IS_WORKFLOW);
				if(Constants.TRUE.equals(isworkflow))
				{
					target = Constants.PAGE_OF_WORKFLOW;
				}
				else
				{
					target = Constants.SHOW_DASHBOARD;
				}
			}
		}
		
		return actionMapping.findForward(target);
	}
 
	/**
	 * 
	 * @param request
	 * @param parameterizedQuery
	 * @param actionForm
	 * @return
	 * @throws CSException
	 */
	private String saveQuery(HttpServletRequest request, IParameterizedQuery parameterizedQuery,
			ActionForm actionForm) throws CSException
	{
		String target = edu.wustl.common.util.global.Constants.FAILURE;
		try
		{
			IFactory factory = AbstractFactoryConfig.getInstance ().getBizLogicFactory ();
			edu.wustl.query.bizlogic.QueryBizLogic bizLogic=(edu.wustl.query.bizlogic.QueryBizLogic)factory.getBizLogic (Constants.ADVANCE_QUERY_INTERFACE_ID);
			SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
					edu.wustl.common.util.global.Constants.SESSION_DATA);
			boolean isShared = ((SaveQueryForm) actionForm).isShareQuery();

			/*IParameterizedQuery queryClone = new DyExtnObjectCloner().clone(parameterizedQuery);
			new HibernateCleanser(queryClone).clean();*/
			if (parameterizedQuery.getId() == null)
			{
				logger.info("In saveQueryAction.java , for fresh Query before insertSavedQueries");
				bizLogic.insertSavedQueries(parameterizedQuery, sessionDataBean,
						((SaveQueryForm) actionForm).isShareQuery());
			}
			else
			{
				DefinedQueryUtil queryUtil=new DefinedQueryUtil();
				logger.info("In saveQueryAction.java , For Edit Query case  before updateQuery");
				queryUtil.updateQuery(parameterizedQuery,sessionDataBean,isShared);
			}
			// save query to workflow if it is a workflow query
			String isworkflow = request.getParameter(Constants.IS_WORKFLOW);
			if (isworkflow != null && isworkflow.equals("true"))
			{
				String workflowId = (String) request.getSession().getAttribute(
						Constants.EXECUTED_FOR_WFID);
				WorkflowBizLogic wfbzlogic = new WorkflowBizLogic();
				wfbzlogic.addWorkflowItem((long) Integer.valueOf(workflowId), parameterizedQuery);
			}

			target = Constants.SUCCESS;
			setActionErrors(request);
			request.setAttribute(Constants.QUERY_SAVED, Constants.TRUE);
			request.getSession().setAttribute(Constants.QUERY_OBJECT, parameterizedQuery);
		}
		catch (BizLogicException bizLogicException)
		{
			setActionError(request, bizLogicException.getMessage());
			logger.info("In saveQueryAction , Exception Occured while saving query");
			logger.error(bizLogicException.getMessage(), bizLogicException);
		}
	
		return target;
	}

	

	private void setActionErrors(HttpServletRequest request)
	{
		ActionMessage message=   new ActionMessage("query.saved.success");
		ActionMessages messages=new ActionMessages();
		messages.add(ActionErrors.GLOBAL_MESSAGE,message);
	//	errors.add(ActionErrors.GLOBAL_ERROR, error);
	//	saveErrors(request, errors);
		saveMessages(request, messages);
	}

	

	/**
	 * This method sets the error action 
	 * @param request
	 * @param errorMessage
	 */
	private void setActionError(HttpServletRequest request, String errorMessage)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}

	/**
	 * This method populates the Parameterized Query related data form the
	 * ActionForm and returns the new ParameterizedQuery object
	 * 
	 * @param query
	 * @param actionForm
	 * @return
	 */
	private IParameterizedQuery populateParameterizedQueryData(IQuery query, ActionForm actionForm,
			HttpServletRequest request)
	{
		SaveQueryForm saveActionForm = (SaveQueryForm) actionForm;

		/**
		 * Name: Abhishek Mehta
		 * Reviewer Name : Deepti 
		 * Bug ID: 5661
		 * Patch ID: 5661_4
		 * See also: 1-4 
		 * Description : Creating IParameterizedQuery's new instance only if it new query else type casting IQuery to IParameterizedQuery. 
		 */

		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) query;
		if(parameterizedQuery instanceof ICab2bQuery)
		{
		 parameterizedQuery = QueryObjectFactory.createParameterizedQuery(query);
		}
		if(query.getId()!=null)
		{
			parameterizedQuery.setId(query.getId());
		}

		HttpSession session = request.getSession();
		setQueryDescriptionAndTitle(saveActionForm, parameterizedQuery);
		boolean isError = isError(request, saveActionForm, parameterizedQuery, session);

		if (isError)
		{
			parameterizedQuery = null;
		}
		return parameterizedQuery;
	}

	private boolean isError(HttpServletRequest request, SaveQueryForm saveActionForm,
			IParameterizedQuery parameterizedQuery, HttpSession session)
	{
		boolean isError = false;
		CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
		String conditionList = request.getParameter(Constants.CONDITIONLIST);
		String cfRHSList = request.getParameter(QueryModuleConstants.STR_TO_FORM_TQ);
		Map<Integer, ICustomFormula> customFormulaIndexMap = (Map<Integer, ICustomFormula>) session
				.getAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP);
		session.removeAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP);
		Map<String, String> displayNameMap = getDisplayNamesForConditions(saveActionForm, request);
		String error = "";
		if(conditionList!=null)
		{
			error = bizLogic.setInputDataToQuery(conditionList, parameterizedQuery.getConstraints(),
				displayNameMap, parameterizedQuery);
		}
		StringBuffer tmpError = new StringBuffer(error.trim());
		if(cfRHSList!=null && cfRHSList.length()>0)
		{
			error = bizLogic.setInputDataToTQ(parameterizedQuery, Constants.SAVE_QUERY_PAGE, cfRHSList,
				customFormulaIndexMap);
		}
		tmpError.append(error);
		if (tmpError.length() > 0)
		{
			setActionError(request, error);
			isError = true;
		}
		return isError;
	}

	
	/**
	 * This method sets title and description of the query
	 * @param saveActionForm
	 * @param parameterizedQuery
	 */
	private void setQueryDescriptionAndTitle(SaveQueryForm saveActionForm,
			IParameterizedQuery parameterizedQuery)
	{
		String queryTitle = saveActionForm.getTitle();
		if (queryTitle != null)
		{
			parameterizedQuery.setName(queryTitle);
		}

		String queryDescription = saveActionForm.getDescription();
		if (queryDescription == null)
		{
			parameterizedQuery.setDescription("");

		}
		else
		{
			parameterizedQuery.setDescription(queryDescription);
		}
	}

	/**
	 * This method returns the map<expressionid+attributeId,displayname> containing the displaynames entered by user for 
	 * parameterized conditions 
	 * @param saveActionForm
	 * @param request
	 * @return
	 */

	private Map<String, String> getDisplayNamesForConditions(SaveQueryForm saveActionForm,
			HttpServletRequest request)
	{
		Map<String, String> displayNameMap = new HashMap<String, String>();

		String queryString = saveActionForm.getQueryString();
		if (queryString != null)
		{
			StringTokenizer strtokenizer = new StringTokenizer(queryString, ";");
			while (strtokenizer.hasMoreTokens())
			{
				String token = strtokenizer.nextToken();
				String displayName = request.getParameter(token
						+ Constants.DISPLAY_NAME_FOR_CONDITION);
				displayNameMap.put(token, displayName);
			}
		}
		return displayNameMap;
	}

}
