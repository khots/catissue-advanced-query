
package edu.wustl.query.util.querysuite;

import java.util.List;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.hibernate.HibernateCleanser;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.Constants;

/**
 * @author chitra_garg
 * saves the defined query in database 
 */
public class DefinedQueryUtil
{

	/**
	 * This methods saves the query in the data base 
	 * 
	 * @param query=IQuery
	 * @throws UserNotAuthorizedException
	 * @throws BizLogicException
	 * @throws DAOException 
	 */
	public void insertQuery(IQuery query, SessionDataBean sessionDataBean, boolean isShared)
			throws UserNotAuthorizedException, BizLogicException, DAOException
	{
		IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query);
		parameterizedQuery.setName(((IParameterizedQuery) query).getName());
		edu.wustl.query.bizlogic.QueryBizLogic bizLogic = (edu.wustl.query.bizlogic.QueryBizLogic) AbstractBizLogicFactory
				.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"), "getBizLogic",
						Constants.ADVANCE_QUERY_INTERFACE_ID);
		IParameterizedQuery queryClone = new DyExtnObjectCloner().clone(parameterizedQuery);
		new HibernateCleanser(queryClone).clean();
		bizLogic.insertSavedQueries(queryClone, sessionDataBean, isShared);

		query.setId(queryClone.getId());

	}

	/**
	 * This methods updates the query in the database which is already saved 
	 * @param query=IQuery
	 * @throws UserNotAuthorizedException
	 * @throws BizLogicException
	 */
	public void updateQuery(IQuery query) throws UserNotAuthorizedException, BizLogicException
	{
		IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query);
		parameterizedQuery.setId(query.getId());
		parameterizedQuery.setName(((IParameterizedQuery) query).getName());
		edu.wustl.query.bizlogic.QueryBizLogic bizLogic = (edu.wustl.query.bizlogic.QueryBizLogic) AbstractBizLogicFactory
				.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"), "getBizLogic",
						Constants.ADVANCE_QUERY_INTERFACE_ID);
		bizLogic.update(parameterizedQuery, Constants.HIBERNATE_DAO);

	}

	/**
	 * This  creates ParameterizedQuery
	 * @param query = IQuery
	 * @return
	 */
	public IParameterizedQuery populateParameterizedQueryData(IQuery query)
	{
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) query;
		List<IOutputAttribute> outputAttributeList = parameterizedQuery.getOutputAttributeList();
		parameterizedQuery = QueryObjectFactory.createParameterizedQuery(query);
		return parameterizedQuery;
	}

}
