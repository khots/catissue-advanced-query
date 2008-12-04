package edu.wustl.query.domain;

import edu.wustl.common.querysuite.queryobject.IQuery;


public class WorkflowItem
{
	private Long id;
	private int position;
	private IQuery query;

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
	
	public IQuery getQuery()
	{
		return query;
	}

	public void setQuery(IQuery query)
	{
		this.query = query;
	}
}
