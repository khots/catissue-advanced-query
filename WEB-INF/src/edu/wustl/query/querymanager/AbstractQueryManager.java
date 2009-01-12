/**
 * 
 */
package edu.wustl.query.querymanager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.ExecutionManager.QueryExecutionThread;


/**
 * @author supriya_dankh
 * The UI layer will use this class for all query backend related operations.
 *  Specific implementations will handle application specific business logic 
 *  like for CIDER adding additional predicates (like active UPI flag) into the IQuery object.
 *  
 *  This class is also responsible for
 *   a.   Maintaining a list of currently running queries
 *   b.   Spawning new threads for new queries
 *   c.   Provide methods to get count, cancel/abort query threads etc.
 *  
 */
public abstract class AbstractQueryManager
{
	
	/**
	 * Map of QUERY_EXECUTION_ID(key) versus CORRESPONDING_THREAD(value)
	 */
	protected Map<Integer, QueryExecutionThread> queryThreads = new HashMap<Integer, QueryExecutionThread>();
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public int execute(AbstractQuery query)throws MultipleRootsException,SqlException
	{
		
		return 0;
	}
	
	/**
	 * 
	 * @param workflow
	 * @return
	 */
	abstract public int execute(Workflow workflow);
	
	/**
	 * 
	 * @param query_excecution_id
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	abstract public Count getQueryCount(int query_excecution_id) throws DAOException, SQLException;
	
	/**
	 * 
	 * @param query_excecution_id
	 * @return
	 */
	abstract public Count getWorkflowCount(int query_excecution_id);	
	
	/**
	 * 
	 * @param query_execution_id
	 */
	abstract public void cancel(int query_execution_id);
	
}
