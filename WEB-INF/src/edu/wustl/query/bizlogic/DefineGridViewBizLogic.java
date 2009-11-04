
package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.util.Utility;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleConstants;

/**
 * This bizlogic is called when user wants to define columns to be shown for grid results.
 *
 * @author deepti_shelar
 *
 */
public class DefineGridViewBizLogic
{

	/**
	 * This list denotes the selected columns in a session.
	 */
	//List<NameValueBean> prevSelectedColumnList;
	/**
	 * This method returns main Entities.
	 * @param queryDetailsObj query Details Object
	 * @param mainEntityList main entity list
	 * @param rootEntitiesList root entity list
	 * @return mainEntityIdList main entity id list
	 */
	private List<String> getMainEntityIdList(QueryDetails queryDetailsObj,
			List<Integer> mainEntityList, List<Integer> rootEntitiesList)
	{
		List<String> mainEntityIdList = new ArrayList<String>();
		Set<String> keySet = queryDetailsObj.getUniqueIdNodesMap().keySet();
		Iterator<Integer> itr = mainEntityList.iterator();
		while (itr.hasNext())
		{
			int mainEntityExpId = itr.next().intValue();
			Iterator<String> iterator = keySet.iterator();
			Object keyId;
			while (iterator.hasNext())
			{
				keyId = iterator.next();
				String nodeId = keyId.toString();
				OutputTreeDataNode node = queryDetailsObj.getUniqueIdNodesMap().get(nodeId);
				int nodeExpId = node.getExpressionId();
				if (mainEntityExpId == nodeExpId
						&& (!isRootEntityOfQuery(nodeExpId, rootEntitiesList))
						&& (!mainEntityIdList.contains(nodeId)))
				{
					mainEntityIdList.add(nodeId);
					break;
				}
			}
		}
		return mainEntityIdList;
	}

	/**
	 * @param entityExpId - Expression id
	 * @param rootEntitiesList - Entity list
	 * @return boolean
	 */
	private boolean isRootEntityOfQuery(int entityExpId, List<Integer> rootEntitiesList)
	{
		boolean isRootEntity = false;
		Iterator<Integer> itr = rootEntitiesList.iterator();
		while (itr.hasNext())
		{
			int rootEntityExpId = itr.next().intValue();
			if (entityExpId == rootEntityExpId)
			{
				isRootEntity = true;
				break;
			}
		}
		return isRootEntity;
	}

	/**
	 * This method cs XML String for containment tree.
	 * @param queryDetailsObj query details object
	 * @param xmlString xml string
	 * @return The method returns the XML string to create a containment tree
	 */
	public StringBuilder createContainmentTree(QueryDetails queryDetailsObj, StringBuilder xmlString)
	{
		//this.prevSelectedColumnNameValueBeanList = prevSelectedColumnNVBList;
		List<Integer> mainEntityExpIdsList = queryDetailsObj.getMainEntityList();
		if (!queryDetailsObj.getUniqueIdNodesMap().isEmpty())
		{
			//Adding root node of tree
			addRootNode(xmlString);

			//This List will maintain list of all root entities in Query
			List<Integer> rootNodesExpIdsList = new ArrayList<Integer>();
			List<OutputTreeDataNode> treeNodeList = queryDetailsObj.getRootOutputTreeNodeList();
			Iterator<OutputTreeDataNode> rootNodeItr = treeNodeList.iterator();
			while (rootNodeItr.hasNext())
			{
				OutputTreeDataNode curSelectedObject = rootNodeItr.next();
				queryDetailsObj.setCurrentSelectedObject(curSelectedObject);
				rootNodesExpIdsList.add(Integer.valueOf(curSelectedObject.getExpressionId()));

				//Add each root node tree and it's corresponding Containment Entities
				addIQueryRootNodeToTree(queryDetailsObj, xmlString);
				xmlString.append(Constants.Closing_Item);
			}
			//Get List of all main Entity Id's, except IQuery root nodes
			List<String> mainEntityIdList = getMainEntityIdList(queryDetailsObj,
					mainEntityExpIdsList, rootNodesExpIdsList);

			//After IQuery tree node, Add Main Entities Nodes and their attributes to Tree,
			// avoiding the root node of the Query
			addAllMainEntitiesToTree(queryDetailsObj, mainEntityIdList, xmlString);
		}

		return xmlString;
	}

	/**
	 * This method add all main entities to containment tree except root node
	 * @param queryDetailsObj query details object
	 * @param mainEntityIdList main entity id list
	 * @param xmlString xml String
	 */
	private void addAllMainEntitiesToTree(QueryDetails queryDetailsObj,
			List<String> mainEntityIdList, StringBuilder xmlString)
	{
		OutputTreeDataNode curSelectedObject;
		for (int i = 0; i < mainEntityIdList.size(); i++)
		{
			String nodeId = mainEntityIdList.get(i);
			curSelectedObject = queryDetailsObj.getUniqueIdNodesMap().get(nodeId);
			queryDetailsObj.setCurrentSelectedObject(curSelectedObject);
			addEntityAndAttributes(queryDetailsObj, true, xmlString);

			//For each main Entity add all Containment
			addAllContainmentsOfMainExpression(queryDetailsObj, xmlString);
			xmlString.append(Constants.Closing_Item);
		}
	}

	/**
	 * This method adds the root node of IQuery to containment tree
	 * @param queryDetailsObj query details object
	 * @param xmlString xml String
	 */
	private void addIQueryRootNodeToTree(QueryDetails queryDetailsObj, StringBuilder xmlString)
	{
		Map<Integer, String> parentNodesIdMap = new HashMap<Integer, String>();
		queryDetailsObj.setParentNodesIdMap(parentNodesIdMap);
		addEntityAndAttributes(queryDetailsObj, true, xmlString);

		//Add all the containments of the Main Entity
		addAllContainmentsOfMainExpression(queryDetailsObj, xmlString);
	}

	/**
	 * Adds root node to the tree, its a just a label node saying classes present in query.
	 * @param xmlString  vector to store tree data
	 */
	private void addRootNode(StringBuilder xmlString)
	{
		//Creating the XML string
		xmlString.append("<tree id=\"0\">");
		/*xmlString.append("<item id = \""+ Constants.ROOT+ "\" text = \""
		 				+ Constants.CLASSES_PRESENT_IN_QUERY
		 				+ "\"  style=\"font-family:Arial, "
		 				+ "Helvetica, sans-serif;font-size: 12px;color: #3c3c3c;\" "
		 				+ "name= \""+ Constants.ROOT +"\" parent= \""
		 				+ Constants.ZERO_ID + "\" parentObjName = "+ "\"\"" +">"); */
		xmlString
				.append("<item id = \""
						+ edu.wustl.common.util.global.Constants.ROOT
						+ "\" text = \""
						+ Constants.CLASSES_PRESENT_IN_QUERY
						+ "\"  style=\"font-family:Arial, Helvetica, sans-serif;font-size: 12px;color: #3c3c3c;\" name= \""
						+ edu.wustl.common.util.global.Constants.ROOT + "\" parent= \""
						+ Constants.ZERO_ID + "\" parentObjName = " + "\"\"" + ">");
	}

	/**
	 * This method adds class node to the tree.
	 * And calls addAttributeNodes method to add attribute nodes to tree.
	 * @param queryDetailsObj query details object
	 * @param isMainEntity is main enetity boolean
	 * @param xmlString  xml string
	 */
	private void addEntityAndAttributes(QueryDetails queryDetailsObj, boolean isMainEntity,
			StringBuilder xmlString)
	{
		StringBuilder newClassName = new StringBuilder();
		//parentNodesIdMap will store expression id of main entity and it's treeClassNodeId
		Map<Integer, String> parentNodesIdMap = queryDetailsObj.getParentNodesIdMap();
		OutputTreeDataNode currentSelectedObject = queryDetailsObj.getCurrentSelectedObject();
		String uniqueNodeId = currentSelectedObject.getUniqueNodeId();
		IOutputEntity outputEntity = currentSelectedObject.getOutputEntity();

		Map<Integer, List<Integer>> mainEntityContainmentIdsMap = queryDetailsObj
				.getMainExpEntityExpressionIdMap();
		int expressionId = currentSelectedObject.getExpressionId();

		QueryableObjectInterface dynamicExtensionsEntity = outputEntity
				.getDynamicExtensionsEntity();
		newClassName.append(dynamicExtensionsEntity.getName());
		String classDisplayName = Utility.getDisplayLabel(newClassName.toString());
		String treeClassNodeId = Constants.CLASS + Constants.UNDERSCORE + uniqueNodeId
				+ Constants.UNDERSCORE + newClassName.toString();
		String parentId;
		if (isMainEntity)
		{
			//If it's a Main Entity , it's always going to be under main Entity
			parentId = Constants.ZERO_ID;
			parentNodesIdMap.put(Integer.valueOf(expressionId), treeClassNodeId);
		}
		else
		{
			//If its a containment Entity
			parentId = getParentForChildEntity(parentNodesIdMap, expressionId,
					mainEntityContainmentIdsMap);
		}
		xmlString
				.append("<item id = \""
						+ treeClassNodeId
						+ "\" text = \""
						+ classDisplayName
						+ "\" style=\"font-family:Arial, Helvetica, sans-serif;font-size: 12px;color: #3c3c3c;\" imheight=\"18\" imwidth=\"18\" name= \""
						+ newClassName.toString() + "\" parent= \"" + parentId
						+ "\" parentObjName = " + "\"\"" + ">");
		addAttributeToClassEntity(newClassName.toString(), treeClassNodeId, currentSelectedObject
				.getAttributes(), xmlString);
		if (!isMainEntity)
		{
			xmlString.append(Constants.Closing_Item);
		}
	}

	/**
	 * This method adds all containments of a main entity to containment tree.
	 * @param queryDetailsObj query details object
	 * @param xmlString xml string
	 */
	private void addAllContainmentsOfMainExpression(QueryDetails queryDetailsObj,
			StringBuilder xmlString)
	{
		OutputTreeDataNode currentSelectedObject = queryDetailsObj.getCurrentSelectedObject();

		int mainExpId = currentSelectedObject.getExpressionId();

		//From containmentMap get all the containment of the main Entity
		List<String> containmentEntitiesIds = getContainmentIdsList(queryDetailsObj, mainExpId);

		//Here u get the list of Unique ID's of all Containments Entities
		Iterator<String> itr = containmentEntitiesIds.iterator();
		String uniqueKeyId;
		while (itr.hasNext())
		{
			uniqueKeyId = itr.next();
			currentSelectedObject = queryDetailsObj.getUniqueIdNodesMap().get(uniqueKeyId);

			//Get current selected Object expression ID
			int expId = currentSelectedObject.getExpressionId();
			IQuery query = queryDetailsObj.getQuery();
			IConstraints constaraints = query.getConstraints();
			IExpression expression = constaraints.getExpression(expId);
			if (expression.isVisible() && expression.isInView())
			{
				queryDetailsObj.setCurrentSelectedObject(currentSelectedObject);
				addEntityAndAttributes(queryDetailsObj, false, xmlString);
			}
		}
	}

	/**Method to get id list of containments.
	 * @param queryDetailsObj query details object
	 * @param mainExpId main expression id
	 * @return containmentEntitiesIds list of containment entities id
	 */
	private List<String> getContainmentIdsList(QueryDetails queryDetailsObj, int mainExpId)
	{
		OutputTreeDataNode currentSelectedObject;
		List<String> containmentEntitiesIds = new ArrayList<String>();
		Map<Integer, List<Integer>> mainEntityContainmentIdsMap = queryDetailsObj
				.getMainExpEntityExpressionIdMap();
		List<Integer> expressionIdsList = mainEntityContainmentIdsMap.get(Integer
				.valueOf(mainExpId));
		Iterator<Integer> expsIdsItr = expressionIdsList.iterator();
		Set<String> keySet = queryDetailsObj.getUniqueIdNodesMap().keySet();
		while (expsIdsItr.hasNext())
		{
			Integer expId = expsIdsItr.next();
			String uniqueKeyId = "";
			Iterator<String> keyItr = keySet.iterator();
			while (keyItr.hasNext())
			{
				uniqueKeyId = keyItr.next();
				currentSelectedObject = queryDetailsObj.getUniqueIdNodesMap().get(uniqueKeyId);
				int entityEpxId = currentSelectedObject.getExpressionId();
				if ((entityEpxId == expId.intValue()) && (expId.intValue() != mainExpId))
				{
					containmentEntitiesIds.add(uniqueKeyId);
					break;
				}
			}
		}
		return containmentEntitiesIds;
	}

	/**Method to add attribute to class entity.
	 * @param className class name
	 * @param treeClassNodeId tree class node id
	 * @param attributeMetadataList attribute metadat list
	 * @param xmlString xml string
	 */
	private void addAttributeToClassEntity(String className, String treeClassNodeId,
			List<QueryOutputTreeAttributeMetadata> attributeMetadataList, StringBuilder xmlString)
	{
		QueryableAttributeInterface attribute;
		String attributeName;
		String attributeDisplayName;
		String treeAttributeNodeId;
		for (QueryOutputTreeAttributeMetadata attributeMetadata : attributeMetadataList)
		{
			attribute = attributeMetadata.getAttribute();
			boolean isNotViewable = edu.wustl.query.util.global.Utility.isNotViewable(attribute);
			boolean isVIHidden = attribute.isTagPresent(Constants.TAGGED_VALUE_VI_HIDDEN);
			if (isNotViewable || isVIHidden)
			{
				continue;
			}
			attributeName = attribute.getDisplayName();
			attributeDisplayName = Utility.getDisplayLabel(attributeName);
			treeAttributeNodeId = attributeMetadata.getUniqueId();
			xmlString
					.append("<item id = \""
							+ treeAttributeNodeId
							+ "\" text = \""
							+ attributeDisplayName
							+ "\" style=\"font-family:Arial, Helvetica, sans-serif;font-size: 12px;color: #3c3c3c;\" imheight=\"18\" imwidtht=\"18\"  name= \""
							+ attributeName + "\" parent= \"" + treeClassNodeId
							+ "\" parentObjName = \"" + className + "\"" + ">");
			xmlString.append(Constants.Closing_Item);
		}
	}

	/**Method to get parent for child entity.
	 * @param parentNodesIdMap parent nodes id map
	 * @param entityExpId entity expression id
	 * @param mainExpEntityExpressionIdMap main expression entity vs entity expression id map
	 * @return parentIdString parent id as string
	 */
	private String getParentForChildEntity(Map<Integer, String> parentNodesIdMap, int entityExpId,
			Map<Integer, List<Integer>> mainExpEntityExpressionIdMap)
	{
		//Else it will be one of the containment of the main entity, so it's parent should be main entity
		Integer mainEntityExpId = getMainEntityIdForChildEntity(entityExpId,
				mainExpEntityExpressionIdMap);
		String parentIdString = "";
		if (mainEntityExpId != null)
		{
			parentIdString = parentNodesIdMap.get(mainEntityExpId);
		}
		return parentIdString;
	}

	/**Method to get main entity for child entity.
	 * @param entityExpId entity expression id.
	 * @param mainExpEntityExpressionIdMap main expression entity vs entity expression id map
	 * @return mainExpressionId main expression id
	 */
	private Integer getMainEntityIdForChildEntity(int entityExpId,
			Map<Integer, List<Integer>> mainExpEntityExpressionIdMap)
	{
		Integer mainExpressionId = null;
		//Set<Integer> mainExpskeySet = mainExpEntityExpressionIdMap.keySet();
		//Iterator< Integer> keySetItr = mainExpskeySet.iterator();
		Iterator<Map.Entry<Integer, List<Integer>>> entryItr = mainExpEntityExpressionIdMap
				.entrySet().iterator();
		while (entryItr.hasNext())
		{
			//Integer mainExpId = keySetItr.next();
			Map.Entry<Integer, List<Integer>> entry = entryItr.next();
			List<Integer> expIdsList = entry.getValue();
			Iterator<Integer> expIdsItr = expIdsList.iterator();
			while (expIdsItr.hasNext())
			{
				Integer expId = expIdsItr.next();
				if (entityExpId == expId.intValue())
				{
					mainExpressionId = entry.getKey();
					break;
				}
			}
		}
		return mainExpressionId;
	}

	/**
	 * returns list of seletced columns
	 * @param selectedColumnIds selected columns id
	 * @param queryDetailsObj query details object
	 * @param selectedColumnsMetadata SelectedColumnsMetadata
	 */
	public void getSelectedColumnsMetadata(String[] selectedColumnIds,
			QueryDetails queryDetailsObj, SelectedColumnsMetadata selectedColumnsMetadata)
	{
		List<IOutputAttribute> outputAttributeList = new ArrayList<IOutputAttribute>();
		IOutputAttribute attr;
		String columnId;
		String expressionId;
		String attrId;
		int lenOfCIds = selectedColumnIds.length;
		for (int i = 0; i < lenOfCIds; i++)
		{
			attr = null;
			columnId = selectedColumnIds[i];
			String[] split = columnId.split(Constants.EXPRESSION_ID_SEPARATOR);
			expressionId = split[QueryModuleConstants.INDEX_PARAM_ZERO];
			attrId = split[Constants.ONE];
			IExpression expression = queryDetailsObj.getQuery().getConstraints().getExpression(
					Integer.valueOf(expressionId));
			QueryableObjectInterface queryableObject = expression.getQueryEntity()
					.getDynamicExtensionsEntity();
			QueryableAttributeInterface queryableAttribute = queryableObject
					.getAttributeByIdentifier(Long.valueOf(attrId));
			if (queryableAttribute.getName().equals(Constants.NAME)
					&& queryableAttribute.getActualEntity().getName().equals(
							Constants.MED_ENTITY_NAME))
			{
				addAttribute(queryableAttribute, outputAttributeList, expression);
			}
			attr = new OutputAttribute(expression, queryableAttribute);
			outputAttributeList.add(attr);
		}
		selectedColumnsMetadata.setSelectedOutputAttributeList(outputAttributeList);
	}

	/**Method to add output attribute for MED.
	 * @param attrib - Attribute
	 * @param outputAttributeList - output attribute list
	 * @param expression expression object
	 */
	private void addAttribute(QueryableAttributeInterface attrib,
			List<IOutputAttribute> outputAttributeList, IExpression expression)
	{
		IOutputAttribute attr;
		QueryableAttributeInterface attribute = expression.getQueryEntity()
				.getDynamicExtensionsEntity().getAttributeByName(attrib, Constants.ID);
		attr = new OutputAttribute(expression, attribute);
		outputAttributeList.add(attr);
	}
}
