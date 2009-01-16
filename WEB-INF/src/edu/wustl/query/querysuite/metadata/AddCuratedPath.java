
package edu.wustl.query.querysuite.metadata;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.wustl.common.util.dbManager.DBUtil;

/**
 * @author vijay_pande
 * Class to add curated path between two entities if an intermediate entity exist common to source as well as target entity.
 */
public class AddCuratedPath
{

	private Connection connection;
	private List<String> entityList;

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException
	{
		Connection connection = DBUtil.getConnection();
		//		Class.forName("com.mysql.jdbc.Driver");
		//		String url = "jdbc:mysql://localhost:3307/upgrade";
		//		Connection connection = DriverManager.getConnection(url, "root", "pspl");
		AddCuratedPath addCurratedPath = new AddCuratedPath(connection);
		addCurratedPath.addCurratedPath();
	}

	public void addCurratedPath() throws IOException, SQLException
	{
		populateMapForPath();

		StringTokenizer st;
		String sourceEntity;
		String targetEntity;
		String intermediatePath;

		int sourceEntityId = 0;
		int targetEntityId = 0;
		int mainEntityId = 0;

		int nextIdPath = 0;
		String sql = "select max(PATH_ID) from path";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next())
		{
			int maxId = rs.getInt(1);
			nextIdPath = maxId + 1;
		}
		stmt.close();
		for (String key : entityList)
		{
			intermediatePath = "";
			st = new StringTokenizer(key, ",");
			sourceEntity = st.nextToken();

			sourceEntityId = UpdateMetadataUtil.getEntityIdByName(sourceEntity, connection
					.createStatement());
			mainEntityId = sourceEntityId;
			while (st.hasMoreTokens())
			{
				targetEntity = st.nextToken();
				targetEntityId = UpdateMetadataUtil.getEntityIdByName(targetEntity, connection
						.createStatement());
				String tempPath = getIntermediatePath(sourceEntityId, targetEntityId);
				if (intermediatePath.equals(""))
				{
					intermediatePath = tempPath;
				}
				else
				{
					intermediatePath = intermediatePath + "_" + tempPath;
				}

				sourceEntity = targetEntity;
				sourceEntityId = targetEntityId;
			}

			sql = "insert into path values (" + nextIdPath + "," + mainEntityId + ",'"
					+ intermediatePath + "'," + targetEntityId + ")";
			//UpdateMetadataUtil.executeInsertSQL(sql, connection.createStatement());
			stmt = connection.createStatement();
			stmt.execute(sql);
			connection.commit();
			nextIdPath++;
		}
	}

	private String getIntermediatePath(int sourceEntityId, int intermediateEntityId)
			throws SQLException
	{
		String inetrmediatePath = null;
		ResultSet rs;
		Statement stmt = connection.createStatement();
		String sql = "select INTERMEDIATE_PATH from path where FIRST_ENTITY_ID =" + sourceEntityId
				+ "  and LAST_ENTITY_ID = " + intermediateEntityId + "";
		rs = stmt.executeQuery(sql);
		if (rs.next())
		{
			inetrmediatePath = rs.getString(1);
		}
		stmt.close();
		return inetrmediatePath;
	}

	private void populateMapForPath()
	{
		entityList = new ArrayList<String>();
		entityList.add("Person,Demographics,Race");
		entityList.add("Person,Demographics,Gender");
		entityList.add("Person,Demographics,Address");
		entityList.add("Person,Demographics,AdvancedDirectiveExists");
		entityList.add("Person,Demographics,EthnicOrigin");
		entityList.add("Person,Demographics,MaritalStatus");
		entityList.add("Person,Demographics,Religion");
		entityList.add("Person,Demographics,Phone");
		entityList.add("Person,Demographics,AssociatedPerson");
		entityList.add("Person,Demographics,PersonName");
		
		//Adding further paths
		entityList.add("Person,Demographics,Address,State");
		entityList.add("Person,Demographics,Address,Country");
		entityList.add("Person,Demographics,Address,AddressType");
		
		entityList.add("Person,Demographics,AssociatedPerson,RelationToPerson");
		
		
		//Adding paths for Laboratory Procedure and Containments
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,SpecimanType");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,Status");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,MedicalRecordNumber");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,Facility");
		
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,MedicalRecordNumber,Facility");
		
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult,LaboratoryTestType");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult,ResultValue");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult,Status");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult,ResultValue,Result");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult,ResultValue,Result,UnitsOfMeasure");
		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult,ResultValue,Result,NormalRange");
		
	}

	public AddCuratedPath(Connection connection)
	{
		super();
		this.connection = connection;
	}
}
