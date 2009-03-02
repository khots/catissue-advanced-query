/**
 * 
 */

package edu.wustl.query.bizlogic;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.dao.DAO;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;

/**
 * @author chetan_patil
 * @created Sep 13, 2007, 7:39:46 PM
 */
public class QueryBizLogic extends DefaultBizLogic
{

	protected void insert(Object obj, DAO dao, SessionDataBean sessionDataBean)
	throws DAOException, UserNotAuthorizedException
	{
		super.insert(obj, dao, sessionDataBean);
	}
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

}
