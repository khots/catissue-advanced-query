/**
 * 
 */

package edu.wustl.common.query.itablemanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
/**
 * @author supriya_dankh
 *
 */
public class ITableManager
{

	/**
	 * Static logger instance variable used for logging.
	 */
	private static org.apache.log4j.Logger logger =Logger.getLogger(ITableManager.class);
	/**
	 * To be used for batch inserts  
	 */
	private int batchSize;

	/**
	 * To return Singleton instance of 'ITableManager'
	 */
	private static ITableManager sINSTANCE;

	/**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public static synchronized ITableManager getInstance() throws DAOException
	{
		if (sINSTANCE == null)
		{
			sINSTANCE = new ITableManager();
		}
		return sINSTANCE;
	}

	/**
	 * 
	 * @return
	 */
	public int getBatchSize()
	{
		return batchSize;
	}

	/**
	 * 
	 * @param batchSize
	 */
	public void setBatchSize(int batchSize)
	{
		this.batchSize = batchSize;
	}

	/**
	 * 
	 * @param patientDeid
	 * @param queryExecLogId
	 * @param upi
	 * @throws SQLException
	 * @throws DAOException
	 */
	public void insertITableEntry(int count_query_exec_id, String upi, String dob)
			throws SQLException, DAOException
	{
		// CODE TO INSERT DATA INTO QUERY_ITABLE

	}

	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @param query_id
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public int insertCountQuery(AbstractQuery abstractQuery) throws DAOException,
			SQLException
	{
		// CODE TO INSERT DATA INTO QUERY_EXECUTION_LOG Table

		// return Query_Execution_Id
		return -1;
	}
	

	/**
	 * This method inserts the given query object into the Query_Execution_Log table.
	 * @param query
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public int insertNewQuery(AbstractQuery query) throws DAOException,
			SQLException
	{
		// CODE TO INSERT DATA INTO QUERY_EXECUTION_LOG Table

		// return Query_Execution_Id
		return -1;
	}	

	/**
	 * 
	 * @param status
	 * @param queryExecId
	 * @throws SQLException 
	 * @throws DAOException 
	 */
	public void changeStatus(String status, int queryExecId) throws SQLException, DAOException
	{

	}

	/**
	 * 
	 * @param queryExecId
	 * @return
	 * @throws SQLException
	 * @throws DAOException
	 */
	public Count getCount(int queryExecId) throws SQLException, DAOException
	{
		Count count = null;
		return count;
	}
	
	public ResultSet executeCompositeQuery(String query) throws SQLException, DAOException
	{
		DatabaseConnectionParams DB_CONNECTION_PARAMS = new DatabaseConnectionParams();
		Statement stmt = null;
		ResultSet resultSet = null;
		try
		{
			DB_CONNECTION_PARAMS.openSession(Constants.JNDI_NAME_CIDER);
			stmt = DB_CONNECTION_PARAMS.getDatabaseStatement();

			resultSet = stmt.executeQuery(query);
			DB_CONNECTION_PARAMS.commit();
		}
		catch (SQLException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		catch (DAOException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		finally
		{
			stmt.close();
			DB_CONNECTION_PARAMS.closeSession();
		}
		return resultSet;
	}	


	/**
	 * To insert details into QUERY EXECUTION LOG table
	 * @param queryExecId
	 * @param xQuery
	 * @param query_type
	 * @param ipAddress
	 * @param projectId
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void insertQueryDetails(int queryExecId, String xQuery, char query_type,
			String ipAddress, Long projectId) throws DAOException, SQLException
	{
		
	}
	
	
	/**
	 * To insert data into QUERY_SECURITY_LOG table
	 * @param queryExecId
	 * @param securityCode
	 */
	public void insertSecurityLog(int queryExecId, String securityCode)
	{
		
	}
}
