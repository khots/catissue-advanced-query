package edu.wustl.query.queryTCExecutor;

import java.io.IOException;

import edu.wustl.common.query.impl.QueryUtility;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.Constants;


public class DB2QueryTestCaseExecutor extends AbstractQueryTestCaseExecutor
{

	@Override
	public void preprocess(String csvFileName) throws DAOException, IOException
	{
		// System.out.println("IN DB2 - PRE_PROCESS METHOD");
		System.out.println("Step 1. PRE-PROCESS ");
		
		if(csvFileName != null && !csvFileName.equals(""))
		{
			QueryUtility.formAndExecuteScriptFromCSV(csvFileName, Constants.addOperation);
		}
	}

	
	@Override
	public void cleanUp(String csvFileName) throws DAOException, IOException
	{
		// System.out.println("IN DB2 - CLEAN_UP METHOD");
		System.out.println("Step 6. CLEAN UP ");
		
		if(csvFileName != null && !csvFileName.equals(""))
		{
			QueryUtility.formAndExecuteScriptFromCSV(csvFileName, Constants.deleteOperation);
		}
	}


}

