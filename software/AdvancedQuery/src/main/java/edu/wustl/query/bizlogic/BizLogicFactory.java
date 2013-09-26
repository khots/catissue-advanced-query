/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.query.bizlogic;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.query.util.global.AQConstants;

public class BizLogicFactory
{

	//Singleton instance
	private static BizLogicFactory factory = null;

	static
	{
		factory = new BizLogicFactory();
	}

	protected BizLogicFactory()
	{
	}

	/**
	 * Setter method in singleton class is to setup mock unit testing.
	 * */
	public static void setBizLogicFactory(BizLogicFactory externalFactory)
	{
		factory = externalFactory;
	}

	public static BizLogicFactory getInstance()
	{
		return factory;
	}

	public IBizLogic getBizLogic(int FORM_ID)
	{
		IBizLogic bizLogic = null;
		switch (FORM_ID)
		{
			case AQConstants.ADVANCE_QUERY_INTERFACE_ID:
                bizLogic = (IBizLogic) new QueryBizLogic();
                break;

			/*case Constants.ADVANCE_QUERY_INTERFACE_ID:
				bizLogic = new AdvanceQueryBizlogic();
				break;*/
			case AQConstants.QUERY_INTERFACE_ID :
			    bizLogic = (IBizLogic) new QueryBizLogic();
				break;

			case AQConstants.QUERY_INTERFACE_BIZLOGIC_ID :
			    bizLogic = (IBizLogic) new QueryBizLogic();
				break;
		}
		return bizLogic;
	}
}
