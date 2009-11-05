package edu.wustl.query.htmlprovider;

import java.util.List;
import java.util.Map;

import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IParameter;

/**
 * This class holds details of an Attribute, required to Generate Html.
 * @author rukhsana_sameer
 *
 */
public class AttributeDetails
{
	/**
	 * Name of Attribute.
	 */
	private String attrName;

	/**
	 * Map containing Attribute and its conditions.
	 */
	private Map<QueryableAttributeInterface, ICondition> attributeNameConditionMap;

	/**
	 * List of operators for an attribute.
	 */
	private List<String> operatorsList;

	/**
	 * Conditions of Attribute.
	 */
	private List<ICondition> conditions;

	/**
	 *	Parameter list in case of Parameterized query.
	 */
	private List<IParameter<?>> parameterList;

	/**
	 * If operator isBetween.
	 */
	private boolean between;

	/**
	 *	Values for selected operator.
	 */
	private List<String> editValues;

	/**
	 * Selected operator.
	 */
	private String selectedOperator;

	/**
	 *	Data type of attribute.
	 */
	private String dataType;

	/**
	 * Is Parameterized Condition.
	 */
	private boolean parameterizedCondition;

	/**
	 * Parameter for parameterized query.
	 */
	private IParameter<?> paramater;


	/**
	 * @return the attrName
	 */
	public String getAttrName()
	{
		return attrName;
	}

	/**
	 * @param attrName the attrName to set
	 */
	public void setAttrName(String attrName)
	{
		this.attrName = attrName;
	}

	/**
	 * @return the attributeNameConditionMap
	 */
	public Map<QueryableAttributeInterface, ICondition> getAttributeNameConditionMap()
	{
		return attributeNameConditionMap;
	}

	/**
	 * @param attributeNameConditionMap the attributeNameConditionMap to set
	 */
	public void setAttributeNameConditionMap(Map<QueryableAttributeInterface, ICondition> attributeNameConditionMap)
	{
		this.attributeNameConditionMap = attributeNameConditionMap;
	}

	/**
	 * @return the operatorsList
	 */
	public List<String> getOperatorsList()
	{
		return operatorsList;
	}

	/**
	 * @param operatorsList the operatorsList to set
	 */
	public void setOperatorsList(List<String> operatorsList)
	{
		this.operatorsList = operatorsList;
	}
	/**
	 * @return the conditions
	 */
	public List<ICondition> getConditions()
	{
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<ICondition> conditions)
	{
		this.conditions = conditions;
	}

	/**
	 * @return the parameterList
	 */
	public List<IParameter<?>> getParameterList()
	{
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(List<IParameter<?>> parameterList)
	{
		this.parameterList = parameterList;
	}

	/**
	 * @return the between
	 */
	public boolean isBetween()
	{
		return between;
	}

	/**
	 * @param between the isBetween to set
	 */
	public void setBetween(boolean between)
	{
		this.between = between;
	}

	/**
	 * @return the editValues
	 */
	public List<String> getEditValues()
	{
		return editValues;
	}

	/**
	 * @param editValues the editValues to set
	 */
	public void setEditValues(List<String> editValues)
	{
		this.editValues = editValues;
	}

	/**
	 * @return the selectedOperator
	 */
	public String getSelectedOperator()
	{
		return selectedOperator;
	}

	/**
	 * @param selectedOperator the selectedOperator to set
	 */
	public void setSelectedOperator(String selectedOperator)
	{
		this.selectedOperator = selectedOperator;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType()
	{
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	/**
	 * @return the parameterizedCondition
	 */
	public boolean isParameterizedCondition()
	{
		return parameterizedCondition;
	}

	/**
	 * @param parameterizedCondition the parameterizedCondition to set
	 */
	public void setParameterizedCondition(boolean parameterizedCondition)
	{
		this.parameterizedCondition = parameterizedCondition;
	}

	/**
	 * @return the parameter
	 */
	public IParameter<?> getParamater()
	{
		return paramater;
	}

	/**
	 * @param paramater the parameter to set
	 */
	public void setParamater(IParameter<?> paramater)
	{
		this.paramater = paramater;
	}

}
