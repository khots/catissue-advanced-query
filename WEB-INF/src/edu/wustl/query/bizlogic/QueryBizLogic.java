/**
 * 
 */

package edu.wustl.query.bizlogic;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.bizlogic.IQueryBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.query.authoriztion.SavedQueryAuthorization;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.enums.QuerySharingStatus;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.security.exception.SMException;
import edu.wustl.security.manager.ISecurityManager;
import edu.wustl.security.manager.SecurityManagerFactory;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

/**
 * @author chetan_patil
 * @created Sep 13, 2007, 7:39:46 PM
 */
public class QueryBizLogic extends DefaultQueryBizLogic implements IQueryBizLogic
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(QueryBizLogic.class);

	/**
	 * It will insert the Query object in the database.
	 * @param query
	 * @param sessionDataBean
	 * @param shareQuery
	 * @throws BizLogicException
	 */
	public void insertSavedQueries(IParameterizedQuery query, SessionDataBean sessionDataBean,
			boolean shareQuery) throws BizLogicException
	{
		HibernateDAO hibernateDao = null;
		try
		{
			hibernateDao = DAOUtil.getHibernateDAO(sessionDataBean);
			insert(query, hibernateDao);

		}
		catch (DAOException e)
		{

			throw new BizLogicException(e);
		}
		finally
		{
			if (hibernateDao != null)
			{
				try
				{
					hibernateDao.commit();
					DAOUtil.closeHibernateDAO(hibernateDao);
				}
				catch (DAOException e)
				{
					logger.error(e.getMessage(), e);
				}
			}
		}

		insertAuthorizationDataForQuery(query, sessionDataBean, shareQuery);
	}

	
	/** 
	 * It will insert the protectionElement & protectionObject(CSM Data) for the Query.
	 * @param query
	 * @param sessionDataBean
	 * @param shareQuery
	 * @throws BizLogicException
	 */
	private void insertAuthorizationDataForQuery(IParameterizedQuery query, SessionDataBean sessionDataBean,
			boolean shareQuery) throws BizLogicException
	{
		HashSet<ParameterizedQuery> protectionObjects = new HashSet<ParameterizedQuery>();
		protectionObjects.add((ParameterizedQuery) query);

		User user = null;
		try
		{

			ISecurityManager securityManager = SecurityManagerFactory.getSecurityManager();
			//user = new PrivilegeUtility().getUserById(sessionDataBean.getCsmUserId());
			user = securityManager.getUserById(sessionDataBean.getCsmUserId());
		}
		catch (SMException e)
		{
			logger.error(e.getMessage(), e);
		}

		SavedQueryAuthorization savedQuery = new SavedQueryAuthorization();
		try
		{
			savedQuery.authenticate(protectionObjects, user.getUserId().toString(), shareQuery,
					user);
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage(), e);
			throw new BizLogicException(e);
		}
	}

	protected String getRole(SessionDataBean sessionDataBean)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * It will update the query object in the dataBase
	 * @param query
	 * @param sessionDataBean
	 * @param shareQuery
	 * @throws BizLogicException
	 */
	public void updateSavedQueries(IParameterizedQuery query, SessionDataBean sessionDataBean,
			boolean shareQuery) throws BizLogicException
	{
		HibernateDAO hibernateDao = null;
		try
		{
			hibernateDao = DAOUtil.getHibernateDAO(sessionDataBean);
			query.setUpdatedBy(sessionDataBean.getUserId());
			query.setUpdationDate(new Date());
			super.update(hibernateDao, query);
			hibernateDao.commit();
			updateProtectionElementForQuery(query, shareQuery);

		}
		catch (DAOException e)
		{

			throw new BizLogicException(e);

		}
		finally
		{

			try
			{
				DAOUtil.closeHibernateDAO(hibernateDao);
			}
			catch (DAOException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * It will insert the protection Element for the Query if it was not shared before 
	 * but now it is marked as shared.
	 * @param query
	 * @param shareQuery it specifies weather user has shared that query.
	 * @throws BizLogicException
	 */
	private void updateProtectionElementForQuery(IParameterizedQuery query, boolean shareQuery)
			throws BizLogicException
	{
		try
		{
			if (shareQuery)
			{
				SavedQueryAuthorization savedQuery = new SavedQueryAuthorization();
				savedQuery.insertProtectionElementForSharedQueries((ParameterizedQuery) query);
			}
		}
		catch (CSException e)
		{
			throw getBizLogicException(e, "db.operation.error", e.getMessage());

		}
		catch (SMException e)
		{

			throw getBizLogicException(e, "db.operation.error", e.getMessage());
		}
	}

	/**
	 * It will check weather the given query is shared by the user or not.
	 * @param query which is to be checked.
	 * @return true if query is shared else false .
	 * @throws BizLogicException
	 * @throws QueryModuleException 
	 */
	public boolean isSharedQuery(IParameterizedQuery query) throws BizLogicException, QueryModuleException
	{

		SavedQueryAuthorization savedQuery = new SavedQueryAuthorization();
		boolean isShared = false;
		try
		{
			isShared = savedQuery.checkIsSharedQuery(query);
		}
		catch (DAOException e)
		{
			throw new BizLogicException(e);
		}
		catch (SMException e)
		{
			logger.error(e.getMessage(), e);
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.GENERIC_EXCEPTION);
		}

		return isShared;
	}

	/**
	 * This method returns the IQuery object 
	 * @param queryId
	 * @return
	 * @throws BizLogicException
	 */
	@SuppressWarnings("unchecked")
	public IQuery getQuery(Long queryId) throws BizLogicException
	{
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		IBizLogic bizLogic = factory.getBizLogic(Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		final List<IQuery> queryList = bizLogic.retrieve(ParameterizedQuery.class.getName(),
				Constants.ID, queryId);
		IQuery query = null;
		if (queryList != null && !queryList.isEmpty())
		{
			query = queryList.get(0);
		}
		return query;
	}

	/**Method to get Sharing status of the Query.
	 * @param parameterizedQuery query of which sharing status is required
	 * @param userId user id 
	 * @return QuerySharingStatus
	 * @throws QueryModuleException Query module exception
	 */
	public QuerySharingStatus getSharingStatus(IParameterizedQuery parameterizedQuery, Long userId)
			throws QueryModuleException
	{
		QuerySharingStatus sharingStatus = QuerySharingStatus.SHARED;
		try
		{
			if (!isSharedQuery(parameterizedQuery))
			{
				sharingStatus = QuerySharingStatus.NOT_SHARED_NOT_USED;
				String hqlString = "select workflow "
						+ " from "
						+ Workflow.class.getName()
						+ " as workflow, "
						+ WorkflowItem.class.getName()
						+ " as wfItem where workflow.createdBy="
						+ userId
						+ " and wfItem.id in elements(workflow.workflowItemList) and wfItem.query.id="
						+ parameterizedQuery.getId();

				DefaultQueryBizLogic bizLogic = new DefaultQueryBizLogic();
				List<Workflow> workflowList = bizLogic.executeQuery(hqlString);
				if (workflowList != null && !workflowList.isEmpty() )
				{
					sharingStatus = QuerySharingStatus.NOT_SHARED_BUT_USED;
				}
			}
		}
		catch (BizLogicException e)
		{
			logger.error(e.getMessage(), e);
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.GENERIC_EXCEPTION);
		}
		logger.info("Sharing status of query.id:" + parameterizedQuery.getId() + " is:"
				+ sharingStatus);
		return sharingStatus;
	}
}
