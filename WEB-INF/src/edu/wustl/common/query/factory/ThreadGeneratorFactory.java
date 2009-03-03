
package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.query.exportmanager.AbstractExportDataThread;
import edu.wustl.query.exportmanager.ExportDataObject;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Factory to return the instance of requested Thread object. 
 * @author ravindra_jain
 * @version 1.0
 * @created 02-Mar-2009 7:20:50 PM
 *
 */
public class ThreadGeneratorFactory
{

	/**
	 * Method to create instance of class AbstractExportDataThread. 
	 * @return The reference of AbstractExportDataThread. 
	 */
	public static AbstractExportDataThread getDefaultAbstractExportDataThread(
			String className, ExportDataObject edo, SessionDataBean sdb)
			throws QueryModuleException
	{
		AbstractExportDataThread exportDataThread = null;
		QueryModuleException queryModuleException = null;

		if (className != null)
		{
			try
			{
				Class[] parameterTypes = {ExportDataObject.class, SessionDataBean.class};
				Constructor declaredConstructor = Class.forName(className).getDeclaredConstructor(
						parameterTypes);
				exportDataThread = (AbstractExportDataThread) declaredConstructor.newInstance(edo,
						sdb);
			}
			catch (ClassNotFoundException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),
						QueryModuleError.CLASS_NOT_FOUND);
				throw queryModuleException;
			}
			catch (IllegalArgumentException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),
						QueryModuleError.SQL_EXCEPTION);
				throw queryModuleException;
			}
			catch (InstantiationException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),
						QueryModuleError.SQL_EXCEPTION);
				throw queryModuleException;
			}
			catch (IllegalAccessException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),
						QueryModuleError.SQL_EXCEPTION);
				throw queryModuleException;
			}
			catch (InvocationTargetException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),
						QueryModuleError.SQL_EXCEPTION);
				throw queryModuleException;
			}
			catch (NoSuchMethodException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),
						QueryModuleError.SQL_EXCEPTION);
				throw queryModuleException;
			}
		}

		return exportDataThread;
	}

}
