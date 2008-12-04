package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * This is a action class to load Define Search Results View screen.
 * @author deepti_shelar
 *
 */
public class DefineSearchResultsViewAction extends Action
{

	/**
	 * This method loads define results jsp.
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
		request.setAttribute(Constants.CURRENT_PAGE, Constants.DEFINE_RESULTS_VIEW);
		CategorySearchForm searchForm = (CategorySearchForm) form;
		searchForm = QueryModuleUtil.setDefaultSelections(searchForm);
		
		//By Baljeet ...for containment objects
		HttpSession session = request.getSession();
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		
	    if(query != null)
	    {
	    	IQuery queryClone = new DyExtnObjectCloner().clone(query);
			Map <EntityInterface, List<EntityInterface>>containedObjectsMap = IQueryUpdationUtil.getAllConatainmentObjects(query,session);
			
			//Update the IQuery with containment objects......add onlt those containment objects which are not present in IQuery
			IQueryUpdationUtil.addConatinmentObjectsToIquery(query,session,queryClone);
			
			//Add the link/association among parent and containment entities
			IQueryUpdationUtil.addLinks(containedObjectsMap, session,queryClone);
	    }
				
		//ends
		return mapping.findForward(Constants.SUCCESS);
	}
}
