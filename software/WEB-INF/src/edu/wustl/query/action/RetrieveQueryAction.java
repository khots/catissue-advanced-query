
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;

import edu.wustl.cider.exception.CiderSystemException;
import edu.wustl.cider.util.CiderUtility;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionforms.SaveQueryForm;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.CsmUtility;
import edu.wustl.security.exception.SMException;
import gov.nih.nci.security.exceptions.CSException;


/**
 * @author chitra_garg
 * This class is for retrieving the
 *query collection . For shared and MyQueries.
 */
public class RetrieveQueryAction extends AbstractQueryBaseAction
{


	private static final int THREE = 3;
	private static final int TWO = 2;
	private static final int SIX = 6;
	private static final int FOUR = 4;
	private static final int ZERO = 0;

	/**
	 * This method Perform execution of action.
	 * @param actionMapping ActionMapping.
	 * @param actionForm ActionForm.
	 * @param request HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @return ActionForward
	 * @throws Exception
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward actionForward = null;
		SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
		try
		{
			removeAttributesFromSession(request);
			Collection<IParameterizedQuery> myQueryCollection = new ArrayList<IParameterizedQuery>();
			Collection<IParameterizedQuery> sharedQueryCollection =
							new ArrayList<IParameterizedQuery>();
			initializeParameterizeQueryCollection(request, saveQueryForm, myQueryCollection,
					sharedQueryCollection);
			//user in work flow pop up
			setDataInRequest(request);
			actionForward = setActionForward(actionMapping, request.getParameter(Constants.PAGE_OF));
		}
		catch (HibernateException hibernateEx)
		{
			actionForward = actionMapping
					.findForward(edu.wustl.common.util.global.Constants.FAILURE);
		}
		request.setAttribute(Constants.POPUP_MESSAGE, ApplicationProperties
				.getValue("query.confirmBox.message"));
		if (request.getParameter(Constants.PAGE_OF) != null)
		{
			request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
		}
		return actionForward;
	}

	/**
	 * This method set the  request related data
	 * @param request HttpServletRequest
	 * @throws CiderSystemException CiderSystemException
	 */
	private void setDataInRequest(HttpServletRequest request) throws CiderSystemException
	{

		request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
		//removed for #14206
//		SessionDataBean sessionDataBean=(SessionDataBean) request.getSession().getAttribute(
//		edu.wustl.common.util.global.Constants.SESSION_DATA);
		//request.setAttribute(Constants.MY_QUERIES_COUNT,
		//ExplorerUtil.getMyQueriesCount(sessionDataBean));
		//request.setAttribute(Constants.SHARED_QUERIES_COUNT,
		//	ExplorerUtil.getSharedQueriesCount(sessionDataBean));
		request.setAttribute(Constants.Query_Type, request.getParameter(Constants.Query_Type));

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
	}

	/**
	 * return my queries & shared queries count Count.
	 * @param dataBean SessionDataBean
	 * @return countsMap Map<String, Integer>
	 * @throws CSException CSException
	 * @throws DAOException DAOException
	 * @throws SMException SMException
	 */
	public Map<String, Integer> getMyQueryAndSharedQueryCount(final SessionDataBean dataBean)
			throws CSException, DAOException, SMException
	{

		Map<String, Integer> countsMap = new HashMap<String, Integer>();
		Collection<Long> myQueryCollection = new ArrayList<Long>();
		Collection<Long> sharedQueryCollection = new ArrayList<Long>();
		CsmUtility.checkExecuteQueryPrivilegeForGetCount(myQueryCollection, sharedQueryCollection,
				dataBean, "");

		if (myQueryCollection != null)
		{
			countsMap.put(Constants.MY_QUERIES, myQueryCollection.size());
		}
		if (sharedQueryCollection != null)
		{
			countsMap.put(Constants.SAHRED_QUERIES, sharedQueryCollection.size());
		}
		return countsMap;
	}

	/**
	 * @param request HttpServletRequest
	 * @param saveQueryForm SaveQueryForm
	 * @param myQueryCollection Collection
	 * @param sharedQueryCollection Collection
	 * @throws CSException CSException
	 * @throws DAOException DAOException
	 * @throws SMException SMException
	 */
	private void initializeParameterizeQueryCollection(HttpServletRequest request,
			SaveQueryForm saveQueryForm, Collection<IParameterizedQuery> myQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection) throws CSException,
			 DAOException, SMException
	{
		String queryNameLike;
		String queryType;
		queryNameLike = setQueryName(request);
		queryType = populateQueryType(request);
		Collection<IParameterizedQuery> finalQueries = null;
		CsmUtility.checkExecuteQueryPrivilege(myQueryCollection, sharedQueryCollection,
				(SessionDataBean) request.getSession().getAttribute(
						edu.wustl.common.util.global.Constants.SESSION_DATA),
						queryNameLike,queryType);
		setQueryCollectionToSaveQueryForm(saveQueryForm, (String) request
				.getAttribute(Constants.PAGE_OF), myQueryCollection, sharedQueryCollection);
		finalQueries = setQueryCollection(request, saveQueryForm,
				myQueryCollection, sharedQueryCollection);

		//request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
		setPagiantion(request, request.getParameter("requestFor"), saveQueryForm);
		if (finalQueries != null)
		{
			saveQueryForm.setParameterizedQueryCollection(getMaxRecord(finalQueries, request));
		}
		populateGrid(request, saveQueryForm.getParameterizedQueryCollection());
	}

	/**
	 * returns type of query that is
	 * count or data .
	 * @param request HttpServletRequest
	 * @return String query type
	 */
	private String populateQueryType(HttpServletRequest request)
	{
		StringBuffer queryType=new StringBuffer("");
		if (request.getParameter(Constants.Query_Type) != null
				&& !request.getParameter(Constants.Query_Type).equals(""))
		{
			queryType.append((String) request.getParameter(Constants.Query_Type));
		}
		return queryType.toString();
	}

	/**
	 * set the query collection
	 * according to pageOf.
	 * @param request HttpServletRequest
	 * @param saveQueryForm SaveQueryForm
	 * @param myQueryCollection Collection
	 * @param sharedQueryCollection Collection
	 * @return  IParameterizedQuery collection
	 */
	private Collection<IParameterizedQuery> setQueryCollection(
			HttpServletRequest request, SaveQueryForm saveQueryForm,
			Collection<IParameterizedQuery> myQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection)
			{
		Collection<IParameterizedQuery> finalQueries = null;
		finalQueries = setMyQueryCollection(request, saveQueryForm,
				myQueryCollection);
		if (request.getParameter(Constants.PAGE_OF) != null
				&& (Constants.SHARED_QUERIES_FOR_MAIN_MENU.equals(request
						.getParameter(Constants.PAGE_OF))
					|| Constants.SAHRED_QUERIES.equals(request.getParameter(Constants.PAGE_OF))
					|| Constants.SAHRED_QUERIES_FOR_WORKFLOW
						.equals(request.getParameter(Constants.PAGE_OF))))
		{
			finalQueries = sharedQueryCollection;
			saveQueryForm.setParameterizedQueryCollection(sharedQueryCollection);

		}
		setQueryCount(request, myQueryCollection, sharedQueryCollection);

		return finalQueries;
	}

	/**
	 * This method set the query Count on workflow popup
	 * for #14206.
	 * @param request HttpServletRequest
	 * @param myQueryCollection myQueryCollection
	 * @param sharedQueryCollection sharedQueryCollection
	 */
	private void setQueryCount(HttpServletRequest request,
			Collection<IParameterizedQuery> myQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection)
	{
		request.setAttribute(Constants.MY_QUERIES_COUNT,myQueryCollection.size() );
		request.setAttribute(Constants.SHARED_QUERIES_COUNT,sharedQueryCollection.size());
	}

	/**
	 * set my queries collection
	 * according to the page of
	 * @param request HttpServletRequest
	 * @param saveQueryForm SaveQueryForm
	 * @param myQueryCollection Collection
	 * @return Collection
	 */
	private Collection<IParameterizedQuery> setMyQueryCollection(
			HttpServletRequest request, SaveQueryForm saveQueryForm,
			Collection<IParameterizedQuery> myQueryCollection)
			{
		Collection<IParameterizedQuery> finalQueries = null;
		if (request.getParameter(Constants.PAGE_OF) != null
				&& (Constants.MY_QUERIES_FOR_MAIN_MENU.equals(request
						.getParameter(Constants.PAGE_OF))
				|| Constants.MY_QUERIES.equals(request.getParameter(Constants.PAGE_OF))
						|| Constants.MY_QUERIES_FOR_WORKFLOW
						.equals(request.getParameter(Constants.PAGE_OF))))
		{
			finalQueries = myQueryCollection;
			saveQueryForm.setParameterizedQueryCollection(myQueryCollection);
			setPagiantion(request, request.getParameter("requestFor"),
					saveQueryForm);

		}
		return finalQueries;
	}

	/**
	 * This method check the query name
	 * given by user to filter results according to
	 * given name.
	 * @param request HttpServletRequest
	 * @return queryName String
	 */
	private String setQueryName(HttpServletRequest request)
	{
		StringBuffer  queryNameLike=new StringBuffer("");
		if (request.getParameter(Constants.QUERY_NAME_LIKE) != null
				&& !request.getParameter(Constants.QUERY_NAME_LIKE).equals(""))
		{
			queryNameLike.append((String) request.getParameter(Constants.QUERY_NAME_LIKE));
		}
		return queryNameLike.toString();
	}

	/**
	 * this method performs the pagination.
	 * @param request HttpServletRequest
	 * @param requestFor requested for page number
	 * @param saveQueryForm SaveQueryForm
	 * @return
	 */
	private void setPagiantion(HttpServletRequest request, String requestFor,
			SaveQueryForm saveQueryForm)
	{

		int totalRecords = 0;
		if (saveQueryForm.getParameterizedQueryCollection() != null)
		{
			totalRecords = saveQueryForm.getParameterizedQueryCollection().size();
		}

		int startIndex = 0;
		int pageNum = getPageNumber(request, requestFor);
		int maxRecords = getRecordsPerPage(request, requestFor);

		request.getSession().setAttribute(Constants.RESULTS_PER_PAGE, maxRecords);
		request.getSession().setAttribute(Constants.PAGE_NUMBER, pageNum);

		startIndex = maxRecords * (pageNum - Constants.ONE);
		totalRecords = getTotalRecords(request, requestFor, totalRecords);
		int totalPages = getTotalPages(maxRecords, totalRecords);
		request.getSession().setAttribute("totalPages", totalPages);
		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);
		request.getSession().setAttribute("startIndex", startIndex);

		List<NameValueBean> resultsPerPgOpts = new ArrayList<NameValueBean>();
		resultsPerPgOpts.add(new NameValueBean("5", "5"));
		resultsPerPgOpts.add(new NameValueBean("10", "10"));
		resultsPerPgOpts.add(new NameValueBean("15", "15"));

		request.setAttribute("resultsPerPageOptions", resultsPerPgOpts);

		int currentPageNo = Constants.ONE ;
		currentPageNo = (Integer) (request.getSession().getAttribute("pageNum"));

		setCurrentPage(request, currentPageNo);
	}

	/**
	 * set the current page by default page is
	 * first page.
	 * @param request HttpServletRequest
	 * @param currentPageNo currentPageNo from request.
	 */
	private void setCurrentPage(HttpServletRequest request, int currentPageNo)
	{
		int firstPageNo =Constants.ONE;
		if (request.getParameter("firstPageNum") == null)
		{
			if (currentPageNo % Constants.FIVE == ZERO)
			{
				firstPageNo = currentPageNo - FOUR;
			}
			else
			{
				firstPageNo = currentPageNo - (currentPageNo % Constants.FIVE - Constants.ONE);
			}

		}
		else
		{
			firstPageNo = Integer.parseInt(request.getParameter("firstPageNum"));
		}
		if (firstPageNo < Constants.FIVE)
		{
			firstPageNo = Constants.ONE;
		}
		request.setAttribute("firstPageNum", firstPageNo);
		request.setAttribute("numOfPageNums", Constants.FIVE);
		if ((firstPageNo + FOUR) > (Integer) (request.getSession().getAttribute("totalPages")))
		{
			request.setAttribute("lastPageNum", (Integer) (request.getSession()
					.getAttribute("totalPages")));
		}
		else
		{
			request.setAttribute("lastPageNum", firstPageNo + FOUR);
		}
	}

	/**
	 * @param maxRecords int
	 * @param totalRecords int
	 * @return totalPages
	 */
	private int getTotalPages(int maxRecords, int totalRecords)
	{
		int totalPages = ZERO;
		if(totalRecords % maxRecords == ZERO)
		{
			totalPages= totalRecords / maxRecords;
		}
		else
		{
			totalPages=(totalRecords / maxRecords) + Constants.ONE;
		}
		return totalPages;
	}

	/**
	 * @param request HttpServletRequest
	 * @param requestFor requested for page
	 * @param matchingUsersCount  total records per page
	 * default is 10.
	 * @return int total Records per page
	 */
	private int getTotalRecords(HttpServletRequest request, String requestFor,
			int matchingUsersCount)
	{
		int totalRecords = matchingUsersCount;
		if (requestFor != null
				&& request.getSession().getAttribute(Constants.TOTAL_RESULTS) != null)
		{
			totalRecords = (Integer) request.getSession().getAttribute(Constants.TOTAL_RESULTS);
		}

		return totalRecords;
	}

	/**
	 * set forward to page.
	 * @param actionMapping ActionMapping
	 * @param pageOf String
	 * @return ActionForward
	 */
	private ActionForward setActionForward(ActionMapping actionMapping, String pageOf)
	{
		ActionForward actionForward;
		actionForward = actionMapping.findForward(Constants.SUCCESS);
		if (pageOf != null)
		{
			actionForward = actionMapping.findForward(pageOf);
		}
		return actionForward;
	}

	/**
	 * This method set the Query Collection in the SaveQueryForm.
	 * @param saveQueryForm SaveQueryForm
	 * @param pageOf String
	 * @param authorizedQueryCollection myQueryCollection
	 * @param sharedQueryCollection  shared Query Collection
	 */
	private void setQueryCollectionToSaveQueryForm(SaveQueryForm saveQueryForm, String pageOf,
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection)
	{
		if (pageOf != null&& (Constants.PUBLIC_QUERY_PROTECTION_GROUP.equals(pageOf) ||
				Constants.PUBLIC_QUERIES_FOR_WORKFLOW
						.equals(pageOf)))
		{
			saveQueryForm.setParameterizedQueryCollection(sharedQueryCollection);
		}
		else
		{
			saveQueryForm.setParameterizedQueryCollection(authorizedQueryCollection);
		}
	}

	/**
	 * set data related to pagination.
	 * @param queries IParameterizedQuery
	 * @param request HttpServletRequest
	 * @return Collection
	 */
	private Collection<IParameterizedQuery> getMaxRecord(Collection<IParameterizedQuery> queries,
			HttpServletRequest request)
	{
		Collection<IParameterizedQuery> coll = new ArrayList<IParameterizedQuery>();
		int maxRec = (Integer) request.getSession().getAttribute(Constants.RESULTS_PER_PAGE);
		int strartIndex = (Integer) request.getSession().getAttribute("startIndex");
		IParameterizedQuery arr[] = new IParameterizedQuery[queries.size()];
		queries.toArray(arr);
		for (int i = 0; i < maxRec; i++)
		{
			if ((i + strartIndex) == arr.length)
			{
				break;
			}
			else
			{
				coll.add(((IParameterizedQuery) arr[i + strartIndex]));
			}
		}
		return coll;
	}

	/**
	 * set the records per page.
	 * @param request HttpServletRequest
	 * @param requestFor String requested for page
	 * @return int maxRecords.
	 */
	private int getRecordsPerPage(HttpServletRequest request, String requestFor)
	{
		Object recordsPerPageObj = getSessionAttribute(request, Constants.RESULTS_PER_PAGE);
		int maxRecords = Constants.PER_PAGE_RESULTS;
		if (recordsPerPageObj != null && requestFor != null)
		{
			maxRecords = Integer.parseInt(recordsPerPageObj.toString());
		}
		return maxRecords;
	}

	/**
	 * set the request page number .
	 * @param request HttpServletRequest
	 * @param requestFor String requested for page
	 * @return int requested for pageNumber.
	 */
	private int getPageNumber(HttpServletRequest request, String requestFor)
	{
		Object pageNumObj = getSessionAttribute(request, Constants.PAGE_NUMBER);
		int pageNum = Constants.ONE;
		if (pageNumObj != null && requestFor != null)
		{
			pageNum = Integer.parseInt(pageNumObj.toString());
		}

		return pageNum;
	}

	/**
	 * @param request HttpServletRequest
	 * @param attributeName String for getting value from session.
	 * @return   requested attribute
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
	 * @param request HttpServletRequest
	 * @param queryColletion Collection
	 */
	public void populateGrid(HttpServletRequest request,
			Collection<IParameterizedQuery> queryColletion)
	{
		//Changed for Bug #14044
		//request.setAttribute("identifierFieldIndex", "0,5");
		 request.setAttribute("identifierFieldIndex", "5");
         request.setAttribute("hiddenFieldIndex", "0,5");
		final List<Object[]> attributesList = new ArrayList<Object[]>();
		Object[] attributes = null;

		for (IParameterizedQuery query : queryColletion)
		{
			attributes = new Object[SIX];
			if (query != null)
			{

				setDescription(attributes, query);

				attributes[TWO] = query.getType();
				attributes[FOUR] = "<div style='padding-left:7px;'>"
						+ "<a href=\"javascript:editSavedQuery("
						+ query.getId().toString() +
						");\">"+"<img src='images/advancequery/application_edit.png' "
						+ "alt='Edit' border='0' id='editQuery_"+query.getId().toString() +"' title='Edit' style='cursor: pointer;' "
				        +"/></a>";
				if (query.getType().equalsIgnoreCase(Constants.QUERY_TYPE_GET_COUNT))
				{
					attributes[FOUR] = attributes[FOUR]+
					" <a href=\"javascript:executeSavedQuery("+
					query.getId().toString() + "&#44"+"'"+
							request.getParameter(Constants.PAGE_OF)+"'"+");\">"+
							"<img src='images/advancequery/execute-button-1.PNG' "
					+ "alt='Execute' id='executeQuery_"+query.getId().toString() +"' border='0' title='Execute' style='cursor: pointer;' "
			        +"/></a>";
				}
				attributes[FOUR] = attributes[FOUR] +
				"</div>";
				attributes[Constants.FIVE] = query.getId().toString();
				attributes[THREE] = edu.wustl.common.util.Utility.parseDateToString(query
						.getUpdationDate(), Constants.DATE_PATTERN_MM_DD_YYYY_HH_MM);
			}
			attributesList.add(attributes);

		}
		request.setAttribute("msgBoardItemList", CiderUtility.getmyData(attributesList));
		setColumnList(request);

	}

	/**
	 * set the description in the grid row.
	 * @param attributes array for grid
	 * @param query IParameterizedQuery
	 */
	private void setDescription(Object[] attributes, IParameterizedQuery query)
	{
		if (query.getDescription() == null || query.getDescription().equals(""))
		{
			attributes[Constants.ONE] = "<div title='Title: " + query.getName()
					+ " &#10;Description: (Not Available)'>" + query.getName()
					+ "</div>";
		}
		else
		{
			attributes[Constants.ONE] = "<div title='Title: " + query.getName()
					+ " &#10;Description: " + query.getDescription() + "'>"
					+ query.getName() + "</div>";
		}
	}

	/**
	 * set column List of grid.
	 * @param request HttpServletRequest
	 */
	private void setColumnList(HttpServletRequest request)
	{
		List<String> columnList = new ArrayList<String>();

		columnList.add("");
		columnList.add("Title");
		columnList.add("Type");
		columnList.add("Updated On");
		columnList.add("Action(s)");
		columnList.add("identifier");
		request.setAttribute("columns", edu.wustl.cider.util.global.Utility.getcolumns(columnList));
		request.setAttribute("colWidth", "\"0,72,6,14,8,0\"");
		request.setAttribute("isWidthInPercent", true);
		request.setAttribute("colTypes", "\"ch,str,str,str,str,int\"");
		request.setAttribute("colDataTypes", "\"ch,txt,txt,txt,txt,ro\"");
	}

	/**
	 * remove data from session.
	 * @param request HttpServletRequest
	 */
	private void removeAttributesFromSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(Constants.SAVE_GENERATED_SQL);
		session.removeAttribute(Constants.SAVE_TREE_NODE_LIST);
		session.removeAttribute(Constants.ID_NODES_MAP);
		session.removeAttribute(Constants.MAIN_ENTITY_MAP);
		session.removeAttribute(Constants.EXPORT_DATA_LIST);
		session.removeAttribute(Constants.ENTITY_IDS_MAP);
		session.removeAttribute(DAGConstant.TQUIMap);
	}


}
