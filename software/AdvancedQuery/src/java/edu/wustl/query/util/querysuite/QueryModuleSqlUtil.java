package edu.wustl.query.util.querysuite;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.util.QueryParams;
import edu.wustl.common.util.XMLPropertyHandler;
import edu.wustl.common.util.global.CommonServiceLocator;
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
 * Utility class for SQL related operations required for Query.
 *
 * @author santhoshkumar_c
 *
 */
final public class QueryModuleSqlUtil {
	private QueryModuleSqlUtil() {
	}

	/**
	 * Creates a view in database for advance query.
	 *
	 * @param tableName
	 *            name of the table to be deleted before creating new one.
	 * @param createTableSql
	 *            SQL to create table
	 * @param sessionData
	 *            session data.
	 * @throws DAOException
	 *             DAOException
	 */
	public static void executeCreateTable(final String tableName,
			final String createTableSql, QueryDetails queryDetailsObj)
			throws DAOException {
		String appName = CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(
				appName);
		JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		try {
			String tablespace = XMLPropertyHandler
					.getValue(AQConstants.TABLESPACE);
			if (tablespace.length() != 0) {
				tablespace = "TABLESPACE " + tablespace;
			}
			jdbcDao.openSession(queryDetailsObj.getSessionData());

			String createViewQuery = createTableSql;
				while (createViewQuery.contains("(select")) {//check for inner select query
					int startIndex = createViewQuery.indexOf("(select");
					int lastIndex = createViewQuery.indexOf(")");

					while (lastIndex < startIndex) {//indices for inner select query
						lastIndex = createViewQuery.indexOf(")", lastIndex + 1);
					}
					//substring for creating inner view from inner select query

					String innerViewQuery = createViewQuery.substring(startIndex, lastIndex + 1);
					String innerViewName = ("VIEW" + (new Random().nextInt(1000)));//inner view name
					String innerView = "CREATE OR REPLACE VIEW "+ innerViewName + " " + tablespace + " " + " AS "+ innerViewQuery;
					//inner view create query

					jdbcDao.executeUpdate(innerView);//creating inner view

					/**
					 * Replacing the inner select query substring with the created inner view
					 */
					createViewQuery = createViewQuery.replace(innerViewQuery,innerViewName);
				}
			//creating a final view with inner views
			String advViewQuery = "CREATE OR REPLACE VIEW " + tableName + " "+ tablespace + " " + " AS " + createViewQuery;
			executeInsertQuery(queryDetailsObj, jdbcDao, advViewQuery);
		} catch (DAOException e) {
			Logger.out.error(e);
			throw e;
		} finally {
			jdbcDao.closeSession();
		}
	}

	/**
	 * @param queryDetailsObj
	 *            queryDetailsObj
	 * @param jdbcDao
	 *            jdbcDao
	 * @param insertSql
	 *            insert query
	 * @throws DAOException
	 *             DAOException
	 */
	private static void executeInsertQuery(QueryDetails queryDetailsObj,
			JDBCDAO jdbcDao, String insertSql) throws DAOException {
		int cnt =0;
		String value;
		/**
		 * removing binding variables by putting variables name in where clause
		 */
		while (insertSql.contains("?")) {
			value = "'"+queryDetailsObj.getColumnValueBean().get(cnt++).getColumnName()+"'";
			insertSql=insertSql.replaceFirst("\\?", value);//modifying sql for inserting variables in where clause
		}
		jdbcDao.executeUpdate(insertSql);
		jdbcDao.commit();
	}

	/**
	 * @param createTableSql
	 *            query
	 * @return newSql
	 */
	private static String modifySqlForCreateTable(String createTableSql) {
		String whereClause = "";
		if (createTableSql.indexOf("where") == -1) {
			whereClause = "WHERE";
		} else {
			whereClause = "where";
		}
		String newSql = createTableSql.substring(0, createTableSql
				.lastIndexOf(whereClause) + 6);
		return newSql;
	}

	/**
	 * @param tableName
	 * @param columnNameIndexMap
	 * @return
	 */
	public static String getSQLForRootNode(final String tableName,
			Map<String, String> columnNameIndexMap) {
		String columnNames = columnNameIndexMap.get(AQConstants.COLUMN_NAMES);
		String indexStr = columnNameIndexMap.get(AQConstants.INDEX);
		int index = -1;
		if (indexStr != null && !AQConstants.NULL.equals(indexStr)) {
			index = Integer.valueOf(indexStr);
		}
		String idColumnName = columnNames;
		if (columnNames.indexOf(',') != -1) {
			idColumnName = columnNames.substring(0, columnNames.indexOf(','));
		}
		StringBuffer selectSql = new StringBuffer(80);
		selectSql.append("select distinct ").append(columnNames).append(
				" from ").append(tableName).append(" where ").append(
				idColumnName).append(" is not null");
		selectSql = selectSql.append(AQConstants.NODE_SEPARATOR).append(index);
		return selectSql.toString();
	}

	/**
	 * Method to get count for the given SQL query
	 *
	 * @param sql
	 *            original SQL for which count is required
	 * @param queryDetailsObj
	 *            object of QueryDetails
	 * @return count integer value of count
	 * @throws DAOException
	 * @throws ClassNotFoundException
	 * @throws SMException
	 */
	public static int getCountForQuery(final String sql,
			QueryDetails queryDetailsObj) throws DAOException,
			ClassNotFoundException, SMException {
		int count = 0;
		List<List<String>> dataList = null;
		String appName = CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(
				appName);
		JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		try {
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
			dataList = queryExecutor.getQueryResultList(queryParams)
					.getResult();
			jdbcDao.commit();
		} finally {
			jdbcDao.closeSession();
		}
		if (dataList != null) {
			List<String> countList = dataList.get(0);
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
	public static void updateAuditQueryDetails(String columnName,
			String newColumnValue, long auditEventId) throws DAOException,
			SQLException {
		String tableName = "catissue_audit_event_query_log";
		String appName = CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(
				appName);
		JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		jdbcDao.openSession(null);
		ResultSet resultSet = jdbcDao.getQueryResultSet("select * from "
				+ tableName + " where 1=0");
		ResultSetMetaData metaData;
		try {
			metaData = resultSet.getMetaData();
			LinkedList<LinkedList<ColumnValueBean>> beanList = populateBeanList(
					columnName, newColumnValue, auditEventId, metaData);
			executeUpdateQuery(columnName, tableName, jdbcDao, beanList);
		} catch (DAOException e) {
			Logger.out.error("Error while updating query auditing details :\n"
					+ e);
			throw e;
		} catch (SQLException e1) {
			Logger.out.error("Error while updating query auditing details :\n"
					+ e1);
			throw e1;
		} finally {
			jdbcDao.closeSession();
			resultSet.close();
		}
	}

	/**
	 * @param columnName
	 *            columnName
	 * @param tableName
	 *            tableName
	 * @param jdbcDao
	 *            jdbcDao
	 * @param beanList
	 *            beanList
	 * @throws DAOException
	 *             DAOException
	 */
	private static void executeUpdateQuery(String columnName, String tableName,
			JDBCDAO jdbcDao, LinkedList<LinkedList<ColumnValueBean>> beanList)
			throws DAOException {
		String updateSql = AQConstants.UPDATE + " " + tableName + " "
				+ AQConstants.SET + " " + columnName + " = ?" + " "
				+ AQConstants.WHERE + " " + AQConstants.AUDIT_EVENT_ID + "= ?";
		jdbcDao.executeUpdate(updateSql, beanList);
		jdbcDao.commit();
	}

	/**
	 * @param columnName
	 *            columnName
	 * @param newColumnValue
	 *            newColumnValue
	 * @param auditEventId
	 *            auditEventId
	 * @param metaData
	 *            metaData
	 * @return metaData
	 * @throws SQLException
	 *             SQLException
	 */
	private static LinkedList<LinkedList<ColumnValueBean>> populateBeanList(
			String columnName, String newColumnValue, long auditEventId,
			ResultSetMetaData metaData) throws SQLException {
		int columnType = getColumnType(columnName, metaData);
		String value = getValueForDBType(newColumnValue, columnType);
		LinkedList<ColumnValueBean> columnValueBean = new LinkedList<ColumnValueBean>();
		ColumnValueBean bean = new ColumnValueBean("value", value);
		columnValueBean.add(bean);
		bean = new ColumnValueBean("auditEventId", Long.valueOf(auditEventId));
		columnValueBean.add(bean);
		LinkedList<LinkedList<ColumnValueBean>> beanList = new LinkedList<LinkedList<ColumnValueBean>>();
		beanList.add(columnValueBean);
		return beanList;
	}

	/**
	 * @param columnName
	 *            columnName
	 * @param metaData
	 *            metaData
	 * @return columnType
	 * @throws SQLException
	 *             SQLException
	 */
	private static int getColumnType(String columnName,
			ResultSetMetaData metaData) throws SQLException {
		int columnType = 0;
		int columnCount = metaData.getColumnCount();
		for (int counter = 1; counter <= columnCount; counter++) {
			String columnNameInTable = metaData.getColumnName(counter);
			if (columnNameInTable.equalsIgnoreCase(columnName)) {
				columnType = metaData.getColumnType(counter);
				break;
			}
		}
		return columnType;
	}

	/**
	 * Returns the value specific to data base type.
	 *
	 * @param newColumnValue
	 *            value to be converted
	 * @param columnType
	 *            type of the column.
	 * @return String value
	 */
	private static String getValueForDBType(String newColumnValue,
			int columnType) {
		String value = "";
		switch (columnType) {
		case Types.VARCHAR:
			value = newColumnValue;
			break;
		case Types.BIGINT:
		case Types.BIT:
		case Types.TINYINT:
		case Types.NUMERIC:
		default:
			value = newColumnValue;
			break;
		}
		return value;
	}
}
