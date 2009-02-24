package edu.wustl.query.viewmanager;

import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.spreadsheet.Node;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * @author vijay_pande
 *
 */
public abstract class AbstractViewIQueryGenerator
{
	/**
	 * @param node 
	 * @return
	 */
	public abstract IQuery createQueryForSpreadSheetView(Node node, QueryDetails queryDetails) throws QueryModuleException;
	/**
	 * @return
	 */
	public abstract IQuery createIQueryForTreeView(QueryDetails queryDetails)throws QueryModuleException;
}
