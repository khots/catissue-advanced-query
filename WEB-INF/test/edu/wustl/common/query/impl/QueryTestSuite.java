
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
@SuiteClasses(value = {edu.wustl.common.query.impl.XQueryGeneratorTest.class})
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
		
		
		/*
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2install.dll");
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2osse.dll");
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2trcapi.dll");
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2locale.dll");
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2wint.dll");
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2sys.dll");
		System.load("C:/Program Files/IBM/SQLLIB/BIN/db2jcct2.dll");
		*/
	}

	@AfterClass
	public static void suiteTearDown()
	{

	}

	/*
	public static void main(String[] args)
	{
		try
		{
			//junit.awtui.TestRunner.run(QueryTestSuite.class);
			JUnitCore.runClasses(QueryTestSuite.class);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	*/

}
