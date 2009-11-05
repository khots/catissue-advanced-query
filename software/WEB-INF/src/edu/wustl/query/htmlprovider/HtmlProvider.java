package edu.wustl.query.htmlprovider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domain.DateAttributeTypeInformation;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.cab2b.common.exception.CheckedException;
import edu.wustl.cab2b.common.util.PermissibleValueComparator;
import edu.wustl.common.query.factory.PermissibleValueManagerFactory;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobject.QueryableAttributeInterfaceComparator;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleConstants;

/**
 * This class generates UI for 'Add Limits' and 'Edit Limits' section.
 * @author deepti_shelar
 */
public class HtmlProvider
{
	/**
	 * logger for this class.
	 */
	private static final org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(HtmlProvider.class);
	/**
	 * Object which holds data operators for attributes.
	 */
	private static ParseXMLFile parseFile = null;
	/**
	 *
	 */
	private int expressionId = -1;

	/**
	 * list of attributes.
	 */
	private String attributesList = "";

	/**
	 *
	 */
	private GenerateHTMLDetails generateHTMLDetails;

	/**
	 * attribute details for each attribute.
	 */
	private AttributeDetails attributeDetails;
	/**
	 * Queryable object for which html needs to be generated.
	 */
	private QueryableObjectInterface queryableObject;
	/**
	 * Map maintained for enumerated attributes.
	 */
	private Map<String, QueryableAttributeInterface> enumratedAttributeMap =
		new HashMap<String, QueryableAttributeInterface>();
	/**
	 * For page set to SAVE_QUERY/ADD_EDIT/EXECUTE_QUERY depending upon which
	 * page the request has come from.
	 */
	private String forPage = "";
	/**
	 * Value tag.
	 */
	private static String valueTag = "\" value=\"";
	/**
	 * Constant for comma.
	 */
	private static String comma = "','";
	/**
	 * html tag for input hidden component.
	 */
	private static String inputHidden = "<input style=\"width:150px;\" type=\"hidden\" name=\"";
	/**
	 * boolean variable for alternate color.
	 */
	private boolean isBGColor = false;
	/**
	 * boolean variable for read only.
	 */
	private boolean isReadOnly = false;

	/**
	 * @return the expressionId
	 */
	public int getExpressionId()
	{
		return expressionId;
	}

	// private String formName = "categorySearchForm";
	/**
	 * @param generateHTMLDetails
	 *            the generateHTMLDetails to be set
	 */
	public HtmlProvider(GenerateHTMLDetails generateHTMLDetails)
	{
		this.generateHTMLDetails = new GenerateHTMLDetails();
		setGenerateHTMLDetails(generateHTMLDetails);
		if (parseFile == null)
		{
			try
			{
				parseFile = ParseXMLFile.getInstance(Constants.DYNAMIC_UI_XML);
			}
			catch (CheckedException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * @param generateHTMLDetails
	 *            the GenerateHTMLDetails to be set
	 */
	private void setGenerateHTMLDetails(GenerateHTMLDetails generateHTMLDetails)
	{
		if (generateHTMLDetails == null)
		{
			this.generateHTMLDetails.setSearchString("");
			this.generateHTMLDetails.setAttributeChecked(false);
			this.generateHTMLDetails.setPermissibleValuesChecked(false);
			this.generateHTMLDetails.setQueryId("");
		}
		else
		{
			this.generateHTMLDetails.setSearchString(generateHTMLDetails
					.getSearchString());
			this.generateHTMLDetails.setAttributeChecked(generateHTMLDetails
					.isAttributeChecked());
			this.generateHTMLDetails
					.setPermissibleValuesChecked(generateHTMLDetails
							.isPermissibleValuesChecked());
			this.generateHTMLDetails.setQueryId(generateHTMLDetails
					.getQueryId());
		}
	}

	/**
	 * @param expressionId
	 *            the expressionId to set
	 */
	public void setExpressionId(int expressionId)
	{
		this.expressionId = expressionId;
	}

	/**
	 * @return
	 */
	/*
	 * public StringBuffer generateSaveQueryPreHTML() { StringBuffer
	 * generatedPreHTML = new StringBuffer(100); generatedPreHTML
	 * .append("<table border=\"0\" width=\"100%\"+
	 * cellspacing=\"0\" cellpadding=\"0\">"); return generatedPreHTML; }
	 */
	/**
	 * Generates component name for an attribute.
	 * @param attribute
	 *            for which component name has to be generated
	 * @return component name.
	 */
	private String generateComponentName(QueryableAttributeInterface attribute)
	{
		StringBuffer componentId = new StringBuffer();
		String attributeName = "";
		if (getExpressionId() > -1)
		{
			if (attribute.getDataType().equalsIgnoreCase(Constants.DATE))
			{
				componentId = componentId.append("Calendar");
			}
			componentId = componentId.append(getExpressionId() + "_");
		}
		else
		{
			attributeName = attribute.getName();
		}
		componentId = componentId.append(attributeName).append(
				attribute.getId().toString());

		// Append the Query Id to componentId ... required for composite Query
		if ((this.generateHTMLDetails.getQueryId() == null)
				|| (this.generateHTMLDetails.getQueryId().equals("")))
		{
			componentId.append("");
		}
		else
		{
			componentId.append('_').append(
					this.generateHTMLDetails.getQueryId());
		}
		return componentId.toString();

	}

	/**
	 * This method generates the html for Add Limits and Edit Limits section.
	 * This internally calls methods to generate other UI components like text,
	 * Calendar, Combobox etc.
	 * @param queryableObject
	 *            queryableObject to be presented on UI.
	 * @param conditions
	 *            List of conditions , These are required in case of edit
	 *            limits, For adding linits this parameter is null
	 * @param pageOf
	 *            page of
	 * @throws PVManagerException
	 *             VI exception
	 * @return String html generated for Add Limits section.
	 */
	public String generateHTML(QueryableObjectInterface queryableObject,
			List<ICondition> conditions, String pageOf)
			throws PVManagerException

	{
		this.isReadOnly = false;
		this.queryableObject = queryableObject;
		Collection<QueryableAttributeInterface> attributeCollection = queryableObject
				.getEntityAttributesForQuery();
		String nameOfTheEntity = queryableObject.getName();
		String entityId = queryableObject.getId().toString();
		// StringBuffer entityName = new StringBuffer(nameOfTheEntity);
		// entityName = new
		// StringBuffer(Utility.getDisplayLabel(entityName.toString()));
		boolean isEditLimits = isEditLimits(conditions);
		StringBuffer generatedHTML = new StringBuffer(Constants.MAX_SIZE);
		StringBuffer generatedPreHTML;// = new StringBuffer();
		String attributesStr = getAttributesString(attributeCollection);
		generatedPreHTML = GenerateHtml.getHtmlHeader(nameOfTheEntity,
				entityId, attributesStr, isEditLimits, pageOf);

		generatedHTML
				.append("<table id=\"dataAttributes\" cellspacing=\"0\" cellpadding=\"0\">");
		generatedHTML
				.append(getHtmlAttributes(conditions, attributeCollection));
		generatedHTML.append("</table>");
		if (generateHTMLDetails != null) {
			generateHTMLDetails.setEnumratedAttributeMap(enumratedAttributeMap);
		}
		return generatedPreHTML.toString() + "####" + generatedHTML.toString();
	}

	/**
	 * Generates html for all the attributes of the entity.
	 * @param conditions
	 *            list of conditions
	 * @param attributeCollection
	 *            collection of attributes
	 * @throws PVManagerException
	 *             VI exception
	 * @return StringBuffer
	 */
	private StringBuffer getHtmlAttributes(List<ICondition> conditions,
			Collection<QueryableAttributeInterface> attributeCollection)
			throws PVManagerException
	{
		StringBuffer generatedHTML = new StringBuffer(Constants.MAX_SIZE);
		// boolean isBGColor = false;
		if (!attributeCollection.isEmpty())
		{
			List<QueryableAttributeInterface> attributes = new ArrayList<QueryableAttributeInterface>(
					attributeCollection);

			Collections.sort(attributes,
					new QueryableAttributeInterfaceComparator());
			for (int i = 0; i < attributes.size(); i++)
			{

				QueryableAttributeInterface attribute = attributes.get(i);
				if (attribute
						.isTagPresent(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
						|| attribute.getQueryEntity().isTagPresent(
								Constants.TAGGED_VALUE_NOT_SEARCHABLE)
						|| attribute.getQueryEntity().isTagPresent(
								Constants.TAG_HIDE_ATTRIBUTES))
				{
					continue;
				}
				getAttributeDetails(attribute, conditions, null);
				String componentId = generateComponentName(attribute);
				boolean attributeChecked = this.generateHTMLDetails
						.isAttributeChecked();
				boolean permissibleValuesChecked = this.generateHTMLDetails
						.isPermissibleValuesChecked();
				getHtmlForAttribute(generatedHTML, attribute, componentId,
						attributeChecked, permissibleValuesChecked);
				attributesList = attributesList + ";" + componentId;
			}
		}
		return generatedHTML;
	}

	/**
	 * This method generates the html for each attribute of the entity
	 * @param generatedHTML html to generate
	 * @param attribute     attribute for which HTML is generated
	 * @param componentId   componentId of attribute
	 * @param attributeChecked  isattributeChecked
	 * @param permissibleValuesChecked ispermissibleValuesChecked
	 * @throws PVManagerException exception
	 */
	private void getHtmlForAttribute(StringBuffer generatedHTML,
			QueryableAttributeInterface attribute, String componentId,
			boolean attributeChecked, boolean permissibleValuesChecked)
			throws PVManagerException

	{
		String space = " ";
		ICondition condition = null;
		if (attributeDetails.getAttributeNameConditionMap() != null)
		{
			condition = attributeDetails.getAttributeNameConditionMap().get(
					attribute);
		}
		if (attribute.isTagPresent(Constants.TAGGED_VALUE_VI_HIDDEN))
		{
			String temp = getHtmlForHiddenComponent(generatedHTML, componentId,
					condition);
			generatedHTML.append(temp);
		}
		else
		{
			String attrLabel = Utility.getDisplayLabel(attribute
					.getDisplayName());
			boolean isBold = checkAttributeBold(attributeChecked,
					permissibleValuesChecked, attribute, attributeDetails
							.getAttrName());
			if (isBold)
			{
				attrLabel = GenerateHtml.getBoldLabel(attrLabel);
			}
			isBGColor = GenerateHtml.getAlternateCss(generatedHTML, isBGColor,
					componentId, isBold, attribute);
			generatedHTML.append(attrLabel).append(space);
			GenerateHtml.getDateFormat(generatedHTML, isBold, attribute);
			generatedHTML.append(":&nbsp;&nbsp;&nbsp;&nbsp;</td>\n");
			generateHTMLForConditions(generatedHTML, attribute);
			generatedHTML.append("\n</tr>");
		}
	}

	/**
	 * This method generates html for hidden component.
	 * @param generatedHTML
	 *            generated html
	 * @param componentId
	 *            component id
	 * @param condition
	 *            in query
	 * @return html
	 */
	private String getHtmlForHiddenComponent(StringBuffer generatedHTML,
			String componentId, ICondition condition)
	{
		String conceptIds = "";
		if (condition != null)
		{
			List<String> conceptIds1 = condition.getValues();
			if (conceptIds1 != null)
			{
				StringBuffer buffer = new StringBuffer();
				for (String concept : conceptIds1)
				{
					buffer.append(concept).append(',');
				}
				conceptIds = buffer.toString();
				conceptIds = conceptIds.substring(0, conceptIds
						.lastIndexOf(','));
			}
		}
		String temp = inputHidden + componentId + "_combobox\" id=\""
				+ componentId + "_combobox\" value=\"In\">";
		generatedHTML.append(temp);
		String textBoxId = componentId + "_textBox";
		temp = inputHidden + textBoxId + "\" id=\"" + textBoxId + valueTag
				+ conceptIds + "\">";
		return temp;
	}

	/**
	 * Gets attribute details for each attribute.
	 * @param attribute
	 *            details to be set of this attribute
	 * @param conditions
	 *            list of conditions
	 * @param parameterList
	 *            list of parameters
	 */
	private void getAttributeDetails(QueryableAttributeInterface attribute,
			List<ICondition> conditions, List<IParameter<?>> parameterList)
	{
		this.attributeDetails = new AttributeDetails();
		attributeDetails.setAttrName(attribute.getName());
		attributeDetails.setOperatorsList(HtmlUtility.getConditionsList(
				attribute, parseFile));
		if (!attributeDetails.getOperatorsList().isEmpty())
		{
			attributeDetails.setBetween(GenerateHtml
					.checkBetweenOperator(attributeDetails.getOperatorsList()
							.get(0)));
		}
		attributeDetails.setConditions(conditions);
		attributeDetails.setDataType(attribute.getDataType());
		ICondition condition = null;
		attributeDetails.setAttributeNameConditionMap(HtmlUtility
				.getMapOfConditions(conditions));
		if (attributeDetails.getAttributeNameConditionMap() != null)
		{
			condition = attributeDetails.getAttributeNameConditionMap().get(
					attribute);
		}
		attributeDetails.setEditValues(null);
		attributeDetails.setSelectedOperator(null);
		if (condition != null)
		{
			attributeDetails.setEditValues(condition.getValues());
			attributeDetails.setSelectedOperator(condition
					.getRelationalOperator().getStringRepresentation());
		}
		getParamaterizedCondition(attribute, parameterList, forPage);
	}

	/**
	 * get details for parameterized query.
	 * @param attribute
	 *            QueryableAttributeInterface
	 * @param parameterList
	 *            list of parameters
	 * @param forPage
	 *            String
	 */
	private void getParamaterizedCondition(
			QueryableAttributeInterface attribute,
			List<IParameter<?>> parameterList, String forPage)
	{
		IParameter<?> paramater = null;
		attributeDetails.setParameterList(parameterList);
		boolean isEditParameterizedQuery = forPage
				.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE)
				&& parameterList != null && !(parameterList.isEmpty());
		boolean isPresentInMap = attributeDetails
				.getAttributeNameConditionMap() != null
				&& attributeDetails.getAttributeNameConditionMap().get(
						attribute) != null;
		if ((forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE) || isEditParameterizedQuery)
				&& isPresentInMap)
		{
			paramater = HtmlUtility.getParameterForCondition(attributeDetails
					.getAttributeNameConditionMap().get(attribute),
					parameterList);
			attributeDetails.setParamater(paramater);
			attributeDetails.setParameterizedCondition(attributeDetails
					.getAttributeNameConditionMap().containsKey(attribute)
					&& paramater != null);
		}
	}

	/**
	 * check if attribute label is bold.
	 * @param attributeChecked
	 *            boolean
	 * @param permissibleValuesChecked
	 *            boolean
	 * @param attribute
	 *            AttributeInterface
	 * @param attrName
	 *            name of attribute
	 * @return boolean
	 * @throws PVManagerException
	 *             VI exception
	 */
	private boolean checkAttributeBold(boolean attributeChecked,
			boolean permissibleValuesChecked,
			QueryableAttributeInterface attribute, String attrName)
			throws PVManagerException
	{
		boolean isBold = false;
		if (attributeChecked)
		{
			isBold = isAttributeBold(attrName.toLowerCase());
		}
		if (!isBold && permissibleValuesChecked)
		{
			isBold = isPerValueAttributeBold(HtmlUtility
					.getPermissibleValuesList(attribute));
		}
		return isBold;
	}

	/**
	 * @param permissibleValuesList
	 *            list of permissible values
	 * @return boolean
	 */
	private boolean isPerValueAttributeBold(
			List<PermissibleValueInterface> permissibleValuesList)
	{
		boolean isBold = false;
		if (permissibleValuesList != null && !permissibleValuesList.isEmpty())
		{
			for (int i = 0; i < permissibleValuesList.size(); i++)
			{
				PermissibleValueInterface perValue =
					(PermissibleValueInterface) permissibleValuesList.get(i);
				String value = perValue.getValueAsObject().toString();
				if (isAttributeBold(value.toLowerCase()))
				{
					isBold = true;
					break;
				}
			}
		}
		return isBold;
	}

	/**
	 * @param attrName
	 *            name of attribute
	 * @return boolean
	 */
	private boolean isAttributeBold(String attrName)
	{
		boolean isBold = false;
		for (String searchString : this.generateHTMLDetails.getSearchStrings())
		{
			if (attrName.indexOf(searchString) >= 0)
			{
				isBold = true;
				break;
			}
		}
		return isBold;
	}

	/**
	 * This function generates the HTML for enumerated values.
	 * @param componentId
	 *            id of component
	 * @param permissibleValues
	 *            list of permissible values
	 * @param editLimitPermissibleValues
	 *            values list in case of edit limits
	 * @return String html for enumerated value dropdown
	 */
	private String generateHTMLForEnumeratedValues(String componentId,
			List<PermissibleValueInterface> permissibleValues,
			List<String> editLimitPermissibleValues)
	{
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
		// String attributeName = attribute.getName();
		// String componentId = generateComponentName(attribute);
		String format = "\n<td width='70%' valign='top' colspan='4' >";
		if (permissibleValues != null && !permissibleValues.isEmpty())
		{
			html.append(format);
			int size = permissibleValues.size();
			// Bug #3700. Derestricting the list width & increasing the
			// height
			String multiple="";
			if(size==1)
			{
				multiple = "MULTIPLE";
			}
            String temp = "\n<select style=\"display:block;\""+ multiple+" styleId='country' "
					+ "size ='"
					+ size
					+ "' name=\""
					+ componentId
					+ "_enumeratedvaluescombobox\"\">";
			html.append(temp);
			List<PermissibleValueInterface> values = new ArrayList<PermissibleValueInterface>(
					permissibleValues);
			Collections.sort(values, new PermissibleValueComparator());
			for (int i = 0; i < values.size(); i++)
			{
				PermissibleValueInterface perValue = (PermissibleValueInterface) values
						.get(i);
				getHtmlEnumValues(editLimitPermissibleValues, html, perValue);
			}
			html.append("\n</select>\n</td>");
			// html.append("\n</td>");
		}
		return html.toString();

	}

	/**
	 * Get html for enumerated values.
	 * @param editLimitPermissibleValues
	 *            values list in case of edit limits
	 * @param html
	 *            generated html
	 * @param perValue
	 *            permissible value
	 */
	private void getHtmlEnumValues(List<String> editLimitPermissibleValues,
			StringBuffer html, PermissibleValueInterface perValue)
	{
		String value = perValue.getValueAsObject().toString();
		if (editLimitPermissibleValues != null
				&& editLimitPermissibleValues.contains(value)
				|| isAttributeBold(value.toLowerCase()))
		{
			html.append("\n<option class=\"PermissibleValuesQuery\" title=\""
					+ value + valueTag + value + "\" SELECTED>" + value
					+ "</option>");
		}
		else
		{
			html.append("\n<option class=\"PermissibleValuesQuery\" title=\""
					+ value + valueTag + value + "\">" + value + "</option>");
		}
	}

	/**
	 * Gets string from collection of attributes.
	 * @param attributeCollection
	 *            collection of attributes
	 * @return String
	 */
	private String getAttributesString(
			Collection<QueryableAttributeInterface> attributeCollection)
	{
		StringBuffer attributesList = new StringBuffer();
		StringBuffer semicolon = new StringBuffer(
				QueryModuleConstants.ENTITY_SEPARATOR);
		if (!attributeCollection.isEmpty())
		{
			List<QueryableAttributeInterface> attributes = new ArrayList<QueryableAttributeInterface>(
					attributeCollection);
			Collections.sort(attributes,
					new QueryableAttributeInterfaceComparator());
			for (int i = 0; i < attributes.size(); i++)
			{
				QueryableAttributeInterface attribute = attributes.get(i);
				if (attribute
						.isTagPresent(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
						|| attribute.getQueryEntity().isTagPresent(
								Constants.TAGGED_VALUE_NOT_SEARCHABLE)
						|| attribute.getQueryEntity().isTagPresent(
								Constants.TAG_HIDE_ATTRIBUTES))
				{
					continue;
				}
				// String attrName = attribute.getName();
				String componentId = generateComponentName(attribute);
				attributesList.append(semicolon).append(componentId);
			}
		}
		return attributesList.toString();
	}

	/**
	 * Method to generate HTML for condition NULL.
	 * @param generatedHTML
	 *            generated html
	 * @param attribute
	 *            AttributeInterface
	 * @param attributeDetails
	 *            details of attribute
	 * @throws PVManagerException
	 *             VI exception
	 */
	private void generateHTMLForConditions(StringBuffer generatedHTML,
			QueryableAttributeInterface attribute,
			AttributeDetails attributeDetails) throws PVManagerException
	{
		List<ICondition> conditions = attributeDetails.getConditions();
		List<PermissibleValueInterface> permissibleValues = null;
		String componentId = generateComponentName(attribute);
		boolean isDate = false;
		AttributeTypeInformationInterface attrTypeInfo = attribute
				.getAttributeTypeInformation();
		if (attrTypeInfo instanceof DateAttributeTypeInformation)
		{
			isDate = true;
		}
		if (attribute.isTagPresent(Constants.VI_IGNORE_PREDICATE))
		{
			QueryableAttributeInterface attributeIDInterface = getIdAttribute(attribute);
			String componentIdOfID = generateComponentName(attributeIDInterface);
			generatedHTML.append(Constants.NEWLINE).append(
					GenerateHtml.getHtmlForOperators(componentId,
							attributeDetails, componentIdOfID, isReadOnly));
		}
		else
		{
			generatedHTML.append(Constants.NEWLINE).append(
					GenerateHtml.generateHTMLForOperators(componentId, isDate,
							attributeDetails, isReadOnly));
		}
		IPermissibleValueManager permissibleValueManager = PermissibleValueManagerFactory
				.getPermissibleValueManager();
		if (permissibleValueManager.isEnumerated(attribute)
				&& permissibleValueManager.showIcon(attribute))
		{
			generatedHTML.append(Constants.NEWLINE).append(
					getHtmlForVIEnumeratedValues(attribute, componentId));
			if (!isReadOnly)
			{
				generatedHTML
						.append(showEnumeratedAttibutesWithIcon(attribute));
				generatedHTML
						.append("\n<td valign='top' width='20%'/>&nbsp; " +
						"\n<td valign='top' width='20%'/>&nbsp;");
			}
		}
		else if (permissibleValueManager.isEnumerated(attribute))
		{
			if (!isEditLimits(conditions))
			{
				permissibleValues = HtmlUtility
						.getPermissibleValuesList(attribute);
			}
			generatedHTML.append(Constants.NEWLINE)
					.append(
							generateHTMLForEnumeratedValues(componentId,
									permissibleValues, attributeDetails
											.getEditValues()));
		}
		else
		{
			if (attribute.getDataType().equalsIgnoreCase(
					Constants.DATATYPE_BOOLEAN))
			{
				generatedHTML.append(Constants.NEWLINE).append(
						GenerateHtml.generateHTMLForRadioButton(componentId,
								attributeDetails.getEditValues()));
			}
			else
			{
				generatedHTML.append(Constants.NEWLINE).append(
						GenerateHtml.generateHTMLForTextBox(componentId,
								attributeDetails, attribute, isReadOnly));

			}
		}
	}

	/**
	 * This method generated html for VI enumerated values.
	 * @param attribute
	 *            QueryableAttributeInterface
	 * @param componentId
	 *            component
	 * @return generated html
	 */
	private String getHtmlForVIEnumeratedValues(
			QueryableAttributeInterface attribute, String componentId)
	{
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
		// AttributeInterface
		// attributeIDInterface=entity.getAttributeByName(Constants.ID);
		QueryableAttributeInterface attributeIDInterface = getIdAttribute(attribute);
		String componentIdOfID = generateComponentName(attributeIDInterface);
		String format = "\n<td width='5%' valign='top'>";
		if (isReadOnly)
		{
			html.append("\n<td width='65%' valign='top' class='content_txt'>");
		}
		else
		{
			html.append(format);
			String temp = createHtmlforVIComboBox(componentId, componentIdOfID);
			html.append(temp);
		}
		if (attributeDetails.getAttributeNameConditionMap() != null
				&& !attributeDetails.getAttributeNameConditionMap().isEmpty())
		{

			ICondition isIdConditionPresent = attributeDetails
					.getAttributeNameConditionMap().get(attributeIDInterface);
			ICondition isNameConditionPresent = attributeDetails
					.getAttributeNameConditionMap().get(attribute);
			html.append(getVIConditions(isIdConditionPresent,
					isNameConditionPresent));
		}
		if (!isReadOnly)
		{
			html.append("\n</select>");
		}
		html.append("\n</td>");
		return html.toString();
	}

	/**
	 * This method created html for a VI entity
	 * @param componentId : uniquely generated component ID
	 * @param componentIdOfID : component id if ID
	 * @return : Generated html for VI entity
	 */
	private String createHtmlforVIComboBox(String componentId,
			String componentIdOfID)
	{
		int size = 1;
		if (attributeDetails.getEditValues() != null
				&& !attributeDetails.getEditValues().isEmpty())
		{
			size = attributeDetails.getEditValues().size();
			size = size > 5 ? 5 : size;
		}
		String temp = "\n<select style=\"width:150px;\"";
		String multiple="";
		if (size != 1)
		{
			multiple = " multiple='multiple'";
			temp=temp+multiple;
		}
		temp=temp+" styleId='country' " + "size ='" + size
		+ "' name=\"" + componentId
		+ "_enumeratedvaluescombobox\"\" id=\"" + componentId
		+ "_enumeratedvaluescombobox\"\" onChange=\"changeId('"
		+ componentId + comma + componentIdOfID + "')\">";
		return temp;
	}

	/**
	 * This method gets VI conditions.
	 * @param isIdConditionPresent
	 *            ICondition
	 * @param isNameConditionPresent
	 *            ICondition
	 * @return html
	 */
	private StringBuffer getVIConditions(ICondition isIdConditionPresent,
			ICondition isNameConditionPresent)
	{
		StringBuffer html = new StringBuffer(Constants.MAX_SIZE);
		if (isIdConditionPresent != null && isNameConditionPresent != null)
		{
			List<String> conditionOfId = isIdConditionPresent.getValues();
			List<String> conditionOfName = isNameConditionPresent.getValues();
			if (conditionOfId != null && !(conditionOfId.isEmpty())
					&& conditionOfName != null && !(conditionOfName.isEmpty()))
			{
				for (int index = 0; index < conditionOfId.size(); index++)
				{
					getHTMLForCondition(html, conditionOfName, index);
				}

			}
		}
		if (html.lastIndexOf(", ") == html.length() - 2)
		{
			html.replace(html.length() - 2, html.length(), "");
		}
		return html;
	}

	/**
	 * Method to get HTML for condition.
	 * @param html
	 *            html
	 * @param conditionOfName
	 *            conditions
	 * @param index
	 *            index of condition
	 */
	private void getHTMLForCondition(StringBuffer html,
			List<String> conditionOfName, int index)
	{
		String[] name = conditionOfName.get(index).split(Constants.ID_DEL);
		String name2 = "";
		if (name.length >= 2)
		{
			name2 = name[2];
			String identifier = conditionOfName.get(index);
			if (isReadOnly)
			{
				html.append(name2).append(", ");
			}
			else
			{
				html
						.append("\n<option class=\"PermissibleValuesQuery\" title=\""
								+ name2
								+ valueTag
								+ identifier
								+ "\" id=\""
								+ identifier
								+ "\"+ SELECTED>"
								+ name2
								+ "</option>");
			}
		}
	}

	/**
	 * Method for generating HTML depending on condition.
	 * @param generatedHTML
	 *            generated html
	 * @param attribute
	 *            AttributeInterface
	 * @throws PVManagerException
	 *             VI exception
	 */
	private void generateHTMLForConditions(StringBuffer generatedHTML,
			QueryableAttributeInterface attribute) throws PVManagerException
	{
		List<ICondition> conditions = attributeDetails.getConditions();
		if ((conditions != null && !conditions.isEmpty()))
		{
			getHtmlConditionNotNull(generatedHTML, attribute, forPage);
		}
		if (conditions == null
				|| (attributeDetails.getAttributeNameConditionMap() != null && !attributeDetails
						.getAttributeNameConditionMap().containsKey(attribute)))
		{
			generateHTMLForConditions(generatedHTML, attribute,
					this.attributeDetails);
		}
	}

	/**
	 * @param generatedHTML
	 *            generated html
	 * @param attribute
	 *            AttributeInterface
	 * @param forPage
	 *            String
	 * @throws PVManagerException
	 *             VI exception
	 */
	private void getHtmlConditionNotNull(StringBuffer generatedHTML,
			QueryableAttributeInterface attribute, String forPage)
			throws PVManagerException
	{
		if (attributeDetails.getAttributeNameConditionMap() != null
				&& attributeDetails.getAttributeNameConditionMap().containsKey(
						attribute))
		{
			IParameter<?> parameter = attributeDetails.getParamater();
			if (forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE)
					&& parameter == null)
			{
				return;
			}

			generateHTMLForConditions(generatedHTML, attribute,
					this.attributeDetails);
		}
	}

	/**
	 * Method generates html for each entity of saved query.
	 * @param expressionID
	 *            expression id
	 * @param entity
	 *            entity for which html tobe generated
	 * @param conditions
	 *            list of conditions
	 * @param isShowAll
	 *            boolean
	 * @param entityList
	 *            list of entities
	 * @param parameterList
	 *            list of parameters
	 * @return generated html
	 * @throws PVManagerException
	 *             VI exception.
	 */
	private StringBuffer getSaveQueryPageHtml(int expressionID,
			QueryableObjectInterface entity, List<ICondition> conditions,
			boolean isShowAll,
			Map<QueryableObjectInterface, List<Integer>> entityList,
			List<IParameter<?>> parameterList) throws PVManagerException
	{
		this.queryableObject = entity;
		setExpressionId(expressionID);
		StringBuffer generatedHTML = new StringBuffer();
		StringBuffer generatedPreHTML = new StringBuffer();
		Collection<QueryableAttributeInterface> attributeCollection = entity
				.getEntityAttributesForQuery();
		Collection<QueryableAttributeInterface> collection = new ArrayList<QueryableAttributeInterface>();
		boolean isEditLimits = isEditLimits(conditions);
		if (!attributeCollection.isEmpty())
		{
			// get the list of dag ids for the corresponding entity
			String dagNodeId = getDagNodeId(expressionID, entity, entityList);
			List<QueryableAttributeInterface> attributes = new ArrayList<QueryableAttributeInterface>(
					attributeCollection);
			Collections.sort(attributes,
					new QueryableAttributeInterfaceComparator());
			GenerateHtml.getHtmlAddEditPage(forPage, generatedHTML);
			for (QueryableAttributeInterface attribute : attributes)
			{
				if (attribute
						.isTagPresent(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
						|| attribute.getQueryEntity().isTagPresent(
								Constants.TAGGED_VALUE_NOT_SEARCHABLE)
						|| attribute.getQueryEntity().isTagPresent(
								Constants.TAG_HIDE_ATTRIBUTES))
				{
					continue;
				}
				getAttributeDetails(attribute, conditions, parameterList);
				Map<QueryableAttributeInterface, ICondition> attributeNameConditionMap =
					attributeDetails.getAttributeNameConditionMap();
				if (checkAtrributeCondition(isShowAll, attribute,
						attributeNameConditionMap))
				{
					continue;
				}
				collection.add(attribute);
				String componentId = generateComponentName(attribute);
				ICondition condition = null;
				if (attributeDetails.getAttributeNameConditionMap() != null)
				{
					condition = attributeDetails.getAttributeNameConditionMap()
							.get(attribute);
				}
				if (attribute.isTagPresent(Constants.TAGGED_VALUE_VI_HIDDEN))
				{
					getHtmlSavedHiddenComponent(generatedHTML, componentId,
							condition);
					attributesList = attributesList
							+ QueryModuleConstants.ENTITY_SEPARATOR
							+ componentId;
				}
				else
				{
					isBGColor = getAlternateHtmlForSavedQuery(generatedHTML,
							isBGColor, componentId);
					generatedHTML.append(getHtmlAttributeSavedQuery(entity,
							dagNodeId, attribute));
				}
			}

			generatedPreHTML.append(getHtml(entity, generatedHTML, collection,
					isEditLimits));
		}
		generatedHTML = getAddEditPageHtml(generatedHTML, generatedPreHTML);
		return generatedHTML;
	}

	/**
	 * This method generates html for hidden component.
	 * @param generatedHTML
	 *            html
	 * @param componentId
	 *            component id
	 * @param condition
	 *            in query
	 */
	private void getHtmlSavedHiddenComponent(StringBuffer generatedHTML,
			String componentId, ICondition condition)
	{
		String select = "";
		if (attributeDetails.isParameterizedCondition())
		{
			select = " checked=true ";
		}
		String conceptIds = "";
		String operator = "In";
		if (condition != null)
		{
			List<String> conceptIds1 = condition.getValues();
			if (conceptIds1 != null && !conceptIds1.isEmpty())
			{
				StringBuffer buffer = new StringBuffer();
				for (String concept : conceptIds1)
				{
					buffer.append(concept).append(',');
				}
				conceptIds = buffer.toString();
				conceptIds = conceptIds.substring(0, conceptIds
						.lastIndexOf(','));
			}
			operator = condition.getRelationalOperator()
					.getStringRepresentation();
		}
		StringBuffer temp = new StringBuffer(Constants.MAX_SIZE);
		temp.append("<td class=\"content_txt\"  width=\"5\" valign=\"top\">	"
				+ "<input type=\"checkbox\" style=\"display:none; \" " + select + "id='" + componentId
				+ "_checkbox'></td>");
		temp.append(inputHidden).append(componentId)
				.append("_combobox\" id=\"").append(componentId).append(
						"_combobox\" value='").append(operator).append("'>");
		generatedHTML.append(temp);
		String textBoxId = componentId + "_textBox";
		generatedHTML.append(inputHidden + textBoxId + "\" id=\"" + textBoxId
				+ valueTag + conceptIds + "\">");
	}

	/**
	 * Modify html for Add/Edit page of Query.
	 * @param generatedHTML
	 *            StringBuffer
	 * @param generatedPreHTML
	 *            StringBuffer
	 * @return modified html
	 */
	private StringBuffer getAddEditPageHtml(StringBuffer generatedHTML,
			StringBuffer generatedPreHTML)
	{
		StringBuffer html = generatedHTML;
		if (forPage.equalsIgnoreCase(Constants.ADD_EDIT_PAGE))
		{
			generatedPreHTML.append("####");
			generatedPreHTML.append(generatedHTML);
			html = generatedPreHTML;
		}
		return html;
	}

	/**
	 * Edit Limit case if Conditions on attribute is not null.
	 * @param conditions
	 *            list of conditions
	 * @return isEditLimits
	 */
	public static boolean isEditLimits(List<ICondition> conditions)
	{
		boolean isEditLimits = false;
		if (conditions != null)
		{
			isEditLimits = true;
		}
		return isEditLimits;
	}

	/**
	 * This method checks if an attribute has conditions.
	 * @param isShowAll
	 *            boolean
	 * @param attribute
	 *            attribute
	 * @param attributeNameConditionMap
	 *            map containing attribute and its conditions.
	 * @return boolean
	 */
	private boolean checkAtrributeCondition(
			boolean isShowAll,
			QueryableAttributeInterface attribute,
			Map<QueryableAttributeInterface, ICondition> attributeNameConditionMap) {
		return attributeNameConditionMap != null
				&& !attributeNameConditionMap.containsKey(attribute)
				&& !isShowAll;
	}

	/**
	 * @param entity
	 *            for which html is to be generated
	 * @param generatedHTML
	 *            generated html
	 * @param collection
	 *            collection of attributes
	 * @param isEditLimits
	 *            boolean
	 * @return StringBuffer
	 */
	private StringBuffer getHtml(QueryableObjectInterface entity,
			StringBuffer generatedHTML,
			Collection<QueryableAttributeInterface> collection,
			boolean isEditLimits)
	{
		StringBuffer generatedPreHTML = new StringBuffer();
		String nameOfTheEntity = entity.getName();
		Collection<QueryableAttributeInterface> attributeCollection = entity
				.getEntityAttributesForQuery();
		if (forPage.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE)
				|| forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			generatedHTML.append(" <input type='hidden'  id='"
					+ this.expressionId + ":" + entity.getName()
					+ "_attributeList'" + " value="
					+ getAttributesString(collection) + " />  ");
		}
		else if (forPage.equalsIgnoreCase(Constants.ADD_EDIT_PAGE))
		{
			GenerateHtml.getTags(generatedHTML);
			generatedHTML.append("</table>");
			generatedPreHTML = GenerateHtml.generatePreHtml(
					getAttributesString(attributeCollection), nameOfTheEntity,
					isEditLimits);
		}
		return generatedPreHTML;
	}

	/**
	 * Method returns DagNode Id.
	 * @param expressionID
	 *            expressionID
	 * @param entity
	 *            EntityInterface
	 * @param entityList
	 *            list of entities
	 * @return String
	 */
	private String getDagNodeId(int expressionID,
			QueryableObjectInterface entity,
			Map<QueryableObjectInterface, List<Integer>> entityList)
	{
		List<Integer> entityDagId = (List<Integer>) entityList.get(entity);
		String dagNodeId = ""; // Converting the dagId to string
		if (entityDagId.size() > 1)
		{
			// DAGNodeId / expressionID to be shown only in case
			// if there are more than one node of the same class
			dagNodeId = expressionID + Constants.QUERY_DOT;
		}
		return dagNodeId;
	}

	/**
	 * This method gets html for each attribute in saved query.
	 * @param entity
	 *            EntityInterface
	 * @param dagNodeId
	 *            String
	 * @param attribute
	 *            AttributeInterface
	 * @return generated html
	 * @throws PVManagerException
	 *             VI exception
	 */
	private StringBuffer getHtmlAttributeSavedQuery(
			QueryableObjectInterface entity, String dagNodeId,
			QueryableAttributeInterface attribute) throws PVManagerException
	{
		this.queryableObject = entity;
		StringBuffer generatedHTML = new StringBuffer(Constants.MAX_SIZE);
		generatedHTML.append(getHtmlForPage(dagNodeId, attribute, entity
				.getName()));
		// GenerateHtml.getDateFormat(generatedHTML, false, attribute);
		if ((forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE) && attributeDetails
				.isParameterizedCondition())
				|| !forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			generatedHTML.append("&nbsp;&nbsp;&nbsp;&nbsp;</b></td>\n");
		}
		generateHTMLForConditions(generatedHTML, attribute);
		generatedHTML.append("\n</tr>");
		return generatedHTML;
	}

	/**
	 * This method generates the alternate css for each attribute in saved
	 * query.
	 * @param generatedHTML
	 *            generated html
	 * @param isBGColor
	 *            boolean
	 * @param componentId
	 *            component identifier
	 * @return boolean
	 */

	private boolean getAlternateHtmlForSavedQuery(StringBuffer generatedHTML,
			boolean isBGColor, String componentId)
	{
		boolean bgColor = isBGColor;
		String styleSheetClass = GenerateHtml.CSS_BGWHITE;
		if ((forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE) && attributeDetails
				.isParameterizedCondition())
				|| !forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			attributesList = attributesList
					+ QueryModuleConstants.ENTITY_SEPARATOR + componentId;
		}
		if (isBGColor)
		{
			styleSheetClass = GenerateHtml.CSS_BGGREY;
		}
		else
		{
			styleSheetClass = GenerateHtml.CSS_BGWHITE;
		}
		bgColor ^= true; // BGColor = !BGColor
		String html = "\n<tr width=\"100%\" class='" + styleSheetClass + "'"
				+ "  id=\"componentId\" " + " >\n";

		generatedHTML.append(html);
		return bgColor;
	}

	/**
	 * Modifies html based on ForPage i.e SAVE_QUERY, EXECUTE_QUERY_PAGE.
	 * @param dagNodeId
	 *            dag node id
	 * @param attribute
	 *            QueryableAttributeInterface
	 * @param name
	 *            of Entity
	 * @return StringBuffer
	 */
	private StringBuffer getHtmlForPage(String dagNodeId,
			QueryableAttributeInterface attribute, String name)
	{
		StringBuffer generatedHTML = new StringBuffer(Constants.MAX_SIZE);
		String attrLabel = Utility.getDisplayLabel(attribute.getDisplayName());
		String html = "";
		String attributeName = "";
		String componentId = generateComponentName(attribute);
		if (attributeDetails.isParameterizedCondition())
		{
			attributeName = attributeDetails.getParamater().getName();
		}
		else
		{
			attributeName = dagNodeId + name + "." + attrLabel;
		}
		String isActive = " disabled ";
		if (attributeDetails.isParameterizedCondition())
		{
			isActive = "enabled";
		}
		if (forPage.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE))
		{
			String checkbox = "";
			if (attribute.isTagPresent(Constants.VI_IGNORE_PREDICATE))
			// generate checkbox for VI Attributes name
			{
				QueryableAttributeInterface attributeIDInterface = getIdAttribute(attribute);
				String componentIdOfID = generateComponentName(attributeIDInterface);
				checkbox = GenerateHtml.generateCheckBox(componentId,
						componentIdOfID, attributeDetails
								.isParameterizedCondition());

			} else {
				checkbox = GenerateHtml.generateCheckBox(componentId,
						attributeDetails.isParameterizedCondition());
			}

			html = " "
					+ checkbox
					+ "<td valign='top' align='left' class='content_txt'>"
					+ "<label for='"
					+ componentId
					+ "_displayName' title='"
					+ dagNodeId
					+ name
					+ "."
					+ attrLabel
					+ "'>"
					+ "<input type=\"textbox\"  class=\"formFieldSized20\"  name='"
					+ componentId + "_displayName'     id='" + componentId
					+ "_displayName' value='" + attributeName + "' " + isActive
					+ "/> " + "</label></td>";

			generatedHTML.append(html);
		}
		if (!forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE))
		{
			html = "<td valign='top' align='left' "
					+ "class='content_txt' nowrap='nowrap' width=\"15%\">"
					+ attrLabel + " ";
			generatedHTML.append(html);
			GenerateHtml.getDateFormat(generatedHTML, false, attribute);
		}
		if (forPage.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE)
				&& attributeDetails.isParameterizedCondition())
		{
			// formName = "saveQueryForm";
			html = "<td valign='top' align='left' class='content_txt' "
					+ " width=\"15%\">"
					+ attributeDetails.getParamater().getName() + " ";
			generatedHTML.append(html);
			if (!isReadOnly)
			{
				GenerateHtml.getDateFormat(generatedHTML, false, attribute);
			}
		}
		return generatedHTML;
	}

	/**
	 * This method generates the html for Save Query section. This internally
	 * calls methods to generate other UI components like text, Calendar,
	 * Combobox etc. This method is same as the generateHTML except that this
	 * will generate html for selected conditions and will display only those
	 * conditions with their values set by user.
	 * @param expressionMap
	 *            map which holds the list of all dag ids / expression ids for a
	 *            particular entity
	 * @param isShowAll
	 *            boolean
	 * @param forPage
	 *            String
	 * @param parameterList
	 *            list of parameters
	 * @param isReadOnly
	 *            true if read only
	 * @return String html generated for Save Query section.
	 * @throws PVManagerException
	 *             VI exception
	 */
	public String getHtmlForSavedQuery(
			Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap,
			boolean isShowAll, String forPage,
			List<IParameter<?>> parameterList, boolean isReadOnly)
			throws PVManagerException
	{
		this.forPage = forPage;
		this.isReadOnly = isReadOnly;
		StringBuffer generatedHTML = new StringBuffer(Constants.MAX_SIZE);
		attributesList = "";
		StringBuffer expressionEntityString = new StringBuffer();
		if (expressionMap.isEmpty())
		{
			generatedHTML.append("No record found.");
			// return generatedHTML.toString();
		}
		else
		{
			// get the map which holds the list of all dag ids / expression ids
			// for a particular entity
			expressionEntityString = getMapsForEntity(expressionMap, isShowAll,
					parameterList, generatedHTML);
		}
		getHtml(expressionMap, forPage, generatedHTML, expressionEntityString);
		return generatedHTML.toString();
	}

	/**
	 * This method generates HTML for save query page and ExecuteQueryPage
	 * @param expressionMap : expression and conditions map
	 * @param forPage : For which html is generated
	 * @param generatedHTML : Generated HTML
	 * @param expressionEntityString : Expression entity string
	 */
	private void getHtml(
			Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap,
			String forPage, StringBuffer generatedHTML,
			StringBuffer expressionEntityString)
	{
		if (!expressionMap.isEmpty()
				&& (forPage.equalsIgnoreCase(Constants.SAVE_QUERY_PAGE) || forPage
						.equalsIgnoreCase(Constants.EXECUTE_QUERY_PAGE)))
		{
			String html = "<input type='hidden' id='isCountQuery' value='"
					+ true + "' />";
			generatedHTML.append(html);

			String totalEntities = "";
			String attributes = "";
			String conditionList = "";
			if ((this.generateHTMLDetails.getQueryId() == null)
					|| (this.generateHTMLDetails.getQueryId().equals("")))
			{
				totalEntities = "totalentities";
				attributes = "attributesList";
				conditionList = "conditionList";
			}
			else
			{
				totalEntities = "totalentities_"
						+ this.generateHTMLDetails.getQueryId();
				attributes = "attributesList_"
						+ this.generateHTMLDetails.getQueryId();
				conditionList = "conditionList_"
						+ this.generateHTMLDetails.getQueryId();
			}
			html = "<input type='hidden' id='" + totalEntities + "' value='"
					+ expressionEntityString + "' />";
			generatedHTML.append(html);
			html = "<input type='hidden' id='" + attributes + "' value='"
					+ attributesList + "' />";
			generatedHTML.append(html);

			generatedHTML.append("<input type='hidden' id='" + conditionList
					+ "'name='conditionList' value='' />");
			this.generateHTMLDetails
					.setEnumratedAttributeMap(enumratedAttributeMap);

		}
	}

	/**
	 * Create a map which holds the list of all Expression(DAGNode) ids for a
	 * particular entity.
	 * @param expressionMap
	 *            map of enpression ids for an entity
	 * @param isShowAll
	 *            boolean
	 * @param parameterList
	 *            list of parameters
	 * @param generatedHTML
	 *            generated html
	 * @throws PVManagerException
	 *             PVManagerException
	 * @return expressionEntityString
	 */
	private StringBuffer getMapsForEntity(
			Map<Integer, Map<QueryableObjectInterface, List<ICondition>>> expressionMap,
			boolean isShowAll, List<IParameter<?>> parameterList,
			StringBuffer generatedHTML) throws PVManagerException
	{
		String colon = ":";
		StringBuffer expressionEntityString = new StringBuffer();
		Map<QueryableObjectInterface, List<ICondition>> entityConditionMap = null;
		Map<QueryableObjectInterface, List<Integer>> entityExpressionIdListMap = GenerateHtml
				.getEntityExpressionIdListMap(expressionMap);
		// Iterator<Integer> iterator = expressionMap.keySet().iterator();
		Iterator<Map.Entry<Integer, Map<QueryableObjectInterface, List<ICondition>>>> entryItr =
			expressionMap.entrySet().iterator();
		while (entryItr.hasNext())
		{
			// Integer expressionId = (Integer) iterator.next();
			Map.Entry<Integer, Map<QueryableObjectInterface, List<ICondition>>> entry = entryItr
					.next();
			entityConditionMap = entry.getValue();

			if (entityConditionMap.isEmpty())
			{
				continue;
			}
			// Iterator<QueryableObjectInterface> it2 =
			// entityConditionMap.keySet().iterator();
			Iterator<Map.Entry<QueryableObjectInterface, List<ICondition>>> inerEntryItr =
				entityConditionMap.entrySet().iterator();
			while (inerEntryItr.hasNext())
			{
				// QueryableObjectInterface entity = it2.next();
				Map.Entry<QueryableObjectInterface, List<ICondition>> entry1 = inerEntryItr
						.next();
				List<ICondition> conditions = entry1.getValue();
				generatedHTML.append(getSaveQueryPageHtml(entry.getKey()
						.intValue(), entry1.getKey(), conditions, isShowAll,
						entityExpressionIdListMap, parameterList));
				expressionEntityString.append(entry.getKey().intValue())
						.append(colon).append(entry1.getKey().getName())
						.append(QueryModuleConstants.ENTITY_SEPARATOR);
			}
		}
		return expressionEntityString;
	}

	/**
	 * This method shows enumerated attributes with icon.
	 * @param attributeInterface
	 *            QueryableAttributeInterface
	 * @return html added by amit_doshi for Vocabulary Interface
	 */
	public String showEnumeratedAttibutesWithIcon(
			QueryableAttributeInterface attributeInterface)
	{
		/*
		 * Need to get the attribute interface of of ID attribute because we
		 * have to set all the concept code to the ID Attribute
		 */
		QueryableAttributeInterface attributeIDInterface = getIdAttribute(attributeInterface);
		String componentIdOfID = generateComponentName(attributeIDInterface);
		String componentId = generateComponentName(attributeInterface);
		enumratedAttributeMap.put(Constants.ATTRIBUTE_INTERFACE + componentId,
				attributeInterface);
		return "\n<td valign='top' width='10%'><a href=\"javascript:openPermissibleValuesConfigWindow('"
				+ componentId
				+ comma
				+ Utility.getDisplayLabel(attributeInterface.getQueryEntity()
						.getName())
				+ comma
				+ queryableObject.getId()
				+ comma
				+ componentIdOfID
				+ "');\"><img  src=\"images/advancequery/ic_lookup.gif\" "
				+ "width=\"22\" height=\"20\" align='left' id='viIcon'"
				+ " border=\"0\"/ title='Search for concepts from Vocabularies'></a></td>";
	}

	/**
	 * This method returns QueryableAttributeInterface for Med id attribute.
	 * @param attributeInterface
	 *            QueryableAttributeInterface
	 * @return QueryableAttributeInterface
	 */
	private QueryableAttributeInterface getIdAttribute(
			QueryableAttributeInterface attributeInterface)
	{
		return this.queryableObject.getAttributeByName(attributeInterface,
				Constants.ID);
	}

	/**
	 * Returns GenerateHTMLDetails.
	 * @return generateHTMLDetails
	 */
	public GenerateHTMLDetails getGenerateHTMLDetails()
	{
		return generateHTMLDetails;
	}
	/**
	 * This method generates HTML for saved conditions
	 * @param expressionID : expression id
	 * @param entity : Entity Object
	 * @param conditions : List of conditions for that expression
	 * @param parameterList : List of parameters in query
	 * @return This method returns the generated html for query constraints
	 * @throws PVManagerException is thrown
	 */
	public StringBuffer getHtmlForSavedConditons(int expressionID, QueryableObjectInterface entity,
			List<ICondition> conditions,List<IParameter<?>> parameterList) throws PVManagerException
	{
		StringBuffer generatedHTML = new StringBuffer();
		this.queryableObject = entity;
		setExpressionId(expressionID);
		//Get the attribute collection for an entity
		Collection<QueryableAttributeInterface> attributeCollection = entity
		.getEntityAttributesForQuery();
		if (!attributeCollection.isEmpty())
		{
			List<QueryableAttributeInterface> attributes = new ArrayList<QueryableAttributeInterface>(
					attributeCollection);
			for (QueryableAttributeInterface attribute : attributes)
			{
				if (attribute.isTagPresent(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
					|| attribute.getQueryEntity().isTagPresent(
					Constants.TAGGED_VALUE_NOT_SEARCHABLE)||attribute.getQueryEntity()
					.isTagPresent(Constants.TAG_HIDE_ATTRIBUTES)
					||attribute.isTagPresent(Constants.TAGGED_VALUE_VI_HIDDEN))
				{
					continue;
				}
				/*
				 * Note ..... The for page is set to EXECUTE_QUERY_PAGE by default as
				   it required at various places
				*/
				this.forPage = Constants.EXECUTE_QUERY_PAGE;
				this.isReadOnly = true;
				getAttributeDetails(attribute, conditions, parameterList);
				//check if the there is condition on the attribute
				if((!this.attributeDetails.isParameterizedCondition()) &&
				(attributeDetails.getAttributeNameConditionMap().get(attribute)!= null))
				{
					String html = "<tr><td valign='top' align='left' class='content_txt' " +
					" width=\"15%\">"+ attribute.getQueryEntity().getName()+"."+Utility.getDisplayLabel(attribute.getName()) +
					" " +"</td>";
			    	generatedHTML.append(html);
			    	generateHTMLForConditions(generatedHTML, attribute, this.attributeDetails);
			    	generatedHTML.append("</tr>");
				 }
			}
		}
		return generatedHTML;
	}
}
