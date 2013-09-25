/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.query.bizlogic;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.factory.AbstractQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.generator.ISqlGenerator;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.querysuite.QueryCSMUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

/**
 * When the user searches or saves a query , the query is checked for the conditions like DAG should not be empty , is there
 * at least one node in view on define view page and does the query contain the main object. If all the conditions are satisfied
 * further process is done else corresponding error message is shown.
 *
 * @author shrutika_chintal
 *
 */
public class ValidateQueryBizLogic
{

	/**
	 * @param request -
	 * @param query -
	 * @return - error message . Returns null if the query is correctly formed.
	 * @throws MultipleRootsException
	 * @throws SqlException
	 */
	public String getValidationMessage(HttpServletRequest request, IQuery query)
	{
		String validationMessage = null;
		boolean isRulePresentInDag = QueryModuleUtil.checkIfRulePresentInDag(query);
		if (!isRulePresentInDag)
		{
			validationMessage = ApplicationProperties.getValue("query.noLimit.error");
			return validationMessage;
		}
		boolean noExpressionInView = isExpressionInView(query);
		if (noExpressionInView)
		{
			validationMessage = ApplicationProperties
					.getValue("query.defineView.noExpression.message");
			return validationMessage;
		}
		try
		{
			populateQuery(request, query);
		}
		catch (MultipleRootsException e)
		{
			Logger.out.error(e);
			validationMessage = "<li><font color='red'> "+ ApplicationProperties
			.getValue("errors.executeQuery.multipleRoots")+ "</font></li>";
		}
		catch (SqlException e)
		{
			Logger.out.error(e);
			if(e.getMessage()!=null)
			{
				validationMessage = "<li><font color='red'> "+ e.getMessage()
				+ "</font></li>";
			}
			else
			{
				validationMessage = "<li><font color='red'> "+ ApplicationProperties
				.getValue("errors.executeQuery.genericmessage")+ "</font></li>";
			}
		}
		catch (RuntimeException e)
		{
			Logger.out.error(e);
			validationMessage = "<li><font color='red'> "+ ApplicationProperties
			.getValue("errors.executeQuery.genericmessage")+ "</font></li>";
			e.printStackTrace();
		}
		catch (DAOException e)
		{
			Logger.out.error(e);
			validationMessage = "<li><font color='red'> "
			+ ApplicationProperties.getValue("errors.executeQuery.genericmessage")
					+ "</font></li>";
			e.printStackTrace();
		}
		return validationMessage;
	}

	/**
	 * @param query query
	 * @return <CODE>true</CODE> expression is in view,
	 * <CODE>false</CODE> otherwise
	 */
	private boolean isExpressionInView(IQuery query)
	{
		IConstraints constraints = query.getConstraints();
		boolean noExpressionInView = true;
		for (IExpression expression : constraints)
		{
			if (expression.isInView())
			{
				noExpressionInView = false;
				break;
			}
		}
		return noExpressionInView;
	}

	/**
	 * @param request request
	 * @param query query
	 * @throws MultipleRootsException MultipleRootsException
	 * @throws SqlException SqlException
	 * @throws DAOException DAOException
	 */
	private void populateQuery(HttpServletRequest request, IQuery query)
			throws MultipleRootsException, SqlException, DAOException
	{
		HttpSession session = request.getSession();
		SessionDataBean sessionDataBean = (SessionDataBean) session
				.getAttribute(AQConstants.SESSION_DATA);
		ISqlGenerator queryGenerator = AbstractQueryGeneratorFactory.getDefaultQueryGenerator();
		String selectSql = queryGenerator.generateSQL(query);
		CommonQueryBizLogic queryBizLogic = new CommonQueryBizLogic();
		long auditEventId = queryBizLogic.insertQuery(selectSql, sessionDataBean);
		session.setAttribute(AQConstants.AUDIT_EVENT_ID, auditEventId);
		Map<AttributeInterface, String> attributeColumnNameMap = queryGenerator
				.getAttributeColumnNameMap();
		session.setAttribute(AQConstants.ATTRIBUTE_COLUMN_NAME_MAP, attributeColumnNameMap);
		Map<String, IOutputTerm> outputTermsColumns = queryGenerator.getOutputTermsColumns();
		QueryDetails queryDetailsObj = new QueryDetails(session);
		session.setAttribute(AQConstants.OUTPUT_TERMS_COLUMNS, outputTermsColumns);
		session.setAttribute(AQConstants.SAVE_GENERATED_SQL, selectSql);
		List<OutputTreeDataNode> rootOutputTreeNodeList = queryGenerator
				.getRootOutputTreeNodeList();
		session.setAttribute(AQConstants.SAVE_TREE_NODE_LIST, rootOutputTreeNodeList);
		session.setAttribute(AQConstants.NO_OF_TREES, Long
				.valueOf(rootOutputTreeNodeList.size()));
		session.setAttribute(AQConstants.COLUMN_VALUE_BEAN, queryGenerator.getColumnValueBean());
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = QueryObjectProcessor
		.getAllChildrenNodes(rootOutputTreeNodeList);
		queryDetailsObj.setUniqueIdNodesMap(uniqueIdNodesMap);
		boolean isSavedQuery = Boolean.valueOf((String) session.getAttribute(AQConstants.IS_SAVED_QUERY));
		//This method will check if main objects for all the dependent objects are present in query or not.
		IQuery queryClone=null;
		if(isSavedQuery)
		{
			session.setAttribute(AQConstants.SAVED_QUERY, AQConstants.TRUE);
		}
		else
		{
			session.setAttribute(AQConstants.SAVED_QUERY, AQConstants.FALSE);
		}
		if(queryDetailsObj.getSessionData().isSecurityRequired())
		{
			queryClone = QueryCSMUtil
				.returnQueryClone(query, session, queryDetailsObj);
		}
		session.setAttribute(AQConstants.ID_NODES_MAP, uniqueIdNodesMap);
		if(queryClone != null)
		{
			selectSql = queryGenerator.generateSQL(queryClone);
			session.setAttribute(AQConstants.SAVE_GENERATED_SQL, selectSql);
			session.setAttribute(AQConstants.COLUMN_VALUE_BEAN, queryGenerator.getColumnValueBean());
		}
	}
}
