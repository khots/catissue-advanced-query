
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
	private IVocabularyManager vocabularyManager = getInstance();

	public List<IVocabulary> getVocabulries() throws VocabularyException
	{
		return vocabularyManager.getVocabularies();

	}

	private IVocabularyManager getInstance() {
		// TODO Auto-generated method stub
		try {
			return VocabularyManager.getInstance();
		} catch (VocabularyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
			EntityInterface entity) throws VocabularyException
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
	public List<IConcept> searchConcept(String term,String vocabName,String vocabVersion) throws VocabularyException
	{
		IVocabulary vocabulary = new Vocabulary(vocabName,vocabVersion);
		return vocabularyManager.searchConcept(term, vocabulary);
		
	}
	public String getNoMappingFoundHTML() {
		return "<tr><td>&nbsp;</td><td class='black_ar_tt'>"+Constants.NO_RESULT+"<td></tr>";
	}
	public String getMappedVocabularyPVChildAsHTML(String vocabName,String vocabversoin, IConcept concept, String checkboxId) {
		return "<tr ><td style='padding-left:30px'>&nbsp;</td><td class='black_ar_tt'> \n" +
				"<input type='checkbox' name='"+vocabName+vocabversoin+"' id='"+checkboxId+"' value='"+concept.getCode()+":"+concept.getDescription()+"' onclick=\"getCheckedBoxId('"+checkboxId+"');\"></td><td class='black_ar_tt'>"+concept.getCode()+":"+concept.getDescription()+"\n" +
				"<td></tr>";
	}
	public String getRootVocabularyNodeHTML(String vocabName,String vocabVer) throws VocabularyException {
		String srcvocabName=VocabUtil.getVocabProperties().getProperty("source.vocab.name");
		String srcvocabVer=VocabUtil.getVocabProperties().getProperty("source.vocab.version");
		
		/*code should uncommented for multiple vocabulary support
		String style="display:none";
		String imgpath= "src=\"images/advancequery/nolines_plus.gif\"/";
		
		if(srcvocabName.equalsIgnoreCase(vocabName) && srcvocabVer.equalsIgnoreCase(vocabVer))
		{
			//to show MED vocabulary tree or data expanded mode 
			style="display:";
			imgpath="src=\"images/advancequery/nolines_minus.gif\"/";
		}*/
		
		String style="display:";
		String imgpath="src=\"images/advancequery/nolines_minus.gif\"/";
		
		return "<table cellpadding ='0' cellspacing ='1'><tr><td>" +
				"<table cellpadding ='0' cellspacing ='1'><tr><td class='grid_header_text'><a id=\"image_"+vocabName+vocabVer+"\"\n" +
				"onClick=\"showHide('inner_div_"+vocabName+vocabVer+"','image_"+vocabName+vocabVer+"');\">\n" +
				"<img "+imgpath+" align='absmiddle'/></a></td><td><input type='checkbox' name='"+vocabName+vocabVer+"' id='root_"+vocabName+vocabVer+"' " +
				"value='"+vocabName+"' onclick=\"setStatusOfAllCheckBox(this.id);\"></td>" +
				"<td class='grid_header_text'>"+vocabName+vocabVer+"\n" +
				"</td></tr></table>"+
				"</td></tr><tr><td><div id='inner_div_"+vocabName+vocabVer+"' style='"+style+"'><table cellpadding ='0' cellspacing ='1'>";
	}
	public String getRootVocabularyHTMLForSearch(String vocabName,String vocabVer) {
	
		/*code should uncommented for multiple vocabulary support
		    String style="display:none";
			String imgpath= "src=\"images/advancequery/nolines_pluse.gif\"/";
		 */
		String style="display:";
		String imgpath= "src=\"images/advancequery/nolines_minus.gif\"/";
		
		return "<table cellpadding ='0' cellspacing ='1'><tr><td>" +
				"<table cellpadding ='0' cellspacing ='1'><tr><td class='grid_header_text'><a id=\"image_"+vocabName+vocabVer+"\"\n" +
				"onClick=\"showHide('inner_div_"+vocabName+vocabVer+"','image_"+vocabName+vocabVer+"');\">\n" +
				"<img "+imgpath+"align='absmiddle'></a></td><td><input type='checkbox' name='"+vocabName+vocabVer+"' id='root_"+vocabName+vocabVer+"' " +
				"value='"+vocabName+"' onclick=\"setStatusOfAllCheckBox(this.id);\"></td>" +
				"<td class='grid_header_text'>"+vocabName.replace("srh_","")+vocabVer+"\n" +
				"</td></tr></table>" +
				"</td></tr><tr><td><div id='inner_div_"+vocabName+vocabVer+"' style='"+style+"'><table cellpadding ='0' cellspacing ='1'>";
	}
	
	public String getEndHTML() {
		return "</table></div></td></tr></table>";
	}
	/**
	 * @return
	 */
	public String getErrorMessageAsHTML() {
		return "<table width='100%' height='100%'><tr><td class='black_ar_tt' style='color:red'>Please enter valid search term<td></tr></table>";
	}
}
