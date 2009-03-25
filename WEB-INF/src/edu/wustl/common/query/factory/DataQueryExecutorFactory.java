
package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.query.queryexecutionmanager.DataQueryExecution;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;

/**
 * Factory to return the instance of Data Query Execution class. 
 * @author ravindra_jain
 * @version 1.0
 * @created 03-Mar-2009 02:55:50 PM
 *
 */
public class DataQueryExecutorFactory
{
	/**
	 * 
	 * @return
	 */
	public static DataQueryExecution getDefaultDataQueryExecutor(AbstractQuery queryObj, int countQueryExecutionId,
			ViewType viewType) throws QueryModuleException
	{
		 String dataQueryExecutionClass = Variables.dataQueryExecutionClassName;
         DataQueryExecution abstractDataQueryExecutor = null;
         if(dataQueryExecutionClass!=null)
         {
			try
			{
				Class className = Class.forName(dataQueryExecutionClass);
				if(className != null)
		         {
		         	Class[] parameterTypes = {AbstractQuery.class,int.class, ViewType.class};
		         	Constructor declaredConstructor = className.getDeclaredConstructor(parameterTypes);
				   	abstractDataQueryExecutor =  (DataQueryExecution)declaredConstructor.newInstance(queryObj,countQueryExecutionId,viewType);
		         }
			}
			catch (ClassNotFoundException e)
			{
				throw new QueryModuleException(e.getMessage(),QueryModuleError.CLASS_NOT_FOUND);
			}
			catch (IllegalArgumentException e)
			{
				throw new QueryModuleException(e.getMessage(),QueryModuleError.SQL_EXCEPTION);
			}
			catch (InstantiationException e)
			{
				throw new QueryModuleException(e.getMessage(),QueryModuleError.SQL_EXCEPTION);
			}
			catch (IllegalAccessException e)
			{
				throw new QueryModuleException(e.getMessage(),QueryModuleError.SQL_EXCEPTION);
			}
			catch (InvocationTargetException e)
			{
				throw new QueryModuleException(e.getMessage(),QueryModuleError.SQL_EXCEPTION);
			}
			catch (NoSuchMethodException e)
			{
				throw new QueryModuleException(e.getMessage(),QueryModuleError.SQL_EXCEPTION);
			}
         }
         return abstractDataQueryExecutor;
	}
}
