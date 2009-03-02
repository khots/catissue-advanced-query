
package edu.wustl.query.exportmanager;

import java.util.Map;

import edu.wustl.common.beans.SessionDataBean;

/**
 * This manager class will handle all the live export data threads
 * References of Export data threads will be maintained here
 * 
 * @author ravindra_jain
 * @version 1.0
 * @created 02-Mar-2009 3:24:31 PM
 */
public class ExportDataManager
{

	/**
	 * Key - query execution Id
	 * Value - ExportDataThread
	 */
	private Map exportDataThreadMap;

	/**
	 * Default Constructor
	 */
	public ExportDataManager()
	{
		// Default Constructor
	}

	/**
	 * 
	 * @param sdb
	 * @param edo
	 */
	public static void export(SessionDataBean sdb, ExportDataObject edo)
	{

	}

}