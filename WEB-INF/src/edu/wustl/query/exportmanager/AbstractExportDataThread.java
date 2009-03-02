
package edu.wustl.query.exportmanager;

import java.util.List;

import edu.wustl.common.beans.SessionDataBean;

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

	protected SessionDataBean sessionDataBean;
	protected ExportDataObject exportDataObject;

	/**
	 * PARAMETERIZED CONSTRUCTOR
	 * 
	 * @param edo
	 * @param sdb
	 */
	public AbstractExportDataThread(ExportDataObject edo, SessionDataBean sdb)
	{

	}

	/**
	 * 
	 * @param edo
	 * @param sdb
	 */
	protected abstract void postProcess(ExportDataObject edo, SessionDataBean sdb);

	/**
	 * 
	 * @param edo
	 */
	private List executeQuery(ExportDataObject edo)
	{
		return null;
	}

	/**
	 * 
	 * @param dataList
	 */
	private void generateCSV(List dataList)
	{

	}

	/**
	 * Thread's RUN method
	 */
	public void run()
	{

	}

}