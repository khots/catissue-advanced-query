/**
 * 
 */
package edu.wustl.query.viewmanager;

import java.util.List;

import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;


/**
 * @author vijay_pande
 *
 */
public class ViewManager
{
	protected int queryDetails;
	protected ViewType viewType;
	
	public void createSpreadSheetView() throws QueryModuleException
	{
		
	}
	
	public void createTreeView() throws QueryModuleException
	{
		
	}
	
	public static ViewManager getInstance(ViewType viewType) throws QueryModuleException
	{
		ViewManager viewManager = null;
		switch (viewType)
		{
			case USER_DEFINED_SPREADSHEET_VIEW :
				viewManager = new UserDefinedViewManager();
				break;
			case PRE_DEFINED_SPREADSHEET_VIEW:
				viewManager = new PreDefinedViewManager();
				break;
			case OBJECT_SPREADSHEET_VIEW:
				viewManager = new ObjectViewManager();
				break;
			default :
				throw new QueryModuleException("Class not define for this ViewType",QueryModuleError.GENERIC_EXCEPTION);
		}
		return viewManager;
	}

	public List<IOutputAttribute> getSelectedColumnList(IQuery query) throws QueryModuleException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
