<%@ page import="java.util.*"%>
<%@ page isELIgnored="false" %>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.cab2b.client.ui.query.ClientQueryBuilder"%>
<%@ page import="edu.wustl.query.actionforms.CategorySearchForm"%>
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
</head>
<body onunload='closeWaitPage()'>
<script type='text/JavaScript' src='jss/advancequery/scwcalendar.js'></script>
<script type="text/javascript" src="jss/javaScript.js"></script>
<html:errors />

<%

	String formAction = Constants.SearchCategory;
	String defineSearchResultsViewAction = Constants.DefineSearchResultsViewAction;
	String isQuery =(String) request.getAttribute("isQuery");
	String isworkflow=(String)request.getAttribute(Constants.IS_WORKFLOW);
	String workflowName=(String)request.getAttribute(Constants.WORKFLOW_NAME);
	String isUpdated=request.getParameter(Constants.IS_QUERY_UPDATED);
	if(isUpdated==null)
	{
		isUpdated="false";
	}
	if(isQuery==null)
	{
		isQuery="false";
	}		
%>
 <input type="hidden" id="executeQuery" value =""/>
<table border="0" cellpadding="0" cellspacing="0" class="contentLayout" >
<tr>
<td>
	<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="login_box_bg" height="100%">
	<tr>
		<td class="table_header_query" height="27">
			<span class="PageHeaderTitle"><bean:message key="query.getCount.title"/></span>
		</td>
	</tr>
	<tr>
	<td align="center">
		<html:form method="POST" action="<%=formAction%>">
		<html:hidden property="stringToCreateQueryObject" value="" />
		<html:hidden property="nextOperation" value="" />
		<html:hidden property="selectedProject" value="" />
		<html:hidden property="selectedProjectName" value="" />
		<html:hidden property="requestFrom" value="" />
		<input type="hidden" name="pageOf" id="pageOf" value="Get Count">
		<input type="hidden" name="isWorkflow" id="isWorkflow" value="">
		<input type="hidden" name="isSaveAs" id="isSaveAs" value="">
		<input type="hidden" name="sharedStatus" id="sharedStatus" value="${requestScope.sharingStatus}">
		<input type="hidden" name="isQueryUpdated" id="isQueryUpdated" value="<%=isUpdated%>">
		<input type="hidden" name="isDisplaySaveAs" id="isDisplaySaveAs" value="true">
		<table border="0"  width="100%" cellspacing="0" cellpadding="0"  bgColor="#FFFFFF" height="100%" >	
		<tr>
			<td style="padding-left:10px;" class='error_msg' colspan="2">
			<div id="titleError" style="overflow:auto; padding-top:5px; padding-bottom:5px;display:none"></div>
			</td>
		</tr>
		<tr height="8px">
			<td colspan="2"></td>
		</tr>
				<!--	<tr id="workflowname" valign="top" style="padding-bottom:20px;padding-left:10px;"> <td colspan="4"> &nbsp;&nbsp;<span class="content_txt_bold"></b><bean:message key="workflow.name"/> </b></span>: <%=workflowName%></td></tr> -->
		<tr style="padding-left:2px padding-top:5px;">
			<td nowrap id="workflowname">&nbsp;
				<span class="content_txt_bold"><bean:message key="workflow.name"/>:</span><span>
				<input type="text" class="textfield_inactive" size="58" styleId="workflowName" property="workflowName" disabled="disabled" value="<%=workflowName%>"/></span>&nbsp;&nbsp;
			</td>
			<td>&nbsp;
				<span class="content_txt_bold"><bean:message key="getcountquery.name"/></span><span class="red_star">*</span>:<span class="content_txt">
				<html:text styleClass="textfield_undefined" size="56" styleId="queryTitle1" property="queryTitle" /></span>
			</td>
		</tr>
		<tr>
			<td height="8" colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">
			<table border="0" width="100%" cellspacing="0" cellpadding="0"  id="table1" height="100%">			 
				<tr>
				<td>
					<table border="0" width="100%" cellspacing="0" cellpadding="0"   id="table2" height="100%">						
					<tr>
					<td  valign="top" width="100%" colspan="4">
						<table border="0"  height="100%" width="100%" cellpadding="0" cellspacing="0" >			
						<tr height="100%" width="100%">
						<td valign="top" width="20%" height="100%" id="resizeableSearchEntityTD">
							<div style="height:100%;width:100%;display:block;" id="collapsableSearchEntity" >
							<%@includefile="/pages/advancequery/content/search/querysuite/ChooseSearchCategory.jsp" %>
							</div>
						</td>
						<td valign="top" width="80%" height="100%">
							<table id="addlimitNdagSection"  border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="650"  >        			        						
							<tr id="addlimitTr" >
							<td height="40%" id="resizeableAddlimitTD">
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%" style="border-top: 1px solid #cccccc;border-left: 1px solid #cccccc;" >
									<tr id="AddLimitsButtonMsg"  valign="top" height="5%">
										<td id="AddLimitsButtonSection" style="*padding-bottom:5px;" >
											<div id="AddLimitsMsgRow"  valign="top">
											<table border="0" width="100%" height="28" cellspacing="0" cellpadding="0" background="images/advancequery/bg_content_header.gif"  >
											<tr width="100%">
												<td style="border-bottom: 1px solid #cccccc;padding-left:5px;" valign='middle' class="PageSubTitle" colspan="8" >
												<bean:message key="query.defineLimits"/>
												</td>
											</tr>
											</table>
											</div>
										</td>
									</tr> 
									<tr id="collapsableAddlimit" >
										<td colspan="2">
											<div>
											<table border="0" width="100%" cellspacing="0" cellpadding="0">	 
											<tr id="rowMsg" height="1px" style="display:none;">
												<td  id="validationMessagesSection"  valign="top" style="padding:0px 0 0 0px;">
													<div id="validationMessagesRow"   style="overflow:auto; display:none"></div>
												</td>
											</tr>	
											<tr valign="top">
												<td width="100%" id="addLimitsSection">
													<div style="height: 240px;overflow:auto;width:100%;"> 
													<div id="addLimits" style="vertical-align : top;">
													</div>
													</div>
												</td>
											</tr>	
	                                        </table>
											<div>
										</td>
									</tr>
								</table>
							</td>
							</tr>							
							<tr id="dagtr">							
							<td valign="top" colspan="2" height="60%" id="resizeableDAGTD">								
								<table border="0" bordercolor="#cccccc" width="100%" cellspacing="0" style="border-bottom: 1px solid #cccccc;border-top: 1px solid #cccccc;border-left: 1px solid #cccccc;" cellpadding="0" bgcolor="#FFFFFF" height="100%">
									<tr valign="top"> 
										<td>											
										<div id="queryTableTd" style="overflow:auto;height:100%;width:100%">
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
					<table cellspacing="0" cellpadding="0" border="0" width="100%" height="40">
					<tr>
					<td width="50%" align="left">	
						<table  border="0" cellspacing="0" cellpadding="0" >
							<tr>
							<td align="left"  style="padding-left:5px" valign="top" id="saveButton">
								<a id="saveButtonId"  href="javascript:preValidateQuery('save','false');" >
									<img border="0" alt="Save" src="images/advancequery/b_save.gif" />
								</a>
							</td>
							<td align="left"  style="padding-left:5px" valign="top" id="saveAsButton">
								<a href="javascript:preValidateQuery('save','true');" >
									<img border="0" alt="Save" src="images/advancequery/b_save_as.gif" />
								</a>
							</td>
							<td style="padding-left:5px" valign="top">
							 <a id="cancelButtonId" href="javascript:preValidateQuery('cancel','false');">
								 <img   border="0" alt="Cancel" src="images/advancequery/b_cancel.gif" />
							</a>
							</td>
							</tr>
						</table>
					</td>
					<td  width="50%" align="right" style="padding-right:4px">
						<table border="0" cellspacing="0" cellpadding="0">
					    <tr>
							<td style="padding:0 5px 5px 0;" valign="middle">
								<span class="content_txt_bold">
								<bean:message key="query.selectProject"/></span>
							</td>
							<td style="padding:0 5px 5px 0;" valign="middle">
								<SELECT NAME="dropdown" id="dropdown" class="textfield" onChange="setProjectData(this,'categorySearchForm')" onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
									<OPTION VALUE=""><span class="small_txt_grey">Unspecified</span>
									<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
									<OPTION title="${project.name}" VALUE="${project.value}"><span class="small_txt_grey">${project.name}</span>
									</c:forEach>
									</SELECT>
							</td>
							<td align="right" valign="center">
								<a id='getCount' href="javascript:validateQuery('search');" ><img alt="Get Count"  border='0' src="images/advancequery/b_get_count.gif"/>
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
	</html:form>
</td>
</tr>
</table>   
</td>
</tr>
</table>     

<!-- scripts for   showing button based on sharing status of query -->
<c:choose>
	<c:when test="${requestScope.sharingStatus=='NOT_SHARED_BUT_USED'}" >
	</c:when>
	<c:when test="${requestScope.sharingStatus=='NOT_SHARED_NOT_USED'}" >
		<script>
			var elem = document.getElementById("saveAsButton");
			elem.style.display = 'none';
             document.getElementById("isDisplaySaveAs").value = "false";
		</script>
	</c:when>
	<c:when test="${requestScope.sharingStatus=='SHARED'}" >
		<script>
			var elem = document.getElementById("saveButton");
			elem.style.display = 'none';
		</script>
	</c:when>
	<c:otherwise>
		<script>
			var elem = document.getElementById("saveAsButton");
			elem.style.display = 'none';
			document.getElementById("isDisplaySaveAs").value = "false";
		</script>
	</c:otherwise>
</c:choose>
</body>
<script>   

focusDAG();
function focusDAG()
{
	document.getElementById("DAG").focus();
	//set focus back to QueryTitle
   document.getElementById("queryTitle1").focus();
}

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
 
function expandDag()
{
	//modified by  (amit_doshi Date: 6 Aug 2009) Bug Fixed :- 13565  (Changed the entire layout of the page to Liquid layout)
    document.getElementById("resizeableSearchEntityTD").style.display="none";
	document.getElementById("resizeableDAGTD").style.height="100%";
	document.getElementById("resizeableAddlimitTD").style.display="none";
 
}

function collapseDag()
{
	//modified by  (amit_doshi Date: 6 Aug 2009) Bug Fixed :- 13565  (Changed the entire layout of the page to Liquid layout)
   	document.getElementById("resizeableSearchEntityTD").style.display="";
	document.getElementById("resizeableDAGTD").style.height="60%";
	document.getElementById("resizeableAddlimitTD").style.display="";
	document.getElementById("resizeableAddlimitTD").style.height="40%";	
	document.getElementById("resizeableAddlimitTD").style.width="80%";	
	document.getElementById("resizeableSearchEntityTD").style.width="20%";
	
}
</script>
</html>
