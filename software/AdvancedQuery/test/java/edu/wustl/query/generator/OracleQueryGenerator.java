package edu.wustl.query.generator;

import java.util.ArrayList;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;


public class OracleQueryGenerator extends SqlGeneratorGenericTestCase
{
	/**
	 * To test all Date Operator Conditions on Oracle database.
	 *
	 */
	public void testDateConditionsOnOracle()
	{
		EntityInterface entity = GenericQueryGeneratorMock.createEntity("DummyEntity");
		IExpression expression = GenericQueryGeneratorMock.createExpression(entity);
		ICondition condition = GenericQueryGeneratorMock.createInCondition(entity, "date");
		try
		{
			List<String> values = new ArrayList<String>();
			values.add("01-01-2000");
			values.add("01-10-2000");
			values.add("05-10-2000");
			condition.setValues(values);

			RelationalOperator operator = RelationalOperator.In;
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"DummyEntity_1.DATE_ATTRIBUTE in (TO_DATE(?,'mm-dd-yyyy'),TO_DATE(?,'mm-dd-yyyy'),TO_DATE(?,'mm-dd-yyyy'))",
					generator.getSQL(condition, expression));

			operator = RelationalOperator.NotIn;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"DummyEntity_1.DATE_ATTRIBUTE NOT in (TO_DATE(?,'mm-dd-yyyy'),TO_DATE(?,'mm-dd-yyyy'),TO_DATE(?,'mm-dd-yyyy'))",
					generator.getSQL(condition, expression));

			values = new ArrayList<String>();
			values.add("01-01-2000");
			condition.setValues(values);

			operator = RelationalOperator.LessThan;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"TO_DATE(TO_CHAR(TRUNC(DummyEntity_1.DATE_ATTRIBUTE),'mm-dd-yyyy'),'mm-dd-yyyy')<TO_DATE(?,'mm-dd-yyyy')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.LessThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"TO_DATE(TO_CHAR(TRUNC(DummyEntity_1.DATE_ATTRIBUTE),'mm-dd-yyyy'),'mm-dd-yyyy')<=TO_DATE(?,'mm-dd-yyyy')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"TO_DATE(TO_CHAR(TRUNC(DummyEntity_1.DATE_ATTRIBUTE),'mm-dd-yyyy'),'mm-dd-yyyy')=TO_DATE(?,'mm-dd-yyyy')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"TO_DATE(TO_CHAR(TRUNC(DummyEntity_1.DATE_ATTRIBUTE),'mm-dd-yyyy'),'mm-dd-yyyy')!=TO_DATE(?,'mm-dd-yyyy')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThan;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"TO_DATE(TO_CHAR(TRUNC(DummyEntity_1.DATE_ATTRIBUTE),'mm-dd-yyyy'),'mm-dd-yyyy')>TO_DATE(?,'mm-dd-yyyy')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator,
					"TO_DATE(TO_CHAR(TRUNC(DummyEntity_1.DATE_ATTRIBUTE),'mm-dd-yyyy'),'mm-dd-yyyy')>=TO_DATE(?,'mm-dd-yyyy')", generator
							.getSQL(condition, expression));

			values.remove(0);
			operator = RelationalOperator.IsNull;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator, "DummyEntity_1.DATE_ATTRIBUTE is NULL", generator.getSQL(
							condition, expression));

			operator = RelationalOperator.IsNotNull;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on Oracle database for Operator:"
							+ operator, "DummyEntity_1.DATE_ATTRIBUTE is NOT NULL", generator
							.getSQL(condition, expression));

			values = new ArrayList<String>();
			values.add("01-01-2000");
			values.add("02-01-2000");
			condition.setValues(values);

			operator = RelationalOperator.Between;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DummyEntity_1.DATE_ATTRIBUTE>=TO_DATE(?,'mm-dd-yyyy') And DummyEntity_1.DATE_ATTRIBUTE<=TO_DATE(?,'mm-dd-yyyy'))", generator
							.getSQL(condition, expression));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Unexpected Exception while testing Date Conditions on Oracle database!!!");
		}
	}

	/**
	 * To test all String Operator Conditions.
	 *
	 */
	public void testStringConditions()
	{
		EntityInterface entity = GenericQueryGeneratorMock.createEntity("DummyEntity");
		IExpression expression = GenericQueryGeneratorMock.createExpression(entity);
		ICondition condition = GenericQueryGeneratorMock.createInCondition(entity, "string");

		try
		{
			RelationalOperator operator = RelationalOperator.In;
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) in (lower(?),lower(?),lower(?),lower(?))", generator.getSQL(
							condition, expression));

			operator = RelationalOperator.NotIn;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) NOT in (lower(?),lower(?),lower(?),lower(?))", generator.getSQL(
							condition, expression));

			List<String> values = new ArrayList<String>();
			values.add("1");
			condition.setValues(values);

			operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) = lower(?)", generator.getSQL(condition, expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) != lower(?)", generator.getSQL(condition, expression));

			operator = RelationalOperator.Contains;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) like lower(?)", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.StartsWith;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) like lower(?)", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.EndsWith;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"lower(DummyEntity_1.STRING_ATTRIBUTE) like lower(?)", generator.getSQL(condition,
							expression));

			values.remove(0);
			operator = RelationalOperator.IsNull;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE is NULL", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.IsNotNull;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE is NOT NULL", generator.getSQL(condition,
							expression));

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Unexpected Exception while testing String Conditions!!!");
		}
	}

	/**
	 * To test the Positive & Negative Test cases for Between Operator.
	 *
	 */
	public void testBetweenOperator()
	{
		EntityInterface entity = GenericQueryGeneratorMock.createEntity("DummyEntity");
		IExpression expression = GenericQueryGeneratorMock.createExpression(entity);
		ICondition condition = GenericQueryGeneratorMock.createInCondition(entity, "string");

		// Negative Test case, Using Between operator on String.
		try
		{
			List<String> values = new ArrayList<String>();
			values.add("str1");
			values.add("str1");
			condition.setValues(values);
			RelationalOperator operator = RelationalOperator.Between;
			condition.setRelationalOperator(operator);
			generator.getSQL(condition, expression);
			assertFalse("It should throw SQL exception....",true);
		}
		catch (Exception e)
		{
			assertTrue("Expected SqlException!!!, String operand can not have Between Operator in condition.",true);
		}

		// Negative Test case, Using Between operator on Boolean.
		condition = GenericQueryGeneratorMock.createInCondition(entity, "boolean");
		try
		{
			List<String> values = new ArrayList<String>();
			values.add("true");
			values.add("false");
			condition.setValues(values);
			RelationalOperator operator = RelationalOperator.Between;
			condition.setRelationalOperator(operator);
			generator.getSQL(condition, expression);
			fail("Expected SqlException!!!, Boolean operand can not have Between Operator in condition.");
		}
		catch (Exception e)
		{
		}

		// Negative Test case, Using Between operator with one value & more than 2 values.
		condition = GenericQueryGeneratorMock.createInCondition(entity, "int");
		try
		{
			List<String> values = new ArrayList<String>();
			values.add("1");
			condition.setValues(values);
			RelationalOperator operator = RelationalOperator.Between;
			condition.setRelationalOperator(operator);
			generator.getSQL(condition, expression);
			fail("Expected SqlException!!!, Two values required for Between Operator in condition.");

			values.add("2");
			values.add("3");
			generator.getSQL(condition, expression);
			fail("Expected SqlException!!!, Two values required for Between Operator in condition.");
		}
		catch (Exception e)
		{
		}
	}
}
