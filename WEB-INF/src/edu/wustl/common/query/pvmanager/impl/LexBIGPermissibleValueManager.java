package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.concepts.CodedEntry;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.vocab.VocabularyManager;
import edu.wustl.common.vocab.med.MedLookUpManager;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.global.Variables;

public class LexBIGPermissibleValueManager implements IPermissibleValueManager
{
	
	/**
	 * get permissible value list for a given attribute
	 * @param attribute
	 */
	public List<PermissibleValueInterface> getPermissibleValueList(final AttributeInterface attribute) {
		return null;
	}

	public List<PermissibleValueInterface> getPermissibleValueList(final AttributeInterface attribute, final EntityInterface entity) {

		List<String> medPermissibleValueList =new ArrayList<String>();
		List<CodedEntry> permissiblevalueList = new ArrayList<CodedEntry>();
		String vocabName="";
		String vocabVersion="";
		if(isEnumerated(attribute,entity)) {
			String filter = getPVFilterValueForAttribute(attribute,entity);
			//call taras method to get concept codes from MED lookup table
			MedLookUpManager medManager = MedLookUpManager.instance();
			medPermissibleValueList = medManager.getPermissibleValues(filter);
			if(medPermissibleValueList != null) {

				VocabularyManager vocabMngr = VocabularyManager.getVocabularyManager();
				
				vocabName=Variables.properties.getProperty("sourceVocabularyName");
				vocabVersion=Variables.properties.getProperty("sourceVocabularyVersion");

				//list of coded entries !!!
				permissiblevalueList   = vocabMngr.getConceptCode(vocabName,vocabVersion ,medPermissibleValueList);

			}
		}
		return convertToPermissiblevalue(permissiblevalueList);

	}

	/**
	 * method to decide whether to show a listbox for the permissible values
	 */
	public boolean showListBoxForPV(final AttributeInterface attribute, final EntityInterface entity)
	{
		
		boolean showListBox = false;
		VocabularyManager vocabMngr = VocabularyManager.getVocabularyManager();
		
		List<CodingSchemeSummary> codingSchemeList  = vocabMngr.getAllCodingSchemes();
		int noOfCodingSchemes = codingSchemeList.size();

		String pvFilter = getPVFilterValueForAttribute(attribute,entity);
		MedLookUpManager medManager = MedLookUpManager.instance();
		List<String> toReturn = medManager.getPermissibleValues(pvFilter);

		if(toReturn.size() < 5 && noOfCodingSchemes ==1) {
			showListBox =true;
		}
		return showListBox;

	}

	/**
	 * check whether attribute is enumerated.
	 * 
	 */
	public boolean isEnumerated(AttributeInterface attribute,EntityInterface entity)
	{
		Collection<TaggedValueInterface> tagList =  entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		boolean isEnumerated = false; 
		//for now considering attribute as NAME , can be made configurable
		if(attribute.getName().equals(Constants.NAME)){
			while(tagIterator.hasNext()){
				TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();

				if(temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEFILTER)) {

					isEnumerated= true;
					break;
				}
			}

		}

		return isEnumerated;
	}

	/**
	 * utility list to convert a list of codedEntries to permissiblevalues 
	 * @param inputList
	 * @return
	 */
	private List<PermissibleValueInterface> convertToPermissiblevalue(List<CodedEntry> inputList){
		ListIterator<CodedEntry> iterator = inputList.listIterator();
		List<PermissibleValueInterface> pvList = new ArrayList<PermissibleValueInterface>();
		while(iterator.hasNext()) {
			CodedEntry cEntry = iterator.next();
			ConceptValue conceptV = new ConceptValue();
			//assign the values from coded entry to concept values
			conceptV.setConceptCode(cEntry.getConceptCode());
			conceptV.setConceptDescription(cEntry.getEntityDescription().getContent());

			pvList.add((PermissibleValueInterface)conceptV);
		}
		return pvList;
	}

	

	/**
	 * Get the permissible value filter for an attribute
	 * @param attribute
	 * @return
	 */
	//TODO make method more generic
	public  String getPVFilterValueForAttribute(AttributeInterface attribute,EntityInterface entity) {
		String pvFilterValue = null;
		Collection<TaggedValueInterface> tagList =  entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		while(tagIterator.hasNext()){
			TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();
			if(temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEFILTER)) {
				pvFilterValue = temp.getValue();
				break;
			}
		}
		return pvFilterValue;
	}


	/**
	 * whether to show ListBox for permissible values
	 */
	public boolean showListBoxForPV(AttributeInterface attribute) {
		return false;
	}


	/**
	 * method to getMappings of permissible values
	 */
	public Map<String,List<PermissibleValueInterface>> getMappings(AttributeInterface attribute,String sourceVocabulary,String targetVocabulary,EntityInterface entity )
	{

		Map<String,List<CodedEntry>> mappingsFromVocabMngr = null;
		Map<String,List<PermissibleValueInterface>> mappings = new HashMap<String,List<PermissibleValueInterface>>();
		if(isEnumerated(attribute,entity)){
			String pvFilter = getPVFilterValueForAttribute(attribute,entity);
			MedLookUpManager medManager = MedLookUpManager.instance();
			List<String> conceptCodes = medManager.getPermissibleValues(pvFilter);
			//String[] conceptCodes =(String[]) toReturn.toArray();
			VocabularyManager vocabMngr = VocabularyManager.getVocabularyManager();
			mappingsFromVocabMngr = vocabMngr.getMappedConcepts(sourceVocabulary, targetVocabulary, conceptCodes);
			Set<String> keySet = mappingsFromVocabMngr.keySet();
			Iterator<String> setIt = keySet.iterator();
			while(setIt.hasNext()){
				String currentKey =(String) setIt.next(); 
				List<CodedEntry> tempList = mappingsFromVocabMngr.get(currentKey);
				List<PermissibleValueInterface> listInFormOfPVI = convertToPermissiblevalue(tempList);
				mappings.put(currentKey,listInFormOfPVI);
			}
			
		}

		return mappings;
	}

	/**
	 * 
	 * @return list of names of vocabularies available
	 */
	public List<String> getVocabularies()
	{
		VocabularyManager vocabMngr = VocabularyManager.getVocabularyManager();
		List<CodingSchemeSummary> csSummaryList = vocabMngr.getAllCodingSchemes();
		List<String>  listOfVocabularies = new ArrayList<String>();
		CodingSchemeSummary csSummary = null;
		ListIterator<CodingSchemeSummary> summaryItr = csSummaryList.listIterator();
		while(summaryItr.hasNext()) {
			csSummary = (CodingSchemeSummary)summaryItr.next();
			listOfVocabularies.add(csSummary.getFormalName() + " "+csSummary.getRepresentsVersion());
		}
		return listOfVocabularies;
	}

	public boolean isEnumerated(AttributeInterface attribute) {
		return false;
	}

	
}
