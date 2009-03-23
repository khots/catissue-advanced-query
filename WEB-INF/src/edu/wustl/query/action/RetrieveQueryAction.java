package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;
import edu.wustl.cider.util.CiderUtility;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.CsmUtility;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;


/**
 * @author chetan_patil
 * @created September 12, 2007, 10:24:05 PM
 */
public class RetrieveQueryAction extends Action
{

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward actionForward = null;	
		try
		{
			HttpSession session = request.getSession();

			removeAttributesFromSession(session);
			SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;

			Collection<IParameterizedQuery> authorizedQueryCollection = new ArrayList<IParameterizedQuery>();
			Collection<IParameterizedQuery> sharedQueryCollection = new ArrayList<IParameterizedQuery>();
			initializeParameterizeQueryCollection(request, saveQueryForm,
					  authorizedQueryCollection,
					sharedQueryCollection);
			//user in work flow pop up
			request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
			request.setAttribute(Constants.MY_QUERIES_COUNT,  authorizedQueryCollection.size());
			request.setAttribute(Constants.SHARED_QUERIES_COUNT, sharedQueryCollection.size());
			actionForward = setActionForward(actionMapping, request.getParameter("pageOf"));
		}
		catch (HibernateException hibernateException)
		{
			actionForward = actionMapping.findForward(Constants.FAILURE);
		}
		request.setAttribute(Constants.POPUP_MESSAGE, ApplicationProperties
				.getValue("query.confirmBox.message"));
		if(request.getParameter("pageOf")!=null)
		{
			request.setAttribute(Constants.PAGE_OF,  request.getParameter("pageOf"));
		}
		return actionForward;
	}

	/**
	 * return my queries collection Count
	 * @param dataBean
	 * @return
	 * @throws CSObjectNotFoundException
	 * @throws CSException
	 */
	public int setMyQueryCount(SessionDataBean dataBean) throws CSObjectNotFoundException, CSException
	{

		Collection<IParameterizedQuery> authorizedQueryCollection = new ArrayList<IParameterizedQuery>();
		Collection<IParameterizedQuery> sharedQueryCollection = new ArrayList<IParameterizedQuery>();
		CsmUtility csmUtility=getDefaultCsmUtility();
		csmUtility.checkExecuteQueryPrivilege(authorizedQueryCollection, sharedQueryCollection,dataBean);

		if(authorizedQueryCollection!=null)
		{
			return authorizedQueryCollection.size();
		}
		return 0;
	}

	/**
	 * return shared query Collection Count
	 * @param dataBean
	 * @return
	 * @throws CSObjectNotFoundException
	 * @throws CSException
	 */
	public int setSharedQueryCount(SessionDataBean dataBean) throws CSObjectNotFoundException, CSException
	{

		Collection<IParameterizedQuery> authorizedQueryCollection = new ArrayList<IParameterizedQuery>();
		Collection<IParameterizedQuery> sharedQueryCollection = new ArrayList<IParameterizedQuery>();
		CsmUtility csmUtility=getDefaultCsmUtility();
		csmUtility.checkExecuteQueryPrivilege( authorizedQueryCollection, sharedQueryCollection,dataBean);

		if(sharedQueryCollection!=null)
		{
			return sharedQueryCollection.size();
		}
		return 0;
	}

	private void initializeParameterizeQueryCollection(HttpServletRequest request,
			SaveQueryForm saveQueryForm,
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection) throws CSException,
			CSObjectNotFoundException
	{

			Collection<IParameterizedQuery> finalQueries= null;
			CsmUtility csmUtility=getDefaultCsmUtility();
			csmUtility.checkExecuteQueryPrivilege( authorizedQueryCollection, sharedQueryCollection,(SessionDataBean) request.getSession().getAttribute(Constants.SESSION_DATA));
			setQueryCollectionToSaveQueryForm(saveQueryForm, (String) request.getAttribute("pageOf"), authorizedQueryCollection,
					sharedQueryCollection);
			if(request.getParameter("pageOf")!=null&&
					(Constants.MY_QUERIES_FOR_MAIN_MENU.equals(request.getParameter("pageOf"))||
					Constants.MY_QUERIES.equals(request.getParameter("pageOf"))||
					Constants.MY_QUERIES_FOR_WORKFLOW.equals(request.getParameter("pageOf"))
				)
				)
			{
				finalQueries = authorizedQueryCollection;
				saveQueryForm.setParameterizedQueryCollection(authorizedQueryCollection);
				saveQueryForm=
					setPagiantion(request,request.getParameter("requestFor"),saveQueryForm);
				
			}
			if(request.getParameter("pageOf")!=null&&
					(Constants.SHARED_QUERIES_FOR_MAIN_MENU.equals(request.getParameter("pageOf"))||
					Constants.SAHRED_QUERIES.equals(request.getParameter("pageOf"))||
					Constants.SAHRED_QUERIES_FOR_WORKFLOW.equals(request.getParameter("pageOf"))
					))
			{
				finalQueries = sharedQueryCollection;
				saveQueryForm.setParameterizedQueryCollection(sharedQueryCollection);
			
			}			
			setPagiantion(request,request.getParameter("requestFor"),saveQueryForm);
			saveQueryForm.setParameterizedQueryCollection(getMaxRecord(finalQueries,request));
			populateGrid(request,saveQueryForm.getParameterizedQueryCollection());
	}



	private SaveQueryForm setPagiantion(HttpServletRequest request, String requestFor,
			SaveQueryForm saveQueryForm)
	{
		
		int totalRecords=0;
		if(saveQueryForm.getParameterizedQueryCollection()!=null)
		{
			totalRecords=saveQueryForm.getParameterizedQueryCollection().size();
		}
		
		int startIndex = 0;
		int pageNum = getPageNumber(request, requestFor);
		int maxRecords = getRecordsPerPage(request, requestFor);		
				
		request.getSession().setAttribute(Constants.RESULTS_PER_PAGE,maxRecords );
		request.getSession().setAttribute(Constants.PAGE_NUMBER,pageNum );
		
		startIndex = maxRecords * (pageNum - 1);
		totalRecords=getTotalRecords(request,requestFor,totalRecords);
		int totalPages = getTotalPages(maxRecords, totalRecords);
		request.getSession().setAttribute("totalPages",totalPages);
		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);
		request.getSession().setAttribute("startIndex",startIndex);
		
		List<NameValueBean> resultsPerPageOptions=new ArrayList<NameValueBean>();
		resultsPerPageOptions.add(new NameValueBean("5","5"));
		resultsPerPageOptions.add(new NameValueBean("10","10"));
		resultsPerPageOptions.add(new NameValueBean("15","15"));
		
		request.setAttribute("resultsPerPageOptions", resultsPerPageOptions);
		
		int firstPageNo = 1, currentPageNo;
		currentPageNo = (Integer) (request.getSession().getAttribute("pageNum"));

		if (request.getParameter("firstPageNum") != null)
		{
			firstPageNo = Integer.parseInt(request.getParameter("firstPageNum"));
		}
		else
		{
			if (currentPageNo % 5 == 0)
			{
				firstPageNo = currentPageNo - 4;
			}
			else
			{
				firstPageNo = currentPageNo - (currentPageNo % 5 - 1);
			}
		}
		if (firstPageNo < 5)
		{
			firstPageNo = 1;
		}
		request.setAttribute("firstPageNum", firstPageNo);
		request.setAttribute("numOfPageNums", 5);
		if ((firstPageNo + 4) > (Integer) (request.getSession().getAttribute("totalPages")))
		{
			request.setAttribute("lastPageNum", (Integer) (request.getSession()
					.getAttribute("totalPages")));
		}
		else
		{
			request.setAttribute("lastPageNum", firstPageNo + 4);
		}
		
		return saveQueryForm;
	}

	/**
	 * @param maxRecords
	 * @param totalRecords
	 * @return
	 */
	private int getTotalPages(int maxRecords,
			int totalRecords)
	{
		int totalPages=0;		
		totalPages = totalRecords % maxRecords == 0
				? totalRecords / maxRecords
				: (totalRecords / maxRecords) + 1;		
		return totalPages;
	}
	
	private int getTotalRecords(HttpServletRequest request, String requestFor, int matchingUsersCount)
	{
		int totalRecords=0;
		if(requestFor!=null && request.getSession().getAttribute(Constants.TOTAL_RESULTS)!=null)
		{
			totalRecords=(Integer)request.getSession().getAttribute(Constants.TOTAL_RESULTS);
		}
		else
		{
			totalRecords=matchingUsersCount;
		}
		
		return totalRecords;
	}

	private ActionForward setActionForward(ActionMapping actionMapping, String pageOf)
	{
		ActionForward actionForward;
		actionForward = actionMapping.findForward(Constants.SUCCESS);
		if(pageOf!=null)
		{
			actionForward = actionMapping.findForward(pageOf);
		}
		return actionForward;
	}


	private void removeAttributesFromSession(HttpSession session)
	{
		session.removeAttribute(Constants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(Constants.SAVE_GENERATED_SQL);
		session.removeAttribute(Constants.SAVE_TREE_NODE_LIST);
		session.removeAttribute(Constants.ID_NODES_MAP);
		session.removeAttribute(Constants.MAIN_ENTITY_MAP);
		session.removeAttribute(Constants.EXPORT_DATA_LIST);
		session.removeAttribute(Constants.ENTITY_IDS_MAP);
		session.removeAttribute(DAGConstant.TQUIMap);
	}

	private void setQueryCollectionToSaveQueryForm(SaveQueryForm saveQueryForm, String pageOf,
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection)
	{
		if(pageOf !=null && (Constants.PUBLIC_QUERY_PROTECTION_GROUP.equals(pageOf)||
				Constants.PUBLIC_QUERIES_FOR_WORKFLOW.equals(pageOf)))
		{
			saveQueryForm.setParameterizedQueryCollection(sharedQueryCollection);
		}
		else
		{
			saveQueryForm.setParameterizedQueryCollection(authorizedQueryCollection);
		}
	}

	private Collection<IParameterizedQuery> getMaxRecord(Collection<IParameterizedQuery> queries,HttpServletRequest request)
	{
		Collection<IParameterizedQuery> coll = new ArrayList<IParameterizedQuery>();
		int maxRec = (Integer) request.getSession().getAttribute(Constants.RESULTS_PER_PAGE);
		int strartIndex = (Integer) request.getSession().getAttribute("startIndex");
		IParameterizedQuery arr[] = new IParameterizedQuery[queries.size()];
		queries.toArray(arr);
		for (int i=0;i<maxRec;i++)
		{
			if((i+strartIndex)==arr.length)
				break;
			else
				coll.add(((IParameterizedQuery)arr[i+strartIndex])); 			
		}		
		return coll;
	}
	private int getRecordsPerPage(HttpServletRequest request, String requestFor)
	{
		Object recordsPerPageObj=getSessionAttribute(request,Constants.RESULTS_PER_PAGE);
		int maxRecords;
		if(recordsPerPageObj!=null && requestFor!=null)
		{
			maxRecords=Integer.parseInt(recordsPerPageObj.toString());
		}
		else
		{
			maxRecords=10;
		}
		return maxRecords;
	}
	private int getPageNumber(HttpServletRequest request, String requestFor)
	{
		Object pageNumObj=getSessionAttribute(request,Constants.PAGE_NUMBER);
		int pageNum=0;
		if(pageNumObj!=null && requestFor!=null)
		{
			pageNum=Integer.parseInt(pageNumObj.toString());
		}
		else
		{
			pageNum=1;
		}
		
		return pageNum;
	}

	private Object getSessionAttribute(HttpServletRequest request, String attributeName)
	{
		Object object=null;
		if(request!=null)
		{
			object=request.getParameter(attributeName);
			if(object==null)
			{
				object=request.getAttribute(attributeName);
				if(object==null)
				{
					object=request.getSession().getAttribute(attributeName);
				}
			}
		}
		
		return object;
	}
	
	/**
	 * this function returns the csmUtility class 
	 * @return
	 */
	public static CsmUtility getDefaultCsmUtility()
	{
		return (CsmUtility)Utility.getObject(Variables.csmUtility);
	}
	
	public void populateGrid(HttpServletRequest request,Collection<IParameterizedQuery> queryColletion)
	{
		request.setAttribute("identifierFieldIndex",3);
		final List<Object[]> attributesList = new ArrayList<Object[]>();
		Object[] attributes = null;
		for (IParameterizedQuery query : queryColletion) {
			attributes = new Object[4];
			if (query != null) {
				if (query.getName() != null) {
					attributes[1] = query.getName();
				} else {
					attributes[1] = "";
				}
				attributes[2] = query.getType();
				attributes[3] = query.getId().toString();
			}
			attributesList.add(attributes);

		}
		request.setAttribute("msgBoardItemList", CiderUtility
				.getmyData(attributesList));
		List<String> columnList = new ArrayList<String>();

		columnList.add(" &nbsp;");
		columnList.add("Query Title");
		columnList.add("Query Type");
		columnList.add("identifier");
		request.setAttribute("columns", edu.wustl.cider.util.global.Utility.getcolumns(columnList));
		request.setAttribute("colWidth", "\"4,80,16,0\"");
		request.setAttribute("isWidthInPercent", true);
		request.setAttribute("colTypes", "\"ch,str,str,int\"");
		request.setAttribute("colDataTypes", "\"ch,txt,txt,ro\"");
	}
	
}


