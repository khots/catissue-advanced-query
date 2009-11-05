
package edu.wustl.query.querysuite.metadata;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.util.querysuite.DAOUtil;

/**
 * @author vijay_pande
 * Class to add curated path between two entities if an intermediate entity exist common to source as well as target entity.
 */
public class AddCuratedPath
{

	//private Connection connection;
	private JDBCDAO jdbcdao = null;
	private List<String> entityList;

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, DAOException
	{
		//Connection connection = DBUtil.getConnection();
		 JDBCDAO jdbcdao=DAOUtil.getJDBCDAO(null);
		//		Class.forName("com.mysql.jdbc.Driver");
		//		String url = "jdbc:mysql://localhost:3307/upgrade";
		//		Connection connection = DriverManager.getConnection(url, "root", "pspl");
	//	AddCuratedPath addCurratedPath = new AddCuratedPath(connection);
		AddCuratedPath addCurratedPath = new AddCuratedPath(jdbcdao);
		addCurratedPath.addCurratedPath();
	}

	public void addCurratedPath() throws IOException, SQLException, DAOException
	{
		populateMapForPath();

		StringTokenizer stringTokenizer;
		String sourceEntity;
		String targetEntity;
		String intermediatePath;

		int sourceEntityId = 0;
		int targetEntityId = 0;
		int mainEntityId = 0;

		int nextIdPath = 0;
		String sql = "select max(PATH_ID) from path";
	//	Statement stmt = connection.createStatement();
		//ResultSet rs = stmt.executeQuery(sql);
		ResultSet resultSet = jdbcdao.getQueryResultSet(sql);
		if (resultSet.next())
		{
			int maxId = resultSet.getInt(1);
			nextIdPath = maxId + 1;
		}
		resultSet.close();
		//stmt.close();
		for (String key : entityList)
		{
			intermediatePath = "";
			stringTokenizer = new StringTokenizer(key, ",");
			sourceEntity = stringTokenizer.nextToken();

//			sourceEntityId = UpdateMetadataUtil.getEntityIdByName(sourceEntity, connection
//					.createStatement());
			sourceEntityId = UpdateMetadataUtil.getEntityIdByName(sourceEntity, jdbcdao
				);
			mainEntityId = sourceEntityId;
			while (stringTokenizer.hasMoreTokens())
			{
				targetEntity = stringTokenizer.nextToken();
//				targetEntityId = UpdateMetadataUtil.getEntityIdByName(targetEntity, connection
//						.createStatement());
				targetEntityId = UpdateMetadataUtil.getEntityIdByName(targetEntity, jdbcdao);
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
			//stmt = connection.createStatement();
			//stmt.execute(sql);
			jdbcdao.executeUpdate(sql);
			//connection.commit();
			//jdbcdao.commit();
			nextIdPath++;
		}
		jdbcdao.commit();
	}

	private String getIntermediatePath(int sourceEntityId, int intermediateEntityId)
			throws SQLException, DAOException
	{
		String inetrmediatePath = null;
		ResultSet resultSet;
		//Statement stmt = connection.createStatement();
		String sql = "select INTERMEDIATE_PATH from path where FIRST_ENTITY_ID =" + sourceEntityId
				+ "  and LAST_ENTITY_ID = " + intermediateEntityId + "";
		//rs = stmt.executeQuery(sql);
		resultSet=jdbcdao.getQueryResultSet(sql);
		if (resultSet.next())
		{
			inetrmediatePath = resultSet.getString(1);
		}
		//stmt.close();
		resultSet.close();
		return inetrmediatePath;
	}

	private void populateMapForPath()
	{
		entityList = new ArrayList<String>();
//		entityList.add("Person,Demographics,Race");
//		entityList.add("Person,Demographics,Gender");
//		entityList.add("Person,Demographics,Address");
//		entityList.add("Person,Demographics,AdvancedDirectiveExists");
//		entityList.add("Person,Demographics,EthnicOrigin");
//		entityList.add("Person,Demographics,MaritalStatus");
//		entityList.add("Person,Demographics,Religion");
//		entityList.add("Person,Demographics,Phone");
//		entityList.add("Person,Demographics,AssociatedPerson");
//		entityList.add("Person,Demographics,PersonName");
		
		//Adding further paths
		//entityList.add("Demographics,Address,State");
		//entityList.add("Demographics,Address,Country");
		//entityList.add("Demographics,Address,AddressType");
		entityList.add("Demographics,AssociatedPerson,RelationToPerson");
		
		
		//Adding paths for Laboratory Procedure and Containments
//		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,LaboratoryResult");
//		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,SpecimanType");
//		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,Status");
//		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,MedicalRecordNumber");
//		entityList.add("LaboratoryProcedure,LaboratoryProcedureDetails,Facility");
//		entityList.add("LaboratoryProcedureDetails,MedicalRecordNumber,Facility");
//		entityList.add("LaboratoryProcedureDetails,LaboratoryResult,LaboratoryTestType");
//		entityList.add("LaboratoryProcedureDetails,LaboratoryResult,ResultValue");
//		entityList.add("LaboratoryProcedureDetails,LaboratoryResult,Status");
//		entityList.add("LaboratoryProcedureDetails,LaboratoryResult,ResultValue,Result");
//		entityList.add("LaboratoryProcedureDetails,LaboratoryResult,ResultValue,Result,UnitsOfMeasure");
//		entityList.add("LaboratoryProcedureDetails,LaboratoryResult,ResultValue,Result,NormalRange");
		
		
		//Adding curated paths for Encounter
//		entityList.add("Encounter,EncounterDetails,Insurance");
//		entityList.add("Encounter,EncounterDetails,Insurance,InsurancePlanType");
//		entityList.add("Encounter,EncounterDetails,Insurance,PersonName");
//		entityList.add("Encounter,EncounterDetails,Insurance,RelationToPerson");
//		entityList.add("Encounter,EncounterDetails,HipaaNotified");
//		entityList.add("Encounter,EncounterDetails,OptOutIndicator");
//		entityList.add("Encounter,EncounterDetails,VipIndicator");
//		entityList.add("Encounter,EncounterDetails,PatientType");
//		entityList.add("Encounter,EncounterDetails,PatientClass");
//		entityList.add("Encounter,EncounterDetails,FinancialClass");
//		entityList.add("Encounter,EncounterDetails,Service");
//		entityList.add("Encounter,EncounterDetails,FacilityDischargeDisposition");
//		entityList.add("Encounter,EncounterDetails,DischargeDisposition");
//		entityList.add("Encounter,EncounterDetails,InfectionControlCode");
//		entityList.add("Encounter,EncounterDetails,DiagnosticRelatedGroup");
//		entityList.add("Encounter,EncounterDetails,PatientLocation");
//		entityList.add("Encounter,EncounterDetails,Diagnosis");
		//entityList.add("EncounterDetails,Diagnosis,DiagnosisCode");
		//entityList.add("EncounterDetails,Diagnosis,DiagnosisType");
		//entityList.add("EncounterDetails,Procedure");
//		entityList.add("EncounterDetails,Procedure,ProcedureCodingMethod");
//		entityList.add("EncounterDetails,Procedure,ProcedureCode");
//		entityList.add("EncounterDetails,Procedure,Provider");
//		entityList.add("EncounterDetails,Procedure,Provider,PersonName");
//		entityList.add("EncounterDetails,Procedure,Provider,ProviderType");
		//entityList.add("EncounterDetails,Provider");
		//entityList.add("EncounterDetails,Provider,ProviderType");
		//entityList.add("EncounterDetails,Provider,PersonName");
	}

	public AddCuratedPath(JDBCDAO jdbcdao)
	{
		super();
		//this.connection = connection;
		this.jdbcdao = jdbcdao;
	}
}
