<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page isELIgnored="false" %>
<% String isworkflow=(String)request.getAttribute(Constants.IS_WORKFLOW);%>

<html>
<link rel="STYLESHEET" type="text/css" href="css/advancequery/dhtmlxtabbar.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/CascadeMenu.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<body>
 <input type="hidden" name="pageOf" id="pageOf" value="DefineView">
 <input type="hidden" name="isWorkflow" id="isWorkflow" value="">
 <input type="hidden" name="requestFrom" value="">
  <table border="0"  height="100%" width="100%"  cellpadding="1" cellspacing="3" valign="top">
	
<tr  valign="top">
	<td  valign="top" width="20%" colspan="2">
		<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr valign="top" width="100%" height="100%" align="left">
				<td valign="top" height="100%" align="left" style="padding:4px 0 0 0;*padding:2px 0 0 0;">
              <%@ include file="/pages/advancequery/content/search/querysuite/selectEntity.jsp" %>				
                </td>
			</tr>

		</table>
	</td>
	<td>
	    <table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr  id="rowMsg" class='validationMessageCss' >
				<td id="validationMessagesSection"  width="80%" class='validationMessageCss'>
					<div id="validationMessagesRow" style="overflow:auto; width:100%; height:50;display:none"></div>
				</td>
			</tr>
			<tr>
				<td valign="top" width="80%" height="100%" class="login_box_bg" align="left">
					<%@ include file="/pages/advancequery/content/search/querysuite/ContainmentViewTree.jsp" %>
				</td>
			</tr>
           

</table></td>
</tr>

 <tr> 
	<td colspan="2" height="40"><table  border="0"><tr>
		<td  align="left" style="padding-left:2px;"><a href="javascript:validateQuery('saveDefineView');"><img alt="Save" src="images/advancequery/b_save.gif"       
			border="0" /></a></td>
        <td id="BackToWorkflowTd" align="left" style="padding-left:5px;"><a href="javascript:showWorkFlowWizard();"><img border="0" alt="Back To Workflow" src="images/advancequery/b_back_to_workflow.gif"   
		     /></a></td></tr></table></td>
			 <td align="right">
			 <table border="0" cellpadding="0" cellspacing="0"><tr>
				 <td align="right" style="padding-right:2px;"><a href="javascript:previousFromDefineResults('DefineResultsView');"><img alt= "<< Redefine Filters" src="images/advancequery/b_redefine_filter.gif" border="0"  
		     /></a></td></tr></table></td>
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
<script>  
//to hide the button row (of defineGridView page )in define view page
 if("<%=isworkflow%>"=="true")
 
 {
  
	document.getElementById("isWorkflow").value="true";
    document.getElementById("workflowname").style.display="block";
 }
 else{
 document.getElementById("BackToWorkflowTd").innerHTML = '<a href="javascript:cancel_GPD_query()"><img  alt="Cancel" src="images/advancequery/b_cancel.gif" border="0" /></a>';
 }
 document.getElementById("buttontr").style.display="none";  

 </script>
</body>
</html>