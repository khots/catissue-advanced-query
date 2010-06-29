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
	private List<EntityInterface> manyToOneEntities = new ArrayList<EntityInterface>();
	private List<EntityInterface> oneToManyEntities = new ArrayList<EntityInterface>();
	private List<EntityInterface> containmentEntities = new ArrayList<EntityInterface>();
	private List<EntityInterface> containmentAssocList = new ArrayList<EntityInterface>();

	/**
	 * Parses the query in order to populate the list of entities required
	 * to find out the main entity id index.
	 * @param query query
	 * @param list list
	 * @return oneToManyEntities
	 */
	public void parseQuery(IQuery query, List<QueryOutputTreeAttributeMetadata> list)
	{
		IConstraints constraints = query.getConstraints();
		for(IExpression expression: constraints)
		{
			if(expression.isInView())
			{
				populateOneToManyEntities(list, (JoinGraph)constraints.getJoinGraph(),
						expression);
			}
		}
		containmentEntities.removeAll(containmentAssocList);
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
			IExpression expression)
	{
		List<IExpression> childrenList = joinGraph.getChildrenList(expression);
		if(childrenList.isEmpty())
		{
			childrenList = joinGraph.getParentList(expression);
		}
		int maxCardinalityValue = checkIfOneToMany(expression,childrenList,joinGraph);
		List<EntityInterface> entities = getSelectedEntitiesForDefineView(list);
		if(entities.contains(expression.getQueryEntity().getDynamicExtensionsEntity()))
		{
			populateAppropriateList(expression, maxCardinalityValue);
		}
	}

	/**
	 * Populate the oneToMany/manyToOne list appropriately.
	 * @param expression expression
	 * @param maxCardinalityValue maxCardinalityValue
	 */
	private void populateAppropriateList(IExpression expression,
			int maxCardinalityValue)
	{
		if(maxCardinalityValue == 0)
		{
			containmentEntities.add(expression.getQueryEntity().getDynamicExtensionsEntity());
		}
		else if(maxCardinalityValue == 1)
		{
			oneToManyEntities.add(expression.getQueryEntity().getDynamicExtensionsEntity());
		}
		else if(maxCardinalityValue == 2)
		{
			manyToOneEntities.add(expression.getQueryEntity().getDynamicExtensionsEntity());
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
	private int checkIfOneToMany(IExpression expression,
			List<IExpression> childrenList, JoinGraph joinGraph)
	{
		Integer maxCardinalityValue = -1;
		AssociationInterface associationInterface;
		for(IExpression child : childrenList)
		{
			IIntraModelAssociation association =
				(IIntraModelAssociation)joinGraph.getAssociation(expression, child);
			if(association == null)
			{
				association = (IIntraModelAssociation)joinGraph.getAssociation(child,expression);
				associationInterface =(AssociationInterface)
				association.getDynamicExtensionsAssociation();
				maxCardinalityValue = getMaxCardinalityForChildToExp(
						maxCardinalityValue, associationInterface);
				if(maxCardinalityValue == 0)
				{
					IExpression finalExp = getParentExpression(joinGraph,child);
					populateContainmentAssoc(finalExp);
				}
			}
			else
			{
				associationInterface =(AssociationInterface)
				association.getDynamicExtensionsAssociation();
				if(maxCardinalityValue == -1)
				{
					maxCardinalityValue = getMaxCardinalityValue(associationInterface);
				}
				if(maxCardinalityValue == 0)
				{
					IExpression finalExp = getChildExpression(joinGraph,child);
					populateContainmentAssoc(finalExp);
				}
				else if(maxCardinalityValue == 1)
				{
					break;
				}
			}
		}
		return maxCardinalityValue;
	}

	/**
	 * Populate the containmentAssocList.
	 * @param finalExp finalExp
	 */
	private void populateContainmentAssoc(IExpression finalExp)
	{
		if(!containmentAssocList.contains(finalExp.getQueryEntity().getDynamicExtensionsEntity()))
		{
			containmentAssocList.add(finalExp.getQueryEntity().getDynamicExtensionsEntity());
		}
	}

	/**
	 * Get the final child expression(added in the DAG) of the passed expression.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @return finalExp
	 */
	private IExpression getChildExpression(JoinGraph joinGraph,
			IExpression expression)
	{
		IExpression finalExp = expression;
		if(!expression.isInView() && !joinGraph.getChildrenList(expression).isEmpty())
		{
			IExpression childExp = joinGraph.getChildrenList(expression).get(0);
			finalExp = getChildExpression(joinGraph,childExp);
		}
		return finalExp;
	}

	/**
	 * Get the parent expression of the passed expression.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @return finalExp
	 */
	private IExpression getParentExpression(JoinGraph joinGraph, IExpression expression)
	{
		IExpression finalExp = expression;
		if(!expression.isInView())
		{
			IExpression parentExp = joinGraph.getParentList(expression).get(0);
			finalExp = getParentExpression(joinGraph,parentExp);
		}
		return finalExp;
	}
	/**
	 * Gets the max cardinality value (0/2).
	 * @param maxCardinality maxCardinality
	 * @param associationInterface associationInterface
	 * @return maxCardinalityValue
	 */
	private Integer getMaxCardinalityForChildToExp(Integer maxCardinality,
			AssociationInterface associationInterface)
	{
		Integer maxCardinalityValue = maxCardinality;
		if(associationInterface.getSourceRole().getAssociationsType().name().equals("CONTAINTMENT"))
		{
			maxCardinalityValue = 0;
		}
		else
		{
			if(associationInterface.getSourceRole().getMaximumCardinality() == null
					|| associationInterface.getSourceRole().getMaximumCardinality().getValue() == 2)
			{
				maxCardinalityValue = 2;
			}
		}
		return maxCardinalityValue;
	}

	/**
	 * Get the maximum cardinality value of the particular association.
	 * @param associationInterface associationInterface
	 * @return maxCardinalityValue
	 */
	private Integer getMaxCardinalityValue(
			AssociationInterface associationInterface)
	{
		Integer maxCardinalityValue;
		if(associationInterface.getTargetRole().getAssociationsType().name().equals("CONTAINTMENT"))
		{
			maxCardinalityValue = 0;
		}
		else
		{
		Cardinality maxCardinality = associationInterface.getSourceRole().getMaximumCardinality();
			if(maxCardinality == null)
			{
				maxCardinalityValue = 1;
			}
			else
			{
				maxCardinalityValue = maxCardinality.getValue();
			}
		}
		return maxCardinalityValue;
	}

	/**
	 * To get the main entity identifier.
	 * @param oneToManyEntities
	 * @param querySessionData querySessionData
	 * @return mainEntityIdIndex
	 */
	public int getMainIdColumnIndex(Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap)
	{
		Map<EntityInterface, Integer> entityIdIndexMap = new HashMap<EntityInterface, Integer>();
		int mainEntityIdIndex;
		if(manyToOneEntities.isEmpty())
		{
			if(oneToManyEntities.isEmpty())
			{
				mainEntityIdIndex = setMainEtityIndexId(queryResultObjectDataBeanMap,
						entityIdIndexMap,containmentEntities);
			}
			else
			{
				mainEntityIdIndex = setMainEtityIndexId(queryResultObjectDataBeanMap,
						entityIdIndexMap,oneToManyEntities);
			}
		}
		else
		{
			mainEntityIdIndex = setMainEtityIndexId(queryResultObjectDataBeanMap,
					entityIdIndexMap,manyToOneEntities);
		}
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
