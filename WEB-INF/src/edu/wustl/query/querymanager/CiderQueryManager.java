/**
 * 
 */
package edu.wustl.query.querymanager;

import java.sql.SQLException;

import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.query.factory.QueryGeneratorFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.queryengine.impl.IQueryGenerator;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.ExecutionManager.CIDERQueryExecutionThread;
import edu.wustl.query.ExecutionManager.QueryExecutionThread;

/**
 * @author supriya_dankh
 * 
 */
public class CiderQueryManager extends AbstractQueryManager {

	/**
	 * @param workflow
	 * @return
	 */
	public int execute(Workflow workflow) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param query_excecution_id
	 * @return
	 */
	public Count getQueryCount(int query_excecution_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param query_excecution_id
	 * @return
	 */
	public Count getWorkflowCount(int query_excecution_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/*
	 * To abort a Running Query
	 * 
	 * @param queryExecId
	 */
	public void cancel(int query_execution_id) {
		// LOGIC TO UPDATE STATUS OF THREAD in iTABLE LOG
		// Stop Thread corresponding to queryExecId

		queryThreads.remove(query_execution_id);

	}

	/**
	 * 
	 * @param query
	 *            SQL query
	 * @return Query Execution Id
	 */
	public int execute(CiderQuery ciderQueryObj) throws MultipleRootsException ,SqlException{
		int queryExecId = -1;
		try {
			IQueryGenerator queryGenerator = QueryGeneratorFactory
					.getDefaultQueryGenerator();
			String generatedQuery = queryGenerator.generateQuery(ciderQueryObj
					.getQuery());
			Thread thread = null;
			QueryExecutionThread queryExecutionThread = null;

			queryExecId = CIDERQueryExecutionThread
					.getQueryExecutionId(ciderQueryObj);

			/*
			 * LOGIC to Insert new entry into the QUERY_EXECUTION_LOG table
			 */

			// queryExecId = getQueryExecutionId();
			thread = new Thread(new CIDERQueryExecutionThread(ciderQueryObj));

			thread.start();

			queryThreads.put(queryExecId, queryExecutionThread);
		} catch (MultipleRootsException e) {
			e.printStackTrace();
		} catch (SqlException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close connection
			// close result set

			// UPDATE STATUS IN iTABLE LOG AS COMPLETED
			// call thread.stop
			queryThreads.remove(queryExecId);
		}

		return queryExecId;
	}
}
