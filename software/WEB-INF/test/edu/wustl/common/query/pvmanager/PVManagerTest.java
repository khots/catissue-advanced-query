/**
 * 
 */

package edu.wustl.common.query.pvmanager;

import junit.framework.TestCase;
import edu.wustl.common.query.pvmanager.impl.PVManagerException;
import edu.wustl.query.bizlogic.SearchPermissibleValueBizlogic;


/**
 * @author ashish_gupta
 *
 */
public class PVManagerTest extends TestCase
{	

	/**
	 * 
	 */
	public void TestVolumeInDb()
	{
		SearchPermissibleValueBizlogic searchPermissibleValueBizlogic = new SearchPermissibleValueBizlogic();
		try
		{
			searchPermissibleValueBizlogic.getVolumeInDb();
		}
		catch (PVManagerException e)
		{
			fail();
		}
	}
}
