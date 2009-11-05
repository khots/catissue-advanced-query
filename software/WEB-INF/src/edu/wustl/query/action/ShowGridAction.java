package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.SpreadsheetGeneratorFactory;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.queryexecutionmanager.DataQueryResultStatus;
import edu.wustl.query.spreadsheet.SpreadSheetData;
import edu.wustl.query.spreadsheet.SpreadSheetViewGenerator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ViewType;

/**
 * This class is invoked when user clicks on a node from the tree. It loads the data required for grid formation.
 * @author deepti_shelar
 */
public class ShowGridAction extends AbstractQueryBaseAction
{
	private static org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(ShowGridAction.class);
	/**
	 * This method loads the data required for Query Output tree. 
	 * With the help of QueryOutputTreeBizLogic it generates a string which will be then passed to client side and tree is formed accordingly. 
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
		//cancel a previously running thread if any
		HttpSession session = request.getSession();
		request.getSession().removeAttribute(Constants.STATUS);
		Long dataQueryExId = (Long) session.getAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID);
		String idOfClickedNode = request.getParameter(Constants.TREE_NODE_ID);
	     if(idOfClickedNode!=null && dataQueryExId!=null)
		{
		AbstractQueryUIManager queryUIManager = AbstractQueryUIManagerFactory
		.getDefaultAbstractUIQueryManager();
		queryUIManager.cancelDataQuery(dataQueryExId);
			 session.removeAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID);
		} 
		//String queryExcutionId = "DataQueryExecutionId1";
		ActionForward forward = mapping.findForward(Constants.SUCCESS); 
        Long dataQueryExecId = (Long) session.getAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID);
        SpreadSheetData spreadsheetData = new SpreadSheetData();
		try{
         if(dataQueryExecId==null)  //data query execution is not set
          {
		    Long dataQEId;
		    QueryDetails queryDetailsObj = new QueryDetails(session);
			// DefaultBizLogic defaultBizLogic = BizLogicFactory.getDefaultBizLogic();
			IQuery query  = (IQuery)session.getAttribute(Constants.PATIENT_DATA_QUERY);
		    	queryDetailsObj.setQuery(query);
			//session.setAttribute(Constants.QUERY_OBJECT, query);
			AbstractQueryUIManager queryUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request, query);
			SpreadSheetViewGenerator viewGenerator = 
				SpreadsheetGeneratorFactory.configureDefaultSpreadsheetGenerator(ViewType.USER_DEFINED_SPREADSHEET_VIEW);

			//String idOfClickedNode = request.getParameter(Constants.TREE_NODE_ID);
			NodeId node = new NodeId(idOfClickedNode);
			IQuery  generatedQuery = viewGenerator.createSpreadsheet(node,
					queryDetailsObj, spreadsheetData,queryUIManager.getAbstractQuery());
			if(session.getAttribute(Constants.PERSON_UPI_COUNT)!=null 
					&& ((Integer)session.getAttribute(Constants.PERSON_UPI_COUNT))<Variables.resultLimit)
		   {
			    		session.setAttribute(Constants.ABSTRACT_QUERY, queryUIManager.getAbstractQuery());
						spreadsheetData.setDataList(new ArrayList<List<Object>>());
						spreadsheetData.setDataTypeList(new ArrayList<String>());
						request.setAttribute("status",DataQueryResultStatus.NO_RECORDS_FOUND);
		   }
			else
			{
				session.setAttribute("GeneratedQuery",generatedQuery);
				session.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, spreadsheetData.getColumnsList());
				queryUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request, generatedQuery);
				dataQEId = getDataqueryExecutionId(session,node,queryDetailsObj.getQueryExecutionId(),queryUIManager);
				session.setAttribute(Constants.SPREADSHEET_DQ_EXECUTION_ID,dataQEId); 
			}
			
		}
	}
		catch (QueryModuleException ex)
		{
			logger.error(ex.getMessage(),ex);
			generateErrorMessage(request, ex.getMessage());
		}
		request.setAttribute(Constants.PAGE_OF, Constants.PAGE_OF_QUERY_RESULTS); 
		return forward;
	}

	/**
	 * @param request
	 * @param message
	 */
	private void generateErrorMessage(HttpServletRequest request, String message)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("errors.item", message);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}

	/**
	 * @param query
	 * @param spreadsheetData
	 * @param request
	 * @param queryExecutionId
	 * @param data 
	 * @throws QueryModuleException
	 */

	
	private Long getDataqueryExecutionId(HttpSession session,NodeId node, Long queryExecutionID,
			AbstractQueryUIManager queryUIManager)throws QueryModuleException 
	{
		Long dataQueryExecId;
		String rootData = node.getRootData();
		if(rootData.equalsIgnoreCase(Constants.NULL_ID))
		{
			 dataQueryExecId =  queryUIManager.executeDataQuery(queryExecutionID, ViewType.SPREADSHEET_VIEW);
			 session.setAttribute(Constants.ABSTRACT_QUERY, queryUIManager.getAbstractQuery()); 
		}
		else
		{
			if(rootData.indexOf(Constants.UNDERSCORE)>-1)
			{
				rootData = rootData.substring(rootData.lastIndexOf(Constants.UNDERSCORE)+1);
			}
			
			dataQueryExecId = queryUIManager.executeDataQuery(queryExecutionID, rootData ,ViewType.SPREADSHEET_VIEW);
		}
		return dataQueryExecId;
	} 

}
