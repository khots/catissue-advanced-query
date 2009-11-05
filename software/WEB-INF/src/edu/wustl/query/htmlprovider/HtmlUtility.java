
package edu.wustl.query.htmlprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.common.query.factory.PermissibleValueManagerFactory;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IParameterizable;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 *This class has utility methods required to generate Html for Add Limits in Query.
 */

public final class HtmlUtility
{
	/**
	 * constructor.
	 */
	private HtmlUtility()
	{

	}

	/**
	 * Returns list of possible non-enumerated/enumerated operators for attribute.
	 * @param attributeInterface AttributeInterface
	 * @param parseFile xml file which contains operator list
	 * @return List listOf operators.
	 */
	public static List<String> getConditionsList(QueryableAttributeInterface attributeInterface,
			ParseXMLFile parseFile)
	{
		List<String> operatorsList; //= new ArrayList<String>();
		List<String> strObj = null;
		if (attributeInterface != null)
		{
			if (attributeInterface.isTagPresent(Constants.TAG_QUANTITATIVE_ATTRIBUTE))
			{
				operatorsList = parseFile.getNonEnumConditionList(Constants.DATATYPE_RANGE);
			}
			else
			{
				String dataType = attributeInterface.getDataType().toLowerCase();
				if (Utility.isNumber(dataType))
				{
					dataType = Constants.DATATYPE_NUMBER;
				}
				IPermissibleValueManager permissibleValueManager = PermissibleValueManagerFactory
						.getPermissibleValueManager();
				if (permissibleValueManager.isEnumerated(attributeInterface))
				{
					operatorsList = parseFile.getEnumConditionList(dataType);
				}
				else
				{
					operatorsList = parseFile.getNonEnumConditionList(dataType);
				}
			}
			strObj = new ArrayList<String>(operatorsList);
			Collections.sort(strObj);
		}
		return strObj;
	}

	/**
	 * Returns the map of name of the attribute and condition obj as its value.
	 * @param conditions
	 *            list of conditions user had applied in case of edit limits
	 * @return Map name of the attribute and condition obj
	 */
	public static Map<QueryableAttributeInterface, ICondition> getMapOfConditions(
			List<ICondition> conditions)
	{
		Map<QueryableAttributeInterface, ICondition> attributeNameConditionMap = null;
		if (conditions != null)
		{
			attributeNameConditionMap = new HashMap<QueryableAttributeInterface, ICondition>();
			for (int i = 0; i < conditions.size(); i++)
			{
				attributeNameConditionMap.put(conditions.get(i).getAttribute(), conditions.get(i));
			}
		}
		return attributeNameConditionMap;
	}

	/**
	 * @param condition ICondition
	 * @param parameterList list of parameters
	 * @return IParameter
	 */
	public static IParameter<?> getParameterForCondition(IParameterizable condition,
			List<IParameter<?>> parameterList)
	{
		IParameter tempParameter = null;
		if (parameterList != null)
		{
			for (IParameter<?> parameter : parameterList)
			{
				if (parameter.getParameterizedObject()==condition)
				{
					tempParameter = parameter;
					break;
				}
			}
		}
		return tempParameter;
	}

	/**
	 * returns PermissibleValuesList' list for attribute.
	 *
	 * @param attribute     AttributeInterface
	 * @return List of permissible values for the passed attribute
	 * added amit_doshi
	 * @throws PVManagerException VI exception
	 */
	public static List<PermissibleValueInterface> getPermissibleValuesList(
			QueryableAttributeInterface attribute) throws PVManagerException
	{
		IPermissibleValueManager permissibleValueManager = PermissibleValueManagerFactory
				.getPermissibleValueManager();
		return permissibleValueManager.getPermissibleValueList(attribute);
	}
}
