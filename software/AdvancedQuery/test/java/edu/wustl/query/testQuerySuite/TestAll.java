
package edu.wustl.query.testQuerySuite;

/**
 *
 */

import junit.framework.Test;
import junit.framework.TestSuite;
import edu.wustl.query.bizlogic.CreateQueryObjectTestCase;
import edu.wustl.query.bizlogic.DashboardBizLogicTestCase;
import edu.wustl.query.generator.SqlGeneratorGenericTestCase;
import edu.wustl.query.htmlprovider.GenerateHtmlDetailsTestCase;
import edu.wustl.query.htmlprovider.GenerateHtmlTestCase;
import edu.wustl.query.htmlprovider.HtmlProviderTestCase;
import edu.wustl.query.htmlprovider.ParseXMLFileTestCase;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProviderTestCase;

/**
 * @author prafull_kadam
 * Test Suite for testing all Query Interface related classes.
 */
public class TestAll
{
	public static void main(String[] args)
	{
		junit.swingui.TestRunner.run(TestAll.class);
	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test suite for Query Interface Classes");
		suite.addTestSuite(CreateQueryObjectTestCase.class);
		suite.addTestSuite(SqlGeneratorGenericTestCase.class);
		//suite.addTestSuite(MySqlQueryGenerator.class);
		suite.addTestSuite(HtmlProviderTestCase.class);
		suite.addTestSuite(DashboardBizLogicTestCase.class);
		suite.addTestSuite(GenerateHtmlDetailsTestCase.class);
		suite.addTestSuite(GenerateHtmlTestCase.class);
		suite.addTestSuite(ParseXMLFileTestCase.class);
		suite.addTestSuite(SavedQueryHtmlProviderTestCase.class);
		return suite;
	}
}
