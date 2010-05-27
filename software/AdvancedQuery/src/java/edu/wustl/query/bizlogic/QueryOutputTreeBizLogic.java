
package edu.wustl.query.bizlogic;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.QueryTreeNodeData;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.beans.QueryResultObjectDataBean;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryCSMUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleSqlUtil;
import edu.wustl.query.util.querysuite.QueryModuleUtil;
import edu.wustl.security.exception.SMException;

/**
 * Creates QueryOutputTree Object as per the data filled by the user on AddLimits section.
 * Creates QueryOutputTree Table.
 * @author deepti_shelar
 */
public class QueryOutputTreeBizLogic
{
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(QueryOutputTreeBizLogic.class);
	/**
	 * Creates a temporary table to store query results.
	 * @param selectSql the query
	 * @param queryDetailsObj QueryDetails object
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 */
    public void createOutputTreeTable(String selectSql, QueryDetails queryDetailsObj)
    throws DAOException, SQLException
    {
        String tableName = AQConstants.TEMP_OUPUT_TREE_TABLE_NAME
        + queryDetailsObj.getSessionData().getUserId() + queryDetailsObj.getRandomNumber();
        QueryModuleSqlUtil.executeCreateTable(tableName, selectSql, queryDetailsObj);
        QueryModuleSqlUtil.updateAuditQueryDetails(AQConstants.TEMP_TABLE_NAME,
        		tableName,queryDetailsObj.getAuditEventId());
    }

    /**
     * This method creates first level(Default) output tree data.
     * @param treeNo treeNo
     * @param root root
     * @param hasConditionOnIdentifiedField hasConditionOnIdentifiedField
     * @param queryDetailsObj queryDetailsObj
     * @return Vector<QueryTreeNodeData> data structure to form tree out of it.
     * @throws DAOException DAOException
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws BizLogicException BizLogicException
     * @throws SMException Security Manager Exception
     * @throws SQLException SQLException
     */
    public List<QueryTreeNodeData> createDefaultOutputTreeData(int treeNo,
            OutputTreeDataNode root, boolean hasConditionOnIdentifiedField,
            QueryDetails queryDetailsObj) throws DAOException, ClassNotFoundException, BizLogicException,
            SMException, SQLException
    {
        String tableName = AQConstants.TEMP_OUPUT_TREE_TABLE_NAME
                + queryDetailsObj.getSessionData().getUserId() + queryDetailsObj.getRandomNumber();
        QueryResultObjectDataBean queryResulObjectDataBean = QueryCSMUtil
                .getQueryResulObjectDataBean(root, queryDetailsObj);
        Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap =
        	new HashMap<Long, QueryResultObjectDataBean>();
        queryResultObjectDataBeanMap.put(root.getId(), queryResulObjectDataBean);
        String selectSql = QueryModuleSqlUtil.getSQLForRootNode(tableName, QueryModuleUtil
                .getColumnNamesForSelectpart(root.getAttributes(), queryDetailsObj, queryResultObjectDataBeanMap
                        .get(root.getId())));

        String[] sqlIndex = selectSql.split(AQConstants.NODE_SEPARATOR);
        selectSql = sqlIndex[0];
        int index = Integer.parseInt(sqlIndex[1]);

        QueryCsmBizLogic queryCsmBizLogic = new QueryCsmBizLogic();
        List dataList = queryCsmBizLogic.executeCSMQuery(selectSql, queryDetailsObj,
                queryResultObjectDataBeanMap, root, hasConditionOnIdentifiedField,0,
                Variables.maximumTreeNodeLimit);
        int count = QueryModuleSqlUtil.getCountForQuery(selectSql,queryDetailsObj);
        updateQueryAuditDetails(root,count,queryDetailsObj.getAuditEventId());
        List<QueryTreeNodeData> treeDataVector = new ArrayList<QueryTreeNodeData>();
        if (dataList != null && !dataList.isEmpty())
        {
        	String size = getTreeSize(dataList, count);
            QueryTreeNodeData treeNode = new QueryTreeNodeData();
            String name = root.getOutputEntity().getDynamicExtensionsEntity().getName();
            name = edu.wustl.query.util.global.Utility.parseClassName(name);
            String displayName = /*Utility.getDisplayLabel*/(name) + " (" + size + ")";
            String nodeId = createNodeId(treeNo, root);
            displayName = AQConstants.TREE_NODE_FONT + displayName + AQConstants.TREE_NODE_FONT_CLOSE;
            treeNode.setIdentifier(nodeId);
            treeNode.setObjectName(name);
            treeNode.setDisplayName(displayName);
            treeNode.setParentIdentifier(AQConstants.ZERO_ID);
            treeNode.setParentObjectName("");
            treeDataVector.add(treeNode);
            treeDataVector = addNodeToTree(index, dataList, treeNode, root, treeDataVector);
        }
        return treeDataVector;
    }

    /**
     * Gets the tree size.
     * @param dataList dataList
     * @param count count
     * @return size
     */
	private String getTreeSize(List dataList, int count)
	{
		//String  size1 = ""+dataList.size();
		StringBuffer size = new StringBuffer();
		size.append(dataList.size());
		if(dataList.size()==Variables.maximumTreeNodeLimit)
		{
			//size = size + "/" +count;
			size.append('/');
			size.append(count);
		}
		return size.toString();
	}
    /**
     * Updates the root node name and count for root records in audit tables.
     * @param root root node object
     * @param cntOfRootRecs records
     * @param auditEventId id of audit_event table
     * @throws DAOException exception
     * @throws SQLException SQLException
     */
    private void updateQueryAuditDetails(OutputTreeDataNode root, int cntOfRootRecs,
    		long auditEventId) throws DAOException, SQLException
    {
    	String rootEntityName = root.getOutputEntity().getDynamicExtensionsEntity().getName();
		QueryModuleSqlUtil.updateAuditQueryDetails(AQConstants.ROOT_ENTITY_NAME,
				rootEntityName, auditEventId);
		QueryModuleSqlUtil.updateAuditQueryDetails(AQConstants.COUNT_OF_ROOT_RECORDS,
				""+cntOfRootRecs, auditEventId);
	}

	/**
     * @param treeNo treeNo
     * @param root root node
     * @return nodeId nodeId
     */
    private String createNodeId(int treeNo, OutputTreeDataNode root)
    {
        String nodeId = treeNo + "_" + AQConstants.NULL_ID + AQConstants.NODE_SEPARATOR
                + root.getUniqueNodeId() + AQConstants.UNDERSCORE + AQConstants.LABEL_TREE_NODE;
        return nodeId;
    }

    /**
     * Encrypts the id to be set for tree node.
     * @param nodeId String original id
     * @return encrypted id
     */
    public String encryptId(String nodeId)
    {
    	return AQConstants.UNIQUE_ID_SEPARATOR + nodeId;
    }

    /**
     * Decrypts the id to be set for tree node.
     * @param nodeId nodeId
     * @return id Decrypted id
     */
    public String decryptId(String nodeId)
    {
        int indexOfSeparator = nodeId.indexOf(AQConstants.UNIQUE_ID_SEPARATOR);
        String decryptedId = nodeId;
        if (indexOfSeparator != -1)
        {
            decryptedId = nodeId.substring(indexOfSeparator
                    + AQConstants.UNIQUE_ID_SEPARATOR.length());
        }
        return decryptedId;
    }

    /**
     * This method adds the node to tree.The id for new node is set as
     * 'Id of OutputTreeNode _id value of that node in newly created table'.
     * @param dataList all records in database satisfying the criteria.
     * @param parentNode parent node of tree data
     * @param node the node to be added of OutputTreeDataNode.
     * @param treeDataVector  data structure to form tree out of it.
     * with their information like attributes and actual column names in database.
     * @return treeDataVector  data structure to form tree out of it.
     */
    private List<QueryTreeNodeData> addNodeToTree(int index, List dataList,
            QueryTreeNodeData parentNode, OutputTreeDataNode node,
            List<QueryTreeNodeData> treeDataVector)
    {
        Iterator dataListIterator = dataList.iterator();
        List rowList;
        String uniqueNodeId = node.getUniqueNodeId();
        String parentNodeId = uniqueNodeId + AQConstants.UNDERSCORE + AQConstants.LABEL_TREE_NODE;

        while (dataListIterator.hasNext())
        {
            rowList = (List) dataListIterator.next();
            QueryTreeNodeData treeNode = null;
            String data = (String) rowList.get(0);
            String currentNodeId = uniqueNodeId + AQConstants.UNDERSCORE + data;
            if (data.contains("#"))
            {
                currentNodeId = uniqueNodeId + AQConstants.UNDERSCORE + AQConstants.HASHED_NODE_ID;
            }
            String nodeIdToSet = parentNodeId + AQConstants.NODE_SEPARATOR + currentNodeId;
            nodeIdToSet = encryptId(nodeIdToSet);
            treeNode = new QueryTreeNodeData();
            treeNode.setIdentifier(nodeIdToSet);
            EntityInterface dynExtEntity = node.getOutputEntity().getDynamicExtensionsEntity();
            String fullyQualifiedEntityName = dynExtEntity.getName();
            treeNode.setObjectName(fullyQualifiedEntityName);
            String displayName = data;
            if (index != -1)
            {
            	if(rowList.size()>1)
            	{
            		if(!rowList.contains(AQConstants.HASHED_OUT))
            		{
		            	displayName = (String) rowList.get(1);
		            	if(displayName.contains("\""))
		            	{
		            		displayName = displayName.replace("\"", "\\\"");
		            	}
		            	if(rowList.get(1).equals(""))
		            	{
		            		displayName = ApplicationProperties.getValue(AQConstants.LABEL_NA);
		            	}
		            	for(int i=2;i<rowList.size()-2;i++)
		            	{
		            		if(rowList.get(i).equals(""))
		            		{
		            			displayName = displayName + ", " + ApplicationProperties.getValue(AQConstants.LABEL_NA);
		            		}
		            		else
		            		{
		            			if(rowList.get(i).toString().contains("\""))
				            	{
		            				displayName = displayName + ", " + rowList.get(i).toString().replace("\"", "\\\"");
				            	}
		            			else
		            			{
		            				displayName = displayName + ", " + (String) rowList.get(i);
		            			}
		            		}
		            	}
            		}
	            	if(rowList.size() > AQConstants.TWO)
	            	{
	            		displayName = calculateAge(rowList, displayName);
	            	}
            	}
                if ("".equals(displayName))
                {
                    displayName = ApplicationProperties.getValue(AQConstants.LABEL_NA);
                }
            }
            treeNode.setDisplayName(displayName);
            treeNode.setParentIdentifier(parentNode.getIdentifier().toString());
            treeNode.setParentObjectName(parentNode.getObjectName());
            treeDataVector.add(treeNode);
        }
        return treeDataVector;
    }

    /**
     * Calculate the age to be displayed in the tree view.
     * @param rowList The list containing the results
     * @param displayName String to be displayed in the tree view.
     * @return displayName String to be displayed in the tree view along with the age.
     */
	private String calculateAge(List rowList, String displayName)
	{
		String birthDate = (String)rowList.get(rowList.size()-AQConstants.TWO);
		String deathDate = (String)rowList.get(rowList.size()-1);
		if(displayName.contains("#"))
		{
			displayName = displayName + " ("+AQConstants.HASH_OUT+")";
		}
		else
		{
			if("".equals(birthDate))
			{
				displayName = displayName + " (" + ApplicationProperties.getValue(AQConstants.LABEL_NA) + ")";
			}
			else
			{
				DateFormat dFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
				Date birthDt = null;
				Date currentDate=null;
				Date deathDt = null;
				try
				{
					birthDt = new Date(dFormat.parse(birthDate).getTime()) ;
					if("".equals(deathDate))
					{
						currentDate = new Date();
					}
					else
					{
						deathDt = new Date(dFormat.parse(deathDate).getTime());
						currentDate = deathDt;
					}
				}
				catch (java.text.ParseException e)
				{
					logger.error(e.getMessage(),e);
				}
				Calendar tempCurrentDate = GregorianCalendar.getInstance();
				tempCurrentDate.setTime(currentDate);
				tempCurrentDate.get(Calendar.YEAR);
				Calendar tempDateOfBirth = GregorianCalendar.getInstance();
				tempDateOfBirth.setTime(birthDt);

				int age = tempCurrentDate.get(Calendar.YEAR) - tempDateOfBirth.get(Calendar.YEAR);
				int ageMonths;

				if(tempCurrentDate.before(new GregorianCalendar(tempCurrentDate.get(Calendar.YEAR), tempDateOfBirth.get(Calendar.MONTH), tempDateOfBirth.get(Calendar.DAY_OF_MONTH))))
				{
					  age--;
				      ageMonths = (12 - (tempDateOfBirth.get(Calendar.MONTH) + 1)) + (tempCurrentDate.get(Calendar.MONTH) + 1);
				}
				else if(tempCurrentDate.after(new GregorianCalendar(tempCurrentDate.get(Calendar.YEAR), tempDateOfBirth.get(Calendar.MONTH), tempDateOfBirth.get(Calendar.DAY_OF_MONTH))))
				{
				      ageMonths = ((tempCurrentDate.get(Calendar.MONTH) + 1) - ((tempDateOfBirth.get(Calendar.MONTH) + 1)));
				}
				else
				{
					age = tempCurrentDate.get(Calendar.YEAR) - tempDateOfBirth.get(Calendar.YEAR);
				    ageMonths = 0;
				}
				if(ageMonths >= 6)
				{
					age = age + 1;
				}
				if(rowList.contains(AQConstants.HASHED_OUT) && age>90)
				{
					displayName = displayName + " (Age > 90)";
				}
				else
				{
					displayName = displayName + " (" + age +")";
				}
			}
		}
		return displayName;
	}

    /**
     * Updates tree when user clicks on any of the nodes.
     * @param identifier string id of the node
     * @param node node clicked
     * @param idColumnMap map which stores all node ids  with their information like attributes and actual column names in database.
     * @param parentNodeId string id of parent
     * @param sessionData SessionDataBean to be sent for execute query
     * @return string representing tree node structure
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws DAOException DAOException
     * @throws BizLogicException BizLogicException
     * @throws SMException Security Manager exception
     */
    public String updateTreeForDataNode(String identifier, OutputTreeDataNode node, String parentNodeId,
            QueryDetails queryDetailsObj) throws ClassNotFoundException, DAOException, BizLogicException,
            SMException
    {
        String tableName = AQConstants.TEMP_OUPUT_TREE_TABLE_NAME
                + queryDetailsObj.getSessionData().getUserId() + queryDetailsObj.getRandomNumber();
        String parentIdColumnName = QueryModuleUtil.getParentIdColumnName(node);
        List<OutputTreeDataNode> children = node.getChildren();
        String outputTreeStr = "";
        for (OutputTreeDataNode childNode : children)
        {
            String selectSql = getSql(parentNodeId, tableName, parentIdColumnName, childNode);
            String name = childNode.getOutputEntity().getDynamicExtensionsEntity().getName();
            name = edu.wustl.query.util.global.Utility.parseClassName(name);
            List<List<String>> dataList = getTreeDataList(queryDetailsObj, selectSql, null, false,node);
            //List dataList = QueryModuleUtil.executeQuery(selectSql, sessionData);
           // int size = dataList.size();
            String  size = ""+dataList.size();
			if(dataList.size()==Variables.maxTreeNdLmtForChildNd)
			{
				int count = QueryModuleSqlUtil.getCountForQuery(selectSql,queryDetailsObj);
				size = size + "/" +count;
			}
            if (!dataList.isEmpty())
            {
                String parId = identifier.substring(identifier.lastIndexOf(AQConstants.NODE_SEPARATOR) + 2, identifier
                        .length());
                String childNodeId = childNode.getUniqueNodeId() + AQConstants.UNDERSCORE
                        + AQConstants.LABEL_TREE_NODE;
                String nodeId = AQConstants.UNIQUE_ID_SEPARATOR + parId + AQConstants.NODE_SEPARATOR
                        + childNodeId;
                String displayName = /*Utility.getDisplayLabel*/(name) + " (" + size + ")";
                displayName = AQConstants.TREE_NODE_FONT + displayName
                        + AQConstants.TREE_NODE_FONT_CLOSE;
                String objectName = name;
                String fullName = node.getOutputEntity().getDynamicExtensionsEntity().getName();
                String parentObjectName = edu.wustl.query.util.global.Utility.parseClassName(fullName);
                outputTreeStr = outputTreeStr + "|" + nodeId + "~" + displayName + "~" + objectName
                        + "~" + identifier + "~" + parentObjectName;
            }
        }
        return outputTreeStr;
    }

    /**
     * Returns the sql to be fired and to get data to update tree view.
     * @param idColumnMap map which strores all node ids  with their information like attributes and actual column names in database.
     * @param parentNodeId string id of parent
     * @param tableName name of the table
     * @param parentIdColumnName name of the column of parent node's id
     * @param childNode child node
     * @return String sql to be fired to get data to update tree.
     */
    private String getSql(String parentNodeId, String tableName, String parentIdColumnName,
            OutputTreeDataNode childNode)
    {
        String selectSql = AQConstants.SELECT_DISTINCT;
        String idColumnOfCurrentNode = "";
        List<QueryOutputTreeAttributeMetadata> attributes = childNode.getAttributes();
        String sqlColumnName = "";
        for (QueryOutputTreeAttributeMetadata attributeMetaData : attributes)
        {
            AttributeInterface attribute = attributeMetaData.getAttribute();
            sqlColumnName = attributeMetaData.getColumnName();
            if (attribute.getName().equalsIgnoreCase(AQConstants.IDENTIFIER))
            {
                idColumnOfCurrentNode = sqlColumnName;
            }
            if (!attribute.getAttributeTypeInformation().getDataType().equalsIgnoreCase(
                    AQConstants.FILE_TYPE))
            {
                selectSql = selectSql + sqlColumnName + ",";
            }
        }
        selectSql = selectSql.substring(0, selectSql.lastIndexOf(','));
        selectSql = selectSql + AQConstants.FROM + tableName;
        if (parentNodeId != null)
        {
            selectSql = selectSql + AQConstants.WHERE + " (" + parentIdColumnName + " = '"
                    + parentNodeId + "' " + LogicalOperator.And + " " + idColumnOfCurrentNode + " "
                    + RelationalOperator.getSQL(RelationalOperator.IsNotNull) + ")";
        }
        return selectSql;
    }



    /**
     * This method is called when user clicks on a node present in a tree, to get all the child nodes added to tree.
     * @param nodeId id of the node clicked.
     * @param idNodeMap map which stores id and nodes already added to tree.
     * @param sessionData sessionData session data to get the user id.
     * @param hasConditionOnIdentifiedField
     * @param mainEntityMap
     * @return String outputTreeStr which is then parsed and then sent to client to form tree.
     * String for one node is comma separated for its id, display name, object name , parentId, parent Object name.
     * Such string elements for child nodes are separated by "|".
     * @throws BizLogicException
     */
    public String updateTreeForLabelNode(String nodeId, QueryDetails queryDetailsObj,
            boolean hasConditionOnIdentifiedField) throws ClassNotFoundException, DAOException, BizLogicException
    {
        String tableName = AQConstants.TEMP_OUPUT_TREE_TABLE_NAME
                + queryDetailsObj.getSessionData().getUserId() + queryDetailsObj.getRandomNumber();
        String selectSql = "";
        int index = -1;



        String labelNode = nodeId.substring(nodeId.lastIndexOf(AQConstants.NODE_SEPARATOR) + 2,
                nodeId.length());
        String[] splitIds = labelNode.split(AQConstants.UNDERSCORE);
        String treeNo = splitIds[0];
        String treeNodeId = splitIds[1];
        String uniqueCurrentNodeId = treeNo + "_" + treeNodeId;
        String parentNodeId = nodeId.substring(0, nodeId.indexOf(AQConstants.NODE_SEPARATOR));
        String decryptedId = decryptId(parentNodeId);
        String[] nodeIds = decryptedId.split(AQConstants.UNDERSCORE);
        String parentId = nodeIds[1];
        String parentData = null;
        Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap = null;
        if (nodeIds.length == 3)
        {
            parentData = nodeIds[2];
        }
        String uniqueParentId = treeNo + "_" + parentId;
        OutputTreeDataNode parentNode = queryDetailsObj.getUniqueIdNodesMap().get(uniqueParentId);
        OutputTreeDataNode currentNode = queryDetailsObj.getUniqueIdNodesMap().get(
                uniqueCurrentNodeId);
        if (!parentNodeId.contains(AQConstants.NULL_ID))
        {
            String parentIdColumnName = QueryModuleUtil.getParentIdColumnName(parentNode);
            List<OutputTreeDataNode> children = parentNode.getChildren();
            if (children.isEmpty())
            {
                return "";
            }
            String columnNames = "";
            QueryResultObjectDataBean queryResulObjectDataBean = QueryCSMUtil
                    .getQueryResulObjectDataBean(currentNode, queryDetailsObj);
            queryResultObjectDataBeanMap = new HashMap<Long, QueryResultObjectDataBean>();
            queryResultObjectDataBeanMap.put(currentNode.getId(), queryResulObjectDataBean);
            Map<String, String> columnNameIndexMap = QueryModuleUtil.getColumnNamesForSelectpart(
                    currentNode.getAttributes(), queryDetailsObj, queryResultObjectDataBeanMap.get(currentNode
                            .getId()));
            columnNames = columnNameIndexMap.get(AQConstants.COLUMN_NAMES);
            String indexStr = columnNameIndexMap.get(AQConstants.INDEX);
            if ((indexStr != null) && (!indexStr.equalsIgnoreCase(AQConstants.NULL)))
            {
                index = Integer.valueOf(indexStr);
            }
            //columnNames = columnNames.substring(0, columnNames.lastIndexOf(";"));
            selectSql = "select distinct " + columnNames;
            String idColumnOfCurrentNode = columnNames;
            if (columnNames.indexOf(',') != -1)
            {
                idColumnOfCurrentNode = columnNames.substring(0, columnNames.indexOf(','));
            }
            selectSql =  edu.wustl.query.util.global.Utility.getSQLForNode(parentData, tableName, parentIdColumnName, selectSql,
                    idColumnOfCurrentNode);
        }
        if (parentNodeId.contains(AQConstants.NULL_ID))
        {

            selectSql = QueryModuleSqlUtil.getSQLForRootNode(tableName, QueryModuleUtil
                    .getColumnNamesForSelectpart(currentNode.getAttributes(), queryDetailsObj,
                            queryResultObjectDataBeanMap.get(currentNode.getId())));

            String indexStr = selectSql.substring(selectSql.indexOf(AQConstants.NODE_SEPARATOR) + 2,
                    selectSql.length());
            if (!indexStr.equalsIgnoreCase(AQConstants.NULL))
            {
                index = Integer.valueOf(indexStr);
            }
            selectSql = selectSql.substring(0, selectSql.indexOf(AQConstants.NODE_SEPARATOR));
        }
        List<List<String>> dataList = getTreeDataList(queryDetailsObj, selectSql,
                queryResultObjectDataBeanMap, hasConditionOnIdentifiedField, currentNode);

        //List dataList = QueryModuleUtil.executeQuery(selectSql, sessionData);
        String outputTreeStr = buildOutputTreeString(index, dataList, currentNode, nodeId,
                parentNode, queryDetailsObj);
        return outputTreeStr;
    }

    /**
     * This method builds a string from the input data , based on this string tree will be formed.
     * @param dataList List of result records
     * @param children List<OutputTreeDataNode> child nodes
     * @param nodeId String id which will be parent id for the new nodes added to tree.
     * @param parentNode parent node
     * @param idNodeMap map which stores id and nodes already added to tree.
     * @return String outputTreeStr which is then parsed and then sent to client to form tree.
     * String for one node is separated by '~' for its id, display name, object name , parentId, parent Object name.
     * Such string elements for child nodes are separated by "|".
     **/
    String buildOutputTreeString(int index, List dataList, OutputTreeDataNode currentNode,
            String parentNodeId, OutputTreeDataNode parentNode, QueryDetails queryDetailsObj)
    {
        Iterator dataListIterator = dataList.iterator();
        List<String> existingNodesList = new ArrayList<String>();
        List rowList;
        String outputTreeStr = "";
        while (dataListIterator.hasNext())
        {
            rowList = (List) dataListIterator.next();
            String data = (String) rowList.get(0);
            String fullyQualifiedEntityName = currentNode.getOutputEntity()
                    .getDynamicExtensionsEntity().getName();
            String entityName = edu.wustl.query.util.global.Utility.parseClassName(fullyQualifiedEntityName);
            String currentNodeId = currentNode.getUniqueNodeId() + AQConstants.UNDERSCORE + data;
            if (data.contains("#"))
            {
                currentNodeId = currentNode.getUniqueNodeId() + AQConstants.UNDERSCORE
                        + AQConstants.HASHED_NODE_ID;
            }
            String labelNode = parentNodeId.substring(parentNodeId
                    .lastIndexOf(AQConstants.NODE_SEPARATOR) + 2, parentNodeId.length());
            String nodeIdToSet = AQConstants.UNIQUE_ID_SEPARATOR + labelNode
                    + AQConstants.NODE_SEPARATOR + currentNodeId;
            String displayName = entityName + AQConstants.UNDERSCORE + data;
            if (index != -1 && rowList.size()>1)
            {
            	if(rowList.contains(AQConstants.HASHED_OUT))
            	{
            		displayName = (String) rowList.get(0);
            	}
            	else
            	{
            		displayName = (String) rowList.get(1);
	            	for(int i=2;i<rowList.size()-2;i++)
	            	{
	            		if(rowList.get(i).equals(""))
	            		{
	            			displayName = displayName + ", " + ApplicationProperties.getValue(AQConstants.LABEL_NA);
	            		}
	            		else
	            		{
	            			displayName = displayName + ", " + (String) rowList.get(i);
	            		}
	            	}
            	}
            	if(rowList.size() > 2)
	            {
	            	displayName = calculateAge(rowList, displayName);
	            }
            }
            if (displayName.equalsIgnoreCase(""))
            {
                //displayName = entityName + Constants.UNDERSCORE + data;
                if ("".equals(data))
                {
                    displayName = ApplicationProperties.getValue(AQConstants.LABEL_NA);
                }
                else
                {
                    displayName = data;
                }
            }
            String objectname = fullyQualifiedEntityName;
            String parentObjectName = "";
            if (parentNode != null)
            {
                parentObjectName = parentNode.getOutputEntity().getDynamicExtensionsEntity()
                        .getName();
            }
            if (!existingNodesList.contains(nodeIdToSet))
            {
                existingNodesList.add(nodeIdToSet);
                queryDetailsObj.getUniqueIdNodesMap().put(
                        String.valueOf(currentNode.getUniqueNodeId()), currentNode);
                outputTreeStr = outputTreeStr + nodeIdToSet + "~" + displayName + "~" + objectname
                        + "~" + parentNodeId + "~" + parentObjectName + "|";
            }
        }
        return outputTreeStr;
    }

    /**
     * @param queryDetailsObj queryDetailsObj
     * @param selectSql selectSql
     * @param queryResultObjectDataBeanMap queryResultObjectDataBeanMap
     * @param hasConditionOnIdentifiedField hasConditionOnIdentifiedField
     * @param root root
     * @return dataList
     * @throws DAOException DAOException
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws BizLogicException BizLogicException
     */
    private List<List<String>> getTreeDataList(QueryDetails queryDetailsObj, String selectSql,
            Map<Long, QueryResultObjectDataBean> queryResultObjectDataBeanMap,
            boolean hasConditionOnIdentifiedField,OutputTreeDataNode root) throws ClassNotFoundException, DAOException, BizLogicException
    {
        QuerySessionData querySessionData = new QuerySessionData();
        querySessionData.setSql(selectSql);
        querySessionData.setQueryResultObjectDataMap(queryResultObjectDataBeanMap);
        querySessionData.setSecureExecute(queryDetailsObj.getSessionData().isSecurityRequired());
        querySessionData.setHasConditionOnIdentifiedField(hasConditionOnIdentifiedField);
       /* List<List<String>> dataList = QueryModuleSqlUtil.executeQuery(queryDetailsObj
                .getSessionData(), querySessionData);

        querySessionData.setTotalNumberOfRecords(dataList.size());*/
        QueryCsmBizLogic queryCsmBizLogic = new QueryCsmBizLogic();
		List<List<String>> dataList = queryCsmBizLogic.executeCSMQuery(selectSql, queryDetailsObj,
				queryResultObjectDataBeanMap, root, hasConditionOnIdentifiedField, 0, Variables.maxTreeNdLmtForChildNd);

		querySessionData.setTotalNumberOfRecords(dataList.size());
        return dataList;
    }
}