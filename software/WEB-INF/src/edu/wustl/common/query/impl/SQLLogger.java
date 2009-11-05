
package edu.wustl.common.query.impl;

import edu.wustl.common.util.FileLogger;
import edu.wustl.query.util.global.Variables;

/**
 * @author siddharth_shah
 */
class SQLLogger extends FileLogger<String>
{

	/**
	 * Constructor.
	 */
	SQLLogger()
	{
		super(false, false);
	}

	/**
	 * @return File Extension
	 */
	@Override
	protected String getLogFileExtension()
	{
		return "sql";
	}

	/**
	 * @return Directory
	 */
	@Override
	protected String getBaseDir()
	{
		return Variables.properties.getProperty("xquery.jbossPath")  + "/log/sql_log";
	}

	/**
	 * @return File Name Prefix
	 */
	@Override
	protected String getLogFileNamePrefix()
	{
		return "query";
	}
}

