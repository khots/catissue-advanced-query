
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;

/**
 * When the user searches or saves a query , the query is checked for the conditions like DAG should not be empty , is there 
 * at least one node in view on define view page and does the query contain the main object. If all the conditions are satisfied 
 * further process is done else corresponding error message is shown.
 * 
 * @author shrutika_chintal
 *
 */
public class ValidateQueryAction extends Action
{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
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
		if (dataKey != null && dataKey.equals(Constants.UPDATE_SESSION_DATA))
		{
			//if dataKey is not null retrieve the data from the session and send it to the jsp
			writeresponse = updateSesionData(response, session);
			
		}
		else{	
			IParameterizedQuery parameterizedQuery=null; 
			if(dataQueryId==null)
		     {
				parameterizedQuery = (IParameterizedQuery)session
				.getAttribute(Constants.QUERY_OBJECT);

		     }
			else{
				IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
		 				.getValue("app.bizLogicFactory"), "getBizLogic",
		 				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		    	 try{
		    		 parameterizedQuery  =(ParameterizedQuery) bizLogic.retrieve(ParameterizedQuery.class
		 					.getName(),Long.valueOf(dataQueryId));
		    	     //request.setAttribute(Constants.DATA_QUERY_ID, dataQueryId);   
		    	     //request.setAttribute(Constants.COUNT_QUERY_ID, countQueryId);
		    	 
		    	 }catch (DAOException daoException)
		 		{
		 			ActionErrors errors = Utility.setActionError(daoException.getMessage(),"errors.item");
		 			saveErrors(request, errors);
		 		} 
				
				
			}
			
		    if(parameterizedQuery!=null && dataQueryId==null && parameterizedQuery.getType().equals(QueryType.GET_COUNT.type))
		    {
		    	parameterizedQuery.setName(request.getParameter("queyTitle"));
		    }
		    String validationMessage = ValidateQueryBizLogic.getValidationMessage(request,parameterizedQuery);
		    if (validationMessage != null)
		   {
			 response.setContentType(Constants.CONTENT_TYPE_TEXT);
			 writeresponse=validationMessage;
		   }
		}
		response.getWriter().write(writeresponse);
		return null;
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
		    .getAttribute(Constants.VALIDATION_MESSAGE_FOR_ORDERING);
		String isListEmpty = (String) session.getAttribute(Constants.IS_LIST_EMPTY);

		if ((isListEmpty != null && isListEmpty.equals(Constants.FALSE)) || message == null)
		{
			message = " "; //if empty string is returned mac+safari gives problem and if message is set to null mozilla gives problem.
		}
		response.setContentType("text/html");
		return message;
	}

}
