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
   
 <input type="hidden" name="pageOf" id="pageOf" value="pageOfGetData">
 <table border="0" width="100%" >
	<tr><td style="padding-left:5px; padding-right:5px;">
 <table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="login_box_bg">
 <tr><td>
  <table border="0" width="100%" cellspacing="0" cellpadding="0"  height="450" bgcolor="#ffffff">	
	 
	 <tr style="padding-left:2px;" id="workflowname">
			<td  nowrap height="30" colspan="3">
			&nbsp;
				<span class="content_txt"><bean:message key="workflow.name"/>:</span><span>
				<input type="text" styleClass="textfield_undefined" class="textfield_inactive" size="50" styleId="workflowName" property="workflowName" disabled="disabled" value="<%=workflowName%>"/></span>&nbsp;&nbsp;
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
	<table border="0" width="100%" cellspacing="0" cellpadding="0"  id="table1">			 
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="0"   id="table2" >																					
		
		<tr>
			<td  valign="top" width="100%" colspan="4">
				<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" >			
					<tr>
						<td valign="bottom" width="10%" >
						<%@ include file="/pages/advancequery/content/search/querysuite/ChooseSearchCategory.jsp" %>
						</td>
					
					

					<td valign="top">
                    <table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  >        
					        <tr id="rowMsg">
													<td id="validationMessagesSection"  class='validationMessageCss'  valign="middle">
														<div id="validationMessagesRow"   class='validationMessageCss' style="overflow:auto; display:none"></div>
													</td>
												</tr>			
							<tr>
							<td >
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  class='login_box_bg'>
																													
												<tr id="AddLimitsButtonMsg" border="0">
													<td id="AddLimitsButtonSection" height="10" >
														<div id="AddLimitsMsgRow"  border="0"></div>
													</td>
												</tr>
												<tr>
													<td width="100%" id="addLimitsSection">
													<div id="addLimits" style="overflow:auto; height:100%;width:100%"></div></td>
												</tr>	
								</table>
							</td>
							</tr>							
							<tr id="AddLimitsButtonMsg" border="0">
								<td valign="top" id="AddLimitsButtonSection" height="29" style="padding-top:3px">
										<div id="AddLimitsButtonRow" valign="middle" align="right" border="0"></div>
								</td>
							</tr>							
							<tr>							
							<td valign="bottom">								
								<table border="0" bordercolor="#cccccc" width="100%" cellspacing="0" class="login_box_bg" cellpadding="0" bgcolor="#FFFFFF" height="100%">
								<tr>
										<td>											
											<div id="queryTableTd" style="overflow:auto;height:350;width:100%">
											<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
												id="DAG" width="100%" height="100%"
												codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
												<param name="movie" value="flexclient/advancequery/dag/DAG.swf?pageOf=pageOfGetData&view=AddLimit&isQuery=<%=isQuery%>"/>
												<param name="quality" value="high" />
												
												<param name="bgcolor" value="#869ca7" />
												<param name="allowScriptAccess" value="sameDomain"/>
												<param name="wmode" value="transparent"/>
												<embed src="flexclient/advancequery/dag/DAG.swf?pageOf=pageOfGetData&view=AddLimit&isQuery=<%=isQuery%>" quality="high" bgcolor="#869ca7"
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
					<table border="0" width="100%" cellspacing="0" cellpadding="0" height="40">
					<tr valign="middle">
					 <td width="50%" align="left" colspan="2">
					  <table border="0" cellspacing="0" cellpadding="0"  >
						<tr>
							 <td style="padding-left:8px; display:none;" valign="top" ><a href="javascript:validateQuery('save');" ><img alt="Save" src="images/advancequery/b_save.gif" border="0"/></a></td>
							 <td style="padding-left:5px;" valign="top"><a href="javascript:showWorkFlowWizard();"><img  alt="Back to Workflow" src="images/advancequery/b_back_to_workflow.gif" border="0" /></a></td>
						</tr>
					 </table>
					</td>
							
					<td width="50%" align="right">
					 <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td valign="top" align="right" style="padding-right:5px"><a href="javascript:saveClientQueryToServer('next');"><img alt="Define Results View >>" src="images/advancequery/b_define_results_view.gif" border="0" /></a></td>
						</tr>
					</table>
					 </td>
					 <td></td>
					</tr>
				</table>   
				</td>
					</tr>
			</table>          
			</td></tr>
	</table>  
</td></tr></table>
</td></tr></table>
<br>
</html:form>
</body>
<script>   
   var element= document.getElementById("addLimitsSection");
   element.style.height="240px";
   var wrkflw = "<%=isworkflow%>";
   if(wrkflw=="true")
   {
      document.getElementById("isWorkflow").value="true";
   }
   else
   {
     document.getElementById("workflowname").style.display="none";
	
   }
 
 </script>
</html>