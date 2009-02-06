<%@ page import="java.util.*"%>
<%@ page isELIgnored="false" %>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.cab2b.client.ui.query.ClientQueryBuilder"%>
<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="edu.wustl.common.beans.NameValueBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
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
	<html:hidden property="selectedProject" value="" />
	<table border="0" width="100%" >
	<tr><td style="padding-left:5px; padding-right:5px;">
<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="login_box_bg">
<tr>
	<td class="table_header_query" height="28">
		<img src="images/advancequery/t_get_counts.gif" altText="Get Counts"/>
	</td>
</tr>
<tr>
	<td align="center">
<table border="0"  width="100%" cellspacing="0" cellpadding="0"  bgColor="#FFFFFF" >	
			<tr>
				<td height="10" style="padding-left:15px;padding-top:3px" class='messagetexterror'>
					<div id="titleError" style="overflow:auto; display:none"></div>
				</td>
			</tr>
			<tr>
			<td colspan="4" style="padding-left:5px;" height="30" class="small_txt_grey" ><span class="red_star">*</span> Denotes mandatory fields
			<table width="100%" cellpadding="2" cellspacing="2" align="center">
			<tr class="td_greydottedline_horizontal"><td height="1"></td></tr></table>
			</td>
			</tr>
			
			<tr >
			<td colspan="4" >
				&nbsp;
				<span class="content_txt_bold"><bean:message key="getcountquery.name"/></span><span class="red_star">*</span>:<span class="content_txt">
				<html:text styleClass="textfield_undefined" size="80" styleId="queryTitle1" property="queryTitle" />&nbsp;&nbsp;</span>
			</td>
			</tr>
			<tr>
				<td height="5">
				</td>
			</tr>
			<tr>	
			<td  width="290" align="middle" valign="middle" height="32"  background="images/advancequery/top_bg_wiz.gif" >
				<img src="images/advancequery/tab_define_query_active.gif"/> <!-- width="118" height="25" /-->
			</td>
			
			<td  align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
			<!--	<img src="images/advancequery/2_inactive.gif" /> <!-- width="199" height="38" /-->
			</td>
			
			<td  align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
				<!--<img src="images/advancequery/3_inactive.gif" /> <!--  width="139" height="38" /-->
			</td>
			<td  noWrap align="right" background="images/advancequery/top_bg_wiz.gif" valign="middle">
				<!--<a href="#" class="greylink">Get Patient Data >> </a>-->&nbsp;
			</td>
		</tr>
	<tr>
	<td colspan="4">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" height="100%" id="table1">			 
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="0" height="100%" bordercolor="#000000" id="table2" >																					
				<tr>
			<td height="60%" valign="top" align="left" colspan="4">
				<table border="0"  height="100%" width="100%" >			
					<tr>
						<td valign="top" width="10%" >
						<%@ include file="/pages/advancequery/content/search/querysuite/ChooseSearchCategory.jsp" %>
						</td>
										<td valign="top" height="60%" style="padding-right:7px">
							<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  >
							<tr>
							<td>
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF"   class='login_box_bg'>
																								
												<tr id="AddLimitsButtonMsg" border="0">
													<td id="AddLimitsButtonSection"  >
														<div id="AddLimitsMsgRow"  border="0"></div>
													</td>
												</tr>
												<tr id="rowMsg">
													<td id="validationMessagesSection" height="30" style="padding-left:5px" class='messagetexterror'>
														<div id="validationMessagesRow" style="overflow:auto; display:none"></div>
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
					<tr><td >
							
					  <table  border="0" cellspacing="0" cellpadding="0" >
						<tr>
							 <td   align="left"  style="padding-left:4px"><img src="images/advancequery/b_save.gif"  onclick="validateQuery('save');"/></td>
							 <td style="padding-left:4px"><img src="images/advancequery/b_cancel.gif"  hspace="3" onclick="validateQuery('cancel');"/></td>
							<!-- <td style="padding-left:4px"><img src="images/advancequery/b_search.gif"  hspace="3" onclick="validateQuery('search');"/></td>-->
						</tr>
					 </table>
					</td>
							
					<td colspan="2" align="right" style="padding-right:7px">
					 <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td style="padding-right:5px" valign="middle" class="content_txt">Select Project:</td>
						</td>
						<td style="padding-right:5px">
							<SELECT NAME="dropdown" class="textfield" onChange="setProjectData(this,'categorySearchForm')">
								<OPTION VALUE="">Unspecified..
								<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
									<OPTION VALUE="${project.value}">${project.name}
								</c:forEach>
							</SELECT>
					    </td>
						<td align="right"><img src="images/advancequery/b_get_count.gif" onclick="validateQuery('search');" /></td>
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
			  
				</td>
					</tr>
			</table>          
			</td></tr>
			

</table> 
</td>
</tr>
</table> 
</td>
</tr>
</table>   <br>          
</html:form>
</body>
</html> 