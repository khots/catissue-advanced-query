
package edu.wustl.query.domain;

import edu.wustl.common.querysuite.queryobject.IAbstractQuery;

/**
 * @author vijay_pande
 *
 */
public class WorkflowItem
{

	private Long id;
	private int position;
	private IAbstractQuery query;

	/**
	 * This method returns the workFlowItem id 
	 * @return workFlowItem id.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * This method sets the workFlowItem id
	 * @param id is workFlowItem id.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * returns the position of an item in workFlow
	 * @return position of item in workFlow
	 */
	public int getPosition()
	{
		return position;
	}

	/**
	 * @param position  is position of item in workFlow
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}

	/**
	 * @return the query from workflowItem
	 */
	public IAbstractQuery getQuery()
	{
		return query;
	}

	/**
	 * @param query the Query object in workFlowItem.
	 */
	public void setQuery(IAbstractQuery query)
	{
		this.query = query;
	}
}
