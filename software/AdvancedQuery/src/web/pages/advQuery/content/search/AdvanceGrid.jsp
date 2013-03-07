<!-- dataList and columnList are to be set in the main JSP file -->

<%@ page import="java.util.HashMap,java.util.Map,edu.wustl.common.beans.QueryResultObjectData,java.util.List"%>

<%@ page import="edu.wustl.query.util.global.AQConstants"%>
<%@ page import="edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>

<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/css/dhtmlxgrid.css">
<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/ext/dhtmlxgrid_pgn_bricks.css">
<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/dhtml_pop/css/dhtmlxgrid_dhx_skyblue.css" />
<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/skins/dhtmlxtoolbar_dhx_skyblue.css" />

<link rel="stylesheet" type="text/css" href="css/advQuery/catissue_suite.css" />
<link rel="stylesheet" type="text/css" href="css/tag-popup.css" />
<link rel="STYLESHEET" type="text/css" href="css/dhtmlDropDown.css">

<script type="text/javascript" src="dhtmlx_suite/dhtml_pop/js/dhtmlx.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxcommon.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxgrid.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="dhtmlx_suite/js/dhtmlxtoolbar.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_filter.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_pgn.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_splt.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="dhtmlx_suite/ext/dhtmlxgrid_mcol.js"></script>
<script type="text/javascript" src="dhtmlx_suite/dhtml_pop/js/dhtmlXTreeGrid.js"></script>

<script type="text/javascript" src="jss/tag-popup.js"></script>
<script type="text/javascript" src="jss/dhtmlDropDown.js"></script>
<script type="text/javascript" src="jss/javaScript.js"></script>
<script type="text/javascript" src="jss/caTissueSuite.js"></script>

<style>
body:nth-of-type(1) div.gridbox_dhx_skyblue table.hdr tr {
	background: -ms-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: -moz-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: -o-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0, #F7F7F7), color-stop(1, #D6D6D6));
	background: -webkit-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: linear-gradient(to bottom, #F7F7F7 0%, #D6D6D6 100%);
	border-radius: 0.3em 0.3em 0.3em 0.3em;
}
.dhx_toolbar_base_dhx_skyblue {
	background: -ms-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: -moz-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: -o-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0, #F7F7F7), color-stop(1, #D6D6D6));
	background: -webkit-linear-gradient(top, #F7F7F7 0%, #D6D6D6 100%);
	background: linear-gradient(to bottom, #F7F7F7 0%, #D6D6D6 100%);
	border-radius: 0.3em 0.3em 0.3em 0.3em;
	
}
</style>

<script type="text/javascript">
<%
	int totalResults = ((Integer) session.getAttribute(AQConstants.TOTAL_RESULTS)).intValue();
	int numResultsPerPage = Integer.parseInt((String) session.getAttribute(AQConstants.RESULTS_PER_PAGE));
	
	
	Map hyperlinkColumnMap = (Map)session.getAttribute(AQConstants.HYPERLINK_COLUMN_MAP);
	if (hyperlinkColumnMap==null)
		hyperlinkColumnMap = new HashMap();
		
%>	
	var jsonData = {rows:[
						<%
							for (int i=0; i < dataList.size(); i++){
								List row = (List)dataList.get(i);
								
								if(i != 0) {out.print(",");}
								
								out.print("{id: "+ i +", data:[");
								
								for (int j = 0; j < row.size(); j++){									
									out.print(",\""+ Utility.toNewGridFormatWithHref(row,hyperlinkColumnMap,j)+"\"") ;
								}				
								out.print("]}");
							}								
						%> 							
					]}
	var isCheckAllPagesChecked ;
	
	var columns = "<%=columnList.get(0)%><%for(int col = 1; col < columnList.size(); col++){%>,<%=columnList.get(col)%><%}%>";
	var filters = "#master_checkbox<%for(int col = 1; col < columnList.size(); col++){%>,#text_filter<%}%>"
	var colWidth = "<%=Utility.getColumnWidth(columnList)%>";
	var colTypes = "<%=Variables.prepareColTypes(dataList, true)%>";
	var colDataTypes = colTypes;

	while(colDataTypes.indexOf("str") !=-1)
	{
		colDataTypes = colDataTypes.replace(/str/,"ro");
	}

	function getIDColumns()
	{	
		var hiddenColumnNumbers = [<%=idColumnsList%>];
		return hiddenColumnNumbers;
	}
	function isIDColumnsForSpecimen()
	{		
		var hiddenColumnNumbers = [<%=idColumnsForSpecimenList%>];
		return hiddenColumnNumbers;
	}
	function getIDColumnsForSpecimen()
	{		
		var hiddenColumnNumbers = [<%=idColumnsForSpecimenList%>];
		return hiddenColumnNumbers;
	}
	
</script>

<table width="100%" valign="top" border="0" height="100%" >
	<tr valign="top">
		<td valign="top">
			<div id='gridbox'  border='0' style='height:100%; background-color:#d9d7d7;' valign="top">
			</div>
			<div id="pagingArea" style="border: 1px solid #A4BED4;"></div>
			<div id="hiddenBox"></div>
		</td>
	</tr>	
</table>

<script type="text/javascript">
	var pageNum = 1;
	var recordPerPage = <%=numResultsPerPage%>	
	var gridBOxTag = document.getElementById('gridbox');
	gridBOxTag.style.height = (document.body.clientHeight * 65) / 100 + 'px';
		
</script>
<script type="text/javascript" src="jss/advQuery/advanceGrid.js"></script>
<script>
	initQueryGrid();
</script>

