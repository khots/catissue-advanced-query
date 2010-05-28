package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.util.global.DEConstants.Cardinality;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.query.beans.QueryResultObjectDataBean;

/**
 * This class parses the query, populates the data structures required for de-normalization of spreadsheet..
 * @author pooja_tavase
 *
 */
public class QueryParser
{
	/**
	 * Parses the query in order to populate the list of entities required
	 * to find out the main entity id index.
	 * @param query query
	 * @param list list
	 * @return oneToManyEntities
	 */
	public List<EntityInterface> parseQuery(IQuery query, List<QueryOutputTreeAttributeMetadata> list)
	{
		IConstraints constraints = query.getConstraints();
		JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
		List<EntityInterface> oneToManyEntities = new ArrayList<EntityInterface>();
		for(IExpression expression: constraints)
		{
			populateOneToManyEntities(list, joinGraph, oneToManyEntities,
					expression);
		}
		return oneToManyEntities;
	}

	/**
	 * Populates a list which will contain all the entities on 'ONE' side of one to many association.
	 * @param list list
	 * @param joinGraph joinGraph
	 * @param oneToManyEntities oneToManyEntities
	 * @param expression expression
	 */
	private void populateOneToManyEntities(
			List<QueryOutputTreeAttributeMetadata> list, JoinGraph joinGraph,
			List<EntityInterface> oneToManyEntities, IExpression expression)
	{
		boolean isOneToMany;
		List<IExpression> childrenList = joinGraph.getChildrenList(expression);
		if(!childrenList.isEmpty())
		{
			//childrenList = joinGraph.getParentList(expression);

			isOneToMany = checkIfOneToMany(expression,childrenList,joinGraph);
			List<EntityInterface> entities = getSelectedEntitiesForDefineView(list);
			if(isOneToMany && entities.contains(expression.getQueryEntity().getDynamicExtensionsEntity()))
			{
				oneToManyEntities.add(expression.getQueryEntity().getDynamicExtensionsEntity());
			}
		}
	}

	/**
	 * Retrieves the list of entities whose attributes are selected for define view.
	 * @param list list
	 * @return entities
	 */
	private List<EntityInterface> getSelectedEntitiesForDefineView(
			List<QueryOutputTreeAttributeMetadata> list)
	{
		List<EntityInterface> entities = new ArrayList<EntityInterface>();
		EntityInterface selectedEntity;
		for(QueryOutputTreeAttributeMetadata outputMetadata : list)
		{
			selectedEntity = outputMetadata.getTreeDataNode().getOutputEntity().getDynamicExtensionsEntity();
			if(!entities.contains(selectedEntity))
			{
				entities.add(selectedEntity);
			}
		}
		return entities;
	}

	/**
	 * Checks if the association is of type 'one to many'.
	 * @param expression expression
	 * @param childrenList childrenList
	 * @param joinGraph joinGraph
	 * @return <CODE>true</CODE> the association is of type 'one to many',
	 * <CODE>false</CODE> otherwise
	 */
	private boolean checkIfOneToMany(IExpression expression,
			List<IExpression> childrenList, JoinGraph joinGraph)
	{
		boolean isOneToMany = false;
		Integer maxCardinalityValue = -1;
		AssociationInterface associationInterface = null;
		for(IExpression child : childrenList)
		{
			IIntraModelAssociation association =
				(IIntraModelAssociation)joinGraph.getAssociation(expression, child);
			if(association == null)
			{
				association = (IIntraModelAssociation)joinGraph.getAssociation(child,expression);
				associationInterface =(AssociationInterface)
				association.getDynamicExtensionsAssociation();
				if(associationInterface.getTargetRole().getAssociationsType().name().equals("CONTAINTMENT"))
				{
					maxCardinalityValue = 0;
				}
			}
			if(association != null)
			{
				associationInterface =(AssociationInterface)
				association.getDynamicExtensionsAssociation();
				if(maxCardinalityValue == -1)
				{
					maxCardinalityValue = getMaxCardinalityValue(associationInterface);
				}
				if(maxCardinalityValue == 1)
				{
					isOneToMany = true;
					break;
				}
			}
		}
		return isOneToMany;
	}

	/**
	 * Get the maximum cardinality value of the particular association.
	 * @param associationInterface associationInterface
	 * @return maxCardinalityValue
	 */
	private Integer getMaxCardinalityValue(
			AssociationInterface associationInterface)
	{
		Integer maxCardinalityValue=-1;
		Cardinality maxCardinality = associationInterface.getSourceRole().getMaximumCardinality();
		if(maxCardinality == null)
		{
			maxCardinalityValue = 1;
		}
		else
		{
			maxCardinalityValue = maxCardinality.getValue();
		}
		return maxCardinalityValue;
	}

	/**
	 * To get the main entity identifier.
	 * @param oneToManyEntities
	 * @param querySessionData querySessionData
	 * @return mainEntityIdIndex
	 */
	public int getMainIdColumnIndex(Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap,
			List<EntityInterface> oneToManyEntities)
	{
		Map<EntityInterface, Integer> entityIdIndexMap = new HashMap<EntityInterface, Integer>();
		int mainEntityIdIndex = setMainEtityIndexId(queryResultObjectDataBeanMap,
				entityIdIndexMap,oneToManyEntities);
		if(mainEntityIdIndex == -1)
		{
			Iterator<EntityInterface> entities = entityIdIndexMap.keySet().iterator();
			if(entities.hasNext())
			{
				EntityInterface entity = entities.next();
				mainEntityIdIndex = entityIdIndexMap.get(entity);
			}
		}
		return mainEntityIdIndex;
	}

	/**
	 * @param querySessionData querySessionData
	 * @param entityIdIndexMap entityIdIndexMap
	 * @param oneToManyEntities oneToManyEntities
	 * @return mainEntityIdIndex
	 */
	private int setMainEtityIndexId(Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap,
			Map<EntityInterface, Integer> entityIdIndexMap, List<EntityInterface> oneToManyEntities)
	{
		int mainEntityIdIndex = -1;
		for (Long id : queryResultObjectDataBeanMap.keySet())
		{
			QueryResultObjectDataBean queryResultObjectDataBean = queryResultObjectDataBeanMap
					.get(id);
			mainEntityIdIndex = getMainIdFromBean(queryResultObjectDataBean,oneToManyEntities);
			if(mainEntityIdIndex == -1)
			{
				populateEntityIdIndexMap(entityIdIndexMap, oneToManyEntities,
						queryResultObjectDataBean);
			}
			else
			{
				break;
			}
		}
		return mainEntityIdIndex;
	}

	/**
	 * Populates the entity id index map where, key=entity and value=main entity id's index
	 * @param entityIdIndexMap entityIdIndexMap
	 * @param oneToManyEntities oneToManyEntities
	 * @param queryResultObjectDataBean queryResultObjectDataBean
	 */
	private void populateEntityIdIndexMap(
			Map<EntityInterface, Integer> entityIdIndexMap,
			List<EntityInterface> oneToManyEntities,
			QueryResultObjectDataBean queryResultObjectDataBean)
	{
		if(queryResultObjectDataBean.getEntity().getName().
				equals(queryResultObjectDataBean.getCsmEntityName()) &&
				oneToManyEntities.contains(queryResultObjectDataBean.getEntity()))
		{
			entityIdIndexMap.put(queryResultObjectDataBean.getEntity(),
					queryResultObjectDataBean.getMainEntityIdentifierColumnId());
		}
	}

	/**
	 * Get main identifier's index from QueryResultObjectDataBean.
	 * @param queryResultObjectDataBean queryResultObjectDataBean
	 * @param oneToManyEntities oneToManyEntities
	 * @return mainEntityIdIndex
	 */
	private int getMainIdFromBean(QueryResultObjectDataBean queryResultObjectDataBean,
			List<EntityInterface> oneToManyEntities)
	{
		int mainEntityIdIndex = -1;
		Map<EntityInterface, Integer> entityIdIndexMap =
			queryResultObjectDataBean.getEntityIdIndexMap();
		for(EntityInterface entity : entityIdIndexMap.keySet())
		{
			if(entity.getName().equals(queryResultObjectDataBean.getEntity().getName())
					&& oneToManyEntities.contains(entity))
			{
				mainEntityIdIndex = entityIdIndexMap.get(entity);
				break;
			}
		}
		return mainEntityIdIndex;
	}
}
