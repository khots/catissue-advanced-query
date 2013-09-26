/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

package edu.wustl.query.bizlogic;

import java.util.Collection;

import junit.framework.TestCase;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.querysuite.queryobject.IQuery;
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
			entity = GenericQueryGeneratorMock.getEntity(cache, entity);
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

	/**
	 * Test setInputDataToQuery method.
	 */
	public void testSetInputDataToQuery()
	{
		try
		{
			CreateQueryObjectBizLogic queryBizlogic = new CreateQueryObjectBizLogic();
			IQuery query = GenericQueryGeneratorMock.creatParticipantQuery();
			String queryInputString = "";
			String errorMessage = queryBizlogic.setInputDataToQuery(queryInputString, query.getConstraints(), null, query);
			assertEquals("",errorMessage);
		}
		catch (Exception e)
		{
			fail("Unexpected Exception while setting input data to query!!!");
		}
	}
}
