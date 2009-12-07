package edu.wustl.query.bizlogic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import oracle.sql.CLOB;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.util.PagenatedResultData;
import edu.wustl.common.util.QueryParams;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.executor.AbstractQueryExecutor;
import edu.wustl.query.util.global.Utility;
import edu.wustl.security.exception.SMException;

/**
 * This class contains the methods required in Simple Query and Advanced Query.
 * @author deepti_shelar
 *
 */
public class CommonQueryBizLogic
{
	/**
	 * Method to execute the given SQL to get the query result.
	 * @param sessionDataBean reference to SessionDataBean object
	 * @param querySessionData query Session Data.
	 * @param startIndex The Starting index of the result set.
	 * @return The reference to PagenatedResultData, which contains the Query result information.
	 * @throws DAOException generic DAOException.
	 */
	public PagenatedResultData execute(SessionDataBean sessionDataBean,
			QuerySessionData querySessionData, int startIndex) throws DAOException
	{
		IDAOFactory daofactory = DAOConfigFactory.getInstance().getDAOFactory();
		JDBCDAO dao = daofactory.getJDBCDAO();

		try
		{
			dao.openSession(null);
			QueryParams queryParams = new QueryParams();
			queryParams.setQuery(querySessionData.getSql());
			queryParams.setSessionDataBean(sessionDataBean);
			queryParams.setSecureToExecute(querySessionData.isSecureExecute());
			queryParams.setHasConditionOnIdentifiedField(
					querySessionData.isHasConditionOnIdentifiedField());
			queryParams.setQueryResultObjectDataMap(querySessionData.getQueryResultObjectDataMap());
			queryParams.setStartIndex(startIndex);
			queryParams.setNoOfRecords(querySessionData.getRecordsPerPage());
			AbstractQueryExecutor queryExecutor = Utility.getQueryExecutor();
			edu.wustl.common.util.PagenatedResultData pagenatedResultData =
				queryExecutor.getQueryResultList(queryParams);

			return pagenatedResultData;
		}
		catch (SMException exp)
		{
			ErrorKey errorKey = ErrorKey.getErrorKey("biz.exequery.error");
			throw new DAOException(errorKey, exp,"CommonQueryBizLogic");
		}
		finally
		{
			dao.closeSession();
		}
	}
	/**
	 * Audits Query.
	 * @param sqlQuery sql Query.
	 * @param sessionData session Data.
	 * @throws DAOException generic DAOException.
	 * @return auditEventId Audit event id
	 */
	public long insertQuery(String sqlQuery, SessionDataBean sessionData) throws DAOException
	{
		long auditEventId = 1;
		String appName=CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daofactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDAO = daofactory.getJDBCDAO();
		jdbcDAO.openSession(null);
		try
		{
			String sqlQuery1 = sqlQuery;//.replaceAll("'", "''");
			String comments = "Advance Query";
			if("ORACLE".equalsIgnoreCase(daofactory.getDataBaseType()))
			{
				auditEventId = executeAuditSqlForOracle(sqlQuery, sessionData, comments,jdbcDAO);
			}
			else
			{
				//MYSQL,MSSQLSERVER
				auditEventId = executeAuditSqlMySQL(sqlQuery1, sessionData,
						comments,jdbcDAO);
			}
			jdbcDAO.commit();
		}
		catch (DAOException e)
		{
			throw e;
		}
		finally
		{
			jdbcDAO.closeSession();
		}
		return auditEventId;
	}
	/**
	 * This method inserts sql statement in audit tables.
	 * @param sqlQuery1 sql to be audited
	 * @param sessionData A data bean that contains information related to user logged in.
	 * @param comments comments to be inserted in audit tables
	 * @param jdbcDAO JDBCDAO object
	 * @return auditEventId Audit event id
	 * @throws DAOException Exception to be thrown
	 */
	private long executeAuditSqlMySQL(String sqlQuery1, SessionDataBean sessionData,
			String comments,JDBCDAO jdbcDAO) throws DAOException
	{
		long auditEventId = -1;
		SimpleDateFormat fSDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStamp = fSDateFormat.format(new Date());
		String ipAddress = sessionData.getIpAddress();

		String userId = sessionData.getUserId().toString();
		String sqlForAudiEvent = "insert into catissue_audit_event" +
				"(IP_ADDRESS,EVENT_TIMESTAMP,USER_ID ,COMMENTS) values ('"
			+ ipAddress + "','" + timeStamp + "','" + userId + "','" + comments + "')";
		jdbcDAO.executeUpdate(sqlForAudiEvent);

		String sql = "select max(identifier) from catissue_audit_event where USER_ID='"
			+ userId + "'";

		List list;
			list = jdbcDAO.executeQuery(sql);
			if (!list.isEmpty())
			{

				List columnList = (List) list.get(0);
				if (!columnList.isEmpty())
				{
					String str = (String) columnList.get(0);
					if (!"".equals(str))
					{
						auditEventId = Long.parseLong(str);
					}
				}
			}
			String sqlForQueryLog = "insert into catissue_audit_event_query_log" +
					"(QUERY_DETAILS,AUDIT_EVENT_ID) values (\""
				+ sqlQuery1 + "\",'" + auditEventId + "')";
			Logger.out.debug("sqlForQueryLog:" + sqlForQueryLog);
			jdbcDAO.executeUpdate(sqlForQueryLog);
			return auditEventId ;
	}

	/**
	 * This method inserts sql statement in audit tables.
	 * @param sqlQuery sql to be audited
	 * @param sessionData
	 * @param comments comments to be inserted in audit tables
	 * @throws DAOException Exception to be thrown
	 */
	public long executeAuditSqlForOracle(String sqlQuery, SessionDataBean sessionData, String comments,JDBCDAO jdbcDAO)
			throws DAOException
	{
		long auditEventId = -1;
		String sql = "select CATISSUE_AUDIT_EVENT_PARAM_SEQ.nextVal from dual";
		try
		{
			List list = jdbcDAO.executeQuery(sql);
			if (!list.isEmpty())
			{
				List columnList = (List) list.get(0);
				if (!columnList.isEmpty())
				{
					String str = (String) columnList.get(0);
					if (!"".equals(str))
					{
						auditEventId = Long.parseLong(str);
					}
				}
			}
			long queryNo = executeAuditEventQuery(comments, jdbcDAO,
					auditEventId, sessionData);

			executeQueryLogSql(sqlQuery, jdbcDAO, auditEventId, queryNo);
		}
		catch (IOException e)
		{
			throw new DAOException(ErrorKey.getErrorKey("errors.item"), e,
			"Failed while writing to output stream.");
		}
		catch (SQLException e)
		{
			throw new DAOException(ErrorKey.getErrorKey("errors.item"), e,
			"Failed while getting output stream from the CLOB object");
		}
		return auditEventId;
	}

	/**
	 * Insert details into catissue_audit_event_query_log table.
	 * @param sqlQuery sqlQuery
	 * @param jdbcDAO jdbcDAO
	 * @param auditEventId auditEventId
	 * @param queryNo queryNo
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 * @throws IOException IOException
	 */
	private void executeQueryLogSql(String sqlQuery, JDBCDAO jdbcDAO,
			long auditEventId, long queryNo) throws DAOException, SQLException,
			IOException
	{
		List list;
		String sqlForQueryLog = "insert into catissue_audit_event_query_log" +
				"(IDENTIFIER,QUERY_DETAILS,AUDIT_EVENT_ID) "
				+ "values (" + queryNo + ",EMPTY_CLOB(),'" + auditEventId + "')";
		jdbcDAO.executeUpdate(sqlForQueryLog);
		String sql1 = "select QUERY_DETAILS from catissue_audit_event_query_log where IDENTIFIER="
				+ queryNo + " for update";

		list = jdbcDAO.executeQuery(sql1);
		CLOB clob = null;

		if (!list.isEmpty())
		{
			List columnList = (List) list.get(0);
			if (!columnList.isEmpty())
			{
				clob = (CLOB) columnList.get(0);
			}
		}
		//			get output stream from the CLOB object
		OutputStream ostream = clob.getAsciiOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(ostream);

		//		use that output stream to write character data to the Oracle data store
		osw.write(sqlQuery.toCharArray());
		//write data and commit
		osw.flush();
		osw.close();
		ostream.close();
		Logger.out.info("sqlForQueryLog:" + sqlForQueryLog);
	}

	/**
	 * Insert the query details in catissue_audit_event table.
	 * @param comments comments
	 * @param jdbcDAO jdbcDAO
	 * @param auditEventId auditEventId
	 * @param sessionData A data bean that contains information related to user logged in.
	 * @return queryNo
	 * @throws DAOException DAOException
	 */
	private long executeAuditEventQuery(String comments, JDBCDAO jdbcDAO,
			long auditEventId, SessionDataBean sessionData)
			throws DAOException
		{
		String ipAddress = sessionData.getIpAddress();
		String userId = sessionData.getUserId().toString();

		SimpleDateFormat fSDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStamp = fSDateFormat.format(new Date());

		String sql;
		List list;
		String sqlForAudiEvent = "insert into catissue_audit_event" +
				"(IDENTIFIER,IP_ADDRESS,EVENT_TIMESTAMP,USER_ID ,COMMENTS) values ('"
				+ auditEventId
				+ "','"
				+ ipAddress
				+ "',to_date('"
				+ timeStamp
				+ "','yyyy-mm-dd HH24:MI:SS'),'" + userId + "','" + comments + "')";
		Logger.out.info("sqlForAuditLog:" + sqlForAudiEvent);
		jdbcDAO.executeUpdate(sqlForAudiEvent);

		long queryNo = 1;
		sql = "select CATISSUE_AUDIT_EVENT_QUERY_SEQ.nextVal from dual";
		list = jdbcDAO.executeQuery(sql);

		if (!list.isEmpty())
		{
			List columnList = (List) list.get(0);
			if (!columnList.isEmpty())
			{
				String str = (String) columnList.get(0);
				if (!"".equals(str))
				{
					queryNo = Long.parseLong(str);
				}
			}
		}
		return queryNo;
	}
}