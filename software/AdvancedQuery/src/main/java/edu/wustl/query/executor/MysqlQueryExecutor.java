/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

/**
 * <p>Title: Query Executor Class for MySQL database</p>
 * <p>Description:  MysqlQueryExecutor class is a class which contains code to execute the sql query to get the results from MYSQL database. </p>
 * Copyright:    Copyright (c) year
 * Company: Washington University, School of Medicine, St. Louis.
 * @author prafull_kadam
 * @version 1.00
 * Created on June 29, 2007
 */

package edu.wustl.query.executor;

import java.sql.SQLException;
import java.util.List;

import edu.wustl.common.util.PagenatedResultData;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.security.exception.SMException;

/**
 * @author prafull_kadam
 * Query Executor class implementation for MySQL database.
 * The Logic implemented for executing query is:
 * - Execute Query
 * - Set Resultset absolute to startIndex
 * - Iterate on Result for given no. of records
 * - Call resultset.last() & get the cursor position for totalNoOfRecords
 */
public class MysqlQueryExecutor extends AbstractQueryExecutor
{

	/**
	 * Default constructor.
	 */
	public MysqlQueryExecutor()
	{
	}

	/**
	 * This method will
	 * - create statement object by executing SQL query, create scrollable resultset
	 * - moves the cursor position to the startIndex, depending upon its value. if value of startIndex is -1, it will return all the query results.
	 * - after getting required number of records, move cursor to last record & get the cursor position index.
	 * - return the Results.
	 * @return The reference to PagenatedResultData object, which will have pagenated data list & total no. of records that query can return.
	 * @throws DAOException
	 * @throws SMException
	 * @see edu.wustl.common.dao.queryExecutor.AbstractQueryExecutor#createStatemtentAndExecuteQuery()
	 */
	protected PagenatedResultData createStatemtentAndExecuteQuery(JDBCDAO jdbcDAO) throws SQLException, SMException, DAOException
	{

		resultSet = jdbcDAO.getQueryResultSet(query);

		if (getSublistOfResult && startIndex > 0)
		{
			resultSet.absolute(startIndex);
		}
		List list = getListFromResultSet(jdbcDAO); // get the result set.

		// find the total number of records.
		int totalRecords;
		if (getSublistOfResult)
		{
			resultSet.last();
			totalRecords = resultSet.getRow();
		}
		else
		{
			totalRecords = list.size(); // these are all records returned from query.
		}
		return new PagenatedResultData(list, totalRecords);
	}


}
