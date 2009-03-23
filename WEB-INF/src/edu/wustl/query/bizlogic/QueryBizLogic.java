/**
 * 
 */

package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.authoriztion.SavedQueryAuthorization;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.query.util.global.Constants;

/**
 * @author chetan_patil
 * @created Sep 13, 2007, 7:39:46 PM
 */
public class QueryBizLogic extends DefaultBizLogic
{

	/* (non-Javadoc)
	 * @see edu.wustl.common.bizlogic.DefaultBizLogic#insert(java.lang.Object, edu.wustl.common.dao.DAO, edu.wustl.common.beans.SessionDataBean)
	 */
	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean)
	throws DAOException, UserNotAuthorizedException
	{
		super.insert(obj, dao, sessionDataBean);
	}
	/**
	 * It will insert the Query object in the database.
	 * @param query
	 * @param sessionDataBean
	 * @param shareQuery
	 * @throws DAOException
	 * @throws UserNotAuthorizedException
	 * @throws BizLogicException
	 */
	public void insertSavedQueries(IParameterizedQuery query,SessionDataBean sessionDataBean, boolean shareQuery)throws DAOException, UserNotAuthorizedException, BizLogicException
	{
		insert(query,edu.wustl.common.util.global.Constants.HIBERNATE_DAO);
//		HashSet<ParameterizedQuery> protectionObjects = new HashSet<ParameterizedQuery>();
//		protectionObjects.add((ParameterizedQuery) query);
//
//		User user = null;
//		try
//		{
//			user = new PrivilegeUtility().getUserProvisioningManager().getUser(sessionDataBean.getUserName());
//		}
//		catch (CSException e)
//		{
//			new UserNotAuthorizedException(e.getMessage(),e);
//		}
//		sessionDataBean.setCsmUserId(user.getUserId().toString());
//		
//		SavedQueryAuthorization savedQuery = new SavedQueryAuthorization();
//		savedQuery.authenticate(protectionObjects,user.getUserId().toString(),shareQuery,user);
	}
	/**
	 * It will update the query object in the dataBase
	 * @param query
	 * @param sessionDataBean
	 * @param shareQuery
	 * @throws DAOException
	 * @throws UserNotAuthorizedException
	 * @throws BizLogicException
	 */
	public void updateSavedQueries(IParameterizedQuery query,SessionDataBean sessionDataBean, boolean shareQuery)throws DAOException, UserNotAuthorizedException, BizLogicException
	{
		super.update(query, Constants.HIBERNATE_DAO);
	}
	
	/**
	 * It will check weather the given query is shared by the user or not.
	 * @param query which is to be checked.
	 * @return true if query is shared else false .
	 * @throws BizLogicException
	 */
	public boolean isSharedQuery(IParameterizedQuery query)throws BizLogicException
	{
		SavedQueryAuthorization savedQuery = new SavedQueryAuthorization();
		boolean isShared=false;
		try
		{
			isShared = savedQuery.checkIsSharedQuery(query);
			
		}
		catch (DAOException e) 
		{
			throw new BizLogicException(e);
		}
		
		return isShared;
	}
}
