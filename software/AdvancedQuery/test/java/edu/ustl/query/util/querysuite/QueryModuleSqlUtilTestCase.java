package edu.ustl.query.util.querysuite;

import java.sql.SQLException;

import edu.wustl.dao.exception.DAOException;
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
	}
}
