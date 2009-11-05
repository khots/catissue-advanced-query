
package edu.wustl.query.util.reflection;

/**Bean class to represent an attribute of a class.
 * @author vijay_pande
 *
 */
public class Field
{

	/**
	 * Variable for attribute name.
	 */
	private String fieldName;
	/**
	 * Variable for attribute value.
	 */
	private Object fieldValue;
	/**
	 * Variable for attribute type.
	 */
	private FieldType fieldType;

	/**
	 * Getter method for fieldName.
	 * @return fieldName
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * Setter method for fieldName.
	 * @param name String fieldName
	 */
	public void setFieldName(String name)
	{
		fieldName = name;
	}

	/**
	 * Getter method for fieldValue.
	 * @return fieldValue of type Object
	 */
	public Object getFieldValue()
	{
		return fieldValue;
	}

	/**
	 * Setter method for fieldValue.
	 * @param value value of the field of type Object
	 */
	public void setFieldValue(Object value)
	{
		fieldValue = value;
	}

	/**
	 * Getter method for fieldType.
	 * @return fieldType FieldType enum
	 */
	public FieldType getFieldType()
	{
		return fieldType;
	}

	/**
	 * Setter method for fieldType.
	 * @param type FieldType fieldType
	 */
	public void setFieldType(FieldType type)
	{
		fieldType = type;
	}

	/**
	 * toString method override to display field details.
	 * @see java.lang.Object#toString()
	 * @return toString value
	 */
	@Override
	public String toString()
	{
		return "FieldName:" + getFieldName() + " FieldType:" + fieldType;
	}
}
