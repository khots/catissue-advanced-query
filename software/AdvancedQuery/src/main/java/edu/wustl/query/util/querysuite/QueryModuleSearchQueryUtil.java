
package edu.wustl.query.util.querysuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.factory.AbstractQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.QueryTreeNodeData;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.query.queryobject.util.QueryObjectProcessor;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.XMLPropertyHandler;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.bizlogic.CommonQueryBizLogic;
import edu.wustl.query.bizlogic.DefineGridViewBizLogic;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;
import edu.wustl.query.bizlogic.QueryOutputTreeBizLogic;
import edu.wustl.query.generator.ISqlGenerator;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.security.exception.SMException;

/**
 * @author santhoshkumar_c
 *
 */
public class QueryModuleSearchQueryUtil
{
	private HttpServletRequest request;
	private HttpSession session;
	private IQuery query;
	private boolean isSavedQuery;

	private QueryDetails queryDetailsObj;

	/**
	 * @param request
	 * @param query
	 */
	public QueryModuleSearchQueryUtil(HttpServletRequest request, IQuery query)
	{
		this.request = request;
		this.session = request.getSession();
		this.query = query;

		isSavedQuery = Boolean.valueOf((String) session.getAttribute(AQConstants.IS_SAVED_QUERY));
		queryDetailsObj = new QueryDetails(session);
	}

	/**
	 * This method extracts query object and forms results for tree and grid.
	 * @param option
	 * @return status
	 */
	public QueryModuleError searchQuery()
	{
		session.removeAttribute(AQConstants.HYPERLINK_COLUMN_MAP);
		QueryModuleError status = QueryModuleError.SUCCESS;
		IQuery newQuery = null;
		try
		{
			session.setAttribute(AQConstants.QUERY_OBJECT, query);
			boolean alreadySavedQuery = Boolean.valueOf((String)session.getAttribute(AQConstants.SAVED_QUERY));
			if (isSavedQuery && !alreadySavedQuery)
			{
				newQuery = processSaveQuery();
			}
			if (queryDetailsObj.getSessionData() != null)
			{
				IQuery originalQuery=null;
				if(newQuery != null)
				{
					originalQuery = queryDetailsObj.getQuery();
					queryDetailsObj.setQuery(newQuery);
				}
				QueryOutputTreeBizLogic outputTreeBizLogic = auditQuery();
				boolean hasCondOnIdentifiedField;
				if(newQuery == null)
				{
					hasCondOnIdentifiedField = edu.wustl.query.util.global.Utility
					.isConditionOnIdentifiedField(query);
				}
				else
				{
					queryDetailsObj.setQuery(newQuery);
					hasCondOnIdentifiedField = edu.wustl.query.util.global.Utility
							.isConditionOnIdentifiedField(newQuery);
				}
				setDataInSession(outputTreeBizLogic, hasCondOnIdentifiedField);
				if(originalQuery != null)
				{
					query = originalQuery;
					session.setAttribute(AQConstants.QUERY_OBJECT, originalQuery);
					queryDetailsObj.setQuery(originalQuery);
				}
			}
		}
		catch (QueryModuleException e)
		{
			status = e.getKey();
		}
		return status;
	}

	/**
	 * @param option
	 * @param outputTreeBizLogic
	 * @param hasCondOnIdentifiedField
	 * @throws QueryModuleException
	 */
	private void setDataInSession(QueryOutputTreeBizLogic outputTreeBizLogic,
			boolean hasCondOnIdentifiedField) throws QueryModuleException
	{
		int initialValue = 0;
		QueryModuleException queryModExp;
		try
		{
			for (OutputTreeDataNode outnode : queryDetailsObj.getRootOutputTreeNodeList())
			{
				List<QueryTreeNodeData> treeData = null;
				treeData = outputTreeBizLogic.createDefaultOutputTreeData(initialValue, outnode,
						hasCondOnIdentifiedField, queryDetailsObj);
				initialValue = setTreeData(initialValue, treeData);
			}
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		catch (ClassNotFoundException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.CLASS_NOT_FOUND);
			throw queryModExp;
		}
		catch (BizLogicException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (SMException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		session.setAttribute(AQConstants.TREE_ROOTS, queryDetailsObj.getRootOutputTreeNodeList());
		Long noOfTrees = Long.valueOf(queryDetailsObj.getRootOutputTreeNodeList().size());
		session.setAttribute(AQConstants.NO_OF_TREES, noOfTrees);
		OutputTreeDataNode node = queryDetailsObj.getRootOutputTreeNodeList().get(0);
		processRecords(queryDetailsObj, node, hasCondOnIdentifiedField);
	}

	/**
	 * @param option
	 * @param initialValue
	 * @param treeData
	 * @return int
	 * @throws QueryModuleException
	 */
	private int setTreeData(int initialValue, List<QueryTreeNodeData> treeData)
			throws QueryModuleException
	{
		int resultsSize = treeData.size();
		if (resultsSize == 0)
		{
			throw new QueryModuleException("Query Returns Zero Results",
					QueryModuleError.NO_RESULT_PRESENT);
		}
		session.setAttribute(AQConstants.TREE_DATA + AQConstants.UNDERSCORE + initialValue, treeData);
		initialValue += 1;
		return initialValue;
	}

	/**
	 * @return outputTreeBizLogic
	 * @throws QueryModuleException
	 */
	private QueryOutputTreeBizLogic auditQuery() throws QueryModuleException
	{
	    CommonQueryBizLogic queryBizLogic = new CommonQueryBizLogic();
		QueryModuleException queryModExp;
		QueryOutputTreeBizLogic outputTreeBizLogic = new QueryOutputTreeBizLogic();
		try
		{
			long auditEventId = 0;
			String selectSql = (String) session.getAttribute(AQConstants.SAVE_GENERATED_SQL);
			if(session.getAttribute(AQConstants.AUDIT_EVENT_ID)== null)
			{
				auditEventId = queryBizLogic.insertQuery(selectSql, queryDetailsObj.getSessionData());
				QueryModuleSqlUtil.updateAuditQueryDetails(AQConstants.QUERY_ID, query.getId().toString(), auditEventId);
				session.setAttribute(AQConstants.AUDIT_EVENT_ID,auditEventId);
			}
			else
			{
				auditEventId = (Long)session.getAttribute(AQConstants.AUDIT_EVENT_ID);
			}
			boolean alreadySavedQuery = Boolean.valueOf((String)session.getAttribute("savedQuery"));
			queryDetailsObj.setAuditEventId(auditEventId);
			getNewQuery(outputTreeBizLogic, selectSql, alreadySavedQuery);
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		catch (SQLException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (MultipleRootsException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.MULTIPLE_ROOT);
			throw queryModExp;
		}
		catch (SqlException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (RuntimeException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		return outputTreeBizLogic;
	}

	/**
	 * @param outputTreeBizLogic outputTreeBizLogic
	 * @param selectSql query
	 * @param alreadySavedQuery alreadySavedQuery
	 * @throws MultipleRootsException MultipleRootsException
	 * @throws SqlException SqlException
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 */
	private void getNewQuery(QueryOutputTreeBizLogic outputTreeBizLogic,
			String selectSql, boolean alreadySavedQuery)
			throws MultipleRootsException, SqlException, DAOException,
			SQLException
	{
		if(isSavedQuery && !alreadySavedQuery)
		{
			String newSql=null;
			ISqlGenerator sqlGenerator = AbstractQueryGeneratorFactory.getDefaultQueryGenerator();
			if(queryDetailsObj.getSessionData().isSecurityRequired())
			{
				newSql = sqlGenerator.generateSQL(queryDetailsObj.getQuery());
				queryDetailsObj.setColumnValueBean(sqlGenerator.getColumnValueBean());
			}
			else
			{
				newSql = sqlGenerator.generateSQL(query);
			}
			selectSql = newSql;
		}
		outputTreeBizLogic.createOutputTreeTable(selectSql, queryDetailsObj);
	}

	/**
	 * @throws QueryModuleException
	 */
	private IQuery processSaveQuery() throws QueryModuleException
	{
		ISqlGenerator sqlGenerator = AbstractQueryGeneratorFactory.getDefaultQueryGenerator();
		validateQuery(sqlGenerator);
		List<OutputTreeDataNode> rootOutputTreeNodeList = sqlGenerator.getRootOutputTreeNodeList();
		session.setAttribute(AQConstants.SAVE_TREE_NODE_LIST, rootOutputTreeNodeList);
		queryDetailsObj.setRootOutputTreeNodeList(rootOutputTreeNodeList);
		queryDetailsObj.setColumnValueBean(sqlGenerator.getColumnValueBean());
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = QueryObjectProcessor
		.getAllChildrenNodes(rootOutputTreeNodeList);
		queryDetailsObj.setUniqueIdNodesMap(uniqueIdNodesMap);
		session.setAttribute(AQConstants.ID_NODES_MAP, uniqueIdNodesMap);
		IQuery newQuery=null;
		boolean alreadySavedQuery=false;
		if(session.getAttribute(AQConstants.SAVED_QUERY) != null)
		{
			alreadySavedQuery = Boolean.valueOf((String)session.getAttribute(AQConstants.SAVED_QUERY));
			if(alreadySavedQuery)
			{
				session.setAttribute(AQConstants.PROCESSED_SAVED_QUERY, AQConstants.TRUE);
			}
		}
		if(queryDetailsObj.getSessionData().isSecurityRequired() && !alreadySavedQuery)
		{
			newQuery = QueryCSMUtil
				.returnQueryClone(query, request.getSession(), queryDetailsObj);
		}
		if(alreadySavedQuery)
		{
			session.setAttribute(AQConstants.SAVED_QUERY, AQConstants.FALSE);
			session.setAttribute(AQConstants.PROCESSED_SAVED_QUERY, AQConstants.FALSE);
		}
		return newQuery;
	}
	/**
	 * Calls sqlGenerator which validates IQuery object and throws appropriate exceptions
	 * @param sqlGenerator sqlGenerator object
	 * @throws QueryModuleException Exception e
	 */
	private void validateQuery(ISqlGenerator sqlGenerator)
			throws QueryModuleException
	{
		QueryModuleException queryModExp;
		try
		{
			session.setAttribute(AQConstants.SAVE_GENERATED_SQL, sqlGenerator.generateSQL(query));
			Map<AttributeInterface, String> attributeColumnNameMap = sqlGenerator
			.getAttributeColumnNameMap();
			session.setAttribute(AQConstants.ATTRIBUTE_COLUMN_NAME_MAP, attributeColumnNameMap);
			session.setAttribute(AQConstants.OUTPUT_TERMS_COLUMNS, sqlGenerator
					.getOutputTermsColumns());
			session.setAttribute(AQConstants.COLUMN_VALUE_BEAN, sqlGenerator.getColumnValueBean());
		}
		catch (MultipleRootsException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.MULTIPLE_ROOT);
			throw queryModExp;
		}
		catch (SqlException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
		catch (RuntimeException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.SQL_EXCEPTION);
			throw queryModExp;
		}
	}

	/**
	 * @param QueryDetailsObj details of query object
	 * @param node OutputTreeDataNode
	 * @param hasCondOnIdentifiedField required for CSM filtering
	 * @throws QueryModuleException exception
	 */
	public void processRecords(QueryDetails QueryDetailsObj, OutputTreeDataNode node,
			boolean hasCondOnIdentifiedField) throws QueryModuleException
	{
		SelectedColumnsMetadata selectedColumnsMetadata = getAppropriateSelectedColumnMetadata(
				query, (SelectedColumnsMetadata) session
						.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA));
		selectedColumnsMetadata.setCurrentSelectedObject(node);
		QueryModuleException queryModExp;
		int recordsPerPage = setRecordsPerPage();
		if (query.getId() != null && isSavedQuery)
		{
			getSelectedColumnsMetadata(QueryDetailsObj, selectedColumnsMetadata);
		}

		QueryResultObjectDataBean queryResulObjectDataBean = QueryCSMUtil
				.getQueryResulObjectDataBean(node, QueryDetailsObj);
		Map<Long, QueryResultObjectDataBean> queryResultObjDataBeanMap = new HashMap<Long, QueryResultObjectDataBean>();
		queryResultObjDataBeanMap.put(node.getId(), queryResulObjectDataBean);
		QueryOutputSpreadsheetBizLogic outputSpreadsheetBizLogic = new QueryOutputSpreadsheetBizLogic();
		try
		{
			ISqlGenerator sqlGenerator = AbstractQueryGeneratorFactory.getDefaultQueryGenerator();
			Map<String, IOutputTerm> outputTermsColumns = sqlGenerator.getOutputTermsColumns();
			if (outputTermsColumns == null)
			{
				outputTermsColumns = (Map<String, IOutputTerm>) session
						.getAttribute(AQConstants.OUTPUT_TERMS_COLUMNS);
			}
			session.setAttribute(AQConstants.OUTPUT_TERMS_COLUMNS, outputTermsColumns);
			Map<String, List<String>> spreadSheetDatamap = outputSpreadsheetBizLogic
					.createSpreadsheetData(AQConstants.TREENO_ZERO, node, QueryDetailsObj, null,
							recordsPerPage, selectedColumnsMetadata, queryResultObjDataBeanMap,
							hasCondOnIdentifiedField, query.getConstraints(), outputTermsColumns);
			setQuerySessionData(selectedColumnsMetadata, spreadSheetDatamap);
		}
		catch (DAOException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.DAO_EXCEPTION);
			throw queryModExp;
		}
		catch (ClassNotFoundException e)
		{
			queryModExp = new QueryModuleException(e.getMessage(), QueryModuleError.CLASS_NOT_FOUND);
			throw queryModExp;
		}
	}

	/**
	 * @param query
	 * @param selectedColumnsMetadata
	 * @return
	 */
	private static SelectedColumnsMetadata getAppropriateSelectedColumnMetadata(IQuery query,
			SelectedColumnsMetadata selectedColumnsMetadata)
	{
		boolean isQueryChanged = false;
		if (query != null && selectedColumnsMetadata != null)
		{
			List<Integer> expressionIdsInQuery = new ArrayList<Integer>();
			IConstraints constraints = query.getConstraints();
			List<QueryOutputTreeAttributeMetadata> selAttributeMetaDataList = selectedColumnsMetadata
					.getSelectedAttributeMetaDataList();
			for (IExpression expression : constraints)
			{
				if (expression.isInView())
				{
					expressionIdsInQuery.add(Integer.valueOf(expression.getExpressionId()));
				}
			}
			int expressionId;
			for (QueryOutputTreeAttributeMetadata element : selAttributeMetaDataList)
			{
				expressionId = element.getTreeDataNode().getExpressionId();
				if (!expressionIdsInQuery.contains(Integer.valueOf(expressionId)))
				{
					isQueryChanged = true;
					break;
				}
			}
		}
		if (isQueryChanged || selectedColumnsMetadata == null)
		{
			selectedColumnsMetadata = new SelectedColumnsMetadata();
			selectedColumnsMetadata.setDefinedView(false);
		}
		return selectedColumnsMetadata;
	}

	/** It will set the results per page.
	 * @return int
	 */
	private int setRecordsPerPage()
	{
		int recordsPerPage;
		String recordsPerPgSessionValue = (String) session.getAttribute(AQConstants.RESULTS_PER_PAGE);
		if (recordsPerPgSessionValue == null)
		{
			recordsPerPgSessionValue = XMLPropertyHandler
					.getValue(AQConstants.RECORDS_PER_PAGE_PROPERTY_NAME);
			session.setAttribute(AQConstants.RESULTS_PER_PAGE, recordsPerPgSessionValue);
		}
		recordsPerPage = Integer.valueOf(recordsPerPgSessionValue);
		return recordsPerPage;
	}

	/**
	 * Set the data in session.
	 * @param selectedColumnsMetadata
	 * @param spreadSheetDatamap
	 */
	public void setQuerySessionData(SelectedColumnsMetadata selectedColumnsMetadata,
			Map<String, List<String>> spreadSheetDatamap)
	{
		QuerySessionData querySessionData = (QuerySessionData) spreadSheetDatamap
				.get(AQConstants.QUERY_SESSION_DATA);
		int totalNoOfRecords = querySessionData.getTotalNumberOfRecords();
		session.setAttribute(AQConstants.QUERY_SESSION_DATA, querySessionData);
		session.setAttribute(AQConstants.TOTAL_RESULTS, Integer.valueOf(totalNoOfRecords));
		//		QueryShoppingCart cart = (QueryShoppingCart)session.getAttribute(Constants.QUERY_SHOPPING_CART);
		//		String message = QueryModuleUtil.getMessageIfIdNotPresentForOrderableEntities(
		//				selectedColumnsMetadata, cart);
		//		session.setAttribute(Constants.VALIDATION_MESSAGE_FOR_ORDERING, message);
		session.setAttribute(AQConstants.PAGINATION_DATA_LIST, spreadSheetDatamap
				.get(AQConstants.SPREADSHEET_DATA_LIST));
		session.setAttribute(AQConstants.SPREADSHEET_COLUMN_LIST, spreadSheetDatamap
				.get(AQConstants.SPREADSHEET_COLUMN_LIST));
		session.setAttribute(AQConstants.SELECTED_COLUMN_META_DATA, spreadSheetDatamap
				.get(AQConstants.SELECTED_COLUMN_META_DATA));
		session.setAttribute(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP, spreadSheetDatamap
				.get(AQConstants.QUERY_REASUL_OBJECT_DATA_MAP));
		session.setAttribute(AQConstants.DEFINE_VIEW_QUERY_REASULT_OBJECT_DATA_MAP,
				spreadSheetDatamap.get(AQConstants.DEFINE_VIEW_QUERY_REASULT_OBJECT_DATA_MAP));
	}

	/**
	 * @param QueryDetailsObj
	 * @param selectedColumnsMetadata
	 */
	public void getSelectedColumnsMetadata(QueryDetails QueryDetailsObj,
			SelectedColumnsMetadata selectedColumnsMetadata)
	{
		OutputTreeDataNode currentSelectedObject = selectedColumnsMetadata.getCurrentSelectedObject();
		List<IOutputAttribute> selAttributeList;
		boolean isDefinedView = true;
		List<IOutputAttribute> selectedOutputAttributeList = new ArrayList<IOutputAttribute>();
		if (query instanceof ParameterizedQuery)
		{
			ParameterizedQuery savedQuery = (ParameterizedQuery) query;
			selAttributeList = savedQuery.getOutputAttributeList();
			if (!selAttributeList.isEmpty())
			{
				DefineGridViewBizLogic gridViewBizLogic = new DefineGridViewBizLogic();
				if(selectedColumnsMetadata.getSelectedOutputAttributeList() == null)
				{
					selectedColumnsMetadata.setSelectedOutputAttributeList(selAttributeList);
				}
				else if(selectedColumnsMetadata.getSelectedOutputAttributeList().isEmpty())
				{
					AttributeInterface attribute;
					OutputAttribute attr;
					for (QueryOutputTreeAttributeMetadata metadata : selectedColumnsMetadata.getSelectedAttributeMetaDataList())
					{
						attribute = metadata.getAttribute();
						attr = new OutputAttribute(query.getConstraints().getExpression(currentSelectedObject
								.getExpressionId()), attribute);
						selectedOutputAttributeList.add(attr);
					}
					selectedColumnsMetadata.setSelectedOutputAttributeList(selectedOutputAttributeList);
					selectedColumnsMetadata.setDefinedView(false);
					isDefinedView = false;
				}
				gridViewBizLogic.getSelectedColumnMetadataForSavedQuery(QueryDetailsObj
						.getUniqueIdNodesMap().values(), selectedColumnsMetadata.getSelectedOutputAttributeList(), selectedColumnsMetadata);
				if(isDefinedView)
				{
					selectedColumnsMetadata.setDefinedView(true);
				}
			}
		}
	}
}
