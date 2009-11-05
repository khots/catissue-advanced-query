/**
 * 
 */
package edu.wustl.query.querysuite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.json.JSONException;

import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.util.querysuite.IQueryUpdationUtil;

/**
 * @author supriya_dankh
 *
 */
public class IQueryUpdationUtilTestCases extends QueryBaseTestCases {

	public IQueryUpdationUtilTestCases(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public void testGetContainmentsForDefaultConditions()
	{
		try {
			QueryableObjectInterface entity = QueryBuilder.createQueryableObject("Person", false);
			String tagName = "DEFAULT_CONDITION";
			
			List<QueryableObjectInterface> containmentsForDefaultConditions = IQueryUpdationUtil.getContainmentsForDefaultConditions(entity, tagName);
			if(containmentsForDefaultConditions!=null)
			{
				assertTrue("Test case for getContainmentsForDefaultConditions is passed.", true);
			}
			else
			{
				fail();
			}
		} catch (DynamicExtensionsSystemException e) {
			e.printStackTrace();
			fail();
		} catch (DynamicExtensionsApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}
		
   public void testGetAllMainObjects()
   {
	   try {
		IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		List<QueryableObjectInterface> allMainObjects = IQueryUpdationUtil.getAllMainObjects(query);
		
		if(allMainObjects!=null)
		{
			assertTrue("Test case for getContainmentsForDefaultConditions is passed.", true);
		}
		else
		{
			fail();
		}
	} catch (DynamicExtensionsSystemException e) {
		e.printStackTrace();
		fail();
	} catch (DynamicExtensionsApplicationException e) {
		e.printStackTrace();
		fail();
	} catch (CyclicException e) {
		e.printStackTrace();
		fail();
	} catch (MultipleRootsException e) {
		e.printStackTrace();
		fail();
	}
			
   }
   
   /**
	* Test the Method: getAllMainObjects(IQuery)
	* 
	*/
	public void testGetAllMainObjectsInQuery() throws Exception
	{
		List<QueryableObjectInterface> list = null;

		IQuery query = QueryBuilder.skeletalRaceGenderAddressQuery();
		IExpression expressionPerson = query.getConstraints().getRootExpression();
		IExpression expressionLabProc = QueryBuilder.createExpression(query.getConstraints(),
				expressionPerson, Constants.LABORATORY_PROCEDURE, false);
		QueryBuilder.createExpression(query.getConstraints(), expressionLabProc,
				Constants.LABORATORY_PROCEDURE_DETAILS, false);
		System.out.println("before getAllMainObjects method call");
		//Call to the Utility Method
		list = IQueryUpdationUtil.getAllMainObjects(query);
		System.out.println("before getAllMainObjects method call");
		//Call Asserts on the return value of the Utility method under test.
		// We also call the assert on the getter methods present on the Return variable Class.
		Assert.assertNotNull("", list);
		if (list.isEmpty())
		{
			fail();
		}

	}

   public void testGetAllMainExpressionList()
   {
	   try {
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			List<IExpression> allMainObjects = IQueryUpdationUtil.getAllMainExpressionList(query);
			
			if(allMainObjects!=null)
			{
				assertTrue("Test case for getContainmentsForDefaultConditions is passed.", true);
			}
			else
			{
				fail();
			}
		} catch (DynamicExtensionsSystemException e) {
			e.printStackTrace();
			fail();
		} catch (DynamicExtensionsApplicationException e) {
			e.printStackTrace();
			fail();
		} catch (CyclicException e) {
			e.printStackTrace();
			fail();
		} catch (MultipleRootsException e) {
			e.printStackTrace();
			fail();
		}

   }
   
   public void testUpdateQueryForParameters()
   {
	   try {
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			String allQueriesConitionStr = "\"queryConditions\":\"\",\"temporalConditions\":null";
			Map<Long, Map<Integer, ICustomFormula>> eachQueryCFMap  = new HashMap<Long, Map<Integer,ICustomFormula>>();
			Map<Integer, ICustomFormula> map = new HashMap<Integer, ICustomFormula>();
			eachQueryCFMap.put(2L, map);
			IQueryUpdationUtil.updateQueryForParameters(query, allQueriesConitionStr, eachQueryCFMap);
				
			assertTrue("Test case for getContainmentsForDefaultConditions is passed.", true);
			
	   } catch (DynamicExtensionsSystemException e) {
			e.printStackTrace();
			fail();
		} catch (DynamicExtensionsApplicationException e) {
			e.printStackTrace();
			fail();
		} catch (CyclicException e) {
			e.printStackTrace();
			fail();
		} catch (MultipleRootsException e) {
			e.printStackTrace();
			fail();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }

}
