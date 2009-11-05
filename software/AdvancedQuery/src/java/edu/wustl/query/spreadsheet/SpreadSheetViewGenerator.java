
package edu.wustl.query.spreadsheet;

import java.util.ArrayList;
import java.util.List;

import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.Utility;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryDetails;
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

	private org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(SpreadSheetViewGenerator.class);

	protected String dataTypeString;
	protected String idOfClickedNode;
	protected QueryDetails queryDetails;
	protected SelectedColumnsMetadata selectedColumnsMetadata;
	protected ViewType viewType;

	
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
	public IQuery createSpreadsheet(NodeId node, QueryDetails queryDetailsObj,
			SpreadSheetData spreadsheetData,AbstractQuery abstractQuery)
			throws QueryModuleException
	{
		ViewManager viewManager = ViewManager.getInstance(viewType);
		
		AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
				.getDefaultViewIQueryGenerator();
		IQuery query = queryGenerator.createQueryForSpreadSheetView(node, queryDetailsObj);

		List<IOutputAttribute> selectedColumns = viewManager.getSelectedColumnList(query);
		List<String> columnsList = getColumnList(selectedColumns,abstractQuery);

		spreadsheetData.setColumnsList(columnsList);
		
		return query;
	}

	/**
	 * @param outputAttributeList
	 * @return
	 * @throws QueryModuleException 
	 */
	protected List<String> getColumnList(List<IOutputAttribute> outputAttributeList,AbstractQuery abstractQuery)
			throws QueryModuleException
	{
		List<String> columnsList = new ArrayList<String>();
		for (IOutputAttribute outputAttribute : outputAttributeList)
		{
			if(!(outputAttribute.getAttribute().getName().equals(Constants.ID)
					&& outputAttribute.getAttribute().getActualEntity().getName().equals(Constants.MED_ENTITY_NAME)))
			{
				String className = outputAttribute.getExpression().getQueryEntity()
						.getDynamicExtensionsEntity().getName();
				String attrLabel = Utility.getDisplayLabel(outputAttribute.getAttribute().getName());
				columnsList.add(attrLabel + " : " + Utility.getDisplayLabel(className));
			}
		}
		logger.debug("Output Attribute list size:"+outputAttributeList.size());
	    return columnsList;
	}
}
