<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">

<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%-- Imports --%>
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants, org.apache.struts.Globals"
%>
<%@ page import="org.apache.struts.action.ActionMessages, edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.domain.Workflow"%><html>
<head>
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<script>
function rowClick(id)
{
	var url = "SearchObject.do?pageOf=pageOfWorkflow&id="+id;
	window.open(url,"_top");
}

</script>
</head>
<body>

	<table cellpadding='0' cellspacing='0' border='0' width='99%' class='contentPage'>
    
	     <tr class="td_bgcolor_grey">
        	<td  valign="middle" class="grid_header_text">identifier</td>
            <td valign="middle" class="grid_header_text">Workflow Name</td>
            	
        </tr>
	<c:forEach var="workflow" items="${requestScope.workflowList}">
	  <jsp:useBean id="workflow" type="edu.wustl.query.domain.Workflow" />
        <tr>
        	<td height='20' styleClass="content_txt"><c:out value='${workflow.id}'/></td>
            <td height='20'  styleClass="content_txt">
					<a href="javascript:rowClick(<c:out value='${workflow.id}'/>)" class="bluelink">
						<c:out value='${workflow.name}'/>
					</a>
			</td>
 			
        </tr>
      </c:forEach>
    </table>

</body>
</html>