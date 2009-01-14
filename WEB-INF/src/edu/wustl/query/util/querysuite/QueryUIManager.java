
package edu.wustl.query.util.querysuite;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.AbstractQueryFactory;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.util.global.Constants;
import edu.wustl.common.query.AbstractQuery;

/**
 *   This class is default implementation of AbstractQueryUIManager.
 */
public class QueryUIManager extends AbstractQueryUIManager
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
	public void processQuery() throws QueryModuleException
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
		finally
		{
		}
		Map<EntityInterface, List<EntityInterface>> mainEntityMap = QueryCSMUtil
				.setMainObjectErrorMessage(abstractQuery.getQuery(), request.getSession(),
						queryDetailsObj);
		queryDetailsObj.setMainEntityMap(mainEntityMap);
	}
}
