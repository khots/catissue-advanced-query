
package edu.wustl.query.querysuite;

import java.util.Date;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.util.querysuite.DefinedQueryUtil;

public class DefinedQueryUtilTestCases extends QueryBaseTestCases
{

	static
	{
		//Utility.initTest();
	}

	public void testInsertQuery()
	{
		try
		{

			IParameterizedQuery query = getQuery();
			DefinedQueryUtil queryUtil = new DefinedQueryUtil();
			SessionDataBean session = getSessionData();
			boolean shared = true;

			Long queryId = query.getId();
			queryUtil.insertQuery(query, session, shared);
			assertNotSame(queryId, query.getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}

	}

	private IParameterizedQuery getQuery() throws Exception
	{
		IParameterizedQuery query = null;

		query = QueryBuilder.skeletalDemograpihcsQuery();
		SessionDataBean session = getSessionData();
		boolean shared = false;
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

	public void testUpdateQuery()
	{
		try
		{
			IQuery query = getQuery();
			SessionDataBean session = getSessionData();
			boolean shared = true;
			DefinedQueryUtil queryUtil = new DefinedQueryUtil();
			query.setName("UpdatedName" + new Date().getTime());
			queryUtil.updateQuery(query, session, shared);

			assertNotNull(query.getId());

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}

	}
}
