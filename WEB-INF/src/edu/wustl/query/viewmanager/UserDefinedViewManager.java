/**
 * 
 */
package edu.wustl.query.viewmanager;

import java.util.List;

import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.util.querysuite.QueryModuleError;
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
		List<IOutputAttribute> outputAttributes =((ParameterizedQuery)query).getOutputAttributeList();
		if(outputAttributes==null || outputAttributes.size()==0)
		{
			throw new QueryModuleException("No output attribute defined for query using Define View!", QueryModuleError.GENERIC_EXCEPTION);
		}
		
		return ((ParameterizedQuery)query).getOutputAttributeList();
	}
}
