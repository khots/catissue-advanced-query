
package edu.wustl.query.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionforms.SaveQueryForm;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryModuleConstants;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * This action class is invoked when user clicks on "Execute" link from MyQueries page
 * @author baljeet_dhindhwal
 *
 */
public class ExecuteQueryAction extends AbstractQueryBaseAction
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(ExecuteQueryAction.class);

	/**
	 * This method fetches query based on query Id and generated HTML according to
	 * Query for  Parameterized Query execution.
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward ActionForward
	 * @throws Exception Exception
	 */
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = Constants.FAILURE;
		Long queryId = null;
		SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
		if (request.getAttribute(Constants.QUERY_ID) == null)
		{
			queryId = saveQueryForm.getQueryId();
			logger.info("Fetching query in ExecuteQueryAction having identifier as " + queryId);
			if (queryId == null)
			{
				target = Constants.SUCCESS;
				ActionErrors errors = Utility.setActionError(Constants.QUERY_IDENTIFIER_NOT_VALID,
						"errors.item");
				saveErrors(request, errors);
			}
			else
			{
				target = fetchQuery(request, queryId, saveQueryForm);
			}

		}
		HttpSession session = request.getSession();
		if (request.getParameter(Constants.PAGE_OF) == null
				|| (request.getParameter(Constants.PAGE_OF) != null && !request.getParameter(
						Constants.PAGE_OF).equals(Constants.PAGE_OF_WORKFLOW)))
		{
			session.removeAttribute(Constants.EXECUTED_FOR_WFID);
			session.removeAttribute(Constants.WORKFLOW_NAME);
			session.removeAttribute(Constants.WORKFLOW_ID);
		}
		session.removeAttribute(Constants.SELECTED_PROJECT);
		return actionMapping.findForward(target);
	}

	/**
	 * This method retrieves the query based on query Id and then
	 * generates the HTML to show parameterize
	 * conditions for Query , to show on the jsp Page
	 * @param request HttpServletRequest
	 * @param queryId query Id
	 * @param saveQueryForm SaveQueryForm
	 * @return target pages
	 * @throws BizLogicException BizLogicException
	 * @throws PVManagerException PVManagerException
	 * @throws QueryModuleException QueryModuleException
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException.
	 */
	private String fetchQuery(HttpServletRequest request, Long queryId, SaveQueryForm saveQueryForm)
			throws BizLogicException, PVManagerException, QueryModuleException, DAOException,
			SQLException
	{
		String target = Constants.FAILURE;
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		try
		{
			final List<IParameterizedQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class
					.getName(), Constants.ID, queryId);
			IParameterizedQuery parameterizedQuery = queryList.get(0);
			Long userId = null;
			HttpSession session = request.getSession();
			SessionDataBean sessionBean = (SessionDataBean) session
					.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
			userId = sessionBean.getUserId();
			boolean showPreviousParameters = false;
			List<Date> startFinishDate = new ArrayList<Date>();
			Long queryExeId = null;
			Map<Long, Long> excutionAndProjectIdMap = getLatestExecutionForQuery(queryId, userId);
			if (!excutionAndProjectIdMap.isEmpty())
			{
				Set<Long> key = excutionAndProjectIdMap.keySet();
				queryExeId = key.iterator().next();
				startFinishDate = Utility.generateQueryExecDate(queryExeId);
				if (startFinishDate.get(0).after(parameterizedQuery.getUpdationDate()))
				{
					showPreviousParameters = true;
				}
			}
			String queryExecutionId = request.getParameter(Constants.EXECUTION_ID_OF_QUERY);
			if (queryExecutionId != null && !queryExecutionId.equals(""))
			{
				startFinishDate = Utility.generateQueryExecDate(Long.valueOf(queryExecutionId));
				target = setParametersOnRecentQuery(request, saveQueryForm, parameterizedQuery,
						queryExecutionId, startFinishDate);

			}
			else if (showPreviousParameters)
			{
				target = setLatestExecutionParameter(request, saveQueryForm, parameterizedQuery,
						userId, startFinishDate, excutionAndProjectIdMap);
			}
			else
			{
				//query Object is set in session as it is required for jsp
				session.setAttribute(Constants.QUERY_OBJECT, parameterizedQuery);
				//Retrieve the List of all project.
				AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
						.getDefaultAbstractUIQueryManager();
				List<NameValueBean> projectList = qUIManager.getObjects(userId);
				target = generateHTMLForMYQueries(request, saveQueryForm, parameterizedQuery,
						session, projectList);
			}
		}
		catch (BizLogicException ex)
		{
			ActionErrors errors = Utility.setActionError(ex.getMessage(), "errors.item");
			saveErrors(request, errors);
		}
		return target;
	}

	/**
	 * @param request HttpServletRequest
	 * @param saveQueryForm  SaveQueryForm
	 * @param parameterizedQuery IParameterizedQuery
	 * @param session HttpSession
	 * @param projectList project List
	 * @return target page
	 * @throws PVManagerException PVManagerException
	 */
	private String generateHTMLForMYQueries(HttpServletRequest request,
			SaveQueryForm saveQueryForm, IParameterizedQuery parameterizedQuery,
			HttpSession session, List<NameValueBean> projectList) throws PVManagerException
	{
		String target;
		if (projectList != null)
		{
			saveQueryForm.setProjectsNameValueBeanList(projectList);
		}
		if (parameterizedQuery.getParameters().isEmpty())
		{
			//Then show only the query constraints
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			StringBuffer constrHtmlContents =htmlProvider
			.getHtmlforSavedConditions(parameterizedQuery);
			StringBuffer genaratedHtml = htmlProvider.generateHtmlDivs("", constrHtmlContents);
			genaratedHtml.append("</br>");
			request.setAttribute(Constants.HTML_CONTENTS, genaratedHtml.toString());
			saveQueryForm.setQueryString(genaratedHtml.toString());
			target = Constants.SHOW_PQC_PAGE;
		}
		else
		{
			//This is the case of Query containing Parameters
			Map<Integer, ICustomFormula> customFormulaIndexMap =
			new HashMap<Integer, ICustomFormula>();
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("");
			generateHTMLDetails.setAttributeChecked(false);
			generateHTMLDetails.setPermissibleValuesChecked(false);
			SavedQueryHtmlProvider  htmlProvider =  new SavedQueryHtmlProvider();
			String htmlContents = htmlProvider.getHTMLForSavedQuery(
			parameterizedQuery, false,Constants.EXECUTE_QUERY_PAGE, customFormulaIndexMap,
					generateHTMLDetails, false);
			//Here get the generated HTML for Query conditions
			StringBuffer constrHtmlContents = htmlProvider
			.getHtmlforSavedConditions(parameterizedQuery);
			StringBuffer genaratedHtml = htmlProvider.generateHtmlDivs(htmlContents, constrHtmlContents);
			genaratedHtml.append("</br>");
			request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE,
					generateHTMLDetails.getEnumratedAttributeMap());
			request.setAttribute(Constants.HTML_CONTENTS, genaratedHtml.toString());
			session.setAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP,
					customFormulaIndexMap);
			saveQueryForm.setQueryString(genaratedHtml.toString());
			target = Constants.SHOW_PQC_PAGE;
		}
		return target;
	}

	/**
	 * @param request HttpServletRequest
	 * @param saveQueryForm SaveQueryForm
	 * @param parameterizedQuery IParameterizedQuery
	 * @param userId user Id
	 * @param startFinishDate execution Date
	 * @param excutionAndProjectIdMap excutionAndProjectIdMap
	 * @return target page
	 * @throws BizLogicException BizLogicException
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 * @throws PVManagerException PVManagerException
	 * @throws QueryModuleException QueryModuleException.
	 */
	private String setLatestExecutionParameter(HttpServletRequest request,
			SaveQueryForm saveQueryForm, IParameterizedQuery parameterizedQuery, Long userId,
			List<Date> startFinishDate, Map<Long, Long> excutionAndProjectIdMap)
			throws BizLogicException, DAOException, SQLException, PVManagerException,
			QueryModuleException
	{
		Date executionDate = startFinishDate.get(0);
		Long queryId = parameterizedQuery.getId();
		HttpSession session = request.getSession();
		Long queryExeId = excutionAndProjectIdMap.keySet().iterator().next();
		String target;
		StringBuffer queryIdString = new StringBuffer("");
		queryIdString.append(queryId);
		StringBuffer htmlContents = new StringBuffer(Constants.MAX_SIZE);

		IParameterizedQuery newQuery=parameterizedQuery;
		if(request.getParameter(Constants.IS_SHARED)!=null&&
				!request.getParameter(Constants.IS_SHARED).equals(Constants.SAHRED_QUERIES))
		{
		newQuery=setParametersForLatestExecution(parameterizedQuery,queryExeId,executionDate
				);
		request.setAttribute(Constants.LATEST_PROJECT,
				excutionAndProjectIdMap.get(queryExeId));
		}

		session.setAttribute(Constants.QUERY_OBJECT, newQuery);
		Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);
		boolean isReadOnly = getIsReadOnlyParameter(saveQueryForm.getPageOf());
		SavedQueryHtmlProvider  htmlProvider =  new SavedQueryHtmlProvider();
		String queryhtmlContents = htmlProvider.getHTMLForSavedQuery(newQuery,
				false, Constants.EXECUTE_QUERY_PAGE, customFormulaIndexMap, generateHTMLDetails,
				isReadOnly);
		request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE,
				generateHTMLDetails.getEnumratedAttributeMap());
		//Here generating HTML for conditions
		StringBuffer constrHtmlContents = htmlProvider
		.getHtmlforSavedConditions(parameterizedQuery);
		StringBuffer genaratedHtml = htmlProvider.generateHtmlDivs(queryhtmlContents, constrHtmlContents);
		queryhtmlContents = genaratedHtml.toString() + "</br>";
		//htmlContents = htmlContents + queryhtmlContents + constrHtmlContents.toString()+ "";
		htmlContents.append(queryhtmlContents+"");
		Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap =
			new HashMap<Long, Map<Integer, ICustomFormula>>();
		eachQueryCFMap.put(newQuery.getId(), customFormulaIndexMap);
		session.setAttribute(QueryModuleConstants.CUSTOM_FORMULA_INDEX_MAP, customFormulaIndexMap);
		request.setAttribute(Constants.HTML_CONTENTS, htmlContents);
		request.setAttribute("queriesIdString", queryIdString.toString());
		//Retrieve the List of all project
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		List<NameValueBean> projectList = qUIManager.getObjects(userId);
		if (projectList != null)
		{
			saveQueryForm.setProjectsNameValueBeanList(projectList);
		}
		target = Constants.SHOW_PQC_PAGE;
		return target;
	}

	/**
	 * This method sets parameters for recent queries
	 * @param request HttpServletRequest
	 * @param saveQueryForm SaveQueryForm
	 * @param parameterizedQuery IParameterizedQuery
	 * @param queryExecutionId query Execution Id
	 * @param startFinishDate execution start & Finish Date
	 * @return target page
	 * @throws BizLogicException BizLogicException
	 * @throws PVManagerException PVManagerException
	 * @throws QueryModuleException QueryModuleException
	 */
	private String setParametersOnRecentQuery(HttpServletRequest request,
			SaveQueryForm saveQueryForm, IParameterizedQuery parameterizedQuery,
			String queryExecutionId, List<Date> startFinishDate) throws BizLogicException,
			PVManagerException, QueryModuleException
	{
		String target;
		Long queryId = parameterizedQuery.getId();
		StringBuffer queryIdString =new StringBuffer("");
		queryIdString.append(queryId);
		StringBuffer htmlContents = new StringBuffer(Constants.MAX_SIZE);

		IParameterizedQuery newQuery;
		newQuery = setParameterForRecentQuey(parameterizedQuery, Long.valueOf(queryExecutionId));
		Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);
		boolean isReadOnly = getIsReadOnlyParameter(saveQueryForm.getPageOf());
		//Here we get the generated html for parameters in query
		SavedQueryHtmlProvider  htmlProvider =  new SavedQueryHtmlProvider();
		String queryhtmlContents = htmlProvider.getHTMLForSavedQuery(newQuery,
				false, Constants.EXECUTE_QUERY_PAGE, customFormulaIndexMap, generateHTMLDetails,
				isReadOnly);
		//Here we get the generated html for added constraints
		StringBuffer constrHtmlContents = htmlProvider
				.getHtmlforSavedConditions(parameterizedQuery);
		String queryNameStr = "";
		queryNameStr = getQueryDetails(newQuery, request, startFinishDate);
		StringBuffer genaratedHtml = htmlProvider.generateHtmlDivs(queryhtmlContents, constrHtmlContents);
		queryhtmlContents =queryNameStr + genaratedHtml.toString() + "</br>";

		htmlContents.append(queryhtmlContents+"");
		Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap =
			new HashMap<Long, Map<Integer, ICustomFormula>>();
		eachQueryCFMap.put(newQuery.getId(), customFormulaIndexMap);
		request.setAttribute(Constants.HTML_CONTENTS, htmlContents.toString());
		request.setAttribute("queriesIdString", queryIdString.toString());
		request.setAttribute(Constants.SHOW_GET_COUNT, Constants.SHOW_GET_COUNT);
		target = Constants.SHOW_PQ_POPUP;

		return target;
	}
	/**
	 * @param queryId query Id
	 * @param userId user Id
	 * @return exEcution and Project Id Map
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 */
	private Map<Long, Long> getLatestExecutionForQuery(Long queryId, Long userId)
			throws DAOException, SQLException
	{
		// Get the map of execution Ids from DB
		ITableManager itableManager = ITableManagerFactory.getDefaultITableManager();
		Map<Long, Long> excutionAndProjectIdMap = null;
		try
		{
			excutionAndProjectIdMap = itableManager.getLatestProjectIdForQuery(queryId, userId);
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage());
			throw e;
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage());
			throw e;

		}
		return excutionAndProjectIdMap;
	}

	/**Method to generate HTML for query details.
	 * @param parameterizedQuery parameterizedQuery
	 * @param request HttpServletRequest
	 * @param startFinishDate execution start & Finish Date
	 * @return query Name String
	 * @throws QueryModuleException QueryModuleException
	 */
	protected String getQueryDetails(IParameterizedQuery parameterizedQuery,
			HttpServletRequest request, List<Date> startFinishDate) throws QueryModuleException
	{
		String queryNameStr = "<table width='100%'><tr class=\"td_subtitle\" width='100%'>"
				+ "<td width=\"12%\" "
				+ "valign=\"top\" class=\"blue_title\">&nbsp; Query Title : &nbsp;</td>"
				+ "<td id = \"queryTitle1\" class=\"blue_title\" align=\"left\"  valign=\"top\">"
				+ "<label valign=\"top\" >&nbsp;" + parameterizedQuery.getName()
				+ "</label></td></tr><tr class=\"td_greydottedline_horizontal\"><td width=\"10%\">"
				+ "</td><td></td></tr><tr height='5'></tr></table>";
		return queryNameStr;
	}

	/**Method to get read only parameter from request.
	 * @param pageOf pageOf
	 * @return isReadOnly
	 */
	private boolean getIsReadOnlyParameter(String pageOf)
	{
		boolean isReadOnly = false;
		if (pageOf != null && pageOf.equals(Constants.PAGE_OF_RECENT_QUERIES))
		{
			isReadOnly = true;
		}
		return isReadOnly;
	}

	/**
	 * this method set the parameters to query for latest execution
	 * @param parameterizedQuery IParameterizedQuery
	 * @param queryExeId execution Id
	 * @return IParameterizedQuery
	 * @throws BizLogicException BizLogicException
	 */
	private IParameterizedQuery setParameterForRecentQuey(IParameterizedQuery parameterizedQuery,
			Long queryExeId) throws BizLogicException
	{
		IParameterizedQuery newQuery = null;

		// take output attributes from retrieved Query
		newQuery = Utility.setParameterForRecentQuey(parameterizedQuery, queryExeId);

		return newQuery;

	}

	/**
	 * this method set the parameters to query for latest execution
	 * @param executionDate execution Date
	 * @param parameterizedQuery Query
	 * @param queryExeId execution Id
	 * @return IParameterizedQuery
	 * @throws BizLogicException BizLogicException
	 * @throws SQLException SQLException
	 * @throws DAOException DAOException
	 */
	private IParameterizedQuery setParametersForLatestExecution(
			IParameterizedQuery parameterizedQuery, Long queryExeId, Date executionDate)
			throws BizLogicException, DAOException, SQLException
	{
		IParameterizedQuery newQuery = null;
		// take output attributes from retrieved Query
		newQuery = Utility.setParametersForLatestExecution(parameterizedQuery, queryExeId,
				executionDate);

		return newQuery;

	}

}