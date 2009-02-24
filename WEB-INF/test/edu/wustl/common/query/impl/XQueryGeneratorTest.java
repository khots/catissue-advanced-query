
package edu.wustl.common.query.impl;

/**
 * 
 */

import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Variables;

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

	private static DatabaseConnectionParams params;

	public static final PassTwoXQueryGenerator xQueryGenerator = new PassTwoXQueryGenerator();
	public static final XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();

	/**
	 * this method is called only once, after which the tests are executed
	 */
	@BeforeClass
	public static void setUpOnce()
	{

		Logger.configure();
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
		params = new DatabaseConnectionParams();

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
		params.openSession(edu.wustl.query.util.global.Constants.JNDI_NAME_CIDER);
	}

	/**
	 * this method is called once after each test
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		params.commit();
		params.closeSession();
	}

	
	@Test
	public void raceGenderAddressTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalRaceGenderAddressQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph
				.getRoot(), joinGraph);
		QueryBuilder.addCondition(demographics, "dateOfBirth", RelationalOperator.GreaterThan,
				"10/10/1900");

		IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(race, "id", RelationalOperator.Equals, "2345");

		IExpression gender = QueryBuilder.findExpression(Constants.GENDER, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(gender, "id", RelationalOperator.Equals, "1987");

		IExpression address = QueryBuilder.findExpression(Constants.ADDRESS, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(address, "postalCode", RelationalOperator.Equals, "3452");

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
		QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");
		QueryBuilder.addOutputAttribute(outputAttributes, race, "id");
		QueryBuilder.addOutputAttribute(outputAttributes, gender, "id");

		query.setOutputAttributeList(outputAttributes);

		String xquery = xQueryGenerator.generateQuery(query);
		
		
		ResultSet rs = params.getResultSet(xquery);

		if (!rs.next())
		{
			fail("no results!!!");
		}

		int a = 10;
		//does result contain value "6446456" ?
		//	result =
	}

	@Ignore
	@Test
	public void demographicsLabDetailsTest() throws Exception
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

	@Ignore
	@Test
	public void parametrizedQueryTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = QueryBuilder.findExpression(Constants.PERSON, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder
				.addParametrizedCondition(query, person, "personUpi", RelationalOperator.Equals);

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
		QueryBuilder.addOutputAttribute(outputAttributes, person, "personUpi");
		query.setOutputAttributeList(outputAttributes);

		String xquery = xQueryGenerator.generateQuery(query);

		/*
		ResultSet rs = executeParametrizedXQuery(xquery, "33333333");
		if (!rs.next())
		{
			fail("no results!!!");
		}*/

	}

	private ResultSet executeParametrizedXQuery(String xquery, String... values) throws Exception
	{
		PreparedStatement ps = params.getPreparedStatement(xquery);

		for (int i = 0; i < values.length; i++)
		{
			ps.setString(i + 1, values[i]);
		}

		return ps.executeQuery();
	}

	@Ignore
	@Test
	public void temporalQueryTest()
	{
		
	}

}