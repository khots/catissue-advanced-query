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

public class CiderQueryUIManager extends QueryUIManager {

	
	private CiderQuery ciderQuery;
	

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
		
		/*((Integer) request.getAttribute(Constants.PROJECT_ID)).intValue();*/
		
		this.ciderQuery = new CiderQuery(query,-1,null,user_id, project_id);
		isSavedQuery = Boolean.valueOf((String) session.getAttribute(Constants.IS_SAVED_QUERY));
		queryDetailsObj = new QueryDetails(session);
	}


	/**
	 * @throws QueryModuleException
	 */
	public void processQuery() throws QueryModuleException
	{
		CiderQueryManager queryManager = new CiderQueryManager();
		QueryModuleException queryModExp;
		try
		{
			int query_execution_id = queryManager.execute(ciderQuery);
			Count queryCount = queryManager.getQueryCount(query_execution_id);

		}
		catch (MultipleRootsException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.MULTIPLE_ROOT);
			throw queryModExp;
		}
		catch (SQLException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SqlException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		Map<EntityInterface, List<EntityInterface>> mainEntityMap = QueryCSMUtil
				.setMainObjectErrorMessage(ciderQuery.getQuery(), request.getSession(),
						queryDetailsObj);
		queryDetailsObj.setMainEntityMap(mainEntityMap);
	}
}
