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
 
	int project_id;
	
	/**
	 * 
	 * @param query
	 * @param queryExecId
	 * @param queryString
	 * @param user_id
	 * @param project_id
	 */
	public CiderQuery(IQuery query,int queryExecId, String queryString, int user_id, int project_id)
	{
		super(query, queryExecId, queryString, user_id);
		this.project_id = project_id;
	}

	/**
	 * 
	 * @return
	 */
	public int getProject_id()
	{
		return project_id;
	}

	/**
	 * 
	 * @param project_id
	 */
	public void setProject_id(int project_id)
	{
		this.project_id = project_id;
	}
}
