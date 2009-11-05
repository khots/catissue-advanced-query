<!--created by amit_doshi 19 Aug 2009 -->
<html>
<head>
<script>
function showLoadingFrame()
	{
		var viLoading=document.getElementById('viLoading');
		viLoading.className="commonLoadingVisible";
		viLoading.src="pages/CommonLoading.html";
	}
	

</script>
<style>
	.commonLoadingVisible {
  background:#fdfdfe;
  position: relative;  /* Do Not Modify */
  overflow: hidden;  /* Do Not Modify */
  z-index: 1000;  /* Do Not Modify */
  left: 40%;  /* Do Not Modify */
  top: 45%;  /* Do Not Modify */
  background-color:#ffffff;
  align:center;
  text-align: center;
  float: left;
  cursor: wait;
  opacity: 1;
  filter: alpha(opacity=100);  /* IE doesn't include opacity (CSS3) (Note: modify both together) */
  width:204px;
  height:92px;*/
}
.commonLoadingHidden
{
  position: absolute;  /* Do Not Modify */
  overflow: hidden;  /* Do Not Modify */
  z-index: 100;  /* Do Not Modify */
  left: 0px;  /* Do Not Modify */
  top: 0px;  /* Do Not Modify */
  float: left;
  opacity: 0;
  height:1px;
  width:1px;
  filter: alpha(opacity=0);  /* IE doesn't include opacity (CSS3) (Note: modify both together) */
}
</style>
</head>

<body onLoad="forwardToLocation();">
 <table align="center" height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" style="background-color:#fdfdfe">
		<tr>
			<td><iframe class="commonLoadingHidden" src="pages/CommonLoading.html" frameborder="0" scrolling="no"  name = "viLoading" id = "viLoading"></iframe></td>
		</tr>
 </table>
</body>
</html>

