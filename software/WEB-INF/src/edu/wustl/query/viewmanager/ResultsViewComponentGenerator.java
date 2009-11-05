
package edu.wustl.query.viewmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

public class ResultsViewComponentGenerator
{

	private final Set<IExpression> mainExpressions = new HashSet<IExpression>();
	private final IJoinGraph joinGraph;
	private final List<OutputTreeDataNode> rootOutputTreeNodeList = new ArrayList<OutputTreeDataNode>();
	private int treeNo;

	/**
	 * parameterized contructor 
	 * @param query query for which to build Tree
	 */
	public ResultsViewComponentGenerator(IQuery query)
	{
		joinGraph = query.getConstraints().getJoinGraph();
	}

	/**
	 * It will check weather the expression with the expresionId is weather contained expression
	 * or a main expression , if it is a main expression it will send the false  else will return true. 
	 * @param expressionId id of the expression
	 * @return true if contained Expression else false.
	 */
	private boolean isContainedExpresion(int expressionId)
	{
		boolean isMainExpression = true;
		for (IExpression exp : mainExpressions)
		{
			if (exp.getExpressionId() == expressionId)
			{
				isMainExpression = false;
				break;
			}
		}
		return isMainExpression;
	}

	/**
	 * populate the set of main expressions by.
	 * traversing expression tree recursively
	 * @param expression - IExpression
	 */
	private void setMainExpressions(IExpression expression)
	{
		List<QueryableObjectInterface> mainEntityList = new ArrayList<QueryableObjectInterface>();
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();

		IQueryUpdationUtil.getAllMainEntities(entity, mainEntityList);
		if (mainEntityList.contains(expression.getQueryEntity().getDynamicExtensionsEntity()))
		{
			mainExpressions.add(expression);
		}

		for (IExpression child : joinGraph.getChildrenList(expression))
		{
			setMainExpressions(child);
		}
	}

	/**
	 * To get the Output Entity for the given Expression.
	 * @param expression The reference to the Expression.
	 * @return The output entity for the Expression.
	 */
	private IOutputEntity getOutputEntity(IExpression expression)
	{
		QueryableObjectInterface entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		IOutputEntity outputEntity = QueryObjectFactory.createOutputEntity(entity);
		outputEntity.getSelectedAttributes().addAll(entity.getEntityAttributesForQuery());
		return outputEntity;
	}

	/**
	 * To create output tree for the given expression graph.
	 * @throws QueryModuleException 
	 * @throws MultipleRootsException When there exists multiple roots in
	 *             joingraph.
	 */
	public List<OutputTreeDataNode> getRootOutputTreeNode() throws QueryModuleException
	{
		Map<Integer, OutputTreeDataNode> outputTreeNodeMap = new HashMap<Integer, OutputTreeDataNode>();
		IExpression rootExpression;
		try
		{
			rootExpression = joinGraph.getRoot();
		}
		catch (MultipleRootsException e)
		{
			throw new QueryModuleException(Constants.QUERY_ERRORMSG + e.getMessage(), e,
					QueryModuleError.MULTIPLE_ROOT);
		}
		OutputTreeDataNode rootOutputTreeNode = null;
		boolean isContained = false;
		treeNo = 0;
		setMainExpressions(rootExpression);
		if (isContainedExpresion(rootExpression.getExpressionId()))
		{
			isContained = true;
		}
		if (rootExpression.isInView())
		{
			IOutputEntity rootOutputEntity = getOutputEntity(rootExpression);
			rootOutputTreeNode = new OutputTreeDataNode(rootOutputEntity, rootExpression
					.getExpressionId(), treeNo++, isContained);
			for (QueryableAttributeInterface attribute : rootOutputEntity.getSelectedAttributes())
			{
				addAttributeToNode(rootOutputTreeNode, attribute);
			}
			rootOutputTreeNodeList.add(rootOutputTreeNode);
			outputTreeNodeMap.put(rootExpression.getExpressionId(), rootOutputTreeNode);
		}
		completeTree(rootExpression, rootOutputTreeNode, outputTreeNodeMap);
		return rootOutputTreeNodeList;
		//setAllInSession();
	}

	/**
	 * It will add the new QueryOutputTreeAttributeMetadata to the rootOutputTreeNode.
	 * @param rootOutputTreeNode the node in which to add attribute
	 * @param attribute attribute to be added.
	 */
	private void addAttributeToNode(OutputTreeDataNode rootOutputTreeNode,
			QueryableAttributeInterface attribute)
	{
		String displayNameForColumn = Utility.getDisplayNameForColumn(attribute);
		rootOutputTreeNode.addAttribute(new QueryOutputTreeAttributeMetadata(attribute, "",
				rootOutputTreeNode, displayNameForColumn));
	}

	/**
	 * TO create the output tree from the constraints.
	 * @param expression The reference to Expression
	 * @param parentOutputTreeNode The reference to parent output tree node.
	 *            null if there is no parent.
	 * @param outputTreeNodeMap 
	 */
	private void completeTree(IExpression expression, OutputTreeDataNode parentOutputTreeNode,
			Map<Integer, OutputTreeDataNode> outputTreeNodeMap)
	{
		List<IExpression> children = joinGraph.getChildrenList(expression);
		boolean isContained;
		for (IExpression childExp : children)
		{
			OutputTreeDataNode childNode = parentOutputTreeNode;

			IOutputEntity childOutputEntity = getOutputEntity(childExp);
			//Integer childAliasAppender = aliasAppenderMap.get(childExp);

			//Set containment object to true if expression is contained.
			isContained = isContainedExpresion(childExp.getExpressionId());

			/**
			 * Check whether output tree node for expression with the same
			 * alias already added or not. if its not added then need to add
			 * it alias in the outputTreeNodeMap
			 */
			childNode = outputTreeNodeMap.get(childExp.getExpressionId());
			if (childNode == null)
			{
				childNode = setChildNode(parentOutputTreeNode, childOutputEntity, childExp,
						isContained);
				outputTreeNodeMap.put(childExp.getExpressionId(), childNode);
			}
			completeTree(childExp, childNode, outputTreeNodeMap);
		}
	}

	/**
	 * @param parentOutputTreeNode - OutputTreeNode
	 * @param childOutputEntity - OutputEntity
	 * @param childExp - child Expression
	 * @param isContained - whether the expression is contained
	 * @return - OutputTreeDataNode
	 */
	private OutputTreeDataNode setChildNode(OutputTreeDataNode parentOutputTreeNode,
			IOutputEntity childOutputEntity, IExpression childExp, boolean isContained)
	{
		OutputTreeDataNode childNode = parentOutputTreeNode;
		if (childNode == null)
		{
			// New root node for output tree found, so create root
			// node & add it in the rootOutputTreeNodeList.

			childNode = new OutputTreeDataNode(childOutputEntity, childExp.getExpressionId(),
					treeNo++, isContained);
			rootOutputTreeNodeList.add(childNode);
		}
		else
		{
			childNode = parentOutputTreeNode.addChild(childOutputEntity,
					childExp.getExpressionId(), isContained);
		}
		for (QueryableAttributeInterface attribute : childOutputEntity.getSelectedAttributes())
		{
			addAttributeToNode(childNode, attribute);
		}
		return childNode;
	}

	/**
	 * To get map of all Children nodes along with their ids under given output tree node.
	 * @param root The root noe of the output tree.
	 * @param map of all Children nodes along with their ids under given output tree node.
	 */
	private void addAllChildrenNodes(OutputTreeDataNode root, Map<String, OutputTreeDataNode> map)
	{
		map.put(root.getUniqueNodeId(), root);
		List<OutputTreeDataNode> children = root.getChildren();
		for (OutputTreeDataNode childNode : children)
		{
			addAllChildrenNodes(childNode, map);
		}
	}

	/**
	 * It returns all the nodes present all tress in results. 
	 * @param keys set of trees
	 * @return Map of uniqueNodeId and tree node
	 */
	public Map<String, OutputTreeDataNode> getAllChildrenNodes(List<OutputTreeDataNode> keys)
	{
		Map<String, OutputTreeDataNode> map = new HashMap<String, OutputTreeDataNode>();
		for (OutputTreeDataNode root : keys)
		{
			addAllChildrenNodes(root, map);
		}
		return map;
	}

}
