
package edu.wustl.common.query.impl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

@RunWith(value = Suite.class)
@SuiteClasses(value = 	{
									edu.wustl.common.query.impl.CountXQueryGeneratorTest.class,
									edu.wustl.common.query.impl.DataXQueryGeneratorTest.class
									
								})
public class QueryTestSuite
{

	@BeforeClass
	public static void suiteSetup()
	{
		/**
		 * DS Initialization code here
		 */
		Utility.initTest();
		EntityCache.getInstance();
		
		/**
		  * Indicating - Do not LOG XQueries
		  */
		Variables.isExecutingTestCase = true;
	}

	
	@AfterClass
	public static void suiteTearDown()
	{

	}

	
}
