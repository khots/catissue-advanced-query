<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="edu.wustl.common.beans.RecentQueriesBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page language="java" isELIgnored="false"%>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<script>
function changeResPerPage(controlId)
{
	var pageOf ='${requestScope.pageOf}';
	var showLast=document.getElementById(controlId).value;
	var url='RetrieveRecentQueries.do?requestFor=nextPage&showLast='+showLast+'&pageOf='+pageOf;;
	document.forms[0].action=url;
	document.forms[0].submit();	
}

function initializeAjaxCall()
{
	var noOfRows=document.getElementById("table1").rows.length;
	for(var i=1;i<=noOfRows;i++)
	{
		var status=document.getElementById("queryStatus_"+i).value;
		var hasPrivilege=document.getElementById("isSecurePrivilege_"+i).value;
		if(status!="Completed"&&status!="Cancelled"&&status!="Query Failed")
		{
			recentQueryAjaxCall(document.getElementById("queryExecutionId_"+i).value,i,hasPrivilege);
		}
	}
}

function recentQueryAjaxCall(executionLogId,index,hasPrivilege)
{
	var url="RecentQueriesAjaxHandler.do?executionLogId="+executionLogId+"&index="+index+"&queryPrivilege="+hasPrivilege;
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
			var index = jsonResponse.resultObject.index;
			var hasPrivilege = jsonResponse.resultObject.queryPrivilege;
			if(queryCount!=-1)
			{
				var StatusObject=document.getElementById("StatusId_"+index);
				var CountObject=document.getElementById("CountId_"+index);

				if(StatusObject!=null)
				{
					StatusObject.innerHTML= status;
				}
				if(CountObject!=null)
				{
					CountObject.innerHTML= queryCount;
				}
			}	
			if(status!="Completed"&&status!="Cancelled"&&status!="Query Failed")
			  {
					recentQueryAjaxCall(executionId,index,hasPrivilege);
			  }
          }

}
</script>
<body onLoad="initializeAjaxCall()" >
	<%int count = 0;%>
	<html:form action="RetrieveRecentQueries">
<logic:notEqual name="records" value="0">
 <table height="82%" width="100%" cellpadding="3" cellspacing="0">
	<tr valign="top">
		<td>
		 <table width="100%" bgcolor="#cccccc" cellpadding="0" cellspacing="0">
			  <tr>
			  <td><table width="100%" border="0"  cellspacing="1" cellpadding="4" >
		              <tr class="td_bgcolor_grey">
                <td width="55%"  height='25' valign="middle" class="grid_header_text">Query Title</td>
                <td  width="15%" valign="middle" class="grid_header_text">Status </td>
                <td   width="10%" valign="middle" class="grid_header_text">Result</td>
                <td   width="20%" valign="middle" class="grid_header_text">Creation Date</td>             	 
				
					<tbody id="table1">
					<c:forEach var="recentQueriesBean" items="${requestScope.recentQueriesBeanList}">
						<%
					  count++;
					%>
					 <tr bgcolor="ffffff" class="content_txt">
							<td  width="55%"  styleClass="content_txt" valign="top"><c:out value='${recentQueriesBean.queryTitle}'/></td>
							<td   width="15%" styleClass="content_txt" valign="top">
							<label id="StatusId_<%=count%>">${recentQueriesBean.status}</label>
							</td>
							<td  width="10%" styleClass="content_txt" valign="top">
							<label id="CountId_<%=count%>">${recentQueriesBean.resultCount}</label>
								<input type="hidden" name="queryExecutionId"  id="queryExecutionId_<%=count%>" value="${recentQueriesBean.queyExecutionId}"/>
								<input type="hidden" name="queryStatus"  id="queryStatus_<%=count%>" value="${recentQueriesBean.status}"/>
								<input type="hidden" name="isSecurePrivilege"  id="isSecurePrivilege_<%=count%>" value="${recentQueriesBean.isSecurePrivilege}"/>
							</td>
							<td   width="20%" styleClass="content_txt" valign="top"><c:out value='${recentQueriesBean.queryCreationDate}'/></td>         
						</tr>
					  </c:forEach>	
					</tbody>				
				</table>
				</td>
				</tr>
				</table>
			
			</tr>

			</table>
</td>
</tr>
<tr valign="bottom">
<td >
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="tr_color_lgrey">				
				<td align="left" height="30" style="padding-left:5px;"><span class="content_txt_bold">Show Items:</span>
				<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${requestScope.numResultsPerPage}" styleClass="textfield_undefined">
							 <c:forEach var="item" items="${requestScope.resultsPerPageOptions}" varStatus="i">
									<html:option value="${item}">${item}</html:option>
							 </c:forEach>
				
					</html:select> 
				</td>				  
		</tr>
		 </table>
</td>
</tr>
</table>
</logic:notEqual>
<table width="100%" cellpadding="4" cellspacing="0">
<logic:equal name="records" value="0">
					<td class="content_txt_bold" style="padding-left:5px;" valign="top">
					<bean:message key="meassges.emptyquery"/>
					</td>	
</logic:equal >
</table>
	</html:form>
</body>