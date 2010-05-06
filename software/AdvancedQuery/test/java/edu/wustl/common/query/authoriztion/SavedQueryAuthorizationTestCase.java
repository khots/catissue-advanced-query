package edu.wustl.common.query.authoriztion;

import junit.framework.TestCase;

public class SavedQueryAuthorizationTestCase extends TestCase
{
	/*public void testShareQuery() throws SMException, CSTransactionException, CSObjectNotFoundException, CSException
	{
		SavedQueryAuthorization authorization = new SavedQueryAuthorization();
		SharedQueryBean bean = new SharedQueryBean();
		bean.setShareTo("all");
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		IParameterizedQuery pQuery = QueryObjectFactory.createParameterizedQuery(query);
		authorization.shareQuery(bean, (ParameterizedQuery)pQuery);
	}*/

	public void testGetUserProtectionGroup()
	{
		String csmUserId = "1";
		SavedQueryAuthorization authorization = new SavedQueryAuthorization();
		authorization.getUserProtectionGroup(csmUserId);
	}

	/*public void testGetSharedUsers() throws SMException, CSObjectNotFoundException
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		ParameterizedQuery pQuery = (ParameterizedQuery)QueryObjectFactory.createParameterizedQuery(query);
		SavedQueryAuthorization authorization = new SavedQueryAuthorization();
		authorization.getSharedUsers(pQuery);
	}

	public void testRemovePrevSharing() throws SMException, CSTransactionException, CSObjectNotFoundException, BizLogicException
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		ParameterizedQuery pQuery = (ParameterizedQuery)QueryObjectFactory.createParameterizedQuery(query);
		SavedQueryAuthorization authorization = new SavedQueryAuthorization();
		authorization.removePrevSharing(pQuery, "1");
	}*/
}
