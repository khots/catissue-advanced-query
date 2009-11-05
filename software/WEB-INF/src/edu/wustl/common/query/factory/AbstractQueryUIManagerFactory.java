package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;

import javax.servlet.http.HttpServletRequest;

import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Factory to return the AbstractQueryUIManager instance.
 *
 * @author maninder_randhawa
 *
 */
public final class AbstractQueryUIManagerFactory
{
    /**
     * Private Default Constructor.
     */
    private AbstractQueryUIManagerFactory()
    {
        // empty constructor
    }

    /**
     * Method to create instance of class AbstractQueryUIManager.
     *
     * @return The reference of AbstractQueryUIManager.
     */
    public static AbstractQueryUIManager getDefaultAbstractUIQueryManager()
    {
        return (AbstractQueryUIManager) Utility
                .getObject(Variables.queryUIManagerClassName);
    }

    /**
     * Method to create instance of class AbstractQueryUIManager.
     *
     * @param className
     *            The class name.
     * @param request
     *            Http Servlet Request.
     * @param iquery
     *            IQuery Object.
     * @return The reference of AbstractQueryUIManager.
     * @throws QueryModuleException
     *             Query Module Exception.
     */
    public static AbstractQueryUIManager configureDefaultAbstractUIQueryManager(
            Class className, HttpServletRequest request, IQuery iquery)
            throws QueryModuleException
    {
        String queryUIManagerClass = Variables.queryUIManagerClassName;
        AbstractQueryUIManager abstractQueryUIManager = null;
        if (queryUIManagerClass != null)
        {
            try
            {
                className = Class.forName(queryUIManagerClass);
                if (className != null)
                {
                    Class[] parameterTypes = { HttpServletRequest.class,
                            IQuery.class };
                    Constructor declaredConstructor = className
                            .getDeclaredConstructor(parameterTypes);
                    abstractQueryUIManager = (AbstractQueryUIManager) declaredConstructor
                            .newInstance(request, iquery);
                }
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
        return abstractQueryUIManager;
    }
}
