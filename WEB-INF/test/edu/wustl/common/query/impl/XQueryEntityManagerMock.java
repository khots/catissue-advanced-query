
package edu.wustl.common.query.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.common.dynamicextensions.domain.DomainObjectFactory;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.common.dynamicextensions.domaininterface.EntityGroupInterface;
import edu.common.dynamicextensions.domaininterface.EntityInterface;
import edu.common.dynamicextensions.entitymanager.EntityManager;
import edu.common.dynamicextensions.entitymanager.EntityManagerInterface;
import edu.common.dynamicextensions.exception.DynamicExtensionsApplicationException;
import edu.common.dynamicextensions.exception.DynamicExtensionsSystemException;

/**
 * 
 * @author juberahamad_patel
 *
 */
public class XQueryEntityManagerMock extends EntityManager
{

	DomainObjectFactory factory = new DomainObjectFactory()
	{

		@Override
		public EntityInterface createEntity()
		{
			EntityInterface entity = super.createEntity();
			entity.setEntityGroup(entityGroup);
			entityGroup.addEntity(entity);
			return entity;
		}

		@Override
		public AttributeInterface createStringAttribute()
		{
			return (setAttrId(super.createStringAttribute()));
		}

		@Override
		public AttributeInterface createDoubleAttribute()
		{
			return (setAttrId(super.createDoubleAttribute()));
		}

		@Override
		public AttributeInterface createDateAttribute()
		{
			return (setAttrId(super.createDateAttribute()));
		}

		@Override
		public AttributeInterface createLongAttribute()
		{
			return (setAttrId(super.createLongAttribute()));
		}

		private AttributeInterface setAttrId(AttributeInterface attr)
		{
			attr.setId(attrId++);
			return attr;
		}
	};

	public XQueryEntityManagerMock()
	{
		entityGroup = factory.createEntityGroup();
	}

	public final EntityGroupInterface entityGroup;

	private long attrId = 1;

	public List<EntityInterface> entityList = new ArrayList<EntityInterface>();

	public static final String PERSON = "Person";
	public static final String DEMOGRAPHICS = "Demographics";
	public static final String RACE = "Race";
	public static final String GENDER = "Gender";
	public static final String LABORATORY_PROCEDURE = "LaboratoryProcedure";

	static long identifier = 0L;

	/**
	 * @see edu.common.dynamicextensions.entitymanager.EntityManager#getAttribute(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AttributeInterface getAttribute(String entityName, String attributeName)
			throws DynamicExtensionsSystemException, DynamicExtensionsApplicationException
	{
		EntityInterface entity = getEntityByName(entityName);

		if (entity != null)
		{
			return getSpecificAttribute(entity.getAttributeCollection(), attributeName);
		}
		return null;
	}

	/**
	 * @see edu.common.dynamicextensions.entitymanager.EntityManager#getEntityByName(java.lang.String)
	 */
	@Override
	public EntityInterface getEntityByName(String name) throws DynamicExtensionsSystemException
	{
		EntityManagerInterface entityManager = EntityManager.getInstance();
		EntityInterface entity = null;
		try
		{
			entity = entityManager.getEntityByName(name);
		}
		catch (DynamicExtensionsApplicationException e)
		{
			e.printStackTrace();
		}

		if (entity != null)
		{
			return entity;
		}

		/*if (name.equalsIgnoreCase(PERSON)) 
		{
		    return createPersonEntity(name);
		}
		else if (name.equalsIgnoreCase(DEMOGRAPHICS)) 
		{
		    return createDemographicsEntity(name);
		}
		else if (name.equalsIgnoreCase(RACE)) 
		{
		    return createRaceEntity(name);
		}
		else if (name.equalsIgnoreCase(GENDER)) 
		{
		    return createGenderEntity(name);
		}*/

		return null;
	}

	private AttributeInterface getSpecificAttribute(Collection<AttributeInterface> collection,
			String aName)
	{
		for (AttributeInterface attribute : collection)
		{
			if (attribute.getName().equalsIgnoreCase(aName))
			{
				return attribute;
			}
		}

		return null;
	}
}