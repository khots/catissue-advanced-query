package edu.wustl.query.bizlogic;

import junit.framework.Test;
import junit.framework.TestSuite;
import edu.wustl.common.query.category.CategoryProcessorTestCases;
import edu.wustl.common.query.processor.QuantitativeAttributeProcessorTestCases;
import edu.wustl.query.bizlogic.helper.WorkflowBizLogicHelperTestCases;
import edu.wustl.query.domain.DirectedGraphTestCases;
import edu.wustl.query.domain.WorkflowTestCases;
import edu.wustl.query.htmlprovider.HtmlProviderTestCases;
import edu.wustl.query.querysuite.ResultsViewComponentGeneratorTestCases;
import edu.wustl.query.querysuite.CsmUtilityTestCases;
import edu.wustl.query.querysuite.DefinedQueryUtilTestCases;
import edu.wustl.query.querysuite.IQueryParseUtilTestCases;
import edu.wustl.query.querysuite.IQueryUpdationUtilTestCases;
import edu.wustl.query.querysuite.QueryAddContainmentsUtilTestCases;
import edu.wustl.query.querysuite.QueryCsmUtilTestCases;
import edu.wustl.query.querysuite.ResultsViewTreeUtilTestCases;
import edu.wustl.query.spreadsheet.SpreadSheetViewGeneratorTestCases;

import edu.wustl.query.utility.QueryParameterUtilTestCases;
import edu.wustl.query.utility.Utility;


public class QueryTestAll extends TestSuite
{
	/**
	 * @param args arg
	 */
	public static void main(String[] args)
	{
		try
		{
			junit.swingui.TestRunner.run(QueryTestAll.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return test
	 */
	public static Test suite()
	{
		Utility.initTest();
			
		TestSuite suite = new TestSuite("Test suite for QUERY business logic");

		
		//for testing QuantitativeAttributeProcessor
		suite.addTestSuite(QuantitativeAttributeProcessorTestCases.class);
		
		//for testing IQueryUpdationUtil 
		suite.addTestSuite(IQueryUpdationUtilTestCases.class);
		
		//for testing ResultsViewComponentGenerator
		suite.addTestSuite(ResultsViewComponentGeneratorTestCases.class);
		
		//for testing IQueryParseUtil
		suite.addTestSuite(IQueryParseUtilTestCases.class);
		
		//for testing QueryAddContainmentsUtil
		suite.addTestSuite(QueryAddContainmentsUtilTestCases.class);
		
		//For testing WorkflowBizLogic
		suite.addTestSuite(WorkflowBizLogicTestCases.class);
		
		// For testing FRAMEWORK FUNCTIONALITY
		suite.addTestSuite(QueryFrameworkTestCase.class);

		// For testing ASynchronous Queries
		suite.addTestSuite(ASynchronousQueriesTestCases.class);
		
		// For testing Saved Queries
		suite.addTestSuite(QueryBizLogicTestCases.class);
		
		// testing CategoryProcessor		
		suite.addTestSuite(CategoryProcessorTestCases.class);
		
		// For testing SpreadSheet View Generator
		suite.addTestSuite(SpreadSheetViewGeneratorTestCases.class);
		
		suite.addTestSuite(ParameterizedQueryTestCases.class);
		
		// For testing Html Provider
		suite.addTestSuite(HtmlProviderTestCases.class);
		
		
		suite.addTestSuite(QueryParameterUtilTestCases.class);
		
		//for testing ValidateQueryBizLogic
		suite.addTestSuite(ValidateQueryBizLogicTestCases.class);
		
		// For testing QueryCsmUtil
		suite.addTestSuite(QueryCsmUtilTestCases.class);
		
		//for testing DirectedGraph domain object
		suite.addTestSuite(DirectedGraphTestCases.class);
		
		//for testing DefineViewGridBizlogic
		suite.addTestSuite(DefineGridViewTestCases.class);
		
		//for testing CreateQueryObjectBizlogic
		suite.addTestSuite(CreateQueryObjectTestCases.class);
		
		suite.addTestSuite(RecentQueriesBizLogicTestCases.class);
		
		//for testing ResultsViewTreeUtil 
		suite.addTestSuite(ResultsViewTreeUtilTestCases.class);
		
		//for testing CsmUtility
		suite.addTestSuite(CsmUtilityTestCases.class);
		

		//for testing DefinedQueryUtil
		suite.addTestSuite(DefinedQueryUtilTestCases.class);
		
		//for testing WorkflowBizLogicHelper
		suite.addTestSuite(WorkflowBizLogicHelperTestCases.class);
		
		//for testing WorkflowBizLogicHelper
		suite.addTestSuite(WorkflowTestCases.class);
		
		//for testing SearchPermissibleValueBizlogic
		suite.addTestSuite(SearchPermissibleValueBizlogicTestCases.class);
		
		return suite;
	}
}

