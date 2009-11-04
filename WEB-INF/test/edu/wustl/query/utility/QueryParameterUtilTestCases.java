package edu.wustl.query.utility;

import java.util.Date;

import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.QueryParameterUtil;
/**
 * 
 * @author baljeet_dhindhwal
 *
 */
public class QueryParameterUtilTestCases extends QueryBaseTestCases
{
	public void testRemoveEmptyParametersWithEmptyTimeStampCfs()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalTwoNodeEmptyTimestampTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			query.setId(1L);
			QueryParameterUtil.removeEmptyParameters(query);
			assertTrue("Empty conditions and Custom Formulas removed successfully",true);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * 
	 */
	public void testRemoveEmptyParametersWithEmptyDSIntervalCfs()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalTwoNodeEmptyDSIntervalTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			query.setId(1L);
			QueryParameterUtil.removeEmptyParameters(query);
			assertTrue("Empty conditions and Custom Formulas removed successfully",true);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			fail();
		}
	}
	
}
