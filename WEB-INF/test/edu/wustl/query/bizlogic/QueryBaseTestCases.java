package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wustl.cider.exception.UserNotAuthenticatedException;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.bizlogic.QueryBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Operation;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Query;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.common.security.exceptions.UserNotAuthorizedException;
import edu.wustl.common.util.dbManager.DAOException;
import edu.wustl.common.util.global.Constants;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import junit.framework.TestCase;


public class QueryBaseTestCases extends TestCase
{
	protected static String PARAMETERIZED_QUERY_ONE="PQ1";
	protected static String PARAMETERIZED_QUERY_TWO="PQ2";
	
	public QueryBaseTestCases()
	{
		
	}
	
	public QueryBaseTestCases(String arg0)
	{
		super(arg0);
	}
	
	public Workflow getWorkflow() throws UserNotAuthorizedException, BizLogicException
	{
		Workflow workflow = new Workflow();
		List<WorkflowItem> workflowItemList = new ArrayList<WorkflowItem>();

		
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		ParameterizedQuery queryOperandOne = new ParameterizedQuery();
		queryOperandOne.setName("PQ1 for workflow");
		queryBizLogic.insert(queryOperandOne,getSessionData(), Constants.HIBERNATE_DAO);
		
		ParameterizedQuery queryOperandTwo = new ParameterizedQuery();
		queryOperandTwo.setName("PQ2 for workflow");
		queryBizLogic.insert(queryOperandTwo,getSessionData(), Constants.HIBERNATE_DAO);
		
		workflow.setName("Workflow from Test Case"+new Date());
		workflow.setCreatedOn(new Date());
		
		WorkflowItem workflowItem =new WorkflowItem();
		workflowItem.setPosition(1);
		workflowItem.setQuery(queryOperandOne);
		workflowItemList.add(workflowItem);
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(2);
		workflowItem.setQuery(queryOperandTwo);
		workflowItemList.add(workflowItem);
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(3);
		IAbstractQuery query = new CompositeQuery();
		Operation operation = new Union();
		operation.setOperandOne(queryOperandOne);
		operation.setOperandTwo(queryOperandTwo);
		
		((CompositeQuery)query).setOperation(operation);
		workflowItem.setQuery(query);
		workflowItemList.add(workflowItem);
  	
		
		workflow.setWorkflowItemList(workflowItemList);

		return workflow;
	}
	
	protected SessionDataBean getSessionData()
	{
		SessionDataBean sessionDataBean = new SessionDataBean();
		sessionDataBean.setFirstName("admin");
		sessionDataBean.setLastName("admin");
		sessionDataBean.setSecurityRequired(false);
		sessionDataBean.setUserId(1L);
		sessionDataBean.setUserName("admin@admin.com");
		return sessionDataBean;
	}
	
	public Workflow gerUserSpecificWorkFlow() throws UserNotAuthorizedException, BizLogicException
	{
		Workflow workflow=getWorkflow();
		workflow.setName("User Specific Workflow Test case"+new Date());
		workflow.setCreatedBy(getSessionData().getUserId());
		return workflow;
	}
}
