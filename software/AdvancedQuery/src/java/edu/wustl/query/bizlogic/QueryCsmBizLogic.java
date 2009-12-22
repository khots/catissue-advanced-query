/**
 *
 */

package edu.wustl.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.util.QueryParams;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.query.generator.ColumnValueBean;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.executor.AbstractQueryExecutor;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.security.exception.SMException;

/**
 * @author supriya_dankh
 *
 */
public class QueryCsmBizLogic
{
	/**
     * logger Logger - Generic logger.
     */
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(QueryCsmBizLogic.class);

	/**
	 * @param selectSql The select query
	 * @param queryDetailsObj Object of QueryDetails
	 * @param queryResulObjectDataMap The queryResulObjectDataMap
	 * @param root Object of OutputTreeDataNode
	 * @param hasConditionOnIdentifiedField to specify if the query
	 * has condition on identified field.
	 * @param startIndex The startIndex.
	 * @param noOfRecords Number of records.
	 * @return dataList List of records
	 * @throws BizLogicException Biz Logic Exception
	 */
    public List<List<String>> executeCSMQuery(String selectSql, QueryDetails queryDetailsObj,
            Map<Long, QueryResultObjectDataBean> queryResulObjectDataMap, OutputTreeDataNode root,
            boolean hasConditionOnIdentifiedField, int startIndex, int noOfRecords) throws BizLogicException
    {
        List<List<String>> dataList = new ArrayList<List<String>>();
            QueryParams queryParams = new QueryParams();
            queryParams.setQuery(selectSql);
            queryParams.setSessionDataBean(queryDetailsObj.getSessionData());
            queryParams.setSecureToExecute(queryDetailsObj.getSessionData().isSecurityRequired());
            queryParams.setHasConditionOnIdentifiedField(hasConditionOnIdentifiedField);
            queryParams.setQueryResultObjectDataMap(queryResulObjectDataMap);
            queryParams.setStartIndex(startIndex);
            queryParams.setNoOfRecords(noOfRecords);
            AbstractQueryExecutor queryExecutor = Utility.getQueryExecutor();
            try
            {
				dataList = queryExecutor.getQueryResultList(queryParams).getResult();
			}
            catch (DAOException e)
            {
				logger.error(e.getMessage(), e);
				throw new BizLogicException
				(null,e,"DAOException : error while retrieving data for first tree node");
			}
            catch (SMException e)
            {
				logger.error(e.getMessage(), e);
				throw new BizLogicException
				(null,e,"SMException : error while retrieving data for first tree node");
			}
        return dataList;
    }

	/**
	 * Retrieves the main entity list if the entity is Abstract.
	 * @param firstEntity Abstract Entity
	 * @param lastEntity The entity on which the query has been fired
	 * @return mainEntityList List of main entities
	 */
    public static List<EntityInterface> getMainEntityList(EntityInterface firstEntity,
            EntityInterface lastEntity)
    {
        Long id1 = firstEntity.getId();
        Long id2 = lastEntity.getId();
        String appName = CommonServiceLocator.getInstance().getAppName();
        IDAOFactory daoFact = DAOConfigFactory.getInstance().getDAOFactory(appName);
        JDBCDAO jdbcDAO = null;
        ResultSet resultSet = null;
        List<Long> firstEntityIdList = new ArrayList<Long>();
        List<EntityInterface> mainEntityList = new ArrayList<EntityInterface>();
        try
        {
            jdbcDAO = daoFact.getJDBCDAO();
            jdbcDAO.openSession(null);
            LinkedList<ColumnValueBean> columnValueBean = new LinkedList<ColumnValueBean>();
    		ColumnValueBean bean = new ColumnValueBean("id1",id1);
    		columnValueBean.add(bean);
    		bean = new ColumnValueBean("id2",id2);
    		columnValueBean.add(bean);
    		bean = new ColumnValueBean("id2",id2);
    		columnValueBean.add(bean);
    		String query = "Select FIRST_ENTITY_ID from PATH where INTERMEDIATE_PATH in (Select INTERMEDIATE_PATH" +
            " from PATH where FIRST_ENTITY_ID = ? and LAST_ENTITY_ID = ?) and LAST_ENTITY_ID = ?";
            resultSet = jdbcDAO.getResultSet(query, columnValueBean, null);
            while (resultSet.next())
            {
                if (resultSet.getInt(1) != id1)
                {
                    firstEntityIdList.add(resultSet.getLong(1));
                }
            }
			jdbcDAO.closeStatement(resultSet);

            populateMainEntityList(firstEntity, firstEntityIdList, mainEntityList);
        }
        catch (SQLException e)
        {
        	logger.info(e.getMessage(), e);
        }
        catch (DAOException e)
        {
        	logger.info(e.getMessage(), e);
        }
        finally
        {
            try
            {
                jdbcDAO.closeSession();
            }
            catch (DAOException e)
            {
            	logger.info(e.getMessage(), e);
            }
        }
        return mainEntityList;
    }

    /**
     * Populates the main entity list.
     * @param firstEntity First Entity
     * @param firstEntityIdList First Entity Id List
     * @param mainEntityList Main Entity List to be populated
     */
	private static void populateMainEntityList(EntityInterface firstEntity,
			List<Long> firstEntityIdList, List<EntityInterface> mainEntityList)
	{
		Collection<EntityInterface> allEntities = firstEntity.getEntityGroup()
		.getEntityCollection();
		for (Long firstEntityId : firstEntityIdList)
		{
		    for (EntityInterface tempEntity : allEntities)
		    {
		        if (Integer.parseInt(tempEntity.getId().toString()) == Integer
		                .parseInt(firstEntityId.toString()))
		        {
		            mainEntityList.add(tempEntity);
		            break;
		        }
		    }
		}
	}

    /**
	 * @param selectSql The select sql
	 * @param queryDetailsObj QueryDetails object
	 * @param queryResulObjectDataMap The QueryResultObjectDataMap
	 * @param root Object of OutputTreeDataNode
	 * @param hasConditionOnIdentifiedField to specify if the query
	 * has condition on identified field.
	 * @return dataList The list of records
	 */
	public List executeCSMQuery(String selectSql, QueryDetails queryDetailsObj,
			Map<Long, QueryResultObjectDataBean> queryResulObjectDataMap, OutputTreeDataNode root,
			boolean hasConditionOnIdentifiedField)
	//throws DAOException, ClassNotFoundException
	{/*
		 String appName = CommonServiceLocator.getInstance().getAppName();
        IDAOFactory daoFact = DAOConfigFactory.getInstance().getDAOFactory(appName);
        JDBCDAO dao = null;
		List<List<String>> dataList = new ArrayList<List<String>>();
		try
		{
			dao = daoFact.getJDBCDAO();
			dao.openSession(queryDetailsObj.getSessionData());
			dataList = dao.executeQuery(selectSql, queryDetailsObj.getSessionData(),
					queryDetailsObj.getSessionData().isSecurityRequired(),
					hasConditionOnIdentifiedField, queryResulObjectDataMap);
			dao.commit();
			dao.closeSession();
		}

		catch (DAOException t)
		{
			t.printStackTrace();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
		return dataList;
	*/
		return null;
		}
}
