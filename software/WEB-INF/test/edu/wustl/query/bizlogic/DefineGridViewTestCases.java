package edu.wustl.query.bizlogic;

import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.query.util.Constants;
import edu.wustl.common.query.util.QueryBuilder;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.IQueryTreeGenerationUtil;
import edu.wustl.query.util.querysuite.QueryDetails;


public class DefineGridViewTestCases extends QueryBaseTestCases
{
	static
	{
		Variables.isExecutingTestCase = true;
	}
	public DefineGridViewTestCases()
	{
		super();
	}
	
	public void testContainmentTreeXml()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			QueryDetails queryDetailsObject = getQueryDetailsObject(query);
			IQueryTreeGenerationUtil.parseIQueryToCreateTree(queryDetailsObject);
			DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
			StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
			xmlString =  defineGridViewBizLogic.createContainmentTree(queryDetailsObject,xmlString);
			
			//This string is appended for the root node of the tree
			xmlString.append("</item></tree>");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail();
		}
	}
	
	public void testSelectedColumnsMetadata()
	{
		try
		{
			IParameterizedQuery query = QueryBuilder.skeletalDemograpihcsQuery();
			QueryDetails queryDetailsObject = getQueryDetailsObject(query);
			IQueryTreeGenerationUtil.parseIQueryToCreateTree(queryDetailsObject);
			DefineGridViewBizLogic defineGridViewBizLogic = new DefineGridViewBizLogic();
			SelectedColumnsMetadata selectedColumnsMetadata = new SelectedColumnsMetadata();
			/* selectedColumnIds created in the format "expression id : attribute id"
			 * 1 - expression id of Person, 2 - expression id of Demographics
			 * Attribute ids:- 18 - personUpi, 455 - dateOfDeath, 454 - dateOfBirth
			 * 460 - socialSecurityNumber
			 */
			Long personUpiId=null;
			Long dateOfDeathId=null;
			Long dateOfBirthId=null;
			Long socialSecurityNumberId=null;
			for(EntityGroupInterface entityGroup : EntityCache.getInstance().getEntityGroups())
			{
				EntityInterface personEntity = entityGroup.getEntityByName(Constants.PERSON);
				personUpiId = personEntity.getAttributeByName("personUpi").getId();
				
				EntityInterface demographicsEntity = entityGroup.getEntityByName(Constants.DEMOGRAPHICS);
				dateOfDeathId = demographicsEntity.getAttributeByName("dateOfDeath").getId();
				dateOfBirthId = demographicsEntity.getAttributeByName("dateOfBirth").getId();
				socialSecurityNumberId = demographicsEntity.getAttributeByName("socialSecurityNumber").getId();
			}
			String[] selectedColumnIds = {"1:"+personUpiId,"2:"+dateOfDeathId,"2:"+dateOfBirthId,"2:"+socialSecurityNumberId};
			defineGridViewBizLogic.getSelectedColumnsMetadata(selectedColumnIds, queryDetailsObject, selectedColumnsMetadata);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail();
		}
	}
}
