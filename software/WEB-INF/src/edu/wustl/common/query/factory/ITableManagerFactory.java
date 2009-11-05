/**
 *
 */
package edu.wustl.common.query.factory;

import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;

/**
 * Factory to return the Query ITableManager instance.
 *
 * @author Gaurav Sawant
 *
 */
public final class ITableManagerFactory
{
    /**
     * Default Empty Constructor.
     */
    private ITableManagerFactory()
    {
        // empty constructor.
    }

    /**
     * Method to create the default instance of class ITableManager.
     *
     * @return The reference of default instance of ITableManager.
     */
    public static ITableManager getDefaultITableManager()
    {
        return (ITableManager) Utility
                .getObject(Variables.queryITableManagerClassName);
    }

    /**
     * Method to create instance of class ITableManager.
     * @param className Class name.
     * @return The reference of ITableManager.
     */
    public static ITableManager configureDefaultITableManager(String className)
    {
        return (ITableManager) Utility.getObject(className);
    }

}

