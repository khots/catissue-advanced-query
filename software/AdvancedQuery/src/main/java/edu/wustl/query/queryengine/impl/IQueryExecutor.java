/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */


package edu.wustl.query.queryengine.impl;

import edu.wustl.common.querysuite.queryobject.IQuery;

/**
 * @version 1.0
 * @created 03-Oct-2006 11:50:05 AM
 */
public interface IQueryExecutor
{

	/**
	 * TODO
	 * @throws Throwable TODO
	 */
	void finalize() throws Throwable;

	/**
	 * @param query The reference to the query to be executed.
	 */
	void executeQuery(IQuery query);
}