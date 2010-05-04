package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.common.dynamicextensions.domaininterface.AbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.BaseAbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;

/**
 * This class is responsible for exporting the query results
 * (i.e. displaying results in spreadsheet in denormalized form)
 * @author pooja_tavase
 *
 */
public class QueryExportDataHandler
{
	private final Map<QueryHeaderData, Integer> entityVsMaxCount =
		new HashMap<QueryHeaderData, Integer>();
	private final Map<Integer, Map<QueryHeaderData, List<List<Object>>>> recordIdVsentityVsDataList =
		new HashMap<Integer, Map<QueryHeaderData, List<List<Object>>>>();
	private final EntityInterface rootEntity;
	private List<EntityInterface> markedEntities = new ArrayList<EntityInterface>();

	public QueryExportDataHandler(EntityInterface rootEntity)
	{
		this.rootEntity = rootEntity;
	}

	public EntityInterface getRootEntity()
	{
		return rootEntity;
	}

	/**
	 * This method will update the Maps needed for generating the Header
	 * & data list.
	 * @param denormalizationMap denormalizationMap
	 * @param mapIndex mapIndex
	 */
	public void updateRowDataList(Map<BaseAbstractAttributeInterface,Object> denormalizationMap, int mapIndex)
	{
		QueryHeaderData queryDataEntity = new QueryHeaderData(rootEntity, "");
		Map<QueryHeaderData, List<List<Object>>> entityVsDataList =
			new HashMap<QueryHeaderData, List<List<Object>>>();
		generateQueryDatamap(denormalizationMap, queryDataEntity, entityVsDataList, 0);
		recordIdVsentityVsDataList.put(mapIndex, entityVsDataList);
		entityVsMaxCount.put(queryDataEntity, 1);
	}

	/**
	 * This method will update the maps required for exporting the data
	 * (i.e. the data to be shown in the spreadsheet).
	 * @param denormalizationMap denormalizationMap
	 * @param queryDataEntity queryDataCont
	 * @param entityVsDataList entityVsDataList
	 * @param dataCnt dataCnt
	 */
	private void generateQueryDatamap(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			QueryHeaderData queryDataEntity,
			Map<QueryHeaderData, List<List<Object>>> entityVsDataList, int dataCnt)
	{
		if(denormalizationMap != null)
		{
			EntityInterface entity = queryDataEntity.getEntity();
			Collection<AbstractAttributeInterface> attributeList = entity.getAbstractAttributeCollection();
			List<Object> rowDataList = new ArrayList<Object>();
			for(BaseAbstractAttributeInterface attribute : attributeList)
			{
				if(denormalizationMap.get(attribute) != null)
				{
					if (attribute instanceof AttributeInterface
							&& !(denormalizationMap.get(attribute) instanceof List))
					{
						rowDataList.add(getValueForAttributeFromMap(attribute, denormalizationMap));

					}
					else if (attribute instanceof AttributeInterface
							&& (denormalizationMap.get(attribute) instanceof List))
					{
						/**
						 * For multi-select case (e.g. In case of permissible values)
						 */
						String value = processMultiselectAttribute(attribute, denormalizationMap);
						rowDataList.add(value);
					}
					else if (attribute instanceof AssociationInterface)
					{
						String recordNo = queryDataEntity.getParentRecordNo() + "_" + dataCnt;
						processAssociationAttribute(attribute, denormalizationMap, entityVsDataList,
								recordNo);
					}
				}
			}
			populateEntityvsDataList(queryDataEntity, entityVsDataList,
					rowDataList);
		}
	}

	/**
	 * This method will retrieve the value of the attribute from the denormalizationMap,
	 * if present will return it else will return null.
	 * @param attribute
	 * @param denormalizationMap
	 * @return recordValue
	 */
	private Object getValueForAttributeFromMap(
			BaseAbstractAttributeInterface attribute,
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap)
	{
		Object recordValue = null;
		if (denormalizationMap.get(attribute) != null)
		{
			recordValue = denormalizationMap.get(attribute).toString();
		}
		return recordValue;
	}

	/**
	 * This method will retrieve the values of the attribute from denormalizationMap
	 * and append these values to the string (comma separated)
	 * @param attribute attribute
	 * @param denormalizationMap denormalizationMap
	 * @return the value
	 */
	private String processMultiselectAttribute(
			BaseAbstractAttributeInterface attribute,
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap)
	{
		final StringBuffer value = new StringBuffer();
		List tempList = (List) denormalizationMap.get(attribute);
		for (Object dataValue : tempList)
		{
			if (dataValue != null)
			{
				appendValues(value, dataValue);
			}
		}
		return value.toString();
	}

	/**
	 * Append the values to the StringBuffer object (comma separated)
	 * @param value value
	 * @param dataValue dataValue
	 */
	private void appendValues(final StringBuffer value, Object dataValue)
	{
		Map<BaseAbstractAttributeInterface, Object> tempValueMap =
			(Map<BaseAbstractAttributeInterface, Object>) dataValue;
		for (Object obj : tempValueMap.values())
		{
			if (value == null || value.equals(""))
			{
				value.append(obj.toString());
			}
			else
			{
				value.append(" ; ");
				value.append(obj.toString());
			}
		}
	}

	/**
	 * To process the association attribute i.e. retrieves the inner map and
	 * gives call to generateQueryDatamap(...) method.
	 * @param attribute attribute
	 * @param denormalizationMap denormalizationMap
	 * @param entityVsDataList entityVsDataList
	 * @param recordNo recordNo
	 */
	private void processAssociationAttribute(
			BaseAbstractAttributeInterface attribute,
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			Map<QueryHeaderData, List<List<Object>>> entityVsDataList,
			String parentRecordNo)
	{
		List tempList = (List) denormalizationMap.get(attribute);
		AssociationInterface association = (AssociationInterface)attribute;
		EntityInterface entity = association.getTargetEntity();
		QueryHeaderData queryDataEntity = new QueryHeaderData(entity, parentRecordNo);
		updateMaxRecordCount(tempList, queryDataEntity);
		if (!tempList.isEmpty())
		{
			for (int recordNo = 0; recordNo < tempList.size(); recordNo++)
			{
				Map<BaseAbstractAttributeInterface, Object> obj =
					(Map<BaseAbstractAttributeInterface, Object>) tempList.get(recordNo);
				generateQueryDatamap(obj, queryDataEntity, entityVsDataList, recordNo);
			}
		}
	}

	/**
	 * Update the map of entity v/s maximum count.
	 * @param tempList tempList
	 * @param queryDataEntity queryDataEntity
	 */
	private void updateMaxRecordCount(List tempList,
			QueryHeaderData queryDataEntity)
	{
		Integer dataListCount = getMaxRecordCountForQueryHeader(queryDataEntity);
		if (dataListCount == null || tempList.size() > dataListCount)
		{
			dataListCount = tempList.size();
		}
		populateEntityVsMaxCount(queryDataEntity,dataListCount);
		entityVsMaxCount.put(queryDataEntity, dataListCount);
	}

	private void populateEntityVsMaxCount(QueryHeaderData queryDataEntity,
			Integer dataListCount)
	{
		Set<QueryHeaderData> keySet = entityVsMaxCount.keySet();
		Iterator<QueryHeaderData> iterator = keySet.iterator();
		while(iterator.hasNext())
		{
			QueryHeaderData headerData = iterator.next();
			if(headerData.getEntity().getName().equals(queryDataEntity.getEntity().getName()))
			{
				entityVsMaxCount.put(headerData, dataListCount);
				break;
			}
		}
	}

	/**
	 * Populate the entity v/s data list map.
	 * @param queryDataEntity queryDataEntity
	 * @param entityVsDataList entityVsDataList
	 * @param rowDataList rowDataList
	 */
	private void populateEntityvsDataList(QueryHeaderData queryDataEntity,
			Map<QueryHeaderData, List<List<Object>>> entityVsDataList,
			List<Object> rowDataList)
	{
		List<List<Object>> entityDataList = entityVsDataList.get(queryDataEntity);
		if (entityDataList == null)
		{
			entityDataList = new ArrayList<List<Object>>();
		}
		entityDataList.add(rowDataList);
		entityVsDataList.put(queryDataEntity, entityDataList);
	}

	/**
	 * Responsible for populating the data list.
	 * @param counter counter
	 * @return the data list
	 */
	public List<Object> getDataList(Integer counter)
	{
		markedEntities = new ArrayList<EntityInterface>();
		Map<QueryHeaderData, List<List<Object>>> entityMap = recordIdVsentityVsDataList
				.get(counter);
		QueryHeaderData queryHeaderData = new QueryHeaderData(rootEntity, "");
		return getDataListForSpreadsheetDataCSV(queryHeaderData, entityMap);
	}

	/**
	 * Populates the data list containing the results to be shown in the spreadsheet.
	 * @param queryHeaderData queryHeaderData
	 * @param entityMap entityMap
	 * @return dataList
	 */
	private List<Object> getDataListForSpreadsheetDataCSV(
			QueryHeaderData queryHeaderData,
			Map<QueryHeaderData, List<List<Object>>> entityMap)
	{
		populateMarkedEntities(queryHeaderData);
		Integer maxRecordCount = getMaxRecordCountForQueryHeader(queryHeaderData);

		Collection<AbstractAttributeInterface> attributeList = getFinalAttributeList(queryHeaderData);
		List<List<Object>> entityDataList = getEntityDataList(entityMap,queryHeaderData);

		List<Object> dataList = new ArrayList<Object>();

		for (int cntr = 0; cntr < maxRecordCount; cntr++)
		{
			List<Object> queryData = getRecordListForRecordId(entityDataList, cntr);
			int attrControlNo = 0;
			for (AbstractAttributeInterface attribute : attributeList)
			{
				if (attribute instanceof AssociationInterface)
				{
					EntityInterface entity = ((AssociationInterface)attribute).getTargetEntity();
					String parentRecordId = queryHeaderData.getParentRecordNo() + "_" + cntr;
					QueryHeaderData headerData = new QueryHeaderData(entity,
							parentRecordId);
					if(!markedEntities.contains(entity))
					{
						dataList.addAll(getDataListForSpreadsheetDataCSV(headerData, entityMap));
					}
				}
				else
				{
					Object dataValue;
					if (entityDataList != null && dataList.size()>=attrControlNo &&
							queryData.size()>attrControlNo)
					{
						dataValue = queryData.get(attrControlNo);
						dataList.add(dataValue);
						attrControlNo++;
					}
				}
			}
		}
		return dataList;
	}

	/**
	 * Populate marked entities. This list contains the list of entities for whom the processing is already done
	 * in order to avoid recursion.
	 * @param queryHeaderData queryHeaderData
	 */
	private void populateMarkedEntities(QueryHeaderData queryHeaderData)
	{
		if(!markedEntities.contains(queryHeaderData.getEntity()))
		{
			markedEntities.add(queryHeaderData.getEntity());
		}
	}

	/**
	 * Populate the list with the direct value from the map.
	 * @param entityDataList entityDataList
	 * @param dataList dataList
	 * @param queryData queryData
	 * @param attrControlNo attrControlNo
	 */
	private void populateListForAttributes(List<List<Object>> entityDataList,
			List<Object> dataList, List<Object> queryData, int attrControlNo)
	{
		Object dataValue;
		if (entityDataList != null && dataList.size()>=attrControlNo &&
				queryData.size()>attrControlNo)
		{
			dataValue = queryData.get(attrControlNo);
			dataList.add(dataValue);
			attrControlNo++;
		}
	}

	/**
	 * Add all the associations (of parents) and then filter the list to contain only the required attributes and associations.
	 * @param queryHeaderData queryHeaderData
	 * @return attributeList
	 */
	private Collection<AbstractAttributeInterface> getFinalAttributeList(
			QueryHeaderData queryHeaderData)
	{
		Collection<AbstractAttributeInterface> attributeList =
			queryHeaderData.getEntity().getAbstractAttributeCollection();
		Collection<AbstractAttributeInterface> newAttributeList = new ArrayList<AbstractAttributeInterface>();
		for(AbstractAttributeInterface attribute : attributeList)
		{
			if(attribute instanceof AssociationInterface)
			{
				if(!((AssociationInterface)attribute).getTargetEntity().getName().
						equals(queryHeaderData.getEntity().getName()))
				{
					newAttributeList.add(attribute);
				}
			}
			else
			{
				newAttributeList.add(attribute);
			}
		}
		return newAttributeList;
	}

	/**
	 * Get entity data list.
	 * @param entityMap entityMap
	 * @param queryHeaderData queryHeaderData
	 * @return entityDataList
	 */
	private List<List<Object>> getEntityDataList(
			Map<QueryHeaderData, List<List<Object>>> entityMap,
			QueryHeaderData queryHeaderData)
	{
		List<List<Object>> entityDataList = null;
		Set<QueryHeaderData> keySet = entityMap.keySet();
		Iterator<QueryHeaderData> iterator = keySet.iterator();
		while(iterator.hasNext())
		{
			QueryHeaderData headerData = iterator.next();
			if(headerData.getEntity().getName().equals(queryHeaderData.getEntity().getName()))
			{
				entityDataList = entityMap.get(headerData);
				break;
			}
		}
		return entityDataList;
	}

	/**
	 * Get record list i.e. the inner list with data.
	 * @param entityDataList entityDataList
	 * @param cntr counter
	 * @return queryData
	 */
	private List<Object> getRecordListForRecordId(
			List<List<Object>> entityDataList, int cntr)
	{
		List<Object> queryData;
		if (entityDataList != null && entityDataList.size() > cntr)
		{
			queryData = entityDataList.get(cntr);
		}
		else
		{
			queryData = new ArrayList<Object>();
		}
		return queryData;
	}

	/**
	 * Get maximum record count for a particular QueryHeaderData object.
	 * @param queryHeaderData queryHeaderData
	 * @return maxCount
	 */
	private Integer getMaxRecordCountForQueryHeader(
			QueryHeaderData queryHeaderData)
	{
		Integer maxCount = 0;
		Set<QueryHeaderData> keySet = entityVsMaxCount.keySet();
		Iterator<QueryHeaderData> iterator = keySet.iterator();
		while(iterator.hasNext())
		{
			QueryHeaderData headerData = iterator.next();
			if(headerData.getEntity().getName().equals(queryHeaderData.getEntity().getName()))
			{
				maxCount = entityVsMaxCount.get(headerData);
				break;
			}
		}
		return maxCount;
	}
}
