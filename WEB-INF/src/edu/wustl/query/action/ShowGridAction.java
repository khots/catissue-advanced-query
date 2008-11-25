
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
import edu.wustl.common.beans.QueryResultObjectDataBean;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.security.utility.Utility;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;
import edu.wustl.query.bizlogic.QueryOutputTreeBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryDetails;
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
				.getAttribute(Constants.MAIN_ENTITY_MAP);
		
		//Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session.getAttribute(Constants.ID_NODES_MAP);
		
		//List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>)session.getAttribute(Constants.TREE_ROOTS);
		//SessionDataBean sessionData = getSessionData(request);
		
		String idOfClickedNode = request.getParameter(Constants.TREE_NODE_ID);
		QueryOutputTreeBizLogic treeBizLogic = new QueryOutputTreeBizLogic();
		idOfClickedNode = treeBizLogic.decryptId(idOfClickedNode);
		Map spreadSheetDatamap = null;
		String actualParentNodeId = idOfClickedNode.substring(idOfClickedNode
				.lastIndexOf(Constants.NODE_SEPARATOR) + 2, idOfClickedNode.length());
		String actualId = actualParentNodeId.substring(actualParentNodeId.lastIndexOf('_') + 1,
				actualParentNodeId.length());
		
		String forward = Constants.SUCCESS;
		if (actualId != null && actualId.equals(Constants.HASHED_NODE_ID))
		{
			getErrorMessageUserNotAuthorized(request);
			forward= Constants.FAILURE;
		}

		else
		{
			spreadSheetDatamap = getspreadSheetDataMap(session, idOfClickedNode, actualParentNodeId);
			spreadSheetDatamap.put(Constants.MAIN_ENTITY_MAP, mainEntityMap);
			request.getSession().setAttribute(Constants.ENTITY_IDS_MAP,
					spreadSheetDatamap.get(Constants.ENTITY_IDS_MAP));
			request.getSession().setAttribute(Constants.EXPORT_DATA_LIST,
					spreadSheetDatamap.get(Constants.EXPORT_DATA_LIST));

			QueryModuleUtil.setGridData(request, spreadSheetDatamap);
		}

		return mapping.findForward(forward);
	}

	/**
	 * This Method returns the Map containing the results to be displayed on spreadsheet
	 * @param session
	 * @param idOfClickedNode
	 * @param actualParentNodeId
	 * @return
	 * @throws DAOException
	 * @throws ClassNotFoundException
	 */
	private Map getspreadSheetDataMap(HttpSession session, String idOfClickedNode,
			String actualParentNodeId) throws DAOException, ClassNotFoundException
	{
		Map spreadSheetDatamap;
		Map<Long, QueryResultObjectDataBean> queryResultObjectDataMap = (Map<Long, QueryResultObjectDataBean>) session
		.getAttribute(Constants.DEFINE_VIEW_QUERY_REASULT_OBJECT_DATA_MAP);
		SelectedColumnsMetadata selectedColumnsMetadata = (SelectedColumnsMetadata) session
		.getAttribute(Constants.SELECTED_COLUMN_META_DATA);
		Map<String, IOutputTerm> outputTermsColumns = (Map<String, IOutputTerm>) session
		.getAttribute(Constants.OUTPUT_TERMS_COLUMNS);
		String recordsPerPageStr = (String) session.getAttribute(Constants.RESULTS_PER_PAGE);
		int recordsPerPage = Integer.valueOf((recordsPerPageStr));
		Map<Long, Map<AttributeInterface, String>> columnMap = (Map<Long, Map<AttributeInterface, String>>) session
		.getAttribute(Constants.ID_COLUMNS_MAP);
		QueryDetails queryDetailsObj = new QueryDetails(session);
		QueryOutputSpreadsheetBizLogic outputSpreadsheetBizLogic = new QueryOutputSpreadsheetBizLogic();
		IQuery query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		boolean hasConditionOnIdentifiedField = Utility.isConditionOnIdentifiedField(query);
		session.setAttribute(Constants.HAS_CONDITION_ON_IDENTIFIED_FIELD,
				hasConditionOnIdentifiedField);
		if (idOfClickedNode.endsWith(Constants.LABEL_TREE_NODE))
		{
			spreadSheetDatamap = outputSpreadsheetBizLogic.processSpreadsheetForLabelNode(
					queryDetailsObj, columnMap, idOfClickedNode, recordsPerPage,
					selectedColumnsMetadata, hasConditionOnIdentifiedField,
					queryResultObjectDataMap, query.getConstraints(), outputTermsColumns);
		}
		else
		{
			spreadSheetDatamap = outputSpreadsheetBizLogic.processSpreadsheetForDataNode(
					queryDetailsObj, actualParentNodeId, recordsPerPage,
					selectedColumnsMetadata, hasConditionOnIdentifiedField,
					queryResultObjectDataMap, query.getConstraints(), outputTermsColumns);
		}
		return spreadSheetDatamap;
	}

	/**Method that will add an error message in action errors when id perticular data node is -1 i.e. user is not authorized to see 
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