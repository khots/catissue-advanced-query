
package edu.wustl.query.action;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.cider.util.CiderUtility;
import edu.wustl.cider.util.global.Utility;
import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionforms.ShowRetrieveRecentForm;
import edu.wustl.query.bizlogic.RecentQueriesBizlogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.security.exception.SMException;

/**
 * This class is used to retrieve the recent queries and set the pagination on
 * the retrieved data.
 *
 * @author chitra_garg
 */
public class RetrieveRecentQueriesAction extends AbstractQueryBaseAction
{
	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(RetrieveRecentQueriesAction.class);
	/**
	 * Static constant for index - status image.
	 */
	private static final int INDEX_STATUS_IMAGE = 0;
	/**
	 * Static constant for index - query title.
	 */
	private static final int INDEX_QUERY_TITLE = 1;
	/**
	 * Static constant for index - result count.
	 */
	private static final int INDEX_RESULT_COUNT = 2;
	/**
	 * Static constant for index - query creation date.
	 */
	private static final int INDEX_QUERY_CREATION_DATE = 3;
	/**
	 * Static constant for index - action icons.
	 */
	private static final int INDEX_ACTIONS = 4;
	/**
	 * Static constant for index - status text.
	 */
	private static final int INDEX_STATUS_TEXT = 5;
	/**
	 * Static constant for index - query execution id.
	 */
	private static final int INDEX_QUERY_EXEC_ID = 6;
	/**
	 * Static constant for index - query privilege.
	 */
	private static final int INDEX_PRIVILEGE = 7;
	/**
	 * Static constant for index - query id.
	 */
	private static final int INDEX_QUERY_ID = 8;

	/**
	 * The actual execute method.
	 *
	 * @param actionMapping
	 *            Action Mapping.
	 * @param actionForm
	 *            Action Form.
	 * @param request
	 *            HttpServletRequest object.
	 * @param response
	 *            HttpServletResponse object.
	 * @return The Action Forward object.
	 * @throws Exception
	 *             Exception.
	 * @see org.apache.struts.action.Action#execute
	 *      (org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		populateRecentQueries(request, (ShowRetrieveRecentForm) actionForm);
		if( request
				.getParameter(Constants.QUERY_NAME_LIKE)==null)
		{
			request.setAttribute(Constants.QUERY_NAME_LIKE, request
				.getParameter(Constants.QUERY_NAME_LIKE));
		}
		else
		{
			String namecontains=request
			.getParameter(Constants.QUERY_NAME_LIKE).replaceAll("'","&#39;");
			request.setAttribute(Constants.QUERY_NAME_LIKE, namecontains);
		}
		return actionMapping.findForward(request.getParameter(Constants.PAGE_OF));

	}

	/**
	 * Method to set the total recent queries count for a user in session.
	 *
	 * @param sessionDataBean
	 *            SessionDatabean
	 * @return The recent query count
	 * @throws QueryModuleException
	 *             Query Module Exception
	 * @throws SMException
	 *             Security Manager Exception
	 */
	public int setRecentQueriesCount(SessionDataBean sessionDataBean) throws QueryModuleException,
			SMException
	{
		try
		{
			RecentQueriesBizlogic bizLogic =
				(edu.wustl.query.bizlogic.RecentQueriesBizlogic) AbstractFactoryConfig
					.getInstance().getBizLogicFactory().getBizLogic(
							Constants.RECENT_QUERY_BIZLOGIC_ID);
			String sql = "SELECT COUNT(*) " + "FROM QUERY_EXECUTION_LOG qel, "
					+ "COUNT_QUERY_EXECUTION_LOG cqel " + "WHERE qel.QUERY_EXECUTION_ID = "
					+ "cqel.COUNT_QUERY_EXECUTION_ID and qel.USER_ID="
					+ sessionDataBean.getUserId()
					+ " and qel.QUERY_TYPE='C' and qel.WORKFLOW_ID IS NULL";
			int numberOfQueries = 0;
			List<List<String>> resultCount = bizLogic.executeQuery(sql, sessionDataBean, -1, -1);
			if (resultCount != null)
			{
				numberOfQueries = Integer.valueOf(resultCount.get(0).get(0));
			}
			return numberOfQueries;
		}
		catch (BizLogicException bizlogicException)
		{
			throw new QueryModuleException(bizlogicException.getMessage(), bizlogicException,
					QueryModuleError.GENERIC_EXCEPTION);
		}

	}

	/**
	 * This method set the total number of records to display.
	 *
	 * @param request
	 *            The HttpServletRequest object.
	 * @return The recent queries count that is set for display.
	 * @throws QueryModuleException
	 *             Query Module Exception
	 * @throws SMException
	 *             Security Manager Exception
	 */
	private int setDisplayedRecentQueryCount(HttpServletRequest request)
			throws QueryModuleException, SMException
	{

		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		int recordCountToDisplay = setRecentQueriesCount(sessionDataBean);
		String showLastfromRequest = request.getParameter(Constants.SHOW_LAST);
		int showLast = 0;
		if (showLastfromRequest == null)
		{
			showLast = Constants.SHOW_LAST_COUNT;// 25;
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
	 * This method populates the data related to the recent queries in the given
	 * request object.
	 *
	 * @param request
	 *            The HttpServletRequest object.
	 * @param showRetrieveRecentForm
	 *            The Action form.
	 * @throws DAOException
	 *             The Hibernate DAO Exception.
	 * @throws QueryModuleException
	 *             Query Module Exception.
	 * @throws BizLogicException
	 *             Business Logic Exception.
	 * @throws SMException
	 *             Security Manager Exception.
	 */
	private void populateRecentQueries(HttpServletRequest request,
			ShowRetrieveRecentForm showRetrieveRecentForm) throws DAOException,
			QueryModuleException, BizLogicException, SMException
	{
		String queryNameLike = request.getParameter(Constants.QUERY_NAME_LIKE);
		if ("".equals(queryNameLike))
		{
			queryNameLike = "";
		}

		RecentQueriesBizlogic bizLogic =
			(edu.wustl.query.bizlogic.RecentQueriesBizlogic) AbstractFactoryConfig
				.getInstance().getBizLogicFactory()
					.getBizLogic(Constants.RECENT_QUERY_BIZLOGIC_ID);
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);

		request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
		request.setAttribute(Constants.RESULTS_PER_PAGE_OPTIONS, Constants.SHOW_LAST_OPTION);
		int lastIndex = setDisplayedRecentQueryCount(request);
		List<List<String>> queryResultList = bizLogic.retrieveQueries(sessionDataBean, lastIndex);

		List<RecentQueriesBean> recentQueriesBeanList = bizLogic.populateRecentQueryBean(
				sessionDataBean, queryResultList, queryNameLike);
		showRetrieveRecentForm.setRecentQueriesBeanList(recentQueriesBeanList);
		request.setAttribute(Constants.RECENT_QUERIES_BEAN_LIST, recentQueriesBeanList);

		final List<Object[]> attributesList = setAttributeList(recentQueriesBeanList);
		request.setAttribute("msgBoardItemList", CiderUtility.getmyData(attributesList));
		List<String> columnList = getHeaderColumnList();
		request.setAttribute("identifierFieldIndex", "5,6,7,8");
		request.setAttribute("columns", Utility.getcolumns(columnList));
		request.setAttribute("colWidth", "\"4,67,7,14,8,0,0,0,0\"");
		request.setAttribute("isWidthInPercent", true);
		request.setAttribute("colTypes", "\"ro,str,str,str,str,int,str,int,str\"");
		request.setAttribute("colDataTypes", "\"txt,txt,txt,txt,txt,ro,ro,ro,ro\"");
		request.setAttribute("records", recentQueriesBeanList.size());
	}
	/**
	 * This method returns header column list for recent queries.
	 * @return header column list
	 */
	private List<String> getHeaderColumnList()
	{
		List<String> columnList = new ArrayList<String>();
		columnList.add(" &nbsp;");
		columnList.add("Title");
		columnList.add("Patients");
		columnList.add("Executed On");
		columnList.add("Action(s)");
		columnList.add("");
		columnList.add("");
		columnList.add("");
		columnList.add("");
		return columnList;
	}
	/**
	 * This method sets the object array required for displaying queries on the recent queries page.
	 * @param recentQueriesBeanList list of recent queries bean
	 * @return list of object array
	 */
	private List<Object[]> setAttributeList(List<RecentQueriesBean> recentQueriesBeanList)
	{

		final List<Object[]> attributesList = new ArrayList<Object[]>();
		Object[] attributes = null;
		int index = 0;
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		for (RecentQueriesBean recentQueriesBean : recentQueriesBeanList)
		{
			index++;
			attributes = new Object[9];
			attributes[INDEX_STATUS_IMAGE] = "";
			attributes[INDEX_QUERY_TITLE] = recentQueriesBean.getQueryTitle();
			attributes[INDEX_RESULT_COUNT] = numberFormat
					.format(recentQueriesBean.getResultCount());
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
			Date date=null;
			try
			{
				date=dateFormat.parse(recentQueriesBean.getQueryCreationDate());
			}
			catch (ParseException e)
			{
				logger.debug(e.getMessage(),e);
			}
			attributes[INDEX_QUERY_CREATION_DATE] =
				edu.wustl.common.util.Utility.parseDateToString
					(date, Constants.DATE_PATTERN_MM_DD_YYYY_HH_MM);
			attributes[INDEX_ACTIONS] = "<div> <a title=\"Query Execution Details\"  id=\"abcd" + index + "\" href=\"ShowParamRecentQueries.do?AjaxRequest=Yes&queryId="
				+ recentQueriesBean.getQueryId() + "&queryExecutionId="+ recentQueriesBean.getQueyExecutionId() + "&pageOf="
				+ Constants.PAGE_OF_RECENT_QUERIES + "\" " + "class='jt' style=font-size=10px' align='right' " +
				"\" rel=\"ShowParamRecentQueries.do?AjaxRequest=Yes&queryId="
				+ recentQueriesBean.getQueryId() + "&queryExecutionId="+ recentQueriesBean.getQueyExecutionId() + "&pageOf="
				+ Constants.PAGE_OF_RECENT_QUERIES + "\" "+">"
				+ "<img src='images/advancequery/application_form.png' " + " hspace='0' border='0'/></a>";
			if (recentQueriesBean.getStatus().equals(Constants.QUERY_IN_PROGRESS)
					|| recentQueriesBean.getStatus().equals(Constants.GENERATING_QUERY))
			{
				attributes[INDEX_ACTIONS] = attributes[INDEX_ACTIONS] + "<img id=\"cancel" + index
						+ "\" src='images/advancequery/cancel.png' "
						+ "title='Cancel' alt='Cancel' hspace='0' "
						+ "border='0' style='cursor: pointer;' "
						+ " onmouseup='javascript:cancel(" + index + ")'/>";
			}
			attributes[INDEX_ACTIONS] = attributes[INDEX_ACTIONS] + "</div>";
			attributes[INDEX_STATUS_TEXT] = recentQueriesBean.getStatus();
			attributes[INDEX_QUERY_EXEC_ID] = recentQueriesBean.getQueyExecutionId();
			attributes[INDEX_PRIVILEGE] = recentQueriesBean.isIsSecurePrivilege();
			attributes[INDEX_QUERY_ID] = recentQueriesBean.getQueryId();
			attributesList.add(attributes);
		}
		return attributesList;
	}
}
