
package edu.wustl.query.exportmanager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.ThreadManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

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

	/** Represents Singleton instance of ExportDataManager **/
	private static ExportDataManager sINSTANCE;

	/**
	 * Key - query execution Id
	 * Value - AbstractExportDataThread
	 */
	private static Map<Integer, AbstractExportDataThread> exportDataThreadMap = Collections
			.synchronizedMap(new HashMap<Integer, AbstractExportDataThread>());

	/**
	 * Default Constructor
	 */
	public ExportDataManager()
	{
		// Default Constructor
	}

	/**
	 * To return Singleton instance of ExportDataManager
	 * @return
	 */
	public static ExportDataManager getInstance()
	{
		if (sINSTANCE == null)
		{
			sINSTANCE = new ExportDataManager();
		}
		return sINSTANCE;
	}

	/**
	 * 
	 * @param sdb
	 * @param edo
	 * @throws QueryModuleException 
	 */
	public static void export(SessionDataBean sessionDataBean, ExportDataObject exportDataObject)
			throws QueryModuleException
	{
		AbstractExportDataThread exportDataThread = ThreadManager.getExportDataThread(
				exportDataObject, sessionDataBean);

		AbstractQuery abstractQuery = (AbstractQuery) exportDataObject.getExportObjectDetails()
				.get(Constants.ABSTRACT_QUERY);

		exportDataThreadMap.put(abstractQuery.getQueryExecId(), exportDataThread);
	}

}