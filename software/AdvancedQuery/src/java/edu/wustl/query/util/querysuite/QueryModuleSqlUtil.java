
package edu.wustl.query.util.querysuite;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.util.QueryParams;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.query.generator.ColumnValueBean;
import edu.wustl.query.executor.AbstractQueryExecutor;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.security.exception.SMException;

/**
 * Util class for sql related operations required for Query.
 * @author santhoshkumar_c
 *
 */
final public class QueryModuleSqlUtil
{
	private QueryModuleSqlUtil()
	{
	}

	/**
	 * Executes the query and returns the results.
	 * @param selectSql sql to be executed
	 * @param sessionData sessiondata
	 * @param querySessionData
	 * @return list of results
	 * @throws ClassNotFoundException
	 * @throws DAOException
	 * @throws SMException
	 */
    public static List<List<String>> executeQuery(final SessionDataBean sessionData,
            final QuerySessionData querySessionData) throws ClassNotFoundException, DAOException, SMException
            {
    	List<List<String>> dataList = new ArrayList<List<String>>();
    	QueryParams queryParams = new QueryParams();
    	queryParams.setQuery(querySessionData.getSql());
    	queryParams.setSessionDataBean(sessionData);
    	queryParams.setStartIndex(-1);
    	queryParams.setSecureToExecute(querySessionData.isSecureExecute());
    	queryParams.setHasConditionOnIdentifiedField(querySessionData.isHasConditionOnIdentifiedField());
    	queryParams.setQueryResultObjectDataMap(querySessionData.getQueryResultObjectDataMap());
    	queryParams.setNoOfRecords(querySessionData.getRecordsPerPage());

    	AbstractQueryExecutor queryExecutor = Utility.getQueryExecutor();
    	dataList = queryExecutor.getQueryResultList(queryParams).getResult();
    	return dataList;
            }

	/**
		 * Creates a new table in database. First the table is deleted if exist already.
		 * @param tableName name of the table to be deleted before creating new one.
		 * @param createTableSql sql to create table
		 * @param sessionData session data.
		 * @throws DAOException DAOException
		 */
	public static void executeCreateTable(final String tableName, final String createTableSql,
			QueryDetails queryDetailsObj) throws DAOException
	{
	    String appName=CommonServiceLocator.getInstance().getAppName();
	    IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
	    JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		try
		{
			jdbcDao.openSession(queryDetailsObj.getSessionData());
			jdbcDao.deleteTable(tableName);
			/*QueryModuleSqlUtil.updateAuditQueryDetails(edu.wustl.query.util.global.Constants.IF_TEMP_TABLE_DELETED,
					"true",queryDetailsObj.getAuditEventId());
			*/
			String newSql = modifySqlForCreateTable(createTableSql);
			String newCreateTableSql = AQConstants.CREATE_TABLE + tableName + " " + AQConstants.AS_CONSTANT + " "
            + newSql;
			jdbcDao.executeUpdate(newCreateTableSql);
			String deleteQuery = "DELETE FROM "+tableName;
			jdbcDao.executeUpdate(deleteQuery);
			jdbcDao.commit();
			String insertSql = "INSERT INTO "+tableName + " "+createTableSql;
			if(insertSql.contains("?"))
			{
				LinkedList<LinkedList<ColumnValueBean>> beanList = new LinkedList<LinkedList<ColumnValueBean>>();
				beanList.add(queryDetailsObj.getColumnValueBean());
				jdbcDao.executeUpdate(insertSql, beanList);
			}
			else
			{
				jdbcDao.executeUpdate(insertSql);
			}
			jdbcDao.commit();
		}
		catch (DAOException e)
		{
			Logger.out.error(e);
			throw e;
		}
		finally
		{
			jdbcDao.closeSession();
		}
	}

	/**
	 * @param createTableSql query
	 * @return newSql
	 */
	private static String modifySqlForCreateTable(String createTableSql)
	{
		String whereClause="";
		if(createTableSql.indexOf("where") != -1)
		{
			whereClause = "where";
		}
		else
		{
			whereClause = "WHERE";
		}
		String newSql = createTableSql.substring(0, createTableSql.lastIndexOf(whereClause)-1);
		return newSql;
	}

	/**
	 * @param tableName
	 * @param columnNameIndexMap
	 * @return
	 */
	public static String getSQLForRootNode(final String tableName,
			Map<String, String> columnNameIndexMap)
	{
		String columnNames = columnNameIndexMap.get(AQConstants.COLUMN_NAMES);
		String indexStr = columnNameIndexMap.get(AQConstants.INDEX);
		int index = -1;
		if (indexStr != null && !AQConstants.NULL.equals(indexStr))
		{
			index = Integer.valueOf(indexStr);
		}
		String idColumnName = columnNames;
		if (columnNames.indexOf(',') != -1)
		{
			idColumnName = columnNames.substring(0, columnNames.indexOf(','));
		}
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select distinct ").append(columnNames).append(" from ").append(tableName)
				.append(" where ").append(idColumnName).append(" is not null");
		selectSql = selectSql.append(AQConstants.NODE_SEPARATOR).append(index);
		return selectSql.toString();
	}

	/**
	 * Method to get count for the given SQL query
	 * @param sql original SQL for which count is required
	 * @param queryDetailsObj object of QueryDetails
	 * @return count int value of count
	 * @throws DAOException
	 * @throws ClassNotFoundException
	 * @throws SMException
	 */
	public static int getCountForQuery(final String sql, QueryDetails queryDetailsObj) throws DAOException,
	ClassNotFoundException, SMException
	{
		int count = 0;
		List<List<String>> dataList = null;
		String appName=CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
	    JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		try
		{
			String countSql = "Select count(*) from (" + sql + ") alias";

			QueryParams queryParams = new QueryParams();
            SessionDataBean sessionData = queryDetailsObj.getSessionData();
            queryParams.setQuery(countSql);
            queryParams.setSessionDataBean(sessionData);
            queryParams.setStartIndex(-1);
            queryParams.setSecureToExecute(true);
            queryParams.setHasConditionOnIdentifiedField(false);
            queryParams.setQueryResultObjectDataMap(null);
            queryParams.setNoOfRecords(200);
			jdbcDao.openSession(queryDetailsObj.getSessionData());
			AbstractQueryExecutor queryExecutor = Utility.getQueryExecutor();
            dataList = queryExecutor.getQueryResultList(queryParams).getResult();
			jdbcDao.commit();
		}
		finally
		{
			jdbcDao.closeSession();
		}
		if(dataList!=null)
		{
			List<String> countList = (List<String>)dataList.get(0);
			count = Integer.valueOf(countList.get(0));
		}
		return count;
	}
	/**
	 *
	 * @param columnName
	 * @param newColumnValue
	 * @param auditEventId
	 * @throws DAOException
	 * @throws SQLException
	 * @throws SQLException
	 */
	public static void updateAuditQueryDetails(String columnName, String newColumnValue,
			long auditEventId) throws DAOException, SQLException
	{
		String tableName = "catissue_audit_event_query_log";
		String appName=CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		jdbcDao.openSession(null);
		ResultSet resultSet = jdbcDao.getQueryResultSet("select * from "+tableName+" where 1=0");
		ResultSetMetaData metaData;
		try
		{
			metaData = resultSet.getMetaData();
			int columnType = 0;
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnNameInTable = metaData.getColumnName(i);
				if(columnNameInTable.equalsIgnoreCase(columnName))
				{
					columnType = metaData.getColumnType(i);
					break;
				}
			}
			String value = getValueForDBType(newColumnValue, columnType);
			LinkedList<ColumnValueBean> columnValueBean = new LinkedList<ColumnValueBean>();
			ColumnValueBean bean = new ColumnValueBean("value",value);
			columnValueBean.add(bean);
			bean = new ColumnValueBean("auditEventId",Long.valueOf(auditEventId));
			columnValueBean.add(bean);
			LinkedList<LinkedList<ColumnValueBean>> beanList = new LinkedList<LinkedList<ColumnValueBean>>();
			beanList.add(columnValueBean);
			String updateSql = AQConstants.UPDATE+" "+ tableName + " "+AQConstants.SET + " " + columnName + " = ?"+
			" "+AQConstants.WHERE + " " + AQConstants.AUDIT_EVENT_ID + "= ?";
			jdbcDao.executeUpdate(updateSql, beanList);
			jdbcDao.commit();
		}
		catch (DAOException e)
		{
			Logger.out.error("Error while updating query auditing details :\n"+e);
			throw e;
		}
		catch (SQLException e1) {
			Logger.out.error("Error while updating query auditing details :\n"+e1);
			throw e1;
		}
		finally
		{
			jdbcDao.closeSession();
			resultSet.close();
		}
	}

	/**
	 * Returns the value specific to data base type.
	 * @param newColumnValue value to be converted
	 * @param columnType type of the column.
	 * @return String value
	 */
	private static String getValueForDBType(String newColumnValue, int columnType) {
		String value = "";
		switch(columnType)
		{
		case Types.VARCHAR :
			value = newColumnValue;
			break;
		case Types.BIGINT :
		case Types.BIT :
		case Types.TINYINT :
		case Types.NUMERIC :
		default:
			value = newColumnValue;
			break;
		}
		return value;
	}
}
