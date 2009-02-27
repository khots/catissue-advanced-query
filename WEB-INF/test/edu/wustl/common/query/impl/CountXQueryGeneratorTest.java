/**
 * 
 */

package edu.wustl.common.query.impl;

import static org.junit.Assert.*;

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
public class CountXQueryGeneratorTest
{

	private static final DatabaseConnectionParams params = new DatabaseConnectionParams();

	private static final PassOneXQueryGenerator generator = new PassOneXQueryGenerator();

	/**
	 * this method is called only once, after which the tests are executed
	 */
	@BeforeClass
	public static void setUpOnce()
	{

		Logger.configure();
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
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

		IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(race, "id", RelationalOperator.In, "3452");

		setSelectAttributes(query);

		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}

	}

	@Test
	public void RaceNotInTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalRaceQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(race, "id", RelationalOperator.NotIn, "3452");

		setSelectAttributes(query);
		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}

	}

	@Test
	public void UpiEqualsTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = joinGraph.getRoot();
		QueryBuilder.addCondition(person, "personUpi", RelationalOperator.Equals,
				"000000000000000001000823");

		setSelectAttributes(query);

		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		assertEquals(1, count);
		
	}

	@Test
	public void UpiNotEqualsTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = joinGraph.getRoot();
		QueryBuilder.addCondition(person, "personUpi", RelationalOperator.Equals,
				"000000000000000001000823");

		setSelectAttributes(query);

		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}

	}

	@Test
	public void DOBBetweenTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph
				.getRoot(), joinGraph);
		QueryBuilder.addCondition(demographics, "dateOfBirth", RelationalOperator.Between,
				"01/01/1950", "01/01/1980");
		
		setSelectAttributes(query);

		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}

	}

	@Test
	public void UpiStartsWithTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = joinGraph.getRoot();
		QueryBuilder.addCondition(person, "personUpi", RelationalOperator.StartsWith,
				"0000000000000000010008");

		setSelectAttributes(query);

		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}


	}

	@Test
	public void UpiEndsWithTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = joinGraph.getRoot();
		QueryBuilder.addCondition(person, "personUpi", RelationalOperator.EndsWith,
				"23");

		setSelectAttributes(query);

		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}


	}

	@Test
	public void AddressContainsTest() throws Exception
	{
		IParameterizedQuery query = QueryBuilder.skeletalAddressQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression address = QueryBuilder.findExpression(Constants.ADDRESS, joinGraph.getRoot(),
				joinGraph);
		QueryBuilder.addCondition(address, "line1", RelationalOperator.Contains, "S");

		setSelectAttributes(query);
		String xquery = generator.generateQuery(query);

		int count = getCountFor(xquery);
		if (count == 0)
		{
			fail("no results !!!");
		}

	}

	
	
	private int getCountFor(String xquery) throws Exception
	{
		int counter = 0;
		ResultSet rs = params.getResultSet(xquery);

		while (rs.next())
		{
			counter++;
		}

		return counter;
	}

	private void setSelectAttributes(IParameterizedQuery query) throws Exception
	{
		IJoinGraph joinGraph = query.getConstraints().getJoinGraph();

		List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();

		IExpression person = QueryBuilder.findExpression(Constants.PERSON, joinGraph.getRoot(),
				joinGraph);
		IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph
				.getRoot(), joinGraph);

		QueryBuilder.addOutputAttribute(outputAttributes, person, "personUpi");
		QueryBuilder.addOutputAttribute(outputAttributes, demographics, "dateOfBirth");
		query.setOutputAttributeList(outputAttributes);

	}

}
