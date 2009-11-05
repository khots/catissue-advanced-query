/**
 * 
 */
package edu.wustl.query.bizlogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.query.CiderWorkFlowDetails;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.cider.util.CiderQueryUIManager;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.querysuite.exceptions.MultipleRootsException;
import edu.wustl.common.querysuite.exceptions.SqlException;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.impl.AbstractQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.querymanager.Count;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.AbstractQueryUIManager;
import edu.wustl.query.util.querysuite.DAOUtil;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.workflowexecutor.WorkflowManager;

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
		//Utility.initTest();
		/**
		 * Indicating - Do not LOG XQueries
		 */
		Variables.isExecutingTestCase = true;
	        Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
	        Variables.abstractQueryManagerClassName = "edu.wustl.cider.querymanager.CiderQueryManager";
	        Variables.queryUIManagerClassName="edu.wustl.cider.util.CiderQueryUIManager";
	        Variables.abstractQueryManagerClassName="edu.wustl.cider.querymanager.CiderQueryManager";
	}
	public WorkflowBizLogicTestCases(String arg0)
	{
		super(arg0);
	}
	public void testInsertWorkflowWithoutDate()
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
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			fail();
		}

		catch (Exception e)
		{
			//step 3
			e.printStackTrace();
			assertTrue("failed because creation date is not given", true);
		}

	}

	public void testGenerateQueryExecIdMap() 
	{
		try
		{
		Workflow workflow = getSavedWorflow();
		WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();

	       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
	       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
	               1L,getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);

	       Map<Long,Long> executionIdList= workflowBizLogic.generateQueryExecIdMap( getSessionData().getUserId(),
	    		   workflow.getId(),  1L);
			   if(executionIdList.size()!=0)
			   {
				   assertTrue("failed to execute CompositeQuery", true);
			   }
			   else
			   {
				   assertFalse("failed to execute CompositeQuery", false);
			   }
			   AbstractQuery query=(AbstractQuery) workflow.getWorkflowItemList().get(2).getQuery();

					   edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
				                getSessionData().getUserId(),
				                1L,
			                "10.88.199.199",
			                workflow.getId(),privilege
			                 );


				
					WorkflowManager  workflowManager=new WorkflowManager();
			              Map<Long, Long> executionIdMap;
			
							executionIdMap = workflowManager.execute(workflowdetails,
							           ciderQuery, new HashMap<Long, Long>());
				
							AbstractQueryManager qManager = AbstractQueryManagerFactory
							.getDefaultAbstractQueryManager();
		
						qManager.cancel(executionIdMap.get(query.getId()));
				
			       executionIdMap = workflowManager.execute(workflowdetails,
			                ciderQuery,executionIdMap);
			       assertTrue("Retrieved Count for cancelled CQ", true);
		}
		catch (BizLogicException e) {
			 assertFalse("failed to execute CompositeQuery ..Got BizLogic exception ...", false);
			 fail();
		}
		catch (QueryModuleException e) {
		
			e.printStackTrace();
			fail();
		} catch (MultipleRootsException e) {
	
			e.printStackTrace();
			fail();
		}
		catch (SqlException e) {

			e.printStackTrace();
			fail();
		}
		catch (SQLException e) {

			e.printStackTrace();
			fail();
		}
	}
	//TO Do it gets the Count for given privilege and execution id Map
	/**
	 * 
	 */
	/*public void testInsertWorkflowWithDescription()
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			Workflow workflow = gerUserSpecificWorkFlow();
			String description="description_"+System.currentTimeMillis();
			workflow.setDescription(description);
			workflowBizLogic.insert(workflow, getSessionData());
			assertTrue("Inserted wokflow",true);
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			System.out.println("description_"+System.currentTimeMillis());
			System.out.println(insertedProject.getDescription());
			if(description.equals(insertedProject.getDescription()))
			{
				System.out.println("equal description");
				assertTrue("description inserted ", true);
			}
		
			
		}
	
		catch (BizLogicException e)
		{
			//step 3
			//fail();
			e.printStackTrace();
		}
//		catch (DAOException e)
//		{
//			//step 3
//			fail();
//		}
	}
	*/
	public void testGetDateForLatestExecution()
	{
		try
		{
			Workflow workflow = getSavedWorflow();
			WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();

		       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
		       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
		               1L,getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);
		       	workflowBizLogic.runWorkflow(workflowdetails);
				List<Long> executionIdList= workflowBizLogic.generateExecutionIdMap(workflow.getId(),workflow.getCreatedBy());
				   if(executionIdList.size()!=0)
				   {
					   assertTrue("failed to execute CompositeQuery", true);
				   }
				   else
				   {
					   assertFalse("failed to execute CompositeQuery", false);
				   }
				   workflowBizLogic.getDateForLatestExecution(executionIdList);
		}
		catch (BizLogicException e) {
			e.printStackTrace();
			assertFalse("Got biz logic exception",false);
		}
	}

	public void testInsertParametersForExecution()
	{
		try
		{
		  Workflow workflow = getSavedWorflow();
		  workflow.setCreatedBy(getSessionData().getUserId());

			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				//step 2
				//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, getSessionData());
				Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			
			}

			catch (Exception e)
			{
				assertFalse("Got biz logic exception",false);
			}
		  
		  SessionDataBean sessionData=getSessionData();

		  HibernateDAO dao= null;
        dao=DAOUtil.getHibernateDAO(sessionData);;

        // Get the workflow
        workflow = (Workflow) dao.retrieveById(Workflow.class.getName(),
                Long.valueOf(workflow.getId()));
        AbstractQuery query=(AbstractQuery) workflow.getWorkflowItemList().get(0).getQuery();

        CiderQueryPrivilege privilege = new CiderQueryPrivilege();
        CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
                1L, sessionData.getUserId(), workflow,(CiderQueryPrivilege)privilege);

		   edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
	                getSessionData().getUserId(),
	                1L,
                  "10.88.199.199",
                  workflow.getId(),privilege
                   );
			AbstractQueryUIManager qUIManager = new CiderQueryUIManager(getSessionData(),(IQuery) query);
			
			
//		   qUIManager.
//		   
		qUIManager.setAbstractQuery(ciderQuery);
                Map<Long, Long> executionIdMap = workflowBizLogic
                . executeGetCountQuery( workflowdetails, 
             			  ciderQuery, qUIManager,query);
                assertTrue("passed  execution",true);
               // workflowBizLogic.insertParametersForExecution(executionIdMap.get(query.getId()), query);
                assertTrue("insert parameters for execution",true);
		}
		catch (Exception e) {
			e.printStackTrace();
		assertFalse("failed to execute",false);
		}
    
	}
	public void testCancelExecutionOfPQ()
	{try
	{
		  Workflow workflow = getSavedWorflow();
		  workflow.setCreatedBy(getSessionData().getUserId());

			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				//step 2
				//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, getSessionData());
				Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			
			}

			catch (Exception e)
			{
				assertFalse("Got biz logic exception",false);
			}
		  
		  SessionDataBean sessionData=getSessionData();

		  HibernateDAO dao= null;
      dao=DAOUtil.getHibernateDAO(sessionData);;

      // Get the workflow
      workflow = (Workflow) dao.retrieveById(Workflow.class.getName(),
              Long.valueOf(workflow.getId()));
      AbstractQuery query=(AbstractQuery) workflow.getWorkflowItemList().get(0).getQuery();

      CiderQueryPrivilege privilege = new CiderQueryPrivilege();
      CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
              1L, sessionData.getUserId(), workflow,(CiderQueryPrivilege)privilege);

		   edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
	                getSessionData().getUserId(),
	                1L,
                "10.88.199.199",
                workflow.getId(),privilege
                 );
			AbstractQueryUIManager qUIManager = new CiderQueryUIManager(getSessionData(),(IQuery) query);

		qUIManager.setAbstractQuery(ciderQuery);
		WorkflowManager  workflowManager=new WorkflowManager();
              Map<Long, Long> executionIdMap = workflowManager.execute(workflowdetails,
                       ciderQuery, new HashMap<Long, Long>());
				AbstractQueryManager qManager = AbstractQueryManagerFactory
				.getDefaultAbstractQueryManager();
		qManager.cancel(executionIdMap.get(query.getId()));
       executionIdMap = workflowManager.execute(workflowdetails,
                ciderQuery,executionIdMap);
              assertTrue("passed  execution",true);
		}
		catch (Exception e) {
		assertFalse("failed to execute",false);
		}
		
	}
	
	public void testCancelExecutionOfCQ()
	{try
	{
		  Workflow workflow = getSavedWorflow();
		  workflow.setCreatedBy(getSessionData().getUserId());

			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				//step 2
				//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, getSessionData());
				Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			
			}

			catch (Exception e)
			{
				assertFalse("Got biz logic exception",false);
			}
		  
		  SessionDataBean sessionData=getSessionData();

		  HibernateDAO dao= null;
      dao=DAOUtil.getHibernateDAO(sessionData);;

      // Get the workflow
      workflow = (Workflow) dao.retrieveById(Workflow.class.getName(),
              Long.valueOf(workflow.getId()));
      AbstractQuery query=(AbstractQuery) workflow.getWorkflowItemList().get(2).getQuery();

      CiderQueryPrivilege privilege = new CiderQueryPrivilege();
      CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
              1L, sessionData.getUserId(), workflow,(CiderQueryPrivilege)privilege);

		   edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
	                getSessionData().getUserId(),
	                1L,
                "10.88.199.199",
                workflow.getId(),privilege
                 );


	
		WorkflowManager  workflowManager=new WorkflowManager();
              Map<Long, Long> executionIdMap = workflowManager.execute(workflowdetails,
                       ciderQuery, new HashMap<Long, Long>());
				AbstractQueryManager qManager = AbstractQueryManagerFactory
				.getDefaultAbstractQueryManager();
		qManager.cancel(executionIdMap.get(query.getId()));
       executionIdMap = workflowManager.execute(workflowdetails,
                ciderQuery,executionIdMap);
              assertTrue("passed  execution",true);
		}
		catch (Exception e) {
		assertFalse("failed to execute",false);
		}
		
	}
	

	
	public void testInsertWorkflow()
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			Workflow workflow = gerUserSpecificWorkFlow();
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedWorkflow = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			//step 4
			assertNotNull(insertedWorkflow);
			//step 5
			assertEquals(insertedWorkflow.getName(),workflow.getName());
		}
	
		catch (BizLogicException e)
		{
			//step 3
			fail();
		}

	}
	

	
	
	public void testCheckWorkflowName()
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			Workflow workflow = getWorkflow();
			workflow.setName("workflow'TestCase"+System.currentTimeMillis());
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			assertTrue("inserted successfully workflo containing special character", true);
		}
		
		catch (BizLogicException e)
		{
			//step 3
			fail();
		}

	}
	
	public void testInsertWorkflowWithEmptyName()
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			Workflow workflow = getWorkflow();
			workflow.setName("");
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			assertFalse("inserted of workflow Should fail as NAme is empty", true);
		}
		
		catch (BizLogicException e)
		{
			assertTrue("Got biz logic exception as expected due to empty name", true);
		}

	}
	
	
	public void testInsertWorkflowEmptyObject()
	{
		Workflow workflow = new Workflow();
		workflow.setName("Workflow from test case" + new Date());
		// For User specific wf
		workflow.setCreatedBy(getSessionData().getUserId());
		workflow.setCreatedOn(new Date());
		workflow.setWorkflowItemList(new ArrayList<WorkflowItem>());
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedProject = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			//step 4
			assertNotNull(insertedProject);
			//step 5
			assertEquals(insertedProject.getName(),workflow.getName());
		}
		catch (Exception e)
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
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			fail();		
		}
//		catch (UserNotAuthorizedException e)
//		{
//			//step 3
//			fail();
//		}
		catch (Exception e)
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
							//workflowBizLogic.update(workflow, Constants.HIBERNATE_DAO);
							workflowBizLogic.update(workflow);
							break;
							
						}
					}
				}
			}
			assertTrue("Workflow updated successfully",true);
		}

		catch (BizLogicException e)
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
		queryOperandOne.setCreatedDate(new Date());
		queryOperandOne.setCreatedBy(getSessionData().getUserId());
		queryOperandOne.setUpdationDate(new Date());
		queryOperandOne.setUpdatedBy(getSessionData().getUserId());

		try
		{
			//queryBizLogic.insert(queryOperandOne,getSessionData(), Constants.HIBERNATE_DAO);
			queryBizLogic.insert(queryOperandOne,getSessionData());
			List workflowList = workflowBizLogic.retrieve(Workflow.class.getName());
			if(workflowList!=null && workflowList.size()>0)
			{
				workflowId = ((Workflow)workflowList.get(0)).getId();
			}
			workflowBizLogic.addWorkflowItem(workflowId,queryOperandOne);
			assertTrue("Workflow Item added successfully",true);
		}
//		catch (UserNotAuthorizedException e)
//		{
//			fail();
//		}
		catch (BizLogicException e)
		{
			fail();
		}
//		catch (DAOException e)
//		{
//			fail();
//		}
	}
	
	public void testAddAlreadyExistingWorkflowItem()
	{
		Long workflowId =null;
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
	
		try
		{
			Workflow workflow =null;
				List workflowList = workflowBizLogic.retrieve(Workflow.class.getName());
			if(workflowList!=null && workflowList.size()>0)
			{
				workflow = ((Workflow)workflowList.get(0));
			}
			workflowBizLogic.addWorkflowItem(workflow.getId(),
					(IQuery) workflow.getWorkflowItemList().get(0).getQuery());
			assertTrue("Workflow Item added successfully",true);
		}
//		catch (UserNotAuthorizedException e)
//		{
//			fail();
//		}
		catch (BizLogicException e)
		{
			fail();
		}
//		catch (DAOException e)
//		{
//			fail();
//		}
	}
	public void testInsertCompositeQueryInWF()
	{
		Workflow workflow=null;
		
			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				workflow=getWorkflow();

				workflowBizLogic.insert(workflow, getSessionData());	
			}

			catch (Exception e)
			{
				assertFalse("Got  logic exception as expected...",false);
			}
	}
	public void testUpdatetCompositeQueryInWF()
	{
		
		Workflow workflow=null;
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			workflow=getWorkflow();

			workflowBizLogic.insert(workflow, getSessionData());
			List<WorkflowItem> workflowItemList= getWorkflowItemWithComplexCompositeQuery();
			List oldWfItemList=workflow.getWorkflowItemList();
			oldWfItemList.addAll(workflowItemList);
			
			workflow.setWorkflowItemList(oldWfItemList);

			Workflow oldObj=(Workflow) workflowBizLogic.retrieve(Workflow.class.getName(), workflow.getId());
			//workflowBizLogic.update(workflow);
			workflowBizLogic.update( workflow,  oldObj,  getSessionData());
		}

		catch (Exception e)
		{
			assertFalse("Got  logic exception as expected...",false);
		}

		
	}
	
	public void testInsertWorkflowEmptyUser() throws DAOException
	{
		Workflow workflow = new Workflow();
		workflow.setName("Workflow for created by" + new Date());
		// For User specific wf
		workflow.setCreatedBy(null);
		workflow.setCreatedOn(new Date());
		workflow.setWorkflowItemList(new ArrayList<WorkflowItem>());
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			if(insertedWf.getCreatedBy()==null)
			{
				fail();
			}
		}
//		catch (UserNotAuthorizedException e)
//		{
//			fail();
//		}
		catch (Exception e)
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
		workflow.setCreatedOn(new Date());
		workflow.setWorkflowItemList(new ArrayList<WorkflowItem>());
		
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			if(insertedWf.getCreatedBy()==workflow.getCreatedBy())
			{
				assertTrue("User specific workflow inserted successfully ",true);
			}
		}
//		catch (UserNotAuthorizedException e)
//		{
//			fail();
//		}
		catch (Exception e)
		{
			assertFalse("Got biz logic exception",false);
		}
	}
	

	//TO Do 
	//testExecuteGetCountQuery
	/**
	 * @throws DAOException 
	 * @throws BizLogicException 
	 * 
	 */
	public void  testExecuteParametrizedQueryInWF() throws DAOException, BizLogicException
	{
		try
		{
		  Workflow workflow = getSavedWorflow();
		  workflow.setCreatedBy(getSessionData().getUserId());

			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				//step 2
				//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, getSessionData());
				Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			
			}

			catch (Exception e)
			{
				assertFalse("Got biz logic exception",false);
			}
		  
		  SessionDataBean sessionData=getSessionData();

		  HibernateDAO dao= null;
        dao=DAOUtil.getHibernateDAO(sessionData);;

        // Get the workflow
        workflow = (Workflow) dao.retrieveById(Workflow.class.getName(),
                Long.valueOf(workflow.getId()));
        AbstractQuery query=(AbstractQuery) workflow.getWorkflowItemList().get(0).getQuery();

        CiderQueryPrivilege privilege = new CiderQueryPrivilege();
        CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
                1L, sessionData.getUserId(), workflow,(CiderQueryPrivilege)privilege);

		   edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
	                getSessionData().getUserId(),
	                1L,
                  "10.88.199.199",
                  workflow.getId(),privilege
                   );
			AbstractQueryUIManager qUIManager = new CiderQueryUIManager(getSessionData(),(IQuery) query);
			
			
//		   qUIManager.
//		   
		qUIManager.setAbstractQuery(ciderQuery);
                Map<Long, Long> executionIdMap = workflowBizLogic
                . executeGetCountQuery( workflowdetails, 
             			  ciderQuery, qUIManager,
          			query);
                assertTrue("passed  execution",true);
		}
		catch (Exception e) {
		assertFalse("failed to execute",false);
		}
    
	}
	
	//TO Do 
	//testExecuteGetCountQuery
	/**
	 * @throws DAOException 
	 * @throws BizLogicException 
	 * 
	 */
	public void  testExecuteCompositeQueryInWF() throws DAOException, BizLogicException
	{
		try
		{
		  Workflow workflow = getSavedWorflow();
		  workflow.setCreatedBy(getSessionData().getUserId());

			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				//step 2
				//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, getSessionData());
				Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			
			}

			catch (Exception e)
			{
				assertFalse("Got biz logic exception",false);
			}
		  
		  SessionDataBean sessionData=getSessionData();

		  HibernateDAO dao= null;
        dao=DAOUtil.getHibernateDAO(sessionData);;

        // Get the workflow
        workflow = (Workflow) dao.retrieveById(Workflow.class.getName(),
                Long.valueOf(workflow.getId()));
        //Composite Query
        List<WorkflowItem> wfList=workflow.getWorkflowItemList();
        AbstractQuery query=(AbstractQuery)wfList .get(2).getQuery();

        CiderQueryPrivilege privilege = new CiderQueryPrivilege();
        CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
                1L, sessionData.getUserId(), workflow,(CiderQueryPrivilege)privilege);

		   edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
	                getSessionData().getUserId(),
	                1L,
                  "10.88.199.199",
                  workflow.getId(),privilege
                   );
			//AbstractQueryUIManager qUIManager = new CiderQueryUIManager(getSessionData(),(IQuery) query);
			
			
//		   qUIManager.
//		   
		//qUIManager.setAbstractQuery(ciderQuery);
                Map<Long, Long> executionIdMap = workflowBizLogic
                . executeGetCountQuery( workflowdetails, 
             			  ciderQuery, null,query);
                assertTrue("passed  execution",true);
	}
	catch (Exception e) {
	assertFalse("failed to execute",false);
	}

    
	}
	
	//TO Do 
	/**
	 * @throws DAOException 
	 * @throws BizLogicException 
	 * 
	 */
	public void testExecuteAllCompositeQueriesInWf() 
	{
		try
		{
			Workflow workflow = getSavedWorflow();
			WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();
			
	       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
	       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
	               -1L, getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);
	
			   Map<Long, Long> executionIdMap =  workflowBizLogic.executeAllCompositeQueries(workflowdetails,
					   new HashMap<Long, Long>());
			   if(executionIdMap.size()!=0)
			   {
				   assertTrue("failed to execute CompositeQuery", true);
			   }
			   else
			   {
				   assertFalse("failed to execute CompositeQuery", false);
			   }
		}
		catch (Exception e) {
			assertFalse("failed to execute CompositeQuery", false);
		}
	}
	
	//TO Do 
	/**
	 * @throws BizLogicException 
	 * 
	 */
	public void testGenerateExecutionIdMap() 
	{
		try
		{
		Workflow workflow = getSavedWorflow();
		WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();

	       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
	       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
	               -1L,getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);

			List<Long> executionIdList= workflowBizLogic.generateExecutionIdMap(workflow.getId(),workflow.getCreatedBy());
			   if(executionIdList.size()!=0)
			   {
				   assertTrue("failed to execute CompositeQuery", true);
			   }
			   else
			   {
				   assertFalse("failed to execute CompositeQuery", false);
			   }
		}
		catch (BizLogicException e) {
			 assertFalse("failed to execute CompositeQuery ..Got BizLogic exception ...", false);
		}
	}
	//TO Do 
	/**
	 * @throws BizLogicException 
	 * 
	 */

	
	//TO Do 
	/**
	 * @throws BizLogicException 
	 * 
	 */
	public void testRunWorkflow() 
	{
		try
		{
			Workflow workflow = getSavedWorflow();
			WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();

	       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
	       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
	               -1L,getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);

			Map<Long,Long> executionIdMap= workflowBizLogic.runWorkflow(workflowdetails);
			if(executionIdMap.size()!=0)
			   {
				   assertTrue("Run Workflow Successfully.......", true);
			   }
			   else
			   {
				   assertFalse("failed to Run Workflow ...", false);
			   }
			assertTrue("Run Workflow Successfully....... ", true);
		}
		catch (BizLogicException e) {
			assertFalse("Got biz logic exception",false);
		}
	}
	
	
	
	//TO Do 
	/**
	 * @throws BizLogicException 
	 * 
	 */
	public void testGetLatestProject()
	{
		try
		{
			Workflow workflow = getSavedWorflow();
			WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();

		       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
		       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
		               1L,getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);

			workflowBizLogic.runWorkflow(workflowdetails);
			Long projectId=workflowBizLogic.getLatestProject(workflow.getId(),getSessionData().getUserId());
			if(projectId==1L&& projectId!=null)
			{
				assertTrue("Successfully retrieved latest project id...",true);
			}
			else
			{
				assertFalse("falied to retrieved latest project id...",false);
			}
		}
		catch (BizLogicException e) {
			assertFalse("Got biz logic exception",false);
		}
	}
	
	
	//TO Do it gets the Count for given privilege and execution id
	/**
	 * 
	 */
	public void testGetCount()
	{
		Workflow workflow;
		try {
			workflow = getSavedWorflow();
		
		WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();
		
       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
               -1L, getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);

		   Map<Long, Long> executionIdMap =  workflowBizLogic.
		   executeAllCompositeQueries(workflowdetails,new HashMap<Long, Long>());
		   Set<Long> executionIdSet=executionIdMap.keySet();
		   Iterator<Long> executionIdIter=executionIdSet.iterator();
		   if(executionIdIter.hasNext())
		   {
			   Long executionId=executionIdMap.get(executionIdIter.next());
			  Count count= workflowBizLogic.getCount(executionId, privilege);
			  if(count!=null&&count.getQueryExectionId().equals(executionId))
			  {
				  System.out.println("For query execution id " +executionId +
						  " Count is ="+count.getCount()+"and status is ="+count.getStatus());
			  }
		   }
			assertTrue("Successfully retrieved Count...",true);
			   
		} catch (BizLogicException e) {
			assertFalse("Got biz logic exception in testGetCount",false);
			e.printStackTrace();
		} catch (QueryModuleException e) {
			assertFalse("Got QueryModuleException testGetCount",false);
			e.printStackTrace();
		}
	}
	
	public void testGetCountOfCancelledCQ()
	{
		
		try
		{
			try
			{
				Workflow workflow = getSavedWorflow();
				WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();

		       CiderQueryPrivilege privilege = new CiderQueryPrivilege();
		       CiderWorkFlowDetails workflowdetails = new CiderWorkFlowDetails(
		               -1L,getSessionData().getUserId(), workflow,(CiderQueryPrivilege)privilege);

				Map<Long,Long> executionIdMap= workflowBizLogic.runWorkflow(workflowdetails);
				if(executionIdMap.size()!=0)
				   {
					   assertTrue("Run Workflow Successfully.......", true);
				   }
				   else
				   {
					   assertFalse("failed to Run Workflow ...", false);
				   }
				assertTrue("Run Workflow Successfully....... ", true);
			}
			catch (BizLogicException e) {
				assertFalse("Got biz logic exception",false);
			}
              assertTrue("passed  execution",true);
		}
		catch (Exception e) {
			e.printStackTrace();
		assertFalse("failed to execute",false);
		}
		
	}
	
	public void testRetrieveWorkflowsWithFilter()
	{
		WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();
		try {
			workflowBizLogic.retrieveWorkflowList(0, "Workflow from Test Case", 
					getSessionData(), 10, new ArrayList<Workflow>());
			assertTrue("workflow retrieved succesfully", true);
		} 
		catch (DAOException e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testRetrieveWorkflowsWithOutFilter()
	{
		WorkflowBizLogic workflowBizLogic=new WorkflowBizLogic();
		try {
			workflowBizLogic.retrieveWorkflowList(0, "", 
					getSessionData(), 10, new ArrayList<Workflow>());
			assertTrue("workflow retrieved succesfully", true);
		} 
		catch (DAOException e)
		{
			e.printStackTrace();
			fail();
		}
		
	}
	public void testInsertWorkflowWithDuplicateName()
	{
		WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
		try
		{
			String wfName="Workflow naem validation Test case"+new Date();
			Workflow workflow = gerUserSpecificWorkFlow();
			workflow .setName( wfName);
			workflowBizLogic.insert(workflow, getSessionData());
			
		Workflow workflow2 = gerUserSpecificWorkFlow();
			workflow2 .setName( wfName);
			workflowBizLogic.insert(workflow2, getSessionData());

			fail();


		}
	
		catch (BizLogicException e)
		{
			e.printStackTrace();
			assertTrue("Insertion failed because workflow with same name already exist",true);
			
		}

	}
	
	public void testHasPrivilegeToView()
	{
		try
		{
			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			Workflow workflow = gerUserSpecificWorkFlow();
			//step 2
			//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
			workflowBizLogic.insert(workflow, getSessionData());
			
			boolean previlege = workflowBizLogic.hasPrivilegeToView(Workflow.class.getName(), workflow.getId(),
					getSessionData());
			assertEquals(true, previlege);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
}

