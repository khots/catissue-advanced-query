package edu.wustl.query.spreadsheet;

import edu.wustl.query.util.global.Constants;


public class Node
{
	private String treeNo;
	private String parentNodeId;
	private String parentNode;
	private String parentData;
	private String currentNode;//label
	private String currentNodeId;
	private String[] currentNodeData;
	public Node(String idOfClickedNode)
	{
		String[] nodeIds = idOfClickedNode.split(Constants.NODE_SEPARATOR);
		parentNode = nodeIds[0];//data
		String[] spiltParentNodeId = parentNode.split(Constants.UNDERSCORE);
		treeNo = spiltParentNodeId[0];
		parentNodeId = spiltParentNodeId[1];
		if(!parentNode.contains(Constants.NULL))
		{
			parentData = spiltParentNodeId[2];
		}
		String currentNode = nodeIds[1];//label
		currentNodeId = currentNode.substring(currentNode.lastIndexOf(Constants.UNDERSCORE)+1);
		currentNode = currentNode.substring(0,currentNode.lastIndexOf(Constants.UNDERSCORE));
		currentNodeData = currentNode.split(Constants.UNDERSCORE);
	}
	
	/**
	 * @return the treeNo
	 */
	public String getTreeNo()
	{
		return treeNo;
	}
	
	/**
	 * @return the parentNodeId
	 */
	public String getParentNodeId()
	{
		return parentNodeId;
	}
	
	
	/**
	 * @return the parentNode
	 */
	public String getParentNode()
	{
		return parentNode;
	}

	/**
	 * @return the parentData
	 */
	public String getParentData()
	{
		return parentData;
	}
	
	/**
	 * @return the currentNode
	 */
	public String getCurrentNode()
	{
		return currentNode;
	}
	
	/**
	 * @return the currentNodeId
	 */
	public String getCurrentNodeId()
	{
		return currentNodeId;
	}

	
	/**
	 * @return the currentNodeData
	 */
	public String[] getCurrentNodeData()
	{
		return currentNodeData;
	}
}
