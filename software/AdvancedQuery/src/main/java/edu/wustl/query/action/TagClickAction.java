
package edu.wustl.query.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.queryFolder.beans.Tag;
import edu.wustl.common.queryFolder.beans.AssignTag;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.actionForm.SaveQueryForm;
import edu.wustl.query.beans.DashBoardBean;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.query.bizlogic.DashboardBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.common.queryFolder.bizlogic.TagBizLogic;

public class TagClickAction extends Action
{

	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String target = new String(AQConstants.SUCCESS);
		String tagIds = (String) request.getParameter(AQConstants.TAGID_STRING);
		List<IParameterizedQuery> queries = new ArrayList<IParameterizedQuery>();
		setQueriesByTag(tagIds, queries);
		SaveQueryForm saveQueryForm = (SaveQueryForm) actionForm;
		setdashBoardData(saveQueryForm, request, queries);

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

		return (mapping.findForward(target));
	}

	private void setdashBoardData(SaveQueryForm saveQueryForm, HttpServletRequest request,
			List<IParameterizedQuery> queries) throws BizLogicException
	{
		DashboardBizLogic dashboardBizLogic = new DashboardBizLogic();
		SessionDataBean sessionDataBean = (SessionDataBean) request.getSession().getAttribute(
				edu.wustl.common.util.global.Constants.SESSION_DATA);
		dashboardBizLogic.setDashboardQueries(sessionDataBean, saveQueryForm);
		saveQueryForm.setParameterizedQueryCollection(queries);
		Map<Long, DashBoardBean> dashBoardDetails = dashboardBizLogic.getDashBoardDetails(queries,
				sessionDataBean.getUserId().toString());
		saveQueryForm.setDashBoardDetailsMap(dashBoardDetails);
	}

	private void setQueriesByTag(String tagIds, List<IParameterizedQuery> queries)
			throws DAOException, BizLogicException
	{

		IBizLogic bizLogic = BizLogicFactory.getInstance().getBizLogic(
				AQConstants.QUERY_INTERFACE_BIZLOGIC_ID);
		long tagId = Long.parseLong(tagIds);
		TagBizLogic tagBizLogic = new TagBizLogic();
		Tag tagById = tagBizLogic.getTagById(tagId);

		Set<AssignTag> assignTagList = tagById.getAssignTag();
		for (AssignTag assignTag : assignTagList)
		{
			List<IParameterizedQuery> queryList = bizLogic.retrieve(
					ParameterizedQuery.class.getName(), AQConstants.IDENTIFIER,
					assignTag.getObjId());
			System.out.println(ParameterizedQuery.class.getName());
			if (queryList != null && !queryList.isEmpty())
			{
				IParameterizedQuery pQuery = queryList.get(0);
				queries.add(pQuery);
			}

		}
	}
}
