
package edu.wustl.query.util.global;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.ThreadGeneratorFactory;
import edu.wustl.query.exportmanager.AbstractExportDataThread;
import edu.wustl.query.exportmanager.ExportDataObject;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class is responsible for returning a requested Thread object
 * Logic to give a Thread - either new every time or through a Thread Pool
 * can be decided here
 *  
 * @author ravindra_jain
 * @version 1.0
 * @created 02-Mar-2009 3:24:31 PM
 */
public class ThreadManager
{

	/**
	 * Default Constructor
	 */
	public ThreadManager()
	{
		// Default Constructor
	}

	/**
	 * 
	 * @param edo
	 * @param sdb
	 * @throws QueryModuleException 
	 */
	public AbstractExportDataThread getExportDataThread(ExportDataObject exportDataObject,
			SessionDataBean sessionDataBean) throws QueryModuleException
	{
		AbstractExportDataThread exportDataThread = ThreadGeneratorFactory
				.getDefaultAbstractExportDataThread(Variables.exportDataThreadClassName,
						exportDataObject, sessionDataBean);

		Thread thread = new Thread(exportDataThread);
		thread.start();

		return exportDataThread;
	}

}