package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.util.global.DEConstants.AssociationType;
import edu.wustl.common.query.queryobject.impl.RecordProcessor.TreeCell;
import edu.wustl.common.querysuite.queryobject.IExpression;

public class AssociationDataHandler
{
	/**
	 * This method will update the Maps needed for generating the Header
	 * & data list.
	 * @param denormalizedLst denormalizationMap
	 * @param rootExp rootExp
	 * @param dataHandler dataHandler
	 * @param mapIndex mapIndex
	 */
	public Table<TreeCell> updateRowDataList(final List<Map<OutputAssociationColumn,Object>> denormalizedLst, final IExpression rootExp, QueryExportDataHandler dataHandler)
	{
		final QueryHeaderData queryDataEntity = new QueryHeaderData(rootExp.getQueryEntity().getDynamicExtensionsEntity(), rootExp);
		dataHandler.entityVsMaxCount.put(queryDataEntity, 1);
		final List<RecordProcessorNode<TreeCell>> nodes =
			generateQueryDatamap(denormalizedLst, queryDataEntity, null, dataHandler);
		final RowAppenderProcNode<TreeCell> root = new RowAppenderProcNode<TreeCell>();
        root.addChildren(nodes);
        return root.getTable(TreeCell.EMPTY_CELL);
	}

	/**
	 * This method will update the maps required for exporting the data
	 * (i.e. the data to be shown in the spreadsheet).
	 * @param denormalizedList denormalizedList
	 * @param queryDataEntity queryDataCont
	 * @param dataHandler dataHandler
	 * @param entityVsDataList entityVsDataList
	 * @param dataCnt dataCnt
	 */
	private List<RecordProcessorNode<TreeCell>> generateQueryDatamap(
			final List<Map<OutputAssociationColumn, Object>> denormalizedList,
			final QueryHeaderData queryDataEntity, final TreeCell parent, QueryExportDataHandler dataHandler)
	{
        final List<RecordProcessorNode<TreeCell>> res = new ArrayList<RecordProcessorNode<TreeCell>>();
        for (Map<OutputAssociationColumn, Object> rec : denormalizedList)
        {
        	final TreeCell cell = new TreeCell(queryDataEntity, rec);
            cell.setParentCell(parent);
            final RecordProcessorNode<TreeCell> recordProcNode = new RecordProcessorNode<TreeCell>(cell);
            res.add(recordProcNode);

            for (Map.Entry<OutputAssociationColumn, Object> entry : rec.entrySet())
            {
                if (entry.getKey().getAbstractAttr() instanceof AssociationInterface)
                {
                    processAssociations(cell, recordProcNode, entry, dataHandler);
                }
            }
        }
        return res;
	}

	/**
	 * To process the association attribute i.e. retrieves the inner map and
	 * gives call to generateQueryDatamap(...) method.
	 * @param cell cell
	 * @param recordProcNode recordProcNode
	 * @param entry entry
	 * @param dataHandler dataHandler
	 */
	private void processAssociations(final TreeCell cell,
			final RecordProcessorNode<TreeCell> recordProcNode,
			final Map.Entry<OutputAssociationColumn, Object> entry, QueryExportDataHandler dataHandler)
	{
		final AssociationInterface association = (AssociationInterface) entry.getKey().getAbstractAttr();
		AbstractTableProcessorNode<TreeCell> childNode;
		if (association.getAssociationType() == AssociationType.CONTAINTMENT)
		{
		    childNode = new ColumnAppenderProcNode<TreeCell>();
		}
		else
		{
		    childNode = new RowAppenderProcNode<TreeCell>();
		}
		recordProcNode.addChild(childNode);
		final List<Map<OutputAssociationColumn, Object>> childRecs =
			(List<Map<OutputAssociationColumn, Object>>) entry.getValue();
		IExpression expression = entry.getKey().getTgtExpression();
		final QueryHeaderData queryHeaderData = new QueryHeaderData(expression.getQueryEntity().getDynamicExtensionsEntity(),
					expression);
		final List<Map<OutputAssociationColumn, Object>> newChildList =
			(List<Map<OutputAssociationColumn, Object>>)dataHandler.updateTempList(childRecs,expression);
		if(association.getAssociationType() == AssociationType.CONTAINTMENT)
		{
			dataHandler.updateMaxRecordCount(newChildList, queryHeaderData);
		}
		else
		{
			dataHandler.entityVsMaxCount.put(queryHeaderData, 1);
		}
		if (!newChildList.isEmpty())
		{
			childNode.addChildren(generateQueryDatamap(newChildList, queryHeaderData, cell, dataHandler));
		}
	}

	/**
     * Traverse through the table and populate the list(List<List<Object>>)
     * which contains the final result to be shown on the spreadsheet.
     * @param table table
	 * @param dataHandler dataHandler
     * @return entityDataList
     */
    public List<List<Object>> getEntityDataList(final Table<TreeCell> table, QueryExportDataHandler dataHandler)
	{
    	final List<List<Object>> entityDataList = new ArrayList<List<Object>>();
    	Map<QueryHeaderData,List<TreeCell>> treeCellMap;
    	List<Object> rowDataList;
        for (int i = 0; i < table.numRows(); i++)
        {
        	treeCellMap = new HashMap<QueryHeaderData, List<TreeCell>>();
            for (int j = 0; j < table.numColumns(); j++)
            {
            	TreeCell cell = table.get(i,j);
            	if(cell != TreeCell.EMPTY_CELL)
            	{
            		List<TreeCell>treeCellList;
            		if(treeCellMap.get(cell.getQueryHeaderData()) == null)
            		{
            			treeCellList = new ArrayList<TreeCell>();
            			treeCellList.add(cell);
            			treeCellMap.put(cell.getQueryHeaderData(), treeCellList);
            		}
            		else
            		{
            			treeCellList = treeCellMap.get(cell.getQueryHeaderData());
            			treeCellList.add(cell);
            		}
            	}
            }
            rowDataList = populateRowData(treeCellMap, dataHandler);
            entityDataList.add(rowDataList);
        }
        return entityDataList;
    }

    /**
     * Populate each row of the table with data from the map.
     * @param treeCellMap treeCellMap
     * @param dataHandler dataHandler
     * @return rowDataList
     */
    private List<Object> populateRowData(Map<QueryHeaderData,List<TreeCell>> treeCellMap,
    		QueryExportDataHandler dataHandler)
    {
    	List<Object> rowDataList = new ArrayList<Object>();
    	Map<OutputAssociationColumn, Object> record;
    	for(QueryHeaderData queryHeaderData : treeCellMap.keySet())
    	{
    		List<TreeCell> cellList = treeCellMap.get(queryHeaderData);
    		int listSize = cellList.size();
    		int maxRecordCnt = dataHandler.entityVsMaxCount.get(queryHeaderData);
    		for(TreeCell cell : cellList)
    		{
    			record = cell.getRec();
    	        for (OutputAssociationColumn key : record.keySet())
    	    	{
    	        	if(key.getAbstractAttr() instanceof AttributeInterface)
    	        	{
    	        		final OutputAttributeColumn opAttributeColumn =
    	        			(OutputAttributeColumn)record.get(key);
    	        		rowDataList.add(opAttributeColumn);
    	        	}
    	    	}
    		}
    		while(listSize<maxRecordCnt)
    		{
    			record = cellList.get(0).getRec();
    			for (OutputAssociationColumn key : record.keySet())
            	{
                	if(key.getAbstractAttr() instanceof AttributeInterface)
                	{
                		final OutputAttributeColumn prevOpAttrCol =
                			(OutputAttributeColumn)record.get(key);
                		OutputAttributeColumn opAttributeColumn = new OutputAttributeColumn
                		("", prevOpAttrCol.getColumnIndex(),prevOpAttrCol.getAttribute(),
                				prevOpAttrCol.getExpression(), null);
                		rowDataList.add(opAttributeColumn);
                	}
            	}
    			listSize++;
    		}
    	}
    	return rowDataList;
    }
}
