/**
 * 
 */

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
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.PrivilegeCache;
import edu.wustl.common.security.PrivilegeUtility;
import edu.wustl.common.util.Permissions;
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
			Collection<IParameterizedQuery> parameterizedQueryCollection = HibernateUtility
					.executeHQL(HibernateUtility.GET_PARAMETERIZED_QUERIES_DETAILS);
					
			PrivilegeCache privilegeCache = setPrivilegeCache(session);
			Collection<IParameterizedQuery> authorizedQueryCollection = new ArrayList<IParameterizedQuery>();
			Collection<IParameterizedQuery> sharedQueryCollection = new ArrayList<IParameterizedQuery>();
			
			message = initializeParameterizeQueryCollection(request, saveQueryForm,
					parameterizedQueryCollection, privilegeCache, authorizedQueryCollection,
					sharedQueryCollection);
			setErrorMessages(request, message);
			actionForward = setActionForward(actionMapping, (String) request.getAttribute("pageOf"));
		}
		catch (HibernateException hibernateException)
		{
			actionForward = actionMapping.findForward(Constants.FAILURE);
		}
		request.setAttribute(Constants.POPUP_MESSAGE, ApplicationProperties
				.getValue("query.confirmBox.message"));
		return actionForward;
	}

	private String initializeParameterizeQueryCollection(HttpServletRequest request,
			SaveQueryForm saveQueryForm,
			Collection<IParameterizedQuery> parameterizedQueryCollection,
			PrivilegeCache privilegeCache,
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection) throws CSException,
			CSObjectNotFoundException
	{
		String message;
		if (parameterizedQueryCollection == null)
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
			
				saveQueryForm.setParameterizedQueryCollection(parameterizedQueryCollection);
				message = String.valueOf(parameterizedQueryCollection.size());
		}
		return message;
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
				Constants.PAGE_OF_MY_QUERIES.equals(pageOf)))
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

}
