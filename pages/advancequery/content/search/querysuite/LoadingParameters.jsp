<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script>

/*To show the image on the loading frame*/
var loadingImageTag="<img src='../images/advancequery/Parameter_Loading.gif' alt='Loading Parameters ...'  title='Loading Parameters ...' >";

function forwardToLocation()
{

	var queryString='${requestScope.queryIdString}';
	 var workflowId=<%=request.getAttribute("workflowId")%>;
	 var projectId=<%=request.getAttribute("selectedProject")%>	;
	 var queryType='<%=request.getAttribute("queryType")%>';
	 	var queryType1='${requestScope.queryType}';
	//var workflowId='${requestScope.workflowId}';
	//alert("workflowId="+workflowId);
	location.href ='ParamerizedQueryPopUp.do?queryIdString='+queryString+"&workflowId="+workflowId+"&selectedProject="+projectId+"&queryType="+queryType;
	showLoadingFrame();
}
</script>
<%@includefile="/pages/CommonLoadingFrame.jsp" %>