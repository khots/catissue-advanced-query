/**
 *
 */
package edu.wustl.query.bizlogic;

import java.util.Collection;

import junit.framework.TestCase;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.util.DAOUtility;
import edu.wustl.query.actionForm.SaveQueryForm;

/**
 * @author pooja_tavase
 *
 */
public class DashboardBizLogicTestCase extends TestCase
{
	/**
	 * Test getPGsForQuery method from DashboardBizLogic class.
	 */
	public void testGetPGsForQuery()
	{
		try
		{
			new DashboardBizLogic().
			getPGsforQuery("21");
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * To test getQueryOwner method from DashboardBizLogic class.
	 */
	public void testGetQueryOwner()
	{
		try
		{
			new DashboardBizLogic().getQueryOwner("21");
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * To test setQueriesToDashboard method from DashboardBizLogic class.
	 */
	public void testSetQueriesToDashboard()
	{
		SessionDataBean sessionDataBean = new SessionDataBean();
		sessionDataBean.setCsmUserId("1");
		SaveQueryForm saveQueryForm = new SaveQueryForm();
		try
		{
			new DashboardBizLogic().setQueriesToDashboard(sessionDataBean, saveQueryForm);
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}

	public void testGetDashboardDetails()
	{
		try
		{
			Collection<IParameterizedQuery> queries = DAOUtility.getInstance().executeHQL(DAOUtility.GET_PARAM_QUERIES_DETAILS);
			new DashboardBizLogic().getDashBoardDetails(queries, "1");
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}
	}

	/*public void testNegativeGetDashboardDetails()
	{
		try
		{
			Collection<IParameterizedQuery> queries = DAOUtility.getInstance().executeHQL(DAOUtility.GET_PARAM_QUERIES_DETAILS);
			new DashboardBizLogic().getDashBoardDetails(queries, null);
			assertFalse("It should throw Null pointer exception....",true);
		}
		catch(NullPointerException e)
		{
			assertTrue("Expected NullPointerExcpetionException!!!, Null pointer",true);
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}
	}*/
}
