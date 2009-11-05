//
//package edu.wustl.query.querysuite.metadata;
//
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//import edu.wustl.dao.JDBCDAO;
//import edu.wustl.dao.exception.DAOException;
//
//public class AddEntity
//{
//
//	//private Connection connection = null;
//	
//	private JDBCDAO jdbcdao = null;
//	
////	AddEntity(Connection connection)
////	{
////		this.connection = connection;
////	}
//	AddEntity(JDBCDAO jdbcdao)
//	{
//		this.jdbcdao = jdbcdao;
//	}
//
////	public static void main(String args[]) throws Exception
////	{
////		Connection connection = DBUtil.getConnection();
////		connection.setAutoCommit(true);
////	}
//
//	public void addEntity(List<String> entityList, String tableName, String parentEntity,
//			int inheritanceStrategy, int isAbstract) throws SQLException, IOException, DAOException
//	{
//		//Statement stmt = connection.createStatement();
//		for (String entityName : entityList)
//		{
//			//insert statements
//			String sql = "select max(identifier) from dyextn_abstract_metadata";
//			//ResultSet rs = stmt.executeQuery(sql);
//			ResultSet rs = jdbcdao.getQueryResultSet(sql);
//			int nextIdOfAbstractMetadata = 0;
//			if (rs.next())
//			{
//				int maxId = rs.getInt(1);
//				nextIdOfAbstractMetadata = maxId + 1;
//			}
//			rs.close();
//			int nextIdDatabaseproperties = 0;
//			sql = "select max(identifier) from dyextn_database_properties";
//			//rs = stmt.executeQuery(sql);
//			rs = jdbcdao.getQueryResultSet(sql);
//			if (rs.next())
//			{
//				int maxId = rs.getInt(1);
//				nextIdDatabaseproperties = maxId + 1;
//			}
//			rs.close();
//
//			sql = "INSERT INTO dyextn_abstract_metadata values(" + nextIdOfAbstractMetadata
//					+ ",NULL,NULL,NULL,'" + entityName + "',null)";
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//			sql = "INSERT INTO dyextn_abstract_entity values(" + nextIdOfAbstractMetadata + ")";
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//
//			if (parentEntity.equals("NULL"))
//			{
//				sql = "INSERT INTO dyextn_entity values(" + nextIdOfAbstractMetadata + ",3,"
//						+ isAbstract + ",NULL," + inheritanceStrategy + ",NULL,NULL,1)";
//				//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//				UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//			}
//			else
//			{
////				int parEId = UpdateMetadataUtil.getEntityIdByName(parentEntity, connection
////						.createStatement());
//				int parEId =UpdateMetadataUtil.executeInsertSQL(parentEntity,jdbcdao);
//				sql = "INSERT INTO dyextn_entity values(" + nextIdOfAbstractMetadata + ",3,"
//						+ isAbstract + "," + parEId + "," + inheritanceStrategy + ",NULL,NULL,1)";
//				//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//				UpdateMetadataUtil.executeInsertSQL(parentEntity,jdbcdao);
//			}
//
//			sql = "INSERT INTO dyextn_database_properties values(" + nextIdDatabaseproperties
//					+ ",'" + tableName + "')";
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(parentEntity,jdbcdao);
//
//			sql = "INSERT INTO dyextn_table_properties (IDENTIFIER,ABSTRACT_ENTITY_ID) values("
//					+ nextIdDatabaseproperties + "," + nextIdOfAbstractMetadata + ")";
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(parentEntity,jdbcdao);
//		}
//	}
//}
