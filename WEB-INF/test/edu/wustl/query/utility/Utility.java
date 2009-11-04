
package edu.wustl.query.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.naming.NamingContextFactory;
import org.jnp.server.NamingBeanImpl;

import com.ibm.db2.jcc.DB2DataSource;

import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.querysuite.queryobject.IQuery;
import edu.wustl.query.util.global.Constants;

/**
 * 
 * @author ravindra_jain
 *
 */
public class Utility
{
	public static String dbType=null, dbHost=null, dbPort=null, dbName=null, dbUserName, dbPassword, dbSchema;
	
	/**
	 * Create new Data source object.
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static DataSource getDataSource() throws FileNotFoundException, IOException
	{
		Properties props = new Properties();
        props.load(new FileInputStream("queryInstall.properties"));
        dbType = props.getProperty("database.type");
        dbHost = props.getProperty("database.host");
        dbPort = props.getProperty("database.port");
        dbName = props.getProperty("database.name");
        dbUserName = props.getProperty("database.username");
        dbPassword = props.getProperty("database.password");
        dbSchema = props.getProperty("database.schema");
		
		
		DB2DataSource dataSource = new DB2DataSource();

		dataSource.setDatabaseName(dbName);
		dataSource.setServerName(dbHost);
		dataSource.setPortNumber(Integer.valueOf(dbPort));
		dataSource.setUser(dbUserName);
		dataSource.setPassword(dbPassword);
		dataSource.setCurrentSchema(dbSchema);
		return dataSource;
	}

	/**
	 * Initialize and start JNP server
	 */
	public static void initTest()
	{
		try
		{
			ErrorKey.init("~");
			//Logger.configure();
			// org.apache.log4j.Logger logger = LoggerConfig.getConfiguredLogger(Utility.class);
			// Create a Properties object and set properties appropriately
			System.setProperty("java.naming.factory.initial", NamingContextFactory.class.getName());
			System.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			System.setProperty("gov.nih.nci.security.configFile",
			"WEB-INF/test/conf/ApplicationSecurityConfig.xml");
			System.setProperty("gov.nih.nci.sdk.remote.catissuecore.securityLevel", "0");
			System.setProperty("gov.nih.nci.sdk.applications.session.timeout", "3000");
			// start JNDI server
			NamingBeanImpl server = new NamingBeanImpl();
			server.start();
			// Create the initial context from the properties we just created
			Context initialContext = new InitialContext();

			// create data source and bind to JNDI
			DataSource ciderDS = getDataSource();
			
			// DataSource queryDS = getDataSource();
			initialContext.createSubcontext(Constants.JNDI_NAME_CIDER);
			// initialContext.createSubcontext(Constants.JNDI_NAME_QUERY);
			// initialContext.createSubcontext("java:/TransactionManager");

			initialContext.rebind(Constants.JNDI_NAME_CIDER, ciderDS);
			
		// initialContext.rebind(Constants.JNDI_NAME_QUERY, queryDS);
		}
		catch (Exception ex)
		{
			System.out.println("Application Initilization fail" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * To get DeSerialized version of IQuery
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static IQuery getQuery(String fileName) throws IOException, ClassNotFoundException
	{
		InputStream is = null;
		ObjectInputStream in = null;
		IQuery query = null;

		try
		{
			is = Utility.class.getClassLoader()
			.getResourceAsStream(fileName);
			// fis = new FileInputStream(fileName);
			in = new ObjectInputStream(is);
			query = (IQuery) in.readObject();
			in.close();
		}
		catch (IOException ex)
		{
			throw ex;
		}
		catch (ClassNotFoundException ex)
		{
			throw ex;
		}
		return query;
	}

}

