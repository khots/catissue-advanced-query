
package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.query.generator.ColumnValueBean;
import edu.wustl.dao.util.DAOUtility;
import edu.wustl.query.beans.DashBoardBean;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.UserCache;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.CsmUtility;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.manager.ISecurityManager;
import edu.wustl.security.manager.SecurityManagerFactory;
import edu.wustl.security.privilege.PrivilegeUtility;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.authorization.domainobjects.Role;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

/**
 * This class contains all the methods required for Query dash-board.
 * @author deepti_shelar
 *
 */
public class DashboardBizLogic extends DefaultQueryBizLogic
{

	private static final org.apache.log4j.Logger LOGGER = LoggerConfig
			.getConfiguredLogger(DashboardBizLogic.class);

	/**
	 * This method gives the last 'executed on' , rootEntityName, and Count of root records
	 * for each saved query to be shown on dash-board.
	 * @param queryCollection saved query collection
	 * @param userId user id
	 * @return map of query id and its related data
	 * @throws BizLogicException BizLogicException
	 * @throws ClassNotFoundException 
	 * @throws DAOException 
	 */
	public Map<Long, DashBoardBean> getDashBoardDetails(
			Collection<IParameterizedQuery> queryCollection, String userId)
			throws BizLogicException, DAOException, ClassNotFoundException
	{
		Map<Long, DashBoardBean> dashBoardDataMap = new HashMap<Long, DashBoardBean>();
		Map<Long, String> queryUserIdMap = getAllQueryIds(queryCollection);
		for (IParameterizedQuery parameterizedQuery : queryCollection)
		{
			LinkedList<ColumnValueBean> columnValueBean = getColumnValueBean(userId,
					parameterizedQuery);
			String sql = dashBoardDetailsQuery();
			try
			{
				getDataList(dashBoardDataMap, parameterizedQuery, columnValueBean, sql,
						queryUserIdMap);
			}
			catch (DAOException e)
			{
				throw new BizLogicException(null, e, "Error while getting audit details"
						+ " for query dashboard");
			}
			catch (ClassNotFoundException e)
			{
				throw new BizLogicException(null, e, "Error while getting audit details"
						+ " for query dashboard");
			}
			catch (SMException e)
			{
				throw new BizLogicException(null, e, "Error while getting audit details"
						+ " for query dashboard");
			}
		}
		return dashBoardDataMap;
	}

	private Map<Long, String> getAllQueryIds(Collection<IParameterizedQuery> queryCollection)
			throws DAOException, ClassNotFoundException
	{
		Map<Long, String> data = new HashMap<Long, String>();
		if (!queryCollection.isEmpty())
		{
			List<Long> queryIds = new ArrayList<Long>();
			for (IParameterizedQuery parameterizedQuery : queryCollection)
			{
				queryIds.add(parameterizedQuery.getId());
			}
			String queryId = queryIds.toString().replace("[", "(");
			queryId = queryId.replace("]", ")");

			String sql = "select qpq.identifier, cu.last_name ||', '|| cu.first_name from query_parameterized_query qpq,"
					+ " catissue_user cu where cu.csm_user_id in (select pq.owner from query_parameterized_query pq where pq.identifier in "
					+ queryId + " and pq.owner is not null) and cu.csm_user_id = qpq.owner";

			List<List<String>> dataList = Utility
					.executeSQL(sql, new LinkedList<ColumnValueBean>());

			for (List<String> result : dataList)
			{
				Long pQueryId = Long.valueOf(result.get(0));
				String userId = result.get(1);
				data.put(pQueryId, userId);
			}
		}
		return data;
	}

	/**
	 * @return query
	 */
	private String dashBoardDetailsQuery()
	{
		String sql = "select distinct mainAuditTable.identifier, mainAuditTable.EVENT_TIMESTAMP,"
				+ " queryAudit.TEMP_TABLE_NAME from"
				+ " catissue_audit_event mainAuditTable, catissue_audit_event_query_log"
				+ " queryAudit"
				+ " where queryAudit.AUDIT_EVENT_ID = mainAuditTable.IDENTIFIER and"
				+ " mainAuditTable.user_id=?" + " and queryAudit.query_id=?"
				+ " order by mainAuditTable.EVENT_TIMESTAMP desc";
		return sql;
	}

	/**
	 * @param dashBoardDataMap Map
	 * @param parameterizedQuery query
	 * @param columnValueBean bean
	 * @param sql SQL
	 * @return dataList
	 * @throws DAOException DAOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws SMExceptionSecurity Manager Exception
	 * @throws BizLogicException BizLogicException
	 */
	private void getDataList(Map<Long, DashBoardBean> dashBoardDataMap,
			IParameterizedQuery parameterizedQuery, LinkedList<ColumnValueBean> columnValueBean,
			String sql, Map<Long, String> queryUserIdMap) throws DAOException,
			ClassNotFoundException, SMException, BizLogicException
	{
		List<List<String>> dataList;
		dataList = Utility.executeSQL(sql, columnValueBean);
		if (dataList.isEmpty())
		{
			populateBeanWithNA(parameterizedQuery, dashBoardDataMap, queryUserIdMap);
		}
		else
		{
			populateDashBoardData(dashBoardDataMap, parameterizedQuery, dataList, queryUserIdMap);
		}
	}

	/**
	 * @param userId user identifier
	 * @param parameterizedQuery query
	 * @return bean
	 */
	private LinkedList<ColumnValueBean> getColumnValueBean(String userId,
			IParameterizedQuery parameterizedQuery)
	{
		LinkedList<Object> data;
		data = new LinkedList<Object>();
		Long queryId = parameterizedQuery.getId();
		data.add(userId);
		data.add(queryId);
		return populateColumnValueBean(data);
	}

	/**
	 * Populates bean for not applicable values wherever necessary.
	 * @param parameterizedQuery query
	 * @param dashBoardDataMap map of dash board details
	 * @throws SMException exception
	 * @throws BizLogicException exception
	 */
	private void populateBeanWithNA(IParameterizedQuery parameterizedQuery,
			Map<Long, DashBoardBean> dashBoardDataMap, Map<Long, String> queryUserIdMap)
			throws SMException, BizLogicException
	{

		DashBoardBean bean = new DashBoardBean();
		String naStr = "N/A";
		bean.setCountOfRootRecords(naStr);
		bean.setExecutedOn(naStr);
		bean.setRootEntityName(naStr);
		String ownerName = queryUserIdMap.get(parameterizedQuery.getId());
		//		String ownerName = getQueryOwnerByQueryId(parameterizedQuery.getId());
		if (ownerName == null || ownerName.equalsIgnoreCase(""))
		{
			User user = getQueryOwner(parameterizedQuery.getId().toString());
			ownerName = user.getLastName() + ", " + user.getFirstName();
			if (user == null)
			{
				bean.setOwnerName(naStr);
			}
			else
			{
				ownerName = user.getLastName() + ", " + user.getFirstName();
			}
		}
		bean.setOwnerName(ownerName);
		dashBoardDataMap.put(parameterizedQuery.getId(), bean);
	}

	/**
	 * Populates the dash board map with data, ExecutedOndate, CountOfRootRecords and RootEntityName.
	 * @param dashBoardMap key->QueryId, value->dashBoardBean
	 * @param pQuery Query
	 * @param dataList Data List of event time stamp and audit event id
	 * @throws BizLogicException BizLogicException
	 */
	private void populateDashBoardData(Map<Long, DashBoardBean> dashBoardMap,
			IParameterizedQuery pQuery, List<List<String>> dataList,
			Map<Long, String> queryUserIdMap) throws BizLogicException
	{
		DashBoardBean bean = new DashBoardBean();
		List<String> row = dataList.get(0);
		String auditEventId = row.get(0);
		String tempTableName = row.get(AQConstants.TWO);
		String rootEntityName = "N/A";
		String cntOfRootRecs = "N/A";
		LinkedList<Object> data = new LinkedList<Object>();
		data.add(auditEventId);
		LinkedList<ColumnValueBean> columnValueBean = populateColumnValueBean(data);
		String sql = "select ROOT_ENTITY_NAME,COUNT_OF_ROOT_RECORDS from "
				+ "catissue_audit_event_query_log where AUDIT_EVENT_ID =?";
		try
		{
			List<List<String>> rootDatalist = Utility.executeSQL(sql, columnValueBean);
			if (!rootDatalist.isEmpty())
			{
				List<String> record = (List<String>) rootDatalist.get(0);
				rootEntityName = setRootEntityName(rootEntityName, record);
				cntOfRootRecs = setRootRecordCount(cntOfRootRecs, record);
			}
		}
		catch (DAOException e)
		{
			throw new BizLogicException(null, e, "error occurred while executing sql "
					+ "for fetching data for query dashboard");
		}
		catch (ClassNotFoundException e)
		{
			throw new BizLogicException(null, e, "error occurred while executing sql "
					+ "for fetching data for query dashboard");
		}
		String executedOnTime = setExecutedOnTime(row, tempTableName);
		bean.setExecutedOn(executedOnTime);
		bean.setRootEntityName(rootEntityName);
		bean.setQuery(pQuery);
		bean.setCountOfRootRecords(cntOfRootRecs);

		String ownerName = queryUserIdMap.get(pQuery.getId());
		//		String ownerName = getQueryOwnerByQueryId(pQuery.getId());
		if (ownerName == null || ownerName.equalsIgnoreCase(""))
		{
			User user = getQueryOwner(pQuery.getId().toString());
			ownerName = user.getLastName() + ", " + user.getFirstName();
		}
		bean.setOwnerName(ownerName);
		dashBoardMap.put(pQuery.getId(), bean);
	}

	/**
	 * @param cntOfRootRecs count
	 * @param record record
	 * @return count
	 */
	private String setRootRecordCount(String cntOfRootRecs, List<String> record)
	{
		String count = cntOfRootRecs;
		if (record.get(1).length() != 0)
		{
			count = record.get(1);
		}
		return count;
	}

	/**
	 * @param rootEntityName root Entity name
	 * @param record record
	 * @return rootEntity
	 */
	private String setRootEntityName(String rootEntityName, List<String> record)
	{
		String rootEntity = rootEntityName;
		if (record.get(0).length() != 0)
		{
			rootEntity = setRootEntityName(record);
		}
		return rootEntity;
	}

	/**
	 * Set executed on time.
	 * @param row row
	 * @param tempTableName tempTableName
	 * @return executedOnTime
	 */
	private String setExecutedOnTime(List<String> row, String tempTableName)
	{
		String executedOnTime = row.get(1);
		if (tempTableName != null && tempTableName.equals(""))
		{
			executedOnTime = "(N/A)";
		}
		else
		{
			executedOnTime = getFormattedDate(executedOnTime);
		}
		return executedOnTime;
	}

	/**
	 * Sets the root entity name.
	 * @param record record
	 * @return rootEntityName
	 */
	private String setRootEntityName(List<String> record)
	{
		String rootEntityName;
		rootEntityName = record.get(0);
		rootEntityName = rootEntityName.substring(rootEntityName.lastIndexOf('.') + 1);
		rootEntityName = edu.wustl.common.util.Utility.getDisplayLabel(rootEntityName);
		return rootEntityName;
	}

	/**
	 * Returns the owner of the query.
	 * @param queryId id
	 * @return User object
	 * @throws BizLogicException exception
	 */
	public User getQueryOwner(String queryId) throws BizLogicException
	{
		Set<ProtectionGroup> pgSet = getPGsforQuery(queryId);
		return getOwnerOfTheQuery(pgSet);
	}

	/**
	 * Returns PGs for given query.
	 * @param queryId id of the query
	 * @return set of PGs
	 * @throws BizLogicException exception
	 */
	public Set<ProtectionGroup> getPGsforQuery(String queryId) throws BizLogicException
	{
		Set<ProtectionGroup> pgSet = null;
		try
		{
			List<ProtectionElement> queryPEs = CsmUtility.getQueryPEs(queryId);
			if (queryPEs != null && !queryPEs.isEmpty())
			{
				ProtectionElement pElement = queryPEs.get(0);
				PrivilegeUtility privUtil = new PrivilegeUtility();
				pgSet = privUtil.getUserProvisioningManager().getProtectionGroups(
						pElement.getProtectionElementId().toString());
			}
		}
		catch (SMException e)
		{
			throw new BizLogicException(null, e,
					"Error while getting owner for query to be shown on dashboard");
		}
		catch (CSObjectNotFoundException e)
		{
			throw new BizLogicException(null, e,
					"Error while getting owner for query to be shown on dashboard");
		}
		return pgSet;
	}

	/**
	 * Returns the owner of the query.
	 * @param pgSet set of protection groups
	 * @return User
	 * @throws BizLogicException exception
	 */
	private User getOwnerOfTheQuery(Set<ProtectionGroup> pgSet) throws BizLogicException
	{
		String ownerId = getOwnerPG(pgSet);
		if (ownerId == null)
		{
			ownerId = "1";
		}
		return UserCache.getUser(ownerId);
	}

	/**
	 * returns the user id from protection group list.
	 * @param pgSet set of PGs
	 * @return String user id
	 */
	private String getOwnerPG(Set<ProtectionGroup> pgSet)
	{
		String userId = null;
		if (pgSet != null)
		{
			for (ProtectionGroup protectionGroup : pgSet)
			{
				String pgName = protectionGroup.getProtectionGroupName();

				if (!pgName.equals(AQConstants.PUBLIC_QUERY_PROTECTION_GROUP))
				{
					userId = pgName.substring(pgName.indexOf('_') + 1);
					break;
				}
			}
		}
		return userId;
	}

	/**
	 * Returns all queries.
	 * @return list of all queries
	 * @throws DAOException DAOException
	 */
	public Collection<IParameterizedQuery> getAllQueries() throws DAOException
	{
		HibernateDAO hibernateDAO = null;

		hibernateDAO = DAOUtil.getHibernateDAO(null);
		return hibernateDAO.executeQuery("from " + IParameterizedQuery.class.getName());
	}

	/**
	 * Gets all queries from the DB.
	 * @return collection of queries
	 * @throws DAOException exception
	 */
	public Collection<IParameterizedQuery> getAllQueriesForUpgrade() throws DAOException
	{
		Collection<IParameterizedQuery> allQueries = DAOUtility.getInstance().executeHQL(
				DAOUtility.GET_PARAM_QUERIES_DETAILS);
		return allQueries;
	}

	/**
	 * Returns true if the passes user is super-administrator.
	 * @param csmUserId user id
	 * @return true/false
	 * @throws BizLogicException exception
	 */
	private boolean ifSuperAdminUser(String csmUserId) throws BizLogicException
	{
		boolean isSuperAdmin = false;
		try
		{
			ISecurityManager securityManager = SecurityManagerFactory.getSecurityManager();
			Role role = securityManager.getUserRole(Long.valueOf(csmUserId));
			if (role.getName().equalsIgnoreCase(edu.wustl.security.global.Constants.ROLE_ADMIN))
			{
				isSuperAdmin = true;
			}
		}
		catch (SMException e)
		{
			throw new BizLogicException(null, e, "Sm exception: while getting role for an user");
		}
		return isSuperAdmin;
	}

	/**
	 * Returns date in format specified.
	 * @param executedOnTime string data
	 * @return formatted string date
	 */
	public static String getFormattedDate(String executedOnTime)
	{
		String tempExecutedOnTime = executedOnTime;
		tempExecutedOnTime = executedOnTime.replace('-', '/');
		StringTokenizer tokenizer = new StringTokenizer(tempExecutedOnTime, " ");
		String time = "";
		if (tokenizer.hasMoreTokens())
		{
			time = getAppropriateTime(tokenizer);
		}
		tempExecutedOnTime = tempExecutedOnTime.substring(0, tempExecutedOnTime.indexOf(' '));
		StringBuffer tempString = new StringBuffer(tempExecutedOnTime);
		tempString.append(' ').append(time);
		return tempString.toString();
	}

	/**
	 * @param tokenizer StringTokenizer
	 * @return time
	 */
	private static String getAppropriateTime(StringTokenizer tokenizer)
	{
		String time;
		String hours;
		String minutes;
		tokenizer.nextToken();
		time = tokenizer.nextToken();
		time = time.substring(0, time.lastIndexOf(':'));
		hours = time.substring(0, time.lastIndexOf(':'));
		minutes = time.substring(time.lastIndexOf(':') + 1, time.length());
		if (Integer.parseInt(hours) > AQConstants.TWELVE)
		{
			time = getPMTime(hours, minutes);
		}
		else if (Integer.parseInt(hours) == 0)
		{
			time = "12:" + minutes + " " + AQConstants.AM_CONSTANT;
		}
		else if (Integer.parseInt(hours) == AQConstants.TWELVE)
		{
			StringBuffer tempTime = new StringBuffer(time);
			tempTime.append(' ').append(AQConstants.PM_CONSTANT);
			time = tempTime.toString();
		}
		else
		{
			StringBuffer tempTime = new StringBuffer(time);
			tempTime.append(' ').append(AQConstants.AM_CONSTANT);
			time = tempTime.toString();
		}
		return time;
	}

	/**
	 * @param hours hours
	 * @param minutes minutes
	 * @return time
	 */
	private static String getPMTime(String hours, String minutes)
	{
		StringBuffer time = new StringBuffer();
		int hrs = Integer.parseInt(hours) - AQConstants.TWELVE;
		if (hrs < AQConstants.TEN)
		{
			time.append('0').append(hrs).append(':').append(minutes).append(' ').append(
					AQConstants.PM_CONSTANT);
		}
		else
		{
			time.append(hrs).append(':').append(minutes).append(' ')
					.append(AQConstants.PM_CONSTANT);
		}
		return time.toString();
	}

	/**
	 * Return query object by id.
	 * @param queryId id
	 * @return query object
	 * @throws DAOException exception
	 */
	public IParameterizedQuery getQueryById(Long queryId) throws DAOException
	{
		//		CsmUtility util = new CsmUtility();
		IParameterizedQuery query = null;
		List<Long> queryIds = new ArrayList<Long>();
		queryIds.add(queryId);
		Collection<IParameterizedQuery> queries = CsmUtility.retrieveQueries(queryIds, "");
		for (IParameterizedQuery parameterizedQuery : queries)
		{
			query = parameterizedQuery;
		}
		return query;
	}

	/**
	 * @param data data
	 * @return columnValueBean
	 */
	private static LinkedList<ColumnValueBean> populateColumnValueBean(LinkedList<Object> data)
	{
		LinkedList<ColumnValueBean> columnValueBean = new LinkedList<ColumnValueBean>();
		ColumnValueBean bean = null;
		for (Object object : data)
		{
			bean = new ColumnValueBean(object.toString(), object);
			columnValueBean.add(bean);
		}
		return columnValueBean;
	}

	/**
	 * Will returns all queries(shared/created) of the user with given CSM user ID.
	 * @param csmUserId CSM user id of the user
	 * @param userName user name
	 * @return all queries(shared/created) of the user with given CSM user ID.
	 * @throws BizLogicException instance of BizLogicException
	 * @throws ClassNotFoundException 
	 */
	public Collection<IParameterizedQuery> getAllQueries(String csmUserId, String userName)
			throws BizLogicException, ClassNotFoundException
	{
		Collection<IParameterizedQuery> queries = null;
		try
		{
			if (ifSuperAdminUser(csmUserId))
			{
				queries = getAllQueriesForUpgrade();
			}
			else
			{
				queries = getSharedQueries(csmUserId, userName);
				Collection<IParameterizedQuery> myQueries = getMyQueries(csmUserId, userName);
				if (myQueries != null)
				{
					queries.addAll(myQueries);
				}
			}
		}
		catch (DAOException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e,
					"DAOException: while Retrieving queries for SuperAdmin");
		}
		return queries;
	}

	/**
	 * Will Returns all the shared queries on which the user with given CSM user ID has access.
	 * @param csmUserId CSM user Id
	 * @param userName user name
	 * @return all the shared queries on which the user with given CSM user ID has access.
	 * @throws BizLogicException instance of BizLogicException
	 */
	public Collection<IParameterizedQuery> getSharedQueries(String csmUserId, String userName)
			throws BizLogicException
	{
		Collection<IParameterizedQuery> queries = null;
		String queryNameLike = "";
		try
		{
			Collection<Long> publicQueryIdList = CsmUtility
					.getQueriesIdList(AQConstants.PUBLIC_QUERY_PROTECTION_GROUP);
			Collection<Long> sharedQueryIdList = CsmUtility.getSharedQueryIdList(userName);
			Set<Long> queriesIdSet = new HashSet<Long>();
			queriesIdSet.addAll(publicQueryIdList);
			queriesIdSet.addAll(sharedQueryIdList);
			queries = CsmUtility.retrieveQueries(queriesIdSet, queryNameLike);
		}
		catch (CSObjectNotFoundException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e, e.getMessage());
		}
		catch (SMException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e, e.getMessage());
		}
		catch (DAOException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e, e.getMessage());
		}
		return queries;
	}

	/**
	 * This will return all queries created by the user with given CSM user Id.
	 * @param csmUserId CSM user id
	 * @return all queries created by the user with given CSM user Id
	 * @throws BizLogicException instance of BizLogicException
	 * @throws ClassNotFoundException 
	 */
	public Collection<IParameterizedQuery> getMyQueries(String csmUserId, String userName)
			throws BizLogicException, ClassNotFoundException
	{
		Collection<IParameterizedQuery> queries = null;
		String userPG = CsmUtility.getUserProtectionGroup(csmUserId);
		String queryNameLike = "";
		try
		{
			Collection<Long> myQueriesIdList = CsmUtility.getQueriesIdList(userPG);
			Collection<Long> queryIds = getQueryIdsFromUserId(csmUserId);
			myQueriesIdList.addAll(queryIds);
			queries = CsmUtility.retrieveQueries(myQueriesIdList, queryNameLike);
		}
		catch (SMException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e, e.getMessage());
		}
		catch (CSObjectNotFoundException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e, e.getMessage());
		}
		catch (DAOException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new BizLogicException(null, e, e.getMessage());
		}
		return queries;
	}

	private Collection<Long> getQueryIdsFromUserId(String userId) throws DAOException,
			ClassNotFoundException
	{
		Collection<Long> queryIds = new ArrayList<Long>();
		String sql = "select identifier from query_parameterized_query where owner = ?";
		LinkedList<Object> data = new LinkedList<Object>();
		data.add(userId);
		LinkedList<ColumnValueBean> columnValueBean = populateColumnValueBean(data);
		final List<List<String>> resultList = Utility.executeSQL(sql, columnValueBean);
		//		JDBCDAO jdbcDAO = DAOUtil.getJDBCDAO(null);
		//		List<ColumnValueBean> values = new ArrayList<ColumnValueBean>();
		//		values.add(new ColumnValueBean(userId, 0));
		//		final List<List<String>> resultList = (List<List<String>>) jdbcDAO
		//				.executeQuery(sql, values);
		for (List<String> record : resultList)
		{
			queryIds.add(Long.valueOf(record.get(0)));
		}

		//		jdbcDAO.closeSession();
		return queryIds;
	}

	public void setQueryOwner(Long userId, ParameterizedQuery query) throws DAOException,
			ClassNotFoundException
	{
		System.out.println("----------------------Inside setQueryOwner Start--------------------");
		String sql = "update query_parameterized_query set owner = ? where IDENTIFIER = ?";
		LinkedList<Object> data = new LinkedList<Object>();
		data.add(userId);
		data.add(query.getId());
		LinkedList<ColumnValueBean> columnValueBean = populateColumnValueBean(data);
		//				Utility.executeSQL(sql, columnValueBean);

		//				JDBCDAO jdbcDAO = DAOUtil.getJDBCDAO(null);
		//		jdbcDAO.openSession(null);
		//		List<ColumnValueBean> values = new ArrayList<ColumnValueBean>();
		//		values.add(new ColumnValueBean(userId, 0));
		//		values.add(new ColumnValueBean(query.getId(), 1));
		//		jdbcDAO.executeUpdate(sql, values);
		//		jdbcDAO.closeSession();

		String appName = CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daofactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDao = daofactory.getJDBCDAO();

		jdbcDao.openSession(null);

		jdbcDao.executeUpdate(sql, columnValueBean);

		jdbcDao.closeSession();

		System.out.println("----------------------Inside setQueryOwner End-------**-------------");
	}

}
