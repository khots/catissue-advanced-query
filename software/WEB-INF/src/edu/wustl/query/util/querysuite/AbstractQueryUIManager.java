
package edu.wustl.query.util.querysuite;

/**
 * This class is base for all QueryUIManager classes.
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.QueryPrivilege;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.viewmanager.ViewType;

public abstract class AbstractQueryUIManager
{

	protected boolean isSavedQuery;
	protected SessionDataBean sessionDataBean;

	public AbstractQueryUIManager()
	{

	}

	public AbstractQueryUIManager(SessionDataBean sessionDataBean)
	{
		this.sessionDataBean = sessionDataBean;
	}

	/**
	 * This method extracts query object and forms results .
	 * 
	 * @param option
	 * @return status
	 */
	public Long searchQuery() throws QueryModuleException
	{
		Long query_exec_id = 0L;
		if (sessionDataBean != null)
		{
			query_exec_id = processQuery();
		}

		return query_exec_id;
	}

	/**
	 * This method Processes the Query by calling execute method of QueryManager.
	 * 
	 * @throws QueryModuleException
	 */
	protected abstract Long processQuery() throws QueryModuleException;

	public abstract Count getCount(Long query_execution_id, QueryPrivilege privilege)
			throws QueryModuleException;

	/**
	 * This method gets the required objects (in case of Cider objects will be Projects)
	 * based on which query results can be filtered.
	 * @param userId get Objects based on user id. 
	 * @return collection of required objects.
	 * @throws QueryModuleException
	 */
	abstract public List<NameValueBean> getObjects(Long userId) throws QueryModuleException;

	/**
	 * This method updates the query object with default conditions
	 * @throws QueryModuleException
	 */
	abstract public void updateQueryForValidation() throws QueryModuleException;

	//	abstract public DataQueryResultsBean getData(int countQueryExecId,List<NodeInfo> upiList, ViewType viewType) throws QueryModuleException, DAOException, SQLException;

	abstract public Long executeDataQuery(Long countQueryExecId, ViewType viewType)
			throws QueryModuleException;

	abstract public Long executeDataQuery(Long countQueryExecId, String data, ViewType viewType)
			throws QueryModuleException;

	abstract public DataQueryResultsBean getNextRecord(Long dataQueryExecId)
			throws QueryModuleException;

	/**
	 * This method audits the query if it returns too few records and does not have privilege.
	 * @param countObject having status,result count
	 * @param privilege of query based on project id and user id.
	 * @throws QueryModuleException
	 */
	abstract public void auditTooFewRecords(Count countObject, QueryPrivilege privilege)
			throws QueryModuleException;

	abstract public AbstractQuery getAbstractQuery();

	abstract public void setAbstractQuery(AbstractQuery abstractQuery);

	abstract public QueryPrivilege getPrivilege(HttpServletRequest request);

	public abstract void insertParametersForExecution(Long query_execution_id,
			IAbstractQuery abstractQuery) throws DAOException;
	public abstract void cancelDataQuery(Long dataQueryExecId);
	
}
