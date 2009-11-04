<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/PagenationTag.tld" prefix="custom"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/ajax.js"></script>
<script type='text/JavaScript' src='jss/advancequery/scwcalendar.js'></script>
<script type="text/javascript" src="jss/jquery-1.3.2.js"></script>
<script>
jQuery().ready(function(){
	$('#paramHeaderDiv').toggle(function(){hideDiv('paramDiv')},function(){showDiv('paramDiv')});
	$('#constrHeaderDiv').toggle(function(){showDiv('constrDiv')},function(){hideDiv('constrDiv')});
	hideAllDivs();
	createAlternateSripts();

		});
		//this method creates the alternate sripts.
function createAlternateSripts()
{
	if(document.getElementById('constrDiv')!=null&&document.getElementById('constrDiv')!=undefined)
	{
		$("#constrDiv:table tr:even").addClass("rowBGGreyColor1");
	}
}

function showDiv(elementid)
{
	if(elementid=='constrDiv')
	{
		  var x = document.getElementById("constrImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);
		  
		  SlideDownRun("constrDiv","dataTable");
	}
	else
	{
		  var x = document.getElementById("paramImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);
		 
		 SlideDownRun("paramDiv","countTable");
	}	
	return;

} 
function hideDiv(elementid)
{
	if(elementid=='constrDiv')
		{
		 var x = document.getElementById("constrImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_close.png";
		  x.setAttribute("src", v);
	 	document.getElementById("constrDiv").style.display="none";
		}
		else
		{
			var x = document.getElementById("paramImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_close.png";
		  x.setAttribute("src", v);
		document.getElementById("paramDiv").style.display="none";
		}
				

}
function hideAllDivs()
{
	var constrDiv=document.getElementById("constrDiv");
	
	if(constrDiv!=null&&constrDiv!=undefined)
	{
		constrDiv.style.display="none";
	}
	
}


function SlideDownRun(obj,tableObj)
{
   slider = document.getElementById(obj);
   slider.style.display="block";
}




function beforepopupclose()
{
	window.location.reload();
}


</script>
<%-- Imports --%>
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants"%>
<%
			ParameterizedQuery query = (ParameterizedQuery) session
			.getAttribute(Constants.QUERY_OBJECT);
%>
<%@page
	import="edu.wustl.common.querysuite.queryobject.impl.ParameterizedQuery"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css"/>
<script src="jss/advancequery/queryModule.js"></script>
<script src="jss/advancequery/calender.js"></script>
<script type="text/javascript" src="jss/javaScript.js"></script>
<script>
		   function GotoDashboardAction()
		   {
		      var frm = document.forms[0];
			  frm.action="ShowDashboard.do?requestFrom=MyQueries";
			  frm.submit();
		   }
		</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="savequery.queryConditionTitle" /></title>
</head>
 <body onunload='closeWaitPage()'>
 

  <html:errors />
<input type="hidden" id="executeQuery" value ="executeQuery"/>
<table border="0" cellpadding="0" cellspacing="0" class="contentLayout" >
	<tr>
	<td>
	<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="login_box_bg" height="100%">
		       <tr>
		          <td height="28" colspan="2" class="table_header_query">
				  <!-- <img src="images/advancequery/execute_query_heading.gif"  altText="Execute Query"/>-->
				  <span class="PageHeaderTitle">Execute Query</span>
		         </td>
		     </tr>
		
			<tr height="5px"><td colspan="2"></td></tr>
			<tr>
			<td valign="top" align="center" width="98%">
			<html:form styleId='saveQueryForm' action="UpdateQueryAction.do" style="margin:0;padding:0;">
			<c:set var="latestProject" value="${requestScope.latestProject}" scope="request"/>
			<input type="hidden" name="currentPage" id="currentPage" value="SaveQuery">
			<table width="98%" border="0" cellspacing="0" cellpadding="0">
					<tr  valign="top">
					 <td colspan="2" > 
					 	<table summary="" cellpadding="0" cellspacing="0" border="0"  
							width="100%">
							<tr>
								<td width="8%" valign="top" class="content_txt_bold">
								  <bean:message	key="query.title" />:
								 </td>
											 
								 <td  id = "queryTitle1" class="content_txt_bold" align="left"  valign="top">
								 <!--  <input  type="text" name="workflowName" value=" <%=query.getName()%>" size="50" valign="middle" class="textfield_inactive" disabled="disabled" /> --> <label valign="top" id="queryLabel" ><%=query.getName()%></label>
								 </td>
							</tr>
							<tr height="5px"> <td colspan="2"></td> </tr>
							<tr>
								<td  valign="top" width="8%" class="content_txt_bold">
									<bean:message	key="query.description" />:
								</td>
								<td  class="content_txt_bold" align="left">
								 <% String desc = query.getDescription();
								 {
									 if(desc ==null || desc.length()==0)
										 desc="Not Available";
								 } %>
								 <%= desc%>
								 </td>
							</tr>
							<tr>
								<td colspan="2" height="5"></td>
							</tr>
						</table>
						</td>
					</tr>
					<!--	<tr>
						<td align="left" colspan="2">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
											<td height="25" valign="middle" class="td_subtitle"><span
												class="blue_title"><img
												src="../design_HTML/images/spacer.gif" width="5" height="1"
												align="absmiddle">Query Parameters</span>
											</td>
									</tr>
									<tr>
											<td class="td_greydottedline_horizontal" height="10"></td>
									</tr>
									</table>
						</td>
					</tr>-->
					<tr><td colspan="2" style="padding-left:5px;"> <div id="validationmsg"></div></td>
					<!--<tr height="3px"><td colspan="2"></td></tr>-->
					<tr  valign="top" >
						<td colspan ="2" height="150px">
						<div  class="login_box_bg" id="parameterlist" style="*overflow-x:auto;">
						   <%=request.getAttribute(Constants.HTML_CONTENTS)%></div>
						</td>
					</tr>
					<tr height="10px">
						<td><!--  	<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" height="4%">
										<tr>
											<td class="formTitle">
												<bean:message key="savequery.definedConditionTitle" />
											</td>
										</tr>
									</table>
								--></td>
					</tr>
					<tr >
			             <!--td style="padding:0 5px 5px 0;" valign="middle" class="content_txt_bold">Select Project:</td -->
						<td align="left" width="8%" >
						<label for="Select Project"  class="content_txt_bold" nowrap="nowrap" align="left" width="15%" valign="top"> Select Project: </label>
						</td>
						 <td  valign="middle" >
			 
						      <!--table summary="" cellpadding="3" cellspacing="0" border="0" width="100%">
							   <tr>
									<td style="padding:0 5px 5px 0;" valign="middle" class="content_txt_bold" align="left" width="15%">Select Project: </td -->
							   
							        <!--td align="left" width="85%" style="padding-left:5px;" !-->
										<SELECT NAME="dropdown" id="selectedProject" class="textfield" onChange="setProjectData(this,'saveQueryForm')" onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
											<OPTION title="Unspecified" VALUE="">Unspecified
											<c:forEach var="project" items="${requestScope.saveQueryForm.projectsNameValueBeanList}">
											 <logic:equal name="latestProject"  value="${project.value}">
												<OPTION title="${project.name}" VALUE="${project.value}" selected="selected">${project.name}
											 </logic:equal>
											 <logic:notEqual name="latestProject"  value="${project.value}">
												<OPTION VALUE="${project.value}" title="${project.name}">${project.name}
											</logic:notEqual>
											</c:forEach>
			                        <!--/td>
			                     </tr> 
			                   </table -->
					    </td>
					</tr>

			       <tr>
						<td height="10" colspan ="2">&nbsp;</td>
					</tr>

					<tr >
						<!--td align="left"><input type="hidden" name="queryString"
							value="" /> <input type="button" name="execute" value="Execute" class="actionButton" 
							onClick="ExecuteSavedQuery()" /> <input type="button" name="cancel" class="actionButton" 
							value="Cancel" onClick="GotoDashboardAction();" /></td -->
			               <td align="left" width="8%" ><a id="getCount" href="javascript:ExecuteSavedQuery('<%=query.getId()%>');" ><img alt="Get Count" border='0' src="images/advancequery/b_get_count.gif" />
							</a></td>
			                 
			                <td align="left" style="padding-left:5px;" ><a id="cancel" href="javascript:GotoDashboardAction();" ><img alt="Cancel" border='0' src="images/advancequery/b_cancel.gif" />
							</a>    
						   </td>
					</tr>
					 <tr height="20%">  <td colspan="2"></td></tr> 
			</table>
			</td>
			<input type="hidden" id="selectedProjectName" value =""/>
   			</html:form>
			</tr>
		</table>
	
	</td>
	</tr>
</table>

	
</body>
<script>  
  if( "<%=request.getAttribute(Constants.HTML_CONTENTS)%>" == "")
  {
    document.getElementById("parameterlist").style.display = "none";
   
  }
</script>
</html>
