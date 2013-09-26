/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.emory.mathcs.backport.java.util.Collections;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.privilege.PrivilegeUtility;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

/**
 * CSM Utility class.
 */
public class CsmUtility
{
	/**
	 * Logger.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(CsmUtility.class);

	/**
	 * @param myQueryCollection Collection<IParameterizedQuery>= collection of my queries
	 * @param sharedQueryColl Collection<IParameterizedQuery>= collection of shared queries
	 * @param sessionDataBean SessionDataBean= details of logged in user
	 * @param queryNameLike queryNameLike
	 * @throws CSException CSException
	 * @throws CSObjectNotFoundException CSObjectNotFoundException
	 * @throws DAOException DAOException
	 * @throws SMException Security Manager Exception
	 */
	public static void checkExecuteQueryPrivilege(
			Collection<IParameterizedQuery> myQueryCollection,
			Collection<IParameterizedQuery> sharedQueryColl,
			SessionDataBean sessionDataBean,String queryNameLike)
	throws CSException, CSObjectNotFoundException, DAOException, SMException
	{
		Collection<Long> myQueriesIdList = new ArrayList<Long>();
		Collection<Long> shareQueryIdList = new ArrayList<Long>();

		populateQueryIdsCollection(myQueriesIdList, shareQueryIdList,
				sessionDataBean.getCsmUserId());

		Collection<IParameterizedQuery> myQueriesList = retrieveQueries(myQueriesIdList,queryNameLike);

		if (myQueriesList != null)
		{
			myQueryCollection.addAll(myQueriesList);
		}
		Collection<IParameterizedQuery> sharedQueriesList =
			retrieveQueries(shareQueryIdList,queryNameLike);
		if (sharedQueriesList != null)
		{
			sharedQueryColl.addAll(sharedQueriesList);
		}
	}
	/**
	 * @param myQueryCollection Collection<Long>= collection of my queries
	 * @param sharedQueryColl Collection<Long>= collection of shared queries
	 * @param csmUserId =csUser Id
	 * @throws CSException CSException
	 * @throws CSObjectNotFoundException CSObjectNotFoundException
	 * @throws SMException Security Manager Exception
	 */
	private static void populateQueryIdsCollection(Collection<Long> myQueryCollection,
			Collection<Long> sharedQueryColl, String csmUserId) throws CSException,
			CSObjectNotFoundException, SMException
	{
		List<ProtectionElement> peList = getQueryPEs("*");
		if (peList != null && !peList.isEmpty())
		{
			PrivilegeUtility privUtil = new PrivilegeUtility();
			for (ProtectionElement pElement : peList)
			{
				String[] objectId = pElement.getObjectId().split("_");
				Long pQueryId = Long.valueOf(objectId[1]);
				Set<ProtectionGroup> pgSet = privUtil.getUserProvisioningManager()
				.getProtectionGroups(pElement.getProtectionElementId().toString());
				populateQueryCollection(myQueryCollection, sharedQueryColl,
						pQueryId, pgSet, csmUserId,pElement);
			}
		}
	}
	/**
	 * Get list of query protection elements.
	 * @param queryId queryId
	 * @return peList
	 * @throws SMException Security Manager Exception
	 */
	public static List<ProtectionElement> getQueryPEs(String queryId) throws SMException
	{
		PrivilegeUtility privilegeUtility = new PrivilegeUtility();
		ProtectionElement pElement = new ProtectionElement();
		pElement.setObjectId(ParameterizedQuery.class.getName() + "_"+queryId);
		ProtectionElementSearchCriteria searchCriteria = new ProtectionElementSearchCriteria(pElement);
		return privilegeUtility.getObjects(searchCriteria);
	}

	/**
	 * @param myQueryCollection Collection<Long>
	 * @param sharedQueryCollection Collection<Long>
	 * @param pQueryId Long
	 * @param pgSet Set<ProtectionGroup>
	 * @param csmUserId String
	 * @param pElement protection element
	 * @throws SMException Security Manager Exception
	 * @throws CSObjectNotFoundException CSObjectNotFoundException
	 */
	private static void populateQueryCollection(Collection<Long> myQueryCollection,
			Collection<Long> sharedQueryCollection, Long pQueryId,
			Set<ProtectionGroup> pgSet, String csmUserId, ProtectionElement pElement)
			throws SMException, CSObjectNotFoundException
	{
		PrivilegeUtility util = new PrivilegeUtility();
		UserProvisioningManager upManager = util.getUserProvisioningManager();
		Set<User> owners = upManager.getOwners(pElement.getProtectionElementId().toString());
		String userPG = getUserProtectionGroup(csmUserId);
		boolean ifMyQuery = false;
		for (ProtectionGroup pGroup : pgSet)
		{
			if (pGroup.getProtectionGroupName().equalsIgnoreCase(userPG))
			{
				myQueryCollection.add(pQueryId);
				sharedQueryCollection.remove(pQueryId);
				ifMyQuery = true;
			}
			if (pGroup.getProtectionGroupName().equals(AQConstants.PUBLIC_QUERY_PROTECTION_GROUP)
					&& !ifMyQuery)
			{
				sharedQueryCollection.add(pQueryId);
			}
		}
		for (User user : owners)
		{
			if(user.getUserId().toString().equals(csmUserId))
			{
				sharedQueryCollection.add(pQueryId);
			}
		}
	}
	/**
	 * @param csmUserId String
	 * @return user protection group
	 */
	public static String getUserProtectionGroup(String csmUserId)
	{
		return "User_" + csmUserId;
	}

	/**
	 * @return Collection<Long>
	 * @throws DAOException DAOException
	 */
	public static Collection<Long> getParameterizedQueryCollection() throws DAOException
	{
		HibernateDAO hibernateDAO = null;
		try
		{
			hibernateDAO = DAOUtil.getHibernateDAO(null);
			return hibernateDAO.executeQuery("select id from "
					+ IParameterizedQuery.class.getName() + "  order by  id desc");
		}
		finally
		{
			DAOUtil.closeHibernateDAO(hibernateDAO);
		}
	}

	/**
	 * To Retrieve Queries.
	 * @param ids Collection<Long>
	 * @param queryNameLike queryNameLike
	 * @return  Collection<IParameterizedQuery>
	 * @throws DAOException DAOException
	 */
	public static Collection<IParameterizedQuery> retrieveQueries(Collection<Long> ids, String queryNameLike)
	throws DAOException
	{
		List<Long> idList = new ArrayList<Long>(ids);
		Collections.sort(idList,Collections.reverseOrder());
		Collection<IParameterizedQuery> queries = new ArrayList<IParameterizedQuery>();
		IBizLogic bizLogic = BizLogicFactory.getInstance().getBizLogic(
				AQConstants.QUERY_INTERFACE_BIZLOGIC_ID);
		for (Long queryId : idList)
		{
			List<IParameterizedQuery> queryList;
			try
			{
				queryList = bizLogic.retrieve(ParameterizedQuery.class
						.getName(), "id", queryId);
				if (queryList != null && !queryList.isEmpty())
				{
					IParameterizedQuery pQuery = queryList.get(0);
					queries.add(pQuery);
				}
			}
			catch (BizLogicException e)
			{
				e.printStackTrace();
			}
		}
		return queries;
	}
}
