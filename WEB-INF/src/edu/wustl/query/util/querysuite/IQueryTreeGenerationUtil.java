package edu.wustl.query.util.querysuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IQuery;

/**
 * 
 * @author baljeet_dhindhwal
 *
 */
public class IQueryTreeGenerationUtil
{
	
	/**
	 * This method creates a map containing list of expression ids of all children for a particular main entity  
	 * @param queryDetailsObject
	 */
	public static void  parseIQueryToCreateTree(QueryDetails queryDetailsObject)
	{
		List<OutputTreeDataNode> rootOutputTreeNodeList = queryDetailsObject.getRootOutputTreeNodeList();
		Map <Integer,List<Integer>> mainEntityContainmentIdsMap = new HashMap<Integer, List<Integer>>();

		List<Integer> mainEntitiesExpsIdsList = new ArrayList<Integer>();
		IQuery query = queryDetailsObject.getQuery();
			
		OutputTreeDataNode rootNode = rootOutputTreeNodeList.get(0);
		Map<Integer, List<OutputTreeDataNode>> mainEntitiesContainmentMap =  IQueryParseUtil.separateAllChildren(rootNode,query); 

		populateEntityIdMap(mainEntityContainmentIdsMap,mainEntitiesContainmentMap);
		
		//Populating the mainEntitiesExpsIdsList 
		Set <Integer>keyset =  mainEntitiesContainmentMap.keySet();
		Iterator<Integer> keySetItr = keyset.iterator();
		while(keySetItr.hasNext())
		{
			mainEntitiesExpsIdsList.add(keySetItr.next());
		}
		
		queryDetailsObject.setMainExpEntityExpressionIdMap(mainEntityContainmentIdsMap);
		queryDetailsObject.setMainEntityList(mainEntitiesExpsIdsList);
	}

	/**
	 * This method populates the map containing list of all expression ids for a main expression
	 * @param mainEntityContainmentIdsMap
	 * @param mainEntitiesContainmentMap
	 */
	private static void populateEntityIdMap(Map <Integer,List<Integer>> mainEntityContainmentIdsMap,Map<Integer, List<OutputTreeDataNode>> mainEntitiesContainmentMap)
	{
		//Set<Integer> keySet = mainEntitiesContainmentMap.keySet();
		//Iterator<Integer> keySetItr = keySet.iterator();
		Iterator<Map.Entry<Integer, List<OutputTreeDataNode> >> entrySetItr = 
			mainEntitiesContainmentMap.entrySet().iterator();
		while(entrySetItr.hasNext())
		{
			//Integer mainEntityExpId = keySetItr.next();
			Map.Entry<Integer, List<OutputTreeDataNode>> entry = entrySetItr.next();
			List<Integer> expressionIdsList = new ArrayList<Integer>();
			List<OutputTreeDataNode> containmentTreeDataNodes = entry.getValue();
			for (OutputTreeDataNode outputTreeDataNode : containmentTreeDataNodes)
			{
				expressionIdsList.add(Integer.valueOf(outputTreeDataNode.getExpressionId()));
			}
			mainEntityContainmentIdsMap.put(entry.getKey(),expressionIdsList);
		}
	}

	


}
