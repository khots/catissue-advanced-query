<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/PagenationTag.tld" prefix="custom"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="edu.wustl.query.actionForm.QueryAdvanceSearchForm"%>
<%@ page import="edu.wustl.query.util.global.AQConstants"%>
<%@ page import="edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>
<%@ page language="java" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>

<style>
	.active-column-0 {
		width: 30px
	}
	
	tr#hiddenCombo {
		display: none;
	}
	
	.nav_button {
		border: 1px solid #A4BED4;
		border-radius: 6px;
		padding: 5px 0;
		width: 130px;
		font-size: 11px;
		background-image: -ms-linear-gradient(top, #F2F2F2 20%, #CCCCCC 90%);
		background-image: -moz-linear-gradient(top, #F2F2F2 20%, #CCCCCC 90%);
		background-image: -o-linear-gradient(top, #F2F2F2 20%, #CCCCCC 90%);
		background-image: url('images/list2_add.gif'), -webkit-gradient(linear, left top, left bottom, color-stop(0.2, #F2F2F2), color-stop(0.9, #CCCCCC));
		background-image: -webkit-linear-gradient(top, #F2F2F2 20%, #CCCCCC 90%);
		background-image: linear-gradient(to bottom, #F2F2F2 20%, #CCCCCC 90%);
		//background-repeat: no-repeat;
		//background-position: 10, 10;
	}
</style>

<script src="jss/advQuery/queryModule.js"></script>
<script src="jss/advQuery/script.js"></script>
<script type="text/javascript" src="jss/advQuery/ajax.js"></script>

<%	
	int specIdColumnIndex = 0;	
	String checkAllPages = (String) session.getAttribute("checkAllPages");
	QueryAdvanceSearchForm form = (QueryAdvanceSearchForm) session.getAttribute("advanceSearchForm");
	int totalResult = ((Integer)session.getAttribute(AQConstants.TOTAL_RESULTS)).intValue();
	List dataList = (List) request.getAttribute(AQConstants.PAGINATION_DATA_LIST);
	String pageOf = (String) request.getAttribute(AQConstants.PAGEOF);
	String title = pageOf + ".searchResultTitle";
	boolean isSpecimenData = false;
	int IDCount = 0;
	
	List columnList = new ArrayList((List)session.getAttribute(AQConstants.SPREADSHEET_COLUMN_LIST));
	columnList.add(0, " ");
	
	String idColumnsList = "";
	String idColumnsForSpecimenList = "";
	for(int col = 0; col < columnList.size(); col++)
	{
		if (columnList.get(col).toString().trim().equals("ID")) {
			if(idColumnsList == "") { 
				idColumnsList += col;
			} else {
				idColumnsList += "," + col;
			}
		}
		
		if (columnList.get(col).toString().trim().equals("Specimen : Id")) {
			if(idColumnsForSpecimenList == "") { 
				idColumnsForSpecimenList += col;
			} else {
				idColumnsForSpecimenList += "," + col;
			}
		}		
	}
%>

<script type="text/javascript">
	var isQueryModule = "<%=pageOf.equals(AQConstants.PAGEOF_QUERY_MODULE)%>";
	var checkAllPages = <%=(checkAllPages != null && checkAllPages.equals("true"))%>
	var QueryResultsConfigureTarget = "<%=AQConstants.GRID_DATA_VIEW_FRAME%>";
	
</script>
<script type="text/javascript" src="jss/advQuery/simpleSearchDataView.js"></script>

<%
	String configAction = new String();
	String redefineQueryAction = new String();
	
	if (pageOf.equals(AQConstants.PAGEOF_SIMPLE_QUERY_INTERFACE)) {
		configAction = "onSimpleConfigure()";
		redefineQueryAction = "onRedefineSimpleQuery()";
	} else if (pageOf.equals("pageOfQueryModule")) {
		configAction = "onQueryResultsConfigure()";
		redefineQueryAction = "onRedefineDAGQuery()";
	} else {
		configAction = "onAdvanceConfigure()";
		redefineQueryAction = "onRedefineAdvanceQuery()";
	}
	boolean mac = false;
	Object os = request.getHeader("user-agent");
	
	if (os != null && os.toString().toLowerCase().indexOf("mac") != -1) {
		mac = true;
	}
	String height = "100%";
	if (mac) {
		/* mac gives problem if the values aer specified in percentage*/
		height = "500";
	}
%>
	
	<script language="JavaScript" type="text/javascript" src="jss/advQuery/javaScript.js"></script>
</head>
<body onload="setCheckBoxState();">

<table cellpadding='0' cellspacing='0' border='0' align='center'
		style="width: 100%; height: 100%;">
	<tr height="1%">
		<td width="33%" align="center" class="bgWizardImage">
			<img src="images/advQuery/1_inactive.gif" /> <!-- width="118" height="25" /-->
		</td>
		<td width="33%" align="center" class="bgWizardImage">
			<img src="images/advQuery/2_inactive.gif" /> <!-- width="199" height="38" /-->
		</td>
		<td width="33%" align="center" class="bgWizardImage">
			<img src="images/advQuery/3_active.gif" /> <!--  width="139" height="38" /-->
		</td>
	</tr>	
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
	<logic:equal name="pageOf" value="<%=AQConstants.PAGEOF_SIMPLE_QUERY_INTERFACE%>">
		<tr height="95%"><td>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class=""><span class=""> Simple Query </span></td>
					<td>
						<img src="images/advQuery/uIEnhancementImages/table_title_corner2.gif"
							alt="Page Title - Search Results" width="31" height="24"
							hspace="0" vspace="0" /></td>
				</tr>
			</table></td>
		</tr>
	</logic:equal>
	<tr>
		<td >
		<html:form action="QueryWizard.do" style="margin:0;padding:0;">
			<html:hidden property="checkAllPages" value="" />
				
			<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
				<tr height="1%">
					<td id="errorStatus"><%@ include file="/pages/advQuery/content/common/ActionErrors.jsp"%></td>
				</tr>
				<%
					if (dataList == null && pageOf.equals(AQConstants.PAGEOF_QUERY_RESULTS)) {
				%>
					<tr><td><bean:message key="advanceQuery.noRecordsFound" /></td></tr>					
				<%
					} else if (dataList != null && dataList.size() != 0) {
												
						if (pageOf.equals(AQConstants.PAGEOF_QUERY_RESULTS)) {
								String[] selectedColumns = form.getSelectedColumnNames();
				%>

				<tr id="hiddenCombo" rowspan="2" >
					<td class="black_new"><!-- Mandar : 434 : for tooltip --> 
						<html:select property="selectedColumnNames" styleClass="selectedColumnNames"
							size="1" styleId="selectedColumnNames" multiple="true"
							onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
						<%
							for (int j = 0; j < selectedColumns.length; j++) {
						%>
							<html:option value="<%=selectedColumns[j]%>"><%=selectedColumns[j]%></html:option>
						<%
							}
						%>
					</html:select></td>
				</tr>
				<%
						}
				%>

				<tr width="100%">
					<td width="100">									
						<%@ include file="/pages/advQuery/content/search/AdvanceGrid.jsp"%>
					</td>
				</tr>

				<tr width="100%">
					<td>
					<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
						<tr>			
							<%
								if(!idColumnsForSpecimenList.equals("")) {
							%>
							<td width="12%">									
								<button type="button" class="nav_button" style="width: 145px" onclick="addToSpecimenList()">
										<img src="images/advQuery/list2_add.gif" style="margin-right: 5px"/>
										Add To Specimen List</button>
							</td>
							
							<%
								} else {								
							%> 	
								<td width="10%">									
									<button type="button" class="nav_button" id="addToListImgId" onclick="getData()">
										<img src="images/advQuery/list2_add.gif" style="margin-right: 5px"/>
										Add To My List</button>
								</td>
							<% 	} %> 							 
							
							<td width="10%">
								<button type="button" class="nav_button" id="exportImgId" onclick="onExport()">
									<img src="images/advQuery/excel.gif" style="margin-right: 5px"/>
									Export CSV</button></td>								
							<td>&nbsp;</td>
							<td width="10%">
								<input type="button" value="Define View" id="defineViewId"
									onclick="<%=configAction%>" class="nav_button"></td>
							<td width="10%">
								<input type="button" value="Redefine Query" id="redefineQueryId"
									onclick="<%=redefineQueryAction%>" class="nav_button"></td>
							<td width="10%">
								<input type="button" value="Save" id="saveResultImgId"
									onclick="saveClientQueryToServer('save')" class="nav_button"></td>							
						</tr>
					</table>
					</td>
				</tr>
				<%
					}
				%>
				<tr>
					<td>
						<html:hidden property="operation" value="" />
						<input type="hidden" name="isQuery" value="true">
					</td>
				</tr>
			</table>			
		</html:form>
	</td></tr>
</table>
		

<!---------------------------------------------------->
<div id="blanket" style="display: none;"></div>
<div id="popUpDiv" style="display: none; top: 100px; left: 210.5px;">
	<a onclick="popup('popUpDiv')">
		<img style="float: right;" height='23' width='24' src='images/close_button.gif'
		border='0'> </a>
	<table class=" manage tags" width="100%" cellspacing="0"
		cellpadding="5" border="0">
		<tbody>
			<tr valign="center" height="35" bgcolor="#d5e8ff">
				<td width="28%" align="left"
					style="font-size: .82em; font-family: verdana;">
					<p>&nbsp&nbsp&nbsp&nbsp<b> Specimen Lists</b></p>
				</td>
			</tr>
		</tbody>
	</table>

	<div id="treegridbox" style="width: 530px; height: 237px; background-color: white;"></div>
	
	<p>	&nbsp&nbsp&nbsp	
		<label width="28%" align="left" style="font-size: .82em; font-family: verdana;" >
			<b> List Name : </b> </label> 
		<input  type="text"  id="newTagName"  name="newTagName" 
			size="20" onclick="this.value='';"  maxlength="50"  /> <br />
	</p>
	<p>
		<input type="button" value="ASSIGN" onclick="assignToSpecimenList()" class="btn3">
		<input type="checkbox" name="objCheckbox"  id="objCheckbox" style="display:none" value="team" checked/>
	</p>
</div>
