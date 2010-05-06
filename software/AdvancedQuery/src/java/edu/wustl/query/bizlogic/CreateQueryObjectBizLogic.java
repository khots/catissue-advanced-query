
package edu.wustl.query.bizlogic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IDateLiteral;
import edu.wustl.common.querysuite.queryobject.IDateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TermType;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.Collections;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.Validator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;
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
	 * Gets the map which holds the data to create the rule object and add it to query.
	 * @param strToCreateQueryObject string to create query object
	 * @param attrCollection Collection of attributes.
	 * @return Map rules details
	 * @throws DynamicExtensionsSystemException
	 *             DynamicExtensionsSystemException
	 * @throws DynamicExtensionsApplicationException
	 *             DynamicExtensionsApplicationException
	 */
	public Map getRuleDetailsMap(String strToCreateQueryObject,
			Collection<AttributeInterface> attrCollection) throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException
	{
		Map ruleDetailsMap = new HashMap();
		if (attrCollection != null)
		{
			Map conditionsMap = createConditionsMap(strToCreateQueryObject);
			ruleDetailsMap = getEntityDetails(attrCollection, conditionsMap);
		}
		return ruleDetailsMap;
	}

	/**
	 * This method get the entity details and populates the rule details map.
	 * @param attrCollection Collection of attributes
	 * @param conditionsMap conditionsMap
	 * @return Map rules details
	 */
	private Map getEntityDetails(Collection<AttributeInterface> attrCollection, Map conditionsMap)
	{
		Map ruleDetailsMap = new HashMap();
		if (conditionsMap != null && !conditionsMap.isEmpty() && attrCollection != null
				&& !attrCollection.isEmpty())
		{
			populateRuleDetailsMap(attrCollection, conditionsMap,
					ruleDetailsMap);
		}
		return ruleDetailsMap;
	}

	/**
	 * @param attrCollection Collection of attributes
	 * @param conditionsMap conditions map
	 * @param errorMessage error message
	 * @param ruleDetailsMap rule details map
	 */
	private void populateRuleDetailsMap(
			Collection<AttributeInterface> attrCollection, Map conditionsMap,
			Map ruleDetailsMap)
	{
		StringBuffer errorMessage = new StringBuffer();
		List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
		List<String> attributeOperators = new ArrayList<String>();
		List<String> secondAttributeValues = new ArrayList<String>();
		List<List<String>> conditionValues = new ArrayList<List<String>>();
		String[] params;
		for (AttributeInterface attr : attrCollection)
		{
			params = paramsValue(conditionsMap, attr);
			if (params != null)
			{
				attributes.add(attr);
				attributeOperators.add(params[AQConstants.INDEX_PARAM_ZERO]);
				secondAttributeValues.add(params[AQConstants.INDEX_PARAM_TWO]);
				List<String> attributeValues = getConditionValuesList(params);
				errorMessage.append(validateAttributeValues(attr.getDataType().trim(), attributeValues));
				if ("".equals(errorMessage.toString()))
				{
					populateConditionValuesForBetween(conditionValues,
							params, attr, attributeValues);
				}
			}
		}
		if ("".equals(errorMessage.toString()))
		{
			ruleDetailsMap.put(AQConstants.ATTRIBUTES, attributes);
			ruleDetailsMap.put(AQConstants.ATTRIBUTE_OPERATORS, attributeOperators);
			ruleDetailsMap.put(AQConstants.SECOND_ATTR_VALUES, secondAttributeValues);
			ruleDetailsMap.put(AQConstants.ATTR_VALUES, conditionValues);
		}
		ruleDetailsMap.put(AQConstants.ERROR_MESSAGE, errorMessage.toString());
	}

	/**
	 * @param conditionValues condition Values
	 * @param params String array
	 * @param attr attribute
	 * @param attributeValues attribute Values
	 */
	private void populateConditionValuesForBetween(
			List<List<String>> conditionValues, String[] params,
			AttributeInterface attr, List<String> attributeValues)
	{
		if (AQConstants.BETWEEN
			.equals(params[AQConstants.INDEX_PARAM_ZERO]))
		{
			attributeValues = Utility.getAttributeValuesInProperOrder
			(attr.getDataType(), attributeValues
			.get(AQConstants.ARGUMENT_ZERO), attributeValues
			.get(1));
		}
		conditionValues.add(attributeValues);
	}

	/**
	 * This method gets the name and Id of the component.
	 * @param conditionsMap conditionsMap
	 * @param attr attribute
	 * @return String parameters
	 */
	private String[] paramsValue(Map conditionsMap, AttributeInterface attr)
	{
		String componentId = attr.getName() + attr.getId().toString();
		return (String[]) conditionsMap.get(componentId);
	}

	/**
	 * @param params parameters
	 * @return ArrayList attributeValues
	 */
	private List<String> getConditionValuesList(String[] params)
	{
		List<String> attributeValues = new ArrayList<String>();
		if (params[1] != null)
		{
			String[] values = params[1].split(AQConstants.QUERY_VALUES_DELIMITER);
			int len = values.length;
			for (int i = 0; i < len; i++)
			{
				if (!"".equals(values[i]))
				{
					attributeValues.add(values[i].trim());
				}
			}
		}
		if (params[AQConstants.TWO] != null)
		{
			attributeValues.add(params[AQConstants.TWO].trim());
		}
		return attributeValues;
	}

	/**
	 * Validates the user input and populates the list of messages to be shown
	 * to the user on the screen.
	 * @param dataType
	 *            String
	 * @param attrvalues
	 *            List<String>
	 * @return String message
	 */
	private String validateAttributeValues(String dataType, List<String> attrvalues)
	{
		Validator validator = new Validator();
		String errorMessages = "";
		for (String enteredValue : attrvalues)
		{
			if (AQConstants.MISSING_TWO_VALUES.equalsIgnoreCase(enteredValue))
			{
				errorMessages = getErrorMessageForBetweenOperator(errorMessages, enteredValue);
			}
			else if ((AQConstants.BIG_INT.equalsIgnoreCase(dataType)
					|| AQConstants.INTEGER.equalsIgnoreCase(dataType))
					|| AQConstants.LONG.equalsIgnoreCase(dataType))
			{
				errorMessages = validateBigIntLongIntegerValues(validator,
						errorMessages, enteredValue);

			}// integer
			else if ((AQConstants.DOUBLE.equalsIgnoreCase(dataType))
					&& !validator.isDouble(enteredValue, false))
			{
				errorMessages = errorMessages
						+ ApplicationProperties.getValue("simpleQuery.decvalue.required");
			} // double
			else if (AQConstants.TINY_INT.equalsIgnoreCase(dataType))
			{
				errorMessages = validateTinyInt(errorMessages, enteredValue);
			}
			else if (AQConstants.FIELD_TYPE_TIMESTAMP_TIME.equalsIgnoreCase(dataType))
			{
				errorMessages =
				getErrorMessageForTimeFormat(validator, errorMessages, enteredValue);
			}
			else if (AQConstants.FIELD_TYPE_DATE.equalsIgnoreCase(dataType)
					|| AQConstants.FIELD_TYPE_TIMESTAMP_DATE.equalsIgnoreCase(dataType))
			{
				errorMessages =
				getErrorMessageForDateFormat(validator, errorMessages, enteredValue);
			}
		}
		return errorMessages;
	}

	/**
	 * @param errorMessages error Messages
	 * @param enteredValue entered Value
	 * @return errorMessage
	 */
	private String validateTinyInt(String errorMessages, String enteredValue)
	{
		String errorMessage = errorMessages;
		if (!AQConstants.BOOLEAN_YES.equalsIgnoreCase(enteredValue.trim())
		&& !AQConstants.BOOLEAN_NO.equalsIgnoreCase(enteredValue.trim()))
		{
			errorMessage = errorMessages
			+ ApplicationProperties.getValue("simpleQuery.tinyint.format");
		}
		return errorMessage;
	}

	/**
	 * @param validator validator
	 * @param errorMessages error Messages
	 * @param enteredValue entered Value
	 * @return errorMessage
	 */
	private String validateBigIntLongIntegerValues(Validator validator,
			String errorMessages, String enteredValue)
	{
		Logger.out.debug(" Check for integer");
		String errorMessage = errorMessages;
		if (validator.convertToLong(enteredValue) == null)
		{
			errorMessage = errorMessages
			+ ApplicationProperties.getValue("simpleQuery.intvalue.required");
			Logger.out.debug(enteredValue + " is not a valid integer");
		}
		else if (!validator.isPositiveNumeric(enteredValue,
				AQConstants.ARGUMENT_ZERO))
		{
			errorMessage = getErrorMessageForPositiveNum(errorMessages, enteredValue);
		}
		return errorMessage;
	}

	/**
	 * This methods returns error message for Positive Number.
	 * @param errorMessages errorMessages
	 * @param enteredValue enteredValue
	 * @return string Message
	 */
	private String getErrorMessageForPositiveNum(String errorMessages, String enteredValue)
	{
		StringBuffer strBuf = new StringBuffer(90);
		strBuf.append(errorMessages);
		strBuf.append(AQConstants.FONT_COLOR).append(ApplicationProperties.getValue
				("simpleQuery.intvalue.poisitive.required")).append(AQConstants.END_STYLE);
		Logger.out.debug(enteredValue + " is not a positive integer");
		return strBuf.toString();
	}

	/**
	 * This methods returns error message for between operator.
	 * @param errorMessages errorMessages
	 * @param enteredValue enteredValue
	 * @return String Message
	 */
	private String getErrorMessageForBetweenOperator(String errorMessages, String enteredValue)
	{
		StringBuffer errorMessage = new StringBuffer(errorMessages);
		errorMessage.append(AQConstants.FONT_COLOR).
		append(ApplicationProperties.getValue
				("simpleQuery.twovalues.required")).append(AQConstants.END_STYLE);
		Logger.out.debug(enteredValue + " two values required for 'Between' operator ");
		return errorMessage.toString();
	}

	/**
	 * This methods returns error message for Time Format.
	 * @param validator validator
	 * @param errorMessages errorMessages
	 * @param enteredValue enteredValue
	 * @return String Message
	 */
	private String getErrorMessageForTimeFormat(Validator validator, String errorMessages,
			String enteredValue)
	{
		StringBuffer errorMessage = new StringBuffer(errorMessages);
		if (!validator.isValidTime(enteredValue, AQConstants.TIME_PATTERN_HH_MM_SS))
		{
			errorMessage.append(AQConstants.FONT_COLOR).append
			(ApplicationProperties.getValue("simpleQuery.time.format")).
			append(AQConstants.END_STYLE);
		}
		return errorMessage.toString();
	}

	/**
	 * This methods returns error message for date Format.
	 * @param validator validator
	 * @param errorMessages errorMessages
	 * @param enteredValue enteredValue
	 * @return String Message
	 */
	private String getErrorMessageForDateFormat(Validator validator, String errorMessages,
			String enteredValue)
	{
		StringBuffer errorMessage = new StringBuffer(errorMessages);
		if (!validator.checkDate(enteredValue))
		{
			errorMessage.append(AQConstants.FONT_COLOR).
			append(ApplicationProperties.getValue("simpleQuery.date.format")).
			append(AQConstants.END_STYLE);
		}
		return errorMessage.toString();
	}

	/**
	 * Creates Map of condition Objects.
	 * @param queryString queryString
	 * @return Map conditions map
	 */
	public Map<String, String[]> createConditionsMap(String queryString)
	{
		Map<String, String[]> conditionsMap = new HashMap<String, String[]>();
		String[] conditions = queryString.split(AQConstants.QUERY_CONDITION_DELIMITER);
		int len = conditions.length;
		for (int counter = 0; counter < len; counter++)
		{
			populateConditionMap(conditionsMap, conditions, counter);
		}
		return conditionsMap;
	}

	/**
	 * @param conditionsMap conditions Map
	 * @param conditions conditions
	 * @param counter counter
	 */
	private void populateConditionMap(Map<String, String[]> conditionsMap,
			String[] conditions, int counter)
	{
		String[] attrParams;
		String condition;
		attrParams = new String[AQConstants.INDEX_LENGTH];
		condition = conditions[counter];
		if (!"".equals(condition))
		{
			condition = condition.substring(AQConstants.ARGUMENT_ZERO, condition
					.indexOf(AQConstants.ENTITY_SEPARATOR));
			String attrName = null;
			StringTokenizer tokenizer = new StringTokenizer(condition,
					AQConstants.QUERY_OPERATOR_DELIMITER);
			while (tokenizer.hasMoreTokens())
			{
				attrName = populateAttributeParameters(attrParams,
						tokenizer);
			}
			conditionsMap.put(attrName, attrParams);
		}
	}

	/**
	 * @param attrParams String array
	 * @param tokenizer String
	 * @return attrName
	 */
	private String populateAttributeParameters(String[] attrParams,
			StringTokenizer tokenizer)
	{
		String attrName;
		attrName = tokenizer.nextToken();
		if (tokenizer.hasMoreTokens())
		{
			String operator = tokenizer.nextToken();
			attrParams[AQConstants.INDEX_PARAM_ZERO] = operator;
			if (tokenizer.hasMoreTokens())
			{
				populateArrayForBetween(attrParams, tokenizer, operator);
			}
		}
		return attrName;
	}

	/**
	 * @param attrParams attrParams
	 * @param tokenizer tokenizer
	 * @param operator operator
	 */
	private void populateArrayForBetween(String[] attrParams, StringTokenizer tokenizer,
			String operator)
	{
		attrParams[1] = tokenizer.nextToken();
		if (RelationalOperator.Between.toString().equals(operator))
		{
			attrParams[AQConstants.INDEX_PARAM_TWO] = tokenizer
					.nextToken();
		}
	}

	/**
	 * This method process the input values for the conditions and set it to the conditions in the query
	 * also replaces the conditions with the parameterized conditions.
	 * @param queryInputString queryInputString
	 * @param constraints constraints
	 * @param displayNamesMap displayNamesMap
	 * @param query query
	 * @return String Message
	 */
	public String setInputDataToQuery(String queryInputString, IConstraints constraints,
			Map<String, String> displayNamesMap, IQuery query)
	{
		String errorMessage = "";
		Map<String, String[]> newConditions = getNewConditions(queryInputString);
		for (IExpression expression : constraints)
		{
			int noOfOprds = expression.numberOfOperands();
			IExpressionOperand operand;
			for (int i = 0; i < noOfOprds; i++)
			{
				operand = expression.getOperand(i);
				if (operand instanceof IRule)
				{
					int expId = expression.getExpressionId();
					IRule rule = (IRule) operand;
					errorMessage = componentValues(displayNamesMap,
						errorMessage, newConditions,expId, rule, query);
					populateRule(displayNamesMap, operand, rule);
				}
			}
		}
		return errorMessage;
	}

	/**
	 * @param queryInputString queryInputString
	 * @return newConditions
	 */
	private Map<String, String[]> getNewConditions(String queryInputString)
	{
		Map<String, String[]> newConditions = null;
		if (queryInputString != null)
		{
			newConditions = createConditionsMap(queryInputString);
		}
		return newConditions;
	}

	/**
	 * @param displayNamesMap map
	 * @param operand operand
	 * @param rule rule
	 */
	private void populateRule(Map<String, String> displayNamesMap,
			IExpressionOperand operand, IRule rule)
	{
		if (displayNamesMap == null &&
				(Collections.list((IRule) operand)).size() == 0)
		{
			IExpression expression1 = rule.getContainingExpression();
			AttributeInterface attributeObj = getIdNotNullAttribute(expression1
					.getQueryEntity().getDynamicExtensionsEntity());

			if (attributeObj != null)
			{
				ICondition condition =
					createIdNotNullCondition(attributeObj);
				rule.addCondition(condition);
			}
		}
	}

	/**
	 * Set input data to Temporal Query.
	 * @param query query
	 * @param pageOf pageOf
	 * @param rhsList rhsList
	 * @param cFIndexMap customFormulaIndexMap
	 * @return errorMsg error message
	 */
	public String setInputDataToTQ(IQuery query, String pageOf, String rhsList,
			Map<Integer, ICustomFormula> cFIndexMap)
	{
		String errorMsg = "";
		if (cFIndexMap != null && !cFIndexMap.isEmpty())
		{
			Map<String, String[]> newRHSMap = getNewRHSMap(rhsList);
			ParameterizedQuery pQuery = null;
			pQuery = setPQuery(query, pQuery);
			for (String key : newRHSMap.keySet())
			{
				String[] newRHSValues = newRHSMap.get(key);
				ICustomFormula customFormula = cFIndexMap.get(Integer.parseInt(key));
				String value = "";
				if (customFormula != null)
				{
					ITerm rhsTerm = QueryObjectFactory.createTerm();
					if (customFormula.getLhs().getTermType().equals(TermType.DSInterval))
					{
						value = setDateOffsetRHS(newRHSValues, rhsTerm);
					}
					else if (customFormula.getLhs().getTermType().equals(TermType.Timestamp))
					{
						Date date;
						try
						{
							IDateLiteral rhsDateLiteral = null;
							if (newRHSValues.length == AQConstants.INDEX_PARAM_TWO)
							{
								rhsDateLiteral = QueryObjectFactory.createDateLiteral();
								value = "";
							}
							else
							{
								date = edu.wustl.common.util.Utility.parseDate(
								newRHSValues[AQConstants.INDEX_PARAM_TWO],
										AQConstants.DATE_FORMAT);
								rhsDateLiteral = QueryObjectFactory
								.createDateLiteral(new java.sql.Date(date.getTime()));
								value = newRHSValues[AQConstants.INDEX_PARAM_TWO];
							}
							rhsTerm.addOperand(rhsDateLiteral);
						}
						catch (ParseException e)
						{
							logger.error(e.getMessage(), e);
							errorMsg = e.getMessage();
						}
					}
					customFormula.setOperator(TemporalQueryUtility
					.getRelationalOperator(newRHSValues[AQConstants.INDEX_PARAM_ONE]));
					customFormula.getAllRhs().clear();
					customFormula.addRhs(rhsTerm);
					if (pageOf.equalsIgnoreCase(AQConstants.EXECUTE_QUERY_PAGE))
					{
						updateCFForExecuteQuery(pQuery, customFormula, value);
					}
					if (pageOf.equalsIgnoreCase(AQConstants.SAVE_QUERY_PAGE))
					{
						pQuery = insertParameters(query, pQuery, customFormula);
					}
				}
			}
		}
		return errorMsg;
	}

	/**
	 * @param newRHSValues newRHSValues
	 * @param rhsTerm rhsTerm
	 * @return value value
	 */
	private String setDateOffsetRHS(String[] newRHSValues, ITerm rhsTerm)
	{
		String value;
		IDateOffsetLiteral rhsDateOffsetLiteral = QueryObjectFactory.createDateOffsetLiteral(
				newRHSValues[AQConstants.INDEX_PARAM_TWO], TemporalQueryUtility
					.getTimeInterval(newRHSValues[AQConstants.INDEX_PARAM_THREE]));
		rhsTerm.addOperand(rhsDateOffsetLiteral);
		value = newRHSValues[AQConstants.INDEX_PARAM_TWO];
		return value;
	}

	/**
	 * @param query query
	 * @param pQuery pQuery
	 * @param customFormula customFormula
	 * @return pQuery pQuery
	 */
	private ParameterizedQuery insertParameters(IQuery query, ParameterizedQuery pQuery,
			ICustomFormula customFormula)
	{
		ParameterizedQuery tempQuery = pQuery;
		tempQuery = setPQuery(query, pQuery);
		IParameter<ICustomFormula> parameter = QueryObjectFactory.
			createParameter(customFormula,null);
		tempQuery.getParameters().add(parameter);
		return tempQuery;
	}

	/**
	 * This method updates the Custom Formula.
	 * @param pQuery pQuery
	 * @param customFormula customFormula
	 * @param value value
	 */
	private void updateCFForExecuteQuery(ParameterizedQuery pQuery, ICustomFormula customFormula,
			String value)
	{
		Collection<ICustomFormula> allParameterizedCustomFormulas = QueryUtility
				.getAllParameterizedCustomFormulas(pQuery);
		for (ICustomFormula cf : allParameterizedCustomFormulas)
		{
			if (cf.getId().equals(customFormula.getId()))
			{
				Set<IExpression> expressionsInFormula = QueryUtility.getExpressionsInFormula(cf);
				for (IExpression exp : expressionsInFormula)
				{
					addOperand(customFormula, value, cf, exp);
				}
			}
		}
	}

	/**
	 * @param customFormula customFormula
	 * @param value value
	 * @param customFormula1 customFormula1
	 * @param exp expression
	 */
	private void addOperand(ICustomFormula customFormula, String value,
			ICustomFormula customFormula1, IExpression exp)
	{
		boolean removeOperand = exp.removeOperand(customFormula1);
		if (removeOperand && !(value.trim().equals("")))
		{
			exp.addOperand(QueryObjectFactory
			.createLogicalConnector(LogicalOperator.And), customFormula);
		}
	}

	/**
	 * @param rhsList List of RHS
	 * @return newRHSMap newRHSMap
	 */
	private Map<String, String[]> getNewRHSMap(String rhsList)
	{
		Map<String, String[]> newRHSMap = new HashMap<String, String[]>();
		String[] rhsArray = rhsList.split(AQConstants.QUERY_CONDITION_DELIMITER);
		for (int i = 1; i < rhsArray.length; i++)
		{
			String[] split = rhsArray[i].split(AQConstants.QUERY_PARAMETER_DELIMITER);
			newRHSMap.put(split[0], split);
		}
		return newRHSMap;
	}

	/**
	 * Check if identifier present in entity.
	 * @param entityInterfaceObj The Entity for which it is required to check if
	 *            identifier present.
	 * @return Reference to the AttributeInterface if identifier attribute is
	 *         present in the entity, else null.
	 */
	private AttributeInterface getIdNotNullAttribute(EntityInterface entityInterfaceObj)
	{
		AttributeInterface idAttribute = null;
		Collection<AttributeInterface> attributes = entityInterfaceObj
				.getEntityAttributesForQuery();
		for (AttributeInterface attribute : attributes)
		{
			if (attribute.getName().equals(AQConstants.IDENTIFIER))
			{
				idAttribute = attribute;
			}
		}
		return idAttribute;
	}

	/**
	 * Creates condition identifier != null'.
	 * @param attributeObj The attribute on which the condition
	 * has to be created(In this case its 'identifier')
	 * @return condition that is created on identifier
	 */
	private ICondition createIdNotNullCondition(AttributeInterface attributeObj)
	{
		ICondition condition = QueryObjectFactory.createCondition(attributeObj,
				RelationalOperator.IsNotNull, null);
		return condition;
	}

	/**
	 * @param displayNamesMap displayNamesMap
	 * @param errorMessage errorMessage
	 * @param newConditions newConditions
	 * @param expId expId
	 * @param rule rule
	 * @param query query
	 * @return String Message
	 */
	private String componentValues(Map<String, String> displayNamesMap, String errorMessage,
			Map<String, String[]> newConditions, int expId, IRule rule, IQuery query)
	{
		StringBuffer errorMessages = new StringBuffer(errorMessage);
		ICondition condition;
		String componentName;
		List<ICondition> removalList = new ArrayList<ICondition>();
		List<ICondition> defaultConditions = new ArrayList<ICondition>();
		int size = rule.size();
		ParameterizedQuery pQuery = null;
		pQuery = setPQuery(query, pQuery);
		for (int j = 0; j < size; j++)
		{
			condition = rule.getCondition(j);
			componentName = generateComponentName(expId, condition.getAttribute());
			if (newConditions != null && newConditions.containsKey(componentName))
			{
				String[] params = newConditions.get(componentName);
				List<String> attributeValues = getConditionValuesList(params);
				errorMessages.append(validateAttributeValues(
				condition.getAttribute().getDataType(), attributeValues));
				if (displayNamesMap != null && !(displayNamesMap.containsKey(componentName)))
				{
					logger.info("Display Map is null");
				}
				else
				{
					condition.setValues(attributeValues);
					condition.setRelationalOperator(RelationalOperator
					.getOperatorForStringRepresentation(params[AQConstants.INDEX_PARAM_ZERO]));
				}
			}
			if ((!newConditions.containsKey(componentName)) && (displayNamesMap == null))
			{
				removalList.add(condition);
				if (query instanceof ParameterizedQuery)
				{
					pQuery = addDefaultConditions(query, condition,
							defaultConditions);
				}
			}
			populateParameters(displayNamesMap, condition, componentName,pQuery);
		}
		removeUnwantedConditions(rule, removalList, defaultConditions);
		return errorMessages.toString();
	}

	/**
	 * @param query query
	 * @param pQuery pQuery
	 * @return tempQuery
	 */
	private ParameterizedQuery setPQuery(IQuery query, ParameterizedQuery pQuery)
	{
		ParameterizedQuery tempQuery = pQuery;
		if (query instanceof ParameterizedQuery)
		{
			tempQuery = (ParameterizedQuery) query;
		}
		return tempQuery;
	}

	/**
	 * @param displayNamesMap display Names Map
	 * @param condition condition
	 * @param componentName component Name
	 * @param pQuery pQuery
	 */
	private void populateParameters(Map<String, String> displayNamesMap,
			ICondition condition, String componentName,
			ParameterizedQuery pQuery)
	{
		if (displayNamesMap != null && displayNamesMap.containsKey(componentName))
		{

			IParameter<ICondition> parameter = QueryObjectFactory.createParameter(condition,
					displayNamesMap.get(componentName));
			pQuery.getParameters().add(parameter);
		}
	}

	/**
	 * @param query query
	 * @param condition condition
	 * @param defaultConditions default Conditions
	 * @return pQuery
	 */
	private ParameterizedQuery addDefaultConditions(IQuery query,
			ICondition condition, List<ICondition> defaultConditions)
	{
		ParameterizedQuery pQuery;
		pQuery = (ParameterizedQuery) query;
		List<IParameter<?>> parameterList = pQuery.getParameters();
		boolean isparameter = false;
		if (parameterList != null)
		{
			isparameter = isParameter(condition, parameterList,
					isparameter);
		}
		if (!isparameter)
		{
			defaultConditions.add(condition);
		}
		return pQuery;
	}

	/**
	 * @param condition condition
	 * @param parameterList parameter List
	 * @param isParameter is parameter
	 * @return ifParameter
	 */
	private boolean isParameter(ICondition condition,
			List<IParameter<?>> parameterList, boolean isParameter)
	{
		boolean ifParameter = isParameter;
		for (IParameter<?> parameter : parameterList)
		{
			if (parameter.getParameterizedObject() instanceof ICondition)
			{
				ICondition paramCondition = (ICondition) parameter
						.getParameterizedObject();
				if (paramCondition.getId().equals(condition.getId()))
				{
					ifParameter = true;
				}
			}
		}
		return ifParameter;
	}

	/**
	 * @param rule rule
	 * @param removalList removal List
	 * @param defaultConditions default Conditions
	 */
	private void removeUnwantedConditions(IRule rule,
			List<ICondition> removalList,
			List<ICondition> defaultConditions)
	{
		for (ICondition removalEntity : removalList)
		{
			if (!defaultConditions.contains(removalEntity))
			{
				rule.removeCondition(removalEntity);
			}
		}
	}

	/**
	 * This Method generates component name as attributeName_attributeId.
	 * @param expressionId Expression Id
	 * @param attribute attribute
	 * @return component Id in the form of string
	 */
	private String generateComponentName(int expressionId, AttributeInterface attribute)
	{
		StringBuffer componentId = new StringBuffer();
		String attributeName = "";
		attributeName = attribute.getName();
		if(expressionId == -1)
		{
			componentId = componentId.append(attributeName).append(attribute.getId().toString());
		}
		else
		{
			componentId = componentId.append(attributeName).append(expressionId);
		}
		return componentId.toString();
	}
}