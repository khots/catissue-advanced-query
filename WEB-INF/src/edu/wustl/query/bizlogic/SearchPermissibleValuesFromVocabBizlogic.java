
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
import edu.wustl.common.vocab.IConcept;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.IVocabularyManager;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.Vocabulary;
import edu.wustl.common.vocab.impl.VocabularyManager;
import edu.wustl.common.vocab.utility.VocabUtil;

/**
 * @author amit_doshi
 *  Class to hold the bizlogic of Vocabulary Interface
 */
public class SearchPermissibleValuesFromVocabBizlogic extends DefaultBizLogic
{

	private LexBIGPermissibleValueManager pvManager = (LexBIGPermissibleValueManager) PermissibleValueManagerFactory.getPermissibleValueManager();
	private IVocabularyManager vocabularyManager = VocabularyManager.getInstance();

	public List<IVocabulary> getVocabulries()
	{
		return vocabularyManager.getVocabularies();

	}

	public List<IConcept> getPermissibleValueList(AttributeInterface attribute, EntityInterface entity)
	{
		List<IConcept> permissibleConcepts = null;

		try
		{
			List<PermissibleValueInterface> permissibleValues = pvManager.getPermissibleValueList(attribute, entity);
			IVocabulary sourceVocabulary = new Vocabulary(VocabUtil.getVocabProperties().getProperty("source.vocab.name"), VocabUtil
					.getVocabProperties().getProperty("source.vocab.version"));
			permissibleConcepts = vocabularyManager.getConceptDetails(getConceptCodeList(permissibleValues), sourceVocabulary);
		}
		catch (VocabularyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return permissibleConcepts;
	}

	public Map<String, List<IConcept>> getMappedConcepts(AttributeInterface attribute, String targetVocabName, String targetVocabVer,
			EntityInterface entity)
	{
		IVocabulary sourceVocabulary = new Vocabulary(VocabUtil.getVocabProperties().getProperty("source.vocab.name"), VocabUtil.getVocabProperties()
				.getProperty("source.vocab.version"));
		IVocabulary targetVocabulary = new Vocabulary(targetVocabName, targetVocabVer);
		List<IConcept> concepts;
		Map<String, List<IConcept>> mappedConcepts = null;
		try
		{
			List<PermissibleValueInterface> permissibleValues = pvManager.getPermissibleValueList(attribute, entity);
			concepts = vocabularyManager.getConceptDetails(getConceptCodeList(permissibleValues), sourceVocabulary);
			mappedConcepts = vocabularyManager.getMappedConcepts(concepts, targetVocabulary);
		}
		catch (VocabularyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mappedConcepts;
	}

	private List<String> getConceptCodeList(List<PermissibleValueInterface> pvList)
	{
		List<String> conceptCodes = null;
		if (pvList != null)
		{
			conceptCodes = new ArrayList<String>();
			for (PermissibleValueInterface pv : pvList)
			{
				conceptCodes.add(pv.getValueAsObject().toString());
			}
		}

		return conceptCodes;
	}
	
	public List<IConcept> searchConcept(String term,String vocabName,String vocabVersion)
	{
		IVocabulary vocabulary = new Vocabulary(vocabName,vocabVersion);
		return vocabularyManager.searchConcept(term, vocabulary);
		
	}
}
