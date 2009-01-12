package edu.wustl.query.util.querysuite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import edu.wustl.query.util.global.Constants;

public abstract class AbstractQueryUIManager {
	
	protected HttpServletRequest request;
	protected HttpSession session;
	protected boolean isSavedQuery;
	QueryDetails queryDetailsObj;

	/**
	 * This method extracts query object and forms results .
	 * 
	 * @param option
	 * @return status
	 */
	public QueryModuleError searchQuery(String option) {
		session.removeAttribute(Constants.HYPERLINK_COLUMN_MAP);
		QueryModuleError status = QueryModuleError.SUCCESS;
		try {

			if (queryDetailsObj.getSessionData() != null) {
				processQuery();
			}
		} catch (QueryModuleException e) {
			status = e.getKey();
		}
		return status;
	}

	/**
	 * @throws QueryModuleException
	 */
	public abstract void processQuery() throws QueryModuleException ;
}
