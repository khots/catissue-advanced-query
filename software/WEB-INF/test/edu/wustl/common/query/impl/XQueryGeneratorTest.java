
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

import edu.wustl.cider.util.DAOUtil;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.query.util.XQueryEntityManagerMock;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.JDBCDAO;
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

	//private static DatabaseConnectionParams params;
	private  JDBCDAO jdbcDao = null;
	

	private static final PassTwoXQueryGenerator xQueryGenerator = new PassTwoXQueryGenerator();
	private static final XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();

	/**
	 * this method is called only once, after which the tests are executed
	 */
	@BeforeClass
	public static void setUpOnce()
	{

		//Logger.configure();
		org.apache.log4j.Logger logger = Logger
		.getLogger(XQueryGeneratorTest.class);
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
		//params = new DatabaseConnectionParams();
		

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
		//params.openSession(edu.wustl.query.util.global.Constants.JNDI_NAME_CIDER);
		jdbcDao = DAOUtil.getJDBCDAO(null);
	}

	/**
	 * this method is called once after each test
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception
	{
//		params.commit();
//		params.closeSession();
		jdbcDao.commit();
		DAOUtil.closeJDBCDAO(jdbcDao);
	}

	@Ignore
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
				Constants.LABORATORY_PROCEDURE,false);
		QueryBuilder.addCondition(lab, "patientAccountNumber", RelationalOperator.IsNotNull);

		IExpression labDetails = QueryBuilder.createExpression(constraints, lab,
				Constants.LABORATORY_PROCEDURE_DETAILS,false);
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

		ResultSet rs = executeParametrizedXQuery(xquery, "000000000000000001000823");

		if (!rs.next())
		{
			fail("no results!!!");
		}

	}

	private ResultSet executeParametrizedXQuery(String xquery, String... values) throws Exception
	{
		//PreparedStatement ps = params.getPreparedStatement(xquery);
		PreparedStatement ps  = jdbcDao.getPreparedStatement(xquery);

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
