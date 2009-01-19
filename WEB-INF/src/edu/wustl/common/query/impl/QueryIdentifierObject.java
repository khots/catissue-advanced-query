package edu.wustl.common.query.impl;


/**
 * QueryIdentifier is responsible for solely
 * identifying a query 
 * 
 * @author ravindra_jain
 * @version 1.0
 *
 */
public class QueryIdentifierObject
{
	private int queryId;
	
	private int userId;

	/**
	 * DEFAULT CONSTRUCTOR 
	 */
	public QueryIdentifierObject()
	{

	}

	/**
	 * CONSTRUCTOR 
	 */
	public QueryIdentifierObject(int queryId, int userId)
	{
		this.queryId = queryId;
		
		this.userId = userId;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getQueryId()
	{
		return queryId;
	}

	/**
	 * 
	 * @return
	 */
	public int getUserId()
	{
		return userId;
	}

	/**
	 * 
	 * @param queryId
	 */
	public void setQueryId(int queryId)
	{
		this.queryId = queryId;
	}

	/**
	 * 
	 * @param userId
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this.getClass().getName().equals(obj.getClass().getName()))
		{
			QueryIdentifierObject queryIdObj = (QueryIdentifierObject) obj;

			if(this.queryId == queryIdObj.queryId && this.userId == queryIdObj.userId)
			{
				return true;
			}
		}

		return false;
	}
}
