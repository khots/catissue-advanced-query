
package edu.wustl.common.query.category;

import java.util.Date;

import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.util.global.Constants;

public class CategoryProcessorTestCases extends QueryBaseTestCases
{

	public CategoryProcessorTestCases()
	{
		super();
	}

	public void testSaveAndExecuteCategoryQuery()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalDemographicsCategoryQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(query, getSessionData(), false);
			System.out.println("Query inserted successfully with Id  " + query.getId());
			query = (IParameterizedQuery) queryBizLogic.retrieve(
					ParameterizedQuery.class.getName(), Constants.ID, query.getId()).get(0);
			System.out.println("Query retrieved successfully with Id  " + query.getId());
			CategoryProcessor processor = new CategoryProcessor(query);
			IQuery generatedQuery = processor.processCategory();
			assertEquals(false, CategoryProcessorUtility.isCategoryQuery(generatedQuery));
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	public void testSingleNodeTemporalCategoryQuery()
	{
		// edu.wustl.query.utility.Utility.initTest();
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalCategorySingleNodeTemporalQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			CategoryProcessor processor = new CategoryProcessor(query);
			IQuery generatedQuery = processor.processCategory();
			assertEquals(false, CategoryProcessorUtility.isCategoryQuery(generatedQuery));
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			passTwoQueryGenerator.generateQuery(generatedQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	public void testDoubleNodeTemporalCategoryQuery()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalCategoryDoubleNodeTemporalQuery();
			query.setCreatedDate(new Date());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());
			query.setCreatedBy(getSessionData().getUserId());
			CategoryProcessor processor = new CategoryProcessor(query);
			IQuery generatedQuery = processor.processCategory();
			assertEquals(false, CategoryProcessorUtility.isCategoryQuery(generatedQuery));
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	public void testSingleNodeTemporalCategoryQueryForPassOne()
	{
		// edu.wustl.query.utility.Utility.initTest();
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalCategorySingleNodeTemporalQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			CategoryProcessor processor = new CategoryProcessor(query);
			IQuery generatedQuery = processor.processCategory();
			assertEquals(false, CategoryProcessorUtility.isCategoryQuery(generatedQuery));
			PassOneXQueryGenerator passOneQueryGenerator = new PassOneXQueryGenerator();
			passOneQueryGenerator.generateQuery(generatedQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	public void testDoubleNodeTemporalCategoryQueryForPassOne()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalCategoryDoubleNodeTemporalQuery();
			query.setCreatedDate(new Date());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());
			query.setCreatedBy(getSessionData().getUserId());
			CategoryProcessor processor = new CategoryProcessor(query);
			IQuery generatedQuery = processor.processCategory();
			assertEquals(false, CategoryProcessorUtility.isCategoryQuery(generatedQuery));
			PassOneXQueryGenerator passOneQueryGenerator = new PassOneXQueryGenerator();
			String queryString = passOneQueryGenerator.generateQuery(generatedQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}
}
