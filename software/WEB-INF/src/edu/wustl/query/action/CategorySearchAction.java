
package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.wustl.cab2b.client.metadatasearch.MetadataSearch;
import edu.wustl.cab2b.common.beans.MatchedClass;
import edu.wustl.cab2b.common.exception.CheckedException;
import edu.wustl.cab2b.common.util.Constants;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.category.CategoryProcessorUtility;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleActionUtil;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class loads screen for categorySearch.
 * When search button is clicked it checks for the input : Text , checkbox ,
 * radiobutton etc. And depending upon the selections made by user,
 * the list of entities is populated. This list is kept in session.
 * @author deepti_shelar
 */

public class CategorySearchAction extends AbstractQueryBaseAction
{

	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(CategorySearchAction.class);

	/**
	 * This method loads the data required for categorySearch.jsp.
	 * @param mapping mapping
	 * @param form form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//getQueryrTitle

		setIsQueryAttribute(request);
		boolean isSharingStatusReq = false;
		boolean isCategoryQuery = false;
		CategorySearchForm searchForm = (CategorySearchForm) form;
		HttpSession session = request.getSession();
		String currentPage = getCurrentPage(request, searchForm);

		String forward = edu.wustl.query.util.global.Constants.SUCCESS;
		String workflow = request.getParameter(edu.wustl.query.util.global.Constants.IS_WORKFLOW);
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) request.getSession()
				.getAttribute(edu.wustl.query.util.global.Constants.QUERY_OBJECT);

		if (edu.wustl.query.util.global.Constants.TRUE.equals(workflow))
		{
			request.setAttribute(edu.wustl.query.util.global.Constants.IS_WORKFLOW,
					edu.wustl.query.util.global.Constants.TRUE);
			String workflowName = (String) request.getSession().getAttribute(
					edu.wustl.query.util.global.Constants.WORKFLOW_NAME);
			request.setAttribute(edu.wustl.query.util.global.Constants.WORKFLOW_NAME, workflowName);
		}

		if (currentPage != null
				&& (currentPage.equalsIgnoreCase(edu.wustl.query.util.global.Constants.COUNT_QUERY) || currentPage
						.equalsIgnoreCase(edu.wustl.query.util.global.Constants.DATA_QUERY)))
		{
			String queryTitle = request.getParameter("queryname");
			searchForm.setQueryTitle(queryTitle);
			isCategoryQuery = CategoryProcessorUtility.isCategoryQuery(parameterizedQuery);
			searchForm = QueryModuleActionUtil.setDefaultSelections(searchForm);
			forward = currentPage;

		}
		else
		{
			String textfieldValue = searchForm.getTextField();

			if (currentPage != null && currentPage.equalsIgnoreCase("prevToAddLimits"))
			{
				setOutputAttributeList(searchForm, session);
				searchForm = QueryModuleActionUtil.setDefaultSelections(searchForm);
				isCategoryQuery = CategoryProcessorUtility.isCategoryQuery(parameterizedQuery);
				textfieldValue = "";
			}
			else if (currentPage != null
					&& currentPage
							.equalsIgnoreCase(edu.wustl.query.util.global.Constants.EDIT_QUERY))
			{
				searchForm = QueryModuleActionUtil.setDefaultSelections(searchForm);
				textfieldValue = "";
				request.setAttribute("isQuery", "true");
				isCategoryQuery = CategoryProcessorUtility.isCategoryQuery(parameterizedQuery);
				searchForm.setQueryTitle(parameterizedQuery.getName());
				logger.info("In categorySearchAction  : opening the query in edit mode QueryId :"
						+ parameterizedQuery.getId());
				isSharingStatusReq = true;
			}

			forward = search(request, response, searchForm, textfieldValue);
		} //end of else
		// for save as
		if (isSharingStatusReq
				|| edu.wustl.query.util.global.Constants.TRUE.equals(request
						.getParameter(edu.wustl.query.util.global.Constants.IS_SAVE_AS)))
		{
			setSharingStatus(request);
		}
		request.setAttribute(edu.wustl.query.util.global.Constants.IS_Category, isCategoryQuery);
		//Retrieve the Project list
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		List<NameValueBean> projectList = qUIManager.getObjects(edu.wustl.query.util.global.Utility
				.getSessionData(request).getUserId());

		if (projectList != null)
		{
			searchForm.setProjectsNameValueBeanList(projectList);
		}
		return mapping.findForward(forward);
	}

	/**Method to get sharing status of query and to set it in request.
	 * @param request object of HttpServletRequest
	 * @throws QueryModuleException query module exception
	 */
	private void setSharingStatus(HttpServletRequest request) throws QueryModuleException
	{
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			Long queryId = Long.parseLong((String) request
					.getParameter(edu.wustl.query.util.global.Constants.QUERY_ID));
			IParameterizedQuery parameterizedQuery = (IParameterizedQuery) request.getSession()
					.getAttribute(edu.wustl.query.util.global.Constants.QUERY_OBJECT);
			if (parameterizedQuery.getId() == null && queryId != null)
			{
				parameterizedQuery = (ParameterizedQuery) queryBizLogic.retrieve(
						ParameterizedQuery.class.getName(), queryId);
				request.getSession().setAttribute(
						edu.wustl.query.util.global.Constants.QUERY_OBJECT, parameterizedQuery);
				CategorySearchForm categorySearchForm = (CategorySearchForm) request
						.getAttribute(edu.wustl.query.util.global.Constants.categorySearchForm);
				if (categorySearchForm != null)
				{
					categorySearchForm.setQueryTitle(parameterizedQuery.getName());
				}
			}
			QuerySharingStatus sharingStatus = queryBizLogic.getSharingStatus(parameterizedQuery,
					edu.wustl.query.util.global.Utility.getSessionData(request).getUserId());
			request.setAttribute(edu.wustl.query.util.global.Constants.SHARING_STATUS,
					sharingStatus.name());
		}
		catch (NumberFormatException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.GENERIC_EXCEPTION);
		}
		catch (BizLogicException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.DAO_EXCEPTION);
		}
	}

	/**
	 * This method sets output attributes in query.
	 * @param searchForm CategorySearchForm
	 * @param session HttpSession
	 */
	private void setOutputAttributeList(CategorySearchForm searchForm, HttpSession session)
	{
		List<IOutputAttribute> selectedOutputAttributeList = new ArrayList<IOutputAttribute>();
		QueryDetails queryDetails = new QueryDetails(session);
		IParameterizedQuery query = (IParameterizedQuery) queryDetails.getQuery();
		if (searchForm.getSelectedColumnNames() != null)
		{
			SelectedColumnsMetadata selectedColumnsMetadata = new SelectedColumnsMetadata();
			DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
			defineGridViewBizLogic.getSelectedColumnsMetadata(searchForm.getSelectedColumnNames(),
					queryDetails, selectedColumnsMetadata);
			selectedOutputAttributeList = selectedColumnsMetadata.getSelectedOutputAttributeList();
		}
		edu.wustl.query.util.global.Utility.setQueryOutputAttributeList(query,
				selectedOutputAttributeList);
	}

	/**
	 * It will retrieve the current page attribute from the searchForm, if it is null will
	 * retrieve the same attribute from request and return that String.
	 * @param request HttpServletRequest
	 * @param searchForm CategorySearchForm
	 * @return currentPage
	 */
	private String getCurrentPage(HttpServletRequest request, CategorySearchForm searchForm)
	{
		String currentPage = searchForm.getCurrentPage();
		if (currentPage == null)
		{
			currentPage = (String) request
					.getAttribute(edu.wustl.query.util.global.Constants.CURRENT_PAGE);

		}
		return currentPage;
	}

	/**
	 * This Method searches categories if text field value is nonempty.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param searchForm CategorySearchForm
	 * @param textfieldValue String
	 * @return String
	 * @throws CheckedException in case exception occurred in search
	 * @throws IOException in case of writing response
	 */
	private String search(HttpServletRequest request, HttpServletResponse response,
			CategorySearchForm searchForm, String textfieldValue) throws CheckedException,
			IOException
	{
		String forward = edu.wustl.query.util.global.Constants.SUCCESS;
		if (textfieldValue != null && !textfieldValue.equals(""))
		{
			String entitiesString = searchCategory(request, searchForm, textfieldValue);
			response.setContentType("text/html");
			response.getWriter().write(entitiesString);
			forward = "";
		}
		return forward;
	}

	/**
	 * This Method searches all the categories related to textfeild value and checkbox checked.
	 * @param request HttpServletRequest
	 * @param searchForm CategorySearchForm
	 * @param textfieldValue String
	 * @return String
	 * @throws CheckedException in case exception occurred in search
	 */
	private String searchCategory(HttpServletRequest request, CategorySearchForm searchForm,
			String textfieldValue) throws CheckedException
	{
		final int[] searchTarget = prepareSearchTarget(searchForm);

		/*
		 * Bug #5131 : Disabling function call and supplying value directly
		 * until the Concept-Code search is fixed
		 */

		int basedOn = Constants.BASED_ON_TEXT;
		String[] searchString = null;
		searchString = prepareSearchString(textfieldValue);
		String attributeChecked = searchForm.getAttributeChecked();
		String permissibleValuesChecked = searchForm.getPermissibleValuesChecked();
		EntityCache cache = EntityCacheFactory.getInstance();
		MetadataSearch advancedSearch = new MetadataSearch(cache);
		MatchedClass matchedClass = advancedSearch.search(searchTarget, searchString, basedOn);
		Set<EntityInterface> entityCollection = matchedClass.getEntityCollection();
		List<EntityInterface> resultList = new ArrayList<EntityInterface>(entityCollection);
		removeEntitiesNotSearchable(resultList);
		StringBuffer entitiesString = prepareEntitiesString(resultList);

		if ("".equals(entitiesString.toString()))
		{
			entitiesString = new StringBuffer(ApplicationProperties
					.getValue("query.noResultFoundMessage"));
		}
		else
		{
			String KeySeparator = edu.wustl.query.util.global.Constants.KEY_CODE;
			String key = request.getParameter(KeySeparator);
			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.KEY_SEPARATOR);
			entitiesString = entitiesString.append(key);
			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.KEY_SEPARATOR);
			entitiesString = entitiesString.append(textfieldValue);
			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.KEY_SEPARATOR);
			entitiesString = entitiesString.append(attributeChecked);

			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.KEY_SEPARATOR);
			entitiesString = entitiesString.append(permissibleValuesChecked);
		}
		return entitiesString.toString();
	}

	/**
	 * Modifies list without entities that are not searchable.
	 * @param resultList list of matching entities
	 */
	private void removeEntitiesNotSearchable(List<EntityInterface> resultList)
	{
		List<EntityInterface> notSearchableList = new ArrayList<EntityInterface>();
		for (EntityInterface entity : resultList)
		{
			Collection<TaggedValueInterface> taggedValueCollection = entity
					.getTaggedValueCollection();
			for (TaggedValueInterface tagValue : taggedValueCollection)
			{
				if (tagValue.getKey().equals(
						edu.wustl.query.util.global.Constants.TAGGED_VALUE_NOT_SEARCHABLE))
				{
					notSearchableList.add(entity);
				}
			}
		}
		resultList.removeAll(notSearchableList);
	}

	/**
	 * This Method prepares a String of all resulted entities with adding seprator between each Entity.
	 * This Method also adds separator between name, id and description of each entity.
	 * @param resultList list of entity interface
	 * @return entitiesString
	 */
	private StringBuffer prepareEntitiesString(List<EntityInterface> resultList)
	{
		StringBuffer entitiesString = new StringBuffer("");
		for (int i = 0; i < resultList.size(); i++)
		{
			EntityInterface entity = (EntityInterface) resultList.get(i);
			String fullyQualifiedEntityName = entity.getName();
			StringBuffer entityName = new StringBuffer(Utility
					.getDisplayLabel(fullyQualifiedEntityName));
			String entityId = entity.getId().toString();
			String description = entity.getDescription();
			if (description == null || description.equals(""))
			{
				description = edu.wustl.query.util.global.Constants.NO_DESCRIPTION;
			}

			entitiesString = entitiesString.append(QueryModuleConstants.ENTITY_SEPARATOR);
			entitiesString = entitiesString.append(entityName);
			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.ATTRIBUTE_SEPARATOR);
			entitiesString = entitiesString.append(entityId);
			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.ATTRIBUTE_SEPARATOR);
			entitiesString = entitiesString.append(description);
		}
		return entitiesString;
	}

	/**
	 * This Method sets isQueryAttribute if isQuery Parameter is present.
	 * @param request HttpServletRequest
	 */
	private void setIsQueryAttribute(HttpServletRequest request)
	{

		request.getSession().removeAttribute(
				edu.wustl.query.util.global.Constants.SELECTED_COLUMN_META_DATA);
		String isQuery = request.getParameter("isQuery");
		if (isQuery == null)
		{
			isQuery = "false";

		}
		request.setAttribute("isQuery", isQuery);
	}

	/**
	 * Prepares a String to be sent to AdvancedSearch logic.
	 * @param textfieldValue String
	 * @return String[] array of strings , taken from user.
	 */
	private String[] prepareSearchString(String textfieldValue)
	{
		int counter = 0;
		StringTokenizer tokenizer = new StringTokenizer(textfieldValue);
		String[] searchString = new String[tokenizer.countTokens()];
		while (tokenizer.hasMoreTokens())
		{
			searchString[counter++] = tokenizer.nextToken();
		}
		return searchString;
	}

	/**
	 * Prepares the int [] for search targets from the checkbox values selected by user.
	 * @param searchForm action form
	 * @return int[] Integer array of selections made by user.
	 */
	private int[] prepareSearchTarget(CategorySearchForm searchForm)
	{
		String classCheckBoxChecked = searchForm.getClassChecked();
		String attributeCheckBoxChecked = searchForm.getAttributeChecked();
		String pvCheckBoxChecked = searchForm.getPermissibleValuesChecked();
		String descriptionChecked = searchForm.getIncludeDescriptionChecked();
		List<Integer> target = new ArrayList<Integer>();

		boolean checked;
		checked = isChecked(classCheckBoxChecked);
		if (checked)
		{
			includeDescription(descriptionChecked, Constants.CLASS_WITH_DESCRIPTION,
					Constants.CLASS, target);
		}

		checked = isChecked(attributeCheckBoxChecked);

		if (checked)
		{
			includeDescription(descriptionChecked, Constants.ATTRIBUTE_WITH_DESCRIPTION,
					Constants.ATTRIBUTE, target);
		}

		checked = isChecked(pvCheckBoxChecked);
		if (checked)
		{
			target.add(Integer.valueOf((edu.wustl.query.util.global.Constants.PV)));
		}

		int size = target.size();
		int[] searchTarget = new int[size];
		for (int i = 0; i < size; i++)
		{
			searchTarget[i] = ((target.get(i))).intValue();
		}
		return searchTarget;
	}

	/**
	 * This Method checks wheather Advanced Option checkbox is checked.
	 * @param option String
	 * @return boolean
	 */
	private boolean isChecked(String option)
	{

		boolean bool = false;
		if (option != null
				&& (option.equalsIgnoreCase(edu.wustl.query.util.global.Constants.ON) || option
						.equalsIgnoreCase(edu.wustl.query.util.global.Constants.TRUE)))
		{
			bool = true;
		}
		return bool;
	}

	/**
	 * This Method includes class and/os atrribute with description to serchTarget
	 * if include description checkbox is checked.
	 * @param includeDescriptionChecked String
	 * @param withDesc int
	 * @param str int
	 * @param target list of integers
	 */
	private void includeDescription(String includeDescriptionChecked, int withDesc, int str,
			List<Integer> target)
	{
		Integer temp = Integer.valueOf(str);
		boolean cheked = isChecked(includeDescriptionChecked);
		if (cheked)
		{
			temp = Integer.valueOf(withDesc);
		}
		target.add(temp);
	}

}
