package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleUtil;
import edu.wustl.common.action.BaseAction;
/**
 * Action is called when user clicks on QueryWizard link on search tab.
 * @author deepti_shelar
 */
public class QueryWizardAction extends Action
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
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		System.out.println("Here in Query Wizard Action");
		HttpSession session = request.getSession();
		CategorySearchForm searchForm = (CategorySearchForm) form;
		session.removeAttribute(Constants.QUERY_OBJECT);
		session.removeAttribute(Constants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(Constants.IS_SAVED_QUERY);
		session.removeAttribute(edu.wustl.common.util.global.Constants.IS_SIMPLE_SEARCH);
		session.removeAttribute(DAGConstant.ISREPAINT);
		session.removeAttribute(DAGConstant.TQUIMap);
		session.removeAttribute(Constants.EXPORT_DATA_LIST);
		session.removeAttribute(Constants.ENTITY_IDS_MAP);
		searchForm = QueryModuleUtil.setDefaultSelections(searchForm);
		return mapping.findForward(edu.wustl.query.util.global.Constants.SUCCESS);
	}
}
