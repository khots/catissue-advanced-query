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
	<html:hidden property="selectedProject" value="" />
	<html:hidden property="selectedProjectName" value="" />
	<html:hidden property="requestFrom" value="" />
	<input type="hidden" name="pageOf" id="pageOf" value="Get Count">
	<input type="hidden" name="isWorkflow" id="isWorkflow" value="">
	<table border="0" width="100%" >
	<tr><td style="padding-left:5px; padding-right:5px;">
<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="login_box_bg">
<tr>
	<td class="table_header_query" height="28">
		<img src="images/advancequery/t_get_counts_define_query.gif" altText="Get Counts - Define Query"/>
	</td>
</tr>
<tr>
	<td align="center">
<table border="0"  width="100%" cellspacing="0" cellpadding="0"  bgColor="#FFFFFF" >	
			<tr>
				<td style="padding-left:15px;" class='messagetexterror' colspan="2">
					<div id="titleError" style="overflow:auto; padding-top:5px; padding-bottom:5px; display:none"></div>
				</td>
			</tr>
			<tr>
			<td style="padding-left:5px;" height="30" class="small_txt_grey" colspan="2"><span class="red_star">*</span> Denotes mandatory feilds
			<table width="100%" border="0" cellpadding="2" cellspacing="2" align="center">
			<tr class="td_greydottedline_horizontal"><td height="1"></td></tr></table> 
			</td>
			</tr>
		<!--	<tr id="workflowname" valign="top" style="padding-bottom:20px;padding-left:10px;"> <td colspan="4"> &nbsp;&nbsp;<span class="content_txt_bold"></b><bean:message key="workflow.name"/> </b></span>: <%=workflowName%></td></tr> -->
			<tr style="padding-left:2px;">
			<td nowrap id="workflowname">
			&nbsp;
				<span class="content_txt_bold"><bean:message key="workflow.name"/>:</span><span>
				<input type="text" styleClass="textfield_undefined" class="textfield_inactive" size="50" styleId="workflowName" property="workflowName" disabled="disabled" value="<%=workflowName%>"/></span>&nbsp;&nbsp;
			</td>
			
			<td  >
				&nbsp;
				<span class="content_txt_bold"><bean:message key="getcountquery.name"/></span><span class="red_star">*</span>:<span class="content_txt">
				<html:text styleClass="textfield_undefined" size="50" styleId="queryTitle1" property="queryTitle" /></span>
			</td>
			</tr>
			<tr>
				<td height="5" colspan="2">
				</td>
			</tr>
	<tr>
	<td colspan="2">
	<table border="0" width="100%" cellspacing="0" cellpadding="0"  id="table1">			 
	<tr>
	<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="0"   id="table2" >						
		<tr>
			<td  valign="top" width="100%" colspan="4">
				<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" >			
					<tr>
						<td valign="bottom" width="10%" >
						<%@includefile="/pages/advancequery/content/search/querysuite/ChooseSearchCategory.jsp" %>
						</td>
					<td valign="top">
                    <table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  >        			        						
							<tr>
							<td >
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  class='login_box_bg'>
																													
												<tr id="AddLimitsButtonMsg" border="0">
													<td id="AddLimitsButtonSection" height="10" >
														<div id="AddLimitsMsgRow"  border="0"></div>
													</td>
												</tr>
												<tr id="rowMsg">
													<td  id="validationMessagesSection"   class="messagetexterror" valign="top" style="padding:3px 0 0 5px;">
														<div id="validationMessagesRow"   style="overflow:auto; display:none"></div>
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
							<td valign="top">								
								<table border="0" bordercolor="#cccccc" width="100%" cellspacing="0" class="login_box_bg" cellpadding="0" bgcolor="#FFFFFF" height="100%">
								<tr>
										<td>											
											<div id="queryTableTd" style="overflow:auto;height:350;width:100%">
											<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
												id="DAG" width="100%" height="100%"
												codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
												<param name="movie" value="flexclient/advancequery/dag/DAG.swf?pageOf=pageOfGetCount&view=AddLimit&isQuery=<%=isQuery%>"/>
												<param name="quality" value="high" />
												
												<param name="bgcolor" value="#869ca7" />
												<param name="allowScriptAccess" value="sameDomain"/>
												<param name="wmode" value="transparent"/>
												<embed src="flexclient/advancequery/dag/DAG.swf?pageOf=pageOfGetCount&view=AddLimit&isQuery=<%=isQuery%>" quality="high" bgcolor="#869ca7"
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
			 <td>		
				 <table cellspacing="0" cellpadding="0" border="0" width="100%" height="40"><tr><td width="50%" align="left">	
					  <table  border="0" cellspacing="0" cellpadding="0" >
						<tr>
							 <td align="left"  style="padding-left:8px" valign="top">
								<a href="javascript:validateQuery('save');" >
									<img border="0" alt="Save" src="images/advancequery/b_save.gif" />
								</a>
							 </td>
							 <td style="padding-left:4px" valign="top">
							 <a href="javascript:validateQuery('cancel');">
							 <img   border="0" alt="Cancel" src="images/advancequery/b_cancel.gif"/></a></td>
							<!-- <td style="padding-left:4px"><img src="images/advancequery/b_search.gif"  hspace="3" onclick="validateQuery('search');"/></td>-->
						</tr>
					 </table>
					</td>
							
					<td  width="50%" align="right" style="padding-right:4px">
					 <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td style="padding:0 5px 5px 0;" valign="middle" class="content_txt_bold">Select Project:</td>
						<td style="padding:0 5px 5px 0;" valign="middle">
							<SELECT NAME="dropdown" class="textfield" onChange="setProjectData(this,'categorySearchForm')">
								<OPTION VALUE="">Unspecified
								<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
									<OPTION VALUE="${project.value}">${project.name}
								</c:forEach>
							</SELECT>
					    </td>
						<td align="right" valign="center">
						<a href="javascript:validateQuery('search');" ><img alt="Get Count" border='0' src="images/advancequery/b_get_count.gif" />
						</a>
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