/**
 *
 */

package edu.wustl.common.query.itablemanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wustl.common.beans.NodeInfo;
import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
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
	public static synchronized ITableManager getInstance()
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
	 * @param count
	 * @throws SQLException
	 * @throws DAOException
	 */
	public void insertITableEntry(int count_query_exec_id, String upi, String dob, int count, PreparedStatement statement)
			throws SQLException
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
	public int insertCountQuery(AbstractQuery abstractQuery) throws DAOException, SQLException
	{
		// CODE TO INSERT DATA INTO COUNT_QUERY_EXECUTION_LOG Table

		// return Count_Query_Execution_Id
		return -1;
	}


	/**
	 * This method inserts the given query object into the Query_Execution_Log table.
	 * @param query
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public int insertNewQuery(AbstractQuery query) throws DAOException, SQLException
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
	public void changeStatus(String status, int queryExecId, DatabaseConnectionParams dbConnectionParams) throws SQLException, DAOException
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

	/**
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 * @throws DAOException
	 */
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
			stmt=null;
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
	@Deprecated
    public void insertQueryDetails(int queryExecId, String xQuery, String query_type,
    		String ipAddress, Long projectId) throws DAOException, SQLException
    {
        insertQueryDetails(queryExecId, xQuery, query_type, ipAddress,
                projectId, null);
    }

    /**
	 * To insert details into QUERY EXECUTION LOG table
	 * @param queryExecId
	 * @param xQuery
	 * @param query_type
	 * @param ipAddress
	 * @param projectId
	 * @param workFlowId TODO
	 * @throws DAOException
	 * @throws SQLException
	 */
	public void insertQueryDetails(int queryExecId, String xQuery, String query_type,
			String ipAddress, Long projectId, Long workFlowId) throws DAOException, SQLException
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


	/**
	 * To insert details into COUNT QUERY EXECUTION LOG table
	 * @param count_query_exec_id
	 * @return
	 * @throws SQLException
	 * @throws DAOException
	 */
	public int insertDataQuery(AbstractQuery abstractQuery, int countQueryExecId)
			throws SQLException, DAOException
	{
		// CODE TO INSERT DATA INTO DATA_QUERY_EXECUTION_LOG Table

		// return Data_Query_Execution_Id
		return -1;
	}


	/**
	 * Returns a list of upis.
	 * @param query_Exec_id
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public final List<NodeInfo> getUpiList(int query_Exec_id) throws QueryModuleException
	{
		List<NodeInfo> upiList = new ArrayList<NodeInfo>();
		DatabaseConnectionParams DB_CONNECTION_PARAMS = new DatabaseConnectionParams();
		ResultSet resultSet = null;
		try
		{
			try
			{
				String sql = Constants.SELECT + Constants.COUNT_QUERY_UPI +","+Constants.COUNT_QUERY_DOB+","+Constants.PATIENT_DEID+ Constants.FROM + Constants.ITABLE + Constants.WHERE
						+ Constants.COUNT_QUERY_EXECUTION_ID + Constants.EQUALS + query_Exec_id;

				DB_CONNECTION_PARAMS.openSession(Constants.JNDI_NAME_CIDER);
				resultSet = DB_CONNECTION_PARAMS.getResultSet(sql);

				while (resultSet.next())
				{
					NodeInfo nodeInfo = new NodeInfo();
					nodeInfo.setObj(resultSet.getObject(1));
					nodeInfo.setDob(resultSet.getTimestamp(2));
					nodeInfo.setPatientDeid(resultSet.getLong(3));
					upiList.add(nodeInfo);
				}
			}
			finally
			{
				DB_CONNECTION_PARAMS.commit();
				if (resultSet != null)
				{
					resultSet.close();
					resultSet=null;
				}
				DB_CONNECTION_PARAMS.closeSession();
			}
		}
		catch (DAOException e)
		{
			throw new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
		}
		catch (SQLException e)
		{
			throw new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
		}
		return upiList;
	}

	/**
	 * Returns a list of upis.
	 * @param query_Exec_id
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public final List<NodeInfo> getUpiList(int query_Exec_id, String upi) throws QueryModuleException
	{
		List<NodeInfo> upiList = new ArrayList<NodeInfo>();
		DatabaseConnectionParams DB_CONNECTION_PARAMS = new DatabaseConnectionParams();
		ResultSet resultSet = null;
		try
		{
			try
			{
				String sql = Constants.SELECT + Constants.COUNT_QUERY_DOB+","+Constants.PATIENT_DEID + Constants.FROM + Constants.ITABLE + Constants.WHERE
						+ Constants.COUNT_QUERY_EXECUTION_ID + Constants.EQUALS + query_Exec_id + " " +Constants.AND_JOIN_CONDITION + " " + Constants.COUNT_QUERY_UPI + Constants.EQUALS + "'" + upi + "'";

				DB_CONNECTION_PARAMS.openSession(Constants.JNDI_NAME_CIDER);
				resultSet = DB_CONNECTION_PARAMS.getResultSet(sql);

				if(resultSet.next())
				{
					NodeInfo nodeInfo = new NodeInfo();
					nodeInfo.setObj(upi);
					nodeInfo.setDob(resultSet.getTimestamp(1));
					nodeInfo.setPatientDeid(resultSet.getLong(2));
					upiList.add(nodeInfo);
				}
			}
			finally
			{
				DB_CONNECTION_PARAMS.commit();
				if (resultSet != null)
				{
					resultSet.close();
					resultSet=null;
				}
				DB_CONNECTION_PARAMS.closeSession();
			}
		}
		catch (DAOException e)
		{
			throw new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
		}
		catch (SQLException e)
		{
			throw new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
		}
		return upiList;
	}



    /**
	 * @param queryId
	 * @param userId
	 * @param workflowId
	 * @param projectId
	 * @return
	 */
	public Map<Long, Integer> getLatestExecutionCountId(Long queryId, Long userId,
            Long workflowId, Long projectId) throws SQLException, DAOException
    {

        return null;
    }

    /**
     * This method returns the latest project that was accessed on the workflow
     * page.
     *
     * @param workflowId
     *            The Workflow Id.
     * @param userId
     *            The Current Logged In User Id
     * @return The Project Id that was last accessed
     * @throws SQLException
     * @throws DAOException
     */
    public Long getLatestProjectId(Long workflowId, Long userId)
            throws SQLException, DAOException
    {
        return (long)-1;
    }
}
