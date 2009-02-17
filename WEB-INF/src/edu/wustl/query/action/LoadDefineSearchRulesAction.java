
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domain.Entity;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.HtmlProvider;
import edu.wustl.query.util.global.Constants;

/**
 * When the Link representing the searched entity is clicked, the UI for Add Limits section is generated with help of
 * GenerateHtmlForAddLimitsBizLogic. The entity is taken from a map of user searched entities is already strored in session. 
 * @author deepti_shelar
 *
 */
public class LoadDefineSearchRulesAction extends Action
{

	/**
	 * This method loads the html for addlimits section.This html is the replaced with the div data with the help of Ajax script.
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
		CategorySearchForm searchForm = (CategorySearchForm) form;
		String entityName = searchForm.getEntityName();

		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString(searchForm.getTextField());
		generateHTMLDetails.setAttributeChecked(Boolean.valueOf(searchForm.getAttributeChecked()));
		generateHTMLDetails.setPermissibleValuesChecked(Boolean.valueOf(searchForm
				.getPermissibleValuesChecked()));

		HtmlProvider htmlProvider = new HtmlProvider(generateHTMLDetails);

		String html = "";
		/*	Map searchedEntitiesMap = (Map) request.getSession().getAttribute(Constants.SEARCHED_ENTITIES_MAP);
			if (searchedEntitiesMap != null)
			{
		*/Entity entity = (Entity) EntityCache.getCache().getEntityById(Long.valueOf((entityName)));
		
		if (entity != null)
		{
			request.getSession().removeAttribute(edu.wustl.query.util.global.Constants.SELECTED_CONCEPT_LIST);
			html = htmlProvider.generateHTML(entity, null,generateHTMLDetails);
		}
		//	}
		request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE, generateHTMLDetails.getEnumratedAttributeMap());
		request.getSession().setAttribute(Constants.ENTITY_NAME, entityName);
		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		response.getWriter().write(html);
		return null;
	}

}
