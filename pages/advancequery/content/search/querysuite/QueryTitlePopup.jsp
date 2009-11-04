<link href="<%=request.getContextPath()%>/css/advancequery/inside.css" rel="stylesheet" type="text/css"/>
<script type="text/JavaScript">
function confirmDelete()
{
	parent.samequerytitleWindow.hide();
}

</script>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align = "left">
  <tr align = "center">
    <td style = "padding-left:14px;padding-right:14px" align="center" valign="middle"><table width="100%" height="90" border="0" align="center" cellpadding="0" cellspacing="0" class="dynamic_table_bg">
      <tr>
        <td height="23" align="center" class="content_txt"><div id="queryTitleDiv" ></div> 
		</td>
	 </tr>
	 </td>
	 </tr>
	 <tr width="100%"  >
				<td valign="bottom"  width="100%" colspan="3">
					<table   cellpadding="0" cellspacing="0"  width="100%"  valign="bottom"  >
						<tr>
							<td  width="100%" class="leftpadding">
								<table border="0" cellpadding="0" cellspacing="0"  width="100%"   valign="bottom">
									
									<tr >
										<td valign="bottom" align="middle" width="100%"><a href="javascript:confirmDelete()">
											<img src="<%=request.getContextPath()%>/images/advancequery/b_ok.gif" alt="Ok" border="0"></a>
											
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>			

</table>
<script>
if(parent.window.document.getElementById("type").value!="Data")
{
	 if(parent.window.document.getElementById("numberOfSameQueryTitle").value==1)
	{

		document.getElementById("queryTitleDiv").innerHTML="Query with title '" + parent.window.document.getElementById("sameQueryTitle").value +"' is already present in workflow. Click on the 'Create Copy' action against the query to add a copy of the query in the workflow.";
	}
	else
	{
		document.getElementById("queryTitleDiv").innerHTML='Queries with titles "' + parent.window.document.getElementById("sameQueryTitle").value +"' are already present in workflow. Click on the 'Create Copy' action against the query to add a copy of the query in the workflow.";
	}
}
else
{
		 if(parent.window.document.getElementById("numberOfSameQueryTitle").value==1)
	{

		document.getElementById("queryTitleDiv").innerHTML="Query with title '" + parent.window.document.getElementById("sameQueryTitle").value +"' is already present in workflow.";
	}
	else
	{
		document.getElementById("queryTitleDiv").innerHTML='Queries with titles "' + parent.window.document.getElementById("sameQueryTitle").value +"' are already present in workflow.";
	}

}
</script>
</body>

