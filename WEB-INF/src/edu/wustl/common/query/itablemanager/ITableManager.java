/**
 *
 */

package edu.wustl.common.query.itablemanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.wustl.common.beans.NodeInfo;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.DAOUtil;
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
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(ITableManager.class);
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
	 * @return Batch Size
	 */
	public int getBatchSize()
	{
		return batchSize;
	}

	/**
	 *
	 * @param batchSize batchSize
	 */
	public void setBatchSize(int batchSize)
	{
		this.batchSize = batchSize;
	}

	/**
	 * @param count_query_exec_id count query execution id
	 * @param upi upi
	 * @param dob date of birth
	 * @param statement PreparedStatement
	 * @throws SQLException SQLException
	 */
	public void insertITableEntry(Long count_query_exec_id, String upi, String dob,
			PreparedStatement statement, boolean generateDEID) throws SQLException
	{
		// CODE TO INSERT DATA INTO QUERY_ITABLE

	}

	/**
	 *
	 * @param abstractQuery AbstractQuery
	 * @return Count Query Execution Id
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 */
	public Long insertCountQuery(AbstractQuery abstractQuery) throws DAOException, SQLException
	{
		// CODE TO INSERT DATA INTO COUNT_QUERY_EXECUTION_LOG Table

		// return Count_Query_Execution_Id
		return -1L;
	}

	/**
	 * This method inserts the given query object into the Query_Execution_Log table.
	 * @param query AbstractQuery
	 * @return Query Execution Id
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 */
	public int insertNewQuery(AbstractQuery query) throws DAOException, SQLException
	{
		// CODE TO INSERT DATA INTO QUERY_EXECUTION_LOG Table

		// return Query_Execution_Id
		return -1;
	}

	/**
	 *
	 * @param status status
	 * @param queryExecId Query Execution Id
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException
	 */
	public void changeStatus(String status, Long queryExecId) throws SQLException, DAOException
	{

	}

	/**
	 *
	 * @param queryExecId query Execution Id
	 * @return Count
	 * @throws SQLException  SQLException
	 * @throws DAOException DAOException
	 */
	public Count getCount(Long queryExecId) throws SQLException, DAOException
	{
		Count count = null;
		return count;
	}

	/**
	 *
	 * @param query query
	 * @return ResultSet
	 * @throws DAOException  DAOException
	 */
	public ResultSet executeCompositeQuery(String query) throws DAOException
	{
		ResultSet resultSet = null;
		JDBCDAO jdbcDAO = null;
		try
		{
			jdbcDAO = DAOUtil.getJDBCDAO(null);
			resultSet = jdbcDAO.getQueryResultSet(query);
		}
		catch (DAOException ex)
		{
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		finally
		{
			DAOUtil.closeJDBCDAO(jdbcDAO);
		}
		return resultSet;
	}

	/**
	 * To insert details into QUERY EXECUTION LOG table
	 * @param xQuery
	 * @param query_type
	 * @param ipAddress
	 * @param projectId
	 * @throws DAOException
	 * @throws SQLException
	 */
	@Deprecated
	public Long insertQueryDetails(String xQuery, String query_type, String ipAddress,
			Long projectId, Long queryId, Long userId) throws DAOException, SQLException
	{
		return insertQueryDetails(xQuery, query_type, ipAddress, projectId, queryId, userId, null);
	}

	/**
	 * To insert details into QUERY EXECUTION LOG table
	 * @param xQuery xQuery
	 * @param query_type query type
	 * @param ipAddress ipAddress
	 * @param projectId project Id
	 * @param workFlowId workFlow Id
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 */
	public Long insertQueryDetails(String xQuery, String query_type, String ipAddress,
			Long projectId, Long queryId, Long userId, Long workFlowId) throws DAOException,
			SQLException
	{
		return -1L;
	}

	/**
	 * To insert data into QUERY_SECURITY_LOG table
	 * @param queryExecId queryExecId
	 * @param securityCode securityCode
	 */
	public void insertSecurityLog(Long queryExecId, String securityCode)
	{

	}

	/**
	 * To insert details into COUNT QUERY EXECUTION LOG table
	 * @param abstractQuery AbstractQuery
	 * @param countQueryExecId count Query Execution  Id
	 * @return Query Execution Id
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException
	 */
	public Long insertDataQuery(AbstractQuery abstractQuery, Long countQueryExecId)
			throws SQLException, DAOException
	{
		// CODE TO INSERT DATA INTO DATA_QUERY_EXECUTION_LOG Table

		// return Data_Query_Execution_Id
		return -1L;
	}

	/**
	 * Returns a list of upis.
	 * @param query_Exec_id query Execution id
	 * @return upis
	 * @throws QueryModuleException QueryModuleException
	 */
	public final List<NodeInfo> getUpiList(Long query_Exec_id) throws QueryModuleException
	{
		List<NodeInfo> upiList = new ArrayList<NodeInfo>();
		JDBCDAO jdbcDAO = null;
		ResultSet resultSet = null;
		try
		{
			try
			{
				jdbcDAO = DAOUtil.getJDBCDAO(null);
				String sql = Constants.SELECT + Constants.COUNT_QUERY_UPI + ","
						+ Constants.COUNT_QUERY_DOB + "," +
						Constants.PATIENT_DEID + Constants.FROM
						+ Constants.ITABLE + Constants.WHERE +
						Constants.COUNT_QUERY_EXECUTION_ID
						+ Constants.EQUALS + query_Exec_id;

				resultSet = jdbcDAO.getQueryResultSet(sql);

				while (resultSet.next())
				{
					NodeInfo nodeInfo = new NodeInfo();
					nodeInfo.setObj(resultSet.getObject(Constants.ONE));
					nodeInfo.setDob(resultSet.getTimestamp(Constants.TWO));
					nodeInfo.setPatientDeid(resultSet.getLong(Constants.THREE));
					upiList.add(nodeInfo);
				}
			}
			finally
			{
				if (resultSet != null)
				{
					resultSet.close();
					resultSet = null;
				}
				DAOUtil.closeJDBCDAO(jdbcDAO);
			}
		}
		catch (DAOException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.DAO_EXCEPTION);
		}
		catch (SQLException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.SQL_EXCEPTION);
		}
		return upiList;
	}

	/**
	 * Returns a list of upis.
	 * @param query_Exec_id query Execution id
	 * @param upi upi
	 * @return upis
	 * @throws QueryModuleException QueryModuleException
	 */
	public final List<NodeInfo> getUpiList(Long query_Exec_id, String upi)
			throws QueryModuleException
	{
		List<NodeInfo> upiList = new ArrayList<NodeInfo>();
		JDBCDAO jdbcDao = null;
		ResultSet resultSet = null;
		try
		{
			try
			{
				jdbcDao = DAOUtil.getJDBCDAO(null);
				String sql = Constants.SELECT + Constants.COUNT_QUERY_DOB + ","
						+ Constants.PATIENT_DEID + Constants.FROM + Constants.ITABLE
						+ Constants.WHERE +
						Constants.COUNT_QUERY_EXECUTION_ID + Constants.EQUALS
						+ query_Exec_id + " "
						+ edu.wustl.common.util.global.Constants.AND_JOIN_CONDITION + " "
						+ Constants.COUNT_QUERY_UPI + Constants.EQUALS + "'" + upi + "'";

				resultSet = jdbcDao.getQueryResultSet(sql);

				if (resultSet.next())
				{
					NodeInfo nodeInfo = new NodeInfo();
					nodeInfo.setObj(upi);
					nodeInfo.setDob(resultSet.getTimestamp(Constants.ONE));
					nodeInfo.setPatientDeid(resultSet.getLong(Constants.TWO));
					upiList.add(nodeInfo);
				}
			}
			finally
			{
				if (resultSet != null)
				{
					resultSet.close();
					resultSet = null;
				}
				DAOUtil.closeJDBCDAO(jdbcDao);
			}
		}
		catch (DAOException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.DAO_EXCEPTION);
		}
		catch (SQLException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.SQL_EXCEPTION);
		}
		return upiList;
	}

	/**
	 * @param queryId query Id
	 * @param userId user Id
	 * @param workflowId work-flow Id
	 * @param projectId project Id
	 * @return Latest Execution Count for given
	 * query or work-flow id
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException
	 */
	public Map<Long, Long> getLatestExecutionCountId(Long queryId, Long userId, Long workflowId,
			Long projectId) throws SQLException, DAOException
	{

		return null;
	}

	/**
	 * @param queryExecutionIdsList query Execution Id List
	 * @return map of query Execution Id Latest Execution Date
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException.
	 */
	public Map<Long, Date> getDateForLatestExecution(Collection<Long> queryExecutionIdsList)
			throws SQLException, DAOException
	{

		return null;
	}
	/**
	 * @param queryExecutionIdsList query Execution Id List
	 * @return Date For Latest Execution
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException.
	 */
	public List<Date> getDateForLatestExecution(Long queryExecutionIdsList)
			throws SQLException, DAOException
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
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException.
	 */
	public Long getLatestProjectId(Long workflowId, Long userId) throws SQLException, DAOException
	{
		return (long) -1;
	}

	/**
	 * @param queryId query Id
	 * @param userId user Id
	 * @param workflowId work-flow Id
	 * @param projectId project Id
	 * @return previously executed Queries
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException.
	 */
	public Set<Long> preExecutedQueries(Long queryId, Long userId, Long workflowId, Long projectId)
			throws SQLException, DAOException
	{
		return (new HashSet<Long>());
	}
    /**
     * @param queryId query Id
     * @param userId user Id
     * @return Latest Project Id and execution id
     * @throws SQLException SQLException
     * @throws DAOException DAOException.
     */
    public Map<Long, Long> getLatestProjectIdForQuery(Long queryId, Long userId)
    throws SQLException, DAOException
    {
    	return  null;
    }
	/**
	 * This method updates the finish time for query execution
	 * @param queryExeId queryExeId
	 * @param type count or data query
	 * @throws DAOException DAOException
	 */
	public void updateFinishTime(Long queryExeId,String type)throws DAOException
	{

	}

}

