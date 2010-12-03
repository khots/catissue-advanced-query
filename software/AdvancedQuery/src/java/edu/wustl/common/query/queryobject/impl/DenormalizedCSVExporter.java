package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * This class is responsible for forming the data list in denormalized form to be exported to a CSV file.
 * @author pooja_tavase
 *
 */
public class DenormalizedCSVExporter
{
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
		List<OutputAttributeColumn> dataList;
		List<String> newDataList;
		for (int i = 0; i < size; i++)
		{
			resultList = getDataList(i, dataHandler);
			dataList = new ArrayList<OutputAttributeColumn>();
			newDataList = new ArrayList<String>();
			populateDataList(resultList, dataList);
			Collections.sort(dataList, new AttributeOrderComparator());
			for(OutputAttributeColumn opAttrCol : dataList)
			{
				String value = opAttrCol.getValue();
				if(value != null)
				{
					StringTokenizer token = new StringTokenizer(value, "|");
					while(token.hasMoreTokens())
					{
						newDataList.add(token.nextToken());
					}
				}
			}
			finalDataList.add(newDataList);
		}
		List<Object> headerList = dataHandler.getHeaderList();
		List<String> finalHeaderList = new ArrayList<String>();
		Collections.sort(headerList, new AttributeOrderComparator());
		for(Object opAttrCol : headerList)
		{
			String header = ((OutputAttributeColumn)opAttrCol).getHeader();
			StringTokenizer token = new StringTokenizer(header, "|");
			while(token.hasMoreTokens())
			{
				finalHeaderList.add(token.nextToken());
			}
		}
		exportDetailsMap.put("dataList", finalDataList);
		exportDetailsMap.put("headerList", finalHeaderList);
		return exportDetailsMap;
	}

	/**
	 * Convert the elements of the result list from object to String and add them in the dataList.
	 * @param resultList resultList
	 * @param dataList dataList
	 */
	private void populateDataList(List<Object> resultList, List<OutputAttributeColumn> dataList)
	{
		OutputAttributeColumn data;
		for(Object object : resultList)
		{
			data = null;
			if(object != null)
			{
				data = (OutputAttributeColumn) object;
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
