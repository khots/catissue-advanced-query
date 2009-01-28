package edu.wustl.query.querysuite;


import junit.framework.Test;
import junit.framework.TestSuite;
import edu.wustl.common.query.impl.XQueryGeneratorTestCase;



public class QueryTestSuite extends TestSuite
{
	/**
	 * @param args arg
	 */
	public static void main(String[] args)
	{
		try
		{
			junit.awtui.TestRunner.run(QueryTestSuite.class);
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
		TestSuite suite = new TestSuite("Test suite for QUERY business logic");
		
		// For testing FAMEWORK for Query testing
		suite.addTestSuite(XQueryGeneratorTestCase.class);
		
		// For testing ASynchronous Queries
		// suite.addTestSuite(ASynchronousQueriesTestCases.class);
		
		return suite;
	}
}
