//
//package edu.wustl.query.querysuite.metadata;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import edu.wustl.dao.JDBCDAO;
//import edu.wustl.dao.exception.DAOException;
//
//public class AddAssociation
//{
//
//	//private Connection connection = null;
//	private JDBCDAO jdbcdao = null;
//	
//
//	public void addAssociation() throws SQLException, IOException, DAOException
//	{
//		///AddAssociations addAssociations = new AddAssociations(connection);
//		AddAssociations addAssociations = new AddAssociations(jdbcdao);
//		String entityName = "edu.wustl.catissuecore.domain.Specimen";
//		String targetEntityName = "edu.wustl.catissuecore.domain.SpecimenPosition";
//		addAssociations.addAssociation(entityName, targetEntityName, "specimen_specimenPosition",
//				"ASSOCIATION", "specimen", true, "specimenPosition", "SPECIMEN_ID", null, 1, 1,
//				"BI_DIRECTIONAL");
//		addAssociations.addAssociation(targetEntityName, entityName, "specimenPosition_specimen",
//				"ASSOCIATION", "specimenPosition", false, "", "SPECIMEN_ID", null, 1, 0,
//				"BI_DIRECTIONAL");
//	}
//
//	public AddAssociation(Connection connection) throws SQLException
//	{
////		super();
////		this.connection = connection;
//		super();
//	}
//}