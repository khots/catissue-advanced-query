
package edu.wustl.query.util.querysuite;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.AbstractQueryFactory;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;

/**
 *   This class is default implementation of AbstractQueryUIManager.
 */
public abstract class QueryUIManager extends AbstractQueryUIManager
{

	private AbstractQuery abstractQuery;

	public QueryUIManager()
	{
	}

	
	/**
	 * @param request
	 * @param query
	 */
	public QueryUIManager(HttpServletRequest request, IQuery query)
	{

		this.request = request;
		this.session = request.getSession();
		SessionDataBean sessionDataBean = ((SessionDataBean) session
				.getAttribute(Constants.SESSION_DATA));
		long user_id = sessionDataBean.getUserId();
		this.abstractQuery = AbstractQueryFactory.getDefaultAbstractQuery();
		this.abstractQuery.setQuery(query);
		this.abstractQuery.setUserId(user_id);
		isSavedQuery = Boolean.valueOf((String) session.getAttribute(Constants.IS_SAVED_QUERY));
		queryDetailsObj = new QueryDetails(session);
	}

	/**
	 * This method Processes the Query by calling execute method of AbstractQueryManager.
	 * 
	 * @throws QueryModuleException
	 */
	public int processQuery() throws QueryModuleException
	{
		AbstractQueryManager queryManager = AbstractQueryManagerFactory
				.getDefaultAbstractQueryManager();
		QueryModuleException queryModExp;
		try
		{
			queryManager.execute(abstractQuery);

		}
		catch (MultipleRootsException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.MULTIPLE_ROOT);
			throw queryModExp;
		}
		catch (SqlException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		return 0;
	}

	@Override
	public Count getCount(int query_execution_id) throws QueryModuleException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
