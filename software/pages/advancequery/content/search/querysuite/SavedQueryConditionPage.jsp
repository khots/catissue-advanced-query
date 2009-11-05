<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%-- Imports --%>
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants"
%>

<%
	String isQuerySaved =(String) request.getAttribute("isQuerySaved");
	String workflowName=(String)request.getAttribute(Constants.WORKFLOW_NAME);
	String pageOf = (String)request.getAttribute("pageOf");
	String isworkflow = (String)request.getAttribute(Constants.IS_WORKFLOW);
	String queryName= (String)request.getAttribute("queryName");
	String queryId = (String)request.getAttribute("queryId");
	String displaySaveAs = (String)request.getParameter("isDisplaySaveAs");
	String saveAs = (String)request.getParameter(Constants.IS_SAVE_AS);
	boolean isShared = (Boolean)request.getAttribute(Constants.SAHRED_QUERIES);
	
	String isUpdated = request.getParameter(Constants.IS_QUERY_UPDATED);
	String valueOfchkBox;
    String isSaveButtonDisable = "";
	 String action;
	 request.setAttribute("pageOf",pageOf);
	 if(isShared==true)
	 {
		valueOfchkBox="false";
	 }
	 else
	 {
		valueOfchkBox="true";
	 }
	if("true".equals(isworkflow))
	{
	  action= Constants.SAVE_QUERY_ACTION+"?isWorkflow="+isworkflow;
	}
	else 
	 action= Constants.SAVE_QUERY_ACTION;
	if(isQuerySaved != null)
	{
		isSaveButtonDisable = "disabled='disabled'";
	}
	Boolean iseditedQuery=false;
	if(request.getParameter(Constants.IS_EDITED_QUERY)!=null && request.getParameter(Constants.IS_EDITED_QUERY).equalsIgnoreCase("true"))
	{
		iseditedQuery = true;
	}
	if("true".equals(saveAs))
	{
		 queryId = null;
		 queryName="";
	}
%>

<html:html >
	<head>
		<meta http-equiv="Content-Language" content="en-us">
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
		<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
		<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css" />
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/queryModule.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/script.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/overlib_mini.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/calender.js"></script>
		<script type='text/JavaScript' src='jss/advancequery/scwcalendar.js'></script>
		<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
        <script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/ajax.js"></script>

		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>
			<bean:message key="savequery.queryInformationTitle"/>
		</title>
	</head>

	<body>
		 
<table border="0" cellpadding="0" cellspacing="0" align="center" class="contentLayout" >
	 <tr>
		  
		  <td>
			<table  cellpadding="0" cellspacing="0"  border="0" align="center" width="100%" class="login_box_bg" height="100%" >
				<tr valign="top">
			       <td height="27" colspan="3" class="table_header_query">
					<!-- <img src="images/advancequery/save_query_heading.gif"  altText="Save Query"/> -->
				   <span class="PageHeaderTitle">Save Query</span>
				  </td>
				</tr>
				
				<tr width="100%" valign="top" align="center">
					<td colspan="3">
					<html:form styleId='saveQueryForm' action='<%=action%>' focus="title" method="POST">
					<html:hidden property="requestFrom" value="" />
			    	 <input type="hidden" name="isWorkflow" id="isWorkflow" value="">
				     <input type="hidden" name="currentPage" id="currentPage" value="SaveQuery">
					 <input type="hidden" name="isQuery" id="isQuery" value="">  
					 <input type="hidden" name="isSaveAs" id="isSaveAs" value="<%=saveAs%>">
					 <input type="hidden" name="queryId" id="queryId" value="<%=queryId%>">  
					 <input type="hidden" name="queryname" id="queryname" value="<%=queryName%>">  
			 		 <input type="hidden" name="isQueryUpdated" id="isQueryUpdated" value="<%=isUpdated%>">
					 <input type="hidden" name="isDisplaySaveAs" id="isDisplaySaveAs" value="<%=displaySaveAs%>">
					 <input type="hidden" name="isEditedQuery" id="isEditedQuery" value="<%=iseditedQuery%>"/>
					 <input type="hidden" name="query_String" id="query_String" value=""/>
 					 <input type="hidden" name="display_Labels" id="display_Labels" value=""/>
					<table cellpadding="0" cellspacing="0" border="0" width="98%" >
							<tr>
								<td id="Error_msg"  class='error_msg' colspan="3" nowrap><html:errors/>
								</td>
							</tr>
							<tr>
								<td colspan="3"  valign="top" style="padding-left:10px;"><span class="red_star">*</span> <span class="small_txt_grey">Denotes mandatory fields</span></td>
							</tr>
							<tr>
								<td  colspan="3"  valign="top" class="td_greydottedline_horizontal" height="10"></td>
							</tr>
							
							<tr>
								<td colspan="3"  valign="top" >&nbsp;</td>
							</tr>
							<tr id="workflowName" >
								<td class="content_txt_bold" colspan="2">
												<bean:message key="workflow.name"/>:
								</td>
								<td >
									<input type="text" name="workflowName" value="<%=workflowName%>" size="78" class="textfield_WorkflowTitle_inactive" disabled="disabled" />
								</td>
						    </tr>
						    <tr><td height="10" colspan="3"></td></tr>
							 <tr>
								<td class="content_txt_bold" colspan="2"  >
									<bean:message key="query.title"/><span class="red_star">*</span>:
								</td>
								<td >
										<html:text       styleClass="textfield_QueryTitle"    styleId="title" property="title" value="<%=queryName%>" />
								</td>
							</tr>
							<tr><td height="10" colspan="3"></td></tr>
							<tr>
									<td colspan="2" class="content_txt_bold" valign="top" ><bean:message key="query.description"/>:</td>
									<td  class="formFieldNoBordersQuery">
										<html:textarea styleClass="textfield_undefined" styleId="description"  cols="80" rows="5"  property="description"> </html:textarea>
									</td>
							</tr>
								<!--<tr class="td_subtitle">
									<td colspan='3'  height="25" class="blue_title" style="padding-left:5px;">
										<bean:message key="savequery.setConditionParametersTitle" />
									</td>
								</tr>-->
							<tr><td colspan='3' style="padding-bottom:5px;">
									<table cellpadding="0" cellspacing="0" width="100%" >
									<tr><td class="small_txt_grey"> <html:checkbox property="shareQuery" styleId="shareQuery" disabled="<%=isShared%>" value="<%=valueOfchkBox%>"/>&nbsp;				
									   <bean:message
												key="query.shareQuery.permission" /></td>
									<td id="checkbox2" align="right"  class="small_txt_grey">
									<input type="checkbox"  id='showAll'  onclick="javaScript:showAttributes(this);" value="${entity.id}">						
									   Show all Attributes</td>			
									</tr>
								</table>
								</td>
							</tr>
						
							
							<tr>
							<td align="left" colspan="3">
										<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
												<td height="25" valign="middle" class="td_subtitle"><span
													class="blue_title"><img
													src="../design_HTML/images/spacer.gif" width="5" height="1"
													align="absmiddle">Parameterized Query</span>
												</td>
										</tr>
										<tr>
												<td class="td_greydottedline_horizontal" height="10"></td>
										</tr>
										</table>
							</td>
							</tr>
							<tr valign='top'>
								
								<td valign='top'  class="content_txt" colspan='3' height="150px">
								<div  valign='top' class="login_box_bg" id="parameterlist" style=" min-height:50px;overflow-x:auto;">
								   <%=request.getAttribute(Constants.HTML_CONTENTS)%>
			    				 </div>
								</td> 
							</tr>
						<tr>
 							<input type="hidden" name="queryString" id="queryString" value=""/>
							<input type="hidden" name="buildQueryString" id="buildQueryString" value=""/>
							<td style="padding-top:10px;padding-bottom:10px;padding-left:5px;"  colspan='3'  align="left">
								<input type="hidden" name="queryString" id="queryString" value=""/>
								<input type="hidden" name="buildQueryString" id="buildQueryString" value=""/>
								<!--input type="button" name="preview" value="Preview" class="actionButton"  disabled="true"/-->
								<c:choose>
									<c:when test="${querySaved eq 'true'}">
									 <!--	<img src="images/advancequery/b_close.gif" onclick="closeSaveQueryWindow()" /> -->
									</c:when>
									<c:otherwise>
										<table cellpadding="0" cellspacing="0" border="0">
											<tr>
											  <td>
												 <a id="savButton" href="javascript:validateAndSaveQuery('<%=queryId%>');" ><img border='0' alt="Save" src="images/advancequery/b_save.gif" <%=isSaveButtonDisable%> /></a>
												</td>
												<td>&nbsp;&nbsp;</td>
												<td id="cancelQuery" valign="top">
													<a id="cancelButton" href="javascript: cancel_GPD_query();"><img   border="0" alt="Cancel" src="images/advancequery/b_cancel.gif"/></a>
												</td>
											</tr>
										</table>
									</c:otherwise>
								</c:choose>
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
		
<script>
  
if("<%=isworkflow%>"!="true")
  {
	  document.getElementById("workflowName").style.display="none";
  }
else
{
   document.getElementById("isWorkflow").value="true";
   document.getElementById("cancelQuery").innerHTML = '<a href="javascript:showWorkFlowWizard();"><img  alt="Cancel" src="images/advancequery/b_cancel.gif" border="0" /></a>';
}
 
 
 
 var isCountQuery= document.getElementById("isCountQuery");

  
  if(isCountQuery==null)
 {
  // document.getElementById("parameterlist").style.height="20px";
   document.getElementById("checkbox2").style.display="none";
   document.getElementById("parameterlist").style.display="none";
 }
 else
 {
    showAttributes1(document.getElementById('showAll')); 
 }


if("<%=isworkflow%>"!="true")
  {
	  document.getElementById("workflowName").style.display="none";
  }
else
{
   document.getElementById("isWorkflow").value="true";
   document.getElementById("cancelQuery").innerHTML = '<a href="javascript:showWorkFlowWizard();"><img  alt="Cancel" src="images/advancequery/b_cancel.gif" border="0" /></a>';
}
</script>	
	</body>
</html:html>
