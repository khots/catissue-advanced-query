
package edu.wustl.common.query.impl;

import edu.wustl.common.util.FileLogger;
import edu.wustl.query.util.global.Variables;

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
		return Variables.properties.getProperty("xquery.jbossPath")  + "/sql_log";
	}

	@Override
	protected String getLogFileNamePrefix()
	{
		return "query";
	}
}
