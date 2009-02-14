/**
 * 
 */

package edu.wustl.common.query.itablemanager;

import java.sql.SQLException;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.querymanager.Count;

/**
 * @author supriya_dankh
 *
 */
public class ITableManager
{

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
	public static ITableManager getInstance() throws DAOException
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
