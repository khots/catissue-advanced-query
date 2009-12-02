/**
 *<p>Title: </p>
 *<p>Description:  </p>
 *<p>Copyright: (c) Washington University, School of Medicine 2004</p>
 *<p>Company: Washington University, School of Medicine, St. Louis.</p>
 *@author Aarti Sharma
 *@version 1.0
 */

package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.DAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.QueryWhereClause;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;

/**
 *<p>Title: </p>
 *<p>Description:  </p>
 *<p>Copyright: (c) Washington University, School of Medicine 2005</p>
 *<p>Company: Washington University, School of Medicine, St. Louis.</p>
 *@author Aarti Sharma
 *@version 1.0
 */

public class QueryBizLogic extends DefaultBizLogic
{

	/*
	    private static final String ALIAS_NAME_TABLE_NAME_MAP_QUERY = "select ALIAS_NAME ,TABLE_NAME from "
	            + "CATISSUE_QUERY_TABLE_DATA";

	    private static final String ALIAS_NAME_PRIVILEGE_TYPE_MAP_QUERY = "select ALIAS_NAME ,
	    PRIVILEGE_ID from "+ "CATISSUE_QUERY_TABLE_DATA";

	    private static final String GET_RELATION_DATA = "select FIRST_TABLE_ID, SECOND_TABLE_ID,"
	            + "FIRST_TABLE_JOIN_COLUMN, SECOND_TABLE_JOIN_COLUMN "
	            + "from CATISSUE_RELATED_TABLES_MAP";


	    private static final String GET_TABLE_ALIAS = "select ALIAS_NAME from CATISSUE_QUERY_TABLE_DATA "
	            + "where TABLE_ID=";

	    private static final String GET_RELATED_TABLE_ALIAS_PART1 = "SELECT table2.alias_name "
	            + " from catissue_table_relation relation, CATISSUE_QUERY_TABLE_DATA table1, "
	            + " CATISSUE_QUERY_TABLE_DATA table2 "
	            + " where relation.parent_table_id = table1.table_id and relation.child_table_id =
	             table2.table_id "+ " and table1.alias_name = ";

	    private static final String GET_RELATED_TABLE_ALIAS_PART2 = " and exists "
	            + "(select * from catissue_search_display_data displayData "
	            + " where relation.RELATIONSHIP_ID = displayData.RELATIONSHIP_ID)";
	    */
	/**
	 * Bug#3549
	 * Patch 1_1
	 * Description: modified query to order the result  by ATTRIBUTE_ORDER column.
	 * @param value value
	 * @return columnNameValueBeanList List of name value beans
	 * @throws DAOException DAOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	public List getColumnNames(String value) throws DAOException, ClassNotFoundException
	{
		String sql = " SELECT tableData2.ALIAS_NAME, temp.COLUMN_NAME, temp.ATTRIBUTE_TYPE," +
				" temp.TABLES_IN_PATH, temp.DISPLAY_NAME,temp.ATTRIBUTE_ORDER "
				+ " from CATISSUE_QUERY_TABLE_DATA tableData2 join "
				+ " ( SELECT  columnData.COLUMN_NAME, columnData.TABLE_ID,"
				+ " columnData.ATTRIBUTE_TYPE, "
				+ " displayData.DISPLAY_NAME, displayData.ATTRIBUTE_ORDER ,"
				+ " relationData.TABLES_IN_PATH "
				+ " FROM CATISSUE_INTERFACE_COLUMN_DATA columnData, "
				+ " CATISSUE_TABLE_RELATION relationData, "
				+ " CATISSUE_QUERY_TABLE_DATA tableData, "
				+ " CATISSUE_SEARCH_DISPLAY_DATA displayData "
				+ " where relationData.CHILD_TABLE_ID = columnData.TABLE_ID and "
				+ " relationData.PARENT_TABLE_ID = tableData.TABLE_ID and "
				+ " relationData.RELATIONSHIP_ID = displayData.RELATIONSHIP_ID and "
				+ " columnData.IDENTIFIER = displayData.COL_ID and "
				+ " tableData.ALIAS_NAME = '"
				+ value
				+ "') temp "
				+ " on temp.TABLE_ID = tableData2.TABLE_ID ORDER BY temp.ATTRIBUTE_ORDER";
		Logger.out.debug("SQL*****************************" + sql);
		String appName = CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
		jdbcDao.openSession(null);
		List list = jdbcDao.executeQuery(sql);//, null, false, null);
		jdbcDao.closeSession();
		List columnNameValueBeanList = new ArrayList();
		Iterator iterator = list.iterator();
		int counter = 0;
		while (iterator.hasNext())
		{
			List rowList = (List) iterator.next();
			String columnValue = (String) rowList.get(counter++) + "." +
			(String) rowList.get(counter++) + "."+ (String) rowList.get(counter++);
			String tablesInPath = (String) rowList.get(counter++);
			if ((tablesInPath != null) && (!"".equals(tablesInPath)))
			{
				columnValue = columnValue + "." + tablesInPath;
			}
			String columnName = (String) rowList.get(counter++);
			NameValueBean nameValueBean = new NameValueBean();
			nameValueBean.setName(columnName);
			nameValueBean.setValue(columnValue);
			columnNameValueBeanList.add(nameValueBean);
			counter = 0;
		}
		return columnNameValueBeanList;
	}

	/**
	 * Returns all the tables in the simple query interface.
	 * @param aliasName alias name of table
	 * @param forQI forQI
	 * @throws DAOException DAOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @return objectNameValueBeanList objectNameValueBeanList
	 */
	public Set getAllTableNames(String aliasName, int forQI) throws DAOException,
			ClassNotFoundException
	{
		String[] selectColumnNames = {AQConstants.TABLE_DISPLAY_NAME_COLUMN,
				AQConstants.TABLE_ALIAS_NAME_COLUMN};
		String[] whereColumnNames = {AQConstants.TABLE_FOR_SQI_COLUMN};
		String[] whereColCondns = {"="};
		String[] whereColumnValues = {String.valueOf(forQI)};

		if ((aliasName != null) && (!"".equals(aliasName)))
		{
			whereColumnNames = new String[AQConstants.TWO];
			whereColumnNames[0] = AQConstants.TABLE_FOR_SQI_COLUMN;
			whereColumnNames[1] = AQConstants.TABLE_ALIAS_NAME_COLUMN;
			whereColCondns = new String[AQConstants.TWO];
			whereColCondns[0] = "=";
			whereColCondns[1] = "=";
			whereColumnValues = new String[AQConstants.TWO];
			whereColumnValues[0] = String.valueOf(forQI);
			aliasName = "'" + aliasName + "'";
			whereColumnValues[1] = aliasName;
		}
		String appName = CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDAO = daoFactory.getJDBCDAO();
		jdbcDAO.openSession(null);
		QueryWhereClause whereClause = new QueryWhereClause(appName);
		whereClause.getWhereCondition(whereColumnNames, whereColCondns, whereColumnValues,
				null);
		List tableList = jdbcDAO.retrieve(AQConstants.TABLE_DATA_TABLE_NAME, selectColumnNames,
				whereClause);
		jdbcDAO.closeSession();

		Set objectNameValueBeanList = new TreeSet();
		if (aliasName == null || "".equals(aliasName))
		{
			NameValueBean nameValueBean = new NameValueBean();
			nameValueBean.setValue("-1");
			nameValueBean.setName(AQConstants.SELECT_OPTION);
			objectNameValueBeanList.add(nameValueBean);
		}
		Iterator objIterator = tableList.iterator();
		while (objIterator.hasNext())
		{
			List row = (List) objIterator.next();
			NameValueBean nameValueBean = new NameValueBean();
			nameValueBean.setName((String) row.get(0));
			nameValueBean.setValue((String) row.get(1));
			objectNameValueBeanList.add(nameValueBean);
		}
		return objectNameValueBeanList;
	}

	/**
	 * This method is called for saveQuery
	 * always returns true as for save query doesn't require authorization check.
	 * @param dao DAO object
	 * @param domainObject domainObject
	 * @param sessionDataBean A data bean that contains information related to user logged in.
	 * @throws BizLogicException BizLogicException
	 * @return true
	 */
	public boolean isAuthorized(DAO dao, Object domainObject, SessionDataBean sessionDataBean)
			throws BizLogicException
	{
		return true;
	}
}