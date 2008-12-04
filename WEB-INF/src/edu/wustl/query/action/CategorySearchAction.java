
package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
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
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * This class loads screen for categorySearch.
 * When search button is clicked it checks for the input : Text , checkbox , radiobutton etc. And depending upon the selections made by user,
 * the list of entities is populated. This list is kept in session.
 * @author deepti_shelar
 */

public class CategorySearchAction extends Action
{

	/**
	 * This method loads the data required for categorySearch.jsp
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
		setIsQueryAttribute(request);
		CategorySearchForm searchForm = (CategorySearchForm) form;
		String currentPage = searchForm.getCurrentPage();
		String forward = edu.wustl.query.util.global.Constants.SUCCESS;
		if (currentPage != null && currentPage.equalsIgnoreCase("resultsView"))
		{
			searchForm = QueryModuleUtil.setDefaultSelections(searchForm);
		}
		else
		{
			String textfieldValue = searchForm.getTextField();
			if (currentPage != null && currentPage.equalsIgnoreCase("prevToAddLimits"))
			{
				textfieldValue = "";
			}
			forward = search(request, response, searchForm, textfieldValue);
		} //end of else  
		return mapping.findForward(forward);
	}

	/**
	 * This Method serches categories if textfeild value is nonempty 
	 * @param request
	 * @param response
	 * @param searchForm
	 * @param textfieldValue
	 * @return 
	 * @throws CheckedException
	 * @throws IOException
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
	 * This Method serches all the categories related to textfeild value and checkbox checked
	 * @param request
	 * @param searchForm
	 * @param textfieldValue
	 * @return
	 * @throws CheckedException
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
		Set<EntityInterface> entityCollection = new HashSet<EntityInterface>();
		String[] searchString = null;
		searchString = prepareSearchString(textfieldValue);
		String attributeChecked = searchForm.getAttributeChecked();
		String permissibleValuesChecked = searchForm.getPermissibleValuesChecked();
		EntityCache cache = EntityCacheFactory.getInstance();
		MetadataSearch advancedSearch = new MetadataSearch(cache);
		MatchedClass matchedClass = advancedSearch.search(searchTarget, searchString, basedOn);
		entityCollection = matchedClass.getEntityCollection();
		List<EntityInterface> resultList = new ArrayList<EntityInterface>(entityCollection);
		removeEntitiesNotSearchable(resultList);
		StringBuffer entitiesString = prepareEntitiesString(resultList);

		if ("".equals(entitiesString))
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
		for(EntityInterface entity : resultList)
		{
			Collection<TaggedValueInterface> taggedValueCollection = entity.getTaggedValueCollection();
			for(TaggedValueInterface tagValue : taggedValueCollection)
			{
				if(tagValue.getKey().equals(
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
	 * @param resultList
	 * @return
	 */
	private StringBuffer prepareEntitiesString(List<EntityInterface> resultList)
	{
		StringBuffer entitiesString = new StringBuffer("");
		for (int i = 0; i < resultList.size(); i++)
		{
			EntityInterface entity = (EntityInterface) resultList.get(i);
			String fullyQualifiedEntityName = entity.getName();
			StringBuffer fullEntityName = new StringBuffer(Utility.parseClassName(fullyQualifiedEntityName));
			StringBuffer entityName = new StringBuffer(Utility.getDisplayLabel(fullEntityName.toString()));
			String entityId = entity.getId().toString();
			String description = entity.getDescription();
			entitiesString = entitiesString
					.append(edu.wustl.query.util.global.Constants.ENTITY_SEPARATOR);
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
	 * This Method sets isQueryAttribute if isQuery Parameter is present
	 * @param request
	 */
	private void setIsQueryAttribute(HttpServletRequest request)
	{
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
		String permissiblevaluesCheckBoxChecked = searchForm.getPermissibleValuesChecked();
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

		checked = isChecked(permissiblevaluesCheckBoxChecked);
		if (checked)
		{
			target.add(Integer.valueOf((Constants.PV)));
		}

		int size=target.size();
		int[] searchTarget = new int[size];
		for (int i = 0; i < size; i++)
		{
			searchTarget[i] = ((target.get(i))).intValue();
		}
		return searchTarget;
	}

	/**
	 * This Method checks wheather Advanced Option checkbox is checked 
	 * @param option
	 * @return
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
	 * This Method includes class and/os atrribute with description to serchTarget if include description checkbox is checked
	 * @param includeDescriptionChecked
	 * @param withDesc
	 * @param str
	 * @param target
	 */
	private void includeDescription(String includeDescriptionChecked, int withDesc, int str,
			List<Integer> target)
	{
		Integer temp = Integer.valueOf(str);
		boolean cheked=isChecked(includeDescriptionChecked);
		if(cheked)
		{
			temp = Integer.valueOf(withDesc);
		}
		target.add(temp);
	}

}