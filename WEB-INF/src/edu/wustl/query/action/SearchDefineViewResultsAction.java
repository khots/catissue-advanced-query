package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.globus.util.http.HTTPRequestParser;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.QueryResultObjectDataBean;
import edu.wustl.common.dao.QuerySessionData;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleSearchQueryUtil;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

public class SearchDefineViewResultsAction  extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		  HttpSession session= request.getSession();
	      String target = setTarget(response,request);
	      if(target.equals(Constants.SUCCESS))
	      {
	    	CategorySearchForm categorySearchForm = (CategorySearchForm) form;
	        QueryDetails queryDetailsObj = new QueryDetails(session);
		    QueryModuleError status = QueryModuleError.SUCCESS;
		    String option=categorySearchForm.getOptions();
		    status = setStatus(request,option);  
		    StringBuffer targetString = new StringBuffer(target);
		    target = handleStatus(targetString, request, status, categorySearchForm);
		    	if(target.equals(Constants.SUCCESS))
		    	{
		    		setSelectedColumnNames(session, categorySearchForm);
		    		Map<String,Object> spreadSheetDataMap = new HashMap<String ,Object>();
	    	        QuerySessionData querySessionData = (QuerySessionData) session.getAttribute(Constants.QUERY_SESSION_DATA);
	    	        Map<Long, QueryResultObjectDataBean> queryResultObjecctDataMap = new HashMap<Long, QueryResultObjectDataBean>();
			        StringBuffer selectedColumnNames = updateSelectedColumnMetadata(session,
					categorySearchForm, queryDetailsObj, spreadSheetDataMap,
					queryResultObjecctDataMap);
			DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
			String SqlForSelectedColumns = defineGridViewBizLogic.createSQLForSelectedColumn(selectedColumnNames.toString(), querySessionData.getSql());
			QueryOutputSpreadsheetBizLogic queryOutputSpreadsheetBizLogic = new QueryOutputSpreadsheetBizLogic();
			String recordsPerPageStr = (String) session.getAttribute(Constants.RESULTS_PER_PAGE);
			int recordsPerPage =Integer.valueOf(recordsPerPageStr);
			querySessionData = queryOutputSpreadsheetBizLogic.getQuerySessionData(queryDetailsObj,recordsPerPage, 0, spreadSheetDataMap, SqlForSelectedColumns,queryResultObjecctDataMap, false);
			spreadSheetDataMap.put(Constants.QUERY_REASUL_OBJECT_DATA_MAP, session.getAttribute(Constants.QUERY_REASUL_OBJECT_DATA_MAP));
			spreadSheetDataMap.put(Constants.QUERY_SESSION_DATA, querySessionData);
			spreadSheetDataMap.put(Constants.MAIN_ENTITY_MAP, queryDetailsObj.getMainEntityMap());
			QueryModuleUtil.setGridData(request, spreadSheetDataMap);
	  }
	 }
			return mapping.findForward(target);
	}


	private String setTarget(HttpServletResponse response, HttpServletRequest request)
	{
		String validationMessage = ValidateQueryBizLogic.getValidationMessage(request,(IQuery)request.getSession().getAttribute(Constants.QUERY_OBJECT));
		String target = Constants.SUCCESS;
		if (validationMessage != null)
		  {
			  response.setContentType(Constants.CONTENT_TYPE_TEXT);
			  target=Constants.FAILURE;
		  }
		return target;
	}


	private StringBuffer updateSelectedColumnMetadata(HttpSession session,
			CategorySearchForm categorySearchForm, QueryDetails queryDetailsObj,
			Map<String, Object> spreadSheetDataMap,
			Map<Long, QueryResultObjectDataBean> queryResultObjecctDataMap)
	{
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata)session.getAttribute(Constants.SELECTED_COLUMN_META_DATA);
		  DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
		  QuerySessionData querySessionData = (QuerySessionData) session.getAttribute(Constants.QUERY_SESSION_DATA);
		  
		  selectedColumnsMetadata.setDefinedView(true);
		 			
		  defineGridViewBizLogic.getSelectedColumnsMetadata(categorySearchForm, queryDetailsObj, selectedColumnsMetadata);
		  StringBuffer selectedColumnNames = new StringBuffer();
		  String nodeData = getClickedNodeData(querySessionData.getSql());
		  List<String> definedColumnsList = defineGridViewBizLogic.getSelectedColumnList(categorySearchForm,selectedColumnsMetadata.getSelectedAttributeMetaDataList(), selectedColumnNames,
					queryResultObjecctDataMap, queryDetailsObj,nodeData);
		  List<NameValueBean>   selectedColumnNameValueBeanList = categorySearchForm.getSelectedColumnNameValueBeanList();
		  selectedColumnsMetadata.setSelectedColumnNameValueBeanList(selectedColumnNameValueBeanList);
		  OutputTreeDataNode currentSelectedObject = selectedColumnsMetadata.getCurrentSelectedObject();
		 selectedColumnsMetadata.setCurrentSelectedObject(currentSelectedObject);
		 spreadSheetDataMap.put(Constants.SPREADSHEET_COLUMN_LIST, definedColumnsList);
		 spreadSheetDataMap.put(Constants.SELECTED_COLUMN_META_DATA,selectedColumnsMetadata);
		 spreadSheetDataMap.put(Constants.DEFINE_VIEW_QUERY_REASULT_OBJECT_DATA_MAP, queryResultObjecctDataMap);
		return selectedColumnNames;
	}


	private void setSelectedColumnNames(HttpSession session, CategorySearchForm categorySearchForm)
	{
		if(categorySearchForm.getSelectedColumnNames()== null)   
		{	
			categorySearchForm.setSelectedColumnNames((String [])session.getAttribute("selectedColumnNames"));
			session.removeAttribute("selectedColumnNames");
		}
	}


	private  String handleStatus(StringBuffer target, HttpServletRequest request, QueryModuleError status,
			CategorySearchForm categorySearchForm)
	{ 
		 ActionErrors errors = new ActionErrors();
		switch(status.getErrorCode())
		   { 
			   
			   case 10 : target=new StringBuffer("decisionMakingPage");
			   request.getSession().setAttribute("selectedColumnNames", categorySearchForm.getSelectedColumnNames());
			             break;
			   case 3:
				         target = new StringBuffer("validateResults");
				        // errors =Utility.setActionError(ApplicationProperties
							//		.getValue("query.zero.records.present"),"errors.item");
				         errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.item",
									ApplicationProperties.getValue("query.zero.records.present")));
				         saveErrors(request, errors);
				   
				       break;
			   
			   case 4:
				   target = new StringBuffer("validateResults");
			          errors=Utility.setActionError(ApplicationProperties
								.getValue("errors.executeQuery.genericmessage"),"errors.item");
			         saveErrors(request, errors);
			   
				       break;
			   case 5:
				   target = new StringBuffer("validateResults");
			          errors=Utility.setActionError(ApplicationProperties
								.getValue("errors.executeQuery.genericmessage"),"errors.item");
			         saveErrors(request, errors);
			   
			           break;
				       
			   default :  
				    	break;
		   }
		return target.toString();
	}


	private QueryModuleError setStatus(HttpServletRequest request,String option)
	{
		QueryModuleError status;
		IQuery query = (IQuery) request.getSession().getAttribute(Constants.QUERY_OBJECT);
		boolean isRulePresentInDag = QueryModuleUtil.checkIfRulePresentInDag(query);
		QueryModuleSearchQueryUtil QMSearchQuery = new QueryModuleSearchQueryUtil(request, query);
		if (isRulePresentInDag)
		{
			status = QMSearchQuery.searchQuery(option);
		}
		else
		{
			status = QueryModuleError.EMPTY_DAG;
		}
		return status;
	}


	private String getClickedNodeData(String sql) {
		
		int index = sql.lastIndexOf('=');
		String clickedData = "";
		if(index != -1)
		{
			clickedData = sql.substring(index +1,sql.length());
		}
		return clickedData;
	}
	
	public static ActionErrors setActionError(String errorMessage, String key)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		return errors;
	}

}
