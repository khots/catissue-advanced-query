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
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script src="jss/advancequery/queryModule.js"></script>
<script type="text/javascript" src="jss/advancequery/wz_tooltip.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/jss/dhtmXTreeCommon.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/jss/dhtmlXTree.js"></script>
<link rel="STYLESHEET" type="text/css" href="css/advancequery/dhtmlxtabbar.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/CascadeMenu.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css" />
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

<table border="0" cellpadding="0" cellspacing="0" class="contentLayout" >
<tr>
	<td  valign="top">
	
		<table border="0" width="90%" cellspacing="0" cellpadding="0"   class="login_box_bg" height="100%">
		<tr>
			<td class="table_header_query" height="28">
				<!--<img src="images/advancequery/t_get_patient_data.gif" altText="Get Patient Data"/>-->
				<span class="PageHeaderTitle">Get Patient Data</span>
			</td>
		</tr>
		<tr>
			<td>
				<%@ include file="/pages/advancequery/common/ActionErrors.jsp" %>
			</td>
		</tr>
		 <tr style="padding-left:2px;" id="workflowname" style="display:none;">
				<td nowrap height="30">
				&nbsp;
					<span class="content_txt"><bean:message key="workflow.name"/>:</span><span>
					<input type="text" styleClass="textfield_undefined" class="textfield_inactive" size="50" styleId="workflowName" property="workflowName" disabled="disabled" value="<%=workflowName%>"/></span>&nbsp;&nbsp;
				</td>
		  </tr>
		<tr>
			<td valign="top">
			<html:form method="post" action="<%=defineSearchResultsViewAction%>">
			<html:hidden property="currentPage" value="prevToAddLimits"/>
			<input type="hidden" name="requestFrom" value="MyQueries">
			<input type="hidden" name="isQuery" value="true">
				<table border="0" width="100%" cellspacing="0" cellpadding="0"  id="table1" height="100%" >
					<tr>
						<td valign="top">
							<table border="0" width="100%" cellspacing="0" cellpadding="0"   id="table2" height="100%" >
								<tr>
									
									<td style="padding-left:85px;" width="142" align="center" valign="top" background="images/advancequery/top_bg_wiz.gif">
										<img src="images/advancequery/define_filters_inactive.gif" /> <!-- width="118" height="25" /-->
									</td>
									<td width="185" align="center" valign="top" height="36"  background="images/advancequery/top_bg_wiz.gif">
										<img src="images/advancequery/2_active.gif" /> <!-- width="199" height="38" /-->
									</td>
									<td align="center" valign="top" background="images/advancequery/top_bg_wiz.gif">
										&nbsp;<!--<img src="images/advancequery/3_inactive.gif" /> <!--  width="139" height="38" /-->
									</td>
								</tr>
								<tr valign="top" >
									<td colspan="3" valign="top" >
										<table border="0" cellspacing="0" cellpadding="0"  valign="top" width="100%" height="100%">
											<tr valign="top">
												<td valign="top"   width="100%">
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
			</td>
		</tr>
		</table>
	
	</td>
</tr>
</table>

</body>
</html> 
