package edu.wustl.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.domaininterface.AbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.BaseAbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.wustl.common.query.queryobject.impl.AttributeComparator;
import edu.wustl.common.query.queryobject.impl.DenormalizedCSVExporter;
import edu.wustl.common.query.queryobject.impl.ListComparator;
import edu.wustl.common.query.queryobject.impl.OutputAttributeColumn;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.QueryExportDataHandler;
import edu.wustl.common.query.queryobject.impl.QueryParser;
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
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryDetails;

public class SpreadsheetDenormalizationBizLogic
{
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(SpreadsheetDenormalizationBizLogic.class);

	private final List<Map<BaseAbstractAttributeInterface,Object>> denormalizationList =
		new ArrayList<Map<BaseAbstractAttributeInterface,Object>>();

	private final Map<EntityInterface,BaseAbstractAttributeInterface> entityVsAssoc =
		new HashMap<EntityInterface, BaseAbstractAttributeInterface>();
	private final Map<EntityInterface,BaseAbstractAttributeInterface> tgtEntityVsAssoc =
		new HashMap<EntityInterface, BaseAbstractAttributeInterface>();
	//private Map<EntityInterface,Map<BaseAbstractAttributeInterface,Object>> entityVsMap;
	private int mainIdColumnIndex = -1;
	private Map<String,String> columnNameMap;
	private int counter = 0;
	//private List<EntityInterface> markedEntities = new ArrayList<EntityInterface>();

	/**
	 * Scans the entire IQuery starting from root node in order to populate the map
	 * required as an input to getDataOutProcessor.
	 * @param queryDetailsObj query details object
	 * @param dataList the list that contains the results of the query
	 * @param selectedColumnsMetadata selectedColumnsMetadata
	 * @param querySessionData querySessionData
	 */
	public Map<String,Object> scanIQuery(QueryDetails queryDetailsObj, List<List<String>> dataList,
			SelectedColumnsMetadata selectedColumnsMetadata, QuerySessionData querySessionData)
	{
		Map<String,Object> exportDetailsMap = new HashMap<String,Object>();
		try
		{
			QueryParser queryParser = new QueryParser();
			queryParser.parseQuery(queryDetailsObj.getQuery(),selectedColumnsMetadata.getSelectedAttributeMetaDataList());
			mainIdColumnIndex = queryParser.
			getMainIdColumnIndex(querySessionData.getQueryResultObjectDataMap());
			if(mainIdColumnIndex != -1)
			{
				String column = new QueryOutputSpreadsheetBizLogic().getColumnName(querySessionData.getSql(), mainIdColumnIndex);
				int totalRecords = getTotalNoOfRecords(queryDetailsObj,column);
				querySessionData.setTotalNumberOfRecords(totalRecords);
				Collections.sort(dataList, new ListComparator(mainIdColumnIndex));

				populateMapForSpreadsheet(queryDetailsObj,
					selectedColumnsMetadata.getSelectedAttributeMetaDataList(),dataList,
					querySessionData.getSql());
				IQuery query = queryDetailsObj.getQuery();
				IConstraints constraints = query.getConstraints();
				JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
				EntityInterface rootEntity = getRootEntity(joinGraph);
				QueryExportDataHandler dataHandler = new QueryExportDataHandler(rootEntity);
				dataHandler.setEntityVsAssoc(entityVsAssoc);
				dataHandler.setTgtEntityVsAssoc(tgtEntityVsAssoc);
				for(int count=0;count<denormalizationList.size();count++)
				{
					dataHandler.updateRowDataList(denormalizationList.get(count),count);
				}
				DenormalizedCSVExporter csvExporter = new DenormalizedCSVExporter();
				exportDetailsMap =
					csvExporter.addDataToCSV(denormalizationList.size(),dataHandler);
			}
		}
		catch (MultipleRootsException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (DAOException e)
		{
			logger.error(e.getMessage(), e);
		}
		return exportDetailsMap;
	}

	/**
	 * Populates the map for define view where key->BaseAbstractAttributeInterface and value->Object,
	 * Object can be actual value of attribute or a map.
	 * @param queryDetailsObj queryDetailsObj
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @param dataList dataList
	 * @param selectSql selectSql
	 */
	private void populateMapForSpreadsheet(QueryDetails queryDetailsObj,
			List<QueryOutputTreeAttributeMetadata>selectedAttributeMetaDataList,
			List<List<String>> dataList, String selectSql) throws MultipleRootsException
	{
		IQuery query = queryDetailsObj.getQuery();
		IConstraints constraints = query.getConstraints();
		JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
		EntityInterface rootEntity = getRootEntity(joinGraph);
		Map<BaseAbstractAttributeInterface,Object> denormalizationMap =
			new HashMap<BaseAbstractAttributeInterface,Object>();
		Integer idColumnValue = -100;
		for(List<String> list : dataList)
		{
			//entityVsMap = new HashMap<EntityInterface, Map<BaseAbstractAttributeInterface,Object>>();
			denormalizationMap = populateDenormalizationList(denormalizationMap, idColumnValue, list);
			idColumnValue = Integer.parseInt(list.get(mainIdColumnIndex));
			columnNameMap = getColumnNameMap(selectSql,list);
			populateMap(denormalizationMap,rootEntity, selectedAttributeMetaDataList, constraints);
		}
		denormalizationList.add(denormalizationMap);
	}

	/**
	 * @param denormalizationMap2 denormalizationMap2
	 * @param entity entity
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @param constraints constraints
	 * @return
	 * @throws MultipleRootsException
	 */
	public Map<BaseAbstractAttributeInterface,Object> populateMap(Map<BaseAbstractAttributeInterface, Object> denormalizationMap2, EntityInterface entity,
			List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList,IConstraints constraints) throws MultipleRootsException
	{
		counter++;
		Map<BaseAbstractAttributeInterface,Object> denormalizationMap =
			new HashMap<BaseAbstractAttributeInterface, Object>();
		List<Map<BaseAbstractAttributeInterface,Object>> innerMapList = new ArrayList<Map<BaseAbstractAttributeInterface,Object>>();
		OutputTreeDataNode treeDataNode = getTreeDataNode(entity,selectedAttributeMetaDataList);
		QueryExportDataHandler dataHandler = new QueryExportDataHandler(null);
		List<AbstractAttributeInterface> attributeList = dataHandler.getFinalAttributeList(entity);
		Collections.sort(attributeList, new AttributeComparator());
		for(AbstractAttributeInterface attribute : attributeList)
		{
			if(attribute instanceof AssociationInterface)
			{
				EntityInterface associatedEntity = isAssociationPresent(attribute,treeDataNode,constraints,entity);
				if(associatedEntity != null)
				{
					Map<BaseAbstractAttributeInterface,Object> tempMap = new HashMap<BaseAbstractAttributeInterface, Object>();
					if(denormalizationMap2.get(attribute) != null)
					{
						tempMap = ((List<Map<BaseAbstractAttributeInterface,Object>>)denormalizationMap2.get(attribute)).get(0);
					}
					Map<BaseAbstractAttributeInterface,Object> innerMap =
						populateMap(tempMap,associatedEntity,selectedAttributeMetaDataList,constraints);
					if(!innerMap.isEmpty())
					{
						innerMapList = new ArrayList<Map<BaseAbstractAttributeInterface,Object>>();
						innerMapList.add(innerMap);
						if(denormalizationMap2.get(attribute) == null)
						{
							denormalizationMap.put(attribute,innerMapList);
							denormalizationMap2.put(attribute, innerMapList);
						}
						else
						{
							populateInnerList(denormalizationMap2, attribute,
									tempMap, innerMap);
						}
					}
				}
			}
			else
			{
				populateMapForAttribute(denormalizationMap2,
						selectedAttributeMetaDataList, denormalizationMap,
						attribute);
			}
		}
		return denormalizationMap;
	}

	/**
	 * Populate the inner list.
	 * @param denormalizationMap2 denormalizationMap2
	 * @param attribute attribute
	 * @param tempMap tempMap
	 * @param innerMap innerMap
	 */
	private void populateInnerList(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap2,
			AbstractAttributeInterface attribute,
			Map<BaseAbstractAttributeInterface, Object> tempMap,
			Map<BaseAbstractAttributeInterface, Object> innerMap)
	{
		List<Map<BaseAbstractAttributeInterface,Object>> innerList =
			(List<Map<BaseAbstractAttributeInterface, Object>>) denormalizationMap2.get(attribute);
		if(!tempMap.equals(innerMap) && !innerList.contains(innerMap))
		{
			boolean isPresent = checkIfPresent(innerMap, innerList);
			if(!isPresent)
			{
				innerList.add(innerMap);
			}
		}
	}

	/**
	 * Checks if the attribute is already present in the map.
	 * @param innerMap innerMap
	 * @param innerList innerList
	 * @return isPresent
	 */
	private boolean checkIfPresent(
			Map<BaseAbstractAttributeInterface, Object> innerMap,
			List<Map<BaseAbstractAttributeInterface, Object>> innerList)
	{
		boolean isPresent = false;
		for(Map<BaseAbstractAttributeInterface,Object> inMap : innerList)
		{
			for(BaseAbstractAttributeInterface key : inMap.keySet())
			{
				isPresent = isPresent(innerMap,inMap, key);
				if(isPresent)
				{
					break;
				}
			}
			if(isPresent)
			{
				break;
			}
		}
		return isPresent;
	}

	/**
	 * Checks if the attribute is already present in the map.
	 * @param innerMap innerMap
	 * @param inMap inMap
	 * @param key key
	 * @return isPresent
	 */
	private boolean isPresent(
			Map<BaseAbstractAttributeInterface, Object> innerMap,
			Map<BaseAbstractAttributeInterface, Object> inMap,
			BaseAbstractAttributeInterface key)
	{
		boolean isPresent = false;
		if(key instanceof AttributeInterface)
		{
			for(BaseAbstractAttributeInterface inKey :innerMap.keySet())
			{
				if(inKey == key && inKey.getName().equals("id") &&
						inMap.get(key).equals(innerMap.get(inKey)))
				{
					isPresent = true;
					break;
				}
			}
		}
		return isPresent;
	}

	/**
	 * @param denormalizationMap2 denormalizationMap2
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @param denormalizationMap denormalizationMap
	 * @param attribute attribute
	 */
	private void populateMapForAttribute(
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap2,
			List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList,
			Map<BaseAbstractAttributeInterface, Object> denormalizationMap,
			AbstractAttributeInterface attribute)
	{
		OutputAttributeColumn value = isPresent(attribute,selectedAttributeMetaDataList);
		if(value != null)
		{
			denormalizationMap.put(attribute, value);
			if(counter == 1)
			{
				denormalizationMap2.put(attribute, value);
			}
		}
	}

	private OutputTreeDataNode getTreeDataNode(EntityInterface entity,
			List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList)
	{
		OutputTreeDataNode treeDataNode = null;
		for(QueryOutputTreeAttributeMetadata outputTreeAttributeMetadata : selectedAttributeMetaDataList)
		{
			if(outputTreeAttributeMetadata.getTreeDataNode().getOutputEntity().getDynamicExtensionsEntity() == entity)
			{
				treeDataNode = outputTreeAttributeMetadata.getTreeDataNode();
				break;
			}
		}
		return treeDataNode;
	}

	/**
	 * Checks if the association between the given expressions is present in the graph.
	 * @param attribute attribute
	 * @param treeDataNode treeDataNode
	 * @param constraints constraints
	 * @return entity
	 * @throws MultipleRootsException MultipleRootsException
	 */
	private EntityInterface isAssociationPresent(
			AbstractAttributeInterface attribute, OutputTreeDataNode treeDataNode,
			IConstraints constraints,EntityInterface currentEntity) throws MultipleRootsException
	{
		JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
		int entityExpressionId;
		EntityInterface dataNodeEntity;
		if(treeDataNode == null)
		{
			entityExpressionId  = getEntityExpId(constraints,currentEntity);
			dataNodeEntity = currentEntity;
		}
		else
		{
			entityExpressionId = treeDataNode.getExpressionId();
			dataNodeEntity = treeDataNode.getOutputEntity().getDynamicExtensionsEntity();
		}
		BaseAbstractAttributeInterface associationInterface;
		EntityInterface entity = null;
		for(IExpression expression: constraints)
		{
			if(joinGraph.containsAssociation(expression, constraints.getExpression
					(entityExpressionId)))
			{
				IIntraModelAssociation association = (IIntraModelAssociation)
				joinGraph.getAssociation(expression, constraints.getExpression
						(entityExpressionId));
				if(association != null)
				{
					associationInterface = (BaseAbstractAttributeInterface)
					association.getDynamicExtensionsAssociation();
					if(associationInterface.equals(attribute))
					{
						tgtEntityVsAssoc.put(dataNodeEntity, associationInterface);
						entity = populateEntityVsAssoc(joinGraph,expression,dataNodeEntity,associationInterface);
						break;
					}
				}

			}
			else if(joinGraph.containsAssociation(constraints.getExpression
					(entityExpressionId),expression))
			{
				IIntraModelAssociation association = (IIntraModelAssociation)
				joinGraph.getAssociation(constraints.getExpression
						(entityExpressionId),expression);
				if(association != null)
				{
					associationInterface = (BaseAbstractAttributeInterface)
					association.getDynamicExtensionsAssociation();
					if(associationInterface.equals(attribute))
					{
						entityVsAssoc.put(dataNodeEntity, associationInterface);
						entity = populateTgtEntityVsAssoc(joinGraph,expression,dataNodeEntity,associationInterface);
					}
				}
			}
		}
		return entity;
	}

	/**
	 * Get the expression id of the given entity.
	 * @param constraints constraints
	 * @param currentEntity currentEntity
	 * @return expressionId
	 */
	private int getEntityExpId(IConstraints constraints,
			EntityInterface currentEntity)
	{
		int expressionId = 0;
		for(IExpression expression : constraints)
		{
			if(expression.getQueryEntity().getDynamicExtensionsEntity().equals(currentEntity))
			{
				expressionId = expression.getExpressionId();
				break;
			}
		}
		return expressionId;
	}

	/**
	 * Populate tgtEntityVsAssoc map, where key-> the target entity of the passed association
	 * which is present in the DAG and value->Association.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @param dynamicExtensionsEntity dynamicExtensionsEntity
	 * @param associationInterface associationInterface
	 * @return entity
	 */
	private EntityInterface populateTgtEntityVsAssoc(JoinGraph joinGraph,
			IExpression expression, EntityInterface dynamicExtensionsEntity,
			BaseAbstractAttributeInterface associationInterface)
	{
		EntityInterface entity = null;
		if(!joinGraph.getChildrenList(expression).isEmpty() && !expression.isInView())
		{
			IExpression exp = joinGraph.getChildrenList(expression).get(0);
			exp = getExpression(joinGraph, exp);
			if(exp.isInView())
			{
				populateTgtVsAssocMap(dynamicExtensionsEntity,
						associationInterface, exp);
				entity = exp.getQueryEntity().getDynamicExtensionsEntity();
			}
		}
		else
		{
			tgtEntityVsAssoc.put(expression.getQueryEntity().getDynamicExtensionsEntity(), associationInterface);
			entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		}
		return entity;
	}

	/**
	 * Put the appropriate association in tgtEntityVsAssoc map.
	 * @param dynamicExtensionsEntity dynamicExtensionsEntity
	 * @param associationInterface associationInterface
	 * @param exp expression
	 */
	private void populateTgtVsAssocMap(EntityInterface dynamicExtensionsEntity,
			BaseAbstractAttributeInterface associationInterface, IExpression exp)
	{
		BaseAbstractAttributeInterface association = tgtEntityVsAssoc.get(exp.getQueryEntity().getDynamicExtensionsEntity());
		if(association == null)
		{
			tgtEntityVsAssoc.put(exp.getQueryEntity().getDynamicExtensionsEntity(), associationInterface);
		}
	}

	/**
	 * Return the corresponding value for the attribute passed, from the columnNameMap.
	 * @param attribute attribute
	 * @param selectedAttributeMetaDataList selectedAttributeMetaDataList
	 * @return opAttrCol
	 */
	private OutputAttributeColumn isPresent(AbstractAttributeInterface attribute,
			List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList)
	{
		OutputAttributeColumn opAttrCol = null;
		String value = null;
		int columnIndex = -1;
		for(QueryOutputTreeAttributeMetadata outputTreeAttributeMetadata : selectedAttributeMetaDataList)
		{
			columnIndex++;
			BaseAbstractAttributeInterface presentAttribute = outputTreeAttributeMetadata.getAttribute();
			if(presentAttribute.equals(attribute))
			{
				value = columnNameMap.get(outputTreeAttributeMetadata.getColumnName());
				opAttrCol = new OutputAttributeColumn(value, columnIndex, (AttributeInterface)attribute, null);
				break;
			}
		}
		return opAttrCol;
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
			counter = 0;
			//markedEntities = new ArrayList<EntityInterface>();
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
	 * Populates entityVsAssoc map where key->the source entity of the given association and
	 * value->Association.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @param dynamicExtensionsEntity
	 * @param associationInterface
	 */
	private EntityInterface populateEntityVsAssoc(JoinGraph joinGraph, IExpression expression, EntityInterface dynamicExtensionsEntity,
			BaseAbstractAttributeInterface associationInterface)
	{
		EntityInterface entity = null;
		if(!joinGraph.getParentList(expression).isEmpty() && !expression.isInView())
		{
			IExpression exp = joinGraph.getParentList(expression).get(0);
			exp = getParentExpression(joinGraph, exp);
			if(exp.isInView())
			{
				populateEntityvsAssocMap(dynamicExtensionsEntity,
						associationInterface, exp);
				entity = exp.getQueryEntity().getDynamicExtensionsEntity();
			}
		}
		else
		{
			entityVsAssoc.put(expression.getQueryEntity().getDynamicExtensionsEntity(), associationInterface);
			entity = expression.getQueryEntity().getDynamicExtensionsEntity();
		}
		return entity;
	}

	/**
	 * Put the appropriate association in entityVsAssoc map.
	 * @param dynamicExtensionsEntity dynamicExtensionsEntity
	 * @param associationInterface associationInterface
	 * @param exp expression
	 */
	private void populateEntityvsAssocMap(
			EntityInterface dynamicExtensionsEntity,
			BaseAbstractAttributeInterface associationInterface, IExpression exp)
	{
		BaseAbstractAttributeInterface association = entityVsAssoc.get(dynamicExtensionsEntity);
		if(association == null)
		{
			entityVsAssoc.put(exp.getQueryEntity().getDynamicExtensionsEntity(), associationInterface);
		}
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
	 * Get the child expression of the passed expression.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @return finalExp
	 */
	private IExpression getExpression(JoinGraph joinGraph,IExpression expression)
	{
		IExpression finalExp = expression;
		if(!expression.isInView() && !joinGraph.getChildrenList(expression).isEmpty())
		{
			IExpression parentExp = joinGraph.getChildrenList(expression).get(0);
			finalExp = getExpression(joinGraph,parentExp);
		}
		return finalExp;
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
			String data = dataList.get(index);
			if(data.length() == 0)
			{
				data = " ";
			}
			columnNameMap.put(token.trim(), data);
			index++;
		}
		return columnNameMap;
	}

	/**
	 * Get the total number of records.
	 * @param queryDetailsObj queryDetailsObj
	 * @param column column
	 * @return totalNoOfRecords
	 * @throws DAOException
	 */
	private int getTotalNoOfRecords(QueryDetails queryDetailsObj,
			String column) throws DAOException
	{
		int totalNoOfRecords = 0;
		ResultSet resultSet = null;
		try
		{
			StringBuffer sql = new StringBuffer(80);
			String tableName = AQConstants.TEMP_OUPUT_TREE_TABLE_NAME
			+ queryDetailsObj.getSessionData().getUserId() + queryDetailsObj.getRandomNumber();
			sql.append("SELECT count(distinct ").append(column).append(") FROM ").append(tableName);
			String appName=CommonServiceLocator.getInstance().getAppName();
			IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
			JDBCDAO jdbcDao = daoFactory.getJDBCDAO();
			jdbcDao.openSession(null);
			resultSet = jdbcDao.getQueryResultSet(sql.toString());
			if(resultSet.next())
			{
				totalNoOfRecords = resultSet.getInt(1);
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			if(resultSet != null)
			{
				try
				{
					resultSet.close();
				}
				catch (SQLException e)
				{
					logger.error(e.getMessage(), e);
				}
			}
		}
		return totalNoOfRecords;
	}
}
