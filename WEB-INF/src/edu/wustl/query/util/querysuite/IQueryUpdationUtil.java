
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.RoleInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.DEUtility;

/**
 *
 * @author Baljeet Singh
 *
 */
public class IQueryUpdationUtil
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(IQueryUpdationUtil.class);

	/**
	 * This method returns list containing all containment entities of a entity.
	 *
	 * @param entity
	 *            to get containments
	 * @param tagName
	 *            name of tag
	 * @return containmentEntitiesList
	 */

	public static List<QueryableObjectInterface> getContainmentsForDefaultConditions(
			QueryableObjectInterface entity, String tagName)
	{
		// List to which all containment entities are added
		ArrayList<QueryableObjectInterface> containmentEntitiesList = new ArrayList<QueryableObjectInterface>();
		Collection<AssociationInterface> associationColl = entity.getEntity().getAllAssociations();

		// For each association check the association type
		Iterator<AssociationInterface> itr = associationColl.iterator();
		while (itr.hasNext())
		{
			AssociationInterface association = itr.next();
			RoleInterface targetRole = association.getTargetRole();
			if (targetRole.getAssociationsType().getValue().equals(
					Constants.CONTAINTMENT_ASSOCIATION)
					&& DEUtility.istagPresent(association, tagName))
			{
				// Then get the target entity and put it in Map
				EntityInterface targetEntity = association.getTargetEntity();
				containmentEntitiesList.add(QueryableObjectUtility
						.createQueryableObject(targetEntity));
			}
		}
		return containmentEntitiesList;
	}

	/**
	 * This method is made public as it is also invoked from action class This
	 * Method returns all main objects present in IQuery
	 *
	 * @param query
	 * @return queryMainEntityList
	 */

	public static List<QueryableObjectInterface> getAllMainObjects(IQuery query)
	{
		IConstraints constraints = query.getConstraints();

		List<QueryableObjectInterface> mainEntityList = new ArrayList<QueryableObjectInterface>();
		List<QueryableObjectInterface> queryMainEntList = new ArrayList<QueryableObjectInterface>();
		// For each Expression in the Query Constraints
		for (IExpression expression : constraints)
		{
			QueryableObjectInterface entity = expression.getQueryEntity()
					.getDynamicExtensionsEntity();
			mainEntityList = getAllMainEntities(entity, mainEntityList);
			if (mainEntityList.contains(entity))
			{
				queryMainEntList.add(entity);
			}
		}
		return queryMainEntList;
	}

	/**This is a recursive method that will create list of all main entities
	 * (Entities for which entity passed to it is having containment association )
	 * @param entity whose main Entities should be found
	 * @param mainEntityList in which to populate the list
	 */
	public static List<QueryableObjectInterface> getAllMainEntities(
			QueryableObjectInterface entity, List<QueryableObjectInterface> mainEntityList)
	{
		try
		{
			if (entity.isCategory())
			{
				if (entity.getRootQueryableObject().isTagPresent(Constants.TAGGED_VALUE_MAIN_ENTIY)
						&& !mainEntityList.contains(entity))
				{
					mainEntityList.add(entity);
				}
			}
			else
			{
				getMainEntityList(entity, mainEntityList);
			}

		}
		catch (DynamicExtensionsSystemException deExeption)
		{
			//deExeption.printStackTrace();
			logger.error(deExeption.getMessage(), deExeption);
			//mainEntityList = new ArrayList<QueryableObjectInterface>();
		}
		return mainEntityList;
	}

	/**
	 * This is a recursive method that will create list of all main entities by calling the getAllMainEntities.
	 * @param entity whose main Entities should be found
	 * @param mainEntityList in which to populate the list
	 * @return mainEntityList
	 * @throws DynamicExtensionsSystemException exception
	 */
	private static List<QueryableObjectInterface> getMainEntityList(
			QueryableObjectInterface entity, List<QueryableObjectInterface> mainEntityList)
			throws DynamicExtensionsSystemException
	{
		List<AssociationInterface> associationList = getIncomingContainmentAssociations(entity
				.getEntity());
		if (!associationList.isEmpty())
		{
			for (AssociationInterface assocoation : associationList)
			{
				getAllMainEntities(QueryableObjectUtility.createQueryableObject(assocoation
						.getEntity()), mainEntityList);
			}
		}
		else if (!mainEntityList.contains(entity))
		{
			mainEntityList.add(entity);
		}
		return mainEntityList;
	}

	/**This method will internally call  getIncomingAssociationIds of DE which will return all incoming associations
	 * for entities passed.This method will filter out all incoming containment associations and return list of them.
	 * @param entity
	 */
	private static List<AssociationInterface> getIncomingContainmentAssociations(
			EntityInterface entity) throws DynamicExtensionsSystemException
	{
		EntityManagerInterface entityManager = EntityManager.getInstance();
		ArrayList<Long> allIds = (ArrayList<Long>) entityManager.getIncomingAssociationIds(entity);
		List<AssociationInterface> list = new ArrayList<AssociationInterface>();
		EntityCache cache = EntityCache.getInstance();
		for (Long id : allIds)
		{
			AssociationInterface associationById = cache.getAssociationById(id);

			RoleInterface targetRole = associationById.getTargetRole();
			if (targetRole.getAssociationsType().getValue().equals(
					Constants.CONTAINTMENT_ASSOCIATION))
			{
				list.add(associationById);
			}
		}
		return list;
	}

	/**
	 * This method add path among two entities
	 *
	 * @param parentExpId
	 * @param childExpressionId
	 * @param path
	 * @param queryObject
	 */

	public static List<IExpression> getAllMainExpressionList(IQuery query)
	{
		IConstraints constraints = query.getConstraints();
		List<QueryableObjectInterface> mainEntityList = new ArrayList<QueryableObjectInterface>();

		List<IExpression> queryMainExpList = new ArrayList<IExpression>();

		// For each Expression in the Query Constraints
		for (IExpression expression : constraints)
		{
			QueryableObjectInterface entity = expression.getQueryEntity()
					.getDynamicExtensionsEntity();

			mainEntityList = getAllMainEntities(entity, mainEntityList);
			if (mainEntityList.contains(entity))
			{
				queryMainExpList.add(expression);
			}

		}
		return queryMainExpList;
	}

	/**
	 * It will update the getPatientDataQuery for the given parameters. It will
	 * update the conditions on the attributes as given in allQueriesConitionStr
	 * & eachQueryCFMap then it will remove the emptyConditions in Query & all
	 * the parameters.
	 *
	 * @param getPatientDataQuery
	 *            which is going to be updated.
	 * @param allQueriesConitionStr
	 *            in which the conditions are mentioned .
	 * @param eachQueryCFMap
	 *            customFormula conditions map
	 * @throws JSONException
	 */
	public static String updateQueryForParameters(IQuery getPatientDataQuery,
			String allQueriesConitionStr, Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap)
			throws JSONException
	{

		String errormsg = "";

		if (allQueriesConitionStr != null  && allQueriesConitionStr.length()>0 && eachQueryCFMap != null)
		{
			JSONObject jsonObject = new JSONObject(allQueriesConitionStr);
			Iterator<Object> keyItr = jsonObject.keys();
			String conditionString = "";
			String rhsList = "";
			while (keyItr.hasNext())
			{
				String innerKey = keyItr.next().toString();
				if (innerKey.equalsIgnoreCase("queryConditions"))
				{
					// This is normal conditions String
					conditionString = jsonObject.get(innerKey).toString();
				}
				else
				{
					// else this is temporal condition string
					rhsList = jsonObject.get(innerKey).toString();
				}
			}
			// Here updating Query for normal Conditions
			errormsg = updateConditions(getPatientDataQuery, eachQueryCFMap, conditionString,
					rhsList);

		}
		return errormsg;
	}

	private static String updateConditions(IQuery getPatientDataQuery,
			Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap, String conditionString,
			String rhsList)
	{

		StringBuffer errormsg = new StringBuffer("");
		if (conditionString != null)
		{
			CreateQueryObjectBizLogic createQueryBizLogic = new CreateQueryObjectBizLogic();
			String errorMessage;
			errorMessage = createQueryBizLogic.setInputDataToQuery(conditionString,
					getPatientDataQuery.getConstraints(), null, getPatientDataQuery);
			errormsg.append(errorMessage);
			Map<Integer, ICustomFormula> customFormulaIndexMap = eachQueryCFMap
					.get(getPatientDataQuery.getId());
			errorMessage = createQueryBizLogic.setInputDataToTQ(getPatientDataQuery,
					Constants.EXECUTE_QUERY_PAGE, rhsList, customFormulaIndexMap);
			errormsg.append(errorMessage);
		}
		if (!(errormsg.length() > 0))
		{
			// Now remove the empty conditions from the Query
			edu.wustl.query.util.global.QueryParameterUtil
					.removeEmptyParameters((IParameterizedQuery) getPatientDataQuery);
		}
		return errormsg.toString();
	}

}
