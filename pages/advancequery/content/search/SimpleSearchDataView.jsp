<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/PagenationTag.tld" prefix="custom" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import = "edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>
<%@ page language="java" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="css/advancequery/catissue_suite.css" rel="stylesheet" type="text/css" />
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />
<script src="jss/advancequery/script.js"></script>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<script language="JavaScript" type="text/javascript" src="jss/advancequery/javaScript.js"></script>
<style>
.active-column-0 {width:30px}
tr#hiddenCombo
{
 display:none;
}
</style>
<head>
<%
	
	 int pageNum = 1;//Integer.parseInt((String)request.getAttribute(Constants.PAGE_NUMBER));
	
	//int totalResults = ((Integer)session.getAttribute(Constants.TOTAL_RESULTS)).intValue();
	int numResultsPerPage = 500;//Integer.parseInt((String)session.getAttribute(Constants.RESULTS_PER_PAGE));
	String pageName = "SpreadsheetView.do";	
	String checkAllPages = (String)session.getAttribute("checkAllPages");
	String status = (String) request.getAttribute("status");	
	String pageOf = (String)request.getAttribute(Constants.PAGE_OF);
	String title = pageOf + ".searchResultTitle";
	boolean isSpecimenData = false;	
	int IDCount = 0;
	%>
		
	 <script> 
	if(navigator.appName == "Microsoft Internet Explorer")
   {
	   document.getElementById("table2").style.height="100%"; 
	   document.getElementById("table1").style.height="100%"; 
   }
   </script>
   <script>
					/* 
						to be used when you want to specify another javascript function for row selection.
						useDefaultRowClickHandler =1 | any value other than 1 indicates you want to use another row click handler.
						useFunction = "";  Function to be used. 	
					*/
					var useDefaultRowClickHandler =1;
					var useFunction = "search";	
				 
				</script>
 
</head>
<body>
<html:form action="QueryWizard.do">
<%@ include file="/pages/advancequery/common/ActionErrors.jsp" %>
<!-------new--->
 <!--Prafull:Added errors tag inside the table-->
 <table id="table1" width="100%" border="0" height="96%" cellpadding="0" cellspacing="0"  >
 	<tr height="100%">
	<td valign="top">
 	 <table id="table2" width="100%" border="0"  cellpadding="3" height="98%" cellspacing="0" class="whitetable_bg">
	
  


		<!-- 
			Patch ID: Bug#3090_28
			Description: The width of <td> are adjusted to fit into the iframe. 
			These changes were made to remove the extra white space on the data view/spreadsheet view page. 
		-->
      <tr id="MsgRow" style="display:none;">
	    <td width="100%" align="left" valign="top" class="info_msg">
		<bean:message key="advanceQuery.noRecordsFound"/></td>
      </tr>

	  <tr id="loadingImage" height="5%" >
		 <td width="100%" align="left" valign="top" style="padding-left:10px;" >
		  <img alt="Loading" src="images/advancequery/load.gif" >
		 </td>
	 </tr>
	 <tr valign="top" width="100%" height="95%" id="speradSheetRow">
	 <td  width="100%" valign="top" height="95%">
<!--  **************  Code for New Grid  *********************** -->
				
			<%@ include file="/pages/advancequery/content/search/AdvanceGrid.jsp" %> 
			<!--  **************  Code for New Grid  *********************** -->
	  </td>
	</tr>
		
<html:hidden property="operation" value=""/>

</table>
</td>
</tr>
</table>
</html:form>
</body>

