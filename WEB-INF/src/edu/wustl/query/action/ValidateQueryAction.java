
package edu.wustl.query.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * When the user searches or saves a query , the query is checked for the conditions like DAG should not be empty , is there 
 * at least one node in view on define view page and does the query contain the main object. If all the conditions are satisfied 
 * further process is done else corresponding error message is shown.
 * 
 * @author shrutika_chintal
 *
 */
public class ValidateQueryAction extends AbstractQueryBaseAction
{

	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String buttonClicked = request.getParameter(Constants.BUTTON_CLICKED);
		String dataQueryId= request.getParameter("dataQueryId");
		// dataKey defines that ajax call from SimpleSearchDataView.jsp is made to get the updated message.
		String dataKey = request.getParameter(Constants.UPDATE_SESSION_DATA);
		HttpSession session = request.getSession();
		
		//retrieve the Selected Project from the GetCounts.jsp and set it in session
		String selectedProject = request.getParameter(Constants.SELECTED_PROJECT);
		session.setAttribute(Constants.SELECTED_PROJECT,selectedProject);
		//Added By Baljeet
		//session.removeAttribute("allLimitExpressionIds");
		String writeresponse= buttonClicked;
		String errormesg = "";
		if (dataKey != null && dataKey.equals(Constants.UPDATE_SESSION_DATA))
		{
			//if dataKey is not null retrieve the data from the session and send it to the jsp
			writeresponse = updateSesionData(response, session);
			
		}
		else
		{	
			IParameterizedQuery parameterizedQuery=null; 
			if(dataQueryId==null)
		     {
				parameterizedQuery = (IParameterizedQuery)session
				.getAttribute(Constants.QUERY_OBJECT);

		     }
			else
			{
				String allQueriesConitionStr = request.getParameter(Constants.QUERY_CONDITION_STRING);
				IFactory factory = AbstractFactoryConfig.getInstance ().getBizLogicFactory ();
				Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap = (Map<Long, Map<Integer, ICustomFormula>>)session.getAttribute(Constants.QUERY_CUSTOM_FORMULA_MAP);
				IBizLogic bizLogic=factory.getBizLogic (Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		    	 try{
		    		 parameterizedQuery  =(ParameterizedQuery) bizLogic.retrieve(ParameterizedQuery.class
		 					.getName(),Long.valueOf(dataQueryId));
		    		 //It will remove empty conditions & all parameters in the query
		    		 errormesg = IQueryUpdationUtil.updateQueryForParameters(parameterizedQuery, allQueriesConitionStr,
		    					eachQueryCFMap);
		    	     //request.setAttribute(Constants.DATA_QUERY_ID, dataQueryId);   
		    	     //request.setAttribute(Constants.COUNT_QUERY_ID, countQueryId);
		    	 
		    	 }catch (BizLogicException daoException)
		 		{
		 			ActionErrors errors = Utility.setActionError(daoException.getMessage(),"errors.item");
		 			saveErrors(request, errors);
		 		} 
			}
	
			
		 writeresponse = getValidationResponse(request,  dataQueryId,
				 errormesg, parameterizedQuery);
	}
		 
		 response.setContentType(Constants.CONTENT_TYPE_TEXT);	  
		response.getWriter().write(writeresponse);
		return null;
	}

	private String getValidationResponse(HttpServletRequest request,
			 String dataQueryId,
			String errormesg,
			IParameterizedQuery parameterizedQuery) throws QueryModuleException {
	
		String writeresponse = request.getParameter(Constants.BUTTON_CLICKED);
		if(!(errormesg.length()>0))
		 {
			if(parameterizedQuery!=null && dataQueryId==null && parameterizedQuery.getType().equals(QueryType.GET_COUNT.type))
		    {
		    	parameterizedQuery.setName(request.getParameter("queyTitle"));
		    }
		    String validationMessage = Utility.getValidationMessage(request, parameterizedQuery);
		    if (validationMessage != null)
		   {
			 writeresponse=validationMessage;
		   }
		}
		 else
		 {
			 writeresponse = errormesg;
		 }
		return writeresponse;
	}

	/**
	 * This method retrieves the data from the session to send it to the jsp
	 * @param response
	 * @param session
	 * @return
	 */
	private String updateSesionData(HttpServletResponse response, HttpSession session)
	{
		String message = (String) session
		    .getAttribute(Constants.MESSAGE_FOR_ORDERING);
		String isListEmpty = (String) session.getAttribute(Constants.IS_LIST_EMPTY);

		if ((isListEmpty != null && isListEmpty.equals(Constants.FALSE)) || message == null)
		{
			message = " "; //if empty string is returned mac+safari gives problem and if message is set to null mozilla gives problem.
		}
		response.setContentType("text/html");
		return message;
	}
}
