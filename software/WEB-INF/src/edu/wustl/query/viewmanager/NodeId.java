package edu.wustl.query.viewmanager;

import edu.wustl.query.util.global.Constants;


public class NodeId
{
	/**
	 * Variable for Root Data
	 */
	private transient String rootData;

	/**
	 * Unique Tree number
	 */
	private transient String treeNo;
	/**
	 * Unique parent node id
	 */
	private transient String uniqueParentNodeId;
	/**
	 * Primary Key attribute values for parent node
	 */
	private transient String[] parentData;
	
	/**
	 * Unique current node id
	 */
	private transient String uniqueCurrentNodeId;
	/**
	 * Primary Key attribute values for current node
	 */
	private transient String[] currentNodeData;

	/**
	 * Boolean value to identify node is Label Node or Data Node
	 */
	private transient boolean labelNode;
	
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
			labelNode = true;
		}
		String parentNode = nodeIds[1];
		String[] splitParentNodeId = parentNode.split(Constants.NODE_DATA_SEPARATOR);
		treeNo = splitParentNodeId[0];
		String parentNodeId = splitParentNodeId[1];
		uniqueParentNodeId = treeNo + Constants.NODE_DATA_SEPARATOR + parentNodeId;
		if(labelNode)
		{
			parentData  = splitParentNodeId[2].split("@@");
		}
		
		/*for(int i=2;i<splitParentNodeId.length;i++)
		{
			parentData[i-2] = splitParentNodeId[i];
		}*/
		
		String currentNode = nodeIds[2];
		String[] splitCurrentNodeId = currentNode.split(Constants.NODE_DATA_SEPARATOR);
		String currentNodeId = splitCurrentNodeId[1];
		uniqueCurrentNodeId = treeNo + Constants.NODE_DATA_SEPARATOR + currentNodeId;
		
		if(!labelNode && splitCurrentNodeId.length > 2)
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
		String[] temp = parentData;
		return temp;
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
		String[] temp = currentNodeData;
		return temp;
	}
	
	/**
	 * @return the labelNode
	 */
	public boolean isLabelNode()
	{
		return labelNode;
	}
}
