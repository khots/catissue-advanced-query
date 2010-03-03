package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.dao.exception.DAOException;
import junit.framework.TestCase;

public class CommonQueryBizLogicTestCase extends TestCase
{
	public void testInsertQuery() throws DAOException
	{
		CommonQueryBizLogic queryBizLogic = new CommonQueryBizLogic();
		SessionDataBean sessionData = new SessionDataBean();
		sessionData.setCsmUserId("1");
		sessionData.setIpAddress("10.88.199.11");
		sessionData.setUserId(1l);
		sessionData.setUserName("admin@admin.com");
		String sqlQuery = "select distinct Participant_1.ETHNICITY Column0, Participant_1.LAST_NAME Column1, Participant_1.BIRTH_DATE Column2, Participant_1.IDENTIFIER Column3, Participant_1.VITAL_STATUS Column4, Participant_1.FIRST_NAME Column5, Participant_1.MIDDLE_NAME Column6, Participant_1.GENDER Column7, Participant_1.SOCIAL_SECURITY_NUMBER Column8, Participant_1.ACTIVITY_STATUS Column9, Participant_1.DEATH_DATE Column10, Participant_1.GENOTYPE Column11  from (select * from CATISSUE_PARTICIPANT where ACTIVITY_STATUS != 'Disabled') Participant_1  where Participant_1.IDENTIFIER is NOT NULL";
		queryBizLogic.insertQuery(sqlQuery, sessionData);
	}
}
