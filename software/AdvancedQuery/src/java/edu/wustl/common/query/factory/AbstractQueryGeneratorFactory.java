package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.queryengine.impl.IQueryGenerator;
import edu.wustl.query.util.global.Variables;

/**
 * Factory to return the SqlGenerator's instance.
 *
 * @author deepti_shelar
 *
 */
public final class AbstractQueryGeneratorFactory
{
    /**
     * Private Constructor.
     */
    private AbstractQueryGeneratorFactory()
    {
        // empty constructor.
    }

    /**
     * Method to create instance of class SqlGenerator.
     *
     * @return The reference of SqlGenerator.
     */
    public static IQueryGenerator getDefaultQueryGenerator()
    {
        return (IQueryGenerator) Utility
                .getObject(Variables.queryGeneratorClassName);
    }

    /**
     * Method to create instance of class SqlGenerator.
     *
     * @param className
     *            The class name.
     * @return The reference of SqlGenerator.
     */
    public static IQueryGenerator configureQueryGenerator(String className)
    {
        return (IQueryGenerator) Utility.getObject(className);
    }
}
