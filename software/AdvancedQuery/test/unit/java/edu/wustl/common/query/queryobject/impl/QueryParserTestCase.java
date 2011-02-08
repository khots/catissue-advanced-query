package edu.wustl.common.query.queryobject.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;

public class QueryParserTestCase extends TestCase
{
	public void testParseQuery()
	{
		QueryParser parser = new QueryParser();
		IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
		EntityCache cache = EntityCacheFactory.getInstance();
        EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
        participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
        IOutputEntity participantOutputEntity = QueryObjectFactory.createOutputEntity(participantEntity);
        participantOutputEntity.getSelectedAttributes().addAll(participantEntity.getEntityAttributesForQuery());
        OutputTreeDataNode outputTreeDataNode = new OutputTreeDataNode(participantOutputEntity, 1, 0);
        List<QueryOutputTreeAttributeMetadata> selectedAttributeMetaDataList = new ArrayList<QueryOutputTreeAttributeMetadata>();
		SelectedColumnsMetadata selectedColumnsMetadata = new SelectedColumnsMetadata();
        selectedColumnsMetadata.setCurrentSelectedObject(outputTreeDataNode);
        selectedColumnsMetadata.setDefinedView(true);
        selectedColumnsMetadata.setSelColNVBeanList(null);
        selectedColumnsMetadata.setSelectedAttributeMetaDataList(selectedAttributeMetaDataList);
        selectedColumnsMetadata.setSelectedOutputAttributeList(null);
		parser.parseQuery(query, selectedAttributeMetaDataList);
	}
}
