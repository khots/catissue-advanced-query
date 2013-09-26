/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-advanced-query/LICENSE.txt for details.
 */

package edu.wustl.query.generator;

import java.io.IOException;

import org.apache.log4j.Logger;

import edu.wustl.common.util.FileLogger;
import edu.wustl.common.util.ParseException;
import edu.wustl.common.util.SqlFormatter;

class SQLLogger extends FileLogger<String>
{
    private static final Logger logger = Logger.getLogger(SQLLogger.class);

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

    @Override
    public boolean log(String string) throws IOException
    {
    	String formattedSql = null;
        try
        {
        	formattedSql = new SqlFormatter(string).format();
        }
        catch (ParseException e)
        {
            logger.warn("error while formatting sql" + e.getStackTrace());
        }
        return super.log(formattedSql);
    }
}
