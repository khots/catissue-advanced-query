
package edu.wustl.query.htmlprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.TermType;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Constants;

/**
 * This class generates html for text box, radio button and
 * other components for the Add Limits section of Query Page.
 * @author rukhsana_sameer
 */
public class GenerateHtml
{

	/**
	 * End tag for html tag td.
	 */
	public static final String endTD = "\n</td>";

	/**
	 * css with white background.
	 */
	public static final String CSS_BGWHITE = "rowBGWhiteColor";
	/**
	 * css with grey background.
	 */
	public static final String CSS_BGGREY = "rowBGGreyColor1";
	/**
	 * css for text box.
	 */
	public static final String CSS_TEXT = "content_txt";
	/**
	 * css for permissible values.
	 */
	public static final String CSS_PV = "content_txt";
	/**
	 * css for highlighted background.
	 */
	public static final String CSS_HIGHLIGHT = "td_blue_highlight";
	/**
	 * TD start tag.
	 */
	private static final String startTD = "\n<td class='";
	/**
	 * Start Input tag.
	 */
	private static String startInput = "<input type=\"text\" class=\"textfield\" name=\"";
	/**
	 * ID tag.
	 */
	private static String idTag = "\" id=\"";
	/**
	 * Value tag.
	 */
	private static String valueTag = "\" value=\"";
	/**
	 * Html for input type textbox.
	 */
	private static String inputTag = "<input style=\"width:150px; display:block;\" class=\"textfield\" "
			+ "type=\"text\" onkeyup = \"validateInput(this,event)\" name=\"";
	/**
	 * End tag.
	 */
	private static String endTag = "\">";
	/**
	 * html style tag with width and display.
	 */
	private static String styleTag = " style=\"width:150px; display:block;\" class=\"textfield\" name=\"";
	/**
	 * Constant for comma.
	 */
	private static String comma = "','";
	/**
	 * Constant for select.
	 */
	private static String selectTag = "\n</select>";

	/**
	 * This method generates html for RadioButton.
	 * @param componentId
	 * 		String
	 * @param values
	 * 		List values
	 * @return String
	 */
	public static String generateHTMLForRadioButton(String componentId, List<String> values)
	{
		String cssClass = CSS_TEXT;
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
		html.append(startTD + cssClass + "' >");
		if (values == null)
		{
			html.append(getHtmlRadioButton(componentId, cssClass, true, ""));
			html.append(getHtmlRadioButton(componentId, cssClass, false, ""));
		}
		else
		{
			if (values.get(0) == null)
			{
				html.append(getHtmlRadioButton(componentId, cssClass, true, ""));
				html.append(getHtmlRadioButton(componentId, cssClass, false, ""));
			}
			else
			{
				getHtmlForValuesNotNull(componentId, values, cssClass, html);
			}
		}
		html.append(endTD);
		html.append(startTD + cssClass + "'>&nbsp;");
		html.append(endTD);
		html.append(startTD + cssClass + "'>&nbsp;");
		html.append(endTD);
		html.append(startTD + cssClass + "'>&nbsp;");
		html.append(endTD);

		return html.toString();
	}

	/**
	 *
	 * @param componentId id of component
	 * @param cssClass name of css class
	 * @param isRadioButtonTrue boolean
	 * @param checked String
	 * @return String
	 */
	private static String getHtmlRadioButton(String componentId, String cssClass,
			boolean isRadioButtonTrue, String checked)
	{
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
		String componentName = componentId + "_booleanAttribute";
		String radioButtonTrueId = componentId + Constants.UNDERSCORE + Constants.TRUE;// "_true";
		String radioButtonFalseId = componentId + Constants.UNDERSCORE + Constants.FALSE;// "_false"
		String buttonId = radioButtonFalseId;
		String name = "False";
		String value = Constants.FALSE;
		if (isRadioButtonTrue)
		{
			buttonId = radioButtonTrueId;
			name = "True";
			value = Constants.TRUE;
		}
		html
				.append("\n<input type='radio' id = '" + componentId + "_" + value + "' value='"
						+ value + "' onclick=\"resetOptionButton('" + buttonId + "',this)\" name='"
						+ componentName + checked + "'/><font class='" + cssClass + "'>" + name
						+ "</font>");
		return html.toString();
	}

	/**
	 * Generates html for radio button depending upon value of radio button.
	 * @param componentId generated component id
	 * @param values list of values
	 * @param cssClass css class name
	 * @param html generated html
	 */
	private static void getHtmlForValuesNotNull(String componentId, List<String> values,
			String cssClass, StringBuffer html)
	{
		if (values.get(0).equalsIgnoreCase(Constants.TRUE))
		{
			html.append(getHtmlRadioButton(componentId, cssClass, true, "checked"));
			html.append(getHtmlRadioButton(componentId, cssClass, false, ""));
		}
		else if (values.get(0).equalsIgnoreCase(Constants.FALSE))
		{
			html.append(getHtmlRadioButton(componentId, cssClass, true, ""));
			html.append(getHtmlRadioButton(componentId, cssClass, false, "checked"));
		}
		else
		{
			html.append(getHtmlRadioButton(componentId, cssClass, true, ""));
			html.append(getHtmlRadioButton(componentId, cssClass, false, ""));
		}
	}

	/**
	 * Generates html for textBox to hold the input for operator selected.
	 *
	 * @param componentId
	 *            String
	 * @param attrDetails
	 *            AttributeDetails
	 * @param attribute QueryableAttributeInterface
	 * @param isReadOnly for view parameters pop up
	 * @return String HTMLForTextBox
	 */
	public static String generateHTMLForTextBox(String componentId, AttributeDetails attrDetails,
			QueryableAttributeInterface attribute, boolean isReadOnly)
	{
		String cssClass = CSS_TEXT;
		//String componentId = generateComponentName(attributeInterface);
		String textBoxId = componentId + "_textBox";
		String textBoxId1 = componentId + "_textBox1";
		//String dataType = attributeInterface.getDataType();
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
		String newLine = "\n";
		html.append("<td width='15%' valign='top' class=\"content_txt\" >\n");
		getHtmlValueAndOperator(attrDetails.getEditValues(), attrDetails.getSelectedOperator(),
				textBoxId, html, isReadOnly);
		html.append(endTD);
		if (attrDetails.getDataType().equalsIgnoreCase(Constants.DATE) && !isReadOnly)
		{
			html.append(newLine)
					.append(generateHTMLForCalendar(componentId, true, false, cssClass));
		}
		else
		{
			html.append("\n<td valign='top' width='1%'>&nbsp;</td>");
		}
		html.append("<td width='15%' align='left' valign='top' class=\"content_txt\">\n");
		if (isBetween(attrDetails))
		{
			getHtmlTextBoxForBetweenOperator(attrDetails.getEditValues(), textBoxId1, html,
					isReadOnly);
		}
		else
		{
			html.append(startInput).append(textBoxId1).append(idTag).append(textBoxId1).append(
					"\" style=\"display:none\">");
		}
		html.append(endTD);
		if (attrDetails.getDataType().equalsIgnoreCase(Constants.DATE) && !isReadOnly)
		{
			html.append(newLine).append(
					generateHTMLForCalendar(componentId, false, isBetween(attrDetails), cssClass));
		}
		else
		{
			html.append("\n<td valign='top' />");
		}
		return html.toString();
	}

	/**
	 *
	 * @param attrDetails AttributeDetails
	 * @return boolean
	 */
	private static boolean isBetween(AttributeDetails attrDetails)
	{
		return (attrDetails.getSelectedOperator() == null && attrDetails.isBetween())
				|| checkBetweenOperator(attrDetails.getSelectedOperator());
	}

	/**
	 * Generate html for text box based upon operator and values.
	 * @param values list of values
	 * @param operator selected operator
	 * @param textBoxId id of text box
	 * @param isReadOnly true if read only
	 * @param html generated html
	 */
	private static void getHtmlValueAndOperator(List<String> values, String operator,
			String textBoxId, StringBuffer html, boolean isReadOnly)
	{
		if (isReadOnly)
		{
			List<String> formattedValues = formatDateValues(values);
			getValue(formattedValues, operator, html);

		}
		else if (values == null || values.isEmpty())
		{
			getHtmlValueNull(operator, textBoxId, html);
		}
		else
		{
			getHtmlValueNotNull(values, operator, textBoxId, html);
		}
	}

	/**
	 * This method formats the date values on recent queries page.
	 * @param values list of string
	 * @return formatted values
	 */
	private static List<String> formatDateValues(List<String> values)
	{
		List<String> dateValues = new ArrayList<String>();
		for (String value : values)
		{
			if (value.indexOf('-') == -1)
			{
				dateValues.add(value);
			}
			else
			{
				dateValues.add(value.replaceAll("-", "/"));
			}
		}
		return dateValues;
	}

	/**
	 *
	 * @param values list of String
	 * @param operator operator in condition
	 * @param html generated html
	 */
	private static void getValue(List<String> values, String operator, StringBuffer html)
	{
		String valueStr;
		if (values == null || values.isEmpty())
		{
			valueStr = "";
		}
		else if (operator.equalsIgnoreCase(Constants.IN)
				|| operator.equalsIgnoreCase(Constants.Not_In))
		{
			valueStr = getValueForInOrNotIn(values);
		}
		else
		{
			valueStr = "";
			if (values.get(0) != null)
			{
				valueStr = values.get(0);
			}
		}
		html.append(valueStr);
	}

	/**
	 * This method gets values for In and Not In operators.
	 * @param values list of string
	 * @return string
	 */
	private static String getValueForInOrNotIn(List<String> values)
	{
		String valueStr = values.toString();
		valueStr = valueStr.replace("[", "");
		valueStr = valueStr.replace("]", "");
		if (values.get(0) == null)
		{
			valueStr = "";
		}
		return valueStr;
	}

	/**
	 *
	 * @param operator selected operator
	 * @param textBoxId id of text box
	 * @param html generated html
	 */
	private static void getHtmlValueNull(String operator, String textBoxId, StringBuffer html)
	{
		String temp;
		if (operator == null)
		{
			temp = inputTag + textBoxId + idTag + textBoxId + endTag;
			html.append(temp);
		}
		else
		{
			if (operator.equalsIgnoreCase(Constants.IS_NOT_NULL)
					|| operator.equalsIgnoreCase(Constants.IS_NULL))
			{
				temp = "<input style=\"width:150px; display:block;\" "
						+ "type=\"text\" class=\"textfield\" disabled='true' name=\"" + textBoxId
						+ idTag + textBoxId + endTag;
				html.append(temp);
			}

		}
	}

	/**
	 * @param values list of values
	 * @param operator selected operator
	 * @param textBoxId id of textbox component
	 * @param html generated html
	 */
	private static void getHtmlValueNotNull(List<String> values, String operator, String textBoxId,
			StringBuffer html)
	{
		if (operator.equalsIgnoreCase(Constants.IN) || operator.equalsIgnoreCase(Constants.Not_In))
		{
			String valueStr = getValueForInOrNotIn(values);
			html.append(inputTag).append(textBoxId).append(idTag).append(textBoxId)
					.append(valueTag).append(valueStr).append(endTag);
		}
		else
		{
			if (values.get(0) == null)
			{
				String temp = inputTag + textBoxId + idTag + textBoxId + valueTag + endTag;
				html.append(temp);
			}
			else
			{
				html.append(inputTag + textBoxId + idTag + textBoxId + valueTag + values.get(0)
						+ endTag);
			}
		}
	}

	/**
	 * Method provides html for text box when operator is IsBetween.
	 * @param values list of values
	 * @param textBoxId1 id of textbox component
	 * @param isReadOnly true in case of read only
	 * @param html generated html
	 */
	private static void getHtmlTextBoxForBetweenOperator(List<String> values, String textBoxId1,
			StringBuffer html, boolean isReadOnly)
	{
		if (isReadOnly)
		{
			String secondValue = values.get(1).replaceAll("-", "/");
			if (values != null && !values.isEmpty() && secondValue != null)
			{
				html.append(secondValue);
			}
		}
		else
		{
			getHtmlBetweenOperator(values, textBoxId1, html);
		}
	}

	/**
	 * This method generates html when it is not in read only mode.
	 * @param values list of values
	 * @param textBoxId1 text box id
	 * @param html generated html
	 */
	private static void getHtmlBetweenOperator(List<String> values, String textBoxId1,
			StringBuffer html)
	{
		if (values == null || values.isEmpty())
		{
			html.append(startInput).append(textBoxId1).append(idTag).append(textBoxId1).append(
					"\" style=\"display:block;width:150px;\">");
		}
		else
		{
			if (values.get(1) == null)
			{
				String temp = startInput + textBoxId1 + idTag + textBoxId1 + valueTag
						+ "\" style=\"display:block;width:150px;\">";
				html.append(temp);
			}
			else
			{
				html.append(startInput + textBoxId1 + idTag + textBoxId1 + valueTag + values.get(1)
						+ "\" style=\"display:block;width:150px;\">");
			}
		}
	}

	/**
	 * Generators html for Calendar.Depending upon the value of operator the
	 * calendar is displayed(hidden/visible).
	 * @param componentId String
	 * @param isFirst
	 *            boolean
	 * @param isBetween
	 *            boolean
	 * @param cssClass
	 * 	       String
	 * @return String HTMLForCalendar
	 */
	private static String generateHTMLForCalendar(String componentId, boolean isFirst,
			boolean isBetween, String cssClass)
	{
		StringBuffer innerStr = new StringBuffer("");
		if (isFirst)
		{
			String textBoxId = componentId + "_textBox";
			String calendarId = componentId + "_calendar";
			String imgStr = "\n<img id=\"calendarImg\" "
					+ "src=\"images/advancequery/calendar.gif\" width=\"24\" height=\"22\""
					+ " border=\"0\" onclick='scwShow(" + textBoxId + ",event);'>";
			innerStr = innerStr.append("\n<td width='1%' class='" + cssClass
					+ "' valign='top' align='left' id=\"" + calendarId + endTag + "\n" + imgStr);
		}
		else
		{
			String textBoxId1 = componentId + "_textBox1";
			String calendarId1 = componentId + "_calendar1";
			String imgStr = "\n<img id=\"calendarImg\" "
					+ "src=\"images/advancequery/calendar.gif\""
					+ " width=\"24\" height=\"22\" border='0'" + " onclick='scwShow(" + textBoxId1
					+ ",event);'>";
			String style = "";
			if (isBetween)
			{
				style = "display:block";
			}
			else
			{
				style = "display:none";
			}
			innerStr = innerStr.append("\n<td width='1%' class='" + cssClass
					+ "' valign='top' id=\"" + calendarId1 + "\" style=\"" + style + endTag + "\n"
					+ imgStr);
		}
		innerStr = innerStr.append(endTD);
		return innerStr.toString();
	}

	/**
	 * Method provides html for Add Limits Header.
	 * @param entityName String
	 * @param entityId String
	 * @param attributeCollection String
	 * @param isEditLimits boolean
	 * @param pageOf pageOf
	 * @return StringBuffer
	 */
	public static StringBuffer getHtmlHeader(String entityName, String entityId,
			String attributeCollection, boolean isEditLimits, String pageOf)
	{
		StringBuffer generatedPreHTML = new StringBuffer(Constants.MAX_SIZE);
		String buttonImg;
		if (isEditLimits)
		{
			buttonImg = "Edit Limits For";
		}
		else
		{
			buttonImg = "Define Limits For";
		}
		//String header = Constants.DEFINE_SEARCH_RULES;
		String html = "<table border=\"0\" width=\"100%\" height=\"28\" "
				+ "background=\"images/advancequery/bg_content_header.gif\" "
				+ "cellspacing=\"0\" cellpadding=\"0\" >" + "\n<tr width=\"100%\">"
				+ "<td style=\"border-bottom: 1px solid #cccccc;padding-left:5px;\" "
				+ "valign='middle' class=\"PageSubTitle\" " + "colspan=\"8\" >" + buttonImg;
		generatedPreHTML.append(html);
		generatedPreHTML.append(" '" + Utility.getDisplayLabel(entityName) + "'");
		generatedPreHTML.append(endTD);
		String buttontd = "<td  style=\"padding-right:5px;\" >"
				+ "<div align=\"right\" border=\"0\"  valign=\"middle\" "
				+ "id=\"AddLimitsButtonRow\">" + "</div></td>";
		generatedPreHTML.append(buttontd);
		generatedPreHTML.append("####");
		generatedPreHTML.append(generateHTMLForButton(entityId, attributeCollection, isEditLimits,
				pageOf));
		generatedPreHTML.append("\n</tr></table>");
		return generatedPreHTML;
	}

	/**
	 * Generates html for button.
	 * @param entityName
	 *            entityName
	 * @param attributesStr
	 *            attributesStr
	 * @param isEditLimits boolean
	 * @return String HTMLForButton
	 */
	private static String generateHTMLForButton(String entityName, String attributesStr,
			boolean isEditLimits)
	{
		//String buttonName = "addLimit";
		String buttonId = "TopAddLimitButton";
		String imgsrc = "images/advancequery/b_edit_limit.gif";
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);

		String temp = "\n<td  colspan=\"2\" " + "height=\"30\" valign=\"top\" align=\"right\" >";
		html.append(temp);
		String buttonCaption = "Add Limit";
		if (isEditLimits)
		{
			buttonCaption = "Edit Limit";
		}
		html.append("\n<a href=\"javascript:produceQuery('" + buttonCaption
				+ "', 'addToLimitSet.do', 'categorySearchForm', '" + entityName + comma
				+ attributesStr + "')\"><img border=\"0\" src=\"" + imgsrc + "\"  id=\"" + buttonId
				+ "\" " + "value=\"" + buttonCaption + "\"/>");
		html.append(endTD + "</a>");
		return html.toString();
	}

	/**
	 * This Method is written to generate button as per the page of getCount and getPatientData.
	 * @param entityName name of entity
	 * @param attributesStr string for all attributes
	 * @param isEditLimits true in case of edit limit
	 * @param pageOf String
	 * @return generated html
	 */
	private static String generateHTMLForButton(String entityName, String attributesStr,
			boolean isEditLimits, String pageOf)
	{
		//String buttonName = "addLimit";
		String buttonId = "TopAddLimitButton";
		String imgsrc; //"images/advancequery/b_add_limit.gif";
		if (Constants.PAGE_OF_GET_DATA.equals(pageOf)) //if pageOf getPatientData
		{
			imgsrc = "images/advancequery/b_add_filters_blue.gif";
		}
		else
		//if pageOf getCount
		{
			imgsrc = "images/advancequery/b_add_limit.gif";
		}

		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);

		//String temp = "\n<td  colspan=\"2\" " + "height=\"30\" valign=\"top\" align=\"right\" >";
		//	html.append(temp);
		String buttonCaption = "Add Limit";
		if (isEditLimits)
		{
			buttonCaption = "Edit Limit";
			if (Constants.PAGE_OF_GET_DATA.equals(pageOf)) //if pageOf getCount
			{
				imgsrc = "images/advancequery/b_edit_filters.gif";
			}
			else
			//if pageOf getPatientData
			{
				imgsrc = "images/advancequery/b_edit_limit.gif";
			}
		}
		html.append("\n<a href=\"javascript:produceQuery('" + buttonCaption
				+ "', 'addToLimitSet.do', 'categorySearchForm', '" + entityName + comma
				+ attributesStr + "')\"><img alt='" + buttonCaption + "' border=\"0\" src=\""
				+ imgsrc + "\"  id=\"" + buttonId + "\" " + "value=\"" + buttonCaption
				+ "\"/> </a>" + endTD);
		return html.toString();
	}

	/**
	 * @param attributeCollection String
	 * @param nameOfTheEntity String
	 * @param isEditLimits boolean
	 * @return StringBuffer
	 */
	public static StringBuffer generatePreHtml(String attributeCollection, String nameOfTheEntity,
			boolean isEditLimits)
	{
		String header = Constants.DEFINE_SEARCH_RULES;
		StringBuffer generatedPreHTML = new StringBuffer(Constants.MAX_SIZE);
		String html = "<table border=\"0\" width=\"100%\" height=\"30%\" "
				+ "cellspacing=\"0\" cellpadding=\"0\">" + "\n<tr height=\"2%\"> "
				+ "<td valign='top' height=\"2%\" colspan=\"8\" "
				+ "bgcolor=\"#EAEAEA\" ><font face=\"Arial\" size=\"2\" " + "color=\"#000000\"><b>";
		generatedPreHTML.append(html);
		generatedPreHTML.append(header + " '" + nameOfTheEntity + "'</b></font>");
		generatedPreHTML.append(endTD);
		generatedPreHTML.append(generateHTMLForButton(nameOfTheEntity, attributeCollection,
				isEditLimits));
		generatedPreHTML.append("\n</tr></table>");
		return generatedPreHTML;
	}

	/**
	 * This method generates the combobox's html to show the operators valid for
	 * the attribute passed to it.
	 * @param componentId
	 *            String
	 * @param isDate
	 *            boolean
	 * @param attributeDetails
	 *            AttributeDetails
	 * @param isReadOnly if read only then true
	 * @return String HTMLForOperators
	 */
	public static String generateHTMLForOperators(String componentId, boolean isDate,
			AttributeDetails attributeDetails, boolean isReadOnly)
	{
		String cssClass = CSS_PV;
		StringBuffer html = new StringBuffer();
		List<String> operatorsList = attributeDetails.getOperatorsList();
		if (isReadOnly)
		{
			html.append("\n<td width='15%' class=" + cssClass + " valign='top' >");
			html.append(attributeDetails.getSelectedOperator());
			html.append(endTD);
		}
		else if (operatorsList != null && !operatorsList.isEmpty())
		{
			html.append("\n<td width='15%' class=" + cssClass + " valign='top' >");
			if (isDate)
			{
				html.append("\n<select   class=\"textfield\"" + styleTag + componentId
						+ "_combobox\" id='"+componentId+"_combobox'" +" onChange=\"operatorChanged('" + componentId
						+ "','true')\">");
			}
			else
			{
				html.append("\n<select  class=\"textfield\"" + styleTag + componentId
						+ "_combobox\" id='"+componentId+"_combobox'"+" onChange=\"operatorChanged('" + componentId
						+ "','false')\">");
			}
			getHtmlForSelectedOperator(attributeDetails, cssClass, html, operatorsList);
			html.append(selectTag);
			html.append(endTD);
		}

		return html.toString();
	}

	/**
	 * Method generates html for selected operator.
	 * @param attributeDetails AttributeDetails
	 * @param cssClass String
	 * @param html StringBuffer
	 * @param operatorsList List
	 */
	private static void getHtmlForSelectedOperator(AttributeDetails attributeDetails,
			String cssClass, StringBuffer html, List<String> operatorsList)
	{
		Iterator<String> iter = operatorsList.iterator();
		String optionEndTag = "</option>";
		while (iter.hasNext())
		{
			String operator = iter.next();
			if (operator.equalsIgnoreCase(attributeDetails.getSelectedOperator()))
			{
				html.append("\n<option  class=" + cssClass + " value=\"" + operator
						+ "\" SELECTED>" + operator + optionEndTag);
			}
			else
			{
				html.append("\n<option  class=" + cssClass + " value=\"" + operator + endTag
						+ operator + optionEndTag);
			}
		}
	}

	/**
	 * @param generatedHTML StringBuffer
	 */
	public static void getTags(StringBuffer generatedHTML)
	{
		generatedHTML.append("\n<tr>\n<td valign=\"top\">");
		generatedHTML.append(endTD);
		generatedHTML.append("\n</tr>");
	}

	/**
	 * Create a map which holds the list of all Expression(DAGNode) ids for a particular entity.
	 * @param expressionMap Map
	 * @return map consisting of the entity and their corresponding expression ids
	 */
	public static Map<QueryableObjectInterface, List<Integer>> getEntityExpressionIdListMap(
			Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap)
	{
		Map<QueryableObjectInterface, List<Integer>> entityExpressionIdMap = new HashMap<QueryableObjectInterface, List<Integer>>();
		Iterator<Map.Entry<Integer, Map<QueryableObjectInterface, List<ICondition>>>> outerMapEntryItr = expressionMap
				.entrySet().iterator();
		List<Integer> dagIdList = new ArrayList<Integer>();
		while (outerMapEntryItr.hasNext())
		{
			//Integer expressionId = (Integer) outerMapIterator.next();
			Map.Entry<Integer, Map<QueryableObjectInterface, List<ICondition>>> outerMapEntry = outerMapEntryItr
					.next();
			Map<QueryableObjectInterface, List<ICondition>> entityMap = outerMapEntry.getValue();
			if (!entityMap.isEmpty())
			{
				Iterator<QueryableObjectInterface> innerMapIterator = entityMap.keySet().iterator();
				while (innerMapIterator.hasNext())
				{
					QueryableObjectInterface entity = innerMapIterator.next();
					if (!entityExpressionIdMap.containsKey(entity))
					{
						//if the entity is not present in the map
						//create new list and add it to map
						dagIdList.clear();
						dagIdList.add(outerMapEntry.getKey());
						entityExpressionIdMap.put(entity, dagIdList);
						continue;
					}
					//if the entity is present in the map
					//add the dag id to the existing list
					dagIdList = (List<Integer>) entityExpressionIdMap.get(entity);
					dagIdList.add(outerMapEntry.getKey());
					entityExpressionIdMap.put(entity, dagIdList);
				}
			}
		}
		return entityExpressionIdMap;
	}

	/**
	 *
	 * @param componentId String
	 * @param isSelected boolean
	 * @param componentIdOfId component Id for id attribute in VI entities
	 * @return String
	 */
	public static String generateCheckBox(String componentId, String componentIdOfId,
			boolean isSelected)
	{
		String select = "";
		if (isSelected)
		{
			select = " checked=true ";
		}
		String tag = "<td class=\"content_txt\"  width=\"5\" valign=\"top\">"
				+ "<input type=\"checkbox\"   id='" + componentId + "_checkbox'" + select
				+ "  onClick=\"enableDisplayFieldForVIAttr(this.form,'" + componentId + comma
				+ componentIdOfId + "')\"></td>";
		return tag;
	}

	/**
	 *
	 * @param componentId String
	 * @param isSelected boolean
	 * @return String
	 */
	public static String generateCheckBox(String componentId, boolean isSelected)
	{
		String select = "";
		if (isSelected)
		{
			select = " checked=true ";
		}
		String tag = "<td class=\"content_txt\"  width=\"5\" valign=\"top\">"
				+ "<input type=\"checkbox\"   id='" + componentId + "_checkbox'" + select
				+ "  onClick=\"enableDisplayField(this.form,'" + componentId + "')\"></td>";
		return tag;
	}

	/**
	 *
	 * @param componentId String
	 * @param oper String
	 * @param operatorList List
	 * @param comboboxId comboboxId
	 * @param termtype TermType
	 * @param isReadOnly true if read only.
	 * @return String
	 */
	public static String generateHTMLForOperator(String componentId, String oper,
			List<String> operatorList, String comboboxId, TermType termtype, boolean isReadOnly)
	{
		StringBuffer generateHTML = new StringBuffer();
		String comboboxName = componentId + "_" + comboboxId;
		String comboboxNameCal = comboboxName;
		String dataType = "false";
		if (termtype == TermType.Timestamp)// termType == time stamp add Calendar
		{
			comboboxNameCal = "Calendar" + comboboxName;
			componentId = "Calendar" + componentId;
			dataType = "true";
		}
		if (operatorList != null && !operatorList.isEmpty())
		{
			StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
			html.append("\n<td width='15%'  valign='top' class='content_txt' >");
			if (isReadOnly)
			{
				html.append(oper);
				generateHTML.append(html);
			}
			else
			{
				html.append("\n<select " + styleTag + comboboxNameCal + "\" id = '" + comboboxName
						+ "'onChange=\"operatorChanged('" + componentId + "','" + dataType
						+ "')\">");
				generateHTML.append(html);
				Iterator<String> iter = operatorList.iterator();
				String operator;
				while (iter.hasNext())
				{
					operator = iter.next();
					getHtmlSelectAttribute(oper, generateHTML, operator);
				}
				generateHTML.append(selectTag);
			}
		}
		return generateHTML.toString();
	}

	/**
	 * @param oper
	 * 		String
	 * @param generateHTML
	 * 		StringBuffer
	 * @param operator String
	 */
	private static void getHtmlSelectAttribute(String oper, StringBuffer generateHTML,
			String operator)
	{
		String optionEndTag = "</option>";
		if (operator.equalsIgnoreCase(oper))
		{
			generateHTML.append("\n<option   value=\"" + operator + "\" SELECTED>" + operator
					+ optionEndTag);
		}
		else
		{
			generateHTML.append("\n<option   value=\"").append(operator).append(endTag).append(
					operator).append(optionEndTag);
		}
	}

	/**
	 * @param forPage
	 * 		String
	 * @param generatedHTML
	 * 	StringBuffer
	 */
	public static void getHtmlAddEditPage(String forPage, StringBuffer generatedHTML)
	{
		if (forPage.equalsIgnoreCase(Constants.ADD_EDIT_PAGE))
		{
			generatedHTML.append("<table border=\"0\" width=\"100%\" "
					+ "height=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
			GenerateHtml.getTags(generatedHTML);
		}
	}

	/**
	 * Method generates html for bold attribute label.
	 * @param attrLabel
	 * 		String
	 * @return StringBuffer
	 */
	public static String getBoldLabel(String attrLabel)
	{
		String imgStr = "\n<img id=\"arrowImg\" "
				+ "src=\"images/advancequery/ic_black_arrow.gif\" />";
		StringBuffer label = new StringBuffer(imgStr);
		label.append("  <strong>");
		label.append(attrLabel).append("</strong>");
		return label.toString();
	}

	/**
	 * This method provides the alternate css for alternate attributes of an entity.
	 * @param generatedHTML
	 * 			StringBuffer
	 * @param isBGColor
	 * 		boolean
	 * @param componentId
	 * 		String
	 * @param isBold boolean
	 * @return boolean
	 */
	public static boolean getAlternateCss(StringBuffer generatedHTML, boolean isBGColor,
			String componentId, boolean isBold,QueryableAttributeInterface attribute)
	{
		String styleSheetClass;
		String desc = attribute.getDescription();
		if(desc == null || desc.equals(""))
		 {
			desc = Constants.NO_DESCRIPTION;
		 }
		boolean bgColor = isBGColor;
		if (isBold)
		{
			styleSheetClass = CSS_HIGHLIGHT;
		}
		else if (bgColor)
		{
			styleSheetClass = CSS_BGGREY;
		}
		else
		{
			styleSheetClass = CSS_BGWHITE;
		}
		bgColor ^= true; //bgColor = !bgColor;
		String html = "\n<tr class='" + styleSheetClass + "' id=\"" + componentId + "\">\n"
				+ "<td valign='top' align='right' "
				+ "class='content_txt' nowrap='nowrap' title=\""+desc+"\" width=\"15%\">";
		generatedHTML.append(html);

		return bgColor;
	}

	/**
	 * Checks if operator is between operator.
	 * @param operator
	 * 			String
	 * @return boolean
	 */
	public static boolean checkBetweenOperator(String operator)
	{
		return (operator != null && operator
				.equalsIgnoreCase(RelationalOperator.Between.toString()));
	}

	/**
	 * Generates html for date format.
	 * @param generatedHTML
	 * 			StringBuffer
	 * @param isBold
	 * 			boolean
	 * @param attribute
	 * 		AttributeInterface
	 */
	public static void getDateFormat(StringBuffer generatedHTML, boolean isBold,
			QueryableAttributeInterface attribute)
	{
		if (attribute.getDataType().equalsIgnoreCase(Constants.DATE))
		{
			StringBuffer dateFormat = new StringBuffer(Constants.DATE_FORMAT);
			//			StringBuffer format = dateFormat;
			if (isBold)
			{
				dateFormat.insert(0, "<strong>");
				dateFormat.append("</strong>");
				//				format = new StringBuffer();
				//				format.append("<strong>");
				//				format.append(dateFormat).append("</strong>");
			}
			generatedHTML.append("\n(" + dateFormat + ")");
		}
	}

	/**
	 *
	 * @param componentId component id
	 * @param attributeDetails attribute details
	 * @param compIdofID component id of ID attribute
	 * @param isReadOnly if read only
	 * @return generated html
	 */
	public static String getHtmlForOperators(String componentId, AttributeDetails attributeDetails,
			String compIdofID, boolean isReadOnly)
	{
		String cssClass = CSS_PV;
		StringBuffer html = new StringBuffer();
		List<String> operatorsList = attributeDetails.getOperatorsList();
		if (operatorsList != null && !operatorsList.isEmpty())
		{
			String temp = "\n<td width='20%' class=" + cssClass + " valign='top' >";
			html.append(temp);
			if (isReadOnly)
			{
				html.append(attributeDetails.getSelectedOperator());
			}
			else
			{
				html.append("\n<select  class=\"textfield\"" + styleTag + componentId
						+ "_combobox\" id=\"" + componentId
						+ "_combobox\" onChange=\"changeIdOperator('" + componentId + comma
						+ compIdofID + "')\">");
				getHtmlForSelectedOperator(attributeDetails, cssClass, html, operatorsList);
				html.append(selectTag);
			}
			html.append(endTD);
		}
		return html.toString();
	}

	/**
	 * This method generates html for operator in case of temporal query.
	 * @param componentId String
	 * @param oper String
	 * @param operatorList list of operators
	 * @param comboboxId String
	 * @param display String
	 * @return generated html
	 */
	public static String generateHTMLForTemporalOperator(String componentId, String oper,
			List<String> operatorList, String comboboxId, String display)
	{
		StringBuffer generateHTML = new StringBuffer();

		if (operatorList != null && !operatorList.isEmpty())
		{
			String comboboxName = componentId + "_" + comboboxId;
			String html = "<td width='15%' valign='top' >"
					+ "\n<select " + " style=\"width:150px; display:"+display+";\" class=\"textfield\" name=\"" + comboboxName + "\" id = '" + comboboxName + "'>";
			generateHTML.append(html);
			Iterator<String> iter = operatorList.iterator();
			String operator;
			while (iter.hasNext())
			{
				operator = iter.next();
				getHtmlSelectAttribute(oper, generateHTML, operator);
			}
			generateHTML.append(selectTag);
			generateHTML.append("</td>");
		}
		return generateHTML.toString();
	}
}
