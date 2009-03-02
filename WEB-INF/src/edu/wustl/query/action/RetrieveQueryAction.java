package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.wustl.common.beans.NameValueBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.PrivilegeCache;
import edu.wustl.common.security.PrivilegeUtility;
import edu.wustl.common.util.Permissions;
import edu.wustl.common.util.dbManager.DBUtil;
import edu.wustl.common.util.dbManager.HibernateUtility;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.util.global.Constants;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
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
		String message = null;			
		try
		{
			HttpSession session = request.getSession();

			removeAttributesFromSession(session);
			SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
			// int count= setQueryCount();
					
			PrivilegeCache privilegeCache = setPrivilegeCache(session);
//			Collection<IParameterizedQuery> authorizedQueryCollection = new ArrayList<IParameterizedQuery>();
//			Collection<IParameterizedQuery> sharedQueryCollection = new ArrayList<IParameterizedQuery>();
//			message = initializeParameterizeQueryCollection(request, saveQueryForm,
//					 privilegeCache, authorizedQueryCollection,
//					sharedQueryCollection);
			message = initializeParameterizeQueryCollection(request, saveQueryForm,
					 privilegeCache, new ArrayList<IParameterizedQuery>(),
					 new ArrayList<IParameterizedQuery>());
			//setPagiantion(request,request.getParameter("requestFor"),parameterizedQueryCollection);

			
			setErrorMessages(request,message);
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

	public int setQueryCount()
	{

		Collection prameterizedQueriesCollection=HibernateUtility
		.executeHQL(HibernateUtility.GET_PARAMETERIZED_QUERIES_DETAILS);
		if(prameterizedQueriesCollection!=null)
		{
			return prameterizedQueriesCollection.size();
		}
		return 0;
	}
//
	private Collection<IParameterizedQuery> retrievePrameterizedQueries()
	{
		return HibernateUtility
			.executeHQL(HibernateUtility.GET_PARAMETERIZED_QUERIES_DETAILS);
	}

	/*
	 * sets the parameters for the pagination 
	 */
//	private void setPagiantion(HttpServletRequest request, String requestFor, int totalRecords)
//	{
//
//		
//		totalRecords=getTotalRecords(request,requestFor,totalRecords);
//		int pageNum=getPageNumber(request,requestFor);
//			
//		int totalPages=0;
//		int maxRecords = getRecordsPerPage(request,requestFor);
//		if(totalRecords>0)
//		{
//			totalPages=totalRecords%maxRecords==0?totalRecords/maxRecords:(totalRecords/maxRecords)+1;
//		}
//		request.getSession().setAttribute(Constants.RESULTS_PER_PAGE,1 );
//		request.getSession().setAttribute(Constants.PAGE_NUMBER,pageNum );
//		request.getSession().setAttribute("totalPages",totalPages);
//		request.getSession().setAttribute(Constants.TOTAL_RESULTS, totalRecords);
//		
//		List<NameValueBean> resultsPerPageOptions=new ArrayList<NameValueBean>();
//		resultsPerPageOptions.add(new NameValueBean("50","50"));
//		resultsPerPageOptions.add(new NameValueBean("100","100"));
//		resultsPerPageOptions.add(new NameValueBean("200","200"));
//		
//		request.setAttribute("resultsPerPageOptions", resultsPerPageOptions);
//		
//		
//	}

	private String initializeParameterizeQueryCollection(HttpServletRequest request,
			SaveQueryForm saveQueryForm,
			
			PrivilegeCache privilegeCache,
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection) throws CSException,
			CSObjectNotFoundException
	{
		String message = null;
		if (setQueryCount()== 0)
		{
			saveQueryForm.setParameterizedQueryCollection(new ArrayList<IParameterizedQuery>());
			message = "No";
		}
		else
		{
			//removed for saved query bug for csm
			//checkExecuteQueryPrivilege(parameterizedQueryCollection, (String) request.getAttribute("pageOf"), privilegeCache,
			//		authorizedQueryCollection, sharedQueryCollection);
			//setQueryCollectionToSaveQueryForm(saveQueryForm, (String) request.getAttribute("pageOf"), authorizedQueryCollection,
			//		sharedQueryCollection);
			//message = authorizedQueryCollection.size() + "";
			
			
				//saveQueryForm.setParameterizedQueryCollection(parameterizedQueryCollection);
			if(request.getParameter("pageOf")!=null&&(Constants.MY_QUERIES_FOR_WORKFLOW.equals(request.getParameter("pageOf"))
					||Constants.PUBLIC_QUERIES_FOR_WORKFLOW.equals(request.getParameter("pageOf"))))
			{
				setQueryCount();
				saveQueryForm=setPagiantion(request,request.getParameter("requestFor"),saveQueryForm);	
				
			}
			else
			{
				saveQueryForm.setParameterizedQueryCollection(retrievePrameterizedQueries());
				setPagiantion(request,request.getParameter("requestFor"),saveQueryForm);
			}
			message = String.valueOf(setQueryCount());
		
		}
		return message;
	}



	private SaveQueryForm setPagiantion(HttpServletRequest request, String requestFor,
			SaveQueryForm saveQueryForm)
	{
		int totalRecords=setQueryCount();
		int startIndex=0;
		int pageNum=getPageNumber(request,requestFor);
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
		
		//to set queries to form 
		Session session1=DBUtil.currentSession();
		Query query = session1.getNamedQuery(HibernateUtility.GET_PARAMETERIZED_QUERIES_DETAILS);
		query.setFirstResult(startIndex);
		query.setMaxResults(maxRecords);
		Collection temp=query.list();
		saveQueryForm.setParameterizedQueryCollection(temp);
		
		
		return saveQueryForm;
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

	private PrivilegeCache setPrivilegeCache(HttpSession session) throws CSException
	{
		//removed for saved query bug for csm
//		SessionDataBean sessionDataBean = (SessionDataBean)session.getAttribute(Constants.SESSION_DATA);
//		User user = new PrivilegeUtility().getUserProvisioningManager().getUser(sessionDataBean.getUserName());
//		sessionDataBean.setCsmUserId(user.getUserId().toString());
//		PrivilegeCache privilegeCache = PrivilegeManager.getInstance().getPrivilegeCache(sessionDataBean.getUserName());
		//return privilegeCache;
		return null;
	}

	private ActionForward setActionForward(ActionMapping actionMapping, String pageOf)
	{
		ActionForward actionForward;
		actionForward = actionMapping.findForward(Constants.SUCCESS);
		//for displaying in work flow pop up
		if(pageOf!=null&&(Constants.MY_QUERIES_FOR_WORKFLOW.equals(pageOf)
				||Constants.PUBLIC_QUERIES_FOR_WORKFLOW.equals(pageOf)))
		{
			actionForward = actionMapping.findForward(Constants.DISPLAY_QUERIES_IN_POPUP);
		}
		else if(pageOf!= null&& (Constants.PUBLIC_QUERY_PROTECTION_GROUP.equals(pageOf)||
				Constants.PAGE_OF_MY_QUERIES.equals(pageOf))
				||Constants.MY_QUERIESFOR_DASHBOARD.equals(pageOf))
		{
			actionForward = actionMapping.findForward(Constants.MY_QUERIES);
		}
		return actionForward;
	}

	private void setErrorMessages(HttpServletRequest request, String message)
	{
		ActionMessages actionMessages = new ActionMessages();
		ActionMessage actionMessage = new ActionMessage("query.resultFound.message", message);
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
		saveMessages(request, actionMessages);
	}

	private void checkExecuteQueryPrivilege(
			Collection<IParameterizedQuery> parameterizedQueryCollection, String pageOf,
			PrivilegeCache privilegeCache,
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection) throws CSException,
			CSObjectNotFoundException
	{
		for(IParameterizedQuery parameterizedQuery : parameterizedQueryCollection)
		{
			String objectId = ((ParameterizedQuery)parameterizedQuery).getObjectId();
			if(privilegeCache.hasPrivilege(objectId, Permissions.EXECUTE_QUERY))
			{
				boolean sharedQuery = setSharedQueriesCollection(sharedQueryCollection,
						parameterizedQuery, objectId);
				if(!sharedQuery)
				{
					authorizedQueryCollection.add(parameterizedQuery);
				}
			}
		}
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

	private boolean setSharedQueriesCollection(
			Collection<IParameterizedQuery> sharedQueryCollection,
			IParameterizedQuery parameterizedQuery, String objectId) throws CSException,
			CSObjectNotFoundException
	{
		ProtectionElement pe = new ProtectionElement();

		List<ProtectionElement> peList = new ArrayList<ProtectionElement>();
		PrivilegeUtility privilegeUtility = new PrivilegeUtility();
		pe.setObjectId(objectId);
		ProtectionElementSearchCriteria searchCriteria = new ProtectionElementSearchCriteria(pe);
		peList = privilegeUtility.getUserProvisioningManager().getObjects(searchCriteria);
		boolean sharedQuery = false;
		if (peList != null && !peList.isEmpty())
		{
			pe = peList.get(0);
		}
		
		Set<ProtectionGroup> pgSet = privilegeUtility.getUserProvisioningManager().getProtectionGroups(pe.getProtectionElementId().toString());
		
		sharedQuery = populateSharedQueryCollection(sharedQueryCollection, parameterizedQuery,
				pgSet);
		return sharedQuery;
	}

	private boolean populateSharedQueryCollection(
			Collection<IParameterizedQuery> sharedQueryCollection,
			IParameterizedQuery parameterizedQuery, Set<ProtectionGroup> pgSet)
	{
		boolean sharedQuery=false;
		for(ProtectionGroup pg : pgSet)
		{
			if(pg.getProtectionGroupName().equals(Constants.PUBLIC_QUERY_PROTECTION_GROUP))
			{
				sharedQueryCollection.add(parameterizedQuery);
				sharedQuery = true;
			}
		}
		return sharedQuery;
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
//	private int getTotalRecords(HttpServletRequest request, String requestFor, int matchingUsersCount)
//	{
//		int totalRecords=0;
//		if(requestFor!=null && request.getSession().getAttribute(Constants.TOTAL_RESULTS)!=null)
//		{
//			totalRecords=(Integer)request.getSession().getAttribute(Constants.TOTAL_RESULTS);
//		}
//		else
//		{
//			totalRecords=matchingUsersCount;
//		}
//		
//		return totalRecords;
//	}
}
