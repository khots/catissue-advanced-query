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
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.util.dbManager.DBUtil;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.util.global.Constants;

public class RetrieveWorkflowAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setPagiantion(request, request.getParameter("requestFor"));

		return mapping.findForward(request.getParameter(Constants.PAGE_OF));
	}

	/**
	 * @param request
	 * @param requestFor
	 */
	private void setPagiantion(HttpServletRequest request, String requestFor) {
		int totalRecords = setWorkflowCount();

		int startIndex = 0;
		int pageNum = getPageNumber(request, requestFor);
		int maxRecords = getRecordsPerPage(request, requestFor);
		startIndex = maxRecords * (pageNum - 1);

		request.getSession().setAttribute(Constants.RESULTS_PER_PAGE,
				maxRecords);
		request.getSession().setAttribute(Constants.PAGE_NUMBER, pageNum);
		int totalPages = getTotalPages(maxRecords, totalRecords);
		request.getSession().setAttribute("totalPages", totalPages);
		request.getSession()
				.setAttribute(Constants.TOTAL_RESULTS, totalRecords);

		List<NameValueBean> resultsPerPageOptions = new ArrayList<NameValueBean>();
		resultsPerPageOptions.add(new NameValueBean("5", "5"));
		resultsPerPageOptions.add(new NameValueBean("10", "10"));
		resultsPerPageOptions.add(new NameValueBean("15", "15"));

		request.setAttribute("resultsPerPageOptions", resultsPerPageOptions);
		request.setAttribute(Constants.PAGE_OF, request
				.getParameter(Constants.PAGE_OF));

		setPaginationVars(request);

		// to set queries to form

		Session session1 = null;
		List<Workflow> workflowList = new ArrayList<Workflow>();
		try {
			session1 = DBUtil.getCleanSession();
			Query query = null;
			query = session1.createQuery("from " + Workflow.class.getName()
					+ "  Workflow order by Workflow.id desc");

			query.setFirstResult(startIndex);
			query.setMaxResults(maxRecords);
			workflowList = query.list();

			request.setAttribute("workflowList", workflowList);
			request.setAttribute("identifierFieldIndex", 2);

			// starts
			final List<Object[]> attributesList = new ArrayList<Object[]>();
			Object[] attributes = null;
			for (Workflow workflow : workflowList) {
				attributes = new Object[3];
				// attributes[0]="0";
				if (workflow != null) {
					// attributes[0]=1;
					if (workflow.getName() != null) {
						attributes[1] = workflow.getName();
					} else {
						attributes[1] = "";
					}
					attributes[2] = workflow.getId().toString();

				}
				attributesList.add(attributes);

			}
			request.setAttribute("msgBoardItemList", CiderUtility
					.getmyData(attributesList));
			List<String> columnList = new ArrayList<String>();

			columnList.add(" &nbsp;");
			columnList.add("Workflow Name");
			columnList.add("identifier");

			request.setAttribute("columns", Utility.getcolumns(columnList));
			request.setAttribute("colWidth", "\"4,96,0\"");
			request.setAttribute("isWidthInPercent", true);
			request.setAttribute("colTypes", "\"ch,str,int\"");
			request.setAttribute("colDataTypes", "\"ch,txt,ro\"");
		} catch (BizLogicException e) {

			e.printStackTrace();
		} finally {
			session1.close();
		}

	}

	/**
	 * @param maxRecords
	 * @param totalRecords
	 * @return
	 */
	private int getTotalPages(int maxRecords, int totalRecords) {
		int totalPages = 0;
		totalPages = totalRecords % maxRecords == 0 ? totalRecords / maxRecords
				: (totalRecords / maxRecords) + 1;
		return totalPages;
	}

	/**
	 * this method returns the 
	 * number of workflows
	 * @return
	 */
	public int setWorkflowCount() {

		Session session1 = null;
		try {
			session1 = DBUtil.getCleanSession();
			Query query = null;
			query = session1.createQuery("from " + Workflow.class.getName());
			List workflowList = query.list();

			if (workflowList != null) {
				return workflowList.size();
			}
		} catch (BizLogicException e) {
			e.printStackTrace();
		} finally {
			session1.close();
		}
		return 0;
	}

	/**
	 * @param request
	 * @param requestFor
	 * @return
	 */
	private int getPageNumber(HttpServletRequest request, String requestFor) {
		Object pageNumObj = getSessionAttribute(request, Constants.PAGE_NUMBER);
		int pageNum = 0;
		if (pageNumObj != null && requestFor != null) {
			pageNum = Integer.parseInt(pageNumObj.toString());
		} else {
			pageNum = 1;
		}

		return pageNum;
	}

	/**
	 * @param request
	 * @param attributeName
	 * @return
	 */
	private Object getSessionAttribute(HttpServletRequest request,
			String attributeName) {
		Object object = null;
		if (request != null) {
			object = request.getParameter(attributeName);
			if (object == null) {
				object = request.getAttribute(attributeName);
				if (object == null) {
					object = request.getSession().getAttribute(attributeName);
				}
			}
		}

		return object;
	}

	/**
	 * @param request
	 * @param requestFor
	 * @return
	 */
	private int getRecordsPerPage(HttpServletRequest request, String requestFor) {
		Object recordsPerPageObj = getSessionAttribute(request,
				Constants.RESULTS_PER_PAGE);
		int maxRecords;
		if (recordsPerPageObj != null && requestFor != null) {
			maxRecords = Integer.parseInt(recordsPerPageObj.toString());
		} else {
			maxRecords = 10;
		}
		return maxRecords;
	}

	/**
	 * @param request
	 */
	private void setPaginationVars(HttpServletRequest request) {
		int firstPageNo = 1, currentPageNo;
		currentPageNo = (Integer) (request.getSession().getAttribute("pageNum"));

		if (request.getParameter("firstPageNum") != null) {
			firstPageNo = Integer
					.parseInt(request.getParameter("firstPageNum"));
		} else {
			if (currentPageNo % 5 == 0) {
				firstPageNo = currentPageNo - 4;
			} else {
				firstPageNo = currentPageNo - (currentPageNo % 5 - 1);
			}
		}
		if (firstPageNo < 5) {
			firstPageNo = 1;
		}
		request.setAttribute("firstPageNum", firstPageNo);
		request.setAttribute("numOfPageNums", 5);
		if ((firstPageNo + 4) > (Integer) (request.getSession()
				.getAttribute("totalPages"))) {
			request.setAttribute("lastPageNum", (Integer) (request.getSession()
					.getAttribute("totalPages")));
		} else {
			request.setAttribute("lastPageNum", firstPageNo + 4);
		}
	}

}
