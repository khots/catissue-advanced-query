
package edu.wustl.query.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.action.BaseAction;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.spreadsheet.SpreadSheetData;
import edu.wustl.query.spreadsheet.SpreadSheetViewGenerator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryDetails;


/**
 * This class is invoked when user clicks on a node from the tree. It loads the data required for grid formation.
 * @author deepti_shelar
 */
public class ShowGridAction extends BaseAction
{

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
	protected ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
	
		Map<EntityInterface, List<EntityInterface>> mainEntityMap = null; //(Map<EntityInterface, List<EntityInterface>>) session
//				.getAttribute(Constants.MAIN_ENTITY_MAP);
		
		//Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session.getAttribute(Constants.ID_NODES_MAP);
		
		//List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>)session.getAttribute(Constants.TREE_ROOTS);
		//SessionDataBean sessionData = getSessionData(request);
		
		String idOfClickedNode = request.getParameter(Constants.TREE_NODE_ID);
		Map spreadSheetDatamap = null;
		String forward = Constants.SUCCESS;
	
		QueryDetails queryDetailsObj = new QueryDetails(session);
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		int queryExecutionId = 0; //((Integer)session.getAttribute(Constants.QUERY_EXECUTION_ID)).intValue();
		if(queryDetailsObj.getQueryExecutionId() == 0)
		{
			queryDetailsObj.setQueryExecutionId(4);
		}
		
		Long queryid = (Long) session.getAttribute("dataQueryId");
		if(queryid == null)
		{
			queryid = Long.parseLong("257");
		}
		
		DefaultBizLogic defaultBizLogic = new DefaultBizLogic();
		query = (IParameterizedQuery) defaultBizLogic.retrieve(ParameterizedQuery.class.getName(),queryid);
		queryDetailsObj.setQuery(query);

		
		
		SpreadSheetViewGenerator spreadSheetViewGenerator = new SpreadSheetViewGenerator();
		SpreadSheetData spreadsheetData = new SpreadSheetData();
		
		spreadSheetViewGenerator.createSpreadsheet(queryDetailsObj, idOfClickedNode, 
				spreadsheetData, request);

		setGridData(request,spreadsheetData);
		
		return mapping.findForward(forward);
	}

	private void setGridData(HttpServletRequest request, SpreadSheetData spreadsheetData)
	{
		HttpSession session = request.getSession();
//		session.setAttribute(Constants.PAGINATION_DATA_LIST, spreadsheetData.getDataList());
		session.setAttribute(Constants.SELECTED_COLUMN_META_DATA, spreadsheetData.getSelectedColumnsMetadata());
		session.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, spreadsheetData.getColumnsList());
		session.setAttribute(Constants.MAIN_ENTITY_MAP, spreadsheetData.getMainEntityMap());
		session.setAttribute(Constants.TOTAL_RESULTS, Integer.parseInt(""+spreadsheetData.getDataList().size()));
		session.setAttribute(Constants.RESULTS_PER_PAGE,"500");
		session.setAttribute(Constants.PAGE_NUMBER,"1");
		
		
		request.setAttribute(Constants.PAGE_OF, Constants.PAGE_OF_QUERY_RESULTS);
		request.setAttribute(Constants.PAGINATION_DATA_LIST, spreadsheetData.getDataList());
	}

	/**Method that will add an error message in action errors when id particular data node is -1 i.e. user is not authorized to see 
	 * this particular record. 
	 * @param request
	 */
	private void getErrorMessageUserNotAuthorized(HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("query.userNotAuthorizedError");
		 errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}
}