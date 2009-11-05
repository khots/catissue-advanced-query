
package edu.wustl.query.spreadsheet;

import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.viewmanager.NodeId;
import edu.wustl.query.viewmanager.ResultsViewComponentGenerator;
import edu.wustl.query.viewmanager.ViewType;

/**
 * @author vijay_pande
 *
 */
public class SpreadSheetViewGeneratorTestCases extends QueryBaseTestCases
{

	public SpreadSheetViewGeneratorTestCases()
	{
		super();
	}

	static
	{
		/**
		 * Indicating - Do not LOG XQueries
		 */
		Variables.isExecutingTestCase = true;
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
	}

	public void testCreateSpreadsheet()
	{
		try
		{
			Variables.viewIQueryGeneratorClassName = "edu.wustl.cider.query.viewgenerator.CiderViewIQueryGenerator";
			SpreadSheetViewGenerator spreadSheetViewGenerator = new SpreadSheetViewGenerator(
					ViewType.USER_DEFINED_SPREADSHEET_VIEW);
			String idOfClickedNode = "NULL::0!_!NULL!_!NULL::0!_!2!_!Label";
			NodeId node = new NodeId(idOfClickedNode);

			QueryDetails queryDetailsObj = getQueryDetailsObj();

			SpreadSheetData spreadsheetData = new SpreadSheetData();

			spreadSheetViewGenerator
					.createSpreadsheet(node, queryDetailsObj, spreadsheetData, null);

			assertTrue("Spreadsheet object populated successfully  ", true);
		}
		catch (Exception e)
		{
			System.out.println("Test case failed " + e.getMessage());
			e.printStackTrace();
			fail();
		}
	}

	private QueryDetails getQueryDetailsObj() throws Exception
	{
		IQuery query = getDataQuery();
		QueryDetails queryDetailsObj = new QueryDetails();
		queryDetailsObj.setQueryExecutionId(4L);
		queryDetailsObj.setQuery(query);

		PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
		String generatedQuery = passTwoQueryGenerator.generateQuery(query);
		//Get the root out put node list , which gives the root node
		ResultsViewComponentGenerator viewGenerator = new ResultsViewComponentGenerator(query);

		List<OutputTreeDataNode> rootOutputTreeNodeList = viewGenerator.getRootOutputTreeNode();
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = viewGenerator
				.getAllChildrenNodes(rootOutputTreeNodeList);
		queryDetailsObj.setRootOutputTreeNodeList(rootOutputTreeNodeList);
		queryDetailsObj.setUniqueIdNodesMap(uniqueIdNodesMap);

		return queryDetailsObj;
	}

	public IParameterizedQuery getDataQuery() throws Exception
	{
		IParameterizedQuery query = null;
		query = QueryBuilder.skeletalDemograpihcsQuery();
		query.setCreatedBy(getSessionData().getUserId());
		query.setCreatedDate(new Date());
		query.setUpdationDate(new Date());
		query.setUpdatedBy(getSessionData().getUserId());

		query.setName("TestDataQuery");
		query.setType(QueryType.GET_DATA.type);

		IExpression expression = QueryBuilder.findExpression("Person", query.getConstraints()
				.getRootExpression(), query.getConstraints().getJoinGraph());
		QueryBuilder.addParametrizedCondition(query, expression, "personUpi",
				RelationalOperator.Equals);

		QueryBuilder.addOutputAttribute(query.getOutputAttributeList(), expression, "personUpi");

		return (IParameterizedQuery) query;
	}
}

