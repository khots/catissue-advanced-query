package edu.wustl.query.domain;

import edu.wustl.common.querysuite.queryobject.IAbstractQuery;


public class WorkflowItem
{
	private Long id;
	private int position;
	private IAbstractQuery query;

	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public IAbstractQuery getQuery()
	{
		return query;
	}

	public void setQuery(IAbstractQuery query)
	{
		this.query = query;
	}
}
