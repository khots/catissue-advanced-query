package edu.wustl.query.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.util.global.CommonServiceLocator;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.query.util.querysuite.QueryDetails;


public class QueryExportDTO {
	
	//
	// catissue properties directory path  
	//
	public String appPath = CommonServiceLocator.getInstance().getPropDirPath();
	 
	//
	// Directory where export files are stored
	//
	public String exportDirPath =  appPath + System.getProperty("file.separator") +"reports"+ System.getProperty("file.separator");
	
	//
	// contains UUID 
	//
	public String fileName = null;
	
	//
	// User Csm Id
	//
	public String csmUserId = null;
	
	//
	// Query Name
	//
	public String queryName = null;
	
	//
	// Contains informative data like Title, Export Date, Query Limits etc, further added to .csv file
	//
	public List<List<String>> exportList = new ArrayList<List<String>>();
		
	//
	// Contains session data required for query execution like sql,recordPerPage etc 
	//
	public QuerySessionData querySessionData = null;
	
	//
	// Contains details for query like IQuery,IsNormalizedForm,OutputTerms etc   
	//
	
	public QueryDetails queryDetails = null;
 	
	//
	// Column List
	//
	public List<String> columnList = new ArrayList<String>();
		
	//
	// Specifies is Simple Search
	//
	public String simpleSearch;
	
	//
	// boolean for export all data
	//
	public boolean exportAll =  false;
 
	//
	// contains values from form (QueryAdvanceSearchForm);
	//
	public Map formValues = null;
	
	//
	// required for modify SQL
    //
	public List<OutputTreeDataNode> rootOutputTreeNodeList = new ArrayList<OutputTreeDataNode>();
	
	//
	// Specifies is Defined View
	//
	public boolean isDefinedView = false;
		
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getCsmUserId() {
		return csmUserId;
	}
	
	public void setCsmUserId(String csmUserId) {
		this.csmUserId = csmUserId;
	}
	
	public List<List<String>> getExportList() {
		return exportList;
	}
	
	public void setExportList(List<List<String>> exportList) {
		this.exportList = exportList;
	}

	public QuerySessionData getQuerySessionData() {
		return querySessionData;
	}

	public void setQuerySessionData(QuerySessionData querySessionData) {
		this.querySessionData = querySessionData;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public QueryDetails getQueryDetails() {
		return queryDetails;
	}

	public void setQueryDetails(QueryDetails queryDetails) {
		this.queryDetails = queryDetails;
	}

	public String isSimpleSearch() {
		return simpleSearch;
	}

	public void setSimpleSearch(String simpleSearch) {
		this.simpleSearch = simpleSearch;
	}

	public List<OutputTreeDataNode> getRootOutputTreeNodeList() {
		return rootOutputTreeNodeList;
	}

	public void setRootOutputTreeNodeList(
			List<OutputTreeDataNode> rootOutputTreeNodeList) {
		this.rootOutputTreeNodeList = rootOutputTreeNodeList;
	}

	public boolean isExportAll() {
		return exportAll;
	}

	public void setExportAll(boolean exportAll) {
		this.exportAll = exportAll;
	}

	public String getExportDirPath() {
		return exportDirPath;
	}

	public void setExportDirPath(String exportDirPath) {
		this.exportDirPath = exportDirPath;
	}
	
	public Map getFormValues() {
		return formValues;
	}

	public void setFormValues(Map formValues) {
		this.formValues = formValues;
	}

	public boolean isDefinedView() {
		return isDefinedView;
	}

	public void setDefinedView(boolean isDefinedView) {
		this.isDefinedView = isDefinedView;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
}
