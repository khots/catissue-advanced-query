
package edu.wustl.query.parser;

import java.io.File;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.QueryBizLogic;

/**
 * This is utility class to create and save queries from xml.
 *
 */
public class ImportQuery
{
	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
			.getConfiguredLogger(ExportQuery.class);

	/**
	 * This method accepts the xml directory and saves query to database.
	 * @param args arguments.
	 * @throws Exception exception.
	 */
	public static void main(String[] args) throws Exception
	{
		if (args.length < 1)
		{
			logger.error("Syntax for running this utility is : \n"+
					"ant -f deploy.xml createQueryFromXml -DxmlDir=" +
					"<Directory where the xml files are located> ");
		}
		else
		{
			File dir = new File(args[0]);
			String[] fileNames = dir.list();
			System.setProperty("gov.nih.nci.security.configFile",
					"ApplicationSecurityConfig.xml");
			System.setProperty("gov.nih.nci.sdk.remote.catissuecore.securityLevel", "0");
			System.setProperty("gov.nih.nci.sdk.applications.session.timeout", "3000");
			QueryBizLogic bizLogic = new QueryBizLogic();
			for (String fileName : fileNames)
			{
				try
				{
					logger.info("Processing file-----" + fileName);
					CreateQuery importQuery = new CreateQuery(dir.getAbsolutePath() + "\\"
							+ fileName);
					IParameterizedQuery query = importQuery.createQuery();
					//ParseObject parseObject = new ParseObject();
					//System.out.println(parseObject.format(parseObject.processObject(query)));
					//query.setCreatedBy(user.getId());
					//query.setUpdatedBy(user.getId());
					if (query != null)
					{
						//save query
						bizLogic.insertSavedQueries(query, getSessionData(), false);
					}
					logger.info("Finished processing file-----" + fileName);
				}
				catch (Exception e)
				{
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Gets session data bean to save query.
	 * @return SessionDataBean
	 */
	private static SessionDataBean getSessionData()
	{
		SessionDataBean sessionDataBean = new SessionDataBean();
		sessionDataBean.setFirstName("admin");
		sessionDataBean.setLastName("admin");
		sessionDataBean.setSecurityRequired(false);
		sessionDataBean.setUserId(1L);
		sessionDataBean.setCsmUserId("1");
		sessionDataBean.setUserName("admin@admin.com");
		return sessionDataBean;
	}

	/**Method to retrieve query by name.
	 * @param name name of query
	 * @return query object of type IParameterizedQuery
	 */
	/*private static User getUserByLoginName(String name)
	{
		User user = null;
		try
		{
			DefaultBizLogic bizLogic = new DefaultBizLogic();
			List<User> userList = bizLogic.retrieve(User.class.getName(),
					QueryParserConstants.ATTR_USER_ID, name);
			if (!userList.isEmpty())
			{
				user = userList.get(0);
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return user;
	}*/
}
