
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.JDBCDAO;
import edu.wustl.common.dao.QuerySessionData;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;

/**
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
	 */
	public static List<List<String>> executeQuery(final SessionDataBean sessionData,
			final QuerySessionData querySessionData) throws ClassNotFoundException, DAOException
	{
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		List<List<String>> dataList = new ArrayList<List<String>>();
		try
		{
			dao.openSession(sessionData);
			dataList = dao.executeQuery(querySessionData.getSql(), sessionData, querySessionData
					.isSecureExecute(), querySessionData.isHasConditionOnIdentifiedField(),
					querySessionData.getQueryResultObjectDataMap());
			dao.commit();
		}
		finally
		{
			dao.closeSession();
		}
		return dataList;
	}

	/**
		 * Creates a new table in database. First the table is deleted if exist already.
		 * @param tableName name of the table to be deleted before creating new one.
		 * @param createTableSql sql to create table
		 * @param sessionData session data.
		 * @throws DAOException DAOException 
		 */
	//Siddharth Shah
//	public static void executeCreateTable(final String tableName, final String createTableSql,
//			QueryDetails queryDetailsObj) throws DAOException
//	{
//		JDBCDAO jdbcDao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
//		try
//		{
//			jdbcDao.openSession(queryDetailsObj.getSessionData());
//			jdbcDao.delete(tableName);
//			jdbcDao.executeUpdate(createTableSql);
//			jdbcDao.commit();
//		}
//		catch (DAOException e)
//		{
//			Logger.out.error(e);
//			//			e.printStackTrace();
//			//			throw e;
//		}
//		finally
//		{
//			jdbcDao.closeSession();
//		}
//	}
	
	public static void executeCreateTable(final String tableName, final String createTableSql,
			QueryDetails queryDetailsObj) throws DAOException
	{
		JDBCDAO jdbcDao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);	
		try
		{
			jdbcDao.openSession(queryDetailsObj.getSessionData());
			if(Variables.databaseName.trim().equalsIgnoreCase("DB2"))
			{
				String newCreateTableSql = Constants.CREATE_TABLE + " " + tableName + " " + Constants.AS + " "
				+ "(" + createTableSql + ")" + "WITH NO DATA";
				String newInsertTableSql = "insert into " + tableName + " (" + createTableSql + ")";
				String newSetSession = "set current schema = " + Variables.properties.getProperty("xquery.schemaName");				
				jdbcDao.executeUpdate(newSetSession);
				jdbcDao.delete(tableName);
				jdbcDao.executeUpdate(newCreateTableSql);
				jdbcDao.executeUpdate(newInsertTableSql);
				jdbcDao.commit();
			}
			else
			{
				String newCreateTableSql = Constants.CREATE_TABLE + tableName + " " + Constants.AS + " "
				+ createTableSql;
				jdbcDao.delete(tableName);
				jdbcDao.executeUpdate(newCreateTableSql);
				jdbcDao.commit();
			}
		}
		catch (DAOException e)
		{
			Logger.out.error(e);
			//			e.printStackTrace();
			//			throw e;
		}
		finally
		{
			jdbcDao.closeSession();
		}
	}


	/**
		 * @param tableName
		 * @param columnNameIndexMap
		 * @return
		 */
	public static String getSQLForRootNode(final String tableName,
			Map<String, String> columnNameIndexMap)
	{
		//		Map<String,String> columnNameIndexMap = getColumnNamesForSelectpart(root,queryResulObjectDataMap,uniqueIdNodesMap2);
		String columnNames = columnNameIndexMap.get(Constants.COLUMN_NAMES);
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select distinct ").append(columnNames).append(" from ").append(tableName)
				.append(" where ");
		String indexStr = columnNameIndexMap.get(Constants.INDEX);
		
		int index = -1;
		if (indexStr != null && !Constants.NULL.equals(indexStr))
		{
			index = Integer.valueOf(indexStr);
		}
		String idColumnName = columnNameIndexMap.get(Constants.ID_COLUMN_NAME);
		StringTokenizer stringTokenizer = new StringTokenizer(idColumnName,",");
		while(stringTokenizer.hasMoreElements())
		{
			selectSql.append(" "+stringTokenizer.nextElement()).append(" is not null "+LogicalOperator.And);
		}
		if(selectSql.substring(selectSql.length()-3).equals(LogicalOperator.And.toString()))
		{
			selectSql = new StringBuffer(selectSql.substring(0,selectSql.length()-3));
		}
		selectSql = selectSql.append(Constants.NODE_SEPARATOR).append(index);
		//selectSql = selectSql + Constants.NODE_SEPARATOR + index;
		return selectSql.toString();
	}

}
