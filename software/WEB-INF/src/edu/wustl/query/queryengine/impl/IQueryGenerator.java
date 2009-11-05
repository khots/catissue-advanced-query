/**
 * 
 */

package edu.wustl.query.queryengine.impl;

import java.util.Map;

import edu.wustl.common.querysuite.querableobjectInterface.QueryableAttributeInterface;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.querysuite.QueryModuleException;

/**
 * To Generate SQL for the given Query Object.
 * 
 * @author prafull_kadam
 * 
 */
public interface IQueryGenerator
{

	/**
	 * Generates SQL for the given Query Object.
	 * 
	 * @param query The Reference to Query Object.
	 * @return the String representing SQL for the given Query object.
	 * @throws QueryModuleException When there are multiple roots present in a
	 *             graph.
	 */
	String generateQuery(IQuery query) throws QueryModuleException;


	Map<QueryableAttributeInterface, String> getAttributeColumnNameMap();

}
