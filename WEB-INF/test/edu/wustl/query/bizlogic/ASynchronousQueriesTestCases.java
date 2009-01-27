
package edu.wustl.query.bizlogic;

import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;
import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.querymanager.CiderQueryManager;
import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
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
	public static String queryGeneratorClassName = null;
	
	public static InputStream is = ASynchronousQueriesTestCases.class.getClassLoader()
	.getResourceAsStream("query.properties");
	
	static
	{
		Logger.configure();
		try
		{
			// EntityCache.getInstance();
			Utility.initTest();
			Properties props = new Properties();
   	        // props.load(new FileInputStream("query.properties"));
			props.load(is);
   	        queryGeneratorClassName = props.getProperty("query.queryGeneratorClassName");
   	        Variables.queryGeneratorClassName = queryGeneratorClassName;
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
	public void testExecuteQuery()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		int expectedNoOfRecords = 0;
		CiderQuery ciderQueryObj = new CiderQuery();
		CiderQueryManager manager = new CiderQueryManager();
		try
		{
			System.out.println("QUERY - PERSON UPI NOT NULL");
			ciderQueryObj.setProjectId(-1L);
			
			// IQuery query = QueryUtility.getQuery(1L);
			// ciderQueryObj.setQuery(query);
			
			IQuery query = new ParameterizedQuery();
			query.setId(1L);
			ciderQueryObj.setQuery(query);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			Count count = manager.getQueryCount(queryExecId);
			noOfRecords = count.getCount();
			
			if(noOfRecords != expectedNoOfRecords)
			{
				fail("Actual & expected no. of records DO NOT MATCH");
			}
			
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
			// String xQuery = null;
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQuery ciderQueryObj = new CiderQuery();
		CiderQueryManager manager = new CiderQueryManager();
		Count count = null;
		
		try
		{
			// System.out.println("QUERY - PERSON UPI NOT NULL");
			System.out.println("QUERY - ON PERSON AND DEMOGRAPHICS - DOB > 10/10/1980");
				// xQuery = "select personUpi_1 Column0 from xmltable(' for $Person_1 in db2-fn:xmlcolumn(\"DEMOGRAPHICS.XMLDATA\")/Person where exists($Person_1/personUpi)  return <return><Person_1>{$Person_1}</Person_1></return>' columns personUpi_1 varchar(1000) path 'Person_1/Person/personUpi')";
			ciderQueryObj.setProjectId(-1L);
				// ciderQueryObj.setQueryString(xQuery);
			IQuery query = QueryUtility.getQuery(1L);
			ciderQueryObj.setQuery(query);
			
//					IQuery query = new ParameterizedQuery();
//					query.setId(1L);
//					ciderQueryObj.setQuery(query);
			
			Variables.temp = 4;
			queryExecId = manager.execute(ciderQueryObj);
			
			count = manager.getQueryCount(queryExecId);
			
			Thread.sleep(5000);
			manager.cancel(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					System.out.println("No of Records :: "+count.getCount());
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
	
	/**
	 * Fire a Query which will return 0 records
	 * Get Count
	 * Should be zero
	 */
	/*public void testQueryWithZeroRecords()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQuery ciderQueryObj = new CiderQuery();
		CiderQueryManager manager = new CiderQueryManager();
		try
		{
			ciderQueryObj.setProjectId(-1L);
			
			// IQuery query = QueryUtility.getQuery(1L);
			// ciderQueryObj.setQuery(query);
			
			IQuery query = new ParameterizedQuery();
			query.setId(1L);
			ciderQueryObj.setQuery(query);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			Count count = manager.getQueryCount(queryExecId);
			noOfRecords = count.getCount();
			
			if(noOfRecords != 0)
			{
				fail("No. of Records returned > 0....");
			}
			
			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
		}
		catch (Exception e)
		{
			System.out.println("AN EXCEPTION HAS OCCURRED........");
			e.printStackTrace();
		}
	}*/
	
	
	/**
	 * Fire a Query which will return 0 records
	 * Get Count
	 * Should be zero
	 */
	/*public void testExecuteQueriesASynchronously()
	{
		int queryExecId = -1;
		int noOfRecords = 0;
		CiderQuery ciderQueryObj = new CiderQuery();
		CiderQueryManager manager = new CiderQueryManager();
		try
		{
			ciderQueryObj.setProjectId(-1L);
			
			// IQuery query = QueryUtility.getQuery(1L);
			// ciderQueryObj.setQuery(query);
			
			IQuery query = new ParameterizedQuery();
			query.setId(1L);
			ciderQueryObj.setQuery(query);
			
			queryExecId = manager.execute(ciderQueryObj);
			
			Count count = manager.getQueryCount(queryExecId);
			noOfRecords = count.getCount();
			
			if(noOfRecords != 0)
			{
				fail("No. of Records returned > 0....");
			}
			
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
