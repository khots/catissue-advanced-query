<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>

<html>
<link rel="STYLESHEET" type="text/css" href="css/advancequery/dhtmlxtabbar.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/CascadeMenu.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<body>
<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
	
<tr  valign="top">
	<td  valign="top" width="20%">
		<table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr  class='validationMessageCss'  >
				<td width="80%" class='validationMessageCss' style="display:none">
					&nbsp;
				</td>
			</tr>
			<tr valign="top" width="100%" height="100%" align="left">
				<td valign="top" height="100%" align="left" >
<%@ include file="/pages/advancequery/content/search/querysuite/selectEntity.jsp" %>				
</td>
			</tr>
		</table>
	</td>
	<td>
	    <table border="0"  height="100%" width="100%" cellpadding="1" cellspacing="3" valign="top">
			<tr  id="rowMsg" class='validationMessageCss'>
				<td id="validationMessagesSection"  width="80%" class='validationMessageCss'>
					<div id="validationMessagesRow" style="overflow:auto; width:100%; height:50;display:none"></div>
				</td>
			</tr>
			<tr>
				<td valign="top" width="80%" height="100%" class="login_box_bg" align="middle">
					<%@ include file="/pages/advancequery/content/search/querysuite/DefineGridResultsView.jsp" %>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<script>  
//to hide the button row (of defineGridView page )in define view page
 document.getElementById("buttontr").style.display="none";  </script>
</body>
</html>