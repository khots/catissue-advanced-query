
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.AbstractQuery;
import edu.wustl.common.util.global.Constants;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.exportmanager.ExportDataManager;
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
	protected abstract void preProcess(ExportDataObject exportDataObject,
			SessionDataBean sessionDataBean);

	@Override
	public final ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
	{
		ExportDataObject exportDataObject = new ExportDataObject();
		SessionDataBean sessionDataBean = getSessionData(request);
			
		preProcess(exportDataObject, sessionDataBean);
		
		AbstractQuery abstractQuery = getAbstractQuery(request);
		exportDataObject.getExportObjectDetails().put(edu.wustl.query.util.global.Constants.ABSTRACT_QUERY, abstractQuery);
		
		try
		{
			ExportDataManager.export(sessionDataBean, exportDataObject);
		}
		catch (Exception ex)
		{
			handleException(ex);
		}

		return null;
	}

	/**
	 * 
	 * @param ex Exception
	 */ 
	private void handleException(Exception ex)
	{
		Logger.out.debug(ex.getMessage());
	}

	/**
	 * 
	 * @param request HttpServletRequest
	 * @return AbstractQuery
	 */
	private AbstractQuery getAbstractQuery(HttpServletRequest request)
	{
		AbstractQuery abstractQuery = (AbstractQuery) request.getSession().getAttribute(edu.wustl.query.util.global.Constants.ABSTRACT_QUERY);
		return abstractQuery;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private SessionDataBean getSessionData(HttpServletRequest request)
	{
		Object obj = request.getSession().getAttribute(Constants.SESSION_DATA);
		SessionDataBean sessionData = null;
		if (obj != null)
		{
			sessionData = (SessionDataBean) obj;
		}
		return sessionData;
	}

}