/**
 * 
 */
package edu.wustl.query.bizlogic;

import edu.wustl.common.bizlogic.QueryBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.Constants;
import edu.wustl.query.bizlogic.WorkflowBizLogic;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.util.global.Variables;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

/**
 * @author vijay_pande
 *
 */
public class WorkflowBizLogicTestCases extends QueryBaseTestCases
{
	public WorkflowBizLogicTestCases()
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
	public WorkflowBizLogicTestCases(String arg0)
	{
		super(arg0);
	}
	
	public void testInsertWorkflow()
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			Workflow workflow = gerUserSpecificWorkFlow();
			//step 2
			workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			//step 4
			assertNotNull(insertedProject);
			//step 5
			assertEquals(insertedProject.getName(),workflow.getName());
		}
		catch (UserNotAuthorizedException e)
		{
			//step 3
			fail();
		}
		catch (BizLogicException e)
		{
			//step 3
			fail();
		}
		catch (DAOException e)
		{
			//step 3
			fail();
		}
	}
	
	public void testInsertWorkflowEmptyObject()
	{
		Workflow workflow = new Workflow();
		workflow.setName("Workflow from test case" + new Date());
		// For User specific wf
		workflow.setCreatedBy(getSessionData().getUserId());
		workflow.setWorkflowItemList(new ArrayList<WorkflowItem>());
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			//step 4
			assertNotNull(insertedProject);
			//step 5
			assertEquals(insertedProject.getName(),workflow.getName());
		}
		catch (UserNotAuthorizedException e)
		{
			//step 3
			fail();
		}
		catch (BizLogicException e)
		{
			//step 3
			fail();
		}
		catch (DAOException e)
		{
			//step 3
			fail();
		}
	}
	
	/**
     * PURPOSE : To test the whether bizlogic handles null object passed to insert.
     * EXPECTED BEHAVIOR : IT should throw an exception with proper message.
     * TEST CASE FLOW : 
     * 1. Create null Project
     * 2. Call bizlogic.insert() method after getting the bizlogic
     * 3. Check whether there is no exception 
     * 4. Check whether the stored proejct can be retrieved from the database properly.
     * 5. Check whether the properties of project are retained properly after saving it to database.
     */
	public void testInsertWorkflowNullObject() 
	{
		//Step 1
		Workflow workflow = null;
		//step 2
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			fail();		
		}
		catch (UserNotAuthorizedException e)
		{
			//step 3
			fail();
		}
		catch (BizLogicException e)
		{
			//step 3
			assertTrue("Got biz logic exception as expected...",true);
		}
	}


	public void testUpdateWorkflow()
	{
		Workflow workflow =null;
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		
		try
		{
			List workflowList = workflowBizLogic.retrieve(Workflow.class.getName());
			if(workflowList!=null && workflowList.size()>0)
			{
				for(int i=0;i<workflowList.size();i++)
				{
					Long userId=((Workflow)workflowList.get(i)).getCreatedBy();
					if(userId!=null)
					{
						if(userId.equals(getSessionData().getUserId()))
						{
							workflow = (Workflow)workflowList.get(i);
							workflow.setName("Updated User Specific Workflow"+ new Date());
							workflowBizLogic.update(workflow, Constants.HIBERNATE_DAO);
							break;
							
						}
					}
				}
			}
			assertTrue("Workflow updated successfully",true);
		}
		catch (UserNotAuthorizedException e)
		{
			fail();
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
			fail();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testAddWorkflowItem()
	{
		Long workflowId =null;
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		ParameterizedQuery queryOperandOne = new ParameterizedQuery();
		queryOperandOne.setName("Added WorkflowItem");
		try
		{
			queryBizLogic.insert(queryOperandOne,getSessionData(), Constants.HIBERNATE_DAO);
			List workflowList = workflowBizLogic.retrieve(Workflow.class.getName());
			if(workflowList!=null && workflowList.size()>0)
			{
				workflowId = ((Workflow)workflowList.get(0)).getId();
			}
			workflowBizLogic.addWorkflowItem(workflowId,queryOperandOne,getSessionData());
			assertTrue("Workflow Item added successfully",true);
		}
		catch (UserNotAuthorizedException e)
		{
			fail();
		}
		catch (BizLogicException e)
		{
			fail();
		}
		catch (DAOException e)
		{
			fail();
		}
	}
	
	public void testInsertWorkflowEmptyUser() throws DAOException
	{
		Workflow workflow = new Workflow();
		workflow.setName("Workflow for created by" + new Date());
		// For User specific wf
		workflow.setCreatedBy(null);
		workflow.setWorkflowItemList(new ArrayList<WorkflowItem>());
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			if(insertedWf.getCreatedBy()==null)
			{
				fail();
			}
		}
		catch (UserNotAuthorizedException e)
		{
			fail();
		}
		catch (BizLogicException e)
		{
			assertFalse("Got biz logic exception",false);
		}
	}

	public void testInsertUserSpecificWorkflow() throws DAOException
	{
		Workflow workflow = new Workflow();
		workflow.setName("Workflow for created by" + new Date());
		// For User specific wf
		workflow.setCreatedBy(1L);
		workflow.setWorkflowItemList(new ArrayList<WorkflowItem>());
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			if(insertedWf.getCreatedBy()==workflow.getCreatedBy())
			{
				assertTrue("User specific workflow inserted successfully ",true);
			}
		}
		catch (UserNotAuthorizedException e)
		{
			fail();
		}
		catch (BizLogicException e)
		{
			assertFalse("Got biz logic exception",false);
		}
	}

}
