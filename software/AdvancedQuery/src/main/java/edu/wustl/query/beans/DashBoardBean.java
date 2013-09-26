/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

package edu.wustl.query.beans;

import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;

public class DashBoardBean
{
	IParameterizedQuery query;
	String executedOn;
	String rootEntityName;
	String countOfRootRecords;
	String ownerName;

	/**
	 * @return the ownerName
	 */
	public String getOwnerName()
	{
		return ownerName;
	}
	/**
	 * @param ownerName the ownerName to set
	 */
	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}
	/**
	 * @return the query
	 */
	public IParameterizedQuery getQuery()
	{
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(IParameterizedQuery query)
	{
		this.query = query;
	}
	/**
	 * @return the executedOn
	 */
	public String getExecutedOn()
	{
		return executedOn;
	}
	/**
	 * @param executedOn the executedOn to set
	 */
	public void setExecutedOn(String executedOn)
	{
		this.executedOn = executedOn;
	}
	/**
	 * @return the rootEntityName
	 */
	public String getRootEntityName()
	{
		return rootEntityName;
	}
	/**
	 * @param rootEntityName the rootEntityName to set
	 */
	public void setRootEntityName(String rootEntityName)
	{
		this.rootEntityName = rootEntityName;
	}
	/**
	 * @return the countOfRootRecords
	 */
	public String getCountOfRootRecords()
	{
		return countOfRootRecords;
	}
	/**
	 * @param countOfRootRecords the countOfRootRecords to set
	 */
	public void setCountOfRootRecords(String countOfRootRecords)
	{
		this.countOfRootRecords = countOfRootRecords;
	}

}
