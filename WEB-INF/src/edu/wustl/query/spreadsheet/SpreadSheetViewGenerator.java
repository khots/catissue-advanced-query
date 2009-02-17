
package edu.wustl.query.spreadsheet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.wustl.cider.querymanager.CiderQueryManager;
import edu.wustl.cider.util.CiderQueryUIManager;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.OutputTreeNode;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.queryexecutionmanager.DataQueryResultsBean;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;

/**
 * @author vijay_pande
 *
 */
public class SpreadSheetViewGenerator
{

	private org.apache.log4j.Logger logger = Logger.getLogger(SpreadSheetViewGenerator.class);

	protected String dataTypeString;
	protected String idOfClickedNode;
	protected QueryDetails queryDetails;
	protected SelectedColumnsMetadata selectedColumnsMetadata;

	public SpreadSheetData createSpreadSheet()
	{
		return null;
	}

	public OutputTreeNode getClickedNode()
	{
		return null;
	}

	public void updateSpreadSheetForDataNode(SpreadSheetData spreadSheetData)
	{

	}

	public void updateViewOfQuery(ViewType viewType)
	{

	}

	/**
	 * @param queryDetailsObj
	 * @param idOfClickedNode2
	 * @param spreadsheetData
	 * @param request 
	 * @throws QueryModuleException 
	 */
	public void createSpreadsheet(QueryDetails queryDetailsObj, String idOfClickedNode2,
			SpreadSheetData spreadsheetData, HttpServletRequest request)
			throws QueryModuleException
	{
		Node node = new Node(idOfClickedNode2);
		OutputTreeDataNode currentTreeNode = null; //queryDetailsObj.getUniqueIdNodesMap().get(uniqueCurrentNodeId);

		List<IOutputAttribute> selectedColumns = ((IParameterizedQuery) queryDetailsObj.getQuery())
				.getOutputAttributeList(); //getSelectedOutputAttributeList(currentTreeNode,constraints);
		SpreadsheetIQueryGenerator queryGenerator = new SpreadsheetIQueryGenerator();
		queryGenerator.createIQuery(node, queryDetailsObj, selectedColumns);

		executeXQuery(queryDetailsObj.getQuery(), spreadsheetData, request, queryDetailsObj
				.getQueryExecutionId());

		List<String> columnsList = getColumnList(queryDetailsObj);

		spreadsheetData.setColumnsList(columnsList);

	}

	/**
	 * @param queryDetailsObj
	 * @return
	 */
	private List<String> getColumnList(QueryDetails queryDetailsObj)
	{
		List<IOutputAttribute> outputAttributeList = ((ParameterizedQuery) queryDetailsObj
				.getQuery()).getOutputAttributeList();
		List<String> columnsList = new ArrayList<String>();
		for (IOutputAttribute outputAttribute : outputAttributeList)
		{
			String className = outputAttribute.getAttribute().getEntity().getName();
			className = Utility.parseClassName(className);
			String attrLabel = Utility.getDisplayLabel(outputAttribute.getAttribute().getName());
			columnsList.add(attrLabel + " : " + className);
		}
		return columnsList;
	}

	private void executeXQuery(IQuery query, SpreadSheetData spreadsheetData,
			HttpServletRequest request, int queryExecutionId) throws QueryModuleException
	{
		//getData
		AbstractQueryUIManager ciderQueryUIManager = AbstractQueryUIManagerFactory
				.configureDefaultAbstractUIQueryManager(this.getClass(), request, query);

		DataQueryResultsBean dataQueryResultsBean;
		dataQueryResultsBean = ciderQueryUIManager.getData(queryExecutionId);
		spreadsheetData.setDataList(dataQueryResultsBean.getAttributeList());
		spreadsheetData.setDataTypeList(dataQueryResultsBean.getDataTypesList());
	}
}
