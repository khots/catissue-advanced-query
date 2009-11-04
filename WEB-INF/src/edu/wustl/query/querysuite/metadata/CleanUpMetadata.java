//
//package edu.wustl.query.querysuite.metadata;
//
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;
//
//import edu.wustl.dao.JDBCDAO;
//import edu.wustl.dao.exception.DAOException;
//import edu.wustl.query.util.querysuite.DAOUtil;
//
///**
// * @author pooja_deshpande
// * Class to delete all the invalid curated paths between 2 entities.
// */
//public class CleanUpMetadata
//{
//
//	//private static Connection connection;
//	private JDBCDAO jdbcdao=null;
//
//	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, DAOException
//	{
//		//Connection connection = DBUtil.getConnection();
//		JDBCDAO jdbcdao=DAOUtil.getJDBCDAO(null);
//
//		//CleanUpMetadata cleanUpMetadata = new CleanUpMetadata(connection);
//		CleanUpMetadata cleanUpMetadata = new CleanUpMetadata(jdbcdao);
//		cleanUpMetadata.cleanMetadata();
//		
//	}
//
//	public List<String> cleanMetadata() throws SQLException, DAOException
//	{
//		StringTokenizer st;
//		String intermediatePath;
//		String nextToken;
//		List<String> deletePaths = new ArrayList<String>();
//		List<String> deleteSQLList = new ArrayList<String>();
//
//		String sql = "Select INTERMEDIATE_PATH from PATH";
////
////		Statement stmt = connection.createStatement();
////		ResultSet rs = stmt.executeQuery(sql);
//		ResultSet rs = jdbcdao.getQueryResultSet(sql);
//
//		while (rs.next())
//		{
//			intermediatePath = rs.getString(1);
//			st = new StringTokenizer(intermediatePath, "_");
//
//			while (st.hasMoreTokens())
//			{
//				nextToken = st.nextToken();
//				int associationId = Integer.valueOf(nextToken);
//				String sql1 = "Select ASSOCIATION_ID from intra_model_association where ASSOCIATION_ID = "
//						+ associationId;
////				Statement stmt1 = connection.createStatement();
////				ResultSet rs1 = stmt1.executeQuery(sql1);
//				ResultSet rs1 = jdbcdao.getQueryResultSet(sql1);
//
//				if (rs1.next())
//				{
//					//stmt1.close();
//					continue;
//				}
//				else
//				{
//					deletePaths.add(intermediatePath);
//					//stmt1.close();
//					break;
//				}
//			}
//		}
//		for (String deletePath : deletePaths)
//		{
//			deleteSQLList.add("delete from PATH where INTERMEDIATE_PATH = '" + deletePath + "'");
//		}
//		return deleteSQLList;
//	}
//
////	public CleanUpMetadata(Connection connection)
////	{
////		super();
////		this.connection = connection;
////	}
//	public CleanUpMetadata(JDBCDAO jdbcdao)
//	{
//		super();
//		this.jdbcdao = jdbcdao;
//	}
//}
