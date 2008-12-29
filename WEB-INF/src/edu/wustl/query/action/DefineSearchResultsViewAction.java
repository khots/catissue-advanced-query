package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
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
		CategorySearchForm searchForm = (CategorySearchForm) form;
		searchForm = QueryModuleUtil.setDefaultSelections(searchForm);
		HttpSession session = request.getSession();
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		
		
		QueryDetails queryDetailsObject = null;
		String entityId = request.getParameter("entityId");
		if(entityId == null)
		{
			//Updating the IQuery for Containment Object for Main Entities
			updateIQueryForContainments(session, query);
		}
		else
		{
			EntityInterface entity = EntityCache.getCache().getEntityById(Long.valueOf(entityId));
			
			//Get the Root entity of the IQuery
			queryDetailsObject = new QueryDetails(session);
			OutputTreeDataNode rootSelectedObject = queryDetailsObject.getRootOutputTreeNodeList().get(0);
			IOutputEntity outputEntity = rootSelectedObject.getOutputEntity();
			EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
			
			//Check if the path exists between Root entity of the IQuery and main Entity added
			List<IPath> pathsList = getPathList(entity, rootEntity);
			if(pathsList.isEmpty())
			{
				
			}
			else
			{
				IClientQueryBuilderInterface queryObject = new ClientQueryBuilder();
				queryObject.setQuery(query);
				
				//Add the main entity to IQuery
				int expressionId = ((ClientQueryBuilder) queryObject).addExpression(entity);
				
				//Get the list of all expression added from ADD Limit on DAG
				List<Integer> expressionIdsList =  (List<Integer>)session.getAttribute("allLimitExpressionIds");
				
				//Add the main Entity to List
				expressionIdsList.add(Integer.valueOf(expressionId));
				
				//Get the containments of main Entity Added
				Map<Integer, HashMap <EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap = IQueryUpdationUtil.getAllConatainmentObjects(query,session);
				
			
				//Now add only the containments of main Entity added 
				HashMap <EntityInterface,Integer>entityExpressionIdMap = new HashMap<EntityInterface, Integer>();
				entityExpressionIdMap.put(entity, expressionId);
				
				//Get the map containing list of all containments of each main expression
				Map <Integer,List<EntityInterface>> eachExpressionContainmentMap =	 (Map <Integer,List<EntityInterface>>)session.getAttribute("mainEntityExpressionsMap");
				
				//Get the containment list if main entity added
				List <EntityInterface> containmentEntitiesList = eachExpressionContainmentMap.get(expressionId);
				
				
				//Get the all IQuery constraints
				IConstraints constraints = query.getConstraints();
				
				//Add the containments to iQuery
				IQueryUpdationUtil.addExpressionsToIQuery(queryObject, entityExpressionIdMap, constraints, containmentEntitiesList);
				
				//Now update the mainExpEntityExpressionIdMap for Main Entity Added
				Map <Integer,HashMap <EntityInterface,Integer>> mainExpEntityExpressionIdMap =  (Map <Integer,HashMap <EntityInterface,Integer>>)session.getAttribute("mainExpEntityExpressionIdMap");
				mainExpEntityExpressionIdMap.put(expressionId, entityExpressionIdMap);
				
				
				//Now add link/Associations among parent children containments
				HashMap <EntityInterface, List<EntityInterface>> parentChildrenMap = eachExpressionParentChildMap.get(expressionId);
				
				//We can use above entityExpressionIdMap directly 
				entityExpressionIdMap = mainExpEntityExpressionIdMap.get(expressionId);
				Set <EntityInterface> parentEntitySet = parentChildrenMap.keySet();
				Iterator<EntityInterface> itr = parentEntitySet.iterator();
				while(itr.hasNext())
				{
					EntityInterface parentEntity = itr.next();
					List<EntityInterface> childEntitiesList = parentChildrenMap.get(parentEntity);
					Iterator<EntityInterface> childEntityItr = childEntitiesList.iterator();
					while(childEntityItr.hasNext())
			    	{
			    		EntityInterface childEntity = childEntityItr.next();
			    		IQueryUpdationUtil.addPath(parentEntity,childEntity, queryObject,entityExpressionIdMap);
			    	}
				}
				
				//Now add links Among Root entity of the IQuery and all main entities added on Define Results View Page
				List<EntityInterface> mainEntityList = (List<EntityInterface>)session.getAttribute(Constants.MAIN_ENTITY_LIST);
				if((mainEntityList!= null) && (!mainEntityList.contains(entity)))
				{
					mainEntityList.add(entity);
				}
				int rootExpressionId = rootSelectedObject.getExpressionId();
				int mainEntityExpId =  entityExpressionIdMap.get(entity);
		    	IQueryUpdationUtil.linkTwoNodes(rootExpressionId,mainEntityExpId,pathsList.get(0),queryObject);
			}
		}

		//Update the Selected Column Name value bean List
		List<NameValueBean> prevSelectedColumnNVBList = setSelectedColumnList(session);
          
        ValidateQueryBizLogic.getValidationMessage(request,query);
		//HashMap <EntityInterface, List<EntityInterface>> containmentMap = (HashMap <EntityInterface, List<EntityInterface>>)session.getAttribute(Constants.CONTAINMENT_OBJECTS_MAP);
		
        Map <Integer,List<EntityInterface>> eachExpressionContainmentMap =	 (Map <Integer,List<EntityInterface>>)session.getAttribute("mainEntityExpressionsMap");
        List<EntityInterface> mainEntityList = (List<EntityInterface>)session.getAttribute(Constants.MAIN_ENTITY_LIST);  
        Map <Integer,HashMap <EntityInterface,Integer>> mainExpEntityExpressionIdMap = (Map <Integer,HashMap <EntityInterface,Integer>>)session.getAttribute("mainExpEntityExpressionIdMap");
        
        queryDetailsObject = new QueryDetails(session);
		queryDetailsObject.setMainEntityList(mainEntityList);
		queryDetailsObject.setEachExpressionContainmentMap(eachExpressionContainmentMap);
		queryDetailsObject.setMainExpEntityExpressionIdMap(mainExpEntityExpressionIdMap);
		DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();

		//Create XML String instead of populating the tree data vector
		StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
		xmlString =  defineGridViewBizLogic.createContainmentTree(searchForm, queryDetailsObject, prevSelectedColumnNVBList, xmlString);
		
		//This string is appended for the root node of the tree
		xmlString.append("</item></tree>");
		setMainEntityList(request);
        session.setAttribute(Constants.SELECTED_COLUMN_NAME_VALUE_BEAN_LIST,searchForm.getSelectedColumnNameValueBeanList());
		session.setAttribute(Constants.QUERY_OBJECT,query);
		String fileName = defineGridViewBizLogic.getFileName();
		defineGridViewBizLogic.writeXML(xmlString.toString(), fileName);
		ActionForward target = null;
		if(entityId != null)
		{
			response.setContentType(Constants.CONTENT_TYPE_TEXT);
			response.getWriter().write(fileName);
			target = null;	
		}
		else
		{
			
			request.setAttribute("fileName", fileName);
			target = mapping.findForward(Constants.SUCCESS);
		}
		return target;
	}
	
	private List<IPath> getPathList(EntityInterface entity, EntityInterface rootEntity)
	{
		List<IPath> pathsList;
		IPathFinder pathFinder = new CommonPathFinder();
		AmbiguityObject ambiguityObject = new AmbiguityObject(rootEntity,entity);
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject,pathFinder);
		
		Map<AmbiguityObject, List<IPath>> map = resolveAmbigity.getPathsForAllAmbiguities();
		pathsList = map.get(ambiguityObject);
		return pathsList;
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
			Map<Integer, HashMap <EntityInterface, List<EntityInterface>>> eachExpressionParentChildMap = IQueryUpdationUtil.getAllConatainmentObjects(query,session);
			
			//Update the IQuery with containment objects......add only those containment objects which are not present in IQuery
			IQueryUpdationUtil.addConatinmentObjectsToIquery(query,session);
			
			//Add the link/association among parent and containment entities
			IQueryUpdationUtil.addLinks(eachExpressionParentChildMap, session);
			
	    }
	}
	
	private void setMainEntityList(HttpServletRequest request)
	{
		Collection<EntityGroupInterface> entityGroups = EntityCache.getCache().getEntityGroups();
		ArrayList<EntityInterface> entityList = new ArrayList<EntityInterface>();
		for (EntityGroupInterface entityGroupInterface : entityGroups)
		{
			Collection<EntityInterface> entityInterface =entityGroupInterface.getEntityCollection();
		     for(EntityInterface entity : entityInterface)
		     {
		    	 if (edu.wustl.query.util.global.Utility.isMainEntity(entity))
		    	 {
		    		 entityList.add(entity);
		    	 }
		     }
		}
		request.setAttribute(edu.wustl.query.util.global.Constants.ENTITY_LIST,entityList);
	}	
	
	
}
