
package edu.wustl.query.bizlogic.helper;

import java.util.Date;
import java.util.Map;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryUIManagerFactory;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
import edu.wustl.query.bizlogic.QueryBaseTestCases;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;

public class WorkflowBizLogicHelperTestCases extends QueryBaseTestCases
{

	static
	{
		//Utility.initTest();
		/**
		 * Indicating - Do not LOG XQueries
		 */
		Variables.isExecutingTestCase = true;
		Variables.abstractQueryManagerClassName = "edu.wustl.cider.querymanager.CiderQueryManager";
		Variables.queryUIManagerClassName = "edu.wustl.cider.util.CiderQueryUIManager";
	}
	
//	public void testExecuteCompositeGetCountQuery()
//	{
//		try
//		{
//			Workflow workflow = getSavedWorflow();
//			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
//
//			CiderQueryPrivilege privilege = new CiderQueryPrivilege();
//			CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(1L, getSessionData()
//					.getUserId(), workflow, (CiderQueryPrivilege) privilege);
//			Map<Long, Long> executionIdList = workflowBizLogic.generateQueryExecIdMap(
//					getSessionData().getUserId(), workflow.getId(), 1L);
//			if (executionIdList.size() != 0)
//			{
//				assertTrue("failed to execute CompositeQuery", true);
//			}
//			else
//			{
//				assertFalse("failed to execute CompositeQuery", false);
//			}
//			AbstractQuery query = (AbstractQuery) workflow.getWorkflowItemList().get(2).getQuery();
//			edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
//					getSessionData().getUserId(), 1L, "10.88.199.199", workflow.getId(), privilege);
//			WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
//			Map<Long, Long> executionIdsMap = workflowBizLogicHelper.executeCompositeGetCountQuery(
//					query, -1L, workflowdetails, ciderQuery);
//			if (executionIdsMap.isEmpty())
//			{
//				fail();
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			fail();
//		}
//	}

	public void testGetWorkflowName()
	{
		try
		{
			WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
			String workflowName = workflowBizLogicHelper.getWorkflowName("workflowName's");
			if (workflowName.indexOf("''") == -1)
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

	public void testGetWorkflowByIdQuery()
	{
		try
		{
			WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
			String sql = workflowBizLogicHelper.getWorkflowByIdQuery(getSessionData().getUserId(),
					"workflow");
			assertNotNull(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public void testGenerateQueryExecIdMap()
	{
		try
		{
			Workflow workflow = getSimpleSavedWorkflow();
			WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
			Map<Long, Long> execIdMap = workflowBizLogicHelper.generateQueryExecIdMap(workflow
					.getCreatedBy(), workflow.getId(), -1L);
			assertNotNull(execIdMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	public void testIsQueryAlreadyExists()
	{
		try
		{
			Workflow workflow = getSimpleSavedWorkflow();
			WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
			boolean isQueryExist = workflowBizLogicHelper.isQueryAlreadyExists(workflow
					.getWorkflowItemList(), -1L);
			assertEquals(true, isQueryExist);
			Long queryId = workflow.getWorkflowItemList().get(0).getQuery().getId();
			isQueryExist = workflowBizLogicHelper.isQueryAlreadyExists(workflow
					.getWorkflowItemList(), queryId);
			assertEquals(false, isQueryExist);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

//	public void testExecuteParameterisedGetCountQuery()
//	{
//		try
//		{
//			Long execId = executeCountQuery();
//			assertNotNull(execId);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			fail();
//		}
//	}

	public void testGetCount()
	{
		try
		{
			Long queryExecId = executeCountQuery();
			assertNotNull(queryExecId);
			WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
			CiderQueryPrivilege privilege = new CiderQueryPrivilege();
			Count count = workflowBizLogicHelper.getCount(queryExecId, privilege);
			assertNotNull(count);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	private Long executeCountQuery() throws Exception
	{
		IQuery query = getCountQuery();
		AbstractQueryUIManager qUIManager = AbstractQueryUIManagerFactory
				.getDefaultAbstractUIQueryManager();
		WorkflowBizLogicHelper workflowBizLogicHelper = WorkflowBizLogicHelper.getInstance();
		return workflowBizLogicHelper.executeParameterisedGetCountQuery(query, qUIManager);
	}
	
	public IQuery getCountQuery() throws Exception
	   {
		   IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
		   query.setCreatedBy(getSessionData().getUserId());
		   query.setCreatedDate(new Date());
		   query.setUpdatedBy(getSessionData().getUserId());
		   query.setUpdationDate(new Date());

		   query.setName("TestCaseQuery"+new Date().getTime());
		   query.setType(QueryType.GET_COUNT.type);
		   //IExpression person = QueryBuilder.createExpression(constraints, null, Constants.PERSON);
		   QueryBuilder.addCondition(query.getConstraints().getExpression(1), "personUpi", RelationalOperator.Equals, "1317900");
		   return query;
	   }

	private Workflow getSimpleSavedWorkflow() throws Exception
	{
		Workflow workflow = gerUserSpecificWorkFlow();
		workflow.setName("Workflow" + new Date().getTime());
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		workflowBizLogic.insert(workflow, getSessionData());
		return workflow;
	}
}
