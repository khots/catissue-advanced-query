package edu.wustl.common.query.factory;

import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;

/**
 * Factory to return the Permissible Value Manager instance.
 *
 * @author Gaurav Sawant
 *
 */
public final class PermissibleValueManagerFactory
{
    /**
     * Private default constructor.
     */
    private PermissibleValueManagerFactory()
    {
        // empty constructor.
    }

    /**
     * Method to get the instance of the PermissibleValueManager.
     *
     * @return instance of the PermissibleValueManager.
     */
    public static IPermissibleValueManager getPermissibleValueManager()
    {
        IPermissibleValueManager permissibleValueManager = (IPermissibleValueManager) Utility
                .getObject(Variables.properties
                        .getProperty("permissiblevalue.manager.impl"));
        return permissibleValueManager;
    }
}

