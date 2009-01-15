
package edu.wustl.query.ExecutionManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.query.exeptions.QueryExecIdNotGeneratedException;
import edu.wustl.common.query.itablemanager.CIDERITableManager;
import edu.wustl.common.query.memCache.UPIMemCache;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.querymanager.CiderQueryManager;
import edu.wustl.query.util.global.Constants;

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

	/** CIDER QUERY Object **/
	private CiderQuery ciderQueryObj;

	/** JDBC result set for this query **/
	private ResultSet results;

	/** UPI in memory cache for this query **/
	private UPIMemCache upiCache = null;

	/**
	 * External Condition which controls cancel
	 * operation of a Thread
	 */
	protected boolean cancelThread = false;

	/**
	 * 
	 * @return
	 */
	public CiderQuery getCiderQueryObj()
	{
		return ciderQueryObj;
	}

	/**
	 * 
	 * @return
	 */
	public ResultSet getResults()
	{
		return results;
	}

	/**
	 * 
	 * @return
	 */
	public UPIMemCache getUpiCache()
	{
		return upiCache;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCancelThread()
	{
		return cancelThread;
	}

	/**
	 * 
	 * @param ciderQueryObj
	 */
	public void setCiderQueryObj(CiderQuery ciderQueryObj)
	{
		this.ciderQueryObj = ciderQueryObj;
	}

	/**
	 * 
	 * @param results
	 */
	public void setResults(ResultSet results)
	{
		this.results = results;
	}

	/**
	 * 
	 * @param upiCache
	 */
	public void setUpiCache(UPIMemCache upiCache)
	{
		this.upiCache = upiCache;
	}

	/**
	 * 
	 * @param cancelThread
	 */
	public void setCancelThread(boolean cancelThread)
	{
		this.cancelThread = cancelThread;
	}

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
		int patientDeid = -1;
		DatabaseConnectionParams dbConnectionParams = null;

		try
		{
			CIDERITableManager manager = CIDERITableManager.getInstance();

			if (ciderQueryObj.getQueryExecId() == 0)
			{
				throw new QueryExecIdNotGeneratedException("");
			}

			dbConnectionParams = new DatabaseConnectionParams();
			dbConnectionParams.openSession(Constants.JNDI_NAME);

			results = dbConnectionParams.getResultSet(ciderQueryObj.getQueryString());

			// set result set JDBC pre-fetch size to ensure batch read. 
			// Default is 10 and this should be tested with production database 
			// setting for various values to get the right value.

			while (results.next())
			{
				if (cancelThread)
				{
					manager.changeStatus("Cancelled", ciderQueryObj.getQueryExecId());
					dbConnectionParams.commit();
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
						manager.insertITableEntry(patientDeid, 
								ciderQueryObj.getQueryExecId(), upi);
					}
				}
			}
			
			// UPDATE TABLE TO CHANGE QUERY STATUS TO 'COMPLETE'
			if (!cancelThread)
			{
				manager.changeStatus("Completed", ciderQueryObj.getQueryExecId());
				dbConnectionParams.commit();
				
			}
			
		}
		catch (SQLException ex)
		{
			Logger.out.debug(ex.getMessage());
		}
		catch (DAOException ex)
		{
			Logger.out.debug(ex.getMessage());
		}
		catch (QueryExecIdNotGeneratedException ex)
		{
			Logger.out.debug(ex.getMessage());
		}
		finally
		{
		 	try
			{
				new CiderQueryManager().removeThreadFromMap(ciderQueryObj.getQueryExecId());
				results.close();
				dbConnectionParams.closeSession();
				upiCache = null;
			}
			catch (SQLException e)
			{
				Logger.out.debug(e.getMessage());
			}
			catch (DAOException e)
			{
				Logger.out.debug(e.getMessage());
			}
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
		CIDERITableManager manager = CIDERITableManager.getInstance();

		return manager.insertNewQuery(ciderQueryObj.getProjectId(), ciderQueryObj.getUserId(),
				ciderQueryObj.getQuery().getId());
	}
}
