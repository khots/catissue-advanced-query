package edu.wustl.query.querysuite.metadata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.common.dynamicextensions.domain.DomainObjectFactory;
import edu.common.dynamicextensions.domaininterface.AbstractAttributeInterface;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.domaininterface.TaggedValueInterface;
import edu.common.dynamicextensions.entitymanager.EntityGroupManager;
import edu.common.dynamicextensions.entitymanager.EntityGroupManagerInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;

/**
 * This class adds Tag Values for entities that are not searchable and
 * attributes that are not viewable or not searchable in Query.
 * @author rukhsana_sameer
 */
public  class AddTaggedValue
{
	/**
	 * Adds tag values for entities and attributes.
	 * @param args filename
	 */
	public static void main(String[] args)
	{
		Logger.configure("");
		try
		{
			ApplicationProperties.initBundle("ApplicationResources");
			/*
			 * args[0] will contain xml filename
			*/
			if (args.length < 1)
			{
				throw new Exception(ApplicationProperties.getValue("filename.required"));
			}
			String fileName = args[0];
			readTaggedValues(fileName);
		}
		catch (Exception e)
		{
			Logger.out.error(e.getStackTrace());
		}
	}
	/**
	 * This method reads tagged values from xml file.
	 * @param fileName name of xml file
	 * @throws DynamicExtensionsApplicationException if entity group does not exist
	 * @throws DynamicExtensionsSystemException from EntityGroupManager
	 */
	private static void readTaggedValues(String fileName)
		throws DynamicExtensionsApplicationException, DynamicExtensionsSystemException
	{
		SAXReader saxReader = new SAXReader();
		try
		{
			FileInputStream inputStream = new FileInputStream(fileName);
			Document document = saxReader.read(inputStream);
			Element rootElement = document.getRootElement();
			Iterator<Element> rootIterator = rootElement.elementIterator(Constants.ELEMENT_ENTITY_GROUP);
			//Element entityGrpEle = null;
			EntityGroupManagerInterface entityGroupManager = EntityGroupManager.getInstance();
			while(rootIterator.hasNext())
			{
				Element entityGrpEle = (Element) rootIterator.next();
				Element grpNameElement = entityGrpEle.element(Constants.ELEMENT_NAME);
				String entityGroupName = grpNameElement.getText();
				EntityGroupInterface entityGroup =
					entityGroupManager.getEntityGroupByName(entityGroupName);
				if(entityGroup == null)
				{
					throw new DynamicExtensionsApplicationException
						(ApplicationProperties.getValue(
							"entitygroup.doesNotExist", entityGroupName));
				}
				deleteTaggedValues(entityGroup);
				readEntities(entityGrpEle, entityGroup);
				entityGroupManager.persistEntityGroup(entityGroup);
			}
			Logger.out.info("TAGGED VALUES ADDED SUCCESSFULLY!!!");
		}
		catch (FileNotFoundException e)
		{
			Logger.out.error(e.getStackTrace());
		}
		catch (DocumentException e)
		{
			Logger.out.error(e.getStackTrace());
		}
	}

	/**
	 * This method deletes already present NOT_SEARCHABLE/NOT_VIEWABLE tagged values.
	 * @param entityGroup entity group that contains the entities and attributes,
	 * of which the tagged values are to be deleted.
	 */
	private static void deleteTaggedValues(EntityGroupInterface entityGroup)
	{
		Collection<EntityInterface> entityCollection = entityGroup.getEntityCollection();
		for(EntityInterface entity : entityCollection)
		{
			deleteEntityTagValues(entity);
			deleteAttributeTagValues(entity);
		}
	}

	/**
	 * Deletes tagged values of attributes.
	 * @param entity that contains attributes,
	 * of which tagged values are to be deleted.
	 */
	private static void deleteAttributeTagValues(EntityInterface entity)
	{
		Collection<AbstractAttributeInterface> allAbstractAttributes = entity.getAllAbstractAttributes();
		for(AbstractAttributeInterface attribute : allAbstractAttributes)
		{
			Collection<TaggedValueInterface> taggedValueCollection =
				attribute.getTaggedValueCollection();
			Iterator<TaggedValueInterface> iterator = taggedValueCollection.iterator();
			while(iterator.hasNext())
			{
				TaggedValueInterface tagValue = iterator.next();
				String key = tagValue.getKey();
				if(key.equals(Constants.TAGGED_VALUE_NOT_SEARCHABLE) ||
						key.equals(Constants.TAGGED_VALUE_NOT_VIEWABLE))
				{
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Deletes tagged values of entity.
	 * @param entity of which tagged value has to be deleted.
	 */
	private static void deleteEntityTagValues(EntityInterface entity)
	{
		Collection<TaggedValueInterface> entitytagValueColl = entity.getTaggedValueCollection();
		Iterator<TaggedValueInterface> tagValueIter = entitytagValueColl.iterator();
		while(tagValueIter.hasNext())
		{
			TaggedValueInterface tagValue = tagValueIter.next();
			String key = tagValue.getKey();
			if(key.equals(Constants.TAGGED_VALUE_NOT_SEARCHABLE))
			{
				tagValueIter.remove();
			}
		}
	}

	/**
	 * This method reads entities present in the entity group, from the xml.
	 * @param entityGrpEle xml element for entity group
	 * @param entityGroup that contains the entities
	 * @throws DynamicExtensionsApplicationException if entity does not exist
	 */
	private static void readEntities(Element entityGrpEle,
			EntityGroupInterface entityGroup) throws DynamicExtensionsApplicationException
	{
		Element nameElement;
		Iterator elementIterator = entityGrpEle.elementIterator(Constants.ELEMENT_ENTITY);//entity element
		//Element entityElement = null;
		//String entityName = null;
		while (elementIterator.hasNext())
		{
			Element entityElement = (Element) elementIterator.next();
			nameElement = entityElement.element(Constants.ELEMENT_NAME);
			String entityName  = nameElement.getText();
			EntityInterface entity = entityGroup.getEntityByName(entityName);
			if (entity == null)
			{
				throw new DynamicExtensionsApplicationException
				(ApplicationProperties.getValue("entity.doesNotExist",entityName));//String errMsg
			}
			readAttributes(entity, entityElement);
			tagValueForEntity(entity, entityElement);
		}
	}

	/**
	 * This method adds NOT_SEARCHABLE tag for entity if present in xml file.
	 * @param entity for which tag value has to be added.
	 * @param entityElement xml element for entity
	 */
	private static void tagValueForEntity(EntityInterface entity, Element entityElement)
	{
		Element entityTag = entityElement.element(Constants.ELEMENT_TAG_NAME);
		if(entityTag != null)
		{
			String entityTagName = entityTag.getText();
			TaggedValueInterface taggedValue = createTagValue(entityTagName, entityTagName);
			entity.addTaggedValue(taggedValue);
		}
	}

	/**
	 * This method reads attributes of an entity.
	 * @param entity that contains the attributes.
	 * @param entityElement xml element for entity.
	 * @throws DynamicExtensionsApplicationException if attribute does not exist.
	 */
	private static void readAttributes(EntityInterface entity,Element entityElement)
		throws DynamicExtensionsApplicationException

	{
		Iterator<Element> attrItr = entityElement.elementIterator(Constants.ELEMENT_ATTRIBUTE);
		while (attrItr.hasNext())
		{
			Element attrElement =  attrItr.next();
			Element attributeName = attrElement.element(Constants.ELEMENT_NAME);
			Iterator<Element> tagValueItr = attrElement.elementIterator(Constants.ELEMENT_TAG_NAME);
			String attrName = attributeName.getText();
			//TaggedValueInterface taggedValue = null;
			while(tagValueItr.hasNext())
			{
				AttributeInterface attribute = entity.getAttributeByName(attrName);
				if (attribute == null)
				{
					throw new DynamicExtensionsApplicationException
					(ApplicationProperties.getValue("attribute.doesNotExist",attrName));
				}
				Element tagValue = (Element) tagValueItr.next();
				String tagName = tagValue.getText();
				checkValidTag(tagName);
				TaggedValueInterface taggedValue = createTagValue(tagName,tagName);
				attribute.addTaggedValue(taggedValue);
			}
		}
	}

	/**
	 * This method checks if tag value is valid.
	 * @param tagName tag value in xml (NOT_SEARCHABLE/NOT_VIEWABLE)
	 * @throws DynamicExtensionsApplicationException if invalid tag value
	 */
	private static void checkValidTag(String tagName) throws DynamicExtensionsApplicationException
	{
		if(!tagName.equals(Constants.TAGGED_VALUE_NOT_SEARCHABLE)
				 && !tagName.equals(Constants.TAGGED_VALUE_NOT_VIEWABLE))
		{
			throw new DynamicExtensionsApplicationException
			(ApplicationProperties.getValue("tagvalue.invalid",tagName));
		}
	}

	/**
	 * Method creates a Tag Value object.
	 * @param tagName name of tag
	 * @param tagValue tag value.
	 * @return TaggedValueInterface
	 */
	private static TaggedValueInterface createTagValue(String tagName, String tagValue)
	{
		TaggedValueInterface taggedValue = DomainObjectFactory.getInstance()
								.createTaggedValue();
		taggedValue.setKey(tagName);
		taggedValue.setValue(tagValue);
		return taggedValue;
	}
}
