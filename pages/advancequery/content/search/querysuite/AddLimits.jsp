<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.cab2b.client.ui.query.ClientQueryBuilder"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page isELIgnored="false" %>

<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<script src="jss/advancequery/queryModule.js"></script>
<script src="jss/advancequery/script.js"></script>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<link rel="STYLESHEET" type="text/css" href="css/advancequery/dhtmlxtabbar.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/CascadeMenu.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />

<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
</head>
<body onunload='closeWaitPage()'>
<script type='text/JavaScript' src='jss/advancequery/scwcalendar.js'></script>
<html:errors />
<%
  
  	String formAction = Constants.SearchCategory;
	String defineSearchResultsViewAction = Constants.DefineSearchResultsViewAction;
	String isQuery =(String) request.getAttribute("isQuery");
	String isworkflow=(String)request.getAttribute(Constants.IS_WORKFLOW);
	String workflowName=(String)request.getAttribute(Constants.WORKFLOW_NAME);
	if(isQuery==null)
	{
		isQuery="false";
	}
	
	
%>

 <html:form method="GET" action="<%=formAction%>" style="margin:0;padding:0;">
	<html:hidden property="stringToCreateQueryObject" value="" />
	<html:hidden property="nextOperation" value="" />
	 <input type="hidden" name="isWorkflow" id="isWorkflow" value="">
   
 <input type="hidden" name="pageOf" id="pageOf" value="DefineFilter">
  <table border="0" width="100%" cellspacing="0" cellpadding="0"  height="450" bgcolor="#ffffff">	
	 
	 <tr style="padding-left:10px;">
			<td  nowrap>
			&nbsp;
				<span class="small_txt_grey"><bean:message key="workflow.name"/></span>:<span class="small_txt_grey">
				<html:text styleClass="textfield_undefined" size="50" styleId="workflowName" property="workflowName" disabled="true" value="<%=workflowName%>"/></span>&nbsp;&nbsp;
			</td>
			</tr>
	 <tr>	
			<td width="33%" align="center" valign="middle" height="36"  background="images/advancequery/top_bg_wiz.gif" >
				<img src="images/advancequery/define_filters_active.gif"/> <!-- width="118" height="25" /-->
			</td>
			<td width="33%" align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
				<img src="images/advancequery/2_inactive.gif" /> <!-- width="199" height="38" /-->
			</td>
			<td width="33%" align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
				<img src="images/advancequery/3_inactive.gif" /> <!--  width="139" height="38" /-->
			</td>
		</tr>
	<tr>
	<td colspan="3">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" height="100%" id="table1">			 
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="0" height="100%" bordercolor="#000000" id="table2" >																					
		
		<tr>
			<td height="60%" valign="top" width="100%" colspan="4">
				<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3">			
					<tr>
						<td valign="top" width="10%" >
						<%@ include file="/pages/advancequery/content/search/querysuite/ChooseSearchCategory.jsp" %>
						</td>
					
					

					<td valign="top" height="60%">
							<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  >        
								
							<tr>
							<td>
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="30%"  class='login_box_bg'>
							
												<tr id="rowMsg">
													<td id="validationMessagesSection"  class='validationMessageCss'>
														<div id="validationMessagesRow"   class='validationMessageCss' style="overflow:auto; height:30;display:none"></div>
													</td>
												</tr>												
												<tr id="AddLimitsButtonMsg" border="0">
													<td id="AddLimitsButtonSection" height="10" >
														<div id="AddLimitsMsgRow"  border="0"></div>
													</td>
												</tr>
												<tr>
													<td height="215" width="100%" id="addLimitsSection">
													<div id="addLimits" style="overflow:auto; height:100%;width:100%"></div></td>
												</tr>	
								</table>
							</td>
							</tr>							
							<tr id="AddLimitsButtonMsg" border="0">
								<td valign="top" id="AddLimitsButtonSection" style="padding-bottom:3px;padding-top:3px">
										<div id="AddLimitsButtonRow" valign="middle" align="right" border="0"></div>
								</td>
							</tr>							
							<tr>							
							<td>								
								<table border="0" bordercolor="#cccccc" width="100%" cellspacing="0" class="login_box_bg" cellpadding="0" bgcolor="#FFFFFF" height="100%">
								<tr>
										<td height="400px" >											
											<div id="queryTableTd" style="overflow:auto;height:400;width:100%">
											<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
												id="DAG" width="100%" height="100%"
												codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
												<param name="movie" value="flexclient/advancequery/dag/DAG.swf?view=AddLimit&isQuery=<%=isQuery%>"/>
												<param name="quality" value="high" />
												
												<param name="bgcolor" value="#869ca7" />
												<param name="allowScriptAccess" value="sameDomain"/>
												<param name="wmode" value="transparent"/>
												<embed src="flexclient/advancequery/dag/DAG.swf?view=AddLimit&isQuery=<%=isQuery%>" quality="high" bgcolor="#869ca7"
													width="100%" height="100%" name="DAG" align="middle" wmode="transparent"
													play="true"
													loop="false" 
													
													quality="high"
													allowScriptAccess="sameDomain"
													type="application/x-shockwave-flash"
													pluginspage="http://www.adobe.com/go/getflashplayer">
												</embed>

											</object>
											
											</div>
										</td>
									</tr>
								</table>
							</td>
							</tr>
							</table>
						</td>
						
					</tr>
					
				</table>    
				
			</td>
		</tr>
	
			</table>		
			</td>
			</tr>
			<tr>
					
					<td colspan="4">
					<table border="0" width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr valign="middle">
					 <td width="50%" align="left" colspan="2">
					  <table border="0" cellspacing="0" cellpadding="0" >
						<tr>
							 <td style="padding-left:7px"  ><img src="images/advancequery/b_save.gif"   hspace="3" onclick="validateQuery('save');"/></td>
							 <td style="padding-left:4px"><img src="images/advancequery/b_back_to_workflow.gif"  hspace="3" onclick="showWorkFlowWizard();"/></td>
						</tr>
					 </table>
					</td>
							
					<td width="50%" align="right">
					 <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						
						<td id="projectList" style="padding-right:5px" > Select Project:
							<SELECT NAME="dropdown" class="textfield" onChange="setProjectData(this,'categorySearchForm')">
								<OPTION VALUE="">Unspecified..
								<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
									<OPTION VALUE="${project.value}">${project.name}
								</c:forEach>
							</SELECT>
					    </td>	
						<td align="right"><img src="images/advancequery/b_define_results_view.gif" onclick="saveClientQueryToServer('next');" /></td>
						</tr>
					</table>
					 </td>
					 <td width="2%">&nbsp;</td>
					</tr>
				</table>   
				</td>
					</tr>
			</table>          
			</td></tr>
			<tr height="7"> <td>&nbsp;</td></tr>

</table>               
</html:form>
</body>
<script>   
   var wrkflw = "<%=isworkflow%>";
   if(wrkflw=="true")
   {
      document.getElementById("isWorkflow").value="true";
	  document.getElementById("projectList").style.display="none";
   }
   else
   {
     document.getElementById("workflowname").style.display="none";
	
   }
 
 </script>
</html>