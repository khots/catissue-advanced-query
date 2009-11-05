
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.HtmlProvider;
import edu.wustl.query.util.global.Constants;

/**
 * When the Link representing the searched entity is clicked, the UI for Add Limits section is generated with help of
 * GenerateHtmlForAddLimitsBizLogic. The entity is taken from a map of user searched entities is already strored in session. 
 * @author deepti_shelar
 *
 */
public class LoadDefineSearchRulesAction extends AbstractQueryBaseAction
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
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CategorySearchForm searchForm = (CategorySearchForm) form;
		String entityId = searchForm.getEntityName();
        String pageOf = request.getParameter(Constants.PAGE_OF);
        GenerateHTMLDetails generateHTMLDetails = null;
        if(searchForm.getTextField()!=null)
        {
        	generateHTMLDetails = new GenerateHTMLDetails();
        	generateHTMLDetails.setSearchString(searchForm.getTextField());
        	generateHTMLDetails.setAttributeChecked(Boolean.valueOf(searchForm.getAttributeChecked()));
        	generateHTMLDetails.setPermissibleValuesChecked(Boolean.valueOf(searchForm
    				.getPermissibleValuesChecked()));
        }
        QueryableObjectInterface queryObject = QueryableObjectUtility.getQueryableObjectFromCache(Long.valueOf(entityId));
        HtmlProvider htmlProvider = new HtmlProvider(generateHTMLDetails);
		String html = "";
		if (queryObject != null)
		{
			html = htmlProvider.generateHTML(queryObject, null,pageOf);
		}
		generateHTMLDetails = htmlProvider.getGenerateHTMLDetails();
		request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE, generateHTMLDetails.getEnumratedAttributeMap());
		request.getSession().setAttribute(Constants.ENTITY_NAME,entityId);
		response.setContentType(Constants.CONTENT_TYPE_TEXT);
		response.getWriter().write(html);
		return null;
	}

}
