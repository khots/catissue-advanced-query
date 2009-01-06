/**
 * 
 */
package edu.wustl.query.querymanager;

import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.domain.Workflow;


/**
 * @author supriya_dankh
 *
 */
public class CiderQueryManager extends AbstractQueryManager
{

	
	public int execute(IQuery query)
	{
		return 0;
	}

	
	public int execute(Workflow workflow)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public Count getQueryCount(int query_excecution_id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Count getWorkflowCount(int query_excecution_id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void abort(int query_excecution_id)
	{
		
	}
}
