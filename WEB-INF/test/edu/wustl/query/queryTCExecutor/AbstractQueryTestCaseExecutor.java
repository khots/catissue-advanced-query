package edu.wustl.query.queryTCExecutor;

import java.io.IOException;
import java.util.Map;

import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.bizlogic.Constants;
import edu.wustl.query.bizlogic.QueryFrameworkTestCase;
import edu.wustl.query.bizlogic.QueryUtility;
import edu.wustl.query.bizlogic.XQueryGeneratorTestCase;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * 
 * @author ravindra_jain
 * @version 1.0
 * @creation date: 26th December, 2008
 */
public abstract class AbstractQueryTestCaseExecutor implements QueryTestCaseExecutorInterface
{
	
	/**
	 * CSV File is read & tables on which query is to be fired,
	 * are populated
	 * import CSV specified in dbInitScriptsFile in XML   
	 * @param csvFileName
	 * @throws IOException 
	 * @throws DAOException 
	 */
	public abstract void preprocess(String csvFileName) throws DAOException, IOException;
	
	/**
	 * Execute XQuery & create ITable
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws DAOException 
	 * @throws QueryModuleException 
	 * @throws SqlException 
	 * @throws MultipleRootsException 
	 * @throws QueryModuleException 
	 */
	public int executeQuery(Long queryId) throws IOException, ClassNotFoundException, MultipleRootsException, SqlException, DAOException, QueryModuleException
	{
		IQuery query = QueryUtility.getQuery(queryId);
		return QueryUtility.runQueryAndPopulateITable(query);
	}
	
	/**
	 * Execute XQuery & create ITable
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws QueryModuleException 
	 * @throws DAOException 
	 * @throws SqlException 
	 * @throws MultipleRootsException 
	 * @throws DAOException 
	 * @throws QueryModuleException 
	 */
	public int executeQuery(String fileName) throws IOException, ClassNotFoundException, MultipleRootsException, SqlException, DAOException, QueryModuleException
	{
		IQuery query = QueryUtility.getQuery(fileName);
		return QueryUtility.runQueryAndPopulateITable(query);
	}
	
	/**
	 * 
     * call compareResults()
     * 
	 * @param actualResultsCSVFileName
	 * @param expectedresultsCSVFileName
	 * @return
	 * @throws Exception 
	 */
	public boolean validateResults(String actualResultsCSVFileName, String expectedResultsCSVFileName, int queryExecId) throws Exception
	{
		if(actualResultsCSVFileName != null && expectedResultsCSVFileName != null &&
				!actualResultsCSVFileName.equals("") && !expectedResultsCSVFileName.equals(""))
		{
			QueryUtility.exportResultsToCSV(QueryUtility.tempTableName, actualResultsCSVFileName, queryExecId);
		
			return compareFiles(actualResultsCSVFileName, expectedResultsCSVFileName);
		}
		return false;
	}
	
	/**
	 * run cleanup scripts specified in CSV in XML
	 * to clean-up the data which was populated for this 
	 * particular test case
	 * @param csvFileName
	 * @throws IOException 
	 * @throws DAOException 
	 */
	public abstract void cleanUp(String csvFileName) throws DAOException, IOException;
	
	/**
	 * 
	 * @param fileName1
	 * @param fileName2
	 * @return
	 * @throws IOException 
	 */
	private boolean compareFiles(String fileName1, String fileName2) throws IOException
	{	
		boolean equals = false;
		
		equals = QueryUtility.compareFiles(fileName1, fileName2);
		
		return equals;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	public boolean executeTestCase(Map<String, String> details) throws Exception
	{
		int queryExecId = -1;
		System.out.println("\n");
        System.out.println("    *************************************************************");
        System.out.println("TEST CASE FOR ...."+details.get(Constants.name));
        System.out.println("TMT Test Case Id :: "+details.get(Constants.tmtTestCaseId));
    	System.out.println();

    	// PRE- PROCESS
    	preprocess(details.get(Constants.dbInitCSV));
		
    	
    	// EXECUTE
    	if(details.get(Constants.queryId) != null)
    	{
    		queryExecId = executeQuery(Long.valueOf(details.get(Constants.queryId)));
    	}
    	else
    	{
    		queryExecId = executeQuery(details.get(Constants.queryInputFile));
    	}
    	
    	
    	//  VALIDATE  RESULTS
    	   // boolean isSuccessful = validateResults(Constants.actualResultsCSVFileName, details.get(Constants.expectedResultsCSV));
    	boolean isSuccessful = validateResults("TC"+QueryFrameworkTestCase.count+"_ActualResults.csv", details.get(Constants.expectedResultsCSV), queryExecId); 
    	
        //  CLEAN UP
		cleanUp(details.get(Constants.dbCleanUpCSV));
	    
	    return  isSuccessful;
	}
	
}
