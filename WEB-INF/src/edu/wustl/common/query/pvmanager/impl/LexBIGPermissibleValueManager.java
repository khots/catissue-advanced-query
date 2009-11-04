
package edu.wustl.common.query.pvmanager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.common.dynamicextensions.domain.StringValue;
import edu.common.dynamicextensions.domaininterface.PermissibleValueInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.wustl.common.query.pvmanager.IPermissibleValueManager;
import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.util.global.Constants;

/**
 * LexBIGPermissibleValueManager class used get the Permissible values for the selected entity.
 * @author amit_doshi
 *
 */
public class LexBIGPermissibleValueManager implements IPermissibleValueManager
{

	/**
	 * This method is used to get the List of permissible values.
	 * @param attribute instance of QueryableAttributeInterface
	 * @return List of PermissibleValueInterface
	 * @throws PVManagerException throws PVManagerException
	 */
	public List<PermissibleValueInterface> getPermissibleValueList(
			final QueryableAttributeInterface attribute) throws PVManagerException
	{

		List<PermissibleValueInterface> permissibleValueList = new ArrayList<PermissibleValueInterface>();
		if (isEnumerated(attribute))
		{
			String pvLookupQuery = getTaggedValueForAttribute(attribute, Constants.PV_LOOKUP_QUERY);
			//fetch pv_view from here
			/*changes done by amit_doshi date 16 SEP 2009 for performance improvement
			 * now query will be fired on TABLE instead of VIEW
			 */
			MedLookUpManager medManager = MedLookUpManager.instance();
			List<String> medPermissibleValueList = medManager.getPermissibleValues(pvLookupQuery);
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
	 * This method is used to get the map of conceptCode V/S VolumeInDb.
	 * @return map of conceptCode V/S VolumeInDb
	 * @throws PVManagerException throws PVManagerException
	 */
	public Map<String, String> getVolumeInDb() throws PVManagerException
	{
		MedLookUpManager medManager = MedLookUpManager.instance();
		Map<String, String> conceptCodeVsVolumeInDb = medManager.getVolumeInDb();

		return conceptCodeVsVolumeInDb;
	}

	/**
	 * This method is used to check that the given QueryableAttributeInterface is Enumerated type or not.
	 * @param attribute instance of QueryableAttributeInterface
	 * @return boolean  of conceptCode V/S VolumeInDb
	 */
	public boolean isEnumerated(QueryableAttributeInterface attribute)
	{
		Collection<TaggedValueInterface> tagList = attribute.getQueryEntity()
				.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		boolean isEnumerated = false;
		//for now considering attribute as NAME , can be made configurable
		if (attribute.getName().equals(Constants.NAME))
		{
			while (tagIterator.hasNext())
			{

				TaggedValueInterface temp = tagIterator.next();
				if (temp.getKey().equals(Constants.PV_LOOKUP_QUERY))
				{
					isEnumerated = true;
					break;
				}
			}

		}

		return isEnumerated;
	}

	/**
	 * Get the permissible value filter for an attribute.
	 * @param attribute -QueryableAttributeInterface
	 * @param tagName - name of the TAG
	 * @return tag value
	 */
	public String getTaggedValueForAttribute(final QueryableAttributeInterface attribute,
			final String tagName)
	{
		String taggedValue = null;
		Collection<TaggedValueInterface> tagList = attribute.getQueryEntity()
				.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagIterator = tagList.iterator();
		while (tagIterator.hasNext())
		{
			TaggedValueInterface temp = tagIterator.next();
			if (temp.getKey().equals(tagName))
			{
				taggedValue = temp.getValue();
				break;
			}
		}
		return taggedValue;
	}

	/**
	 * Get the permissible value filter for an attribute.
	 * @param attribute -QueryableAttributeInterface
	 * @return boolean to show icon or not
	 * @throws PVManagerException throws PVManagerException
	 */
	public boolean showIcon(QueryableAttributeInterface attribute) throws PVManagerException
	{
		boolean showIcon = true;
		try
		{
			if (isEnumerated(attribute))
			{
				Properties configuredVocabs = VocabUtil.getConfigVocabProps();
				Properties VIProperties = VocabUtil.getVocabProperties();
				if (configuredVocabs.size() == Integer.parseInt(VIProperties
						.getProperty("min.vocab.count")))
				{

					String pvLookupQuery = getTaggedValueForAttribute(attribute,
							Constants.PV_LOOKUP_QUERY);
					MedLookUpManager medManager = MedLookUpManager.instance();
					int pvCount = medManager.getPermissibleValuesCount(pvLookupQuery);
					if (pvCount <= Integer.parseInt(VIProperties.getProperty("min.pv.count")))
					{
						showIcon = false;
					}

				}
			}

		}
		catch (VocabularyException e)
		{
			throw new PVManagerException(e.getError().getErrorMessage(), e);
		}
		return showIcon;

	}

	/**
	 * This method is used to check weather need to show default permissible values for selected entity or not.
	 * @param attribute -QueryableAttributeInterface
	 * @return boolean show default permissible values or not for selected entity.
	 */
	public boolean showDefaultPermissibleValues(QueryableAttributeInterface attribute)
	{
		boolean show = true;
		String showDefaultPV = getTaggedValueForAttribute(attribute, Constants.SHOW_DEFAULT_PV);
		if (showDefaultPV != null)
		{
			show = false;
		}
		return show;
	}
}

