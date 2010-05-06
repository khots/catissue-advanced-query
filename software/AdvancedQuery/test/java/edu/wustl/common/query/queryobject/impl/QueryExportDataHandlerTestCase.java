package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AssociationInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.BaseAbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import junit.framework.TestCase;

public class QueryExportDataHandlerTestCase extends TestCase
{
	public void testUpdateRowDataList()
	{
		Map<BaseAbstractAttributeInterface,Object> denormalizationMap =
			new HashMap<BaseAbstractAttributeInterface, Object>();
		Map<BaseAbstractAttributeInterface,Object> innerMap =
			new HashMap<BaseAbstractAttributeInterface, Object>();
		List<Map<BaseAbstractAttributeInterface,Object>> innerMapList =
			new ArrayList<Map<BaseAbstractAttributeInterface,Object>>();
		EntityCache cache = EntityCacheFactory.getInstance();
        EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
        participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
        AttributeInterface lastName = GenericQueryGeneratorMock.findAttribute(participantEntity, "lastName");

        EntityInterface pmiEntity = GenericQueryGeneratorMock.createEntity("ParticipantMedicalIdentifier");
        pmiEntity = GenericQueryGeneratorMock.getEntity(cache, pmiEntity);
        AttributeInterface mrnNo = GenericQueryGeneratorMock.findAttribute(pmiEntity, "medicalRecordNumber");
        AttributeInterface id = GenericQueryGeneratorMock.findAttribute(pmiEntity, "id");

        AssociationInterface association = GenericQueryGeneratorMock.getAssociation(participantEntity, pmiEntity);
        innerMap.put(id, "1");
        innerMap.put(mrnNo, "mrn1");
        innerMapList.add(innerMap);

        innerMap =new HashMap<BaseAbstractAttributeInterface, Object>();
        innerMap.put(id, "2");
        innerMap.put(mrnNo, "mrn2");
        innerMapList.add(innerMap);

        denormalizationMap.put(lastName, "abc");
        denormalizationMap.put(association, innerMapList);

        QueryExportDataHandler dataHandler = new QueryExportDataHandler(participantEntity);
        dataHandler.updateRowDataList(denormalizationMap, 0);
        dataHandler.getDataList(0);
	}
}
