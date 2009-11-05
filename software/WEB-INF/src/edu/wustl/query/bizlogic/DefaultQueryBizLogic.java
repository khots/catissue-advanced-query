package edu.wustl.query.bizlogic;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.bizlogic.IQueryBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.util.PagenatedResultData;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.dao.DAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.AdvanceQueryDAO;


public class DefaultQueryBizLogic extends DefaultBizLogic implements IQueryBizLogic
{


	/**
	 * constructor initialized with default application name.
	 */
	public DefaultQueryBizLogic()
	{
		//application name need to update in.
		//query.properties as well as application resource.properties
		super(AdvanceQueryDAO.getInstance().getAppName());

	}
	/**
	 * This method return true if authorized user.
	 * @param dao DAO object.
	 * @param domainObject Domain object.
	 * @param sessionDataBean  SessionDataBean object.
	 * @throws BizLogicException generic BizLogic Exception
	 * @return true if authorized user.
	 */

	//@see edu.wustl.common.bizlogic.IBizLogic#
	// isAuthorized(edu.wustl.common.dao.AbstractDAO, java.lang.Object, edu.wustl.common.beans.SessionDataBean)
	public boolean isAuthorized(DAO dao, Object domainObject, SessionDataBean sessionDataBean)
			throws BizLogicException
	{
//		boolean isAuthorized = false;
//		if(sessionDataBean != null && sessionDataBean.isAdmin())
//		{
			return true;
//		}
//		return isAuthorized;
	}

/**
 * @param sessionDataBean - SessionDataBean
 * @param querySessionData - QuerySessionData
 * @param startIndex - int
 * @return PagenatedResultData
 */
	public PagenatedResultData execute(SessionDataBean sessionDataBean,
			QuerySessionData querySessionData, int startIndex) throws DAOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param sql - String
	 * @return List
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public List executeSQL(String sql) throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param columnName - String
	 * @param columnValue - Object
	 * @return String
	 * @throws DAOException - exception
	 */
	public String getAliasName(String columnName, Object columnValue) throws DAOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param aliasName - Alias Name
	 * @param forQI - For QI
	 * @return Set
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public Set getAllTableNames(String aliasName, int forQI) throws DAOException,
			ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param columnName - String
	 * @param aliasName - String
	 * @return String
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public String getAttributeType(String columnName, String aliasName) throws DAOException,
			ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param value - String
	 * @return List
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public List getColumnNames(String value) throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param aliasName - Alias Name
	 * @param defaultViewAttributesOnly - boolean
	 * @return List
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public List getColumnNames(String aliasName, boolean defaultViewAttributesOnly)
			throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param aliasName - Alias Name
	 * @return String
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public String getDisplayName(String aliasName) throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @param tableName - Table Name
	 * @return String
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public String getDisplayNamebyTableName(String tableName) throws DAOException,
			ClassNotFoundException
	{
		String displayName=null;
		if("QUERY_WORKFLOW".equals(tableName))
		{
			displayName = Constants.WORKFLOW;
		}
		return displayName;
	}

	/**
	 * @return List
	 * @throws DAOException - Exception
	 */
	public List getMainObjectsOfQuery() throws DAOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param prevValue - String
	 * @return Set
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public Set getNextTableNames(String prevValue) throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Map
	 */
	public Map getPivilegeTypeMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Map
	 */
	public Map getQueryObjectNameTableNameMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param aliasName - String
	 * @return List
	 * @throws DAOException - Exception
	 */
	public List getRelatedTableAliases(String aliasName) throws DAOException
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return Map
	 */
	public Map getRelationData()
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @param specimanType - String
	 * @param jdbcDAO - JDBCDAO object
	 * @return String
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public String getSpecimenTypeCount(String specimanType, JDBCDAO jdbcDAO) throws DAOException,
			ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param aliasName - String
	 * @return String
	 * @throws DAOException - Exception
	 */
	public String getTableIdFromAliasName(String aliasName) throws DAOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Map<String, Object>
	 * @throws DAOException
	 */
	public Map<String, Object> getTotalSummaryDetails() throws DAOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Initialize Data Query.
	 */
	public void initializeQueryData()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @param sqlQuery - String
	 * @param sessionData - SessionDataBean
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public void insertQuery(String sqlQuery, SessionDataBean sessionData) throws DAOException,
			ClassNotFoundException
	{
		// TODO Auto-generated method stub

	}
	/**
	 * @param sqlQuery - String
	 * @param sessionData - SessionDataBean
	 * @param jdbcDAO - JDBCDAO
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public void insertQueryForMySQL(String sqlQuery, SessionDataBean sessionData, JDBCDAO jdbcDAO)
			throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @param sqlQuery - String
	 * @param sessionData - SessionDataBean
	 * @param jdbcDAO - JDBCDAO
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 * @throws SQLException - exception
	 * @throws IOException - Exception
	 */
	public void insertQueryForOracle(String sqlQuery, SessionDataBean sessionData, JDBCDAO jdbcDAO)
			throws DAOException, ClassNotFoundException, SQLException, IOException
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @param aliasName - String
	 * @return List
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public List setColumnNames(String aliasName) throws DAOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param parentTableId - Table Id
	 * @param childTableId - Table Id
	 * @return Set
	 * @throws DAOException - Exception
	 * @throws ClassNotFoundException - Exception
	 */
	public Set setTablesInPath(Long parentTableId, Long childTableId) throws DAOException,
			ClassNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

}