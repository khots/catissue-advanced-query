/**
 *
 */
package edu.wustl.common.query;

import edu.wustl.common.querysuite.queryobject.IAbstractQuery;


/**
 *
 *
 * @author supriya_dankh
 *
 */
public abstract class AbstractQuery
{
	private IAbstractQuery query;
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
	 * @param userId
	 */
	public AbstractQuery(IAbstractQuery query, int queryExecId, String queryString, Long userId)
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
	public IAbstractQuery getQuery()
	{
		return query;
	}

	/**
	 * sets query object
	 * @param query
	 */
	public void setQuery(IAbstractQuery query)
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

	@Override
	public boolean equals(Object obj)
	{
	    boolean isEqual = false;
	    if (obj instanceof AbstractQuery)
        {
	        if(((AbstractQuery)obj).getQuery().getId().equals(this.getQuery().getId()))
            {
	            isEqual = true;
            }
	        else
	        {
	            isEqual = false;
	        }
        }
	    else
        {
	        isEqual = false;
        }
	    return isEqual;
	}

	@Override
	public String toString()
	{
//	    StringBuilder toString = new StringBuilder();
//	    toString.append("Query[id:");
//	    toString.append(this.query != null ? this.query.getId() : "null");
//        toString.append(" execId:");
//        toString.append(this.queryExecId);
//        toString.append(" userId:");
//        toString.append(this.userId);
//        toString.append("]");
	    return "Query[id:"+this.query.getId()+" execId:"+this.queryExecId+" userId:"+this.userId+"]";
	}

	@Override
	public int hashCode()
	{
	    // FIXME For now just assume the Query Id as the hashcode
	    // To confirm that we also would need the userId in the hashcode


//	    StringBuilder hashCodeStr = new StringBuilder();
//	    hashCodeStr.append(this.query.getId());
//	    hashCodeStr.append(this.queryExecId);

	    return this.query.getId().intValue();
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
