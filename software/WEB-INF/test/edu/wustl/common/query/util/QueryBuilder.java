
package edu.wustl.common.query.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionAttribute;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.ILiteral;
import edu.wustl.common.querysuite.queryobject.INumericLiteral;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * helper class that helps build parameterized queries of various configurations
 * 
 * @author ravindra_jain
 * @author juber patel
 * 
 * @created 4th December, 2008
 * 
 */
public class QueryBuilder extends edu.wustl.query.util.global.QueryBuilder
{

	public static XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();

	/**
	 * simple IQuery for Person with no additional conditions
	 * 	 * 
	 * @param expression The Expression reference created by function
	 * @return The corresponding join Graph reference.
	 * @throws DynamicExtensionsSystemException 
	 * @throws CyclicException 
	 * @throws DynamicExtensionsApplicationException 
	 */
	public static IParameterizedQuery skeletalPersonQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException
	{
		IParameterizedQuery query = null;
		query = QueryObjectFactory.createParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		query.setConstraints(constraints);

		IExpression person = createExpression(constraints, null, Constants.PERSON, false);
		addBasicPersonConditions(person, "N");

		return query;
	}

	/**
	 *  simple IQuery for Person & Demographics
	 *  
	 * @param expression The Expression reference created by function
	 * @return The corresponding join Graph reference.
	 * @throws DynamicExtensionsSystemException 
	 * @throws CyclicException 
	 * @throws DynamicExtensionsApplicationException 
	 * @throws MultipleRootsException 
	 */
	public static IParameterizedQuery skeletalDemograpihcsQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalPersonQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression demographics = createExpression(constraints, person, Constants.DEMOGRAPHICS,
				false);
		addBasicVersionConditions(demographics);

		return query;
	}
	
	/**
	 * 
	 * @return query Instance
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 */
	public static IParameterizedQuery skeletalDemograpihcsQueryWithEmptyConditions()
	throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
	CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalPersonQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression demographics = createExpression(constraints, person, Constants.DEMOGRAPHICS,
				false);
		addBasicVersionConditions(demographics);

		addEmptyConditionsForDemographics(demographics);
		return query;
		
	}
	
	/**
	 * 
	 * @param expression
	 */
	public  static void addEmptyConditionsForDemographics(IExpression expression)
	{
		addCondition(expression, "socialSecurityNumber", RelationalOperator.Contains,
		"");
		addCondition(expression, "mothersMaidenName", RelationalOperator.Contains,
		"");
	}
	

	/**
	 *  simple IQuery for Person, Demographics and Race
	 *  
	 * @param expression The Expression reference created by function
	 * @return The corresponding join Graph reference.
	 * @throws DynamicExtensionsSystemException 
	 * @throws CyclicException 
	 * @throws DynamicExtensionsApplicationException 
	 * @throws MultipleRootsException 
	 */
	public static IParameterizedQuery skeletalRaceQuery() throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException, CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression demographics = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);
		createExpression(constraints, demographics, Constants.RACE, false);

		return query;
	}

	/**
	 *  simple IQuery for Person, Demographics and Address
	 *  
	 * @param expression The Expression reference created by function
	 * @return The corresponding join Graph reference.
	 * @throws DynamicExtensionsSystemException 
	 * @throws CyclicException 
	 * @throws DynamicExtensionsApplicationException 
	 * @throws MultipleRootsException 
	 */
	public static IParameterizedQuery skeletalAddressQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression demographics = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);
		createExpression(constraints, demographics, Constants.ADDRESS, false);

		return query;
	}

	/**
	 * IQuery for person, demographics, race & gender with no conditions
	 * 
	 * @param expression The Expression reference created by function
	 * @return The corresponding join Graph reference.
	 * @throws MultipleRootsException 
	 * @throws CyclicException 
	 * @throws DynamicExtensionsApplicationException 
	 * @throws DynamicExtensionsSystemException 
	 */
	public static IParameterizedQuery skeletalRaceGenderQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression demographics = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);
		createExpression(constraints, demographics, Constants.RACE, false);
		createExpression(constraints, demographics, Constants.GENDER, false);

		return query;
	}

	public static IParameterizedQuery skeletalRaceGenderAddressQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalRaceGenderQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression demographics = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);
		createExpression(constraints, demographics, Constants.ADDRESS, false);

		return query;
	}

	public static IParameterizedQuery skeletalLabProcedureQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException
	{
		IParameterizedQuery query = null;
		query = QueryObjectFactory.createParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		query.setConstraints(constraints);

		createExpression(constraints, null, Constants.LABORATORY_PROCEDURE, false);
		return query;
	}

	public static IParameterizedQuery skeletalLabProcedureDetailsQuery()
			throws DynamicExtensionsSystemException, MultipleRootsException,
			DynamicExtensionsApplicationException, CyclicException
	{
		IParameterizedQuery query = skeletalLabProcedureQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression labProcedure = findExpression(Constants.LABORATORY_PROCEDURE, joinGraph
				.getRoot(), joinGraph);
		IExpression details = createExpression(constraints, labProcedure,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);
		addBasicVersionConditions(details);

		return query;

	}

	public static IParameterizedQuery skeletalEncounterQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException
	{
		IParameterizedQuery query = null;
		query = QueryObjectFactory.createParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		query.setConstraints(constraints);

		createExpression(constraints, null, Constants.ENCOUNTER, false);

		return query;

	}

	public static IParameterizedQuery skeletalEncounterDetailsQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException
	{
		IParameterizedQuery query = skeletalEncounterQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression encounter = findExpression(Constants.ENCOUNTER, joinGraph.getRoot(), joinGraph);
		IExpression details = createExpression(constraints, encounter, Constants.ENCOUNTER_DETAILS,
				false);
		addBasicVersionConditions(details);

		return query;
	}

	public static void addBasicPersonConditions(IExpression personExpression, String researchOptOut)
	{
		addCondition(personExpression, "activeUpiFlag", RelationalOperator.Equals, "A");
		addCondition(personExpression, "researchOptOut", RelationalOperator.In, researchOptOut);
	}

	public static void addBasicVersionConditions(IExpression expression)
	{
		//select current date from sysibm.SYSDUMMY1
		addCondition(expression, "effectiveEndTimeStamp", RelationalOperator.GreaterThan,
				"03/01/2009");
		addCondition(expression, "effectiveStartTimeStamp", RelationalOperator.LessThan,
				"03/01/2009");
	}

	/**
	 * 
	 * @param query
	 * @throws QueryModuleException
	 */
	private static void updateQueryWithSelectedAttributes(IParameterizedQuery query)
			throws QueryModuleException
	{
		List<IOutputAttribute> selectedOutputAttributeList = new ArrayList<IOutputAttribute>();
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) query;
		IConstraints constraints = parameterizedQuery.getConstraints();
		IExpression rootExpression;
		try
		{
			rootExpression = constraints.getRootExpression();

			EntityInterface entity = rootExpression.getQueryEntity().getDynamicExtensionsEntity()
					.getEntity();
			Collection<AttributeInterface> allAttributes = entity
					.getPrimarykeyAttributeCollectionInSameEntity();
			for (AttributeInterface attributeInterface : allAttributes)
			{
				QueryableAttributeInterface queryableAttribute = QueryableObjectUtility
						.createQueryableAttribute(attributeInterface, entity);
				IOutputAttribute createOutputAttribute = QueryObjectFactory.createOutputAttribute(
						rootExpression, queryableAttribute);
				selectedOutputAttributeList.add(createOutputAttribute);
			}
			parameterizedQuery.setOutputAttributeList(selectedOutputAttributeList);
		}
		catch (MultipleRootsException e)
		{
			QueryModuleException queryModuleException = new QueryModuleException(e.getMessage(), e,
					QueryModuleError.MULTIPLE_ROOT);
			throw queryModuleException;
		}
	}

	/**
	 * It will create a query with the Two categories one with the Person as a root entity
	 * other with Lab as root Entity
	 * It will add the condition on "personUpi" Not Null in Person's Category
	 * and "patientAccountNumber" Is Not Null in Labs category
	 * @return Query 
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 */
	public static IParameterizedQuery skeletalDemographicsCategoryQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException
	{
		IParameterizedQuery query = QueryObjectFactory.createParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		List<IOutputAttribute> outputAttributeList = new ArrayList<IOutputAttribute>();
		query.setConstraints(constraints);

		IExpression demographics = createExpression(constraints, null,
				"Demographics", true);
		addBasicConditionsOnCategory(demographics, "personUpi");
		outputAttributeList.add(getOutputAttribute(demographics, "personUpi"));

		IExpression labTest = createExpression(constraints, demographics,
				"Lab Tests", true);
		addBasicConditionsOnCategory(labTest, "patientAccountNumber");
		outputAttributeList.add(getOutputAttribute(labTest, "patientAccountNumber"));
		query.setOutputAttributeList(outputAttributeList);
		return query;
	}

	private static IOutputAttribute getOutputAttribute(IExpression expression, String attributeName)
	{
		QueryableAttributeInterface attribute = findAttribute(expression.getQueryEntity()
				.getDynamicExtensionsEntity(), attributeName);
		return new OutputAttribute(expression, attribute);

	}

	/**
	 * This method returns queryable attribute based on expression and attribute. 
	 * @param expression entity/category name.
	 * @param attributeName name of attribute
	 * @return queryable attribute
	 * @throws DynamicExtensionsSystemException
	 */
	public static QueryableAttributeInterface getQueryableAttribute(String expression,
			String attributeName) throws DynamicExtensionsSystemException
	{
		EntityInterface entity = entityManager.getEntityByName(expression);
		QueryableObjectInterface queryableObject = QueryableObjectUtility
				.createQueryableObject(entity);
		return findAttribute(queryableObject, attributeName);
	}

	/**
	 * This method creates a temporal query on Demographics on dateOfBirth
	 * 05/19/2009 - dateOfBirth > 30 Minutes
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalDemograpihcsTemporalQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalPersonQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression demographics = createExpression(constraints, person, Constants.DEMOGRAPHICS,
				false);
		addBasicVersionConditions(demographics);
		ITerm lhs = QueryObjectFactory.createTerm();
		Date date = Utility.parseDate("05/19/2009", "MM/dd/yyyy");
		ILiteral lhsDateLiteral = QueryObjectFactory.createDateLiteral(new java.sql.Date(date
				.getTime()));

		lhs.addOperand(lhsDateLiteral);
		IExpressionAttribute attribute = QueryObjectFactory.createExpressionAttribute(demographics,
				getQueryableAttribute("Demographics", "dateOfBirth"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				attribute);
		IDateOffsetLiteral offSet = QueryObjectFactory.createDateOffsetLiteral("30",
				TimeInterval.Minute);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(offSet);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.setOperator(RelationalOperator.GreaterThan);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		demographics.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		demographics.setInView(true);
		return query;
	}

	/**
	 * This method creates a temporal query on Cider Person Test Category on collectionTimeStamp
	 * 05/19/2009 - collectionTimeStamp > 30 Minutes
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalCategorySingleNodeTemporalQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalDemographicsCategoryQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression categoryExpression = findExpression("Lab Tests", joinGraph
				.getRoot(), joinGraph);
		ITerm lhs = QueryObjectFactory.createTerm();
		Date date = Utility.parseDate("05/19/2009", "MM/dd/yyyy");
		ILiteral lhsDateLiteral = QueryObjectFactory.createDateLiteral(new java.sql.Date(date
				.getTime()));

		lhs.addOperand(lhsDateLiteral);
		IExpressionAttribute attribute = QueryObjectFactory.createExpressionAttribute(
				categoryExpression, findAttribute(categoryExpression.getQueryEntity()
						.getDynamicExtensionsEntity(), "collectionTimeStamp"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				attribute);
		IDateOffsetLiteral offSet = QueryObjectFactory.createDateOffsetLiteral("30",
				TimeInterval.Minute);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(offSet);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.setOperator(RelationalOperator.GreaterThan);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		categoryExpression.addOperand(QueryObjectFactory
				.createLogicalConnector(LogicalOperator.And), formula);
		categoryExpression.setInView(true);
		return query;
	}

	/**
	 * This method creates a temporal query on Cider Person Test Category(collectionTimeStamp)
	 * &  CIDER LAB TEST CATEGORY(resulTimeStamp)
	 * collectionTimeStamp (minus)-  resulTimeStamp > 30 Minutes
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalCategoryDoubleNodeTemporalQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalDemographicsCategoryQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression categoryExpressionOne = findExpression("Lab Tests", joinGraph
				.getRoot(), joinGraph);

		ITerm lhs = QueryObjectFactory.createTerm();
		// first Node's Expression Attribute
		//		IExpressionAttribute collectionTimeStamp = QueryObjectFactory.createExpressionAttribute(
		//				categoryExpressionOne, findAttribute(categoryExpressionOne.getQueryEntity()
		//						.getDynamicExtensionsEntity(), "collectionTimeStamp"));

		IExpressionAttribute ageAtProcedure = QueryObjectFactory.createDateOffsetAttribute(
				categoryExpressionOne, findAttribute(categoryExpressionOne.getQueryEntity()
						.getDynamicExtensionsEntity(), "ageAtProcedure"), TimeInterval.Day);
		lhs.addOperand(ageAtProcedure);

		//second nodes Expression Atribute
		IExpression categoryExpressionTwo = findExpression("Demographics", joinGraph
				.getRoot(), joinGraph);

		IExpressionAttribute resultTimeStamp = QueryObjectFactory.createExpressionAttribute(
				categoryExpressionTwo, findAttribute(categoryExpressionTwo.getQueryEntity()
						.getDynamicExtensionsEntity(), "dateOfBirth"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Plus),
				resultTimeStamp);
		// creating Rhs
		Date date = Utility.parseDate("05/19/2009", "MM/dd/yyyy");
		ILiteral offSet = QueryObjectFactory.createDateLiteral(new java.sql.Date(date.getTime()));
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(offSet);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.setOperator(RelationalOperator.GreaterThan);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		categoryExpressionOne.addOperand(QueryObjectFactory
				.createLogicalConnector(LogicalOperator.And), formula);
		categoryExpressionOne.setInView(true);
		return query;
	}

	/**
	 * This method creates a temporal query on Demographics on dateOfBirth and LabProcedureDetails ageAtProcedure
	 * dateOfBirth - ageAtProcedure Between date1 and date2
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalTwoNodeTimestampTemporalQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression expressionOne = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);

		ITerm lhs = QueryObjectFactory.createTerm();

		IExpressionAttribute dateOfBirth = QueryObjectFactory.createExpressionAttribute(
				expressionOne, findAttribute(expressionOne.getQueryEntity()
						.getDynamicExtensionsEntity(), "dateOfBirth"));
		lhs.addOperand(dateOfBirth);

		//second nodes Expression Atribute
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression labProcedure = createExpression(constraints, person,
				Constants.LABORATORY_PROCEDURE, false);
		IExpression expressionTwo = createExpression(constraints, labProcedure,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);

		IExpressionAttribute ageAtProcedure = QueryObjectFactory.createExpressionAttribute(
				expressionTwo, findAttribute(expressionTwo.getQueryEntity()
						.getDynamicExtensionsEntity(), "ageAtProcedure"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				ageAtProcedure);
		// creating Rhs
		Date date = Utility.parseDate("05/19/2009", "MM/dd/yyyy");
		ILiteral offSet = QueryObjectFactory.createDateLiteral(new java.sql.Date(date.getTime()));
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(offSet);

		Date date1 = Utility.parseDate("09/19/2009", "MM/dd/yyyy");
		ILiteral offSet1 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date1.getTime()));
		ITerm rhs1 = QueryObjectFactory.createTerm();
		rhs1.addOperand(offSet1);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.addRhs(rhs1);
		formula.setOperator(RelationalOperator.Between);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		expressionOne.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		expressionOne.setInView(true);
		return query;
	}
	
	/**
	 * 
	 * @return
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery  skeletalTwoNodeEmptyTimestampTemporalQuery()
	throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
	CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalDemograpihcsQueryWithEmptyConditions();
		
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression expressionOne = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);

		//Creating LHS
		ITerm lhs = QueryObjectFactory.createTerm();
		//first nodes Expression Attribute
		IExpressionAttribute dateOfBirth = QueryObjectFactory.createExpressionAttribute(
				expressionOne, findAttribute(expressionOne.getQueryEntity()
						.getDynamicExtensionsEntity(), "dateOfBirth"));
  
		lhs.addOperand(dateOfBirth);	
		//second nodes Expression Attribute
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression labProcedure = createExpression(constraints, person,
				Constants.LABORATORY_PROCEDURE, false);
		IExpression expressionTwo = createExpression(constraints, labProcedure,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);

		IExpressionAttribute ageAtProcedure = QueryObjectFactory.createExpressionAttribute(
				expressionTwo, findAttribute(expressionTwo.getQueryEntity()
						.getDynamicExtensionsEntity(), "ageAtProcedure"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				ageAtProcedure);
		
		
		//Creating RHSs
		//Date date = Utility.parseDate("05/19/2009", "MM/dd/yyyy");
		//ILiteral offSet = QueryObjectFactory.createDateLiteral(new java.sql.Date(date.getTime()));
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(null);

		//Date date1 = Utility.parseDate("09/19/2009", "MM/dd/yyyy");
		//ILiteral offSet1 = QueryObjectFactory.createDateLiteral(new java.sql.Date(date1.getTime()));
		ITerm rhs1 = QueryObjectFactory.createTerm();
		rhs1.addOperand(null);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setId(1l);
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.addRhs(rhs1);
		formula.setOperator(RelationalOperator.Between);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		expressionOne.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		expressionOne.setInView(true);
		
		//Adding that custom  formula as Parameter to Query
		IParameter<ICustomFormula> parameter = QueryObjectFactory.createParameter(
				formula,"testColumn");
		query.getParameters().add(parameter);
		return query;
	}
	/**
	 * 
	 * @return
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	
	public static IParameterizedQuery skeletalTwoNodeEmptyDSIntervalTemporalQuery()
	throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
	CyclicException, MultipleRootsException, ParseException
	{
		
		IParameterizedQuery query = skeletalDemograpihcsQueryWithEmptyConditions();
		
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression expressionOne = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);
		

		//Creating LHS
		ITerm lhs = QueryObjectFactory.createTerm();
		IExpressionAttribute dateOfBirth = QueryObjectFactory.createExpressionAttribute(
				expressionOne, findAttribute(expressionOne.getQueryEntity()
						.getDynamicExtensionsEntity(), "dateOfBirth"));
		lhs.addOperand(dateOfBirth);
		//second nodes Expression Attribute
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression labProcedure = createExpression(constraints, person,
				Constants.LABORATORY_PROCEDURE, false);
		IExpression expressionTwo = createExpression(constraints, labProcedure,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);

		IExpressionAttribute collectionTimeStamp = QueryObjectFactory.createExpressionAttribute(
				expressionTwo, findAttribute(expressionTwo.getQueryEntity()
						.getDynamicExtensionsEntity(), "collectionTimeStamp"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				collectionTimeStamp);

		//Creating RHS
		IDateOffsetLiteral offSet = QueryObjectFactory.createDateOffsetLiteral("",
				TimeInterval.Minute);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(offSet);

		IDateOffsetLiteral offSet1 = QueryObjectFactory.createDateOffsetLiteral("",
				TimeInterval.Minute);
		ITerm rhs1 = QueryObjectFactory.createTerm();
		rhs1.addOperand(offSet1);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setId(1l);
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.addRhs(rhs1);
		formula.setOperator(RelationalOperator.Between);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		expressionOne.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		expressionOne.setInView(true);
		
		//Adding that custom  formula as Parameter to Query
		IParameter<ICustomFormula> parameter = QueryObjectFactory.createParameter(
				formula,"testColumn");
		query.getParameters().add(parameter);

		return query; 
	}
	

	/**
	 * This method creates a temporal query on Demographics on dateOfBirth and LabProcedureDetails ageAtProcedure
	 * dateOfBirth - ageAtProcedure Between date1 and date2
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalTwoNodeDSIntervalTemporalQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression expressionOne = findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
				joinGraph);

		ITerm lhs = QueryObjectFactory.createTerm();

		IExpressionAttribute dateOfBirth = QueryObjectFactory.createExpressionAttribute(
				expressionOne, findAttribute(expressionOne.getQueryEntity()
						.getDynamicExtensionsEntity(), "dateOfBirth"));
		lhs.addOperand(dateOfBirth);

		//second nodes Expression Atribute
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression labProcedure = createExpression(constraints, person,
				Constants.LABORATORY_PROCEDURE, false);
		IExpression expressionTwo = createExpression(constraints, labProcedure,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);

		IExpressionAttribute collectionTimeStamp = QueryObjectFactory.createExpressionAttribute(
				expressionTwo, findAttribute(expressionTwo.getQueryEntity()
						.getDynamicExtensionsEntity(), "collectionTimeStamp"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				collectionTimeStamp);
		// creating Rhs
		IDateOffsetLiteral offSet = QueryObjectFactory.createDateOffsetLiteral("10",
				TimeInterval.Minute);
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(offSet);

		IDateOffsetLiteral offSet1 = QueryObjectFactory.createDateOffsetLiteral("50",
				TimeInterval.Minute);
		ITerm rhs1 = QueryObjectFactory.createTerm();
		rhs1.addOperand(offSet1);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.addRhs(rhs1);
		formula.setOperator(RelationalOperator.Between);

		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		expressionOne.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		expressionOne.setInView(true);
		return query;
	}

	/**
	 * 
	 * @return
	 * @throws DynamicExtensionsSystemException
	 * @throws MultipleRootsException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 */
	public static IParameterizedQuery skeletalMedicationOrderQuery()
			throws DynamicExtensionsSystemException, MultipleRootsException,
			DynamicExtensionsApplicationException, CyclicException
	{
		IParameterizedQuery query = skeletalPersonQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression medicationOrder = createExpression(constraints, person,
				Constants.MEDICATION_ORDER, false);
		IExpression medicationOrderDetails = createExpression(constraints, medicationOrder,
				Constants.MEDICATION_ORDER_DETAILS, false);
		addBasicVersionConditions(medicationOrderDetails);
		return query;
	}
	
	/**
	 * 
	 * strength - strength operator value
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalQANumericLiteralTemporalQuery(
			ArithmeticOperator arithOp, RelationalOperator operator)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		IParameterizedQuery query = skeletalMedicationOrderQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();

		IExpression medOrderDetails = findExpression(Constants.MEDICATION_ORDER_DETAILS, joinGraph
				.getRoot(), joinGraph);
		IExpression strength = createExpression(constraints, medOrderDetails,
				Constants.STRENGTH, false);
		
		ITerm lhs = QueryObjectFactory.createTerm();

		IExpressionAttribute strengthAttr = QueryObjectFactory.createExpressionAttribute(
				strength, findAttribute(strength.getQueryEntity()
						.getDynamicExtensionsEntity(), "strength"));
		lhs.addOperand(strengthAttr);

		//second nodes Expression Atribute
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression medOrder2 = createExpression(constraints, person,
				Constants.MEDICATION_ORDER, false);
		IExpression medOrderDetails2 = createExpression(constraints, medOrder2,
				Constants.MEDICATION_ORDER_DETAILS, false);
		IExpression strength2 = createExpression(constraints, medOrderDetails2,
				Constants.STRENGTH, false);
		IExpressionAttribute strengthAttr2 = QueryObjectFactory.createExpressionAttribute(
				strength2, findAttribute(strength2.getQueryEntity()
						.getDynamicExtensionsEntity(), "strength"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(arithOp),
				strengthAttr2);
		// creating Rhs
		INumericLiteral literal = QueryObjectFactory.createNumericLiteral("10");
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(literal);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.setOperator(operator);
		if(operator.equals(RelationalOperator.Between))
		{
			INumericLiteral literal1 = QueryObjectFactory.createNumericLiteral("500");
			ITerm rhs1 = QueryObjectFactory.createTerm();
			rhs1.addOperand(literal1);
			formula.addRhs(rhs1);
		}
		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		strength.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		strength.setInView(true);
		return query;
	}
	
	
	/**
	 * 
	 * result - result operator value
	 * @return temporal query
	 * @throws DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 * @throws CyclicException
	 * @throws MultipleRootsException
	 * @throws ParseException
	 */
	public static IParameterizedQuery skeletalLabResultTemporalQuery()
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException,
			CyclicException, MultipleRootsException, ParseException
	{
		//IParameterizedQuery query = skeletalLabProcedureDetailsQuery();
		IParameterizedQuery query = skeletalDemograpihcsQuery();
		IConstraints constraints = query.getConstraints();
		IJoinGraph joinGraph = constraints.getJoinGraph();
		IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression labProc1 = createExpression(constraints, person,
				Constants.LABORATORY_PROCEDURE, false);
		IExpression labProcDetails = createExpression(constraints, labProc1,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);
		IExpression labResult = createExpression(constraints, labProcDetails,
				Constants.LAB_RESULT, false);
		IExpression resultValue = createExpression(constraints, labResult,
				Constants.RESULT_VALUE, false);
		IExpression result = createExpression(constraints, resultValue,
				Constants.RESULT, false);
		ITerm lhs = QueryObjectFactory.createTerm();

		IExpressionAttribute resultAttr = QueryObjectFactory.createExpressionAttribute(
				result, findAttribute(result.getQueryEntity()
						.getDynamicExtensionsEntity(), "result"));
		lhs.addOperand(resultAttr);

		//second nodes Expression Atribute
		//IExpression person = findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
		IExpression labProc = createExpression(constraints, person,
				Constants.LABORATORY_PROCEDURE, false);
		IExpression labProcDetails2 = createExpression(constraints, labProc,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);
		IExpression labresult2 = createExpression(constraints, labProcDetails2,
				Constants.LAB_RESULT, false);
		IExpression resultVal2 = createExpression(constraints, labresult2,
				Constants.RESULT_VALUE, false);
		IExpression result2 = createExpression(constraints, resultVal2,
				Constants.RESULT, false);
		IExpressionAttribute resultAttr2 = QueryObjectFactory.createExpressionAttribute(
				resultVal2, findAttribute(result2.getQueryEntity()
						.getDynamicExtensionsEntity(), "result"));
		lhs.addOperand(QueryObjectFactory.createArithmeticConnector(ArithmeticOperator.Minus),
				resultAttr2);
		// creating Rhs
		INumericLiteral literal = QueryObjectFactory.createNumericLiteral("10");
		ITerm rhs = QueryObjectFactory.createTerm();
		rhs.addOperand(literal);

		ICustomFormula formula = QueryObjectFactory.createCustomFormula();
		formula.setLhs(lhs);
		formula.getAllRhs().clear();
		formula.addRhs(rhs);
		formula.setOperator(RelationalOperator.Between);
		
			INumericLiteral literal1 = QueryObjectFactory.createNumericLiteral("500");
			ITerm rhs1 = QueryObjectFactory.createTerm();
			rhs1.addOperand(literal1);
			formula.addRhs(rhs1);
		
		IOutputTerm outputTerm = QueryObjectFactory.createOutputTerm();
		outputTerm.setTerm(formula.getLhs());
		outputTerm.setName("testColumn");
		query.getOutputTerms().add(outputTerm);
		result.addOperand(QueryObjectFactory.createLogicalConnector(LogicalOperator.And),
				formula);
		result.setInView(true);
		return query;
	}
}
