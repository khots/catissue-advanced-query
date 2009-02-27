
package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import edu.common.dynamicextensions.domain.StringValue;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.vocab.IVocabulary;
import edu.wustl.common.vocab.IVocabularyManager;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.impl.VocabularyManager;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.util.global.Constants;

public class LexBIGPermissibleValueManager implements IPermissibleValueManager
{

	public List<PermissibleValueInterface> getPermissibleValueList(
			final AttributeInterface attribute, final EntityInterface entity)
			throws PVManagerException
	{

		List<PermissibleValueInterface> permissibleValueList = new ArrayList<PermissibleValueInterface>();
		if (isEnumerated(attribute, entity))
		{
			String filter = getTaggedValueForAttribute(attribute, entity,Constants.PERMISSIBLEVALUEFILTER);
			//fetch pv_view from here 
			String view = getTaggedValueForAttribute(attribute, entity,Constants.PERMISSIBLEVALUEVIEW);
			MedLookUpManager medManager = MedLookUpManager.instance();
			List<String> medPermissibleValueList = medManager.getPermissibleValues(filter, view);//(filter,view)
			permissibleValueList = new ArrayList<PermissibleValueInterface>();
			if (medPermissibleValueList != null)
			{
				for (String conceptCode : medPermissibleValueList)
				{
					StringValue pv = new StringValue();
					pv.setValue(conceptCode);
					permissibleValueList.add(pv);
				}
			}

		}
		return permissibleValueList;

	}

	
	
	/**
	 * check whether attribute is enumerated.
	 * 
	 */
	public boolean isEnumerated(AttributeInterface attribute, EntityInterface entity)
	{
		Collection<TaggedValueInterface> tagList = entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		boolean isEnumerated = false;
		//for now considering attribute as NAME , can be made configurable
		if (attribute.getName().equals(Constants.NAME))
		{
			while (tagIterator.hasNext())
			{
				//entity.getParentEntity().getName().eq
				TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();

				if (temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEFILTER)
						|| temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEVIEW))
				{

					isEnumerated = true;
					break;
				}
			}

		}

		return isEnumerated;
	}

	/**
	 * Get the permissible value filter for an attribute
	 * @param attribute
	 * @return
	 */

	public String getTaggedValueForAttribute(final AttributeInterface attribute,
			final EntityInterface entity,final String tagName)
	{
		String taggedValue = null; 
		Collection<TaggedValueInterface> tagList = entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		while (tagIterator.hasNext())
		{
			TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();
			if (temp.getKey().toString().equals(tagName))
			{
				taggedValue = temp.getValue();
				break;
			}
		}
		return taggedValue;
	}

	//Commented By: Tara Khoiwal
	//Reason:As the filtering condition SQL is moved to metadata so no need of tokenizing the pv_filter tag.
	//	public List<String> getTaggedValueForAttribute(final AttributeInterface attribute, final EntityInterface entity)//,final String tagName)
	//	{
	//		List<String> taggedValuesList = null;
	//		//String taggedValue =""; 
	//		Collection<TaggedValueInterface> tagList = entity.getTaggedValueCollection();
	//		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
	//		while (tagIterator.hasNext())
	//		{
	//			TaggedValueInterface temp = (TaggedValueInterface) tagIterator.next();
	//			if (temp.getKey().toString().equals(Constants.PERMISSIBLEVALUEFILTER))
	//			{
	//				taggedValuesList = new ArrayList<String>();
	//				String pvFilterValue = temp.getValue();
	//				StringTokenizer tokenizer = new StringTokenizer(pvFilterValue,",",false);
	//				while(tokenizer.hasMoreTokens())
	//				{
	//					taggedValuesList.add(tokenizer.nextToken());
	//				}
	//				break;
	//				//taggedValue = temp.getValue().toString();
	//			}
	//		}
	//		return taggedValuesList;
	//	}
	//	
	
	/**
	 * 
	 */
	public boolean showIcon(AttributeInterface attribute, EntityInterface entity)
			throws PVManagerException
	{
		boolean showIcon = true;
		try
		{
			IVocabularyManager vocabMngr = VocabularyManager.getInstance();
			if (isEnumerated(attribute, entity))
			{
				Properties properties = VocabUtil.getConfigVocabProps();
				
				//List<IVocabulary> vocabularies = vocabMngr.getConfiguredVocabularies();
				int noOfCodingSchemes = properties.size();
				String pvFilter = getTaggedValueForAttribute(attribute, entity,Constants.PERMISSIBLEVALUEFILTER);
				String view = getTaggedValueForAttribute(attribute, entity,Constants.PERMISSIBLEVALUEVIEW);
				MedLookUpManager medManager = MedLookUpManager.instance();
				int pvCount = medManager.getPermissibleValuesCount(pvFilter, view);
				if (pvCount < 10 && noOfCodingSchemes == 1)
				{
					showIcon = false;
				}
			}
		}
		catch (VocabularyException e)
		{
			throw new PVManagerException(e.getError().getErrorMessage(), e);
		}
		return showIcon;

	}
}
