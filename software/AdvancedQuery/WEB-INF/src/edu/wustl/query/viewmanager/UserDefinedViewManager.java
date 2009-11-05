/**
 * 
 */
package edu.wustl.query.viewmanager;

import java.util.List;

import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * @author vijay_pande
 *
 */
public class UserDefinedViewManager extends ViewManager
{
	public void createSpreadSheetView() throws QueryModuleException
	{
		
	}
	
	public List<IOutputAttribute> getSelectedColumnList(IQuery query) throws QueryModuleException
	{
		return ((ParameterizedQuery)query).getOutputAttributeList();
	}
}
