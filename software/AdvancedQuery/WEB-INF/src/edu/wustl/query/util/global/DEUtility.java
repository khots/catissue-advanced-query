package edu.wustl.query.util.global;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import edu.common.dynamicextensions.domaininterface.AbstractMetadataInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;


/**
 * It will contain all the utility method which are related with DynamicExtentions.
 * @author pavan_kalantri
 *
 */
public class DEUtility
{

	/**
	 * @param entity entity for which Primary Key list is required.
	 * @return primary key list
	 */
	public static List<String> getPrimaryKey(EntityInterface entity)
	{
		Collection<TaggedValueInterface> taggedValueCollection = entity.getTaggedValueCollection();
		List<String> primaryKeyList = new ArrayList<String>();
		for (TaggedValueInterface tag : taggedValueCollection)
		{
			if (Constants.PRIMARY_KEY_TAG_NAME.equals(tag.getKey()))
			{
				StringTokenizer stringTokenizer = new StringTokenizer(tag.getValue(), ",");
				while (stringTokenizer.hasMoreTokens())
				{
					primaryKeyList.add(stringTokenizer.nextToken());
				}
			}
		}
		return primaryKeyList;
	}

	/**
	 * This method checks if a particular tag is present on a metadata object.
	 * @param entity AbstractMetadataInterface
	 * @param tag to be checked
	 * @return boolean
	 */
	public static boolean istagPresent(AbstractMetadataInterface entity, String tag)
	{
		boolean isTagPresent = false;
		Collection<TaggedValueInterface> taggedValueCollection = entity.getTaggedValueCollection();
		for (TaggedValueInterface tagValue : taggedValueCollection)
		{
			if (tagValue.getKey().equals(tag))
			{
				isTagPresent = true;
				break;
			}
		}
		return isTagPresent;
	}

	/**
	 * This method gets tag value for a particular tag  for any AbstractMetadataInterface.
	 * @param entity abstract metadata object
	 * @param tag name
	 * @return tag value
	 */
	public static String getTagValue(AbstractMetadataInterface entity, String tag)
	{
		String value = "";
		Collection<TaggedValueInterface> taggedValueCollection = entity.getTaggedValueCollection();
		for (TaggedValueInterface tagValue : taggedValueCollection)
		{
			if (tagValue.getKey().equals(tag))
			{
				value = tagValue.getValue();
				break;
			}
		}
		return value;
	}

	/**
	 * This method returns the parent entity's attribute incase the attribute is inherited
	 * else will return the same attribute.
	 * @param attribute of child entity.
	 * @return parent entity attribute.
	 */
	public static AttributeInterface getParentAttribute(AttributeInterface attribute)
	{
		AttributeInterface parentAtrtribute = attribute;
		if (DEUtility.istagPresent(attribute, "Inherited"))
		{
			for (AttributeInterface attributeInterface : attribute.getEntity().getAllAttributes())
			{
				if (attributeInterface.getName().equals(attribute.getName()))
				{
					parentAtrtribute = attributeInterface;
					break;
				}
			}
		}
		return parentAtrtribute;
	}

}
