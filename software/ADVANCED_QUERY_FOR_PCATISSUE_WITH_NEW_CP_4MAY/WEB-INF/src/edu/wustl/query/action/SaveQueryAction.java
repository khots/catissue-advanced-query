/**
 *
 */

package edu.wustl.query.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import edu.wustl.common.action.BaseAction;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.hibernate.HibernateCleanser;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.beans.SharedQueryBean;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.bizlogic.SaveQueryBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryModuleSqlUtil;
import gov.nih.nci.security.exceptions.CSException;

/**
 * This class saves the Query in DAG into database.
 *
 * @author chetan_patil
 */
public class SaveQueryAction extends BaseAction
{

	/**
	 * This method saves/edits the query.
	 * @param actionMapping Object of ActionMapping
	 * @param actionForm Object of ActionForm
	 * @param request Object of HttpServletRequest
	 * @param response Object of HttpServletResponse
	 * @throws Exception Object of Exception
	 * @return Object of ActionForward
	 */
	@Override
	protected ActionForward executeAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		IQuery query = (IQuery) session.getAttribute(AQConstants.QUERY_OBJECT);
		String target = edu.wustl.common.util.global.Constants.FAILURE;
		if (query == null)
		{
			final String errorMsg = ApplicationProperties.getValue("query.noLimit.error");
			setActionError(request, errorMsg);
		}
		else
		{
			IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query,
					actionForm, request);
			if (parameterizedQuery != null)
			{
				target = saveQuery(request, parameterizedQuery, actionForm);
			}
		}
		return actionMapping.findForward(target);
	}

	/**
	 * This method saves/edits the query.
	 * @param request Object of HttpServletRequest
	 * @param parameterizedQuery Object of IParameterizedQuery
	 * @param actionForm Object of ActionForm
	 * @return target The target
	 * @throws CSException Common Security Exception
	 * @throws DAOException DAO Exception
	 * @throws SQLException SQLException
	 */
	private String saveQuery(HttpServletRequest request, IParameterizedQuery parameterizedQuery,
			ActionForm actionForm) throws CSException, DAOException, SQLException
	{
		String target = edu.wustl.common.util.global.Constants.FAILURE;
		try
		{
			SaveQueryBizLogic bizLogic = new SaveQueryBizLogic();
			HttpSession session = request.getSession();
			SessionDataBean sessionDataBean = (SessionDataBean) session.getAttribute(
					edu.wustl.common.util.global.Constants.SESSION_DATA);
			SaveQueryForm form = (SaveQueryForm)actionForm;
			SharedQueryBean sharedQueryBean = populateBean(form);
			boolean isEditQuery = form.isEditQuery();
			Long queryId = parameterizedQuery.getId();
			if (isEditQuery)
			{
				bizLogic.updateQuery(parameterizedQuery, sessionDataBean,sharedQueryBean);
			}
			else
			{
				IParameterizedQuery queryClone =
					new DyExtnObjectCloner().clone(parameterizedQuery);
				new HibernateCleanser(queryClone).clean();
				bizLogic.saveQuery(queryClone, sessionDataBean,sharedQueryBean);
				queryId = queryClone.getId();
			}
			long auditEventId = (Long)session.getAttribute(AQConstants.AUDIT_EVENT_ID);
			QueryModuleSqlUtil.updateAuditQueryDetails
			(AQConstants.QUERY_ID, queryId.toString(), auditEventId);
			target = AQConstants.SUCCESS;
			String errorMessage = ApplicationProperties.getValue("query.saved.success") + " "+
			parameterizedQuery.getName();
			setActionError(request,errorMessage);
			request.setAttribute(AQConstants.QUERY_SAVED, "true");
			session.setAttribute(AQConstants.QUERY_OBJECT, parameterizedQuery);
		}
		catch (BizLogicException bizLogicException)
		{
			handleErrors(request, bizLogicException);
		}
		return target;
	}

	/**
	 * @param request request
	 * @param bizLogicException Exception
	 */
	private void handleErrors(HttpServletRequest request,
			BizLogicException bizLogicException)
	{
		Throwable cause = bizLogicException.getWrapException().getCause();
		if(cause.getCause() == null)
		{
			final String errorMsg = "Error occurred during saving this query.";
			setActionError(request, errorMsg);
		}
		else
		{
			String localizedMessage = cause.getCause().getLocalizedMessage();
			if(localizedMessage != null)
			{
				if(localizedMessage.indexOf("cannot insert NULL into") == -1)
				{
					final String errorMsg = "Please specify a different name" +
					" for the query as the query with this name already exists";
					setActionError(request, errorMsg);
				}
				else
				{
					final String errorMsg = "Please specify a name for the query";
					setActionError(request, errorMsg);
				}
			}
		}
	}

	/**
	 * This method is used to populate the SharedQueryBean.
	 * @param form Object of SaveQueryForm
	 * @return bean Object of SharedQueryBean
	 */
	private SharedQueryBean populateBean(SaveQueryForm form)
	{
		SharedQueryBean bean = new SharedQueryBean();
		bean.setSelectedRoles(form.getSelectedRoles());
		bean.setProtocolCoordinatorIds(form.getProtocolCoordinatorIds());
		bean.setShareTo(form.getShareTo());
		return bean;
	}

	/**
	 * This method sets the error action.
	 * @param request Object of HttpServletRequest
	 * @param errorMessage The error message to set
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
	 * ActionForm and returns the new ParameterizedQuery object.
	 * @param query IQuery object
	 * @param actionForm Object of ActionForm
	 * @param request HttpServletRequest object
	 * @return parameterizedQuery The populated parameterized query
	 */
	private IParameterizedQuery populateParameterizedQueryData(IQuery query,
			ActionForm actionForm,HttpServletRequest request)
	{
		SaveQueryForm saveActionForm = (SaveQueryForm) actionForm;
		String error = "";
		IParameterizedQuery pQuery = (IParameterizedQuery) query;
		if (query.getId() == null)
		{
			pQuery = QueryObjectFactory.createParameterizedQuery(query);
		}
		HttpSession session = request.getSession();
		String queryTitle = saveActionForm.getTitle();
		if (queryTitle != null)
		{
			pQuery.setName(queryTitle);
		}
		String queryDescription = saveActionForm.getDescription();
		if (queryDescription == null)
		{
			pQuery.setDescription("");
		}
		else
		{
			pQuery.setDescription(queryDescription);
		}
		CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
		String conditionList = request.getParameter(AQConstants.CONDITIONLIST);
		String cfRHSList = request.getParameter(AQConstants.STR_TO_FORM_TQ);
		Map<Integer, ICustomFormula> customFormulaIndexMap = (Map<Integer, ICustomFormula>) session
				.getAttribute(AQConstants.CUSTOM_FORMULA_INDEX_MAP);
		session.removeAttribute(AQConstants.CUSTOM_FORMULA_INDEX_MAP);
		Map<String, String> displayNameMap = getDisplayNamesForConditions(saveActionForm, request);
		error = bizLogic.setInputDataToQuery(conditionList, pQuery.getConstraints(),
				displayNameMap, pQuery);
		error = bizLogic.setInputDataToTQ(pQuery, AQConstants.SAVE_QUERY_PAGE, cfRHSList,
				customFormulaIndexMap);
		if (error != null && error.trim().length() > 0)
		{
			setActionError(request, error);
			return null;
		}
		List<IOutputAttribute> selectedOutputAttributeList = saveView(session);
		pQuery.getOutputTerms();
		pQuery.setOutputAttributeList(selectedOutputAttributeList);
		return pQuery;
	}

	/**
	 * @param session session
	 * @return selectedOutputAttributeList
	 */
	private List<IOutputAttribute> saveView(HttpSession session)
	{
		// Saving view
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session
				.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		List<IOutputAttribute> selectedOutputAttributeList = new ArrayList<IOutputAttribute>();
		if (selectedColumnsMetadata != null)
		{
			selectedOutputAttributeList = selectedColumnsMetadata.getSelectedOutputAttributeList();
		}
		return selectedOutputAttributeList;
	}

	/**
	 * This method returns the map<expressionid+attributeId,display name>
	 * containing the display names entered by user for
	 * parameterized conditions.
	 * @param saveActionForm Object of SaveQueryForm
	 * @param request Object of HttpServletRequest
	 * @return displayNameMap
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
						+ AQConstants.DISPLAY_NAME_FOR_CONDITION);
				displayNameMap.put(token, displayName);
			}
		}
		return displayNameMap;
	}
}
