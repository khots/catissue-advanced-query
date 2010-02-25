package edu.wustl.query.bizlogic;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import junit.framework.TestCase;

public class SaveQueryBizLogicTestCase extends TestCase
{
	public void populateParameterizedQueryData()
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		SaveQueryBizLogic bizLogic = new SaveQueryBizLogic();
		IParameterizedQuery originalQuery = bizLogic.populateParameterizedQueryData(query);
	}

	public void testGetQuery()
	{
		try
		{
			IQuery query = new SaveQueryBizLogic().getQuery(Long.valueOf(1));
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}

	public void testGetUserById()
	{
		try
		{
			new SaveQueryBizLogic().getUserById("1");
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}

	public void testGetSharingDetailsBean()
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		try
		{
			new SaveQueryBizLogic().getSharingDetailsBean(query);
		}
		catch (BizLogicException e)
		{
			e.printStackTrace();
		}
	}
}
