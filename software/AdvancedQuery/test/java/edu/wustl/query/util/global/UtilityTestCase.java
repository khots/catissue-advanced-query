package edu.wustl.query.util.global;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import junit.framework.TestCase;

public class UtilityTestCase extends TestCase
{
	public void testIsCOnditionOnIdentifiedField()
	{
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		boolean isConditionOnIdData = Utility.isConditionOnIdentifiedField(query);
		assertEquals("is condition on identifier field",false, isConditionOnIdData);
	}

	public void getDisplayNameForColumn()
	{
		EntityCache cache = EntityCacheFactory.getInstance();
		EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
        participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
		AttributeInterface attribute = GenericQueryGeneratorMock.findAttribute(participantEntity, "birthDate");
		Utility.getDisplayNameForColumn(attribute);
		Utility.getPrimaryKey(participantEntity);
	}

	public void testCheckFeatureUsage()
	{
		Utility.checkFeatureUsage("app.name");
	}
}
