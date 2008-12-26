
package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.domain.StringValue;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.IVocabularyManager;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.VocabularyManager;
import edu.wustl.query.util.global.Constants;

public class LexBIGPermissibleValueManager implements IPermissibleValueManager
{
	public List<PermissibleValueInterface> getPermissibleValueList(final AttributeInterface attribute, final EntityInterface entity) throws PVManagerException 
	{

		List<PermissibleValueInterface> permissibleValueList = new ArrayList<PermissibleValueInterface>();
		if (isEnumerated(attribute, entity))
		{
			List<String> filter = getPVFilterValueForAttribute(attribute, entity);
			//call taras method to get concept codes from MED lookup table
			MedLookUpManager medManager = MedLookUpManager.instance();
			List<String> medPermissibleValueList = medManager.getPermissibleValues(filter);
			permissibleValueList = new ArrayList<PermissibleValueInterface>();
			for(String conceptCode:medPermissibleValueList)
			{
				StringValue pv= new StringValue();
				pv.setValue(conceptCode);				
				permissibleValueList.add(pv);
			}
			
		}
		return permissibleValueList;

	}

	/*public List<PermissibleValueInterface> getPermissibleValueList(final AttributeInterface attribute, final EntityInterface entity) 
	{

		List<String> medPermissibleValueList = new ArrayList<String>();
		List<IConcept> permissiblevalueList = null;
		
		if (isEnumerated(attribute, entity))
		{
			String filter = getPVFilterValueForAttribute(attribute, entity);
			//call taras method to get concept codes from MED lookup table
			MedLookUpManager medManager = MedLookUpManager.instance();
			medPermissibleValueList = medManager.getPermissibleValues(filter);
			if (medPermissibleValueList != null)
			{

				IVocabularyManager vocabMngr = VocabularyManager.getInstance();

				Properties vocabProps = VocabUtil.getVocabProperties();
				//list of coded entries !!!
				try
				{
					permissiblevalueList = vocabMngr.getConceptDetails(medPermissibleValueList, new Vocabulary(vocabProps
							.getProperty("source.vocab.name"), vocabProps.getProperty("source.vocab.version")));
				}
				catch (VocabularyException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return convertToPermissiblevalue(permissiblevalueList);

	}*/

	/**
	 * method to decide whether to show a listbox for the permissible values
	 * @throws PVManagerException 
	 * @throws VocabularyException 
	 */
	public boolean showListBoxForPV(AttributeInterface attribute, EntityInterface entity) throws PVManagerException
	{

		boolean showListBox = false;
		IVocabularyManager vocabMngr= VocabularyManager.getInstance();
		
		if (isEnumerated(attribute, entity))
		{
			List<IVocabulary> vocabularies;
			try
			{
				vocabularies = vocabMngr.getConfiguredVocabularies();
			}
			catch (VocabularyException e)
			{
				throw new PVManagerException("Error occured while checking the available vocabularies.",e);
			}
			int noOfCodingSchemes = vocabularies.size();
			List<String> pvFilter = getPVFilterValueForAttribute(attribute, entity);
			MedLookUpManager medManager = MedLookUpManager.instance();
			List<String> toReturn = medManager.getPermissibleValues(pvFilter);
			if (toReturn != null && toReturn.size() < 10 && noOfCodingSchemes == 1)
			{
				showListBox = true;
			}
		}
		
		return showListBox;

	}

	/**
	 * check whether attribute is enumerated.
	 * 
	 */
	public boolean isEnumerated(AttributeInterface attribute,EntityInterface entity)
	{
		Collection<TaggedValueInterface> tagList = entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		boolean isEnumerated = false;
		//for now considering attribute as NAME , can be made configurable
		if (attribute.getName().equals(Constants.NAME))
		{
			while (tagIterator.hasNext())
			{
				TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();

				if (temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEFILTER))
				{

					isEnumerated = true;
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
	 *//*
	private List<PermissibleValueInterface> convertToPermissiblevalue(List<IConcept> inputList)
	{
		ListIterator<IConcept> iterator = inputList.listIterator();
		List<PermissibleValueInterface> pvList = new ArrayList<PermissibleValueInterface>();
		while (iterator.hasNext())
		{
			IConcept concept = iterator.next();
			ConceptValue conceptV = new ConceptValue();
			//assign the values from coded entry to concept values
			conceptV.setConceptCode(concept.getCode());
			conceptV.setConceptDescription(concept.getDescription());

			pvList.add((PermissibleValueInterface) conceptV);
		}
		return pvList;
	}
*/
	/**
	 * Get the permissible value filter for an attribute
	 * @param attribute
	 * @return
	 */
	//TODO make method more generic
	public List<String> getPVFilterValueForAttribute(final AttributeInterface attribute, final EntityInterface entity)
	{
		List<String> pvFilterValueList = null;
		Collection<TaggedValueInterface> tagList = entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		while (tagIterator.hasNext())
		{
			TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();
			if (temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEFILTER))
			{
				pvFilterValueList = new ArrayList<String>();
				String pvFilterValue = temp.getValue();
				StringTokenizer tokenizer = new StringTokenizer(pvFilterValue,",",false);
				while(tokenizer.hasMoreTokens())
				{
					pvFilterValueList.add(tokenizer.nextToken());
				}
				break;
			}
		}
		return pvFilterValueList;
	}
	
	/**
	 * 
	 */
	public boolean showIcon(AttributeInterface attribute, EntityInterface entity) throws PVManagerException
	{
		boolean showIcon = true;
		try
		{
			IVocabularyManager vocabMngr = VocabularyManager.getInstance();
			if (isEnumerated(attribute, entity))
			{
				List<IVocabulary> vocabularies = vocabMngr.getConfiguredVocabularies();
				int noOfCodingSchemes = vocabularies.size();
				List<String> pvFilter = getPVFilterValueForAttribute(attribute, entity);
				MedLookUpManager medManager = MedLookUpManager.instance();
				List<String> toReturn = medManager.getPermissibleValues(pvFilter);
				if (toReturn != null && toReturn.size() < 5 && noOfCodingSchemes == 1)
				{
					showIcon = false;
				}
			}
		}
		catch (VocabularyException e)
		{
			Logger.out.error(e.getMessage(),e);
		}
		return showIcon;

	}
}
