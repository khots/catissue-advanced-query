
package edu.wustl.query.queryexecutionmanager;

import java.util.ArrayList;
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
	private Long dataQueryExecId;

	/**  **/
	private Long countQueryExecId;

	/**  **/
	private List<List<Object>> attributeList;

	/**  **/
	private List<String> dataTypesList;

	private DataQueryResultStatus resultStatus;

	/**
	 * Default Constructor
	 */
	public DataQueryResultsBean()
	{
		attributeList = new ArrayList<List<Object>>();
		dataTypesList = new ArrayList<String>();
	}

	/**
	 * PARAMETERIZED CONSTRUCTOR
	 * @param attributeList
	 * @param dataTypesList
	 */
	public DataQueryResultsBean(Long countQueryExecId, Long dataQueryExecId,
			List<List<Object>> attributeList, List<String> dataTypesList)
	{
		this.countQueryExecId = countQueryExecId;
		this.dataQueryExecId = dataQueryExecId;
		this.attributeList = attributeList;
		this.dataTypesList = dataTypesList;
	}

	public DataQueryResultsBean(List<List<Object>> dataList, List<String> dataTypesList,
			DataQueryResultStatus dataQueryResultStatus)
	{
		this.attributeList = dataList;
		this.dataTypesList = dataTypesList;
		this.resultStatus = dataQueryResultStatus;
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
	public Long getDataQueryExecId()
	{
		return dataQueryExecId;
	}

	/**
	 * 
	 * @param dataQueryExecId
	 */
	public void setDataQueryExecId(Long dataQueryExecId)
	{
		this.dataQueryExecId = dataQueryExecId;
	}

	/**
	 * 
	 * @return
	 */
	public Long getCountQueryExecId()
	{
		return countQueryExecId;
	}

	/**
	 * 
	 * @param countQueryExecId
	 */
	public void setCountQueryExecId(Long countQueryExecId)
	{
		this.countQueryExecId = countQueryExecId;
	}

	/**
	 * @return
	 */
	public DataQueryResultStatus getResultStatus()
	{
		return resultStatus;
	}

	/**
	 * @param resultStatus
	 */
	public void setResultStatus(DataQueryResultStatus dataQueryResultStatus)
	{
		this.resultStatus = dataQueryResultStatus;
	}

}
