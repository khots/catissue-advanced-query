
package edu.wustl.query.queryexecutionmanager;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.beans.NodeInfo;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This is an abstract class which is extended by CIDERDataQueryExecution
 * Responsible for executing the data query
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since February 12, 2009
 */

public abstract class DataQueryExecution
{

	/**
	 * 
	 * @param abstractQuery
	 * @param primaryKeyList
	 * @return
	 */
	public List<List<Object>> executeDataQuery(AbstractQuery abstractQuery,
			List<List<Object>> primaryKeyList)
	{

		return new ArrayList<List<Object>>();
	}

	/**
	 * 
	 * @param abstractQuery
	 * @param primaryKeyList
	 * @return
	 */
	public List<List<Object>> executeDataQueryForExport(AbstractQuery abstractQuery,
			List<NodeInfo> primaryKeyList) throws QueryModuleException
	{

		return new ArrayList<List<Object>>();
	}
}
