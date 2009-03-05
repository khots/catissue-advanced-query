
package edu.wustl.query.queryexecutionmanager;

import java.util.List;

/**
 * This Class holds the Results of a Get Data Query
 * Composed of attribuleList - List<List<Object>> and
 * 			   dataTypesList - List<String>
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since February 12, 2009
 */
public class DataQueryResultsBean
{

	/**  **/
	private int dataQueryExecId;

	/**  **/
	private int countQueryExecId;
	
	/**  **/
	private List<List<Object>> attributeList;

	/**  **/
	private List<String> dataTypesList;

	/**
	 * Default Constructor
	 */
	public DataQueryResultsBean()
	{

	}

	/**
	 * PARAMETERIZED CONSTRUCTOR
	 * @param attributeList
	 * @param dataTypesList
	 */
	public DataQueryResultsBean(int countQueryExecId, int dataQueryExecId, List<List<Object>> attributeList, List<String> dataTypesList)
	{
		this.countQueryExecId = countQueryExecId;
		this.dataQueryExecId = dataQueryExecId;
		this.attributeList = attributeList;
		this.dataTypesList = dataTypesList;
	}

	/**
	 * 
	 * @return
	 */
	public List<List<Object>> getAttributeList()
	{
		return attributeList;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getDataTypesList()
	{
		return dataTypesList;
	}

	/**
	 * 
	 * @param attributeList
	 */
	public void setAttributeList(List<List<Object>> attributeList)
	{
		this.attributeList = attributeList;
	}

	/**
	 * 
	 * @param dataTypesList
	 */
	public void setDataTypesList(List<String> dataTypesList)
	{
		this.dataTypesList = dataTypesList;
	}

	/**
	 * 
	 * @return
	 */
	public int getDataQueryExecId()
	{
		return dataQueryExecId;
	}

	/**
	 * 
	 * @param dataQueryExecId
	 */
	public void setDataQueryExecId(int dataQueryExecId)
	{
		this.dataQueryExecId = dataQueryExecId;
	}

	/**
	 * 
	 * @return
	 */
	public int getCountQueryExecId()
	{
		return countQueryExecId;
	}

	/**
	 * 
	 * @param countQueryExecId
	 */
	public void setCountQueryExecId(int countQueryExecId)
	{
		this.countQueryExecId = countQueryExecId;
	}

}
