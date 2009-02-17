/**
 * 
 */

package edu.wustl.query.spreadsheet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.client.ui.dag.ambiguityresolver.AmbiguityObject;
import edu.wustl.cab2b.client.ui.query.ClientQueryBuilder;
import edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface;
import edu.wustl.cab2b.client.ui.query.IPathFinder;
import edu.wustl.cab2b.common.queryengine.Cab2bQuery;
import edu.wustl.common.query.impl.CommonPathFinder;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.metadata.associations.IIntraModelAssociation;
import edu.wustl.common.querysuite.metadata.path.IPath;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IParameter;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.IQueryEntity;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.Condition;
import edu.wustl.common.querysuite.queryobject.impl.OutputAttribute;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.Collections;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.flex.dag.DAGResolveAmbiguity;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;
import edu.wustl.query.util.querysuite.QueryAddContainmentsUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * @author vijay_pande
 *
 */
public class SpreadsheetIQueryGenerator
{

	private org.apache.log4j.Logger logger= Logger.getLogger(SpreadsheetIQueryGenerator.class);
	public void createIQuery(Node node, QueryDetails queryDetailsObj,
			List<IOutputAttribute> selectedColumns)
	{
		List<Object> upis = null;
		IQuery query = null;
		try
		{
			query = queryDetailsObj.getQuery();
			
			//Pass that IQuery to PassTwoXQueryGenerator to parse it and populate the rootNodeOutput list
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String generatedQuery = passTwoQueryGenerator.generateQuery(query); 
			
			//Get the root out put node list , which gives the root node
			List<OutputTreeDataNode> rootOutputTreeNodeList = passTwoQueryGenerator.getRootOutputTreeNodeList();
			IOutputEntity outputEntity = rootOutputTreeNodeList.get(0).getOutputEntity();
			EntityInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
			
			OutputTreeDataNode parentNode = null; //queryDetailsObj.getUniqueIdNodesMap().get(node.getParentNode());
			
			if(parentNode == null)
			{
				query = queryDetailsObj.getQuery();
				
				//Fetch UPI list for query
				//upis = getUPI(queryDetailsObj.getQueryExecutionId());
				
			}
			else
			{
				
				String parentData = node.getParentData();
				
				//Now Create IQuery From Main Entity , adding containments till attributes with tagged values are found
				//Get the parent child map for containment of a  main Entity
				Map<EntityInterface, List<EntityInterface>> partentChildEntityMap = getAllParentChildrenMap(rootEntity); 
				
				//Once u got the parent child map, get the parent child map for tagged entities for results view, and populate the list for tagged entities
				Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = getTaggedEntitiesParentChildMap(partentChildEntityMap);
				
				//Here we get path list from Root Entity to parent of Tagged Entity for results view 
				Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = getPathsMapForTaggedEntity(taggedEntitiesParentChildMap,partentChildEntityMap);
				
				//Now Add all entities related to a tagged entity to IQuery
				query =  formIQuery(rootEntity,eachTaggedEntityPathMap);
				
				upis = setSingleUPI("00050000");
			}
			
			setConstraintOnPersosnUPI(rootEntity, query);
			
			
		}
		catch(Exception ex)
		{
			logger.error("Error occured "+ex.getMessage(), ex);
		}

		//return upis;
	}

	/**
	 * @param upi
	 * @return
	 */
	private List<Object> setSingleUPI(String upi)
	{
		List<Object> upis = new ArrayList<Object>();
		upis.add(upi);
		return upis;
	}

	private List<Object> getUPI(int queryExecutionId) throws QueryModuleException
	{
		List<List<String>> upis = null;
		List<Object> result= new ArrayList<Object>();
		try
		{
			upis = IQueryUpdationUtil.getPersonUpis(queryExecutionId);
			if(upis!=null && upis.size()>0)
			{
				for(int i = 0; i<upis.size();i++)
				{
					result.add(upis.get(i).get(0));
				}
			}
		}
		catch(DAOException ex)
		{
			throw new QueryModuleException(ex.getMessage(), QueryModuleError.DAO_EXCEPTION);
		}
		return result;
		
	}

	private void setConstraintOnPersosnUPI(EntityInterface entity, IQuery query) throws MultipleRootsException
	{
		List<AttributeInterface> primaryKeyCollection = entity.getPrimaryKeyAttributeCollection();
		IExpression expression = query.getConstraints().getRootExpression();
//		List<IOutputAttribute> outputattrList = new ArrayList<IOutputAttribute>();
		ParameterizedQuery parameterizedQuery = (ParameterizedQuery)query;
		ICondition condition = null;
		for (IExpressionOperand expressionOperand : Collections.list(expression))
		{
			if(expressionOperand instanceof IRule)
			{
				((IRule) expressionOperand).removeAllConditions();
		    	//Only in case of Person Entity, it will be parameterized condition else not
				
	 			for(AttributeInterface attribute : primaryKeyCollection)
	 			{
	 				condition = new Condition();
	 				condition.setAttribute(attribute);
	 				condition.setRelationalOperator(RelationalOperator.Equals);
	 				condition.setValue("?");
//	 				IOutputAttribute ioutAttribute = new OutputAttribute(expression,attribute);
//	 				outputattrList.add(ioutAttribute);
	 			}
	 			((IRule) expressionOperand).addCondition(condition);
	 			
//				parameterizedQuery.setOutputAttributeList(outputattrList);
			}
		}
		
		IParameter<ICondition> parameter= QueryObjectFactory.createParameter(condition, "new");
		parameterizedQuery.getParameters().add(parameter);
		
	}

	private static AttributeInterface findAttribute(EntityInterface entity, String attributeName)
	{
		Collection<AttributeInterface> attributes = entity.getEntityAttributesForQuery();
		for (AttributeInterface attribute : attributes)
		{
			if (attribute.getName().equals(attributeName))
			{
				return attribute;
			}
		}
		return null;
	}

	private void setConstraintsOnPrimaryKey(EntityInterface entity, IConstraints constraints,
			List<String> values)
	{
		List<String> primariKeyList = Utility.getPrimaryKey(entity);

		IQueryEntity constraintEntity = QueryObjectFactory.createQueryEntity(entity);
		int i = 0;
		for (String primaryKeyAttribute : primariKeyList)
		{
			// creating Person Expression.

			IExpression personExpression = constraints.addExpression(constraintEntity);

			AttributeInterface attribute = findAttribute(entity, primaryKeyAttribute);

			List<String> conditionValue = new ArrayList<String>();
			conditionValue.add(values.get(i++));
			ICondition condition = QueryObjectFactory.createCondition(attribute,
					RelationalOperator.Equals, conditionValue);

			List<ICondition> conditions = new ArrayList<ICondition>();
			conditions.add(condition);
			IRule rule = QueryObjectFactory.createRule(conditions);

			personExpression.addOperand(rule);

			personExpression.setInView(true);
		}
	}

	public IQuery createIQueryForLabelNode()
	{

		return null;
	}
	
	
	
	private Map<EntityInterface, List<EntityInterface>> getAllParentChildrenMap(EntityInterface mainEntity)
	{
		HashMap<EntityInterface, List<EntityInterface>> partentChildEntityMap = new HashMap<EntityInterface, List<EntityInterface>>();
		ArrayList<EntityInterface> mainEntityContainmentList = new ArrayList<EntityInterface>();
		QueryAddContainmentsUtil.getMainEntityContainments(partentChildEntityMap, mainEntityContainmentList, mainEntity);
		return partentChildEntityMap;
	}
	
	private Map<EntityInterface, List<EntityInterface>> getTaggedEntitiesParentChildMap(Map<EntityInterface, List<EntityInterface>> partentChildEntityMap)
	{
		
		Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap = new HashMap<EntityInterface, List<EntityInterface>>();
		Set <EntityInterface> keySet =partentChildEntityMap.keySet();
	    for(EntityInterface keyEntity : keySet)
	    {
	    	List <EntityInterface> taggedEntitiesListForMainEntity = new ArrayList<EntityInterface>(); 
	    	List<EntityInterface> childrenEntities = partentChildEntityMap.get(keyEntity);
	     	for(EntityInterface childEntity : childrenEntities)
	     	{
	     		if(edu.wustl.query.util.global.Utility.istagPresent(childEntity,"resultview"))
		    	{
	     			taggedEntitiesListForMainEntity.add(childEntity);
		    	}
	     	}
	     	taggedEntitiesParentChildMap.put(keyEntity, taggedEntitiesListForMainEntity);
	    }
	    return taggedEntitiesParentChildMap;
	}
	
	private Map<EntityInterface, List<EntityInterface>> getPathsMapForTaggedEntity(Map<EntityInterface, List<EntityInterface>> taggedEntitiesParentChildMap,Map<EntityInterface, List<EntityInterface>> parentChilddrenMap)
	{
		Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap = new HashMap<EntityInterface, List<EntityInterface>>();
		Set <EntityInterface> keySet =  taggedEntitiesParentChildMap.keySet();
		for(EntityInterface entity : keySet)
		{
			List <EntityInterface> taggedEntityList = taggedEntitiesParentChildMap.get(entity);
            if(taggedEntityList != null && !taggedEntityList.isEmpty())
            {
            	for(EntityInterface taggedEntity : taggedEntityList)
            	{
            		List <EntityInterface> pathList = new ArrayList<EntityInterface>();
            		pathList.add(taggedEntity);
            		getPathListForTaggedEntity(taggedEntity,pathList,parentChilddrenMap);
            		eachTaggedEntityPathMap.put(taggedEntity, pathList);
            	}
            }
		}
		return eachTaggedEntityPathMap;
	}
	
	private List<EntityInterface> getPathListForTaggedEntity(EntityInterface taggedEntity,List <EntityInterface> pathList,Map<EntityInterface, List<EntityInterface>> parentChilddrenMap)
 	{
 		Set<EntityInterface> keySet  = parentChilddrenMap.keySet();
 		for(EntityInterface mainEntity : keySet)
 		{
 			List <EntityInterface> containmentList = parentChilddrenMap.get(mainEntity);
 			if(containmentList.contains(taggedEntity))
 			{
 				pathList.add(mainEntity);
 				getPathListForTaggedEntity(mainEntity,pathList,parentChilddrenMap);
 			}
 		}
 		return pathList;
 	}
	
	
 	private IQuery formIQuery(EntityInterface rootEntity,Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap)
 	{
 		//In IQuery, root entity should have a parametarized condition on primary key
 		List <AttributeInterface> primaryAttributesList = rootEntity.getPrimaryKeyAttributeCollection();
	    List<String> attributeOperators = new ArrayList<String>();
	    List <String>conditionList = new ArrayList<String>();
	    List<List<String>>  conditionValues = new ArrayList<List<String>>();
 		if(rootEntity.getName().equalsIgnoreCase("Person"))
 		{
 			//Only in case of Person Entity, it will be parametrized condition else not
 			for(int i=0; i<primaryAttributesList.size(); i++)
 			{
 				 attributeOperators.add("Equals");
 				 conditionList.add("?");
 			}
 			 conditionValues.add(conditionList);
 		}
 		else
 		{
 			
 		}
 		
 		IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
	    int rootExpId = m_queryObject.addRule(primaryAttributesList,attributeOperators,conditionValues,rootEntity);
	    
	    Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap = new HashMap<EntityInterface, List<Integer>>(); 
	    
 		//All Intermediate nodes as well tagged entities should be added like containment
 		Set <EntityInterface> taggedEntitiesKeySet = eachTaggedEntityPathMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntitiesKeySet)
 		{
 			List<EntityInterface> pathList = eachTaggedEntityPathMap.get(taggedEntity);
 			List <Integer> pathExpressionsList =  new ArrayList<Integer>();
 			for(int i= pathList.size()-1; i >=0; i--)
 			{
 				EntityInterface pathEntity = pathList.get(i);
 				if(pathEntity.getName().equalsIgnoreCase(rootEntity.getName()))
 				{
 					pathExpressionsList.add(rootExpId);
 				}
 				else
 				{
 					updatePathExpressionsList(pathEntity,m_queryObject, pathExpressionsList);
 				}
 			}
 			eachTaggedEntityPathExpressionsMap.put(taggedEntity,pathExpressionsList);
 		}
 		
 		//Now u have eachTaggedEntityPathExpressionsMap, so create path among added expressions
 		addLinksAmongExpressionsAdded(eachTaggedEntityPathExpressionsMap,m_queryObject); 
 		
 		//Only tagged attributes should be added as IOutPutAttribute List
 		addTaggedOutputAttributesToQuery(eachTaggedEntityPathExpressionsMap,m_queryObject);
 		
 		return m_queryObject.getQuery();
 	}
 	
	private void updatePathExpressionsList(EntityInterface pathEntity ,IClientQueryBuilderInterface m_queryObject,List <Integer> pathExpressionsList)
 	{
 		IQuery query = m_queryObject.getQuery();
 		IConstraints constraints = query.getConstraints();
 		boolean isMatchFound = false ;
 		for(IExpression expression : constraints)
 		{
 			if(expression.getQueryEntity().getDynamicExtensionsEntity().getName().equals(pathEntity.getName()))
 			{
 				pathExpressionsList.add(expression.getExpressionId());
 				isMatchFound = true;
 			}
 		}
 		//If no match is found with any of the expression, only then add the expression to query
 		if(!isMatchFound)
		{
				//add new expression to Query and add that expression id to pathExpressionsList
				int expressionId = ((ClientQueryBuilder) m_queryObject).addExpression(pathEntity);
				pathExpressionsList.add(expressionId);
		}
 	}
	
 	private void addLinksAmongExpressionsAdded(Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap,IClientQueryBuilderInterface m_queryObject )
 	{
 		IQuery query =  m_queryObject.getQuery();
 		IConstraints constraints = query.getConstraints();
 		IJoinGraph graph = constraints.getJoinGraph();
 		
 		Set <EntityInterface> taggedEntityKeySet= eachTaggedEntityPathExpressionsMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntityKeySet)
 		{
 			List <Integer> pathExpressionsList = eachTaggedEntityPathExpressionsMap.get(taggedEntity);
 		    if((pathExpressionsList != null) && (!pathExpressionsList.isEmpty()))
 		    {
 		    	for(int i=0; i<pathExpressionsList.size()-1; i++)
 		    	{
                    int parentExpId = pathExpressionsList.get(i);
                    int childExpId = pathExpressionsList.get(i+1);
 		    		
 		    		IExpression parentExpression = constraints.getExpression(parentExpId);
                    IExpression childExpresion = constraints.getExpression(childExpId);
                    IIntraModelAssociation association = (IIntraModelAssociation) (graph.getAssociation(
                    		parentExpression, childExpresion));
                    if(association == null)
                    {
                    	//If association is null, only then add the association
                    	IPath path = getIPath(parentExpression.getQueryEntity().getDynamicExtensionsEntity(),childExpresion.getQueryEntity().getDynamicExtensionsEntity());
                	    if (!m_queryObject.isPathCreatesCyclicGraph(parentExpId, childExpId, path))
                		{
                	    	QueryAddContainmentsUtil.linkTwoNodes(parentExpId, childExpId, path, m_queryObject);
                		}
                    }
                    
 		    	}
 		    }
 		}
 		
 	}
 	
 	
	private void addTaggedOutputAttributesToQuery(Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap,IClientQueryBuilderInterface m_queryObject)
	{
 		//For each tagged Entity ,get the tagged Attributes and add them to Ioutput Attribute list
 		ParameterizedQuery query = (ParameterizedQuery)m_queryObject.getQuery();
 		List <IOutputAttribute> outputAttributeList =  new ArrayList<IOutputAttribute>();
 		Cab2bQuery cab2bQuery = (Cab2bQuery)query;
	    IConstraints constraints = query.getConstraints();
 		Set <EntityInterface> taggedEntityKeySet= eachTaggedEntityPathExpressionsMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntityKeySet)
 		{
 			List<Integer> pathExpressionsIdsList = eachTaggedEntityPathExpressionsMap.get(taggedEntity);
 			int taggedEntityExpId = pathExpressionsIdsList.get((pathExpressionsIdsList.size()-1));
 			
 			Collection <AttributeInterface> attributeCollection= taggedEntity.getAllAttributes();
 			for(AttributeInterface attribute : attributeCollection)
 			{
 				if(edu.wustl.query.util.global.Utility.istagPresent(attribute,"resultview"))
 				{
 					//If the Attribute is tagged ,then Create IOutPut attribute and add to list
 					outputAttributeList.add(new OutputAttribute(constraints.getExpression(taggedEntityExpId),attribute));    					
 				}
 			}
 		}
 		
 	   //Setting the IOUtPut Attribute List to Query 
 	  cab2bQuery.setOutputAttributeList(outputAttributeList);
	}
 	
	private IPath getIPath(EntityInterface parentEntity,EntityInterface childEntity)
	{
		 //Now Add path among expressions added to query
	    Map<AmbiguityObject, List<IPath>> map = null;
		AmbiguityObject ambiguityObject = null;
		ambiguityObject = new AmbiguityObject(parentEntity, childEntity);
		IPathFinder pathFinder = new CommonPathFinder();
		DAGResolveAmbiguity resolveAmbigity = new DAGResolveAmbiguity(ambiguityObject, pathFinder);
		map = resolveAmbigity.getPathsForAllAmbiguities();
		List<IPath> pathList = map.get(ambiguityObject);
		return pathList.get(0);
	}
	
	private IQuery addDefaultConditionForUPI(EntityInterface rootEntity,Map<EntityInterface, List<EntityInterface>> eachTaggedEntityPathMap)
 	{
 		//In IQuery, root entity should have a parametarized condition on primary key
 		List <AttributeInterface> primaryAttributesList = rootEntity.getPrimaryKeyAttributeCollection();
	    List<String> attributeOperators = new ArrayList<String>();
	    List <String>conditionList = new ArrayList<String>();
	    List<List<String>>  conditionValues = new ArrayList<List<String>>();
 		if(rootEntity.getName().equalsIgnoreCase("Person"))
 		{
 			//Only in case of Person Entity, it will be parametrized condition else not
 			for(int i=0; i<primaryAttributesList.size(); i++)
 			{
 				 attributeOperators.add("Equals");
 				 conditionList.add("?");
 			}
 			 conditionValues.add(conditionList);
 		}
 		else
 		{
 			
 		}
 		
 		IClientQueryBuilderInterface m_queryObject = new ClientQueryBuilder();
	    int rootExpId = m_queryObject.addRule(primaryAttributesList,attributeOperators,conditionValues,rootEntity);
	    
	    Map <EntityInterface, List<Integer>> eachTaggedEntityPathExpressionsMap = new HashMap<EntityInterface, List<Integer>>(); 
	    
 		//All Intermediate nodes as well tagged entities should be added like containment
 		Set <EntityInterface> taggedEntitiesKeySet = eachTaggedEntityPathMap.keySet();
 		for(EntityInterface taggedEntity : taggedEntitiesKeySet)
 		{
 			List<EntityInterface> pathList = eachTaggedEntityPathMap.get(taggedEntity);
 			List <Integer> pathExpressionsList =  new ArrayList<Integer>();
 			for(int i= pathList.size()-1; i >=0; i--)
 			{
 				EntityInterface pathEntity = pathList.get(i);
 				if(pathEntity.getName().equalsIgnoreCase(rootEntity.getName()))
 				{
 					pathExpressionsList.add(rootExpId);
 				}
 				else
 				{
 					updatePathExpressionsList(pathEntity,m_queryObject, pathExpressionsList);
 				}
 			}
 			eachTaggedEntityPathExpressionsMap.put(taggedEntity,pathExpressionsList);
 		}
 		
 		//Now u have eachTaggedEntityPathExpressionsMap, so create path among added expressions
 		addLinksAmongExpressionsAdded(eachTaggedEntityPathExpressionsMap,m_queryObject); 
 		
 		//Only tagged attributes should be added as IOutPutAttribute List
 		addTaggedOutputAttributesToQuery(eachTaggedEntityPathExpressionsMap,m_queryObject);
 		
 		return m_queryObject.getQuery();
 	}
	
}
