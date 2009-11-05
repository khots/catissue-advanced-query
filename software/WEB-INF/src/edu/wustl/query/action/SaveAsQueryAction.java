package edu.wustl.query.action;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.hibernate.HibernateCleanser;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.DefinedQueryUtil;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * performs the saving of query
 * @author chitra_garg
 *
 */
public class SaveAsQueryAction extends AbstractQueryBaseAction
{

	/**
	 * The actual execute method.
	 *
	 * @param mapping
	 *            Action Mapping.
	 * @param form
	 *            Action Form.
	 * @param request
	 *            HttpServletRequest object.
	 * @param response
	 *            HttpServletResponse object.
	 * @return The Action Forward object.
	 * @throws Exception
	 *             Exception.
	 * @see org.apache.struts.action.Action#execute
	 *      (org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward executeBaseAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{

		Writer writer = response.getWriter();
		String	validationMessage =null;
		//start save as query
		Long oldQueryId=Long.valueOf(request.getParameter("oldId"));
		String renameAs=request.getParameter("renameAs");
		//validating query name
		validationMessage = validateQuerytitle(renameAs);
		if(validationMessage==null)
		{
			SessionDataBean sessionDataBean=(SessionDataBean) request.getSession().getAttribute(
					edu.wustl.common.util.global.Constants.SESSION_DATA);

			Long queryId=saveAsQuery(oldQueryId, renameAs, sessionDataBean);
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("queryId", queryId);
			jsonResponse.put("renameAs", renameAs);
			jsonResponse.put("querySavedMsg",
					"<span class='success_msg'>Query saved successfully.</span>");
			writer.write(jsonResponse.toString());
		}
		else
		{
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("errormessage", validationMessage);
			writer.write(jsonResponse.toString());
		}
	//ends
		response.setContentType(Constants.CONTENT_TYPE_TEXT);

		return null;
	}

	/**
	 * @param renameAs title to which rename query
	 * @return validationMessage
	 */
	private String validateQuerytitle(String renameAs)
	{
		String validationMessage;
		ValidateQueryBizLogic validateQueryBizLogic=new ValidateQueryBizLogic();
		if (renameAs == null || "".equals(renameAs))
		{
				validationMessage = ApplicationProperties
					.getValue("query.title.madatory");

		}
		else
		{
			validationMessage=validateQueryBizLogic.validateForDuplicateQueryName(renameAs, null);
		}
		return validationMessage;
	}

	/**
	 * Save As query with the new name
	 * @param oldQueryId old Query Id
	 * @param renameAs renameAs
	 * @param sessionDataBean sessionDataBean
	 * @return queryID
	 * @throws DAOException DAOException
	 * @throws QueryModuleException QueryModuleException
	 */
	private Long saveAsQuery(Long oldQueryId, String renameAs,
			SessionDataBean sessionDataBean) throws DAOException,
			QueryModuleException
			{
		HibernateDAO dao = null;
		dao = DAOUtil.getHibernateDAO(null);

		edu.wustl.common.querysuite.queryobject.impl.AbstractQuery query =
			(edu.wustl.common.querysuite.queryobject.impl.AbstractQuery) dao
		.retrieveById(
				edu.wustl.common.querysuite.queryobject.impl.AbstractQuery.class
						.getName(), oldQueryId);
		DAOUtil.closeHibernateDAO(dao);
		HibernateCleanser cleanser = new HibernateCleanser(query);
		cleanser.clean();
		query.setName(renameAs);
		DefinedQueryUtil definedQueryUtil = new DefinedQueryUtil();
		try
		{

			definedQueryUtil.insertQuery((IParameterizedQuery)query, sessionDataBean, false);

		}

		catch (BizLogicException e)
		{
			throw new QueryModuleException(e.getMessage(), e, QueryModuleError.DAO_EXCEPTION);
		}
		return query.getId();


	}

}
