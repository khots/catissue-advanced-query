package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.impl.PassTwoXQueryGenerator;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.exceptions.CyclicException;
import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.CompositeQuery;
import edu.wustl.common.querysuite.queryobject.impl.Operation;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.Union;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.query.domain.Workflow;
import edu.wustl.query.domain.WorkflowItem;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ResultsViewComponentGenerator;


public class QueryBaseTestCases extends TestCase
{
	protected static String PARAMETERIZED_QUERY_ONE="PQ1";
	protected static String PARAMETERIZED_QUERY_TWO="PQ2";
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		ApplicationProperties.initBundle("ApplicationResources");
		Properties queryProperties = new Properties();
		Variables.properties = queryProperties;
		Variables.properties.setProperty("permissiblevalue.manager.impl",
				"edu.wustl.common.query.pvmanager.impl.LexBIGPermissibleValueManager");
		
		System.setProperty("configured.vocabs.file", "query-properties/testConfiguredVocabs.properties");
		System.setProperty("vocab.properties.file","query-properties/testVocab.properties");
		//System.setProperty("LG_CONFIG_FILE","C:/LexBigTestData/config/config.props");
		
		Variables.properties.setProperty("queryType","X");
		Variables.queryITableManagerClassName="edu.wustl.cider.querymanager.CIDERITableManager";
	}
	
	public QueryBaseTestCases()
	{
		
	}
	
	public QueryBaseTestCases(String arg0)
	{
		super(arg0);
	}
	
	public Workflow getWorkflow() throws BizLogicException
	{
		Workflow workflow = new Workflow();
		List<WorkflowItem> workflowItemList = new ArrayList<WorkflowItem>();

		
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		ParameterizedQuery queryOperandOne = new ParameterizedQuery();
		queryOperandOne.setName("PQ1 for workflow");
		queryOperandOne.setCreatedDate(new Date());
		queryOperandOne.setCreatedBy(getSessionData().getUserId());
		queryOperandOne.setUpdationDate(new Date());
		queryOperandOne.setUpdatedBy(getSessionData().getUserId());

		queryBizLogic.insert(queryOperandOne,getSessionData());
		
		ParameterizedQuery queryOperandTwo = new ParameterizedQuery();
		queryOperandTwo.setName("PQ2 for workflow");
		queryOperandTwo.setCreatedDate(new Date());
		queryOperandTwo.setCreatedBy(getSessionData().getUserId());
		queryOperandTwo.setUpdationDate(new Date());
		queryOperandTwo.setUpdatedBy(getSessionData().getUserId());

		
		queryBizLogic.insert(queryOperandTwo,getSessionData());
		
		workflow.setName("Workflow from Test Case"+System.currentTimeMillis());
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
		query.setCreatedDate(new Date());
		query.setCreatedBy(getSessionData().getUserId());
		query.setUpdationDate(new Date());
		query.setUpdatedBy(getSessionData().getUserId());

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
		sessionDataBean.setCsmUserId("1");
		sessionDataBean.setUserName("admin@admin.com");
		return sessionDataBean;
	}
	
	public Workflow gerUserSpecificWorkFlow() throws  BizLogicException
	{
		Workflow workflow=getWorkflow();
		workflow.setName("User Specific Workflow Test case"+new Date());
		workflow.setCreatedBy(getSessionData().getUserId());
		workflow.setCreatedOn(new Date());
		return workflow;
	}
	public Workflow getSavedWorflow() throws BizLogicException
	{
		 Workflow workflow=null;

			WorkflowBizLogic workflowBizLogic = new WorkflowBizLogic();
			try
			{
				 workflow = getWorkflowContainigSavedParametriezedQuery();
				 workflow.setName("testExecuteAllCompositeQueries_"+System.currentTimeMillis());
				  workflow.setCreatedBy(getSessionData().getUserId());
				//step 2
				//workflowBizLogic.insert(workflow, getSessionData(), Constants.HIBERNATE_DAO);
				workflowBizLogic.insert(workflow, getSessionData());
				Workflow insertedWf = (Workflow) workflowBizLogic.retrieve(Workflow.class.getName(),workflow.getId());
			
			}

			catch (Exception e)
			{
				assertFalse("Got biz logic exception",false);
			}
			return workflow;
		  
	}
	public Workflow getWorkflowContainigSavedParametriezedQuery() throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException, CyclicException, BizLogicException
	{

		Workflow workflow = new Workflow();
		List<WorkflowItem> workflowItemList = new ArrayList<WorkflowItem>();

		
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		IParameterizedQuery queryOperandOne =QueryBuilder.skeletalPersonQuery();
		queryOperandOne.setName("PQ1 for workflow"+System.currentTimeMillis());
		queryOperandOne.setCreatedDate(new Date());
		queryOperandOne.setCreatedBy(getSessionData().getUserId());
		queryOperandOne.setUpdationDate(new Date());
		queryOperandOne.setUpdatedBy(getSessionData().getUserId());
		queryOperandOne.setType(Constants.QUERY_TYPE_GET_COUNT);
		queryBizLogic.insert(queryOperandOne,getSessionData());
		
		IParameterizedQuery queryOperandTwo = QueryBuilder.skeletalPersonQuery();
		queryOperandTwo.setName("PQ2 for workflow"+System.currentTimeMillis());
		queryOperandTwo.setCreatedDate(new Date());
		queryOperandTwo.setCreatedBy(getSessionData().getUserId());
		queryOperandTwo.setUpdationDate(new Date());
		queryOperandTwo.setUpdatedBy(getSessionData().getUserId());
	
		queryOperandTwo.setType(Constants.QUERY_TYPE_GET_COUNT);
		queryBizLogic.insert(queryOperandTwo,getSessionData());
		
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
		query.setCreatedDate(new Date());
		query.setCreatedBy(getSessionData().getUserId());
		query.setUpdationDate(new Date());
		query.setUpdatedBy(getSessionData().getUserId());
		
		Operation operation = new Union();
		operation.setOperandOne(queryOperandOne);
		operation.setOperandTwo(queryOperandTwo);
		
		((CompositeQuery)query).setOperation(operation);
		workflowItem.setQuery(query);
		workflowItemList.add(workflowItem);
  	
		
		workflow.setWorkflowItemList(workflowItemList);

		return workflow;
		
	}
	public Workflow getWorkflowContainigParametriezedQuery() throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException, CyclicException, BizLogicException
	{

		Workflow workflow = new Workflow();
		List<WorkflowItem> workflowItemList = new ArrayList<WorkflowItem>();

		
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		IParameterizedQuery queryOperandOne =QueryBuilder.skeletalPersonQuery();
		queryOperandOne.setName("PQ1 for workflow"+System.currentTimeMillis());
		queryOperandOne.setCreatedDate(new Date());
		queryOperandOne.setCreatedBy(getSessionData().getUserId());
		queryOperandOne.setUpdationDate(new Date());
		queryOperandOne.setUpdatedBy(getSessionData().getUserId());
			
		IParameterizedQuery queryOperandTwo = QueryBuilder.skeletalPersonQuery();
		queryOperandTwo.setName("PQ2 for workflow"+System.currentTimeMillis());
		queryOperandTwo.setCreatedDate(new Date());
		queryOperandTwo.setCreatedBy(getSessionData().getUserId());
	
		
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
		query.setCreatedDate(new Date());
		query.setCreatedBy(getSessionData().getUserId());
		query.setUpdationDate(new Date());
		query.setUpdatedBy(getSessionData().getUserId());
		Operation operation = new Union();
		operation.setOperandOne(queryOperandOne);
		operation.setOperandTwo(queryOperandTwo);
		
		((CompositeQuery)query).setOperation(operation);
		workflowItem.setQuery(query);
		workflowItemList.add(workflowItem);
  	
		
		workflow.setWorkflowItemList(workflowItemList);

		return workflow;
		
	}
	public List<WorkflowItem> getWorkflowItemWithComplexCompositeQuery() throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException, CyclicException, BizLogicException
	{
		List<WorkflowItem> workflowItemList=new ArrayList<WorkflowItem>();
		QueryBizLogic queryBizLogic = new QueryBizLogic();
		IParameterizedQuery queryOperandOne =new ParameterizedQuery();
		queryOperandOne.setName("PQ1 for workflow"+System.currentTimeMillis());
		queryOperandOne.setCreatedDate(new Date());
		queryOperandOne.setCreatedBy(getSessionData().getUserId());
		queryOperandOne.setUpdationDate(new Date());
		queryOperandOne.setUpdatedBy(getSessionData().getUserId());
		queryBizLogic.insert(queryOperandOne,getSessionData());
		
		WorkflowItem workflowItem =new WorkflowItem();
		workflowItem.setPosition(1);
		workflowItem.setQuery(queryOperandOne);
		workflowItemList.add(workflowItem);
			
		IParameterizedQuery queryOperandTwo = new ParameterizedQuery();
		queryOperandTwo.setName("PQ2 for workflow"+System.currentTimeMillis());
		queryOperandTwo.setCreatedDate(new Date());
		queryOperandTwo.setCreatedBy(getSessionData().getUserId());
		queryOperandTwo.setUpdationDate(new Date());
		queryOperandTwo.setUpdatedBy(getSessionData().getUserId());
		queryBizLogic.insert(queryOperandTwo,getSessionData());
		
		 workflowItem =new WorkflowItem();
		workflowItem.setPosition(2);
		workflowItem.setQuery(queryOperandTwo);
		workflowItemList.add(workflowItem);
		
		IParameterizedQuery queryOperandthree =new ParameterizedQuery();
		queryOperandthree.setName("PQ1 for workflow"+System.currentTimeMillis());
		queryOperandthree.setCreatedDate(new Date());
		queryOperandthree.setCreatedBy(getSessionData().getUserId());
		queryOperandthree.setUpdationDate(new Date());
		queryOperandthree.setUpdatedBy(getSessionData().getUserId());
		queryBizLogic.insert(queryOperandthree,getSessionData());
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(3);
		workflowItem.setQuery(queryOperandthree);
		workflowItemList.add(workflowItem);
			
		IParameterizedQuery queryOperandfour = new ParameterizedQuery();
		queryOperandfour.setName("PQ2 for workflow"+System.currentTimeMillis());
		queryOperandfour.setCreatedDate(new Date());
		queryOperandfour.setCreatedBy(getSessionData().getUserId());
		queryOperandfour.setUpdationDate(new Date());
		queryOperandfour.setUpdatedBy(getSessionData().getUserId());
		queryBizLogic.insert(queryOperandfour,getSessionData());
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(4);
		workflowItem.setQuery(queryOperandfour);
		workflowItemList.add(workflowItem);
			
			
	
		IAbstractQuery cquery1 = new CompositeQuery();
		cquery1.setCreatedDate(new Date());
		cquery1.setCreatedBy(getSessionData().getUserId());
		cquery1.setUpdationDate(new Date());
		cquery1.setUpdatedBy(getSessionData().getUserId());
		Operation operation1 = new Union();
		operation1.setOperandOne(queryOperandOne);
		operation1.setOperandTwo(queryOperandTwo);
		((CompositeQuery)cquery1).setOperation(operation1);
		
	
		IAbstractQuery cquery2 = new CompositeQuery();
		cquery2.setCreatedDate(new Date());
		cquery2.setCreatedBy(getSessionData().getUserId());
		cquery2.setUpdationDate(new Date());
		cquery2.setUpdatedBy(getSessionData().getUserId());
		Operation operation2 = new Union();
		operation2.setOperandOne(queryOperandthree);
		operation2.setOperandTwo(queryOperandfour);
		((CompositeQuery)cquery2).setOperation(operation2);
		

		
		IAbstractQuery cquery3 = new CompositeQuery();
		cquery3.setCreatedDate(new Date());
		cquery3.setCreatedBy(getSessionData().getUserId());
		cquery3.setUpdationDate(new Date());
		cquery3.setUpdatedBy(getSessionData().getUserId());
		Operation operation3 = new Union();
		operation3.setOperandOne(cquery1);
		operation3.setOperandTwo(cquery2);
		((CompositeQuery)cquery3).setOperation(operation3);
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(7);
		workflowItem.setQuery(cquery3);
		workflowItemList.add(workflowItem);
		
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(5);
		workflowItem.setQuery(cquery1);
		workflowItemList.add(workflowItem);
			
		workflowItem =new WorkflowItem();
		workflowItem.setPosition(6);
		workflowItem.setQuery(cquery2);
		workflowItemList.add(workflowItem);
		
		return workflowItemList;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws QueryModuleException
	 */
	public QueryDetails getQueryDetailsObject(IParameterizedQuery query) throws QueryModuleException
	{
		QueryDetails queryDetailsObj = new QueryDetails();
		queryDetailsObj.setQueryExecutionId(1L);
		queryDetailsObj.setQuery(query);
		PassTwoXQueryGenerator passTwoQueryGenerator = new PassTwoXQueryGenerator();
		passTwoQueryGenerator.generateQuery(query);
		ResultsViewComponentGenerator viewGenerator = new ResultsViewComponentGenerator(query);
		
		List<OutputTreeDataNode> rootOutputTreeNodeList = viewGenerator.getRootOutputTreeNode(); 
		queryDetailsObj.setRootOutputTreeNodeList(rootOutputTreeNodeList);
		Map<String, OutputTreeDataNode> uniqueIdNodesMap = viewGenerator.getAllChildrenNodes(rootOutputTreeNodeList);
		queryDetailsObj.setUniqueIdNodesMap(uniqueIdNodesMap);
		return queryDetailsObj;
	}
	
	public IQuery getQueryObject() throws Exception
   {
	   IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
	   query.setCreatedBy(getSessionData().getUserId());
	   query.setCreatedDate(new Date());
	   query.setUpdatedBy(getSessionData().getUserId());
	   query.setUpdationDate(new Date());

	   query.setName("TestCaseQuery");
	   query.setType(QueryType.GET_COUNT.type);
	   //IExpression person = QueryBuilder.createExpression(constraints, null, Constants.PERSON);
	   QueryBuilder.addCondition(query.getConstraints().getExpression(1), "personUpi", RelationalOperator.NotEquals, "1317900");
	   return query;
   }
   
   public String getConditionList (IQuery query)
   {
	   StringBuffer queryString= new StringBuffer("@#condition#@2_401!*=*!Contains!*=*!9!&&!@#condition#@1_31!*=*!Contains!*=*!9!&&!");
	   return queryString.toString();
   }
   
   public Map<String, String> getdisplayNameMap()
   {
	   Map<String, String> displayNameMap = new HashMap<String, String>();
	   displayNameMap.put("1_31","person upi");
	   displayNameMap.put("2_401","socialSecurityNumber");
	   return displayNameMap; 
   }

}
