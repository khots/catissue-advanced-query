//
//package edu.wustl.query.querysuite.metadata;
//
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import edu.wustl.dao.JDBCDAO;
//import edu.wustl.dao.exception.DAOException;
//
//public class AddAssociations
//{
//
//	//private Connection connection = null;
//	private JDBCDAO jdbcdao = null;
//
////	AddAssociations(Connection connection)
////	{
////		this.connection = connection;
////	}
//	AddAssociations(JDBCDAO jdbcdao)
//	{
//		this.jdbcdao = jdbcdao;
//	}
////	public static void main(String args[]) throws Exception
////	{
////		Connection connection = DBUtil.getConnection();
////		connection.setAutoCommit(true);
////	}
//
//	public void addAssociation(String entityName, String associatedEntityName,
//			String associationName, String associationType, String roleName, boolean isSwap,
//			String roleNameTable, String srcAssociationId, String targetAssociationId,
//			int maxCardinality, int isSystemGenerated, String direction) throws SQLException,DAOException, IOException
//	
//	{
//		//Statement stmt = connection.createStatement();
//		String sql = "select max(identifier) from dyextn_abstract_metadata";
//		//ResultSet rs = stmt.executeQuery(sql);
//		ResultSet rs = jdbcdao.getQueryResultSet(sql);
//
//		int nextIdOfAbstractMetadata = 0;
//		if (rs.next())
//		{
//			int maxId = rs.getInt(1);
//			nextIdOfAbstractMetadata = maxId + 1;
//		}
//		int nextIdOfDERole = 0;
//		sql = "select max(identifier) from dyextn_role";
//		//rs = stmt.executeQuery(sql);
//		rs = jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			int maxId = rs.getInt(1);
//			nextIdOfDERole = maxId + 1;
//		}
//		int nextIdOfDBProperties = 0;
//
//		sql = "select max(identifier) from dyextn_database_properties";
//		//rs = stmt.executeQuery(sql);
//		rs =jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			int maxId = rs.getInt(1);
//			nextIdOfDBProperties = maxId + 1;
//		}
//		int nextIDintraModelAssociation = 0;
//		sql = "select max(ASSOCIATION_ID) from intra_model_association";
//		//rs = stmt.executeQuery(sql);
//		jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			int maxId = rs.getInt(1);
//			nextIDintraModelAssociation = maxId + 1;
//		}
//
//		int nextIdPath = 0;
//		sql = "select max(PATH_ID) from path";
//		//rs = stmt.executeQuery(sql);
//		jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			int maxId = rs.getInt(1);
//			nextIdPath = maxId + 1;
//		}
//
//		int entityId = 0;
//
//		sql = "select identifier from dyextn_abstract_metadata where name like '" + entityName
//				+ "'";
//		//rs = stmt.executeQuery(sql);
//		jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			entityId = rs.getInt(1);
//		}
//		if (entityId == 0)
//		{
//			System.out.println("Entity not found of name ");
//		}
//
//		int associatedEntityId = 0;
//
//		sql = "select identifier from dyextn_abstract_metadata where name like '"
//				+ associatedEntityName + "'";
//		//rs = stmt.executeQuery(sql);
//		jdbcdao.getQueryResultSet(sql);
//		if (rs.next())
//		{
//			associatedEntityId = rs.getInt(1);
//		}
//		if (associatedEntityId == 0)
//		{
//			System.out.println("Entity not found of name ");
//		}
//
//		sql = "insert into dyextn_abstract_metadata values (" + nextIdOfAbstractMetadata
//				+ ",null,null,null,'" + associationName + "',null)";
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql, jdbcdao);
//
//		sql = "INSERT INTO DYEXTN_BASE_ABSTRACT_ATTRIBUTE values(" + nextIdOfAbstractMetadata + ")";
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//
//		sql = "insert into dyextn_attribute values (" + nextIdOfAbstractMetadata + "," + entityId
//				+ ")";
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql, jdbcdao);
//		int roleId = nextIdOfDERole;
//		if (isSwap)
//		{
//			roleId = nextIdOfDERole + 1;
//			sql = "insert into dyextn_role values (" + nextIdOfDERole + ",'" + associationType
//					+ "'," + maxCardinality + ",0,'" + roleName + "')";
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(sql, jdbcdao);
//
//			if (isSystemGenerated == 0)
//			{
//				sql = "insert into dyextn_role values (" + roleId + ",'ASSOCIATION',2,0,'"
//						+ roleNameTable + "')";
//			}
//			else
//			{
//				sql = "insert into dyextn_role values (" + roleId + ",'ASSOCIATION',1,0,'"
//						+ roleNameTable + "')";
//			}
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(sql, jdbcdao);
//		}
//
//		if (!isSwap)
//		{
//			int lastIdOfDERole = nextIdOfDERole - 2;
//			int idOfDERole = nextIdOfDERole - 1;
//			sql = "insert into dyextn_association values (" + nextIdOfAbstractMetadata + ",'"
//					+ direction + "'," + associatedEntityId + "," + lastIdOfDERole + ","
//					+ idOfDERole + "," + isSystemGenerated + ")";
//		}
//		else
//		{
//			if (isSystemGenerated == 0)
//			{
//				sql = "insert into dyextn_association values (" + nextIdOfAbstractMetadata + ",'"
//						+ direction + "'," + associatedEntityId + "," + roleId + ","
//						+ nextIdOfDERole + ",0)";
//			}
//			else
//			{
//				sql = "insert into dyextn_association values (" + nextIdOfAbstractMetadata + ",'"
//						+ direction + "'," + associatedEntityId + "," + roleId + ","
//						+ nextIdOfDERole + ",1)";
//			}
//		}
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//		sql = "insert into dyextn_database_properties values (" + nextIdOfDBProperties + ",'"
//				+ associationName + "')";
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql, jdbcdao);
//		if (isSwap)
//		{
//			if (targetAssociationId == null)
//			{
//				sql = "insert into dyextn_constraint_properties (IDENTIFIER,SOURCE_ENTITY_KEY,TARGET_ENTITY_KEY,ASSOCIATION_ID) values("
//						+ nextIdOfDBProperties
//						+ ",null,'"
//						+ srcAssociationId
//						+ "',"
//						+ nextIdOfAbstractMetadata + ")";
//			}
//			else
//			{
//				if (srcAssociationId == null)
//				{
//					sql = "insert into dyextn_constraint_properties (IDENTIFIER,SOURCE_ENTITY_KEY,TARGET_ENTITY_KEY,ASSOCIATION_ID) values("
//							+ nextIdOfDBProperties
//							+ ",'"
//							+ targetAssociationId
//							+ "',"
//							+ srcAssociationId + "," + nextIdOfAbstractMetadata + ")";
//				}
//				else
//				{
//					sql = "insert into dyextn_constraint_properties (IDENTIFIER,SOURCE_ENTITY_KEY,TARGET_ENTITY_KEY,ASSOCIATION_ID) values("
//							+ nextIdOfDBProperties
//							+ ",'"
//							+ targetAssociationId
//							+ "','"
//							+ srcAssociationId + "'," + nextIdOfAbstractMetadata + ")";
//				}
//			}
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//		}
//		else
//		{
//			if (targetAssociationId == null)
//			{
//				sql = "insert into dyextn_constraint_properties (IDENTIFIER,SOURCE_ENTITY_KEY,TARGET_ENTITY_KEY,ASSOCIATION_ID) values("
//						+ nextIdOfDBProperties
//						+ ",'"
//						+ srcAssociationId
//						+ "',null,"
//						+ nextIdOfAbstractMetadata + ")";
//			}
//			else
//			{
//				if (srcAssociationId != null)
//				{
//					srcAssociationId = "'" + srcAssociationId + "'";
//				}
//				sql = "insert into dyextn_constraint_properties (IDENTIFIER,SOURCE_ENTITY_KEY,TARGET_ENTITY_KEY,ASSOCIATION_ID) values("
//						+ nextIdOfDBProperties
//						+ ","
//						+ srcAssociationId
//						+ ",'"
//						+ targetAssociationId + "'," + nextIdOfAbstractMetadata + ")";
//			}
//			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//			UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//		}
//
//		sql = "insert into association values(" + nextIDintraModelAssociation + ",2)";
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//		
//		sql = "insert into intra_model_association values(" + nextIDintraModelAssociation + ","
//				+ nextIdOfAbstractMetadata + ")";
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//		sql = "insert into path values (" + nextIdPath + "," + entityId + ","
//				+ nextIDintraModelAssociation + "," + associatedEntityId + ")";
//
//		//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
//		UpdateMetadataUtil.executeInsertSQL(sql,jdbcdao);
//		
//	}
//}
