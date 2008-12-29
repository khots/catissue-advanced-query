
package edu.wustl.query.bizlogic;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.bizlogic.QueryBizLogic;
import edu.wustl.query.util.global.Constants;

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
			case Constants.SIMPLE_QUERY_INTERFACE_ID :
				bizLogic = new QueryBizLogic();
				break;

			case Constants.ADVANCE_QUERY_INTERFACE_ID:
				bizLogic = new edu.wustl.query.bizlogic.QueryBizLogic();
				break;
			case Constants.QUERY_INTERFACE_ID :
				bizLogic = new QueryBizLogic();
				break;

			case Constants.QUERY_INTERFACE_BIZLOGIC_ID :
				bizLogic = new QueryBizLogic();
				break;
				
			case Constants.WORKFLOW_BIZLOGIC_ID :
				bizLogic = new WorkflowBizLogic();
				break;
			
			case Constants.SEARCH_PV_FROM_VOCAB_BILOGIC_ID :
				bizLogic = new SearchPermissibleValueBizlogic();
				break;
		}
		return bizLogic;
	}
}
