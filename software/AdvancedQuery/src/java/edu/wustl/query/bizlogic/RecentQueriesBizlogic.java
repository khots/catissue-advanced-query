
package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.QueryParams;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.queryexecutor.AbstractQueryExecutor;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.security.exception.SMException;

/**
 * Bizlogic class to get recent queries from database.
 * @author rukhsana_sameer
 *
 */
public class RecentQueriesBizlogic extends DefaultBizLogic
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(RecentQueriesBizlogic.class);

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
			int startIndex, int noOfRecords) throws QueryModuleException, SMException
	{
		QueryModuleException queryModExp;
		List<List<String>> queryResultList = null;
		JDBCDAO dao = null;

		try
		{
			dao = DAOUtil.getJDBCDAO(sessionDataBean);

			QueryParams queryParams = new QueryParams();
			queryParams.setQuery(query);
			queryParams.setSessionDataBean(sessionDataBean);
			queryParams.setSecureToExecute(false);
			queryParams.setHasConditionOnIdentifiedField(false);
			queryParams.setQueryResultObjectDataMap(null);
			queryParams.setStartIndex(startIndex);
			queryParams.setNoOfRecords(noOfRecords);
			AbstractQueryExecutor queryExecutor = Utility.getQueryExecutor();

			queryResultList = queryExecutor.getQueryResultList(queryParams);

		}

		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), e,
					QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		finally
		{
			try
			{
				DAOUtil.closeJDBCDAO(dao);
			}
			catch (DAOException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		return queryResultList;
	}

	/**
	 * @param queryNameLike 
	 * @param id=query id
	 * @param sessionDataBean session specific data
	 * @return
	 * @throws DAOException
	 */
	private String retrieveQueryName(Long queryId, String queryNameLike) throws DAOException
	{

		HibernateDAO hibernateDao = null;
		String name = null;
		try
		{
			hibernateDao = DAOUtil.getHibernateDAO(null);

			List queryNameList = new ArrayList();
			if (queryNameLike != null && !("").equals(queryNameLike))
			{
				StringBuffer escapeSquence=new StringBuffer("");
	            String queryNameLikeUpper = queryNameLike.toUpperCase();
	            queryNameLikeUpper =  Utility.setSpecialCharacters(escapeSquence,
						queryNameLikeUpper);
	            StringBuilder query = new StringBuilder("select query.name from  ")
                .append(IParameterizedQuery.class.getName());
	            
	            query.append( " query where  query.id=  ").append(queryId).append(
	                    " and upper(query.name) like ").append("'").append("%").append(
	                    queryNameLikeUpper).append("%").append("'").append(escapeSquence).append(
	                    " or upper(query.description) like ").append("'").append("%").append(
	                    queryNameLikeUpper).append("%").append("'").append(escapeSquence).append(" order by query.id desc");
	            
//				queryNameList = hibernateDao.executeQuery("select query.name from "
//						+ IParameterizedQuery.class.getName() + " query where  query.id=  "
//						+ queryId + "and upper(query.name) like " + "'%"
//						+ queryNameLike.toUpperCase() + "%'" + "or upper(query.description) like "
//						+ "'%" + queryNameLike.toUpperCase() + "%'" + " order by query.id desc");
				
	        	queryNameList = hibernateDao.executeQuery(query.toString());
	            
			}
			else
			{
				queryNameList = hibernateDao.executeQuery("select query.name from "
						+ IParameterizedQuery.class.getName() + " query where  query.id=  "
						+ queryId);
			}
			if (!queryNameList.isEmpty())
			{
				name = (String) queryNameList.get(0);
			}

		}
		finally
		{
				DAOUtil.closeHibernateDAO(hibernateDao);
		}
		return name;
	}

	/**
	 * @param sessionDataBean =session related data
	 * @param queryResultList =List of queries for which
	 * RecentQueryBean list is to be populate
	 * @return= RecentQueriesBean List
	 * @param queryNameLike 
	 * @throws DAOException
	 */
	public List<RecentQueriesBean> populateRecentQueryBean(SessionDataBean sessionDataBean,
			List<List<String>> queryResultList, String queryNameLike) throws DAOException
	{
		List<RecentQueriesBean> recQueryBeanList = new ArrayList<RecentQueriesBean>();

		//		for (List<String> parameterizedQuery : queryResultList)
		//		{
		for (int i = 0; i < queryResultList.size(); i++)
		{
			List<String> query = queryResultList.get(i);
			Long queryId = Long.valueOf(query.get(3));
			String title = retrieveQueryName(queryId, queryNameLike);
			if (title != null && !title.equals(""))
			{
				RecentQueriesBean recentQueriesBean = new RecentQueriesBean();
				recentQueriesBean.setQueryCreationDate(query.get(0));//(0));
				Long count = Long.valueOf(query.get(1));
				recentQueriesBean.setResultCount(Long.valueOf(query.get(1)));//(100L);
				recentQueriesBean.setStatus(query.get(2));//2));

				recentQueriesBean.setQueryTitle(title);
				recentQueriesBean.setQueyExecutionId(Long.valueOf(query.get(4)));//Long.valueOf(query.get(4)));
				setPrivilege(query, recentQueriesBean, count);
				recentQueriesBean.setQueryId(queryId);
				recQueryBeanList.add(recentQueriesBean);
			}
		}
		//}
		return recQueryBeanList;
	}

	protected void setPrivilege(List<String> parameterizedQuery,
			RecentQueriesBean recentQueriesBean, Long count)
	{
		// TODO Auto-generated method stub
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
	public List<List<String>> retrieveQueries(SessionDataBean sessionDataBean, int lastIndex)
			throws QueryModuleException, SMException
	{

		String sql = "SELECT CREATION_TIME,QUERY_COUNT,QUERY_STATUS,QUERY_ID,QUERY_EXECUTION_ID,PROJECT_ID,USER_ID "
				+ "FROM QUERY_EXECUTION_LOG qel, COUNT_QUERY_EXECUTION_LOG cqel "
				+ "WHERE qel.QUERY_EXECUTION_ID = cqel.COUNT_QUERY_EXECUTION_ID and qel.USER_ID="
				+ sessionDataBean.getUserId()
				+ " and qel.QUERY_TYPE='C' and qel.WORKFLOW_ID IS NULL order by qel.CREATION_TIME desc ";
		return executeQuery(sql, sessionDataBean, -1, lastIndex);
	}
}
