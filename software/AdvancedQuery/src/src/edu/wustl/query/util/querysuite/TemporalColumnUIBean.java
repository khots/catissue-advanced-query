
package edu.wustl.query.util.querysuite;

import java.util.List;
import java.util.Map;

import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.querysuite.queryobject.IConstraints;
import edu.wustl.common.querysuite.queryobject.IOutputTerm;

/**Bean class for TemporalColumn.
 * @author vijay_pande
 *
 */
public class TemporalColumnUIBean
{

	private OutputTreeDataNode node;
	private String sql;
	private List<String> columnsList;
	private Map<String, IOutputTerm> outputTermsColumns;
	private int columnIndex = 0;
	private IConstraints constraints;

	/**
	 * Default constructor.
	 */
	public TemporalColumnUIBean()
	{

	}

	/**Parameterized constructor.
	 * @param node OutputTreeDataNode node
	 * @param selectSql2 sql string
	 * @param columnsList columns list
	 * @param outputTermsColumns output terms columns map
	 * @param columnIndex column index
	 * @param constraints constraints of query
	 */
	public TemporalColumnUIBean(OutputTreeDataNode node, String selectSql2,
			List<String> columnsList, Map<String, IOutputTerm> outputTermsColumns, int columnIndex,
			IConstraints constraints)
	{
		this.node = node;
		sql = selectSql2;
		this.columnsList = columnsList;
		this.outputTermsColumns = outputTermsColumns;
		this.columnIndex = columnIndex;
		this.constraints = constraints;
	}

	/**
	 * @return the columnIndex
	 */
	public int getColumnIndex()
	{
		return columnIndex;
	}

	/**
	 * @param columnIndex the columnIndex to set
	 */
	public void setColumnIndex(int columnIndex)
	{
		this.columnIndex = columnIndex;
	}

	/**
	 * @return the columnsList
	 */
	public List<String> getColumnsList()
	{
		return columnsList;
	}

	/**
	 * @param columnsList the columnsList to set
	 */
	public void setColumnsList(List<String> columnsList)
	{
		this.columnsList = columnsList;
	}

	/**
	 * @return the node
	 */
	public OutputTreeDataNode getNode()
	{
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(OutputTreeDataNode node)
	{
		this.node = node;
	}

	/**
	 * @return the outputTermsColumns
	 */
	public Map<String, IOutputTerm> getOutputTermsColumns()
	{
		return outputTermsColumns;
	}

	/**
	 * @param outputTermsColumns the outputTermsColumns to set
	 */
	public void setOutputTermsColumns(Map<String, IOutputTerm> outputTermsColumns)
	{
		this.outputTermsColumns = outputTermsColumns;
	}

	/**
	 * @return the sql
	 */
	public String getSql()
	{
		return sql;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql)
	{
		this.sql = sql;
	}

	/**
	 * @return the constraints
	 */
	public IConstraints getConstraints()
	{
		return constraints;
	}

	/**
	 * @param constraints the constraints to set
	 */
	public void setConstraints(IConstraints constraints)
	{
		this.constraints = constraints;
	}

}
