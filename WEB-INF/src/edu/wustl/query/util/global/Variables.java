
package edu.wustl.query.util.global;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Variables //extends edu.wustl.common.util.global.Variables
{

	public static int maximumTreeNodeLimit;
	public static Map<String, String> aliasAndPageOfMap = new HashMap<String, String>();
	public static String queryGeneratorClassName = "";
	public static Properties properties;

	public static String abstractQueryClassName = "";
	public static String abstractQueryManagerClassName = "";
	public static String queryUIManagerClassName = "";
	public static String queryITableManagerClassName = "";
	public static String viewIQueryGeneratorClassName = "";
	public static String spreadSheetGeneratorClassName = "";

	public static int recordsPerPageForSpreadSheet;
	public static int recordsPerPageForTree;
	public static int ajaxCallSleepTime;
	public static int gridDisplaySleepTime;
	public static int dataQueryQueueSize;
	public static long dataQueryThreadWaitTime;
	public static int maxtreeExpansionLimit;
	public static String urlAccessValidator;
	public static int resultLimit;
	public static String prepareColTypes(List dataColl)
	{
		return prepareColTypes(dataColl, false);
	}

	public static String prepareColTypes(List dataColl, boolean createCheckBoxColumn)
	{
		String colType = "";
		StringBuffer tempcolType = new StringBuffer("");
		if (dataColl != null && !dataColl.isEmpty())
		{
			List rowDataColl =  dataColl;

			if (createCheckBoxColumn == true)
			{
				colType = "ch,";
			}

		    tempcolType = tempcolType.append(colType);	
		    tempcolType = processRowDataCol(tempcolType, rowDataColl);
		}

		colType = tempcolType.toString();
		if (colType.length() > 0)
		{
			colType = colType.substring(0, colType.length() - 1);
		}
		return colType;
	}

	private static StringBuffer processRowDataCol(StringBuffer tempcolType, List rowDataColl)
	{
		Iterator it = rowDataColl.iterator();
		while (it.hasNext())
		{

			Object obj = it.next();
			if (obj != null && obj instanceof Number)
			{
				tempcolType = tempcolType.append("int,");
			}
			else if (obj != null && obj instanceof Date)
			{
				tempcolType = tempcolType.append("date,");
			}
			else
			{
				tempcolType = tempcolType.append("str,");
			}
		}
		return tempcolType;
	}

	/**
	 * Used for inserting data in batches - Statement.executeBatch()
	 */
	public static int batchSize;

	/**
	 * Query ITABLE
	 */
	public static final String ITABLE = "QUERY_ITABLE";

	/**
	 * QUERY EXECUTION LOG TABLE
	 */
	public static final String QUERY_EXECUTION_LOG = "QUERY_EXECUTION_LOG";
	/** temp variable **/
	public static int temp = 1;
	/**
	  * for not Logging XQueries test cases get executed
	  **/
	public static boolean isExecutingTestCase = false;

	/** Thread class used for Exporting data for get Data Query **/
	public static String exportDataThreadClassName = "";
	/** Used to get executor for get Data Query **/
	public static String dataQueryExecutionClassName = "";
	/** Export Home - Path where export results will be stored **/
	public static String exportHome = "";
	/**
	 * specify queryReadDeniedObjectList.
	 */
	public static List<String> queryReadDeniedObjectList = new ArrayList<String>();

	/**
	 * specify CONST_VARIABLES_VALUE.
	 */
	private static final String CONST_VARIABLES_VALUE = "";


	/**
	 * specify applicationName.
	 */
	public static String applicationHome = CONST_VARIABLES_VALUE;
	public static String propertiesDirPath = CONST_VARIABLES_VALUE;
	public static String applicationName = Constants.APPLICATION_NAME;
	public static String applicationVersion = CONST_VARIABLES_VALUE;

	/**
	 * limit of records for count
	 */
	public static Long limitForCount;
	/**
	 * limit of records for data
	 */
	public static Long limitForData;
	/**
	 * flag to send email on failure
	 */
	public static boolean sendEmailOnFailure;
	/**
	 * email addresses array.
	 */
	public static String[] emailAddresses;

}
