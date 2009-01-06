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
	
	public DbDetails(Connection con,int currentBatchCount,PreparedStatement prepStmt)
	{
		this.con = con;
		this.currentBatchCount = currentBatchCount;
		this.prepStmt = prepStmt;
	}
	
	public Connection getCon()
	{
		return con;
	}

	public void setCon(Connection con)
	{
		this.con = con;
	}
	
	public int getCurrentBatchCount()
	{
		return currentBatchCount;
	}

	
	public void setCurrentBatchCount(int currentBatchCount)
	{
		this.currentBatchCount = currentBatchCount;
	}

	public PreparedStatement getPrepStmt()
	{
		return prepStmt;
	}

	
	public void setPrepStmt(PreparedStatement prepStmt)
	{
		this.prepStmt = prepStmt;
	}
	
}
