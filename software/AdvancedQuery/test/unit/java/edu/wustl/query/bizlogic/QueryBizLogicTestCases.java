package edu.wustl.query.bizlogic;

import java.util.Date;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.querymanager.CiderQueryManager;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;

/**
 * @author vijay_pande
 *
 */
public class QueryBizLogicTestCases extends QueryBaseTestCases
{
	public QueryBizLogicTestCases()
	{
		super();
	}
	
	static{
		 // Utility.initTest();
		/**
		 * Indicating - Do not LOG XQueries
		 */
		Variables.isExecutingTestCase = true;
	        Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
	}

	public void testInsertQueryWithoutDate()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query from test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), false);
			fail();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue("Query can not be inserted as date not set ",true);
		}
	}
	
	public void testInsertQueryWithProtectionElement()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query from test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setCreatedDate(new Date());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdationDate(new Date());
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), false);
			assertTrue("Query inserted successfully with protection element ",true);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testUpdateQuery()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query from test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setCreatedDate(new Date());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdationDate(new Date());
		String updatedTitle = "This query is updated by test case";
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), false);
			IParameterizedQuery insertedQuery = (ParameterizedQuery) queryBizLogic.retrieve(ParameterizedQuery.class.getName(), parameterizedQuery.getId());
			insertedQuery.setName(updatedTitle);
			queryBizLogic.updateSavedQueries(insertedQuery, getSessionData(), true);
			IParameterizedQuery updatedQuery = (ParameterizedQuery) queryBizLogic.retrieve(ParameterizedQuery.class.getName(), parameterizedQuery.getId());
			if(updatedTitle.equals(updatedQuery.getName()))
			{
				assertTrue("Query updated successfully with protection element ",true);
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
	
	public void testIsSharedQuery()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query from test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setCreatedDate(new Date());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdationDate(new Date());
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), true);
			IParameterizedQuery sharedQuery = (ParameterizedQuery) queryBizLogic.retrieve(ParameterizedQuery.class.getName(), parameterizedQuery.getId());
			
			if(queryBizLogic.isSharedQuery(sharedQuery))
			{
				assertTrue("Query is shared to all users",true);
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
	
	public void testIsSharedQueryForUnsavedQuery()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query from test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setCreatedDate(new Date());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdationDate(new Date());
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			IParameterizedQuery sharedQuery = (ParameterizedQuery) queryBizLogic.retrieve(ParameterizedQuery.class.getName(), parameterizedQuery.getId());
			
			queryBizLogic.isSharedQuery(sharedQuery);
			
				fail();
				assertFalse("Query is shared to all users",false);
			
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			assertTrue("Query is shared to all users",true);
		}
	}
	
	public void testGetQuery()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query to test  'testGetQuery' test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setCreatedDate(new Date());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdationDate(new Date());
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), false);
			IQuery insertedQuery = queryBizLogic.getQuery(parameterizedQuery.getId());
			if(insertedQuery != null)
			{
				assertTrue("Query retrieved successfully ",true);
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
	
	public void testSetSharingStatus()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query to test  'testGetQuery' test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		parameterizedQuery.setCreatedBy(getSessionData().getUserId());
		parameterizedQuery.setCreatedDate(new Date());
		parameterizedQuery.setUpdatedBy(getSessionData().getUserId());
		parameterizedQuery.setUpdationDate(new Date());
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), false);
			IParameterizedQuery insertedQuery = (IParameterizedQuery)queryBizLogic.getQuery(parameterizedQuery.getId());
			QuerySharingStatus querySharingStatus = queryBizLogic.getSharingStatus(insertedQuery, getSessionData().getUserId());
			if(querySharingStatus==QuerySharingStatus.NOT_SHARED_NOT_USED)
			{
				assertTrue("Status retrieved successfully", true);
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
	
	public void testExecuteQueryAndGetCount()
	{
		CiderQueryManager manager = new CiderQueryManager();
		try
		{
			Long queryExecId = executeCountQuery();

			Count count = manager.getQueryCount(queryExecId);
			System.out.println("Count >>>>> :: " + count.getCount());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	private Long executeCountQuery() throws Exception, MultipleRootsException, SqlException,
			QueryModuleException
	{
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
		CiderQueryPrivilege privilege = new CiderQueryPrivilege();
		CiderQueryManager manager = new CiderQueryManager();
		IParameterizedQuery query = getCountQuery();
		CiderQuery ciderQueryObj = new CiderQuery(query, -1L, null, -1L, null, "10.88.199.224",
				privilege);

		Long queryExecId = manager.execute(ciderQueryObj);
		
		for (int i = 0; i < 20000; i++)
		{
			// do nothing
		}
		
		return queryExecId;
	}

	public void testExecuteDataQuery()
	{
		Long countQueryExecId = 1L;
		CiderQueryManager manager = new CiderQueryManager();
		try
		{
			CiderQueryPrivilege privilege = new CiderQueryPrivilege();
			countQueryExecId = executeCountQuery();
			
			Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
			
			CiderQuery ciderQueryObj = new CiderQuery(getDataQuery(), -1L, null, -1L, null,
					"10.88.199.224", privilege);
			Long dataQueryExecutionId = manager.execute(ciderQueryObj, null,
					countQueryExecId, ViewType.SPREADSHEET_VIEW);
			System.out.println("Data Query Execution Id >>>>>> :: "
					+ dataQueryExecutionId);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public IParameterizedQuery getCountQuery() throws Exception
	{
		IParameterizedQuery query = new ParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		query.setConstraints(constraints);
		query.setName("TestCountQuery");
		query.setType(QueryType.GET_COUNT.type);
		query.setCreatedBy(getSessionData().getUserId());
		query.setCreatedDate(new Date());
		query.setUpdationDate(new Date());
		query.setUpdatedBy(getSessionData().getUserId());


		IExpression personEx = QueryBuilder.createExpression(constraints, null,
				"Person", false);
		QueryBuilder.addCondition(personEx, "personUpi", RelationalOperator.EndsWith, "7");

		return (IParameterizedQuery) query;
	}

	public IParameterizedQuery getDataQuery() throws Exception
	{
		IParameterizedQuery query = null;
		query = QueryBuilder.skeletalDemograpihcsQuery();
		query.setCreatedBy(getSessionData().getUserId());
		query.setCreatedDate(new Date());
		query.setUpdationDate(new Date());
		query.setUpdatedBy(getSessionData().getUserId());

		query.setName("TestDataQuery");
		query.setType(QueryType.GET_DATA.type);
		
		IExpression expression = QueryBuilder.findExpression("Person", query.getConstraints().getRootExpression(), query.getConstraints().getJoinGraph());
		QueryBuilder.addParametrizedCondition(query, expression, "personUpi", RelationalOperator.Equals);
		
		QueryBuilder.addOutputAttribute(query.getOutputAttributeList(), expression, "personUpi");

		return (IParameterizedQuery) query;
	}
	
}

