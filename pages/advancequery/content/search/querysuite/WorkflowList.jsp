<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/PagenationTag.tld" prefix="custom" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.List,edu.wustl.cider.util.global.Utility,edu.wustl.cider.util.global.CiderConstants"%>
<%@ page import="edu.wustl.common.util.global.Constants"%>
<%@ page language="java" isELIgnored="false"%>
<%@ page import="edu.wustl.query.domain.Workflow"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="css/cider.css" rel="stylesheet" type="text/css" />

<!-- dataList and columnList are to be set in the main JSP file -->
<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXGrid.css"/>
<script  src="dhtml_comp/js/dhtmlXCommon.js"></script>
<script  src="dhtml_comp/js/dhtmlXGrid.js"></script>		
<script  src="dhtml_comp/js/dhtmlXGridCell.js"></script>	
<script  src="dhtml_comp/js/dhtmlXGrid_mcol.js"></script>

	
<script>

	var myData =${requestScope.msgBoardItemList};
	var columns =${requestScope.columns};
	var colWidth =${requestScope.colWidth};
	var isWidthInPercent=${requestScope.isWidthInPercent};
	var colTypes =${requestScope.colTypes};
	var colDataTypes =${requestScope.colDataTypes};

function init_grid()
{			
	var funcName = "rowClick";

	mygrid = new dhtmlXGridObject('gridbox');
	mygrid.setImagePath("dhtml_comp/imgs/");
	mygrid.setStyle("font-family: Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold;color: #000000;background-color: #E2E2E2; border-left-width: 1px;border-left-color: #CCCCCC; border-top-width: 1px;border-top-color: #CCCCCC; border-bottom-color: #CCCCCC; border-top-width: 1px;border-bottom-color: #CCCCCC; border-right-width: 1px;border-right-color: #CCCCCC; text-align:left;padding-left:10px;");
	mygrid.setHeader(columns);
	mygrid.setEditable("true");
	mygrid.enableAutoHeigth(true);
	
	//document.write("<hr>"+colWidth+"<hr>");
    mygrid.enableRowsHover(true,'grid_hover')

	if(isWidthInPercent)
	{
		mygrid.setInitWidthsP(colWidth+",0");
	}
	else
	{
		mygrid.setInitWidths(colWidth);
	}

	mygrid.setColTypes(colDataTypes);
//	mygrid.setSkin("light");
	mygrid.enableAlterCss("even","uneven");
	//mygrid.setColAlign("left,left")
	mygrid.setColSorting(colTypes);
	//colTypes = "\"ch,str,int\"";
	
	//mygrid.enableMultiselect(true)
	mygrid.init();

	/*
	mygrid.loadXML("dhtmlxgrid/grid.xml");
	// clears the dummy data and refreshes the grid.
	// fix for grid display on IE for first time.
	mygrid.clearAll();
	*/

	for(var row=0;row<myData.length;row++)
	{
		mygrid.addRow(row+1,myData[row],row+1);
		mygrid.setRowTextStyle(row+1,"font-family: Arial, Helvetica, sans-serif;font-size: 12px;padding-left:10px;color: #000000;background-color: #FFFFFF;border-left-width: 1px;border-left-color: #CCCCCC;  border-bottom-color: #CCCCCC; border-bottom-color: #CCCCCC; border-right-width: 1px;border-right-color: #CCCCCC");
	}

	//mygrid.setOnRowSelectHandler(funcName);
	mygrid.setOnRowDblClickedHandler(funcName);
	// :To hide ID columns by kalpana
	function getIDColumns()
		{
			var hiddenColumnNumbers = new Array();
			hiddenColumnNumbers[0]=${requestScope.identifierFieldIndex};
			return hiddenColumnNumbers;
		}
	
	
	// :To hide ID columns
		var hideCols = getIDColumns();
		for(i=0;i<hideCols.length;i++)
		{
			mygrid.setHeaderCol(hideCols[i],"");
			mygrid.setColumnHidden(hideCols[i],true);
		}
	mygrid.setSizes();
}

function rowClick(id)
{
	var colid ='${requestScope.identifierFieldIndex}';
	var cl = mygrid.cells(id,colid);
	var searchId = cl.getValue();
	var url = "SearchObject.do?pageOf=pageOfWorkflow&operation=search&id="+searchId;
	window.open(url,"_top");
}

window.onload=function loadPageContents()
{
	init_grid();
}



function changeResPerPage(controlId)
{
	var pageOf ='${requestScope.pageOf}';
	var resultsPerPage=document.getElementById(controlId).value;
	var url='RetrieveWorkflowAction.do?requestFor=nextPage&pageNum=1&numResultsPerPage='+resultsPerPage+'&pageOf='+pageOf;
	document.forms[0].action=url;
	document.forms[0].submit();	
}


</script>
</head>
<body>
<html:form action="RetrieveWorkflowAction">


	
     <table width="100%"  border="0" style="overflow-y:hidden;overflow-x:hidden;">
		<tr height="100%">
			<td valign="top" height="100%">
				<div id='gridbox' width='100%' height="90%" style='overflow:hidden'></div>
			</td>
		</tr>
	</table>
      
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr class="tr_color_lgrey">
				<td align="left"  height="30" style="padding-left:5px;"><span class="content_txt_bold">Show Last:&nbsp;</span>
					<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${sessionScope.numResultsPerPage}" styleClass="textfield_undefined">
						<html:options collection="resultsPerPageOptions" labelProperty="name" property="value"/>
					</html:select>
				</td>
				<td align="right" class="content_txt" style="padding-right:5px;">
					<c:set var="totalPages" value="${sessionScope.totalPages}"/>  
						<jsp:useBean id="totalPages" type="java.lang.Integer"/>
					<c:forEach var="pageCoutner" begin="1" end="${totalPages}">
							<c:set var="linkURL">
								RetrieveWorkflowAction.do?requestFor=nextPage&pageNum=<c:out value="${pageCoutner}"/>&pageOf=<c:out value="${requestScope.pageOf}"/>
							</c:set>
							<jsp:useBean id="linkURL" type="java.lang.String"/>
							<c:if test="${sessionScope.pageNum == pageCoutner}">
									<c:out value="${pageCoutner}"/> 
							</c:if>
							<c:if test="${sessionScope.pageNum != pageCoutner}"> | <a class="bluelink" href="<%=linkURL%>"><c:out value="${pageCoutner}"/></a>
							</c:if>
						</c:forEach></td></tr></table>
</html:form>
</body>
</html>