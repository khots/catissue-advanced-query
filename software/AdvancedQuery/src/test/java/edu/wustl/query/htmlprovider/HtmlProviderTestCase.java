/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

package edu.wustl.query.htmlprovider;

import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.query.generator.GenericQueryGeneratorMock;
import edu.wustl.query.util.querysuite.EntityCacheFactory;
import junit.framework.TestCase;

public class HtmlProviderTestCase extends TestCase
{
	/**
	 * Test the HTML generated.
	 */
	public void testGenerateHtml()
	{
		try
		{
			HtmlProvider htmlProvider = new HtmlProvider(null);
			EntityCache cache = EntityCacheFactory.getInstance();
            EntityInterface participantEntity = GenericQueryGeneratorMock.createEntity("Participant");
            participantEntity = GenericQueryGeneratorMock.getEntity(cache, participantEntity);
			htmlProvider.generateHTML(participantEntity,null);
			assertTrue("Html generated successfully for Demographics.",true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}

	}
}
