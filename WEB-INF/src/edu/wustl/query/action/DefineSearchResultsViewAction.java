package edu.wustl.query.action;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AddContainmentsUtil;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * This is a action class to load Define Search Results View screen.
 * @author deepti_shelar
 *
 */
/**
 * @author baljeet_dhindhwal
 *
 */
public class DefineSearchResultsViewAction extends Action
{

	private static org.apache.log4j.Logger logger =Logger.getLogger(IQueryUpdationUtil.class);
	/**
	 * This method loads define search results view jsp.
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
		String entityId = request.getParameter(Constants.MAIN_ENTITY_ID);
		if(entityId == null)
		{
			AddContainmentsUtil.updateIQueryForContainments(session, query);
		}
		else
		{
			AddContainmentsUtil.updateIQueryForContainments(session, query, entityId);
		}
		List<NameValueBean> prevSelectedColumnNVBList = setSelectedColumnList(session);
        ValidateQueryBizLogic.getValidationMessage(request,query);
        QueryDetails queryDetailsObject = getQueryDetailsObject(session);
		StringBuilder xmlString = getConatinmentTreeXML(searchForm, prevSelectedColumnNVBList,queryDetailsObject);
		setMainEntityList(request);
        session.setAttribute(Constants.SELECTED_COLUMN_NAME_VALUE_BEAN_LIST,searchForm.getSelectedColumnNameValueBeanList());
		session.setAttribute(Constants.QUERY_OBJECT,query);
		String fileName = getFileName();
		writeXMLToTempFile(xmlString.toString(), fileName);
		ActionForward target = null;
		if(entityId != null)
		{
			response.setContentType(Constants.CONTENT_TYPE_TEXT);
			response.getWriter().write(fileName);
			target = null;	
		}
		else
		{
			request.setAttribute(Constants.XML_FILE_NAME, fileName);
			target = mapping.findForward(Constants.SUCCESS);
		}
		return target;
	}

	/**
	 * This method creates XML string to create containment tree
	 * @param searchForm
	 * @param prevSelectedColumnNVBList
	 * @param queryDetailsObject
	 * @return XML String
	 */
	private StringBuilder getConatinmentTreeXML(CategorySearchForm searchForm,
			List<NameValueBean> prevSelectedColumnNVBList, QueryDetails queryDetailsObject)
	{
		DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
		//Create XML String instead of populating the tree data vector
		StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
		xmlString =  defineGridViewBizLogic.createContainmentTree(searchForm, queryDetailsObject, prevSelectedColumnNVBList, xmlString);
		
		//This string is appended for the root node of the tree
		xmlString.append("</item></tree>");
		return xmlString;
	}
	
	/**
	 * This method returns Unique file name 
	 * @return Unique file name
	 */
	private String getFileName()
	{
		return "loadXML_"+System.currentTimeMillis()+".xml";
	}
	
	/**
	 * This method writes XML tree to create tree to a temporary file 
	 * @param xmlString
	 * @param fileName
	 * @throws BizLogicException
	 */
	private void writeXMLToTempFile(String xmlString,String fileName) throws BizLogicException
	{
		try 
		{        
			String path=edu.wustl.query.util.global.Variables.applicationHome+System.getProperty("file.separator");
	        OutputStream fout= new FileOutputStream(path+fileName);
	        OutputStream bout= new BufferedOutputStream(fout);
	        OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1");
	        out.write(xmlString);
	        out.flush();  
	        out.close();
		}
		catch (IOException e) 
		{
			logger.info("Couldn't create XML file");	
		}
	}
	
	
	/**
	 * This method returns QueryDetails Object 
	 * @param session
	 * @return queryDetailsObject
	 */
	private QueryDetails getQueryDetailsObject(HttpSession session)
	{
		QueryDetails queryDetailsObject = new QueryDetails(session);
		
		getMainExpEntityExpressionIdMap(queryDetailsObject);
		
		return queryDetailsObject;
	}
	
	/**
	 * This method creates a map containing list of expression ids of all children for a particular main entity  
	 * @param queryDetailsObject
	 */
	private void getMainExpEntityExpressionIdMap(QueryDetails queryDetailsObject)
	{
		List<OutputTreeDataNode> rootOutputTreeNodeList = queryDetailsObject.getRootOutputTreeNodeList();
		Map <Integer,List<Integer>> mainEntityContainmentIdsMap = new HashMap<Integer, List<Integer>>();
		Map<Integer, List<OutputTreeDataNode>> mainEntitiesContainmentMap = new HashMap<Integer, List<OutputTreeDataNode>>(); 
		//We also need to populate the Main Entity list from the IQuery
		List<EntityInterface> mainEntitiesList = new ArrayList<EntityInterface>();
		Iterator<OutputTreeDataNode> rootNodeItr = rootOutputTreeNodeList.iterator();
		 IQuery query = queryDetailsObject.getQuery();
		
		while(rootNodeItr.hasNext())
		{
			OutputTreeDataNode rootNode = rootNodeItr.next();
			populateMainEntityList(rootNode,mainEntitiesList);
			List<OutputTreeDataNode> childrenNodes = rootNode.getChildren();
			List <OutputTreeDataNode> rootEntityContainmentList = new ArrayList<OutputTreeDataNode>();
			List <OutputTreeDataNode> mainEntitiesTreeDataNodesList = new ArrayList<OutputTreeDataNode>();			
			
			//Now iterate over each children entity and find out if it is any containment entity or any main entity
			separateChildrenNodes(query, childrenNodes, rootEntityContainmentList,mainEntitiesTreeDataNodesList);
			
			//First add the containments of the root Entity. For each containment Entity, get the containments
			getAllConatinmentsForMainEntity(rootEntityContainmentList);
			
			mainEntitiesContainmentMap.put(Integer.valueOf(rootNode.getExpressionId()), rootEntityContainmentList);
			
			//Now for each main Entity , get the containment list
			for(int count =0; count < mainEntitiesTreeDataNodesList.size(); count ++)
			{
				List <OutputTreeDataNode> mainEntityContainmentList = new ArrayList<OutputTreeDataNode>(); 
				OutputTreeDataNode mainTreeDataNode = mainEntitiesTreeDataNodesList.get(count);
				
				//populate main Entity List
				populateMainEntityList(mainTreeDataNode,mainEntitiesList);
				
				getAllMainEntityContainments(mainEntityContainmentList, mainTreeDataNode);
				mainEntitiesContainmentMap.put(Integer.valueOf(mainTreeDataNode.getExpressionId()),mainEntityContainmentList);
			}
		}
		populateEntityIdMap(mainEntityContainmentIdsMap,mainEntitiesContainmentMap);
		queryDetailsObject.setMainExpEntityExpressionIdMap(mainEntityContainmentIdsMap);
		queryDetailsObject.setMainEntityList(mainEntitiesList);
	}



	/**
	 * This method updates rootEntityContainmentList for root entity
	 * @param rootEntityContainmentList
	 */
	private void getAllConatinmentsForMainEntity(List<OutputTreeDataNode> rootEntityContainmentList)
	{
		for(int count = 0; count < rootEntityContainmentList.size(); count ++)
		{
			OutputTreeDataNode treeDataNode =  rootEntityContainmentList.get(count);
			List <OutputTreeDataNode> list = getAllContainmentEntities(treeDataNode);
			if(!list.isEmpty())
			{
				rootEntityContainmentList.addAll(list);
			}
		}
	}

	/**
	 * This method updates mainEntityContainmentList for each main entity present in Query  
	 * @param mainEntityContainmentList
	 * @param mainTreeDataNode
	 */
	private void getAllMainEntityContainments(List<OutputTreeDataNode> mainEntityContainmentList,
			OutputTreeDataNode mainTreeDataNode)
	{
		List <OutputTreeDataNode> childrenList  = mainTreeDataNode.getChildren();
		if(childrenList != null && !childrenList.isEmpty())
		{
			mainEntityContainmentList.addAll(childrenList);
		}
		
		//For each containment of Main entity , get further containments
		if(!mainEntityContainmentList.isEmpty())
		{
			getAllConatinmentsForMainEntity(mainEntityContainmentList);
		}
	}

	/**
	 * This method separates out the containment entities and main entities
	 * from children of root entity 
	 * @param query
	 * @param childrenNodes
	 * @param rootEntityContainmentList
	 * @param mainEntitiesTreeDataNodesList
	 */
	private void separateChildrenNodes(IQuery query, List<OutputTreeDataNode> childrenNodes,
			List<OutputTreeDataNode> rootEntityContainmentList,
			List<OutputTreeDataNode> mainEntitiesTreeDataNodesList)
	{
		for (OutputTreeDataNode childNode : childrenNodes)
		{
			if(childNode.isContainedObject())
			{
				rootEntityContainmentList.add(childNode);
			}
			else if(checkIfOneOftheMainEntity(childNode, query))
			{
			    mainEntitiesTreeDataNodesList.add(childNode);
			}
		}
	}
	
	/**
	 * This method populates main entities list present in IQuery
	 * @param mainNode
	 * @param mainEntitiesList
	 */
	private void populateMainEntityList(OutputTreeDataNode mainNode,List<EntityInterface> mainEntitiesList)
	{
		IOutputEntity outputEntity = mainNode.getOutputEntity();
		EntityInterface entity = outputEntity.getDynamicExtensionsEntity();
		mainEntitiesList.add(entity);
	}
	
	/**
	 * This method populates the map containing list of all expression ids for a main expression
	 * @param mainEntityContainmentIdsMap
	 * @param mainEntitiesContainmentMap
	 */
	private void populateEntityIdMap(Map <Integer,List<Integer>> mainEntityContainmentIdsMap,Map<Integer, List<OutputTreeDataNode>> mainEntitiesContainmentMap)
	{
		Set<Integer> keySet = mainEntitiesContainmentMap.keySet();
		Iterator<Integer> keySetItr = keySet.iterator();
		while(keySetItr.hasNext())
		{
			Integer mainEntityExpId = keySetItr.next();
			List<Integer> expressionIdsList = new ArrayList<Integer>();
			List<OutputTreeDataNode> containmentTreeDataNodes = mainEntitiesContainmentMap.get(mainEntityExpId);
			for (OutputTreeDataNode outputTreeDataNode : containmentTreeDataNodes)
			{
				expressionIdsList.add(Integer.valueOf(outputTreeDataNode.getExpressionId()));
			}
			mainEntityContainmentIdsMap.put(mainEntityExpId,expressionIdsList);
		}
	}
	
	/**
	 * This method returns list of all containments OutputTreeDataNodes for a node 
	 * @param treeDataNode
	 * @return conatainmentEntities
	 */
	private List <OutputTreeDataNode> getAllContainmentEntities(OutputTreeDataNode treeDataNode)
	{
		List <OutputTreeDataNode> conatainmentEntities = new ArrayList<OutputTreeDataNode>();
		List <OutputTreeDataNode> list = treeDataNode.getChildren();
		if((list != null) && (!list.isEmpty()))
		{
			for (OutputTreeDataNode outputTreeDataNode : list)
			{
			   	if(outputTreeDataNode.isContainedObject())
			   	{
			   		conatainmentEntities.add(outputTreeDataNode);
			   	}
			}
		}
		return conatainmentEntities;
	}
	
	/**
	 * This method checks whether entity is main entity or not
	 * @param childNode
	 * @param query
	 * @return ifMainEntity
	 */
	private boolean checkIfOneOftheMainEntity(OutputTreeDataNode childNode, IQuery query)
	{
		boolean ifMainEntity = false;
		IOutputEntity outputEntity = childNode.getOutputEntity();
		EntityInterface entity = outputEntity.getDynamicExtensionsEntity();
		List<EntityInterface> mainEntityList = IQueryUpdationUtil.getAllMainObjects(query);
			if(mainEntityList.contains(entity))
			{
				ifMainEntity = true;
			}
		return ifMainEntity;
	}
	
	/**
	 * This method returns prevSelectedColumnNVBList
	 * @param session
	 * @return prevSelectedColumnNVBList
	 */
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

	/**
	 * This method returns list of all main entities present in Model 
	 * @param request
	 */
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
