
package edu.wustl.query.flex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.metadata.path.Path;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.utils.ConstraintsObjectBuilder;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.query.flex.dag.CustomFormulaNode;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.flex.dag.DAGNode;
import edu.wustl.query.flex.dag.DAGPanel;
import edu.wustl.query.flex.dag.DAGPath;
import edu.wustl.query.flex.dag.SingleNodeCustomFormulaNode;

/**
 * This class act as an interface between Flex Client and Java
 * All flex client requests are handled through this class interface.
 * @author baljeet_dhindhwal
 *
 */
public class FlexInterface
{

	//--------------DAG-----------------------------
	/**
	 * Restores query object in DAG.
	 */
	public void restoreQueryObject()
	{
		if (dagPanel == null)
		{
			this.initFlexInterface();
		}
		else
		{
			dagPanel.restoreQueryObject();
		}
	}

	/**
	 * Add Nodes in define Result view.
	 * @param nodesStr node string
	 * @return Dag node
	 */
	public DAGNode addNodeToView(String nodesStr)
	{
		return dagPanel.addNodeToOutPutView(nodesStr);
	}

	/**
	 * Repaints DAG.
	 * @return map
	 */
	public Map<String, Object> repaintDAG()
	{
		return dagPanel.repaintDAG();
	}

	/**
	 * Gets search results.
	 * @return int
	 */
	public int getSearchResult()
	{
		return dagPanel.search();
	}

	/**
	 * create DAG Node.
	 * @param strToCreateQueryObject string to create query object
	 * @param entityName name of entity
	 * @return dag node
	 */
	public DAGNode createNode(String strToCreateQueryObject, String entityName)
	{
		return dagPanel.createQueryObject(strToCreateQueryObject, entityName, "Add");
	}

	/**
	 *
	 * @param expressionId expression id
	 * @param pageOf String
	 * @return html string
	 * @throws PVManagerException PV exception
	 */
	public String getLimitUI(int expressionId,String pageOf) throws PVManagerException
	{
		Map map = dagPanel.editAddLimitUI(expressionId,pageOf);
		String htmlStr = (String) map.get(DAGConstant.HTML_STR);
		IExpression expression = (IExpression) map.get(DAGConstant.EXPRESSION);
		dagPanel.setExpression(expression);
		return htmlStr;
	}

	/**
	 * Edit Node.
	 * @param strToCreateQueryObject string to create query object
	 * @param entityName name of entity
	 * @return dag node
	 */
	public DAGNode editNode(String strToCreateQueryObject, String entityName)
	{
		return dagPanel.createQueryObject(strToCreateQueryObject, entityName, "Edit");
	}

	/**
	 * Checks validity of nodes for query.
	 * @param linkedNodeList list og dag nodes
	 * @return error string.
	 */
	public String checkIfNodesAreValid(List<DAGNode> linkedNodeList)
	{
		String error = "";

		error = dagPanel.checkForValidAttributes(linkedNodeList);

		return error;
	}

	/**
	 * This method checks if single node TQ is valid.
	 * @param linkedNodeList list of dag nodes
	 * @return isNodeValid
	 */
	public boolean checkIfSingleNodeValid(List<DAGNode> linkedNodeList)
	{
		boolean isNodeValid = false;
		isNodeValid = dagPanel.checkForNodeValidAttributes(linkedNodeList.get(0));
		return isNodeValid;
	}


	/**
	 * This method returns the data related to single node Temporal Query
	 * to populate Single Node TQ pop up .
	 * @param linkedNodeList : List of selected DAG nodes
	 * @return Map containing data related to single node Temporal Query
	 */
	public Map<String, Object> getSingleNodeQueryDate(List<DAGNode> linkedNodeList)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		Map<String, Object> singleNodeDataMap = dagPanel.getSingleNodeQueryData
		(sourceNode.getExpressionId(),sourceNode.getNodeName());

		return singleNodeDataMap;
	}


	/**
	 * This method returns the data related to single node Temporal Query
	 * when Single node TQ is edited.
	 * @param customNode Single Node TQ that is to be edited
	 * @return returns the data related to single node Temporal Query
	 */
	public Map<String, Object> getSingleNodeEditData(SingleNodeCustomFormulaNode customNode)
	{
		Map<String, Object> singleNodeDataMap = dagPanel.getSingleNodeQueryData(customNode
				.getNodeExpressionId(), customNode.getEntityName());
		return singleNodeDataMap;
	}


	/**
	 * This method returns the data related to two node Temporal Query
	 * to populate two nodes TQ pop up.
	 * @param linkedNodeList List of selected DAG nodes
	 * @return map containing data related to two node Temporal Query
	 */
	public Map<String, Object> retrieveQueryData(List<DAGNode> linkedNodeList)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		DAGNode destinationNode = linkedNodeList.get(1);
		Map<String, Object> queryDataMap = dagPanel.getQueryData(sourceNode.getExpressionId(),
				destinationNode.getExpressionId(), sourceNode.getNodeName(), destinationNode
						.getNodeName());
		return queryDataMap;
	}

	/**
	 * This method returns the data related to single node Temporal Query
	 * when two node TQ is edited.
	 * @param customNode : Two node TQ to be edited
	 * @return : Map containing data related to two node Temporal Query.
	 */
	public Map<String, Object> retrieveEditQueryData(CustomFormulaNode customNode)
	{
		Map<String, Object> queryDataMap = dagPanel.getQueryData(customNode.getFirstNodeExpId(),
				customNode.getSecondNodeExpId(), customNode.getFirstNodeName(), customNode
						.getSecondNodeName());
		return queryDataMap;
	}

	/**
	 * This method removes the custom formula from query when
	 * a two node custom formula is deleted from query in dag.
	 * @param nodeID : Custom formula node Id to be removed
	 */
	public void removeCustomFormula(String nodeID)
	{
		dagPanel.removeCustomFormula(nodeID);
	}

	/**
	 * Deletes node from output view.
	 * @param expId : Expression id
	 */
	public void deleteFromView(int expId)
	{
		dagPanel.deleteExpressionFormView(expId);
	}

	/**
	 * Adds node to output view.
	 * @param expId : Expression ID
	 */
	public void addToView(int expId)
	{
		dagPanel.addExpressionToView(expId);
	}


	/**
	 * This method checks if node can be deleted in case of Data Query.
	 * @param expId : expression Id
	 * @return : corresponding message
	 */

  	public String isDeletableNode(int expId)
	{
	  return dagPanel.isDeletableNode(expId);
	}

	/**
	 * Deletes node from DAG.
	 * @param expId : Expression Id
	 */
	public void deleteNode(int expId)
	{
		 dagPanel.deleteExpression(expId);//delete Expression
	}

	/**
	 * Gets path List between nodes.
	 * @param linkedNodeList : Selected nodes from DAG
	 * @return List of paths between two selected nodes.
	 */
	private List<IPath> getPathList(List<DAGNode> linkedNodeList)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		DAGNode destinationNode = linkedNodeList.get(1);
		return dagPanel.getPaths(sourceNode, destinationNode);
	}

	/**
	 * Gets association(path) between 2 nodes.
	 * @param linkedNodeList : Selected nodes from DAG
	 * @return List of DAG paths
	 */
	public List<DAGPath> getpaths(List<DAGNode> linkedNodeList)
	{
		List<IPath> pathsList = getPathList(linkedNodeList);
		List<DAGPath> pathsListStr = new ArrayList<DAGPath>();
		for (int i = 0; i < pathsList.size(); i++)
		{
			Path tempPath = (Path) pathsList.get(i);
			DAGPath path = new DAGPath();
			path.setToolTip(DAGPanel.getPathDisplayString(pathsList.get(i)));
			path.setId(Long.valueOf(tempPath.getPathId()).toString());
			pathsListStr.add(path);
		}
		return pathsListStr;
	}

	/**
	 * This method forms the custom formula when two nodes tq condition is added on dag.
	 * @param customFormulaNode : Custom formula node object
	 * @param operation : add or edit operation
	 * @return : Custom formula node object
	 */
	public CustomFormulaNode formTemporalQuery(CustomFormulaNode customFormulaNode, String operation)
	{
		return dagPanel.formTemporalQuery(customFormulaNode, operation);
	}

	/**
	 * This method forms the custom formula when single node tq condition is added on dag.
	 * @param node : SingleNodeCustomFormulaNode node
	 * @param operation : add or edit operation
	 * @return SingleNodeCustomFormulaNode node
	 */
	public SingleNodeCustomFormulaNode formSingleNodeFormula(SingleNodeCustomFormulaNode node,
			String operation)
	{
		return dagPanel.formSingleNodeFormula(node, operation);
	}

	/**
	 * Links 2 nodes.
	 * @param linkedNodeList : Selected nodes from DAG
	 * @param selectedPaths : List of paths between selected nodes
	 * @return : List of paths between two selected nodes
	 */

	public List<DAGPath> linkNodes(List<DAGNode> linkedNodeList, List<DAGPath> selectedPaths)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		DAGNode destinationNode = linkedNodeList.get(1);
		List<IPath> pathsList = getPathList(linkedNodeList);
		List<IPath> selectedList = new ArrayList<IPath>();
		for (int j = 0; j < selectedPaths.size(); j++)
		{
			for (int i = 0; i < pathsList.size(); i++)
			{
				IPath path = pathsList.get(i);
				String pathStr = Long.valueOf(path.getPathId()).toString();
				DAGPath dagPath = selectedPaths.get(j);
				String pathId = dagPath.getId();
				if (pathStr.equals(pathId))
				{
					selectedList.add(path);
					break;
				}

			}
		}
		return dagPanel.linkNode(sourceNode, destinationNode, selectedList);
	}

	/**
	 * Deletes association between 2 nodes.
	 * @param linkedNodeList : Selected nodes from DAG
	 * @param linkName : Name of the link
	 */
	public void deleteLink(List<DAGNode> linkedNodeList, String linkName)
	{
		dagPanel.deletePath(linkName, linkedNodeList);
	}

	/**
	 * Sets logical operator set from UI.
	 * @param node : Corresponding edited DAG node
	 * @param operandIndex : Index of operand
	 * @param operator : Operator
	 */
	public void setLogicalOperator(DAGNode node, int operandIndex, String operator)
	{
		int parentExpId = node.getExpressionId();
		dagPanel.updateLogicalOperator(parentExpId, operandIndex, operator);
	}

	/**
	 *This method is always invoked when DAG is loaded
	 * hence initializes the DAG.
	 */
	public void initFlexInterface()
	{
		IConstraintsObjectBuilderInterface queryObject = new ConstraintsObjectBuilder();
		IPathFinder pathFinder = new CommonPathFinder();
		dagPanel = new DAGPanel(pathFinder);
		dagPanel.setQueryObject(queryObject);
	}

	/**
	 *  DagPanel Object.
	 */
	private DAGPanel dagPanel = null;
	//private HttpSession session = null;

	//----- END DAG -------
}
