
package edu.wustl.query.util.global;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.query.exportmanager.AbstractExportDataThread;
import edu.wustl.query.exportmanager.ExportDataObject;

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
	 */
	public AbstractExportDataThread getExportDataThread(ExportDataObject edo, SessionDataBean sdb)
	{
		return null;
	}

}