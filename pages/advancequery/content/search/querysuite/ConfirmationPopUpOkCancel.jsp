<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css"/>
<html>
<head>

<script>
<%
String messageKey =(String) request.getParameter("messageKey");
%>
function returnAnswer(answer)
{
	parent.confirmAnswer=answer;
	parent.pvwindow.hide();
	
}


</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"  class="dynamic_table_bg">
  <tr>
  
     <td height="40" align="center"  style="font-family:Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #3c3c3c;" colspan="2">
	<bean:message key="<%=messageKey%>"/>

	</td>
  </tr>
	<tr style="padding-top:10px;">
		  <td align="right" width="50%" style="padding-left:5px;padding-right:5px;" valign="top">
		   <a href="javascript:returnAnswer('true');" ><img alt="Ok" border='0' src="<%=request.getContextPath()%>/images/advancequery/b_ok.gif" />
						</a>				
		</td>
               
                <td align="left" style="padding-left:10px;*padding-left:4px;"  width="50%"  valign="top"><a href="javascript:returnAnswer('false');" ><img alt="Cancel" border='0' align= "left"    src="<%=request.getContextPath()%>/images/advancequery/b_cancel.gif"/></a> 
				   
			   </td>
		
	</tr>
</table>
</body>
</html>

