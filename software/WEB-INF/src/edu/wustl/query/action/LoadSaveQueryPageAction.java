
package edu.wustl.query.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.hibernate.HibernateCleanser;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.actionforms.SaveQueryForm;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import edu.wustl.query.util.querysuite.QueryModuleException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

public class LoadSaveQueryPageAction extends AbstractQueryBaseAction
{

	@Override
	/**
	 * This action loads all the conditions from the query.
	 */
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String isworkflow = (String) request.getAttribute(Constants.IS_WORKFLOW);
		if (isworkflow == null)
		{
			isworkflow = request.getParameter(Constants.IS_WORKFLOW);
		}

		String pageOf = (String) request.getAttribute(Constants.PAGE_OF);
		if (pageOf == null)
		{

			pageOf = request.getParameter(Constants.PAGE_OF);
		}
		request.setAttribute(Constants.PAGE_OF, pageOf);
		if (Constants.TRUE.equals(isworkflow))
		{
			request.setAttribute(Constants.IS_WORKFLOW, Constants.TRUE);
			String workflowName = (String) request.getSession().getAttribute(
					Constants.WORKFLOW_NAME);
			request.setAttribute(Constants.WORKFLOW_NAME, workflowName);
		}

		IQuery queryObject = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		String target = edu.wustl.common.util.global.Constants.FAILURE;
		if ("true".equals(request.getParameter(Constants.IS_SAVE_AS)))
		{
			request.setAttribute(Constants.QUERY_ID, queryObject.getId().toString());
			queryObject = new DyExtnObjectCloner().clone(queryObject);
			new HibernateCleanser(queryObject).clean(true);
			queryObject.setName("");
			session.setAttribute(Constants.QUERY_OBJECT, queryObject);
		}
		if (queryObject != null)
		{
			boolean isShowAll = request.getParameter(Constants.SHOW_ALL) == null ? false : true;
			target = loadPage(form, request, queryObject, isShowAll);
			setQueryOutputAttributeList(queryObject,session);
			if (queryObject.getConstraints() == null || queryObject.getConstraints().size() == 0)
			{
				// Handle null query

				target = Constants.SUCCESS;
				String errorMsg = ApplicationProperties.getValue("query.noLimit.error");
				ActionErrors errors = Utility.setActionError(errorMsg, "errors.item");
				saveErrors(request, errors);
				request.setAttribute(Constants.IS_QUERY_SAVED, Constants.IS_QUERY_SAVED);
			}
		}

		

		checkIsQueryAlreadyShared(queryObject, request);
		setErrorMessage(request);

		return mapping.findForward(target);
	}


	/**
	 * This Method sets the  selected column to the output attribute list of the Query.
	 * @param parameterizedQuery
	 * @param session
	 */
	private void setQueryOutputAttributeList(IQuery parameterizedQuery,HttpSession session)
	{
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session
				.getAttribute(Constants.SELECTED_COLUMN_META_DATA);
		if (selectedColumnsMetadata != null)
		{
			List<IOutputAttribute> selectedOutputAttributeList = selectedColumnsMetadata.getSelectedOutputAttributeList();
			Utility.setQueryOutputAttributeList((IParameterizedQuery)parameterizedQuery, 
					selectedOutputAttributeList);
		}

	}

	/**
	 * It will check weather the Query is shared or not & set the shared_queries flag in the request
	 * accordingly.
	 * @param queryObject query which is to be checked.
	 * @param request
	 * @throws CSException
	 * @throws CSObjectNotFoundException
	 * @throws QueryModuleException 
	 */
	private void checkIsQueryAlreadyShared(IQuery queryObject, HttpServletRequest request)
			throws CSObjectNotFoundException, CSException, QueryModuleException
	{
		try
		{
			//			QueryBizLogic queryBizLogic = (QueryBizLogic)AbstractBizLogicFactory.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"),
			//				"getBizLogic", Constants.ADVANCE_QUERY_INTERFACE_ID);
			//			for cp
			IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
			QueryBizLogic queryBizLogic = (QueryBizLogic) factory
					.getBizLogic(Constants.ADVANCE_QUERY_INTERFACE_ID);
			boolean isShared = queryBizLogic.isSharedQuery((IParameterizedQuery) queryObject);
			request.setAttribute(Constants.SAHRED_QUERIES, isShared);
		}
		catch (BizLogicException e)
		{
			ActionErrors errors = Utility.setActionError(e.getMessage(), "errors.item");
			saveErrors(request, errors);
		}
	}

	/**
	 * This Method generates Html for Save query page
	 * @param form
	 * @param request
	 * @param queryObject
	 * @param isShowAll
	 * @return
	 * @throws PVManagerException
	 */
	private String loadPage(ActionForm form, HttpServletRequest request, IQuery queryObject,
			boolean isShowAll) throws PVManagerException
	{
		String target;
		Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);
		String htmlContents = new SavedQueryHtmlProvider().getHTMLForSavedQuery(queryObject,
				isShowAll, Constants.SAVE_QUERY_PAGE, customFormulaIndexMap, generateHTMLDetails,
				false);
		request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE,
				generateHTMLDetails.getEnumratedAttributeMap());
		request.getSession().setAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP,
				customFormulaIndexMap);
		request.setAttribute(Constants.HTML_CONTENTS, htmlContents);
		String showAllLink = isShowAll
				? Constants.SHOW_SELECTED_ATTRIBUTE
				: Constants.SHOW_ALL_ATTRIBUTE;
		request.setAttribute(Constants.SHOW_ALL_LINK, showAllLink);
		if (!isShowAll)
		{
			request.setAttribute(Constants.SHOW_ALL, Constants.TRUE);
		}
		target = Constants.SUCCESS;
		SaveQueryForm savedQueryForm = (SaveQueryForm) form;
		request.setAttribute(Constants.QUERY_NAME, ((ParameterizedQuery) queryObject).getName());
		if (queryObject.getId() != null && queryObject instanceof ParameterizedQuery)
		{
			request.setAttribute(Constants.QUERY_ID, queryObject.getId().toString());
			savedQueryForm.setDescription(((ParameterizedQuery) queryObject).getDescription());
			savedQueryForm.setTitle(((ParameterizedQuery) queryObject).getName());
		}
		//the title from GetCount query page should be displayed as default in Save Query page
		if (queryObject.getId() == null && queryObject instanceof ParameterizedQuery)
		{
			savedQueryForm.setTitle(((ParameterizedQuery) queryObject).getName());
		}
		return target;
	}

	private void setErrorMessage(HttpServletRequest request)
	{
		String errorMessage = (String) request.getSession()
				.getAttribute("errorMessageForEditQuery");
		if (errorMessage != null)
		{
			ActionErrors errors = Utility.setActionError(errorMessage, "errors.item");
			saveErrors(request, errors);
			request.getSession().removeAttribute("errorMessageForEditQuery");
		}
	}

}
