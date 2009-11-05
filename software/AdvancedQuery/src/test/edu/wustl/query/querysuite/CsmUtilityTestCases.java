
package edu.wustl.query.querysuite;

import java.util.ArrayList;
import edu.wustl.query.utility.Utility;
import java.util.Collection;
import java.util.Date;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.CsmUtility;

public class CsmUtilityTestCases extends QueryBaseTestCases
{
	

	private IParameterizedQuery getQuery() throws Exception
	{
		IParameterizedQuery query = null;

		query = QueryBuilder.skeletalDemograpihcsQuery();
		SessionDataBean session = getSessionData();
		boolean shared = true;
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		String queryName = "Query" + (new Date()).getTime();
		query.setName(queryName);
		query.setCreatedBy(session.getUserId());
		query.setCreatedDate(new Date());
		query.setUpdatedBy(session.getUserId());
		query.setUpdationDate(new Date());

		queryBizLogic.insertSavedQueries(query, session, shared);

		return query;
	}

	public void testGetUserProtectionGroup()
	{
		try
		{
			CsmUtility value = new CsmUtility();
			String csmId = value.getUserProtectionGroup("2");
			assertNotNull(csmId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public void testGetParameterizedQueryCollection()
	{
		try
		{
			getQuery();
			Collection<Long> values = new ArrayList<Long>();
			CsmUtility util = new CsmUtility();
			values = util.getParameterizedQueryCollection();
			assertTrue(!values.isEmpty());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public void testRetrieveQueries()
	{
		try
		{
			Collection<Long> values = new ArrayList<Long>();
			CsmUtility util = new CsmUtility();
			values = util.getParameterizedQueryCollection();
			String querytype="";
			Collection<IParameterizedQuery> queries = util.retrieveQueries(values, "Que",querytype);
			assertTrue(!queries.isEmpty());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public void testCheckIsSharedQuery()
	{
		try
		{
			IParameterizedQuery query = getQuery();
			CsmUtility util = new CsmUtility();
			boolean isShared = util.checkIsSharedQuery(query.getClass().getName() + "_"
					+ query.getId());
			assertEquals(true, isShared);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public void testCheckExecuteQueryPrivilege()
	{
		Collection<IParameterizedQuery> myQueryCollection = new ArrayList<IParameterizedQuery>();
		Collection<IParameterizedQuery> sharedQueryCollection = new ArrayList<IParameterizedQuery>();
		
		SessionDataBean sessionDataBean = getSessionData();
		
		
		try
		{
			String querytype="";
			CsmUtility.checkExecuteQueryPrivilege(myQueryCollection,
					sharedQueryCollection, sessionDataBean, "Query", querytype);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testRetrieveQueriesForGetCount()
	{
		try
		{
			Collection<Long> values = new ArrayList<Long>();
			Collection<Long> finalValues = new ArrayList<Long>();
			CsmUtility util = new CsmUtility();
			values = util.getParameterizedQueryCollection();
			finalValues = util.retrieveQueriesForGetCount(values, "Query");
			assertTrue(!finalValues.isEmpty());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testCheckExecuteQueryPrivilegeForGetCount()
	{
		Collection<Long> myQueryCollection = new ArrayList<Long>();
		Collection<Long> sharedQueryCollection = new ArrayList<Long>();
		SessionDataBean sessionDataBean = getSessionData();
		
		try
		{
			CsmUtility.checkExecuteQueryPrivilegeForGetCount(myQueryCollection,
					sharedQueryCollection, sessionDataBean, "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
