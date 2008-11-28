package edu.wustl.query.htmlprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domain.PermissibleValue;
import edu.common.dynamicextensions.domain.UserDefinedDE;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.util.ParseXMLFile;
import edu.wustl.query.util.global.Constants;
/**
 *This class has utility methods required to generate Html for Add Limits in Query.
 */

public class HtmlUtility
{
	/**
	 * Method gets enumerated condition list.
	 * @param dataType dataType of attribute
	 * @param parseFile xml file which contains operator list
	 * @return operator list
	 */
	private static List<String> getEnumConditionList(String dataType,ParseXMLFile parseFile)
	{
		List<String> operatorsList = new ArrayList<String>();
		/*if (dataType.equalsIgnoreCase("long") || dataType.equalsIgnoreCase("double")
				|| dataType.equalsIgnoreCase("short")
				|| dataType.equalsIgnoreCase("integer") || dataType.equalsIgnoreCase("float"))*/
		if(isNumber(dataType.toLowerCase()))
		{
			operatorsList = parseFile.getEnumConditionList(Constants.DATATYPE_NUMBER);
		}
		else
		{
			operatorsList = parseFile.getEnumConditionList(dataType.toLowerCase());
		}
		return operatorsList;
	}
	/**
	 * Check if data type is numeric.
	 * @param dataType type of attribute
	 * @return boolean
	 */
	private static boolean isNumber(String dataType)
	{
		boolean isNumber = false;
		for(int i=0;i<Constants.NUMBER.length;i++)
		{
			if(dataType.equalsIgnoreCase(Constants.NUMBER[i]))
			{
				isNumber = true;
				break;
			}
		}
		return isNumber;
	}
	/**
	 * Method gets non-enumerated condition list.
	 * @param dataType data type of attribute
	 * @param parseFile xml file which contains operator list
	 * @return operator list
	 */
	private static List<String> getNonEnumConditionList(String dataType,ParseXMLFile parseFile)
	{
		List<String> operatorsList = new ArrayList<String>();
		if (isNumber(dataType.toLowerCase()))
		{
			operatorsList = parseFile.getNonEnumConditionList(Constants.DATATYPE_NUMBER);
		}
		else
		{
			operatorsList = parseFile.getNonEnumConditionList(dataType.toLowerCase());
		}
		return operatorsList;
	}

	/**
	 * Returns list of possible non-enumerated/enumerated operators for attribute.
	 * @param attributeInterface AttributeInterface
	 * @param parseFile xml file which contains operator list
	 * @return List listOf operators.
	 */
	public static List<String> getConditionsList(AttributeInterface attributeInterface,ParseXMLFile parseFile)
	{
		List<String> operatorsList = new ArrayList<String>();
		List<String> strObj = null;
		if (attributeInterface != null)
		{
			String dataType = attributeInterface.getDataType();
			UserDefinedDE userDefineDE = (UserDefinedDE) attributeInterface
					.getAttributeTypeInformation().getDataElement();
			if (userDefineDE != null &&
				userDefineDE.getPermissibleValueCollection().size() < Constants.MAX_PV_SIZE)
			{
				operatorsList = HtmlUtility.getEnumConditionList(dataType,parseFile);
			}
			else
			{
				operatorsList = HtmlUtility.getNonEnumConditionList(dataType,parseFile);
			}
			strObj = new ArrayList<String>(operatorsList);
			operatorsList = new ArrayList<String>();
			Collections.sort(strObj);
			//operatorsList = getSortedOperatorList(strObj);
		}
		return strObj;
	}

	/**
	 *
	 * @param strObj List of sorted operators
	 * @return list of operators in sorted order
	 *//*
	private static List<String> getSortedOperatorList(List<String> strObj)
	{
		List<String> operatorsList = new ArrayList<String>();
		for (int i = 0; i < strObj.size(); i++)
		{
			if (strObj.get(i) != null)
			{
				operatorsList.add((String) strObj.get(i));
			}
		}
		return operatorsList;
	}*/
	/**
	 * Returns the map of name of the attribute and condition obj as its value.
	 * @param conditions
	 *            list of conditions user had applied in case of edit limits
	 * @return Map name of the attribute and condition obj
	 */
	public static Map<String, ICondition> getMapOfConditions(List<ICondition> conditions)
	{
		Map<String, ICondition> attributeNameConditionMap = null;
		if (conditions != null)
		{
			attributeNameConditionMap = new HashMap<String, ICondition>();
			for (int i = 0; i < conditions.size(); i++)
			{
				attributeNameConditionMap.put(
					conditions.get(i).getAttribute().getName(), conditions.get(i));
			}
		}
		return attributeNameConditionMap;
	}
	/**
	 * returns PermissibleValuesList' list for attribute.
	 * @param attribute
	 *            AttributeInterface
	 * @return List of permissible values for the passed attribute
	 */
	public static List<PermissibleValueInterface> getPermissibleValuesList(AttributeInterface attribute)
	{
		UserDefinedDE userDefineDE = (UserDefinedDE) attribute.getAttributeTypeInformation()
				.getDataElement();
		List<PermissibleValueInterface> permissibleValues = new ArrayList<PermissibleValueInterface>();
		if (userDefineDE != null && userDefineDE.getPermissibleValueCollection() != null)
		{
			Iterator<PermissibleValueInterface> permissibleValueInterface = userDefineDE
					.getPermissibleValueCollection().iterator();
			while (permissibleValueInterface.hasNext())
			{
				PermissibleValue permValue = (PermissibleValue) permissibleValueInterface.next();
				//getPermissibleValue(permissibleValues, permValue);
				permissibleValues.add(permValue);
			}
		}
		return permissibleValues;
	}

	/*private static void getPermissibleValue(List<PermissibleValueInterface> permissibleValues,
			PermissibleValue permValue)
	{
		if (permValue instanceof StringValueInterface)
		{
			permissibleValues.add(((StringValueInterface) permValue));
		}
		else if (permValue instanceof ShortValueInterface)
		{
			permissibleValues.add(((ShortValueInterface) permValue));
		}
		else if (permValue instanceof LongValueInterface)
		{
			permissibleValues.add(((LongValueInterface) permValue));
		}
		else if (permValue instanceof DateValueInterface)
		{
			permissibleValues.add(((DateValueInterface) permValue));
		}
		else if (permValue instanceof BooleanValueInterface)
		{
			permissibleValues.add(((BooleanValueInterface) permValue));
		}
		else if (permValue instanceof IntegerValueInterface)
		{
			permissibleValues.add(((IntegerValueInterface) permValue));
		}
		else if (permValue instanceof DoubleValueInterface)
		{
			permissibleValues.add((DoubleValueInterface) permValue);
		}
		else if (permValue instanceof FloatValueInterface)
		{
			permissibleValues.add(((FloatValueInterface) permValue));
		}
		permissibleValues.add(permValue);
	}*/

	/**
	 * @param condition ICondition
	 * @param parameterList list of parameters
	 * @return IParameter
	 */
	public static IParameter<?> isParameterized(ICondition condition, List<IParameter<?>> parameterList)
	{
		IParameter tempParameter = null;
		if(parameterList !=null)
		{
		 for (IParameter<?> parameter : parameterList)
		 {
	            if (parameter.getParameterizedObject() instanceof ICondition)
	            {
	            	ICondition paramCondition = (ICondition) parameter.getParameterizedObject();
	                if(paramCondition.getId()==condition.getId())
	                {
	                	tempParameter = parameter;
	                	break;
	                }
	                    //return parameter;
	             }
	        }
		}
		return tempParameter;
	}
}
