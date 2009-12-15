/**
 *
 */

package edu.wustl.query.generator;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.util.global.AQConstants;

/**
 * @author prafull_kadam
 * To test SQL generator class with positive & negative test cases.
 * It does not use Entity Manager, Test queries on dummy Entity.
 * Specifically test SQL representation of each data type with corresponding operators.
 */
public class SqlGeneratorGenericTestCase extends TestCase
{

	SqlGenerator generator;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		generator = new SqlGenerator();
		super.setUp();
	}

	/**
	 * To test all Integer Operator Conditions.
	 *
	 */
	public void testIntegerConditions()
	{
		EntityInterface entity = GenericQueryGeneratorMock.createEntity("DummyEntity");
		IExpression expression = GenericQueryGeneratorMock.createExpression(entity);
		ICondition condition = GenericQueryGeneratorMock.createInCondition(entity, "int");

		try
		{
			RelationalOperator operator = RelationalOperator.In;
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE in (1,2,3,4)", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.NotIn;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE NOT in (1,2,3,4)", generator.getSQL(condition,
							expression));

			List<String> values = new ArrayList<String>();
			values.add("1");
			condition.setValues(values);

			operator = RelationalOperator.LessThan;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE<1", generator.getSQL(condition, expression));

			operator = RelationalOperator.LessThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE<=1", generator.getSQL(condition, expression));

			operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE=1", generator.getSQL(condition, expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE!=1", generator.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThan;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE>1", generator.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE>=1", generator.getSQL(condition, expression));

			values.remove(0);
			operator = RelationalOperator.IsNull;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE is NULL", generator.getSQL(condition, expression));

			operator = RelationalOperator.IsNotNull;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE is NOT NULL", generator.getSQL(condition,
							expression));

			values = new ArrayList<String>();
			values.add("1");
			values.add("100");
			condition.setValues(values);

			operator = RelationalOperator.Between;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"(DummyEntity_1.INT_ATTRIBUTE>=1 And DummyEntity_1.INT_ATTRIBUTE<=100)", generator.getSQL(condition,
							expression));

		}
		catch (Exception e)
		{
			fail("Unexpected Exception while testing Integer Conditions!!!");
		}
	}

	/**
	 * To test all Boolean Operator Conditions.
	 *
	 */
	public void testBooleanConditions()
	{
		EntityInterface entity = GenericQueryGeneratorMock.createEntity("DummyEntity");
		IExpression expression = GenericQueryGeneratorMock.createExpression(entity);
		ICondition condition = GenericQueryGeneratorMock.createInCondition(entity, "boolean");

		try
		{
			List<String> values = new ArrayList<String>();
			values.add(AQConstants.TRUE);
			condition.setValues(values);

			RelationalOperator operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Boolean Attribute for Operator:" + operator,
					"DummyEntity_1.BOOLEAN_ATTRIBUTE=1", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Boolean Attribute for Operator:" + operator,
					"DummyEntity_1.BOOLEAN_ATTRIBUTE!=1", generator.getSQL(condition,
							expression));

			values.set(0,AQConstants.FALSE);
			assertEquals("Incorrect SQL generated for Boolean Attribute for Operator:" + operator,
					"DummyEntity_1.BOOLEAN_ATTRIBUTE!=0", generator.getSQL(condition,
							expression));

		}
		catch (Exception e)
		{
			fail("Unexpected Exception while testing Boolean Conditions!!!");
		}
	}
}
