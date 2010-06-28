package edu.wustl.common.query.queryobject.impl;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;


public class OutputAttributeColumn
{
	private String value;

	private int columnIndex;

	private AttributeInterface attribute;

	private String header;

	public OutputAttributeColumn(String value, int columnIndex, AttributeInterface attribute, String header)
	{
		this.value = value;
		this.columnIndex = columnIndex;
		this.attribute = attribute;
		this.header = header;
	}

	/**
	 * @return the attribute.
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param attribute the attribute to set.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return the columnIndex.
	 */
	public int getColumnIndex()
	{
		return columnIndex;
	}

	/**
	 * @param columnIndex the columnIndex to set.
	 */
	public void setColumnIndex(int columnIndex)
	{
		this.columnIndex = columnIndex;
	}

	/**
	 * @return the attribute.
	 */
	public AttributeInterface getAttribute()
	{
		return attribute;
	}

	/**
	 * @param attribute the attribute to set.
	 */
	public void setAttribute(AttributeInterface attribute)
	{
		this.attribute = attribute;
	}

	/**
	 * @param header the header to set.
	 */
	public void setHeader(String header)
	{
		this.header = header;
	}

	/**
	 * @return the header.
	 */
	public String getHeader()
	{
		return header;
	}

	@Override
	public int hashCode()
	{
		return (int) (attribute.getId() * 3L);
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean isEqual = false;
		if (obj instanceof OutputAttributeColumn)
		{
			OutputAttributeColumn entityObject = (OutputAttributeColumn) obj;
			if (value.equals(entityObject.getValue()) && columnIndex == entityObject.getColumnIndex()
					&& attribute.equals(entityObject.getAttribute()))
			{
				isEqual = true;
			}
		}
		return isEqual;
	}
}
