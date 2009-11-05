package edu.wustl.query.bizlogic.helper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowDetails;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.workflowexecutor.WorkflowManager;

/**
 * This is a helper class for the WorkflowBizLogic class.
 *
 * @author Gaurav Sawant
 *
 */
public final class WorkflowBizLogicHelper
{

    /**
     * Singleton member instance.
     */
    private static WorkflowBizLogicHelper instance = null;

    /**
     * Workflow Manager instance.
     */
    private static WorkflowManager workflowManager = new WorkflowManager();

    /**
     * Empty private constructor.
     */
    private WorkflowBizLogicHelper()
    {
        // Empty constructor.
    }

    /**
     * Single Get Instance method to return the single instance.
     * @return The singleton instance of this helper class.
     */
    public static synchronized WorkflowBizLogicHelper getInstance()
    {
        if (instance == null)
        {
            instance = new WorkflowBizLogicHelper();
        }
        return instance;
    }


    /**
     * This method returns the name of workflow to validate it.
     * @param workflowName
     *            = name of workflow
     * @return workflow name
     */
    public String getWorkflowName(String workflowName)
    {
        String name = workflowName;
        if (name.contains("'"))
        {
            // replace "'" with "''"
            name = name.replace("'", "''");
        }
        return name;
    }

    /**
     * Returns the query string used to fetch the workflow.
     *
     * @param userId
     *            The User Id.
     * @param queryNameLike
     *            The partial matching worflow name.
     * @return The Hibernate Query used to fetch the workflow.
     */
    public String getWorkflowByIdQuery(Long userId, String queryNameLike)
    {
        StringBuilder query = new StringBuilder("select Workflow.id from ")
                .append(Workflow.class.getName()).append(
                        Constants.WORKFLOW_WHERE_CREATED_BY).append(userId);

        if (queryNameLike != null && !queryNameLike.equals(""))
        {
        	StringBuffer escapeSquence=new StringBuffer("");
            String queryNameLikeUpper = queryNameLike.toUpperCase();
            queryNameLikeUpper =  Utility.setSpecialCharacters(escapeSquence,
					queryNameLikeUpper);
            query.append(
                    " and upper(Workflow.name) like ").append("'").append("%").append(
                    queryNameLikeUpper).append("%").append("'").append(escapeSquence).append(
                    " or upper(Workflow.description) like ").append("'").append("%").append(
                    queryNameLikeUpper).append("%").append("'").append(escapeSquence).append(" order by Workflow.id desc");
        }
        return query.toString();
    }


    /**
     * This method executes the parameterized get count query and returns the
     * corresponding query execution id.
     *
     * @param query
     *            Query object
     * @param qUIManager
     *            Query UI manager object to be used.
     * @return The Query Execution Id.
     * @throws QueryModuleException
     *             Query Module Exception.
     * @throws DAOException
     *             Hibernate Exception.
     */
    public Long executeParameterisedGetCountQuery(IAbstractQuery query,
            AbstractQueryUIManager qUIManager) throws QueryModuleException,
            DAOException
    {
        IParameterizedQuery queryClone = (IParameterizedQuery) new DyExtnObjectCloner()
                .clone(query);
        Long queryExecId = qUIManager.searchQuery();
        qUIManager.insertParametersForExecution(queryExecId, queryClone);
        return queryExecId;
    }

    /**
     * This method executes the composite get count query and returns the
     * corresponding map of query-id v/s query execution id for the queries that
     * were executed.
     *
     * @param query
     *            Query object.
     * @param projectIdVal
     *            The project Id.
     * @param workflowDetails
     *            The Workflow Details Object.
     * @param ciderQuery
     *            The Cider Query object.
     * @return The map of query-id v/s query execution id for the queries that
     *         were executed.
     * @throws DAOException
     *             Hibernate Exception.
     * @throws SQLException
     *             SQL Exception.
     * @throws QueryModuleException
     *             Query Module Exception.
     * @throws MultipleRootsException
     *             Multiple Roots Exception.
     * @throws SqlException
     *             Query SQL Exception.
     */
    public Map<Long, Long> executeCompositeGetCountQuery(
            edu.wustl.common.querysuite.queryobject.impl.AbstractQuery query,
            Long projectIdVal, WorkflowDetails workflowDetails,
            AbstractQuery ciderQuery) throws DAOException, SQLException,
            QueryModuleException, MultipleRootsException, SqlException
    {
        Map<Long, Long> executionIdsMap = new HashMap<Long, Long>();
        Long projectId = null;
        if (projectIdVal > 0)
        {
            projectId = projectIdVal;
        }

        long userId = ((CiderWorkFlowDetails) workflowDetails).getUserId();
        Long workflowId = workflowDetails.getWorkflow().getId();

        Map<Long, Long> preExecIdMap = generateQueryExecIdMap(userId,
                workflowId, projectId);
        preExecIdMap.remove(query.getId());
        executionIdsMap.putAll(workflowManager.execute(workflowDetails,
                ciderQuery, preExecIdMap));
        return executionIdsMap;
    }


    /**
     * This method generates the map of query ids against the coressponding
     * latest query excution ids (if any).
     *
     * @param userId
     *            user Id
     * @param workflowId
     *            workflowId
     * @param projectId
     *            project Id
     * @return map of query id and execution id.
     * @throws DAOException
     *             Hibernate DAO Exception.
     * @throws SQLException
     *             SQL Exception.
     */
    public Map<Long, Long> generateQueryExecIdMap(Long userId, Long workflowId,
            Long projectId) throws DAOException, SQLException

    {
        // Get the map of execution Ids from DB
        ITableManager itableManager = ITableManagerFactory
                .getDefaultITableManager();
        Map<Long, Long> execIdMap = itableManager.getLatestExecutionCountId(
                null, userId, workflowId, projectId);
        return execIdMap;
    }

    /**
     * @param queryExecId query Execution Id
     * @param privilege QueryPrivilege
     * @return Count
     * @throws QueryModuleException QueryModuleException
     */
    public Count getCount(Long queryExecId, QueryPrivilege privilege) throws QueryModuleException
    {
        return workflowManager.getCount(queryExecId, privilege);
    }

    /**
     * While adding an item to work-flow this method checks that does it already
     * exist or not ? if exists then return false
     *
     * @param workflowItemList
     *            work-flow Item List
     * @param queryId
     *            query Id
     * @return boolean value for already exist or not
     */
    public boolean isQueryAlreadyExists(List<WorkflowItem> workflowItemList,
            Long queryId)
    {
        boolean isAlreadyExist = true;
        if (workflowItemList != null && queryId != null)
        {
            Iterator<WorkflowItem> workflowItemIter = workflowItemList
                    .iterator();
            while (workflowItemIter.hasNext())
            {
                if (workflowItemIter.next().getQuery().getId().equals(queryId))
                {
                    isAlreadyExist = false;
                    break;
                }
            }
        }
        return isAlreadyExist;
    }

}
