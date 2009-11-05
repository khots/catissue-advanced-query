
package edu.wustl.common.query.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.querymanager.CiderQueryManager;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.Constants;
import edu.wustl.query.bizlogic.QueryFrameworkTestCase;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * This class is responsible for creating tables on which each test case
 * will be fired. Scripts will be read from a file & then, fired on the DB.
 * Information about which script file is to read for each test case will be provided 
 * in an XML file
 * @author ravindra_jain
 *
 */
public class QueryUtility
{

	// public static XQueryGenerator xQueryGenerator = new XQueryGenerator();
	public static PassOneXQueryGenerator xQueryGenerator = new PassOneXQueryGenerator();
	// public static IQueryGenerator xQueryGenerator = QueryGeneratorFactory.getDefaultQueryGenerator();
	public static String tempTableName = "QUERY_ITABLE";

	public static Map<Integer, Map<String, String>> xmlDetailsMap = new HashMap<Integer, Map<String, String>>();;
	public static int noOfTestCases = 0;
	public static String dbInitCommonScriptsFile, dbCleanUpCommonScriptsFile, path;
	public static QueryFrameworkTestCase x;

	/**
	 * 
	 * @param queryId
	 * @return
	 * @throws DAOException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static IQuery getQuery(Long queryId) throws DAOException, IOException,
			ClassNotFoundException
	{
		IQuery query = null;

		try
		{
			if (queryId != null)
			{
				//AbstractDAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
				HibernateDAO dao = DAOUtil.getHibernateDAO(null);
				dao.openSession(null);
				query = (IParameterizedQuery) dao.retrieveById(ParameterizedQuery.class.getName(), Long
						.valueOf(queryId));
				System.out.println("Step 2. Retrieving IQuery from Database.... Query_Id.... "
						+ queryId);

				dao.closeSession();
			}
		}
		catch (DAOException ex)
		{
			System.out.println("Step 2. Error in Retrieving IQuery from Database.... Query Id.... "
					+ queryId);
			System.out.println("\t A DAO Exception has occurred....");
			System.out.println("\t Remaining Steps cannot be executed....");
			ex.printStackTrace();
			throw ex;
		}

		return query;
	}

	/**
	 * To get DeSerialized version of IQuery from a serialized 1
	 * stored in a file
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public static IQuery getQuery(String fileName) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = null;
		ObjectInputStream in = null;
		IQuery query = null;

		try
		{
			// To De-serialize IQuery object from a file
			fileName = Constants.testHome + fileName;
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			query = (IQuery) in.readObject();
			in.close();
		}
		catch (IOException ex)
		{
			System.out
					.println("Step 2. Error in Retrieving IQuery from Serialized version from file.... "
							+ fileName);
			System.out.println("\t An I/O Exception has occurred....");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}
		catch (ClassNotFoundException ex)
		{
			System.out
					.println("Step 2. Error in Retrieving IQuery from Serialized version from file.... "
							+ fileName);
			System.out.println("\t A ClassNotFound Exception has occurred....");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}
		return query;
	}

	/**
	 * 
	 * @param query
	 * @throws MultipleRootsException
	 * @throws SqlException
	 * @throws DAOException 
	 * @throws DAOException
	 * @throws QueryModuleException 
	 * @throws QueryModuleException
	 */
	public static Long runQueryAndPopulateITable(IQuery query) throws MultipleRootsException,
			SqlException, DAOException, QueryModuleException
	{
//		JDBCDAO jdbcDao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		Long queryExecId = 0L;
		try
		{
			/*HttpServletRequest request = new MockHttpServletRequest();
			
			CiderQueryUIManager ciderQueryUIManager = new CiderQueryUIManager();
			ciderQueryUIManager.
			
			HttpSession session = request.getSession();
			session.setAttribute(arg0, arg1);
			*/
			CiderQueryManager manager = new CiderQueryManager();
			Long noOfRecords = 0L;
			CiderQueryPrivilege privilege = new CiderQueryPrivilege();
			CiderQuery ciderQueryObj = new CiderQuery(query, -1L, "", -1L, null, "10.88.199.224",privilege);
			queryExecId = manager.execute(ciderQueryObj);
			
			Count count = manager.getQueryCount(queryExecId);
			
			while(!count.getStatus().equalsIgnoreCase(Constants.QUERY_COMPLETED))
			{
				if(count.getStatus().equalsIgnoreCase(Constants.QUERY_CANCELLED))
				{
					System.out.println("QUERY CANCELLED.......");
				}
				
				count = manager.getQueryCount(queryExecId);
			}
			
			noOfRecords = count.getCount();

			System.out.println("No of Records :: "+noOfRecords);
			System.out.println("TEST CASE EXECUTED.....");
			
//			jdbcDao.openSession(null);
//			
//			String populateTableSql = "insert into " + tempTableName + " (" + xquery + ")";
//			jdbcDao.executeUpdate(populateTableSql);
//			jdbcDao.commit();
					
			// NO NEED NOW
			// QueryModuleSqlUtil.executeCreateTable(tempTableName, xquery, null);
			System.out.println("Step 3. Executed XQuery on DB and ITABLE Populated....");
			
		}
		catch (MultipleRootsException ex)
		{
			System.out
					.println("Step 3. MultipleRootsException encountered while generating XQuery....");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}
		catch (SqlException ex)
		{
			System.out.println("Step 3. SqlException encountered while generating XQuery....");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}
		/*catch (DAOException e)
		{
			System.out.println("Step 3. Could not Populate Table with name.... '"
					+ tempTableName + "'");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw e;
		}
		finally
		{
			jdbcDao.closeSession();
		}
		catch (DAOException ex)
		{
			System.out.println("Step 3. Could not create Temp Table with name.... '"
					+ tempTableName + "'");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}
		catch (QueryModuleException ex)
		{
			System.out.println("Step 3. Could not create Temp Table with name.... '"
					+ tempTableName + "'");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}*/
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryExecId;
	}

	/**
	 * 
	 * @param fileName1 Name of first file to be compared
	 * @param fileName2 Name of second file to be compared
	 * @return
	 * @throws IOException 
	 */
	public static boolean compareFiles(String fileName1, String fileName2) throws IOException
	{
		boolean result = false;
		BufferedReader input1 = null, input2 = null;
		Map<Integer, String> file1Details, file2Details;
		int count1 = 0, count2 = 0;

		try
		{
			File file1 = new File(Constants.testHome + fileName1);
			File file2 = new File(Constants.testHome + fileName2);

			input1 = new BufferedReader(new FileReader(file1));
			input2 = new BufferedReader(new FileReader(file2));

			String line = null;

			file1Details = new HashMap<Integer, String>();
			file2Details = new HashMap<Integer, String>();

			while ((line = input1.readLine()) != null)
			{
				count1++;
				file1Details.put(count1, line);
			}

			while ((line = input2.readLine()) != null)
			{
				count2++;
				file2Details.put(count2, line);
			}

			if (file1Details.size() == file2Details.size())
			{
				result = true;

				for (int count = 0; count < file1Details.size(); count++)
				{
					if (!file1Details.get(count + 1).equals(file2Details.get(count + 1)))
					{
						result = false;
						break;
					}
				}
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Step 5. A FileNotFound Exception has been encountered....");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}
		catch (IOException ex)
		{
			System.out.println("Step 5. An I/O Exception has been encountered....");
			System.out.println("\t Remaining Steps cannot be executed....");
			throw ex;
		}

		if (!result)
		{
			System.out.println("Step 5. Validating Results.... Test Case Result :: FAIL");
			System.out.println("\t ACTUAL & EXPECTED RESULTS DO NOT MATCH ....");
			System.out.println("\t No. of Records Retrieved by Query ::  " + file1Details.size());
			System.out.println("\t No. of Records in Expected Results Table ::  "
					+ file2Details.size());
		}
		else
		{
			System.out.println("Step 5. Validating Results.... Test Case Result :: SUCCESS");
			System.out.println("\t No. of Records :: " + file1Details.size());
			System.out.println("\t ACTUAL & EXPECTED RESULTS MATCH ....");
		}

		return result;
	}

	/**
	 * This method will export a table into a CSV  
	 * @param tableName Table whose records are to be exported
	 * @param csvFileName CSV File in which records are to be inserted  
	 * @throws Exception 
	 */
	public static void exportResultsToCSV(String tableName, String csvFileName, Long queryExecId) throws Exception
	{
		Connection con = null;

		int rows_exported;
		String msg_retrieval = null;
		String msg_removal = null;
		String sqlcode = null;
		String msg = null;

		CallableStatement callStmt1 = null;
		ResultSet rs1 = null;
		PreparedStatement stmt1 = null;
		ResultSet rs2 = null;
		CallableStatement callStmt2 = null;
		
		try
		{
			Properties props = new Properties();
			props.put("user", x.dbUserName);
			props.put("password", x.dbPassword);

			// initialize DB2Driver and establish database connection.
			com.ibm.db2.jcc.DB2Driver db2Driver = (com.ibm.db2.jcc.DB2Driver) Class.forName(
					"com.ibm.db2.jcc.DB2Driver").newInstance();

			String URL = "jdbc:" + x.dbType + "://" + x.dbHost + ":" + x.dbPort + "/" + x.dbName
					+ ":currentSchema=" + x.dbSchema + ";";
			con = DriverManager.getConnection(URL, props);

			String sql = "CALL SYSPROC.ADMIN_CMD(?)";
			callStmt1 = con.prepareCall(sql);

//			String param = "export to " + Constants.testHome + csvFileName;
//			param = param + " of DEL messages on server select * from " + tableName;

			String param = "export to " + Constants.testHome + csvFileName;
			param = param + " of DEL messages on server select UPI from " + tableName+ " where COUNT_QUERY_EXECUTION_ID="+queryExecId + " order by UPI";
			
			// set the input parameter
			callStmt1.setString(1, param);

			// execute export by calling ADMIN_CMD
			callStmt1.execute();
			rs1 = callStmt1.getResultSet();
			// retrieve the result set  
			if (rs1.next())
			{
				// the numbers of rows exported
				rows_exported = rs1.getInt(1);

				// retrieve the select stmt for message retrieval 
				// containing SYSPROC.ADMIN_GET_MSGS
				msg_retrieval = rs1.getString(2);

				// retrieve the stmt for message cleanup
				// containing CALL of SYSPROC.ADMIN_REMOVE_MSGS
				msg_removal = rs1.getString(3);

				// display the output
				System.out.println("Step 4. Exporting Results to CSV....");

				System.out.println("\t Total number of rows exported  : " + rows_exported);
				//// System.out.println("Script for retrieving the messages: " + msg_retrieval); 
				//// System.out.println("Script for removing the messages  : " + msg_removal);
			}

			stmt1 = con.prepareStatement(msg_retrieval);
			//// System.out.println("\n" + "Executing " + msg_retrieval);  

			rs2 = stmt1.executeQuery();

			while (rs2.next())
			{
				sqlcode = rs2.getString(1);

				msg = rs2.getString(2);
				//// System.out.println("Sqlcode : " +sqlcode);
				//// System.out.println("Msg     : " +msg);
			}

			///// System.out.println("\nExecuting " + msg_removal);
			callStmt2 = con.prepareCall(msg_removal);

			callStmt2.execute();
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			try
			{
				// close the statements 
				callStmt1.close();
				callStmt2.close();
				stmt1.close();

				// close the result sets
				rs1.close();
				rs2.close();

				// roll back any changes to the database made by this sample
				con.rollback();

				// close the connection                                   
				con.close();
			}
			catch (Exception x)
			{
				System.out.print("Step 4. Unable to Rollback/Disconnect ");
				System.out.println("\t from 'TEST1' database");
			}
		}
	}

	/**
	 * 
	 * @param csvFileName
	 * @param operation
	 * @throws DAOException
	 * @throws IOException
	 */
	public static void formAndExecuteScriptFromCSV(String csvFileName, String operation)
			throws DAOException, IOException
	{
		BufferedReader input = null;
		String tableName = csvFileName.split("_")[0];
		String script = null;

		try
		{
			/*if(operation.equals(Constants.addOperation))
			{
				System.out.println("Step 1. Executing Scripts from CSV...."+csvFileName);
				System.out.println("\t Initializing DB for table .... "+tableName);
				QueryUtility.importFromCSV(tableName, csvFileName);
			}
			else	
			{*/
			if (operation.equals(edu.wustl.query.bizlogic.Constants.deleteOperation))
			{
				// System.out.println("\t Executing Scripts from CSV...."+csvFileName);
				System.out.println("\t Cleaning DB for table .... " + tableName);
				script = "DELETE FROM " + tableName;
				executeScriptOnDB(script);
			}
			else
			{
				System.out.println("\t Initializing Table " + tableName
						+ "..... \n\t  with data in File.... " + csvFileName);

				File file = new File(Constants.testHome + csvFileName);
				input = new BufferedReader(new FileReader(file));
				String line = null;

				while ((line = input.readLine()) != null)
				{
					line = line.replaceAll("\"", "'");
					if (operation.equals(Constants.addOperation))
					{
						script = "INSERT INTO " + tableName + " VALUES (" + line + ")";
					}
					/*else
					if(operation.equals(Constants.deleteOperation))
					{
						String value = line.split(",")[0];
						script = "DELETE FROM "+tableName+" WHERE "+ "UPI" +"="+value;
					}*/
					executeScriptOnDB(script);
				}
			}
			// }
		}
		catch (IOException ex)
		{
			System.out.println("An I/O Exception has occurred....");
			// System.out.println("Steps 3, 4 and 5 cannot be executed....");
			throw ex;
		}
	}

	private static void executeScriptOnDB(String script) throws DAOException
	{
		if (script != null)
		{
			//JDBCDAO jdbcDao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
			JDBCDAO jdbcDao =DAOUtil.getJDBCDAO(null);

			try
			{
				jdbcDao.openSession(null);
				jdbcDao.executeUpdate(script);
				jdbcDao.commit();

				// System.out.println("SCRIPT ..... "+script+"  EXECUTED SUCCESSFULLY....");
			}
			catch (DAOException ex)
			{
				System.out.println("Step 6. EXCEPTION occurred while executing script ...."
						+ script);
				System.out.println("Remaining steps cannot be executed....");
				throw ex;
			}
			finally
			{
				//jdbcDao.closeSession();
				DAOUtil.closeJDBCDAO(jdbcDao);
			}
		}
	}

	/**
	 * Used to read 'FrameWork.xml'
	 * @param fileName
	 */
	public void readXmlFile(String fileName)
	{
		try
		{
			String xmlFileName = fileName;

			InputStream inputXmlFile = this.getClass().getClassLoader().getResourceAsStream(
					xmlFileName);

			if (inputXmlFile != null)
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(inputXmlFile);

				Element root = null;
				root = doc.getDocumentElement();

				if (root == null)
				{
					throw new Exception("file cannot be read");
				}

				dbInitCommonScriptsFile = root.getAttribute("dbInitCommonScriptsFile");
				dbCleanUpCommonScriptsFile = root.getAttribute("dbCleanUpCommonScriptsFile");

				path = root.getAttribute("testHome");
				Constants.testHome = path;

				NodeList nodeList = doc.getElementsByTagName(Constants.TestCase);
				noOfTestCases = nodeList.getLength();

				System.out.println();
				System.out.println("********* Total No of TC's ********* :: " + noOfTestCases);
				System.out.println();

				String query = null, tmtTestCaseId = null, dbInitCSV = null, expectedResultsCSV = null;
				String dbCleanUpCSV = null, queryInputFile = null, queryId = null, type = null;

				for (int count = 0; count < noOfTestCases; count++)
				{
					Map<String, String> details = new HashMap<String, String>();
					Node node = nodeList.item(count);

					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						Element element = (Element) node;

						query = getTagValue(Constants.name, element);
						tmtTestCaseId = getTagValue(Constants.tmtTestCaseId, element);
						dbInitCSV = getTagValue(Constants.dbInitCSV, element);
						expectedResultsCSV = getTagValue(Constants.expectedResultsCSV, element);
						dbCleanUpCSV = getTagValue(Constants.dbCleanUpCSV, element);

						NodeList savedQueryNode = element.getElementsByTagName(Constants.query);
						Element savedQueryNodeElmnt = (Element) savedQueryNode.item(0);
						type = savedQueryNodeElmnt.getAttribute(Constants.queryType);
						if (type.equals(Constants.saved_query))
						{
							queryId = savedQueryNodeElmnt.getAttribute(Constants.queryId);
							queryInputFile = null;
						}

						if (type.equals(Constants.serialized_query))
						{
							queryInputFile = savedQueryNodeElmnt
									.getAttribute(Constants.queryInputFile);
							queryId = null;
						}
					}

					details.put(Constants.name, query);
					details.put(Constants.tmtTestCaseId, tmtTestCaseId);
					details.put(Constants.dbInitCSV, dbInitCSV);
					details.put(Constants.expectedResultsCSV, expectedResultsCSV);
					details.put(Constants.dbCleanUpCSV, dbCleanUpCSV);
					details.put(Constants.queryInputFile, queryInputFile);
					details.put(Constants.queryId, queryId);

					xmlDetailsMap.put(count + 1, details);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("XML reading done ........");
	}

	/**
	 * To test to form DeSerialized Query from a serialized 1
	 * stored in a file
	 * 
	 */
	public static void serializeIQueryToFile(String fileName, IQuery query)
	{
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try
		{
			// To serialize IQuery object into a file
			fileName = Constants.testHome + fileName;

			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(query);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private String getTagValue(String tag, Element element)
	{
		if (element.getElementsByTagName(tag).getLength() == 0)
		{
			return null;
		}
		NodeList nlList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	/**
	 * This method is used to run scripts which are
	 * common to all the Test Cases 
	 * @param scriptFileName
	 * @throws DAOException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void runSQLScriptsOnDB(String scriptFileName) throws DAOException,
			FileNotFoundException, IOException
	{
		BufferedReader input = null;
		JDBCDAO jdbcDao = null;
		try
		{
			File tempfile = new File(Constants.testHome + scriptFileName);
			input = new BufferedReader(new FileReader(tempfile));

			String line = null;

			jdbcDao = DAOUtil.getJDBCDAO(null);

			while ((line = input.readLine()) != null)
			{
				jdbcDao.executeUpdate(line);
			}

			System.out
					.println("PRE-REQUISITE: Running Common scripts.... Running scripts from file.. "
							+ scriptFileName);
			jdbcDao.commit();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("PRE-REQUISITE: Running Common scripts.... File '" + scriptFileName
					+ "' NOT FOUND....");
			System.out.println("Remaining steps cannot be executed....");
			throw ex;
		}
		catch (IOException ex)
		{
			System.out
					.println("PRE-REQUISITE: Running Common scripts.... \n An exception has been encountered.. "
							+ "An IO Exception has occurred while reading from file '"
							+ scriptFileName + "'");
			System.out.println("Remaining steps cannot be executed....");
			throw ex;
		}
		catch (DAOException ex)
		{
			System.out
					.println("PRE-REQUISITE: Running Common scripts.... \n An exception has been encountered.. "
							+ "Check scripts from file.. " + scriptFileName);
			System.out.println("Remaining steps cannot be executed....");
			throw ex;
		}
		finally
		{
			DAOUtil.closeJDBCDAO(jdbcDao);
		}
	}

	/**
	 * 
	 * @param targetTableName
	 * @param sourceCSVFileName
	 */
	public static void importFromCSV(String targetTableName, String sourceCSVFileName)
	{
		Connection con = null;

		String msg_retrieval = null;
		String msg_removal = null;
		String sqlcode = null;
		String msg = null;

		CallableStatement callStmt1 = null;
		ResultSet rs1 = null;
		PreparedStatement stmt1 = null;
		ResultSet rs2 = null;
		CallableStatement callStmt2 = null;

		int rows_read;
		int rows_skipped;
		int rows_loaded;
		int rows_rejected;
		int rows_deleted;
		int rows_committed;

		try
		{
			Properties props = new Properties();
			// props.put("user",     "db2admin");
			// props.put("password", "chak11##");
			props.put("user", x.dbUserName);
			props.put("password", x.dbPassword);

			// initialize DB2Driver and establish database connection.
			com.ibm.db2.jcc.DB2Driver db2Driver = (com.ibm.db2.jcc.DB2Driver) Class.forName(
					"com.ibm.db2.jcc.DB2Driver").newInstance();

			String URL = "jdbc:" + x.dbType + "://" + x.dbHost + ":" + x.dbPort + "/" + x.dbName
					+ ":currentSchema=" + x.dbSchema + ";";
			// con = DriverManager.getConnection("jdbc:db2://localhost:50000/TEST1:currentSchema=DB2ADMIN;", props);
			con = DriverManager.getConnection(URL, props);

			//// System.out.println("HOW TO PERFORM IMPORT USING ADMIN_CMD.\n");
			// prepare the CALL statement for OUT_LANGUAGE
			String sql = "CALL SYSPROC.ADMIN_CMD(?)";
			callStmt1 = con.prepareCall(sql);

			String param = "IMPORT FROM " + Constants.testHome + sourceCSVFileName;
			param = param + " of DEL messages on server insert into " + targetTableName
					+ " (UPI, XMLDATA)";

			// set the input parameter
			callStmt1.setString(1, param);
			//// System.out.println("CALL ADMIN_CMD('" + param + "')");

			// execute export by calling ADMIN_CMD
			callStmt1.execute();
			rs1 = callStmt1.getResultSet();
			// retrieve the result set  
			if (rs1.next())
			{
				// retrieve the no of rows read
				rows_read = rs1.getInt(1);
				// retrieve the no of rows skipped
				rows_skipped = rs1.getInt(2);
				// retrieve the no of rows loaded
				rows_loaded = rs1.getInt(3);
				// retrieve the no of rows rejected
				rows_rejected = rs1.getInt(4);
				// retrieve the no of rows deleted
				rows_deleted = rs1.getInt(5);
				// retrieve the no of rows committed
				rows_committed = rs1.getInt(6);

				// containing SYSPROC.ADMIN_GET_MSGS
				msg_retrieval = rs1.getString(7);

				// containing CALL of SYSPROC.ADMIN_REMOVE_MSGS
				msg_removal = rs1.getString(8);

				// Displaying the result set
				System.out.print("\n\t Total number of rows read      : ");
				System.out.println(rows_read);
				System.out.print("\t Total number of rows skipped   : ");
				System.out.println(rows_skipped);
				System.out.print("\t Total number of rows loaded    : ");
				System.out.println(rows_loaded);
				System.out.print("\tTotal number of rows rejected  : ");
				System.out.println(rows_rejected);
				System.out.print("\t Total number of rows deleted   : ");
				System.out.println(rows_deleted);
				System.out.print("\t Total number of rows committed : ");
				System.out.println(rows_read);
				//    	         System.out.print("SQL for retrieving the messages: "); 
				//    	         System.out.println(msg_retrieval); 
				//    	         System.out.print("SQL for removing the messages  : "); 
				//    	         System.out.println(msg_removal);
			}

			stmt1 = con.prepareStatement(msg_retrieval);
			System.out.println("\n" + "Executing " + msg_retrieval);

			rs2 = stmt1.executeQuery();

			while (rs2.next())
			{
				sqlcode = rs2.getString(1);

				msg = rs2.getString(2);
				System.out.println("Sqlcode : " + sqlcode);
				System.out.println("Msg     : " + msg);
			}

			System.out.println("\nExecuting " + msg_removal);
			callStmt2 = con.prepareCall(msg_removal);

			callStmt2.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				callStmt1.close();
				callStmt2.close();
				stmt1.close();

				rs1.close();
				rs2.close();

				con.rollback();
				con.close();
			}
			catch (Exception x)
			{
				System.out.print("\n Unable to Rollback/Disconnect ");
				System.out.println("from 'TEST1' database");
			}
		}
	}
}
