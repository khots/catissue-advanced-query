package edu.wustl.query.bizlogic;

import edu.wustl.common.dao.DAO;
import edu.wustl.common.dao.DAOFactory;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

/**
 * @author vijay_pande
 *
 */
public class QueryBizLogicTestCases extends QueryBaseTestCases
{
	public QueryBizLogicTestCases()
	{
		super();
	}
	
	static{
		// Utility.initTest();
		/**
		 * Indicating - Do not LOG XQueries
		 */
		Variables.isExecutingTestCase = true;
	        Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
	}
	
//	public void testInsertQuery()
//	{
//		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
//		parameterizedQuery.setName("Parameterized query from test case");
//		DAO dao = DAOFactory.getInstance().getDAO(Constants.HIBERNATE_DAO);
//		try
//		{
//			QueryBizLogic queryBizLogic = new QueryBizLogic();
//			queryBizLogic.insert(parameterizedQuery, dao, getSessionData());
//			assertTrue("Query inserted successfully....",true);
//			
//		}
//		catch (Exception e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//			fail();
//		}
//	}
	
	public void testInsertQueryWithProtectionElement()
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		parameterizedQuery.setName("Parameterized query from test case");
		parameterizedQuery.setType(Constants.QUERY_TYPE_GET_COUNT);
		
		try
		{
			QueryBizLogic queryBizLogic = new QueryBizLogic();
			queryBizLogic.insertSavedQueries(parameterizedQuery, getSessionData(), false);
			assertTrue("Query inserted successfully with protection element ",true);
			
		}
		catch (Exception e) {
			fail();
		}
	}
}
