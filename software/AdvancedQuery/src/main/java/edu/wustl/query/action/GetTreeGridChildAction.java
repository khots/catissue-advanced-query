
package edu.wustl.query.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.queryFolder.beans.AssignTag;
import edu.wustl.common.queryFolder.beans.Tag;
import edu.wustl.query.bizlogic.BizLogicFactory;
import edu.wustl.common.queryFolder.bizlogic.TagBizLogic;
import edu.wustl.query.util.global.AQConstants;

public class GetTreeGridChildAction extends Action
{

	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String tagIds = (String) request.getParameter(AQConstants.TAGID_STRING);

		IBizLogic bizLogic = BizLogicFactory.getInstance().getBizLogic(
				AQConstants.QUERY_INTERFACE_BIZLOGIC_ID);
		long tagId = Long.parseLong(tagIds);
		TagBizLogic tagBizLogic = new TagBizLogic();
		Tag tagById = tagBizLogic.getTagById(tagId);
		JSONArray treeData = new JSONArray();
		Set<AssignTag> assignTagList = tagById.getAssignTag();
		int childCount = 0;
		for (AssignTag assignTag : assignTagList)
		{
			List<IParameterizedQuery> queryList = bizLogic.retrieve(
					ParameterizedQuery.class.getName(), AQConstants.IDENTIFIER,
					assignTag.getObjId());
			String objName = ((IParameterizedQuery) queryList.get(0)).getName();
			JSONObject obj = new JSONObject();
			obj.put(AQConstants.IDENTIFIER, assignTag.getId());
			obj.put(AQConstants.NAME, objName);
			childCount++;
			treeData.put(obj);
		}
		JSONObject arrayObj = new JSONObject();
		arrayObj.put(AQConstants.TREE_DATA, treeData);
		arrayObj.put(AQConstants.CHILDCOUNT, childCount);
		response.flushBuffer();
		request.setAttribute(AQConstants.POPUP_HEADER,
				ApplicationProperties.getValue("queryfolder.confirmBox.querydelete.message"));
		System.out.println(AQConstants.POPUP_HEADER);
		PrintWriter out = response.getWriter();
		out.write(arrayObj.toString());

		return null;

	}
}
