package edu.wustl.query.querysuite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wustl.common.exception.BizLogicException;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.util.EmailClient;
import edu.wustl.common.util.ExportReport;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.query.bizlogic.QueryTagBizLogic;
import edu.wustl.query.dto.QueryExportDTO;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.UserCache;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryDetails;
import gov.nih.nci.security.authorization.domainobjects.User;

public class QueryDataExportTask implements Runnable {
	
	private QueryExportDTO dto;
	private ExportReport report;
	private static final Logger LOGGER = Logger.getCommonLogger(QueryTagBizLogic.class);
 
	public QueryDataExportTask(QueryExportDTO dto) {
		this.dto = dto;
	}

	@Override
	public void run() {
		QuerySessionData clone = createCloneQuerySessionData(dto.getQuerySessionData());
		modifySql(clone, dto.getQueryDetails(), dto.getRootOutputTreeNodeList());
		int recordsPerPage = clone.getRecordsPerPage();
		EmailClient.getInstance();
		int dataSize = clone.getRecordsPerPage();
			
		int pageNumber = 1; 
		while(dataSize == recordsPerPage)
		{
			List<List<String>> dataList = new ArrayList<List<String>>();
			List<List<String>> subExportList = new ArrayList<List<String>>();
			try 
			{
//				dataList = Utility.getPaginationDataList(dto.isSimpleSearch(), dto.getColumnList(), null,
//						recordsPerPage, pageNumber, dto.getQuerySessionData(), dto.isDefinedView());
				dataSize = dataList.size();					
				dataList = Utility.getFormattedOutput(dataList);
				subExportList = addDataToExportList(subExportList, dto.getColumnList(),  
						dataList, pageNumber);
				exportAndSend(subExportList, !(dataSize == recordsPerPage), true);
				pageNumber ++;	
			//} //catch (DAOException e) {
				//LOGGER.error("Error while exporting all data"+ e.getMessage());
			} catch (IOException e) {
				LOGGER.error("Error while exporting all data"+ e.getMessage());
			}
		}
		
		
	}
	
	private void modifySql(QuerySessionData querySessionData, QueryDetails queryDetails, 
			List<OutputTreeDataNode> rootOutputTreeNodeList)
	{
		Map<String,String> aliasMap = queryDetails.getColumnNameVsAliasMap();
		String columnAlias = null;
		for (QueryOutputTreeAttributeMetadata attrMeta: rootOutputTreeNodeList.get(0).getAttributes())
		{
			if (attrMeta.getAttribute().getName().equals(AQConstants.IDENTIFIER) )
			{
				columnAlias = aliasMap.get(attrMeta.getColumnName());
				break;
			}
		}
		String sql = querySessionData.getSql();		
		
		if (columnAlias != null){
			if(sql.indexOf(columnAlias) < 0){
			    sql = "SELECT DISTINCT "+ columnAlias +", "+ sql.substring(15); // 15 is index after SELECT DISTINCT
			}
			sql = sql+ " ORDER BY " + columnAlias;
		}
		querySessionData.setSql(sql);	
	}
	
	private QuerySessionData createCloneQuerySessionData(QuerySessionData querySessionData)
	{
		QuerySessionData clone = new QuerySessionData();
		clone.setSql(querySessionData.getSql());
		clone.setTotalNumberOfRecords(querySessionData.getTotalNumberOfRecords());
		clone.setRecordsPerPage(querySessionData.getRecordsPerPage());
		clone.setSecureExecute(querySessionData.isSecureExecute());
		clone.setHasConditionOnIdentifiedField(querySessionData.isHasConditionOnIdentifiedField());
		clone.setQueryResultObjectDataMap(querySessionData.getQueryResultObjectDataMap());
		return clone;
	}
	
	private void sendEmailToClient() 
	{
		Map<String,Object> contextMap = new HashMap<String,Object>();
		User user = null;
		try {
			user = UserCache.getUser(dto.getCsmUserId());
			contextMap.put("user", user);
			contextMap.put("appUrl", CommonServiceLocator.getInstance().getAppURL());
			contextMap.put("fileName", dto.getFileName());
			contextMap.put("queryInfo", dto.getExportList());
			
			String queryName = "Query";              // required for custom email subject
			if(dto.getQueryName()!= null){
				queryName =  dto.getQueryName();
			}
			
			EmailClient.getInstance().sendEmail("query.exportData",  new String[]{ user.getEmailId() }, null, null, contextMap, queryName);
		} catch (BizLogicException e) {
			LOGGER.error("Error while sending email"+ e.getMessage());
		}		
	}
	
	public void exportAndSend(List<List<String>> exportList, boolean downloadFile, boolean sendEmail)
			throws IOException
	{	
		String filePath = null;
		if(report == null){
			filePath = getActualFilePath();
			report = new ExportReport(filePath);
		}
		report.writeData(exportList, AQConstants.DELIMETER);
		if(downloadFile){
			report.closeFile();
			report = null;
			if(sendEmail){	
				sendEmailToClient();
			} 
		}
		
	}
	
	private List<List<String>> addDataToExportList(
			List<List<String>> exportList, List<String> columnList, List<List<String>> dataList, int pageNumber)
	{
		
		Object[] obj = dto.getFormValues().keySet().toArray();
		boolean isDefineView = false;
		if(pageNumber == 1){
			for(List<String> queryInfo : dto.getExportList()){
				exportList.add(queryInfo);
			}
			exportList.add(columnList);
		}
		
		int columnsSize = columnList.size();

		if (isDefineView || dto.exportAll)
		{
			for (int counter = 0; counter < dataList.size(); counter++)
			{
				List<String> list = dataList.get(counter);
				if(isDefineView)
				{
					exportList.add(list);
				}
				else
				{
					List<String> subList = list.subList(0, columnsSize);
					exportList.add(subList);
				}
			}
		}
		else
		{
			for (int counter = 0; counter < obj.length; counter++)
			{
				String index = obj[counter].toString().split("_")[1];
				if(index.length() > 2){
					index = index.substring(1);
				}				
				int id = Integer.parseInt(index);
				List<String> list = dataList.get(id);
				List<String> subList = list.subList(0, columnsSize);
				exportList.add(subList);
			}

		}
		return exportList;
	}
		
	public String getActualFilePath(){
		String filePath = dto.getExportDirPath() + dto.getFileName()+"_"+dto.getCsmUserId()+ AQConstants.CSV_FILE_EXTENSION;
		return filePath;
	}

}
