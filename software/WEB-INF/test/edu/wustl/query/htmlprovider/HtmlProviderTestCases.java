package edu.wustl.query.htmlprovider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.Rule;
import edu.wustl.common.util.Collections;
import edu.wustl.query.bizlogic.CreateQueryObjectBizLogic;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.enums.QueryType;
public class HtmlProviderTestCases extends QueryBaseTestCases
{

	/**
	 * Generates html for Add Limits page for Demographics entity.
	 */
	public void testGenerateHtmlForDemographics()
	{
		try
		{
			HtmlProvider htmlProvider = new HtmlProvider(null);
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Demographics",false);
			htmlProvider.generateHTML(queryableObject,null,"");
			assertTrue("Html generated successfully for Demographics.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Generates html for VI Entity (Race).
	 */
	public void testGenerateHtmlForRace()
	{
		try
		{
			HtmlProvider htmlProvider = new HtmlProvider(null);
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Race",false);
			htmlProvider.generateHTML(queryableObject,null,"");
			assertTrue("Html generated successfully for Race.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * Generates html for Category.
	 */
	public void testGenerateHtmlForCategory()
	{
		try
		{
			HtmlProvider htmlProvider = new HtmlProvider(null);
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("CIDER PERSON TEST CATEGORY",true);
			htmlProvider.generateHTML(queryableObject,null,"");
			assertTrue("Html generated successfully for Category.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * Generates html for edit case.
	 */
	public void testGenerateHtmlForEdit()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression person = joinGraph.getRoot();
			QueryBuilder.addCondition(person, "personUpi",RelationalOperator.In, "1317900");
			HtmlProvider htmlProvider = new HtmlProvider(null);
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Person",false);
			List<ICondition> conditions = getConditionList(person);
			htmlProvider.generateHTML(queryableObject,conditions,"");
			assertTrue("Html generated successfully for Edit case.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	private List<ICondition> getConditionList(IExpression person)
	{
		List<ICondition> conditions = new ArrayList<ICondition>();
		if (person.numberOfOperands() > 0)
		{
			Rule rule = ((Rule) (person.getOperand(0)));
			conditions = Collections.list(rule);
		}
		return conditions;
	}
	/**
	 * Generate html for edit case for VI entities.
	 */
	public void testGenerateHtmlForEditRace()
	{
		try
		{
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("Race");
			generateHTMLDetails.setAttributeChecked(true);
			generateHTMLDetails.setPermissibleValuesChecked(true);
			generateHTMLDetails.setQueryId("1");
			HtmlProvider htmlProvider = new HtmlProvider(generateHTMLDetails);
			
			IParameterizedQuery query = QueryBuilder.skeletalRaceQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression race = QueryBuilder.findExpression(Constants.RACE, joinGraph.getRoot(),
					joinGraph);
			QueryBuilder.addCondition(race, "id", RelationalOperator.In, "3326");
			QueryBuilder.addCondition(race, "name", RelationalOperator.In, "Black");
			List<ICondition> conditions = getConditionList(race);
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Race",false);
			htmlProvider.generateHTML(queryableObject,conditions,"");
			assertTrue("Html generated successfully for Edit case in VI.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Generates html with between operator. 
	 */
	public void testBetweenDateOfBirth()
	{
		try
		{
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("date");
			generateHTMLDetails.setAttributeChecked(true);
			generateHTMLDetails.setPermissibleValuesChecked(true);
			generateHTMLDetails.setQueryId("1");
			HtmlProvider htmlProvider = new HtmlProvider(generateHTMLDetails);
			
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph
					.getRoot(), joinGraph);
			QueryBuilder.addCondition(demographics, "dateOfBirth", RelationalOperator.Between,
					"01/01/1950", "01/01/1980");
			List<ICondition> conditions = getConditionList(demographics);
			QueryableObjectInterface queryableObject = 
				QueryBuilder.createQueryableObject("Demographics",false);
			htmlProvider.generateHTML(queryableObject,conditions,"");
			assertTrue("Html generated successfully for Between operator.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Generated html for saved query.
	 */
	public void testSavedQueryHtml()
	{
		try
		{
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("race");
			generateHTMLDetails.setAttributeChecked(true);
			generateHTMLDetails.setPermissibleValuesChecked(true);
			generateHTMLDetails.setQueryId("1");
			IParameterizedQuery query = QueryBuilder.skeletalRaceQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			htmlProvider.getHTMLForSavedQuery(query,true,"Save Query Page", new HashMap<Integer, ICustomFormula>(),generateHTMLDetails,false);
			assertTrue("Html generated successfully for saved query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Generated html for temporal query.
	 */
	public void testTemporalQueryHtml()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			query.setId(1L);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			Map<Integer,ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
			htmlProvider.getHTMLForSavedQuery(query,true,"Save Query Page",customFormulaIndexMap,null,false);
			assertTrue("Html generated successfully for saved query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Generated html for radio button.
	 */
	public void testGenerateHtmlRadioButton()
	{
		try
		{
			GenerateHtml.generateHTMLForRadioButton("1_31",null);
			List<String> values = new ArrayList<String>();
			values.add("true");
			GenerateHtml.generateHTMLForRadioButton("1_31",values);
			values.add(0,"false");
			GenerateHtml.generateHTMLForRadioButton("1_31",values);
			values.add(0,null);
			GenerateHtml.generateHTMLForRadioButton("1_31",values);
			values.add(0,"a");
			GenerateHtml.generateHTMLForRadioButton("1_31",values);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Generated html for demographics.
	 */
	public void testGeneratePreHtml()
	{
		try
		{
			GenerateHtml.generatePreHtml("!&&!dateOfBirth397!&&!dateOfDeath398!&&!mothersMaidenName399!&&!placeOfBirth400!&&!socialSecurityNumber401",
					"371",true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * Generated html for saved query.
	 */
	public void testQueryHtmlForExecuteQueryPage()
	{
		try
		{
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("race");
			generateHTMLDetails.setAttributeChecked(true);
			generateHTMLDetails.setPermissibleValuesChecked(true);
			generateHTMLDetails.setQueryId("1");
			IQuery prameterizedQuery = getQueryObject();
    		String conditionList = getConditionList(prameterizedQuery);
    		Map<String, String> displayNameMap = getdisplayNameMap();
    		CreateQueryObjectBizLogic bizlogic = new CreateQueryObjectBizLogic();
    		bizlogic.setInputDataToQuery(conditionList, prameterizedQuery.getConstraints(), displayNameMap, prameterizedQuery);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			htmlProvider.getHTMLForSavedQuery(prameterizedQuery,true,"Execute Query Page", new HashMap<Integer, ICustomFormula>(),generateHTMLDetails,false);
			assertTrue("Html generated successfully for execute query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * Generated html for saved query.
	 */
	public void testTemporalQueryHtmlBetweenOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalTwoNodeTimestampTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			query.setId(1L);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			Map<Integer,ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
			htmlProvider.getHTMLForSavedQuery(query,true,"Save Query Page",customFormulaIndexMap,null,false);
			PassOneXQueryGenerator passOneQueryGenerator = new PassOneXQueryGenerator();
			String generatedQuery = passOneQueryGenerator.generateQuery(query);
			assertTrue("Html generated successfully for saved query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * Generated html for saved query.
	 */
	public void testTemporalDSIntervalBetweenOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalTwoNodeDSIntervalTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			query.setId(1L);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			Map<Integer,ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
			htmlProvider.getHTMLForSavedQuery(query,true,"Save Query Page",customFormulaIndexMap,null,false);
			assertTrue("Html generated successfully for saved query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * This method generates HTML for saved conditions of query 
	 */
	public void testGetHTMLForSavedConditions()
	{
		try
		{
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("race");
			generateHTMLDetails.setAttributeChecked(true);
			generateHTMLDetails.setPermissibleValuesChecked(true);
			generateHTMLDetails.setQueryId("1");
			IParameterizedQuery query = QueryBuilder.skeletalRaceQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			htmlProvider.getHtmlforSavedConditions(query);
			assertTrue("Html generated successfully for saved conditions for a query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * This method generates HTML for saved conditions of query with custom formulas
	 */
	public void testGetHTMLForSavedConditiosWithCfs()
	{
		try
		{
			GenerateHTMLDetails generateHTMLDetails = new GenerateHTMLDetails();
			generateHTMLDetails.setSearchString("race");
			generateHTMLDetails.setAttributeChecked(true);
			generateHTMLDetails.setPermissibleValuesChecked(true);
			generateHTMLDetails.setQueryId("1");
			IParameterizedQuery query = QueryBuilder.skeletalTwoNodeTimestampTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			htmlProvider.getHtmlforSavedConditions(query);
			assertTrue("Html generated successfully for saved conditions for a query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	
		
	}
	
	
	/*public void testTemporalNumericLiteralBetweenOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalTwoNodeNumericTemporalQuery();
			query.setCreatedBy(1L);
			query.setCreatedDate(new Date());
			query.setUpdatedBy(1L);
			query.setUpdationDate(new Date());
			query.setName("TestCaseQuery");
			query.setType(QueryType.GET_COUNT.type);
			query.setId(1L);
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			Map<Integer,ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
			htmlProvider.getHTMLForSavedQuery(query,true,"Save Query Page",customFormulaIndexMap,null,true);
			assertTrue("Html generated successfully for saved query.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}*/

}
