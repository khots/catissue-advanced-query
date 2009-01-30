
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
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

import static org.junit.Assert.*;

/**
 * Test class for XQueryGenerator.
 * 
 * @author ravindra_jain, juber patel
 * @created 4th December, 2008
 */
public class XQueryGeneratorTest
{

	public static final PassOneXQueryGenerator xQueryGenerator = new PassOneXQueryGenerator();
	public static final XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();

	/**
	 * this method is called only once, after which the tests are executed
	 */
	@BeforeClass
	public static void setUpOnce()
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
	public static void tearDownOnce()
	{

	}

	/**
	 * this method is called once before each test
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception
	{

	}

	/**
	 * this method is called once after each test
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void raceGenderAddressTest()
	{

		try
		{
			IQuery query = IQueryBuilder.skeletalRaceGenderAddressQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression demographics = IQueryBuilder.findExpression(
					XQueryEntityManagerMock.DEMOGRAPHICS, joinGraph.getRoot(), joinGraph);
			IQueryBuilder.addCondition(demographics, "dateOfBirth", RelationalOperator.GreaterThan,
					"10/10/1900");

			IExpression race = IQueryBuilder.findExpression(XQueryEntityManagerMock.RACE, joinGraph
					.getRoot(), joinGraph);
			IQueryBuilder.addCondition(race, "id", RelationalOperator.Equals, "2345");

			String xquery = xQueryGenerator.generateQuery(query);

			//does result contain value "6446456" ?
			//	result =

		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void demographicsLabDetailsTest()
	{
		try
		{
			IQuery query = IQueryBuilder.skeletalDemograpihcsQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression person = IQueryBuilder.findExpression(XQueryEntityManagerMock.PERSON,
					joinGraph.getRoot(), joinGraph);

			IExpression lab = IQueryBuilder.createExpression(constraints,
					XQueryEntityManagerMock.LABORATORY_PROCEDURE);
			//add conditions on lab
			IExpression labDetails = IQueryBuilder.createExpression(constraints,
					XQueryEntityManagerMock.LABORATORY_PROCEDURE_DETAILS);
			//add conditions on details

			IQueryBuilder.connectExpressions(person, lab, LogicalOperator.And, joinGraph);
			IQueryBuilder.connectExpressions(lab, labDetails, LogicalOperator.And, joinGraph);

			String xquery = xQueryGenerator.generateQuery(query);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}

	}
}