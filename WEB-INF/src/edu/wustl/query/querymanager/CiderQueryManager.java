/**
 * 
 */

package edu.wustl.query.querymanager;

import java.sql.SQLException;

import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.query.factory.QueryGeneratorFactory;
import edu.wustl.common.query.itablemanager.CIDERITableManager;
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
 * This class is used to perform cider specific back end operations.
 * @author supriya_dankh
 * 
 */
public class CiderQueryManager extends AbstractQueryManager
{

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
	public void cancel(int query_execution_id)
	{
		// LOGIC TO UPDATE STATUS OF THREAD in iTABLE LOG
		// Stop Thread corresponding to queryExecId

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
	public int execute(CiderQuery ciderQueryObj) throws MultipleRootsException, SqlException, QueryModuleException
	{ 
		//String generatedQuery = "select personUpi_1 Column0 from xmltable(' for $Person_1 in db2-fn:xmlcolumn(\"DEMOGRAPHICS.XMLDATA\")/Person where exists($Person_1/personUpi)  return <return><Person_1>{$Person_1}</Person_1></return>' columns personUpi_1 varchar(1000) path 'Person_1/Person/personUpi')";
		QueryModuleException queryModExp;
		int queryExecId = -1;
		try 
		{
			IQueryGenerator queryGenerator = QueryGeneratorFactory
					.getDefaultQueryGenerator();
			String generatedQuery = queryGenerator.generateQuery(ciderQueryObj
					.getQuery());
			
			//generatedQuery = "select personUpi_1 Column0 from xmltable(' for $Person_1 in db2-fn:xmlcolumn(\"DEMOGRAPHICS.XMLDATA\")/Person where exists($Person_1/personUpi)  return <return><Person_1>{$Person_1}</Person_1></return>' columns personUpi_1 varchar(1000) path 'Person_1/Person/personUpi')";

			Thread thread = null;
			QueryExecutionThread queryExecutionThread = null;

			queryExecId = CIDERQueryExecutionThread.getQueryExecutionId(ciderQueryObj);
			ciderQueryObj.setQueryExecId(queryExecId);
			ciderQueryObj.setQueryString(generatedQuery);

			thread = new Thread(new CIDERQueryExecutionThread(ciderQueryObj));

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
		finally
		{
			// close connection
			// close result set

			// UPDATE STATUS IN iTABLE LOG AS COMPLETED
			// call thread.stop
			queryThreads.remove(queryExecId);
		}

		return queryExecId;
	}
}
