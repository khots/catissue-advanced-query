package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.wustl.common.bizlogic.DefaultBizLogic;
import edu.wustl.common.query.factory.PermissibleValueManagerFactory;
import edu.wustl.common.query.pvmanager.impl.LexBIGPermissibleValueManager;

/**
 * @author amit_doshi
 *  Class to hold the bizlogic of Vocabulary Interface
 */
public class SearchPermissibleValuesFromVocabBizlogic extends DefaultBizLogic {
	
	LexBIGPermissibleValueManager pvManager = (LexBIGPermissibleValueManager)PermissibleValueManagerFactory.getPermissibleValueManager();
	public List<String> getVocabulries()
	{
		List<String> vocabulries= new ArrayList<String>();
		vocabulries=pvManager.getVocabularies();
		return vocabulries; 
		
	}
	public List<PermissibleValueInterface> getPermissibleValueList(AttributeInterface attribute,EntityInterface entity)
	{
		return pvManager.getPermissibleValueList(attribute,entity);
	}
	public Map<String,List<PermissibleValueInterface>> getMappings(AttributeInterface attribute,String sourceVocabulary,String sourceVocabVer,String targetVocabulary, String targetVocabVer, EntityInterface entity )
	{
		return pvManager.getMappings(attribute, sourceVocabulary,sourceVocabVer, targetVocabulary,targetVocabVer,entity);
	}
	
}
