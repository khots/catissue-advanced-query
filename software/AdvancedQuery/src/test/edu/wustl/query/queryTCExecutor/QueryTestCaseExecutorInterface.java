package edu.wustl.query.queryTCExecutor;

import java.io.IOException;
import java.util.Map;

import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * 
 * @author ravindra_jain
 * @version 1.0
 * @creation date: 26th December, 2008
 */

public interface QueryTestCaseExecutorInterface
{
	/**
	 * import CSV specified in dbInitScriptsFile   
	 * @param csvFileName
	 * @throws IOException 
	 * @throws DAOException 
	 */
	public void preprocess(String csvFileName) throws DAOException, IOException;
	
	/**
	 * 
	 * @param queryId
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws DAOException 
	 * @throws QueryModuleException 
	 * @throws SqlException 
	 * @throws MultipleRootsException 
	 */
	public Long executeQuery(Long queryId) throws DAOException, IOException, ClassNotFoundException, MultipleRootsException, SqlException, QueryModuleException;
	
	/**
	 * 
	 * @param fileName
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws QueryModuleException 
	 * @throws DAOException 
	 * @throws SqlException 
	 * @throws MultipleRootsException 
	 */
	public Long executeQuery(String fileName) throws IOException, ClassNotFoundException, MultipleRootsException, SqlException, DAOException, QueryModuleException;
	
	/**
	 * import CSV specified in dbInitScriptsFile into the EXPECTED_RESULTS table
     * and call compareResults()
     * 
	 * @param actualResultsCSVFileName
	 * @param expectedresultsCSVFileName
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	public boolean validateResults(String actualResultsCSVFileName, String expectedresultsCSVFileName, Long queryExecId) throws IOException, Exception; 
	
	/**
	 * run cleanup scripts specified in CSV
	 * @param csvFileName
	 * @throws IOException 
	 * @throws DAOException 
	 */
	public void cleanUp(String csvFileName) throws DAOException, IOException;
	
	/**
	 * 
	 * @param details
	 * @return
	 * @throws DAOException
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws MultipleRootsException
	 * @throws SqlException
	 * @throws QueryModuleException
	 * @throws Exception 
	 */
	public boolean executeTestCase(Map<String, String> details) throws DAOException, IOException, NumberFormatException, ClassNotFoundException, MultipleRootsException, SqlException, QueryModuleException, Exception;
}

