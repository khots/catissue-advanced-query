package edu.ustl.query.util.querysuite;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.util.querysuite.QueryModuleSqlUtil;
import junit.framework.TestCase;

public class QueryModuleSqlUtilTestCase extends TestCase
{
	public void test() throws DAOException, SQLException
	{
		String columnName = "ROOT_ENTITY_NAME";
		String newColumnValue = "edu.wustl.clinportal.domain.Participant";
		long auditEventId = 29894;

		QueryModuleSqlUtil.updateAuditQueryDetails(columnName, newColumnValue, auditEventId);
		QueryModuleError status = QueryModuleError.EMPTY_DAG;
		int errorCode = status.getErrorCode();
		assertEquals("Error code", errorCode,1);

		QueryModuleException queryModExp = new QueryModuleException("DAG is Empty", status);
		QueryModuleError key = queryModExp.getKey();
		assertEquals("Error code", key.getErrorCode(),1);
		queryModExp = new QueryModuleException("DAG is Empty");
	}

	public void testGetSqlForRootNode()
	{
		String tableName = "TEMP_OUTPUTTREE_16";
		Map<String, String> columnNameIndexMap = new HashMap<String, String>();
		columnNameIndexMap.put("index", "6");
		columnNameIndexMap.put("columnNames", "Column0 , Column1, Column6, Column3, Column4");
		String selectSql = QueryModuleSqlUtil.getSQLForRootNode(tableName, columnNameIndexMap);
		assertEquals("SQL for root node",selectSql,"select distinct Column0 , Column1, Column6, Column3, Column4 from TEMP_OUTPUTTREE_16 where Column0  is not null::6");
	}
}
