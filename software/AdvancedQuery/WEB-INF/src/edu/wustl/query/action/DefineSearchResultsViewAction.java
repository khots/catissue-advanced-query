
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.actionforms.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.IQueryTreeGenerationUtil;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;

/**
 * This is a action class to load Define Search Results View screen.
 * @author deepti_shelar
 *
 */
/**
 * @author baljeet_dhindhwal
 *
 */
public class DefineSearchResultsViewAction extends AbstractQueryBaseAction
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(IQueryUpdationUtil.class);

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
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setAttribute(Constants.CURRENT_PAGE, Constants.DEFINE_RESULTS_VIEW);
		CategorySearchForm searchForm = (CategorySearchForm) form;
		//searchForm = QueryModuleActionUtil.setDefaultSelections(searchForm);
		HttpSession session = request.getSession();
		
		String workflow = request.getParameter(Constants.IS_WORKFLOW);
		if (Constants.TRUE.equals(workflow))
		{
			request.setAttribute(Constants.IS_WORKFLOW, Constants.TRUE);
			String workflowName = (String) request.getSession().getAttribute(
					Constants.WORKFLOW_NAME);
			request.setAttribute(Constants.WORKFLOW_NAME, workflowName);
		}

		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		Utility.createRootOutPutTree(query, request);
		setSelectedoutputattributes((IParameterizedQuery) query, searchForm, session);
		String entityId = request.getParameter(Constants.MAIN_ENTITY_ID);
		List<QueryableObjectInterface> mainEntityList = IQueryUpdationUtil.getAllMainObjects(query);
		session.setAttribute(Constants.MAIN_ENTITY_LIST, mainEntityList);
		QueryDetails queryDetailsObject = new QueryDetails(session);
		IQueryTreeGenerationUtil.parseIQueryToCreateTree(queryDetailsObject);
		StringBuilder xmlString = getConatinmentTreeXML(queryDetailsObject);
		setMainEntityList(request);

		//Set the selected column name value bean list to Form
		//setSelectedColumnsNVBeanList(searchForm, prevSelectedColumnNVBList);
		session.setAttribute(Constants.SELECTED_NAME_VALUE_LIST, searchForm
				.getSelectedColumnNameValueBeanList());
		
		/*
		 * changes made for defined Query
		 */
		if (searchForm.getQueryTitle() != null)
		{
			((ParameterizedQuery) query).setName(searchForm.getQueryTitle());
			// session.setAttribute(Constants.QUERY_OBJECT,query);
		}
		String fileName = getFileName();
		writeXMLToTempFile(xmlString.toString(), fileName);
		ActionForward target = null;
		if (entityId == null)
		{
			request.setAttribute(Constants.XML_FILE_NAME, fileName);
			target = mapping.findForward(Constants.SUCCESS);
			
		}
		else
		{
			response.setContentType(Constants.CONTENT_TYPE_TEXT);
			response.getWriter().write(fileName);
			target = null;
		}
		session.setAttribute(Constants.QUERY_OBJECT, query);
		//for save as
		if (query.getId() != null)
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			QuerySharingStatus sharingStatus = queryBizLogic.getSharingStatus(
					(IParameterizedQuery) query, Utility.getSessionData(request).getUserId());
			request.setAttribute(Constants.SHARING_STATUS, sharingStatus.name());
		}
		return target;
	}

	//Attributes are shown selected on Define view page when page is navigated from view Result page	
	private void setSelectedoutputattributes(IParameterizedQuery query,
			CategorySearchForm searchform, HttpSession session)
	{
		String currentPage = searchform.getCurrentPage();
		List<IOutputAttribute> selectedOutputAttributeList = new ArrayList<IOutputAttribute>();
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session
				.getAttribute(Constants.SELECTED_COLUMN_META_DATA);
		if (selectedColumnsMetadata == null)
		{
			selectedOutputAttributeList = query.getOutputAttributeList();
		}
		if (selectedOutputAttributeList == null || selectedOutputAttributeList.isEmpty()
				&& (selectedColumnsMetadata != null && "SaveQuery".equalsIgnoreCase(currentPage)))
		{
			selectedOutputAttributeList = selectedColumnsMetadata.getSelectedOutputAttributeList();
		}
		List<NameValueBean> selectedColumnNameValue = new ArrayList<NameValueBean>();
		for (IOutputAttribute outputAttribute : selectedOutputAttributeList)
		{
			QueryableAttributeInterface attributeInterface = outputAttribute.getAttribute();
			if (attributeInterface.getActualEntity().getName().equals(Constants.MED_ENTITY_NAME)
					&& attributeInterface.getName().equals(Constants.ID))
			{
				continue;
			}
			String uniqueid = outputAttribute.getExpression().getExpressionId()
					+ Constants.EXPRESSION_ID_SEPARATOR + attributeInterface.getId();
			String displayName = Utility.getDisplayNameForColumn(outputAttribute);
			NameValueBean nameValueBean = new NameValueBean(displayName, uniqueid);
			selectedColumnNameValue.add(nameValueBean);
		}
		searchform.setSelectedColumnNameValueBeanList(selectedColumnNameValue);
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
		xmlString = defineGridViewBizLogic.createContainmentTree(queryDetailsObject, xmlString);

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
		return "loadXML_" + System.currentTimeMillis() + ".xml";
	}

	/**
	 * This method writes XML tree to create tree to a temporary file 
	 * @param xmlString
	 * @param fileName
	 * @throws BizLogicException
	 */
	private void writeXMLToTempFile(String xmlString, String fileName) throws BizLogicException
	{
		try
		{
			String path = edu.wustl.query.util.global.Variables.applicationHome
					+ System.getProperty("file.separator");
			OutputStream fout = new FileOutputStream(path + fileName);
			OutputStream bout = new BufferedOutputStream(fout);
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
	/*private List<IOutputAttribute> setSelectedColumnList(HttpSession session)
	 {
	 List<IOutputAttribute> prevSelectedColumnList;
	 SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session.getAttribute(Constants.SELECTED_COLUMN_META_DATA);
	 if (selectedColumnsMetadata==null)
	 {
	 prevSelectedColumnList=null;
	 }
	 else
	 {
	 prevSelectedColumnList = selectedColumnsMetadata.getSelectedOutputAttributeList();
	 }
	 return  prevSelectedColumnList;
	 }*/

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
			Collection<EntityInterface> entityInterface = entityGroupInterface
					.getEntityCollection();
			for (EntityInterface entity : entityInterface)
			{
				if (edu.wustl.query.util.global.Utility.isMainEntity(entity))
				{
					entityList.add(entity);
				}
			}
		}
		request.setAttribute(Constants.ENTITY_LIST, entityList);
	}
}
