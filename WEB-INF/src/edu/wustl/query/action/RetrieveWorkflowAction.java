package edu.wustl.query.action;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.wustl.cider.util.CiderUtility;
import edu.wustl.cider.util.global.Utility;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.util.dbManager.DBUtil;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;


public class RetrieveWorkflowAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		setPagiantion( request, request.getParameter("requestFor")
				);

		return mapping.findForward(request.getParameter(Constants.PAGE_OF));
	}
	private void setPagiantion(HttpServletRequest request, String requestFor
			)
	{
		int totalRecords=setWorkflowCount();
		
		int recordsPerPage=1;
		
		int startIndex=0;
		int pageNum=getPageNumber(request,requestFor);
		String pageOf = request.getParameter(Constants.PAGEOF);
		int maxRecords = getRecordsPerPage(request,requestFor);
		if(maxRecords==-1)
		{
			maxRecords=10;
		}
		if(pageNum<1)
		{
			pageNum=1;
		}
		startIndex=maxRecords*(pageNum-1);
		
		int totalPages=0;

		totalRecords=getTotalRecords(request,requestFor,totalRecords);

		if(totalRecords>0)
		{
			totalPages=totalRecords%maxRecords==0?totalRecords/maxRecords:(totalRecords/maxRecords)+1;
		}
		request.getSession().setAttribute(Constants.RESULTS_PER_PAGE,maxRecords );
		request.getSession().setAttribute(Constants.PAGE_NUMBER,pageNum );
		request.getSession().setAttribute("totalPages",totalPages);
		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);
		
		List<NameValueBean> resultsPerPageOptions=new ArrayList<NameValueBean>();
		resultsPerPageOptions.add(new NameValueBean("10","10"));
		resultsPerPageOptions.add(new NameValueBean("20","20"));
		resultsPerPageOptions.add(new NameValueBean("30","30"));
		
		request.setAttribute("resultsPerPageOptions", resultsPerPageOptions);
		request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
		
		//to set queries to form 

		Session session1=DBUtil.currentSession();
		Query query = null;
		query = session1.createQuery("from "+Workflow.class.getName());
	
		query.setFirstResult(startIndex);
		query.setMaxResults(maxRecords);
		List<Workflow> workflowList = query.list();
		//workflowForm.setParameterizedQueryCollection(temp);
		request.setAttribute("workflowList", workflowList);
		request.setAttribute("identifierFieldIndex", 2);
		
		
		//starts
		final List<Object[]> attributesList = new ArrayList<Object[]>();
		Object[] attributes = null;
		for (Workflow  workflow : workflowList)
		{
			 attributes = new Object[3];
			// attributes[0]="0";
			if (workflow != null)
			{
				//attributes[0]=1;
				if(workflow.getName()!=null)
				{
					attributes[1] = workflow.getName();
				}
				else
				{
					attributes[1] ="";
				}
				attributes[2]= workflow.getId().toString();

			}
				attributesList.add(attributes);

		}

		//ends
		//request.setAttribute("msgBoardItemList", workflowList);
		request.setAttribute("msgBoardItemList", CiderUtility.getmyData(attributesList));
		List<String> columnList = new ArrayList<String>();

		columnList.add(" &nbsp;");
		columnList.add("Workflow Name");
		columnList.add("identifier");

		request.setAttribute("columns", Utility.getcolumns(columnList));
		List<String> columnWidthsList = Utility.getColumnWidths(columnList.size());
		request.setAttribute("colWidth","\"4,96,0\"");//Utility.getcolWidth(columnWidthsList, true));
		
		request.setAttribute("isWidthInPercent", true);
		request.setAttribute("colTypes", "\"ch,str,int\"");
		request.setAttribute("colDataTypes", "\"ch,txt,ro\"");
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
	public int setWorkflowCount()
	{

		Session session1=DBUtil.currentSession();
		Query query = null;
		query = session1.createQuery("from "+Workflow.class.getName());
		List workflowList = query.list();
		if(workflowList!=null)
		{
			return workflowList.size();
		}
		return 0;
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

}
