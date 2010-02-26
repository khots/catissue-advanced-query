package edu.wustl.query.bizlogic;

import junit.framework.TestCase;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.beans.SharedQueryBean;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
public class SaveQueryBizLogicTestCase extends TestCase
{

	private SessionDataBean getSession()
	{
		SessionDataBean sessionDataBean = new SessionDataBean();
		sessionDataBean.setIpAddress("");
		sessionDataBean.setUserId(1l);
		sessionDataBean.setUserName("admin@admin.com");

		return sessionDataBean;
	}
	public void testSaveQuery()
	{
		IQuery query= GenericQueryGeneratorMock.creatParticipantQuery();
		SaveQueryBizLogic bizLogic = new SaveQueryBizLogic();
		IParameterizedQuery parameterizedQuery = bizLogic.populateParameterizedQueryData(query);

		try
		{
			SharedQueryBean queryBean = new SharedQueryBean();
			queryBean.setShareTo("none");
			bizLogic.saveQuery(parameterizedQuery, getSession(), null);
			Long queryId = parameterizedQuery.getId();
			IQuery retrievedQuery = new SaveQueryBizLogic().getQuery(queryId);
			assertNotNull(query);

		}
		catch (BizLogicException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void testPopulateParameterizedQueryData()
//	{
//		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
//		SaveQueryBizLogic bizLogic = new SaveQueryBizLogic();
//		IParameterizedQuery originalQuery = bizLogic.populateParameterizedQueryData(query);
//	}


//	public void testGetQuery()
//	{
//		try
//		{
//			IQuery query = new SaveQueryBizLogic().getQuery(Long.valueOf(1));
//		}
//		catch (BizLogicException e)
//		{
//			e.printStackTrace();
//		}
//	}


	public void testGetSharingDetailsBean()
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		try
		{
			SharedQueryBean queryBean= new SaveQueryBizLogic().getSharingDetailsBean(query);
			assertNotNull(queryBean);
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}
}
