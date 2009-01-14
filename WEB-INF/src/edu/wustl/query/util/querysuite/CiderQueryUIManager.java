package edu.wustl.query.util.querysuite;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.CiderQuery;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.querymanager.CiderQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Constants;


/**
 *   This class is Cider Specific implementation of AbstractQueryUIManager.
 */
public class CiderQueryUIManager extends QueryUIManager {

	
	private CiderQuery ciderQuery;
	
	public CiderQueryUIManager() 
	{


	}

	/**
	 * @param request
	 * @param query
	 */
	public CiderQueryUIManager(HttpServletRequest request, IQuery query) {

		
		this.request = request;
		this.session = request.getSession();
		SessionDataBean sessionDataBean = ((SessionDataBean) session.getAttribute(Constants.SESSION_DATA));
		long user_id = sessionDataBean.getUserId();
	    long project_id = 1;
		this.ciderQuery = new CiderQuery(query,-1,null,user_id, project_id);
		isSavedQuery = Boolean.valueOf((String) session.getAttribute(Constants.IS_SAVED_QUERY));
		queryDetailsObj = new QueryDetails(session);
	}


	/**
	 * This method Processes the Query by calling execute method of CiderQueryManager.
	 * 
	 * @throws QueryModuleException
	 */
	public int processQuery() throws QueryModuleException
	{
		CiderQueryManager queryManager = new CiderQueryManager();
		QueryModuleException queryModExp;
		int query_execution_id ;
		try
		{
			query_execution_id = queryManager.execute(ciderQuery);

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
		return query_execution_id;
	}
	
	public Count getCount(int query_execution_id) throws QueryModuleException
	{  
		CiderQueryManager queryManager = new CiderQueryManager();
		Count queryCount;
		try
		{
			queryCount = queryManager.getQueryCount(query_execution_id);
		}
		catch (DAOException e)
		{
			QueryModuleException queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException e)
		{
			QueryModuleException queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		return queryCount;
	}
}
