package edu.wustl.query.queryexecutor;

import java.sql.SQLException;
import java.util.List;

import edu.wustl.common.util.PagenatedResultData;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.security.exception.SMException;


public class Db2QueryExecuter extends AbstractQueryExecutor
{

	public Db2QueryExecuter()
	{
		
	}
	

	@Override
	protected PagenatedResultData createStatemtentAndExecuteQuery(
			JDBCDAO jdbcDAO) throws SQLException, SMException, DAOException {
		//query=putPageNumInSQL(query, startIndex, noOfRecords);
//		stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
//				ResultSet.CONCUR_READ_ONLY);
		//resultSet = stmt.executeQuery();
		resultSet=jdbcDAO.getQueryResultSet(query);

		if (getSublistOfResult)
		{
			if (startIndex > 0) // move cursor to the start index.
			{
				resultSet.absolute(startIndex);
			}
		}
		List list = getListFromResultSet(jdbcDAO); // get the resulset.

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
