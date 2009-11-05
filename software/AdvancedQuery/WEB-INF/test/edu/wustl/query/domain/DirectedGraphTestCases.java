package edu.wustl.query.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import edu.wustl.cider.query.CiderGraph;
import edu.wustl.cider.query.CiderQuery;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Operation;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;


/**
 * @author vijay_pande
 *
 */
public class DirectedGraphTestCases extends TestCase
{
	
	public void testGetQueryIdsOFAllLeafNodes()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			Set<Long> queryIds =  dependencyGraph.getQueryIdsOFAllLeafNodes();
			if(queryIds!=null && queryIds.size()==2)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetAllIntermediateNodes()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			List<AbstractQuery> queryList =  dependencyGraph.getAllIntermediateNodes();
			if(queryList!=null && queryList.size()==1)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetChildNodes()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			query1.setQuery(getCompositeQuery());
			query1.setWorkFlowId(1l);
			query1.setProjectId(1l);
			List<AbstractQuery> queryList =  dependencyGraph.getChildNodes(query1);
			if(queryList!=null && queryList.size()==2)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetLeafNodes()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			query1.setQuery(getCompositeQuery());
			query1.setWorkFlowId(1l);
			query1.setProjectId(1l);
			Set<Long> queryIds = new HashSet<Long>();
			queryIds =  dependencyGraph.getLeafNodes(query1, queryIds);
			if(queryIds!=null && queryIds.size()==2)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetAllLeafNodes()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			List<AbstractQuery> queryList = dependencyGraph.getAllLeafNodes();
			if(queryList!=null && queryList.size()==2)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetAllNotExecutedLeafNodes()
	{
		try
		{
			Variables.queryITableManagerClassName = "edu.wustl.common.query.itablemanager.ITableManager";
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			query1.setQuery(getCompositeQuery());
			query1.setWorkFlowId(1l);
			query1.setProjectId(1l);
			
			Set<Long> queryIds = new HashSet<Long>();
			queryIds =  dependencyGraph.getAllNotExecutedLeafNodes(query1, queryIds);
			if(queryIds!=null && queryIds.size()==2)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	public void testGetAllNotExecutedLeafNodesForPQ()
	{
		try
		{
			Variables.queryITableManagerClassName = "edu.wustl.common.query.itablemanager.ITableManager";
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			ParameterizedQuery query = new ParameterizedQuery();
			query.setName("PQ1 for workflow");
			query.setCreatedDate(new Date());
			query.setCreatedBy(1L);
			query.setUpdationDate(new Date());
			query.setUpdatedBy(1L);
			query.setId(1L);
			query.setType(Constants.QUERY_TYPE_GET_COUNT);
			query1.setQuery(query);
			query1.setWorkFlowId(1l);
			query1.setProjectId(1l);
			
			Set<Long> queryIds = new HashSet<Long>();
			queryIds =  dependencyGraph.getAllNotExecutedLeafNodes(query1, queryIds);
		
				assert(true);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetAllChildNodes()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			query1.setQuery(getCompositeQuery());
			query1.setWorkFlowId(1l);
			query1.setProjectId(1l);
			List<AbstractQuery> queryList = dependencyGraph.getAllChildNodes(query1);
			if(queryList!=null && queryList.size()==2)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetOutgoingEdgesExecIdList()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			query1.setQuery(getCompositeQuery());
			query1.setWorkFlowId(1l);
			query1.setProjectId(1l);
			List<Long> queryIds =  dependencyGraph.getOutgoingEdgesExecIdList(query1);
			if(queryIds==null || queryIds.contains(null))
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGenrateSQL()
	{
		try
		{
			DirectedGraph dependencyGraph = getDependancyGraph();
			CiderQuery query1 = new CiderQuery();
			query1.setQuery(getCompositeQuery());
			if(dependencyGraph.generateSQL(query1) == null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	private DirectedGraph getDependancyGraph()
	{
		ICompositeQuery compositeQuery = getCompositeQuery();
		DirectedGraph dependencyGraph = new DirectedGraph();
		CiderQuery query1 = new CiderQuery();
		query1.setQuery(getCompositeQuery());
		query1.setWorkFlowId(1l);
		query1.setProjectId(1l);
		
		CiderQuery query2 =  new CiderQuery();
		query2.setQuery(compositeQuery.getOperation().getOperandOne());
		query2.setWorkFlowId(1l);
		query2.setProjectId(1l);
		
		CiderQuery query3 =  new CiderQuery();
		query3.setQuery(compositeQuery.getOperation().getOperandTwo());
		query3.setWorkFlowId(1l);
		query3.setProjectId(1l);
		
		dependencyGraph.getDependencyGraph().addVertex(query1);
		dependencyGraph.getDependencyGraph().addVertex(query2);
		dependencyGraph.getDependencyGraph().addVertex(query3);
		dependencyGraph.getDependencyGraph().addEdge(query1, query2);
		dependencyGraph.getDependencyGraph().addEdge(query1, query3);
		return dependencyGraph;
	}
	
	private ICompositeQuery getCompositeQuery()
	{
		ParameterizedQuery queryOperandOne = new ParameterizedQuery();
		queryOperandOne.setName("PQ1 for workflow");
		queryOperandOne.setCreatedDate(new Date());
		queryOperandOne.setCreatedBy(1L);
		queryOperandOne.setUpdationDate(new Date());
		queryOperandOne.setUpdatedBy(1L);
		queryOperandOne.setId(1L);
		queryOperandOne.setType(Constants.QUERY_TYPE_GET_COUNT);
		
		ParameterizedQuery queryOperandTwo = new ParameterizedQuery();
		queryOperandTwo.setName("PQ2 for workflow");
		queryOperandTwo.setCreatedDate(new Date());
		queryOperandTwo.setCreatedBy(1L);
		queryOperandTwo.setUpdationDate(new Date());
		queryOperandTwo.setUpdatedBy(1L);
		queryOperandTwo.setId(2L);
		queryOperandTwo.setType(Constants.QUERY_TYPE_GET_COUNT);
		
		IAbstractQuery query = new CompositeQuery();
		Operation operation = new Union();
		operation.setOperandOne(queryOperandOne);
		operation.setOperandTwo(queryOperandTwo);
		query.setId(3L);
		query.setType(Constants.QUERY_TYPE_GET_COUNT);
		
		((CompositeQuery)query).setOperation(operation);
		
		return ((CompositeQuery)query);
	}
	public void testSetDependencyGraph()
	{
		try
		{
			DirectedGraph dependancyGraph = getDependancyGraph();
		
			dependancyGraph.setDependencyGraph(dependancyGraph.getDependencyGraph());

				assert(true);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
			
	}
	
}
