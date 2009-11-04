package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.query.queryexecutionmanager.DataQueryExecution;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;

/**
 * Factory to return the instance of Data Query Execution class.
 *
 * @author ravindra_jain
 * @version 1.0
 * @created 03-Mar-2009 02:55:50 PM
 *
 */
public final class DataQueryExecutorFactory
{
    /**
     * Private Default constructor.
     */
    private DataQueryExecutorFactory()
    {
        // empty constructor.
    }

    /**
     * Method to create instance of the appropriate DataQueryExecution.
     *
     * @param queryObj
     *            Query Object.
     * @param countQueryExecutionId
     *            Query Execution Id.
     * @param viewType
     *            Type of View.
     * @return instance of the appropriate DataQueryExecution.
     * @throws QueryModuleException
     *             Qeury Module Exception.
     */
    public static DataQueryExecution getDefaultDataQueryExecutor(
            AbstractQuery queryObj, Long countQueryExecutionId,
            ViewType viewType) throws QueryModuleException
    {
        String dataQueryExecutionClass = Variables.dataQueryExecutionClassName;
        DataQueryExecution abstractDataQueryExecutor = null;
        try
        {
            if (dataQueryExecutionClass != null)
            {
                Class className = Class.forName(dataQueryExecutionClass);
                if (className != null)
                {
                    Class[] parameterTypes = { AbstractQuery.class, Long.class,
                            ViewType.class };
                    Constructor declaredConstructor = className
                            .getDeclaredConstructor(parameterTypes);
                    abstractDataQueryExecutor = (DataQueryExecution) declaredConstructor
                            .newInstance(queryObj, countQueryExecutionId,
                                    viewType);
                }
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
        return abstractDataQueryExecutor;
    }
}

