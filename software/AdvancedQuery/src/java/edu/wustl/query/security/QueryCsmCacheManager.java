package edu.wustl.query.security;

import edu.common.dynamicextensions.domain.DomainObjectFactory;
import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AssociationMetadataInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ColumnPropertiesInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintKeyPropertiesInterface;
import edu.common.dynamicextensions.domaininterface.databaseproperties.ConstraintPropertiesInterface;
import edu.wustl.cab2b.common.beans.MatchedClass;
import edu.wustl.cab2b.common.beans.MatchedClassEntry;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.beans.QueryResultObjectData;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.AbstractClient;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.query.generator.ColumnValueBean;
import edu.wustl.dao.util.HibernateMetaData;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.util.global.QueryCSMValidator;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.privilege.IValidator;
import edu.wustl.security.privilege.PrivilegeCache;
import edu.wustl.security.privilege.PrivilegeManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

























public class QueryCsmCacheManager
{
  private JDBCDAO jdbcDAO;
  private IValidator validator;
  
  public QueryCsmCacheManager(JDBCDAO jdbcDAO)
  {
    this.jdbcDAO = jdbcDAO;
    this.validator = getValidatorInstance();
  }
  

  private IValidator getValidatorInstance()
  {
    IValidator validatorInstance;
    
    if ((Variables.validatorClassname == null) || (Variables.validatorClassname.length() == 0))
    {
      validatorInstance = null;
    }
    else
    {
      validatorInstance = (IValidator)Utility.getObject(Variables.validatorClassname);
    }
    return validatorInstance;
  }
  




  public QueryCsmCache getNewCsmCacheObject()
  {
    return new QueryCsmCache();
  }
  










  public void filterRow(SessionDataBean sessionDataBean, Map<String, QueryResultObjectDataBean> queryResultObjectDataMap, List aList, QueryCsmCache cache)
    throws SMException
  {
    Boolean isAuthorisedUser = Boolean.valueOf(true);
    Boolean hasPrivilegeOnID = Boolean.valueOf(true);
    if (queryResultObjectDataMap != null)
    {
      Set keySet = queryResultObjectDataMap.keySet();
      for (Object key : keySet)
      {
        QueryResultObjectDataBean queryResultObjectDataBean = (QueryResultObjectDataBean)queryResultObjectDataMap.get(key);
        
        String entityName = queryResultObjectDataBean.getCsmEntityName();
        int mainEntityId = -1;
        long finalMainEntityId = mainEntityId;
        mainEntityId = getMainEntityIdFromBean(aList, queryResultObjectDataBean, mainEntityId);
        

        if ((mainEntityId != -1) && (queryResultObjectDataBean.isReadDeniedObject()))
        {
          int mainProtocolIdIndex = queryResultObjectDataBean.getMainProtocolIdIndex();
          List<List<String>> cpIdsList = null;
          if (mainProtocolIdIndex != -1)
          {
            cpIdsList = getCSIdFromDataList(aList, finalMainEntityId, mainProtocolIdIndex);
          }
          else
          {
            finalMainEntityId = getFinalMainEntityId(queryResultObjectDataBean, mainEntityId, finalMainEntityId);
            
            cpIdsList = getCpIdsListForGivenEntityId(entityName, finalMainEntityId);
          }
        }
        



        removeUnauthorizedData(aList, isAuthorisedUser, hasPrivilegeOnID, queryResultObjectDataBean);
      }
    }
  }
  









  private int getMainEntityIdFromBean(List aList, QueryResultObjectDataBean queryResultObjectDataBean, int mainEntityId)
  {
    int entityId = mainEntityId;
    if (queryResultObjectDataBean.getMainEntityIdentifierColumnId() != -1)
    {
      String tempEntityId = (String)aList.get(queryResultObjectDataBean.getMainEntityIdentifierColumnId());
      
      if ((tempEntityId != null) && (tempEntityId.length() != 0))
      {
        entityId = Integer.parseInt((String)aList.get(queryResultObjectDataBean.getMainEntityIdentifierColumnId()));
      }
    }
    
    return entityId;
  }
  



  public Boolean checkHasPrivilegeOnId(SessionDataBean sessionDataBean, QueryCsmCache cache, List<List<String>> cpIdsList)
    throws SMException
  {
    Boolean hasPrivilegeOnID;
    

    if (cpIdsList.isEmpty())
    {
      hasPrivilegeOnID = Boolean.valueOf(checkPermissionOnGlobalParticipant(sessionDataBean));
    }
    else
    {
      List<Boolean> iPrivilegeList = new ArrayList();
      for (int i = 0; i < cpIdsList.size(); i++)
      {
        List<String> cpIdList = (List)cpIdsList.get(i);
        updatePrivilegeList(sessionDataBean, cache, null, iPrivilegeList, cpIdList);
      }
      hasPrivilegeOnID = isAuthorizedUser(iPrivilegeList, false);
    }
    return hasPrivilegeOnID;
  }
  







  private long getFinalMainEntityId(QueryResultObjectDataBean queryResultObjectDataBean, int mainEntityId, long finalMainEntityId)
  {
    long tempMainEntityId = finalMainEntityId;
    if (((queryResultObjectDataBean.getMainEntity() == null) || (queryResultObjectDataBean.getEntity().equals(queryResultObjectDataBean.getMainEntity()))) || 
    




      (tempMainEntityId == -1L))
    {
      tempMainEntityId = mainEntityId;
    }
    return tempMainEntityId;
  }
  






  public List<Object[]> getMainEntityIds(QueryResultObjectDataBean queryResultObjectDataBean, Set<String> entityIds)
  {
    List mainEntityIds = new ArrayList();
    FileInputStream inputStream = null;
    List<Object[]> allMainObjectIds= null;
    HibernateDAO hibernateDAO = null;
    try
    {
      String appName = CommonServiceLocator.getInstance().getAppHome();
      File file = new File(appName + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator") + "classes" + System.getProperty("file.separator") + "mainProtocolObjectQueries.properties");
      
      
      if (file.exists())
      {
        inputStream = new FileInputStream(file);
        Properties mainProtObjFile = new Properties();
        mainProtObjFile.load(inputStream);
        String sql = mainProtObjFile.getProperty(queryResultObjectDataBean.getEntity().getName());
        hibernateDAO = DAOUtil.getHibernateDAO(null);
        if (sql == null)
        {
          mainEntityIds = getMainEntityIdForDE(queryResultObjectDataBean, entityIds, hibernateDAO, mainProtObjFile);
        }
        else
        {
          sql = addInClause(sql, entityIds);
          


          allMainObjectIds = hibernateDAO.executeQuery(sql);
          if (allMainObjectIds.isEmpty()) {}
        } }



    }
    catch (DAOException e)
    {

      e.printStackTrace();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        DAOUtil.closeHibernateDAO(hibernateDAO);
        if (inputStream != null)
        {
          inputStream.close();
        }
      }
      catch (DAOException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return allMainObjectIds;
  }
  












  private List<Object[]> getMainEntityIdForDE(QueryResultObjectDataBean queryResultObjectDataBean, Set<String> entityIds, HibernateDAO hibernateDAO, Properties mainProtObjFile)
    throws DAOException, SQLException
  {
    List<Object[]> mainEntityIds = new ArrayList();
    EntityInterface originalEntity = queryResultObjectDataBean.getEntity();
    EntityCache entityCache = EntityCacheFactory.getInstance();
    List<String> entityNames = getHookEntities(mainProtObjFile);
    EntityInterface associatedHookEntity = null;
    for (String hookEntityName : entityNames)
    {
      EntityInterface hookEntity = DomainObjectFactory.getInstance().createEntity();
      hookEntity.setName(hookEntityName.substring(hookEntityName.lastIndexOf('.') + 1, hookEntityName.length()));
      
      hookEntity.setDescription(null);
      hookEntity = populateHookEntity(entityCache, hookEntity, hookEntityName);
      AssociationMetadataInterface association = hookEntity.getAssociation(originalEntity);
      if (association != null)
      {
        associatedHookEntity = hookEntity;
        break;
      } }
    Map<String, String> hookEntityVsEnity;
    if (associatedHookEntity != null)
    {
      List<List> hookEntityIds = getAssociationDetails(entityIds, originalEntity, associatedHookEntity, queryResultObjectDataBean.getMainEntity());
      
      String sql = mainProtObjFile.getProperty(associatedHookEntity.getName());
      if (sql == null)
      {
        for (List idList : hookEntityIds) {
          mainEntityIds.add(new Object[] { idList.get(0), idList.get(1) });
        }
      }
      else
      {
        hookEntityVsEnity = new HashMap();
        for (List idList : hookEntityIds) {
          hookEntityVsEnity.put(idList.get(1).toString(), idList.get(0).toString());
        }
        
        sql = addInClause(sql, hookEntityVsEnity.keySet());
        mainEntityIds = hibernateDAO.executeQuery(sql);
        
        for (Object[] list : mainEntityIds) {
          list[0] = hookEntityVsEnity.get(list[0].toString());
        }
      }
    }
    return mainEntityIds;
  }
  





  private List<String> getHookEntities(Properties mainProtObjFile)
  {
    List<String> hookEntityList = new ArrayList();
    String hookEntityString = mainProtObjFile.getProperty("hookEntity");
    StringTokenizer tokens = new StringTokenizer(hookEntityString, ",");
    while (tokens.hasMoreTokens())
    {
      hookEntityList.add(tokens.nextToken());
    }
    
    return hookEntityList;
  }
  







  private EntityInterface populateHookEntity(EntityCache entityCache, EntityInterface hookEntity, String hookEntityName)
  {
    EntityInterface tempHookEntity = hookEntity;
    Collection<EntityInterface> entityCollection = new HashSet();
    entityCollection.add(tempHookEntity);
    MatchedClass matchedClass = entityCache.getEntityOnEntityParameters(entityCollection);
    MatchedClass resultMatchClass = new MatchedClass();
    for (MatchedClassEntry matchedClassEntry : matchedClass.getMatchedClassEntries())
    {
      resultMatchClass.addMatchedClassEntry(matchedClassEntry);
    }
    resultMatchClass.setEntityCollection(resultMatchClass.getSortedEntityCollection());
    for (EntityInterface entity : resultMatchClass.getEntityCollection())
    {
      if (entity.getName().equals(hookEntityName))
      {
        tempHookEntity = entity;
        break;
      }
    }
    return tempHookEntity;
  }
  










  private List<List> getAssociationDetails(Set<String> entityIds, EntityInterface originalEntity, EntityInterface hookEntity, EntityInterface csmEntity)
    throws DAOException, SQLException
  {
    AssociationInterface association = getActualAssociation(originalEntity, hookEntity);
    if (association == null)
    {
      association = getActualAssociation(originalEntity, csmEntity);
    }
    if (association == null)
    {
      association = getMainContainerAssociation(originalEntity, hookEntity);
    }
    String tableName = association.getConstraintProperties().getName();
    Collection<ConstraintKeyPropertiesInterface> tgrKeyColl = association.getConstraintProperties().getTgtEntityConstraintKeyPropertiesCollection();
    
    String columnName = null;
    Iterator i$ = tgrKeyColl.iterator(); if (i$.hasNext()) { ConstraintKeyPropertiesInterface tgtConstraintKey = (ConstraintKeyPropertiesInterface)i$.next();
      
      ColumnPropertiesInterface colProperties = tgtConstraintKey.getTgtForiegnKeyColumnProperties();
      
      columnName = colProperties.getName();
    }
    
    String sql = "SELECT IDENTIFIER, " + columnName + " FROM " + tableName + " WHERE IDENTIFIER in (" + getInClause(entityIds) + ") ";
    






    return this.jdbcDAO.executeQuery(sql);
  }
  








  private Long getHookEntity(ResultSet resultSet, Long foreignKey)
    throws SQLException, DAOException
  {
    Long hookEntityId = null;
    if (resultSet != null)
    {
      if (resultSet.next())
      {
        foreignKey = Long.valueOf(resultSet.getLong(1));
      }
      this.jdbcDAO.closeStatement(resultSet);
    }
    if (foreignKey != null)
    {
      hookEntityId = foreignKey;
    }
    return hookEntityId;
  }
  











  private AssociationInterface getMainContainerAssociation(EntityInterface originalEntity, EntityInterface hookEntity)
    throws DAOException, SQLException
  {
    String pathSql = "SELECT INTERMEDIATE_PATH FROM PATH WHERE FIRST_ENTITY_ID = ?  AND LAST_ENTITY_ID = ? ";
    

    LinkedList<ColumnValueBean> columnValueBean = new LinkedList();
    ColumnValueBean bean = new ColumnValueBean("FIRST_ENTITY_ID", hookEntity.getId());
    columnValueBean.add(bean);
    bean = new ColumnValueBean("LAST_ENTITY_ID", originalEntity.getId());
    columnValueBean.add(bean);
    ResultSet resultSet1 = this.jdbcDAO.getResultSet(pathSql, columnValueBean, null);
    String intermediatePath = "";
    if ((resultSet1 != null) && (resultSet1.next()))
    {
      intermediatePath = resultSet1.getString(1);
      intermediatePath = intermediatePath.substring(0, intermediatePath.indexOf('_'));
    }
    this.jdbcDAO.closeStatement(resultSet1);
    pathSql = "SELECT DE_ASSOCIATION_ID FROM INTRA_MODEL_ASSOCIATION WHERE ASSOCIATION_ID = ?";
    columnValueBean = new LinkedList();
    bean = new ColumnValueBean("intermediatePath", intermediatePath);
    columnValueBean.add(bean);
    resultSet1 = this.jdbcDAO.getResultSet(pathSql, columnValueBean, null);
    Long associationId = null;
    if ((resultSet1 != null) && (resultSet1.next()))
    {
      associationId = Long.valueOf(resultSet1.getLong(1));
    }
    this.jdbcDAO.closeStatement(resultSet1);
    AssociationInterface association = hookEntity.getAssociationByIdentifier(Long.valueOf(associationId.longValue()));
    return association;
  }
  






  private AssociationInterface getActualAssociation(EntityInterface originalEntity, EntityInterface hookEntity)
  {
    AssociationInterface association = null;
    Collection<AssociationInterface> associationColl = hookEntity.getAssociationCollection();
    
    for (AssociationInterface assoc : associationColl)
    {
      if (assoc.getTargetEntity().getId().equals(originalEntity.getId()))
      {
        association = assoc;
        break;
      }
    }
    return association;
  }
  








  public void removeUnauthorizedData(List aList, Boolean isAuthorisedUser, Boolean hasPrivilegeOnID, QueryResultObjectDataBean queryResultObjectDataBean)
  {
    if (isAuthorisedUser.booleanValue())
    {


      if (!hasPrivilegeOnID.booleanValue())
      {
        removeUnauthorizedFieldsData(aList, true, queryResultObjectDataBean);
      }
      
    }
    else {
      removeUnauthorizedFieldsData(aList, false, queryResultObjectDataBean);
    }
  }
  













  public boolean hasPrivilegeOnIdentifiedData(SessionDataBean sessionDataBean, Map<String, QueryResultObjectDataBean> queryResultObjectDataMap, List aList, QueryCsmCache cache)
    throws SMException
  {
    Boolean hasPrivilegeOnIdentifiedData = Boolean.valueOf(true);
    if (queryResultObjectDataMap != null)
    {
      Set keySet = queryResultObjectDataMap.keySet();
      for (Object key : keySet)
      {
        QueryResultObjectDataBean queryResultObjectDataBean = (QueryResultObjectDataBean)queryResultObjectDataMap.get(key);
        
        String entityName = queryResultObjectDataBean.getCsmEntityName();
        int mainEntityId = -1;
        mainEntityId = getMainEntityIdFromBean(aList, queryResultObjectDataBean, mainEntityId);
        
        long finalMainEntityId = mainEntityId;
        
        if (mainEntityId != -1)
        {
          finalMainEntityId = getFinalMainEntityId(queryResultObjectDataBean, mainEntityId, finalMainEntityId);
          

          List<List<String>> cpIdsList = getCpIdsListForGivenEntityId(entityName, finalMainEntityId);
          


          hasPrivilegeOnIdentifiedData = checkForIDPrivilege(sessionDataBean, cache, cpIdsList);
          
          break;
        }
      }
    }
    return hasPrivilegeOnIdentifiedData.booleanValue();
  }
  



  public Boolean checkForIDPrivilege(SessionDataBean sessionDataBean, QueryCsmCache cache, List<List<String>> cpIdsList)
    throws SMException
  {
    Boolean hasPrivilegeOnIdentifiedData;
    

    if (cpIdsList.isEmpty())
    {
      hasPrivilegeOnIdentifiedData = Boolean.valueOf(checkPermissionOnGlobalParticipant(sessionDataBean));
    }
    else
    {
      List<Boolean> identifiedPrivilegeList = new ArrayList();
      for (int i = 0; i < cpIdsList.size(); i++)
      {
        List<String> cpIdList = (List)cpIdsList.get(i);
        updatePrivilegeList(sessionDataBean, cache, null, identifiedPrivilegeList, cpIdList);
      }
      hasPrivilegeOnIdentifiedData = isAuthorizedUser(identifiedPrivilegeList, false);
    }
    return hasPrivilegeOnIdentifiedData;
  }
  









  public void filterRowForSimpleSearch(SessionDataBean sessionDataBean, Map queryResultObjectDataMap, List aList, QueryCsmCache cache)
    throws SMException
  {
    Boolean isAuthorisedUser = Boolean.valueOf(true);
    
    Set keySet = queryResultObjectDataMap.keySet();
    Iterator keyIterator = keySet.iterator();
    
    while (keyIterator.hasNext())
    {
      isAuthorisedUser = checkIfAuthorized(sessionDataBean, queryResultObjectDataMap, aList, cache, isAuthorisedUser, keyIterator);
    }
  }
  













  private Boolean checkIfAuthorized(SessionDataBean sessionDataBean, Map queryResultObjectDataMap, List aList, QueryCsmCache cache, Boolean isAuthorisedUser, Iterator keyIterator)
    throws SMException
  {
    Boolean ifAuthorizedUser = isAuthorisedUser;
    

    QueryResultObjectData queryResultObjectData = (QueryResultObjectData)queryResultObjectDataMap.get(keyIterator.next());
    

    int entityId = Integer.parseInt(aList.get(queryResultObjectData.getIdentifierColumnId()).toString());
    
    String entityName = getEntityName(queryResultObjectData);
    
    List<List<String>> cpIdsList = getCpIdsListForGivenEntityId(entityName, entityId);
    
    Boolean hasPrivilegeOnIdentifiedData;
    if (cpIdsList.isEmpty())
    {
      hasPrivilegeOnIdentifiedData = Boolean.valueOf(checkPermissionOnGlobalParticipant(sessionDataBean));
    }
    else
    {
      List<Boolean> readPrivilegeList = new ArrayList();
      List<Boolean> identifiedPrivilegeList = new ArrayList();
      
      for (int i = 0; i < cpIdsList.size(); i++)
      {
        List<String> cpIdList = (List)cpIdsList.get(i);
        updatePrivilegeList(sessionDataBean, cache, readPrivilegeList, identifiedPrivilegeList, cpIdList);
      }
      
      ifAuthorizedUser = isAuthorizedUser(readPrivilegeList, true);
      hasPrivilegeOnIdentifiedData = isAuthorizedUser(identifiedPrivilegeList, false);
    }
    

    List identifiedColumnIdentifiers = queryResultObjectData.getIdentifiedDataColumnIds();
    List objectColumnIdentifiers = queryResultObjectData.getDependentColumnIds();
    removeUnauthorizedData(aList, ifAuthorizedUser, hasPrivilegeOnIdentifiedData, identifiedColumnIdentifiers, objectColumnIdentifiers, true);
    
    return ifAuthorizedUser;
  }
  











  public boolean hasPrivilegeOnIdentifiedDataForSimpleSearch(SessionDataBean sessionDataBean, Map queryResultObjectDataMap, List aList, QueryCsmCache cache)
    throws SMException
  {
    boolean hasPrivilegeOnIdentifiedData = true;
    
    Set keySet = queryResultObjectDataMap.keySet();
    Iterator keyIterator = keySet.iterator();
    

    while (keyIterator.hasNext())
    {
      QueryResultObjectData queryResultObjectData = (QueryResultObjectData)queryResultObjectDataMap.get(keyIterator.next());
      

      int entityId = Integer.parseInt(aList.get(queryResultObjectData.getIdentifierColumnId()).toString());
      

      String entityName = getEntityName(queryResultObjectData);
      
      List<List<String>> cpIdsList = getCpIdsListForGivenEntityId(entityName, entityId);
      
      hasPrivilegeOnIdentifiedData = checkIDPrivilegeForSimpleSearch(sessionDataBean, cache, cpIdsList);
    }
    
    return hasPrivilegeOnIdentifiedData;
  }
  




  private boolean checkIDPrivilegeForSimpleSearch(SessionDataBean sessionDataBean, QueryCsmCache cache, List<List<String>> cpIdsList)
    throws SMException
  {
    boolean hasPrivilegeOnIdentifiedData;
   
    


    if (cpIdsList.isEmpty())
    {
      hasPrivilegeOnIdentifiedData = checkPermissionOnGlobalParticipant(sessionDataBean);
    }
    else
    {
      List<Boolean> identifiedPrivilegeList = new ArrayList();
      for (int i = 0; i < cpIdsList.size(); i++)
      {
        List<String> cpIdList = (List)cpIdsList.get(i);
        updatePrivilegeList(sessionDataBean, cache, null, identifiedPrivilegeList, cpIdList);
      }
      
      hasPrivilegeOnIdentifiedData = isAuthorizedUser(identifiedPrivilegeList, false).booleanValue();
    }
    return hasPrivilegeOnIdentifiedData;
  }
  













  private void updatePrivilegeList(SessionDataBean sessionDataBean, QueryCsmCache cache, List<Boolean> readPrivilegeList, List<Boolean> identifiedPrivilegeList, List<String> cpIdList)
    throws SMException
  {
    Long cpId = Long.valueOf(cpIdList.get(0) != null ? Long.parseLong((String)cpIdList.get(0)) : -1L);
    String entityName = Variables.mainProtocolObject;
    
    if (readPrivilegeList == null)
    {
      Boolean hasPrivilegeOnIdentifiedData = checkIdentifiedDataAccess(sessionDataBean, cache, entityName, cpId);
      
      identifiedPrivilegeList.add(hasPrivilegeOnIdentifiedData);
    }
    else
    {
      Boolean isAuthorisedUser = checkReadDenied(sessionDataBean, cache, entityName, cpId);
      readPrivilegeList.add(isAuthorisedUser);
      

      if (isAuthorisedUser.booleanValue())
      {
        Boolean hasPrivilegeOnIdentifiedData = checkIdentifiedDataAccess(sessionDataBean, cache, entityName, cpId);
        
        identifiedPrivilegeList.add(hasPrivilegeOnIdentifiedData);
      }
    }
  }
  




  private Boolean checkReadDenied(SessionDataBean sessionDataBean, QueryCsmCache cache, String entityName, Long cpId)
    throws SMException
  {
    Boolean isAuthorisedUser;
    



    if (cache.isReadDenied(cpId) == null)
    {
      isAuthorisedUser = checkPermission(sessionDataBean, entityName, cpId, "READ_DENIED");
      
      cache.addNewObjectInReadPrivilegeMap(cpId, isAuthorisedUser);
    }
    else
    {
      isAuthorisedUser = cache.isReadDenied(cpId);
    }
    return isAuthorisedUser;
  }
  




  private String getEntityName(QueryResultObjectData queryResultObjectData)
  {
    String tableName = (String)AbstractClient.objectTableNames.get(queryResultObjectData.getAliasName());
    
    HibernateDAO hibernateDAO = null;
    try
    {
      hibernateDAO = (HibernateDAO)DAOConfigFactory.getInstance().getDAOFactory(CommonServiceLocator.getInstance().getAppName()).getDAO();

    }
    catch (DAOException e)
    {
      e.printStackTrace();
    }
    HibernateMetaData hibernateMetaData = hibernateDAO.getHibernateMetaData();
    return hibernateMetaData.getClassName(tableName);
  }
  










  private Boolean isAuthorizedUser(List<Boolean> privilegeList, boolean isReadDenied)
  {
    for (int i = 0; i < privilegeList.size(); i++)
    {
      Boolean isAuthorized = (Boolean)privilegeList.get(i);
      if ((isReadDenied) && (!isAuthorized.booleanValue()))
      {
        return Boolean.valueOf(false);
      }
      if ((!isReadDenied) && (isAuthorized.booleanValue()))
      {
        return Boolean.valueOf(true);
      }
    }
    
    return Boolean.valueOf(isReadDenied);
  }
  













  private void removeUnauthorizedData(List aList, Boolean isAuthorisedUser, Boolean hasPrivilegeOnIdentifiedData, List identifiedColumnIdentifiers, List objectColumnIdentifiers, boolean isSimpleSearch)
  {
    if (isAuthorisedUser.booleanValue())
    {


      if (!hasPrivilegeOnIdentifiedData.booleanValue())
      {
        removeUnauthorizedFieldsData(aList, identifiedColumnIdentifiers, objectColumnIdentifiers, true, isSimpleSearch);
      }
      

    }
    else {
      removeUnauthorizedFieldsData(aList, identifiedColumnIdentifiers, objectColumnIdentifiers, false, isSimpleSearch);
    }
  }
  







  private Boolean checkIdentifiedDataAccess(SessionDataBean sessionDataBean, QueryCsmCache cache, String entityName, Long entityId)
    throws SMException
  {
    Boolean hasPrivilegeOnIdentifiedData;
    





    if (cache.isIdentifedDataAccess(entityId) == null)
    {
      hasPrivilegeOnIdentifiedData = Boolean.valueOf((checkPermission(sessionDataBean, entityName, entityId, "PHI_ACCESS").booleanValue()) || (checkPermission(sessionDataBean, entityName, entityId, "REGISTRATION").booleanValue()));
      





      cache.addNewObjectInIdentifiedDataAccsessMap(entityId, hasPrivilegeOnIdentifiedData);

    }
    else
    {
      hasPrivilegeOnIdentifiedData = cache.isIdentifedDataAccess(entityId);
    }
    return hasPrivilegeOnIdentifiedData;
  }
  







  private List<List<String>> getCpIdsListForGivenEntityId(String entityName, long entityId)
  {
    String sql = (String)Variables.entityCPSqlMap.get(entityName);
    List<List<String>> cpIdsList = new ArrayList();
    if (entityName.equals(Variables.mainProtocolObject))
    {
      List<String> cpIdList = new ArrayList();
      cpIdList.add(String.valueOf(entityId));
      cpIdsList.add(cpIdList);
    }
    else if (sql != null)
    {
      sql = sql + " ?";
      LinkedList<ColumnValueBean> columnValueBean = new LinkedList();
      ColumnValueBean bean = new ColumnValueBean("entityId", Long.valueOf(entityId));
      columnValueBean.add(bean);
      try
      {
        cpIdsList = executeQuery(sql, columnValueBean);
      }
      catch (Exception e)
      {
        edu.wustl.common.util.logger.Logger.out.error("Error occured while getting CP ids for entity : " + entityName);
        e.printStackTrace();
      }
    }
    return cpIdsList;
  }
  
  public List<List<String>> getCpIdsListForGivenEntityIdList(String entityName, Set<String> entityIds)
  {
    String sql = (String)Variables.entityCPSqlMap.get(entityName);
    List<List<String>> cpIdsList = new ArrayList();
    if (sql != null)
    {
      String inClause = getInClause(entityIds);
      sql = sql.substring(0, sql.lastIndexOf("=")) + " in ( " + inClause + " )";
      try
      {
        cpIdsList = executeQuery(sql, null);
      }
      catch (Exception e)
      {
        edu.wustl.common.util.logger.Logger.out.error("Error occured while getting CP ids for entity : " + entityName);
        e.printStackTrace();
      }
    }
    return cpIdsList;
  }
  





  public static String getQueryStringForCP(String entityName, int entityId)
  {
    StringBuffer queryString = new StringBuffer();
    String str; if ((entityName == null) || (entityId == 0))
    {
      str = null;
    }
    else
    {
      queryString.append((String)Variables.entityCPSqlMap.get(entityName));
      queryString.append(entityId);
      str = queryString.toString();
    }
    return str;
  }
  













  public Boolean checkPermission(SessionDataBean sessionDataBean, String entityName, Long entityId, String permission)
    throws SMException
  {
    PrivilegeManager privilegeManager = PrivilegeManager.getInstance();
    PrivilegeCache privilegeCache = privilegeManager.getPrivilegeCache(sessionDataBean.getUserName());
    



    Boolean isAuthorisedUser = Boolean.valueOf(privilegeCache.hasPrivilege(entityName + "_" + entityId, permission));
    

    if ("READ_DENIED".equals(permission))
    {
      isAuthorisedUser = Boolean.valueOf(!isAuthorisedUser.booleanValue());
    }
    
    if ((!isAuthorisedUser.booleanValue()) && (this.validator != null))
    {
      isAuthorisedUser = Boolean.valueOf(this.validator.hasPrivilegeToView(sessionDataBean, entityId.toString(), permission));
    }
    
    return isAuthorisedUser;
  }
  






  private boolean checkPermissionOnGlobalParticipant(SessionDataBean sessionDataBean)
  {
    boolean isAuthorisedUser = false;
    this.validator = getValidatorInstance();
    if (this.validator != null)
    {
      isAuthorisedUser = this.validator.hasPrivilegeToViewGlobalParticipant(sessionDataBean);
    }
    return isAuthorisedUser;
  }
  









  private List<List<String>> executeQuery(String sql, LinkedList<ColumnValueBean> columnValueBean)
    throws DAOException, ClassNotFoundException, SQLException
  {
    List<List<String>> aList = new ArrayList();
    ResultSet resultSet = null;
    resultSet = this.jdbcDAO.getResultSet(sql, columnValueBean, null);
    if (resultSet != null)
    {
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();
      while (resultSet.next())
      {
        int counter = 1;
        List<String> entityIdsList = new ArrayList();
        while (counter <= columnCount)
        {
          entityIdsList.add(resultSet.getObject(counter).toString());
          counter++;
        }
        aList.add(entityIdsList);
      }
      this.jdbcDAO.closeStatement(resultSet);
    }
    return aList;
  }
  













  private void removeUnauthorizedFieldsData(List aList, List identifiedColumnIdentifiers, List objectColumnIdentifiers, boolean removeOnlyIdentifiedData, boolean isSimpleSearch)
  {
    Vector objectColumnIds = new Vector();
    
    if (removeOnlyIdentifiedData)
    {
      objectColumnIds.addAll(identifiedColumnIdentifiers);
    }
    else
    {
      objectColumnIds.addAll(objectColumnIdentifiers);
    }
    edu.wustl.common.util.logger.Logger.out.debug("objectColumnIds:" + objectColumnIds);
    if (objectColumnIds != null)
    {
      addHashStringToList(aList, isSimpleSearch, objectColumnIds);
    }
  }
  






  private void addHashStringToList(List aList, boolean isSimpleSearch, Vector objectColumnIds)
  {
    for (int k = 0; k < objectColumnIds.size(); k++)
    {
      if (isSimpleSearch)
      {
        aList.set(((Integer)objectColumnIds.get(k)).intValue() - 1, "##");

      }
      else
      {
        aList.set(((Integer)objectColumnIds.get(k)).intValue(), "##");
      }
    }
  }
  








  private void removeUnauthorizedFieldsData(List aList, boolean removeOnlyIdentifiedData, QueryResultObjectDataBean queryResultObjectDataBean)
  {
    Vector objectColumnIds = new Vector();
    boolean isAuthorizedUser = true;
    if (removeOnlyIdentifiedData)
    {
      objectColumnIds.addAll(queryResultObjectDataBean.getIdentifiedDataColumnIds());
    }
    else
    {
      objectColumnIds.addAll(queryResultObjectDataBean.getObjectColumnIds());
      isAuthorizedUser = false;
    }
    if (objectColumnIds != null)
    {
      for (int k = 0; k < objectColumnIds.size(); k++)
      {
        aList.set(((Integer)objectColumnIds.get(k)).intValue(), "####");
      }
    }
    

    List tqColumnMetadataList = queryResultObjectDataBean.getTqColumnMetadataList();
    
    QueryCSMValidator queryCSMValidator = new QueryCSMValidator();
  }
  










  public Map<String, Boolean> getAccessPrivilegeMap(String objName, Long identifier, SessionDataBean sessionDataBean, QueryCsmCache cache)
    throws SMException
  {
    Boolean isAuthorisedUser = Boolean.valueOf(true);
    Boolean hasPrivilegeOnIdentifiedData = Boolean.valueOf(true);
    Map<String, Boolean> accessprivilegeMap = new HashMap();
    List<List<String>> cpIdsList = getCpIdsListForGivenEntityId(objName, identifier.intValue());
    
    if (cpIdsList.isEmpty())
    {
      hasPrivilegeOnIdentifiedData = Boolean.valueOf(checkPermissionOnGlobalParticipant(sessionDataBean));
    }
    else
    {
      List<Boolean> readPrivilegeList = new ArrayList();
      List<Boolean> identifiedPrivilegeList = new ArrayList();
      
      for (int i = 0; i < cpIdsList.size(); i++)
      {
        List<String> cpIdList = (List)cpIdsList.get(i);
        updatePrivilegeList(sessionDataBean, cache, readPrivilegeList, identifiedPrivilegeList, cpIdList);
      }
      
      isAuthorisedUser = isAuthorizedUser(readPrivilegeList, true);
      hasPrivilegeOnIdentifiedData = isAuthorizedUser(identifiedPrivilegeList, false);
    }
    
    accessprivilegeMap.put("isReadDenied", Boolean.valueOf(!isAuthorisedUser.booleanValue()));
    accessprivilegeMap.put("hasPHIAccess", hasPrivilegeOnIdentifiedData);
    
    return accessprivilegeMap;
  }
  







  public List<List<String>> getCSIdFromDataList(List aList, long finalMainEntityId, int cpIdIndex)
  {
    List<List<String>> cpIdsList = new ArrayList();
    String cpId = (String)aList.get(cpIdIndex);
    List<String> cpIdList = new ArrayList();
    cpIdList.add(String.valueOf(cpId));
    cpIdList.add(String.valueOf(finalMainEntityId));
    cpIdsList.add(cpIdList);
    return cpIdsList;
  }
  
  private String addInClause(String sql, Set<String> entityIds) {
    String idColumn = sql.substring(sql.indexOf("where") + 6, sql.indexOf(" ="));
    sql = "select " + idColumn + ", " + sql.substring(7, sql.indexOf("="));
    sql = sql + "in (" + getInClause(entityIds) + ")";
    
    return sql;
  }
  
  private String getInClause(Set<String> entityIds) {
    StringBuilder inClause = null;
    
    for (String entityId : entityIds)
    {
      if (inClause == null) {
        inClause = new StringBuilder(" " + entityId);
      } else {
        inClause.append(", ").append(entityId);
      }
    }
    
    return inClause != null ? inClause.toString() : null;
  }
}
