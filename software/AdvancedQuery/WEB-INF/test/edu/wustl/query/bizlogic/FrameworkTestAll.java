package edu.wustl.query.bizlogic;

import junit.framework.Test;
import junit.framework.TestSuite;


public class FrameworkTestAll extends TestSuite
{
	/**
	 * @param args arg
	 */
	public static void main(String[] args)
	{
		try
		{
			junit.swingui.TestRunner.run(FrameworkTestAll.class);
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
		TestSuite suite = new TestSuite("Test suite for XQuery Framework Functionality Testing.......");
		
		// For testing FAMEWORK for Query testing
		suite.addTestSuite(QueryFrameworkTestCase.class);
		
		return suite;
	}
}
