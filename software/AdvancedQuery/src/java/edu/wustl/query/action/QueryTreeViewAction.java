
package edu.wustl.query.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.common.query.queryobject.impl.QueryTreeNodeData;
import edu.wustl.query.util.global.AQConstants;

/**
 * This action is called when user clicks on Search button after forming the query object.This class loads required tree data in session/request.
 * And then it forwards control to QueryTreeView.jsp.
 * @author deepti_shelar
 */
public class QueryTreeViewAction extends Action
{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		Long noOfTrees = (Long) session.getAttribute(AQConstants.NO_OF_TREES);
		for (int i = 0; i < noOfTrees; i++)
		{
			String key = AQConstants.TREE_DATA + "_" + i;
			List<QueryTreeNodeData> treeData = (List<QueryTreeNodeData>) session
					.getAttribute(key);
			request.setAttribute(key, treeData);
			session.removeAttribute(key);
		}
		return mapping.findForward(AQConstants.SUCCESS);
	}
}
