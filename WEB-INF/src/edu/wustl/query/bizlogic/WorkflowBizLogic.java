
package edu.wustl.query.bizlogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.ICompositeQuery;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.DAO;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.helper.WorkflowBizLogicHelper;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowDetails;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.workflowexecutor.WorkflowManager;

/**
 * BizLogic class to insert/update WorkFlow Object
 *
 * @author vijay_pande
 */
public class WorkflowBizLogic extends DefaultQueryBizLogic
{

	/**
	 * Private logger instance.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(WorkflowBizLogic.class);

	// private final WorkflowManager workflowManager = new WorkflowManager();

	/**
	 * Inserts domain object.
	 *
	 * @param obj
	 *            The object to be inserted.
	 * @param dao
	 *            the dao object.
	 * @param sessionDataBean
	 *            session specific data.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	@Override
	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean)
			throws BizLogicException
	{
		Workflow workflow = (Workflow) obj;
		workflow.setCreatedBy(sessionDataBean.getUserId());

		logger.info("In  WORKFLOW  BIZ LOGIC >>>>>> INSERT METHOD");
		logger.info("#### Workflow Name #### ::  " + workflow.getName());
		try
		{
			for (WorkflowItem workflowItem : workflow.getWorkflowItemList())
			{
				IAbstractQuery query = workflowItem.getQuery();
				if (query.getId() == null)
				{
					saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query);
				}
			}
			// dao.insert(workflow, sessionDataBean, false, false); not in new
			// common package
			dao.insert(workflow, false);

		}
		catch (DAOException e)
		{
			throw new BizLogicException(e);
		}
	}

	/**
	 * Method to save compositeQuery object.
	 *
	 * @param dao
	 *            Object of DAO.
	 * @param sessionDataBean
	 *            object of SessionDataBean.
	 * @param query
	 *            object of ICompositeQuery to be saved.
	 * @throws DAOException
	 *             DAO exception.
	 */
	private void saveCompositeQuery(DAO dao, SessionDataBean sessionDataBean, ICompositeQuery query)
			throws DAOException
	{
		query.setCreatedBy(sessionDataBean.getUserId());
		query.setCreatedDate(new Date());

		query.setUpdatedBy(sessionDataBean.getUserId());
		query.setUpdationDate(new Date());

		if (query.getOperation().getOperandOne().getId() == null)
		{
			saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query.getOperation()
					.getOperandOne());
		}
		if (query.getOperation().getOperandTwo().getId() == null)
		{
			saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query.getOperation()
					.getOperandTwo());
		}
		// dao.insert(query, sessionDataBean, false, true);
		dao.insert(query, false);// cp

	}

	/**
	 * Updates domain object.
	 *
	 * @param dao
	 *            the dao object.
	 * @param obj
	 *            The object to be updated into the database.
	 * @param oldObj
	 *            old object that is to be updated.
	 * @param sessionDataBean
	 *            session specific data.
	 *@throws BizLogicException
	 *             BizLogicException.
	 */
	@Override
	protected void update(DAO dao, Object obj, Object oldObj, SessionDataBean sessionDataBean)
			throws BizLogicException
	{
		Workflow workflow = (Workflow) obj;
		workflow.setCreatedBy(sessionDataBean.getUserId());
		try
		{
			for (WorkflowItem workflowItem : workflow.getWorkflowItemList())
			{
				IAbstractQuery query = workflowItem.getQuery();
				if (query.getId() == null)
				{

					saveCompositeQuery(dao, sessionDataBean, (ICompositeQuery) query);

				}
			}
			dao.update(obj);
		}
		catch (DAOException e)
		{
			throw new BizLogicException(e);
		}

	}

	/**
	 * @param workflowDetails
	 *            workflowDetails object.
	 * @return Map of queryId and execution id.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public Map<Long, Long> runWorkflow(WorkflowDetails workflowDetails) throws BizLogicException
	{
		WorkflowManager wfm = new WorkflowManager();
		try
		{
			return wfm.execute(workflowDetails);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw getBizLogicException(e, "biz.logic.error", e.getMessage());
		}
	}

	/**
	 * This method returns the Map of queryId and execution id.
	 *
	 * @param workflowDetails
	 *            workflowDetails object.
	 * @param ciderQuery
	 *            ciderQuery object.
	 * @param qUIManager
	 *            AbstractQueryUIManager object.
	 * @param query
	 *            AbstractQuery.
	 * @return Map of queryId and execution id.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public Map<Long, Long> executeGetCountQuery(WorkflowDetails workflowDetails,
			AbstractQuery ciderQuery, AbstractQueryUIManager qUIManager,
			edu.wustl.common.querysuite.queryobject.impl.AbstractQuery query)
			throws BizLogicException
	{
		// map of query names and execution ids
		try
		{
			Map<Long, Long> executionIdsMap = null;
			if (query instanceof IParameterizedQuery)
			{
				Long queryExecId = WorkflowBizLogicHelper.getInstance()
						.executeParameterisedGetCountQuery(query, qUIManager);
				executionIdsMap = new HashMap<Long, Long>();
				executionIdsMap.put(query.getId(), queryExecId);
			}
			else if (query instanceof ICompositeQuery)
			{

				Long projectIdVal = (long) ((CiderWorkFlowDetails) workflowDetails).getProjectId();
				executionIdsMap = WorkflowBizLogicHelper.getInstance()
						.executeCompositeGetCountQuery(query, projectIdVal, workflowDetails,
								ciderQuery);
			}
			return executionIdsMap;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw getBizLogicException(e, "biz.exequery.error", e.getMessage());
		}
	}

	/**
	 * This method executes all the composite queries within the workflow.
	 *
	 * @param workflowDetails
	 *            workflowDetails object.
	 * @param preExecutedIdMap
	 *            previously executed queries and execution id Map.
	 * @return queries and execution id Map.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public Map<Long, Long> executeAllCompositeQueries(WorkflowDetails workflowDetails,
			Map<Long, Long> preExecutedIdMap) throws BizLogicException
	{

		WorkflowManager wfm = new WorkflowManager();
		try
		{
			// generate the
			Map<Long, Long> execIdMap = wfm.executeAllCompositeQueries(workflowDetails,
					preExecutedIdMap);
			return execIdMap;
		}
		catch (QueryModuleException ex)
		{
			throw getBizLogicException(ex, "biz.queryModuleException.error", ex.getMessage());

		}
		catch (MultipleRootsException ex)
		{

			throw getBizLogicException(ex, "biz.multiroot.error", ex.getMessage());
		}
		catch (SqlException ex)
		{

			throw getBizLogicException(ex, Constants.BIZ_SQL_EXCEPTION_ERROR, ex.getMessage());
		}
	}

	/**
	 * Returns the count object for the given <code>Query Execution Id</code>.
	 *
	 * @param queryExecId
	 *            query Execution Id.
	 * @param privilege
	 *            QueryPrivilege.
	 * @return Count Query Count object.
	 * @throws QueryModuleException
	 *             QueryModuleException.
	 */
	public Count getCount(Long queryExecId, QueryPrivilege privilege) throws QueryModuleException
	{
		return WorkflowBizLogicHelper.getInstance().getCount(queryExecId, privilege);
	}

	/**
	 * Overriding the parent class's method to validate the enumerated attribute
	 * values
	 *
	 * @param obj
	 *            Object to compare.
	 * @param dao
	 *            The base DAO Object.
	 * @param operation
	 *            The Operation String.
	 * @return true if the object is valid.
	 * @throws BizLogicException
	 *             Business Logic Exception if invalid.
	 * @see edu.wustl.common.bizlogic.DefaultBizLogic#validate(java.lang.Object,
	 *      edu.wustl.dao.DAO, java.lang.String)
	 */
	@Override
	protected boolean validate(Object obj, DAO dao, String operation) throws BizLogicException
	{
		if (obj == null)
		{
			throw getBizLogicException(null, Constants.BIZ_INVALID_OBJECT,
					"NULL object passed for validation");
		}
		Workflow workflow = (Workflow) obj;
		// validate empty workflow
		workflow.setName(workflow.getName().trim());
		if (workflow.getName() == null || workflow.getName().equals(""))
		{
			throw getBizLogicException(null, Constants.BIZ_INVALID_OBJECT,
					"Workflow Name cannot be empty");
		}

		HibernateDAO hibernateDAO = null;
		try
		{
			hibernateDAO = DAOUtil.getHibernateDAO(null);
			String wfName = WorkflowBizLogicHelper.getInstance()
					.getWorkflowName(workflow.getName());
			List list = hibernateDAO.executeQuery(new StringBuilder("select id from ").append(
					Workflow.class.getName()).append("  Workflow where upper(Workflow.name) = '")
					.append(wfName.toUpperCase()).append("'").toString());
			// edit
			if (workflow.getId() != null && !list.isEmpty())
			{
				if (!workflow.getId().equals(list.get(0)))
				{
					throw getBizLogicException(null, Constants.BIZ_INVALID_OBJECT,
							"Workflow with same name already exists");
				}
			}
			else if (!list.isEmpty())
			{
				throw getBizLogicException(null, Constants.BIZ_INVALID_OBJECT,
						"Workflow with same name already exists");
			}
		}
		catch (DAOException e)
		{
			throw new BizLogicException(e);
		}
		finally
		{
			closeHibernateDAO(hibernateDAO);
		}
		return true;
	}

	/**
	 * This method closes the hibernateDAO object.
	 *
	 * @param hibernateDAO
	 *            Hibernate DAO Object.
	 *
	 */
	private void closeHibernateDAO(HibernateDAO hibernateDAO)
	{
		try
		{
			DAOUtil.closeHibernateDAO(hibernateDAO);
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage());
		}
	}

	/**
	 * This method adds the given query to workflow. Workflow to which query is
	 * to be added is identified by workflow id.
	 *
	 * @param workflowId
	 *            workflow id.
	 * @param query
	 *            query to be added in workflow.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public void addWorkflowItem(Long workflowId, IQuery query) throws BizLogicException
	{
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		Workflow workflow = (Workflow) bizLogic.retrieve(Workflow.class.getName(), workflowId);
		if (WorkflowBizLogicHelper.getInstance().isQueryAlreadyExists(
				workflow.getWorkflowItemList(), query.getId()))
		{
			WorkflowItem workflowItem = new WorkflowItem();
			workflowItem.setQuery(query);
			List<WorkflowItem> workflowItemList = workflow.getWorkflowItemList();
			workflowItemList.add(workflowItem);
			workflow.setWorkflowItemList(workflowItemList);
			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			workflowBizLogic.update(workflow);
		}
	}

	/**
	 * This method returns the map of query execution id.
	 *
	 * @param workflowId
	 *            work-flow Id.
	 * @param userId
	 *            user Id.
	 * @return Execution Id List.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public List<Long> generateExecutionIdMap(Long workflowId, Long userId) throws BizLogicException
	{
		return generateExecutionIdMap(workflowId, userId, null);
	}

	/**
	 * This method returns the List of query execution id.
	 *
	 * @param workflowId
	 *            workflowId.
	 * @param userId
	 *            user Id.
	 * @param projectId
	 *            project Id.
	 * @return Execution Id List.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public List<Long> generateExecutionIdMap(Long workflowId, Long userId, Long projectId)
			throws BizLogicException
	{
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);

		Workflow workflow = (Workflow) bizLogic.retrieve(Workflow.class.getName(), workflowId);

		Map<Long, Long> execIdMap = generateQueryExecIdMap(userId, workflow.getId(), projectId);

		List<Long> queryExecIdsList = new ArrayList<Long>(workflow.getWorkflowItemList().size());

		// For each workflow item list fetch the latest execution count.
		List<WorkflowItem> workflowItemList = workflow.getWorkflowItemList();
		for (WorkflowItem workflowItem : workflowItemList)
		{
			// Get the query object
			Long queryId = workflowItem.getQuery().getId();
			Long queryExecId = execIdMap.get(queryId);
			if (queryExecId == null)
			{
				queryExecId = 0L;
			}
			queryExecIdsList.add(queryExecId);
		}
		return queryExecIdsList;

	}

	/**
	 * This method generates the Query Exeution Map of query id and execution
	 * id.
	 *
	 * @param userId
	 *            user Id.
	 * @param workflowId
	 *            workflowId
	 * @param projectId
	 *            project Id
	 * @return map of query id and execution id.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public Map<Long, Long> generateQueryExecIdMap(Long userId, Long workflowId, Long projectId)
			throws BizLogicException
	{
		try
		{
			return WorkflowBizLogicHelper.getInstance().generateQueryExecIdMap(userId, workflowId,
					projectId);
		}
		catch (DAOException ex)
		{
			BizLogicException bizLogicException = new BizLogicException(ex);
			throw bizLogicException;
		}
		catch (SQLException ex)
		{
			throw getBizLogicException(ex, Constants.BIZ_SQL_EXCEPTION_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method returns the latest execution Date.
	 *
	 * @param queryExecutionIdsList
	 *            execution id list.
	 * @return Map of queryId and latest execution date.
	 * @throws BizLogicException
	 *             BizLogicException.
	 */
	public Map<Long, Date> getDateForLatestExecution(Collection<Long> queryExecutionIdsList)
			throws BizLogicException
	{
		// Get the map of execution Ids from DB
		ITableManager itableManager = ITableManagerFactory.getDefaultITableManager();
		try
		{
			Map<Long, Date> execIdMap = itableManager
					.getDateForLatestExecution(queryExecutionIdsList);
			return execIdMap;
		}
		catch (DAOException ex)
		{
			BizLogicException bizLogicException = new BizLogicException(ex);
			throw bizLogicException;

		}
		catch (SQLException ex)
		{
			throw getBizLogicException(ex, Constants.BIZ_SQL_EXCEPTION_ERROR, ex.getMessage());
		}

	}

	/**
	 * This method returns the latest project that was accessed on the workflow
	 * page.
	 *
	 * @param workflowId
	 *            The Workflow Id.
	 * @param userId
	 *            The Current Logged In User Id.
	 * @return The Project Id that was last accessed.
	 * @throws BizLogicException
	 *             if there is an error while fetching the projectId.
	 */
	public Long getLatestProject(Long workflowId, Long userId) throws BizLogicException
	{
		// Get the map of execution Ids from DB
		ITableManager itableManager = ITableManagerFactory.getDefaultITableManager();

		try
		{
			return itableManager.getLatestProjectId(workflowId, userId);
		}
		catch (DAOException ex)
		{
			BizLogicException bizLogicException = new BizLogicException(ex);
			throw bizLogicException;

		}
		catch (SQLException ex)
		{
			throw getBizLogicException(ex, Constants.BIZ_SQL_EXCEPTION_ERROR, ex.getMessage());
		}
	}

	/**
	 * This method returns the number of workflows for the given user and
	 * optional partial matching workflow name.
	 *
	 * @param userId
	 *            user Id.
	 * @param workflowNameLike
	 *            query name String.
	 * @return total count of workflows.
	 */
	public int setWorkflowCount(Long userId, String workflowNameLike)
	{
		HibernateDAO hibernateDAO = null;
		int count = 0;
		try
		{
			hibernateDAO = DAOUtil.getHibernateDAO(null);
			String query = WorkflowBizLogicHelper.getInstance().getWorkflowByIdQuery(userId,
					workflowNameLike);
			List workflowList = hibernateDAO.executeQuery(query);
			if (workflowList != null)
			{
				count = workflowList.size();
			}
		}
		catch (DAOException e)
		{
			logger.info(e.getMessage(), e);
		}
		finally
		{
			closeHibernateDAO(hibernateDAO);
		}
		return count;
	}

	/**
	 * This method fetches the workflows based on the query filter conditions
	 * provided and returns the count of workflows fetched. The actual workflows
	 * are passed back into the parameter <code>workflowList</code>.
	 *
	 * @param startIndex
	 *            The start index.
	 * @param queryNameLike
	 *            query Name String to search.
	 * @param sessionDataBean
	 *            SessionDataBean.
	 * @param maxRecords
	 *            max Records.
	 * @param workflowList
	 *            workflowList.
	 * @return The count of workflows fetched.
	 * @throws DAOException
	 *             DAO Exception.
	 */
	public int retrieveWorkflowList(int startIndex, String queryNameLike,
			SessionDataBean sessionDataBean, int maxRecords, List<Workflow> workflowList)
			throws DAOException
	{
		HibernateDAO hibernateDAO = null;
		StringBuilder query = new StringBuilder();
		Long userId = sessionDataBean.getUserId();
		if (queryNameLike == null || "".equals(queryNameLike))
		{
			query.append("from ").append(Workflow.class.getName()).append(
					Constants.WORKFLOW_WHERE_CREATED_BY).append(userId).append(
					" order by Workflow.id desc");
		}
		else
		{
			StringBuffer escapeSquence = new StringBuffer("");
			String queryNameLikeUpper = queryNameLike.toUpperCase();
			queryNameLikeUpper = Utility.setSpecialCharacters(escapeSquence, queryNameLikeUpper);
			query.append("from ").append(Workflow.class.getName()).append(
					Constants.WORKFLOW_WHERE_CREATED_BY).append(userId).append(
					" and upper(Workflow.name) like ").append("'").append("%").append(
					queryNameLikeUpper).append("%").append("'").append(escapeSquence.toString())
					.append(" or upper(Workflow.description) like ").append("'").append("%")
					.append(queryNameLikeUpper).append("%").append("'").append(
							escapeSquence.toString()).append(" order by Workflow.id desc");
		}

		try
		{
			hibernateDAO = DAOUtil.getHibernateDAO(sessionDataBean);
			workflowList.addAll(hibernateDAO.executeQuery(query.toString(), startIndex, maxRecords,
					null));
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage());
			throw e;
		}
		finally
		{
			DAOUtil.closeHibernateDAO(hibernateDAO);
		}
		return setWorkflowCount(userId, queryNameLike);
	}

	/**
	 * Overridden method to check if there is provilege to view.
	 *
	 * @param objName
	 *            Object Name.
	 * @param identifier
	 *            Id.
	 * @param sessionDataBean
	 *            Session Data Bean.
	 * @return <code>true</code> if there is privilege else <code>false</code>.
	 *
	 * @see edu.wustl.common.bizlogic.AbstractBizLogic#hasPrivilegeToView(java.lang.String,
	 *      java.lang.Long, edu.wustl.common.beans.SessionDataBean)
	 */
	@Override
	public boolean hasPrivilegeToView(String objName, Long identifier,
			SessionDataBean sessionDataBean)
	{
		boolean hasPrivilege = false;
		try
		{
			if (identifier != null && sessionDataBean != null)
			{
				hasPrivilege = isMyWorkflow(identifier, sessionDataBean);
			}
		}
		catch (BizLogicException bizLogicException)
		{
			logger.debug(bizLogicException);
		}
		return hasPrivilege;
	}

	/**
	 * This method returns true if the given workflow belongs to the logged in user.
	 * @param identifier Long identifier
	 * @param sessionDataBean object of SessionDataBean
	 * @return boolean for is current user's workflow
	 * @throws BizLogicException Biz Logic Exception
	 */
	private boolean isMyWorkflow(Long identifier, SessionDataBean sessionDataBean)
			throws BizLogicException
	{
		boolean hasPrivilege = false;
		Object workflowObject = retrieve(Workflow.class.getName(), identifier);
		if (workflowObject != null)
		{
			Long createdBy = ((Workflow) workflowObject).getCreatedBy();
			if (createdBy != null && createdBy.equals(sessionDataBean.getUserId()))
			{
				hasPrivilege = true;
			}
		}
		return hasPrivilege;
	}

	/**
	 * Overridden method to check if the read denied access to be checked.
	 *
	 * @return <code>true</code> for now.
	 * @see edu.wustl.common.bizlogic.DefaultBizLogic#isReadDeniedTobeChecked()
	 */
	@Override
	public boolean isReadDeniedTobeChecked()
	{
		return true;
	}
}
