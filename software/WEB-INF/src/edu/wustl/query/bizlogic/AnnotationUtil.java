/**
 *<p>Title: </p>
 *<p>Description:  </p>
 *<p>Copyright:TODO</p>
 *@author
 *@version 1.0
 */

package edu.wustl.query.bizlogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.hibernate.HibernateException;

import edu.common.dynamicextensions.domain.DomainObjectFactory;
import edu.common.dynamicextensions.domain.Entity;
import edu.common.dynamicextensions.domain.userinterface.Container;
import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.RoleInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintPropertiesInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.common.dynamicextensions.util.global.DEConstants.AssociationDirection;
import edu.common.dynamicextensions.util.global.DEConstants.AssociationType;
import edu.common.dynamicextensions.util.global.DEConstants.Cardinality;
import edu.wustl.cab2b.server.path.PathFinder;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.DAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.annotations.xmi.PathObject;
import edu.wustl.query.util.querysuite.AdvanceQueryDAO;
import edu.wustl.query.util.querysuite.DAOUtil;

/**
 * @author vishvesh_mulay
 *
 */
public class AnnotationUtil
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(AnnotationUtil.class);

	/**
	 * @param staticEntityId - Entity id
	 * @param dynamicEntityId - DE id
	 * @param isEntityFromXmi - boolean
	 * @return Association id - Association id
	 * @throws DynamicExtensionsSystemException - Exception
	 * @throws DynamicExtensionsApplicationException - Exception
	 * @throws DynamicExtensionsSystemException - Exception
	 * @throws BizLogicException - Exception
	 */
	public static synchronized Long addAssociation(Long staticEntityId, Long dynamicEntityId,
			boolean isEntityFromXmi) throws DynamicExtensionsApplicationException,
			DynamicExtensionsSystemException, BizLogicException
	{
		AssociationInterface association = null;
		EntityInterface staticEntity = null;
		EntityInterface dynamicEntity = null;
		Set<PathObject> processedPathList = new HashSet<PathObject>();

		String appName = AdvanceQueryDAO.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		DAO dao = null;

		try
		{
			dao = daoFactory.getDAO();
			dao.openSession(null);
			staticEntity = (EntityInterface) dao.retrieveById(Entity.class.getName(),
					staticEntityId);
			dynamicEntity = (EntityInterface) ((Container) dao.retrieveById(Container.class
					.getName(), dynamicEntityId)).getAbstractEntity();

			//Create source role and target role for the association
			String roleName = staticEntityId.toString().concat("_").concat(
					dynamicEntityId.toString());
			association = getAssociation(roleName, dynamicEntity);

			//Create constraint properties for the created association.
			ConstraintPropertiesInterface constraintProperties = getConstraintProperties(
					staticEntity, dynamicEntity);
			association.setConstraintProperties(constraintProperties);

			staticEntity = getStaticEntity(association, staticEntity);
			//Add the column related to the association to the entity table of the associated entities.
			EntityManager.getInstance().addAssociationColumn(association);
			try
			{
				addQueryPathsForAllAssociatedEntities(dynamicEntity, staticEntity, association,
						staticEntity.getId(), processedPathList);
			}
			catch (SQLException e)
			{
				logger.error(e.getMessage(), e);
			}

			addEntitiesToCache(isEntityFromXmi, dynamicEntity, staticEntity);
		}
		catch (HibernateException e1)
		{
			throw new BizLogicException(ErrorKey.getErrorKey("error.common.hibernate"), e1, e1
					.getMessage());
		}
		catch (DAOException e)
		{
			throw new BizLogicException(e);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		return association.getId();
	}

	/**
	 * @param association - AssociationInterface
	 * @param staticEntity - EntityInterface
	 * @return staticEntity
	 * @throws DynamicExtensionsApplicationException - Exception
	 * @throws DynamicExtensionsSystemException - Exception
	 */
	private static EntityInterface getStaticEntity(AssociationInterface association,
			EntityInterface staticEntity) throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException
	{
		EntityInterface newStaticEntity = staticEntity;
		//Add association to the static entity and save it.
		newStaticEntity.addAssociation(association);

		newStaticEntity = EntityManager.getInstance().persistEntityMetadataForAnnotation(
				staticEntity, true, false, association);
		return newStaticEntity;
	}

	/**
	 * @param roleName - Dynamic Extension Role Name
	 * @param dynamicEntity - Entity
	 * @return Association Interface
	 */
	private static AssociationInterface getAssociation(String roleName,
			EntityInterface dynamicEntity)
	{
		AssociationInterface association = null;
		RoleInterface sourceRole = getRole(AssociationType.CONTAINTMENT, roleName,
				Cardinality.ZERO, Cardinality.ONE);
		RoleInterface targetRole = getRole(AssociationType.CONTAINTMENT, roleName,
				Cardinality.ZERO, Cardinality.MANY);

		//Create association with the created source and target roles.
		try
		{
			association = getAssociation(dynamicEntity, AssociationDirection.SRC_DESTINATION,
					roleName, sourceRole, targetRole);
		}
		catch (DynamicExtensionsSystemException e)
		{
			logger.error(e.getMessage(), e);
		}
		return association;
	}

	/**
	 * @param dynamicEntity
	 * @param staticEntity
	 */

	/*
	 *Commented out by Baljeet
	 */
	/*public static void addCatissueGroup(EntityInterface dynamicEntity,
	 *  EntityGroupInterface entityGroupInterface,List<EntityInterface>
	 *  processedEntityList)
	 {
	 if (processedEntityList.contains(dynamicEntity))
	 {
	 return;
	 }
	 else
	 {
	 processedEntityList.add(dynamicEntity);
	 }

	 //Add the entity group to the dynamic entity and all it's associated entities.
	 if (!checkBaseEntityGroup(dynamicEntity.getEntityGroup()))
	 {
	 dynamicEntity.setEntityGroup(entityGroupInterface);
	 }
	 Collection<AssociationInterface> associationCollection = dynamicEntity
	 .getAssociationCollection();

	 for (AssociationInterface associationInteface : associationCollection)
	 {
	 addCatissueGroup(associationInteface.getTargetEntity(), entityGroupInterface, processedEntityList);
	 //associationInteface.getTargetEntity().addEntityGroupInterface(entityGroupInterface);
	 }
	 }*/

	/**
	 * @param entityGroupColl
	 * @return
	 */

	/*
	 * Commented out By Baljeet
	 */
	/*public static boolean checkBaseEntityGroup(EntityGroupInterface entityGroup)
	 {
	 if (entityGroup.getId().intValue() == Constants.CATISSUE_ENTITY_GROUP)
	 {
	 return true;
	 }

	 return false;
	 }*/

	/**
	 * @param dynamicEntity - Entity
	 * @param staticEntity - Entity
	 * @param association - Association
	 * @param staticEntityId - Entity id
	 * @param processedPathList - Set<PathObject>
	 * @throws BizLogicException - Exception
	 * @throws SQLException - Exception
	 * @throws DAOException - Exception
	 */
	public static void addQueryPathsForAllAssociatedEntities(EntityInterface dynamicEntity,
			EntityInterface staticEntity, AssociationInterface association, Long staticEntityId,
			Set<PathObject> processedPathList) throws BizLogicException, SQLException, DAOException
	{
		if (staticEntity != null)
		{
			PathObject pathObject = new PathObject();
			pathObject.setSourceEntity(staticEntity);
			pathObject.setTargetEntity(dynamicEntity);
			pathObject.setAssociation(association);

			if (processedPathList.contains(pathObject))
			{
				return;
			}
			else
			{
				processedPathList.add(pathObject);
			}
			boolean ispathAdded = isPathAdded(staticEntity.getId(), dynamicEntity.getId(),
					association.getId());
			if (!ispathAdded)
			{
				AnnotationUtil.addPathsForQuery(staticEntity.getId(), dynamicEntity.getId(),
						staticEntityId, association.getId());
			}
		}

		Collection<AssociationInterface> associationCollection = dynamicEntity
				.getAssociationCollection();
		for (AssociationInterface associationInteface : associationCollection)
		{
			addQueryPathsForAllAssociatedEntities(associationInteface.getTargetEntity(),
					dynamicEntity, associationInteface, staticEntityId, processedPathList);

		}
	}

	/**
	 * @param staticEntityId -Entity Id
	 * @param dynamicEntityId - Entity Id
	 * @param associationId - Association id
	 * @return - boolean
	 * @throws SQLException - SQL Exception
	 * @throws DAOException - DAO Exception
	 */
	public static boolean isPathAdded(Long staticEntityId, Long dynamicEntityId, Long associationId)
			throws SQLException, DAOException
	{
		boolean ispathAdded = false;
		ResultSet resultSet = null;
		JDBCDAO jdbcdao = null;
		try
		{
			jdbcdao = DAOUtil.getJDBCDAO(null);
			Long itramodelAssociationId = getIntraModelAssociationId(associationId, jdbcdao);
			String checkForPathQuery = "select path_id from path where FIRST_ENTITY_ID = "
					+ staticEntityId + " and LAST_ENTITY_ID = " + dynamicEntityId
					+ " and INTERMEDIATE_PATH like '" + itramodelAssociationId + "'";
			resultSet = jdbcdao.getQueryResultSet(checkForPathQuery);
			while (resultSet.next())
			{
				ispathAdded = true;
				break;
			}
		}
		finally
		{
			jdbcdao.closeStatement(resultSet);
			resultSet.close();
		}

		return ispathAdded;
	}

	/**
	 * @param associationId - Association Id
	 * @param jdbcdao - JDBCDAO object
	 * @return Intramodel Association id
	 * @throws DAOException - Exception
	 * @throws SQLException - Exception
	 */
	private static Long getIntraModelAssociationId(Long associationId, JDBCDAO jdbcdao)
			throws DAOException, SQLException
	{
		ResultSet resultSet = null;
		String associationIdQuery = "select ASSOCIATION_ID "
				+ "from INTRA_MODEL_ASSOCIATION where DE_ASSOCIATION_ID=" + associationId;
		Long intraModelAssociatinId = null;
		try
		{

			resultSet = jdbcdao.getQueryResultSet(associationIdQuery);
			if (resultSet != null)
			{
				while (resultSet.next())
				{
					intraModelAssociatinId = resultSet.getLong(1);
				}
			}

		}
		finally
		{
			jdbcdao.closeStatement(resultSet);
			resultSet.close();
		}
		return intraModelAssociatinId;
	}

	/**
	 * @param isEntityFromXmi - boolean
	 * @param dynamicEntity - Entity
	 * @param staticEntity - Entity
	 * @throws BizLogicException - Exception
	 */
	public static void addEntitiesToCache(boolean isEntityFromXmi, EntityInterface dynamicEntity,
			EntityInterface staticEntity) throws BizLogicException
	{
		if (!isEntityFromXmi)
		{
			Connection conn = null;
			try
			{
				InitialContext ctx = new InitialContext();
				String DATASOURCE_JNDI_NAME = "java:/catissuecore";
				DataSource dataSource = (DataSource) ctx.lookup(DATASOURCE_JNDI_NAME);
				conn = dataSource.getConnection();
				PathFinder.getInstance().refreshCache(conn, true);
			}
			catch (Exception e)
			{
				logger.debug(e.getMessage(), e);
			}
			finally
			{
				try
				{
					if (conn != null)
					{
						conn.close();
					}
				}
				catch (SQLException e)
				{
					logger.debug(e.getMessage(), e);
				}
			}

		}
	}

	/**
	 * @param staticEntity - Entity
	 * @param dynamicEntity - Entity
	 * @return ConstraintPropertiesInterface
	 */
	private static ConstraintPropertiesInterface getConstraintProperties(
			EntityInterface staticEntity, EntityInterface dynamicEntity)
	{
		DomainObjectFactory factory = DomainObjectFactory.getInstance();
		ConstraintPropertiesInterface constrProp = factory.createConstraintProperties();
		constrProp.setName(dynamicEntity.getTableProperties().getName());
		for (AttributeInterface primaryAttribute : staticEntity.getPrimaryKeyAttributeCollection())
		{
			constrProp.getTgtEntityConstraintKeyProperties().getTgtForiegnKeyColumnProperties()
					.setName(
							"DYEXTN_AS_" + staticEntity.getId().toString() + "_"
									+ dynamicEntity.getId().toString());
			constrProp.getTgtEntityConstraintKeyProperties().setSrcPrimaryKeyAttribute(
					primaryAttribute);
			constrProp.getSrcEntityConstraintKeyPropertiesCollection().clear();
		}
		return constrProp;
	}

	/**
	 * @param targetEntity - Target Entity
	 * @param associationDirection - Association direction
	 * @param assoName -  Association name
	 * @param sourceRole - Source Role
	 * @param targetRole - Target Role
	 * @return AssociationInterface
	 * @throws DynamicExtensionsSystemException - Exception
	 */
	private static AssociationInterface getAssociation(EntityInterface targetEntity,
			AssociationDirection associationDirection, String assoName, RoleInterface sourceRole,
			RoleInterface targetRole) throws DynamicExtensionsSystemException
	{
		AssociationInterface association = DomainObjectFactory.getInstance().createAssociation();
		association.setTargetEntity(targetEntity);
		association.setAssociationDirection(associationDirection);
		association.setName(assoName);
		association.setSourceRole(sourceRole);
		association.setTargetRole(targetRole);
		return association;
	}

	/**
	 * @param associationType associationType
	 * @param name name
	 * @param minCard  minCard
	 * @param maxCard maxCard
	 * @return  RoleInterface
	 */
	private static RoleInterface getRole(AssociationType associationType, String name,
			Cardinality minCard, Cardinality maxCard)
	{
		RoleInterface role = DomainObjectFactory.getInstance().createRole();
		role.setAssociationsType(associationType);
		role.setName(name);
		role.setMinimumCardinality(minCard);
		role.setMaximumCardinality(maxCard);
		return role;
	}

	/**
	 * @param staticEntityId - Entity Id
	 * @param dynamicEntityId - Entity Id
	 * @param hookEntityId - Hook Entity id
	 * @param deAssociationID - Association Id
	 */
	public static void addPathsForQuery(Long staticEntityId, Long dynamicEntityId,
			Long hookEntityId, Long deAssociationID)
	{
		try
		{
			Long maxPathId = getMaxId("path_id", "path");
			maxPathId += 1;
			insertNewPaths(maxPathId, staticEntityId, dynamicEntityId, deAssociationID);
			if (hookEntityId != null && !(staticEntityId.equals(hookEntityId)))
			{
				maxPathId += 1;
				addPathFromStaticEntity(hookEntityId, staticEntityId, deAssociationID);
			}
		}
		catch (DAOException e)
		{
			logger.debug(e.getMessage(), e);
		}
		catch (SQLException e)
		{
			logger.debug(e.getMessage(), e);
		}
	}

	/**
	 * @param hookEntityId - Entity Id
	 * @param previousDynamicEntity - Entity
	 * @param deAssociationId - Association id
	 * @throws SQLException - Exception
	 * @throws DAOException - Exception
	 */
	private static void addPathFromStaticEntity(Long hookEntityId, Long previousDynamicEntity,
			Long deAssociationId) throws SQLException, DAOException
	{
		ResultSet resultSet = null;
		String query = "";
		String appName = AdvanceQueryDAO.getInstance().getAppName();
		IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcdao = null;

		try
		{
			jdbcdao = daoFactory.getJDBCDAO();
			jdbcdao.openSession(null);

			resultSet = jdbcdao
					.getQueryResultSet("select ASSOCIATION_ID from " +
							"INTRA_MODEL_ASSOCIATION where DE_ASSOCIATION_ID="
							+ deAssociationId);
			resultSet.next();
			Long intraModelAssociationId = resultSet.getLong(1);

			query = "select INTERMEDIATE_PATH from path where FIRST_ENTITY_ID=" + hookEntityId
					+ " and LAST_ENTITY_ID=" + previousDynamicEntity;

			resultSet = jdbcdao.getQueryResultSet(query);
			resultSet.next();

			resultSet.next();

			String path = resultSet.getString(1);
			path = path.concat("_").concat(intraModelAssociationId.toString());

			query = "insert into path (PATH_ID, FIRST_ENTITY_ID," +
					"INTERMEDIATE_PATH, LAST_ENTITY_ID) values (maxPathId,"
					+ "hookEntityId,path,dynamicEntityId)";
			resultSet = jdbcdao.getQueryResultSet(query);

			jdbcdao.commit();
		}
		finally
		{

			resultSet.close();
			jdbcdao.closeSession();
		}
	}

	/**
	 * @param maxPathId - Path Id
	 * @param staticEntityId - Entity id
	 * @param dynamicEntityId - Entity id
	 * @param deAssociationID - Association id
	 * @throws SQLException - Exception
	 * @throws DAOException - Exception
	 */
	private static void insertNewPaths(Long maxPathId, Long staticEntityId, Long dynamicEntityId,
			Long deAssociationID) throws DAOException, SQLException
	{
		Long intraModelAssociationId = getMaxId("ASSOCIATION_ID", "ASSOCIATION");
		intraModelAssociationId += 1;
		try
		{
			String associationQuery = "insert into ASSOCIATION " +
					"(ASSOCIATION_ID, ASSOCIATION_TYPE) values ("
					+ intraModelAssociationId
					+ ","
					+ edu.wustl.cab2b.server.path.AssociationType.INTRA_MODEL_ASSOCIATION
							.getValue() + ")";
			String intraModelQuery = "insert into INTRA_MODEL_ASSOCIATION" +
					" (ASSOCIATION_ID, DE_ASSOCIATION_ID) values ("
					+ intraModelAssociationId + "," + deAssociationID + ")";
			String directPathQuery = "insert into PATH" +
					" (PATH_ID, FIRST_ENTITY_ID,INTERMEDIATE_PATH, LAST_ENTITY_ID) values ("
					+ maxPathId
					+ ","
					+ staticEntityId
					+ ",'"
					+ intraModelAssociationId
					+ "',"
					+ dynamicEntityId + ")";

			List<String> list = new ArrayList<String>();
			list.add(associationQuery);
			list.add(intraModelQuery);
			list.add(directPathQuery);

			executeQuery(list);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.debug(e.getMessage(), e);
		}
	}

	/**
	 * @param maxPathId
	 * @param staticEntityId
	 * @param dynamicEntityId
	 * @param intraModelAssociationId
	 * @param conn
	 * @throws SQLException
	 */
	/*	private static void addIndirectPaths(Long maxPathId, Long staticEntityId, Long dynamicEntityId,
	 Long intraModelAssociationId, Connection conn)

	 {
	 ResultSet resultSet = null;
	 PreparedStatement statement = null;
	 String query = "";
	 try
	 {
	 //resultSet = getIndirectPaths(conn, staticEntityId);
	 query = "select FIRST_ENTITY_ID,INTERMEDIATE_PATH from path where LAST_ENTITY_ID="
	 + staticEntityId;
	 statement = conn.prepareStatement(query);
	 resultSet = statement.executeQuery();


	 query = "insert into path (PATH_ID, FIRST_ENTITY_ID,INTERMEDIATE_PATH, LAST_ENTITY_ID) values (?,?,?,?)";
	 statement = conn.prepareStatement(query);
	 while (resultSet.next())
	 {

	 Long firstEntityId = resultSet.getLong(1);
	 String path = resultSet.getString(2);
	 path = path.concat("_").concat(intraModelAssociationId.toString());

	 statement.setLong(1, maxPathId);
	 maxPathId++;
	 statement.setLong(2, firstEntityId);
	 statement.setString(3, path);
	 statement.setLong(4, dynamicEntityId);
	 statement.execute();
	 statement.clearParameters();
	 }
	 }
	 catch (SQLException e)
	 {
	 e.printStackTrace();
	 }
	 finally
	 {
	 try
	 {
	 resultSet.close();
	 statement.close();
	 }
	 catch (SQLException e)
	 {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }

	 }
	 }
	 */
	//	/**
	//	 * @param conn
	//	 * @param staticEntityId
	//	 * @return
	//	 * @throws SQLException
	//	 */
	//	private static ResultSet getIndirectPaths(Connection conn, Long staticEntityId)
	//	{
	//		String query = "select FIRST_ENTITY_ID,INTERMEDIATE_PATH from path where LAST_ENTITY_ID="
	//				+ staticEntityId;
	//		java.sql.PreparedStatement statement = null;
	//		ResultSet resultSet = null;
	//		try
	//		{
	//			statement = conn.prepareStatement(query);
	//			resultSet = statement.executeQuery();
	//		}
	//		catch (SQLException e)
	//		{
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//		finally
	//		{
	//			try
	//			{
	//
	//				statement.close();
	//			}
	//			catch (SQLException e)
	//			{
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//
	//		}
	//		return resultSet;
	//	}
	/**
	 * @param queryList - List<String>
	 */
	private static void executeQuery(List<String> queryList)
	{
		//Statement statement = null;

		JDBCDAO jdbcdao = null;
		try
		{
			//statement = conn.createStatement();
			jdbcdao = DAOUtil.getJDBCDAO(null);
			for (String query : queryList)
			{
				//statement.execute(query);
				jdbcdao.executeUpdate(query);
			}
		}
		//		catch (SQLException e)
		//		{
		//			// TODO Auto-generated catch block
		//			logger.debug(e.getMessage(),e);
		//		}
		catch (DAOException e)
		{
			logger.info(e.getMessage(), e);
		}

		finally
		{

			//			try
			//			{
			//statement.close();
			try
			{
				jdbcdao.commit();
				DAOUtil.closeJDBCDAO(jdbcdao);
			}
			catch (DAOException e)
			{
				// TODO Auto-generated catch block
				logger.info(e.getMessage(), e);
			}
			//		}
			//			catch (SQLException e)
			//			{
			//				// TODO Auto-generated catch block
			//				logger.debug(e.getMessage(),e);
			//			}
		}
	}

	/**
	 * @param columnName -  Column name
	 * @param tableName - Table Name
	 * @return Max Id
	 * @throws SQLException - Exception
	 * @throws DAOException - Exception
	 */
	private static Long getMaxId(String columnName, String tableName) throws SQLException,
			DAOException
	{
		String query = "select max(" + columnName + ") from " + tableName;
		ResultSet resultSet = null;
		JDBCDAO jdbcdao = null;
		Long maxId = null;
		try
		{

			String appName = AdvanceQueryDAO.getInstance().getAppName();
			IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);

			jdbcdao = daoFactory.getJDBCDAO();
			jdbcdao.openSession(null);

			resultSet = jdbcdao.getQueryResultSet(query);
			resultSet.next();
			maxId = resultSet.getLong(1);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(), e);
		}
		finally
		{
			resultSet.close();
			jdbcdao.closeSession();
		}
		return maxId;
	}

	/**
	 * @param entityName - Participant name
	 * @return Entity id
	 * @throws DynamicExtensionsApplicationException - Exception
	 * @throws DynamicExtensionsSystemException - Exception
	 */
	public static Long getEntityId(String entityName) throws DynamicExtensionsSystemException,
			DynamicExtensionsApplicationException
	{
		Long entityId = null;
		if (entityName != null)
		{
			EntityManagerInterface entityManager = EntityManager.getInstance();
			EntityInterface entity;
			entity = entityManager.getEntityByName(entityName);
			if (entity != null)
			{
				entityId = entity.getId();
			}
		}
		return entityId;
	}

}
