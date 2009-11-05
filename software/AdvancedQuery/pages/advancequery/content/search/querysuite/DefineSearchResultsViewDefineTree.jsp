<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.query.actionforms.CategorySearchForm"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String isworkflow=(String)request.getAttribute(Constants.IS_WORKFLOW);
	String isUpdated = request.getParameter(Constants.IS_QUERY_UPDATED);
	String isEdited = request.getParameter(Constants.IS_EDITED_QUERY);
%>

<html>
<link rel="STYLESHEET" type="text/css" href="css/advancequery/dhtmlxtabbar.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/CascadeMenu.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<body>
 <input type="hidden" name="pageOf" id="pageOf" value="DefineView">
 <input type="hidden" name="isWorkflow" id="isWorkflow" value="">
 <input type="hidden" name="isSaveAs" id="isSaveAs" value="">
 <input type="hidden" name="requestFrom" value="">
<input type="hidden" name="sharedStatus" id="sharedStatus" value="${requestScope.sharingStatus}">
<input type="hidden" name="isQueryUpdated" id="isQueryUpdated" value="<%=isUpdated%>">
<input type="hidden" name="isEditedQuery" id="isEditedQuery" value="<%=isEdited%>">
  <table border="0"  height="100%" width="100%"  cellpadding="1" cellspacing="3" valign="top">
	
<tr  valign="top" align="middle">
 <!-- 	<td  valign="top" width="20%" colspan="2">
		<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr valign="top" width="100%" height="100%" align="left">
				<td valign="top" height="100%" align="left" style="padding:4px 0 0 0;*padding:2px 0 0 0;">
             		
                </td>
			</tr>

		</table>
	</td> -->
	<td colspan="3">
	    <table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr  id="rowMsg" class='validationMessageCss' >
				<td id="validationMessagesSection"  width="80%" class='validationMessageCss'>
					<div id="validationMessagesRow" style="overflow:auto; width:100%; height:50;display:none"></div>
				</td>
			</tr>
			<tr>
				<td valign="top" width="80%" height="100%" class="login_box_bg" align="center">
					<%@ include file="/pages/advancequery/content/search/querysuite/ContainmentViewTree.jsp" %>
				</td>
			</tr>
           

</table></td>
</tr>

 <tr> 
	<td colspan="3" height="40" width="100%"><table  border="0" width="100%" cellpadding="0" cellspacing="0" ><tr>
		<td  align="left" style="padding-left:2px;" width="12%"><a href="javascript:previousFromDefineResults('DefineResultsView');"><img alt= "<< Redefine Filters" src="images/advancequery/b_redefine_filter.gif" border="0"  
		     /></a></td>
        <td id="BackToWorkflowTd" align="left" style="padding-left:5px;" width="*"><a href="javascript:showWorkFlowWizard();"><img border="0" alt="Back To Workflow" src="images/advancequery/b_back_to_workflow.gif"   
		     /></a></td>
		 <td align="right" style="padding-right:2px;" id="saveButton" width="25%"><a href="javascript:preValidateQuery('saveDefineView','false');"><img alt="Save" src="images/advancequery/b_save.gif"       
			border="0" /></a></td>
		 <td align="right" style="padding-right:2px;" id="saveAsButton" width="8%"><a href="javascript:preValidateQuery('saveDefineView','true');"><img alt="Save" src="images/advancequery/b_save_as.gif"       
			border="0" /></a></td>
			</tr></table></td>
			<!--  <td align="right" valign="top">
		   <img src="images/advancequery/b_back_to_workflow.gif" hspace="3" vspace="3" onclick="javascript:showWorkFlowWizard()"/>
	     </td> -->	
          <!--  <td align="right" id="projectList" style="padding-left:4px;">
					 <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td style="padding-right:5px" valign="middle" class="content_txt"> Project:</td>
						</td>
						<td style="padding-right:5px">
							<SELECT NAME="dropdown" class="textfield" onChange="setProjectData(this,'categorySearchForm')">
								<OPTION VALUE="">Unspecified..
								<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
									<OPTION VALUE="${project.value}">${project.name}
								</c:forEach>
							</SELECT>
					    </td> 				
		  </tr>
		</table>
	</td>-->
 <!-- <td align="right"><img src="images/advancequery/b_execute_inact.gif" onclick=""/></td> -->

</tr>
</table>
<c:choose>	
	<c:when test="${requestScope.sharingStatus=='NOT_SHARED_BUT_USED'}" >
	</c:when>
	<c:when test="${requestScope.sharingStatus=='NOT_SHARED_NOT_USED'}" >
		<script>
			var elem = document.getElementById("saveAsButton");
			elem.style.display = 'none';
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
		</script>
	</c:otherwise>
</c:choose>
<script>  
//to hide the button row (of defineGridView page )in define view page
 if("<%=isworkflow%>"=="true")
 
 {
  
	document.getElementById("isWorkflow").value="true";
    document.getElementById("workflowname").style.display="block";
 }
 else{
 document.getElementById("BackToWorkflowTd").innerHTML = '<a href="javascript:cancel_GPD_query()"><img  alt="Cancel" src="images/advancequery/b_cancel.gif" border="0" /></a>';
 document.getElementById("workflowname").innerHTML = '<td>&nbsp;</td>';
 }
 //document.getElementById("buttontr").style.display="none";  

 </script>
</body>
</html>
