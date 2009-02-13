<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<html>
<head>
<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTree.css">
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<script src="jss/advancequery/queryModule.js"></script>
<script type="text/javascript" src="jss/advancequery/wz_tooltip.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/jss/dhtmXTreeCommon.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/jss/dhtmlXTree.js"></script>
<link rel="STYLESHEET" type="text/css" href="css/advancequery/dhtmlxtabbar.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/CascadeMenu.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
</head>
<body onunload='closeWaitPage()'>
<!-- Make the Ajax javascript available -->
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 

<%
	String formAction = Constants.ViewSearchResultsAction;
	String defineSearchResultsViewAction = Constants.DefineSearchResultsViewAction;
	Map treesMap = (Map) request.getAttribute("treesMap");
	String workflowName=(String)request.getAttribute(Constants.WORKFLOW_NAME);
%>
<html:form method="GET" action="<%=defineSearchResultsViewAction%>" style="margin:0;padding:0;">
<html:hidden property="currentPage" value="prevToAddLimits"/>
<input type="hidden" name="isQuery" value="true">
<table bordercolor="#000000" border="0" width="100%" cellspacing="0" cellpadding="0"  height="100%" >
	<tr height="5%">
		<td>
			<%@ include file="/pages/advancequery/common/ActionErrors.jsp" %>
		</td>
	</tr>
	<tr id="workflowname" valign="top" style="padding-bottom:5px;padding-left:10px;"> <td><span class="content_txt"></b><bean:message key="workflow.name"/> </b></span>: <%=workflowName%></td></tr>
	<tr>
		<td valign="top">
			<table border="0" width="100%" cellspacing="0" cellpadding="0" height="95%" id="table1" >
				<tr>
					<td valign="top">
						<table border="0" width="100%" cellspacing="0" cellpadding="0" height="100%" bordercolor="#000000" id="table2" >
							<tr  height="10">
								
								<td width="33%" align="center" valign="top" background="images/advancequery/top_bg_wiz.gif">
									<img src="images/advancequery/define_filters_inactive.gif" /> <!-- width="118" height="25" /-->
								</td>
								<td width="33%" align="center" valign="middle" height="36"  background="images/advancequery/top_bg_wiz.gif">
									<img src="images/advancequery/2_active.gif" /> <!-- width="199" height="38" /-->
								</td>
								<td width="33%" align="center" valign="top" background="images/advancequery/top_bg_wiz.gif">
									<img src="images/advancequery/3_inactive.gif" /> <!--  width="139" height="38" /-->
								</td>
							</tr>
							<tr height="1">
								<td></td>
							</tr>
							<tr valign="top"  height="100%" width="100%">
								<td colspan="4" valign="top" height="100%" width="100%">
									<table border="0" cellspacing="0" cellpadding="0" valign="top"  height="100%" width="100%">
									<tr valign="top">
										<td valign="top" height="100%" colspan="4" width="100%">
											<!--		tiles insert -->
											<tiles:insert attribute="content"></tiles:insert>
										</td>
									</tr>
									</table>
								</td>
							</tr>
						</table>						
					</td>
				</tr>
</table>
</html:form>

</body>
</html> 