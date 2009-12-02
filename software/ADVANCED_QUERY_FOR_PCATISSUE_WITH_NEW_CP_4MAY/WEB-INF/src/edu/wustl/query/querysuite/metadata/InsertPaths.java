
package edu.wustl.query.querysuite.metadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;

/**
 * This class is called from an Ant target to insert indirect paths between clinportal entities.
 * It takes a txt file as an input. The file contains list of entities to be connected on each line.
 * E.g : edu.wustl.clinportal.domain.ClinicalStudy,edu.wustl.clinportal.domain.ClinicalStudyRegistration,edu.wustl.clinportal.domain.Participant
 * @author deepti_shelar
 *
 */
public class InsertPaths
{

	static private BufferedWriter writer = null;

	/**
	 * @param args name of the file input for paths
	 */
	public static void main(String[] args)
	{
		String fileName = "paths.txt";
		/*if(args.length !=0)
		{
			fileName = args[0];
		}*/
		List<List<String>> pathList;
		try {
			pathList = parseFile(fileName);
			writer = new BufferedWriter(new FileWriter("PathsLog.txt"));
			String appName = CommonServiceLocator.getInstance().getAppName();
			if(appName == null)
			{
				appName = "Query";
			}
			IDAOFactory daoFactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
			JDBCDAO dao = null;
			dao = daoFactory.getJDBCDAO();
			dao.openSession(null);
			for (List<String> entityList : pathList)
			{
				System.out.println(entityList.toString());
				getPathAndInsert(dao,entityList);
			}
			writer.write("\nCompleted inserting indirect paths between clinportal entities.");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Parses the input text file and creates a list of entities.
	 * @param fileName input text file
	 * @return List of entities
	 * @throws IOException exception
	 */
	private static List<List<String>> parseFile(String fileName) throws IOException
	{
		List<List<String>> indirectPaths = new ArrayList<List<String>>();
		List<String> paths = new ArrayList<String>();
		File file = new File(fileName);

		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;

		while ((line = bufRdr.readLine()) != null)
		{
			paths.add(line);
		}
		for (String path : paths)
		{
			List<String> singlePath = new ArrayList<String>();
			StringTokenizer tokenizer = new StringTokenizer(path, ",");
			while (tokenizer.hasMoreTokens())
			{
				singlePath.add(tokenizer.nextToken());
			}
			indirectPaths.add(singlePath);
		}
		return indirectPaths;
	}

	/**
	 * Gets the string of intermediate path and Inserts a record in path table for given set of entities
	 * @param dao
	 * @param entityList list of entities
	 * @throws IOException
	 * @throws SQLException
	 * @throws DAOException
	 * @throws IOException exception
	 * @throws DAOException exception
	 */
	private static void getPathAndInsert(JDBCDAO dao, List<String> entityList)
	throws DAOException, SQLException, IOException

	{
		List<Long> entityIdList = getEntityIDsList(dao, entityList, null);
		if(entityIdList == null)
		{
			writer.write("\nSkipping these entities as the above entity" +
					" does not present from the list of :"+entityList.toString());
			return;
		}
		List<Long> parentEntityIdList = getParentEntityIdsList(dao, entityIdList);
		List<String> intraModelId = getIntermediatePaths(dao, entityIdList, parentEntityIdList);
		if(intraModelId.isEmpty())
		{
			writer.write("\nNo path present between "+getEntityName(dao,entityIdList));
		}
		else
		{
			StringBuffer intermediatePath = new StringBuffer(intraModelId.get(0));
			for (int index = 1; index < intraModelId.size(); index++)
			{
				intermediatePath.append('_');
				intermediatePath.append(intraModelId.get(index).toString());
			}
			Long maxPathId = getNextPathId(dao);
			insertPath(dao, entityIdList, intermediatePath, maxPathId);
		}
	}
	/**
	 * Inserts a record in path table for given set of entities
	 * @param dao dao
	 * @param entityIdList list of entity id's
	 * @param intermediatePath string path to be inserted
	 * @param maxPathId next path id
	 * @throws DAOException exception
	 * @throws SQLException exception
	 * @throws IOException exception
	 */
	private static void insertPath(JDBCDAO dao, List<Long> entityIdList,
			StringBuffer intermediatePath, Long maxPathId) throws DAOException,
			SQLException, IOException {
		Long srcEntId = entityIdList.get(0);
		Long targetEntId = entityIdList.get(entityIdList.size() - 1);
		String sql = "select INTERMEDIATE_PATH from PATH where FIRST_ENTITY_ID="
			+ srcEntId + " and LAST_ENTITY_ID="
			+ targetEntId;
		ResultSet resultSet = dao.getQueryResultSet(sql);
		boolean ifSamePathExists = false;
		List<Long> idList = new ArrayList<Long>();
		idList.add(srcEntId);
		idList.add(targetEntId);
		while (resultSet.next())
		{
			String interPath = resultSet.getString("INTERMEDIATE_PATH");
			if(intermediatePath.toString().equalsIgnoreCase(interPath))
			{
				writer.write("\nSame path already exists between "+getEntityName(dao,idList));
				ifSamePathExists = true;
			}
			else
			{
				ifSamePathExists = false;
			}
			continue;
		}
		dao.closeStatement(resultSet);
		if(!ifSamePathExists)
		{
			sql = "INSERT INTO path values(" + maxPathId + "," + srcEntId + ",'"
			+ intermediatePath.toString() + "',"
			+ targetEntId + ")";
			dao.executeUpdate(sql);
			writer.write("\nInserted path between "+getEntityName(dao,idList));
			dao.commit();
		}
	}
	/**
	 * Gets the maximum path id from Path table
	 * @param dao dao
	 * @return long id
	 * @throws DAOException exception
	 * @throws SQLException exception
	 */
	private static Long getNextPathId(JDBCDAO dao) throws DAOException,
			SQLException
	{
		ResultSet resultSet;
		Long maxPathId = null;
		resultSet = dao.getQueryResultSet("select max(PATH_ID) from path");
		if (resultSet.next())
		{
			maxPathId = resultSet.getLong(1) + 1;
		}
		dao.closeStatement(resultSet);
		return maxPathId;
	}

	/**
	 * Gets the list of parent entity identifiers
	 * @param dao dao
	 * @param entityIdList entity identifiers
	 * @return list of parent entity identifiers
	 * @throws SQLException exception
	 * @throws DAOException exception
	 * @throws IOException exception
	 */
	private static List<Long> getParentEntityIdsList(JDBCDAO dao, List<Long> entityIdList)
			throws SQLException, DAOException, IOException
	{
		List<Long> parententityIdList = new ArrayList<Long>();
		ResultSet resultSet = null;
		for (int c = 0; c < entityIdList.size(); c++)
		{
			Long entityId = entityIdList.get(c);
			String sql = "select PARENT_ENTITY_ID from DYEXTN_ENTITY where IDENTIFIER='" + entityId
					+ "'";
			resultSet = dao.getQueryResultSet(sql);
			while (resultSet.next())
			{
				parententityIdList.add(resultSet.getLong(1));
			}
		}
		dao.closeStatement(resultSet);
		return parententityIdList;
	}

	/**
	 * Gets the list of entity identifiers
	 * @param dao dao
	 * @param entityIdList entity identifiers
	 * @param entityGrpId group id
	 * @return list of entity identifiers
	 * @throws SQLException exception
	 * @throws DAOException exception
	 * @throws IOException exception
	 */
	private static List<Long> getEntityIDsList(JDBCDAO dao, List<String> entityList,
			Long entityGrpId) throws SQLException, DAOException, IOException
	{
		List<Long> entityIdList = new ArrayList<Long>();
		String sql = "";
		ResultSet resultSet = null;
		for (String entityName : entityList)
		{
			if(entityGrpId == null)
			{
				sql = "select dam.IDENTIFIER from DYEXTN_ABSTRACT_METADATA dam ,DYEXTN_ENTITY de where dam.NAME='"
					+ entityName
					+ "' and  dam.identifier= de.identifier ";
			}
			else
			{
				sql = "select dam.IDENTIFIER from DYEXTN_ABSTRACT_METADATA dam ,DYEXTN_ENTITY de where dam.NAME='"
					+ entityName
					+ "' and  dam.identifier= de.identifier and de.ENTITY_GROUP_ID = '"
					+ entityGrpId + "'";
			}
			resultSet = dao.getQueryResultSet(sql);
			if(resultSet.next())
			{
				entityIdList.add(resultSet.getLong(1));
			}
			else
			{
				writer.write("\nEntity does not exist with name : "+entityName);
				entityIdList = null;
				break;
			}
		}
		dao.closeStatement(resultSet);
		return entityIdList;
	}

	/**
	 * Returns the string of intermediate path
	 * @param dao dao
	 * @param entityIdList list of entity identifiers
	 * @param parentEntityIdList list of parent entity identifiers
	 * @return path string
	 * @throws SQLException exception
	 * @throws DAOException exception
	 * @throws IOException exception
	 */
	private static List<String> getIntermediatePaths(JDBCDAO dao, List<Long> entityIdList,
			List<Long> parentEntityIdList) throws SQLException, IOException, DAOException
	{
		List<String> intraModelId = new ArrayList<String>();
		ResultSet resultSet = null;
		for (int index = 0; index < entityIdList.size() - 1; index++)
		{
			if (index + 1 < entityIdList.size())
			{
				boolean notFound = true;
				String sql = "select INTERMEDIATE_PATH from PATH where FIRST_ENTITY_ID="
						+ entityIdList.get(index) + " and LAST_ENTITY_ID="
						+ entityIdList.get(index + 1);
				resultSet = dao.getQueryResultSet(sql);
				while (resultSet.next())
				{
					notFound = false;
					intraModelId.add(resultSet.getString(1));
				}
				dao.closeStatement(resultSet);
				if (notFound)
				{
					ResultSet rs1;
					String sql1 = "select INTERMEDIATE_PATH from PATH where FIRST_ENTITY_ID="
							+ parentEntityIdList.get(index) + " and LAST_ENTITY_ID="
							+ entityIdList.get(index + 1);

					rs1 = dao.getQueryResultSet(sql1);
					boolean found = false;
					while (rs1.next())
					{
						intraModelId.add(rs1.getString(1));
						found = true;
					}
					dao.closeStatement(rs1);
					if (!found)
					{
						ResultSet rs2;
						String sql2 = "select INTERMEDIATE_PATH from PATH where FIRST_ENTITY_ID="
								+ entityIdList.get(index) + " and LAST_ENTITY_ID="
								+ parentEntityIdList.get(index + 1);
						rs2 = dao.getQueryResultSet(sql2);
						while (rs2.next())
						{
							intraModelId.add(rs2.getString(1));
						}
						dao.closeStatement(rs2);
					}
				}
			}
		}
		return intraModelId;
	}
	/**
	 *
	 * @param dao dao
	 * @param idList idList
	 * @return entity name
	 * @throws DAOException DAOException
	 * @throws SQLException SQLException
	 * @throws IOException IOException
	 */
	private static String getEntityName(JDBCDAO dao,List<Long> idList) throws DAOException, SQLException, IOException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append('(');
		for (Long id : idList)
		{
			buffer.append(',').append(id);
		}
		buffer.append(')');
		if(buffer.charAt(1) == ',')
		{
			buffer.replace(1, 2, "");
		}
		String sql = "select name from DYEXTN_ABSTRACT_METADATA where identifier in "+buffer.toString();
		ResultSet resultSet = dao.getQueryResultSet(sql);
		buffer = new StringBuffer();
		while (resultSet.next())
		{
			buffer.append(resultSet.getString(1));
			buffer.append(',');
		}
		dao.closeStatement(resultSet);
		return buffer.toString();
	}
}
