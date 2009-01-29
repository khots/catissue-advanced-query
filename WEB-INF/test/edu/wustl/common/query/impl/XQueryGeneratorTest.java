
package edu.wustl.common.query.impl;

/**
 * 
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

/**
 * Test class for XQueryGenerator.
 * 
 * @author ravindra_jain, juber patel
 * @created 4th December, 2008
 */
public class XQueryGeneratorTest
{

	public static PassOneXQueryGenerator xQueryGenerator = new PassOneXQueryGenerator();

	XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();


	/**
	 * this method is called only once, after which the tests are executed
	 */
	@BeforeClass
	public void prepare()
	{
		Logger.configure();
		try
		{
			/**
			 * DS Initialization code here
			 */
			Utility.initTest();
			EntityCache.getInstance();

			/**
			  * Indicating - Do not LOG XQueries
			  */
			Variables.isExecutingTCFramework = true;

			Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * this method is called only once, when all tests have executed
	 */
	@AfterClass
	public void wrapUp()
	{
		
		
	}
	
	
	/**
	 * this method is called once before each test
	 * @throws Exception
	 */
	@Before
	protected void setUp() throws Exception
	{

	}

	/**
	 * this method is called once after each test
	 * @throws Exception
	 */
	@After
	protected void tearDown() throws Exception
	{

	}

	@Test
	public void testXQuery_Test1()
	{
		// WRITE TEST CASE LOGIC HERE
	}

}