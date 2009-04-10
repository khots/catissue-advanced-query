
package edu.wustl.query.workflowexecutor;

import java.util.Map;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.query.domain.WorkflowDetails;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * The work flow manager class will handle all the operations related to work
 * flow. UI layer will always talk to this Class for all the UI related
 * operations.
 *
 * @author supriya_dankh
 *
 */
public class WorkflowManager
{
	private Map<Integer, WorkflowExecutor> execIdWorkflowExecutorMap;
	private WorkflowExecutor workflowExecutor;

	public WorkflowManager()
	{

	}

	public Map<Long, Integer> execute(WorkflowDetails workflowDetails,
            AbstractQuery ciderQuery, Map<Long, Integer> preExecIdMap) throws QueryModuleException,
            MultipleRootsException, SqlException
    {
        workflowExecutor = new WorkflowExecutor(workflowDetails);
        workflowExecutor.addAllExistingExecutionId(preExecIdMap);
        return workflowExecutor.execute(ciderQuery);
    }

	public Map<Long, Integer> execute(WorkflowDetails workflowDetails)
            throws QueryModuleException, MultipleRootsException, SqlException
    {
        workflowExecutor = new WorkflowExecutor(workflowDetails);
        return workflowExecutor.execute();
    }

	public Count getCount(int queryExecId,QueryPrivilege privilege) throws QueryModuleException
	{
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
		.getDefaultAbstractUIQueryManager();
		Count countObject = qUIManager.getCount(queryExecId,privilege);

		return countObject;
	}

	public void cancel(int workflowExecId){
		// TODO Add Implementation
	}



}