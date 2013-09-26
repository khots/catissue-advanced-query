/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.common.query.queryobject.locator;

/**
 * Class which contains x,y coordinates & its getters & setters.
 * Objects of this class is used to specify node position of the query nodes in the DAG.
 * @author prafull_kadam
 */
public class Position
{

	/**
	 * The x & y coordinates.
	 */
	private int xCoordinate, yCoordinate;

	/**
	 * Default constructor.
	 * @param xCoordinate The X coordinate value
	 * @param yCoordinate The Y coordinate value
	 */
	public Position(int xCoordinate, int yCoordinate)
	{
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	/**
	 * To get X coordinate value.
	 * @return The X coordinate value
	 */
	public int getX()
	{
		return xCoordinate;
	}

	/**
	 * To get Y coordinate value.
	 * @return The Y coordinate value
	 */
	public int getY()
	{
		return yCoordinate;
	}
}
