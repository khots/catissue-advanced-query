/**
 * 
 */
package edu.wustl.common.query.itablemanager;

import java.sql.SQLException;
import java.util.Map;

import edu.wustl.common.util.dbManager.DAOException;

/**
 * @author supriya_dankh
 *
 */
public class ITableManager
{
	private int batchSize;
	private static ITableManager sINSTANCE; 
	
	/**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public static ITableManager getInstance() throws DAOException
	{
		if(sINSTANCE == null)
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
	public void insertITableEntry(int patientDeid, int queryExecLogId, String upi) throws SQLException, DAOException
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
	public int insertNewQuery(Long projectId, Long userId,
								Long query_id) throws DAOException, SQLException
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
}
