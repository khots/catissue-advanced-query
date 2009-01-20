/**
 * 
 */

package edu.wustl.query.querymanager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.query.factory.QueryGeneratorFactory;
import edu.wustl.common.query.itablemanager.CIDERITableManager;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.queryengine.impl.IQueryGenerator;
import edu.wustl.query.queryexecutionmanager.CIDERQueryExecutionThread;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class is used to perform cider specific back end operations.
 * @author supriya_dankh
 * 
 */
public class CiderQueryManager extends AbstractQueryManager
{

	/**
	 * Map of QUERY_EXECUTION_ID(key) versus CORRESPONDING_THREAD(value)
	 */
	private Map<Integer, CIDERQueryExecutionThread> queryThreads = new HashMap<Integer, CIDERQueryExecutionThread>();

	/**
	 * @param workflow
	 * @return
	 */
	public int execute(Workflow workflow)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * gets query count.
	 * 
	 * @param query_excecution_id
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public Count getQueryCount(int query_excecution_id) throws DAOException, SQLException
	{
		Count count = null;
		CIDERITableManager manager = CIDERITableManager.getInstance();
		count = manager.getCount(query_excecution_id);
		return count;
	}

	/**
	 * gets work flow count.
	 * 
	 * @param query_excecution_id
	 * @return
	 */
	public Count getWorkflowCount(int query_excecution_id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/*
	 * To abort a Running Query
	 * 
	 * @param queryExecId
	 */
	public void cancel(int query_execution_id) throws SQLException
	{
		CIDERQueryExecutionThread thread = queryThreads.get(query_execution_id);
		thread.cancel();
		queryThreads.remove(query_execution_id);
	}

	/**
	 * To generate the query  string from coder query object and execute it in a thread.
	 * 
	 * @param ciderQueryObj
	 * @return
	 * @throws MultipleRootsException
	 * @throws QueryModuleException 
	 */
	public int execute(CiderQuery ciderQueryObj) throws MultipleRootsException, SqlException,
			QueryModuleException
	{
		String generatedQuery = "";
		QueryModuleException queryModExp;
		int queryExecId = -1;
		try
		{
			IQueryGenerator queryGenerator = QueryGeneratorFactory.getDefaultQueryGenerator();
			generatedQuery = queryGenerator.generateQuery(ciderQueryObj.getQuery());
			

			Thread thread = null;
			CIDERQueryExecutionThread queryExecutionThread = null;

			queryExecId = CIDERQueryExecutionThread.getQueryExecutionId(ciderQueryObj);
			ciderQueryObj.setQueryExecId(queryExecId);
			ciderQueryObj.setQueryString(generatedQuery);
			
			queryExecutionThread = new CIDERQueryExecutionThread(ciderQueryObj); 
			
			thread = new Thread(queryExecutionThread);
			queryThreads.put(queryExecId, queryExecutionThread);

			thread.start();
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}

		return queryExecId;
	}

	/**
	 * 
	 * @param queryExecId
	 */
	public void removeThreadFromMap(int queryExecId)
	{
		queryThreads.remove(queryExecId);
	}
}
