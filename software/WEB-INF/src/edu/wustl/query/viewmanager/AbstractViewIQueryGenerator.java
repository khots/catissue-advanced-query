package edu.wustl.query.viewmanager;

import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
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
	public abstract IQuery createQueryForSpreadSheetView(NodeId node, QueryDetails queryDetails) throws QueryModuleException;
	/**
	 * @return
	 */
	public abstract IQuery createIQueryForTreeView(QueryDetails queryDetails,boolean hasSecurePrivilege)throws QueryModuleException;
	
	/**
	 * This method formats the output to be displayed for tree view. 
	 * @param displayData data to be displayed.
	 * @param rootEntity root entity.
	 * @param query abstractQuery.
	 * @return formatted output.
	 */
	public abstract StringBuffer getFormattedOutputForTreeView(StringBuffer displayData,QueryableObjectInterface rootEntity,boolean hasSecurePrivilege);
}
