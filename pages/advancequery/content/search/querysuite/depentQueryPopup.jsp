\<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css"/>
<script type="text/JavaScript">
function confirmDelete()
{
	parent.pvwindow1.hide();
}

</script>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
     
        <td height="40" align="center"  style="font-family:Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #3c3c3c;">You cannot delete the query as other composite/join queries are dependent on the selected query. To delete the query, you have to first delete the dependent queries.
	<tr>
	 <td width = "100"><a href="javascript:confirmDelete()"><img src="images/advancequery/b_ok.gif" alt="Ok" width="50" height="23" border="0" /></a></td>
	 <tr>
	</td>
  </tr>
</table>
</body>
