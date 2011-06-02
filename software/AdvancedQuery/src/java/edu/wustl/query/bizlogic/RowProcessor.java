package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import edu.wustl.common.query.queryobject.impl.OutputAssociationColumn;
import edu.wustl.common.query.queryobject.impl.OutputAttributeColumn;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.QueryExportDataHandler;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.impl.JoinGraph;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryDetails;

public class RowProcessor
{
	private Map<IExpression, ExpressionRecords> expMap;
	private SelectedColumnsMetadata selectColMetadata;
	private QueryDetails queryDetailsObj;
	private String sql;
	private final Map<IExpression,BaseAbstractAttributeInterface> expVsAssoc =
		new HashMap<IExpression, BaseAbstractAttributeInterface>();
	private final Map<IExpression,BaseAbstractAttributeInterface> tgtExpVsAssoc =
		new HashMap<IExpression, BaseAbstractAttributeInterface>();
	private Map<String,String> columnNameMap;

	/**
	 * @return the columnNameMap
	 */
	public Map<String, String> getColumnNameMap()
	{
		return columnNameMap;
	}

	/**
	 * @param columnNameMap the columnNameMap to set
	 */
	public void setColumnNameMap(Map<String, String> columnNameMap)
	{
		this.columnNameMap = columnNameMap;
	}

	/**
	 * @return the expVsAssoc
	 */
	public Map<IExpression, BaseAbstractAttributeInterface> getExpVsAssoc()
	{
		return expVsAssoc;
	}

	/**
	 * @return the tgtExpVsAssoc
	 */
	public Map<IExpression, BaseAbstractAttributeInterface> getTgtExpVsAssoc()
	{
		return tgtExpVsAssoc;
	}

	private void init(String sql,
			SelectedColumnsMetadata selectColMetadata, QueryDetails queryDetailsObj)
	{
		this.selectColMetadata = selectColMetadata;
		this.queryDetailsObj = queryDetailsObj;
		expMap = new HashMap<IExpression, ExpressionRecords>();
		this.sql = sql;
	}

	public List<Map<OutputAssociationColumn,Object>> populateData(List<List<String>> dataList,
			String sql,
			SelectedColumnsMetadata selectColMetadata, QueryDetails queryDetailsObj) throws MultipleRootsException
	{
		init(sql, selectColMetadata, queryDetailsObj);

		initializeExpMap(queryDetailsObj.getQuery().getConstraints());
		IExpression rootExp = queryDetailsObj.getQuery().getConstraints().getRootExpression();

		for(List<String>list : dataList)
		{
			setColumnNameMap(getColumnNameMap(sql,list));
			populateIdRecordMap(rootExp, list);
		}

		return new ArrayList<Map<OutputAssociationColumn,Object>>(expMap.get(rootExp).getExpRecs().values());
	}

	private void initializeExpMap(IConstraints constraints)
	{
		for(IExpression expression : constraints)
		{
			if(expression.isInView())
			{
				expMap.put(expression, new ExpressionRecords());
			}
		}

	}

	/**
	 * Populates the recordMap with key=id(value of identifier of each node in query)
	 * and value=object(object can be list, map or a string value)
	 * @param rootExp Root Expression
	 * @param list list of records
	 * @throws MultipleRootsException MultipleRootsException
	 */
	private void populateIdRecordMap(
			IExpression rootExp,
			List<String> list)
			throws MultipleRootsException
	{
		Integer idIndex = idIndex(rootExp);
		if(idIndex < list.size() && idIndex != -1)
		{
			String idValue = list.get(idIndex);
			Map<OutputAssociationColumn,Object> recMap = recMap(rootExp, idValue);
			List<AbstractAttributeInterface> attributeList = attrList(rootExp);
			for(AbstractAttributeInterface attribute : attributeList)
			{
				if(attribute instanceof AssociationInterface)
				{
					List<IExpression> associatedExpList = getAssociatedExpressions(attribute,rootExp,queryDetailsObj.getQuery().getConstraints());

					for(IExpression associatedExp : associatedExpList)
					{
							OutputAssociationColumn opAssocCol = new OutputAssociationColumn(attribute, rootExp, associatedExp);
							populateIdRecordMap(associatedExp, list);

							List<Map<OutputAssociationColumn,Object>> childList = (List<Map<OutputAssociationColumn, Object>>) recMap.get(opAssocCol);
							if (childList == null)
							{
								childList = new ArrayList<Map<OutputAssociationColumn,Object>>();
								recMap.put(opAssocCol, childList);
							}
							Integer childIdIndex = idIndex(associatedExp);
							if(childIdIndex < list.size())
							{
								String childId = list.get(idIndex(associatedExp));
								if(!childList.contains(expMap.get(associatedExp).getMap(childId)))
								{
									childList.add(expMap.get(associatedExp).getMap(childId));
								}
							}
					   }

				}
				else if(attribute instanceof AttributeInterface)
				{
					// isPresent should take AttributeInterface as input
					OutputAttributeColumn val = getValueForAttribute((AttributeInterface)attribute, selectColMetadata.getSelectedAttributeMetaDataList(), rootExp);
					if (val != null)
					{
						OutputAssociationColumn opAssocCol = new OutputAssociationColumn(attribute, rootExp, null);
						recMap.put(opAssocCol, val);
					}
				}
			}
		}
	}

	/**
	 * Returns the index of the identifier of the passed expression.
	 * @param rootExp expression
	 * @return rootIndex Index of identifier of the passed expression.
	 */
	private Integer idIndex(
			IExpression rootExp)
	{
		Integer rootIndex = -1;
		Map<EntityInterface, Integer> entityIdIndexMap = new HashMap<EntityInterface, Integer>();
		List<String> selectSqlColLst = getColumnList(sql);
		Iterator<OutputTreeDataNode> iterator = queryDetailsObj.getUniqueIdNodesMap().values()
		.iterator();
		while (iterator.hasNext())
		{
			OutputTreeDataNode opTreeDataNode = iterator.next();
			if(opTreeDataNode.getExpressionId() == rootExp.getExpressionId())
			{
				List<QueryOutputTreeAttributeMetadata> attributes = opTreeDataNode.getAttributes();
				for (QueryOutputTreeAttributeMetadata attributeMetaData : attributes)
				{
					AttributeInterface attribute = attributeMetaData.getAttribute();
					String sqlColumnName = attributeMetaData.getColumnName().trim();
					if (attribute.getName().equals(AQConstants.IDENTIFIER))
					{
						int index = selectSqlColLst.indexOf(sqlColumnName);

						if (index >= 0)
						{
							if(rootExp.getQueryEntity().getDynamicExtensionsEntity() == attribute.getEntity())
							{
								rootIndex = index;
							}
							entityIdIndexMap.put(attribute.getEntity(), index);
							break;
						}
						else
						{
							entityIdIndexMap.put(attribute.getEntity(), selectSqlColLst.size());
							rootIndex = selectSqlColLst.size();
							selectSqlColLst.add(sqlColumnName);
							break;
						}
					}
				}
			}
		}
		return rootIndex;
	}

	/**
	 * Populates the list containing the list of column names from the define view.
	 * @param selectSql SQL query
	 * @return selectSqlColumnList
	 */
	private List<String> getColumnList(String selectSql)
	{
		List<String> selectSqlColLst = new ArrayList<String>();
		String substring = selectSql.substring(selectSql.indexOf("select")+6, selectSql.indexOf("from"));
		StringTokenizer tokenizer = new StringTokenizer(substring,",");
		while(tokenizer.hasMoreTokens())
		{
			selectSqlColLst.add(tokenizer.nextToken().trim());
		}
		return selectSqlColLst;
	}

	/**
	 * Retrieves the record map from the expression map corresponding to the passed identifier.
	 * @param rootExp expression
	 * @param idValue identifier
	 * @return record map
	 */
	private Map<OutputAssociationColumn,Object> recMap(IExpression rootExp, String idValue)
	{
		ExpressionRecords exprRecs =  expMap.get(rootExp);
		expMap.put(rootExp, exprRecs);
		return exprRecs.getMap(idValue);
	}

	/**
	 * Retrieves the attribute list(attributes + associations) for the passed expression.
	 * @param rootExp expression
	 * @return attributeList
	 */
	private List<AbstractAttributeInterface> attrList(IExpression rootExp) {
		QueryExportDataHandler dataHandler = new QueryExportDataHandler(null,null);
		List<AbstractAttributeInterface> attributeList = dataHandler.getFinalAttributeList(rootExp.getQueryEntity().getDynamicExtensionsEntity());
		Collections.sort(attributeList, new AttributeComparator());
		return attributeList;
	}

	/**
	 * Checks if the association between the given expressions is present in the graph.
	 * @param attribute attribute
	 * @param treeDataNode treeDataNode
	 * @param constraints constraints
	 * @return entity
	 * @throws MultipleRootsException MultipleRootsException
	 */
	public List<IExpression> getAssociatedExpressions(
			AbstractAttributeInterface attribute, IExpression entityExpression,
			IConstraints constraints) throws MultipleRootsException
	{
		List<IExpression> finalExpList = new ArrayList<IExpression>();
		JoinGraph joinGraph = (JoinGraph)constraints.getJoinGraph();
		BaseAbstractAttributeInterface assocInterface;
		IExpression finalExp;
		for(IExpression expression: constraints)
		{
			if(joinGraph.containsAssociation(expression, entityExpression))
			{
				IIntraModelAssociation association = (IIntraModelAssociation)
				joinGraph.getAssociation(expression, entityExpression);
				if(association != null)
				{
					assocInterface = (BaseAbstractAttributeInterface)
					association.getDynamicExtensionsAssociation();
					if(assocInterface.equals(attribute))
					{
						tgtExpVsAssoc.put(entityExpression, assocInterface);
						finalExp = populateEntityVsAssoc(joinGraph,expression,entityExpression,
								assocInterface);
						//finalExpList.add(finalExp);
					}
				}
			}
			else if(joinGraph.containsAssociation(entityExpression,expression))
			{
				IIntraModelAssociation association = (IIntraModelAssociation)
				joinGraph.getAssociation(entityExpression,expression);
				if(association != null)
				{
					assocInterface = (BaseAbstractAttributeInterface)
					association.getDynamicExtensionsAssociation();
					if(assocInterface.equals(attribute))
					{
						expVsAssoc.put(entityExpression, assocInterface);
						finalExp = populateTgtEntityVsAssoc(joinGraph,expression,assocInterface);
						finalExpList.add(finalExp);
					}
				}
			}
		}
		return finalExpList;
	}

	/**
	 * Populate tgtEntityVsAssoc map, where key-> the target entity of the passed association
	 * which is present in the DAG and value->Association.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @param entityExp dynamicExtensionsEntity
	 * @param assocInterface associationInterface
	 * @return entity
	 */
	private IExpression populateTgtEntityVsAssoc(JoinGraph joinGraph,
			IExpression expression,
			BaseAbstractAttributeInterface assocInterface)
	{
		IExpression finalExp = null;
		if(!joinGraph.getChildrenList(expression).isEmpty() && !expression.isInView())
		{
			IExpression exp = joinGraph.getChildrenList(expression).get(0);
			exp = getExpression(joinGraph, exp);
			if(exp.isInView())
			{
				populateTgtVsAssocMap(assocInterface, exp);
				finalExp = exp;
			}
		}
		else
		{
			tgtExpVsAssoc.put(expression, assocInterface);
			finalExp = expression;
		}
		return finalExp;
	}

	/**
	 * Put the appropriate association in tgtEntityVsAssoc map.
	 * @param entityExpr dynamicExtensionsEntity
	 * @param assocInterface associationInterface
	 * @param exp expression
	 */
	private void populateTgtVsAssocMap(BaseAbstractAttributeInterface assocInterface, IExpression exp)
	{
		BaseAbstractAttributeInterface association = tgtExpVsAssoc.get(exp);
		if(association == null)
		{
			tgtExpVsAssoc.put(exp, assocInterface);
		}
	}

	/**
	 * Return the corresponding value for the attribute passed, from the columnNameMap.
	 * @param attribute attribute
	 * @param selectAttrMetaDataLst selectedAttributeMetaDataList
	 * @return opAttrCol
	 */
	public OutputAttributeColumn getValueForAttribute(AttributeInterface attribute,
			List<QueryOutputTreeAttributeMetadata> selectAttrMetaDataLst, IExpression expression)
	{
		OutputAttributeColumn opAttrCol = null;
		String value;
		int columnIndex = -1;

		for(QueryOutputTreeAttributeMetadata outputTreeAttributeMetadata : selectAttrMetaDataLst)
		{
			columnIndex++;
			BaseAbstractAttributeInterface presentAttribute = outputTreeAttributeMetadata.getAttribute();
			if(presentAttribute.equals(attribute) && outputTreeAttributeMetadata.getTreeDataNode().getExpressionId() == expression.getExpressionId())
			{
				value = columnNameMap.get(outputTreeAttributeMetadata.getColumnName());
				opAttrCol = new OutputAttributeColumn(value, columnIndex, attribute, expression, null);
				break;
			}
		}
		return opAttrCol;
	}

	/**
	 * Populates entityVsAssoc map where key->the source entity of the given association and
	 * value->Association.
	 * @param joinGraph joinGraph
	 * @param expression expression
	 * @param entityExp
	 * @param assocInterface
	 */
	private IExpression populateEntityVsAssoc(JoinGraph joinGraph, IExpression expression, IExpression entityExp,
			BaseAbstractAttributeInterface assocInterface)
	{
		IExpression finalExp = null;
		if(!joinGraph.getParentList(expression).isEmpty() && !expression.isInView())
		{
			IExpression exp = joinGraph.getParentList(expression).get(0);
			exp = getParentExpression(joinGraph, exp);
			if(exp.isInView())
			{
				populateEntityvsAssocMap(entityExp,
						assocInterface, exp);
				finalExp = exp;
			}
		}
		else
		{
			expVsAssoc.put(expression, assocInterface);
			finalExp = expression;
		}
		return finalExp;
	}

	/**
	 * Put the appropriate association in entityVsAssoc map.
	 * @param entityExp dynamicExtensionsEntity
	 * @param assocInterface associationInterface
	 * @param exp expression
	 */
	private void populateEntityvsAssocMap(
			IExpression entityExp,
			BaseAbstractAttributeInterface assocInterface, IExpression exp)
	{
		BaseAbstractAttributeInterface association = expVsAssoc.get(entityExp);
		if(association == null)
		{
			expVsAssoc.put(exp, assocInterface);
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
	public Map<String, String> getColumnNameMap(String selectSql, List<String> dataList)
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
}
