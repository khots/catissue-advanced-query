
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.privilege.PrivilegeUtility;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

/**
 * CSM Utility class
 */
public class CsmUtility
{

	private static final String QUERY_WHERE = " query where";
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(CsmUtility.class);

	/**
	 * @param myQueryCollection Collection<IParameterizedQuery>= collection of my queries
	 * @param sharedQueryCollection Collection<IParameterizedQuery>= collection of shared queries
	 * @param sessionDataBean SessionDataBean= details of logged in user
	 * @param queryNameLike queryName to search
	 * @param queryType query Type
	 * @throws CSException CSException
	 * @throws DAOException DAOException
	 * @throws SMException SMException
	 */
	public static void checkExecuteQueryPrivilege(
			Collection<IParameterizedQuery> myQueryCollection,
			Collection<IParameterizedQuery> sharedQueryCollection, SessionDataBean sessionDataBean,
			String queryNameLike, String queryType) throws CSException,
			DAOException,SMException
	{
		Collection<Long> myQueriesIdList = new ArrayList<Long>();
		Collection<Long> sharedQueriesIdList = new ArrayList<Long>();

		try
		{
			setSharedQueriesCollection(myQueriesIdList, sharedQueriesIdList, sessionDataBean
					.getCsmUserId());

			Collection<IParameterizedQuery> resultList = retrieveQueries(myQueriesIdList,
					queryNameLike,queryType);

			setResultList(myQueryCollection, resultList);
			resultList = retrieveQueries(sharedQueriesIdList, queryNameLike,queryType);
			setResultList(sharedQueryCollection, resultList);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * set result list from shared queries.
	 * @param sharedQueryCollection collection of shared queries
	 * @param resultList result
	 */
	private static void setResultList(
			Collection<IParameterizedQuery> sharedQueryCollection,
			Collection<IParameterizedQuery> resultList)
	{
		if (resultList != null)
		{
			sharedQueryCollection.addAll(resultList);
		}
	}

	/**
	 * @param myQueryCollection Collection<Long>= collection of my queries
	 * @param sharedQueryCollection Collection<Long>= collection of shared queries
	 * @param csmUserId csmUser Id
	 * @throws CSException CSException
	 * @throws SMException SMException
	 */
	private static void setSharedQueriesCollection(Collection<Long> myQueryCollection,
			Collection<Long> sharedQueryCollection, String csmUserId) throws CSException,
		 SMException
	{
		ProtectionElement protectionElement = new ProtectionElement();

		List<ProtectionElement> peList; //= new ArrayList<ProtectionElement>();
		try
		{
			PrivilegeUtility privilegeUtility = new PrivilegeUtility();
			//pe.setObjectId(ParameterizedQuery.class.getName() + "_" + parameterizedQueryId);
			protectionElement.setObjectId(ParameterizedQuery.class.getName() + "_*");
			ProtectionElementSearchCriteria searchCriteria =
				new ProtectionElementSearchCriteria(protectionElement);
			//	UserProvisioningManager manager=privilegeUtility.getUserProvisioningManager();
			peList = privilegeUtility.getObjects(searchCriteria);
			if (peList != null && !peList.isEmpty())
			{
				for (ProtectionElement pEl : peList)
				{
					String objectId[] = pEl.getObjectId().split("_");
					Long parameterizedQueryId = Long.valueOf(objectId[Constants.ONE]);
					Set<ProtectionGroup> pgSet;
					pgSet = privilegeUtility.getUserProvisioningManager().getProtectionGroups(
							pEl.getProtectionElementId().toString());
					populateSharedQueryCollection(myQueryCollection, sharedQueryCollection,
							parameterizedQueryId, pgSet, csmUserId);
				}
			}
		}
		catch (SMException smeException)
		{
			logger.debug(smeException.getMessage(), smeException);
		}

	}

	/**
	 * @param authorizedQueryCollection Collection<Long>
	 * @param sharedQueryCollection Collection<Long>
	 * @param parameterizedQueryId Long
	 * @param pgSet Set<ProtectionGroup>
	 * @param csmUserId String
	 */
	private static void populateSharedQueryCollection(Collection<Long> authorizedQueryCollection,
			Collection<Long> sharedQueryCollection, Long parameterizedQueryId,
			Set<ProtectionGroup> pgSet, String csmUserId)
	{
		for (ProtectionGroup pg : pgSet)
		{
			if (pg.getProtectionGroupName().equals(Constants.PUBLIC_QUERY_PROTECTION_GROUP))
			{
				sharedQueryCollection.add(parameterizedQueryId);

			}
			if (pg.getProtectionGroupName().equals(getUserProtectionGroup(csmUserId)))
			{
				authorizedQueryCollection.add(parameterizedQueryId);
			}
		}

	}

	/**
	 * Creates csm user
	 * @param csmUserId String
	 * @return String
	 */
	public static String getUserProtectionGroup(String csmUserId)
	{
		return "User_" + csmUserId;
	}

	/**
	 * @return Collection<Long> Parameterized Query Collection
	 * @throws DAOException DAOException
	 */
	public static Collection<Long> getParameterizedQueryCollection() throws DAOException
	{

		String query="select id from "
			+ IParameterizedQuery.class.getName() + "  order by  id desc";
		Collection<Long> queryIds =Utility.executeQuery(query);

		return queryIds;

	}

	/**
	 * To retrieveQueries
	 * @param ids Collection<Long>
	 * @param queryNameLike String
	 * @param queryType query Type count or data
	 * @return  Collection<IParameterizedQuery>
	 * @throws DAOException DAOException
	 */
	public static Collection<IParameterizedQuery> retrieveQueries(Collection<Long> ids,
			String queryNameLike, String queryType) throws DAOException
	{
		StringBuffer objectToRead = generateListOfIds(ids);
	      StringBuilder query = new StringBuilder();
			StringBuffer type=new StringBuffer("");
			if(queryType!=null&&!queryType.equals(""))
			{
				type.append(" query.type='").append(queryType).append("' and ");
			}


			createHQL(queryNameLike, objectToRead, query, type);
			 Collection<IParameterizedQuery> pqCollection= Utility.executeQuery(query.toString());

		return pqCollection;
	}

	/**
	 * Creates the query HQl.
	 * @param queryNameLike query name to search
	 * @param objectToRead id string
	 * @param query query
	 * @param type type of query
	 */
	private static void createHQL(String queryNameLike,
			StringBuffer objectToRead, StringBuilder query, StringBuffer type)
	{
		if (queryNameLike != null && !queryNameLike.equals("")
				&& !objectToRead.toString().equals("()"))
		{

			StringBuffer escapeSquence=new StringBuffer("");
			StringBuffer queryNameLikeUpper =new  StringBuffer(
					Utility.setSpecialCharacters(escapeSquence,
		    		queryNameLike.toUpperCase()));

		    query.append("from ").append(IParameterizedQuery.class.getName())
		    .append(QUERY_WHERE).append(type).append(
		            Constants.QUERY_WHERE_QUERY_ID_IN).append(objectToRead).append(
		            " and upper(query.name) like ").append("'").append("%").append(
		            queryNameLikeUpper).append("%").append("'").append(escapeSquence.toString()).append(
		            " or upper(query.description) like ").append("'").append("%").append(
		            queryNameLikeUpper).append("%").append("'").
		            append(escapeSquence.toString()).append(Constants.ORDER_BY_QUERY_ID_DESC);

		}
		else if (!objectToRead.toString().equals("()"))
		{
			query.append("from " + IParameterizedQuery.class.getName()
				+ QUERY_WHERE+ type.toString() + Constants.QUERY_WHERE_QUERY_ID_IN
					+ objectToRead +Constants.ORDER_BY_QUERY_ID_DESC);


		}
	}

	/**
	 * To generateListOfIds
	 * @param ids Collection<Long>
	 * @return StringBuffer
	 */
	private static StringBuffer generateListOfIds(Collection<Long> ids)
	{
		StringBuffer objectToRead = new StringBuffer();
		objectToRead.append('(');
		Iterator<Long> idIter = ids.iterator();

		for (int j = 0; j < ids.size(); j++)
		{

			objectToRead.append(idIter.next());

			if (j < ids.size() - Constants.ONE)
			{
				objectToRead.append(',');
			}
		}

		objectToRead.append(')');
		return objectToRead;
	}

	/**
	 * It will check weather the object with the given objectId is shared or not.
	 * I will check weather the
	 * @param objectId of the object which is to be checked for sharing.
	 * @return true if object with given objectId has associated public Protection Group
	 * @throws CSException CSException
	 * @throws SMException SMException
	 */
	public boolean checkIsSharedQuery(String objectId) throws
			CSException, SMException
	{
		ProtectionElement protectionElement = new ProtectionElement();
		List<ProtectionElement> peList; //= new ArrayList<ProtectionElement>();
		boolean sharedQuery = false;

		try
		{
			PrivilegeUtility privilegeUtility = new PrivilegeUtility();

			protectionElement.setObjectId(objectId);

			ProtectionElementSearchCriteria searchCriteria =
				new ProtectionElementSearchCriteria(protectionElement);
			peList = privilegeUtility.getUserProvisioningManager().getObjects(searchCriteria);

			if (peList != null && !peList.isEmpty())
			{
				sharedQuery = checkPublicPGName(peList,
						privilegeUtility);
			}
		}
		catch (SMException smException)
		{
			logger.debug(smException.getMessage(), smException);
		}
		return sharedQuery;

	}

	/**
	 * check public protection group.
	 * @param peList ProtectionElement List
	 * @param privilegeUtility PrivilegeUtility
	 * @return is shared or not.
	 * @throws CSObjectNotFoundException CSObjectNotFoundException
	 * @throws SMException SMException
	 */
	private boolean checkPublicPGName(List<ProtectionElement> peList,
			 PrivilegeUtility privilegeUtility)
			throws CSObjectNotFoundException, SMException
			{
		ProtectionElement protectionElement;
		protectionElement = peList.get(0);
		boolean sharedQuery = false;
		Set<ProtectionGroup> pgSet = null;
		pgSet = privilegeUtility.getUserProvisioningManager().getProtectionGroups(
				protectionElement.getProtectionElementId().toString());
		for (ProtectionGroup pg : pgSet)
		{
			if (pg.getProtectionGroupName().
					equals(Constants.PUBLIC_QUERY_PROTECTION_GROUP))
			{
				sharedQuery = true;
			}
		}
		return sharedQuery;
	}

	/**
	 * to check execute query privilege.
	 * @param myQueryCollection Collection<Long>
	 * @param sharedQueryCollection Collection<Long>
	 * @param sessionDataBean SessionDataBean
	 * @param queryNameLike String
	 * @throws CSException CSException
	 * @throws DAOException DAOException
	 * @throws SMException SMException
	 */
	public static void checkExecuteQueryPrivilegeForGetCount(Collection<Long> myQueryCollection,
			Collection<Long> sharedQueryCollection, SessionDataBean sessionDataBean,
			String queryNameLike) throws CSException, DAOException,
			SMException
	{
		Collection<Long> myQueriesIdList = new ArrayList<Long>();
		Collection<Long> sharedQueriesIdList = new ArrayList<Long>();

		try
		{
			setSharedQueriesCollection(myQueriesIdList, sharedQueriesIdList, sessionDataBean
					.getCsmUserId());

			Collection<Long> resultList = retrieveQueriesForGetCount(myQueriesIdList, queryNameLike);

			if (resultList != null)
			{
				myQueryCollection.addAll(resultList);
			}
			resultList = retrieveQueriesForGetCount(sharedQueriesIdList, queryNameLike);
			if (resultList != null)
			{
				sharedQueryCollection.addAll(resultList);
			}

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * to retrieveQueriesForGetCount.
	 * @param ids Collection<Long>
	 * @param queryNameLike String
	 * @return Collection<Long>
	 * @throws DAOException DAOException
	 */
	public static Collection<Long> retrieveQueriesForGetCount(Collection<Long> ids,
			String queryNameLike) throws DAOException
	{
		StringBuffer objectToRead = generateListOfIds(ids);

		String query = createQuery(queryNameLike, objectToRead);
		Collection<Long> queryIds=Utility.executeQuery(query);

		return queryIds;
	}

	/**
	 * create the query.
	 * @param queryNameLike String query name to search
	 * @param objectToRead query ids to search for
	 * @return StringBuilder query
	 */
	private static String createQuery(String queryNameLike,
			StringBuffer objectToRead)
	{
		StringBuilder query = new StringBuilder();
		if (queryNameLike != null && !queryNameLike.equals("")
				&& !objectToRead.toString().equals("()"))
		{
			StringBuffer escapeSquence=new StringBuffer("");
		    StringBuffer queryNameLikeUpper =new  StringBuffer(Utility.setSpecialCharacters(escapeSquence,
		    		queryNameLike.toUpperCase()));
		    query.append("select id from  ").append(IParameterizedQuery.class.getName())
		    .append(QUERY_WHERE).append(
		            Constants.QUERY_WHERE_QUERY_ID_IN).append(objectToRead).append(
		            " and upper(query.name) like ").append("'").append("%").append(
		            queryNameLikeUpper.toString()).append("%").append("'")
		            .append(escapeSquence.toString()).append(
		            " or upper(query.description) like ").append("'").append("%").append(
		            queryNameLikeUpper.toString()).append("%").append("'").append(escapeSquence.toString())
		            .append(Constants.ORDER_BY_QUERY_ID_DESC);
		}
		else if (!objectToRead.toString().equals("()"))
		{

			query.append("select id from "
					+ IParameterizedQuery.class.getName()+QUERY_WHERE
					+ Constants.QUERY_WHERE_QUERY_ID_IN
					+ objectToRead + Constants.ORDER_BY_QUERY_ID_DESC);
		}
		return query.toString();
	}

}
