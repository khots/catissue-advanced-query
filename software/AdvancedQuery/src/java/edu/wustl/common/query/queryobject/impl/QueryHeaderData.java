package edu.wustl.common.query.queryobject.impl;

import edu.common.dynamicextensions.domaininterface.EntityInterface;

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
}
