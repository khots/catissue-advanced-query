
package edu.wustl.query.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domaininterface.CategoryInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.querysuite.QueryModuleConstants;

public class CategoryAction extends AbstractQueryBaseAction
{

	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		Collection<CategoryInterface> allCategories = EntityCache.getInstance().getAllCategories();
		StringBuffer categoryString = prepareCategoryString(allCategories);
		response.setContentType("text/html");
		response.getWriter().write(categoryString.toString());
		if (request.getParameter("isQuery") == null)
		{
			session.removeAttribute(DAGConstant.QUERY_OBJECT);
			session.removeAttribute("allLimitExpressionIds");
		}
		return mapping.findForward("");
	}

	private StringBuffer prepareCategoryString(Collection<CategoryInterface> resultList)
	{
		StringBuffer categoryString = new StringBuffer("");
		for (CategoryInterface category : resultList)
		{
			String categoryName = category.getName();
			/*StringBuffer fullCategoryName = new StringBuffer(Utility
			 .parseClassName(fullyQualifiedCategoryName));
			 StringBuffer categoryName = new StringBuffer(Utility.getDisplayLabel(fullCategoryName
			 .toString()));*/
			String categoryId = category.getId().toString();
			String description = category.getDescription();
			categoryString = categoryString.append(QueryModuleConstants.ENTITY_SEPARATOR);
			categoryString = categoryString.append(categoryName);
			categoryString = categoryString
					.append(edu.wustl.query.util.global.Constants.ATTRIBUTE_SEPARATOR);
			categoryString = categoryString.append(categoryId);
			categoryString = categoryString
					.append(edu.wustl.query.util.global.Constants.ATTRIBUTE_SEPARATOR);
			categoryString = categoryString.append(description);
		}
		return categoryString;
	}
}
