
package edu.wustl.query.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.Constants;

/**
 * This is wrapper over a JGraphT graph library that we are using to form a
 * dependency graph. This is a generic graph which will have all the methods
 * related to a graph object.
 *
 * @author maninder_randhawa
 * @author Gaurav Sawant
 *
 */
public class DirectedGraph
{

	protected org.jgrapht.DirectedGraph<AbstractQuery, DefaultEdge> dependencyGraph;

	public DirectedGraph()
	{
		super();
		dependencyGraph = new DefaultDirectedGraph<AbstractQuery, DefaultEdge>(DefaultEdge.class);
	}

	public org.jgrapht.DirectedGraph<AbstractQuery, DefaultEdge> getDependencyGraph()
	{
		return dependencyGraph;
	}

	public void setDependencyGraph(
			org.jgrapht.DirectedGraph<AbstractQuery, DefaultEdge> dependencyGraph)
	{
		this.dependencyGraph = dependencyGraph;
	}

	/**
	 * This method returns all Composite queries in workflow graph.
	 * @return intermediateNodes
	 */
	public List<AbstractQuery> getAllIntermediateNodes()
	{
		Set<AbstractQuery> allNodes = dependencyGraph.vertexSet();
		List<AbstractQuery> intermediateNodes = new ArrayList<AbstractQuery>();
		for (AbstractQuery loopCntr : allNodes)
		{
			if (dependencyGraph.outDegreeOf(loopCntr) > 0)
			{
				intermediateNodes.add(loopCntr);
			}
		}
		return intermediateNodes;
	}

	/**
	 * This method returns all Parameterized queries in workflow graph.
	 * @return leafNodes
	 */
	public List<AbstractQuery> getAllLeafNodes()
	{
		Set<AbstractQuery> allNodes = dependencyGraph.vertexSet();
		List<AbstractQuery> leafNodes = new ArrayList<AbstractQuery>();
		for (AbstractQuery loopCntr : allNodes)
		{
			if (dependencyGraph.outDegreeOf(loopCntr) == 0)
			{
				leafNodes.add(loopCntr);
			}
		}
		return leafNodes;
	}

	/**
	 * This method returns all Parameterized queries in workflow graph.
	 * @return leafNodes
	 */
	public Set<Long> getQueryIdsOFAllLeafNodes()
	{
		Set<AbstractQuery> allNodes = dependencyGraph.vertexSet();
		Set<Long> leafNodes = new HashSet<Long>();
		for (AbstractQuery loopCntr : allNodes)
		{
			if (dependencyGraph.outDegreeOf(loopCntr) == 0
					&& Constants.QUERY_TYPE_GET_COUNT.equalsIgnoreCase(loopCntr.getQuery()
							.getType()))
			{
				leafNodes.add(loopCntr.getQuery().getId());
			}
		}
		return leafNodes;
	}

	/**
	 * This method returns the immediate child nodes for the given parent node.
	 * Note that this method returns just the immediate child nodes and not the
	 * entire subtree. If the given <code>parentQueryNode</code> is a leaf node
	 * then it will return an empty list
	 *
	 * @param parentQueryNode
	 *            The parent Query node.
	 * @return The List of nodes i.e. Left and Right node if the given
	 *         parentQueryNode is a CQ else will return an empty list
	 */
	public List<AbstractQuery> getChildNodes(AbstractQuery parentQueryNode)
	{
		List<AbstractQuery> leafNodes = new ArrayList<AbstractQuery>();
		if (dependencyGraph.outDegreeOf(parentQueryNode) > 0)
		{
			Set<DefaultEdge> allChildEdges = dependencyGraph.outgoingEdgesOf(parentQueryNode);
			for (DefaultEdge childEdge : allChildEdges)
			{
				AbstractQuery childQuery = dependencyGraph.getEdgeTarget(childEdge);
				leafNodes.add(childQuery);
			}
		}
		return leafNodes;
	}

	public Set<Long> getLeafNodes(AbstractQuery parentQueryNode, Set<Long> leafNodes)
	{

		if (dependencyGraph.outDegreeOf(parentQueryNode) > 0)
		{
			Set<DefaultEdge> allChildEdges = dependencyGraph.outgoingEdgesOf(parentQueryNode);
			for (DefaultEdge childEdge : allChildEdges)
			{

				AbstractQuery childQuery = dependencyGraph.getEdgeTarget(childEdge);
				getLeafNodes(childQuery, leafNodes);
			}
		}
		else
		{
			leafNodes.add(parentQueryNode.getQuery().getId());
		}
		return leafNodes;
	}

	public Set<Long> getAllNotExecutedLeafNodes(AbstractQuery parentQueryNode, Set<Long> leafNodes)
			throws SQLException, DAOException
	{
		Set<Long> notExecutedLeafNodes = new HashSet<Long>();
		if (parentQueryNode.getQuery() instanceof ParameterizedQuery)
		{
			notExecutedLeafNodes.add(parentQueryNode.getQuery().getId());
			return notExecutedLeafNodes;
		}
		else
		{
			ITableManager itableManager = ITableManagerFactory.getDefaultITableManager();

			Set<Long> executedLeafNodes; //= new HashSet<Long>();
			getLeafNodes(parentQueryNode, leafNodes);
			executedLeafNodes = itableManager.preExecutedQueries(
					parentQueryNode.getQuery().getId(), parentQueryNode.getUserId(),
					parentQueryNode.getWorkFlowId(), ((CiderQuery) parentQueryNode).getProjectId());
			for (Long parametrizedQuery : leafNodes)
			{
				if (!executedLeafNodes.contains(parametrizedQuery))
				{
					notExecutedLeafNodes.add(parametrizedQuery);
				}
			}
		}
		return notExecutedLeafNodes;
	}

	/**
	 * This method returns all the child nodes for the given parent node. Note
	 * that this method returns all the child nodes by visiting the left and
	 * right subtree.If the given <code>parentQueryNode</code> is a leaf node
	 * then it will return an empty list.
	 *
	 * @param parentQueryNode
	 *            The parent Query node.
	 * @return The List of all the child nodes if the given parentQueryNode is a
	 *         CQ else will return an empty list
	 */
	public List<AbstractQuery> getAllChildNodes(AbstractQuery parentQueryNode)
	{
		List<AbstractQuery> leafNodes = new ArrayList<AbstractQuery>();
		leafNodes.addAll(getChildNodes(parentQueryNode));
		List<AbstractQuery> childLeafNodes = new ArrayList<AbstractQuery>();
		for (AbstractQuery childQuery : leafNodes)
		{
			childLeafNodes.addAll(getAllChildNodes(childQuery));
		}
		leafNodes.addAll(childLeafNodes);
		return leafNodes;
	}

	/**
	 * This methods returns execution Id List of all
	 * dependent queries of a Composite Query.
	 * @param query
	 * @return execIdList
	 */
	public List<Long> getOutgoingEdgesExecIdList(AbstractQuery query)
	{

		DefaultDirectedGraph<AbstractQuery, DefaultEdge> graph = (DefaultDirectedGraph<AbstractQuery, DefaultEdge>) getDependencyGraph();
		Set<DefaultEdge> edges = graph.outgoingEdgesOf(query);
		List<Long> execIdList = new ArrayList<Long>();
		Iterator<DefaultEdge> iter = edges.iterator();
		while (iter.hasNext())
		{
			AbstractQuery tempQuery = graph.getEdgeTarget(iter.next());
			execIdList.add(tempQuery.getQueryExecId());
		}
		return execIdList;
	}

	public String generateSQL(AbstractQuery query)
	{
		return null;
	}
}
