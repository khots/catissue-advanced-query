package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.domaininterface.BaseAbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.wustl.common.query.queryobject.impl.DenormalizedCSVExporter;
import edu.wustl.common.query.queryobject.impl.ListComparator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.QueryExportDataHandler;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryDetails;

public class SpreadsheetDenormalizationBizLogic
{
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(SpreadsheetDenormalizationBizLogic.class);

	private List<Map<BaseAbstractAttributeInterface,Object>> denormalizationList =
		new ArrayList<Map<BaseAbstractAttributeInterface,Object>>();

	private int mainIdColumnIndex = -1;

	/**
	 * Scans the entire IQuery starting from root node in order to populate the map
	 * required as an input to getDataOutProcessor.
	 * @param queryDetailsObj query details object
	 * @param dataList the list that contains the results of the query
	 * @param selectedColumnsMetadata selectedColumnsMetadata
	 * @param querySessionData querySessionData
	 */
	public List<List<String>> scanIQuery(QueryDetails queryDetailsObj, List<List<String>> dataList,
			SelectedColumnsMetadata selectedColumnsMetadata, QuerySessionData querySessionData)
	{
		List<List<String>> finalDataList = new ArrayList<List<String>>();
		try
		{
			if(selectedColumnsMetadata.isDefinedView())
			{
					mainIdColumnIndex = getMainIdColumnIndex(querySessionData);
					if(mainIdColumnIndex != -1)
					{
						Collections.sort(dataList, new ListComparator(mainIdColumnIndex));
					}
					populateMapForDefineView(queryDetailsObj,
						selectedColumnsMetadata.getSelectedAttributeMetaDataList(),dataList,
						querySessionData.getSql());
			}
			IQuery query = queryDetailsObj.getQuery();
			IConstraints constraints = query.getConstraints();
			JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
			EntityInterface rootEntity = getRootEntity(joinGraph);
			QueryExportDataHandler dataHandler = new QueryExportDataHandler(rootEntity);
			for(int count=0;count<denormalizationList.size();count++)
			{
				dataHandler.updateRowDataList(denormalizationList.get(count),count);
			}
			DenormalizedCSVExporter csvExporter = new DenormalizedCSVExporter();
			String filename = generateNewFileName(rootEntity);
			finalDataList =
				csvExporter.addDataToCSV(filename, denormalizationList.size(),dataHandler);
		}
		catch (MultipleRootsException e)
		{
			logger.error(e.getMessage(), e);
		}
		return finalDataList;
	}

	/**
	 * To get the main entity identifier.
	 * @param querySessionData querySessionData
	 * @return mainEntityIdIndex
	 */
	private int getMainIdColumnIndex(QuerySessionData querySessionData)
	{
		Map<EntityInterface, Integer> entityIdIndexMap = new HashMap<EntityInterface, Integer>();
		int mainEntityIdIndex = setMainEtityIndexId(querySessionData,
				entityIdIndexMap);
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
	 *
	 * @param querySessionData querySessionData
	 * @param entityIdIndexMap entityIdIndexMap
	 * @return mainEntityIdIndex
	 */
	private int setMainEtityIndexId(QuerySessionData querySessionData,
			Map<EntityInterface, Integer> entityIdIndexMap)
	{
		Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap =
			querySessionData.getQueryResultObjectDataMap();
		int mainEntityIdIndex = -1;
		for (Long id : queryResultObjectDataBeanMap.keySet())
		{
			QueryResultObjectDataBean queryResultObjectDataBean = queryResultObjectDataBeanMap
					.get(id);
			mainEntityIdIndex = getMainIdFromBean(queryResultObjectDataBean);
			if(mainEntityIdIndex == -1)
			{
				if(queryResultObjectDataBean.getEntity().getName().
						equals(queryResultObjectDataBean.getCsmEntityName()))
				{
					entityIdIndexMap.put(queryResultObjectDataBean.getEntity(),
							queryResultObjectDataBean.getMainEntityIdentifierColumnId());
				}
			}
			else
			{
				break;
			}
		}
		return mainEntityIdIndex;
	}

	private int getMainIdFromBean(QueryResultObjectDataBean queryResultObjectDataBean)
	{
		int mainEntityIdIndex = -1;
		Map<EntityInterface, Integer> entityIdIndexMap =
			queryResultObjectDataBean.getEntityIdIndexMap();
		for(EntityInterface entity : entityIdIndexMap.keySet())
		{
			if(entity.getName().equals(queryResultObjectDataBean.getEntity().getName()))
			{
				mainEntityIdIndex = entityIdIndexMap.get(entity);
				break;
			}
		}
		return mainEntityIdIndex;
	}

	/**
	 * Populates the map for define view where key->BaseAbstractAttributeInterface and value->Object,
	 * Object can be actual value of attribute or a map.
	 * @param queryDetailsObj queryDetailsObj
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @param dataList dataList
	 * @param selectSql selectSql
	 */
	private void populateMapForDefineView(QueryDetails queryDetailsObj,
			List<QueryOutputTreeAttributeMetadata>selectedAttributeMetaDataList,
			List<List<String>> dataList, String selectSql) throws MultipleRootsException
	{
		IQuery query = queryDetailsObj.getQuery();
		IConstraints constraints = query.getConstraints();
		JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
		EntityInterface rootEntity = getRootEntity(joinGraph);
		OutputTreeDataNode treeDataNode;
		Map<BaseAbstractAttributeInterface,Object> denormalizationMap =
			new HashMap<BaseAbstractAttributeInterface,Object>();
		Map<BaseAbstractAttributeInterface,Object> innerMap;
		Integer idColumnValue = -100;
		Map<BaseAbstractAttributeInterface,Map<BaseAbstractAttributeInterface,Object>> associationInterfaceMap =
			new HashMap<BaseAbstractAttributeInterface, Map<BaseAbstractAttributeInterface,Object>>();
		BaseAbstractAttributeInterface associationInterface;
		for(List<String> list : dataList)
		{
			associationInterfaceMap =
				new HashMap<BaseAbstractAttributeInterface, Map<BaseAbstractAttributeInterface,Object>>();
			denormalizationMap = populateDenormalizationList(denormalizationMap, idColumnValue, list);
			idColumnValue = Integer.parseInt(list.get(mainIdColumnIndex));
			for(QueryOutputTreeAttributeMetadata outputTreeAttributeMetadata : selectedAttributeMetaDataList)
			{
				innerMap = new HashMap<BaseAbstractAttributeInterface, Object>();
				treeDataNode = outputTreeAttributeMetadata.getTreeDataNode();
				String entityName = treeDataNode.getOutputEntity().getDynamicExtensionsEntity().getName();
				String columnName = outputTreeAttributeMetadata.getColumnName();
				BaseAbstractAttributeInterface attribute = outputTreeAttributeMetadata.getAttribute();
				Map<String,String> columnNameMap = getColumnNameMap(selectSql,list);
				String value = columnNameMap.get(columnName);
				if(rootEntity.getName().equals(entityName))
				{
					populateDenormalizationMap(denormalizationMap, attribute,
							value);
				}
				else
				{
					populateInnerMap(attribute, value,innerMap);
						associationInterface =
							getBaseAbstractAttributeInterface(constraints, joinGraph, treeDataNode);
					if(associationInterfaceMap.get(associationInterface) == null)
					{
						associationInterfaceMap.put(associationInterface, innerMap);
					}
					else
					{
						Map<BaseAbstractAttributeInterface, Object> map = associationInterfaceMap.get(associationInterface);
						map.put(attribute, value);
					}
				}
			}
			if(associationInterfaceMap != null)
			{
				populateDenormalizedMap(denormalizationMap, associationInterfaceMap);
			}
		}
		denormalizationList.add(denormalizationMap);
	}

	/**
	 * Add the map in the denormalizationList after a particular entity's records are added in the map.
	 * @param denormalizationMap denormalizationMap
	 * @param idColumnValue idColumnValue
	 * @param list list
	 */
	private Map<BaseAbstractAttributeInterface, Object> populateDenormalizationList(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			Integer idColumnValue, List<String> list)
	{
		Map<BaseAbstractAttributeInterface, Object> tempMap =denormalizationMap;
		if(!denormalizationMap.isEmpty() && idColumnValue != -100 &&
					idColumnValue != Integer.parseInt(list.get(mainIdColumnIndex)))
		{
			denormalizationList.add(denormalizationMap);
			tempMap = new HashMap<BaseAbstractAttributeInterface, Object>();
		}
		return tempMap;
	}

	/**
	 * Get the root entity from the join graph.
	 * @param joinGraph joinGraph
	 * @return rootEntity
	 * @throws MultipleRootsException MultipleRootsException
	 */
	private EntityInterface getRootEntity(JoinGraph joinGraph)
			throws MultipleRootsException
	{
		IExpression rootExpression = joinGraph.getRoot();
		IQueryEntity queryEntity = rootExpression.getQueryEntity();
		return queryEntity.getDynamicExtensionsEntity();
	}

	/**
	 * If the key already exists in the map, then this method retrieves the value(which is list of map)
	 * corresponding to the AssociationInterface and adds this value to the list.
	 * @param denormalizationMap denormalizationMap
	 * @param associationInterfaceMap associationInterfaceMap
	 */
	private void populateDenormalizedMap(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			Map<BaseAbstractAttributeInterface,Map<BaseAbstractAttributeInterface,Object>>associationInterfaceMap)
	{
		for(BaseAbstractAttributeInterface associationInterface : associationInterfaceMap.keySet())
		{
			Map<BaseAbstractAttributeInterface,Object> innerMap =
				associationInterfaceMap.get(associationInterface);
			List<Map<BaseAbstractAttributeInterface,Object>> innerMapList;
			if(denormalizationMap.get(associationInterface) == null)
			{
				innerMapList = new ArrayList<Map<BaseAbstractAttributeInterface,Object>>();
				innerMapList.add(innerMap);
				denormalizationMap.put(associationInterface, innerMapList);
			}
			else
			{
				innerMapList =
				(List<Map<BaseAbstractAttributeInterface,Object>>)denormalizationMap.get(associationInterface);
				if(!innerMapList.contains(innerMap))
				{
					innerMapList.add(innerMap);
				}
			}
		}
	}

	/**
	 * Populates the map in cases where object will be the actual value of the attribute.
	 * @param denormalizationMap denormalizationMap
	 * @param attribute attribute
	 * @param value value
	 */
	private void populateDenormalizationMap(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			BaseAbstractAttributeInterface attribute, String value)
	{
		if(value != null && denormalizationMap.get(attribute) == null)
		{
			denormalizationMap.put(attribute, value);
		}
	}

	/**
	 * Populates the inner map in case of define view.
	 * @param attribute attribute
	 * @param value value
	 * @return innerMap
	 */
	private Map<BaseAbstractAttributeInterface, Object> populateInnerMap(
			BaseAbstractAttributeInterface attribute, String value,
			Map<BaseAbstractAttributeInterface, Object> innerMap)
	{
		populateDenormalizationMap(innerMap, attribute, value);
		return innerMap;
	}

	/**
	 * This method finds the association for the passed object of OutputTreeDataNode
	 * and returns the associationInterface.
	 * @param constraints constraints
	 * @param joinGraph joinGraph
	 * @param treeDataNode treeDataNode
	 * @return associationInterface
	 */
	private BaseAbstractAttributeInterface getBaseAbstractAttributeInterface(
			IConstraints constraints, JoinGraph joinGraph,
			OutputTreeDataNode treeDataNode)
	{
		BaseAbstractAttributeInterface associationInterface = null;
		for(IExpression expression: constraints)
		{
			if(joinGraph.containsAssociation(expression, constraints.getExpression
					(treeDataNode.getExpressionId())))
			{
				IIntraModelAssociation association = (IIntraModelAssociation)
				joinGraph.getAssociation(expression, constraints.getExpression
						(treeDataNode.getExpressionId()));
				if(association != null)
				{
					associationInterface = (BaseAbstractAttributeInterface)
					association.getDynamicExtensionsAssociation();
					break;
				}
			}
		}
		return associationInterface;
	}

	/**
	 * This method populates the list for normal view.
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @param dataList dataList
	 * @param selectSql selectSql
	 */
	private void populateListForNormalView(List<QueryOutputTreeAttributeMetadata>
					selectedAttributeMetaDataList,List<List<String>> dataList, String selectSql)
	{
		Map<BaseAbstractAttributeInterface,Object> denormalizationMap;
		for(List<String> list : dataList)
		{
			denormalizationMap = new HashMap<BaseAbstractAttributeInterface,Object>();
			populateDenormalizationMap(selectedAttributeMetaDataList,
					selectSql, denormalizationMap, list);
			denormalizationList.add(denormalizationMap);
		}
	}

	/**
	 * This method populates the map (BaseAbstractAttributeInterface->Object), where, in this case,
	 * Object will be actual value of the attribute.
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @param selectSql selectSql
	 * @param denormalizationMap denormalizationMap
	 * @param list list
	 */
	private void populateDenormalizationMap(
			List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList,
			String selectSql,
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			List<String> list)
	{
		for(QueryOutputTreeAttributeMetadata outputTreeAttributeMetadata : selectedAttributeMetaDataList)
		{
			String columnName = outputTreeAttributeMetadata.getColumnName();
			BaseAbstractAttributeInterface attribute = outputTreeAttributeMetadata.getAttribute();
			Map<String,String> columnNameMap = getColumnNameMap(selectSql,list);
			String value = columnNameMap.get(columnName);
			populateDenormalizationMap(denormalizationMap, attribute, value);
		}
	}

	/**
	 * Forms a columnNameMap (columnName-->Value)
	 * @param selectSql selectSql
	 * @param dataList dataList
	 * @return columnNameMap
	 */
	private Map<String, String> getColumnNameMap(String selectSql, List<String> dataList)
	{
		String modifiedSql;
		if(selectSql.contains(AQConstants.DISTINCT))
		{
			modifiedSql = selectSql.substring(selectSql.indexOf(AQConstants.DISTINCT)+9, selectSql.indexOf(AQConstants.FROM_CLAUSE)-1);
		}
		else
		{
			modifiedSql = selectSql.substring(selectSql.indexOf(AQConstants.SELECT_CLAUSE)+7, selectSql.indexOf(AQConstants.FROM_CLAUSE)-1);
		}
		StringTokenizer tokenizer = new StringTokenizer(modifiedSql, ",");
		Map<String,String> columnNameMap = new HashMap<String, String>();
		int index = 0;
		while(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			columnNameMap.put(token, dataList.get(index++));
		}
		return columnNameMap;
	}

	/**
	 * This method generates theCSV file name where the data is to be exported.
	 * @param rootEntity rootEntity
	 * @return the file name
	 */
	private String generateNewFileName(EntityInterface rootEntity)
	{
		String randomNumber;
		int number = (int) (Math.random() * 100000);
		randomNumber = AQConstants.UNDERSCORE + Integer.toString(number);

		StringBuffer fileName = new StringBuffer();
		String entityName = rootEntity.getName();
		entityName = entityName.substring(entityName.lastIndexOf('.')+1);
		String appName = CommonServiceLocator.getInstance().getAppHome();
		fileName.append(appName).append(System.getProperty("file.separator"));
		fileName.append(entityName).append(randomNumber).append(".csv");
		return fileName.toString();
	}
}
