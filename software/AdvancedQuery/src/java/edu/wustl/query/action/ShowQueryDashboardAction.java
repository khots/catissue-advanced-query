
package edu.wustl.query.action;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import edu.wustl.cab2b.common.cache.AbstractEntityCache;
import edu.wustl.common.action.SecureAction;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.beans.DashboardBean;
import edu.wustl.query.bizlogic.DashboardBizLogic;
import edu.wustl.query.dto.QueryExportDTO;
import edu.wustl.query.flex.dag.DAGConstant;
import edu.wustl.query.querysuite.QueryDataExportTask;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;

/**
 * The Class ShowQueryDashboardAction.
 * @author nitesh_marwaha
 *
 */
public class ShowQueryDashboardAction extends SecureAction
{

	@Override
	/**
	 * This method is used to fetch queries and display on the dashboard.
	 * @param actionMapping mapping
	 * @param actionForm form
	 * @param request request
	 * @param response response
	 * @throws Exception Exception
	 * @return ActionForward actionForward
	 * @see edu.wustl.common.action.SecureAction#executeSecureAction(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		
		SessionDataBean sessionDataBean = (SessionDataBean)session.getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		
		cleanUpSession(request);
		saveToken(request);
		ActionForward actionForward;
		if (AbstractEntityCache.isCacheReady)
		{
			actionForward = mapping.findForward(AQConstants.SUCCESS);
			request.setAttribute(AQConstants.POPUP_MESSAGE, ApplicationProperties
					.getValue("query.confirmBox.message"));
			request.setAttribute(AQConstants.POPUP_HEADER,
					ApplicationProperties.getValue("queryfolder.app.popuptitle"));
			request.setAttribute(AQConstants.POPUP_DELETE_QUERY_MESSAGE,
					ApplicationProperties.getValue("queryfolder.confirmBox.querydelete.message"));
			request.setAttribute(AQConstants.POPUP_ASSIGN_MESSAGE,
					ApplicationProperties.getValue("queryfolder.alertBox.message"));
			request.setAttribute(AQConstants.POPUP_ASSIGN_QMESSAGE,
					ApplicationProperties.getValue("queryfolder.alertBox.query.message"));
			request.setAttribute(AQConstants.POPUP_TEXT,
					ApplicationProperties.getValue("queryfolder.app.newfolder.text"));
			request.setAttribute(AQConstants.POPUP_DELETE_QUERY_FOLDER_MESSAGE,
					ApplicationProperties.getValue("queryfolder.confirmBox.folderdelete.message"));
			String tagId = request.getParameter("tagId");
			if(tagId != null){
				request.setAttribute("tagId", tagId);
			}
			
			
			//
			// file name is not empty when user clicks on link to download exported data
			//
			String fileName = request.getParameter("fileName");
			if(fileName != null && !fileName.isEmpty()){	
				QueryExportDTO queryExportDTO = new QueryExportDTO(); 
				queryExportDTO.setCsmUserId(sessionDataBean.getCsmUserId());
				queryExportDTO.setFileName(fileName);
				QueryDataExportTask dataExportTask = new QueryDataExportTask(queryExportDTO);
				String actualFile = dataExportTask.getActualFilePath();

				if(new File(actualFile).isFile()){
					request.setAttribute("fileName", fileName);
				} else {
					request.setAttribute("fileName", "fileNotPresent");
				}
			}
		}
		else
		{
			actionForward = getActionForward(mapping, request);
		}
		return actionForward;
	}

	/**
	 * Gets the action forward.
	 *
	 * @param actionMapping the action mapping
	 * @param request the request
	 *
	 * @return the action forward
	 */
	private ActionForward getActionForward(ActionMapping actionMapping, HttpServletRequest request)
	{
		ActionForward actionForward;
		ActionErrors errors = new ActionErrors();
		String errorMessage = ApplicationProperties.getValue("entityCache.error");
		ActionError error = new ActionError("query.errors.item", errorMessage);
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
		actionForward = actionMapping.findForward(AQConstants.CACHE_ERROR);
		return actionForward;
	}

	/**
	 * Cleans up data from session.
	 *
	 * @param request request
	 */
	private void cleanUpSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.removeAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
		session.removeAttribute(AQConstants.SAVE_GENERATED_SQL);
		session.removeAttribute(AQConstants.SAVE_TREE_NODE_LIST);
		session.removeAttribute(AQConstants.ID_NODES_MAP);
		session.removeAttribute(AQConstants.MAIN_ENTITY_MAP);
		session.removeAttribute(AQConstants.EXPORT_DATA_LIST);
		session.removeAttribute(AQConstants.ENTITY_IDS_MAP);
		session.removeAttribute(DAGConstant.TQUIMAP);
		session.removeAttribute(DAGConstant.JQUIMAP);
		session.setAttribute(AQConstants.SAVED_QUERY, AQConstants.FALSE);
	}

}
