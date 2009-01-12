
package edu.wustl.query.ExecutionManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.query.exeptions.QueryExecIdNotGeneratedException;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.query.memCache.UPIMemCache;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.Constants;

/**
 * This class represents the Thread to execute the query
 * This class is responsible for
 *   a.   Execute the query, read one record at time from the result set, 
 *   	  update the UPI cache and populate the iTable
 *   b.   Provide an interface to kill the thread which will cleanup the JDBC ResultSet and the UPI cache
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since January 7, 2009
 */

public class CIDERQueryExecutionThread implements Runnable
{

	CiderQuery ciderQueryObj;

	// JDBC result set for this query
	ResultSet results;

	// UPI in memory cache for this query
	UPIMemCache upiCache = null;
	
	/**
	 * External Condition which controls cancel
	 * operation of a Thread
	 */
	protected boolean cancelThread = false;

	/**
	 * PARAMETERIZED CONSTRUCTOR
	 * @param ciderQueryObj
	 */
	public CIDERQueryExecutionThread(CiderQuery ciderQueryObj)
	{
		upiCache = new UPIMemCache();
		this.ciderQueryObj = ciderQueryObj;
	}

	/**
	 *  To ABORT / CANCEL the THREAD (Query)
	 * @throws SQLException 
	 */
	public void cancel() throws SQLException
	{
		// Set appropriate status for CANCEL in query_execution_log
		// Stop Thread
		cancelThread = true;
		// results.close();
		// upiCache = null;
	}

	/**
	 * Thread's RUN method
	 */
	public void run()
	{
		String upi;
		int noOfRecords = 0;
		int patientDeid = -1;
		DatabaseConnectionParams dbConnectionParams = null;

		try
		{
			if (ciderQueryObj.getQueryExecId() == 0)
			{
				throw new QueryExecIdNotGeneratedException("");
			}

			dbConnectionParams = (DatabaseConnectionParams) DAOFactory.getInstance().getDAO(
					Constants.JDBC_DAO);
			dbConnectionParams.openSession(null);

			long queryExecutionStartTime = System.currentTimeMillis();

			results = dbConnectionParams.getResultSet(ciderQueryObj.getQueryString());

			long queryExecutionEndTime = System.currentTimeMillis();

			System.out.println("Executing Query :: " + ciderQueryObj.getQueryString());
			System.out.println("Time To execute query (in seconds) :: "
					+ (queryExecutionEndTime - queryExecutionStartTime));

			long stTime = System.currentTimeMillis();

			// set result set JDBC pre-fetch size to ensure batch read. 
			// Default is 10 and this should be tested with production database 
			// setting for various values to get the right value.

			while (results.isLast())
			{
				if (cancelThread)
				{
					break;
				}
				else
				{
					upi = results.getString(1);

					/**
					 * this has to be a blocking call to wait for more records 
					 * there has to be a way to differentiate between no more records (END_OF_QUERY) 
					 * and there are more records but have to wait for it (WAIT_FOR_MORE_RECORDS).
					 **/

					if (upiCache.add(upi))
					{
						//	LOGIC TO Generate DEID for patient
						// patientDeid = Get Patient DEID

						//	INSERTING UPI into ITABLE
						ITableManager manager = ITableManager.getInstance();
						manager.insertITableEntry(patientDeid, 
								ciderQueryObj.getQueryExecId(), upi);
					}
				}
			}

			long edTime = System.currentTimeMillis();

			System.out.println("TIME TO ITERATE OVER THE RESULT SET.... " + (edTime - stTime));

			System.out.println("EXECUTION COMPLETED");
			System.out.println(noOfRecords);

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (DAOException ex)
		{
			ex.printStackTrace();
		}
		catch (QueryExecIdNotGeneratedException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				results.close();
				dbConnectionParams.closeSession();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
			upiCache = null;
		}
	}

	/**
	 * Logic to calculate execution Id for each query
	 * @param ciderQueryObj
	 * @return Query Execution Id
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public static int getQueryExecutionId(CiderQuery ciderQueryObj) throws DAOException,
			SQLException
	{
		ITableManager manager = ITableManager.getInstance();

		return manager.insertNewQuery(ciderQueryObj.getProject_id(), ciderQueryObj.getUser_id(),
				ciderQueryObj.getQuery().getId());
	}
}
