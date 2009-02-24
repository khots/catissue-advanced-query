
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.action.BaseAction;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.spreadsheet.SpreadSheetData;
import edu.wustl.query.spreadsheet.SpreadSheetViewGenerator;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;

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

		try
		{
			QueryDetails queryDetailsObj = new QueryDetails(session);

			Long queryid = (Long) session.getAttribute(Constants.DATA_QUERY_ID);

			DefaultBizLogic defaultBizLogic = new DefaultBizLogic();
			IQuery query = (IParameterizedQuery) defaultBizLogic.retrieve(ParameterizedQuery.class
					.getName(), queryid);
			queryDetailsObj.setQuery(query);

			SpreadSheetViewGenerator spreadSheetViewGenerator = new SpreadSheetViewGenerator();
			SpreadSheetData spreadsheetData = new SpreadSheetData();

			spreadSheetViewGenerator.createSpreadsheet(queryDetailsObj, spreadsheetData, request);

			setGridData(request, spreadsheetData);

		}
		catch (QueryModuleException ex)
		{
			generateErrorMessage(request, ex.getMessage());
		}
		return mapping.findForward(Constants.SUCCESS);
	}

	/**
	 * @param request
	 * @param spreadsheetData
	 */
	private void setGridData(HttpServletRequest request, SpreadSheetData spreadsheetData)
	{
		HttpSession session = request.getSession();
		session.setAttribute(Constants.SELECTED_COLUMN_META_DATA, spreadsheetData
				.getSelectedColumnsMetadata());
		session.setAttribute(Constants.SPREADSHEET_COLUMN_LIST, spreadsheetData.getColumnsList());
		session.setAttribute(Constants.MAIN_ENTITY_MAP, spreadsheetData.getMainEntityMap());
		session.setAttribute(Constants.TOTAL_RESULTS, Integer.parseInt(""
				+ spreadsheetData.getDataList().size()));
		session.setAttribute(Constants.RESULTS_PER_PAGE, Variables.recordsPerPageForSpreadSheet);
		session.setAttribute(Constants.PAGE_NUMBER, "1");

		request.setAttribute(Constants.PAGE_OF, Constants.PAGE_OF_QUERY_RESULTS);
		request.setAttribute(Constants.PAGINATION_DATA_LIST, spreadsheetData.getDataList());
	}

	/**
	 * @param request
	 * @param message
	 */
	private void generateErrorMessage(HttpServletRequest request, String message)
	{
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError(message);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	}
}