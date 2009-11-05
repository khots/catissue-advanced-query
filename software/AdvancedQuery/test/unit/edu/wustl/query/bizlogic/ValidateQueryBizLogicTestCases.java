package edu.wustl.query.bizlogic;

import java.util.Date;
import java.util.Random;

import junit.framework.TestCase;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IExpressionOperand;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.bizlogic.ValidateQueryBizLogic;
import edu.wustl.query.enums.QueryType;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.LogicalOperator;


public class ValidateQueryBizLogicTestCases extends TestCase
{
	public ValidateQueryBizLogicTestCases()
	{
		super();
	}
	
	
	public void testGetValidationMessage()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression personEx = QueryBuilder.createExpression(constraints, null,Constants.PERSON, false);
			IExpression demoEx = QueryBuilder.createExpression(constraints, personEx,Constants.PERSON, false);
			IExpressionOperand iRule = QueryObjectFactory.createRule();
			personEx.addOperand(iRule);
			parameterizedQuery.setType(QueryType.GET_DATA.type);
			parameterizedQuery.setName("Parameterized query from test case");
			if(ValidateQueryBizLogic.getValidationMessage("pageOfQuery", "PQTestCase1", QueryType.GET_DATA.type, (IQuery)parameterizedQuery)==null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateExpressionInView()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression personEx = QueryBuilder.createExpression(constraints, null,Constants.PERSON, false);
			parameterizedQuery.setName("Parameterized query from test case");
			if(ValidateQueryBizLogic.validateExpressionInView(parameterizedQuery)==null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}

	public void testValidateExpressionInViewNegative()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = QueryObjectFactory.createParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			parameterizedQuery.setName("Parameterized query from test case");
			if(ValidateQueryBizLogic.validateExpressionInView(parameterizedQuery)==null)
			{
				fail();
			}
			else
			{
				assert(true);
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateRuleInDAG()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression personEx = QueryBuilder.createExpression(constraints, null,Constants.PERSON, false);
			IExpressionOperand iRule = QueryObjectFactory.createRule();
			personEx.addOperand(iRule);
			if(ValidateQueryBizLogic.validateRuleInDAG(parameterizedQuery)==null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateRuleInDAGNegative()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			if(ValidateQueryBizLogic.validateRuleInDAG(parameterizedQuery)!=null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateDQForMultipleParents()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalLabProcedureDetailsQuery();
			IJoinGraph joinGraph = query.getConstraints().getJoinGraph();
			IExpression person = QueryBuilder.findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
			IExpression labDetails = QueryBuilder.findExpression(Constants.LABORATORY_PROCEDURE_DETAILS, joinGraph.getRoot(), joinGraph);
			IExpression lab = QueryBuilder.createExpression(query.getConstraints(), person, Constants.LABORATORY_PROCEDURE,	false);	
			QueryBuilder.connectExpressions(lab,labDetails,LogicalOperator.And,joinGraph);
			query.setType(QueryType.GET_DATA.type);
			if(ValidateQueryBizLogic.validateDQForMultiParents(query, null)!=null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testValidateDQForMPNegative()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalLabProcedureDetailsQuery();
			IJoinGraph joinGraph = query.getConstraints().getJoinGraph();
			IExpression person = QueryBuilder.findExpression(Constants.PERSON, joinGraph.getRoot(), joinGraph);
			IExpression lab = QueryBuilder.createExpression(query.getConstraints(), person, Constants.LABORATORY_PROCEDURE,	false);	
			//IExpression labDetails = QueryBuilder.findExpression(Constants.LABORATORY_PROCEDURE_DETAILS, joinGraph.getRoot(), joinGraph);
			//QueryBuilder.connectExpressions(lab,labDetails,LogicalOperator.And,joinGraph);
			query.setType(QueryType.GET_DATA.type);
			if(ValidateQueryBizLogic.validateDQForMultiParents(query, null)!=null)
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testValidateQueryTitle()
	{
		try
		{
			if(ValidateQueryBizLogic.validateQueryTitle("DefineView", QueryType.GET_DATA.type, "TestQuery", getIQuery("for_ValidateQueryTitle"))== null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateDuplicateQueryTitle()
	{
		try
		{
			Random random = new Random();
			String queryTitle = random.nextInt()+"_for_ValidateQueryTitle_"+random.nextInt();
			if(ValidateQueryBizLogic.validateQueryTitle("DefineView", QueryType.GET_COUNT.type, queryTitle, getIQuery(queryTitle))== null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateDuplicateQueryTitleNegative()
	{
		try
		{
			String queryTitle = "PQ1 for workflow";
			// new QueryBizLogic().insertSavedQueries((IParameterizedQuery)getIQuery(queryTitle), null, false);
			if(ValidateQueryBizLogic.validateQueryTitle("DefineView", QueryType.GET_COUNT.type, queryTitle, getIQuery(queryTitle))== null)
			{
				fail();
			}
			else
			{
				assert(true);
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testValidateQueryTitleNegative()
	{
		try
		{
			if(ValidateQueryBizLogic.validateQueryTitle("TestPage", QueryType.GET_COUNT.type, null, getIQuery("for_ValidateQueryTitleNegative"))!= null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testGetMessageForBaseObjectDataQuery()
	{
		
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression personEx = QueryBuilder.createExpression(constraints, null,Constants.PERSON, false);
			IExpression labEx = QueryBuilder.createExpression(constraints, personEx,Constants.LABORATORY_PROCEDURE, false);
			parameterizedQuery.setName("Parameterized query from test case");
			
			if(ValidateQueryBizLogic.getMessageForBaseObject(constraints, QueryType.GET_DATA.type) == null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	
	public void testGetMessageForBaseObjectDataQueryNegative()
	{
		
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression labEx = QueryBuilder.createExpression(constraints, null,Constants.LABORATORY_PROCEDURE, false);
			IExpression labDetailsEx = QueryBuilder.createExpression(constraints, labEx,Constants.LABORATORY_PROCEDURE_DETAILS, false);
			parameterizedQuery.setName("Parameterized query from test case");
			
			if(ValidateQueryBizLogic.getMessageForBaseObject(constraints, QueryType.GET_DATA.type) != null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testGetMessageForBaseObjectCountQuery()
	{
		
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression labEx = QueryBuilder.createExpression(constraints, null,Constants.LABORATORY_PROCEDURE, false);
			IExpression personEx = QueryBuilder.createExpression(constraints, labEx,Constants.PERSON, false);
			parameterizedQuery.setName("Parameterized query from test case");
			
			if(ValidateQueryBizLogic.getMessageForBaseObject(constraints, QueryType.GET_COUNT.type) == null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public void testGetMessageForBaseObjectCountQueryNegative()
	{
		
		try
		{
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			IExpression labEx = QueryBuilder.createExpression(constraints, null,Constants.LABORATORY_PROCEDURE, false);
			IExpression labDetailsEx = QueryBuilder.createExpression(constraints, labEx,Constants.LABORATORY_PROCEDURE_DETAILS, false);
			parameterizedQuery.setName("Parameterized query from test case");
			
			if(ValidateQueryBizLogic.getMessageForBaseObject(constraints, QueryType.GET_COUNT.type) != null)
			{
				assert(true);
			}
			else
			{
				fail();
			}
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	public void testGetValidationMessageTitle()
	{
		try
		{				
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			parameterizedQuery.setName("");
			ValidateQueryBizLogic.getValidationMessage("DefineView","", QueryType.GET_DATA.type,parameterizedQuery);
			
				assert(true);
			
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	public void testGetValidationExpressionInView()
	{
		try
		{				
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			parameterizedQuery.setName("parametrizedQuery"+new Date());
			ValidateQueryBizLogic.getValidationMessage("DefineView",parameterizedQuery.getName(), QueryType.GET_DATA.type,parameterizedQuery);
			
				assert(true);
			
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	public void testGetMessageForBaseObject()
	{
		try
		{				
			IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
			parameterizedQuery.setName("parametrizedQuery"+new Date());
			IConstraints constraints = QueryObjectFactory.createConstraints();
			parameterizedQuery.setConstraints(constraints);
			ValidateQueryBizLogic.getValidationMessage("DefineView",parameterizedQuery.getName(), QueryType.GET_DATA.type,parameterizedQuery);
			
				assert(true);
			
		}
		catch (Exception e) 
		{
			fail();
		}
	}
	
	public IQuery getIQuery(String queryName)
	{
		IParameterizedQuery parameterizedQuery = new ParameterizedQuery();
		IConstraints constraints = QueryObjectFactory.createConstraints();
		parameterizedQuery.setConstraints(constraints);
		try
		{
			IExpression personEx = QueryBuilder.createExpression(constraints, null,Constants.PERSON, false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		parameterizedQuery.setName(queryName);
		return parameterizedQuery;
	}
	
  
}
