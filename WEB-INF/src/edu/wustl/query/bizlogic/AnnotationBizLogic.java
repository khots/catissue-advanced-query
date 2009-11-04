///**
// *<p>Title: </p>
// *<p>Description:  </p>
// *<p>Copyright:TODO</p>
// *@author 
// *@version 1.0
// */
//
//package edu.wustl.query.bizlogic;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//import edu.common.dynamicextensions.bizlogic.BizLogicFactory;
//import edu.common.dynamicextensions.domain.Category;
//import edu.common.dynamicextensions.domain.CategoryEntity;
//import edu.common.dynamicextensions.domain.integration.EntityMap;
//import edu.common.dynamicextensions.domain.integration.EntityMapCondition;
//import edu.common.dynamicextensions.domain.integration.EntityMapRecord;
//import edu.common.dynamicextensions.domain.integration.FormContext;
//import edu.common.dynamicextensions.domaininterface.AssociationInterface;
//import edu.common.dynamicextensions.domaininterface.EntityInterface;
//import edu.common.dynamicextensions.entitymanager.EntityManager;
//import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
//import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
//import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
//import edu.wustl.cab2b.server.cache.EntityCache;
//import edu.wustl.common.bizlogic.DefaultBizLogic;
//import edu.wustl.common.exception.BizLogicException;
//import edu.wustl.common.util.global.Constants;
//import edu.wustl.common.util.global.Status;
//import edu.wustl.common.util.logger.LoggerConfig;
//import edu.wustl.dao.DAO;
//import edu.wustl.dao.HibernateDAO;
//import edu.wustl.dao.exception.DAOException;
//import edu.wustl.query.action.ExecuteQueryAction;
//import edu.wustl.query.util.querysuite.DAOUtil;
//
///**
// * @author sandeep_chinta
// *
// * TODO To change the template for this generated type comment go to
// * Window - Preferences - Java - Code Style - Code Templates
// */
//
//public class AnnotationBizLogic extends DefaultQueryBizLogic
//{
//
//	private static org.apache.log4j.Logger logger = LoggerConfig
//			.getConfiguredLogger(ExecuteQueryAction.class);
//
//	private static final String STATIC_ENTITY_ID = "staticEntityId";
//	private static final String CONTAINER_ID = "containerId";
//
//	/**
//	 * @param staticEntityId - Entity Id
//	 * @return List of all dynamic entities id from a given static entity
//	 * eg: returns all dynamic entity id from a Participant,Specimen etc
//	 */
//	public List getListOfDynamicEntitiesIds(long staticEntityId)
//	{
//		List<EntityMap> dynamicList = new ArrayList<EntityMap>();
//
//		List list = new ArrayList();
//		try
//		{
//			dynamicList = retrieve(EntityMap.class.getName(), this.STATIC_ENTITY_ID, Long
//					.valueOf(staticEntityId));
//			if (dynamicList != null && !dynamicList.isEmpty())
//			{
//				for (EntityMap entityMap : dynamicList)
//				{
//					list.add(entityMap.getContainerId());
//				}
//			}
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		return list;
//	}
//
//	/**
//	 * @param staticEntityId - Entity Id
//	 * @return List of all dynamic entities Objects from a given static entity
//	 * eg: returns all dynamic entity objects from a Participant,Specimen etc
//	 */
//	public List getListOfDynamicEntities(long staticEntityId)
//	{
//		List dynamicList = new ArrayList();
//		try
//		{
//			dynamicList = retrieve(EntityMap.class.getName(), this.STATIC_ENTITY_ID, Long
//					.valueOf(staticEntityId));
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		return dynamicList;
//	}
//
//	/**
//	 * @param staticEntityId - Entity id
//	 * @param typeId - Type Id
//	 * @param staticRecordId - record Id
//	 * @return List of all dynamic entities id from a given static entity based on its protocol linkage
//	 * eg: returns all dynamic entity id from a Participant,Specimen
//	 * etc which is linked to Protocol 1, Protocol 2 etc
//	 */
//	public List getListOfDynamicEntitiesIds(long staticEntityId, long typeId, long staticRecordId)
//	{
//		List dynamicList = new ArrayList();
//
//		String[] selectColumnName = {this.CONTAINER_ID};
//		String[] whereColumnName = {this.STATIC_ENTITY_ID, "typeId", "staticRecordId"};
//		String[] whereColumnCondition = {"=", "=", "="};
//		Object[] whereColumnValue = {Long.valueOf(staticEntityId), Long.valueOf(typeId),
//				Long.valueOf(staticRecordId)};
//		String joinCondition = Constants.AND_JOIN_CONDITION;
//
//		try
//		{
//			dynamicList = retrieve(EntityMap.class.getName(), selectColumnName, whereColumnName,
//					whereColumnCondition, whereColumnValue, joinCondition);
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		return dynamicList;
//	}
//
//	/**
//	 * @param entityRecord
//	 * Updates the Entity Record object in database
//	 * @throws BizLogicException - Exception
//	 */
//	public void updateEntityRecord(EntityMapRecord entityRecord) throws BizLogicException
//	{
//		HibernateDAO hibernateDao = null;
//
//		try
//		{
//			hibernateDao = DAOUtil.getHibernateDAO(null);
//			update(hibernateDao, entityRecord);
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//		catch (DAOException e)
//		{
//			throw new BizLogicException(e);
//		}
//		finally
//		{
//			if (hibernateDao != null)
//			{
//				try
//				{
//					DAOUtil.closeHibernateDAO(hibernateDao);
//				}
//				catch (DAOException e)
//				{
//					logger.error(e.getMessage(), e);
//				}
//			}
//
//		}
//	}
//
//	/**
//	 * @param entityRecord
//	 * Inserts a new EntityRecord record in Database
//	 * @throws DynamicExtensionsSystemException - Exception
//	 * @throws BizLogicException - Exception
//	 */
//	public void insertEntityRecord(EntityMapRecord entityRecord) throws BizLogicException,
//			DynamicExtensionsSystemException
//	{
//		HibernateDAO hibernateDao = null;
//		try
//		{
//			hibernateDao = DAOUtil.getHibernateDAO(null);
//			insert(entityRecord, hibernateDao);
//			Long entityMapId = entityRecord.getFormContext().getEntityMap().getId();
//			Long staticEntityRecordId = entityRecord.getStaticEntityRecordId();
//			Long dynExtRecordId = entityRecord.getDynamicEntityRecordId();
//			associateRecords(entityMapId, Long.valueOf(staticEntityRecordId), Long
//					.valueOf(dynExtRecordId));
//		}
//		catch (DAOException e)
//		{
//
//			logger.debug(e.getMessage(), e);
//			throw new BizLogicException(e);
//		}
//		finally
//		{
//			if (hibernateDao != null)
//			{
//				try
//				{
//					DAOUtil.closeHibernateDAO(hibernateDao);
//				}
//				catch (DAOException e)
//				{
//					logger.error(e.getMessage(), e);
//				}
//			}
//
//		}
//	}
//
//	/**
//	 * @param entityMapId - Id for Entity Map
//	 * @param staticEntityRecordId - Entity Record Id
//	 * @param dynamicEntityRecordId - Entity Record Id
//	 * @throws DynamicExtensionsSystemException - Exception
//	 * @throws BizLogicException - Exception
//	 */
//	private void associateRecords(Long entityMapId, Long staticEntityRecordId,
//			Long dynamicEntityRecordId) throws BizLogicException, DynamicExtensionsSystemException
//	{
//		DefaultBizLogic defaultBizLogic = BizLogicFactory.getDefaultBizLogic();
//		Object object = defaultBizLogic.retrieve(EntityMap.class.getName(), entityMapId);
//		EntityManagerInterface entityManager = EntityManager.getInstance();
//		if (object != null)
//		{
//			EntityMap entityMap = (EntityMap) object;
//			Long dynamicEntityId = entityManager.getEntityIdByContainerId(entityMap
//					.getContainerId());
//			Long rootContainerId = entityMap.getContainerId();
//			Long containerId = entityManager.isCategory(rootContainerId);
//			dynamicEntityId = getDynamicEntityId(defaultBizLogic, containerId, dynamicEntityId);
//			//root category entity id .take that entity from cache
//
//			EntityInterface dynamicEntity = EntityCache.getInstance()
//					.getEntityById(dynamicEntityId);
//			EntityInterface staticEntity = EntityCache.getInstance().getEntityById(
//					entityMap.getStaticEntityId());
//
//			Collection<AssociationInterface> associationCollection = staticEntity
//					.getAssociationCollection();
//			do
//			{
//				AssociationInterface associationInterface = null;
//				for (AssociationInterface association : associationCollection)
//				{
//					if (association.getTargetEntity().equals(dynamicEntity))
//					{
//						associationInterface = association;
//						break;
//					}
//				}
//				entityManager.associateEntityRecords(associationInterface, staticEntityRecordId,
//						dynamicEntityRecordId);
//				dynamicEntity = dynamicEntity.getParentEntity();
//			}
//			while (dynamicEntity != null);
//		}
//	}
//
//	/**
//	 * @param defaultBizLogic - DefaultBizLogic
//	 * @param dynamicEntityId - Long
//	 * @param containerId - Long
//	 * @return dynamicEntityId
//	 */
//	private Long getDynamicEntityId(DefaultBizLogic defaultBizLogic, Long containerId,
//			Long dynamicEntityId)
//	{
//		Long value = dynamicEntityId;
//		if (containerId != null)
//		{
//
//			List<CategoryEntity> entityList;
//			try
//			{
//				entityList = defaultBizLogic.retrieve(CategoryEntity.class.getName(), "id", value);
//				if (entityList != null && !entityList.isEmpty())
//				{
//					value = entityList.get(0).getEntity().getId();
//				}
//			}
//			catch (BizLogicException e)
//			{
//				logger.info(e.getMessage(), e);
//			}
//		}
//		return value;
//	}
//
//	/**
//	 * @param entityMap
//	 * Updates the Entity Map object in database
//	 * @throws BizLogicException -Exception
//	 */
//	public void updateEntityMap(EntityMap entityMap) throws BizLogicException
//	{
//		HibernateDAO hibernateDao = null;
//		try
//		{
//			hibernateDao = DAOUtil.getHibernateDAO(null);
//			update(hibernateDao, entityMap);
//			//update(entityMap,null, Constants.HIBERNATE_DAO,null);
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//		catch (DAOException e)
//		{
//			logger.info(e.getMessage(), e);
//			throw new BizLogicException(e);
//
//		}
//		finally
//		{
//			if (hibernateDao != null)
//			{
//				try
//				{
//					DAOUtil.closeHibernateDAO(hibernateDao);
//				}
//				catch (DAOException e)
//				{
//					logger.error(e.getMessage(), e);
//				}
//			}
//
//		}
//	}
//
//	/**
//	 * @param entityMap - Entity map
//	 * Inserts a new EntityMap record in Database
//	 * @throws BizLogicException - Exception
//	 */
//	public void insertEntityMap(EntityMap entityMap) throws BizLogicException
//	{
//
//		HibernateDAO hibernateDao = null;
//		try
//		{
//			hibernateDao = DAOUtil.getHibernateDAO(null);
//			Long staticEntityId = entityMap.getStaticEntityId();
//			Long dynamicEntityId = entityMap.getContainerId();
//			Long deAssociationID = AnnotationUtil.addAssociation(staticEntityId, dynamicEntityId,
//					false);
//			if (deAssociationID != null)
//			{
//				insert(entityMap, hibernateDao);
//			}
//		}
//		catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//		finally
//		{
//			if (hibernateDao != null)
//			{
//				try
//				{
//					DAOUtil.closeHibernateDAO(hibernateDao);
//				}
//				catch (DAOException e)
//				{
//					logger.error(e.getMessage(), e);
//				}
//			}
//
//		}
//
//	}
//
//	/**
//	 * @param dynamicEntityContainerId - Container Id
//	 * @return List of Static Entity Id from a given Dynamic Entity Id
//	 */
//	public List getListOfStaticEntitiesIds(long dynamicEntityContainerId)
//	{
//		List dynamicList = new ArrayList();
//
//		String[] selectColumnName = {STATIC_ENTITY_ID};
//		String[] whereColumnName = {CONTAINER_ID};
//		String[] whereColumnCondition = {"="};
//		Object[] whereColumnValue = {Long.valueOf(dynamicEntityContainerId)};
//		String joinCondition = null;
//
//		try
//		{
//			dynamicList = retrieve(EntityMap.class.getName(), selectColumnName, whereColumnName,
//					whereColumnCondition, whereColumnValue, joinCondition);
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		return dynamicList;
//	}
//
//	/**
//	 * @param dynamicEntityContainerId
//	 * @return List of Static Entity Objects from a given Dynamic Entity Id
//	 */
//	public List getListOfStaticEntities(long dynamicEntityContainerId)
//	{
//		List dynamicList = new ArrayList();
//
//		try
//		{
//			dynamicList = retrieve(EntityMap.class.getName(), CONTAINER_ID, Long
//					.valueOf(dynamicEntityContainerId));
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//
//		}
//
//		return dynamicList;
//	}
//
//	/**
//	 * @param entityMapId
//	 * @return EntityMap object for its given id
//	 */
//	public EntityMap getEntityMap(long entityMapId)
//	{
//		EntityMap map = null;
//
//		try
//		{
//			map = (EntityMap) retrieve(EntityMap.class.getName(), entityMapId);
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		return map;
//	}
//
//	/**
//	 * @param entityMapids - Entity Map Id
//	 * @param staticRecordId - record id
//	 * @return EntityMapRecordList
//	 */
//	public List getEntityMapRecordList(List entityMapids, long staticRecordId)
//	{
//		List dynamicList = new ArrayList();
//
//		String[] selectColumnName = null;
//		String[] whereColumnName = {"staticEntityRecordId", "formContext.entityMap.id"};
//		String[] whereColumnCondition = {"=", "="};
//		String joinCondition = Constants.AND_JOIN_CONDITION;
//
//		Iterator iter = entityMapids.iterator();
//		while (iter.hasNext())
//		{
//			Long entityMapId = (Long) iter.next();
//			if (entityMapId != null)
//			{
//				Object[] whereColumnValue = {Long.valueOf(staticRecordId), entityMapId};
//				try
//				{
//					List list = retrieve(EntityMapRecord.class.getName(), selectColumnName,
//							whereColumnName, whereColumnCondition, whereColumnValue, joinCondition);
//					if (list != null)
//					{
//						dynamicList.addAll(list);
//					}
//				}
//				catch (BizLogicException e)
//				{
//					// TODO Auto-generated catch block
//					logger.debug(e.getMessage(), e);
//				}
//			}
//
//		}
//
//		return dynamicList;
//	}
//
//	/**
//	 * @param entityMapId - Entity Map Id
//	 * @param dynamicEntityRecordId - Record Id
//	 */
//	public void deleteEntityMapRecord(long entityMapId, long dynamicEntityRecordId)
//	{
//		List dynamicList = new ArrayList();
//
//		String[] selectColumnName = null;
//		String[] whereColumnName = {"formContext.entityMap.id", "dynamicEntityRecordId"};
//		String[] whereColumnCondition = {"=", "="};
//		Object[] whereColumnValue = {Long.valueOf(entityMapId), Long.valueOf(dynamicEntityRecordId)};
//		String joinCondition = Constants.AND_JOIN_CONDITION;
//
//		try
//		{
//
//			dynamicList = retrieve(EntityMapRecord.class.getName(), selectColumnName,
//					whereColumnName, whereColumnCondition, whereColumnValue, joinCondition);
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		if (dynamicList != null && !dynamicList.isEmpty())
//		{
//			deleteEntityMapRecord(dynamicList);
//		}
//	}
//
//	/**
//	 * @param dynamicList - List of Entity map Record
//	 */
//	private void deleteEntityMapRecord(List dynamicList)
//	{
//		HibernateDAO hibernateDao = null;
//		try
//		{
//			hibernateDao = DAOUtil.getHibernateDAO(null);
//			EntityMapRecord entityRecord = (EntityMapRecord) dynamicList.get(0);
//			entityRecord.setLinkStatus(Status.ACTIVITY_STATUS_DISABLED.toString());
//			update(hibernateDao, entityRecord);
//		}
//		catch (DAOException e1)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e1.getMessage(), e1);
//		}
//		catch (BizLogicException e1)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e1.getMessage(), e1);
//		}
//		finally
//		{
//			checkHibernateCondition(hibernateDao);
//
//		}
//
//	}
//
//	/**
//	 * @param hibernateDao - HibernateDAO object
//	 */
//	private void checkHibernateCondition(HibernateDAO hibernateDao)
//	{
//		if (hibernateDao != null)
//		{
//			try
//			{
//				DAOUtil.closeHibernateDAO(hibernateDao);
//			}
//			catch (DAOException e)
//			{
//				// TODO Auto-generated catch block
//				logger.debug(e.getMessage(), e);
//
//			}
//		}
//
//	}
//
//	/**
//	 * @param containerId - Container Id
//	 * @param recordIdList - Record List id
//	 * @throws BizLogicException - exception
//	 */
//	public void deleteAnnotationRecords(Long containerId, List<Long> recordIdList)
//			throws BizLogicException
//	{
//		EntityManagerInterface entityManagerInterface = EntityManager.getInstance();
//		try
//		{
//			entityManagerInterface.deleteRecords(containerId, recordIdList);
//		}
//		catch (Exception e)
//		{
//			logger.debug(e.getMessage(), e);
//			/*   throw new BizLogicException(ApplicationProperties
//			 .getValue("app.annotatations.errors.deleteRecord"), e);*/
//		}
//	}
//
//	/**
//	 * Deletes an object from the database.
//	 * @param obj The object to be deleted.
//	 * @throws BizLogicException - exception
//	 */
//	@Override
//	protected void delete(Object obj, DAO dao) throws BizLogicException
//	{
//		try
//		{
//			dao.delete(obj);
//		}
//		catch (DAOException e)
//		{
//			throw new BizLogicException(e);
//
//		}
//	}
//
//	/**
//	 * @param dynEntitiesList - List<Long>
//	 * @param cpIdList - List<Long>
//	 * @return AnnotationIdsBasedOnCondition
//	 */
//	public List getAnnotationIdsBasedOnCondition(List dynEntitiesList, List cpIdList)
//	{
//		List<Long> dynEntitiesIdList = new ArrayList<Long>();
//		if (dynEntitiesList != null && !dynEntitiesList.isEmpty())
//		{
//			Iterator dynEntitiesIterator = dynEntitiesList.iterator();
//			while (dynEntitiesIterator.hasNext())
//			{
//				EntityMap entityMap = (EntityMap) dynEntitiesIterator.next();
//				Iterator formIterator = entityMap.getFormContextCollection().iterator();
//				while (formIterator.hasNext())
//				{
//					FormContext formContext = (FormContext) formIterator.next();
//					Integer noOfEntries = formContext.getNoOfEntries();
//					String studyFormLabel = formContext.getStudyFormLabel();
//					dynEntitiesIdList.add(getContainerId(entityMap, noOfEntries, studyFormLabel));
//				}
//			}
//		}
//		return dynEntitiesIdList;
//	}
//
//	/**
//	 * @param entityMap - EntityMap
//	 * @param noOfEntries - Integer
//	 * @param studyFormLabel - String
//	 * @return Container Id
//	 */
//	private Long getContainerId(EntityMap entityMap, Integer noOfEntries, String studyFormLabel)
//	{
//		Long containerId = null;
//		if ((noOfEntries == null) && (studyFormLabel == null || ("").equals(studyFormLabel)))
//		{
//			containerId = entityMap.getContainerId();
//			//			if (formContext.getEntityMapConditionCollection() != null
//			//			&& !formContext.getEntityMapConditionCollection().isEmpty())
//			//			{
//			//
//			//				/*
//			//				 * Commented  out By Baljeet
//			//				 */
//			//				/* boolean check = checkStaticRecId(formContext
//			//				 .getEntityMapConditionCollection(), cpIdList);
//			//				 if (check)
//			//				 dynEntitiesIdList.add(entityMap.getContainerId());
//			//				 */
//			//			}
//			//			else
//			//			{
//			//				dynEntitiesIdList.add(entityMap.getContainerId());
//			//			}
//		}
//		return containerId;
//	}
//
////	/**
////	 * @param entityMapConditionCollection
////	 * @param cpIdList
////	 * @return
////	 * @throws CacheException 
////	 */
//
//	/*
//	 * Commented out By Baljeet
//	 */
//	/*  private boolean checkStaticRecId(Collection entityMapConditionCollection,
//	 List cpIdList) 
//	 {/*
//	 Iterator entityMapCondIterator = entityMapConditionCollection
//	 .iterator();
//	 try
//	 {
//	 CatissueCoreCacheManager cache = CatissueCoreCacheManager.getInstance();    
//	 if (cpIdList != null && !cpIdList.isEmpty())
//	 while (entityMapCondIterator.hasNext())
//	 {
//	 EntityMapCondition entityMapCond = (EntityMapCondition) entityMapCondIterator
//	 .next();                
//	 if (entityMapCond.getTypeId().toString().equals(cache.getObjectFromCache(AnnotationConstants.COLLECTION_PROTOCOL_ENTITY_ID).toString()) && cpIdList.contains(entityMapCond.getStaticRecordId()))
//	 return true;
//	 }
//	 }
//	 catch(Exception e){}
//	 return false;
//	 }*/
//
//	/**
//	 * @param containerId - Container Id
//	 * @return EntityMap object for its given id
//	 */
//	public List getEntityMapOnContainer(long containerId)
//	{
//		List dynamicList = new ArrayList();
//
//		try
//		{
//			dynamicList = retrieve(EntityMap.class.getName(), this.CONTAINER_ID, Long
//					.valueOf(containerId));
//		}
//		catch (BizLogicException e)
//		{
//			// TODO Auto-generated catch block
//			logger.debug(e.getMessage(), e);
//		}
//
//		return dynamicList;
//	}
//
//	/**
//	 * @param entityMapCondition - Entity map Condition
//	 */
//	public void insertEntityMapCondition(EntityMapCondition entityMapCondition)
//	{
//		HibernateDAO hibernateDao = null;
//		try
//		{
//			hibernateDao = DAOUtil.getHibernateDAO(null);
//			insert(entityMapCondition, hibernateDao);
//		}
//		catch (BizLogicException e)
//		{
//			logger.debug(e.getMessage(), e);
//		}
//		catch (DAOException e)
//		{
//			logger.debug(e.getMessage(), e);
//
//		}
//		finally
//		{
//			if (hibernateDao != null)
//			{
//				try
//				{
//					DAOUtil.closeHibernateDAO(hibernateDao);
//				}
//				catch (DAOException e)
//				{
//					logger.debug(e.getMessage(), e);
//
//				}
//			}
//
//		}
//
//	}
//
//	/**
//	 * @param deContainerId - Container id
//	 * @return EntityMapsForContainer
//	 * @throws BizLogicException - exception
//	 */
//	//Function added by Preeti :  to get all entitymap entries for a dynamic entity container
//	public Collection getEntityMapsForContainer(Long deContainerId) throws BizLogicException
//	{
//		return retrieve(EntityMap.class.getName(), CONTAINER_ID, deContainerId);
//	}
//
//	/**
//	 * @param categoryEntityId - Entity Id for Category
//	 * @return CategoryTitle
//	 * @throws BizLogicException - Exception
//	 * @throws DynamicExtensionsSystemException - Exception
//	 * @throws DynamicExtensionsApplicationException - Exception
//	 */
//	public String getCategoryTitle(Long categoryEntityId) throws BizLogicException,
//			DynamicExtensionsSystemException, DynamicExtensionsApplicationException
//	{
//		String categoryTitle = null;
//		//Select from dyextn_category table where the
//		//category_entity_id is the given id ,there is only
//		//one entry for it .Thats the name of category
//		List idList = retrieve(Category.class.getName(), "rootCategoryElement.id", categoryEntityId);
//		if (!idList.isEmpty())
//		{
//			categoryTitle = ((Category) idList.get(0)).getName();
//		}
//		return categoryTitle;
//	}
//}
