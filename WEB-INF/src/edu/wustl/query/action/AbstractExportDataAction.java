
package edu.wustl.query.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public abstract class AbstractExportDataAction extends AbstractQueryBaseAction
{

	/**
	 * preProcess.
	 * @param exportDataObject exportDataObject
	 * @param sessionDataBean sessionDataBean
	 */
	protected abstract void preProcess(ExportDataObject exportDataObject,
			SessionDataBean sessionDataBean);

	/**
	 * execute
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 */
	@Override
	protected final ActionForward executeBaseAction(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
	{
		ExportDataObject exportDataObject = new ExportDataObject();
		SessionDataBean sessionDataBean = getSessionData(request);

		AbstractQuery abstractQuery = getAbstractQuery(request);
		exportDataObject.getExportObjectDetails().put(
				edu.wustl.query.util.global.Constants.ABSTRACT_QUERY, abstractQuery);

		//Set the export url - to download data
		String exportUrl = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath();
		exportDataObject.getExportObjectDetails().put(
				edu.wustl.query.util.global.Constants.EXPORT_URL, exportUrl);

		preProcess(exportDataObject, sessionDataBean);

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
	 * to handle Exception
	 * @param ex Exception
	 */
	private void handleException(Exception expsn)
	{
		Logger.out.debug(expsn.getMessage());
	}

	/**
	 * to getAbstractQuery
	 * @param request HttpServletRequest
	 * @return AbstractQuery
	 */
	private AbstractQuery getAbstractQuery(HttpServletRequest request)
	{
		AbstractQuery abstractQuery = (AbstractQuery) request.getSession().getAttribute(
				edu.wustl.query.util.global.Constants.ABSTRACT_QUERY);
		abstractQuery.getQuery().setId(
				(Long) request.getSession().getAttribute(
						edu.wustl.query.util.global.Constants.DATA_QUERY_ID));
		return abstractQuery;
	}

	/**
	 * to getSessionData
	 * @param request request
	 * @return SessionDataBean
	 */
	protected SessionDataBean getSessionData(HttpServletRequest request)
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
