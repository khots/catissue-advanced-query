
package edu.wustl.query.util.querysuite;

import edu.wustl.cab2b.common.queryengine.ICab2bQuery;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractFactoryConfig;
import edu.wustl.common.factory.IFactory;
import edu.wustl.common.hibernate.HibernateCleanser;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.Constants;

/**saves the defined query in database.
 * @author chitra_garg
 *
 */
public class DefinedQueryUtil
{

	/**
	 * This methods saves the query in the data base.
	 * @param query =IQuery
	 * @param sessionDataBean object of SessionDataBean
	 * @param isShared boolean for query shared or not
	 * @throws BizLogicException BizLogic Exception
	 */
	public void insertQuery(IQuery query, SessionDataBean sessionDataBean, boolean isShared)
			throws BizLogicException
	{
		IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query);
		//		edu.wustl.query.bizlogic.QueryBizLogic bizLogic = (edu.wustl.query.bizlogic.QueryBizLogic) AbstractBizLogicFactory
		//				.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"), "getBizLogic",
		//						Constants.ADVANCE_QUERY_INTERFACE_ID);
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		edu.wustl.query.bizlogic.QueryBizLogic bizLogic = (edu.wustl.query.bizlogic.QueryBizLogic) factory
				.getBizLogic(Constants.ADVANCE_QUERY_INTERFACE_ID);

		IParameterizedQuery queryClone = new DyExtnObjectCloner().clone(parameterizedQuery);
		new HibernateCleanser(queryClone).clean();
		bizLogic.insertSavedQueries(queryClone, sessionDataBean, isShared);

		query.setId(queryClone.getId());

	}

	/**
	 * This methods updates the query in the database which is already saved.
	 * @param query =IQuery
	 * @param sessionDataBean object of SessionDataBean
	 * @param isShared boolean for query shared or not
	 * @throws BizLogicException BizLogic Exception
	 */
	public void updateQuery(IQuery query, SessionDataBean sessionDataBean, boolean isShared)
			throws BizLogicException
	{
		IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query);
		parameterizedQuery.setId(query.getId());
		//		edu.wustl.query.bizlogic.QueryBizLogic bizLogic = (edu.wustl.query.bizlogic.QueryBizLogic) AbstractBizLogicFactory
		//				.getBizLogic(ApplicationProperties.getValue("app.bizLogicFactory"), "getBizLogic",
		//						Constants.ADVANCE_QUERY_INTERFACE_ID);
		//
		IFactory factory = AbstractFactoryConfig.getInstance().getBizLogicFactory();
		edu.wustl.query.bizlogic.QueryBizLogic bizLogic = (edu.wustl.query.bizlogic.QueryBizLogic) factory
				.getBizLogic(Constants.ADVANCE_QUERY_INTERFACE_ID);
		bizLogic.updateSavedQueries(parameterizedQuery, sessionDataBean, isShared);

	}

	/**
	 * This  creates ParameterizedQuery.
	 * @param query = IQuery
	 * @return parameterizedQuery
	 */
	public IParameterizedQuery populateParameterizedQueryData(IQuery query)
	{
		IParameterizedQuery parameterizedQuery;
		if (query instanceof ICab2bQuery)
		{
			parameterizedQuery = QueryObjectFactory.createParameterizedQuery(query);
		}
		else
		{
			parameterizedQuery = (IParameterizedQuery) query;
		}
		return parameterizedQuery;
	}

}
