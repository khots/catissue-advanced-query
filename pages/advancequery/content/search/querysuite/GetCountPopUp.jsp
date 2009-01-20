<%@ page import="java.util.*"%>
<%@ page language="java" isELIgnored="false" %>
<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="edu.wustl.common.beans.NameValueBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
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
    callGetCountAjaxAction(-1);
}
function callGetCountAjaxAction(executionId)
{
	var url="GetCountAjaxHandler.do?executionId="+executionId;
	var request=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	var handlerFunction = getReadyStateHandler(request,responseHandler,true); 
	request.onreadystatechange = handlerFunction; 
	request.open("POST",url,true);    
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	request.send("");
}

function responseHandler(response)
{
	  var jsonResponse = eval('('+ response+')');
          if(jsonResponse.resultObject!=null)
          {
			var queryCount = jsonResponse.resultObject.queryCount;
			var status = jsonResponse.resultObject.status;
			var executionId = jsonResponse.resultObject.executionId;
					 
			//alert("queryCount:::::= "+ queryCount + "  status:::" + status +"  executionId:::" + executionId);
					 	 
			if(queryCount!=-1)
			{
				var StatusObject=document.getElementById("StatusId");
				var CountObject=document.getElementById("CountId");

				if(StatusObject!=null)
				{
					StatusObject.innerHTML= status;
				}
				if(CountObject!=null)
				{
					CountObject.innerHTML= queryCount;
				}
			}	
          }
		if(status!="Completed")
			callGetCountAjaxAction(executionId);
}
function retrieveRecentQueries()
{
	parent.pvwindow.hide();
	parent.document.forms[0].action="\RetrieveRecentQueries.do"
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
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
      <tr>
        <td colspan="2" align="center" valign="top"><table width="98%" border="0" cellspacing="0" cellpadding="4">
          <tr>
            <td height="5" colspan="2"></td>
          </tr>
          <tr >
            <td height="25" valign="middle" nowrap><span class="content_txt">Execution Status: <strong id="StatusId"></strong></span></td>
            <td align="right" valign="middle"><form name="form1" method="post" action="">
			<a href="javascript:retrieveRecentQueries()">
              <img src="images/advancequery/b_abort_execution.gif" alt="Abort Execution" width="116" height="23">&nbsp;<img src="images/advancequery/b_notify_me.gif" alt="Notify me when done" width="146" height="23">
			  </a>
            </form></td>
          </tr>
          <tr>
            <td height="25" colspan="2" valign="bottom"><span class="content_txt"><b id="CountId"></b> results found. </span></td>
          </tr>
          <tr>
            <td colspan="2" valign="top" class="tr_color_lgrey"><span class="content_txt">Note: This query is executed without selecting a project, so results from all facilities are included in the count.  If you would want to execute this query for a specific project, you can select the project below and the results will be filtered based on the project rules.</span> </td>
          </tr>
          <tr>
            <td height="10" colspan="2" class="tr_color_lgrey"></td>
          <tr>
            <td colspan="2" class="tr_color_lgrey"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="middle" class="content_txt">Select Project:</td>
                <td valign="middle">&nbsp;</td>
                <td valign="middle"><form name="form2" method="post" action="">
				<SELECT NAME="getCount" class="textfield">
						<c:set var="selectedProject" value="${requestScope.categorySearchForm.currentSelectedProject}"/>
						<c:if test="${selectedProject==''}">
								<OPTION VALUE="" selected>--Select--
						</c:if>
						<c:forEach var="project" items="${requestScope.categorySearchForm.projectsNameValueBeanList}">
							<c:choose>
								<c:when test="${project.value eq selectedProject}">
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
                <td valign="middle"><form name="form3" method="post" action="">
                    <img src="images/advancequery/b_get_count_inact.gif" alt="Get Count" width="84" height="23">
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

