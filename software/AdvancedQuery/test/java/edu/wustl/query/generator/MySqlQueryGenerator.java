package edu.wustl.query.generator;

import java.util.ArrayList;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;

public class MySqlQueryGenerator extends SqlGeneratorGenericTestCase
{
	/**
	 * To test all Date Operator Conditions on Mysql database.
	 *
	 */
	public void testDateConditionsOnMysql()
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
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"DummyEntity_1.DATE_ATTRIBUTE in (STR_TO_DATE('1-1-2000','%m-%d-%Y'),STR_TO_DATE('1-10-2000','%m-%d-%Y'),STR_TO_DATE('5-10-2000','%m-%d-%Y'))",
					generator.getSQL(condition, expression));

			operator = RelationalOperator.NotIn;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"DummyEntity_1.DATE_ATTRIBUTE NOT in (STR_TO_DATE('1-1-2000','%m-%d-%Y'),STR_TO_DATE('1-10-2000','%m-%d-%Y'),STR_TO_DATE('5-10-2000','%m-%d-%Y'))",
					generator.getSQL(condition, expression));

			values = new ArrayList<String>();
			values.add("01-01-2000");
			condition.setValues(values);

			operator = RelationalOperator.LessThan;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DATE_FORMAT(DATE(DummyEntity_1.DATE_ATTRIBUTE),'%m-%d-%Y'),'%m-%d-%Y')<STR_TO_DATE('1-1-2000','%m-%d-%Y')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.LessThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DATE_FORMAT(DATE(DummyEntity_1.DATE_ATTRIBUTE),'%m-%d-%Y'),'%m-%d-%Y')<=STR_TO_DATE('1-1-2000','%m-%d-%Y')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DATE_FORMAT(DATE(DummyEntity_1.DATE_ATTRIBUTE),'%m-%d-%Y'),'%m-%d-%Y')=STR_TO_DATE('1-1-2000','%m-%d-%Y')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DATE_FORMAT(DATE(DummyEntity_1.DATE_ATTRIBUTE),'%m-%d-%Y'),'%m-%d-%Y')!=STR_TO_DATE('1-1-2000','%m-%d-%Y')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThan;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DATE_FORMAT(DATE(DummyEntity_1.DATE_ATTRIBUTE),'%m-%d-%Y'),'%m-%d-%Y')>STR_TO_DATE('1-1-2000','%m-%d-%Y')", generator
							.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator,
					"(DATE_FORMAT(DATE(DummyEntity_1.DATE_ATTRIBUTE),'%m-%d-%Y'),'%m-%d-%Y')>=STR_TO_DATE('1-1-2000','%m-%d-%Y')", generator
							.getSQL(condition, expression));

			values.remove(0);
			operator = RelationalOperator.IsNull;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
							+ operator, "DummyEntity_1.DATE_ATTRIBUTE is NULL", generator.getSQL(
							condition, expression));

			operator = RelationalOperator.IsNotNull;
			condition.setRelationalOperator(operator);
			assertEquals(
					"Incorrect SQL generated for Date Attribute on MySQL database for Operator:"
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
					"(DummyEntity_1.DATE_ATTRIBUTE>=STR_TO_DATE('1-1-2000','%m-%d-%Y') And DummyEntity_1.DATE_ATTRIBUTE<=STR_TO_DATE('2-1-2000','%m-%d-%Y'))", generator
							.getSQL(condition, expression));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Unexpected Exception while testing Date Conditions on MySQL database!!!");
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
					"DummyEntity_1.STRING_ATTRIBUTE in ('1','2','3','4')", generator.getSQL(
							condition, expression));

			operator = RelationalOperator.NotIn;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE NOT in ('1','2','3','4')", generator.getSQL(
							condition, expression));

			List<String> values = new ArrayList<String>();
			values.add("1");
			condition.setValues(values);

			operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE='1'", generator.getSQL(condition, expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE!='1'", generator.getSQL(condition, expression));

			operator = RelationalOperator.Contains;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE like 1", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.StartsWith;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE like 1", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.EndsWith;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for String Attribute for Operator:" + operator,
					"DummyEntity_1.STRING_ATTRIBUTE like 1", generator.getSQL(condition,
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
}
