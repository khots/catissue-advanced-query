package edu.wustl.query.querysuite;

import java.util.Date;
import java.util.List;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.viewmanager.ResultsViewComponentGenerator;


public class ResultsViewComponentGeneratorTestCases extends QueryBaseTestCases
{
	public ResultsViewComponentGeneratorTestCases()
	{
		super();
	}
	
	public void testCreateTree() 
	{
		try
		{
		IParameterizedQuery query = null;
		SessionDataBean sessionData = getSessionData();
		query = QueryBuilder.skeletalRaceGenderAddressQuery();
		String queryName = "Query" + (new Date()).getTime();
		query.setName(queryName);
		query.setCreatedBy(sessionData.getUserId());
		query.setCreatedDate(new Date());
		query.setUpdatedBy(sessionData.getUserId());
		query.setUpdationDate(new Date());
		ResultsViewComponentGenerator treeUtil = new ResultsViewComponentGenerator(query);
		treeUtil.getRootOutputTreeNode();
		}
		catch (Exception e) {
			fail();
		}

		
	}
	
	public void testGetUniqueNodeIdMap() 
	{
		try
		{
		IParameterizedQuery query = null;
		SessionDataBean sessionData = getSessionData();
		query = QueryBuilder.skeletalRaceGenderAddressQuery();
		String queryName = "Query" + (new Date()).getTime();
		query.setName(queryName);
		query.setCreatedBy(sessionData.getUserId());
		query.setCreatedDate(new Date());
		query.setUpdatedBy(sessionData.getUserId());
		query.setUpdationDate(new Date());
		ResultsViewComponentGenerator treeUtil = new ResultsViewComponentGenerator(query);
		List<OutputTreeDataNode> rootOutputTreeNode = treeUtil.getRootOutputTreeNode();
		treeUtil.getAllChildrenNodes(rootOutputTreeNode);
		}
		catch (Exception e) 
		{
			fail();
		}

		
	}
}
