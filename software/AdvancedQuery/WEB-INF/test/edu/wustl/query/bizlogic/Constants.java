package edu.wustl.query.bizlogic;


public class Constants
{
	// DB Type Constants
	public static final String DB2 = "db2";
	public static final String MySQL = "mysql";
	public static final String Oracle = "oracle";
	public static final String MSSQL = "mssql";
	
	//DAO Constants.
	public static final int HIBERNATE_DAO = 1;
	public static final int JDBC_DAO = 2;
	
	// FrameWork.xml constants
	public static final String TestCase = "TestCase";
	public static final String name = "name";
	public static final String tmtTestCaseId = "tmtTestCaseId";
	public static final String dbInitCSV = "dbInitCSV";
	public static final String expectedResultsCSV = "expectedResultsCSV";
	public static final String dbCleanUpCSV = "dbCleanUpCSV";
	public static final String queryType = "type";
	public static final String queryInputFile = "fileName";
	public static final String queryId = "id";
	
	// Query Type
	public static final String query = "query";
	public static final String saved_query = "saved_query";
	public static final String serialized_query = "serialized_query";
	
	// public static final String path = "E:\\catissueworkspace\\AdvancedQuery_LATEST\\WEB-INF\\test\\filesForTestCase\\";
	public static String testHome=null;
	// public static final String actualResultsCSVFileName = "actualResults.csv";
	
	public static final String addOperation = "add";
	public static final String deleteOperation = "delete";
	
	public static final String QUERY_EXECUTION_ID = "QUERY_EXECUTION_ID";
    public static final String QRY_ID = "QUERY_ID";
    public static final String QUERY_IN_PROGRESS = "In Progress";
    public static final String QUERY_COMPLETED = "Completed";
    public static final String QUERY_CANCELLED = "Cancelled";
}
