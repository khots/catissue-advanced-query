package edu.wustl.common.vocab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.query.factory.PermissibleValueManagerFactory;
import edu.wustl.common.query.pvmanager.impl.ConceptValue;
import edu.wustl.common.query.pvmanager.impl.LexBIGPermissibleValueManager;
import edu.wustl.common.util.logger.Logger;


public class TestVocabManager
{	
	public void displayPermissibleValues(String entityName) throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		EntityInterface entity= EntityManager.getInstance().getEntityByName(entityName);
		AttributeInterface attribute = entity.getAttributeByName("name");
		Logger.out.info("Got the attribute name of race" + attribute.getName());
		Logger.out.info("Tags of the entity=====>" ); 
		Collection<TaggedValueInterface> taggCollection=entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> iter = taggCollection.iterator();
		while(iter.hasNext())
		{
			TaggedValueInterface tagValue = (TaggedValueInterface)iter.next();
			Logger.out.info(tagValue.getKey() + "-->" + tagValue.getValue());
		}
		List<PermissibleValueInterface> pvList = new PermissibleValueManagerFactory().getPermissibleValueManager().getPermissibleValueList(attribute,entity);
		Logger.out.info("Permissible values of " + entity.getName() + " are:");
		Logger.out.info("==========================================================================");
		Logger.out.info("Concept Code\t\t\tConcept Name");
		Logger.out.info("\n");
		
		for(PermissibleValueInterface perValue: pvList)
		{
			Logger.out.info(perValue.getValueAsObject().toString());
		}
		
	}
	
	public void displayMapping(String entityName) throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		EntityInterface entity= EntityManager.getInstance().getEntityByName(entityName);
		AttributeInterface attribute = entity.getAttributeByName("name");
		
		LexBIGPermissibleValueManager ciderPVManager = new LexBIGPermissibleValueManager();
		Map<String,List<PermissibleValueInterface>> mappedConcepts = ciderPVManager.getMappings(attribute,"med","1.0","snomed","1.0", entity);
		Set<String> keySet = mappedConcepts.keySet();
		Iterator<String> iterator = keySet.iterator();
		String conceptCodeName;
		List<PermissibleValueInterface> listOfMappingsForThisCode;
		List<String> mappedConceptDetails;
		while(iterator.hasNext()) 
		{
			 conceptCodeName = iterator.next();
			 listOfMappingsForThisCode = mappedConcepts.get(conceptCodeName);
			 mappedConceptDetails = convertPVIObjToString(listOfMappingsForThisCode);
			 Logger.out.info(" *** "+conceptCodeName + "\t\t\t"+mappedConceptDetails);
		}
		
	}
	
	/**
	 * utility list to convert a list of PermissibleValueInterface to String which is the name and description 
	 * of the code 
	 * @param inputList
	 * @return
	 */
	public List<String> convertPVIObjToString(List<PermissibleValueInterface> inputList){
		ListIterator<PermissibleValueInterface> iterator = inputList.listIterator();
		List<String> pvList = new ArrayList<String>();
		ConceptValue cv;
		while(iterator.hasNext()) {
			cv =(ConceptValue) iterator.next();
			pvList.add(cv.getValueAsObject().toString());
		}
		return pvList;
	}
	
	/**
	 * method to search the given term in the given vocabulary.
	 * @param term       : term to be searched
	 * @param vocabName  : vocabulary name
	 * @param vocabVersion :vocabulary version
	 */
	public void search(String term , String vocabName, String vocabVersion)
	{
		VocabularyManager vm = VocabularyManager.getVocabularyManager();
		List<String> matchingTerms = vm.search(term,vocabName, vocabVersion);
		if(matchingTerms != null )
		{
			ListIterator<String> itr = matchingTerms.listIterator();
			Logger.out.info("Matching terms are :: ");
			while(itr.hasNext())
			{
				Logger.out.info(itr.next());
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			//temporary
			System.setProperty("LG_CONFIG_FILE","E:\\LexBIG\\2.2.0\\resources\\config\\config.props");
			TestVocabManager testVocabManager = new TestVocabManager();
			testVocabManager.displayPermissibleValues(args[0]);
			testVocabManager.displayMapping(args[0]);
			//search method invoke.
		}
		catch (DynamicExtensionsSystemException e)
		{
			Logger.out.debug(e.getStackTrace());
		}
		catch (DynamicExtensionsApplicationException e)
		{
			Logger.out.debug(e.getStackTrace());
		}
		
		
	}
	
	


}
