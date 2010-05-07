package edu.wustl.query.bizlogic;

import junit.framework.TestCase;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.beans.SharedQueryBean;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import gov.nih.nci.security.authorization.domainobjects.User;
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
	/*public void testSaveQuery()
	{
		IQuery query= GenericQueryGeneratorMock.creatParticipantQuery();
		SaveQueryBizLogic bizLogic = new SaveQueryBizLogic();
		IParameterizedQuery parameterizedQuery1 = QueryObjectFactory.createParameterizedQuery(query);
		IParameterizedQuery parameterizedQuery = bizLogic.populateParameterizedQueryData(parameterizedQuery1);

		try
		{
			SharedQueryBean queryBean = new SharedQueryBean();
			queryBean.setShareTo("none");
			bizLogic.saveQuery(parameterizedQuery, getSession(), null);
			Long queryId = parameterizedQuery.getId();
			IQuery retrievedQuery = new SaveQueryBizLogic().getQuery(queryId);
			assertNotNull(retrievedQuery);

		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}*/
	public void testPopulateParameterizedQueryData()
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		IParameterizedQuery pQuery = QueryObjectFactory.createParameterizedQuery(query);
		SaveQueryBizLogic bizLogic = new SaveQueryBizLogic();
		IParameterizedQuery originalQuery = bizLogic.populateParameterizedQueryData(pQuery);
	}


	/*public void testGetQuery()
	{
		try
		{
			IQuery query = new SaveQueryBizLogic().getQuery(Long.valueOf(1));
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}*/


	public void testGetSharingDetailsBean()
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		IParameterizedQuery pQuery = QueryObjectFactory.createParameterizedQuery(query);
		query.setId(Long.valueOf(1));
		try
		{
			SharedQueryBean queryBean= new SaveQueryBizLogic().getSharingDetailsBean(query);
			SessionDataBean sessionDataBean = getSession();
			long[] users = {1,2,3};
			String roles = "Administrator";
			queryBean.setCsmUserId(sessionDataBean.getCsmUserId());
			User user = new User();
			queryBean.setOwner(user);
			queryBean.setProtocolCoordinatorIds(users);
			queryBean.setQuery((ParameterizedQuery)pQuery);
			queryBean.setSelectedRoles(roles);
			queryBean.setShareTo("None");

			queryBean.getCsmUserId();
			queryBean.getOwner();
			queryBean.getProtocolCoordinatorIds();
			queryBean.getQuery();
			queryBean.getSelectedRoles();
			queryBean.getShareTo();

		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}
}
