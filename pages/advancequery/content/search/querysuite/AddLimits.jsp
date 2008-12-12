<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.cab2b.client.ui.query.ClientQueryBuilder"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

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
	String isQuery =(String) request.getAttribute("isQuery");;
	if(isQuery==null)
	{
		isQuery="false";
	}
	
	
%>

<html:form method="GET" action="<%=formAction%>" style="margin:0;padding:0;">
	<html:hidden property="stringToCreateQueryObject" value="" />
	<html:hidden property="nextOperation" value="" />

<table border="0" width="100%" cellspacing="0" cellpadding="0"  height="450" >	
		
		<tr>	
			<td width="33%" align="center" class="bgWizardImage" valign="top">
				<img src="images/advancequery/1_active.gif"/> <!-- width="118" height="25" /-->
			</td>
			<td width="33%" align="center" class="bgWizardImage" valign="top">
				<img src="images/advancequery/2_inactive.gif" /> <!-- width="199" height="38" /-->
			</td>
			<td width="33%" align="center" class="bgWizardImage" valign="top">
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
							<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%" bordercolorlight="#000000" >        
								
							<tr>
							<td>
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="30%" bordercolorlight="#000000" class='tbBordersAllbordersBlack'>
							
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
													<td height="200" width="100%" id="addLimitsSection">
													<div id="addLimits" style="overflow:auto; height:100%;width:100%"></div></td>
												</tr>	
								</table>
							</td>
							</tr>							
							<tr id="AddLimitsButtonMsg" border="0">
								<td valign="top" id="AddLimitsButtonSection">
										<div id="AddLimitsButtonRow" align="right" border="0"></div>
								</td>
							</tr>							
							<tr>							
							<td valign="top">								
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%" bordercolorlight="#000000">
								<tr>
										<td height="400px">											
											<div id="queryTableTd" style="overflow:auto;height:400;width:100%">
											<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
												id="DAG" width="100%" height="100%"
												codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
												<param name="movie" value="flexclient/advancequery/dag/DAG.swf?view=AddLimit&isQuery=<%=isQuery%>"/>
												<param name="quality" value="high" />
												
												<param name="bgcolor" value="#869ca7" />
												<param name="allowScriptAccess" value="sameDomain"/>
												<embed src="flexclient/advancequery/dag/DAG.swf?view=AddLimit&isQuery=<%=isQuery%>" quality="high" bgcolor="#869ca7"
													width="100%" height="100%" name="DAG" align="middle"
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
					<table border="0" width="100%" cellspacing="0" cellpadding="0" height="24" background="images/advancequery/bot_bg_wiz.gif">
					<tr valign="top">
					 <td width="2%" valign="bottom">&nbsp;</td>
					 <td width="50%" align="left">
					  <table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/advancequery/b_save.gif" width="51"  hspace="3" onclick="validateQuery('save');"/></td>
							 <td><img src="images/advancequery/b_search.gif"  hspace="3" onclick="validateQuery('search');"/></td>
						</tr>
					 </table>
					</td>
							
					<td width="50%" align="right">
					 <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
							<td><img src="images/advancequery/b_next.gif"   hspace="3" onclick="saveClientQueryToServer('next');" /></td>
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

</table>               
</html:form>
</body>
</html> 