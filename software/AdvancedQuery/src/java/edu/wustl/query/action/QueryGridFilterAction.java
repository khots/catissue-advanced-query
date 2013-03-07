package edu.wustl.query.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.common.dynamicextensions.domain.AttributeTypeInformation;
import edu.common.dynamicextensions.domain.BooleanAttributeTypeInformation;
import edu.common.dynamicextensions.domain.DateAttributeTypeInformation;
import edu.common.dynamicextensions.domain.NumericAttributeTypeInformation;
import edu.common.dynamicextensions.domain.StringAttributeTypeInformation;
import edu.common.dynamicextensions.domaininterface.AttributeInterface;
import edu.wustl.common.action.SecureAction;
import edu.wustl.common.query.queryobject.impl.OutputTreeDataNode;
import edu.wustl.common.query.queryobject.impl.metadata.QueryOutputTreeAttributeMetadata;
import edu.wustl.common.query.queryobject.impl.metadata.SelectedColumnsMetadata;
import edu.wustl.common.util.global.QuerySessionData;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.bizlogic.QueryOutputSpreadsheetBizLogic;
import edu.wustl.query.bizlogic.SpreadsheetDenormalizationBizLogic;
import edu.wustl.query.util.global.AQConstants;
import edu.wustl.query.util.global.Utility;
import edu.wustl.query.util.querysuite.QueryDetails;
import edu.wustl.query.util.querysuite.QueryModuleUtil;

public class QueryGridFilterAction extends SecureAction
{
	private static final Logger LOGGER = Logger.getCommonLogger(QueryGridFilterAction.class);
	
	@SuppressWarnings("unchecked")
	@Override
	protected ActionForward executeSecureAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();	
		int  pageNum = Integer.parseInt ((String)request.getParameter("pageNum"));
		int recordsPerPage = Integer.parseInt((String)request.getParameter("recordPerPage"));
		
		SelectedColumnsMetadata selectedColMetadata =(SelectedColumnsMetadata)session
															.getAttribute(AQConstants.SELECTED_COLUMN_META_DATA);
				
		QuerySessionData querySessionData = (QuerySessionData) session
							.getAttribute(edu.wustl.common.util.global.Constants.QUERY_SESSION_DATA);
		QueryDetails queryDetailsObj = new QueryDetails(session);
		String oldquery = querySessionData.getSql();		
		querySessionData.setSql(getQuery(session, request, oldquery));
				
		boolean isContPresent = new QueryOutputSpreadsheetBizLogic().isContainmentPresent(queryDetailsObj.getQuery());;		
				
		List dataList = Utility.getPaginationDataList(request, getSessionData(request),
				recordsPerPage, pageNum, querySessionData, selectedColMetadata.isDefinedView());
		
		if(isContPresent && queryDetailsObj.getQuery().getConstraints().size() != 1 
				&& !queryDetailsObj.getQuery().getIsNormalizedResultQuery())
		{
			SpreadsheetDenormalizationBizLogic  denormalizationBizLogic = new SpreadsheetDenormalizationBizLogic();
			Map<String,Object> exportDetailsMap = denormalizationBizLogic.scanIQuery
														(queryDetailsObj, dataList, selectedColMetadata, querySessionData);
			if(!exportDetailsMap.isEmpty())
			{
				dataList = (List<List<String>>)exportDetailsMap.get("dataList");
				List<String>colList = (List<String>)exportDetailsMap.get("headerList");
				session.setAttribute(AQConstants.SPREADSHEET_COLUMN_LIST, colList);
			}
		}
		
		session.setAttribute(AQConstants.TOTAL_RESULTS, querySessionData.getTotalNumberOfRecords());
		createAndWriteJsonInResponse(dataList, pageNum, recordsPerPage, response, session);
		
		// set original query
		querySessionData.setSql(oldquery);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private String getQuery(HttpSession session, HttpServletRequest request, String oldquery)
	{		
		StringBuilder query = new StringBuilder();
		query.append(oldquery.substring(0, oldquery.lastIndexOf("where") + 6));
		try {
			String jsonString = (String) request.getParameter("Data");			
			JSONObject jsonObj = new JSONObject(jsonString);
			JSONArray columns = jsonObj.getJSONArray("columns");
			JSONArray values = jsonObj.getJSONArray("values");
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			for(int i = 0; i < columns.length(); i++) {
				paramsMap.put(columns.getString(i).split("_")[0], values.getString(i));
			}
			
			List<OutputTreeDataNode> rootOutputTreeNodeList = (List<OutputTreeDataNode>) session
								.getAttribute(AQConstants.SAVE_TREE_NODE_LIST);
			List<QueryOutputTreeAttributeMetadata> attributes = new ArrayList<QueryOutputTreeAttributeMetadata>();
			
			for(OutputTreeDataNode node: rootOutputTreeNodeList) { 
				attributes.addAll(node.getAttributes());
				for(OutputTreeDataNode child: QueryModuleUtil.getInViewChildren(node)) {
					attributes.addAll(child.getAttributes());
				}
			}
						
			for(QueryOutputTreeAttributeMetadata attr: attributes){
				AttributeTypeInformation type = (AttributeTypeInformation) attr.getAttribute().getAttributeTypeInformation();
				String value = paramsMap.get(attr.getDisplayName());
				if(value != null && !value.equals("")){
					if(type instanceof StringAttributeTypeInformation) {
						query.append(attr.getColumnName()).append(" LIKE '")
							.append(value).append("%' and ");
						
					} else if(type instanceof NumericAttributeTypeInformation ||
								type instanceof BooleanAttributeTypeInformation){
						query.append(attr.getColumnName()).append(" = ")
							.append(value).append(" and ");						
					} else if(type instanceof DateAttributeTypeInformation) {
						
					}
				}
			}	
			
			query.append(oldquery.substring(oldquery.lastIndexOf("where")+ 5));			
			
			LOGGER.out.error("New query : "+ query.toString());
		} catch(Exception e ){
			LOGGER.out.error("Exception found while creating query :", e);
		}
		return query.toString();
	}
	
	private void createAndWriteJsonInResponse(List dataList, int pageNum, int recordsPerPage, 
			HttpServletResponse response, HttpSession session) throws IOException {
		Map hyperlinkColumnMap = (Map)session.getAttribute(AQConstants.HYPERLINK_COLUMN_MAP);
		if (hyperlinkColumnMap == null) {
				hyperlinkColumnMap = new HashMap();
		}
		List<String> columnsList = (List<String>) session.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST);
		StringBuilder  columns = new StringBuilder();
		columns.append("columns: [");
		for(String column: columnsList) {
			columns.append(",\"").append(column).append("\"");
		}
		columns.append("]");		
		
		int totalResult = ((Integer) session.getAttribute(AQConstants.TOTAL_RESULTS)).intValue();
		int pos = recordsPerPage * (pageNum - 1);
		StringBuilder  jsonData = new StringBuilder();
		jsonData.append("data: {total_count: ")
				.append(totalResult)
				.append(",\n pos: ")
				.append(pos)
				.append(",\n rows: [");
		
		
		for (int i = 0; i < dataList.size(); i++){
			List row = (List)dataList.get(i);
			if(i != 0) {
				jsonData.append(", ");
			}
			
			jsonData.append("{id : ").append(pos + i).append(", data:[0");
			
			for (int j = 0; j < row.size(); j++){	
				jsonData.append(",\"")
						.append(Utility.toNewGridFormatWithHref(row, hyperlinkColumnMap, j))
						.append("\"");
			}
			jsonData.append("]}") ;
		}
		jsonData.append("]}");
				
		response.getWriter().write("{"+ columns.toString() + ", "+ jsonData.toString() + "}");
	}
}
