<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">

<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%

	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
%>
<%-- Imports --%>
<link href="css/cider.css" rel="stylesheet" type="text/css" />
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants, org.apache.struts.Globals"
%>
<%@ page language="java" isELIgnored="false"%>
<%@ page import="org.apache.struts.action.ActionMessages, edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.common.querysuite.queryobject.impl.AbstractQuery"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<script>
function closePopup()
{
	var checkboxArray=document.getElementsByName('chkbox');
	clearSelBoxList(parent.window.document.getElementById("queryId"));
	clearSelBoxList(parent.window.document.getElementById("queryTitle"));
	clearSelBoxList(parent.window.document.getElementById("queryType"));


	var isOptionAdded=false;

	if(checkboxArray!=null)
	{
			var numOfRows =checkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
					var alreadyExist=false;
					var id = 'checkbox_'+count;
					
				if(document.getElementById(id).checked)
				{
					//checks that query already exists in parent window
					if(checkAlreadyPresent(document.getElementById("queryIdControl_"+count).value)==false)
					{
						addOption(parent.window.document.getElementById("queryId"),document.getElementById("queryIdControl_"+count).value,document.getElementById("queryIdControl_"+count).value);

						addOption(parent.window.document.getElementById("queryTitle"),""+document.getElementById("queryTitleControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
						addOption(parent.window.document.getElementById("queryType"),""+document.getElementById("queryTypeControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
       
						  //add selected count query to dropdowns of parent window
					/*	if((document.getElementById("queryTypeControl_"+count).value)=="GetCount")
					  {
						 var countQDD= parent.window.document.getElementsByName("countQueryDropDown");
						  for(var i=0;i<countQDD.length;i++) 
						 {
							  addOption(countQDD[i],document.getElementById("queryTitleControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
						 }
					  } */
						isOptionAdded=true;
					}
					else
					{
						alert("Query with title "+(document.getElementById("queryTitleControl_"+count).value)+" already in present in workflow");
					}
				}	
			}
	}
	if(isOptionAdded==true)
	{
		updateOpenerUI();
		closeModalWindow();
	}

}

function closeModalWindow()
{
	parent.pvwindow.hide();

}
function clearSelBoxList(selBoxObj)
{	
	if(selBoxObj != null)
	 {
		while(selBoxObj.length > 0)
		 {
			selBoxObj.remove(selBoxObj.length - 1);
		  }
	  }
}


function updateOpenerUI() { 
  if(document.getElementById) { 
         elm = parent.window.document.getElementById("btn");
       } 

	if (document.all)
		{
			elm.fireEvent('onclick');
		}
	else
		{
		var clickEvent = window.document.createEvent('MouseEvent');
        clickEvent.initEvent('click', false, true);
        elm.dispatchEvent(clickEvent);
		}
     } 



function selectAllOptions(control)
	{
		for(i=control.options.length-1;i>=0;i--)
		{
			control.options[i].selected=true;
		}	
	}

function addOption(theSel, theText, theValue)
{
	var optn = parent.window.document.createElement("OPTION");
	optn.text=theText;
	optn.value=theValue;
	theSel.options.add(optn);	
}

function selectAllCheckboxes()
{ 
	//alert("checkboxControl.checked");
	var checkboxControl=document.getElementById("selectAllCheckbox");
	//alert("checkboxControl.checked"+checkboxControl.checked);
	if(checkboxControl!=null && checkboxControl!=undefined)// && checkboxControl.checked==true)
	{
			var checkboxArray=document.getElementsByName('chkbox');
						var numOfRows =checkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
				
					document.getElementById( 'checkbox_'+count).checked=checkboxControl.checked;		
			}

	}
	/*if(checkboxControl!=null && checkboxControl!=undefined && checkboxControl.checked==false)
	{
			var checkboxArray=document.getElementsByName('chkbox');
						var numOfRows =checkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
				
					document.getElementById( 'checkbox_'+count).checked=false;
			}

	}*/

}
function checkAlreadyPresent(itemToadd)
{

/*	var noOfRows=parent.window.document.getElementById("table1").rows.length;
				alert("noOfRows"+noOfRows);
	var numOfRows2 =document.getElementsByName('chkbox').length;
	alert("numOfRows2"+numOfRows2);
		for(var idCount=0;idCount<noOfRows; idCount++)
		{

			for(var idCount2=0;idCount2<numOfRows2; idCount2++)
			{
					
				alert(document.getElementById("queryTitleControl_"+idCount2));
				var itemToadd=document.getElementById("queryTitleControl_"+idCount2);
				alert("itemToadd"+itemToadd);
				if(parent.window.document.getElementById('selectedqueryId_'+idCount).value==itemToadd)
				{	
					
				}	
			}
		}	*/
		
	var noOfRows=parent.window.document.getElementById("table1").rows.length
	var alreadyExist=false;

		for(var idCount=0;idCount<noOfRows; idCount++)
		{
			if(parent.window.document.getElementById('selectedqueryId_'+idCount).value==itemToadd)
			{	
				alreadyExist=true;
			}	
		}	
		return alreadyExist;
}
function changeResPerPage(controlId)
{
	var resultsPerPage=document.getElementById(controlId).value;
	var url='RetrieveQueryAction.do?pageOf=myQueriesForWorkFlow&requestFor=nextPage&pageNum=1&numResultsPerPage='+resultsPerPage;
	document.forms[0].action=url;
	document.forms[0].submit();	
}

function retrieveMyQueries(pageOf)
{
	var url='RetrieveQueryAction.do?pageOf='+pageOf+'&requestFor=nextPage&pageNum=1';
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
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<tr>
	<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="login_box_bg">
			<tr >
				<td width="20%" align="left" valign="top" background="images/advancequery/bg_content_header.gif"><img src="images/advancequery/t_explore.gif" alt="Explore" width="64" height="26" valign="absmiddle"></td>

				<td valign="top" background="images/advancequery/bg_content_header.gif">
				<c:set var="query_type_data" value="myQueriesForWorkFlow"/>
				 <c:set var="qtype" value="${requestScope.pageOf}"/>
				  <logic:equal name="query_type_data" value="${qtype}">
					<img src="images/advancequery/t_myqueries.gif" alt="My Queries " width="99" height="26" valign="absmiddle">
				  </logic:equal>
				   <logic:notEqual name="query_type_data" value="${qtype}">
						<img src="images/advancequery/t_shared_queries.gif" alt="My Queries " width="125" height="26" valign="absmiddle">
				   </logic:notEqual>
				</td>

			</tr>
			<tr>
				<td valign="top" style="border-right:1px solid #DDDDDD; border-bottom:1px solid #DDDDDD;" height="300px">
					<table width="99%" border="0" cellpadding="2" cellspacing="0">
					<tr>
							<td width="2" >&nbsp;</td>
							<td colspan="2" valign="bottom" class="blue_title">Queries</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td width="18" align="left" valign="top"><img src="images/advancequery/ic_folder.gif" alt="explore" width="16" height="16"  align="absmiddle">
							</td>
							<td valign="top"  class="content_txt"><a href="javascript:retrieveMyQueries('myQueriesForWorkFlow')" class="blacklink"><bean:message key="workflow.myqueries"/> 
						</a>&nbsp;(${requestScope.myQueriesCount})</td>
						</tr>
						<tr>
							<td >&nbsp;</td>
							<td align="left" valign="top"><img src="images/advancequery/ic_folder.gif" alt="explore" width="16" height="16"  align="absmiddle">
							
							</td>
							<td valign="top"  class="content_txt"><a href="javascript:retrieveMyQueries('sharedQueriesForWorkFlow')" class="blacklink"><bean:message key="workflow.sharedqueries"/></a>&nbsp;(${requestScope.sharedQueriesCount})</td>
						</tr>
					</table>		
				</td>
				<td style="border-bottom:1px solid #DDDDDD;" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="4">
						<tr>
						
						<td align="left" valign="middle"><!--&nbsp;<a href="javascript:showNextReleaseMsg()" class="bluelink">Delete</a>&nbsp;<span class="content_txt">|</span>&nbsp;--><a href="javascript:closePopup()" class="bluelink"><bean:message key="workflow.add"/></a></td>
						<td align="right">
							<table border="0" cellspacing="0" cellpadding="2">
								<tr>
								<!--    <td class="content_txt"><bean:message key="workflow.show"/></td>
								<td><select name="select" class="textfield_undefined" disabled="true">
									<option>All</option>
									<option>Get Count</option>
									<option>Get Patient Data</option>
								  </select></td>-->
								</tr>
						  </table>
						</td>
						</tr>
					</table>

					<table width="100%" border="0" cellspacing="0" cellpadding="4">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#EAEAEA">
									  <tr class="td_bgcolor_grey">
										<td width="10" height="25" valign="middle" ><input id="selectAllCheckbox"   type="checkbox" name="checkbox8" value="checkbox" onClick="javascript:selectAllCheckboxes()">                 
										</td>

										<td valign="middle" class="grid_header_text"><bean:message key="workflow.queryTitle"/></td>
										<td width="111" valign="middle" class="grid_header_text"><bean:message key="workflow.querytype"/></td>
										</tr>
											<div  id="searchDiv">
													<c:set var="parameterizedQueryCollection" value="${saveQueryForm.parameterizedQueryCollection}" />
													<jsp:useBean id="parameterizedQueryCollection" type="java.util.Collection" />
											
													<c:forEach items="${parameterizedQueryCollection}" var="parameterizedQuery" varStatus="queries">
													<jsp:useBean id="parameterizedQuery" type="edu.wustl.common.querysuite.queryobject.impl.AbstractQuery" />
													

															<%String target = "executeQuery('"+parameterizedQuery.getId()+"')"; 
															  String queryId=parameterizedQuery.getId()+"";
															  String title = parameterizedQuery.getName();
															  String newTitle = Utility.getQueryTitle(title);
															  String queryType=
															  parameterizedQuery.getType();
															  String tooltip = Utility.getTooltip(title);
															  String function = "Tip('"+tooltip+"', WIDTH, 700)";
															  queryCount++;
															%>
															<tr bgcolor="#FFFFFF">
															<td height="25" valign="top">
																<c:set var="checkboxControl">checkbox_<%=queryCount%></c:set>
																<jsp:useBean id="checkboxControl" type="java.lang.String"/>

															<html:checkbox property="chkbox" styleId="<%=checkboxControl%>"/>
															
															<td height="25" valign="top" class="content_txt" >
																<%=newTitle%>
															</td>
															  <td height="25" valign="top" class="content_txt"><%=queryType%></td>
															 
															 </td>
																<c:set var="queryTitleControlId">queryTitleControl_<%=queryCount%></c:set>
																<jsp:useBean id="queryTitleControlId" type="java.lang.String"/>
																<html:hidden property="queryTitleControl" styleId="<%=queryTitleControlId%>"
																value="<%=newTitle%>"/>

																<c:set var="queryIdControl">queryIdControl_<%=queryCount%></c:set>
																<jsp:useBean id="queryIdControl" type="java.lang.String"/>
																<html:hidden property="queryIdControl" styleId="<%=queryIdControl%>"
																value="<%=queryId%>"/>

																
																<c:set var="queryTypeControl">queryTypeControl_<%=queryCount%></c:set>
																<jsp:useBean id="queryTypeControl" type="java.lang.String"/>
																<html:hidden property="queryTypeControl" styleId="<%=queryTypeControl%>"
																value='<%=queryType%>'/>

														</tr>
													</c:forEach>
											</div>

										</tr>
									</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr valign="bottom">
			<td class="tr_color_lgrey" height="25px">&nbsp;</td>
			<td colspan="2" class="tr_color_lgrey" valign="bottom">

			<table height="*" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="tr_color_lgrey">					
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
					<bean:message key="meassges.emptyquery"/>
					</td>	
</logic:equal >
</table>
</html:form>
</body>

