
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.cider.util.CiderUtility;
import edu.wustl.cider.util.global.Utility;
import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;

/**
 * This class retrieves work flows.
 * and do pagination
 * @author chitra_garg
 *
 */
public class RetrieveWorkflowAction extends AbstractQueryBaseAction
{

	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		setPagiantion(request, request.getParameter("requestFor"));

		return mapping.findForward(request.getParameter(Constants.PAGE_OF));
	}

	/**
	 * @param request HttpServletRequest
	 * @param requestFor String
	 * @throws DAOException DAOException
	 */
	private void setPagiantion(HttpServletRequest request, String requestFor) throws DAOException
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		String queryNameLike = "";
		if (request.getParameter(Constants.QUERY_NAME_LIKE) != null
				&& !request.getParameter(Constants.QUERY_NAME_LIKE).equals(""))
		{
			queryNameLike = (String) request.getParameter(Constants.QUERY_NAME_LIKE);
		}
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		int maxRecords = getRecordsPerPage(request, requestFor);
		int pageNum = getPageNumber(request, requestFor);
		int startIndex = 0;
		startIndex = maxRecords * (pageNum - 1);

		// to set queries to form
		//Session session1 = null;
		List<Workflow> workflowList = new ArrayList<Workflow>();

		int totalRecords = workflowBizLogic.retrieveWorkflowList(startIndex, queryNameLike,
				sessionDataBean, maxRecords, workflowList);

		//startIndex = maxRecords * (pageNum - 1);
		request.getSession().setAttribute(Constants.RESULTS_PER_PAGE, maxRecords);
		request.getSession().setAttribute(Constants.PAGE_NUMBER, pageNum);
		int totalPages = getTotalPages(maxRecords, totalRecords);
		request.getSession().setAttribute("totalPages", totalPages);
		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);

		List<NameValueBean> resultsPerPageOptions = new ArrayList<NameValueBean>();
		resultsPerPageOptions.add(new NameValueBean("5", "5"));
		resultsPerPageOptions.add(new NameValueBean("10", "10"));
		resultsPerPageOptions.add(new NameValueBean("15", "15"));

		request.setAttribute("resultsPerPageOptions", resultsPerPageOptions);
		request.setAttribute(Constants.PAGE_OF, request.getParameter(Constants.PAGE_OF));
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

		setPaginationVars(request);

		request.setAttribute("workflowList", workflowList);
		request.setAttribute("identifierFieldIndex", 5);

		// starts
		setGridData(request, workflowList);

	}

	/**
	 * set the workflow list in grid
	 * @param request HttpServletRequest
	 * @param workflowList workflow List
	 */
	private void setGridData(HttpServletRequest request, List<Workflow> workflowList)
	{
		final List<Object[]> attributesList = new ArrayList<Object[]>();
		Object[] attributes = null;
		for (Workflow workflow : workflowList)
		{
			attributes = new Object[6];
			// attributes[0]="0";
			if (workflow != null)
			{
				// attributes[0]=1;
				if (workflow.getName() != null)
				{
					String workflowName = workflow.getName().replaceAll("'", "&#39;").replaceAll(
							",", "&#44;"); // .replaceAll("\\", "&#92;")
					String description = workflow.getDescription().replaceAll("'", "&#39;")
							.replaceAll(",", "&#44;");

					attributes[1] = "<div title='Name: " + workflowName + "&#10; Description: "
							+ description + "'>" + workflowName + "</div>";
					//					}
				}
				else
				{
					attributes[1] = "";
				}
				String createdDate = "";
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(workflow.getCreatedOn());
				createdDate = edu.wustl.common.util.Utility.parseDateToString(workflow
						.getCreatedOn(), Constants.DATE_PATTERN_MM_DD_YYYY_HH_MM);
				attributes[2] = createdDate;
				attributes[4] = workflow.getId();
				attributes[3] = "<div title='Edit' align='center'>"
						+ "<a href='SearchObject.do?pageOf=pageOfWorkflow&operation=search&id="
						+ workflow.getId()
						+ "' target='_top'><img src='images/advancequery/application_edit.png'"
						+ " alt='Edit' border='0' title='Edit' /></a></div>";

			}
			String createdDate = "";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(workflow.getCreatedOn());
			createdDate = edu.wustl.common.util.Utility.parseDateToString(workflow.getCreatedOn(),
					Constants.DATE_PATTERN_MM_DD_YYYY_HH_MM);

			attributesList.add(attributes);

			attributes[2] = createdDate;
			attributes[4] = workflow.getId();
			attributes[3] = "<div title='Edit' align='center'>"
					+ "<a href='SearchObject.do?pageOf=pageOfWorkflow&operation=search&id="
					+ workflow.getId()
					+ "' target='_top'><img src='images/advancequery/application_edit.png'"
					+ " alt='Edit' border='0' title='Edit' /></a></div>";

		}
		request.setAttribute("msgBoardItemList", CiderUtility.getmyData(attributesList));
		List<String> columnList = new ArrayList<String>();

		columnList.add("");
		columnList.add("Name");
		columnList.add("Created On");
		columnList.add("Action(s)");
		columnList.add("identifier");

		request.setAttribute("columns", Utility.getcolumns(columnList));
		request.setAttribute("colWidth", "\"0,78,14,8,0\"");
		request.setAttribute("isWidthInPercent", true);
		request.setAttribute("colTypes", "\"ch,str,str,str,int\"");
		request.setAttribute("colDataTypes", "\"ch,txt,txt,txt,ro\"");
	}

	/**
	 * @param maxRecords max Records
	 * @param totalRecords total Records
	 * @return total Pages
	 */
	private int getTotalPages(int maxRecords, int totalRecords)
	{
		int totalPages = 0;
		totalPages = totalRecords % maxRecords == 0
				? totalRecords / maxRecords
				: (totalRecords / maxRecords) + 1;
		return totalPages;
	}

	/**
	 * @param request HttpServletRequest
	 * @param requestFor String
	 * @return page Num
	 */
	private int getPageNumber(HttpServletRequest request, String requestFor)
	{
		Object pageNumObj = getSessionAttribute(request, Constants.PAGE_NUMBER);
		int pageNum = 0;
		if (pageNumObj != null && requestFor != null)
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
	 * @param request HttpServletRequest
	 * @param attributeName String
	 * @return Object
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
	 * @param requestFor String request For page number.
	 * @return max Records per page
	 */
	private int getRecordsPerPage(HttpServletRequest request, String requestFor)
	{
		Object recordsPerPageObj = getSessionAttribute(request, Constants.RESULTS_PER_PAGE);
		int maxRecords;
		if (recordsPerPageObj != null && requestFor != null)
		{
			maxRecords = Integer.parseInt(recordsPerPageObj.toString());
		}
		else
		{
			maxRecords = 10;
		}
		return maxRecords;
	}

	/**
	 * @param request HttpServletRequest
	 */
	private void setPaginationVars(HttpServletRequest request)
	{
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
	}

}
