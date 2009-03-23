package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.PrivilegeUtility;
import edu.wustl.common.util.dbManager.DBUtil;
import edu.wustl.query.util.global.Constants;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

public class CsmUtility {

	/**
	 * @param authorizedQueryCollection= collection of my queries
	 * @param sharedQueryCollection= collection of shared queries
	 * @param sessionDataBean= details of logged in user
	 * @throws CSException 
	 * @throws CSObjectNotFoundException
	 */
	public void  checkExecuteQueryPrivilege (	
			Collection<IParameterizedQuery> authorizedQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection,
			SessionDataBean sessionDataBean ) throws CSException,
			CSObjectNotFoundException
	{
		Collection<Long> parameterizedQueryCollection=getParameterizedQueryCollection();

		Collection<Long> myQueriesIdList = new ArrayList<Long>();
		Collection<Long> sharedQueriesIdList = new ArrayList<Long>();
		for(Long parameterizedQueryId : parameterizedQueryCollection)
		{
				setSharedQueriesCollection(myQueriesIdList,sharedQueriesIdList,
						parameterizedQueryId,sessionDataBean.getCsmUserId());
				
		}

		authorizedQueryCollection=retrieveQueries(myQueriesIdList);
		sharedQueryCollection=retrieveQueries(sharedQueriesIdList);
	}
	
	/**
	 * @param authorizedQueryCollection= collection of my queries
	 * @param sharedQueryCollection= collection of shared queries
	 * @param parameterizedQuery= id of parameterized query
	 * @param csmUserId =csUser Id
	 * @throws CSException
	 * @throws CSObjectNotFoundException
	 */
	private void setSharedQueriesCollection(Collection<Long> authorizedQueryCollection,
			Collection<Long> sharedQueryCollection,
			Long parameterizedQuery,String csmUserId) throws CSException,
			CSObjectNotFoundException
	{
		ProtectionElement pe = new ProtectionElement();

		List<ProtectionElement> peList = new ArrayList<ProtectionElement>();
		PrivilegeUtility privilegeUtility = new PrivilegeUtility();
		pe.setObjectId(ParameterizedQuery.class.getName()+"_"+parameterizedQuery);
		ProtectionElementSearchCriteria searchCriteria = new ProtectionElementSearchCriteria(pe);
		peList = privilegeUtility.getUserProvisioningManager().getObjects(searchCriteria);
		if (peList != null && !peList.isEmpty())
		{
			pe = peList.get(0);
		}
		
		Set<ProtectionGroup> pgSet = privilegeUtility.getUserProvisioningManager().getProtectionGroups(pe.getProtectionElementId().toString());
		
		 populateSharedQueryCollection(authorizedQueryCollection,sharedQueryCollection, parameterizedQuery,
				pgSet,csmUserId);
		
	}
	
	/**
	 * @param authorizedQueryCollection
	 * @param sharedQueryCollection
	 * @param parameterizedQueryId
	 * @param pgSet
	 * @param csmUserId
	 */
	private void populateSharedQueryCollection(Collection<Long> authorizedQueryCollection,
			Collection<Long> sharedQueryCollection,
			Long parameterizedQueryId, Set<ProtectionGroup> pgSet,String csmUserId)
	{
		String userProtectionGroup=getUserProtectionGroup(csmUserId);
		for(ProtectionGroup pg : pgSet)
		{
			if(pg.getProtectionGroupName().equals(Constants.PUBLIC_QUERY_PROTECTION_GROUP))
			{
				sharedQueryCollection.add(parameterizedQueryId);
			}
			if(pg.getProtectionGroupName().equals(userProtectionGroup))
			{
				authorizedQueryCollection.add(parameterizedQueryId);
			}
		}

	}
	/**
	 * @param csmUserId
	 * @return
	 */
	public String getUserProtectionGroup(String csmUserId)
	{
		return "User_" + csmUserId;
	}
	/**
	 * @return
	 */
	public static Collection<Long> getParameterizedQueryCollection()
	{
		Session session1 = null;
		try {
			session1 = DBUtil.getCleanSession();
			Query query = null;
			
			query = session1.createQuery("select id from " + IParameterizedQuery.class.getName()
					
					 );

		
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			session1.close();
		}
		return null;
		
	}
	/**
	 * @param ids
	 * @return
	 */
	public static Collection<IParameterizedQuery> retrieveQueries(Collection<Long>  ids)
	{
		StringBuffer objectToRead = generateListOfIds(ids);
		
		Session session1 = null;
		
		try {
			session1 = DBUtil.getCleanSession();
			if(!objectToRead.toString().equals("()"))
			{
				
				Query query = null;
				
				query = session1.createQuery("from " + IParameterizedQuery.class.getName()  + 
						" query where  query.id in  "+objectToRead
						 );
	
			
				return query.list();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			session1.close();
		}
		return null;
	}
	private static StringBuffer generateListOfIds(Collection<Long> ids) {
		StringBuffer objectToRead = new StringBuffer();
		objectToRead.append("(");
		Iterator<Long> idIter=ids.iterator();

		for (int j = 0; j < ids.size(); j++) 
		{

			objectToRead.append(idIter.next());

			if(j<ids.size()-1)
			{
				objectToRead.append(",");
			}
		}

		objectToRead.append(")");
		return objectToRead;
	}
	
	/**
	 * It will check weather the object with the given objectId is shared or not.
	 * I will check weather yhe 
	 * @param objectId of the object which is to be checked for sharing.
	 * @return true if object with given objectId has associated public Protection Group 
	 * @throws CSObjectNotFoundException
	 * @throws CSException
	 */
	public boolean checkIsSharedQuery(String objectId) throws CSObjectNotFoundException, CSException
	{
		ProtectionElement pe = new ProtectionElement();
		List<ProtectionElement> peList = new ArrayList<ProtectionElement>();
		PrivilegeUtility privilegeUtility = new PrivilegeUtility();
		pe.setObjectId(objectId);
		boolean sharedQuery=false;
		//ProtectionGroupSearchCriteria groupSearchCriteria = new ProtectionGroupSearchCriteria
		ProtectionElementSearchCriteria searchCriteria = new ProtectionElementSearchCriteria(pe);
		peList = privilegeUtility.getUserProvisioningManager().getObjects(searchCriteria);
		if (peList != null && !peList.isEmpty())
		{
			pe = peList.get(0);
			Set<ProtectionGroup> pgSet = privilegeUtility.getUserProvisioningManager()
					.getProtectionGroups(pe.getProtectionElementId().toString());
			for (ProtectionGroup pg : pgSet)
			{
				if (pg.getProtectionGroupName().equals(Constants.PUBLIC_QUERY_PROTECTION_GROUP))
				{
					sharedQuery = true;
				}
			}
		}
		return sharedQuery;
		
	}
}
