
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
import edu.wustl.query.util.global.Constants;

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
	public String getNoMappingFoundHTML() {
		return "<tr><td>&nbsp;</td><td class='black_ar_tt'>"+Constants.NO_RESULT+"<td></tr>";
	}
	public String getMappedVocabularyPVChildAsHTML(String vocabName,IConcept concept, String checkboxId) {
		return "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td class='black_ar_tt'> \n" +
				"<input type='checkbox' name='"+vocabName+"' id='"+checkboxId+"' value='"+concept.getCode()+":"+concept.getDescription()+"' onclick=\"getCheckedBoxId('"+checkboxId+"');\">"+concept.getCode()+":"+concept.getDescription()+"\n" +
				"</input><td></tr>";
	}
	public String getRootVocabularyNodeHTML(String vocabName,String vocabVer) {
		
		return "<table><tr><td colspan='2' class='grid_header_text'><a id=\"image_"+vocabName+"\" \n" +
				"onClick=\"showHide('inner_div_"+vocabName+"','image_"+vocabName+"');\" > \n" +
						"<img src=\"images/advancequery/nolines_minus.gif\"/ align='absmiddle'> </a><input type='checkbox' name='"+vocabName+"' id='"+vocabName+"' value='"+vocabName+"' onclick=\"setStatusOfAllCheckBox('"+vocabName+"');\">"+vocabName+" "+vocabVer+"\n" +
								"</input></td></tr>" +
								"<tr><td><div id='inner_div_"+vocabName+"'  ><table>";
	}
	
	public String getEndHTML() {
		return "</table></div></td></tr></table>";
	}
}
