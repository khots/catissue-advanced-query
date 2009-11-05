package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.query.exportmanager.AbstractExportDataThread;
import edu.wustl.query.exportmanager.ExportDataObject;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Factory to return the instance of requested Thread object.
 *
 * @author ravindra_jain
 * @version 1.0
 * @created 02-Mar-2009 7:20:50 PM
 *
 */
public final class ThreadGeneratorFactory
{
    /**
     * Private Constructor.
     */
    private ThreadGeneratorFactory()
    {
        // empty constructor.
    }

    /**
     * Method to create instance of class AbstractExportDataThread.
     *
     * @param className
     *            Class Name.
     * @param exportDataObj
     *            Export Data Object.
     * @param sessionDataBean
     *            Session Data Bean Object.
     * @return The reference of AbstractExportDataThread.
     * @throws QueryModuleException
     *             Query Module Excpetion.
     */
    public static AbstractExportDataThread getDefaultAbstractExportDataThread(
            String className, ExportDataObject exportDataObj,
            SessionDataBean sessionDataBean) throws QueryModuleException
    {
        AbstractExportDataThread exportDataThread = null;
        if (className != null)
        {
            try
            {
                Class[] parameterTypes = { ExportDataObject.class,
                        SessionDataBean.class };
                Constructor declaredConstructor = Class.forName(className)
                        .getDeclaredConstructor(parameterTypes);
                exportDataThread = (AbstractExportDataThread) declaredConstructor
                        .newInstance(exportDataObj, sessionDataBean);
            } catch (ClassNotFoundException cnfe)
            {
                throw new QueryModuleException(cnfe.getMessage(), cnfe,
                        QueryModuleError.CLASS_NOT_FOUND);
            } catch (Exception e)
            {
                throw new QueryModuleException(e.getMessage(), e,
                        QueryModuleError.SQL_EXCEPTION);
            }
        }
        return exportDataThread;
    }
}

