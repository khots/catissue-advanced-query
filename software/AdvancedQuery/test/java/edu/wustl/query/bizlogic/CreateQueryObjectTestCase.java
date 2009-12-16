package edu.wustl.query.bizlogic;

import java.util.Collection;
import java.util.HashSet;

import junit.framework.TestCase;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.common.beans.MatchedClass;
import edu.wustl.cab2b.common.beans.MatchedClassEntry;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;


public class CreateQueryObjectTestCase extends TestCase
{
	static
	{
		CommonServiceLocator.getInstance();

	}
	public CreateQueryObjectTestCase()
	{
		super();
	}

	/**
	 * Test rule details map.
	 */
	public void testRuleDetailsMap()
	{
		try
		{
			CreateQueryObjectBizLogic queryBizlogic = new CreateQueryObjectBizLogic();
			EntityCache cache = EntityCacheFactory.getInstance();
			EntityInterface entity = GenericQueryGeneratorMock.createEntity("Participant");
			Long attributeId = null;
			Collection<EntityInterface> entityCollection = new HashSet<EntityInterface>();
			entityCollection.add(entity);
			MatchedClass matchedClass = cache.getEntityOnEntityParameters(entityCollection);
			MatchedClass resultantMatchedClass = new MatchedClass();
			for (MatchedClassEntry matchedClassEntry : matchedClass.getMatchedClassEntries())
			{
				resultantMatchedClass.addMatchedClassEntry(matchedClassEntry);
			}
			matchedClass = cache.getCategories(entityCollection);
			for (MatchedClassEntry matchedClassEntry : matchedClass.getMatchedClassEntries())
			{
				resultantMatchedClass.addMatchedClassEntry(matchedClassEntry);
			}
			resultantMatchedClass.setEntityCollection(resultantMatchedClass.getSortedEntityCollection());
			for(EntityInterface tEntity : resultantMatchedClass.getEntityCollection())
			{
				if(tEntity.getName().equals("edu.wustl.clinportal.domain.Participant"))
				{
					entity = tEntity;
					break;
				}
			}
			Collection<AttributeInterface> attrCollection = entity.getAllAttributes();

			for(AttributeInterface attribute : attrCollection)
			{
				if(attribute.getName().equals("id"))
				{
					attributeId = attribute.getId();
				}
			}
			String strToCreateQueryObject = "@#condition#@id"+attributeId+"!*=*!Equals!*=*!1;";
			queryBizlogic.getRuleDetailsMap(strToCreateQueryObject, attrCollection);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
}
