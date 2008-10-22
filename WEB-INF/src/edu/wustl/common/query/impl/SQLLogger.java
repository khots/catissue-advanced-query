
package edu.wustl.common.query.impl;

import edu.wustl.common.util.FileLogger;

class SQLLogger extends FileLogger<String>
{

	SQLLogger()
	{
		super(false, false);
	}

	@Override
	protected String getLogFileExtension()
	{
		return "sql";
	}

	@Override
	protected String getBaseDir()
	{
		return System.getProperty("user.home") + "/sql_log";
	}

	@Override
	protected String getLogFileNamePrefix()
	{
		return "query";
	}
}
