
package edu.wustl.query.util.querysuite;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.tree.QueryTreeNodeData;
import edu.wustl.query.util.global.Constants;

/**
 * @author santhoshkumar_c
 *
 */
public class QueryDetails
{

	private List<OutputTreeDataNode> rootOutputTreeNodeList;
	private Map<String, OutputTreeDataNode> uniqueIdNodesMap;
	private SessionDataBean sessionData;
	private Map<AttributeInterface, String> attributeColumnNameMap;
	private Map<String, IOutputTerm> outputTermsColumns;
	private IQuery query;
	private List<QueryTreeNodeData> treeDataVector;
	private OutputTreeDataNode currentSelectedObject;
	private Map<Integer, String> parentNodesIdMap;
	private List<Integer> mainEntityList;
	private Map<Integer, List<Integer>> mainExpEntityExpressionIdMap;
	private Long queryExecutionId = 0L;

	/**
	 * 
	 */
	private Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap;

	/**
	 * returns the parentChildren Map
	 * @return parentChildrenMap.
	 */
	public Map<OutputTreeDataNode, List<OutputTreeDataNode>> getParentChildrenMap()
	{
		return parentChildrenMap;
	}

	/**
	 * sets the parentChildren Map
	 * @param parentChildrenMap map
	 */
	public void setParentChildrenMap(
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap)
	{
		this.parentChildrenMap = parentChildrenMap;
	}

	/**
	 * returns the mainExpEntity vs ExpressionId Map
	 * @return the mainExpEntityExpressionIdMap
	 */
	public Map<Integer, List<Integer>> getMainExpEntityExpressionIdMap()
	{
		return mainExpEntityExpressionIdMap;
	}

	/**
	 * Sets the mainExpEntity vs ExpressionId Map
	 * @param mainExpEntityExpressionIdMap the mainExpEntityExpressionIdMap to set
	 */
	public void setMainExpEntityExpressionIdMap(
			Map<Integer, List<Integer>> mainExpEntityExpressionIdMap)
	{
		this.mainExpEntityExpressionIdMap = mainExpEntityExpressionIdMap;
	}

	/**
	 * returns the parentNodesIdMap.
	 * @return the parentNodesIdMap. 
	 */
	public Map<Integer, String> getParentNodesIdMap()
	{
		return parentNodesIdMap;
	}

	/**
	 * sets the parentNodesIdMap
	 * @param parentNodesIdMap the parentNodesIdMap to set
	 */
	public void setParentNodesIdMap(Map<Integer, String> parentNodesIdMap)
	{
		this.parentNodesIdMap = parentNodesIdMap;
	}

	/**
	 * returns the currentSelectedObject
	 * @return the currentSelectedObject
	 */
	public OutputTreeDataNode getCurrentSelectedObject()
	{
		return currentSelectedObject;
	}

	/**
	 * sets the currentSelectedObject
	 * @param currentSelectedObject the currentSelectedObject to set
	 */
	public void setCurrentSelectedObject(OutputTreeDataNode currentSelectedObject)
	{
		this.currentSelectedObject = currentSelectedObject;
	}

	/**
	 * returns the treeDataVector.
	 * @return the treeDataVector
	 */
	public List<QueryTreeNodeData> getTreeDataVector()
	{
		return treeDataVector;
	}

	/**
	 * sets the treeDataVector
	 * @param treeDataVector the treeDataVector to set
	 */
	public void setTreeDataVector(List<QueryTreeNodeData> treeDataVector)
	{
		this.treeDataVector = treeDataVector;
	}

	/**
	 * returns the mainEntityList
	 * @return the mainEntityList
	 */
	public List<Integer> getMainEntityList()
	{
		return mainEntityList;
	}

	/**
	 * sets the mainEntityList.
	 * @param mainEntityList the mainEntityList to set
	 */
	public void setMainEntityList(List<Integer> mainEntityList)
	{
		this.mainEntityList = mainEntityList;
	}

	/**
	 * default constructor
	 */
	public QueryDetails()
	{
		// to-do
	}

	/**
	 * parameterized constructor.
	 * @param session session to retrieve the required objects.
	 */
	public QueryDetails(HttpSession session)
	{
		//this.session = session;
		rootOutputTreeNodeList = (List<OutputTreeDataNode>) session
				.getAttribute(Constants.SAVE_TREE_NODE_LIST);
		uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session
				.getAttribute(Constants.ID_NODES_MAP);
		//		mainEntityMap = (Map<QueryableObjectInterface, List<QueryableObjectInterface>>) session
		//				.getAttribute(Constants.MAIN_ENTITY_MAP);
		sessionData = (SessionDataBean) session
				.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
		attributeColumnNameMap = (Map<AttributeInterface, String>) session
				.getAttribute(Constants.ATTRIBUTE_COLUMN_NAME_MAP);
		outputTermsColumns = (Map<String, IOutputTerm>) session
				.getAttribute(Constants.OUTPUT_TERMS_COLUMNS);
		query = (IQuery) session.getAttribute(Constants.QUERY_OBJECT);
		if (session.getAttribute(Constants.EXECUTION_ID_OF_QUERY) != null)
		{
			queryExecutionId = (Long.valueOf(session.getAttribute(Constants.EXECUTION_ID_OF_QUERY)
					.toString()));
		}
	}

	/**
	 * returns the attributeColumnNameMap.
	 * @return attributeColumnNameMap
	 */
	public Map<AttributeInterface, String> getAttributeColumnNameMap()
	{
		return attributeColumnNameMap;
	}

	/**
	 * sets the attributeColumnNameMap
	 * @param attributeColumnNameMap to set to the attributeColumnNameMap.
	 */
	public void setAttributeColumnNameMap(Map<AttributeInterface, String> attributeColumnNameMap)
	{
		this.attributeColumnNameMap = attributeColumnNameMap;
	}

	/**
	 * returns rootOutputTreeNodeList.
	 * @return the rootOutputTreeNodeList
	 */
	public List<OutputTreeDataNode> getRootOutputTreeNodeList()
	{
		return rootOutputTreeNodeList;
	}

	/**
	 * sets the rootOutputTreeNodeList.
	 * @param rootOutputTreeNodeList the rootOutputTreeNodeList to set
	 */
	public void setRootOutputTreeNodeList(List<OutputTreeDataNode> rootOutputTreeNodeList)
	{
		this.rootOutputTreeNodeList = rootOutputTreeNodeList;
	}

	/**
	 * returns sessionData.
	 * @return the sessionData
	 */
	public SessionDataBean getSessionData()
	{
		return sessionData;
	}

	/**
	 * Sets the sessionData.
	 * @param sessionData the sessionData to set
	 */
	public void setSessionData(SessionDataBean sessionData)
	{
		this.sessionData = sessionData;
	}

	/**
	 * returns uniqueIdNodesMap.
	 * @return the uniqueIdNodesMap
	 */
	public Map<String, OutputTreeDataNode> getUniqueIdNodesMap()
	{
		return uniqueIdNodesMap;
	}

	/**
	 * sets the uniqueIdNodesMap.
	 * @param uniqueIdNodesMap the uniqueIdNodesMap to set
	 */
	public void setUniqueIdNodesMap(Map<String, OutputTreeDataNode> uniqueIdNodesMap)
	{
		this.uniqueIdNodesMap = uniqueIdNodesMap;
	}

	/**
	 * @return the outputTermsColumns
	 */
	public Map<String, IOutputTerm> getOutputTermsColumns()
	{
		return outputTermsColumns;
	}

	/**
	 * @param outputTermsColumns the outputTermsColumns to set
	 */
	public void setOutputTermsColumns(Map<String, IOutputTerm> outputTermsColumns)
	{
		this.outputTermsColumns = outputTermsColumns;
	}

	/**
	 * @return the query
	 */
	public IQuery getQuery()
	{
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(IQuery query)
	{
		this.query = query;
	}

	/**
	 * @return the queryExecutionId
	 */
	public Long getQueryExecutionId()
	{
		return queryExecutionId;
	}

	/**
	 * @param queryExecutionId the queryExecutionId to set
	 */
	public void setQueryExecutionId(Long queryExecutionId)
	{
		this.queryExecutionId = queryExecutionId;
	}
}
