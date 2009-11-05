
package edu.wustl.query.util.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.wustl.common.util.global.Constants;
import edu.wustl.common.util.logger.LoggerConfig;

/**Class which provides utility methods related to reflection.
 * @author vijay_pande
 *
 */
public class ReflectionUtilities
{

	/**
	 * logger object for the class.
	 */
	private static final Logger logger = LoggerConfig
			.getConfiguredLogger(ReflectionUtilities.class);
	/**
	 * HashSet to store which fields to skip while processing.
	 */
	private static Set<String> skipFields = new HashSet<String>();
	/**
	 * static block to populate skipFields set.
	 */
	static
	{
		skipFields.add("serialVersionUID");
		skipFields.add("isSystemGenerated");
		skipFields.add("containingExpression");
		skipFields.add("termType");
	}

	/**Method to get details of the attributes for the given input object.
	 * This provides also gives details of the attributes which are inherited and value is not null.
	 * @param obj object for which attributes details are required
	 * @return List<Field> list of Fields
	 * @throws ReflectionException Reflection Exception
	 */
	public static List<Field> getFieldList(Object obj) throws ReflectionException
	{
		logger.debug("Inside getFieldList method for object:" + obj.getClass().getName() + " "
				+ obj);
		return getFieldsForClass(obj, obj.getClass().getName());
	}

	/**Method to get details of the attributes of the object which belongs to the class provided as input.
	 * Attributes with value NOTNULL will only be the part of the returned list.
	 * @param obj object for which attributes details are required
	 * @param className className wrt which we want attribute details
	 * @return List list of Fields
	 * @throws ReflectionException Reflection Exception
	 */
	private static List<Field> getFieldsForClass(Object obj, String className)
			throws ReflectionException
	{
		List<Field> myFields = new ArrayList<Field>();
		try
		{
			Class<?> clazz = Class.forName(className);
			List<java.lang.reflect.Field> fields = Arrays.asList(clazz.getDeclaredFields());
			for (java.lang.reflect.Field field : fields)
			{
				Field myField;
				Object value = getValue(field, obj, clazz);
				if (value != null)
				{
					myField = populateField(field, value);
					myFields.add(myField);
				}
			}
			if (clazz.getSuperclass() != null)
			{
				myFields.addAll(getFieldsForClass(obj, clazz.getSuperclass().getName()));
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new ReflectionException(e.getMessage(), e);
		}
		return myFields;
	}

	/**Method to populate Filed object from reflection's Field object.
	 * @param field attribute to be populated
	 * @param value value of the attribute
	 * @return myField
	 * @throws ReflectionException Reflection Exception
	 */
	private static Field populateField(java.lang.reflect.Field field, Object value)
			throws ReflectionException
	{
		Field myField = new Field();
		myField.setFieldName(field.getName());
		myField.setFieldValue(value);
		myField.setFieldType(getFieldType(myField));
		return myField;
	}

	/**Method to find out the type of attribute.
	 * @param field Attribute for which type have to be find out
	 * @return FieldType enum value
	 * @throws ReflectionException Reflection Exception
	 */
	private static FieldType getFieldType(Field field) throws ReflectionException
	{
		FieldType fieldType = null;
		if (isCollection(field))
		{
			fieldType = FieldType.COLLECTION;
		}
		else if (isPrimitive(field.getFieldValue().getClass().getName()))
		{
			fieldType = FieldType.PRIMITIVE;
		}
		else if (isIterable(field))
		{
			fieldType = FieldType.ITERABLE;
		}
		else if (isEnum(field.getFieldValue().getClass()))
		{
			fieldType = FieldType.ENUM;
		}
		else if (isDomainClass(field.getFieldValue().getClass().getName()))
		{
			fieldType = FieldType.DOMAIN_OBJECT;
		}
		logger.debug(field);
		return fieldType;
	}

	/**Method check whether attribute is of type ENUM.
	 * @param clazz Class
	 * @return boolean value
	 * @throws ReflectionException Reflection Exception
	 */
	public static boolean isEnum(Class<?> clazz) throws ReflectionException
	{
		return clazz.isEnum();
	}

	/**Method to check whether attribute is of type ITerable.
	 * @param field Attribute for which type have to be find out
	 * @return boolean value
	 */
	public static boolean isIterable(Field field)
	{
		return (Iterable.class.isInstance(field.getFieldValue())) ? true : false;
	}

	/**Method to check whether attribute is of type Domain object.
	 * @param name Class name
	 * @return boolean value
	 */
	public static boolean isDomainClass(String name)
	{
		return (name.startsWith("edu.")) ? true : false;
	}

	/**Method to check whether attribute is of type Collection.
	 * @param field Attribute for which type have to be find out
	 * @return boolean value
	 */
	public static boolean isCollection(Field field)
	{
		return (Collection.class.isInstance(field.getFieldValue())) ? true : false;
	}

	/**Method to check whether attribute is of type Primitive.
	 * @param name name of the class
	 * @return boolean value
	 */
	public static boolean isPrimitive(String name)
	{
		return (name.startsWith("java.")) ? true : false;
	}

	/**Method to get the value of the attribute.
	 * @param field Attribute of which value is required
	 * @param obj Object to which attribute belongs
	 * @param classs Class to which attribute belongs
	 * @return Object actual value of the attribute
	 * @throws ReflectionException Reflection Exception
	 */
	private static Object getValue(java.lang.reflect.Field field, Object obj, Class<?> classs)
			throws ReflectionException
	{
		Object value = null;
		try
		{
			if (!isFieldToSkip(field, classs.getName()))
			{
				String name = field.getName();
				Method method = getMethod(name, classs, obj);
				if (method != null && Modifier.isPublic(method.getModifiers()))
				{
					value = method.invoke(obj);
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new ReflectionException(e.getMessage(), e);
		}
		return value;
	}

	/**Method to check whether system should skip the processing of the field or not.
	 * There some attributes like serialVersionUID which are not significant while processing.
	 * To skip such attribute add them to skipField set.
	 * @param field attribute under process
	 * @param className class name
	 * @return boolean value
	 */
	private static boolean isFieldToSkip(java.lang.reflect.Field field, String className)
	{
		boolean isSkip = skipFields.contains(field.getName());
		if (!isSkip)
		{
			if (field.getName().equals("expression") && className.contains("ExpressionAttribute"))
			{
				isSkip = true;
			}
		}
		return isSkip;
	}

	/**Method to get the getter Method for the attribute, if available.
	 * @param name name of the attribute
	 * @param classs class to which attribute belongs
	 * @param object to which method belongs
	 * @return Method object
	 */
	private static Method getMethod(String name, Class<?> classs, Object object)
	{
		Method methodToReturn = null;
		List<Method> methods = Arrays.asList(classs.getDeclaredMethods());
		String methodName = name.substring(Constants.ZERO, Constants.ONE).toUpperCase()
				+ name.substring(Constants.ONE);
		for (Method method : methods)
		{
			if (method.getName().equals("get" + methodName)
					|| method.getName().equals("is" + methodName))
			{
				methodToReturn = method;
				break;
			}
		}
		return methodToReturn;
	}

	/**
	 * Method to get all attribute list of a class
	 * @param object Object for which attribute details are required
	 * @return List myFields
	 * @throws ReflectionException Reflection Exception
	 */
	public static List<Field> getAllFieldList(Object object) throws ReflectionException
	{
		List<Field> myFields;
		try
		{
			myFields = new ArrayList<Field>();
			Class<?> classs = Class.forName(object.getClass().getName());
			List<java.lang.reflect.Field> fields = Arrays.asList(classs.getDeclaredFields());
			for (java.lang.reflect.Field field : fields)
			{
				Object value = getValue(field, object, classs);
				Field myField = populateField(field, value);
				myFields.add(myField);
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new ReflectionException(e.getMessage(), e);
		}
		return myFields;
	}

	/**Method to get the value of the 'name' attribute from the object.
	 * @param object Object for which value of 'name' is required
	 * @return String name
	 * @throws ReflectionException Reflection Exception
	 */
	public static String getName(Object object) throws ReflectionException
	{
		return (String) getValueFor(object, "name");
	}

	/**Method to get the value of the 'id' attribute from the object.
	 * @param object Object for which value of 'id' is required
	 * @return Long id
	 * @throws ReflectionException Reflection Exception
	 */
	public static Long getId(Object object) throws ReflectionException
	{
		return (Long) getValueFor(object, "id");
	}

	/**Method to get value of the given attribute name.
	 * @param object object of which attribute's value is required
	 * @param attributeName attribute name of which value is required
	 * @return Object value of attribute
	 * @throws ReflectionException Reflection Exception
	 */
	public static Object getValueFor(Object object, String attributeName)
			throws ReflectionException
	{
		Object value = null;
		String methodName = attributeName.substring(Constants.ZERO, Constants.ONE).toUpperCase()
				+ attributeName.substring(Constants.ONE);
		try
		{
			Class<?> clazz = Class.forName(object.getClass().getName());
			Method method = clazz.getMethod("get" + methodName);
			if (method == null)
			{
				method = clazz.getMethod("is" + methodName);
			}
			value = method.invoke(object);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new ReflectionException(e.getMessage(), e);
		}
		return value;
	}
}
