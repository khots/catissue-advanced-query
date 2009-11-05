<!--created by amit_doshi 19 Aug 2009 -->

<script>

/*To show the image on the loading frame*/
var loadingImageTag="<img src='../images/advancequery/VI_Loading.gif' alt='Loading the Vocabulary Interface ...'  title='Loading the Vocabulary Interface ...' >";

/* To froward the request to desired page*/
function forwardToLocation()
{
	location.href ='SearchMappedPV.do?componentId='+parent.compId+"&editVocabURN="+parent.editVocabURN;
	showLoadingFrame();
}
</script>
<%@includefile="/pages/CommonLoadingFrame.jsp" %>

