package edu.wustl.query.action;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AddContainmentsUtil;
import edu.wustl.query.util.querysuite.IQueryTreeGenerationUtil;
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
        QueryDetails queryDetailsObject = new QueryDetails(session);
        IQueryTreeGenerationUtil.parseIQueryToCreateTree(queryDetailsObject);
        StringBuilder xmlString = getConatinmentTreeXML(queryDetailsObject);
		setMainEntityList(request);
        
		//Set the selected column name value bean list to Form
		setSelectedColumnsNVBeanList(searchForm, prevSelectedColumnNVBList);
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
	 * This method sets the selected column name value bean list 
	 * @param searchForm
	 * @param prevSelectedColumnNVBList
	 */
	private void setSelectedColumnsNVBeanList(CategorySearchForm searchForm,
			List<NameValueBean> prevSelectedColumnNVBList)
	{
		List<NameValueBean> defaultSelectedColumnNameValueBeanList = searchForm.getSelectedColumnNameValueBeanList(); 
	     if(defaultSelectedColumnNameValueBeanList==null)
	     {
	    	 defaultSelectedColumnNameValueBeanList = new ArrayList<NameValueBean>();      
	     }
		 if (prevSelectedColumnNVBList != null)
	     {
			 searchForm.setSelectedColumnNameValueBeanList(prevSelectedColumnNVBList);
	     }
	     else 
	     {
	    	 searchForm.setSelectedColumnNameValueBeanList(defaultSelectedColumnNameValueBeanList);
	     }
	}

	/**
	 * This method creates XML string to create containment tree
	 * @param searchForm
	 * @param prevSelectedColumnNVBList
	 * @param queryDetailsObject
	 * @return XML String
	 */
	private StringBuilder getConatinmentTreeXML(QueryDetails queryDetailsObject)
	{
		DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
		//Create XML String instead of populating the tree data vector
		StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
		xmlString =  defineGridViewBizLogic.createContainmentTree(queryDetailsObject,xmlString);
		
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
