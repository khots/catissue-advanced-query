package edu.wustl.query.viewmanager;

import edu.wustl.query.util.global.Constants;


public class NodeId
{
	/**
	 * Variable for Root Data
	 */
	private String rootData;

	/**
	 * Unique Tree number
	 */
	private String treeNo;
	/**
	 * Unique parent node id
	 */
	private String uniqueParentNodeId;
	/**
	 * Primary Key attribute values for parent node
	 */
	private String[] parentData;
	
	/**
	 * Unique current node id
	 */
	private String uniqueCurrentNodeId;
	/**
	 * Primary Key attribute values for current node
	 */
	private String[] currentNodeData;

	/**
	 * Boolean value to identify node is Label Node or Data Node
	 */
	private boolean isLabelNode;
	
	/**
	 * Constructor for the class
	 * @param idOfClickedNode id of clicked node
	 */
	public NodeId(String idOfClickedNode)
	{
		String[] nodeIds = idOfClickedNode.split(Constants.NODE_SEPARATOR);
		rootData = nodeIds[0];
		
		if(idOfClickedNode.endsWith(Constants.LABEL_TREE_NODE))
		{
			isLabelNode = true;
		}
		String parentNode = nodeIds[1];
		String[] splitParentNodeId = parentNode.split(Constants.UNDERSCORE);
		treeNo = splitParentNodeId[0];
		String parentNodeId = splitParentNodeId[1];
		uniqueParentNodeId = treeNo + Constants.UNDERSCORE + parentNodeId;
		if(isLabelNode)
		{
			parentData  = splitParentNodeId[2].split("@@");
		}
		
		/*for(int i=2;i<splitParentNodeId.length;i++)
		{
			parentData[i-2] = splitParentNodeId[i];
		}*/
		
		String currentNode = nodeIds[2];
		String[] splitCurrentNodeId = currentNode.split(Constants.UNDERSCORE);
		String currentNodeId = splitCurrentNodeId[1];
		uniqueCurrentNodeId = treeNo + Constants.UNDERSCORE + currentNodeId;
		
		if(!isLabelNode && splitCurrentNodeId.length > 2)
		{
			currentNodeData  = splitCurrentNodeId[2].split("@@");
			/*for(int i=2;i<splitCurrentNodeId.length;i++)
			{
				currentNodeData[i-2] = splitCurrentNodeId[i];
			}*/
		}
		
	}

	/**
	 * @return the rootData
	 */
	public String getRootData()
	{
		return rootData;
	}

	/**
	 * @return the treeNo
	 */
	public String getTreeNo()
	{
		return treeNo;
	}
	
	/**
	 * @return the uniqueParentNodeId
	 */
	public String getUniqueParentNodeId()
	{
		return uniqueParentNodeId;
	}
	
	/**
	 * @return the parentData
	 */
	public String[] getParentData()
	{
		return parentData;
	}
	
	/**
	 * @return the uniqueCurrentNodeId
	 */
	public String getUniqueCurrentNodeId()
	{
		return uniqueCurrentNodeId;
	}
	
	/**
	 * @return the currentNodeData
	 */
	public String[] getCurrentNodeData()
	{
		return currentNodeData;
	}
	
	/**
	 * @return the isLabelNode
	 */
	public boolean isLabelNode()
	{
		return isLabelNode;
	}
}
