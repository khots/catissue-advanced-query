
package edu.wustl.query.queryexecutionmanager;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.query.AbstractQuery;

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

	public List<List<Object>> executeDataQuery(AbstractQuery abstractQuery,
			List<List<Object>> primaryKeyList)
	{

		return new ArrayList<List<Object>>();
	}

}
