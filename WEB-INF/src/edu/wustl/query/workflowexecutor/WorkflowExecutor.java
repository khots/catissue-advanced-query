/**
 *
 */

package edu.wustl.query.workflowexecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.domain.WorkflowDetails;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Class to execute workflow
 * Following task will be performed by the class
 * 		- Execute workflow
 * 		- Execute particular query of the workflow
 * 		- Get UPI list for the query
 * @author vijay_pande
 * @version 1.0
 * @since January 28, 2009
 */
public class WorkflowExecutor
{
	/**
	 * Static logger instance
	 */
	private static Logger log = edu.wustl.common.util.logger.Logger.getLogger(WorkflowExecutor.class);

	private final WorkflowDetails workflowDetails;
	private final AbstractQueryManager queryManager = AbstractQueryManagerFactory
			.getDefaultAbstractQueryManager();

	/**
	 * Constructor for class WorkflowExecutor
	 * @param workflowDetails object of WorkflowDetails
	 */
	public WorkflowExecutor(WorkflowDetails workflowDetails)
	{
		this.workflowDetails = workflowDetails;
	}

	/**
	 * Method which will execute particular query from the workflow
	 * @param ciderQuery object of CiderQuery
	 * @return
	 * @throws QueryModuleException generic query exception which contains exception details
	 * @throws MultipleRootsException
	 * @throws SqlException
	 */
	public Map<Long, Integer> execute(AbstractQuery ciderQuery) throws QueryModuleException, MultipleRootsException,
			SqlException
	{
        Map<Long, Integer> executionIdsMap = new HashMap<Long, Integer>();
		// determine if the given query is a PQ or CQ
		// check the condition -
		if (ciderQuery.getQuery() instanceof ICompositeQuery) {
			// Run the composite query
			ICompositeQuery cquery = (ICompositeQuery) ciderQuery.getQuery();

			// Need to spawn Cider execution threads from the child queries
			// For now we assume that there are only PQs in a CQ.
			// Ideally it is a recursive call.
			IAbstractQuery queryOne = cquery.getOperation().getOperandOne();
			IAbstractQuery queryTwo = cquery.getOperation().getOperandTwo();

			// Get all the PQs and start their execution.
			List<AbstractQuery> childQueryNodes =
				workflowDetails.getDependencyGraph().getChildNodes(ciderQuery);
			List<Integer> queryExecIdsList = new ArrayList<Integer>();

			for (AbstractQuery childQuery : childQueryNodes) {
//			    int execId = queryManager.execute(childQuery);
			    executionIdsMap.putAll(this.execute(childQuery));
//                executionIdsMap.put(childQuery.getQuery().getId(), execId);
			}
			queryExecIdsList.addAll(executionIdsMap.values());

			// Need to submit the cquery to the Composite Query Executer
			String cqSqlQueryString = workflowDetails.getDependencyGraph().generateSQL(ciderQuery);
			ciderQuery.setQueryString(cqSqlQueryString);

			CompositeQueryExecutor compositeQueryExecutor = new CompositeQueryExecutor(ciderQuery,queryExecIdsList,cqSqlQueryString);
			int cqExecId = compositeQueryExecutor.executeQuery();
			executionIdsMap.put(ciderQuery.getQuery().getId(), cqExecId);

		} else if(ciderQuery.getQuery() instanceof IParameterizedQuery) {
			// run the PQ only if it not executed previously
		    if (executionIdsMap.get(ciderQuery.getQuery().getId()) == null)
		    {
		        int queryExecId = queryManager.execute(ciderQuery);

		        // Use query Id as key
		        executionIdsMap.put(ciderQuery.getQuery().getId(), queryExecId);
		        log.debug("ciderQuery started Exec - " + queryExecId);
		    }
		}
		return executionIdsMap;
	}

	/**
	 * Method to execute workflow
	 * 		- First it will execute all Parameterized queries
	 * 		- The all the composite queries will be executed
	 * @throws QueryModuleException generic query exception which contains exception details
	 * @throws MultipleRootsException
	 * @throws SqlException
	 */
	public void execute() throws QueryModuleException, MultipleRootsException, SqlException
	{
		executeAllSimpleQueries();
		executeAllCompositeQueries();
	}

	/**
	 * Method to execute all parameterized queries in workflow
	 * @throws QueryModuleException generic query exception which contains exception details
	 * @throws MultipleRootsException
	 * @throws SqlException
	 */
	private void executeAllSimpleQueries() throws QueryModuleException, MultipleRootsException,
			SqlException
	{
		List<AbstractQuery> simpleQueries = workflowDetails.getDependencyGraph().getAllLeafNodes();
		for (AbstractQuery query : simpleQueries)
		{
			queryManager.execute(query);
		}
	}

	/**
	 *  Method to execute all composite queries in workflow
	 * @throws SqlException
	 * @throws MultipleRootsException
	 */
	private void executeAllCompositeQueries() throws QueryModuleException,
            MultipleRootsException, SqlException
	{
		List<AbstractQuery> compositeQueries = workflowDetails.getDependencyGraph()
				.getAllIntermediateNodes();
		for (AbstractQuery query : compositeQueries)
		{
//			CompositeQueryExecutor compositeQueryExecutor = new CompositeQueryExecutor(
//					query, workflowDetails.getDependencyGraph()
//							.getOutgoingEdgesExecIdList(query), workflowDetails
//							.getDependencyGraph().generateSQL(query));
//			compositeQueryExecutor.generateQueryExecId();
		    this.execute(query);
		}
	}

	/**
	 * Method to get UPI list that corresponds to CiderQuery
	 * @param ciderQuery
	 * @return
	 */
	public List<String> getUPIList(CiderQuery ciderQuery)
	{
		return null;
	}


	/**
	 * @param queryExecId
	 * @return
	 * @throws QueryModuleException
	 */
	public Count getCount(int queryExecId) throws QueryModuleException
	{
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
		.getDefaultAbstractUIQueryManager();
		Count countObject = qUIManager.getCount(queryExecId);
		return countObject;
	}
}
