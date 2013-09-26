/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

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
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.global.AQConstants;

/**
 * @author prafull_kadam
 * To test SQL generator class with positive & negative test cases.
 * It does not use Entity Manager, Test query on dummy Entity.
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
     * To test the generateSQL(IQuery) method for a Query:
     * [Participant.activityStatus = 'Active']
     *
     */
    public void testParticipantQuery()
    {
        IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
        try
        {
            String sql = generator.generateSQL(query);
            assertEqualSql(
                    "Incorrect SQL formed for From clause of the Expression !!!",
                    "select distinct Participant_1.ACTIVITY_STATUS Column0, Participant_1.BIRTH_DATE Column1, Participant_1.EMPI_ID Column2, Participant_1.LNAME_METAPHONE Column3, Participant_1.EMPI_ID_STATUS Column4, Participant_1.VITAL_STATUS Column5, Participant_1.SOCIAL_SECURITY_NUMBER Column6, Participant_1.GENOTYPE Column7, Participant_1.MIDDLE_NAME Column8, Participant_1.LAST_NAME Column9, Participant_1.IDENTIFIER Column10, Participant_1.GENDER Column11, Participant_1.FIRST_NAME Column12, Participant_1.ETHNICITY Column13, Participant_1.DEATH_DATE Column14  from (select * from CATISSUE_PARTICIPANT where ACTIVITY_STATUS != 'Disabled') Participant_1  where lower(Participant_1.ACTIVITY_STATUS) = lower(?)",
                    sql);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

	/**
     * TO test query for the TABLE_PER_SUB_CLASS inheritance strategy. Query for
     * Collection protocol class as: cp.aliquotInSameContainer = 'True' and
     * cp.activityStatus = 'Active' Here, 1. aliquotInSameContainer attribute is
     * in the derived class i.e. Collection Protocol. 2. activityStatus
     * attribute is in the base class of Collection Protocol i.e.
     * SpecimenProtocol.
     */
    public void testCreateInheritanceQuery()
    {
        IQuery query = GenericQueryGeneratorMock.createInheritanceQuery();
        String sql;
        try
        {
            sql = generator.generateSQL(query);
            assertEqualSql(
                    "Incorrect SQL formed for Query !!!",
                    "select distinct ClinicalStudy_1.UNSIGNED_CONSENT_DOC_URL Column0, ClinicalStudy_1.IDENTIFIER Column1, ClinicalStudy_1.ACTIVITY_STATUS Column2, ClinicalStudy_1.DESCRIPTION_URL Column3, ClinicalStudy_1.END_DATE Column4, ClinicalStudy_1.ENROLLMENT Column5, ClinicalStudy_1.IRB_IDENTIFIER Column6, ClinicalStudy_1.SHORT_TITLE Column7, ClinicalStudy_1.START_DATE Column8, ClinicalStudy_1.TITLE Column9, ClinicalStudy_1.IS_EMPI_ENABLE Column10  from (select CATISSUE_CLINICAL_STUDY.UNSIGNED_CONSENT_DOC_URL, CATISSUE_SPECIMEN_PROTOCOL.IDENTIFIER, CATISSUE_SPECIMEN_PROTOCOL.ACTIVITY_STATUS, CATISSUE_SPECIMEN_PROTOCOL.DESCRIPTION_URL, CATISSUE_SPECIMEN_PROTOCOL.END_DATE, CATISSUE_SPECIMEN_PROTOCOL.ENROLLMENT, CATISSUE_SPECIMEN_PROTOCOL.IRB_IDENTIFIER, CATISSUE_SPECIMEN_PROTOCOL.SHORT_TITLE, CATISSUE_SPECIMEN_PROTOCOL.START_DATE, CATISSUE_SPECIMEN_PROTOCOL.TITLE, CATISSUE_SPECIMEN_PROTOCOL.IS_EMPI_ENABLE from CATISSUE_CLINICAL_STUDY inner join CATISSUE_SPECIMEN_PROTOCOL on CATISSUE_CLINICAL_STUDY.IDENTIFIER=CATISSUE_SPECIMEN_PROTOCOL.IDENTIFIER where CATISSUE_SPECIMEN_PROTOCOL.ACTIVITY_STATUS != 'Disabled') ClinicalStudy_1  where lower(ClinicalStudy_1.UNSIGNED_CONSENT_DOC_URL) = lower(?) And lower(ClinicalStudy_1.ACTIVITY_STATUS) = lower(?)",
                    sql);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void testTemporalPartCPR()
    {
    	IQuery query = GenericQueryGeneratorMock.createTemporalQueryParticipantCPR();
    	String sql;
        try
        {
            sql = generator.generateSQL(query);
            assertEqualSql(
                    "Incorrect SQL formed for Query !!!",
                    "select distinct Participant_1.ACTIVITY_STATUS Column0, Participant_1.BIRTH_DATE Column1," +
                    " Participant_1.EMPI_ID Column2, Participant_1.LNAME_METAPHONE Column3," +
                    " Participant_1.EMPI_ID_STATUS Column4, Participant_1.VITAL_STATUS Column5," +
                    " Participant_1.SOCIAL_SECURITY_NUMBER Column6, Participant_1.GENOTYPE Column7," +
                    " Participant_1.MIDDLE_NAME Column8, Participant_1.LAST_NAME Column9," +
                    " Participant_1.IDENTIFIER Column10, Participant_1.GENDER Column11," +
                    " Participant_1.FIRST_NAME Column12, Participant_1.ETHNICITY Column13," +
                    " Participant_1.DEATH_DATE Column14  from (select * from CATISSUE_PARTICIPANT where" +
                    " ACTIVITY_STATUS != 'Disabled') Participant_1 left join (select * from" +
                    " CATISSUE_CLINICAL_STUDY_REG where ACTIVITY_STATUS != 'Disabled')" +
                    " ClinicalStudyRegistration_2 on (Participant_1.IDENTIFIER=" +
                    "ClinicalStudyRegistration_2.PARTICIPANT_ID)  where" +
                    " (extract(day from cast(ClinicalStudyRegistration_2.REGISTRATION_DATE as timestamp)" +
                    " - cast(Participant_1.BIRTH_DATE as timestamp))*86400 +" +
                    " extract(hour from cast(ClinicalStudyRegistration_2.REGISTRATION_DATE as timestamp)" +
                    " - cast(Participant_1.BIRTH_DATE as timestamp))*3600 +" +
                    " extract(minute from cast(ClinicalStudyRegistration_2.REGISTRATION_DATE as timestamp)" +
                    " - cast(Participant_1.BIRTH_DATE as timestamp))*60 +" +
                    " extract(second from cast(ClinicalStudyRegistration_2.REGISTRATION_DATE as timestamp)" +
                    " - cast(Participant_1.BIRTH_DATE as timestamp)) > (30)*60)",
                    sql);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void testInheritanceQueryWithAssociation1()
    {
        IQuery query = GenericQueryGeneratorMock.createInheritanceQueryWithAssociation1();
        try
        {
            generator.generateSQL(query);
            assertFalse("It should throw null pointer exception....",true);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            assertTrue("No tagged values present for the given association....",true);
        }
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
					"DummyEntity_1.INT_ATTRIBUTE in (?,?,?,?)", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.NotIn;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE NOT in (?,?,?,?)", generator.getSQL(condition,
							expression));

			List<String> values = new ArrayList<String>();
			values.add("1");
			condition.setValues(values);

			operator = RelationalOperator.LessThan;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE<?", generator.getSQL(condition, expression));

			operator = RelationalOperator.LessThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE<=?", generator.getSQL(condition, expression));

			operator = RelationalOperator.Equals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE=?", generator.getSQL(condition, expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE!=?", generator.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThan;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE>?", generator.getSQL(condition, expression));

			operator = RelationalOperator.GreaterThanOrEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Integer Attribute for Operator:" + operator,
					"DummyEntity_1.INT_ATTRIBUTE>=?", generator.getSQL(condition, expression));

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
					"(DummyEntity_1.INT_ATTRIBUTE>=? And DummyEntity_1.INT_ATTRIBUTE<=?)", generator.getSQL(condition,
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
					"DummyEntity_1.BOOLEAN_ATTRIBUTE=?", generator.getSQL(condition,
							expression));

			operator = RelationalOperator.NotEquals;
			condition.setRelationalOperator(operator);
			assertEquals("Incorrect SQL generated for Boolean Attribute for Operator:" + operator,
					"DummyEntity_1.BOOLEAN_ATTRIBUTE!=?", generator.getSQL(condition,
							expression));

			values.set(0,AQConstants.FALSE);
			assertEquals("Incorrect SQL generated for Boolean Attribute for Operator:" + operator,
					"DummyEntity_1.BOOLEAN_ATTRIBUTE!=?", generator.getSQL(condition,
							expression));

		}
		catch (Exception e)
		{
			fail("Unexpected Exception while testing Boolean Conditions!!!");
		}
	}

	public void testAllGetConditions()
	{
		try
		{
			generator.getAttributeColumnNameMap();
			generator.getColumnValueBean();
			generator.getRootOutputTreeNodeList();
			generator.getOutputTermsColumns();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
    private void assertEqualSql(String msg, String expectedSql, String sql)
    {
        /*
         * FOR DEBUGGING: remove the comment put below. It will format the SQL
         * before comparison. This will ease finding differences in the
         * SQLs; note that, for String comparisons, Eclipse displays a "compare" window when you
         * double-click on the "junit.framework.ComparisonFailure" message
         */

        // assertEquals(msg, format(expectedSql), format(sql));
        assertEquals(msg, expectedSql, sql);
    }

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
