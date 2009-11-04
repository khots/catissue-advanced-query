package edu.wustl.common.query.factory;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;

/**
 * Factory to return the AbstractQuery instance.
 *
 * @author maninder_randhawa
 *
 */
public final class AbstractQueryFactory
{

    /**
     * Private Constructor.
     */
    private AbstractQueryFactory()
    {
        // empty constructor.
    }

    /**
     * Method to create instance of class AbstractQuery.
     *
     * @return The reference of AbstractQuery.
     */
    public static AbstractQuery getDefaultAbstractQuery()
    {
        return (AbstractQuery) Utility
                .getObject(Variables.abstractQueryClassName);
    }

    /**
     *
     */
    /**
     * Method to create instance of class AbstractQuery.
     *
     * @param className
     *            The Class name.
     * @return The reference of AbstractQuery.
     */
    public static AbstractQuery configureAbstractQuery(String className)
    {
        return (AbstractQuery) Utility.getObject(className);
    }

}

