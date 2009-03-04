
package edu.wustl.query.exportmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.beans.NodeInfo;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.DataQueryExecutorFactory;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.util.ExportReport;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.queryexecutionmanager.DataQueryExecution;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleException;

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
	 * @param edo
	 * @param sdb
	 */
	protected abstract void postProcess(ExportDataObject exportDataObject,
			SessionDataBean sessionDataBean);

	/**
	 * 
	 * @param edo
	 * @throws QueryModuleException 
	 */
	private final List executeQuery(ExportDataObject exportDataObject) throws QueryModuleException
	{
		DataQueryExecution dataQueryExecutor = DataQueryExecutorFactory
				.getDefaultDataQueryExecutor();

		AbstractQuery abstractQuery = (AbstractQuery) exportDataObject.getExportObjectDetails()
				.get(Constants.ABSTRACT_QUERY);

		List<NodeInfo> primaryKeyList = ITableManagerFactory.getDefaultITableManager().getUpiList(
				abstractQuery.getQueryExecId());

		return dataQueryExecutor.executeDataQueryForExport(abstractQuery, primaryKeyList);
	}

	/**
	 * 
	 * @param dataList
	 * @throws IOException 
	 */
	private final void generateCSV(List dataList) throws IOException
	{
		// Hard coded data for testing
		List<List<Object>> dList = new ArrayList<List<Object>>();
		List<Object> list = new ArrayList<Object>();
		list.add("abcd");
		list.add("99897");
		list.add("c");
		list.add("1025");
		
		dList.add(list);
		
		ExportReport report = new ExportReport("f:\\zip_Trial\\file.csv");
		report.writeData(dList, Constants.DELIMETER);
		report.closeFile();
	}

	/**
	 * Thread's RUN method
	 */
	public final void run()
	{
		List dataList = null;
		try
		{
			dataList = executeQuery(exportDataObject);

			generateCSV(dataList);
			
			ExportUtility.createZip("f:\\zip_Trial", "file.csv", "file.zip");
	
			postProcess(exportDataObject, sessionDataBean);
		}
		catch (QueryModuleException ex)
		{
			Logger.out.debug(ex.getMessage());
		}
		catch (IOException ex)
		{
			Logger.out.debug(ex.getMessage());
		}
	}

}