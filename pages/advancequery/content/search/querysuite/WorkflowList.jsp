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
	mygrid.setEditable("false");
	mygrid.enableAutoHeigth(true);
	mygrid.enableAlterCss("even","uneven");
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

	mygrid.setColSorting(colTypes);
	mygrid.init();


	for(var row=0;row<myData.length;row++)
	{
		mygrid.addRow(row+1,myData[row],row+1);
		mygrid.setRowTextStyle(row+1,"font-family: Arial, Helvetica, sans-serif;font-size: 12px;padding-left:10px;color: #000000;border-left-width: 1px;border-left-color: #CCCCCC;  border-bottom-color: #CCCCCC; border-bottom-color: #CCCCCC; border-right-width: 1px;border-right-color: #CCCCCC");
	}

	//mygrid.setOnRowSelectHandler(funcName);
	//mygrid.setOnRowDblClickedHandler(funcName);
		mygrid.setOnRowSelectHandler(funcName);
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

<logic:notEqual name="totalPages" value="0">
<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="4">
	<tr height="90%">
		<td valign="top">
			<table height="65%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr width="100%" valign="top">					
						  <td class="content_txt_bold">
								<html:form action="RetrieveWorkflowAction">						
								 <table width="100%"  border="0" style="overflow-y:hidden;overflow-x:hidden;">
									<tr height="100%">
										<td valign="top" height="100%">
											<div id='gridbox' width='100%' height="90%" style='overflow:hidden'></div>
										</td>
									</tr>
								</table>
							   </html:form>					
						  </td>
				   </tr>
			</table>
	    </td>
   </tr>
   <tr height="*" valign="bottom">		
			<td colspan="2" class="tr_color_lgrey" valign="bottom" > 
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="tr_color_lgrey">					
						<td class="content_txt_bold" width="200" valign="bottom">						  
								<table>
									<tr>
										<td class="content_txt_bold" style="padding-left:5px;">
											<bean:message key="userSearch.resultsPerPage"/>
										</td>
										<td>
											<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${sessionScope.numResultsPerPage}">
												<html:options collection="resultsPerPageOptions" labelProperty="name" property="value"/>
											</html:select>
										</td>
									</tr>
								</table>
							
						  </td>
						<td class="content_txt_bold" align="center">
							<bean:message key="userSearch.showing"/> ${sessionScope.pageNum} <bean:message key="userSearch.of"/>  <c:out value="${sessionScope.totalPages}"></c:out>
						</td>	
						<td width="15" align="right">
							
							<logic:greaterEqual name="firstPageNum" value="${requestScope.numOfPageNums+1}">	
								<a class="bluelinkNoUnderline" href="RetrieveWorkflowAction.do?requestFor=nextPage&pageOf=${requestScope.pageOf}&pageNum=${requestScope.firstPageNum-requestScope.numOfPageNums}&firstPageNum=${requestScope.firstPageNum-5}&lastPageNum=${requestScope.lastPageNum-5}"> 
									<< 			    						    	
								</a>							
							</logic:greaterEqual>	
						</td>
						<td class="content_txt" width="100" align="center" nowrap>
							<div ID="links">									
								<c:set var="totalPages" value="${sessionScope.totalPages}"/>										 									
								<jsp:useBean id="totalPages" type="java.lang.Integer"/>																														
								<c:forEach var="pageCoutner" begin= "${requestScope.firstPageNum}" end="${requestScope.lastPageNum}">
									<c:set var="linkURL">
										RetrieveWorkflowAction.do?requestFor=nextPage&pageNum=<c:out value="${pageCoutner}"/>&pageOf=<c:out value="${requestScope.pageOf}"/>
									</c:set>
									<jsp:useBean id="linkURL" type="java.lang.String"/>
									<c:if test="${sessionScope.pageNum == pageCoutner}">
									<span class="content_txt_bold">
										<c:out value="${pageCoutner}"/> 
									</span>
									</c:if>
									<c:if test="${sessionScope.pageNum != pageCoutner}">
										<a class="bluelink" href="<%=linkURL%>"><c:out value="${pageCoutner}"/></a>
									</c:if>
									<c:if test="${pageCoutner < requestScope.lastPageNum}">
										|&nbsp;
									</c:if>
								</c:forEach>    
							</div>  
						</td>	
						<td width="15" style="padding-right:5px;"align="right">
							<logic:lessEqual name="lastPageNum" value="${sessionScope.totalPages-1}">
								<a class="bluelinkNoUnderline" href="RetrieveWorkflowAction.do?requestFor=nextPage&pageOf=${requestScope.pageOf}&pageNum=${requestScope.lastPageNum+1}&firstPageNum=${requestScope.firstPageNum+5}&lastPageNum=${requestScope.lastPageNum+5}"> 
						  		  	>> 
								</a>
							</logic:lessEqual>
							
						</td>					
					</tr>
					
		</table>
		</td>
		</tr>
		</table>
</logic:notEqual>
<table width="100%" cellpadding="4" cellspacing="0">
<logic:equal name="totalPages" value="0">
					<td class="content_txt_bold" style="padding-left:5px;" valign="top">
					<bean:message key="meassges.emptyworflow"/>
					</td>	
</logic:equal >
</table>
</body>
</html>