
package edu.wustl.query.util.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import edu.wustl.cab2b.server.path.PathFinder;
import edu.wustl.common.util.XMLPropertyHandler;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;

public class QueryServletContextListenerUtil
{

	public static void initializeQuery(ServletContextEvent sce, String datasourceJNDIName) throws Exception
	{
		Logger.configDefaultLogger(sce.getServletContext());
		Variables.applicationHome = sce.getServletContext().getRealPath("");

		setGlobalVariable();

		//Added by Baljeet....This method caches all the Meta data
		initEntityCache(datasourceJNDIName);

	}

	private static void initEntityCache(String datasourceJNDIName)
	{
		try
		{
			//Added for initializing PathFinder and EntityCache
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(datasourceJNDIName);
			Connection conn = ds.getConnection();
			PathFinder.getInstance(conn);
		}
		catch (Exception e)
		{
			//logger.debug("Exception occured while initialising entity cache");
		}

	}

	private static void setGlobalVariable() throws Exception
	{
		String path = System.getProperty("app.propertiesFile");
		XMLPropertyHandler.init(path);
		File propetiesDirPath = new File(path);
		Variables.propertiesDirPath = propetiesDirPath.getParent();

		Variables.applicationName = ApplicationProperties.getValue("app.name");
		Variables.applicationVersion = ApplicationProperties.getValue("app.version");
		int maximumTreeNodeLimit = Integer.parseInt(XMLPropertyHandler.getValue(Constants.MAXIMUM_TREE_NODE_LIMIT));
		Variables.maximumTreeNodeLimit = maximumTreeNodeLimit;
		readProperties();
		path = System.getProperty("app.propertiesFile");

	}

	private static void readProperties()
	{
		File file = new File(Variables.applicationHome + System.getProperty("file.separator") + "WEB-INF" + System.getProperty("file.separator")
				+ "classes" + System.getProperty("file.separator") + "query.properties");

		if (file.exists())
		{
			Properties queryProperties = new Properties();
			try
			{
				queryProperties.load(new FileInputStream(file));

				Variables.queryGeneratorClassName = queryProperties.getProperty("query.queryGeneratorClassName");
				//Added to get AbstractQuery Implementer Class Name.
				Variables.abstractQueryClassName = queryProperties.getProperty("query.abstractQueryClassName");
				Variables.abstractQueryManagerClassName = queryProperties.getProperty("query.abstractQueryManagerClassName");
				Variables.abstractQueryUIManagerClassName = queryProperties.getProperty("query.abstractQueryUIManagerClassName");
				Variables.recordsPerPageForspreadSheet = Integer.parseInt(queryProperties.getProperty("spreadSheet.recordsPerPage"));
				Variables.recordsPerPageForTree = Integer.parseInt(queryProperties.getProperty("tree.recordsPerPage"));
				
				
				Variables.properties = queryProperties;
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}
}
