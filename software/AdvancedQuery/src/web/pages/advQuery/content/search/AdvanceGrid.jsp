<!-- dataList and columnList are to be set in the main JSP file -->

<%@ page import="java.util.HashMap,java.util.Map,edu.wustl.common.beans.QueryResultObjectData,java.util.List"%>

<%@ page import="edu.wustl.query.util.global.AQConstants"%>

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
<script type="text/javascript" src="jss/advQuery/json2.js"></script>

<style>
	body:nth-of-type(1) div.gridbox_dhx_skyblue table.hdr tr {
		border-radius: 0.3em 0.3em 0.3em 0.3em;
	}
	.dhx_toolbar_base_dhx_skyblue {	
		background-image: url("dhtmlx_suite/imgs/dhxtoolbar_dhx_skyblue/sky_blue_grid1.gif");
		background-repeat: repeat-x;
		border-radius: 0.3em 0.3em 0.3em 0.3em;	
	}
	
	div.gridbox_dhx_skyblue table.obj.row20px tr.rowselected td {
		background-image: url("dhtmlx_suite/imgs/sky_blue_sel_1.png");
	}
</style>

<table width="99.9%" border="0">
	<tr>
		<td>
			<div id='gridbox' border='0' style='background-color:#d9d7d7;'></div>
			<div id="pagingArea" style="border: 1px solid #A4BED4;"></div>
			<div id="hiddenBox"></div>
		</td>
	</tr>	
</table>

<script type="text/javascript">
	var isCheckAllPagesChecked ;
	var gridDataJson = <%=(String) request.getAttribute("gridDataJson")%>;
	<%
		int tempColumnSize = (Integer)session.getAttribute("temporalColumnSize");
		int filterCounts = columnList.size() - tempColumnSize;	
	%>
	
	var colWidth = "<%=(String) request.getAttribute("colWidth")%>";
	var filterCounts = <%= filterCounts %>;
		
	var pageNum = 1;
	var recordPerPage = <%=(String) session.getAttribute(AQConstants.RESULTS_PER_PAGE)%>	
	var gridBOxTag = document.getElementById('gridbox');
	gridBOxTag.style.height = (document.body.clientHeight * 65) / 100 + 'px';
</script>
<script type="text/javascript" src="jss/advQuery/advanceGrid.js"></script>
<script>
	 window.onload = initQueryGrid;	
</script>