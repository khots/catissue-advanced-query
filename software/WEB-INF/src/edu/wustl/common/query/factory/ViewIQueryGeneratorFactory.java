package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;

/**
 * Factory class to get the instance of the ViewIQueryGenerator implementation
 * class.
 *
 * @author vijay_pande
 *
 */
public final class ViewIQueryGeneratorFactory
{
    /**
     * Private Constructor.
     */
    private ViewIQueryGeneratorFactory()
    {
        // empty constructor.
    }

    /**
     * Method to create instance of class AbstractViewIQueryGenerator.
     *
     * @return The reference of AbstractViewIQueryGenerator.
     */
    public static AbstractViewIQueryGenerator getDefaultViewIQueryGenerator()
    {
        return (AbstractViewIQueryGenerator) Utility
                .getObject(Variables.viewIQueryGeneratorClassName);
    }
}

