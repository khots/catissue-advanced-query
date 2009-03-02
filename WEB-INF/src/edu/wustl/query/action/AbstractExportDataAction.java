
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.query.exportmanager.ExportDataObject;

/**
 * This action class will act as the starting point of the export functionality
 * 
 * @author ravindra_jain
 * @version 1.0
 * @created 02-Mar-2009 3:24:31 PM
 */
public abstract class AbstractExportDataAction extends Action
{
	/**
	 * 
	 * @param exportDataObject
	 * @param sessionDataBean
	 */
	protected abstract void preProcess(ExportDataObject exportDataObject, SessionDataBean sessionDataBean);
	
	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		return null;
	}

}