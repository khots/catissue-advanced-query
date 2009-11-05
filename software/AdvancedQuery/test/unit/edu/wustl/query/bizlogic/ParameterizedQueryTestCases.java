package edu.wustl.query.bizlogic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.wustl.cider.query.CiderQuery;
import edu.wustl.cider.querymanager.CiderQueryPrivilege;
import edu.wustl.common.query.factory.AbstractQueryManagerFactory;
import edu.wustl.common.query.factory.ITableManagerFactory;
import edu.wustl.common.query.itablemanager.ITableManager;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.common.querysuite.queryobject.RelationalOperator;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.query.enums.QueryType;
import edu.wustl.query.querymanager.AbstractQueryManager;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;

public class ParameterizedQueryTestCases extends QueryBaseTestCases{

	static
	{
		Variables.queryUIManagerClassName ="edu.wustl.cider.util.CiderQueryUIManager";
		Variables.abstractQueryManagerClassName="edu.wustl.cider.querymanager.CiderQueryManager";
	}
	public ParameterizedQueryTestCases()
	{
		super();
	}
	
	public ParameterizedQueryTestCases(String arg0)
	{
		super(arg0);
	}
	
    public void testSaveParameterizedQuery()
    {
    	try
    	{
    		IQuery prameterizedQuery = getQueryObject();
    		IParameterizedQuery query=null;
    		String conditionList = getConditionList(prameterizedQuery);
    		Map<String, String> displayNameMap = getdisplayNameMap();
    		CreateQueryObjectBizLogic bizlogic = new CreateQueryObjectBizLogic();
    		String errormsg =bizlogic.setInputDataToQuery(conditionList, prameterizedQuery.getConstraints(), displayNameMap, prameterizedQuery);
    		QueryBizLogic querybizLogic = new QueryBizLogic();
    		if(errormsg==null||errormsg==""||errormsg.length()==0)
    		{	
    			querybizLogic.insertSavedQueries((IParameterizedQuery)prameterizedQuery, getSessionData(), false);
    		    assertTrue("Parameterized Query saved Successfully", true);

    		    query =(IParameterizedQuery) querybizLogic.retrieve(ParameterizedQuery.class.getName(), Constants.ID, prameterizedQuery.getId()).get(0);
					 			
				if (query != null )
    			{
    				assertNotNull(query.getParameters());
    			}
				else
				{
                  fail();					
				}
    		}
      }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	  e.getMessage();
    	  fail();	
    	}
   }
	
    public void testParameterRetainedOnParameterizedQuery()
    {
    	try
    	{
    		IQuery prameterizedQuery = getQueryObject();
    		IParameterizedQuery query=null;
    		String conditionList = getConditionList(prameterizedQuery);
    		Map<String, String> displayNameMap = getdisplayNameMap();
    		CreateQueryObjectBizLogic bizlogic = new CreateQueryObjectBizLogic();
    		String errormsg =bizlogic.setInputDataToQuery(conditionList, prameterizedQuery.getConstraints(), displayNameMap, prameterizedQuery);
    		QueryBizLogic querybizLogic = new QueryBizLogic();
    		if(errormsg==null||errormsg==""||errormsg.length()==0)
    		{	
    			querybizLogic.insertSavedQueries((IParameterizedQuery)prameterizedQuery, getSessionData(), false);
    		    assertTrue("Parameterized Query saved Successfully", true);

    		    query =(IParameterizedQuery) querybizLogic.retrieve(ParameterizedQuery.class.getName(), Constants.ID, prameterizedQuery.getId()).get(0);
					 			
				if (query != null )
    			{
    				assertNotNull(query.getParameters());
    				
    				AbstractQueryManager qManager = AbstractQueryManagerFactory
    				.getDefaultAbstractQueryManager();
    			      CiderQueryPrivilege privilege = new CiderQueryPrivilege();
    				  edu.wustl.common.query.AbstractQuery ciderQuery = new CiderQuery(query, 0L, null,
    			                getSessionData().getUserId(),
    			                1L,
    		                "10.88.199.199",
    		               null,privilege
    		                 );
    				
    				//Here once query is  updated , get it's execution id
    				Long query_exec_id = qManager.execute(ciderQuery);
    				
    				ITableManager itableManager = ITableManagerFactory.getDefaultITableManager();
    				Map<Long, Long> excutionAndProjectIdMap=null;
    				excutionAndProjectIdMap = itableManager.getLatestProjectIdForQuery(
    						query.getId(), getSessionData().getUserId());
    				if(excutionAndProjectIdMap!=null)
    				{
    					assertTrue("Successfuly executed parametrized query with project=" +excutionAndProjectIdMap
    							.get(query_exec_id)+"Execution id="+query_exec_id,true);
    				}
    				else
    				{
                      fail();					
    				}
    				
    			}
				else
				{
                  fail();					
				}
    		}
      }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	  e.getMessage();
    	  fail();	
    	}
   }
	

}
