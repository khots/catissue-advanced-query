package edu.wustl.query.querysuite;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IExpression;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.IQueryParseUtil;
import edu.wustl.query.viewmanager.ResultsViewComponentGenerator;


public class IQueryParseUtilTestCases extends TestCase
{
	static
	{
		Properties queryProperties = new Properties();
		try
		{
			//xquery.jbossPath
			queryProperties.setProperty("xquery.jbossPath", "./log");
			Variables.properties = queryProperties;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void testSeparateAllChildren()
	{
		try
		{
			IQuery query = QueryBuilder.skeletalRaceGenderAddressQuery();
			IExpression expressionPerson = query.getConstraints().getRootExpression();
			IExpression expressionLabProc = QueryBuilder.createExpression(query.getConstraints(), expressionPerson, Constants.LABORATORY_PROCEDURE, false);
			QueryBuilder.createExpression(query.getConstraints(), expressionLabProc, Constants.LABORATORY_PROCEDURE_DETAILS, false);


			Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
			//Pass that IQuery to PassTwoXQueryGenerator to parse it and populate the rootNodeOutput list
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			passTwoQueryGenerator.generateQuery(query);

			//Get the root out put node list , which gives the root node
			ResultsViewComponentGenerator viewGenerator = new ResultsViewComponentGenerator(query);
			
			List<OutputTreeDataNode> rootOutputTreeNodeList = viewGenerator.getRootOutputTreeNode();
//			IOutputEntity outputEntity = rootOutputTreeNodeList.get(0).getOutputEntity();
//			QueryableObjectInterface rootEntity = outputEntity.getDynamicExtensionsEntity();
			
			Map<Integer, List<OutputTreeDataNode>> childrentMap = IQueryParseUtil.separateAllChildren(rootOutputTreeNodeList.get(0), query);
			if(childrentMap==null || childrentMap.size()==0)
			{
				fail();
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	public void testGetParentChildrensForaMainNode()
	{
		try
		{
			IQuery query = QueryBuilder.skeletalRaceGenderAddressQuery();
			
			IExpression expression = query.getConstraints().getRootExpression();
			QueryBuilder.createExpression(query.getConstraints(), expression, Constants.LABORATORY_PROCEDURE, false);

			Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassTwoXQueryGenerator";
			//Pass that IQuery to PassTwoXQueryGenerator to parse it and populate the rootNodeOutput list
			PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
			passTwoQueryGenerator.generateQuery(query);

			//Get the root out put node list , which gives the root node
			ResultsViewComponentGenerator viewGenerator = new ResultsViewComponentGenerator(query);
			
			List<OutputTreeDataNode> rootOutputTreeNodeList = viewGenerator.getRootOutputTreeNode();
			
			Map<OutputTreeDataNode, List<OutputTreeDataNode>> parentChildrensMap = IQueryParseUtil.getParentChildrensForaMainNode(rootOutputTreeNodeList.get(0));
			if(parentChildrensMap==null || parentChildrensMap.size()==0)
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
}
