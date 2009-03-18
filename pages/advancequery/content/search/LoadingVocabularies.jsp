<script>

function forwardToLocation()
{
	location.href ='SearchMappedPV.do?componentId='+parent.compId+"&editVocabURN="+parent.editVocabURN;
}
</script>
<head>
	<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<html>
<body onLoad="forwardToLocation();">
 <table align="center" height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="content_txt"  align="center" style="padding-left:10px;color:blue;font-size: 14px;">Loading the Vocabulary Interface...</td>
		</tr>
 </table>
</body>
</html>