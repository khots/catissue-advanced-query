package edu.wustl.common.query.impl;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(value = Suite.class)
@SuiteClasses(value = {XQueryGeneratorTest.class})
public class QueryTestSuite
{
	
	/**
	 * 
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

	
}
