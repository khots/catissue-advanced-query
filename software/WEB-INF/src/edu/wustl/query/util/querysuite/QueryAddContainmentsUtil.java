
package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.RoleInterface;
import edu.wustl.cab2b.client.ui.dag.PathLink;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.querableobject.QueryableObjectUtility;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.utils.IConstraintsObjectBuilderInterface;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.Constants;

/**
 * 
 * @author Baljeet Singh
 *
 */
public class QueryAddContainmentsUtil
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(QueryAddContainmentsUtil.class);

	/**
	 * This method returns all containments for a Main entity present in Query
	 * @param partentChildEntityMap
	 * @param mainEntityContainmentList
	 * @param mainEntity
	 */
	public static void getMainEntityContainments(
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap,
			List<QueryableObjectInterface> mainEntityContainmentList,
			QueryableObjectInterface mainEntity)
	{
		//For each Entity, get all Associations
		List<QueryableObjectInterface> list = getContainmentAssociations(mainEntity);
		List<QueryableObjectInterface> uniqueEntityList = new ArrayList<QueryableObjectInterface>();
		Iterator<QueryableObjectInterface> itr = list.iterator();
		while (itr.hasNext())
		{
			QueryableObjectInterface entity = itr.next();
			if (!mainEntityContainmentList.contains(entity))
			{
				mainEntityContainmentList.add(entity);
				uniqueEntityList.add(entity);
			}
		}
		partentChildEntityMap.put(mainEntity, uniqueEntityList);

		//Now for each containment entity, get further containments till it ends
		if (!mainEntityContainmentList.isEmpty())
		{
			getChildEntityContainment(partentChildEntityMap, mainEntityContainmentList);
		}
	}

	/**
	 * This method returns all containments for a child entity of a main Entity present in Query
	 * @param partentChildEntityMap
	 * @param mainEntityContainmentList
	 */
	private static void getChildEntityContainment(
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap,
			List<QueryableObjectInterface> mainEntityContainmentList)
	{
		List<QueryableObjectInterface> list;
		int count = 0;
		while (count < mainEntityContainmentList.size())
		{
			QueryableObjectInterface containmentEntity = mainEntityContainmentList.get(count);
			list = getContainmentAssociations(containmentEntity);
			List<QueryableObjectInterface> uniqueEntityList = new ArrayList<QueryableObjectInterface>();
			Iterator<QueryableObjectInterface> itr = list.iterator();
			while (itr.hasNext())
			{
				QueryableObjectInterface entity = itr.next();
				if (!mainEntityContainmentList.contains(entity))
				{

					mainEntityContainmentList.add(entity);
					uniqueEntityList.add(entity);
				}
			}
			if (!uniqueEntityList.isEmpty())
			{
				partentChildEntityMap.put(containmentEntity, uniqueEntityList);
			}
			count++;
		}
	}

	/**
	 * This method returns list containing all containment entities of a entity
	 * @param entity
	 * @return containmentEntitiesList
	 */

	private static List<QueryableObjectInterface> getContainmentAssociations(
			QueryableObjectInterface entity)
	{
		//List to which all containment entities are added
		ArrayList<QueryableObjectInterface> containmentEntitiesList = new ArrayList<QueryableObjectInterface>();
		Collection<AssociationInterface> associationColl = entity.getEntity().getAllAssociations();

		//For each association check the association type
		Iterator<AssociationInterface> itr = associationColl.iterator();
		while (itr.hasNext())
		{
			AssociationInterface association = itr.next();
			RoleInterface targetRole = association.getTargetRole();
			if (targetRole.getAssociationsType().getValue().equals(
					Constants.CONTAINTMENT_ASSOCIATION))
			{
				//Then get the target entity and put it in Map  
				QueryableObjectInterface targetEntity = QueryableObjectUtility
						.createQueryableObject(association.getTargetEntity());
				containmentEntitiesList.add(targetEntity);
			}
		}
		return containmentEntitiesList;
	}

	/**
	 * This method add path among two entities
	 * @param parentExpressionId
	 * @param childExpressionId
	 * @param path
	 * @param queryObject
	 */

	//This method is marked public as it is also invoked from DefineSearchResultsView action class 
	public static void linkTwoNodes(int parentExpressionId, int childExpressionId,
			final IPath path, IConstraintsObjectBuilderInterface queryObject)
	{
		try
		{
			//Adding the link between the two entities 
			List<Integer> intermediateExpressions = queryObject.addPath(parentExpressionId,
					childExpressionId, path);
			PathLink link = new PathLink();
			link.setAssociationExpressions(intermediateExpressions);
			link.setSourceExpressionId(parentExpressionId);
			link.setDestinationExpressionId(childExpressionId);
			link.setPath(path);
			updateQueryObject(link, parentExpressionId, queryObject);
		}
		catch (CyclicException e)
		{
			logger.trace(e);
		}
	}

	/**
	 * This method updates IQuery for added path among two entities  
	 * @param link
	 * @param parentExpressionId
	 * @param queryObject
	 */
	private static void updateQueryObject(PathLink link, int parentExpressionId,
			IConstraintsObjectBuilderInterface queryObject)
	{
		String operator = "";
		int destId = link.getLogicalConnectorExpressionId();
		queryObject.setLogicalConnector(parentExpressionId, destId,
				edu.wustl.cab2b.client.ui.query.Utility.getLogicalOperator(operator), false);
	}
}
