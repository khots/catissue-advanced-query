
package edu.wustl.common.query.factory;

import edu.wustl.common.util.Utility;
import edu.wustl.query.queryexecutionmanager.DataQueryExecution;
import edu.wustl.query.util.global.Variables;

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
	public static DataQueryExecution getDefaultDataQueryExecutor()
	{
		return (DataQueryExecution) Utility.getObject(Variables.dataQueryExecutionClassName);
	}

	/**
	 * Method to create instance of class AbstractQueryManager. 
	 * @return The reference of AbstractQueryManager. 
	 */
	public static DataQueryExecution configureDefaultDataQueryExecutor(String className)
	{
		return (DataQueryExecution) Utility.getObject(className);
	}

}
