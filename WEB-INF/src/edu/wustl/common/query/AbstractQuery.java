/**
 * 
 */
package edu.wustl.common.query;

import edu.wustl.common.querysuite.queryobject.IQuery;


/**
 * 
 * 
 * @author supriya_dankh
 *
 */
public abstract class AbstractQuery
{
	/** IQuery **/
	private IQuery query;
	/** QUERY_EXECUTION_ID **/
	private int queryExecId;
	/** XQUERY **/
	private String queryString;
	/** USER ID **/
	private Long userId;
	/** machine IP ADDRESS from where query was fired **/
	private String ipAddress;
	
	/**
	 * Default Constructor
	 */
	public AbstractQuery()
	{
		// Default Constructor
	}
	
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
	 * gets query execution id
	 * @return queryExecId
	 */
	public int getQueryExecId()
	{
		return queryExecId;
	}

	/**
	 * gets query string
	 * @return queryString
	 */
	public String getQueryString()
	{
		return queryString;
	}

	/**
	 * sets query execution id
	 * @param queryExecId
	 */
	public void setQueryExecId(int queryExecId)
	{
		this.queryExecId = queryExecId;
	}

	
	/**
	 * sets query string
	 * @param queryString
	 */
	public void setQueryString(String queryString)
	{
		this.queryString = queryString;
	}

	/**
	 * gets query object
	 * @return query
	 */
	public IQuery getQuery()
	{
		return query;
	}
	
	/**
	 * sets query object
	 * @param query
	 */
	public void setQuery(IQuery query)
	{
		this.query = query;
	}
	
	/**
	 * gets user id 
	 * @return  userId
	 */
	public Long getUserId()
	{
		return userId;
	}
	
	/**
	 * sets user id
	 * @param userId
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	
	/**
	 * To get IP Address
	 * @return
	 */
	public String getIpAddress()
	{
		return ipAddress;
	}

	/**
	 * To set IP Address
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
}
