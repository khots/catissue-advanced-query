/**
 * 
 */
package edu.wustl.common.query.itablemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * @author supriya_dankh
 *
 */
public class DbDetails
{

	private Connection con;
	private int currentBatchCount;
	private PreparedStatement prepStmt;
	
	/**
	 * 
	 * @param con
	 * @param currentBatchCount
	 * @param prepStmt
	 */
	public DbDetails(Connection con,int currentBatchCount,PreparedStatement prepStmt)
	{
		this.con = con;
		this.currentBatchCount = currentBatchCount;
		this.prepStmt = prepStmt;
	}
	
	/**
	 * 
	 * @return
	 */
	public Connection getCon()
	{
		return con;
	}

	/**
	 * 
	 * @param con
	 */
	public void setCon(Connection con)
	{
		this.con = con;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCurrentBatchCount()
	{
		return currentBatchCount;
	}

	/**
	 * 
	 * @param currentBatchCount
	 */
	public void setCurrentBatchCount(int currentBatchCount)
	{
		this.currentBatchCount = currentBatchCount;
	}

	/**
	 * 
	 * @return
	 */
	public PreparedStatement getPrepStmt()
	{
		return prepStmt;
	}

	/**
	 * 
	 * @param prepStmt
	 */
	public void setPrepStmt(PreparedStatement prepStmt)
	{
		this.prepStmt = prepStmt;
	}
	
}
