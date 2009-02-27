
package edu.wustl.query.spreadsheet;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ViewManager;
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
	private ViewType viewType;

	public SpreadSheetViewGenerator(ViewType viewType)
	{
		this.viewType = viewType;
	}
	
	public void updateViewOfQuery(ViewType viewType)
	{
		this.viewType = viewType;
	}

	/**
	 * @param node
	 * @param queryDetailsObj
	 * @param spreadsheetData
	 * @throws QueryModuleException 
	 */
	public void createSpreadsheet(NodeId node, QueryDetails queryDetailsObj,
			SpreadSheetData spreadsheetData)
			throws QueryModuleException
	{
		ViewManager viewManager = ViewManager.getInstance(viewType);
		List<IOutputAttribute> selectedColumns = viewManager.getSelectedColumnList(queryDetailsObj
				.getQuery());
		AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
				.getDefaultViewIQueryGenerator();
		queryGenerator.createQueryForSpreadSheetView(node, queryDetailsObj);

		List<String> columnsList = getColumnList(selectedColumns);

		spreadsheetData.setColumnsList(columnsList);

	}

	/**
	 * @param outputAttributeList
	 * @return
	 * @throws QueryModuleException 
	 */
	private List<String> getColumnList(List<IOutputAttribute> outputAttributeList)
			throws QueryModuleException
	{
		List<String> columnsList = new ArrayList<String>();
		if (outputAttributeList.size() == 0)
		{
			logger.error("No output attribute defined for patient data query");
			throw new QueryModuleException("No output attribute defined for patient data query",
					QueryModuleError.GENERIC_EXCEPTION);
		}
		for (IOutputAttribute outputAttribute : outputAttributeList)
		{
			String className = outputAttribute.getAttribute().getEntity().getName();
			className = Utility.parseClassName(className);
			String attrLabel = Utility.getDisplayLabel(outputAttribute.getAttribute().getName());
			columnsList.add(attrLabel + " : " + className);
		}
		return columnsList;
	}
}
