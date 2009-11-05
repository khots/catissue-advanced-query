package edu.wustl.query.bizlogic;

import java.util.List;

import edu.wustl.common.beans.RecentQueriesBean;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.security.exception.SMException;


public class RecentQueriesBizLogicTestCases extends QueryBaseTestCases {

	public RecentQueriesBizLogicTestCases(String name) {
		super(name);
	}
	
	public void testRetrieveQueries()
	{
		SessionDataBean sessionData = getSessionData();
		RecentQueriesBizlogic resBizlogic = new RecentQueriesBizlogic();
		try {
			
			List<List<String>> retrieveQueries = resBizlogic.retrieveQueries(sessionData, 10);
			if(retrieveQueries!=null)
			{
				assertTrue("Recent quries retrived succesfully",true);
			}
		} catch (SMException e) {
			e.printStackTrace();
			fail();
		} catch (QueryModuleException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testPopulateRecentQueryBean()
	{
		SessionDataBean sessionData = getSessionData();
		RecentQueriesBizlogic resBizlogic = new RecentQueriesBizlogic();
			try {
				List<List<String>> retrieveQueries = resBizlogic.retrieveQueries(sessionData, 10);
				List<RecentQueriesBean> populateRecentQueryBean = resBizlogic.populateRecentQueryBean(sessionData,retrieveQueries,"TestCaseQuery");
				
				if(populateRecentQueryBean!=null)
				{
					assertTrue("Recent Queries retrived", true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}
				 			
	}
}


