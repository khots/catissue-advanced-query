package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.NameValueBean;
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

import edu.wustl.query.util.querysuite.QueryModuleException;


public class RetrieveRecentQueriesAction extends Action
{
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		populateRecentQueries( request,  null,
				(ShowRetrieveRecentForm)actionForm);

		return actionMapping.findForward(Constants.SUCCESS);
	
	}
	/**
	 * set the total recent queries count for 
	 * a user in session 
	 * @param requestFor
	 * @param showRetrieveRecentForm
	 */
	private int  setRecentQueriesCount(HttpServletRequest request)
	{
		String sql="select count(*) from  QUERY_EXECUTION_LOG where USER_ID=1 ";
		JDBCDAO dao  = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
		int numberOfQueries=0;

			try
			{
				SessionDataBean sessionDataBean = (SessionDataBean)request.getSession().getAttribute(Constants.SESSION_DATA);
				dao.openSession(sessionDataBean);
				List<List<String>> resultCount =  dao.executeQuery(sql, null, false, null);
//				request.getSession().setAttribute("recentQueriesCount",Integer.valueOf(
//						resultCount.get(0).get(0)));
				numberOfQueries=resultCount.size();//Integer.valueOf(resultCount.get(0).get(0));
				dao.closeSession();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
			return numberOfQueries;
	}
	/**
	 * This method set the total number of recored to display 
	 * @param request
	 * @return
	 */
	private int setDisplayedRecentQueryCount(HttpServletRequest request)
	{ 
//		if((request.getSession().getAttribute("recentQueriesCount"))==null)
//		{
			//setRecentQueriesCount(request);
//		}
		
		//int recentQueriesCount=setRecentQueriesCount(request);// (Integer) request.getSession().getAttribute("recentQueriesCount");
		//int recordCountToDisplay=0;//total results to be displayed
		//set the result count 
//		if(recentQueriesCount==null)
//		{
//			recordCountToDisplay=8;
//		}
//		else
//		{
		int recordCountToDisplay=setRecentQueriesCount(request);//recordCountToDisplay=Integer.valueOf(recentQueriesCount);
	//	}
		
		String showLastfromRequest=request.getParameter("showLast");
		int showLast=0;
		if(showLastfromRequest==null)
		{
			showLast=25;//default
		}
		else
		{
			showLast=Integer.valueOf(request.getParameter("showLast"));

		}
		request.setAttribute(Constants.RESULTS_PER_PAGE, showLast);
		if(showLast<recordCountToDisplay)
		{
			recordCountToDisplay=showLast;
		}
		return recordCountToDisplay;
		
	}
	private int setPagiantion(HttpServletRequest request, String requestFor,
	ShowRetrieveRecentForm showRetrieveRecentForm)
	{
		int totalRecords= setDisplayedRecentQueryCount( request);
		int maxRecords=10;//records per page
		int totalPages=0;
		
		if(totalRecords>0)
		{
			totalPages=totalRecords%maxRecords==0?totalRecords/maxRecords:(totalRecords/maxRecords)+1;
		}

		int pageNum=getPageNumber(request,request.getParameter("requestFor"));
		if(pageNum<1)
		{
			pageNum=1;
		}
		//request.getSession().setAttribute(Constants.RESULTS_PER_PAGE,maxRecords );
		//request.getSession().setAttribute(Constants.PAGE_NUMBER,pageNum );
		request.getSession().setAttribute("totalPages",totalPages);
		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);

		//drop down box
		List<NameValueBean> resultsPerPageOptions=new ArrayList<NameValueBean>();
		resultsPerPageOptions.add(new NameValueBean("25","25"));
		resultsPerPageOptions.add(new NameValueBean("50","50"));
		resultsPerPageOptions.add(new NameValueBean("100","100"));
		resultsPerPageOptions.add(new NameValueBean("200","200"));
		//resultsPerPageOptions.add(new NameValueBean("all","-1"));
		request.setAttribute("resultsPerPageOptions", resultsPerPageOptions);
		
		return totalRecords;
	}
	private ShowRetrieveRecentForm populateRecentQueries(HttpServletRequest request, String requestFor,
			ShowRetrieveRecentForm showRetrieveRecentForm) throws DAOException, QueryModuleException
	{
		int totalCount=setPagiantion(request,requestFor,showRetrieveRecentForm);

		
		/*
		 * retrieve recent Queries //TO DO 
		 * 
		 *  
		 */
		SessionDataBean sessionDataBean = (SessionDataBean)request.getSession().getAttribute(Constants.SESSION_DATA);
		
		String sql="select * from  QUERY_EXECUTION_LOG where USER_ID="+ sessionDataBean.getCsmUserId() +" order by CREATIONTIME desc ";
		int pageNum=getPageNumber(request,request.getParameter("requestFor"));//return the page number for requested
		int recordsPerPage=10;//constant always
		int lastIndex=calculateLastIndex(totalCount,pageNum,recordsPerPage);


		JDBCDAO dao  = (JDBCDAO) DAOFactory.getInstance().getDAO(Constants.JDBC_DAO);
			
			List<List<String>> queryResultList = null;
			try
			{
				dao.openSession(sessionDataBean);
				PagenatedResultData pagiPagenatedResultData = (PagenatedResultData) dao.executeQuery(sql, sessionDataBean, false, false, null, (pageNum-1)*recordsPerPage, lastIndex);//executeQuery(sql, null, false, null);
				queryResultList = pagiPagenatedResultData.getResult();
				dao.closeSession();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}

		//create result Set
		if(queryResultList!=null)
		{
			List<RecentQueriesBean> recentQueriesBeanList=new ArrayList<RecentQueriesBean>();
			//IdList to retrive the title
//			Object[] whereColumn =new Long[queryResultList.size()];
//			for(int i=0;i<queryResultList.size();i++)
//			{
//				whereColumn[i]=
//			}
			
			for(List<String> parameterizedQuery : queryResultList)
			{
			
				
				//write code for json  forming
				RecentQueriesBean recentQueriesBean=new RecentQueriesBean();
				recentQueriesBean.setQueryCreationDate(parameterizedQuery.get(0));//new Timestamp((new Date()).getTime()));
				//recentQueriesBean.setQueryTitle("Title");
				recentQueriesBean.setResultCount(100L);
				recentQueriesBean.setStatus(parameterizedQuery.get(2));
				
				String title=retrieveQueryName(Long.valueOf(parameterizedQuery.get(5)),sessionDataBean);//query id from listparameterizedQuery.get(5)
				recentQueriesBean.setQueryTitle(title);
				recentQueriesBean.setQueyExecutionId(Long.valueOf(parameterizedQuery.get(4)));
				recentQueriesBeanList.add(recentQueriesBean);
			}
			showRetrieveRecentForm.setRecentQueriesBeanList(recentQueriesBeanList);
			request.setAttribute("recentQueriesBeanList",recentQueriesBeanList);
		}

		return showRetrieveRecentForm;

	}

	private int calculateLastIndex(int totalCount, int pageNum, int recordsPerPage)
	{
		if(recordsPerPage*pageNum>totalCount)
		{
			return totalCount-(recordsPerPage*(pageNum-1));
		}
		return recordsPerPage;
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
	
	private String retrieveQueryName(Long id ,SessionDataBean sessionDataBean) throws DAOException
	{
//		Iterator itr = idSet.iterator();
//		int i =0;
//		while(itr.hasNext())
//		{
//			NameValueBean nameValueBean = (NameValueBean)itr.next();
//			whereColumn[i] =Long.valueOf(nameValueBean.getValue());		
//			i++;
//		}				
		String sourceObjectName = ParameterizedQuery.class.getName();
//		String[] selectColumnName = { "name" };
//		String[] whereColumnName = { "id" };
//		String[] whereColumnCondition = { "=" };
//		Object[] whereColumnValue = {id};
//		String joinCondition = null;
		  AbstractDAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
          dao.openSession(sessionDataBean);
//		List list = dao.retrieve(sourceObjectName, selectColumnName,
//				whereColumnName, whereColumnCondition,
//				whereColumnValue, joinCondition);
          String name=((ParameterizedQuery)dao.retrieve( sourceObjectName,  id)).getName();
          dao.closeSession();
		
		return name;
	}
}
