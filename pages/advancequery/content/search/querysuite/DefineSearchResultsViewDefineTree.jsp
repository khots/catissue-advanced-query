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

  <table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
	
<tr  valign="top">
	<td  valign="top" width="20%">
		<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr  class='validationMessageCss'  >
				<td width="80%" class='validationMessageCss' style="display:none">
					&nbsp;
				</td>
			</tr>
			<tr valign="top" width="100%" height="100%" align="left">
				<td valign="top" height="100%" align="left" >
<%@ include file="/pages/advancequery/content/search/querysuite/selectEntity.jsp" %>				
</td>
			</tr>
		</table>
	</td>
	<td>
	    <table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr  id="rowMsg" class='validationMessageCss' >
				<td id="validationMessagesSection"  width="80%" class='validationMessageCss' colspan="5">
					<div id="validationMessagesRow" style="overflow:auto; width:100%; height:50;display:none"></div>
				</td>
			</tr>
			<tr>
				<td valign="top" width="80%" height="100%" class="login_box_bg" align="middle" colspan="5">
					<%@ include file="/pages/advancequery/content/search/querysuite/ContainmentViewTree.jsp" %>
				</td>
			</tr>
            <tr> 
			<td align="left"><img src="images/advancequery/b_save.gif"       
			 onclick="validateQuery('save');"/></td>
          <!-- <td width="30%" align="left" style="padding-left:4px;"><img src="images/advancequery/b_back_to_workflow.gif"   
		     onclick="showWorkFlowWizard();"/></td> -->

			<td align="right"><img src="images/advancequery/b_redefine_filter.gif"   
		     onclick="previousFromDefineResults();"/></td>
            <td align="right" id="projectList" style="padding-left:4px;">
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
	</td>
 <!-- <td align="right"><img src="images/advancequery/b_execute_inact.gif"   
		     onclick=""/></td> -->

</tr>

</table>
<script>  
//to hide the button row (of defineGridView page )in define view page
 if("<%=isworkflow%>"=="true")
 {
    document.getElementById("isWorkflow").value="true";
	document.getElementById("projectList").style.display="none";
 }
 document.getElementById("buttontr").style.display="none";  
 
 </script>
</body>
</html>