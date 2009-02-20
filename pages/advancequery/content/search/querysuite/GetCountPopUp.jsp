<%@ page import="java.util.*"%>
<%@ page language="java" isELIgnored="false" %>
<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="edu.wustl.common.beans.NameValueBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script>
<script src="jss/advancequery/queryModule.js"></script>
<html>
<head>
<title>CIDER: Clinical Investigation Data Exploration Repository</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script type="text/JavaScript">
<!--

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    //Calling the GetCount Ajax function to continuosly fetch the count of the executing query
	getCountAjaxAction(-1);
}

function retrieveRecentQueries()
{
	document.forms['form2'].notify.value="true";
	parent.pvwindow.hide();
	parent.document.forms[0].action="\ShowDashboard.do"
	parent.document.forms[0].submit();
}
//-->
</script>
<script type="text/javascript">
<!--

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body onLoad="MM_preloadImages('images/advancequery/m_home_act.gif')">
<script type="text/javascript" src="wz_tooltip.js"></script>
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<c:set var="currentSelectedProject" value="${requestScope.categorySearchForm.currentSelectedProject}"/>
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
      <tr>
        <td colspan="2" align="center" valign="top"><table width="98%" border="0" cellspacing="0" cellpadding="4">
          <tr>
            <td height="5" colspan="2"></td>
          </tr>
          <tr >
            <td height="25" valign="middle" nowrap><span class="content_txt">Execution Status: <strong id="StatusId"></strong></span></td>
            <td align="right" valign="middle">
			<form id="form1" name="form1" method="post" action="">
              <a href="javascript:abortExecutionAjaxAction();"><img border='0' src="images/advancequery/b_abort_execution.gif" alt="Abort Execution" width="116" height="23"></a>&nbsp;<a href="javascript:retrieveRecentQueries();"><img border='0' src="images/advancequery/b_notify_me.gif" alt="Notify me when done" width="146" height="23"></a>
            </form></td>
          </tr>
          <tr>
            <td height="25" colspan="2" valign="bottom"><span class="content_txt"><b id="CountId"></b>  results found. </span></td>
          </tr>
          <tr>
		  <c:choose>
			 <c:when test="${currentSelectedProject==''}">
	            <td colspan="2" valign="top" class="tr_color_lgrey"><span id="NoteId" class="content_txt">Note: The query "${requestScope.categorySearchForm.queryTitle}" is executed without selecting a project, so results from all facilities are included in the count.  If you would want to execute this query for a specific project, you can select the project below and the results will be filtered based on the project rules.</span> </td>								
			 </c:when>
			 <c:otherwise>
				<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
					<c:if test="${project.value eq currentSelectedProject}">
						<c:set var="currentSelectedProjectName" value="${project.name}"/>
					</c:if>
				</c:forEach>
	            <td colspan="2" valign="top" class="tr_color_lgrey"><span id="NoteId" class="content_txt">Note: The query "${requestScope.categorySearchForm.queryTitle}" is executed for project "${currentSelectedProjectName}".The results will be filtered based on the project rules.</span> </td>
			</c:otherwise>
		</c:choose>
          </tr>
          <tr>
            <td height="10" colspan="2" class="tr_color_lgrey"></td>
          <tr>
            <td colspan="2" class="tr_color_lgrey"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="middle" class="content_txt">Select Project:</td>
                <td valign="middle">&nbsp;</td>
                <td valign="middle"><form name="form2" method="post" action="">
				<html:hidden property="notify" value="false" />
				<html:hidden property="abortExecution" value="false" />
				<html:hidden property="executionId" value="-1" />
				<html:hidden property="isNewQuery" value="false" />
				<html:hidden property="selectedProject" value="${currentSelectedProject}" />
				<html:hidden property="selectedProjectName" value="" />
				<SELECT NAME="getCount" class="textfield" onChange="setProjectData(this,'form2')">
							<c:choose>
								<c:when test="${currentSelectedProject==''}">
									<OPTION VALUE="" selected>Unspecified..
								</c:when>
								<c:otherwise>
									<OPTION VALUE="">Unspecified..
								</c:otherwise>
							</c:choose>
						<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
							<c:choose>
								<c:when test="${project.value eq currentSelectedProject}">
									<OPTION VALUE="${project.value}" selected>${project.name}
								</c:when>
								<c:otherwise>
									<OPTION VALUE="${project.value}">${project.name}
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</SELECT>
				</form></td>
                <td valign="middle">&nbsp;</td>
                <td valign="middle"><form id="form3" name="form3" method="post" action="">
                    <img src="images/advancequery/b_get_count_inact.gif" alt="Get Count" width="84" height="23" onclick="">
                </form></td>
              </tr>
            </table></td>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

