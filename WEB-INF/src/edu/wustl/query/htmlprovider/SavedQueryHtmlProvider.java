
package edu.wustl.query.htmlprovider;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.ITerm;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TermType;
import edu.wustl.common.querysuite.queryobject.TimeInterval;
import edu.wustl.common.querysuite.queryobject.impl.DateLiteral;
import edu.wustl.common.querysuite.queryobject.impl.DateOffsetLiteral;
import edu.wustl.common.querysuite.queryobject.impl.NumericLiteral;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.utils.QueryUtility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import edu.wustl.query.util.querysuite.TemporalQueryUtility;

/**
 * This class generated html for saved and temporal queries.
 */

public class SavedQueryHtmlProvider
{

	/**
	 * constant for TOTAL_CF.
	 */
	public static final String TOTAL_CF = "totalCF";
	/**
	 * constant used for STR_FORM_TQ.
	 */
	public static final String STR_FORM_TQ = "strToFormTQ";
	/**
	 * constant for td tag.
	 */
	public static final String tdtag = "</td>";
	/**
	 * constant for td text box.
	 */
	public static final String tdText = "<td class='content_txt' width=\"10%\">";
	/**
	 * constant for block display.
	 */
	public static final String blockDisplay = "block";
	/**
	 * constant for Calendar.
	 */
	public static final String calendarString = "Calendar";

	/*public static final String STR_FORM_TQ = "strToFormTQ";
	public static final String STR_FORM_TQ = "strToFormTQ";
	public static final String STR_FORM_TQ = "strToFormTQ";*/

	/**
	 * This method retrieve expressionid , entity and conditions from the query
	 * object.
	 *
	 * @param queryObject
	 *            IQuery
	 * @param isShowAll
	 *            boolean
	 * @param forPage
	 *            page from where request is made (Saved query/Execute query)
	 * @param customformulaIndexMap
	 *            map of custom formula
	 * @param generateHtmlDetails
	 *            information required to generate html
	 * @param isReadOnly true if read only
	 * @return generated html
	 * @throws PVManagerException
	 *             exception for VI
	 */
	public String getHTMLForSavedQuery(IQuery queryObject, boolean isShowAll, String forPage,
			Map<Integer, ICustomFormula> customformulaIndexMap,
			GenerateHTMLDetails generateHtmlDetails, boolean isReadOnly) throws PVManagerException
	{
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);
		if (queryObject.getId() == null)
		{
			generateHTMLDetails.setQueryId("");
		}
		else
		{
			generateHTMLDetails.setQueryId(queryObject.getId().toString());
		}
		HtmlProvider generateHtml = new HtmlProvider(generateHTMLDetails);
		StringBuffer htmlString = new StringBuffer();
		List<IParameter<?>> parameterList = null;
		Map<ICustomFormula, String> customformulaColumNameMap = getCustomFormulaColumnNameMap(
				queryObject, forPage);
		if (queryObject instanceof ParameterizedQuery)
		{
			ParameterizedQuery pQuery = (ParameterizedQuery) queryObject;
			parameterList = pQuery.getParameters();
		}
		Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap =
			createExpressionMap(queryObject);

		if ((!(forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE)) || !(parameterList.isEmpty())))
		{
			htmlString.append(createTableForSavedQuery(forPage));
			htmlString.append(generateHtml.getHtmlForSavedQuery(expressionMap, isShowAll, forPage,
					parameterList, isReadOnly));
			//Get generateHtmlDetailsObject from htmlProvider
			if (generateHtmlDetails != null)
			{
				generateHtmlDetails.setEnumratedAttributeMap(generateHtml.getGenerateHTMLDetails()
						.getEnumratedAttributeMap());
			}
			htmlString.append(generateHTMLForTemporalSavedQuery(customformulaColumNameMap, forPage,
					customformulaIndexMap, queryObject, isReadOnly));
			htmlString.append("</table>");
		}

		return htmlString.toString();
	}

	/**
	 * This method creates expression map.
	 * @param queryObject query object
	 * @return expressionMap expression Map
	 */
	private Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> createExpressionMap(
			IQuery queryObject)
	{
		Map<Integer, Map<QueryableObjectInterface, List<ICondition>>>
		expressionMap=new HashMap<Integer, Map<QueryableObjectInterface, List<ICondition>>>();
		IConstraints constraints = queryObject.getConstraints();
		for (IExpression expression : constraints)
		{
			for (int i = 0; i < expression.numberOfOperands(); i++)
			{
				IExpressionOperand operand = expression.getOperand(i);
				QueryableObjectInterface entity = expression.getQueryEntity()
						.getDynamicExtensionsEntity();
				if (operand instanceof IRule)
				{
					IRule ruleObject = (IRule) operand;
					List<ICondition> conditions = edu.wustl.common.util.Collections
							.list(ruleObject);
					Map<QueryableObjectInterface, List<ICondition>> entityConditionMap =
						new HashMap<QueryableObjectInterface, List<ICondition>>();
					entityConditionMap.put(entity, conditions);
					expressionMap.put(expression.getExpressionId(), entityConditionMap);
				}
			}
		}
		return expressionMap;
	}

	/**
	 * Gets map of custom formula and column name.
	 * @param queryObject IQuery
	 * @param forPage String
	 * @return map
	 */
	private Map<ICustomFormula, String> getCustomFormulaColumnNameMap(IQuery queryObject,
			String forPage)
	{
		List<IOutputTerm> outputTerms = queryObject.getOutputTerms();
		Set<ICustomFormula> customFormulas = null;
		if (forPage.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE))
		{
			customFormulas = QueryUtility.getCustomFormulas(queryObject);
		}
		else if (forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			ParameterizedQuery pQuery = (ParameterizedQuery) queryObject;
			customFormulas = (Set<ICustomFormula>) QueryUtility
					.getAllParameterizedCustomFormulas(pQuery);
		}
		Map<ICustomFormula, String> customformulaColumNameMap = createCustomFormulaColumnNameMap(
				outputTerms, customFormulas);
		return customformulaColumNameMap;
	}

	/**
	 * Creates customformulaColumNameMap.
	 * @param outputTerms list of IOutputTerm
	 * @param customFormulas set of ICustomFormula
	 * @return Map
	 */
	private Map<ICustomFormula, String> createCustomFormulaColumnNameMap(
			List<IOutputTerm> outputTerms, Set<ICustomFormula> customFormulas)
	{
		Map<ICustomFormula, String> customformulaColumNameMap = new HashMap<ICustomFormula, String>();
		if (customFormulas != null)
		{
			for (ICustomFormula customFormula : customFormulas)
			{
				for (IOutputTerm outputTerm : outputTerms)
				{
					if (customFormula.getLhs().equals(outputTerm.getTerm()))
					{
						customformulaColumNameMap.put(customFormula, outputTerm.getName());
					}
				}
			}
		}
		return customformulaColumNameMap;
	}

	/**
	 * @param forPage String
	 * @return html
	 */
	private String createTableForSavedQuery(String forPage)
	{

		StringBuffer generatedHTML = new StringBuffer(
				"<table width='100%' class=\"alterantestrips\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\">");
		String html = "";
		if (forPage.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE))
		{
			html = "<tr class='td_bgcolor_grey' width=\"100%\"><th  valign='top' "
					+ "class='grid_header_text'>"
					+ ApplicationProperties.getValue("savequery.column.userDefined")
					+ "</th><th valign='top' style=\"border-left:3px solid #D4D0C8;\">"
					+ ApplicationProperties.getValue("savequery.column.displayLabel")
					+ "</th><th valign='top' style=\"border-left:3px solid #D4D0C8;\">"
					+ ApplicationProperties.getValue("savequery.column.attributeName")
					+ "</th><th valign='top' style=\"border-left:3px solid #D4D0C8;\">"
					+ ApplicationProperties.getValue("savequery.column.condition")
					+ "</th><th colspan=\"4\"  valign='top' style=\"border-left:3px solid #D4D0C8;\">"
					+ ApplicationProperties.getValue("savequery.column.value") + "</th></tr>";
			generatedHTML.append(html);
		}
		else if (forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			html = "<tr valign='top' class='td_bgcolor_grey' width=\"100%\">"
					+ "<th class=\"grid_header_text\" valign='top' width=\"40%\">"
					+ ApplicationProperties.getValue("savequery.column.displayLabel")
					+ "</th><th style=\"border-left:3px solid #D4D0C8;\""
					+ " valign='top' width=\"20%\" >"
					+ ApplicationProperties.getValue("savequery.column.condition") + "</th><th"
					+ " style=\"border-left:3px solid #D4D0C8;\" "
					+ "colspan=\"4\" valign='top' width=\"40%\">"
					+ ApplicationProperties.getValue("savequery.column.value") + "</th></tr>";
			generatedHTML.append(html);
		}
		return generatedHTML.toString();
	}

	/**
	 * Generate html for temporal query.
	 * @param customformulaColumNameMap Map
	 * @param forPage String
	 * @param customformulaIndexMap Map
	 * @param queryObject IQuery
	 * @param isReadOnly if read only
	 * @return generated html
	 */
	private String generateHTMLForTemporalSavedQuery(
			Map<ICustomFormula, String> customformulaColumNameMap, String forPage,
			Map<Integer, ICustomFormula> customformulaIndexMap, IQuery queryObject,
			boolean isReadOnly)
	{
		StringBuffer generateHTML = new StringBuffer(Constants.MAX_SIZE);
		int count = 0;
		String totalCfId = "";
		String strToFormTQId = "";
		String html = "";
		String componentId = "";

		if (customformulaColumNameMap.isEmpty())
		{
			if (queryObject.getId() == null)
			{
				//This is the case of saving a new query with Custom formula
				componentId = String.valueOf(count);
				totalCfId = TOTAL_CF;
				strToFormTQId = STR_FORM_TQ;
			}
			else
			{
				//This is already saved Query
				componentId = count + "_" + queryObject.getId().longValue();
				totalCfId = TOTAL_CF + "_" + queryObject.getId().longValue();
				strToFormTQId = STR_FORM_TQ + "_" + queryObject.getId().longValue();
			}
		}
		else
		{
			StringBuffer htmlBuffer = null;
			for (ICustomFormula customFormula : customformulaColumNameMap.keySet())
			{
				htmlBuffer = new StringBuffer("");
				String operator = customFormula.getOperator().getStringRepresentation();
				componentId = "";
				if (queryObject.getId() == null)
				{
					//This is the case of saving a new query with Custom formula
					componentId = String.valueOf(count);
					totalCfId = TOTAL_CF;
					strToFormTQId = STR_FORM_TQ;
				}
				else
				{
					//This is already saved Query
					componentId = count + "_" + queryObject.getId().longValue();
					totalCfId = TOTAL_CF + "_" + queryObject.getId().longValue();
					strToFormTQId = STR_FORM_TQ + "_" + queryObject.getId().longValue();
				}

				generateHTML.append("\n<tr>");
				if (forPage.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE))
				{
					boolean isParametrized = HtmlUtility.getParameterForCondition(customFormula,
							((IParameterizedQuery) queryObject).getParameters()) == null
							? false
							: true;
					String isDisabled = "disabled";
					if(isParametrized)
					{ 
						isDisabled = "enabled";
					}
					htmlBuffer = htmlBuffer.append(" ").append(
					GenerateHtml.generateCheckBox(componentId, isParametrized)).append(
					"<td valign='top' align='left' class='content_txt'>").append(
					"<label for='" + componentId + "_displayName' title='").append(
					customformulaColumNameMap.get(customFormula)).append("'>").append(
					"<input type=\"textbox\"  class=\"formFieldSized20\"  name='").append(
					componentId).append("_displayName' id='").append(componentId).append(
					"_displayName' value='").append(
					customformulaColumNameMap.get(customFormula)).append(
					"'"+isDisabled+ "> ").append("</label></td>");
					html = htmlBuffer.toString();
					generateHTML.append(html);
					html = "<td></td>";
					generateHTML.append(html);
				}
				else if (forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
				{
					htmlBuffer = htmlBuffer.append("<td valign='top' align='left' ").append(
					"class='content_txt' nowrap='nowrap' width=\"15%\">").append(
					customformulaColumNameMap.get(customFormula)).append(" ");
					html = htmlBuffer.toString();
					generateHTML.append(html);
					generateHTML.append(tdtag);
				}
				getHtmlForTerm(generateHTML, customFormula, operator, componentId, isReadOnly);
				customformulaIndexMap.put(count, customFormula);
				count++;
			}
		}
		html = "<input type='hidden' id='" + totalCfId + "' value='"
				+ customformulaColumNameMap.keySet().size() + "' />";
		generateHTML.append(html);
		generateHTML.append("<input type='hidden' id='" + strToFormTQId
				+ "' value='' name='strToFormTQ'/>");
		return generateHTML.toString();
	}

	/**
	 * Get html for each term in temporal query.
	 * @param generateHTML StringBuffer
	 * @param customFormula ICustomFormula
	 * @param operator String
	 * @param isReadOnly true if read only
	 * @param componentId String
	 */
	private void getHtmlForTerm(StringBuffer generateHTML, ICustomFormula customFormula,
			String operator, String componentId, boolean isReadOnly)
	{
		List<String> operatorList = TemporalQueryUtility.getRelationalOperators();
		TermType termType = customFormula.getLhs().getTermType();
		String operatorComponentId = componentId;
		generateHTML.append(GenerateHtml.generateHTMLForOperator(operatorComponentId, operator,
				operatorList, "combobox", termType, isReadOnly));
		generateHTML.append(getHtmlDSInterval(componentId, customFormula, operator, isReadOnly));
		boolean isTimeStamp = getHtmlTimestamp(generateHTML, componentId, operator, customFormula,
				isReadOnly);
		generateHTML.append(getHtmlNumeric(componentId, customFormula, operator, isReadOnly));
		String html = "<td valign=\"top\"></td>" + "<input type='hidden' id='isTimeStamp_"
				+ componentId + "' value='" + isTimeStamp + "' />" + "\n</tr>";
		generateHTML.append(html);
	}

	/**
	 * This method generates html for temporal query with numeric literal.
	 * @param componentId component id in html
	 * @param customFormula ICustomFormula
	 * @param operator operator in condition
	 * @param isReadOnly true if read only
	 * @return generated html
	 */
	private StringBuffer getHtmlNumeric(String componentId, ICustomFormula customFormula,
			String operator, boolean isReadOnly)
	{
		StringBuffer generateHTML = new StringBuffer();
		TermType termType = customFormula.getLhs().getTermType();
		ITerm term = customFormula.getAllRhs().get(0);
		String displayTextbox1 = "none";
		if (termType.equals(TermType.Numeric))
		{
			NumericLiteral operand = (NumericLiteral) term.getOperand(0);
			String html = tdText;
			generateHTML.append(html);
			if (isReadOnly)
			{
				generateHTML.append(operand.getNumber());
				generateHTML.append(tdtag);
				if (operator.equals(RelationalOperator.Between.getStringRepresentation()))
				{
					ITerm secondTerm = customFormula.getAllRhs().get(1);
					operand = (NumericLiteral) secondTerm.getOperand(0);
					generateHTML.append(tdText);
					generateHTML.append(operand.getNumber());
					generateHTML.append(tdtag);
				}
			}
			else
			{
				html = "<input class='textfield' style=\"width:150px; display:block;\" "
				+ "type='text' value='" + operand.getNumber() + "' id='" + componentId
				+ "_textBox" + "' />";
				generateHTML.append(html);
				generateHTML.append(tdtag);
				if (operator.equals(RelationalOperator.Between.getStringRepresentation()))
				{
					ITerm secondTerm = customFormula.getAllRhs().get(1);
					operand = (NumericLiteral) secondTerm.getOperand(0);
					displayTextbox1 = blockDisplay;
				}
				html = tdText;
				generateHTML.append(html);
				html = "<input class='textfield' style=\"width:150px; display:" + displayTextbox1
				+ ";\" " + "type=\"text\" value='" + operand.getNumber() + "' id=\""
				+ componentId + "_textBox1" + "\" />";
				generateHTML.append(html);
				generateHTML.append(tdtag);
			}
		}
		return generateHTML;
	}

	/**
	 * Get html for timestamp attribute.
	 * @param generateHTML generated html
	 * @param componentId component id
	 * @param oper Operator
	 * @param customFormula ICustomFormula
	 * @param isReadOnly true if read only
	 * @return isTimeStamp
	 */
	private boolean getHtmlTimestamp(StringBuffer generateHTML, String componentId, String oper,
			ICustomFormula customFormula, boolean isReadOnly)
	{
		boolean isTimeStamp = false;
		String displayTextbox1 = "none";
		TermType termType = customFormula.getLhs().getTermType();
		ITerm term = customFormula.getAllRhs().get(0);
		if (termType.equals(TermType.Timestamp))
		{
			DateLiteral operand = (DateLiteral) term.getOperand(0);
			String textBoxId = calendarString + componentId + "_textBox";
			String calendarId = calendarString + componentId + "_calendar";
			generateHTML.append("<td valign=\"top\" class='content_txt'>");
			SimpleDateFormat format = new SimpleDateFormat(QueryModuleConstants.DATE_FORMAT);
			String date = "";
			if (operand.getDate() != null)
			{
				date = format.format(operand.getDate());
			}
			if (isReadOnly)
			{
				generateHTML.append(getReadOnlyTimestampTerm(oper, customFormula, date));
			}
			else
			{
				String html = "<input style=\"width:150px; display:block;\" value= '" + date
				+ "'type=\"text\" name=\"" + componentId + "\" id='" + textBoxId + "'>";
				generateHTML.append(html);
				generateHTML.append(tdtag);
				String imgStr = "\n<img id=\"calendarImg\" "
						+ "src=\"images/advancequery/calendar.gif\" width=\"24\" "
						+ "height=\"22\"" + " border=\"0\"  onclick='scwShow(" + textBoxId
						+ ",event);'>";
				String innerStr = "\n<td width='3%' valign='top' id=\"" + calendarId + "\">" + "\n"
						+ imgStr;
				generateHTML.append(innerStr);
				date = "";
				if (oper.equals(RelationalOperator.Between.getStringRepresentation()))
				{
					displayTextbox1 = blockDisplay;
					ITerm secondTerm = customFormula.getAllRhs().get(1);
					operand = (DateLiteral) secondTerm.getOperand(0);
					if (operand.getDate() != null)
					{
						date = format.format(operand.getDate());
					}
				}
				textBoxId = calendarString + componentId + "_textBox1";
				calendarId = calendarString + componentId + "_calendar1";
				generateHTML.append("<td valign=\"top\">");
				html = "<input style=\"width:150px; display:" + displayTextbox1 + ";\" value= '"
				+ date + "'type=\"text\" name=\"" + componentId + "\" id=\"" + textBoxId
				+ "\">";
				generateHTML.append(html);
				generateHTML.append(tdtag);
				imgStr = "\n<img id=\"calendarImg\" " + "src=\"images/advancequery/calendar.gif\" "
						+ "width=\"24\" height=\"22\"" + " border=\"0\"  onclick='scwShow("
						+ textBoxId + ",event);'>";
				innerStr = "\n<td width='3%' valign='top' style=\"display:" + displayTextbox1
						+ ";\" id=\"" + calendarId + "\">" + "\n" + imgStr;
				generateHTML.append(innerStr);
			}
			isTimeStamp = true;

		}
		return isTimeStamp;
	}

	/**
	 * This method gets html for timestamp type of temporal query.
	 * @param oper string
	 * @param customFormula ICustomFormula
	 * @param date date
	 * @return html
	 */
	private String getReadOnlyTimestampTerm(String oper, ICustomFormula customFormula, String date)
	{
		StringBuffer generateHTML = new StringBuffer(Constants.MAX_SIZE);
		SimpleDateFormat format = new SimpleDateFormat(QueryModuleConstants.DATE_FORMAT);
		DateLiteral operand;
		generateHTML.append(date);
		generateHTML.append("&nbsp;&nbsp;&nbsp");
		generateHTML.append(tdtag);
		if (oper.equals(RelationalOperator.Between.getStringRepresentation()))
		{
			ITerm secondTerm = customFormula.getAllRhs().get(1);
			operand = (DateLiteral) secondTerm.getOperand(0);
			if (operand.getDate() != null)
			{
				date = format.format(operand.getDate());
			}
			generateHTML.append("<td valign=\"top\" class='content_txt'>");
			generateHTML.append(date);
			generateHTML.append(tdtag);
		}
		return generateHTML.toString();
	}

	/**
	 * This method gets html for DSInterval.
	 * @param componentId String
	 * @param customFormula ICustomFormula
	 * @param oper String
	 * @param isReadOnly true if read only
	 * @return generated html
	 */
	private StringBuffer getHtmlDSInterval(String componentId, ICustomFormula customFormula,
			String oper, boolean isReadOnly)
	{
		StringBuffer generateHTML = new StringBuffer();
		TermType termType = customFormula.getLhs().getTermType();
		ITerm term = customFormula.getAllRhs().get(0);
		String displayTextbox1 = "none";
		if (termType.equals(TermType.DSInterval))
		{
			List<String> operatorList;
			DateOffsetLiteral operand = (DateOffsetLiteral) term.getOperand(0);
			TimeInterval<?> timeInterval = operand.getTimeInterval();
			StringBuffer operator = new StringBuffer(timeInterval.toString());
			operator.append('s');
			operatorList = TemporalQueryUtility.getTimeIntervals();
			String html = tdText;
			generateHTML.append(html);
			if (isReadOnly)
			{
				generateHTML.append(operand.getOffset());
				generateHTML.append(tdtag);
				generateHTML.append("<td valign='top' class='content_txt' >");
				generateHTML.append(operator.toString());
				generateHTML.append(tdtag);
				if (oper.equals(RelationalOperator.Between.getStringRepresentation()))
				{
					ITerm secondTerm = customFormula.getAllRhs().get(1);
					operand = (DateOffsetLiteral) secondTerm.getOperand(0);
					timeInterval = operand.getTimeInterval();
					operator = new StringBuffer(timeInterval.toString());
					operator.append('s');
					html = tdText;
					generateHTML.append(html);
					html = operand.getOffset();
					generateHTML.append(html);
					generateHTML.append(tdtag);
					generateHTML.append("<td width='15%' valign='top' class='content_txt'>");
					generateHTML.append(operator.toString());
					generateHTML.append(tdtag);
				}
			}
			else
			{
				html = "<input class='textfield' style=\"width:150px; display:block;\" "
				+ "type=\"text\" value='" + operand.getOffset() + "' id=\"" + componentId
						+ "_textBox" + "\" />";
				generateHTML.append(html);
				generateHTML.append(tdtag);
				generateHTML.append(GenerateHtml.generateHTMLForTemporalOperator(componentId,
						operator.toString(), operatorList, "combobox1", "block"));
				if (oper.equals(RelationalOperator.Between.getStringRepresentation()))
				{
					displayTextbox1 = blockDisplay;
					ITerm secondTerm = customFormula.getAllRhs().get(1);
					operand = (DateOffsetLiteral) secondTerm.getOperand(0);
					timeInterval = operand.getTimeInterval();
					operator = new StringBuffer(timeInterval.toString());
					operator.append('s');
					operatorList = TemporalQueryUtility.getTimeIntervals();
				}
				html = tdText;
				generateHTML.append(html);
				html = "<input class='textfield' style=\"width:150px; display:" + displayTextbox1
				+ ";\" " + "type=\"text\" value='" + operand.getOffset() + "' id=\""
				+ componentId + "_textBox1" + "\" />";
				generateHTML.append(html);
				generateHTML.append(tdtag);
				generateHTML.append(GenerateHtml.generateHTMLForTemporalOperator(componentId,
						operator.toString(), operatorList, "combobox2", displayTextbox1));
			}
		}
		return generateHTML;
	}

	/**
	 * This method generates html for constraints in the query
	 * @param queryObject : Query Object
	 * @throws PVManagerException thrown
	 * @return Returns the final HTML string
	 */
	public StringBuffer getHtmlforSavedConditions(IQuery queryObject) throws PVManagerException
	{
		StringBuffer headerhtmlString = new StringBuffer("");
		List<IParameter<?>> parameterList = null;
		ParameterizedQuery pQuery = null;
		if (queryObject instanceof ParameterizedQuery)
		{
			pQuery = (ParameterizedQuery) queryObject;
			parameterList = pQuery.getParameters();
		}
		/*
		  Expression map is created for each expression ID, containing a
		  map for all conditions of a expression in a list
		*/
		Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap =
			createExpressionMap((ParameterizedQuery) queryObject);
		headerhtmlString.append(createTableForSavedQuery(Constants.EXECUTE_QUERY_PAGE));

		/*
		 * Here i generate the html for query constraints for which user
		 * has added limits
		 */
		 StringBuffer htmlString = getHtmlForConstraints(parameterList,expressionMap);

		//Here i am dealing with custom formulas
		Set<ICustomFormula> nonParameterizedCustomFormulas = getNonParamCustomFormulas(pQuery);
		//Here i need to create map
		List<IOutputTerm> outputTerms = queryObject.getOutputTerms();
		Map<ICustomFormula, String> customformulaColumNameMap = createCustomFormulaColumnNameMap(
				outputTerms, nonParameterizedCustomFormulas);
		htmlString.append(getHtmlForNonParameterizedCustomFormulas(customformulaColumNameMap,
				pQuery));
		if (htmlString.length()!=0)
		{
			headerhtmlString.append(htmlString);
			headerhtmlString.append("</table>");
		}
		else
		{
			//Return empty string
			headerhtmlString = new StringBuffer("");
		}
		return headerhtmlString;
	}

	/**
	 * This method generates html for added constraints.
	 * @param parameterList : Parameter list in query
	 * @param expressionMap : expression map in generated out of query object
	 * @throws PVManagerException exception thrown
	 * @return The generated html for Constraints is returned
	 */
	private StringBuffer getHtmlForConstraints(
			List<IParameter<?>> parameterList,
			Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap)
			throws PVManagerException
	{
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);
		HtmlProvider generateHtml = new HtmlProvider(generateHTMLDetails);
		Map<QueryableObjectInterface, List<ICondition>> entityConditionMap = null;
		Iterator<Map.Entry<Integer, Map<QueryableObjectInterface, List<ICondition>>>> entryItr =
			expressionMap.entrySet().iterator();
		StringBuffer htmlString = new StringBuffer("");
		while (entryItr.hasNext())
		{
			//Getting the entity condition map from expression map
			Map.Entry<Integer, Map<QueryableObjectInterface, List<ICondition>>> entry = entryItr
					.next();
			//entityConditionMap is the entity condition Map
			entityConditionMap = entry.getValue();
			if (entityConditionMap == null || entityConditionMap.isEmpty())
			{
				continue;
			}
			//Getting the list of all conditions for each expression
			Iterator<Map.Entry<QueryableObjectInterface, List<ICondition>>> inerEntryItr =
				entityConditionMap.entrySet().iterator();
			while (inerEntryItr.hasNext())
			{
				Map.Entry<QueryableObjectInterface, List<ICondition>> entry1 = inerEntryItr.next();
				//get all condition for an expression
				List<ICondition> conditions = entry1.getValue();
				int expressionID = entry.getKey().intValue();
				QueryableObjectInterface entity = entry1.getKey();

				htmlString.append(generateHtml.getHtmlForSavedConditons(expressionID, entity,
						conditions, parameterList));
			}
		}
		return htmlString;
	}

	/**
	 * This method returns the map of non parameterized
	 * custom formulas.
	 * @param pQuery : Query Object
	 * @return Set of non parameterized custom formulas
	 */
	private Set<ICustomFormula> getNonParamCustomFormulas(ParameterizedQuery pQuery)
	{
		//Here i get all the custom formulas in query
		Set<ICustomFormula> customFormulas = QueryUtility.getCustomFormulas(pQuery);

		//Here i get only parameterized custom formulas in query
		Set<ICustomFormula> pcustomFormulas = (Set<ICustomFormula>) QueryUtility
				.getAllParameterizedCustomFormulas(pQuery);

		//Here i need to separate out custom formulas which are not parameterized
		Set<ICustomFormula> nonParameterizedCustomFormulas = new HashSet<ICustomFormula>();
		if (!pcustomFormulas.isEmpty())
		{
			separateCustomFormulas(customFormulas, pcustomFormulas, nonParameterizedCustomFormulas);
		}
		else
		{
			nonParameterizedCustomFormulas.addAll(customFormulas);
		}
		return nonParameterizedCustomFormulas;
	}

	/**
	 * This method separates out custom formulas
	 * whether parameterized or not
	 * @param customFormulas : Set for all custom formulas in query
	 * @param pcustomFormulas : Set for parameterized custom formulas
	 * @param nonParameterizedCustomFormulas : Set for non parameterized custom formulas
	 */
	private void separateCustomFormulas(Set<ICustomFormula> customFormulas,
			Set<ICustomFormula> pcustomFormulas, Set<ICustomFormula> nonParameterizedCustomFormulas)
	{
		for (ICustomFormula customFormula : customFormulas)
		{
			boolean matchFound = false;
			Iterator<ICustomFormula> itr = pcustomFormulas.iterator();
			while (itr.hasNext())
			{
				ICustomFormula pCustomFormula = itr.next();
				if (customFormula.getId().longValue() == pCustomFormula.getId().longValue())
				{
					matchFound = true;
					break;
				}
			}
			//If match not found, add it to the non parameterized custom formula set
			if (!matchFound)
			{
				nonParameterizedCustomFormulas.add(customFormula);
			}
		}
	}

	/**
	 * This method returns the html for non parameterized
	 * custom formulas.
	 * @param customformulaColumNameMap : Custom Formulas column name map
	 * @param queryObj : Query Object
	 * @return : This method returns the generated HTML for non parameterized custom
	 * formulas
	 */
	private StringBuffer getHtmlForNonParameterizedCustomFormulas(
			Map<ICustomFormula, String> customformulaColumNameMap, IQuery queryObj)
	{
		StringBuffer generateHTML = new StringBuffer(Constants.MAX_SIZE);
		int count = 0;
		String componentId = "";

		if (!customformulaColumNameMap.isEmpty())
		{
			StringBuffer htmlBuffer = null;
			Iterator<Map.Entry<ICustomFormula,String>> customFormulaEntryItr =
			customformulaColumNameMap.entrySet().iterator();
			while(customFormulaEntryItr.hasNext())
			{
				Map.Entry<ICustomFormula,String> entry = customFormulaEntryItr.next();
				ICustomFormula customFormula = entry.getKey();
				htmlBuffer = new StringBuffer("");
				String operator = customFormula.getOperator().getStringRepresentation();
				//componentId = "";
				componentId = ""+count; //+ "_" + queryObj.getId().longValue();
				generateHTML.append("\n<tr>");
				htmlBuffer = htmlBuffer.append("<td valign='top' align='left' ").append(
						"class='content_txt' nowrap='nowrap' width=\"15%\">").append(
						customformulaColumNameMap.get(customFormula)).append(" ");
				generateHTML.append(htmlBuffer.toString());
				generateHTML.append(tdtag);
				getHtmlForTerm(generateHTML, customFormula, operator, componentId, true);
				count++;
			}
		}
		return generateHTML;
	}
	/**
	 * This method add html div tags for generated htmls
	 * @param queryhtmlContents : generated html for parameters
	 * @param constrHtmlContents : generated html for constraints
	 * @return Returns final generated HTML String
	 */
	public StringBuffer generateHtmlDivs(String queryhtmlContents, StringBuffer constrHtmlContents)
	{
		StringBuffer genaratedHtml = new StringBuffer(Constants.MAX_SIZE);
		genaratedHtml.append("<table width=\"100%\" >");
		if ((queryhtmlContents != null) && (!queryhtmlContents.equals("")))
		{
			genaratedHtml
			.append("<tr  class=\"td_subtitle\" width='100%'><td>" +
			"<div id=\"paramHeaderDiv\"  class=\"showhandcursor blueHeader blue_title\" " +
			"height=\"10px\">" +"<img border=\"0\" " +
			"src=\"images/advancequery/arrow_open.png\" id=\"paramImg\"/>&nbsp;" +
			"Parameterized Condition </div></td></tr>"+"<tr class=\"td_greydottedline_horizontal\">" +
			"<td colspan=\"2\" width=\"100%\"></td></tr>"+
			"<tr width='100%'><td><div id=\"paramDiv\"  style=\"overflow:auto;\"" +
			"class = \"content_txt\">").append(queryhtmlContents).append("</div></td></tr>");
		}
		if ((constrHtmlContents != null) && (constrHtmlContents.length()!=0))
		{

			genaratedHtml
			.append("<tr  class=\"td_subtitle\" width='100%'><td>" +
			"<div id=\"constrHeaderDiv\" class=\"showhandcursor blueHeader blue_title\" " +
			"height=\"10px\"> " +"<img border=\"0\" " +
			"src=\"images/advancequery/arrow_close.png\" id=\"constrImg\"/>&nbsp;" +
			"Constraints </div></td></td>" +"<tr class=\"td_greydottedline_horizontal\">"+
			"<td colspan=\"2\" width=\"100%\"></td></tr>"+
			"<tr width='100%'><td><div id=\"constrDiv\" style=\"overflow:auto;\""+
			"class = \"content_txt\">").append(constrHtmlContents).append("</div></td></td>");
		}
		genaratedHtml.append("</table>");
		return genaratedHtml;
	}
}
