package edu.wustl.query.querysuite;
import java.util.List;

import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.bizlogic.QueryCsmBizLogic;
import edu.wustl.query.util.global.Variables;

public class QueryCsmUtilTestCases extends QueryBaseTestCases
{
	static
	{
		Variables.isExecutingTestCase = true;
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
	}
	
	public QueryCsmUtilTestCases()
	{
		super();
	}
		
	public void testQueryCsmBizLogic()
	{
		try
		{
			QueryableObjectInterface firstQueryableObject = QueryBuilder.createQueryableObject(Constants.LABORATORY_PROCEDURE_DETAILS,false);
			QueryableObjectInterface lastQueryableObject = QueryBuilder.createQueryableObject("Result",false);
			List<QueryableObjectInterface> mainEntityList = QueryCsmBizLogic.getMainEntityList(firstQueryableObject, lastQueryableObject);
			if(mainEntityList.size()!=0)
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			System.out.println("Test case failed "+e.getMessage());
			e.printStackTrace();
			fail();
		}
	}
}
