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
	String isSaveButtonDisable = "";
	 String action;
	 request.setAttribute("pageOf",pageOf);
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
%>



<html:html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
		<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
		<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css" />
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/queryModule.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/script.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/overlib_mini.js"></script>
		<script language="JavaScript" type="text/javascript" src="jss/advancequery/calender.js"></script>
		<c:if test="${querySaved eq 'true'}">
          <script>
           window.onunload=function()
		   {
		        var forwardTo="";
				var parentWindowForm = window.opener.document.forms[0];
				if("<%=isworkflow%>"=="true")
			     forwardTo="RedirectToWorkflow.do";
				else
				{
				 forwardTo="ShowDashboard.do?requestFrom=MyQueries";
				 parentWindowForm.requestFrom.value="MyQueries"; 
				}
				parentWindowForm.action = forwardTo; 
                parentWindowForm.submit(); 
               // window.self.close();
		   }
		   		   
		  </script>
       </c:if>
		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>
			<bean:message key="savequery.queryInformationTitle"/>
		</title>
	</head>

	<body>
		<html:form styleId='saveQueryForm' action='<%=action%>'>
		<table summary="" cellpadding="0" cellspacing="0" align="center" border="0"  width="100%" style="padding:5px;">

		  <td>
			<table summary="" cellpadding="0" cellspacing="0"  border="0" align="center" width="100%" class="login_box_bg" >
					
				<tr class="table_header_query" >
					<td colspan='3' height="26" style="padding-left:5px;">
					<img border='0' src="images/advancequery/t_query_info.gif">
					</td>
				</tr>
				<tr>
					<td style="padding-left:5px;padding-bottom:5px;padding-top:5px;"  colspan="3" class='messagetexterror' nowrap><html:errors/>
					</td>
					<tr>
				<tr>
				<td colspan="3"  valign="top" style="padding-left:5px;"><span class="red_star">*</span> <span class="small_txt_grey">Denotes mandatory fields</span></td>
				</tr>
				<tr><td colspan="3" style="padding:0 10px 10px 10px;"><table width="100%" cellpadding="0" cellspacing="0"> <tr><td class="td_greydottedline_horizontal" height="1"></td></tr></table></td>
				</td></tr>
					<tr id="workflowName" >
						<td class="content_txt_bold" colspan="2" style="padding-left:5px;padding-bottom:10px;">
								<bean:message key="workflow.name"/>:
					</td>
					<td style="padding-bottom:10px;">
						<input type="text" name="workflowName" value="<%=workflowName%>" class="textfield_inactive" disabled="disabled" />
					</td>
				  </tr>
				  <tr>
						<td class="content_txt_bold" colspan="2" style="padding-left:5px;" >
					<bean:message key="query.title"/><span class="red_star">*</span>:
					</td>
					<td >
						<html:text       styleClass="textfield_QueryTitle"      styleId="title" property="title" />
					</td>
					
				</tr>
				<tr><td height="10"></td></tr>
				<tr>
					<td colspan="2" class="content_txt_bold" valign="top" style="padding-left:5px;padding-bottom:5px;"><bean:message key="query.description"/>:</td>
					<td style="padding-bottom:5px;" class="formFieldNoBordersQuery">
						<html:textarea styleClass="textfield_undefined"   cols="80" rows="5"  property="description"> </html:textarea>
					</td>
				</tr>
				<!--<tr class="td_subtitle">
					<td colspan='3'  height="25" class="blue_title" style="padding-left:5px;">
						<bean:message key="savequery.setConditionParametersTitle" />
					</td>
				</tr>-->
				<tr><td colspan="3" style="padding:5px 10px 10px 10px;"><table width="100%" cellpadding="0" cellspacing="0"> <tr><td class="td_greydottedline_horizontal" height="1"></td></tr></table></td>
				</td></tr>
				<tr>
					<td style="padding-left:10px;padding-top:5px;" class="content_txt" colspan='3' height='20'>
					<!--<div  style="width:100%; max-height:300px; min-height:50px; overflow-y:auto;">
					<table cellpadding="0" cellspacing="0" bgcolor="#cccccc">
					<tr>
					<td>
						<%=request.getAttribute(Constants.HTML_CONTENTS)%>
				   </td>
				   </tr>
				   </table>
				   </div>-->
				   <html:checkbox property="shareQuery" styleId="shareQuery"/>&nbsp;				
				   <bean:message
							key="query.shareQuery.permission" /></td>

					</td> 
				</tr>
				<tr>
				  <td style="padding-top:10px;padding-bottom:10px;padding-left:10px;"  colspan='3'  align="left">
						    <input type="hidden" name="queryString" id="queryString" value=""/>
						    <input type="hidden" name="buildQueryString" id="buildQueryString" value=""/>
							<!--input type="button" name="preview" value="Preview" class="actionButton"  disabled="true"/-->
							<c:choose>
								<c:when test="${querySaved eq 'true'}">
							 <!--	<img src="images/advancequery/b_close.gif" onclick="closeSaveQueryWindow()" /> -->
								</c:when>
								<c:otherwise>
									<a href="javascript:produceSavedQuery();" ><img border='0' src="images/advancequery/b_save.gif" <%=isSaveButtonDisable%> /></a>
									<!--<a href="javascript:closeSaveQueryWindow();" ><img border='0' src="images/advancequery/b_close.gif" /></a>-->
									
								</c:otherwise>
							</c:choose>
				  </td>
				</tr>
				</table>
            </td>
          </tr>
		  
		 	
		 </table>
		</html:form>
<script>
  
if("<%=isworkflow%>"!="true")
  {
     
	  document.getElementById("workflowName").style.display="none";
   }
</script>	
	</body>
</html:html>
