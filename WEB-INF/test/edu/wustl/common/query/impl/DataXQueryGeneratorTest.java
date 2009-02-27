/**
 * 
 */

package edu.wustl.common.query.impl;

import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Variables;

/**
 * @author juberahamad_patel
 *
 */
public class DataXQueryGeneratorTest
{

	private static final DatabaseConnectionParams params = new DatabaseConnectionParams();

	private static final PassTwoXQueryGenerator generator = new PassTwoXQueryGenerator();

	/**
	 * this method is called only once, after which the tests are executed
	 */
	@BeforeClass
	public static void setUpOnce()
	{

		Logger.configure();
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
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
	public void RaceInTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalRaceQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		QueryBuilder.addParametrizedCondition(query, joinGraph.getRoot(), "personUpi",
				RelationalOperator.Equals);

		IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(race, "id", RelationalOperator.In, "3452");

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
		QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");
		QueryBuilder.addOutputAttribute(outputAttributes, race, "id");
		query.setOutputAttributeList(outputAttributes);

		String xquery = generator.generateQuery(query);

		ResultSet rs = executeParametrizedXQuery(xquery, "000000000000000001000823");
		if (!rs.next())
		{
			fail("No Results !!!");
		}

	}

	@Test
	public void RaceNotInTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalRaceQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		QueryBuilder.addParametrizedCondition(query, joinGraph.getRoot(), "personUpi",
				RelationalOperator.Equals);

		IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(race, "id", RelationalOperator.NotIn, "3452");

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
		QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");
		QueryBuilder.addOutputAttribute(outputAttributes, race, "id");
		query.setOutputAttributeList(outputAttributes);

		String xquery = generator.generateQuery(query);

		ResultSet rs = executeParametrizedXQuery(xquery, "000000000000000001000823");
		if (!rs.next())
		{
			fail("No Results !!!");
		}

	}

	@Test
	public void DOBBetweenTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		QueryBuilder.addParametrizedCondition(query, joinGraph.getRoot(), "personUpi",
				RelationalOperator.Equals);

		IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph
				.getRoot(), joinGraph);
		QueryBuilder.addCondition(demographics, "dateOfBirth", RelationalOperator.Between,
				"01/01/1950", "01/01/1980");

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
		QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");
		QueryBuilder.addOutputAttribute(outputAttributes, demographics, "dateOfBirth");
		query.setOutputAttributeList(outputAttributes);

		String xquery = generator.generateQuery(query);

		ResultSet rs = executeParametrizedXQuery(xquery, "000000000000000001000823");
		if (!rs.next())
		{
			fail("No Results !!!");
		}

	}

	@Test
	public void AddressContainsTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalAddressQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		QueryBuilder.addParametrizedCondition(query, joinGraph.getRoot(), "personUpi",
				RelationalOperator.Equals);

		IExpression address = QueryBuilder.findExpression(Constants.ADDRESS, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(address, "line1", RelationalOperator.Contains, "S");

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
		QueryBuilder.addOutputAttribute(outputAttributes, joinGraph.getRoot(), "personUpi");
		QueryBuilder.addOutputAttribute(outputAttributes, address, "line1");
		query.setOutputAttributeList(outputAttributes);

		String xquery = generator.generateQuery(query);

		ResultSet rs = executeParametrizedXQuery(xquery, "000000000000000001000823");
		if (!rs.next())
		{
			fail("No Results !!!");
		}

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

}
