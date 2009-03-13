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
	
	int totalResults = ((Integer)session.getAttribute(Constants.TOTAL_RESULTS)).intValue();
	int numResultsPerPage = 500;//Integer.parseInt((String)session.getAttribute(Constants.RESULTS_PER_PAGE));
	String pageName = "SpreadsheetView.do";	
	String checkAllPages = (String)session.getAttribute("checkAllPages");
	List<String> columnList = (List<String>) session.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);
	if(columnList==null)
		columnList = (List<String>) request.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);

	List dataList = (List) request.getAttribute(Constants.PAGINATION_DATA_LIST);
	String[] selectedColumnNames =null;
	if(columnList!=null)
	{
		selectedColumnNames = new String[columnList.size()];
		columnList.toArray(selectedColumnNames);
	}
	
	String pageOf = (String)request.getAttribute(Constants.PAGE_OF);
	String title = pageOf + ".searchResultTitle";
	boolean isSpecimenData = false;	
	int IDCount = 0;
	
	%>
		

	<script language="javascript">
		var colZeroDir='ascending';

		function getData()
		{	//ajax call to get update data from server
			var request = newXMLHTTPReq();			
			var handlerFunction = getReadyStateHandler(request,displayValidationMessage,true);	
			request.onreadystatechange = handlerFunction;			
			var actionURL = "updateSessionData=updateSessionData";		
			var url = "ValidateQuery.do";
			request.open("POST",url,true);	
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send(actionURL);		
		}

		function displayValidationMessage(message)
		{
			//var message contains space " " if the message is not to be shown.   
			if (message != null && message == " ") 		// do not show popup
			{
				onAddToCart();
			}
			else
			{
				var isChecked = updateHiddenFields(); // if atleast one check box is checked.
				if (isChecked == "true")
				{
					var r=confirm(message);
					if (r==true)
					{
						onAddToCart();
					}
				}
				else
				{
					alert("Please select at least one checkbox");
				}
			}
		}
		

		function onExport()
		{
			var isChecked = updateHiddenFields();
			  var pageNum = "<%=pageNum%>";
			var chkBox = document.getElementById('checkAll');
			var isCheckAllAcrossAllChecked = chkBox.checked;
		    if(isChecked == "true")
		    {
				var action = "SpreadsheetExport.do?pageNum="+pageNum+"&isCheckAllAcrossAllChecked="+isCheckAllAcrossAllChecked ;
				document.forms[0].operation.value="export";
				document.forms[0].action = action;
				document.forms[0].target = "_blank";
				document.forms[0].submit();
				document.forms[0].target = "_self";
			}
			else
			{
				alert("Please select at least one checkbox");
			}
		}

		var selected;

		function addCheckBoxValuesToArray(checkBoxName)
		{
			var theForm = document.forms[0];
		    selected=new Array();
		
		    for(var i=0,j=0;i<theForm.elements.length;i++)
		    {
		 	  	if(theForm[i].type == 'checkbox' && theForm[i].checked==true)
			        selected[j++]=theForm[i].value;
			}
		}
		
		

        //Commented out By Baljeet...
		
		 
				
		function callAction(action)
		{
			document.forms[0].action = action;
			document.forms[0].submit();
		}
		function setCheckBoxState()
		{
		   if(document.getElementById('checkAll'))
		   {
			var chkBox = document.getElementById('checkAll');
			var isCheckAllAcrossAllChecked = chkBox.checked;
		<%	if(checkAllPages != null && checkAllPages.equals("true"))
			{ %>
			chkBox.checked = true;
				rowCount = mygrid.getRowsNum();
				for(i=1;i<=rowCount;i++)
				{
					var cl = mygrid.cells(i,0);
					if(cl.isCheckbox())
					cl.setChecked(true);
				}
		<%	} %>
		}
		}

</script>
</head>
<body onload="setCheckBoxState()" height="100%">
<%@ include file="/pages/advancequery/common/ActionErrors.jsp" %>
<!-------new--->
 <!--Prafull:Added errors tag inside the table-->

 <table id="table1" width="100%" border="0" height="96%" cellpadding="0" cellspacing="0"  >
 
	<tr height="100%">
		<td valign="top">
 	 <table id="table2" width="100%" border="0"  cellpadding="3" height="98%" cellspacing="0" class="whitetable_bg">
	
   <script> 
	if(navigator.appName == "Microsoft Internet Explorer")
   {
	   document.getElementById("table2").style.height="100%"; 
	   document.getElementById("table1").style.height="100%"; 
   }
   </script>
	  
	 

<html:form action="QueryWizard.do" style="margin:0;padding:0;height:100%;">
<html:hidden property="checkAllPages" value=""/>	

	<%
		if((dataList == null || dataList.size()==0) && pageOf.equals(Constants.PAGEOF_QUERY_RESULTS))
		{
		%>
			<span class="info_msg"><bean:message key="advanceQuery.noRecordsFound"/></span>
		<%}
		else if(dataList != null && dataList.size() != 0)
		{
	%>
		<!-- 
			Patch ID: Bug#3090_28
			Description: The width of <td> are adjusted to fit into the iframe. 
			These changes were made to remove the extra white space on the data view/spreadsheet view page. 
		-->
		<!-- <tr height="3%">
			 <td align="left" class="tr_bg_blue1">
				<span class="blue_ar_b"> &nbsp;<bean:message key="<%=title%>" />&nbsp;</span>
			 </td>
		</tr> 	-->	
		
		<%
		if(pageOf.equals(Constants.PAGEOF_QUERY_RESULTS))
		{			
			String[] selectedColumns=selectedColumnNames;
		%>
		
		<tr id="hiddenCombo" rowspan="4" style="bgcolor:blue;" >
			<td class="black_new" >bfh
	<!-- Mandar : 434 : for tooltip -->
	   			<html:select property="selectedColumnNames" styleClass="selectedColumnNames"  size="1" styleId="selectedColumnNames" multiple="true"
				 onmouseover="showTip(this.id)" onmouseout="hideTip(this.id)">
	   				<%
					for(int j=0;j<selectedColumns.length;j++)
	   				{
	   				%>
						<html:option value="<%=selectedColumns[j]%>"><%=selectedColumns[j]%></html:option>
					<%
	   				}
	   				%>
	   	 		</html:select>
			</td>
		</tr>
		<% 
		} 
		%>
		
		<tr valign="top" width="100%" height="*">
			<td  width="100%" valign="top" height="100%">
<!--  **************  Code for New Grid  *********************** -->
				<script>
					/* 
						to be used when you want to specify another javascript function for row selection.
						useDefaultRowClickHandler =1 | any value other than 1 indicates you want to use another row click handler.
						useFunction = "";  Function to be used. 	
					*/
					var useDefaultRowClickHandler =1;
					var useFunction = "search";	
				</script>
			<!--	<%@ include file="/pages/advancequery/content/search/AdvanceGrid.jsp" %> -->
<!--  **************  Code for New Grid  *********************** -->
			</td>
		</tr>

		<!--<tr width="100%" valign="top" height="15%">
		
		<td width="90%">
		
			<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" valign="top">
			<tr class="tr_color_lgrey" style="display:none">
					<td width="5%" nowrap valign="top" class="black_ar">
						&nbsp;
					</td>
					
					</td>
					<td width="10%" align="right" valign="top">
						&nbsp;
					</td>
					<td width="5%" nowrap align="right" valign="top">
						&nbsp;
					</td>
					<td width="5%" nowrap align="right" valign="top">
						<img src="images/advancequery/b_exp.gif"  hspace="3" onclick="onExport()"/>&nbsp;
					</td>
					<td width="5%" nowrap align="right" valign="top">
						<img src="images/advancequery/b_def_view.gif"  hspace="3" />&nbsp;
					</td>
					<td width="5%" nowrap align="right" valign="top">
						<img src="images/advancequery/b_red_query.gif" hspace="3" />&nbsp;
					</td>
			</tr>
			</table>
			
			</td>
		</tr> -->
	<% } %>

	
<html:hidden property="operation" value=""/>
<input type="hidden" name="isQuery" value="true">
</html:form>
</table>
</td>
</tr>
</table>
