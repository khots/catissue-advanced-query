/**
 * 
 */
package edu.wustl.common.query;

import edu.wustl.common.querysuite.queryobject.IQuery;


/**
 * This class is used to create Cider Specific Query Object.
 * 
 * @author supriya_dankh
 *
 */
public class CiderQuery extends AbstractQuery
{
 
	private Long projectId;
	
	/**
	 * Default Constructor
	 */
	public CiderQuery()
	{
		// Default Constructor
	}
	
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
	 * gets the project id
	 * 
	 * @return
	 */
	public Long getProjectId()
	{
		return projectId;
	}

	/**
	 * sets the project id
	 * 
	 * @param project_id
	 */
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
}
