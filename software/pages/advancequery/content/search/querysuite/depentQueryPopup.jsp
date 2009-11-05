<link href="<%=request.getContextPath()%>/css/advancequery/inside.css" rel="stylesheet" type="text/css"/>
<script type="text/JavaScript">
function confirmDelete()
{
	parent.pvwindow1.hide();
}

</script>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align = "left">
  <tr align = "center">
    <td style = "padding-left:14px;padding-right:14px" align="center" valign="middle"><table width="100%" height="90" border="0" align="center" cellpadding="0" cellspacing="0" class="dynamic_table_bg">
      <tr>
        <td height="23" align="center" class="content_txt">You cannot delete the query as other composite/join queries are dependent on the selected query. To delete the query, you have to first delete the dependent queries.
		</td>
      </tr>

	</td>
  </tr>
</table>
</body>

