package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.PrivilegeCache;
import edu.wustl.common.security.PrivilegeManager;
import edu.wustl.common.security.PrivilegeUtility;
import edu.wustl.common.util.Permissions;
import edu.wustl.common.util.dbManager.HibernateUtility;
import edu.wustl.query.util.global.Constants;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

public class CsmUtility {
	
	private PrivilegeCache setPrivilegeCache(SessionDataBean sessionDataBean ) throws CSException
	{
		User user = new PrivilegeUtility().getUserProvisioningManager().getUser(sessionDataBean.getUserName());
		sessionDataBean.setCsmUserId(user.getUserId().toString());
		PrivilegeCache privilegeCache = PrivilegeManager.getInstance().getPrivilegeCache(sessionDataBean.getUserName());
		return privilegeCache;

	}
	public void  checkExecuteQueryPrivilege(
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection,
			SessionDataBean sessionDataBean ) throws CSException,
			CSObjectNotFoundException
	{
		Collection<IParameterizedQuery> parameterizedQueryCollection=getParameterizedQueryCollection();
		
		PrivilegeCache privilegeCache =setPrivilegeCache(sessionDataBean);
		for(IParameterizedQuery parameterizedQuery : parameterizedQueryCollection)
		{
			String objectId = ((ParameterizedQuery)parameterizedQuery).getObjectId();
			if(privilegeCache.hasPrivilege(objectId, Permissions.EXECUTE_QUERY))
			{
				boolean sharedQuery = setSharedQueriesCollection(sharedQueryCollection,
						parameterizedQuery, objectId,sessionDataBean.getCsmUserId());
				if(!sharedQuery)
				{
					authorizedQueryCollection.add(parameterizedQuery);
				}
			}
		}
	}
	
	private boolean setSharedQueriesCollection(
			Collection<IParameterizedQuery> sharedQueryCollection,
			IParameterizedQuery parameterizedQuery, String objectId,String csmUserId) throws CSException,
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
				pgSet,csmUserId);
		return sharedQuery;
	}
	
	private boolean populateSharedQueryCollection(
			Collection<IParameterizedQuery> sharedQueryCollection,
			IParameterizedQuery parameterizedQuery, Set<ProtectionGroup> pgSet,String csmUserId)
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
	public String getUserProtectionGroup(String csmUserId)
	{
		return "User_" + csmUserId;
	}
	public static Collection getParameterizedQueryCollection()
	{

		return	HibernateUtility
		.executeHQL(HibernateUtility.GET_PARAMETERIZED_QUERIES_DETAILS);
		
	}
}
