
package edu.wustl.common.query.impl;

/**
 * 
 */

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

/**
 * Test class for XQueryGenerator.
 * 
 * @author ravindra_jain
 * @author juber patel
 * 
 * @created 4th December, 2008
 */
public class XQueryGeneratorTest
{

	public static final PassTwoXQueryGenerator xQueryGenerator = new PassTwoXQueryGenerator();
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
			Variables.isExecutingTestCase = true;

			Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
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
			IParameterizedQuery query = QueryBuilder.skeletalRaceGenderAddressQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS,
					joinGraph.getRoot(), joinGraph);
			QueryBuilder.addCondition(demographics, "dateOfBirth", RelationalOperator.GreaterThan,
					"10/10/1900");

			IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
					joinGraph);
			QueryBuilder.addCondition(race, "id", RelationalOperator.Equals, "2345");

			IExpression gender = QueryBuilder.findExpression(Constants.GENDER, joinGraph.getRoot(),
					joinGraph);
			QueryBuilder.addCondition(gender, "id", RelationalOperator.Equals, "1987");

			IExpression address = QueryBuilder.findExpression(Constants.ADDRESS, joinGraph
					.getRoot(), joinGraph);
			QueryBuilder.addCondition(address, "postalCode", RelationalOperator.Equals, "3452");

			List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
			QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");
			QueryBuilder.addOutputAttribute(outputAttributes, race, "id");
			QueryBuilder.addOutputAttribute(outputAttributes, gender, "id");

			query.setOutputAttributeList(outputAttributes);

			String xquery = xQueryGenerator.generateQuery(query);

			int a = 10;
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
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression person = QueryBuilder.findExpression(Constants.PERSON, joinGraph.getRoot(),
					joinGraph);

			IExpression lab = QueryBuilder.createExpression(constraints, person,
					Constants.LABORATORY_PROCEDURE);
			QueryBuilder.addCondition(lab, "patientAccountNumber", RelationalOperator.IsNotNull);

			IExpression labDetails = QueryBuilder.createExpression(constraints, lab,
					Constants.LABORATORY_PROCEDURE_DETAILS);
			QueryBuilder.addCondition(labDetails, "ageAtProcedure", RelationalOperator.GreaterThan,
					"18");

			List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
			QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");

			query.setOutputAttributeList(outputAttributes);

			String xquery = xQueryGenerator.generateQuery(query);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}

	}
}