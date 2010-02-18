package edu.wustl.query.bizlogic;

import java.util.List;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import junit.framework.TestCase;

public class QueryCsmBizLogicTestCase extends TestCase
{
	public void testGetMainEntityList()
	{
		EntityCache cache = EntityCacheFactory.getInstance();
        EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
        participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
        EntityInterface csrEntity = GenericQueryGeneratorMock.createEntity("ClinicalStudyRegistration");
        csrEntity = GenericQueryGeneratorMock.getEntity(cache, csrEntity);
        QueryCsmBizLogic.getMainEntityList(participantEntity, csrEntity);
	}

	public void testExecuteQuery()
	{
		QueryCsmBizLogic queryCsmBizLogic = new QueryCsmBizLogic();
		List result = queryCsmBizLogic.executeCSMQuery("Select * from catissue_participant", null, null, null, true);
	}
}
