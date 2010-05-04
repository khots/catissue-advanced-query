package edu.wustl.common.query.queryobject.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.datahandler.AbstractDataHandler;
import edu.wustl.common.datahandler.DataHandlerFactory;
import edu.wustl.common.datahandler.DataHandlerParameter;
import edu.wustl.common.datahandler.HandlerTypeEnum;
import edu.wustl.common.datahandler.ParametersEnum;
import edu.wustl.common.util.logger.LoggerConfig;

public class DenormalizedCSVExporter
{
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(DenormalizedCSVExporter.class);
	/**
	 * Just calls the method to write the results in the CSV file.
	 * @param fileName file name
	 * @param size size of the data list
	 */
	public List<List<String>> addDataToCSV(String fileName, int size,QueryExportDataHandler dataHandler)
	{
		List<List<String>> dataList = new ArrayList<List<String>>();
		try
		{
			dataList = writeToCSVFile(fileName, size,dataHandler);
		}
		catch (IllegalArgumentException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (SecurityException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (ClassNotFoundException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (InstantiationException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (IllegalAccessException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (InvocationTargetException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (NoSuchMethodException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
		}
		return dataList;
	}

	/**
	 * This method writes the query results into the CSV file specified.
	 * @param fileName fileName
	 * @param size size
	 * @throws IllegalArgumentException IllegalArgumentException
	 * @throws SecurityException SecurityException
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws InstantiationException InstantiationException
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws InvocationTargetException InvocationTargetException
	 * @throws NoSuchMethodException NoSuchMethodException
	 * @throws IOException IOException
	 */
	private List<List<String>> writeToCSVFile(String fileName, int size,QueryExportDataHandler handler)
			throws IllegalArgumentException,SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException
	{
		List<List<String>> finalDataList = new ArrayList<List<String>>();
		List<Object> resultList;
		List<String> dataList;
		/*DataHandlerParameter parameter = new DataHandlerParameter();
		parameter.setProperty(ParametersEnum.BUFFERSIZE, null);
		AbstractDataHandler dataHandler = null;*/
		try
		{
			/*dataHandler = DataHandlerFactory.getDataHandler(HandlerTypeEnum.CSV, parameter,
					fileName);
			dataHandler.openFile();*/

			//Add the header to report
			//dataHandler.appendData(getColumnHeaderList());

			for (int i = 0; i < size; i++)
			{
				resultList = getDataList(i, handler);
				//dataHandler.appendData(resultList);
				dataList = new ArrayList<String>();
				populateDataList(resultList, dataList);
				finalDataList.add(dataList);
			}
		}
		finally
		{
			//dataHandler.closeFile();
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
