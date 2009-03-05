<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">

<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%-- Imports --%>
<link href="css/cider.css" rel="stylesheet" type="text/css" />
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants, org.apache.struts.Globals"
%>
<%@ page language="java" isELIgnored="false"%>
<%@ page import="org.apache.struts.action.ActionMessages, edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/queryModule.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/ajax.js"></script>

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
var url = editQuery(searchId);
//window.open(url,"_top");
}

window.onload=function loadPageContents()
{
init_grid();
}



function changeResPerPage(controlId)
{
	var resultsPerPage=document.getElementById(controlId).value;
	var url='RetrieveQueryAction.do?pageOf=${requestScope.pageOf}&requestFor=nextPage&pageNum=1&numResultsPerPage='+resultsPerPage;
	document.forms[0].action=url;
	document.forms[0].submit();	
}

</script>
<head>
<title>CIDER: Clinical Investigation Data Exploration Repository</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="JavaScript"></script>
<script type="javascript" src="jss/advancequery/wz_tooltip.js"></script>
<link href="css/advancequery/workflow.css" rel="stylesheet" type="text/css" />
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body>

<% 
boolean mac = false;
Object os = request.getHeader("user-agent");
if(os!=null && os.toString().toLowerCase().indexOf("mac")!=-1)
{
	mac = true;
}
String height = "100%";		
if(mac)
{
  height="500";
}
String message = null; 
String popupMessage = (String)request.getAttribute(Constants.POPUP_MESSAGE);
int queryCount = 0;%>

<html:form action="SaveWorkflow">
<logic:notEqual name="totalPages" value="0">
<table height="90%"width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#cccccc" >
					<tr class="tr_color_lgrey">
						<td class="content_txt_bold" valign="top">						  
								<table width="100%"  border="0" style="overflow-y:hidden;overflow-x:hidden;">
									<tr height="100%">
										<td valign="top" height="100%">
											<div id='gridbox' width='100%' height="90%" style='overflow:hidden'></div>
										</td>
									</tr>
								</table>
							
						  </td>
						</tr>
					</table>
			</tr>
			<tr valign="bottom">
		<td colspan="2" class="tr_color_lgrey" valign="bottom" >

			<table height="*" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="tr_color_lgrey" >					
						<td class="content_txt_bold" width="200">						  
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
								<a class="bluelinkNoUnderline" href="RetrieveQueryAction.do?requestFor=nextPage&pageOf=${requestScope.pageOf}&pageNum=${requestScope.firstPageNum-requestScope.numOfPageNums}&firstPageNum=${requestScope.firstPageNum-5}&lastPageNum=${requestScope.lastPageNum-5}"> 
									<< 			    						    	
								</a>							
							</logic:greaterEqual>	
						</td>
						<td class="content_txt" width="100" align="center" nowrap>
							<div ID="links">
								<c:set var="pageOf" value="${requestScope.pageOf}"/>  
								<jsp:useBean id="pageOf" type="java.lang.String"/>									
								<c:set var="totalPages" value="${sessionScope.totalPages}"/>										 									
								<jsp:useBean id="totalPages" type="java.lang.Integer"/>																														
								<c:forEach var="pageCoutner" begin= "${requestScope.firstPageNum}" end="${requestScope.lastPageNum}">
									<c:set var="linkURL">
										RetrieveQueryAction.do?pageOf=<c:out value="${pageOf}"/>&requestFor=nextPage&pageNum=<c:out value="${pageCoutner}"/>
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
								<a class="bluelinkNoUnderline" href="RetrieveQueryAction.do?requestFor=nextPage&pageOf=${requestScope.pageOf}&pageNum=${requestScope.lastPageNum+1}&firstPageNum=${requestScope.firstPageNum+5}&lastPageNum=${requestScope.lastPageNum+5}"> 
						  		  	>> 
								</a>
							</logic:lessEqual>
							
						</td>					
					</tr>
					
		</table>
</logic:notEqual>
<table width="100%" cellpadding="4" cellspacing="0">
<logic:equal name="totalPages" value="0">
					<td class="content_txt_bold" style="padding-left:5px;" valign="top">
					<bean:message key="meassges.emptyquery"/>
					</td>	
</logic:equal >
</table>
			<html:hidden styleId="queryId" property="queryId" />
		</html:form>
</body>

