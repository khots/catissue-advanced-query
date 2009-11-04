
package edu.wustl.query.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.QueryWorkflowBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.htmlprovider.GenerateHTMLDetails;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * @author chitra_garg
 *
 */
public class ParamerizedQueryPopUpAction extends AbstractQueryBaseAction
{

	/**
	* This action is used for processing Work flow object
	*
	* @param mapping
	*            ActionMapping.
	* @param form
	*            ActionForm.
	* @param request
	*            HttpServletRequest.
	* @param response
	*            HttpServletResponse.
	* @return ActionForward ActionFowrward.
	* @throws Exception
	*            Exception
	*
	* @see org.apache.struts.action.Action#execute(org.apache.s
	*      truts.action.ActionMapping, org.apache.struts.action.ActionForm,
	*      javax.servlet.http.HttpServletRequest,
	*      javax.servlet.http.HttpServletResponse)
	*/
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		HttpSession session = request.getSession();
		String parameter = request.getParameter(Constants.Query_Type);
		String htmlContents = "";
		Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap = new HashMap<Long, Map<Integer, ICustomFormula>>();
		if (parameter != null && parameter.equalsIgnoreCase(Constants.QUERY_TYPE_GET_DATA))
		{
			Map<Long, IParameterizedQuery> queryIdMap = (Map) session
					.getAttribute(Constants.QUERY_ID_MAP);
			String queryIdString = (String) session.getAttribute(Constants.QUERY_ID_STRING);
			if (queryIdMap != null && !queryIdMap.isEmpty())
			{
				IParameterizedQuery parameterizedQuery = queryIdMap
						.get(Long.valueOf(queryIdString));
				htmlContents = createHTMLForDataPQ(eachQueryCFMap, parameterizedQuery, request);
				session.setAttribute(Constants.QUERY_CUSTOM_FORMULA_MAP, eachQueryCFMap);
			}
		}
		else
		{
			//Set<Long> queriesIdsSet = null;
			Map<Long, IParameterizedQuery> queryIdMap = null;
			if (session.getAttribute(Constants.QUERY_ID_MAP) != null)
			{
				queryIdMap = (Map<Long, IParameterizedQuery>) session
						.getAttribute(Constants.QUERY_ID_MAP);
			}
			if (session.getAttribute(Constants.PQ_ID_SET) != null)
			{
				//	queriesIdsSet = (HashSet<Long>) session.getAttribute(Constants.PQ_ID_SET);
				htmlContents = createHTML(request, eachQueryCFMap, queryIdMap);
				session.setAttribute("eachQueryCFMap", eachQueryCFMap);
			}

		}
		request.setAttribute(Constants.HTML_CONTENTS, htmlContents);
		request.setAttribute(Constants.QUERY_ID_STRING, session
				.getAttribute(Constants.QUERY_ID_STRING));
		session.removeAttribute(Constants.HTML_CONTENTS);

		// session.getAttribute("queriesIdString");
		// session.getAttribute(Constants.HTML_CONTENTS);
		return mapping.findForward(Constants.SUCCESS);
	}

	/**
	 * This method generates the HTML for the given query id's String call the
	 * SavedQueryHtmlProvider to do this.
	 * @param request HttpServletRequest
	 * @param eachQueryCFMap custom formula map
	 * @param queryIdMap query Id Map
	 * @return htmlContents string
	 * @throws BizLogicException BizLogicException
	 * @throws PVManagerException PVManagerException.
	 */
	private String createHTML(HttpServletRequest request,
			Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap,
			Map<Long, IParameterizedQuery> queryIdMap) throws BizLogicException, PVManagerException
	{
		HttpSession session = request.getSession();
		Long workflowId = Long.valueOf(request.getParameter(Constants.WORKFLOW_ID));

		Map<Long, Long> executionIdMap = Utility.generateQueryExecIdMap(request, workflowId);

		Map<Long, Date> executionDateMap = null;
		if (!executionIdMap.isEmpty())//executionIdMap.size() != 0)
		{
			executionDateMap = Utility.generateQueryExecDateMap(executionIdMap.values());
		}

		StringBuffer htmlContents = new StringBuffer("");

		Map<QueryWorkflowBean, IParameterizedQuery> allSessionUpdatedQueries = (HashMap<QueryWorkflowBean, IParameterizedQuery>) session
				.getAttribute("allSessionUpdatedQueries");
		if (allSessionUpdatedQueries == null)
		{
			allSessionUpdatedQueries = new HashMap<QueryWorkflowBean, IParameterizedQuery>();

		}

		Set<Long> queriesIDSet = queryIdMap.keySet();
		Iterator<Long> queriesIdsItr = queriesIDSet.iterator();
		Map<String, QueryableAttributeInterface> enumuratedMap = new HashMap<String, QueryableAttributeInterface>();
		while (queriesIdsItr.hasNext())
		{
			Long queryId = queriesIdsItr.next();
			Long queryExecutionId = executionIdMap.get(queryId);

			QueryWorkflowBean queryWorkflowBean = new QueryWorkflowBean();
			queryWorkflowBean.setQueryId(queryId);
			queryWorkflowBean.setWorkflowId(workflowId);
			queryWorkflowBean.setProjectId(Long.valueOf(request
					.getParameter(Constants.SELECTED_PROJECT)));
			IParameterizedQuery parameterizedQuery = queryIdMap.get(queryId);// retrieveQuery(queryId);

			if (queryExecutionId != null)
			{

				parameterizedQuery = Utility.setParametersForLatestExecution(parameterizedQuery,
						queryExecutionId, executionDateMap.get(queryExecutionId));
			}

			Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("");
			generateHTMLDetails.setAttributeChecked(false);
			generateHTMLDetails.setPermissibleValuesChecked(false);
			String queryhtmlContents = new SavedQueryHtmlProvider().getHTMLForSavedQuery(
					parameterizedQuery, false, Constants.EXECUTE_QUERY_PAGE, customFormulaIndexMap,
					generateHTMLDetails, false);
			//			session.setAttribute(Constants.ENUMRATED_ATTRIBUTE, generateHTMLDetails
			//					.getEnumratedAttributeMap());

			enumuratedMap.putAll(generateHTMLDetails.getEnumratedAttributeMap());
			// Adding Query Title related issue
			String queryNameStr = "<table width='100%'><tr class=\"td_subtitle\" "
					+ "width='100%'><td width=\"12%\" "
					+ "valign=\"top\" class=\"blue_title\">&nbsp; Query Title : &nbsp;</td>"
					+ "<td id = \"queryTitle1\" class=\"blue_title\" align=\"left\" "
					+ " valign=\"top\">" + "<label valign=\"top\" >&nbsp;"
					+ parameterizedQuery.getName()
					+ "</label></td></tr><tr class=\"td_greydottedline_horizontal\">"
					+ "<td width=\"10%\">" + "</td><td></td></tr><tr height='5'></tr></table>";
			queryhtmlContents = queryNameStr + queryhtmlContents + "</br>";

			// Maintain a map related to custom formulas in a Query
			// customFormulaIndexMap
			eachQueryCFMap.put(parameterizedQuery.getId(), customFormulaIndexMap);
			//htmlContents= htmlContents + queryhtmlContents + "";
			//for pmd
			htmlContents.append(queryhtmlContents);
			htmlContents.append("");
			allSessionUpdatedQueries.put(queryWorkflowBean, parameterizedQuery);
		}
		session.setAttribute("allSessionUpdatedQueries", allSessionUpdatedQueries);
		session.setAttribute(Constants.ENUMRATED_ATTRIBUTE, enumuratedMap);

		return htmlContents.toString();
	}

	/**
	 * This method generates the HTML for the given query id's String call the
	 * SavedQueryHtmlProvider to do this.
	 * @param eachQueryCFMap custom formula map
	 * @param parameterizedQuery query object
	 * @param request http request
	 * @return generated html
	 * @throws BizLogicException bizlogic exception
	 * @throws PVManagerException VI exception
	 */
	private String createHTMLForDataPQ(Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap,
			IParameterizedQuery parameterizedQuery, HttpServletRequest request)
			throws BizLogicException, PVManagerException
	{
		StringBuffer htmlContents = new StringBuffer();
		GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
		generateHTMLDetails.setSearchString("");
		generateHTMLDetails.setAttributeChecked(false);
		generateHTMLDetails.setPermissibleValuesChecked(false);
		Map<Integer, ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
		String queryhtmlContents = new SavedQueryHtmlProvider().getHTMLForSavedQuery(
				parameterizedQuery, false, Constants.EXECUTE_QUERY_PAGE, customFormulaIndexMap,
				generateHTMLDetails, false);

		request.getSession().setAttribute(Constants.ENUMRATED_ATTRIBUTE,
				generateHTMLDetails.getEnumratedAttributeMap());
		// Adding Query Title related issue
		String queryNameStr = "<table width='100%'><tr class=\"td_subtitle\" "
				+ "width='100%'><td width=\"12%\" "
				+ "valign=\"top\" class=\"blue_title\">&nbsp; Query Title : &nbsp;</td>"
				+ "<td id = \"queryTitle1\" class=\"blue_title\" align=\"left\"  valign=\"top\">"
				+ "<label valign=\"top\" >&nbsp;" + parameterizedQuery.getName()
				+ "</label></td></tr><tr class=\"td_greydottedline_horizontal\"><td width=\"10%\">"
				+ "</td><td></td></tr><tr height='5'></tr></table>";
		htmlContents.append(queryNameStr).append(queryhtmlContents);
		htmlContents.append("</br>");
		// Maintain a map related to custom formulas in a Query
		// customFormulaIndexMap
		eachQueryCFMap.put(parameterizedQuery.getId(), customFormulaIndexMap);
		return htmlContents.toString();
	}
}
