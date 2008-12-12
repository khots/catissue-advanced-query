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
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.tree.QueryTreeNodeData;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{ 
		request.setAttribute(Constants.CURRENT_PAGE, Constants.DEFINE_RESULTS_VIEW);
		
		System.out.println();
		CategorySearchForm searchForm = (CategorySearchForm) form;
		searchForm = QueryModuleUtil.setDefaultSelections(searchForm);
		HttpSession session = request.getSession();
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		
		//Updating the IQuery for Containment Object for Main Entities
		updateIQueryForContainments(session, query);
		 
	    //Update the Selected Column Name value bean List
		List<NameValueBean> prevSelectedColumnNVBList = setSelectedColumnList(session);
          
        ValidateQueryBizLogic.getValidationMessage(request,query);
		HashMap <EntityInterface, List<EntityInterface>> containmentMap = (HashMap <EntityInterface, List<EntityInterface>>)session.getAttribute(Constants.CONTAINMENT_OBJECTS_MAP);
		List<EntityInterface> mainEntityList = (List<EntityInterface>)session.getAttribute("mainEntityList");  
		List<QueryTreeNodeData> treeDataVector = new ArrayList<QueryTreeNodeData>();
		
		QueryDetails queryDetailsObject = new QueryDetails(session);
		queryDetailsObject.setMainEntityList(mainEntityList);
		queryDetailsObject.setContainmentMap(containmentMap);
        //This tree data Vector is modified for creating tree
		queryDetailsObject.setTreeDataVector(treeDataVector);
		DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
 
		defineGridViewBizLogic.createContainmentTree(searchForm, queryDetailsObject, prevSelectedColumnNVBList);
	    session.setAttribute(Constants.SELECTED_COLUMN_NAME_VALUE_BEAN_LIST,searchForm.getSelectedColumnNameValueBeanList());
		session.setAttribute(Constants.TREE_DATA, treeDataVector);
		session.setAttribute(Constants.QUERY_OBJECT,query);
		return mapping.findForward(Constants.SUCCESS);
	}

	private List<NameValueBean> setSelectedColumnList(HttpSession session)
	{
		List<NameValueBean> prevSelectedColumnNVBList;
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session.getAttribute(Constants.SELECTED_COLUMN_META_DATA);
        if (selectedColumnsMetadata==null)
        {
        	 prevSelectedColumnNVBList=null;
        }
        else
        {
        	 prevSelectedColumnNVBList = selectedColumnsMetadata.getSelectedColumnNameValueBeanList();
        }
		return  prevSelectedColumnNVBList;
	}

	private void updateIQueryForContainments(HttpSession session, IQuery query)
	{
		if(query != null)
	    { 
	    	IQuery queryClone = new DyExtnObjectCloner().clone(query);
			Map <EntityInterface, List<EntityInterface>>containedObjectsMap = IQueryUpdationUtil.getAllConatainmentObjects(query,session);
			
			//Update the IQuery with containment objects......add onlt those containment objects which are not present in IQuery
			IQueryUpdationUtil.addConatinmentObjectsToIquery(query,session,queryClone);
			
			//Add the link/association among parent and containment entities
			IQueryUpdationUtil.addLinks(containedObjectsMap, session,queryClone);
			
			//Removing session attributes
			session.removeAttribute(Constants.ENTITY_EXPRESSION_ID_MAP);
	    }
	}
}
