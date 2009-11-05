
package edu.wustl.query.bizlogic;

import edu.wustl.common.bizlogic.IBizLogic;
import edu.wustl.query.util.global.Constants;
/**
 * @author siddharth_shah
 */
public class BizLogicFactory
{

	//Singleton instance
	private static BizLogicFactory factory = null;

	static
	{
		factory = new BizLogicFactory();
	}

	/**
	 * Default Constructor.
	 */
	protected BizLogicFactory()
	{
    	// TODO Auto-generated constructor stub
	}

	/**
	 * Setter method in singleton class is to setup mock unit testing.
	 * @param externalFactory - BizLogicFactory
	 * */
	public static void setBizLogicFactory(BizLogicFactory externalFactory)
	{
		factory = externalFactory;
	}

	/**
	 * @return BizLogicFactory
	 */
	public static BizLogicFactory getInstance()
	{
		return factory;
	}

	/**
	 * @param formId - form id
	 * @return IBizLogic
	 */
	public IBizLogic getBizLogic(int formId)
	{
		IBizLogic bizLogic = null;
		switch(formId)
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
			case Constants.PV_VOCAB_BILOGIC_ID :
				bizLogic = new SearchPermissibleValueBizlogic();
				break;
			default:
				break;
		}
		return bizLogic;
	}
}
