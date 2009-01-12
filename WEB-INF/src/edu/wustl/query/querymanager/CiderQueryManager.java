/**
 * 
 */

package edu.wustl.query.querymanager;

import java.sql.SQLException;

import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.query.factory.QueryGeneratorFactory;
import edu.wustl.common.query.itablemanager.CIDERITableManager;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.queryengine.impl.IQueryGenerator;
import edu.wustl.query.ExecutionManager.CIDERQueryExecutionThread;
import edu.wustl.query.ExecutionManager.QueryExecutionThread;

/**
 * @author supriya_dankh
 * 
 */
public class CiderQueryManager extends AbstractQueryManager
{

	/**
	 * @param workflow
	 * @return
	 */
	public int execute(Workflow workflow)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param query_excecution_id
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public Count getQueryCount(int query_excecution_id) throws DAOException, SQLException
	{
		Count count = null;
		CIDERITableManager manager = CIDERITableManager.getInstance();
			count = manager.getCount(query_excecution_id);
		return count;
	}

	/**
	 * @param query_excecution_id
	 * @return
	 */
	public Count getWorkflowCount(int query_excecution_id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/*
	 * To abort a Running Query
	 * 
	 * @param queryExecId
	 */
	public void cancel(int query_execution_id)
	{
		// LOGIC TO UPDATE STATUS OF THREAD in iTABLE LOG
		// Stop Thread corresponding to queryExecId

		queryThreads.remove(query_execution_id);

	}

	/**
	 * 
	 * @param ciderQueryObj
	 * @return
	 * @throws MultipleRootsException
	 * @throws SQLException
	 * @throws DAOException
	 * @throws SqlException
	 */
	public int execute(CiderQuery ciderQueryObj) throws MultipleRootsException, SqlException
	{
		// String xQuery = "select personUpi_1 Column0 ,id_1 Column1 ,dateOfDeath_2 Column2 ,dateOfBirth_2 Column3 ,id_2 Column4 ,effectiveEndTimeStamp_2 Column5 ,effectiveStartTimeStamp_2 Column6 ,mothersMaidenName_2 Column7 ,placeOfBirth_2 Column8 ,socialSecurityNumber_2 Column9 from xmltable(' for $Person_1 in db2-fn:xmlcolumn(\"DEMOGRAPHICS.XMLDATA\")/Person ,$Demographics_2 in $Person_1/demographicsCollection/demographics where (exists($Person_1/personUpi)) and($Demographics_2/dateOfBirth>xs:dateTime(\"1920-10-10T23:59:59\") )  return <return><Person_1>{$Person_1}</Person_1><Demographics_2>{$Demographics_2}</Demographics_2></return>' columns personUpi_1 varchar(1000) path 'Person_1/Person/personUpi' ,id_1 DECIMAL(31) path 'Person_1/Person/id' ,dateOfDeath_2 TimeStamp path 'Demographics_2/demographics/dateOfDeath' ,dateOfBirth_2 TimeStamp path 'Demographics_2/demographics/dateOfBirth' ,id_2 DECIMAL(31) path 'Demographics_2/demographics/id' ,effectiveEndTimeStamp_2 TimeStamp path 'Demographics_2/demographics/effectiveEndTimeStamp' ,effectiveStartTimeStamp_2 TimeStamp path 'Demographics_2/demographics/effectiveStartTimeStamp' ,mothersMaidenName_2 varchar(1000) path 'Demographics_2/demographics/mothersMaidenName' ,placeOfBirth_2 varchar(1000) path 'Demographics_2/demographics/placeOfBirth' ,socialSecurityNumber_2 varchar(1000) path 'Demographics_2/demographics/socialSecurityNumber')";

		int queryExecId = -1;
		try
		{
			IQueryGenerator queryGenerator = QueryGeneratorFactory
					.getDefaultQueryGenerator();
			String generatedQuery = queryGenerator.generateQuery(ciderQueryObj
					.getQuery());

			Thread thread = null;
			QueryExecutionThread queryExecutionThread = null;

			queryExecId = CIDERQueryExecutionThread.getQueryExecutionId(ciderQueryObj);
			ciderQueryObj.setQueryExecId(queryExecId);
			ciderQueryObj.setQueryString(generatedQuery);

			thread = new Thread(new CIDERQueryExecutionThread(ciderQueryObj));

			queryThreads.put(queryExecId, queryExecutionThread);

			thread.start();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (SqlException e)
		{
			throw e;
		}
		finally
		{
			// close connection
			// close result set

			// UPDATE STATUS IN iTABLE LOG AS COMPLETED
			// call thread.stop
			queryThreads.remove(queryExecId);
		}

		return queryExecId;
	}
}
