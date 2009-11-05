
package edu.wustl.query.bizlogic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateLiteral;
import edu.wustl.common.querysuite.queryobject.IDateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.INumericLiteral;
import edu.wustl.common.querysuite.queryobject.IOperand;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TermType;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.Validator;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import edu.wustl.query.util.querysuite.TemporalQueryUtility;

/**
 * Creates Query Object as per the data filled by the user on AddLimits section.
 * This will also validate the inputs and generate messages and they will be
 * shown to user.
 * @author deepti_shelar
 */
public class CreateQueryObjectBizLogic
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(CreateQueryObjectBizLogic.class);

	/**
	 * Gets the map which holds the data to create the rule object and add it to
	 * query.
	 * @param strToCreateQuery
	 *            str to create query obj
	 * @param attrCollection
	 *            attribute collection
	 * @return Map rules details
	 * @throws DynamicExtensionsSystemException
	 *             DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 *             DynamicExtensionsApplicationException
	 */
	public Map getRuleDetailsMap(final String strToCreateQuery,
			final Collection<QueryableAttributeInterface> attrCollection)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		Map ruleDetailsMap = new HashMap();
		if (attrCollection != null)
		{
			ruleDetailsMap = getEntityDetails(attrCollection, createConditionsMap(strToCreateQuery));
		}
		return ruleDetailsMap;
	}

	/**
	 * This method get the entity details and populates the rule details map.
	 * @param attrCollection
	 *            attribute Collection
	 * @param conditionsMap
	 *            condition map
	 * @return Map rules details
	 */
	private Map getEntityDetails(final Collection<QueryableAttributeInterface> attrCollection,
			final Map conditionsMap)
	{
		StringBuffer errorMessage = new StringBuffer();
		Map ruleDetailsMap = new HashMap();
		if (conditionsMap != null && !conditionsMap.isEmpty() && attrCollection != null
				&& !attrCollection.isEmpty())
		{
			List<QueryableAttributeInterface> attributes = new ArrayList<QueryableAttributeInterface>();
			List<String> attrOperators = new ArrayList<String>();
			List<String> secondAttrValues = new ArrayList<String>();
			ArrayList<ArrayList<String>> conditionValues = new ArrayList<ArrayList<String>>();
			String[] params;
			for (QueryableAttributeInterface attr : attrCollection)
			{
				params = paramsValue(conditionsMap, attr);
				if (params != null)
				{
					attributes.add(attr);
					attrOperators.add(params[QueryModuleConstants.INDEX_PARAM_ZERO]);
					//firstAttributeValues.add(params[Constants.INDEX_PARAM_ONE]
					// );
					secondAttrValues.add(params[QueryModuleConstants.INDEX_PARAM_TWO]);
					List<String> attributeValues = getConditionValuesList(params);
					errorMessage.append(validateAttributeValues(attr.getDataType().trim(),
							attributeValues));
					if ("".equals(errorMessage.toString()))
					{
						if (QueryModuleConstants.Between
								.equals(params[QueryModuleConstants.INDEX_PARAM_ZERO]))
						{
							attributeValues = Utility.getAttributeValuesInProperOrder(attr
									.getDataType(), attributeValues
									.get(QueryModuleConstants.ARGUMENT_ZERO), attributeValues
									.get(1));
						}
						conditionValues.add((ArrayList<String>) attributeValues);
					}
				}
			}
			if ("".equals(errorMessage.toString()))
			{
				ruleDetailsMap.put(Constants.ATTRIBUTES, attributes);
				ruleDetailsMap.put(Constants.ATTRIBUTE_OPERATORS, attrOperators);
				// ruleDetailsMap.put(AppletConstants.FIRST_ATTR_VALUES,
				// firstAttributeValues);
				ruleDetailsMap.put(Constants.SECOND_ATTR_VALUES, secondAttrValues);
				ruleDetailsMap.put(Constants.ATTR_VALUES, conditionValues);
			}
			ruleDetailsMap.put(Constants.ERROR_MESSAGE, errorMessage.toString());
		}
		return ruleDetailsMap;
	}

	/**
	 * This method get the name and Id of the component.
	 * @param conditionsMap
	 *            condition map
	 * @param attr
	 *            attribute
	 * @return String
	 */
	private String[] paramsValue(final Map conditionsMap, final QueryableAttributeInterface attr)
	{
		String componentId = attr.getName() + attr.getId().toString();
		return (String[]) conditionsMap.get(componentId);
	}

	/**
	 * @param params
	 *            parameters given by user
	 * @return ArrayList attributeValues
	 */
	private List<String> getConditionValuesList(final String[] params)
	{
		List<String> attributeValues = new ArrayList<String>();
		if (params[Constants.ONE] != null)
		{
			String[] values = params[Constants.ONE]
					.split(QueryModuleConstants.QUERY_VALUES_DELIMITER);
			int len = values.length;
			for (int i = 0; i < len; i++)
			{
				if (!"".equals(values[i]))
				{
					attributeValues.add(values[i].trim());
				}
			}
		}
		if (params[Constants.TWO] != null)
		{
			attributeValues.add(params[Constants.TWO].trim());
		}
		return attributeValues;
	}

	/**
	 * Validates the user input and populates the list of messages to be shown
	 * to the user on the screen.
	 * @param dataType
	 *            String
	 * @param attrvalues
	 *            List
	 * @return String message
	 */
	private String validateAttributeValues(String dataType, List<String> attrvalues)
	{
		Validator validator = new Validator();
		StringBuffer errorMessages = new StringBuffer();
		for (String enteredValue : attrvalues)
		{
			if (Constants.MISSING_TWO_VALUES.equalsIgnoreCase(enteredValue))
			{
				errorMessages.append(getErrorMessageForBetweenOperator(enteredValue));
			}
			else if (enteredValue.length() > 0
					&& (QueryModuleConstants.BIG_INT.equalsIgnoreCase(dataType) || QueryModuleConstants.INTEGER
							.equalsIgnoreCase(dataType))
					|| QueryModuleConstants.LONG.equalsIgnoreCase(dataType))
			{
				logger.debug(" Check for integer");

				if (validator.convertToLong(enteredValue) == null)
				{
					errorMessages.append(ApplicationProperties
							.getValue("simpleQuery.intvalue.required"));
					logger.debug(enteredValue + " is not a valid integer");
				}
				else if (!validator.isPositiveNumeric(enteredValue,
						QueryModuleConstants.ARGUMENT_ZERO))
				{
					errorMessages.append(getErrorMessageForPositiveNum(enteredValue));
				}

			}// integer
			else if (enteredValue.length() > 0
					&& (QueryModuleConstants.DOUBLE.equalsIgnoreCase(dataType))
					&& !validator.isDouble(enteredValue, false))
			{
				errorMessages.append(ApplicationProperties
						.getValue("simpleQuery.decvalue.required"));
			} // double
			else if (enteredValue.length() > 0
					&& QueryModuleConstants.TINY_INT.equalsIgnoreCase(dataType))
			{
				if (!QueryModuleConstants.BOOLEAN_YES.equalsIgnoreCase(enteredValue.trim())
						&& !QueryModuleConstants.BOOLEAN_NO.equalsIgnoreCase(enteredValue.trim()))
				{
					errorMessages.append(ApplicationProperties
							.getValue("simpleQuery.tinyint.format"));
				}
			}
			else if (enteredValue.length() > 0
					&& edu.wustl.common.util.global.Constants.FIELD_TYPE_TIMESTAMP_TIME
							.equalsIgnoreCase(dataType))
			{
				errorMessages.append(getErrorMessageForTimeFormat(validator, enteredValue));
			}
			else if ((edu.wustl.common.util.global.Constants.FIELD_TYPE_DATE
					.equalsIgnoreCase(dataType) || edu.wustl.common.util.global.Constants.FIELD_TYPE_TIMESTAMP_DATE
					.equalsIgnoreCase(dataType))
					&& enteredValue.length() > 0)
			{
				errorMessages.append(getErrorMessageForDateFormat(validator, enteredValue));
			}
		}
		return errorMessages.toString();
	}

	/**
	 * This methods returns error message for Positive Number.
	 * @param enteredValue
	 *            value entered by user
	 * @return string Message
	 */
	private String getErrorMessageForPositiveNum(final String enteredValue)
	{
		String errorMessages = "<span class='error_msg'>"
				+ ApplicationProperties.getValue("simpleQuery.intvalue.poisitive.required")
				+ "</span> <br/>";
		logger.debug(enteredValue + " is not a positive integer");
		return errorMessages;
	}

	/**
	 * This methods returns error message for between operator.
	 * @param enteredValue
	 *            value entered by user
	 * @return String Message
	 */
	private String getErrorMessageForBetweenOperator(final String enteredValue)
	{
		String errorMessages = "<span class='error_msg'>"
				+ ApplicationProperties.getValue("simpleQuery.twovalues.required")
				+ "</span> <br/>";
		logger.debug(enteredValue + " two values required for 'Between' operator ");
		return errorMessages;
	}

	/**
	 * This methods returns error message for Time Format.
	 * @param validator
	 *            validator object
	 * @param enteredValue
	 *            value entered by user
	 * @return String Message
	 */
	private String getErrorMessageForTimeFormat(final Validator validator, final String enteredValue)
	{
		StringBuffer errorMessages = new StringBuffer();
		if (!validator.isValidTime(enteredValue, Constants.TIME_PATTERN_HH_MM_SS))
		{
			errorMessages.append("<span class='error_msg'>").append(
					ApplicationProperties.getValue("simpleQuery.time.format")).append(
					"</span> <br/>");
		}
		return errorMessages.toString();
	}

	/**
	 * This methods returns error message for date Format.
	 * @param validator
	 *            validator object
	 * @param enteredValue
	 *            value entered by user
	 * @return String Message
	 */
	private String getErrorMessageForDateFormat(final Validator validator, final String enteredValue)
	{
		StringBuffer errorMessages = new StringBuffer();
		if (!validator.checkDate(enteredValue))
		{
			errorMessages.append("<span class='error_msg'>").append(
					ApplicationProperties.getValue("simpleQuery.date.format")).append(
					"</span> <br/>");
		}
		return errorMessages.toString();
	}

	/**
	 * Craetes Map of condition Objects.
	 * @param queryString
	 *            queryString
	 * @return Map conditions map
	 */
	public Map<String, String[]> createConditionsMap(final String queryString)
	{
		Map<String, String[]> conditionsMap = new HashMap<String, String[]>();
		String[] conditions = queryString.split(QueryModuleConstants.QUERY_CONDITION_DELIMITER);
		String[] attrParams;
		String condition;
		int len = conditions.length;
		for (int i = 0; i < len; i++)
		{
			attrParams = new String[QueryModuleConstants.INDEX_LENGTH];
			condition = conditions[i];
			if (!"".equals(condition))
			{
				condition = condition.substring(QueryModuleConstants.ARGUMENT_ZERO, condition
						.indexOf(QueryModuleConstants.ENTITY_SEPARATOR));
				String attrName = null;
				StringTokenizer tokenizer = new StringTokenizer(condition,
						QueryModuleConstants.QUERY_OPERATOR_DELIMITER);
				while (tokenizer.hasMoreTokens())
				{
					attrName = tokenizer.nextToken();
					if (tokenizer.hasMoreTokens())
					{
						String operator = tokenizer.nextToken();
						attrParams[QueryModuleConstants.INDEX_PARAM_ZERO] = operator;
						if (tokenizer.hasMoreTokens())
						{
							attrParams[Constants.ONE] = tokenizer.nextToken();
							if (RelationalOperator.Between.toString().equals(operator))
							{
								attrParams[QueryModuleConstants.INDEX_PARAM_TWO] = tokenizer
										.nextToken();
							}
						}
					}
				}
				conditionsMap.put(attrName, attrParams);
			}
		}
		return conditionsMap;
	}

	/**
	 * This method process the input values for the conditions and set it to the
	 * conditions in the query also replaces the conditions with the
	 * parameterized conditions.
	 * @param queryInputString query string
	 * @param constraints query constraints
	 * @param displayNamesMap display names map
	 * @param query query
	 * @return String Message
	 */
	public String setInputDataToQuery(final String queryInputString,
			final IConstraints constraints, final Map<String, String> displayNamesMap,
			final IQuery query)
	{
		StringBuffer errorMessage = new StringBuffer("");
		String error = "";
		Map<String, String[]> newConditions = null;
		if (queryInputString != null)
		{
			newConditions = createConditionsMap(queryInputString);
		}
		if (displayNamesMap == null)
		{
			error = updateQueryForExecution(query, newConditions);
		}
		else
		{
			// Utility.removeEmptyCoditionsFromQuery((ParameterizedQuery)
			// query);
			((ParameterizedQuery) query).getParameters().clear();
			for (IExpression expression : constraints)
			{
				int no_of_oprds = expression.numberOfOperands();
				IExpressionOperand operand;
				for (int i = 0; i < no_of_oprds; i++)
				{
					operand = expression.getOperand(i);
					if (operand instanceof IRule)
					{
						int expId = expression.getExpressionId();
						IRule rule = (IRule) operand;
						error = componentValues(displayNamesMap, errorMessage, newConditions,
								expId, rule, query);
						break;
					}
				}
			}
		}
		return error;
	}

	/**
	 * This Method updates Parameterized Query for new conditions.
	 * @param query query IQuery
	 * @param newConditions new conditions array
	 * @return errorMessage
	 */
	private String updateQueryForExecution(final IQuery query,
			final Map<String, String[]> newConditions)
	{
		// List<IParameter<?>> parameterRemovalList = new
		// ArrayList<IParameter<?>>();
		StringBuilder errorMessage = new StringBuilder("");
		for (IParameter<?> parameter : ((ParameterizedQuery) query).getParameters())
		{
			IExpression expression = null;
			if (parameter.getParameterizedObject() instanceof ICondition)
			{
				ICondition condition = (ICondition) parameter.getParameterizedObject();
				expression = QueryUtility.getExpression((IParameter<ICondition>) parameter, query);
				String conditionValue = condition.getValue();
				String componentName = getComponentName(expression, condition);
				String dataType = condition.getAttribute().getDataType();
				if (newConditions != null && newConditions.containsKey(componentName))
				{
					String[] params = newConditions.get(componentName);
					List<String> values = getConditionValuesList(params);

					// Validating the values entered the for a particular
					// attribute condition
					errorMessage.append(validateAttributeValues(dataType, values));
					if (errorMessage.toString().equals(""))
					{
						String operator = params[QueryModuleConstants.INDEX_PARAM_ZERO];
						// it will check wether the condition values are in
						// proper order or not
						// if not will update the values in proper order.
						values = getconditionValuesInProperOrder(dataType, operator, values);
						condition.setValues(values);
						condition.setRelationalOperator(RelationalOperator
								.getOperatorForStringRepresentation(operator));
					}
					else
					{
						break;
					}

				}
				else if (!(conditionValue != null && conditionValue.equalsIgnoreCase("")))
				{// the conditions was not empty
					// make it empty
					Utility.updateConditionToEmptyCondition(condition);
				}
			}
		}
		return errorMessage.toString();
	}

	/**
	 * It will form the Component name on the basis of Expression & attribute
	 *
	 * @param expression
	 *            expression
	 * @param condition
	 *            condition
	 * @return componentName
	 */
	private String getComponentName(IExpression expression, ICondition condition)
	{
		StringBuffer componentName = new StringBuffer("");
		String dataType = condition.getAttribute().getDataType();
		if (dataType.equalsIgnoreCase(Constants.DATE))
		{
			componentName.append(Constants.Calendar);
		}
		componentName.append(expression.getExpressionId()).append(Constants.UNDERSCORE).append(
				condition.getAttribute().getId());
		return componentName.toString();
	}

	/**
	 * If operator is Between then it will return the two values entered in
	 * proper order i.e in ascending order on the basis of data type.
	 *
	 * @param dataType
	 *            data Type of values.
	 * @param operator
	 *            relational operator
	 * @param conditionValues
	 *            list which contains values entered for value.
	 * @return
	 */
	private List<String> getconditionValuesInProperOrder(String dataType, String operator,
			List<String> conditionValues)
	{
		List<String> values = new ArrayList<String>(conditionValues);
		if (QueryModuleConstants.Between.equals(operator))
		{
			values = Utility.getAttributeValuesInProperOrder(dataType, conditionValues.get(0),
					conditionValues.get(Constants.ONE));
		}
		return values;
	}

	/**
	 * This method updates the Parameterized Query for Parameterized Temporal
	 * Conditions
	 *
	 * @param query
	 *            : Query Instabce
	 * @param pageOf
	 *            : Page of
	 * @param rhsList
	 *            : RHS list for a custom formula
	 * @param custFormIndxMap
	 *            : Custom formula Index Map
	 * @return error message if any
	 */
	public String setInputDataToTQ(IQuery query, String pageOf, String rhsList,
			Map<Integer, ICustomFormula> custFormIndxMap)
	{
		StringBuffer errorMsg = new StringBuffer();
		ArrayList<ITerm> rhsTermsList = new ArrayList<ITerm>();
		if (custFormIndxMap != null && !custFormIndxMap.isEmpty())
		{

			Map<String, String[]> newRHSMap = getNewRHSMap(rhsList);
			ParameterizedQuery pQuery = null;
			pQuery = (ParameterizedQuery) query;

			for (Map.Entry<String, String[]> entry : newRHSMap.entrySet())
			{
				rhsTermsList.clear();
				String[] newRHSValues = entry.getValue(); // newRHSMap.get(key);
				ICustomFormula customFormula = custFormIndxMap
						.get(Integer.parseInt(entry.getKey()));
				List<String> values = new ArrayList<String>();
				if (customFormula != null)
				{
					ITerm rhsTerm = QueryObjectFactory.createTerm();
					if ((customFormula.getLhs().getTermType()).equals(TermType.DSInterval))
					{
						values = setDateOffsetRHS(newRHSValues, rhsTermsList);
					}
					else if ((customFormula.getLhs().getTermType()).equals(TermType.Timestamp))
					{
						try
						{
							createRHSsForDateLiterals(rhsTermsList, newRHSValues, values);
						}
						catch (ParseException e)
						{
							logger.error("Exception occured while updating the "
									+ "Parameterized Query for temporal conditions", e);
							errorMsg.append(e.getMessage());
						}
					}
					else
					{
						createRHSsForIntLiterals(rhsTermsList, newRHSValues, values, rhsTerm);
					}
					errorMsg.append(validateAttributeValues(QueryModuleConstants.DOUBLE, values));
					if ("".equals(errorMsg.toString()))
					{
						customFormula
								.setOperator(TemporalQueryUtility
										.getRelationalOperator(newRHSValues[QueryModuleConstants.INDEX_PARAM_ONE]
												.trim()));
						customFormula.getAllRhs().clear();
						// Adding all RHSs
						for (int i = 0; i < rhsTermsList.size(); i++)
						{
							customFormula.addRhs(rhsTermsList.get(i));
						}
						updateCustomFormulaForExecute(pageOf, pQuery, customFormula, values);
						insertCustomFormulaParameters(pageOf, pQuery, customFormula);
					}
				}
			}
		}
		return errorMsg.toString();
	}

	/**
	 * This method inserts custom formula parameters.
	 *
	 * @param pageOf
	 *            saved query
	 * @param pQuery
	 *            parameterized query
	 * @param customFormula
	 *            custom formula
	 */
	private void insertCustomFormulaParameters(String pageOf, ParameterizedQuery pQuery,
			ICustomFormula customFormula)
	{
		if (pageOf.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE))
		{
			insertParamaters(pQuery, customFormula);
		}
	}

	/**
	 * This method updates custom formula in case of execute query.
	 *
	 * @param pageOf
	 *            if page of execute query
	 * @param pQuery
	 *            query to be updates
	 * @param customFormula
	 *            update query with customFormula
	 * @param values
	 *            list of values
	 */
	private void updateCustomFormulaForExecute(String pageOf, ParameterizedQuery pQuery,
			ICustomFormula customFormula, List<String> values)
	{
		if (pageOf.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			updateCFForExecuteQuery(pQuery, customFormula, values);
		}
	}

	/**
	 *This method creates RHSs for date literals
	 *
	 * @param rhsTermsList
	 *            : List to contain RHS terms
	 * @param newRHSValues
	 *            : New RHS values for a cusrom formula
	 * @param values
	 *            : Values
	 * @throws ParseException
	 *             : Parse Exception
	 */
	private void createRHSsForDateLiterals(List<ITerm> rhsTermsList, String[] newRHSValues,
			List<String> values) throws ParseException
	{
		Date date;
		ITerm rhsTerm = QueryObjectFactory.createTerm();
		IDateLiteral rhsDateLiteral = null;
		if (newRHSValues.length == QueryModuleConstants.INDEX_PARAM_TWO)
		{
			rhsDateLiteral = QueryObjectFactory.createDateLiteral();
			values.add("");
			rhsTerm.addOperand(rhsDateLiteral);
			rhsTermsList.add(rhsTerm);
		}
		else
		{
			date = edu.wustl.common.util.Utility
					.parseDate(newRHSValues[QueryModuleConstants.INDEX_PARAM_TWO].trim());
			rhsDateLiteral = QueryObjectFactory
					.createDateLiteral(new java.sql.Date(date.getTime()));

			values.add(newRHSValues[QueryModuleConstants.INDEX_PARAM_TWO].trim());
			rhsTerm.addOperand(rhsDateLiteral);
			rhsTermsList.add(rhsTerm);

			// Check if Between operator is selected, then in that it will have
			// two rhs Terms
			String selectedRelOp = newRHSValues[QueryModuleConstants.INDEX_PARAM_ONE];
			if (selectedRelOp.equalsIgnoreCase(QueryModuleConstants.Between))
			{
				ITerm rhsTerm1 = QueryObjectFactory.createTerm();
				// If it is Between Op, we need to create other date Literal and
				// other rhs term
				date = edu.wustl.common.util.Utility
						.parseDate(newRHSValues[QueryModuleConstants.INDEX_PARAM_THREE].trim());
				rhsDateLiteral = QueryObjectFactory.createDateLiteral(new java.sql.Date(date
						.getTime()));

				values.add(newRHSValues[QueryModuleConstants.INDEX_PARAM_THREE].trim());
				rhsTerm1.addOperand(rhsDateLiteral);
				rhsTermsList.add(rhsTerm1);
			}
		}
		// rhsTerm.addOperand(rhsDateLiteral);

	}

	/**
	 *
	 * @param rhsTermsList
	 *            : List to contain RHS Terms
	 * @param newRHSValues
	 *            : New RHS Values
	 * @param values
	 *            : values
	 * @param rhsTerm
	 *            : rhs term
	 */
	private void createRHSsForIntLiterals(List<ITerm> rhsTermsList, String[] newRHSValues,
			List<String> values, ITerm rhsTerm)
	{
		// This is the case when lhs term type is IntLiteral type
		String value = "";
		if (newRHSValues.length > QueryModuleConstants.INDEX_PARAM_TWO)
		{
			value = newRHSValues[QueryModuleConstants.INDEX_PARAM_TWO].trim();
		}
		INumericLiteral intLiteral = QueryObjectFactory.createNumericLiteral(value);

		values.add(value);
		// Adding Numeric operand
		rhsTerm.addOperand(intLiteral);
		rhsTermsList.add(rhsTerm);

		// Check if there is Between Operator
		String selectedRelOp = newRHSValues[QueryModuleConstants.INDEX_PARAM_ONE];

		if (selectedRelOp.equalsIgnoreCase(QueryModuleConstants.Between))
		{
			// Then create second numeric Literal
			value = newRHSValues[QueryModuleConstants.INDEX_PARAM_THREE].trim();
			intLiteral = QueryObjectFactory.createNumericLiteral(value);

			values.add(value);
			// Create 2nd LHS term for Between
			ITerm rhsTerm1 = QueryObjectFactory.createTerm();
			rhsTerm1.addOperand(intLiteral);
			rhsTermsList.add(rhsTerm1);
		}
	}

	/**
	 * @param newRHSValues
	 *            : New RHS values for a custom formula
	 * @param rhsTermsList
	 * 			  : RHS term list
	 * @return values List
	 */
	private List<String> setDateOffsetRHS(String[] newRHSValues, List<ITerm> rhsTermsList)
	{
		ITerm rhsTerm = QueryObjectFactory.createTerm();
		List<String> values = new ArrayList<String>();
		IDateOffsetLiteral rhsDtOffsetLit = QueryObjectFactory.createDateOffsetLiteral(
				newRHSValues[QueryModuleConstants.INDEX_PARAM_TWO].trim(), TemporalQueryUtility
						.getTimeInterval(newRHSValues[QueryModuleConstants.INDEX_PARAM_THREE]
								.trim()));

		values.add(newRHSValues[QueryModuleConstants.INDEX_PARAM_TWO].trim());
		rhsTerm.addOperand(rhsDtOffsetLit);
		rhsTermsList.add(rhsTerm);

		// Check if "Between" relational operator is selected
		String selectedRelOp = newRHSValues[QueryModuleConstants.INDEX_PARAM_ONE];
		if (selectedRelOp.equalsIgnoreCase(QueryModuleConstants.Between))
		{
			ITerm rhsTerm1 = QueryObjectFactory.createTerm();
			// If it is Between Op, we need to create other date Literal and
			// other rhs term
			rhsDtOffsetLit = QueryObjectFactory.createDateOffsetLiteral(
					newRHSValues[QueryModuleConstants.INDEX_PARAM_FOUR].trim(),
					TemporalQueryUtility
							.getTimeInterval(newRHSValues[QueryModuleConstants.INDEX_PARAM_FIVE]
									.trim()));

			values.add(newRHSValues[QueryModuleConstants.INDEX_PARAM_FOUR].trim());

			rhsTerm1.addOperand(rhsDtOffsetLit);
			rhsTermsList.add(rhsTerm1);
		}
		return values;
	}

	/**
	 * @param pQuery parameterized query
	 * @param customFormula custom formula
	 */
	private void insertParamaters(ParameterizedQuery pQuery, ICustomFormula customFormula)
	{
		IParameter<ICustomFormula> parameter = QueryObjectFactory.createParameter(customFormula,
				null);
		pQuery.getParameters().add(parameter);
	}

	/**
	 * @param pQuery
	 *            : Parameterized Query
	 * @param customFormula
	 *            : Custom Formula
	 * @param values
	 *            : Values
	 */
	private void updateCFForExecuteQuery(ParameterizedQuery pQuery, ICustomFormula customFormula,
			List<String> values)
	{
		Collection<ICustomFormula> customFormulas = QueryUtility
				.getAllParameterizedCustomFormulas(pQuery);
		for (ICustomFormula cf : customFormulas)
		{
			if (cf.getId().equals(customFormula.getId()))
			{
				Set<IExpression> exprnInFormula = QueryUtility.getExpressionsInFormula(cf);
				for (IExpression exp : exprnInFormula)
				{
					boolean removeOperand = exp.removeOperand(cf);
					if (removeOperand && !values.isEmpty())// !(value.trim().
					// equals("")))
					{
						exp.addOperand(QueryObjectFactory
								.createLogicalConnector(LogicalOperator.And), customFormula);
					}
				}
			}
		}
	}

	/**
	 * @param rhsList
	 *            : RHS List for a custom formula
	 * @return : new RHSs map
	 */
	private Map<String, String[]> getNewRHSMap(String rhsList)
	{
		Map<String, String[]> newRHSMap = new HashMap<String, String[]>();
		String[] rhsArray = rhsList.split(QueryModuleConstants.QUERY_CONDITION_DELIMITER);
		for (int i = 1; i < rhsArray.length; i++)
		{
			String[] split = rhsArray[i].split(QueryModuleConstants.QUERY_PARAMETER_DELIMITER);
			newRHSMap.put(split[0], split);
		}
		return newRHSMap;
	}

	/**
	 * @param displayNamesMap display names map
	 * @param errorMessage error message
	 * @param newConditions new conditions array
	 * @param expId expression id
	 * @param rule IRule object
	 * @param query IQuery object
	 * @return String Message
	 */
	private String componentValues(Map<String, String> displayNamesMap, StringBuffer errorMessage,
			Map<String, String[]> newConditions, int expId, IRule rule, IQuery query)
	{
		ICondition condition;
		String componentName = null;
		ParameterizedQuery pQuery = null;
		if (query instanceof ParameterizedQuery)
		{
			pQuery = (ParameterizedQuery) query;
		}
		if (displayNamesMap != null)
		{
			for (String key : displayNamesMap.keySet())
			{
				String exprId = key.split(Constants.UNDERSCORE)[0];
				if (exprId.contains(Constants.Calendar))
				{
					exprId = exprId.substring(Constants.Calendar.length());
				}
				if (rule.getContainingExpression().getExpressionId() == Long.parseLong(exprId))
				{
					condition = getConditionForExpression(newConditions, key, rule, pQuery,
							errorMessage);
					if (condition != null)
					{
						if (!errorMessage.toString().equals(""))
						{
							break;
						}
						// Create parameter
						componentName = generateComponentName(expId, condition.getAttribute(),
								query.getId());
						IParameter<ICondition> parameter = QueryObjectFactory.createParameter(
								condition, displayNamesMap.get(componentName));
						pQuery.getParameters().add(parameter);
					}
				}
			}
		}
		return errorMessage.toString();
	}

	/** Method to get/create condition for the given expression.
	 * @param newConditions new conditions array
	 * @param key key for new conditions
	 * @param rule IRule object
	 * @param pQuery parameterized query
	 * @param errorMessage error message
	 * @return condition
	 */
	private ICondition getConditionForExpression(Map<String, String[]> newConditions, String key,
			IRule rule, ParameterizedQuery pQuery, StringBuffer errorMessage)
	{

		ICondition condition = null;
		String[] values = newConditions.get(key);
		List<String> attributeValues = getConditionValuesList(values);
		RelationalOperator relationalOperator = RelationalOperator
				.getOperatorForStringRepresentation(values[QueryModuleConstants.INDEX_PARAM_ZERO]);
		int exprId = rule.getContainingExpression().getExpressionId();
		boolean isParameter = false;
		for (ICondition tempCondition : rule)
		{
			String componentName = generateComponentName(exprId, tempCondition.getAttribute(),
					pQuery.getId());
			if (newConditions.containsKey(componentName) && key.equals(componentName))
			{
				condition = tempCondition;
				errorMessage.append(validateAttributeValues(condition.getAttribute().getDataType(),
						attributeValues));
				if (!(errorMessage.toString().equals("")))
				{
					break;
				}
				condition.setValues(attributeValues);
				condition.setRelationalOperator(relationalOperator);
				List<IParameter<?>> parameterList = pQuery.getParameters();
				if (parameterList != null)
				{
					for (IParameter<?> parameter : parameterList)
					{
						if (parameter.getParameterizedObject() instanceof ICondition
								&& ((ICondition) parameter.getParameterizedObject()).getId() != null)
						{
							ICondition paramCondition = (ICondition) parameter
									.getParameterizedObject();
							if (paramCondition.getId().equals(condition.getId()))
							{
								condition = null;
								isParameter = true;
								break;
							}
						}
					}
				}
				break;
			}
		}
		if (condition == null && !isParameter && errorMessage.toString().equals(""))
		{
			long attributeId = 0;
			String expressionId = key.split(Constants.UNDERSCORE)[0];
			if (expressionId.contains(Constants.Calendar))
			{
				expressionId = expressionId.substring(Constants.Calendar.length());
			}
			if (exprId == Integer.parseInt(expressionId))
			{
				attributeId = Long.parseLong(key.split(Constants.UNDERSCORE)[1]);
				/*
				 * EntityCache entityCache = EntityCache.getInstance();
				 * AttributeInterface attribute =
				 * entityCache.getAttributeById(attributeId);
				 */
				QueryableAttributeInterface attribute = QueryableObjectUtility
						.getQueryableAttributeFromCache(attributeId);
				errorMessage.append(validateAttributeValues(attribute.getDataType(),
						attributeValues));
				if (errorMessage.toString().equals(""))
				{
					condition = QueryObjectFactory.createCondition(attribute, relationalOperator,
							attributeValues);
					rule.addCondition(condition);
				}
			}
		}
		return condition;
	}

	/**
	 * This Method generates component name as expressionId_attributeId.
	 * @param expressionId expression id
	 * @param attribute attribute to be processed
	 * @param queryId IQuery id
	 * @return componentId
	 */
	private String generateComponentName(int expressionId, QueryableAttributeInterface attribute,
			Long queryId)
	{
		StringBuffer componentId = new StringBuffer();
		if (attribute.getDataType().equalsIgnoreCase(Constants.DATE))
		{
			componentId.append("Calendar");
		}
		componentId.append(expressionId).append(QueryModuleConstants.UNDERSCORE).append(
				attribute.getId().toString());
		if (queryId != null)
		{
			componentId.append(QueryModuleConstants.UNDERSCORE).append(queryId.toString());
		}
		return componentId.toString();
	}

	/**
	 * Method to validate and remove condition-parameters with empty condition values.
	 *
	 * @param query Parameterized query
	 */
	public void validateConditions(ParameterizedQuery query)
	{
		for (IExpression expression : query.getConstraints())
		{
			for (IOperand operand : expression)
			{
				if (operand instanceof IRule)
				{
					processEmptyConditionValue(query.getParameters(), (IRule) operand);
				}
			}
		}
	}

	/**
	 * Method to process empty condition value.
	 *
	 * @param parameters List of parameters
	 * @param iRule IRule object
	 */
	private void processEmptyConditionValue(List<IParameter<?>> parameters, IRule iRule)
	{
		List<ICondition> removeCondList = new ArrayList<ICondition>();
		for (ICondition condition : iRule)
		{
			if (isRemoveCondition(condition))
			{
				removeCondList.add(condition);
				removeParameter(parameters, condition);
			}
		}
		for (ICondition condition : removeCondList)
		{
			iRule.removeCondition(condition);
		}
	}

	/**
	 * Method to decide whether a condition should be removed or not / based on empty condition values.
	 *
	 * @param condition condition to be processed
	 * @return isRemove
	 */
	private boolean isRemoveCondition(ICondition condition)
	{
		boolean isRemove = false;
		List<String> values = condition.getValues();
		switch (condition.getRelationalOperator())
		{
			case Between :
				if (values.size() < Constants.TWO || values.get(Constants.ZERO) == null
						|| "".equals(values.get(Constants.ZERO))
						|| values.get(Constants.ONE) == null
						|| "".equals(values.get(Constants.ONE)))
				{
					isRemove = true;
				}
				break;
			case Contains :
			case Equals :
			case NotEquals :
			case StartsWith :
			case EndsWith :
			case GreaterThan :
			case GreaterThanOrEquals :
			case LessThan :
			case LessThanOrEquals :
			case In :
			case NotIn :
				if (values.size() < Constants.ONE || values.get(Constants.ZERO) == null
						|| "".equals(values.get(Constants.ZERO)))
				{
					isRemove = true;
				}
				break;
			default :
				logger.error("Operator not handled here:" + condition.getRelationalOperator());
				break;
		}
		return isRemove;
	}

	/**
	 * Method to remove parameter from query with empty condition value.
	 *
	 * @param parameters List of parameters
	 * @param condition condition to be processed
	 */
	private void removeParameter(List<IParameter<?>> parameters, ICondition condition)
	{
		Iterator<IParameter<?>> iterator = parameters.iterator();
		while (iterator.hasNext())
		{
			IParameter<?> parameter = iterator.next();
			if (parameter.getParameterizedObject() instanceof ICondition
					&& ((ICondition) parameter.getParameterizedObject()).equals(condition))
			{
				iterator.remove();
			}
		}
	}
}
