
package edu.wustl.common.query.impl;

import edu.wustl.common.util.FileLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class SQLLogger.
 */
class SQLLogger extends FileLogger<String>
{

	/**
	 * Instantiates a new sQL logger.
	 */
	SQLLogger()
	{
		super(false, false);
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.util.FileLogger#getLogFileExtension()
	 */
	@Override
	protected String getLogFileExtension()
	{
		return "sql";
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.util.FileLogger#getBaseDir()
	 */
	@Override
	protected String getBaseDir()
	{
		return System.getProperty("user.home") + "/sql_log";
	}

	/* (non-Javadoc)
	 * @see edu.wustl.common.util.FileLogger#getLogFileNamePrefix()
	 */
	@Override
	protected String getLogFileNamePrefix()
	{
		return "query";
	}
}
