package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wustl.common.util.logger.LoggerConfig;

/**
 * This class is responsible for forming the data list in denormalized form to be exported to a CSV file.
 * @author pooja_tavase
 *
 */
public class DenormalizedCSVExporter
{
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(DenormalizedCSVExporter.class);
	/**
	 * Just calls the method to get the final data list to be written into the CSV file.
	 * @param fileName file name
	 * @param size size of the data list
	 */
	public Map<String,Object> addDataToCSV(int size,QueryExportDataHandler dataHandler)
	{
		Map<String,Object> exportDetailsMap = new HashMap<String,Object>();
		List<List<String>> finalDataList = new ArrayList<List<String>>();
		List<Object> resultList;
		List<String> dataList;
		for (int i = 0; i < size; i++)
		{
			resultList = getDataList(i, dataHandler);
			dataList = new ArrayList<String>();
			populateDataList(resultList, dataList);
			finalDataList.add(dataList);
		}
		finalDataList = writeToCSVFile(size,dataHandler);
		List<String> headerList = dataHandler.getHeaderList();
		exportDetailsMap.put("dataList", finalDataList);
		exportDetailsMap.put("headerList", headerList);
		return exportDetailsMap;
	}

	/**
	 * This method writes the query results into the CSV file specified.
	 * @param fileName fileName
	 * @param size size
	 */
	private List<List<String>> writeToCSVFile(int size,QueryExportDataHandler handler)
	{
		List<List<String>> finalDataList = new ArrayList<List<String>>();
		List<Object> resultList;
		List<String> dataList;

		//Add the header to report
		List<String> headerList = handler.getHeaderList();
		List<Object> finalHeaders = new ArrayList<Object>();
		for(String header : headerList)
		{
			finalHeaders.add(header);
		}
		for (int i = 0; i < size; i++)
		{
			resultList = getDataList(i, handler);
			dataList = new ArrayList<String>();
			populateDataList(resultList, dataList);
			finalDataList.add(dataList);
		}
		return finalDataList;
	}

	/**
	 * Convert the elements of the result list from object to String and add them in the dataList.
	 * @param resultList resultList
	 * @param dataList dataList
	 */
	private void populateDataList(List<Object> resultList, List<String> dataList)
	{
		String data;
		for(Object object : resultList)
		{
			data = null;
			if(object != null)
			{
				data = object.toString();
			}
			dataList.add(data);
		}
	}

	/**
	 * This method is used to get the data list to be written to the CSV file.
	 * @param counter counter
	 * @param dataHandler dataHandler
	 * @return dataList
	 */
	private List<Object> getDataList(int counter,QueryExportDataHandler dataHandler)
	{
		return dataHandler.getDataList(counter);
	}
}
