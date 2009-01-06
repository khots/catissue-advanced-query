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
	private int user_id;
	
	public AbstractQuery(IQuery query,int user_id)
	{
		this.query = query;
		this.user_id = user_id;
	}
	
	public IQuery getQuery()
	{
		return query;
	}
	
	public void setQuery(IQuery query)
	{
		this.query = query;
	}
	
	public int getUser_id()
	{
		return user_id;
	}
	
	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}
}
