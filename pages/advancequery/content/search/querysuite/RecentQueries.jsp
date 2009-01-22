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
	var showLast=document.getElementById(controlId).value;
	var url='RetrieveRecentQueries.do?requestFor=nextPage&showLast='+showLast;
	document.forms[0].action=url;
	document.forms[0].submit();	
}

function initializeAjaxCall()
{
	var noOfRows=document.getElementById("table1").rows.length;
	for(var i=1;i<=noOfRows;i++)
	{
		recentQueryAjaxCall(document.getElementById("queryExecutionId_"+i).value,i);
	}
}

function recentQueryAjaxCall(executionLogId,index)
{
	var url="RecentQueriesAjaxHandler.do?executionLogId="+executionLogId+"&index="+index;
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
          }
		if(status!="Completed")
			recentQueryAjaxCall(executionId,index);
}
</script>
<body onLoad="initializeAjaxCall()">
	<%int count = 0;%>
	<html:form action="RetrieveRecentQueries">
		<table width="100%" border="0"  cellspacing="1">
		
              <tr class="td_bgcolor_grey">
                <td width="55%"  height='25' valign="middle" class="grid_header_text">Title</td>
                <td  width="15%" valign="middle" class="grid_header_text">Status </td>
                <td   width="10%" valign="middle" class="grid_header_text">Result</td>
                <td   width="20%" valign="middle" class="grid_header_text">Date</td>
              </tr>

			  <tr>
				<table width="100%" border="0" cellpadding="4" cellspacing="1">
					<tbody id="table1">
					<c:forEach var="recentQueriesBean" items="${requestScope.recentQueriesBeanList}">
						<%
					  count++;
					%>

					 <tr class="content_txt">
							<td  width="55%" height='20' styleClass="content_txt"><c:out value='${recentQueriesBean.queryTitle}'/></td>
							<td   width="15%" styleClass="content_txt">
							<label id="StatusId_<%=count%>"></label>
							</td>
							<td  width="10%" styleClass="content_txt">
							<label id="CountId_<%=count%>"></label>
								<input type="hidden" name="queryExecutionId"  id="queryExecutionId_<%=count%>" value="${recentQueriesBean.queyExecutionId}"/>
							</td>
							<td   width="20%" styleClass="content_txt"><c:out value='${recentQueriesBean.queryCreationDate}'/></td>         
						</tr>
					  </c:forEach>	
					</tbody>
				</table>
			</tr>

			<tr>
				<td width="10">&nbsp;</td>
				<td width="33%"><span class="content_txt">Show Last:
				  </span>    
					<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${requestScope.numResultsPerPage}">
							 <c:forEach var="item" items="${requestScope.resultsPerPageOptions}" varStatus="i">
									<html:option value="${item}">${item}</html:option>
							 </c:forEach>
				
					</html:select>
				</td>
				  <td width="10">&nbsp;</td>
				   <td width="10">&nbsp;</td>
         </tr>
	</table>
	<c:set var="totalPages" value="${sessionScope.totalPages}"/>  
		<jsp:useBean id="totalPages" type="java.lang.Integer"/>
	<c:forEach var="pageCoutner" begin="1" end="${totalPages}">
			<c:set var="linkURL">
				RetrieveRecentQueries.do?requestFor=nextPage&pageNum=<c:out value="${pageCoutner}"/>
				&showLast=<c:out value="${requestScope.numResultsPerPage}"/>
			</c:set>
			<jsp:useBean id="linkURL" type="java.lang.String"/>
			<c:if test="${sessionScope.pageNum == pageCoutner}">
					<c:out value="${pageCoutner}"/> 
			</c:if>
			<c:if test="${sessionScope.pageNum != pageCoutner}">
				<a class="bluelink" href="<%=linkURL%>"> 
					|<c:out value="${pageCoutner}"/> 
				</a>
			</c:if>
		</c:forEach>

	</html:form>
</body>