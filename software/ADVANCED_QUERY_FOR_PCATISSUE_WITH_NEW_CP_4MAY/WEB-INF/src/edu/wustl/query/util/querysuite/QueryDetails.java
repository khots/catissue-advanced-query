
package edu.wustl.query.util.querysuite;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.global.AQConstants;

/**
 * @author santhoshkumar_c
 *
 */
public class QueryDetails
{

	//private QueryDetails(){}	
	private List<OutputTreeDataNode> rootOutputTreeNodeList;
	private Map<String, OutputTreeDataNode> uniqueIdNodesMap;
	private Map<EntityInterface, List<EntityInterface>> mainEntityMap;
	private SessionDataBean sessionData;
	private String randomNumber;

	private Map<AttributeInterface, String> attributeColumnNameMap;
	private Map<String, IOutputTerm> outputTermsColumns;
	private IQuery query;
	private long auditEventId; 

	//private HttpSession session;

	public QueryDetails(HttpSession session)
	{
		//this.session = session;
		rootOutputTreeNodeList = (List<OutputTreeDataNode>) session
				.getAttribute(AQConstants.SAVE_TREE_NODE_LIST);
		uniqueIdNodesMap = (Map<String, OutputTreeDataNode>) session
				.getAttribute(AQConstants.ID_NODES_MAP);
		mainEntityMap = (Map<EntityInterface, List<EntityInterface>>) session
				.getAttribute(AQConstants.MAIN_ENTITY_MAP);
		sessionData = (SessionDataBean) session.getAttribute(AQConstants.SESSION_DATA);
		this.randomNumber = QueryModuleUtil.generateRandomNumber(session);
		attributeColumnNameMap = (Map<AttributeInterface, String>) session
				.getAttribute(AQConstants.ATTRIBUTE_COLUMN_NAME_MAP);
		outputTermsColumns = (Map<String, IOutputTerm>) session
				.getAttribute(AQConstants.OUTPUT_TERMS_COLUMNS);
		query = (IQuery) session.getAttribute(AQConstants.QUERY_OBJECT);
		if(session.getAttribute("AUDIT_EVENT_ID") != null)
		{
			auditEventId =(Long) session.getAttribute("AUDIT_EVENT_ID");
		}
	}

	/**
	 * 
	 * @return
	 */
	public Map<AttributeInterface, String> getAttributeColumnNameMap()
	{
		return attributeColumnNameMap;
	}

	/**
	 * 
	 * @param attributeColumnNameMap
	 */
	public void setAttributeColumnNameMap(Map<AttributeInterface, String> attributeColumnNameMap)
	{
		this.attributeColumnNameMap = attributeColumnNameMap;
	}

	/**
	 * @return the rootOutputTreeNodeList
	 */
	public List<OutputTreeDataNode> getRootOutputTreeNodeList()
	{
		return rootOutputTreeNodeList;
	}

	/**
	 * @param rootOutputTreeNodeList the rootOutputTreeNodeList to set
	 */
	public void setRootOutputTreeNodeList(List<OutputTreeDataNode> rootOutputTreeNodeList)
	{
		this.rootOutputTreeNodeList = rootOutputTreeNodeList;
	}

	/**
	 * @return the mainEntityMap
	 */
	public Map<EntityInterface, List<EntityInterface>> getMainEntityMap()
	{
		return mainEntityMap;
	}

	/**
	 * @param mainEntityMap the mainEntityMap to set
	 */
	public void setMainEntityMap(Map<EntityInterface, List<EntityInterface>> mainEntityMap)
	{
		this.mainEntityMap = mainEntityMap;
	}

	/**
	 * @return the randomNumber
	 */
	public String getRandomNumber()
	{
		return randomNumber;
	}

	/**
	 * @param randomNumber the randomNumber to set
	 */
	public void setRandomNumber(String randomNumber)
	{
		this.randomNumber = randomNumber;
	}

	/**
	 * @return the sessionData
	 */
	public SessionDataBean getSessionData()
	{
		return sessionData;
	}

	/**
	 * @param sessionData the sessionData to set
	 */
	public void setSessionData(SessionDataBean sessionData)
	{
		this.sessionData = sessionData;
	}

	/**
	 * @return the uniqueIdNodesMap
	 */
	public Map<String, OutputTreeDataNode> getUniqueIdNodesMap()
	{
		return uniqueIdNodesMap;
	}

	/**
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
	 * @return the auditEventId
	 */
	public long getAuditEventId() {
		return auditEventId;
	}

	/**
	 * @param auditEventId the auditEventId to set
	 */
	public void setAuditEventId(long auditEventId) {
		this.auditEventId = auditEventId;
	}
}
