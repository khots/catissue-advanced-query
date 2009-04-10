package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.AbstractDAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.JDBCDAO;
import edu.wustl.common.dao.queryExecutor.PagenatedResultData;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * Bizlogic class to get recent queries from database.
 * @author rukhsana_sameer
 *
 */
public class RecentQueriesBizlogic extends DefaultBizLogic
{
	/**
	 * Executes the query & returns the results specified by the offset values i.e. startIndex & noOfRecords.
	 * @param query
	 * @param sessionDataBean =session related data
	 * @param isSecureExecute
	 * @param hasConditionOnIdentifiedField
	 * @param queryResultObjectDataMap
	 * @param startIndex
	 * @param noOfRecords
	 * @return
	 * @throws QueryModuleException
	 */
	public List<List<String>> executeQuery(String query, SessionDataBean sessionDataBean,
			boolean isSecureExecute, boolean hasConditionOnIdentifiedField,
			Map queryResultObjectDataMap, int startIndex, int noOfRecords)
			throws QueryModuleException
	{
		QueryModuleException queryModExp;
		List<List<String>> queryResultList = null;
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		try
		{
			dao.openSession(sessionDataBean);
			PagenatedResultData pagiPagenatedResultData = dao.executeQuery(
					query, sessionDataBean, false, false, null, startIndex, noOfRecords);
			queryResultList = pagiPagenatedResultData.getResult();

		}
		catch (ClassNotFoundException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.CLASS_NOT_FOUND);
			throw queryModExp;
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				queryModExp = new QueryModuleException(e.getMessage(),
						QueryModuleError.DAO_EXCEPTION);
				throw queryModExp;
			}
		}
		return queryResultList;
	}
	
	/**
	 * @param id=query id
	 * @param sessionDataBean session specific data
	 * @return
	 * @throws DAOException
	 */
	private String retrieveQueryName(Long id, SessionDataBean sessionDataBean) throws DAOException
	{

		String sourceObjectName = AbstractQuery.class.getName();
		AbstractDAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
		dao.openSession(sessionDataBean);

		String name = ((AbstractQuery) dao.retrieve(sourceObjectName, id)).getName();
		dao.closeSession();

		return name;
	}
	
	/**
	 * @param sessionDataBean =session related data
	 * @param queryResultList =List of queries for which
	 * RecentQueryBean list is to be populate
	 * @return= RecentQueriesBean List
	 * @throws DAOException
	 */
	public List<RecentQueriesBean> populateRecentQueryBean(SessionDataBean sessionDataBean,
			List<List<String>> queryResultList) throws DAOException
	{
		List<RecentQueriesBean> recentQueriesBeanList = new ArrayList<RecentQueriesBean>();

		for (List<String> parameterizedQuery : queryResultList)
		{
			RecentQueriesBean recentQueriesBean = new RecentQueriesBean();
			recentQueriesBean.setQueryCreationDate(parameterizedQuery.get(0));//(0));
			Long count = Long.valueOf(parameterizedQuery.get(1));
			recentQueriesBean.setResultCount(Long.valueOf(parameterizedQuery.get(1)));//(100L);
			recentQueriesBean.setStatus(parameterizedQuery.get(2));//2));

			String title = retrieveQueryName(Long.valueOf(parameterizedQuery.get(3)),
					sessionDataBean);
			recentQueriesBean.setQueryTitle(title);
			recentQueriesBean.setQueyExecutionId(Long.valueOf(parameterizedQuery.get(4)));//Long.valueOf(parameterizedQuery.get(4)));
			setPrivilege(parameterizedQuery, recentQueriesBean, count);
			recentQueriesBeanList.add(recentQueriesBean);
		}
		return recentQueriesBeanList;
	}

	protected void setPrivilege(List<String> parameterizedQuery, RecentQueriesBean recentQueriesBean,
			Long count)
	{
		
	}
	
	/**
	 * This method returns the  Query List
	 *
	 * @param sessionDataBean
	 * @param sql
	 * @param pageNum
	 * @param recordsPerPage
	 * @param lastIndex
	 * @return
	 * @throws QueryModuleException
	 */
	public List<List<String>> retrieveQueries(SessionDataBean sessionDataBean,int lastIndex)
	throws QueryModuleException
	{

		String sql ="SELECT CREATIONTIME,QUERY_COUNT,QUERY_STATUS,QUERY_ID,QUERY_EXECUTION_ID,PROJECT_ID,USER_ID "+
		   "FROM QUERY_EXECUTION_LOG qel, COUNT_QUERY_EXECUTION_LOG cqel "+
		   "WHERE qel.QUERY_EXECUTION_ID = cqel.COUNT_QUERY_EXECUTION_ID and cqel.USER_ID="+
		   sessionDataBean.getUserId() + " and qel.QUERY_TYPE='C' and qel.WORKFLOW_ID IS NULL order by qel.CREATIONTIME desc ";
		List<List<String>> queryResultList = executeQuery(sql, sessionDataBean, false, false, null,
				0, lastIndex);
		return queryResultList;
	}
}
