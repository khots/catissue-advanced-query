/**
 * 
 */

package edu.wustl.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.querysuite.AdvanceQueryDAO;

/**
 * @author supriya_dankh
 *
 */
public class QueryCsmBizLogic
{

	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(QueryCsmBizLogic.class);

	/**
	 * Retrieves the main entity list if the entity is Abstract
	 * @param firstEntity Abstract Entity
	 * @param lastEntity The entity on which the query has been fired
	 * @return List of main entities
	 */
	public static List<QueryableObjectInterface> getMainEntityList(
			QueryableObjectInterface firstEntity, QueryableObjectInterface lastEntity)
	{
		Long id1 = firstEntity.getId();
		Long id2 = lastEntity.getId();
		String appName = AdvanceQueryDAO.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcdao = null;
		ResultSet resultset = null;

		List<QueryableObjectInterface> mainEntityList = null;
		try
		{
			jdbcdao = daoFactory.getJDBCDAO();
			jdbcdao.openSession(null);
			resultset = jdbcdao
					.getQueryResultSet("Select FIRST_ENTITY_ID from PATH where INTERMEDIATE_PATH in (Select INTERMEDIATE_PATH from PATH where FIRST_ENTITY_ID = "
							+ id1
							+ " and LAST_ENTITY_ID = "
							+ id2
							+ ") and LAST_ENTITY_ID = "
							+ id2);
			mainEntityList = createMainEntityList(firstEntity, resultset);
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			try
			{
				resultset.close();
				jdbcdao.closeSession();
			}
			catch (DAOException e)
			{
				logger.error(e.getMessage(), e);
			}
			catch (SQLException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		return mainEntityList;
	}

	/**
	 * @param firstEntity
	 * @param resultset
	 * @return
	 * @throws SQLException
	 */
	private static List<QueryableObjectInterface> createMainEntityList(
			QueryableObjectInterface firstEntity, ResultSet resultset) throws SQLException
	{
		List<Long> firstEntityIdList = new ArrayList<Long>();
		List<QueryableObjectInterface> mainEntityList = new ArrayList<QueryableObjectInterface>();
		while (resultset.next())
		{
			if (resultset.getInt(1) != firstEntity.getId())
			{
				firstEntityIdList.add(resultset.getLong(1));
			}
		}
		addToMainEntityList(firstEntity, firstEntityIdList, mainEntityList);
		return mainEntityList;
	}

	/**
	 * 
	 * @param firstEntity
	 * @param firstEntityIdList
	 * @param mainEntityList
	 */
	private static void addToMainEntityList(QueryableObjectInterface firstEntity,
			List<Long> firstEntityIdList, List<QueryableObjectInterface> mainEntityList)
	{
		Collection<EntityInterface> allEntities = firstEntity.getEntity().getEntityGroup()
				.getEntityCollection();
		for (Long firstEntityId : firstEntityIdList)
		{
			for (EntityInterface tempEntity : allEntities)
			{
				if (Integer.parseInt(tempEntity.getId().toString()) == Integer
						.parseInt(firstEntityId.toString()))
				{
					mainEntityList.add(QueryableObjectUtility.createQueryableObject(tempEntity));
					break;
				}
			}
		}
	}
}
