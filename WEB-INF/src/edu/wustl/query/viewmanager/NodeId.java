package edu.wustl.query.viewmanager;

import edu.wustl.query.util.global.Constants;


public class NodeId
{
	private String rootData;

	private String treeNo;
	private String uniqueParentNodeId;
	private String[] parentData;
	
	private String uniqueCurrentNodeId;
	private String[] currentNodeData;

	public NodeId(String idOfClickedNode)
	{
		String[] nodeIds = idOfClickedNode.split(Constants.NODE_SEPARATOR);
		rootData = nodeIds[0];
		
		String parentNode = nodeIds[1];
		String[] splitParentNodeId = parentNode.split(Constants.UNDERSCORE);
		treeNo = splitParentNodeId[0];
		String parentNodeId = splitParentNodeId[1];
		uniqueParentNodeId = treeNo + Constants.UNDERSCORE + parentNodeId;
		
		if(!idOfClickedNode.endsWith(Constants.LABEL_TREE_NODE))
		{
			parentData  = new String[splitParentNodeId.length-2];
			for(int i=2;i<splitParentNodeId.length;i++)
			{
				parentData[i-2] = splitParentNodeId[i];
			}
		}
		
		String currentNode = nodeIds[2];
		String[] splitCurrentNodeId = currentNode.split(Constants.UNDERSCORE);
		String currentNodeId = splitCurrentNodeId[1];
		uniqueCurrentNodeId = treeNo + Constants.UNDERSCORE + currentNodeId;
		
		if(!idOfClickedNode.endsWith(Constants.LABEL_TREE_NODE))
		{
			currentNodeData  = new String[splitCurrentNodeId.length-2];
			for(int i=2;i<splitCurrentNodeId.length;i++)
			{
				currentNodeData[i-2] = splitCurrentNodeId[i];
			}
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
}
