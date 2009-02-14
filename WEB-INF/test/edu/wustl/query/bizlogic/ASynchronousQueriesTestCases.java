
package edu.wustl.query.bizlogic;

import junit.framework.TestCase;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.querymanager.CiderQueryManager;
import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.query.impl.QueryUtility;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

/**
 * ASynchronous queries related test cases
 * @version 1.0
 * @author ravindra_jain
 *
 */
public class ASynchronousQueriesTestCases extends TestCase
{

	public static PassOneXQueryGenerator xQueryGenerator = new PassOneXQueryGenerator();

	static
	{
		Logger.configure();
		try
		{
			EntityCache.getInstance();
			Utility.initTest();
			/**
			 * Indicating - Do not LOG XQueries
			 */
			Variables.isExecutingTestCase = true;
   	        Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp()
	{
		try
		{
			super.setUp();
		}
		catch (Exception e)
		{
			System.out.println();
			e.printStackTrace();
			fail("An Exception has occurred.... Please refer to 'System.err' link below for Details");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	  * Fire a query
	  * PersonUPI is NOT NULL
	  * Get count
	  * Initialize 'expectedNoOfRecords' to an appropriate value
	  */
	public void testExecuteQueryWithinXML1()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UPI NOT NULL and DEMOGRAPHICS DOB>10/10/1985");
			
			query = QueryUtility.getQuery(23L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();
			
			System.out.println("\n XQUERY :: "+ ciderQueryObj.getQueryString() +"\n\n");
			
			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	
	public void testExecuteQueryAcrossXML()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UPI NOT NULL and LABS, ACCESSION NUMBER CONTAINS 2008295007131");
			
			query = QueryUtility.getQuery(24L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("\n XQUERY :: "+ ciderQueryObj.getQueryString() +"\n\n");
			
			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	
	/**
	  * Fire a query
	  * Wait for some time (approximately 5 seconds)
	  * Get count
	  * Cancel Query (Thread corresponding to query)
	  * Get count again
	  */
	public void testCancelQuery()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		CiderQuery ciderQueryObj = null;
		
		try
		{
			System.out.println("QUERY - PERSON UPI NOT NULL");
			
			query = QueryUtility.getQuery(21L);
			
			ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			for(int i=0; i<20000; i++)
			{
				for(int j=0; j<50000; j++)
				{
					// do nothing
				}
			}
			
			manager.cancel(queryExecId);
			System.out.println("After manager.cancel()....");
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
				{
					fail("QUERY COMPLETED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
			{	
				System.out.println("QUERY CANCELLED....");
				assertTrue("QUERY CANCELLED....", true);
			}
			else if(count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				System.out.println("QUERY COMPLETED.....");
				fail("QUERY COMPLETED.....");
			}
			else if(count.getStatus().equalsIgnoreCase(Constants.QUERY_IN_PROGRESS))
			{
				System.out.println("QUERY IN PROGRESS.....");
				fail("QUERY IN PROGRESS.....");
			}
		}
		catch (Exception e)
		{
			fail("AN EXCEPTION HAS OCCURRED........");
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
		
		System.out.println("\n XQUERY :: "+ ciderQueryObj.getQueryString() +"\n\n");
		
		System.out.println("No of Records :: "+noOfRecords);
		System.out.println("TEST CASE EXECUTED.....");
	}
	
	/**
	 * Fire a Query which will return 0 records
	 * Get Count
	 * Should be zero
	 */
	/*public void testQueryWithZeroRecords()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UPI LESS THAN 1");
			
			query = Utility.getQuery(fileName2);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			// IQuery query = QueryUtility.getQuery(1L);
			// ciderQueryObj.setQuery(query);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			
			if(noOfRecords > 0)
			{
				fail("NO OF RECORDS > 0");
			}
			System.out.println("SUCCESSFULL....................................");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}*/
	
	
	/**
	 * Fire 2 or more queries simultaneously
	 */
	/*public void testExecuteQueriesASynchronously()
	{
		int queryExecId1 = -1;
		int queryExecId2 = -1;
		int noOfRecords1 = 0;
		int noOfRecords2 = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UPI NOT NULL");
			
			query = QueryUtility.getQuery(23L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId1 = manager.execute(ciderQueryObj);
			queryExecId2 = manager.execute(ciderQueryObj);
			System.out.println("QUERY EXECUTION ID1 :::: "+queryExecId1);
			System.out.println("QUERY EXECUTION ID2 :::: "+queryExecId2);
			
			Count count1 = manager.getQueryCount(queryExecId1);
			Count count2 = manager.getQueryCount(queryExecId2);
			
			while(!count1.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count1.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count1 = manager.getQueryCount(queryExecId1);
			}
			
			while(!count2.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count2.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count2 = manager.getQueryCount(queryExecId2);
			}
			
			noOfRecords1 = count1.getCount();
			noOfRecords2 = count2.getCount();
			
			System.out.println("No of Records 1 :: "+noOfRecords1);
			System.out.println("No of Records 2 :: "+noOfRecords2);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}*/
	
	
	/**
	  * Fire a query
	  * PersonUPI is NOT NULL
	  * Get count
	  * Initialize 'expectedNoOfRecords' to an appropriate value
	  */
	public void testExecuteQueryWithinXML2()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UPI NOT NULL and DEMOGRAPHICS DOB>10/10/1980");
			
			query = QueryUtility.getQuery(22L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("\n XQUERY :: "+ ciderQueryObj.getQueryString() +"\n\n");
			
			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	
	
	public void testBetweenOperator()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - DEMOGRAPHICS DOB between 1940 and 1970");
			
			query = QueryUtility.getQuery(41L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	
	public void testStartsWithOperator()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UI STARTS WITH 000000000000000008690");
			
			query = QueryUtility.getQuery(42L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	public void testEndsWithOperator()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UI ENDS WITH 3");
			
			query = QueryUtility.getQuery(43L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	
	public void testINOperator()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UI IN 000000000000000008690923");
			
			query = QueryUtility.getQuery(44L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}
	
	
	/*public void testNOT_INOperator()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQueryManager manager = new CiderQueryManager();
		IQuery query = null;
		
		try
		{
			System.out.println("QUERY - PERSON UI NOT IN 000000000000000008690923");
			
			query = QueryUtility.getQuery(45L);
			
			CiderQuery ciderQueryObj = new CiderQuery(query, -1, "", -1L, null);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			System.out.println("QUERY EXECUTION ID :::: "+queryExecId);
		
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					fail("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}*/
}
