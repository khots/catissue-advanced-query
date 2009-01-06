/**
 * 
 */
package edu.wustl.query.querymanager;

import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.domain.Workflow;


/**
 * @author supriya_dankh
 * The UI layer will use this class for all query backend related operations.
   Specific implementations will handle application specific business logic like for CIDER adding additional predicates (like active UPI flag) into the IQuery object.
  */
public abstract class AbstractQueryManager
{
  abstract public int execute(IQuery query);
  abstract public int execute(Workflow workflow);
  abstract public Count getQueryCount(int query_excecution_id);
  abstract public Count getWorkflowCount(int query_excecution_id);	
  abstract public void abort(int query_excecution_id);
}
