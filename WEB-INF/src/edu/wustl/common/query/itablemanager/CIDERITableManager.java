
package edu.wustl.common.query.itablemanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.DatabaseConnectionParams;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.Constants;
import edu.wustl.query.querymanager.Count;
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

	private static DatabaseConnectionParams sDB_CONNECTION_PARAMS = (DatabaseConnectionParams) DAOFactory
			.getInstance().getDAO(Constants.JDBC_DAO);
	
	private static CIDERITableManager sINSTANCE; 
	
	/**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public static CIDERITableManager getInstance() throws DAOException
	{
		if(sINSTANCE == null)
		{
			sINSTANCE = new CIDERITableManager();
		}
		return sINSTANCE;
	}
	/**
	 * 
	 * @param patientDeid
	 * @param queryExecLogId
	 * @param upi
	 * @throws DAOException
	 * @throws SQLException 
	 */
	public void insertITableEntry(int patientDeid, int queryExecLogId, String upi) throws DAOException, SQLException
	{
		try
		{
			// CODE TO INSERT DATA INTO QUERY_ITABLE
			sDB_CONNECTION_PARAMS.openSession(null);
	
			ArrayList<Object> columnValues = new ArrayList<Object>();
			columnValues.add(patientDeid);
			columnValues.add(queryExecLogId);
			columnValues.add(upi);
			sDB_CONNECTION_PARAMS.insert(Variables.ITABLE, columnValues);
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(DAOException ex)
		{
			throw ex;
		}
		finally
		{
			sDB_CONNECTION_PARAMS.closeSession();
		}
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
	public int insertNewQuery(Long projectId, Long userId, Long query_id) throws SQLException, DAOException
	{
		int queryExecId = -1;
		ResultSet rs = null;
		
		try
		{
			sDB_CONNECTION_PARAMS.openSession(null);
	
			Statement stmt = sDB_CONNECTION_PARAMS.getDatabaseStatement();
			
			java.util.Date today = new java.util.Date();
		    java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
			// Insert a row
			// Indicate you want automatically 
			// generated keys
			String query = "INSERT INTO "
					+ Variables.EXECUTION_LOG_TABLE
					+ "(creationTime, USER_ID, status, project_id, query_execution_id, query_id) VALUES "
					+ "(" + timestamp + "," + userId + ",in-progress" + "," + projectId + ",default,"
					+ query_id + ")";
	
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.getGeneratedKeys(); 
			// Retrieve the automatically
			// generated key value in a ResultSet.
			// Only one row is returned.
			// Create ResultSet for query
			while (rs.next())
			{
				queryExecId = rs.getInt(1);
				// Get automatically generated key 
				// value
				System.out.println("automatically generated key value = " + queryExecId);
			}
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(DAOException ex)
		{
			throw ex;
		}
		finally
		{
			sDB_CONNECTION_PARAMS.closeSession();
		}

		return queryExecId;
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
		try
		{
			sDB_CONNECTION_PARAMS.openSession(null);
			Statement stmt = sDB_CONNECTION_PARAMS.getDatabaseStatement();
			
			String query = "UPDATE "+Variables.EXECUTION_LOG_TABLE+" SET STATUS="+status+" WHERE query_execution_id="+queryExecId;
			
			stmt.executeUpdate(query);
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(DAOException ex)
		{
			throw ex;
		}
		finally
		{
			sDB_CONNECTION_PARAMS.closeSession();
		}
	}
	
	/**
	 * 
	 * @param queryExecId
	 * @return
	 * @throws SQLException
	 * @throws DAOException
	 */
	public Count getCount(int queryExecId)throws SQLException, DAOException
	{
		Count count = new Count();
		try
		{
			sDB_CONNECTION_PARAMS.openSession(null);
			Statement stmt = sDB_CONNECTION_PARAMS.getDatabaseStatement();
			String query = "select count(*) from "+Variables.ITABLE +"where query_excecution_id="+queryExecId;
			ResultSet rs = stmt.executeQuery(query);
			count.setCount(rs.getInt(0));
			query = "select status from "+Variables.EXECUTION_LOG_TABLE+" where query_excecution_id="+queryExecId;
			rs = stmt.executeQuery(query);
			String status = rs.getString(0);
			count.setQuery_exection_id(queryExecId);
			count.setStatus(status);
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(DAOException ex)
		{
			throw ex;
		}
		finally
		{
			sDB_CONNECTION_PARAMS.closeSession();
		}
		return count;
		
	}
}
