
package edu.wustl.query.flex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.cde.CDE;
import edu.wustl.common.cde.CDEManager;
import edu.wustl.common.cde.PermissibleValue;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.metadata.path.Path;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.util.global.Constants;
import edu.wustl.query.flex.dag.CustomFormulaNode;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.flex.dag.DAGNode;
import edu.wustl.query.flex.dag.DAGPanel;
import edu.wustl.query.flex.dag.DAGPath;
import edu.wustl.query.flex.dag.JoinQueryNode;
import edu.wustl.query.flex.dag.SingleNodeCustomFormulaNode;

public class FlexInterface
{

	public FlexInterface()
	{

	}

	private List<String> toStrList(List<NameValueBean> nvBeanList)
	{
		List<String> strList = new ArrayList<String>();
		for (NameValueBean bean : nvBeanList)
		{
			strList.add(bean.getName());
		}
		return strList;
	}

	public List<String> getTissueSidePVList()
	{
		List<NameValueBean> aList = CDEManager.getCDEManager().getPermissibleValueList(
				"Tissue Side", null);
		return toStrList(aList);
	}

	public List<String> getPathologicalStatusPVList()
	{
		List<NameValueBean> aList = CDEManager.getCDEManager().getPermissibleValueList(
				"Pathological Status", null);
		return toStrList(aList);
	}

	public List<String> getSpecimenClassStatusPVList()
	{
		Map specimenTypeMap = getSpecimenTypeMap();
		Set specimenKeySet = specimenTypeMap.keySet();
		List<NameValueBean> specimenClassList = new ArrayList<NameValueBean>();

		Iterator itr1 = specimenKeySet.iterator();
		while (itr1.hasNext())
		{
			String specimenKey = (String) itr1.next();
			specimenClassList.add(new NameValueBean(specimenKey, specimenKey));
		}
		return toStrList(specimenClassList);
	}

	public List<String> getFluidSpecimenTypePVList()
	{
		Map specimenTypeMap = getSpecimenTypeMap();
		List<NameValueBean> aList = (List) specimenTypeMap.get("Fluid");
		return toStrList(aList);
	}

	public List<String> getTissueSpecimenTypePVList()
	{
		Map specimenTypeMap = getSpecimenTypeMap();
		List<NameValueBean> aList = (List) specimenTypeMap.get("Tissue");
		return toStrList(aList);
	}

	public List<String> getMolecularSpecimenTypePVList()
	{
		Map specimenTypeMap = getSpecimenTypeMap();
		List<NameValueBean> aList = (List<NameValueBean>) specimenTypeMap.get("Molecular");
		return toStrList(aList);
	}

	public List<String> getCellSpecimenTypePVList()
	{
		Map specimenTypeMap = getSpecimenTypeMap();
		List<NameValueBean> aList = (List<NameValueBean>) specimenTypeMap.get("Cell");
		return toStrList(aList);
	}

	private Map getSpecimenTypeMap()
	{
		CDE specimenClassCDE = CDEManager.getCDEManager().getCDE("Specimen");
		Set setPV = specimenClassCDE.getPermissibleValues();
		Iterator itr = setPV.iterator();

		//List specimenClassList = CDEManager.getCDEManager().getPermissibleValueList("Specimen", null);
		Map<String, List> subTypeMap = new HashMap<String, List>();
		//specimenClassList.add(new NameValueBean(Constants.SELECT_OPTION, "-1"));

		while (itr.hasNext())
		{
			List<NameValueBean> innerList = new ArrayList<NameValueBean>();
			Object obj = itr.next();
			PermissibleValue pValue = (PermissibleValue) obj;
			//String tmpStr = pv.getValue();
			//specimenClassList.add(new NameValueBean(tmpStr, tmpStr));

			Set list1 = pValue.getSubPermissibleValues();
			Iterator itr1 = list1.iterator();
			innerList.add(new NameValueBean(Constants.SELECT_OPTION, "-1"));

			while (itr1.hasNext())
			{
				Object obj1 = itr1.next();
				PermissibleValue pv1 = (PermissibleValue) obj1;
				//Setting Specimen Type
				String tmpInnerStr = pv1.getValue();
				innerList.add(new NameValueBean(tmpInnerStr, tmpInnerStr));
			}

			subTypeMap.put(pValue.getValue(), innerList);
		}
		//System.out.println("subTypeMap "+subTypeMap);
		return subTypeMap;
	}

	public List getSpecimenTypeStatusPVList()
	{
		return CDEManager.getCDEManager().getPermissibleValueList("Specimen Type", null);
	}

	public List getSCGList()
	{
		return null;
	}

	//--------------DAG-----------------------------
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
	 * Add Nodes in define Result view
	 * @param nodesStr
	 * @return
	 */
	public DAGNode addNodeToView(String nodesStr)
	{
		DAGNode dagNode = dagPanel.addNodeToOutPutView(nodesStr);
		return dagNode;
	}

	/**
	 * Repaints DAG
	 * @return
	 */
	public Map<String, Object> repaintDAG()
	{
		return dagPanel.repaintDAG();
	}

	public int getSearchResult()
	{
		return dagPanel.search();
	}

	/**
	 * create DAG Node
	 * @param strToCreateQueryObject
	 * @param entityName
	 */
	public DAGNode createNode(String strToCreateQueryObject, String entityName)
	{
		DAGNode dagNode = dagPanel.createQueryObject(strToCreateQueryObject, entityName, "Add",null,null);
		return dagNode;
	}

	/**
	 *
	 * @param expressionId
	 * @return
	 */
	public String getLimitUI(int expressionId)
	{
		Map map = dagPanel.editAddLimitUI(expressionId);
		String htmlStr = (String) map.get(DAGConstant.HTML_STR);
		IExpression expression = (IExpression) map.get(DAGConstant.EXPRESSION);
		dagPanel.setExpression(expression);
		return htmlStr;
	}

	/**
	 * Edit Node
	 * @param strToCreateQueryObject
	 * @param entityName
	 * @return
	 */
	public DAGNode editNode(String strToCreateQueryObject, String entityName)
	{
		DAGNode dagNode = dagPanel.createQueryObject(strToCreateQueryObject, entityName, "Edit",null,null);
		return dagNode;
	}

	/**
	 * Checks validity of nodes for query
	 * @param linkedNodeList
	 * @return True if both nodes have any of the attribute as Date, else False
	 */
	public boolean checkIfNodesAreValid(List<DAGNode> linkedNodeList, Boolean isJoinQueryButtonSelected)
	{
		boolean areNodesValid = false;
		if(!isJoinQueryButtonSelected)
		{
			areNodesValid = dagPanel.checkForValidAttributes(linkedNodeList);
		}
		return areNodesValid;
	}

	public boolean checkIfSingleNodeValid(List<DAGNode> linkedNodeList)
	{
		boolean isNodeValid = false;
		isNodeValid = dagPanel.checkForNodeValidAttributes(linkedNodeList.get(0));
		return isNodeValid;
	}

	public Map getSingleNodeQueryDate(List<DAGNode> linkedNodeList)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		Map singleNodeDataMap = dagPanel.getSingleNodeQueryData(sourceNode.getExpressionId(),
				sourceNode.getNodeName());

		return singleNodeDataMap;
	}

	public Map getSingleNodeEditData(SingleNodeCustomFormulaNode customNode)
	{
		Map singleNodeDataMap = dagPanel.getSingleNodeQueryData(customNode.getNodeExpressionId(),
				customNode.getEntityName());
		return singleNodeDataMap;
	}

	public Map retrieveQueryData(List<DAGNode> linkedNodeList, boolean isJoinQueryButtonSelected)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		DAGNode destinationNode = linkedNodeList.get(1);
		Map queryDataMap = dagPanel.getQueryData(sourceNode.getExpressionId(), destinationNode
				.getExpressionId(), sourceNode.getNodeName(), destinationNode.getNodeName(),
				isJoinQueryButtonSelected);
		return queryDataMap;
	}

	public Map retrieveEditQueryData(CustomFormulaNode customNode)
	{
		Map queryDataMap = dagPanel.getQueryData(customNode.getFirstNodeExpId(), customNode
				.getSecondNodeExpId(), customNode.getFirstNodeName(), customNode
				.getSecondNodeName(), false);
		return queryDataMap;
	}

	public Map retrieveEditJoinQueryData(JoinQueryNode joinQueryNode)
	{
		Map queryDataMap = dagPanel.getQueryData(joinQueryNode.getFirstNodeExpressionId(), joinQueryNode
				.getSecondNodeExpressionId(), joinQueryNode.getFirstEntityName(), joinQueryNode
				.getSecondEntityName(), true);
		return queryDataMap;
	}

	public void removeCustomFormula(String nodeID)
	{
		dagPanel.removeCustomFormula(nodeID);
	}

	public void removeJoinFormula(String joinQueryNodeID)
	{
		dagPanel.removeJoinFormula(joinQueryNodeID);
	}

	public List getFormattedLabelForJQ(JoinQueryNode joinQueryNode)
	{
		return dagPanel.getFormattedLabelForJQ(joinQueryNode);
	}

	/**
	 * Deletes node from output view
	 * @param expId
	 */
	public void deleteFromView(int expId)
	{
		dagPanel.deleteExpressionFormView(expId);
	}

	/**
	 * Adds node to output view
	 * @param expId
	 */
	public void addToView(int expId)
	{
		dagPanel.addExpressionToView(expId);
	}

	/**
	 * Deletes node from DAG
	 * @param expId
	 */
	public void deleteNode(int expId)
	{
		dagPanel.deleteExpression(expId);//delete Expression
	}

	/**
	 * Gets path List between nodes
	 * @param linkedNodeList
	 * @return
	 */
	private List<IPath> getPathList(List<DAGNode> linkedNodeList)
	{
		DAGNode sourceNode = linkedNodeList.get(0);
		DAGNode destinationNode = linkedNodeList.get(1);
		List<IPath> pathsList = dagPanel.getPaths(sourceNode, destinationNode);
		return pathsList;
	}

	/**
	 * Gets association(path) between 2 nodes
	 * @param linkedNodeList
	 * @return
	 */
	public List getpaths(List<DAGNode> linkedNodeList)
	{
		List<IPath> pathsList = getPathList(linkedNodeList);
		List<DAGPath> pathsListStr = new ArrayList<DAGPath>();
		for (int i = 0; i < pathsList.size(); i++)
		{
			Path dagPath = (Path) pathsList.get(i);
			DAGPath path = new DAGPath();
			path.setToolTip(DAGPanel.getPathDisplayString(pathsList.get(i)));
			path.setId(Long.valueOf(dagPath.getPathId()).toString());
			pathsListStr.add(path);
		}
		return pathsListStr;
	}

	/**
	 * Gets association(path) between 2 nodes
	 * @param linkedNodeList
	 * @return
	 */
	public CustomFormulaNode formTemporalQuery(CustomFormulaNode customFormulaNode, String operation)
	{
		return dagPanel.formTemporalQuery(customFormulaNode, operation);
	}

	/**
	 *
	 * @param customFormulaNodeList
	 * @param operation
	 * @return
	 */
	public JoinQueryNode formJoinQuery(JoinQueryNode node, String operation)
	{
		return dagPanel.formJoinQuery(node, operation);
	}

	public SingleNodeCustomFormulaNode formSingleNodeFormula(SingleNodeCustomFormulaNode node,
			String operation)
	{
		return dagPanel.formSingleNodeFormula(node, operation);
	}

	/**
	 * Links 2 nodes
	 * @param linkedNodeList
	 * @param selectedPaths
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
	 * Deletes association between 2 nodes
	 * @param linkedNodeList
	 * @param linkName
	 */
	public void deleteLink(List<DAGNode> linkedNodeList, String linkName)
	{
		dagPanel.deletePath(linkName, linkedNodeList);
	}

	/**
	 * Sets logical operator set from UI
	 * @param node
	 * @param operandIndex
	 * @param operator
	 */
	public void setLogicalOperator(DAGNode node, int operandIndex, String operator)
	{
		int parentExpId = node.getExpressionId();
		dagPanel.updateLogicalOperator(parentExpId, operandIndex, operator);
	}

	/**
	 *Initializes DAG
	 *
	 */
	public void initFlexInterface()
	{
		queryObject = new ClientQueryBuilder();
		IPathFinder pathFinder = new CommonPathFinder();
		dagPanel = new DAGPanel(pathFinder);
		dagPanel.setQueryObject(queryObject);
	}

	private IClientQueryBuilderInterface queryObject = null;
	private DAGPanel dagPanel = null;
	//private HttpSession session = null;

	//----- END DAG -------
}