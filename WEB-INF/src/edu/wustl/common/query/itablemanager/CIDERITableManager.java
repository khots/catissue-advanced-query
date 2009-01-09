
package edu.wustl.common.query.itablemanager;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.util.global.Variables;

/**
 * This class is responsible for
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since January 7, 2009
 * 
 */
public class CIDERITableManager extends ITableManager
{

	private static DatabaseConnectionParams sDB_CONNECTION_PARAMS = null;

	/**
	 * 
	 * @param patientDeid
	 * @param queryExecLogId
	 * @param upi
	 * @throws SQLException
	 * @throws DAOException
	 */
	public void insertITableEntry(int patientDeid, int queryExecLogId, String upi)
			throws SQLException, DAOException
	{
		// CODE TO INSERT DATA INTO QUERY_ITABLE

		ArrayList<Object> columnValues = new ArrayList<Object>();
		columnValues.add(patientDeid);
		columnValues.add(queryExecLogId);
		columnValues.add(upi);
		sDB_CONNECTION_PARAMS.insert(Variables.ITABLE, columnValues);
	}

	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @param query_id
	 * @return
	 * @throws SQLException 
	 * @throws DAOException 
	 */
	public int insertNewQuery(int projectId, int userId, Long query_id) throws DAOException,
			SQLException
	{
		int queryExecId = -1;
		// CODE TO INSERT DATA INTO QUERY_EXECUTION_LOG Table

		ArrayList<Object> columnValues = new ArrayList<Object>();
		// get Query_Exec_Id
		// add it

		columnValues.add(projectId);
		columnValues.add(userId);
		columnValues.add(query_id);

		// add Status & Timestamp

		// return Query_Execution_Id

		sDB_CONNECTION_PARAMS.insert(Variables.EXECUTION_LOG_TABLE, columnValues);
		return queryExecId;
	}
}
