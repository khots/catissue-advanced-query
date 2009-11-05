package edu.wustl.common.query.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.ArithmeticOperator;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IOutputAttribute;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.query.bizlogic.QueryBaseTestCases;


public class QuantitativeAttributeProcessorTestCases extends QueryBaseTestCases
{

	public void testQuantitativeConditions()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalMedicationOrderQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression medicationDetails = QueryBuilder.findExpression(
					Constants.MEDICATION_ORDER_DETAILS, joinGraph.getRoot(), joinGraph);
			
			IExpression strength =QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.STRENGTH, false);
			IExpression quantity = QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.QUANTITY, false);
			QueryBuilder.addCondition(strength,"strength",RelationalOperator.GreaterThan,
					"10");
			
			List<IOutputAttribute> outputAttributes = new ArrayList<IOutputAttribute>();
			QueryBuilder.addOutputAttribute(outputAttributes,strength, "strength");
			QueryBuilder.addOutputAttribute(outputAttributes,quantity, "quantity");
			query.setOutputAttributeList(outputAttributes);
			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	public void testQuantitativeConditionsGreaterThanOrEuals()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalMedicationOrderQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression medicationDetails = QueryBuilder.findExpression(
					Constants.MEDICATION_ORDER_DETAILS, joinGraph.getRoot(), joinGraph);
			IExpression strength =QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.STRENGTH, false);
			QueryBuilder.addCondition(strength,"strength",RelationalOperator.GreaterThanOrEquals,
					"10");
			
			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	public void testQuantitativeConditionsBetweenOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalMedicationOrderQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression medicationDetails = QueryBuilder.findExpression(
					Constants.MEDICATION_ORDER_DETAILS, joinGraph.getRoot(), joinGraph);
			IExpression strength =QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.STRENGTH, false);
			QueryBuilder.addCondition(strength,"strength",RelationalOperator.Between,
					"10","100");
			
			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeConditionsLessThanOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalMedicationOrderQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression medicationDetails = QueryBuilder.findExpression(
					Constants.MEDICATION_ORDER_DETAILS, joinGraph.getRoot(), joinGraph);
			IExpression strength =QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.STRENGTH, false);
			QueryBuilder.addCondition(strength,"strength",RelationalOperator.LessThan,
					"10");
			
			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	public void testQuantitativeConditionsLessThanOrEquals()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalMedicationOrderQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression medicationDetails = QueryBuilder.findExpression(
					Constants.MEDICATION_ORDER_DETAILS, joinGraph.getRoot(), joinGraph);
			IExpression strength =QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.STRENGTH, false);
			QueryBuilder.addCondition(strength,"strength",RelationalOperator.LessThanOrEquals,
					"10");
			
			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeConditionsEqualsOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalMedicationOrderQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression medicationDetails = QueryBuilder.findExpression(
					Constants.MEDICATION_ORDER_DETAILS, joinGraph.getRoot(), joinGraph);
			IExpression strength =QueryBuilder.createExpression(constraints, medicationDetails,
					Constants.STRENGTH, false);
			QueryBuilder.addCondition(strength,"strength",RelationalOperator.Equals,
					"10");
			
			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	public void testQuantitativeTemporalLessThanOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Minus,RelationalOperator.LessThan);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeTemporalLessThanEqualToOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Plus,RelationalOperator.LessThanOrEquals);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeTemporalGreateThanEqualToOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Plus,RelationalOperator.GreaterThanOrEquals);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeTemporalGreateThanOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Minus,RelationalOperator.GreaterThanOrEquals);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeTemporalEqualsMinusOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Minus,RelationalOperator.Equals);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testQuantitativeTemporalEqualsPlusOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Plus,RelationalOperator.Equals);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
	public void testQuantitativeTemporalBetweenPlusOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Plus,RelationalOperator.Between);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	public void testQuantitativeTemporalBetweenMinusOperator()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalQANumericLiteralTemporalQuery(
					ArithmeticOperator.Minus,RelationalOperator.Between);
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			String queryString = passTwoQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	
	public void testLabResultTemporalQuery()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalLabResultTemporalQuery();
			query.setCreatedDate(new Date());
			query.setCreatedBy(getSessionData().getUserId());
			query.setUpdationDate(new Date());
			query.setUpdatedBy(getSessionData().getUserId());

			QuantitativeAttributeProcessor processor = new QuantitativeAttributeProcessor(query);
			IQuery generatedQuery = processor.processQuery();
			PassOneXQueryGenerator passOneQueryGenerator = new PassOneXQueryGenerator();
			String queryString = passOneQueryGenerator.generateQuery(generatedQuery);
			System.out.println("Query generated Succesfully");
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
