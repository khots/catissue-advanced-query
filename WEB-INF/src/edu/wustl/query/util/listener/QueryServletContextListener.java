
package edu.wustl.query.util.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.CacheException;
import edu.wustl.common.util.global.ApplicationProperties;

public class QueryServletContextListener implements ServletContextListener
{

	String DATASOURCE_JNDI_NAME = "java:/query";

	public void contextInitialized(ServletContextEvent sce)
	{
		try
		{
			ServletContext servletContext = sce.getServletContext();
			ApplicationProperties
					.initBundle(servletContext.getInitParameter("resourcebundleclass"));
			QueryServletContextListenerUtil.initializeQuery(sce, DATASOURCE_JNDI_NAME);

		}
		catch (Exception e)
		{
			//logger.error("Application failed to initialize");
			throw new RuntimeException(e.getLocalizedMessage(), e);

		}
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

}
