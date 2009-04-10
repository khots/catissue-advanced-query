
package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.ShowRetrieveRecentForm;
import edu.wustl.query.bizlogic.RecentQueriesBizlogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
* @author chitra_garg
*retrieves the recent queries and set the pagination on
*retrieved data
*/
public class RetrieveRecentQueriesAction extends Action
{

	@Override
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		populateRecentQueries(request, (ShowRetrieveRecentForm) actionForm);

		return actionMapping.findForward(request.getParameter(Constants.PAGE_OF));

	}

	/**
	 * set the total recent queries count for
	 * a user in session
	 * @param request
	 * @return
	 * @throws QueryModuleException
	 * @throws BizLogicException 
	 */
	public int setRecentQueriesCount(SessionDataBean sessionDataBean) throws QueryModuleException
	{
		try
		{
			RecentQueriesBizlogic bizLogic = (edu.wustl.query.bizlogic.RecentQueriesBizlogic)AbstractBizLogicFactory
			.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"),
					"getBizLogic",Constants.RECENT_QUERY_BIZLOGIC_ID);
			String sql ="SELECT COUNT(*) "+
	        "FROM QUERY_EXECUTION_LOG qel, COUNT_QUERY_EXECUTION_LOG cqel "+
	        "WHERE qel.QUERY_EXECUTION_ID = cqel.COUNT_QUERY_EXECUTION_ID and cqel.USER_ID="+
	        sessionDataBean.getUserId() + " and qel.QUERY_TYPE='C' and qel.WORKFLOW_ID IS NULL";
			int numberOfQueries = 0;
			List<List<String>> resultCount = bizLogic.executeQuery(sql, sessionDataBean, false, false, null, -1,
					-1);
			if (resultCount != null)
			{
				numberOfQueries = Integer.valueOf(resultCount.get(0).get(0));
			}

			return numberOfQueries;
		}
		catch (BizLogicException bizlogicException)
		{
			throw new QueryModuleException(bizlogicException.getMessage(),QueryModuleError.GENERIC_EXCEPTION);
		}
		
	}

	/**
	 * This method set the total number of records to display
	 * @param request
	 * @return
	 * @throws QueryModuleException
	 * @throws BizLogicException 
	 */
	private int setDisplayedRecentQueryCount(HttpServletRequest request)
			throws QueryModuleException, BizLogicException
	{

		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				Constants.SESSION_DATA);
		int recordCountToDisplay = setRecentQueriesCount(sessionDataBean);
		String showLastfromRequest = request.getParameter(Constants.SHOW_LAST);
		int showLast = 0;
		if (showLastfromRequest == null)
		{
			showLast = Constants.SHOW_LAST_COUNT;//25;
		}
		else
		{
			showLast = Integer.valueOf(request.getParameter(Constants.SHOW_LAST));

		}
		request.setAttribute(Constants.RESULTS_PER_PAGE, showLast);
		request.setAttribute("records", recordCountToDisplay);
		if (showLast < recordCountToDisplay)
		{
			recordCountToDisplay = showLast;
		}
		return recordCountToDisplay;

	}

	/**
	 * @param request
	 * @param showRetrieveRecentForm
	 * @throws DAOException
	 * @throws QueryModuleException
	 * @throws BizLogicException 
	 */
	private void populateRecentQueries(HttpServletRequest request,
			ShowRetrieveRecentForm showRetrieveRecentForm) throws DAOException,
			QueryModuleException, BizLogicException
	{
		RecentQueriesBizlogic bizLogic = (edu.wustl.query.bizlogic.RecentQueriesBizlogic)AbstractBizLogicFactory
		.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"),
				"getBizLogic",Constants.RECENT_QUERY_BIZLOGIC_ID);
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				Constants.SESSION_DATA);

		request.setAttribute(Constants.PAGE_OF,request.getParameter(Constants.PAGE_OF));
		request.setAttribute(Constants.RESULTS_PER_PAGE_OPTIONS, Constants.SHOW_LAST_OPTION);
		int lastIndex = setDisplayedRecentQueryCount(request);
		List<List<String>> queryResultList = bizLogic.retrieveQueries(sessionDataBean, lastIndex);

		List<RecentQueriesBean> recentQueriesBeanList = bizLogic.populateRecentQueryBean(sessionDataBean,
				queryResultList);
		showRetrieveRecentForm.setRecentQueriesBeanList(recentQueriesBeanList);
		request.setAttribute(Constants.RECENT_QUERIES_BEAN_LIST, recentQueriesBeanList);
	}

}