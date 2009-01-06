/**
 * 
 */
package edu.wustl.common.query.itablemanager;

import java.util.Map;


/**
 * @author supriya_dankh
 *
 */
public class ITableManager
{
	private int batchSize;
	private Map<Integer,DbDetails> dbDetailMap;
	
	public int getBatchSize()
	{
		return batchSize;
	}
	
	public void setBatchSize(int batchSize)
	{
		this.batchSize = batchSize;
	}
	
	public Map<Integer, DbDetails> getDbDetailMap()
	{
		return dbDetailMap;
	}
	
	public void setDbDetailMap(Map<Integer, DbDetails> dbDetailMap)
	{
		this.dbDetailMap = dbDetailMap;
	}
	
	public int getCount(int query_excecution_id)
	{
		return 0;
	}
	
	public String getInstance(int query_excecution_id)
	{
		return null;
	}
	
}
