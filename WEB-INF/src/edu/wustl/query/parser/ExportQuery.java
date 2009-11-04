
package edu.wustl.query.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import edu.wustl.common.querysuite.queryobject.IParameterizedQuery;
import edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.QueryBizLogic;
import edu.wustl.query.util.reflection.ReflectionException;

/**Wrapper class to export query that is to convert query object, present in DB, to XML.
 * @author vijay_pande
 *
 */
public class ExportQuery
{
	/**
	 * logger for this class.
	 */
	private static org.apache.log4j.Logger logger = LoggerConfig
	.getConfiguredLogger(ExportQuery.class);

	/**
	 *
	 * @param args arguments
	 * @throws ParserConfigurationException parse exception
	 * @throws ReflectionException reflection exception
	 * @throws IOException IO exception
	 */
	public static void main(String[] args) throws ParserConfigurationException,
			ReflectionException, IOException
	{
		String fileName = args[0];
		ExportQuery exportQuery = new ExportQuery();
		List<IParameterizedQuery> queryList;
		if(args.length < 1)
		{
			logger
					.error("Syntax for running this utility is \n : " +
							"ant -f deploy.xml exportQueryToXml -DxmlFilePath=" +
							"<Directory where the xml files will be created> ");
		}
		else
		{
			if(args.length == 2)
			{
				String queryName = args[1];
				queryList = exportQuery.getQueryByName(queryName);
			}
			else
			{
				queryList = exportQuery.getAllQueries();
			}
			for (IParameterizedQuery query : queryList)
			{
				exportSingleQuery(fileName, query);
			}
		}
	}

	/**
	 * This method exports each query to xml.
	 * @param fileName name of file
	 * @param query IQuery
	 * @throws ParserConfigurationException exception
	 * @throws ReflectionException exception
	 * @throws IOException exception
	 */
	private static void exportSingleQuery(String fileName, IParameterizedQuery query)
			throws ParserConfigurationException, ReflectionException, IOException
	{
		if (query != null)
		{
			ParseObject parseObject = new ParseObject();

			Document document = parseObject.processObject(query);
			String xmlForm = parseObject.format(document);
			File file = new File(fileName+"/" + query.getName() + ".xml");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(xmlForm);
			writer.close();
		}
	}
	/**Method to retrieve query by name.
	 * @param name name of query
	 * @return query object of type IParameterizedQuery
	 */
	private List<IParameterizedQuery> getQueryByName(String name)
	{
		List<IParameterizedQuery> queryList = new ArrayList<IParameterizedQuery>();
		try
		{
			QueryBizLogic bizLogic = new QueryBizLogic();
			queryList = bizLogic.retrieve(ParameterizedQuery.class
					.getName(),QueryParserConstants.ATTR_NAME, name);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
		}
		return queryList;
	}

	/**
	 * This method gets all queries from database.
	 * @return list of queries.
	 */
	private List<IParameterizedQuery> getAllQueries()
	{
		List<IParameterizedQuery> queryList = new ArrayList<IParameterizedQuery>();
		try
		{
			QueryBizLogic bizLogic = new QueryBizLogic();
			queryList= bizLogic.retrieve(ParameterizedQuery.class
					.getName());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
		}
		return queryList;
	}
}
