
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
import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.util.XMLPropertyHandler;
import edu.wustl.common.util.global.ApplicationProperties;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.common.vocab.VocabularyException;
import edu.wustl.common.vocab.utility.VocabUtil;
import edu.wustl.query.util.filter.StrutsConfigReader;
import edu.wustl.query.util.global.Constants;
import edu.wustl.query.util.global.VIProperties;
import edu.wustl.query.util.global.Variables;

public class QueryServletContextListenerUtil
{

	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(QueryServletContextListenerUtil.class);

	public static void initializeQuery(ServletContextEvent sce, String datasourceJNDIName)
			throws Exception
	{
		ErrorKey.init("~");
		//Logger.configDefaultLogger(sce.getServletContext());
		/**
		 * Getting Application Properties file path
		 */
		String propDirPath = sce.getServletContext().getRealPath("WEB-INF")
				+ System.getProperty("file.separator") + "classes";
		/*String appResourcesPath = propDirPath+ System.getProperty("file.separator")
				+ sce.getServletContext().getInitParameter("applicationproperties");*/
		CommonServiceLocator.getInstance().setAppHome(sce.getServletContext().getRealPath(""));

		LoggerConfig.configureLogger(propDirPath);
		Variables.applicationHome = sce.getServletContext().getRealPath("");

		setGlobalVariable();

		//Added by Baljeet....This method caches all the Meta data
		initEntityCache(datasourceJNDIName);

		// Added to create map of query actions which will be used
		//by QueryRequestFilter to check authorization
		StrutsConfigReader.init(Variables.applicationHome + File.separator
				+ Constants.WEB_INF_FOLDER_NAME + File.separator
				+ Constants.AQ_STRUTS_CONFIG_FILE_NAME);

	}

	/**
	 * @param datasourceJNDIName datasourceJNDIName
	 */
	private static void initEntityCache(String datasourceJNDIName)
	{
		try
		{
			//Added for initializing PathFinder and EntityCache
			InitialContext ctx = new InitialContext();
			DataSource dataSource = (DataSource) ctx.lookup(datasourceJNDIName);
			Connection conn = dataSource.getConnection();
			//PathFinder.getInstance(conn);
			PathFinder.getInstance(conn);

		}
		catch (Exception e)
		{
			logger.error("Exception occured while initialising entity cache", e);
		}

	}

	/**
	 * @throws Exception Exception
	 */
	private static void setGlobalVariable() throws Exception
	{
		String path = System.getProperty("app.propertiesFile");
		XMLPropertyHandler.init(path);
		File propetiesDirPath = new File(path);
		Variables.propertiesDirPath = propetiesDirPath.getParent();

		//		Variables.applicationName = ApplicationProperties.getValue("app.name");
		Variables.applicationName = Constants.APPLICATION_NAME;
		Variables.applicationVersion = ApplicationProperties.getValue("app.version");
		int maximumTreeNodeLimit = Integer.parseInt(XMLPropertyHandler
				.getValue(Constants.MAXIMUM_TREE_NODE_LIMIT));
		Variables.maximumTreeNodeLimit = maximumTreeNodeLimit;
		readProperties();
		path = System.getProperty("app.propertiesFile");

		//configure VI
		setVIProperties();

	}

	/**
	 * @throws VocabularyException VocabularyException
	 */
	private static void setVIProperties() throws VocabularyException
	{
		Properties vocabProperties = VocabUtil.getVocabProperties();
		VIProperties.sourceVocabName = vocabProperties.getProperty("source.vocab.name");
		VIProperties.sourceVocabVersion = vocabProperties.getProperty("source.vocab.version");
		VIProperties.sourceVocabUrn = vocabProperties.getProperty("source.vocab.urn");
		VIProperties.searchAlgorithm = vocabProperties.getProperty("match.algorithm");
		VIProperties.maxPVsToShow = Integer.valueOf(vocabProperties.getProperty("pvs.to.show"));
		VIProperties.maxToReturnFromSearch = Integer.valueOf(vocabProperties
				.getProperty("max.to.return.from.search"));
		VIProperties.translationAssociation = vocabProperties
				.getProperty("vocab.translation.association.name");
		VIProperties.medClassName = vocabProperties.getProperty("med.class.name");

	}

	/**
	 * read properties. on server startup.
	 */
	private static void readProperties()
	{

		File file = new File(Variables.propertiesDirPath + System.getProperty("file.separator")
				+ "query.properties");

		if (file.exists())
		{
			Properties queryProperties = new Properties();
			try
			{
				queryProperties.load(new FileInputStream(file));

				Variables.queryGeneratorClassName = queryProperties
						.getProperty("query.queryGeneratorClassName");
				//Added to get AbstractQuery Implementer Class Name.
				Variables.abstractQueryClassName = queryProperties
						.getProperty("query.abstractQueryClassName");
				Variables.abstractQueryManagerClassName = queryProperties
						.getProperty("query.abstractQueryManagerClassName");
				Variables.queryUIManagerClassName = queryProperties
						.getProperty("query.abstractQueryUIManagerClassName");
				Variables.queryITableManagerClassName = queryProperties
						.getProperty("query.abstractQueryITableManagerClassName");
				Variables.viewIQueryGeneratorClassName = queryProperties
						.getProperty("query.viewIQueryGeneratorClassName");
				Variables.urlAccessValidator = queryProperties
						.getProperty("query.urlAccessValidator");
				Variables.recordsPerPageForSpreadSheet = Integer.parseInt(queryProperties
						.getProperty("spreadSheet.recordsPerPage"));
				Variables.recordsPerPageForTree = Integer.parseInt(queryProperties
						.getProperty("tree.recordsPerPage"));
				Variables.resultLimit = Integer.parseInt(queryProperties
						.getProperty("datasecurity.resultLimit"));
				Variables.exportDataThreadClassName = queryProperties
						.getProperty("query.exportDataThreadClassName");
				Variables.dataQueryExecutionClassName = queryProperties
						.getProperty("query.dataQueryExecutionClassName");
				Variables.properties = queryProperties;
				Variables.spreadSheetGeneratorClassName = queryProperties
						.getProperty("query.spreadSheetGeneratorClassName");
				Variables.ajaxCallSleepTime = Integer.parseInt(queryProperties
						.getProperty("ajaxCall.sleepTime"));
				Variables.gridDisplaySleepTime = Integer.parseInt(queryProperties
						.getProperty("gridDisplay.sleepTime"));

				Variables.maxtreeExpansionLimit =
					Integer.parseInt(queryProperties.getProperty("tree.expansionLimit"));
				Variables.exportHome = queryProperties.getProperty("exportHome");
				Variables.applicationName = queryProperties.getProperty("app.name");
				Variables.dataQueryQueueSize = Integer.parseInt(queryProperties
						.getProperty("dataQuery.queueSize"));
				Variables.dataQueryThreadWaitTime = Long.parseLong(queryProperties
						.getProperty("dataQueryThread.waitTime"));
				setlimitdataProperties(queryProperties);
				setPropertiesForSenaMail(queryProperties);

			}
			catch (FileNotFoundException e)
			{
				logger.error(e.getMessage(), e);
			}
			catch (IOException e)
			{
				logger.error(e.getMessage(), e);
			}
		}

	}

	/**
	 * set the Properties for email.
	 * @param queryProperties Properties
	 */
	private static void setPropertiesForSenaMail(Properties queryProperties)
	{
		Variables.sendEmailOnFailure = Boolean.parseBoolean(queryProperties
				.getProperty("sendemail.on.query.execution.fail"));
		Variables.emailAddresses=queryProperties.getProperty("email.addresseses").split(",");
	}

	/**
	 * set the limits for count and data results.
	 * @param queryProperties Properties
	 */
	private static void setlimitdataProperties(Properties queryProperties)
	{
		Variables.limitForCount = Long.parseLong(queryProperties
				.getProperty("countquery.recordlimit"));
		Variables.limitForData = Long.parseLong(queryProperties
				.getProperty("dataquery.recordlimit"));
	}
}
