package edu.wustl.query.bizlogic;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.factory.QueryObjectFactory;
import edu.wustl.common.querysuite.queryobject.IOutputEntity;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import junit.framework.TestCase;

public class DefineGridViewBizLogicTestCase extends TestCase
{
	public void testGetClassName()
	{
		DefineGridViewBizLogic bizLogic = new DefineGridViewBizLogic();
		EntityCache cache = EntityCacheFactory.getInstance();
        EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
        participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
        IOutputEntity outputEntity = QueryObjectFactory.createOutputEntity(participantEntity);
        outputEntity.getSelectedAttributes().addAll(participantEntity.getEntityAttributesForQuery());
        OutputTreeDataNode outputTreeDataNode = new OutputTreeDataNode(outputEntity, 1, 1);
        String className = bizLogic.getClassName(outputTreeDataNode);
        assertEquals("Expected Class Name","edu.wustl.clinportal.domain.Participant",className);
	}

	public void testCreateSQLForSelectedColumn()
	{
		String columnNames = "Column2,Column4,Column5,Column6";
		String sql = "select distinct Column0,Column1,Column2,Column3,Column4,Column5,Column6,Column7,Column8,Column9,Column10,Column11 from TEMP_OUTPUTTREE1_86034 where Column4 is NOT NULL";
		DefineGridViewBizLogic gridViewBizLogic = new DefineGridViewBizLogic();
		gridViewBizLogic.createSQLForSelectedColumn(columnNames, sql);
	}
}
