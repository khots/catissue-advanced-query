package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;


public class IQueryParseUtil
{
	
	/**
	 * This method separates out all children, starting from  given root node to leaf nodes
	 * This method separates out containment and main children 
	 * @param rootNode
	 */
	public static Map<Integer, List<OutputTreeDataNode>> separateAllChildren(OutputTreeDataNode rootNode,
			IQuery query)
	{
		Map<Integer, List<OutputTreeDataNode>> mainEntitiesContainmentMap = new HashMap<Integer, List<OutputTreeDataNode>>();
		List <OutputTreeDataNode> containmentsTreeDataNodesList = new ArrayList<OutputTreeDataNode>();
		List <OutputTreeDataNode> mainEntitiesTreeDataNodesList = new ArrayList<OutputTreeDataNode>();
		
		List<OutputTreeDataNode> childrenNodes = rootNode.getChildren();
		
		//Now iterate over each children entity and find out if it is any containment entity or any main entity
		separateChildrenNodes(query, childrenNodes, containmentsTreeDataNodesList,mainEntitiesTreeDataNodesList);
	
	    //Now for each children containment of root entity, separate further till leaf nodes
		separateChildrenForEachContainment(containmentsTreeDataNodesList,mainEntitiesTreeDataNodesList,query);
	
		mainEntitiesContainmentMap.put(Integer.valueOf(rootNode.getExpressionId()), containmentsTreeDataNodesList);
	
		//Now for each main Entity , get the containment list
		for(int count =0; count < mainEntitiesTreeDataNodesList.size(); count ++)
		{
			List <OutputTreeDataNode> mainEntityContainmentList = new ArrayList<OutputTreeDataNode>(); 
			OutputTreeDataNode mainTreeDataNode = mainEntitiesTreeDataNodesList.get(count);
			getAllMainEntityContainments(mainEntityContainmentList, mainTreeDataNode,mainEntitiesTreeDataNodesList,query);
			mainEntitiesContainmentMap.put(Integer.valueOf(mainTreeDataNode.getExpressionId()),mainEntityContainmentList);
		}
	
	    return mainEntitiesContainmentMap;   
	}
    
	/**
	 * This method separates out the containment entities and main entities
	 * from children of root entity 
	 * @param query
	 * @param childrenNodes
	 * @param rootEntityContainmentList
	 * @param mainEntitiesTreeDataNodesList
	 */
	private static void separateChildrenNodes(IQuery query, List<OutputTreeDataNode> childrenNodes,
			List<OutputTreeDataNode> rootEntityContainmentList,
			List<OutputTreeDataNode> mainEntitiesTreeDataNodesList)
	{
		for (OutputTreeDataNode childNode : childrenNodes)
		{
			if(childNode.isContainedObject())
			{
				rootEntityContainmentList.add(childNode);
			}
			else if(checkIfOneOftheMainEntity(childNode, query))
			{
			    mainEntitiesTreeDataNodesList.add(childNode);
			}
		}
	}
	
	/**
	 * This method checks whether entity is main entity or not
	 * @param childNode
	 * @param query
	 * @return ifMainEntity
	 */
	private static boolean checkIfOneOftheMainEntity(OutputTreeDataNode childNode, IQuery query)
	{
		boolean ifMainEntity = false;
		IOutputEntity outputEntity = childNode.getOutputEntity();
		EntityInterface entity = outputEntity.getDynamicExtensionsEntity();
		List<EntityInterface> mainEntityList = QueryAddContainmentsUtil.getAllMainObjects(query);
			if(mainEntityList.contains(entity))
			{
				ifMainEntity = true;
			}
		return ifMainEntity;
	}
	
	
	/**
	 * This method separates main/Containment nodes for each containment entity of root node
	 * @param containmentsTreeDataNodesList
	 * @param mainEntitiesTreeDataNodesList
	 * @param query
	 */
	private static void separateChildrenForEachContainment(List<OutputTreeDataNode> containmentsTreeDataNodesList,List <OutputTreeDataNode> mainEntitiesTreeDataNodesList,IQuery query)
	{
		for(int count = 0; count < containmentsTreeDataNodesList.size(); count ++)
		{
			OutputTreeDataNode treeDataNode =  containmentsTreeDataNodesList.get(count);
			List <OutputTreeDataNode> list = getAllContainmentEntities(treeDataNode,mainEntitiesTreeDataNodesList,query);
			if(!list.isEmpty())
			{
				containmentsTreeDataNodesList.addAll(list);
			}
		}
	}
	
	/**
	 * This method returns list of all containments OutputTreeDataNodes for a node 
	 * @param treeDataNode
	 * @return conatainmentEntities
	 */
	private static List <OutputTreeDataNode> getAllContainmentEntities(OutputTreeDataNode treeDataNode,List <OutputTreeDataNode> mainEntitiesTreeDataNodesList,IQuery query)
	{
		List <OutputTreeDataNode> conatainmentEntities = new ArrayList<OutputTreeDataNode>();
		List <OutputTreeDataNode> list = treeDataNode.getChildren();
		if((list != null) && (!list.isEmpty()))
		{
			for (OutputTreeDataNode outputTreeDataNode : list)
			{
			   	if(outputTreeDataNode.isContainedObject())
			   	{
			   		conatainmentEntities.add(outputTreeDataNode);
			   	}
			   	else if(checkIfOneOftheMainEntity(outputTreeDataNode, query))
			   	{
			   		//If it is not an containment, check if it is a Main Entity. If it is a main entity, add it to main entity list
			   		mainEntitiesTreeDataNodesList.add(outputTreeDataNode);
			   	}
			}
		}
		return conatainmentEntities;
	}
	
	/**
	 * This method updates mainEntityContainmentList for each main entity present in Query  
	 * @param mainEntityContainmentList
	 * @param mainTreeDataNode
	 */
	private static void getAllMainEntityContainments(List<OutputTreeDataNode> mainEntityContainmentList,
			OutputTreeDataNode mainTreeDataNode,List <OutputTreeDataNode> mainEntitiesTreeDataNodesList, IQuery query)
	{
		List <OutputTreeDataNode> childrenList  = mainTreeDataNode.getChildren();
		if(childrenList != null && !childrenList.isEmpty())
		{
			mainEntityContainmentList.addAll(childrenList);
		}
		
		//For each containment of Main entity , get further containments
		if(!mainEntityContainmentList.isEmpty())
		{
			separateChildrenForEachContainment(mainEntityContainmentList,mainEntitiesTreeDataNodesList,query);
		}
	}
	
	public static Map <OutputTreeDataNode, List<OutputTreeDataNode>> getParentChildrensForaMainNode(OutputTreeDataNode mainTreeDataNode)
	{
		Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap =  new HashMap<OutputTreeDataNode, List<OutputTreeDataNode>>();
		List <OutputTreeDataNode> mainChildrenList  = mainTreeDataNode.getChildren();
		List <OutputTreeDataNode> childrenList = new ArrayList<OutputTreeDataNode>();
		childrenList.addAll(mainChildrenList);
		if((childrenList != null)  && (!childrenList.isEmpty()))
		{
			parentChildrenMap.put(mainTreeDataNode, mainChildrenList);
			
			//Now for each children ,find their further children
			getChildrenForEachMainChild(childrenList,parentChildrenMap);
		}
		
		return parentChildrenMap;
	}
	
	private static void getChildrenForEachMainChild(List <OutputTreeDataNode> mainChildrenList,Map <OutputTreeDataNode, List<OutputTreeDataNode>>parentChildrenMap)
	{
		int count = 0;
		while(count < mainChildrenList.size())
		{
			OutputTreeDataNode treeNode = mainChildrenList.get(count);
		    List <OutputTreeDataNode> childrenList =  treeNode.getChildren();
		   
		    //Even if Children List is empty , it is added to parentChildrenMap 
		    parentChildrenMap.put(treeNode, childrenList);
		    if(!childrenList.isEmpty())
		    {
		    	mainChildrenList.addAll(childrenList);
		    }
		    count ++;
		}
	}

}
