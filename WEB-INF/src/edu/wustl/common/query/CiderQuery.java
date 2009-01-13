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
 
	private Long projectId;
	
	/**
	 * 
	 * @param query
	 * @param queryExecId
	 * @param queryString
	 * @param user_id
	 * @param project_id
	 */
	public CiderQuery(IQuery query,int queryExecId, String queryString, Long user_id, Long projectId)
	{
		super(query, queryExecId, queryString, user_id);
		this.projectId = projectId;
	}

	/**
	 * 
	 * @return
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * 
	 * @param project_id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
}
