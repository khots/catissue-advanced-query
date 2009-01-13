/**
 * 
 */
package edu.wustl.common.query;

import edu.wustl.common.querysuite.queryobject.IQuery;


/**
 * @author supriya_dankh
 *
 */
public abstract class AbstractQuery
{
	private IQuery query;
	private int queryExecId;
	private String queryString;
	private Long userId;
	
	/**
	 * 
	 * @param query
	 * @param queryExecId
	 * @param queryString
	 * @param user_id
	 */
	public AbstractQuery(IQuery query, int queryExecId, String queryString, Long userId)
	{
		this.query = query;
		this.queryExecId = queryExecId;
		this.queryString = queryString;
		this.userId = userId;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getQueryExecId()
	{
		return queryExecId;
	}

	/**
	 * 
	 * @return
	 */
	public String getQueryString()
	{
		return queryString;
	}

	/**
	 * 
	 * @param queryExecId
	 */
	public void setQueryExecId(int queryExecId)
	{
		this.queryExecId = queryExecId;
	}

	
	/**
	 * @param queryString
	 */
	public void setQueryString(String queryString)
	{
		this.queryString = queryString;
	}

	/**
	 * 
	 * @return
	 */
	public IQuery getQuery()
	{
		return query;
	}
	
	/**
	 * 
	 * @param query
	 */
	public void setQuery(IQuery query)
	{
		this.query = query;
	}
	
	/**
	 * 
	 * @return
	 */
	public Long getUserId()
	{
		return userId;
	}
	
	/**
	 * 
	 * @param user_id
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
}
