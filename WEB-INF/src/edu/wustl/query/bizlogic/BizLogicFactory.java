package edu.wustl.query.bizlogic;

import edu.wustl.query.util.global.Constants;
import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.common.bizlogic.QueryBizLogic;


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
		switch(FORM_ID)
        {
        	case Constants.SIMPLE_QUERY_INTERFACE_ID:
        		bizLogic = new QueryBizLogic();
        		break;
        		
        	/*case Constants.ADVANCE_QUERY_INTERFACE_ID:
        		bizLogic = new AdvanceQueryBizlogic();
        		break;*/
        	case Constants.QUERY_INTERFACE_ID:
        		bizLogic = new QueryBizLogic();
    			break;
        }
		return bizLogic;
    }
}
