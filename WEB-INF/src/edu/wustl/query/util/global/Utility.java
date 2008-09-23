package edu.wustl.query.util.global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.entitymanager.EntityManagerConstantsInterface;
import edu.wustl.common.util.logger.Logger;


public class Utility extends edu.wustl.common.util.Utility 
{
	
	private static String pattern = "MM-dd-yyyy";
	
	public static Object toNewGridFormat(Object obj) 
	{
		obj = toGridFormat(obj);
		if (obj instanceof String) {
			String objString = (String) obj;
			StringBuffer tokenedString = new StringBuffer();

			StringTokenizer tokenString = new StringTokenizer(objString, ",");

			while (tokenString.hasMoreTokens()) {
				tokenedString.append(tokenString.nextToken() + " ");
			}
			String gridFormattedStr = new String(tokenedString);
			obj = gridFormattedStr;
		}

		return obj;
	}
	
	/**
	 * Adds the attribute values in the list in sorted order and returns the
	 * list containing the attribute values in proper order
	 * 
	 * @param dataType -
	 *            data type of the attribute value
	 * @param value1 -
	 *            first attribute value
	 * @param value2 -
	 *            second attribute value
	 * @return List containing value1 and valu2 in sorted order
	 */
	public static ArrayList<String> getAttributeValuesInProperOrder
	(
		String dataType, String value1, String value2) {
		String v1 = value1;
		String v2 = value2;
		ArrayList<String> attributeValues = new ArrayList<String>();
		if (dataType.equalsIgnoreCase(EntityManagerConstantsInterface.DATE_ATTRIBUTE_TYPE)) 
		{
			SimpleDateFormat df =new SimpleDateFormat(pattern);
			try 
			{
				Date date1 = df.parse(value1);
				Date date2 = df.parse(value2);
				if(date1.after(date2))
				{
					v1 = value2;
					v2 = value1;
				}
			} 
			catch (ParseException e) 
			{
				Logger.out.error("Can not parse the given date in getAttributeValuesInProperOrder() method :"+e.getMessage());
				e.printStackTrace();
			}
		}
		else 
		{
			if (dataType
					.equalsIgnoreCase(EntityManagerConstantsInterface.INTEGER_ATTRIBUTE_TYPE)
					|| dataType
					.equalsIgnoreCase(EntityManagerConstantsInterface.LONG_ATTRIBUTE_TYPE)) {
				if (Long.parseLong(value1) > Long.parseLong(value2)) {
					v1 = value2;
					v2 = value1;
				}

			} else {
				if (dataType
						.equalsIgnoreCase(EntityManagerConstantsInterface.DOUBLE_ATTRIBUTE_TYPE)) {
					if (Double.parseDouble(value1) > Double.parseDouble(value2)) {
						v1 = value2;
						v2 = value1;
					}

				}
			}
		}
		attributeValues.add(v1);
		attributeValues.add(v2);
		return attributeValues;
	}
}
