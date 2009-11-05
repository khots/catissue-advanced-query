
package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.queryexecutionmanager.DataQueryResultStatus;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ViewType;

/**
 * This class is invoked when user clicks on a node from the tree.
 * It loads the data required for tree formation.
 * @author deepti_shelar
 */
public class BuildQueryOutputTreeAction extends AbstractQueryBaseAction
{

	/**
	 * This method loads the data required for Query Output tree.
	 * 	With the help of QueryOutputTreeBizLogic it generates a string which will be then
	 * passed to client side and tree is formed accordingly.
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws QueryModuleException
	 *             Query Module Exception
	 * @throws IOException
	 * 				IO Exception
	 * @return ActionForward actionForward
	 */
	// private static org.apache.log4j.Logger logger = LoggerConfig
	// .getConfiguredLogger(BuildQueryOutputTreeAction.class);
	//
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws QueryModuleException,
			IOException
	{
		HttpSession session = request.getSession();
		// remove previous thread running if any
		Long dataQueryExId = (Long) session.getAttribute(Constants.TREEVIEW_DQ_EXECUTION_ID);
		String nodeId = request.getParameter(Constants.TREE_NODE_ID);
		if (dataQueryExId != null)
		{
			AbstractQueryUIManager abstractQueryUIManager = AbstractQueryUIManagerFactory
					.getDefaultAbstractUIQueryManager();
			abstractQueryUIManager.cancelDataQuery(dataQueryExId);
			session.removeAttribute(Constants.TREEVIEW_DQ_EXECUTION_ID);
			session.removeAttribute(Constants.CHILDRENLISTMAP);
			session.removeAttribute(Constants.TREE_NODE_ID);
		}
		// session.setAttribute(Constants.TREE_NODE_ID, nodeId);
		session.removeAttribute(Constants.TREE_NODE_TYPE);
		if (nodeId != null && !nodeId.equals(""))
		{
			String responseString = "";
			if (nodeId.endsWith(Constants.LABEL_TREE_NODE))
			{

				responseString = processLabelNodeClick(request, nodeId);

			}
			else
			{
				session.setAttribute(Constants.TREE_NODE_TYPE, Constants.DATA_TREE_NODE);
				processDataNodeClicked(nodeId, request);
				responseString = Constants.RESPONSE_WAIT;
			}
			response.getWriter().write(responseString);
			session.setAttribute(Constants.TREE_NODE_ID, nodeId);
		}
		return null;
	}

	/**Method to process click of label node.
	 * @param request HttpServletRequest object
	 * @param nodeId clicked node id
	 * @return responseString
	 * @throws QueryModuleException Query module exception
	 */
	private String processLabelNodeClick(HttpServletRequest request, String nodeId)
			throws QueryModuleException
	{
		HttpSession session = request.getSession();
		String responseString = "";
		if (session.getAttribute(Constants.PERSON_UPI_COUNT) == null)
		{
			session.setAttribute(Constants.TREE_NODE_TYPE, Constants.LABEL_TREE_NODE);
			// Process label node clicked
			NodeId node = new NodeId(nodeId);
			String uniqueCurrentNodeId = node.getUniqueCurrentNodeId();
			Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session
					.getAttribute(Constants.ID_NODES_MAP);
			OutputTreeDataNode labelTreeDataNode = uniqueIdNodesMap.get(uniqueCurrentNodeId);
			IOutputEntity outputEntity = labelTreeDataNode.getOutputEntity();
			QueryableObjectInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
			QueryPrivilege privilege = getprivilege(session);

			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap = IQueryParseUtil
					.getParentChildrensForaMainNode(labelTreeDataNode);
			IQuery patientDataQuery = (IQuery) session.getAttribute(Constants.PATIENT_DATA_QUERY);
			QueryDetails queryDetails = getQueryDetailsObject(labelTreeDataNode, parentChildrenMap,
					patientDataQuery);

			AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
					.getDefaultViewIQueryGenerator();
			IQuery generatedQuery = queryGenerator.createIQueryForTreeView(queryDetails, privilege
					.isSecurePrivilege());
			// session.setAttribute("primaryKeyIndexList",
			// primaryKeyIndexesList);
			AbstractQueryUIManager abstractQueryUIManager = AbstractQueryUIManagerFactory
					.configureDefaultAbstractUIQueryManager(this.getClass(), request,
							generatedQuery);
			Long countQueryExecId = (Long) session.getAttribute(Constants.EXECUTION_ID_OF_QUERY);
			Long dataQueryExecId = getDataqueryExecutionId(node.getRootData(), countQueryExecId,
					abstractQueryUIManager);
			session.setAttribute(Constants.TREEVIEW_DQ_EXECUTION_ID, dataQueryExecId);
			session.setAttribute(Constants.ROOT_ENTITY, rootEntity);
			session.setAttribute(Constants.GENERATED_QUERY, generatedQuery);
			// ///////////
			responseString = Constants.RESPONSE_WAIT;
		}
		else
		{
			responseString = DataQueryResultStatus.NO_MORE_RECORDS.name();
		}
		return responseString;

	}

	/**Method to create QueryDetails object.
	 * @param labelTreeDataNode label node
	 * @param parentChildrenMap parent children map
	 * @param patientDataQuery data query
	 * @return queryDetails of type QueryDetails
	 */
	private QueryDetails getQueryDetailsObject(OutputTreeDataNode labelTreeDataNode,
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap,
			IQuery patientDataQuery)
	{
		QueryDetails queryDetails = new QueryDetails();
		queryDetails.setCurrentSelectedObject(labelTreeDataNode);
		queryDetails.setQuery(patientDataQuery);
		queryDetails.setParentChildrenMap(parentChildrenMap);
		return queryDetails;
	}

	/**Method to get privileges from session.
	 * @param session HttpSession object
	 * @return privilege of type QueryPrivilege
	 */
	private QueryPrivilege getprivilege(HttpSession session)
	{
		QueryPrivilege privilege = new QueryPrivilege();
		if (session.getAttribute(Constants.QUERY_PRIVILEGE) != null)
		{
			privilege = (QueryPrivilege) (session.getAttribute(Constants.QUERY_PRIVILEGE));
		}
		return privilege;
	}

	/** Method to start execution of data query.
	 * @param rootData root data of clicked node
	 * @param queryExecutionID count query execution id
	 * @param abstractQueryUIManager Query UI manager
	 * @return dataQueryExecId
	 * @throws QueryModuleException Query Module Exception
	 */
	private Long getDataqueryExecutionId(String rootData, Long queryExecutionID,
			AbstractQueryUIManager abstractQueryUIManager) throws QueryModuleException
	{
		Long dataQueryExecId;
		if (rootData.equalsIgnoreCase(Constants.NULL_ID))
		{
			dataQueryExecId = abstractQueryUIManager.executeDataQuery(queryExecutionID,
					ViewType.TREE_VIEW);
		}
		else
		{
			dataQueryExecId = abstractQueryUIManager.executeDataQuery(queryExecutionID, rootData,
					ViewType.TREE_VIEW);
		}
		return dataQueryExecId;
	}

	/**Method to process click on data node.
	 * @param nodeId clicked node id
	 * @param request HttpServletRequest object
	 * @throws QueryModuleException Query module exception
	 */
	private void processDataNodeClicked(String nodeId, HttpServletRequest request)
			throws QueryModuleException
	{
		HttpSession session = request.getSession();
		NodeId node = new NodeId(nodeId);
		String uniqueCurrentNodeId = node.getUniqueCurrentNodeId();
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = (Map) session
				.getAttribute(Constants.ID_NODES_MAP);
		OutputTreeDataNode labelTreeDataNode = uniqueIdNodesMap.get(uniqueCurrentNodeId);

		int rootNodeExpd = labelTreeDataNode.getExpressionId();
		IQuery patientDataQuery = (IQuery) session.getAttribute(Constants.PATIENT_DATA_QUERY);
		if (patientDataQuery != null)
		{
			IConstraints constraints = patientDataQuery.getConstraints();
			IExpression expression = constraints.getExpression(rootNodeExpd);
			QueryableObjectInterface entity = expression.getQueryEntity()
					.getDynamicExtensionsEntity();
			List<QueryableObjectInterface> mainEntityList = IQueryUpdationUtil
					.getAllMainObjects(patientDataQuery);

			// Check if the entity, for which children nodes are to be shown ,
			// is main entity or not
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap = IQueryParseUtil
					.getParentChildrensForaMainNode(labelTreeDataNode);
			if ((mainEntityList.contains(entity)) && (!parentChildrenMap.isEmpty()))
			{
				// For main OutputTreeDataNode, get the map of all
				// parent/children map
				// Set<OutputTreeDataNode> mainEntitiesKeySet =
				// parentChildrenMap.keySet();
				Map<String, List<OutputTreeDataNode>> parentChildrenListMap = createParentChildrenMap(
						nodeId, session, labelTreeDataNode, parentChildrenMap);
				session.setAttribute(Constants.CHILDRENLISTMAP, parentChildrenListMap);
			}
		}

	}

	/**Method to create map of parent vs its children.
	 * @param nodeId node id
	 * @param session HttpSession object
	 * @param labelTreeDataNode clicked node
	 * @param parentChildrenMap parent children map
	 * @return parentChildrenListMap
	 */
	private Map<String, List<OutputTreeDataNode>> createParentChildrenMap(String nodeId,
			HttpSession session, OutputTreeDataNode labelTreeDataNode,
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap)
	{
		List<OutputTreeDataNode> childrenList = parentChildrenMap.get(labelTreeDataNode);
		List<OutputTreeDataNode> tempChildrenList = new ArrayList<OutputTreeDataNode>();
		Iterator<OutputTreeDataNode> iter = childrenList.iterator();
		while (iter.hasNext())
		{
			tempChildrenList.add((OutputTreeDataNode) iter.next());
		}

		// this map is created for
		Map<String, List<OutputTreeDataNode>> parentChildrenListMap = (Map<String, List<OutputTreeDataNode>>) session
				.getAttribute("tempParentChildMap");

		if (parentChildrenListMap == null)
		{
			parentChildrenListMap = new HashMap<String, List<OutputTreeDataNode>>();
		}
		parentChildrenListMap.put(nodeId, tempChildrenList);
		return parentChildrenListMap;
	}

}
