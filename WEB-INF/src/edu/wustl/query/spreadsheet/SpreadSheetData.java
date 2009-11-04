package edu.wustl.query.spreadsheet;

import java.util.List;
import java.util.Map;

import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;


/**
 * @author vijay_pande
 *
 */
public class SpreadSheetData
{
	private List<List<Object>> dataList;
	private List<String> dataTypeList;
	private SelectedColumnsMetadata selectedColumnsMetadata;
	private List<String> columnsList;
	private Map mainEntityMap;
	
	/**
	 * @return the dataList
	 */
	public List<List<Object>> getDataList()
	{
		return dataList;
	}
	
	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<List<Object>> dataList)
	{
		this.dataList = dataList;
	}
	
	/**
	 * @return the dataTypeList
	 */
	public List<String> getDataTypeList()
	{
		return dataTypeList;
	}

	/**
	 * @param dataTypeList the dataTypeList to set
	 */
	public void setDataTypeList(List<String> dataTypeList)
	{
		this.dataTypeList = dataTypeList;
	}

	/**
	 * @return the selectedColumnsMetadata
	 */
	public SelectedColumnsMetadata getSelectedColumnsMetadata()
	{
		return selectedColumnsMetadata;
	}
	
	/**
	 * @param selectedColumnsMetadata the selectedColumnsMetadata to set
	 */
	public void setSelectedColumnsMetadata(SelectedColumnsMetadata selectedColumnsMetadata)
	{
		this.selectedColumnsMetadata = selectedColumnsMetadata;
	}
	
	/**
	 * @return the columnsList
	 */
	public List<String> getColumnsList()
	{
		return columnsList;
	}
	
	/**
	 * @param columnsList the columnsList to set
	 */
	public void setColumnsList(List<String> columnsList)
	{
		this.columnsList = columnsList;
	}
	
	/**
	 * @return the mainEntityMap
	 */
	public Map getMainEntityMap()
	{
		return mainEntityMap;
	}
	
	/**
	 * @param mainEntityMap the mainEntityMap to set
	 */
	public void setMainEntityMap(Map mainEntityMap)
	{
		this.mainEntityMap = mainEntityMap;
	}
}
