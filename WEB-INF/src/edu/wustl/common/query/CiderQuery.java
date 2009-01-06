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
	
	public CiderQuery(IQuery query,int user_id,int project_id)
	{
		super(query,user_id);
		this.project_id = project_id;
	}

	
	public int getProject_id()
	{
		return project_id;
	}

	
	public void setProject_id(int project_id)
	{
		this.project_id = project_id;
	}
}
