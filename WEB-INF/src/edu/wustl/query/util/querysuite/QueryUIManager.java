
package edu.wustl.query.util.querysuite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryFactory;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;

/**
 *   This class is default implementation of AbstractQueryUIManager.
 */
public abstract class QueryUIManager extends AbstractQueryUIManager
{

	private AbstractQuery abstractQuery;
	protected IQuery query;

	public QueryUIManager()
	{
		super();
	}

	public QueryUIManager(SessionDataBean sessionDataBean, IQuery query)
	{
		super(sessionDataBean);
		this.query = query;
	}

	/**
	 * @param request
	 * @param query
	 */
	public QueryUIManager(HttpServletRequest request, IQuery query) throws QueryModuleException
	{
		super(((SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA)));
		HttpSession session = request.getSession();
		long user_id = sessionDataBean.getUserId();
		this.abstractQuery = AbstractQueryFactory.getDefaultAbstractQuery();
		this.abstractQuery.setQuery(query);
		this.abstractQuery.setUserId(user_id);
		isSavedQuery = Boolean.valueOf((String) session.getAttribute(Constants.IS_SAVED_QUERY));
	}

	/**
	 * This method Processes the Query by calling execute method of AbstractQueryManager.
	 * 
	 * @throws QueryModuleException
	 */
	public Long processQuery() throws QueryModuleException
	{
		AbstractQueryManager queryManager = AbstractQueryManagerFactory
				.getDefaultAbstractQueryManager();
		Long query_execution_id = 0L;
		try
		{
			query_execution_id = queryManager.execute(abstractQuery);

		}
		catch (MultipleRootsException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.MULTIPLE_ROOT);
		}
		catch (SqlException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.SQL_EXCEPTION);
		}
		return query_execution_id;
	}

	@Override
	public Count getCount(Long query_execution_id, QueryPrivilege privilege)
			throws QueryModuleException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method inserts defined queries.
	 * @throws QueryModuleException
	 */
	public void insertDefinedQueries() throws QueryModuleException
	{
		//inserts Defined Query
		DefinedQueryUtil definedQueryUtil = new DefinedQueryUtil();
		try
		{
			definedQueryUtil.insertQuery(query, sessionDataBean, false);
		}

		catch (BizLogicException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.DAO_EXCEPTION);
		}

	}

	/**
	 * This method updates the defined queries in database.
	 * @throws QueryModuleException
	 */
	public void updateDefinedQueries(SessionDataBean sessionDataBean) throws QueryModuleException
	{
		//inserts Defined Query
		DefinedQueryUtil definedQueryUtil = new DefinedQueryUtil();
		try
		{
			definedQueryUtil.updateQuery(query, sessionDataBean, false);
		}
		//		catch (UserNotAuthorizedException e)
		//		{
		//			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
		//			throw queryModExp;
		//		}
		catch (BizLogicException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.DAO_EXCEPTION);
		}
		//		catch (DAOException e)
		//		{
		//			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
		//			throw queryModExp;
		//		}
	}

	/**
	 * Method to get object of AbstractQuery
	 * @return abstractQuery object of AbstractQuery
	 */
	public AbstractQuery getAbstractQuery()
	{
		return abstractQuery;
	}

	/**
	 * Method to set object of AbstractQuery
	 * @param abstractQuery object of AbstractQuery
	 */
	public void setAbstractQuery(AbstractQuery abstractQuery)
	{
		this.abstractQuery = abstractQuery;
	}

	/**
	* @param query_execution_id
	* @param abstractQuery
	*/
	public void insertParametersForExecution(Long query_execution_id, IAbstractQuery abstractQuery) throws DAOException
	{

	}
}
