
package edu.wustl.query.queryexecutionmanager;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.QueryBuildException;

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
	public List<List<Object>> executeForExport() throws QueryModuleException
	{

		return new ArrayList<List<Object>>();
	}
}
