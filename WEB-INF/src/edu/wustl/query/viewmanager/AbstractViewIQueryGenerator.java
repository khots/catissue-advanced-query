package edu.wustl.query.viewmanager;

import java.util.List;

import edu.wustl.common.querysuite.queryobject.IQuery;
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
	public abstract List<IQuery> createQueryForSpreadSheetView(NodeId node, QueryDetails queryDetails) throws QueryModuleException;
	/**
	 * @return
	 */
	public abstract IQuery createIQueryForTreeView(QueryDetails queryDetails)throws QueryModuleException;
}
