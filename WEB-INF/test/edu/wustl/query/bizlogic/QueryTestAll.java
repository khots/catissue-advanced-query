package edu.wustl.query.bizlogic;

import junit.framework.Test;
import junit.framework.TestSuite;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.utility.Utility;


public class QueryTestAll extends TestSuite
{
	/**
	 * @param args arg
	 */
	public static void main(String[] args)
	{
		try
		{
			junit.swingui.TestRunner.run(QueryTestAll.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return test
	 */
	public static Test suite()
	{
		Utility.initTest();
		EntityCache.getInstance();
		
		TestSuite suite = new TestSuite("Test suite for QUERY business logic");
		
		//For testing WorkflowBizLogic
		suite.addTestSuite(WorkflowBizLogicTestCases.class);
		
		// For testing FRAMEWORK FUNCTIONALITY
		suite.addTestSuite(QueryFrameworkTestCase.class);

		// For testing ASynchronous Queries
		suite.addTestSuite(ASynchronousQueriesTestCases.class);
		
		// For testing Saved Queries
		suite.addTestSuite(QueryBizLogicTestCases.class);
		
		return suite;
	}
}
