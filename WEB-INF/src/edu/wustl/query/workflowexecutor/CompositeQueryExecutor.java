
package edu.wustl.query.workflowexecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This class will handle execution of composite queries present inside the workflow.
 * @author maninder_randhawa
 *
 */
public class CompositeQueryExecutor
{

	private final AbstractQuery ciderQuery;
	//add comment change name of variable
	private List<Integer> queryExecIdList;
	private final ITableManager iTableManager;
	private final String sql;

	/**
	 * Parameterized constructor to be used for executing Composite queries with
	 * already executed child Parameterized queries.
	 *
	 * @param ciderQuery
	 *            Cider Query object.
	 * @param queryExecIdList
	 *            List of Query Execution Ids.
	 * @param sql
	 *            The SQL query string for executing this Composite query as a
	 *            separate query.
	 */
	public CompositeQueryExecutor(AbstractQuery ciderQuery, List<Integer> queryExecIdList, String sql)
	{
		this.ciderQuery = ciderQuery;
		this.queryExecIdList = queryExecIdList;
		this.sql = sql;

		this.iTableManager = ITableManagerFactory.getDefaultITableManager();
	}

	/**
	 * Parameterized constructor to be used when one or more PQs are not
	 * executed. The calling method is suppose to call the method - XXX while
	 * running the composite query. Refer to the method signature
	 *
	 * @param ciderQuery
	 *            The Cider Query object.
	 * @param sql
	 *            The SQL query string for executing this Composite query as a
	 *            separate query.
	 * @see XXX method.
	 */
	public CompositeQueryExecutor(AbstractQuery ciderQuery, String sql)
	{
		this.ciderQuery = ciderQuery;
		this.sql = sql;

		this.iTableManager = ITableManagerFactory.getDefaultITableManager();
	}


	/**
	 * This method will cancel execution of composite query.
	 * @param queryexecId
	 */
	public void cancel(int queryExecId) throws QueryModuleException
	{
		try
		{
			iTableManager.changeStatus(Constants.QUERY_CANCELLED, queryExecId);
		}
		catch (DAOException de)
		{
			QueryModuleException queryModExp = new QueryModuleException(de.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException sqe)
		{
			QueryModuleException queryModExp = new QueryModuleException(sqe.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
	}

	/**
	 * This method will add entry into query execution log for a composite query
	 * and will return query execution id  for it.
	 * @return queryExecId
	 */
	public int generateQueryExecId() throws QueryModuleException
	{
		int queryExecId = 0;

		try
		{
			queryExecId = iTableManager.insertNewQuery(ciderQuery);
		}
		catch (DAOException de)
		{
			QueryModuleException queryModExp = new QueryModuleException(de.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException sqe)
		{
			QueryModuleException queryModExp = new QueryModuleException(sqe.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}

		return queryExecId;
	}

	/**
	 * This method will execute a get count query which is there inside AbstractQuery.
	 * @return count
	 */
	public Count getCount() throws QueryModuleException
	{
		String status = checkStatus();
		Count count = new Count();
		int result;
		try
		{
			if (status.equals(Constants.QUERY_CANCELLED))
			{
				cancel(ciderQuery.getQueryExecId());
				//get the latest count
				count.setStatus(status);
			}
			else if (status.equals(Constants.QUERY_IN_PROGRESS)) {
				count.setStatus(status);
				// One of the child queries is yet still in progress so this query is not yet run.
				count.setCount(0);
			}
			else
			{
				//wrong impl. CiderGraph.generateSQL() shd get executed on the database which will return int value of the count
				// count = iTableManager.getCount(ciderQuery.getQueryExecId());

				ResultSet resultSet = iTableManager.executeCompositeQuery(sql);
				if(resultSet!=null)
				{
					result = resultSet.getInt(1);
					count.setCount(result);
					count.setStatus(Constants.QUERY_COMPLETED);
				}
			}
		}
		catch (DAOException de)
		{
			QueryModuleException queryModExp = new QueryModuleException(de.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException sqe)
		{
			QueryModuleException queryModExp = new QueryModuleException(sqe.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		count.setQueryExectionId(ciderQuery.getQueryExecId());

		return count;
	}



	/**
	 * Check status of queries for passed list of query execution id's.
	 * @return status
	 */
	private String checkStatus() throws QueryModuleException
	{
		String status = Constants.QUERY_COMPLETED;

		try
		{
			Iterator<Integer> queryExecIdListIter = queryExecIdList.iterator();
			Count count = null;

			while (queryExecIdListIter.hasNext())
			{
				count = iTableManager.getCount(queryExecIdListIter.next());
				if (count.getStatus().equals(Constants.QUERY_CANCELLED))
				{
					status = Constants.QUERY_CANCELLED;
					break;
				} else if (count.getStatus().equals(Constants.QUERY_IN_PROGRESS)) {
					status = Constants.QUERY_IN_PROGRESS;
					break;
				}
			}
		}
		catch (DAOException de)
		{
			QueryModuleException queryModExp = new QueryModuleException(de.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException sqe)
		{
			QueryModuleException queryModExp = new QueryModuleException(sqe.getMessage(),
					QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		return status;
	}

	/**
	 * This method executes the Composite Query.
	 * @throws QueryModuleException
	 * @throws SqlException
	 * @throws MultipleRootsException
	 */
	public int executeQuery() throws MultipleRootsException, SqlException, QueryModuleException
	{
        AbstractQueryManager qManager = AbstractQueryManagerFactory.getDefaultAbstractQueryManager();
        return qManager.execute(ciderQuery, queryExecIdList);
	}
}
