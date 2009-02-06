
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.dao.AbstractDAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.dao.JDBCDAO;
import edu.wustl.common.dao.queryExecutor.PagenatedResultData;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.actionForm.ShowRetrieveRecentForm;
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
	 */
	private int setRecentQueriesCount(HttpServletRequest request) throws QueryModuleException
	{
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				Constants.SESSION_DATA);
		String sql = "select count(*) from  QUERY_EXECUTION_LOG where USER_ID= "
				+ sessionDataBean.getUserId();
		int numberOfQueries = 0;
		List<List<String>> resultCount = executeQuery(sql, sessionDataBean, false, false, null, -1,
				-1);
		if (resultCount != null)
		{
			numberOfQueries = Integer.valueOf(resultCount.get(0).get(0));
		}

		return numberOfQueries;
	}

	/**
	 * This method set the total number of records to display 
	 * @param request
	 * @return
	 * @throws QueryModuleException 
	 */
	private int setDisplayedRecentQueryCount(HttpServletRequest request)
			throws QueryModuleException
	{

		int recordCountToDisplay = setRecentQueriesCount(request);
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
		if (showLast < recordCountToDisplay)
		{
			recordCountToDisplay = showLast;
		}
		return recordCountToDisplay;

	}

	/**
	 * set the pagination related data 
	 * @param request = HttpRequest
	 * @param requestFor = requested for page 
	 * @param showRetrieveRecentForm =ShowRetrieveRecentForm
	 * @return
	 * @throws QueryModuleException 
	 */
	private int setPagiantion(HttpServletRequest request,
			ShowRetrieveRecentForm showRetrieveRecentForm) throws QueryModuleException
	{
		int totalRecords = setDisplayedRecentQueryCount(request);
		int maxRecords = getNumberOfRecordsPerPage();
		int totalPages = 0;
		if (totalRecords > 0)
		{
			totalPages = totalRecords % maxRecords == 0
					? totalRecords / maxRecords
					: (totalRecords / maxRecords) + 1;
		}
		int pageNum = getPageNumber(request);

		request.getSession().setAttribute("pageNum", pageNum);
		request.getSession().setAttribute(Constants.TOTAL_PAGES, totalPages);
		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);
		request.setAttribute(Constants.PAGE_OF,request.getParameter(Constants.PAGE_OF));
		//drop down box
		request.setAttribute(Constants.RESULTS_PER_PAGE_OPTIONS, Constants.SHOW_LAST_OPTION);

		return totalRecords;
	}

	private void populateRecentQueries(HttpServletRequest request,
			ShowRetrieveRecentForm showRetrieveRecentForm) throws DAOException,
			QueryModuleException
	{
		int totalCount = setPagiantion(request, showRetrieveRecentForm);
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				Constants.SESSION_DATA);

		int pageNum = getPageNumber(request);//return the page number for requested
		int recordsPerPage = getNumberOfRecordsPerPage();//records per  page
		int lastIndex = calculateLastIndex(totalCount, pageNum, recordsPerPage);

		List<List<String>> queryResultList = retrieveQueries(sessionDataBean, pageNum,
				recordsPerPage, lastIndex);

		List<RecentQueriesBean> recentQueriesBeanList = populateRecentQueryBean(sessionDataBean,
				queryResultList);
		showRetrieveRecentForm.setRecentQueriesBeanList(recentQueriesBeanList);
		request.setAttribute(Constants.RECENT_QUERIES_BEAN_LIST, recentQueriesBeanList);


	}

	/**
	 * This method returns the  Query List
	 * 
	 * @param sessionDataBean
	 * @param sql
	 * @param pageNum
	 * @param recordsPerPage
	 * @param lastIndex
	 * @return
	 * @throws QueryModuleException 
	 */
	private List<List<String>> retrieveQueries(SessionDataBean sessionDataBean, int pageNum,
			int recordsPerPage, int lastIndex) throws QueryModuleException
	{
		String sql = "select * from  QUERY_EXECUTION_LOG where USER_ID="
				+ sessionDataBean.getUserId() + " order by CREATIONTIME desc ";
		List<List<String>> queryResultList = executeQuery(sql, sessionDataBean, false, false, null,
				(pageNum - 1) * recordsPerPage, lastIndex);
		return queryResultList;
	}

	/**
	 * @param sessionDataBean =session related data 
	 * @param queryResultList =List of queries for which 
	 * RecentQueryBean list is to be populate
	 * @return= RecentQueriesBean List
	 * @throws DAOException
	 */
	private List<RecentQueriesBean> populateRecentQueryBean(SessionDataBean sessionDataBean,
			List<List<String>> queryResultList) throws DAOException
	{
		List<RecentQueriesBean> recentQueriesBeanList = new ArrayList<RecentQueriesBean>();

		for (List<String> parameterizedQuery : queryResultList)
		{
			RecentQueriesBean recentQueriesBean = new RecentQueriesBean();
			recentQueriesBean.setQueryCreationDate(parameterizedQuery.get(0));//new Timestamp((new Date()).getTime()));
			recentQueriesBean.setResultCount(100L);
			recentQueriesBean.setStatus(parameterizedQuery.get(2));

			String title = retrieveQueryName(Long.valueOf(parameterizedQuery.get(5)),
					sessionDataBean);//query id from listparameterizedQuery.get(5)
			recentQueriesBean.setQueryTitle(title);
			recentQueriesBean.setQueyExecutionId(Long.valueOf(parameterizedQuery.get(4)));
			recentQueriesBeanList.add(recentQueriesBean);
		}
		return recentQueriesBeanList;
	}

	/**
	 * This method set the last index of records to be fetched
	 * @param totalCount=total records
	 * @param pageNum=page number for which last index is to be manipulate
	 * @param recordsPerPage= number of records per page
	 * @return =last index
	 */
	private int calculateLastIndex(int totalCount, int pageNum, int recordsPerPage)
	{
		if (recordsPerPage * pageNum > totalCount)
		{
			return totalCount - (recordsPerPage * (pageNum - 1));
		}
		return recordsPerPage;
	}

	/**
	 * This method returns the page number 
	 * @param request
	 * @param requestFor = request for which page 
	 * @return 
	 */
	private int getPageNumber(HttpServletRequest request)
	{
		//String requestFor=request.getParameter("requestFor");
		Object pageNumObj = getSessionAttribute(request, Constants.PAGE_NUMBER);
		int pageNum = 0;
		if (pageNumObj != null)///&& requestFor != null)
		{
			pageNum = Integer.parseInt(pageNumObj.toString());
		}
		else
		{
			pageNum = 1;
		}

		return pageNum;
	}

	/**
	 * this method returns the given attribute from request
	 * or session
	 * @param request
	 * @param attributeName
	 * @return
	 */
	private Object getSessionAttribute(HttpServletRequest request, String attributeName)
	{
		Object object = null;
		if (request != null)
		{
			object = request.getParameter(attributeName);
			if (object == null)
			{
				object = request.getAttribute(attributeName);
				if (object == null)
				{
					object = request.getSession().getAttribute(attributeName);
				}
			}
		}

		return object;
	}

	/**
	 * @param id=query id
	 * @param sessionDataBean session specific data
	 * @return
	 * @throws DAOException
	 */
	private String retrieveQueryName(Long id, SessionDataBean sessionDataBean) throws DAOException
	{

		String sourceObjectName = ParameterizedQuery.class.getName();
		AbstractDAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
		dao.openSession(sessionDataBean);

		String name = ((ParameterizedQuery) dao.retrieve(sourceObjectName, id)).getName();
		dao.closeSession();

		return name;
	}

	/**
	 * @return number of results per page
	 */
	private int getNumberOfRecordsPerPage()
	{
		return Constants.PER_PAGE_RESULTS;
	}

	/**
	 * Executes the query & returns the results specified by the offset values i.e. startIndex & noOfRecords.
	 * @param query 
	 * @param sessionDataBean =session related data
	 * @param isSecureExecute
	 * @param hasConditionOnIdentifiedField
	 * @param queryResultObjectDataMap
	 * @param startIndex
	 * @param noOfRecords
	 * @return
	 * @throws QueryModuleException
	 */
	public List<List<String>> executeQuery(String query, SessionDataBean sessionDataBean,
			boolean isSecureExecute, boolean hasConditionOnIdentifiedField,
			Map queryResultObjectDataMap, int startIndex, int noOfRecords)
			throws QueryModuleException
	{
		QueryModuleException queryModExp;
		List<List<String>> queryResultList = null;
		JDBCDAO dao = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		try
		{
			dao.openSession(sessionDataBean);
			PagenatedResultData pagiPagenatedResultData = (PagenatedResultData) dao.executeQuery(
					query, sessionDataBean, false, false, null, startIndex, noOfRecords);
			queryResultList = pagiPagenatedResultData.getResult();

		}
		catch (ClassNotFoundException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.CLASS_NOT_FOUND);
			throw queryModExp;
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				queryModExp = new QueryModuleException(e.getMessage(),
						QueryModuleError.DAO_EXCEPTION);
				throw queryModExp;
			}
		}
		return queryResultList;
	}
}
