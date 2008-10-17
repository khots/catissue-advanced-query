package edu.wustl.query.util.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import net.sf.ehcache.CacheException;
import edu.wustl.cab2b.server.path.PathFinder;
import edu.wustl.common.util.XMLPropertyHandler;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.Variables;


public class QueryCoreServletContextListener implements ServletContextListener
{
	
	String DATASOURCE_JNDI_NAME = "java:/query";
	
	public void contextInitialized(ServletContextEvent sce)
    {
    	try{  
	    	ServletContext servletContext = sce.getServletContext();
	    	Variables.applicationHome = sce.getServletContext().getRealPath("");
	    	Logger.configDefaultLogger(servletContext);
	        ApplicationProperties.initBundle(servletContext.getInitParameter("resourcebundleclass"));
			setGlobalVariable();
			
			//Added by Baljeet....This method caches all the Meta data
			initEntityCache(); 
			
    	}
    	catch(Exception e)
    	{
    		//logger.error("Application failed to initialize");
    		throw new RuntimeException( e.getLocalizedMessage(), e);

    	}
    }
	
	private void initEntityCache()
	{
		try
		{
            //Added for initializing PathFinder and EntityCache
			InitialContext ctx = new InitialContext();
	        DataSource ds = (DataSource)ctx.lookup(DATASOURCE_JNDI_NAME);
	        Connection conn = ds.getConnection();
			PathFinder.getInstance(conn);
		}
		catch (Exception e)
		{
			//logger.debug("Exception occured while initialising entity cache");
		}
		
	}
	private void setGlobalVariable() throws Exception
	{  
		String path = System.getProperty("app.propertiesFile");
    	XMLPropertyHandler.init(path);
    	File propetiesDirPath = new File(path);
    	Variables.propertiesDirPath = propetiesDirPath.getParent();

        Variables.applicationName = ApplicationProperties.getValue("app.name");
        Variables.applicationVersion = ApplicationProperties.getValue("app.version");
		int maximumTreeNodeLimit = Integer.parseInt(XMLPropertyHandler.getValue(Constants.MAXIMUM_TREE_NODE_LIMIT));
		Variables.maximumTreeNodeLimit = maximumTreeNodeLimit;
		setQueryGeneratorClass();
 	    path = System.getProperty("app.propertiesFile");
	}

	
	
	public void contextDestroyed(ServletContextEvent sce)
    {
    	//  shutting down the cacheManager
		try
		{
			//CatissueCoreCacheManager catissueCoreCacheManager = CatissueCoreCacheManager.getInstance();
			//catissueCoreCacheManager.shutdown();
		}
		catch (CacheException e)
		{
			//logger.debug("Exception occured while shutting instance of CatissueCoreCacheManager");
			//e.printStackTrace();
		}
	 }
	
	public void setQueryGeneratorClass()
	{
		File file = new File(Variables.applicationHome + System.getProperty("file.separator")
				+ "WEB-INF" + System.getProperty("file.separator") + "classes"
				+ System.getProperty("file.separator") + "query.properties");
		if (file.exists())
		{
			Properties queryProperties = new Properties();
			try
			{
				queryProperties.load(new FileInputStream(file));
				Variables.queryGeneratorClassName = queryProperties
						.getProperty("query.queryGeneratorClassName");
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
