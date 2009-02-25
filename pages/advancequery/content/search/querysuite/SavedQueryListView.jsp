<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">

<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%-- Imports --%>
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
<script>

function changeResPerPage(controlId)
{
	var resultsPerPage=document.getElementById(controlId).value;
	var url='RetrieveQueryAction.do?pageOf=myQueriesforDashboard&requestFor=nextPage&pageNum=1&numResultsPerPage='+resultsPerPage;
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
<%@ include file="/pages/advancequery/common/ActionErrors.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#cccccc">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="4" cellspacing="1" >
								<tr class="td_bgcolor_grey">
										<td width="10" valign="middle" height="25"><input id="selectAllCheckbox"   type="checkbox" name="checkbox8" value="checkbox" onClick="javascript:selectAllCheckboxes()">                 </td>
										<td valign="middle" class="grid_header_text"><bean:message key="workflow.queryTitle"/></td>
										<td width="111" valign="middle" class="grid_header_text"><bean:message key="workflow.querytype"/></td>
										</tr>
											<div  id="searchDiv">
													<c:set var="parameterizedQueryCollection" value="${saveQueryForm.parameterizedQueryCollection}" />
												
													<c:forEach items="${parameterizedQueryCollection}" var="parameterizedQuery" varStatus="queries">
													<jsp:useBean id="parameterizedQuery" type="edu.wustl.common.querysuite.queryobject.IParameterizedQuery" />
													

															<%String target = "editQuery('"+parameterizedQuery.getId()+"')"; 
															  String queryId=parameterizedQuery.getId()+"";
															  String title = parameterizedQuery.getName();
															  String newTitle = Utility.getQueryTitle(title);
															  
															  String tooltip = Utility.getTooltip(title);
															  String function = "Tip('"+tooltip+"', WIDTH, 700)";
															  queryCount++;
															%>
															<tr bgcolor="#FFFFFF">
															<td  valign="top">
																<c:set var="checkboxControl">checkbox_<%=queryCount%></c:set>
																<jsp:useBean id="checkboxControl" type="java.lang.String"/>

															<html:checkbox property="chkbox" styleId="<%=checkboxControl%>"/>
															
															<td  valign="top" class="content_txt" >
																<html:link styleClass='bluelink' href='#' onclick='<%=target%>'  onmouseover="<%=function%>" >
											 						<%=newTitle%>
																</html:link><br/>
															</td>
															  <td  valign="top" class="content_txt">Get Count</td>
															 
							
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
																value="Get Count"/>

														</tr>
													</c:forEach>
											</div>

									
									</table>
							</td>
						</tr>
					</table>

			</tr>
			<tr>
		<td colspan="2" class="tr_color_lgrey" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr colspan="3">
				<td  align="left"  height="30" style="padding-left:5px;"><span class="content_txt_bold">Show Last:</span>															<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${sessionScope.numResultsPerPage}" styleClass="textfield_undefined">
												<html:options collection="resultsPerPageOptions" labelProperty="name" property="value"/>
											</html:select>
				</td>
				<td align="right" style="padding-right:5px;" class="content_txt">
													<c:set var="pageOf" value="${requestScope.pageOf}"/>  
														<jsp:useBean id="pageOf" type="java.lang.String"/>
												<c:set var="totalPages" value="${sessionScope.totalPages}"/>  
														<jsp:useBean id="totalPages" type="java.lang.Integer"/>
													<c:forEach var="pageCoutner" begin="1" end="${totalPages}">
															<c:set var="linkURL">
																RetrieveQueryAction.do?pageOf=<c:out value="${pageOf}"/>&requestFor=nextPage&pageNum=<c:out value="${pageCoutner}"/>
															</c:set>
															<jsp:useBean id="linkURL" type="java.lang.String"/>
															<c:if test="${sessionScope.pageNum == pageCoutner}">
																	<c:out value="${pageCoutner}"/> 
															</c:if>
															<c:if test="${sessionScope.pageNum != pageCoutner}"> | <a class="bluelink" href="<%=linkURL%>"><c:out value="${pageCoutner}"/></a>
															</c:if>
														</c:forEach>

				</td>
			 
				</tr>
			</table>
			<html:hidden styleId="queryId" property="queryId" />
		</html:form>
</body>

