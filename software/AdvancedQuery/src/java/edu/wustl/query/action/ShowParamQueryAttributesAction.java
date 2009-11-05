package edu.wustl.query.action;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleConstants;

/**
 * AJAX handler to sho all/selected attributes on Save Query page
 * 
 * @author vijay_pande
 * 
 */
public class ShowParamQueryAttributesAction extends AbstractQueryBaseAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wustl.common.action.BaseAction#executeAction(
	 * org.apache.struts.action.ActionMapping,
	 * org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Boolean isChecked = Boolean.parseBoolean(request
				.getParameter("isChecked"));
		HttpSession session = request.getSession();
		IQuery queryObject = (IQuery) session
				.getAttribute(Constants.QUERY_OBJECT);
		Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
		/**
		 * isChecked = showAll, isChecked boolean value directly used to show
		 * all conditions isChecked = true : Show all conditional parameters
		 * isChecked = false : Show only those parameters on which user had set
		 * any condition
		 */
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);

		String htmlContents = new SavedQueryHtmlProvider()
				.getHTMLForSavedQuery(queryObject, isChecked,
						Constants.SAVE_QUERY_PAGE, customFormulaIndexMap,
						generateHTMLDetails, false);
		request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE,
				generateHTMLDetails.getEnumratedAttributeMap());
		session.setAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP,
				customFormulaIndexMap);

		Writer writer = response.getWriter();
		writer.write(htmlContents);

		return null;
	}

}
