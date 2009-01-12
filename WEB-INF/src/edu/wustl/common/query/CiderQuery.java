/**
 * 
 */
package edu.wustl.common.query;

import edu.wustl.common.querysuite.queryobject.IQuery;


/**
 * @author supriya_dankh
 *
 */
public class CiderQuery extends AbstractQuery
{
 
	long project_id;
	
	/**
	 * 
	 * @param query
	 * @param queryExecId
	 * @param queryString
	 * @param user_id
	 * @param project_id
	 */
	public CiderQuery(IQuery query,int queryExecId, String queryString, long user_id, long project_id)
	{
		super(query, queryExecId, queryString, user_id);
		this.project_id = project_id;
	}

	/**
	 * 
	 * @return
	 */
	public long getProject_id()
	{
		return project_id;
	}

	/**
	 * 
	 * @param project_id
	 */
	public void setProject_id(long project_id)
	{
		this.project_id = project_id;
	}
}
