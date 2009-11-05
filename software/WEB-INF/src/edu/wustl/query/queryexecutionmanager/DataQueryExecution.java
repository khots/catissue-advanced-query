
package edu.wustl.query.queryexecutionmanager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.datahandler.DataHandlerParameter;
import edu.wustl.common.datahandler.HandlerTypeEnum;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class is extended by CIDERDataQueryExecution
 * Responsible for executing the data query
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since February 12, 2009
 */

public class DataQueryExecution
{

	/**
	 * 
	 * @param abstractQuery
	 * @param primaryKeyList
	 * @return
	 */
	public List<List<Object>> execute() throws QueryModuleException 
	{

		return new ArrayList<List<Object>>();
	}

	/**
	 * 
	 * @param abstractQuery
	 * @param primaryKeyList
	 * @return
	 */
	public void executeForExport(HandlerTypeEnum handlerType, DataHandlerParameter parameter, String fileName,
			String role) throws QueryModuleException, ClassNotFoundException,
	IllegalArgumentException, InstantiationException, IllegalAccessException,
	InvocationTargetException, SecurityException, NoSuchMethodException, IOException
	{

		// return new ArrayList<List<Object>>();
	}
}
