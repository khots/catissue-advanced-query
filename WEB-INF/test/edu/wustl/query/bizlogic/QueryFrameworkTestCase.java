
package edu.wustl.query.bizlogic;

/**
 * 
 */

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.query.impl.QueryUtility;
import edu.wustl.common.query.impl.XQueryEntityManagerMock;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.queryTCExecutor.QueryTestCaseExecutorFactory;
import edu.wustl.query.queryTCExecutor.QueryTestCaseExecutorInterface;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

/**
 * TestCase class for XQueryGenerator.
 * 
 * @author ravindra_jain
 * @created 4th December, 2008
 */
public class QueryFrameworkTestCase extends TestCase
{

	// public static XQueryGenerator xQueryGenerator;
	public static PassOneXQueryGenerator xQueryGenerator = new PassOneXQueryGenerator();
	// public static IQueryGenerator xQueryGenerator = QueryGeneratorFactory.getDefaultQueryGenerator();

	XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();
	public static String xmlFileName = "FrameWork.xml";
	Map<String, String> details;
	public static QueryTestCaseExecutorInterface executor;
	public static int count = 0;
	public static String dbType = null, dbHost = null, dbPort = null, dbName = null, dbUserName,
			dbPassword, dbSchema;

	static
	{
		Logger.configure();
		try
		{
			EntityCache.getInstance();

			Utility.initTest();

			Properties props = new Properties();
			props.load(new FileInputStream("queryInstall.properties"));
			dbType = props.getProperty("database.type");
			dbHost = props.getProperty("database.host");
			dbPort = props.getProperty("database.port");
			dbName = props.getProperty("database.name");
			dbUserName = props.getProperty("database.username");
			dbPassword = props.getProperty("database.password");
			dbSchema = props.getProperty("database.schema");

			// System.out.println("DB TYPE >>>> ::::: "+dbType);

			executor = QueryTestCaseExecutorFactory.getInstance().getTestCaseExecutor(dbType);

			/**
			 * Indicating - Do not LOG XQueries
			 */
			Variables.isExecutingTCFramework = true;

			Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";

			new QueryUtility().readXmlFile(xmlFileName);

			String dbInitCommonScriptsFile = QueryUtility.dbInitCommonScriptsFile;

			if (dbInitCommonScriptsFile != null && !dbInitCommonScriptsFile.equals(""))
			{
				QueryUtility.runSQLScriptsOnDB(dbInitCommonScriptsFile);
			}
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
			count++;
			details = QueryUtility.xmlDetailsMap.get(count);

			boolean isSuccessful = executor.executeTestCase(details);
			assertTrue("ACTUAL & EXPECTED RESULTS DO NOT MATCH", isSuccessful);
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
		if (count == QueryUtility.noOfTestCases)
		{
			String dbCleanUpCommonScriptsFile = QueryUtility.dbCleanUpCommonScriptsFile;
			if (dbCleanUpCommonScriptsFile != null && !dbCleanUpCommonScriptsFile.equals(""))
			{
				QueryUtility.runSQLScriptsOnDB(dbCleanUpCommonScriptsFile);
			}
		}
	}

	/**
	 * 
	 */
	/*public void testQueries()
	{
		try
		{
	    	for(int count=0; count < QueryUtility.noOfTestCases; count++)
	    	{
	    		details = QueryUtility.xmlDetailsMap.get(count);
	    		boolean isSuccessful = executor.executeTestCase(details);
	    		
	    		assertTrue("ACTUAL & EXPECTED RESULTS DO NOT MATCH", isSuccessful);
	    	}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}*/

	public void testXQuery_PersonUpi_is_NOT_NULL()
	{
		//    	IQuery query = XQueryGeneratorMock.createQuery2();
		//    	QueryUtility.serializeIQueryToFile("query_1.xml", query);
	}

	/*public void testXQuery_PersonUpi_is_NOT_NULL_AND_DOB_greater_than_10_10_1920()
	{
		
	}
	
	public void testXQuery_PersonUpi_is_NOT_NULL_AND_DOB_less_than_10_10_1920()
	{
		
	}
	
	public void testXQuery_Find_all_Persons_where_PersonUpi_CONTAINS_values_000000000000000008690923()
	{
		
	}*/
}