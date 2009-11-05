package edu.wustl.query.domain;

import edu.wustl.common.querysuite.queryobject.IAbstractQuery;
import junit.framework.TestCase;


public class WorkflowTestCases extends TestCase
{
	public void testGetQuery()
	{
		try
		{
			Workflow workflow = new Workflow();
			IAbstractQuery query = workflow.getQuery("22");
			assertNotNull(query);
			query = workflow.getQuery("42");
			assertNotNull(query);
			query = workflow.getQuery("22_42_+");
			assertNotNull(query);
			
			query = workflow.getQuery("22");
			assertNotNull(query);
			query = workflow.getQuery("42");
			assertNotNull(query);
			query = workflow.getQuery("22_42_");
			assertNotNull(query);
			
			query = workflow.getQuery("22");
			assertNotNull(query);
			query = workflow.getQuery("42");
			assertNotNull(query);
			query = workflow.getQuery("22_42_*");
			assertNotNull(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}	
