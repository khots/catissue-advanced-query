package edu.wustl.query.xquerydatatypes;

public class XQueryDataTypeInformation
{
	public String name;
	
	public String dataType;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the databaseDataType
	 */
	public String getDataType()
	{
		return dataType;
	}

	/**
	 * @param databaseDataType the databaseDataType to set
	 */
	public void setDataType(String databaseDataType)
	{
		this.dataType = databaseDataType;
	}

}
