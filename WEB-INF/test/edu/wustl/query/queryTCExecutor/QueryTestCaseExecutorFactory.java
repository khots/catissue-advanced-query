package edu.wustl.query.queryTCExecutor;

import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.Constants;

/**
 * BizLogicFactory is a factory for DAO instances of various domain objects.
 * @author gautam_shetty
 */
public class QueryTestCaseExecutorFactory
{
	//Singleton instance
	private static QueryTestCaseExecutorFactory factory = null;
	
	
	static
	{
		factory = new QueryTestCaseExecutorFactory();
	}
	
	protected QueryTestCaseExecutorFactory()
	{
	}
	
	public static void setBizLogicFactory(QueryTestCaseExecutorFactory externalFactory)
	{
		factory = externalFactory;
	}
	
	public static QueryTestCaseExecutorFactory getInstance()
	{
		return factory;
	}
	
    /**
     * 
     * @param DATABASE_TYPE
     * @return
     */
	public QueryTestCaseExecutorInterface getTestCaseExecutor(String DATABASE_TYPE)
    {
		Logger.out.debug("In QueryTestCaseExecutorFactory , DATABASE TYPE : "+DATABASE_TYPE);
		
		QueryTestCaseExecutorInterface testCaseExecutor = null;
        
		if(DATABASE_TYPE.equals(Constants.DB2))
		{
			testCaseExecutor = new DB2QueryTestCaseExecutor();
		}
		else if(DATABASE_TYPE.equals(Constants.MySQL))
		{
			testCaseExecutor = new MySQLQueryTestCaseExecutor();
		}
		else if(DATABASE_TYPE.equals(Constants.MSSQL))
		{
			testCaseExecutor = new MSSQLQueryTestCaseExecutor();
		}
		else if(DATABASE_TYPE.equals(Constants.Oracle))
		{
			testCaseExecutor = new OracleQueryTestCaseExecutor();
		}
		
        return testCaseExecutor;
    }
}
