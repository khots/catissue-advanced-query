
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

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.action.BaseAction;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;
import edu.wustl.query.bizlogic.QueryOutputTreeBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

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
		Map<EntityInterface, List<EntityInterface>> mainEntityMap = (Map<EntityInterface, List<EntityInterface>>) session
				.getAttribute(AQConstants.MAIN_ENTITY_MAP);

		//Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session.getAttribute(AQConstants.ID_NODES_MAP);

		//List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>)session.getAttribute(AQConstants.TREE_ROOTS);
		//SessionDataBean sessionData = getSessionData(request);

		String idOfClickedNode = request.getParameter(AQConstants.TREE_NODE_ID);
		QueryOutputTreeBizLogic treeBizLogic = new QueryOutputTreeBizLogic();
		idOfClickedNode = treeBizLogic.decryptId(idOfClickedNode);
		Map gridDataMap = null;
		String parentNodeId = idOfClickedNode.substring(idOfClickedNode
				.lastIndexOf(AQConstants.NODE_SEPARATOR) + 2, idOfClickedNode.length());
		String actualId = parentNodeId.substring(parentNodeId.lastIndexOf('_') + 1,
				parentNodeId.length());

		String forward = AQConstants.SUCCESS;
		if (actualId != null && actualId.equals(AQConstants.HASHED_NODE_ID))
		{
			getErrorMessageUserNotAuthorized(request);
			forward = AQConstants.FAILURE;
		}

		else
		{
			gridDataMap = getspreadSheetDataMap(session, idOfClickedNode, parentNodeId);
			gridDataMap.put(AQConstants.MAIN_ENTITY_MAP, mainEntityMap);
			request.getSession().setAttribute(AQConstants.ENTITY_IDS_MAP,
					gridDataMap.get(AQConstants.ENTITY_IDS_MAP));
			request.getSession().setAttribute(AQConstants.EXPORT_DATA_LIST,
					gridDataMap.get(AQConstants.EXPORT_DATA_LIST));

			QueryModuleUtil.setGridData(request, gridDataMap);
		}

		return mapping.findForward(forward);
	}

	/**
	 * This Method returns the Map containing the results to be displayed on spreadsheet
	 * @param session
	 * @param idOfClickedNode
	 * @param parentNodeId
	 * @return
	 * @throws DAOException
	 * @throws ClassNotFoundException
	 */
	private Map getspreadSheetDataMap(HttpSession session, String idOfClickedNode,
			String parentNodeId) throws DAOException, ClassNotFoundException
	{
		Map gridDatamap;
		Map<Long, QueryResultObjectDataBean> resultDataMap = (Map<Long, QueryResultObjectDataBean>) session
				.getAttribute(AQConstants.DEFINE_VIEW_QUERY_REASULT_OBJECT_DATA_MAP);
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session
				.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		Map<String, IOutputTerm> outputTermsColumns = (Map<String, IOutputTerm>) session
				.getAttribute(AQConstants.OUTPUT_TERMS_COLUMNS);
		String recordsPerPageStr = (String) session.getAttribute(AQConstants.RESULTS_PER_PAGE);
		int recordsPerPage = Integer.valueOf((recordsPerPageStr));
		Map<Long, Map<AttributeInterface, String>> columnMap = (Map<Long, Map<AttributeInterface, String>>) session
				.getAttribute(AQConstants.ID_COLUMNS_MAP);
		//QueryDetails queryDetailsObj = new QueryDetails(session);
		QueryOutputSpreadsheetBizLogic outputSpreadsheetBizLogic = new QueryOutputSpreadsheetBizLogic();
		IQuery query = (IQuery) session.getAttribute(AQConstants.QUERY_OBJECT);
		boolean hasConditionOnIdentifiedField = Utility.isConditionOnIdentifiedField(query);
		session.setAttribute(AQConstants.HAS_CONDITION_ON_IDENTIFIED_FIELD,
				hasConditionOnIdentifiedField);
		if (idOfClickedNode.endsWith(AQConstants.LABEL_TREE_NODE))
		{
			gridDatamap = outputSpreadsheetBizLogic.processSpreadsheetForLabelNode(
					session, columnMap, idOfClickedNode, recordsPerPage,
					selectedColumnsMetadata, hasConditionOnIdentifiedField,
					resultDataMap, query.getConstraints(), outputTermsColumns);
		}
		else
		{
			gridDatamap = outputSpreadsheetBizLogic.processSpreadsheetForDataNode(
					session, parentNodeId, recordsPerPage, selectedColumnsMetadata,
					hasConditionOnIdentifiedField, resultDataMap,
					query.getConstraints(), outputTermsColumns);
		}
		return gridDatamap;
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