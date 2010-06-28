package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.BaseAbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.query.util.global.Utility;

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
	private Map<EntityInterface,BaseAbstractAttributeInterface> entityVsAssoc =
		new HashMap<EntityInterface, BaseAbstractAttributeInterface>();
	private Map<EntityInterface,BaseAbstractAttributeInterface> tgtEntityVsAssoc =
		new HashMap<EntityInterface, BaseAbstractAttributeInterface>();
	private final Map<Integer,List<OutputAttributeColumn>> selectedAttributes =
		new HashMap<Integer,List<OutputAttributeColumn>>();
	private final List<OutputAttributeColumn> selectedAttributeList = new ArrayList<OutputAttributeColumn>();
	private int index;

	public QueryExportDataHandler(EntityInterface rootEntity)
	{
		this.rootEntity = rootEntity;
	}

	public EntityInterface getRootEntity()
	{
		return rootEntity;
	}

	/**
	 * @param entityVsAssoc the entityVsAssoc to set
	 */
	public void setEntityVsAssoc(Map<EntityInterface,BaseAbstractAttributeInterface> entityVsAssoc)
	{
		this.entityVsAssoc = entityVsAssoc;
	}

	/**
	 * @return the entityVsAssoc
	 */
	public Map<EntityInterface,BaseAbstractAttributeInterface> getEntityVsAssoc()
	{
		return entityVsAssoc;
	}

	public void setTgtEntityVsAssoc(
			Map<EntityInterface, BaseAbstractAttributeInterface> tgtEntityVsAssoc)
	{
		this.tgtEntityVsAssoc = tgtEntityVsAssoc;
	}

	/**
	 * @return the tgtEntityVsAssoc
	 */
	public Map<EntityInterface, BaseAbstractAttributeInterface> getTgtEntityVsAssoc()
	{
		return tgtEntityVsAssoc;
	}

	/**
	 * This method will update the Maps needed for generating the Header
	 * & data list.
	 * @param denormalizationMap denormalizationMap
	 * @param mapIndex mapIndex
	 */
	public void updateRowDataList(Map<BaseAbstractAttributeInterface,Object> denormalizationMap, int mapIndex)
	{
		this.index = mapIndex;
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
			processDiffAttributes(denormalizationMap, queryDataEntity,
					entityVsDataList, dataCnt);
		}
	}

	/**
	 * This method gets all the attributes of entities one by one and
	 * accordingly populates the denormalization map.
	 * @param denormalizationMap denormalizationMap
	 * @param queryDataEntity queryDataEntity
	 * @param entityVsDataList entityVsDataList
	 * @param dataCnt dataCnt
	 */
	private void processDiffAttributes(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			QueryHeaderData queryDataEntity,
			Map<QueryHeaderData, List<List<Object>>> entityVsDataList,
			int dataCnt)
	{
		EntityInterface entity = queryDataEntity.getEntity();
		Collection<AbstractAttributeInterface> attributeList = getAbstractAttributeCollection(entity);
		List<Object> rowDataList = new ArrayList<Object>();
		for(BaseAbstractAttributeInterface attribute : attributeList)
		{
			if(denormalizationMap.get(attribute) != null)
			{
				if (attribute instanceof AttributeInterface
						&& !(denormalizationMap.get(attribute) instanceof List))
				{
					Object recordValue = getValueForAttributeFromMap(attribute, denormalizationMap);
					rowDataList.add(recordValue);
					populateSelectedAttributes(recordValue);
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

	/**
	 * Add the attribute in the list if not present.
	 * @param attribute attribute
	 * @param recordValue
	 */
	private void populateSelectedAttributes(Object recordValue)
	{
		List<OutputAttributeColumn> selectedList = selectedAttributes.get(index);
		boolean flag = false;
		if(selectedList == null)
		{
			selectedList = new ArrayList<OutputAttributeColumn>();
			selectedList.add((OutputAttributeColumn) recordValue);
			selectedAttributes.put(index, selectedList);
		}
		else
		{
			for(OutputAttributeColumn opAttrColumn : selectedList)
			{
				if(opAttrColumn.getAttribute().equals(((OutputAttributeColumn)recordValue).getAttribute()))
				{
					flag = true;
					break;
				}
			}
			if(flag == false)
			{
				selectedList.add((OutputAttributeColumn) recordValue);
			}
		}
		if(!selectedAttributeList.contains(recordValue))
		{
			selectedAttributeList.add((OutputAttributeColumn) recordValue);
		}
	}

	private Collection<AbstractAttributeInterface> getAbstractAttributeCollection(
			EntityInterface entity)
	{
		Collection<AbstractAttributeInterface> finalList = new ArrayList<AbstractAttributeInterface>();
		Collection<AbstractAttributeInterface> attributeList =
			entity.getAllAbstractAttributes();
		finalList.addAll(attributeList);
		AbstractAttributeInterface assocInterface = (AbstractAttributeInterface)entityVsAssoc.get(entity);
		if(assocInterface != null && !finalList.contains(assocInterface))
		{
			finalList.add(assocInterface);
		}
		return finalList;
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
		OutputAttributeColumn recordValue = null;
		if (denormalizationMap.get(attribute) != null)
		{
			recordValue = (OutputAttributeColumn)denormalizationMap.get(attribute);
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
			if ("".equals(value))
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
		EntityInterface entity = getTargetEntity(association);
		if(entity == null)
		{
			entity = association.getTargetEntity();
		}
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

	private EntityInterface getTargetEntity(AssociationInterface association)
	{
		EntityInterface entity = null;
		for(EntityInterface tgtEntity : tgtEntityVsAssoc.keySet())
		{
			if(tgtEntityVsAssoc.get(tgtEntity).equals(association))
			{
				entity = tgtEntity;
				break;
			}
		}
		return entity;
	}

	/**
	 * Update the map of entity v/s maximum count.
	 * @param tempList tempList
	 * @param queryDataEntity queryDataEntity
	 */
	private void updateMaxRecordCount(List tempList,
			QueryHeaderData queryDataEntity)
	{
		Integer dataListCount = entityVsMaxCount.get(queryDataEntity);
		if (dataListCount == null || tempList.size() > dataListCount)
		{
			dataListCount = tempList.size();
		}
		entityVsMaxCount.put(queryDataEntity, dataListCount);
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
	 * This method returns the header list to be displayed in the CSV file.
	 * @return headerList
	 */
	public List<Object> getHeaderList()
	{
		markedEntities = new ArrayList<EntityInterface>();
		List<Object> headerList;
		QueryHeaderData headerData = new QueryHeaderData(rootEntity, "");
		headerList = getHeaderForSpreadsheetDataCSV(headerData);
		return headerList;
	}

	/**
	 * This method generates the headers to be displayed in the CSV file.
	 * @param headerData headerData
	 * @return
	 */
	private List<Object> getHeaderForSpreadsheetDataCSV(
			QueryHeaderData queryHeaderData)
	{
		List<Object> headerList = new ArrayList<Object>();

		populateMarkedEntities(queryHeaderData);
		Integer maxRecordCount = getMaxRecordCountForQueryHeader(queryHeaderData);

		Collection<AbstractAttributeInterface> attributeList =
			getFinalAttributeList(queryHeaderData.getEntity());

		for (int cntr = 0; cntr < maxRecordCount; cntr++)
		{
			for (AbstractAttributeInterface attribute : attributeList)
			{
				if (attribute instanceof AssociationInterface)
				{
					populateHeaderListForAssoc(queryHeaderData, headerList,
							cntr, attribute);
				}
				else
				{
					populateHeaderListForAttribute(headerList, cntr, attribute);
				}
			}
		}
		return headerList;
	}

	/**
	 * Populate header list for attributes.
	 * @param headerList headerList
	 * @param cntr counter
	 * @param attribute attribute
	 */
	private void populateHeaderListForAttribute(List<Object> headerList,
			int cntr, AbstractAttributeInterface attribute)
	{
		OutputAttributeColumn opAttrCol = getOpAttributeColumnForHeader(attribute);
		if(opAttrCol != null)
		{
			StringBuffer headerDisplay = new StringBuffer();
			headerDisplay.append(Utility.getDisplayNameForColumn((AttributeInterface)attribute));
			if(cntr>0)
			{
				headerDisplay.append('_').append(cntr);
			}
			if(opAttrCol.getHeader() == null)
			{
				opAttrCol.setHeader(headerDisplay.toString());
				headerList.add(opAttrCol);
			}
			else
			{
				StringBuffer originalHeader = new StringBuffer(opAttrCol.getHeader());
				originalHeader.append("|").append(headerDisplay.toString());
				opAttrCol.setHeader(originalHeader.toString());
			}
		}
	}

	private OutputAttributeColumn getOpAttributeColumnForHeader(AbstractAttributeInterface attribute)
	{
		OutputAttributeColumn opAttrCol = null;
		for(OutputAttributeColumn opAttrColumn : selectedAttributeList)
		{
			if(opAttrColumn.getAttribute().equals(attribute))
			{
				opAttrCol = opAttrColumn;
				break;
			}
		}
		return opAttrCol;
	}
	/**
	 * Populate the header list.
	 * @param queryHeaderData queryHeaderData
	 * @param headerList headerList
	 * @param cntr counter
	 * @param attribute attribute
	 */
	private void populateHeaderListForAssoc(QueryHeaderData queryHeaderData,
			List<Object> headerList, int cntr,
			AbstractAttributeInterface attribute)
	{
		EntityInterface entity = getTargetEntity((AssociationInterface)attribute);
		if(entity == null)
		{
			entity = ((AssociationInterface)attribute).getTargetEntity();
		}
		String parentRecordId = queryHeaderData.getParentRecordNo() + "_" + cntr;
		QueryHeaderData headerData = new QueryHeaderData(entity,
				parentRecordId);
		if(!markedEntities.contains(entity))
		{
			headerList.addAll(getHeaderForSpreadsheetDataCSV(headerData));
		}
	}

	/**
	 * Responsible for populating the data list.
	 * @param counter counter
	 * @return the data list
	 */
	public List<Object> getDataList(Integer counter)
	{
		this.index = counter;
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
		Collection<AbstractAttributeInterface> attributeList = getFinalAttributeList(queryHeaderData.getEntity());
		List<List<Object>> entityDataList = entityMap.get(queryHeaderData);

		List<Object> dataList = new ArrayList<Object>();

		for (int cntr = 0; cntr < maxRecordCount; cntr++)
		{
			List<Object> queryData = getRecordListForRecordId(entityDataList, cntr);
			int attrControlNo = 0;
			int counter = 0;
			for (AbstractAttributeInterface attribute : attributeList)
			{
				if (attribute instanceof AssociationInterface)
				{
					EntityInterface entity = getTargetEntity((AssociationInterface)attribute);
					if(entity != null)
					{
						//entity = ((AssociationInterface)attribute).getTargetEntity();

					String parentRecordId = queryHeaderData.getParentRecordNo() + "_" + cntr;
					QueryHeaderData headerData = new QueryHeaderData(entity,
							parentRecordId);
					if(!markedEntities.contains(entity))
					{
						dataList.addAll(getDataListForSpreadsheetDataCSV(headerData, entityMap));
					}
					}
				}
				else
				{
					Object dataValue = null;
					if (entityDataList != null && entityDataList.size()>cntr &&
							getOpAttributeColumnForHeader(attribute) != null)
					{
						dataValue = queryData.get(counter);
						counter++;
					}
					OutputAttributeColumn opAttrCol = getOpAttributeColumnForData(attribute);
					if(opAttrCol != null)
					{
						if(dataValue == null)
						{
							dataValue = opAttrCol;
							StringBuffer value = new StringBuffer(((OutputAttributeColumn)dataValue).getValue());
							value.append("|").append(" ");
							((OutputAttributeColumn)dataValue).setValue(value.toString());
						}
						if(!dataList.contains(dataValue))
						{
							dataList.add(dataValue);
						}
					}
					attrControlNo++;
				}
			}
		}
		return dataList;
	}

	private OutputAttributeColumn getOpAttributeColumnForData(AbstractAttributeInterface attribute)
	{
		OutputAttributeColumn opAttrCol = null;
		List<OutputAttributeColumn> selectedList = selectedAttributes.get(index);

		for(OutputAttributeColumn opAttrColumn : selectedList)
		{
			if(opAttrColumn.getAttribute().equals(attribute))
			{
				opAttrCol = opAttrColumn;
				break;
			}
		}
		return opAttrCol;
	}
	/**
	 * Populate marked entities. This list contains the list of entities
	 * for whom the processing is already done
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
	 * Add all the associations (of parents) and then
	 * filter the list to contain only the required attributes and associations.
	 * @param queryHeaderData queryHeaderData
	 * @return attributeList
	 */
	public List<AbstractAttributeInterface> getFinalAttributeList(
			EntityInterface entity)
	{
		List<AbstractAttributeInterface> attributeList =
			(List<AbstractAttributeInterface>) entity.getAllAbstractAttributes();
		List<AbstractAttributeInterface> newAttributeList = new ArrayList<AbstractAttributeInterface>();
		for(AbstractAttributeInterface attribute : attributeList)
		{
			populateNewAttributeList(entity, newAttributeList, attribute);
		}
		AbstractAttributeInterface assocInterface = (AbstractAttributeInterface)entityVsAssoc.get(entity);
		if(assocInterface != null && !newAttributeList.contains(assocInterface))
		{
			newAttributeList.add(assocInterface);
		}
		return newAttributeList;
	}

	/**
	 * Populate new attribute list.
	 * @param entity entity
	 * @param newAttributeList newAttributeList
	 * @param attribute attribute
	 */
	private void populateNewAttributeList(EntityInterface entity,
			List<AbstractAttributeInterface> newAttributeList,
			AbstractAttributeInterface attribute)
	{
		if(attribute instanceof AssociationInterface)
		{
			if(!((AssociationInterface)attribute).getTargetEntity().getName().
					equals(entity.getName()))
			{
				newAttributeList.add(attribute);
			}
		}
		else
		{
			newAttributeList.add(attribute);
		}
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
		Integer maxCount = entityVsMaxCount.get(queryHeaderData);
		if (maxCount == null)
		{
			maxCount = 0;
		}
		return maxCount;
	}
}
