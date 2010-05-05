package edu.wustl.common.query.queryobject.impl;

import edu.common.dynamicextensions.domaininterface.EntityInterface;

/**
 * @author pooja_tavase
 *
 */
public class QueryHeaderData
{
	private EntityInterface entity;
	private String recordNo;

	public QueryHeaderData(EntityInterface entity,String recordNo)
	{
		this.entity = entity;
		this.recordNo = recordNo;
	}

	/**
	 * @return the entity
	 */
	public EntityInterface getEntity()
	{
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(EntityInterface entity)
	{
		this.entity = entity;
	}

	/**
	 * @return the recordNo
	 */
	public String getRecordNo()
	{
		return recordNo;
	}

	/**
	 * @param recordNo the recordNo to set
	 */
	public void setRecordNo(String recordNo)
	{
		this.recordNo = recordNo;
	}

	/**
	 * @return the recordNo
	 */
	public String getParentRecordNo()
	{
		return recordNo;
	}

	@Override
	public int hashCode()
	{
		return (int) (entity.getId() * 3L);
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean isEqual = false;
		if (obj instanceof QueryHeaderData)
		{
			QueryHeaderData entityObject = (QueryHeaderData) obj;
			if (entity.equals(entityObject.getEntity()) && recordNo != null
					&& recordNo.equals(entityObject.getParentRecordNo()))
			{
				isEqual = true;
			}
		}
		return isEqual;
	}
}
