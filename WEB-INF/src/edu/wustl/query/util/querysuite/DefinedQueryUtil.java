package edu.wustl.query.util.querysuite;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.factory.AbstractBizLogicFactory;
import edu.wustl.common.hibernate.HibernateCleanser;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.metadata.util.DyExtnObjectCloner;
import edu.wustl.query.util.global.Constants;


public class DefinedQueryUtil
{

	public void insertQuery(IQuery query) throws UserNotAuthorizedException, BizLogicException
	{
		IParameterizedQuery parameterizedQuery = populateParameterizedQueryData(query);
		parameterizedQuery.setName(((IParameterizedQuery)query).getName());
		IBizLogic bizLogic = AbstractBizLogicFactory.getBizLogic(ApplicationProperties
				.getValue("app.bizLogicFactory"), "getBizLogic",
				Constants.QUERY_INTERFACE_BIZLOGIC_ID);
		IParameterizedQuery queryClone = new DyExtnObjectCloner().clone(parameterizedQuery);
		new HibernateCleanser(queryClone).clean();
		bizLogic.insert(queryClone, Constants.HIBERNATE_DAO);
		query.setId(queryClone.getId());
		
	}
	public IParameterizedQuery populateParameterizedQueryData(IQuery query)	
	{
		IParameterizedQuery parameterizedQuery = (IParameterizedQuery) query;

		if (query.getId() == null)
		{
			parameterizedQuery = QueryObjectFactory.createParameterizedQuery(query);
		}

	return parameterizedQuery;
	}

}
