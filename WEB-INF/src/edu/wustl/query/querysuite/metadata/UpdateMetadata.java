//
//package edu.wustl.query.querysuite.metadata;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
///**
// * @author vijay_pande
// * 1. For AddAssociation immediately execute executeInsertSQL method since identifier should get updated
// * 2. Verify that all required MAP/List are set properly to generate SQL
// * 3. First generate SQl statements, verify them and then only execute them on DB
// * 4. For delete entity first delete all attributes for that entity. DeleteEntity will generate SQL statements to delete association
// * 5. Always first execute all the delete SQLs then update SQLs and then execute Insert SQLs
// * 6. Map to delete path is populated in DeleteAssociation class itself
// * 7. SQLs of AddRaceMetadata are not redirected to the SQL file. They are directly ran on DB
// */
//public class UpdateMetadata
//{
//
//	private static Connection connection = null;
//	// The Name of the server for the database. For example : localhost
//	static String DATABASE_SERVER_NAME;
//	// The Port number of the server for the database.
//	static String DATABASE_SERVER_PORT_NUMBER;
//	// The Type of Database. Use one of the two values 'MySQL', 'Oracle'.
//	static String DATABASE_TYPE;
//	//	Name of the Database.
//	static String DATABASE_NAME;
//	// Database User name
//	static String DATABASE_USERNAME;
//	// Database Password
//	static String DATABASE_PASSWORD;
//	// The database Driver 
//	static String DATABASE_DRIVER;
//	//Oracle Version
//	static String ORACLE_TNS_NAME;
//
//	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException
//	{
//		try
//		{
//			configureDBConnection(args);
//			connection = getConnection();
//			connection.setAutoCommit(true);
//			UpdateMetadataUtil.isExecuteStatement = true;
//
////			deleteMeatadata();
////			List<String> updateSQL = updateSQLForDistributionProtocol();
////			UpdateMetadataUtil.executeSQLs(updateSQL, connection.createStatement(), false);
////
////			addMetadata();
////			updateMetadata();
////			addCurratedPath();
////			deletePermissibleValue();
////			addPermissibleValue();
////			cleanUpMetadata();
//		}
//		finally
//		{
//			if (UpdateMetadataUtil.metadataSQLFile != null)
//			{
//				UpdateMetadataUtil.metadataSQLFile.close();
//			}
//			if (UpdateMetadataUtil.failureWriter != null)
//			{
//				UpdateMetadataUtil.failureWriter.close();
//			}
//			if (connection != null)
//			{
//				connection.close();
//			}
//		}
//	}
//
//	/**
//	 * Configuration
//	 * @param args
//	 */
//	private static void configureDBConnection(String[] args)
//	{
//		if (args.length < 7)
//		{
//			throw new RuntimeException("In sufficient number of arguments");
//		}
//		DATABASE_SERVER_NAME = args[0];
//		DATABASE_SERVER_PORT_NUMBER = args[1];
//		DATABASE_TYPE = args[2];
//		DATABASE_NAME = args[3];
//		DATABASE_USERNAME = args[4];
//		DATABASE_PASSWORD = args[5];
//		DATABASE_DRIVER = args[6];
//	}
//
//	/**
//	 * This method will create a database connection using configuration info.
//	 * @return Connection : Database connection object
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 */
//	private static Connection getConnection() throws ClassNotFoundException, SQLException
//	{
//		Connection connection = null;
//		// Load the JDBC driver
//		Class.forName(DATABASE_DRIVER);
//		// Create a connection to the database
//		String url = "";
//		if ("MySQL".equalsIgnoreCase(DATABASE_TYPE))
//		{
//			url = "jdbc:mysql://" + DATABASE_SERVER_NAME + ":" + DATABASE_SERVER_PORT_NUMBER + "/"
//					+ DATABASE_NAME; // a JDBC url
//		}
//		if ("Oracle".equalsIgnoreCase(DATABASE_TYPE))
//		{
//			url = "jdbc:oracle:thin:@" + DATABASE_SERVER_NAME + ":" + DATABASE_SERVER_PORT_NUMBER
//					+ ":" + DATABASE_NAME;
//		}
//		connection = DriverManager.getConnection(url, DATABASE_USERNAME, DATABASE_PASSWORD);
//		return connection;
//	}
//}
