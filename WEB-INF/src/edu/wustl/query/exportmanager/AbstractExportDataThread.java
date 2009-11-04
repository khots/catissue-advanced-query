
package edu.wustl.query.exportmanager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.datahandler.DataHandlerParameter;
import edu.wustl.common.datahandler.HandlerTypeEnum;
import edu.wustl.common.datahandler.ParametersEnum;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.DataQueryExecutorFactory;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.queryexecutionmanager.DataQueryExecution;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;

/**
 * This Abstract class is responsible for carrying out the generic logic of exporting data.
 * This class will have an abstract postProcess() method which will be implemented by host subclass
 * 
 * @author ravindra_jain
 * @version 1.0
 * @created 02-Mar-2009 3:24:31 PM
 */
public abstract class AbstractExportDataThread implements Runnable
{
	private static org.apache.log4j.Logger logger = Logger.getLogger(AbstractExportDataThread.class);
	/** **/
	protected SessionDataBean sessionDataBean;
	/** **/
	protected ExportDataObject exportDataObject;

	/**
	 * PARAMETERIZED CONSTRUCTOR
	 * 
	 * @param edo
	 * @param sdb
	 */
	public AbstractExportDataThread(ExportDataObject exportDataObject, SessionDataBean sessionDataBean)
	{
		this.exportDataObject = exportDataObject;
		this.sessionDataBean = sessionDataBean;
	}

	/**
	 * 
	 * @return
	 */
	public SessionDataBean getSessionDataBean()
	{
		return sessionDataBean;
	}

	/**
	 * 
	 * @return
	 */
	public ExportDataObject getExportDataObject()
	{
		return exportDataObject;
	}

	/**
	 * 
	 * @param sessionDataBean
	 */
	public void setSessionDataBean(SessionDataBean sessionDataBean)
	{
		this.sessionDataBean = sessionDataBean;
	}

	/**
	 * 
	 * 
	 * @param exportDataObject
	 */
	public void setExportDataObject(ExportDataObject exportDataObject)
	{
		this.exportDataObject = exportDataObject;
	}

	/**
	 * 
	 * @param exportDataObject
	 * @return
	 */
	protected String getFileName(ExportDataObject exportDataObject)
	{
		AbstractQuery query = (AbstractQuery) exportDataObject.getExportObjectDetails().get(Constants.ABSTRACT_QUERY);
		String fileName = "Query_" + query.getQuery().getId().toString();
		return fileName;
	}
	
	/**
	 * 
	 * @param edo
	 * @param sdb
	 */
	protected abstract void postProcess(ExportDataObject exportDataObject,
			SessionDataBean sessionDataBean) throws BizLogicException;
	
	/**
	 * 
	 * @param exportDataObject
	 * @param exception
	 */
	protected abstract void handleException(ExportDataObject exportDataObject,
			Exception exception) throws Exception;

	/**
	 * 
	 * @param edo
	 * @throws QueryModuleException 
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	private final void executeQuery(ExportDataObject exportDataObject, String fileName) throws QueryModuleException, IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException
	{
		AbstractQuery abstractQuery = (AbstractQuery) exportDataObject.getExportObjectDetails()
				.get(Constants.ABSTRACT_QUERY);

		DataQueryExecution dataQueryExecutor = DataQueryExecutorFactory
		.getDefaultDataQueryExecutor(abstractQuery, abstractQuery.getQueryExecId(), ViewType.EXPORT_VIEW);
		
		DataHandlerParameter parameter = new DataHandlerParameter();
		parameter.setProperty(ParametersEnum.BUFFERSIZE, 500);
		// parameter.setProperty(ParametersEnum.BUFFERSIZE, 500);
		// parameter.setProperty(ParametersEnum.DELIMITER, Constants.DELIMETER);
		dataQueryExecutor.executeForExport(HandlerTypeEnum.CSV, parameter, fileName,
				(String)exportDataObject.getExportObjectDetails().get("role"));
		
		// return dataQueryExecutor.executeForExport();
	}

	/**
	 * Thread's RUN method
	 */
	public final void run()
	{
		// List<List<String>> dataList = null;
		try
		{
			String fileName = getFileName(exportDataObject);
			
			executeQuery(exportDataObject, Variables.exportHome+fileName+".csv");
			
			// ExportUtility.createCSV(dataList, Variables.exportHome+fileName+".csv");
			
			ExportUtility.createZip(Variables.exportHome, fileName+".csv", fileName);
	
			postProcess(exportDataObject, sessionDataBean);
		}
		catch (Exception exception)
		{	
			try
			{
				handleException(exportDataObject, exception);
			}
			catch (Exception ex)
			{
				logger.error(ex.getMessage(), ex);
			}
			logger.error(exception.getMessage(), exception);
		}
	}

}
