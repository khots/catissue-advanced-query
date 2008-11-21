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
import edu.wustl.common.query.pvmanager.impl.LexBIGPermissibleValueManager;
import edu.wustl.common.query.pvmanager.impl.ConceptValue;


public class TestVocabManager
{	
	public void displayPermissibleValues(String entityName) throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		EntityInterface entity= EntityManager.getInstance().getEntityByName(entityName);
		AttributeInterface attribute = entity.getAttributeByName("name");
		System.out.println("Got the attribute name of race" + attribute.getName());
		System.out.println("Tags of the entity=====>" ); 
		Collection<TaggedValueInterface> taggCollection=entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> iter = taggCollection.iterator();
		while(iter.hasNext())
		{
			TaggedValueInterface tagValue = (TaggedValueInterface)iter.next();
			System.out.println(tagValue.getKey() + "-->" + tagValue.getValue());
		}
		List<PermissibleValueInterface> pvList = new PermissibleValueManagerFactory().getPermissibleValueManager().getPermissibleValueList(attribute,entity);
		System.out.println("Permissible values of " + entity.getName() + " are:");
		System.out.println("==========================================================================");
		System.out.println("Concept Code\t\t\tConcept Name");
		System.out.println("\n");
		
		for(PermissibleValueInterface perValue: pvList)
		{
			System.out.println(perValue.getValueAsObject().toString());
		}
		
	}
	
	public void displayMapping(String entityName) throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		EntityInterface entity= EntityManager.getInstance().getEntityByName(entityName);
		AttributeInterface attribute = entity.getAttributeByName("name");
		
		LexBIGPermissibleValueManager ciderPVManager = new LexBIGPermissibleValueManager();
		Map<String,List<PermissibleValueInterface>> mappedConcepts = ciderPVManager.getMappings(attribute,"med", "snomed", entity);
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
			System.out.println(" *** "+conceptCodeName + "\t\t\t"+mappedConceptDetails);
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
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			TestVocabManager testVocabManager = new TestVocabManager();
			testVocabManager.displayPermissibleValues(args[0]);
			testVocabManager.displayMapping(args[0]);
			
		}
		catch (DynamicExtensionsSystemException e)
		{
			e.printStackTrace();
		}
		catch (DynamicExtensionsApplicationException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	


}
