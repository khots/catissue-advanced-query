package edu.wustl.query.bizlogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.common.dynamicextensions.domain.AttributeTypeInformation;
import edu.common.dynamicextensions.domain.DomainObjectFactory;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeTypeInformationInterface;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.category.CategoryProcessorUtility;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.querableobject.QueryableEntity;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableObjectInterface;
import edu.wustl.common.querysuite.queryobject.ICondition;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.ICustomFormula;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IJoinGraph;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IRule;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.query.htmlprovider.SavedQueryHtmlProvider;
import edu.wustl.query.util.global.Variables;


public class CreateQueryObjectTestCases extends QueryBaseTestCases
{
	static
	{
		Variables.isExecutingTestCase = true;
		Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
		CommonServiceLocator.getInstance();

	}
	public CreateQueryObjectTestCases()
	{
		super();
	}

	public void testRuleDetailsMap()
	{
		try
		{
			CreateQueryObjectBizLogic queryBizlogic = new CreateQueryObjectBizLogic();
			QueryableObjectInterface queryableObject = QueryBuilder.createQueryableObject(Constants.PERSON,false);
			Collection<QueryableAttributeInterface> attributeColl =
				queryableObject.getEntityAttributesForQuery();
			QueryableAttributeInterface attribute = queryableObject.getAttributeByName("personUpi");
			String strToCreateQueryObject = "@#condition#@personUpi"+attribute.getId()+"!*=*!Not Equals!*=*!1317900!&&!";
			queryBizlogic.getRuleDetailsMap(strToCreateQueryObject, attributeColl);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

	public void testValidateConditions()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			IConstraints constraints = query.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression person = joinGraph.getRoot();
			QueryBuilder.addCondition(person, "personUpi",RelationalOperator.In, "1317900");
			IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
					joinGraph);
			//empty condition values added to expression.
			QueryBuilder.addCondition(demographics,"socialSecurityNumber",RelationalOperator.Contains,new ArrayList<String>());
			// added parameter to expression
			QueryBuilder.addParametrizedCondition(query, demographics,"socialSecurityNumber",RelationalOperator.Contains);
			CreateQueryObjectBizLogic queryBizlogic = new CreateQueryObjectBizLogic();
			queryBizlogic.validateConditions((ParameterizedQuery)query);
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			passTwoQueryGenerator.generateQuery(query);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}



	/**
	 * It will create a parameterized Query with a ageAtProcedure & lastResultTimeStamp as parameters
	 * & then at the Time of Execution it will put the conditions as
	 * ageAtProcedure Between 50 & 6
	 * lastResultTimeStamp  Between 06/10/2007 & 05/05/1956
	 */
	public void testDescendinValuesForBetweenOperator()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = QueryBuilder.skeletalLabProcedureDetailsQuery();
			IConstraints constraints = parameterizedQuery.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();

			IExpression labDetails = QueryBuilder.findExpression(Constants.LABORATORY_PROCEDURE_DETAILS, joinGraph.getRoot(),
					joinGraph);
			int expressionId = labDetails.getExpressionId();
			QueryBuilder.addParametrizedCondition(parameterizedQuery,labDetails,"ageAtProcedure",RelationalOperator.Between);
			QueryBuilder.addParametrizedCondition(parameterizedQuery,labDetails,"lastResultTimeStamp",RelationalOperator.Between);

			CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
			QueryableAttributeInterface ageAtProcedure = QueryBuilder.findAttribute(labDetails.getQueryEntity().getDynamicExtensionsEntity(), "ageAtProcedure");
			QueryableAttributeInterface lastResultTimeStamp = QueryBuilder.findAttribute(labDetails.getQueryEntity().getDynamicExtensionsEntity(), "lastResultTimeStamp");
			String conditionstr = "@#condition#@"+labDetails.getExpressionId()+"_"+ageAtProcedure.getId()
				+"!*=*!Between!*=*!50!*=*!6!&&!@#condition#@"+edu.wustl.query.util.global.Constants.Calendar
				+ labDetails.getExpressionId()+"_"+lastResultTimeStamp.getId()+"!*=*!Between!*=*!06-10-2007!*=*!05-05-1956!&&!";
			String errorMessage = bizLogic.setInputDataToQuery(conditionstr, parameterizedQuery
					.getConstraints(), null, parameterizedQuery);
			if(!errorMessage.equals(""))
			{
				fail();
			}
			IRule rule =CategoryProcessorUtility.getRule(labDetails);
			for(ICondition condition :rule )
			{
				if(condition.getAttribute().getName().equals("ageAtProcedure") )
				{
					double value1 = Double.parseDouble(condition.getValue());
					double value2 = Double.parseDouble(condition.getValues().get(1));
					if(value1>value2)
					{
						fail("Values For Between Operator on ageAtProcedure are not in Proper Order");
					}
				}
				if(condition.getAttribute().getName().equals("lastResultTimeStamp"))
				{
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
					Date date1 = dateFormat.parse(condition.getValue());
					Date date2 = dateFormat.parse(condition.getValues().get(1));
					if (date1.after(date2))
					{
						fail("Values For Between Operator on lastResultTimeStamp are not in Proper Order");
					}

				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

	public void testValidateDateAttributeValue()
	{
		try
		{
			CreateQueryObjectBizLogic queryBizlogic = new CreateQueryObjectBizLogic();
			QueryableObjectInterface queryableObject = QueryBuilder.createQueryableObject(Constants.LABORATORY_PROCEDURE_DETAILS,false);
			QueryableAttributeInterface collectionTimeStamp = QueryBuilder.findAttribute(queryableObject,"collectionTimeStamp");
			QueryableAttributeInterface ageAtProcedure = QueryBuilder.findAttribute(queryableObject,"ageAtProcedure");
			Collection<QueryableAttributeInterface> attributeColl =
				queryableObject.getEntityAttributesForQuery();
			String strToCreateQueryObject = "@#condition#@collectionTimeStamp"+collectionTimeStamp.getId()
				+"!*=*!Greater Than!*=*!13-01-1950!&&!";
			Map ruleDetailsMap = queryBizlogic.getRuleDetailsMap(strToCreateQueryObject, attributeColl);
			if(ruleDetailsMap.get(edu.wustl.query.util.global.Constants.ERROR_MESSAGE).equals(""))
			{
				fail();
			}
			strToCreateQueryObject = "@#condition#@collectionTimeStamp"+collectionTimeStamp.getId()+"!*=*!Between!*=*!05-05-2009!*=*!missingTwoValues!&&!";
			ruleDetailsMap = queryBizlogic.getRuleDetailsMap(strToCreateQueryObject, attributeColl);
			if(ruleDetailsMap.get(edu.wustl.query.util.global.Constants.ERROR_MESSAGE).equals(""))
			{
				fail();
			}
			// test for Double attributes
			strToCreateQueryObject = "@#condition#@ageAtProcedure"+ageAtProcedure.getId()+"!*=*!Greater than!*=*!abc!&&!";
			ruleDetailsMap = queryBizlogic.getRuleDetailsMap(strToCreateQueryObject, attributeColl);
			if(ruleDetailsMap.get(edu.wustl.query.util.global.Constants.ERROR_MESSAGE).equals(""))
			{
				fail();
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

	public void testValidateIntegerAttributeValue()
	{
		try
		{
			AttributeTypeInformationInterface attrTypeInfo = null;
			QueryableObjectInterface enti = null;
			AttributeInterface attribute = null;
			for (EntityGroupInterface entityGroup : EntityCache.getInstance().getEntityGroups())
			{
				EntityInterface entity = entityGroup.getEntityByName("Diagnosis");
				attribute = entity.getAttributeByName("sequence");
				attrTypeInfo = attribute.getAttributeTypeInformation();
				AttributeTypeInformation integerTypeInfo = DomainObjectFactory.getInstance()
						.createIntegerAttributeTypeInformation();
				attribute.setAttributeTypeInformation(integerTypeInfo);
				enti = new QueryableEntity(entity);
			}
			CreateQueryObjectBizLogic queryBizlogic = new CreateQueryObjectBizLogic();
			Collection<QueryableAttributeInterface> attributeColl = enti
					.getEntityAttributesForQuery();
			String strToCreateQueryObject = "@#condition#@sequence" + attribute.getId()
					+ "!*=*!Greater Than!*=*!abc!&&!";
			Map ruleDetailsMap = queryBizlogic.getRuleDetailsMap(strToCreateQueryObject,
					attributeColl);
			if (ruleDetailsMap.get(edu.wustl.query.util.global.Constants.ERROR_MESSAGE).equals(""))
			{
				fail();
			}
			strToCreateQueryObject = "@#condition#@sequence" + attribute.getId()
					+ "!*=*!Greater Than!*=*!-5!&&!";
			ruleDetailsMap = queryBizlogic.getRuleDetailsMap(strToCreateQueryObject, attributeColl);
			if (ruleDetailsMap.get(edu.wustl.query.util.global.Constants.ERROR_MESSAGE).equals(""))
			{
				fail();
			}
			attribute.setAttributeTypeInformation(attrTypeInfo);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	public void testUpdateQueryForExecution()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = QueryBuilder.skeletalDemograpihcsQuery();
			IConstraints constraints = parameterizedQuery.getConstraints();
			IJoinGraph joinGraph = constraints.getJoinGraph();
			IExpression person = joinGraph.getRoot();
			IExpression demographics = QueryBuilder.findExpression(Constants.DEMOGRAPHICS, joinGraph.getRoot(),
					joinGraph);
			QueryBuilder.addParametrizedCondition(parameterizedQuery,person,"personUpi",RelationalOperator.Contains);
			QueryBuilder.addParametrizedCondition(parameterizedQuery,demographics,"socialSecurityNumber",RelationalOperator.Contains);
			CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
			String conditionstr = "@#condition#@2_401!*=*!Contains!*=*!9!&&!@#condition#@1_31!*=*!Contains!*=*!9!&&!";
			String errorMessage = bizLogic.setInputDataToQuery(conditionstr, parameterizedQuery
					.getConstraints(), null, parameterizedQuery);
			if(!errorMessage.equals(""))
			{
				fail();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	public void testSetInputDataForTQ()
	{
		try
		{
			IParameterizedQuery parameterizedQuery = QueryBuilder.skeletalDemograpihcsTemporalQuery();
			CreateQueryObjectBizLogic bizLogic = new CreateQueryObjectBizLogic();
			String rhsList = "@#condition#@0##Equals##20##Minutes";
			Map<Integer,ICustomFormula> customFormulaIndexMap = new HashMap<Integer, ICustomFormula>();
			SavedQueryHtmlProvider htmlProvider = new SavedQueryHtmlProvider();
			htmlProvider.getHTMLForSavedQuery(parameterizedQuery,true,edu.wustl.query.util.global.Constants.SAVE_QUERY_PAGE,customFormulaIndexMap,null,false);
			String errorMessage = bizLogic.setInputDataToTQ(parameterizedQuery,edu.wustl.query.util.global.Constants.EXECUTE_QUERY_PAGE,
					rhsList, customFormulaIndexMap);
			if(!errorMessage.equals(""))
			{
				fail();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
}
