package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.query.actionForm.CategorySearchForm;
import edu.wustl.query.bizlogic.QueryOutputTreeBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryDetails;

/**
 * This class is invoked when user clicks on a node from the tree. It loads the
 * data required for tree formation.
 *
 * @author deepti_shelar
 *
 */
public class BuildQueryOutputTreeAction extends Action
{

	/**
	 * This method loads the data required for Query Output tree. With the help
	 * of QueryOutputTreeBizLogic it generates a string which will be then
	 * passed to client side and tree is formed accordingly.
	 *
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws Exception
	 *             Exception
	 * @return ActionForward actionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		HttpSession session = request.getSession();
		QueryDetails queryDetailsObj = new QueryDetails(session);
		boolean hasConditionOnIdentifiedField = (Boolean) session
				.getAttribute(AQConstants.HAS_CONDN_ON_ID_FIELD);
		CategorySearchForm actionForm = (CategorySearchForm) form;
		String outputTreeStr = "";
		String nodeId = actionForm.getNodeId();
		QueryOutputTreeBizLogic outputTreeBizLogic = new QueryOutputTreeBizLogic();
		String actualParentNodeId = nodeId.substring(nodeId
				.lastIndexOf(AQConstants.NODE_SEPARATOR) + 2, nodeId.length());
		String[] nodeIds = actualParentNodeId.split(AQConstants.UNDERSCORE);
		String treeNo = nodeIds[0];
		String treeNodeId = nodeIds[1];
		String uniqueId = treeNo + "_" + treeNodeId;
		OutputTreeDataNode parentNode = queryDetailsObj.getUniqueIdNodesMap()
				.get(uniqueId);
		if (nodeId.endsWith(AQConstants.LABEL_TREE_NODE))
		{
			outputTreeStr = outputTreeBizLogic.updateTreeForLabelNode(nodeId,
					queryDetailsObj, hasConditionOnIdentifiedField);
		} else
		{
			String data = nodeIds[2];
			outputTreeStr = outputTreeBizLogic.updateTreeForDataNode(nodeId,
					parentNode, data, queryDetailsObj);
		}
		response.setContentType("text/html");
		response.getWriter().write(outputTreeStr);
		return null;
	}
}
