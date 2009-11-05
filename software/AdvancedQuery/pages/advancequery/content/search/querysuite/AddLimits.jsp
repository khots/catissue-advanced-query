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
<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css"/>

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
	String isSaveAs=(String)request.getAttribute(Constants.IS_SAVE_AS);
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

 
<table border="0" cellpadding="0" cellspacing="0" class="contentLayout" >
<tr>
<td>
	<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="login_box_bg" height="100%">
	<tr>
			<td height="27" background="images/bg_content_header.gif">
				<!-- <img src="images/advancequery/t_get_patient_data.gif" altText="Get Patient Data"/> -->
				<span class="PageHeaderTitle"><bean:message key="query.getData.title"/></span>
			</td>
		</tr>
	<tr>
	<td align="center">
		<html:form method="POST" action="<%=formAction%>" style="margin:0;padding:0;">
		<html:hidden property="stringToCreateQueryObject" value="" />
		<html:hidden property="nextOperation" value="" />
		<input type="hidden" name="isWorkflow" id="isWorkflow" value="">
		<input type="hidden" name="pageOf" id="pageOf" value="pageOfGetData">
		<input type="hidden" name="isSaveAs" id="isSaveAs" value="<%=isSaveAs%>">
		<input type="hidden" name="isQueryUpdated" id="isQueryUpdated" value="<%=isUpdated%>">
		<html:hidden property="requestFrom" value="" />
		<input type="hidden" name="isWorkflow" id="isWorkflow" value="">
		<table border="0" width="100%" height="100%" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">	
		<tr id='emptyTr'>
				<td colspan="3" height='10'>
				</td>
			 </tr>
		<tr style="padding-left:2px;" id="workflowname">
					<td  nowrap height="30" colspan="3">
					&nbsp;
						<span class="content_txt"><bean:message key="workflow.name"/>:</span><span>
						<input type="text" styleClass="textfield_undefined" class="textfield_inactive" size="50" styleId="workflowName" property="workflowName" disabled="disabled" value="<%=workflowName%>"/></span>&nbsp;&nbsp;
					</td>
			  </tr>
		<tr>	
					<td style="padding-left:92px;" width="142" align="center" valign="bottom" height="30"  background="images/advancequery/top_bg_wiz.gif" >
						<img src="images/advancequery/define_filters_active.gif"/> <!-- width="118" height="25" /-->
					</td>
					<td width="185" align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
						<img src="images/advancequery/2_inactive.gif" /> <!-- width="199" height="38" /-->
					</td>
					<td align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
						&nbsp;<!--<img src="images/advancequery/3_inactive.gif" /> --><!--  width="139" height="38" /-->
					</td>
				</tr>
		<tr>
			<td colspan="3">
			<table border="0" width="100%" cellspacing="0" cellpadding="0"  id="table1" height="100%">			 
				<tr>
				<td>
					<table border="0" width="100%" cellspacing="0" cellpadding="0"   id="table2" height="100%" >																					
					<tr>
					<td  valign="top" width="100%" colspan="4">
						<table border="0"  height="100%" width="100%" cellpadding="0" cellspacing="0" >	
						<tr height="100%" width="100%">
						<td valign="top" width="20%" height="100%" id="resizeableSearchEntityTD">
							<div style="height:100%;width:100%;display:block;" id="collapsableSearchEntity" >
							<%@includefile="/pages/advancequery/content/search/querysuite/ChooseSearchCategory.jsp" %>
							</div>
						</td>
						<td valign="top" width="80%" height="100%" >
							<table id="addlimitNdagSection" border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="650"  >        
							<tr id="addlimitTr" >
							<td height="40%" id="resizeableAddlimitTD">
								<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%"  style="border-top: 1px solid #cccccc;border-left: 1px solid #cccccc;">
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
								<table border="0" bordercolor="#cccccc" width="100%" cellspacing="0" style="border-bottom: 1px solid #cccccc;border-top: 1px solid #cccccc;border-left: 1px solid #cccccc;"  cellpadding="0" bgcolor="#FFFFFF" height="100%">
									<tr valign="top">
										<td>											
										<div id="queryTableTd" style="overflow:auto;height:100%;width:100%">
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
				<td >
					<table border="0" width="100%" cellspacing="0" cellpadding="0" height="40">
					<tr valign="middle">
					<td width="50%" align="left">
						<table border="0" cellspacing="0" cellpadding="0"  >
							<tr>
							<td style="padding-left:8px; display:none;" valign="top" >
								<a href="javascript:validateQuery('save');" ><img alt="Save" src="images/advancequery/b_save.gif" border="0"/>
								</a>
							</td>
							<td id="BackToWorkflowTd" style="padding-left:6px;" valign="top">
								<a href="javascript:showWorkFlowWizard();"><img  alt="Back to Workflow" src="images/advancequery/b_back_to_workflow.gif" border="0" />
								</a>
							</td>
							</tr>
						</table>
					</td>
					<td width="50%" align="right">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td valign="top" align="right" style="padding-right:5px">
								<a href="javascript:saveClientQueryToServer('next');">
								<img alt="Define Results View >>" src="images/advancequery/b_define_results_view.gif" border="0" />
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
</body>
<script>  

    focusDAG();
   function focusDAG()
   {
	    document.getElementById("DAG").focus();
   }
   var element= document.getElementById("addLimitsSection");
   element.style.height="240px";
   var wrkflw = "<%=isworkflow%>";
   if(wrkflw=="true")
   {
	  document.getElementById("emptyTr").style.display="none";
      document.getElementById("isWorkflow").value="true";
   }
   else
   {
     document.getElementById("workflowname").style.display="none";
	 document.getElementById("BackToWorkflowTd").innerHTML = '<a href="javascript:cancel_GPD_query()"><img  alt="Cancel" src="images/advancequery/b_cancel.gif" border="0" /></a>';
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

