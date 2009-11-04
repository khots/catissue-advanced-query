
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="edu.wustl.common.beans.RecentQueriesBean"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" isELIgnored="false"%>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<script type="text/javascript" src="jss/advancequery/ajax.js"></script>
<script src="jss/advancequery/queryModule.js"></script>
<script type='text/JavaScript' src='jss/advancequery/scwcalendar.js'></script>
<script type="text/javascript" src="jss/json.js"> </script>
<script type="text/javascript" src="jss/text-utils.js"> </script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />

<script type="text/JavaScript">

<%

      String str = (String)request.getAttribute("queriesIdString");
		String queryType = request.getParameter(Constants.Query_Type);;
%>

  
  var stringIds = '<%=str%>';
 
 function showLoading()
 {
	document.getElementById('parameterlist').style.display="none";
	var	type = "<%=queryType%>";
	if(type=="Data")
	{
		document.getElementById("getDataButton").style.display="none";
	}
	else
	{
		document.getElementById("getCountButton").style.display="none";

	}
	initializeLoadingDiv();

 }
  function hideLoading()
 {
		clearLoadingDiv();


	var	type = "<%=queryType%>";
	if(type=="Data")
	{
		 document.getElementById("getDataButton").style.display="block";
	}
	else
	{
		document.getElementById("getCountButton").style.display="block";
	 
	}

	document.getElementById('parameterlist').style.display="block";
 }

function clearLoadingDiv()
{
		var loading=document.getElementById("loading");
		/*alert(loading.className);
		if(loading.className=="loading-visible")
		{*/
			//loading.className = "loading-invisible";
			
			document.getElementById("loading").style.display="none";
			document.getElementById("loadingTd").style.display="none";
	//	}

}
function initializeLoadingDiv()
{
	document.getElementById("validationmsg").innerHTML="";
	//document.getElementById("loading").className = "loading-visible";
		document.getElementById("loadingTd").style.display="block";
	document.getElementById("loading").style.display="block";
	document.getElementById("loadingTd").focus();
	self.scrollTo(0,0);


}

 function closeWindow()
{
	parent.pvwindow3.hide();
}
function getNormalConditionsForEachQuery(queryId,frmName)
{ 

   // var frmName = document.forms[0].name;
    //var frmName = "saveQueryForm";
	var  attributeList  = "attributesList"+"_"+queryId;
	var list = document.getElementById(attributeList).value;
    var buildquerystr =  createQueryStringForExecution(frmName, list,frmName);
    var conditionList = "conditionList"+"_"+queryId;
	document.getElementById(conditionList).value = buildquerystr;

    //Here dealing with Custom Formula
	/* var totalCFId = "totalCF"+ "_"+queryId;
		  
	 var totalCFCount = document.getElementById(totalCFId).value;
	 if(totalCFCount != 0)
	 {
		var buildTQStr = createQueryStringForExcecuteSavedTQ(frmName,totalCFCount,queryId);
        //alert("buildTQStr in getConditionsForEachQuery:"+buildTQStr); 
      
	     if(buildquerystr != null && buildquerystr != "")
		 {
	         
			 //This is the case when no condition is selected for any of the attributes of query
			 buildquerystr = buildTQStr + "&&&" +buildquerystr;
		 }
		 else
		 {
              //This is the case when there is condition only on custom formula in query
			  buildquerystr =  "CFC###"+buildTQStr + "!&&!";
		 } 
	  } */
	return buildquerystr;
}

function getTemporalConditionsForEachQuery(queryId)
{
      var frmName = document.forms[0].name;
      var totalCFId = "totalCF"+ "_"+queryId;
      var totalCFCount = document.getElementById(totalCFId).value;
      var buildTQStr = null;
	  if(totalCFCount != 0)
	  {
         buildTQStr = createQueryStringForExcecuteSavedTQ(frmName,totalCFCount,queryId);
	  }
	  return buildTQStr;
}
function updatePQ(idString)
{
	//alert(parent.window.queryIdString);
	//alert("idString" +idString);
	var allQueriesCondStr = "";
	var allQueriesCFCondStr = "";
	var arr = idString.split("_");
	
	 var frmName = document.forms[0].name;
	
	
	var allQueriesObj = new Object();
	
	for(var i=0; i<arr.length; i++)
	{
       var queryId = arr[i];
	  // var normalCondStr = "";
	  // var cfCondStr = "";
	   
	   //Get Normal conditions on Query
	   var normalCondStr  =  getNormalConditionsForEachQuery(queryId, frmName);
      
       var temporalCondStr = getTemporalConditionsForEachQuery(queryId, frmName);
	  

       var queryObj = new Object();
	   queryObj['queryConditions'] = normalCondStr;
       queryObj['temporalConditions'] = temporalCondStr;
 
    
	  allQueriesObj[queryId] = queryObj;

	}

  	var actionURL = ""; 
	actionURL =  "allQueriesCondStr="+encodeURIComponent(allQueriesObj.toJSONString()); 
/*	if(allQueriesCFCondStr != "")
	{
		actionURL = "allQueriesCondStr=" + encodeURIComponent(allQueriesCondStr)+ "&allQueriesCFCondStr=" + encodeURIComponent(allQueriesCFCondStr);
    }
	else
	{
		actionURL = "allQueriesCondStr=" + encodeURIComponent(allQueriesCondStr);
	} */

     //Sending a javascript object to Server side as jason String
	 
 
    //This is a trial code 
	/* var allQueryObjs = new Object();
     for(var k=0; k<3 ; k++)
	 {
         var varObj =  new Object();
         var key =  k; 
		 varObj['condStr'] = "This is normal String";
		 varObj['cfStr'] =  "This is custom Formula String" ;


		 allQueryObjs[key] = varObj;
     }*/
	 
	 //  actionURL=actionURL+"&varObject="+encodeURIComponent(allQueryObjs.toJSONString());
   
   
   var projectId=parent.window.document.getElementById("selectedProject").value;
	var workflowId=parent.window.document.getElementById("id").value;
	var url	="UpdatePQForWorkflow.do?queryId="+parent.window.document.getElementById("requestForId").value+"&selectedProject="+projectId+"&workflowId="+workflowId+"&executeType="+parent.window.document.getElementById("executeType").value;

	var request	=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
		
	showLoading();


	var handlerFunction = getReadyStateHandler(request,getCount,true); 
	request.onreadystatechange = handlerFunction; 
	request.open("POST",url,true);    
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	request.send(actionURL);
}
function updateDataPQ(idString)
{
    var allQueriesCondStr = "";
	var allQueriesCFCondStr = "";

	 var frmName = document.forms[0].name;
	var normalCondStr  =  getNormalConditionsForEachQuery(idString, frmName);
    var temporalCondStr = getTemporalConditionsForEachQuery(idString, frmName);
	  

       var queryObj = new Object();
	   queryObj['queryConditions'] = normalCondStr;
       queryObj['temporalConditions'] = temporalCondStr;
   	var actionURL = ""; 
	actionURL =  "buttonClicked=ViewResults&allQueriesCondStr="+encodeURIComponent(queryObj.toJSONString());
	
	var dataQueryId= parent.window.document.getElementById("dataQueryId").value;
  	var countQueryId= parent.window.document.getElementById("countQueryId").value;
	var projectId=parent.window.document.getElementById("selectedProject").value;
	var workflowId=parent.window.document.getElementById("id").value;
  	var request = newXMLHTTPReq();		
	var handlerFunction = getReadyStateHandler(request,displayValidationMesage,true);
	request.onreadystatechange = handlerFunction;	

	showLoading();
	var url = "ValidateQuery.do?dataQueryId="+dataQueryId+"&countQueryId="+countQueryId+"&selectedProject="+projectId+"&workflowId="+workflowId;
	request.open("POST",url,true);	
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	 request.send(actionURL);
		//parent.pvwindow4.hide();
		//request.onreadystatechange = handlerFunction; 
		//request.open("POST",url,true);    
		//request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	//	request.send(actionURL);
	//document.forms[0].action="\QueryResultsView.do?dataQueryId="+dataQueryId+"&queryExecutionId="+exId+"&selectedProject="+projectId+"&workflowId="+workflowId+"&workflowName="+workflowName+'&'+actionURL;
   	//document.forms[0].submit();
}

function displayValidationMesage(response)
{ 
	//document.getElementById('laodingparam').innerHTML="";
  if(response=="ViewResults")
 {
	  var dataQueryId= parent.window.document.getElementById("dataQueryId").value;
	  
	  var allQueriesCondStr = "";
		var allQueriesCFCondStr = "";

		 var frmName = document.forms[0].name;
		var normalCondStr  =  getNormalConditionsForEachQuery(dataQueryId, frmName);
	    var temporalCondStr = getTemporalConditionsForEachQuery(dataQueryId, frmName);
		  

	       var queryObj = new Object();
		   queryObj['queryConditions'] = normalCondStr;
	       queryObj['temporalConditions'] = temporalCondStr;
	 
	  	var actionURL = ""; 
		//actionURL =  "&allQueriesCondStr="+encodeURIComponent(queryObj.toJSONString());
		
			var countQueryId= parent.window.document.getElementById("countQueryId").value;
	  	var identifier=parent.window.document.getElementById("queryIdForRow_"+countQueryId);
		var object=identifier.parentNode;
		var tdChildCollection=object.getElementsByTagName('input');
		var executinIDElement=tdChildCollection[3].id;
		var exId= parent.window.document.getElementById(executinIDElement).value;
		var projectId=parent.window.document.getElementById("selectedProject").value;
	    var workflowId=parent.window.document.getElementById("id").value;
		var workflowName =parent.window.document.getElementById("name").value;
		parent.pvwindow4.hide();
	  parent.window.document.getElementById('allQueriesCondStr').value=queryObj.toJSONString();
	  parent.window.document.forms[0].action="\QueryResultsView.do?dataQueryId="+dataQueryId+"&queryExecutionId="+exId+"&selectedProject="+projectId+"&workflowId="+workflowId+"&workflowName="+workflowName;
      parent.window.document.forms[0].submit();
 	}
  else  //display message
  {
     //document.getElementById('parameterlist').style.display='';//="height: 240px;overflow-y:auto;";
	 hideLoading();
	document.getElementById("validationmsg").style.display="block";


	  var elem = document.getElementById("validationmsg").innerHTML = response;
  }
 }
  
function getCount(response)
{
	//document.getElementById('laodingparam').innerHTML="";
	var jsonResponse = eval('('+ response+')');
    if(jsonResponse.error!=null)
	{

	   //document.getElementById('parameterlist').style.display='';//="height: 240px;overflow-y:auto;";
	   hideLoading();
	  var elem = document.getElementById("validationmsg").innerHTML = jsonResponse.error;
	  
	}
	//parent.window.workflowExecuteGetCountQuery(parent.window.document.getElementById("requestForId").value,0);
	else
	{
		parent.window.workflowResponseHandler(eval('('+ response+')'));
		closeWindow();
	}
     
	
}

</script>
<body>
<html:form styleId='saveQueryForm' action="UpdateQueryAction.do" style="margin:0;padding:0;">
 <input type="hidden" name="pQIdList" id="pQIdList" value="${requestScope.pQueryIdList}">
 <c:set var="showGetCount" value="${requestScope.showGetCount}" scope="page"/>
 <table height="82%" width="100%" align="middle" border="0">
	<tr valign="bottom" align="center" height="2%" >
		<td  colspan="2">
			<table width="98%" border="0"  cellspacing="0" cellpadding="0" align="center">
				<tr valign="top">
					<td>
						 <table width="100%"  cellspacing="0" cellpadding="0" >
								<tr><td  width="100%" style="padding-left:5px;"> <div id="validationmsg"></div></td></tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr valign="top" id="contenttr" width="100%">
		<td style="padding-left:5px;padding-right:7px;" colspan="2">
			<table width="100%" border="0"  cellspacing="0" cellpadding="0">
				<tr valign="top" width="100%">
					<td >
						<div  class="login_box_bg" id="parameterlist" valign="top">
							<%=request.getAttribute(Constants.HTML_CONTENTS)%>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr><td>
		<table width="100%">
			<tr  id="loading"   style="display:none;" align="center"  width="100%" >
				<td  width="100%" id="loadingTd"  align="center"  colspan="2">	
					<div style="height:100%;"  >
							<img alt="Loading"  align="absmiddle" src="images/advancequery/Parameter_Processing.gif" >
							
					</div>
				</td>
			</tr>
		</table>
	</td></tr>

	<tr><td>
		<table width="100%">
			<tr >
				<logic:equal name="showGetCount" value="">
					<td id='getCountButton'> 
					<a href="javascript:updatePQ(stringIds);" ><img alt="Get Count" border='0'id="getCount"   src="images/advancequery/b_get_count.gif" />
					</a>
					 </td>
				 </logic:equal>

				 <td id='getDataButton' >
				<a href="javascript:updateDataPQ(stringIds);" ><img alt="Get Data" border='0'  src="images/advancequery/b_search.gif"  id="getData"/>
				</a>
				 </td>
			</tr>
		</table>
	</td></tr>




<!--	<tr>
		<logic:equal name="showGetCount" value="">
		<td id='getCountButton'> 
		<a href="javascript:updatePQ(stringIds);" ><img alt="Get Count" border='0'id="getCount"   src="images/advancequery/b_get_count.gif" />
		</a>
		 </td>
		  </logic:equal>

		
		 <td id='getDataButton' >
		<a href="javascript:updateDataPQ(stringIds);" ><img alt="Get Data" border='0'  src="images/advancequery/b_search.gif"  id="getData"/>
		</a>
		 </td>
				
	</tr>
	-->
</table>
  </html:form>
</body>

<script>
var type = "<%=queryType%>";
if(type=="Data")
{
	document.getElementById("getCountButton").style.display="none";
}
else
{
  document.getElementById("getDataButton").style.display="none";
}
</script>
