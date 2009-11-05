//
//package edu.wustl.query.querysuite.metadata;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import edu.common.dynamicextensions.domain.Entity;
//import edu.common.dynamicextensions.domain.databaseproperties.TableProperties;
//import edu.wustl.dao.JDBCDAO;
//import edu.wustl.dao.exception.DAOException;
//import edu.wustl.query.util.querysuite.DAOUtil;
//
///**
// * 
// * @author deepti_shelar
// *
// */
//public class DeleteEntity
//{
//
////	private Connection connection = null;
////	private static Statement stmt = null;
//	private JDBCDAO jdbcdao=null;
//
//	private String entityNameToDelete;
//	private Entity entityToDelete;
//
//	public static void main(String[] args) throws Exception
//	{
////		Connection connection = DBUtil.getConnection();
////		connection.setAutoCommit(true);
//		JDBCDAO jdbcdao=DAOUtil.getJDBCDAO(null);
//		
//		//DeleteEntity deleteEntity = new DeleteEntity(connection);
//		DeleteEntity deleteEntity = new DeleteEntity(jdbcdao);
//
//		deleteEntity.populateEntityToDeletetList();
//		deleteEntity.updateEntityToDelete();
//
//		List<String> deleteSQL = new ArrayList<String>();
//
//		List<String> deleteAttribute = deleteEntity.deleteAttribute();
//		deleteSQL.addAll(deleteAttribute);
//
//		List<String> deleteEntitySQL = deleteEntity.deleteEntity();
//		deleteSQL.addAll(deleteEntitySQL);
//		//UpdateMetadataUtil.executeSQLs(deleteSQL, connection.createStatement(), true);
//		UpdateMetadataUtil.executeSQLs(deleteSQL,jdbcdao, true);
//
//		//connection.close();
//		DAOUtil.closeJDBCDAO(jdbcdao);
//	}
//
//	public List<String> deleteEntity() throws SQLException, DAOException
//	{
//		List<String> deleteSQL = new ArrayList<String>();
//		if (entityNameToDelete == null)
//		{
//			System.out.println("entityNameToDelete is NULL");
//		}
//		updateEntityToDelete();
//
//		deleteSQL = getDeleteEntitySQL();
//		return deleteSQL;
//	}
//
//	private List<String> getDeleteEntitySQL() throws SQLException, DAOException
//	{
//		List<String> deleteSQL = new ArrayList<String>();
//		String sql;
//
//		sql = "delete from dyextn_table_properties where identifier = "
//				+ entityToDelete.getTableProperties().getId();
//		deleteSQL.add(sql);
//
//		sql = "delete from dyextn_database_properties where identifier = "
//				+ entityToDelete.getTableProperties().getId();
//		deleteSQL.add(sql);
//
//		deleteAssociation(deleteSQL, entityToDelete.getId());
//
//		sql = "delete from intra_model_association where DE_ASSOCIATION_ID in (select identifier from DYEXTN_ASSOCIATION where TARGET_ENTITY_ID ="
//				+ entityToDelete.getId() + ")";
//		deleteSQL.add(sql);
//
//		sql = "delete from DYEXTN_TAGGED_VALUE where ABSTRACT_METADATA_ID ="
//				+ entityToDelete.getId();
//		deleteSQL.add(sql);
//
//		sql = "delete from DYEXTN_SEMANTIC_PROPERTY where ABSTRACT_METADATA_ID ="
//				+ entityToDelete.getId();
//		deleteSQL.add(sql);
//
//		sql = "delete from dyextn_entity where identifier = " + entityToDelete.getId();
//		deleteSQL.add(sql);
//
//		sql = "delete from dyextn_abstract_entity where id = " + entityToDelete.getId();
//		deleteSQL.add(sql);
//
//		sql = "delete from dyextn_abstract_metadata where identifier = " + entityToDelete.getId();
//		deleteSQL.add(sql);
//
//		return deleteSQL;
//	}
//
//	private List<String> deleteAttribute() throws SQLException, DAOException
//	{
//		//DeleteAttribute deleteAttribute = new DeleteAttribute(connection);
//		DeleteAttribute deleteAttribute = new DeleteAttribute(jdbcdao);
//		
//		List<String> entityNameList = new ArrayList<String>();
//		entityNameList.add("edu.wustl.catissuecore.domain.Quantity");
//		deleteAttribute.setEntityNameList(entityNameList);
//
//		HashMap<String, List<String>> entityAttributesToDelete = new HashMap<String, List<String>>();
//		List<String> attributesToDelete = new ArrayList<String>();
//		attributesToDelete.add("value");
//		entityAttributesToDelete.put("edu.wustl.catissuecore.domain.Quantity", attributesToDelete);
//		deleteAttribute.setEntityAttributesToDelete(entityAttributesToDelete);
//
//		HashMap<String, String> attributeDatatypeMap = new HashMap<String, String>();
//		attributeDatatypeMap.put("value", "double");
//		deleteAttribute.setAttributeDatatypeMap(attributeDatatypeMap);
//
//		List<String> deleteSQL = deleteAttribute.deleteAttribute();
//		return deleteSQL;
//	}
//
//	private void deleteAssociation(List<String> deleteSQL, Long id) throws SQLException, DAOException
//	{
//		int SOURCE_ENTITY_ID;
//		int TARGET_ENTITY_ID;
//		List<Long> roleIdMap = new ArrayList<Long>();
//
//		//DeleteAssociation deleteAssociation = new DeleteAssociation(connection);
//		DeleteAssociation deleteAssociation = new DeleteAssociation(jdbcdao);
//
//		String sql = "Select FIRST_ENTITY_ID, LAST_ENTITY_ID from path where LAST_ENTITY_ID=" + id
//				+ " or FIRST_ENTITY_ID=" + id;
//		//ResultSet rs = stmt.executeQuery(sql);
//		ResultSet rs =jdbcdao.getQueryResultSet(sql);
//		while (rs.next())
//		{
//
//			SOURCE_ENTITY_ID = rs.getInt(1);
//			TARGET_ENTITY_ID = rs.getInt(2);
//
//			roleIdMap.addAll(deleteAssociation.getSourceSQL(deleteSQL, SOURCE_ENTITY_ID,
//					TARGET_ENTITY_ID));
//			roleIdMap.addAll(deleteAssociation.getSourceSQL(deleteSQL, TARGET_ENTITY_ID,
//					SOURCE_ENTITY_ID));
//		}
//
//		for (Long srcRoleId : roleIdMap)
//		{
//			sql = "delete from dyextn_role where IDENTIFIER=" + srcRoleId;
//			deleteSQL.add(sql);
//		}
//	}
//
//	private void populateEntityToDeletetList()
//	{
//		entityNameToDelete = "edu.wustl.catissuecore.domain.Quantity";
//	}
//
//	private void updateEntityToDelete() throws SQLException, DAOException
//	{
//		String sql;
//		//stmt = connection.createStatement();
//		ResultSet rs;
//
//		sql = "select identifier,name from dyextn_abstract_metadata where NAME='"
//				+ entityNameToDelete + "'";
//		//rs = stmt.executeQuery(sql);
//		rs=jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			entityToDelete.setId(rs.getLong(1));
//			entityToDelete.setName(rs.getString(2));
//		}
//		TableProperties tableProperties = new TableProperties();
//		sql = "select identifier from dyextn_table_properties where ABSTRACT_ENTITY_ID="
//				+ entityToDelete.getId();
//		//rs = stmt.executeQuery(sql);
//		rs=jdbcdao.getQueryResultSet(sql);
//
//		if (rs.next())
//		{
//			tableProperties.setId(rs.getLong(1));
//		}
//		entityToDelete.setTableProperties(tableProperties);
//	}
//
//	public void setEntityNameToDelete(String entityNameToDelete)
//	{
//		this.entityNameToDelete = entityNameToDelete;
//	}
//
////	public DeleteEntity(Connection connection) throws SQLException
////	{
////		super();
////		this.connection = connection;
////		this.stmt = connection.createStatement();
////		this.entityToDelete = new Entity();
////	}
//	
//	public DeleteEntity(JDBCDAO jdbcdao) throws SQLException
//	{
//		super();
//		this.jdbcdao = jdbcdao;
//		this.entityToDelete = new Entity();
//	}
//}
