package edu.wustl.query.querysuite;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.cider.util.global.CiderConstants;
import edu.wustl.common.query.factory.ViewIQueryGeneratorFactory;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.util.querysuite.ResultsViewTreeUtil;
import edu.wustl.query.viewmanager.AbstractViewIQueryGenerator;

public class ResultsViewTreeUtilTestCases extends QueryBaseTestCases 
{
   
	public ResultsViewTreeUtilTestCases()
	{
		super();
	}
	
	/**
	 * Test case to test ResultsViewTreeUtil class methods, with query having secure
	 * Privileges 
	 */
	public void testResultViewTreeIQueryWithSecurePrevileges()
	{
		try 
		{
			Variables.viewIQueryGeneratorClassName = "edu.wustl.cider.query.viewgenerator.CiderViewIQueryGenerator";
			AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
					.getDefaultViewIQueryGenerator();
			IParameterizedQuery query = QueryBuilder
					.skeletalDemograpihcsQuery();
			QueryDetails queryDetails = getQueryDetailsObject(query);
			OutputTreeDataNode rootNode = queryDetails
					.getRootOutputTreeNodeList().get(0);
			queryDetails.setCurrentSelectedObject(rootNode);
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap = IQueryParseUtil
					.getParentChildrensForaMainNode(rootNode);
			queryDetails.setParentChildrenMap(parentChildrenMap);
			queryGenerator.createIQueryForTreeView(queryDetails, true);
			
		} 
		catch (QueryModuleException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		} catch (CyclicException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (MultipleRootsException e) 
		{
			e.printStackTrace();
			fail();
		} 
	}
	
	/**
	 * Test case to test ResultsViewTreeUtil class methods, with temporal query
	 *  having secure privileges 
	 */
	
	public void testResultViewTreeTqIQueryWithSecurePrevileges()
	{
		try
		{
			Variables.viewIQueryGeneratorClassName = "edu.wustl.cider.query.viewgenerator.CiderViewIQueryGenerator";
			AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory.getDefaultViewIQueryGenerator();

			IParameterizedQuery query1 = QueryBuilder.skeletalTwoNodeDSIntervalTemporalQuery();
			QueryDetails queryDetails1 = getQueryDetailsObject(query1);
			OutputTreeDataNode rootNode1 = queryDetails1
					.getRootOutputTreeNodeList().get(0);
			queryDetails1.setCurrentSelectedObject(rootNode1);
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap1 = IQueryParseUtil
					.getParentChildrensForaMainNode(rootNode1);
			queryDetails1.setParentChildrenMap(parentChildrenMap1);
			queryGenerator.createIQueryForTreeView(queryDetails1, true);
		}
		catch (QueryModuleException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		} catch (CyclicException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (MultipleRootsException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (ParseException e) 
		{
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test case to test ResultsViewTreeUtil class methods, with query
	 *  having no secure privileges 
	 */
	
	public void testResultViewTreeIQueryWithoutSecurePrevileges()
	{
		try
		{
			Variables.viewIQueryGeneratorClassName = "edu.wustl.cider.query.viewgenerator.CiderViewIQueryGenerator";
			AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory
					.getDefaultViewIQueryGenerator();
			IParameterizedQuery query = QueryBuilder
					.skeletalDemograpihcsQuery();
			QueryDetails queryDetails = getQueryDetailsObject(query);
			OutputTreeDataNode rootNode = queryDetails
					.getRootOutputTreeNodeList().get(0);
			queryDetails.setCurrentSelectedObject(rootNode);
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap = IQueryParseUtil
					.getParentChildrensForaMainNode(rootNode);
			queryDetails.setParentChildrenMap(parentChildrenMap);
			queryGenerator.createIQueryForTreeView(queryDetails, false);
			
		}
		catch (QueryModuleException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		} catch (CyclicException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (MultipleRootsException e) 
		{
			e.printStackTrace();
			fail();
		} 

	}
	
	/**
	 * Test case to test ResultsViewTreeUtil class methods, with temporal query
	 *  having no secure privileges 
	 */
	
	public void testResultViewTreeTqIQueryWithoutSecurePrevileges()
	{
		try
		{
			Variables.viewIQueryGeneratorClassName = "edu.wustl.cider.query.viewgenerator.CiderViewIQueryGenerator";
			AbstractViewIQueryGenerator queryGenerator = ViewIQueryGeneratorFactory.getDefaultViewIQueryGenerator();

			IParameterizedQuery query1 = QueryBuilder.skeletalTwoNodeDSIntervalTemporalQuery();
			QueryDetails queryDetails1 = getQueryDetailsObject(query1);
			OutputTreeDataNode rootNode1 = queryDetails1
					.getRootOutputTreeNodeList().get(0);
			queryDetails1.setCurrentSelectedObject(rootNode1);
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrenMap1 = IQueryParseUtil
					.getParentChildrensForaMainNode(rootNode1);
			queryDetails1.setParentChildrenMap(parentChildrenMap1);
			queryGenerator.createIQueryForTreeView(queryDetails1, false);
			
		}
		catch (QueryModuleException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		} catch (CyclicException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (MultipleRootsException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (ParseException e) 
		{
			
			e.printStackTrace();
			fail();
		}

		
		
	}
	
	public void testGetEntityFromCache()
	{
		try
		{
			//Get the Person Entity
			Collection<EntityGroupInterface> entityGroups = EntityCache.getCache()
					.getEntityGroups();
			EntityInterface personEntity = ResultsViewTreeUtil.getEntityFromCache(
					CiderConstants.PERSON_ENTITY_NAME, entityGroups);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	/*public void testcheckForResultViewTag()
	{
		try {
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Demographics",false);
			QueryableObjectInterface queryableObjectInterface = QueryBuilder.createQueryableObject("Person",false);
			boolean isTagPresent = ResultsViewTreeUtil.checkForResultViewTag(queryableObject,queryableObjectInterface);
			
			if(isTagPresent || !isTagPresent)
			{
				assertTrue("Results view tag is present",true);
			}
		} 
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (DynamicExtensionsApplicationException e) 
		{
			fail();
		}
	}*/
	
	/**
	 * Unit test case to test getAllParentChildrenMap() method
	 */
	/*public void testgetAllParentChildrenMap()
	{
		try 
		{
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Person",false);
			
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap = ResultsViewTreeUtil
			.getAllParentChildrenMap(queryableObject);
			
			if(partentChildEntityMap!=null)
			{
				assertTrue("ParentChildrenMap is generated successfully",true);
			}
			else
			{
				fail();
			}
		} 
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	
	*//**
	 * Unit test case to test getTaggedEntitiesParentChildMap()
	 *//*
	public void testgetTaggedEntitiesParentChildMap()
	{
		try 
		{
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Person",false);
			
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap = ResultsViewTreeUtil
			.getAllParentChildrenMap(queryableObject);
			
			if(partentChildEntityMap!=null)
			{
				Map<QueryableObjectInterface, List<QueryableObjectInterface>> taggedParentChildMap = ResultsViewTreeUtil.getTaggedEntitiesParentChildMap(partentChildEntityMap, queryableObject);
				if(taggedParentChildMap != null && !taggedParentChildMap.isEmpty())
				{
                   assertTrue("Tagged parent children map is created successfully", true);					
				}
				else
				{
					fail();
				}
			}
		} 
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	
	*//**
	 * Unit test case to test getPathsMapForTaggedEntity() method
	 *//*
	public void testgetPathsMapForTaggedEntity()
	{
		try
		{
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Person",false);
			
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap = ResultsViewTreeUtil
			.getAllParentChildrenMap(queryableObject);
			
			if(partentChildEntityMap!=null)
			{
				Map<QueryableObjectInterface, List<QueryableObjectInterface>> taggedParentChildMap = ResultsViewTreeUtil.getTaggedEntitiesParentChildMap(partentChildEntityMap, queryableObject);
				if(taggedParentChildMap != null && !taggedParentChildMap.isEmpty())
				{
					Map<QueryableObjectInterface, List<QueryableObjectInterface>> pathsMapForTaggedEntities = ResultsViewTreeUtil.getPathsMapForTaggedEntity(taggedParentChildMap, partentChildEntityMap);
					if(!pathsMapForTaggedEntities.isEmpty())
					{
						assertTrue("Paths map for tagged entities is created successfully ",true);
					}
					else
					{
						fail();
					}
				}
			}	
		}
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	*//**
	 * Unit test case to test addAllTaggedEntitiesToIQuery() method
	 *//*
	public void testaddAllTaggedEntitiesToIQuery()
	{
		try
		{
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Person",false);
			
			Map<QueryableObjectInterface, List<QueryableObjectInterface>> partentChildEntityMap = ResultsViewTreeUtil
			.getAllParentChildrenMap(queryableObject);
			
			if(partentChildEntityMap!=null)
			{
				Map<QueryableObjectInterface, List<QueryableObjectInterface>> taggedParentChildMap = ResultsViewTreeUtil.getTaggedEntitiesParentChildMap(partentChildEntityMap, queryableObject);
				if(taggedParentChildMap != null && !taggedParentChildMap.isEmpty())
				{
					Map<QueryableObjectInterface, List<QueryableObjectInterface>> pathsMapForTaggedEntities = ResultsViewTreeUtil.getPathsMapForTaggedEntity(taggedParentChildMap, partentChildEntityMap);
					if(!pathsMapForTaggedEntities.isEmpty())
					{
						Map <QueryableObjectInterface, List<Integer>> taggedEntityPathExprMap = ResultsViewTreeUtil.addAllTaggedEntitiesToIQuery(pathsMapForTaggedEntities, queryBuilder, rootEntity, rootExpId, expressionIdMap);
						
					}
				}
			}	
		}
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		} 
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	
	*//**
	 * Unit test case to test getIPath() method 
	 *//*
	public void testgetIPath()
	{
		try 
		{
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Person",false);
			QueryableObjectInterface queryableObject2 = 
				QueryBuilder.createQueryableObject("Demographics",false);
			
			IPath path = ResultsViewTreeUtil.getIPath(queryableObject, queryableObject2);
			if(path != null)
			{
				assertTrue("Path is present",true);
			}
			
		} 
		catch (DynamicExtensionsSystemException e) 
		{
			e.printStackTrace();
			fail();
		}
		catch (DynamicExtensionsApplicationException e) 
		{
			e.printStackTrace();
			fail();
		}
	}*/
	
	//public void test
}
