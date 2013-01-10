package edu.wustl.query.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.dao.JDBCDAO;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.query.generator.ColumnValueBean;
import edu.wustl.dao.query.generator.DBTypes;
import edu.wustl.query.dto.QueryDTO;
import gov.nih.nci.security.authorization.domainobjects.User;

public class QueryDAO {
	
	public Session session = null;
	
	protected final static String GET_ALL_QUERIES = 
		  " SELECT" 
		+ " pq.identifier, pq.query_name, pq.description, ae.event_timestamp, aeq.root_entity_name, aeq.count_of_root_records" 
		+ " FROM" 
		+ " query_parameterized_query pq" 
		+ " LEFT OUTER JOIN catissue_audit_event_query_log aeq ON aeq.query_id = pq.identifier" 
		+ " LEFT OUTER JOIN catissue_audit_event ae ON aeq.audit_event_id = ae.identifier" 
		+ " WHERE" 
		+ " aeq.query_id is null" 
		+ " OR (ae.event_timestamp =" 
				+ " (SELECT MAX(iae.event_timestamp)" 
				+ " FROM catissue_audit_event iae, catissue_audit_event_query_log iaeq" 
				+ " WHERE iaeq.audit_event_id = iae.identifier" 
				+ " AND iaeq.query_id = pq.identifier))";
	
	protected final static String GET_QUERIES_BY_ID =  
		  " SELECT" 
		+ " pq.identifier, pq.query_name, pq.description, ae.event_timestamp, aeq.root_entity_name, aeq.count_of_root_records" 
		+ " FROM" 
		+ " query_parameterized_query pq" 
		+ " LEFT OUTER JOIN catissue_audit_event_query_log aeq ON aeq.query_id = pq.identifier" 
		+ " LEFT OUTER JOIN catissue_audit_event ae ON aeq.audit_event_id = ae.identifier" 
		+ " WHERE pq.identifier IN (%s)" 
		+ " AND (aeq.query_id is null" 
		+ " OR(ae.event_timestamp =" 
				+ " (SELECT MAX(iae.event_timestamp)" 
				+ " FROM catissue_audit_event iae, catissue_audit_event_query_log iaeq" 
				+ " WHERE iaeq.audit_event_id = iae.identifier" 
				+ " AND iaeq.query_id = pq.identifier)))";
	
	public List<QueryDTO> getAllQueries() 
	throws BizLogicException, DAOException {
		String appName=CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daofactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDAO = daofactory.getJDBCDAO(); 

		try {
			jdbcDAO.openSession(null);
			List<List<String>> result = jdbcDAO.executeQuery(GET_ALL_QUERIES, null,null);
			jdbcDAO.commit();	 
			return createQueryDTOs(result);
		} catch (DAOException e) {
			throw new BizLogicException(null,e,"DAOException: while Retrieving queries");
		} finally {
			jdbcDAO.closeSession();
		}
	}
	
	public List<QueryDTO> getQueriesById(Collection<Long> queryIds) throws BizLogicException, DAOException  
	{
		String appName=CommonServiceLocator.getInstance().getAppName();
		IDAOFactory daofactory = DAOConfigFactory.getInstance().getDAOFactory(appName);
		JDBCDAO jdbcDAO = daofactory.getJDBCDAO(); 
		
		try {
			jdbcDAO.openSession(null);
			String query = String.format(GET_QUERIES_BY_ID, getParamPlaceHolders(queryIds.size()));	 
			
			List<ColumnValueBean> parameters = new ArrayList<ColumnValueBean>();
			for (Long queryId : queryIds) {
			    parameters.add(new ColumnValueBean(queryId, DBTypes.LONG));
			}
			 
			List<List<String>> result = jdbcDAO.executeQuery(query, null, parameters);
			jdbcDAO.commit();
			 
			return createQueryDTOs(result);
		} catch (DAOException e) {	
			throw new BizLogicException(null,e,"DAOException: while Retrieving queries");
		} finally {
			jdbcDAO.closeSession();
		}
	}
	
	private List<QueryDTO> createQueryDTOs(List<List<String>> result) 
	throws BizLogicException {
		List<QueryDTO> queries = new ArrayList<QueryDTO>(); 
		for (List<String> rows : result) {
			QueryDTO queryDTO = new QueryDTO();
			queryDTO.setQueryId(Long.parseLong(rows.get(0)));
			queryDTO.setQueryName(rows.get(1));
			queryDTO.setQueryDescription(rows.get(2));	
			queryDTO.setExecutedOn(getExecutedOnTime(rows.get(3)));
			queryDTO.setRootEntityName(getRootEntityName(rows.get(4)));
			queryDTO.setCountOfRootRecords(getRootRecordCount(rows.get(5)));
			queryDTO.setOwnerName(getOwnerName(rows.get(0)));
			queries.add(queryDTO);
		}
		return queries;	
	}
	
	private String getRootRecordCount(String recordCount) {
		String count = "N/A";
		if(recordCount != null && !recordCount.isEmpty()) {
			count = recordCount;
		}
		return count;
	}
	
	private String getRootEntityName(String rootEntityName) {	 
		String rootEnName = "N/A";
		if(rootEntityName != null && !rootEntityName.isEmpty()) {
			rootEnName = rootEntityName.substring(rootEntityName.lastIndexOf('.') + 1);
		}
		return rootEnName;
	}
	
	private String getExecutedOnTime(String executedOnTime) {
		String executionTime = "N/A";
		if (executedOnTime != null && !executedOnTime.isEmpty()){
			executionTime = DashboardBizLogic.getFormattedDate(executedOnTime);
		}
		return executionTime;
	}
	
	//
	// TODO: Need to move out this method 
	//
	private String getOwnerName(String queryId) throws BizLogicException
	{
		DashboardBizLogic dashboardBizLogic = new DashboardBizLogic();
		User user = dashboardBizLogic.getQueryOwner(queryId);
		String ownerName = user.getLastName() + "," + user.getFirstName();
		return ownerName;
	}
	
	private String getParamPlaceHolders(int size) {
		StringBuilder parameters = new StringBuilder();
		for (int i = 0; i < size; i++) {
		    parameters.append("?, ");
		}

		if (size != 0) {
		    // remove trailing comma
		    parameters.delete(parameters.length() - 2, parameters.length());
		}
		
		return parameters.toString();
	}	
}
