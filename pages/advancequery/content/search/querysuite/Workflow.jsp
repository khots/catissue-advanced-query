<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="edu.wustl.query.util.global.CompositeQueryOperations"%>
<%@ page import="edu.wustl.query.enums.QueryType" %>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />
<script  src="jss/advancequery/workflows.js"></script>
<script  src="jss/advancequery/wz_tooltip.js"></script>
<script src="jss/ajax.js"></script>	
<script type="text/JavaScript">

<%
String qType_GetCount= (QueryType.GET_COUNT).type;
String qType_GetData= (QueryType.GET_DATA).type;
%>
var previousProject="-1";

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function test(title)
{
	 var t =	escape(title);
	executeGetCountQuery(escape(t),0);
}

function showPopUp(pageOf)
{
	var url='QueryAction.do?pageOf='+pageOf+'&queryId=queryId&queryTitle=queryTitle&queryType=queryType';
	pvwindow=dhtmlmodal.open('Select queries', 'iframe', url,'Select queries', 'width=930px,height=400px,center=1,resize=1,scrolling=1');
}

function updateUI()
{
	addQuery();
}

function unionQueries()
{
	addCQToList("<%=CompositeQueryOperations.UNION.getOperation()%>");
}

function intersectQueries()
{
	addCQToList("<%=CompositeQueryOperations.INTERSECTION.getOperation()%>");
}

function minusQueries()
{
	addCQToList("<%=CompositeQueryOperations.MINUS.getOperation()%>");
}

function addCQToList(operation)
{
	var queryIdsToAdd='';
	var queryCount=0;
	queryCount=document.getElementById("table1").rows.length;
	var selectedQueryCount=0;
	for(var counter=0;counter<queryCount;counter++)
	{
		var checkboxControl=document.getElementById("checkbox_"+(counter));
		if(checkboxControl!=null && checkboxControl!=undefined && checkboxControl.checked==true)
		{
			queryIdsToAdd=queryIdsToAdd+","+counter;
			selectedQueryCount=selectedQueryCount+1;
		}
	}

	if(queryIdsToAdd!=""&& selectedQueryCount==2)
	{
		
		createCQ(queryIdsToAdd,operation,queryCount);
		uncheckselectedCheckBoxes();
		
	}		
}

function uncheckselectedCheckBoxes()
{
	var queryCount=0;
	queryCount=document.getElementById("table1").rows.length;
		for(var counter=0;counter<queryCount;counter++)
			{
				var checkboxControl=document.getElementById("checkbox_"+(counter));
				if(checkboxControl!=null && checkboxControl!=undefined && checkboxControl.checked==true)
				{
					checkboxControl.checked=false;
				}
			}
}
function createCQ(queryIdsToAdd,operation,queryCount)
{
	var queryIds=queryIdsToAdd.split(",");
	var operandsTdContent="";
	var cqTitle="";
	var cqQueryId="";
	var operandsCounter=0;
	var expression="";
	for(var counter=0;counter<queryIds.length;counter++)
	{
		if(queryIds[counter]!=null && queryIds[counter]!='')
		{
			if( cqTitle=='')
			{
				cqTitle="[ Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+" ]";
			}
			else
			{
				cqTitle=cqTitle+" "+operation+" "+"[ Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+" ]";
			}
			if(cqQueryId=='')
			{
				cqQueryId="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=document.getElementById("selectedqueryId_"+queryIds[counter]).value;
				expression=document.getElementById("expression_"+queryIds[counter]).value;

			}
			else
			{
				cqQueryId="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=operandsTdContent+"_"+document.getElementById("selectedqueryId_"+queryIds[counter]).value;
	
				expression=expression+"_"+document.getElementById("expression_"+queryIds[counter]).value+"_"+shortOperator(operation);
			}
			operandsCounter=operandsCounter+1;
		}
	}
	var cqType="Count";
	var cqId="";

	var rowContents=new Array(7);
	rowContents[0]=createCheckBox("chkbox","checkbox_"+queryCount,'',queryCount,false);
	rowContents[1]=createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+queryCount);
	rowContents[2]=createTextElement(cqTitle);
	rowContents[3]=createTextElement(cqType);
	//rowContents[3]=createTextElement(operandsTdContent);
	//rowContents[3]=getSelectObjectControl();
	rowContents[5]=createTextElement("");
	rowContents[6]=createHiddenElement("selectedqueryId","selectedqueryId_"+queryCount,operandsTdContent);
	//rowContents[4].appendChild(createHiddenElement("identifier","identifier_"+operandsTdContent,operandsTdContent));
	rowContents[7]=cqTitle;
	rowContents[8]=cqType;
	
	var operatorsTdContent=operation;
	//uncommented for the underscore separated operation string 
	for(var counter=0;counter<operandsCounter-2;counter++)
	{
		operatorsTdContent=operatorsTdContent+"_"+operation;
	}
	//create a table containing tbody with id "table1"
	addRowToTable("table1",rowContents,operandsTdContent,operatorsTdContent,expression);	
}

function shortOperator(operation)
{
	if(operation=='Union')
		return "+";
		
	if(operation=='Intersection')
		return "*";
	
	if(operation=='Minus')
		return "-";
	
	return "";//for NONE
}


function submitWorflow()
{
	document.forms[0].submit();
}
function cancelGetCountQuery(queryId,executionLogId,removeExecutedCount)
{	

	var identifier=document.getElementById("queryIdForRow_"+queryId);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
	var selectedquery=selectedqueryId.split("_");
	
	index=selectedquery[1];

	document.getElementById("cancelajaxcall_"+index).value='true';
	
	var projectId=document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&queryId="+queryId+'&state='+'cancel'+"&executionLogId="+executionLogId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId+"&removeExecutedCount="+removeExecutedCount;
	//changeExecuteLinkToExecute(queryId,0);
	var request=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	var handlerFunction = getReadyStateHandler(request,cancelExecuteQuery,true); 
	request.onreadystatechange = handlerFunction; 
	request.open("POST",url,true);    
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	request.send("");

}
function cancelExecuteQuery(response)
{
	var jsonResponse = eval('('+ response+')');
			  var hasValue = false;


			//TO DO
			//write code to show error occured while cancel 

 
		  if(jsonResponse.removeExecutedCount!=null)
		  {
			var queryId = jsonResponse.queryId;
			var identifier=document.getElementById("queryIdForRow_"+queryId);
			var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
			var tdChildCollection=object.getElementsByTagName('input');
			var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
			var selectedquery=selectedqueryId.split("_");
			index=selectedquery[1];
			removeCountResults1(index);
		  }

		  	if(jsonResponse.queryId!=null)
			{
				var queryId = jsonResponse.queryId;
				//var executionLogId = jsonResponse.executionLogId;
				var queryId = jsonResponse.queryId;
				var identifier=document.getElementById("queryIdForRow_"+queryId);
				var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
				var tdChildCollection=object.getElementsByTagName('input');
				var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
				var selectedquery=selectedqueryId.split("_");
				index=selectedquery[1];
				changeExecuteLinkToExecute(queryId,0);
				document.getElementById("cancelajaxcall_"+index).value='false';

			}
			
	
}
function removeCountResults1(queryIndex)
{
		var lableObject=document.getElementById("label_"+queryIndex);
		lableObject.innerHTML="";
}
function showResetCountPopup()
{
	var selectedProject=document.getElementById('selectedProject');
	var currentProject=document.getElementById('selectedProject').value;
	if(previousProject!='-1' && previousProject!=currentProject)
	{
		var isExecuted=isQueryExecuted();
		if(isExecuted==true)
		{
			var url='ShowResetCountPopUp.do';
			pvwindow=dhtmlmodal.open('Change Project', 'iframe', url,'Change Project', 
			'width=400px,height=150px,center=1,resize=1,scrolling=1');
		}
	}
	previousProject=currentProject;
}
function isQueryExecuted()
{
	var rows=document.getElementById("table1").rows.length;
	for(var i=0;i<rows;i++)
	{
		if(document.getElementById("queryExecId_"+i).value!='0' && document.getElementById("queryExecId_"+i).value!="")
		{

			return true;
		}
	}
	return false;
}

function setCancelFlag(queryId)
{
		/*queryTitle=unescape(queryTitle);
		queryTitle=unescape(queryTitle);
		//var nameIdentifier=document.getElementsByName("identifier");
		var numOfRows =document.getElementById("table1").rows.length;
			for(var count = 0; count < numOfRows; count++)
			{
				var title=document.getElementById("identifier_"+count);
				if(title.value==queryTitle)
				{
							var object=title.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
							var selectedquery=selectedqueryId.split("_");
					
							index=selectedquery[1];
							document.getElementById("cancelajaxcall_"+index).value='false'
							
				}
			}*/

						var identifier=document.getElementById("queryIdForRow_"+queryId);
							var object=identifier.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var qTitle = tdChildCollection[1].value;
							var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
							var selectedquery=selectedqueryId.split("_");
							queryIndex=selectedquery[1];
							document.getElementById("cancelajaxcall_"+queryIndex).value='false';
	
}

function runWorkflow()
{

	if(document.getElementById("isdone").value=='false')
	{
		var rows=document.getElementById("table1").rows.length;
		var operators="";
		var workflowName=document.getElementById("name").value;
		var operands="";
		var workflowName=document.getElementById("name").value;
		var identifier="";
		var displayQueryTitle="";
				var expression="";
		for(i=0;i<rows;i++)
		{
			operands=operands+document.getElementById("operands_"+i).value+",";
			operators=operators+document.getElementById("operators_"+i).value+",";
			displayQueryTitle=displayQueryTitle+document.getElementById("displayQueryTitle_"+i).value+",";
			expression=expression+document.getElementById("expression_"+i).value+",";
			var title=document.getElementById("identifier_"+i);
			var object=title.parentNode;
			var tdChildCollection=object.getElementsByTagName('input');
			var queryId=tdChildCollection[2].id;
			identifier=identifier+document.getElementById(queryId).value+",";
		}
		var url="SaveWorkflowAjaxHandler.do?operands="+operands+"&operators="+operators+"&workflowName="+workflowName+"&displayQueryTitle="+displayQueryTitle+"&workflowId="+document.getElementById("id").value+"&identifier="+identifier+"&expression="+ encodeURIComponent(expression)+"&excuteType=workflow";

		var request=newXMLHTTPReq();
		if(request == null)
		{
			alert ("Your browser does not support AJAX!");
			return;
		}
		var handlerFunction = getReadyStateHandler(request,runWorkflowResponseHandler,true); 
		request.onreadystatechange = handlerFunction; 
		request.open("POST",url,true);    
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send("");

	}
	else
	{
		runSavedWorkflow();
	}


}
function runWorkflowResponseHandler(response)
{
	var jsonResponse = eval('('+ response+')');
    var hasValue = false;

	 if(jsonResponse.errormessage!=null)
	 {
		   document.getElementById("errormessage").innerHTML=jsonResponse.errormessage;
	 }

	//set workflow id 
	 if(jsonResponse.workflowId!="")
     {
		 document.getElementById("id").value=jsonResponse.workflowId;
		 document.getElementById("operation").value="edit";

	 }
	 if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
		jsonResponse.errormessage==""))
	{
		document.getElementById("isdone").value=true;
	   
	}


	//set the name identifier mapping 
	if(jsonResponse.executionQueryResults!=null)
	  {
		 var num = jsonResponse.executionQueryResults.length; 
			for(var i=0;i<num;i++)
			{
				 var queryTitle = jsonResponse.executionQueryResults[i].queryTitle;


				//var nameIdentifier=document.getElementById("table1").rows;
					var numOfRows =document.getElementById("table1").rows.length;
					for(var count = 0; count < numOfRows; count++)
					{
						
						var title=document.getElementById("identifier_"+count);
						if(title.value==queryTitle)
						{
							var object=title.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var queryId=tdChildCollection[2].id;
							document.getElementById(queryId).value=jsonResponse.executionQueryResults[i].queryId;
							document.getElementById(queryId).id="queryIdForRow_"+jsonResponse.executionQueryResults[i].queryId;
						
						}
					}

			
					if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
						jsonResponse.errormessage==""))
					{
						 runSavedWorkflow();
					}

			}
	   }

}
function runSavedWorkflow()
{
	var projectId=document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&selectedProject="+projectId+"&workflowId="+document.getElementById("id").value+"&executeType=executeWorkFlow";

	var request=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	var handlerFunction = getReadyStateHandler(request,workflowResponseHandler,true); 
	request.onreadystatechange = handlerFunction; 
	request.open("POST",url,true);    
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	request.send("");
}

function executeGetCountQuery(queryTitle,executionLogId)
{

	//setCancelFlag(queryTitle);
	queryTitle=unescape(queryTitle);
	queryTitle=unescape(queryTitle);

	if(document.getElementById("isdone").value=='false')
	{
		var rows=document.getElementById("table1").rows.length;
		var operators="";
		var workflowName=document.getElementById("name").value;
		var operands="";
		var workflowName=document.getElementById("name").value;
		var identifier="";
		var displayQueryTitle="";
				var expression="";
		for(i=0;i<rows;i++)
		{
			operands=operands+document.getElementById("operands_"+i).value+",";
			operators=operators+document.getElementById("operators_"+i).value+",";
			displayQueryTitle=displayQueryTitle+document.getElementById("displayQueryTitle_"+i).value+",";
			expression=expression+document.getElementById("expression_"+i).value+",";
			var title=document.getElementById("identifier_"+i);
			var object=title.parentNode;
			var tdChildCollection=object.getElementsByTagName('input');
			var queryId=tdChildCollection[2].id;
			identifier=identifier+document.getElementById(queryId).value+",";
		}
		var url="SaveWorkflowAjaxHandler.do?operands="+operands+"&operators="+operators+"&workflowName="+workflowName+"&displayQueryTitle="+displayQueryTitle+"&workflowId="+document.getElementById("id").value+"&queryTitle="+encodeURIComponent(queryTitle)+"&identifier="+identifier+"&expression="+ encodeURIComponent(expression);

		var request=newXMLHTTPReq();
		if(request == null)
		{
			alert ("Your browser does not support AJAX!");
			return;
		}
		var handlerFunction = getReadyStateHandler(request,responseHandler,true); 
		request.onreadystatechange = handlerFunction; 
		request.open("POST",url,true);    
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send("");

	}
	else
	{
				
		//var nameIdentifier=document.getElementsByName("identifier");
		var numOfRows =document.getElementById("table1").rows.length;
			for(var count = 0; count < numOfRows; count++)
			{
				var title=document.getElementById("identifier_"+count);
				if(title.value==queryTitle)
				{
							var object=title.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var queryId=tdChildCollection[2].id;//object.childNodes[0].id;//object.id;
							//setDropDowns(queryTitle);
							workflowExecuteGetCountQuery(document.getElementById(queryId).value,executionLogId);
				}
			}
	}
	setCountDropDown = false;
}


function responseHandler(response)
{
	var jsonResponse = eval('('+ response+')');
    var hasValue = false;

		//set error message 
	 if(jsonResponse.errormessage!=null)
     {
           document.getElementById("errormessage").innerHTML=jsonResponse.errormessage;
	 }

	//set workflow id 
	 if(jsonResponse.workflowId!="")
     {
		 document.getElementById("id").value=jsonResponse.workflowId;
		  document.getElementById("operation").value="edit";

	 }
	 if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
		jsonResponse.errormessage==""))
	{
		document.getElementById("isdone").value=true;
	   
	}


	//set the name identifier mapping 
	if(jsonResponse.executionQueryResults!=null)
	  {
		 var num = jsonResponse.executionQueryResults.length; 
			for(var i=0;i<num;i++)
			{
				 var queryTitle = jsonResponse.executionQueryResults[i].queryTitle;


				//var nameIdentifier=document.getElementById("table1").rows;
				var numOfRows =document.getElementById("table1").rows.length;
				for(var count = 0; count < numOfRows; count++)
				{
					
					var title=document.getElementById("identifier_"+count);
					if(title.value==queryTitle)
					{
						var object=title.parentNode;
						var tdChildCollection=object.getElementsByTagName('input');
						var queryId=tdChildCollection[2].id;
						document.getElementById(queryId).value=jsonResponse.executionQueryResults[i].queryId;
						document.getElementById(queryId).id="queryIdForRow_"+jsonResponse.executionQueryResults[i].queryId;
					
					}
				}

				
					if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
						jsonResponse.errormessage==""))
					{

					   if(jsonResponse.queryTitle!=null&&jsonResponse.queryTitle==queryTitle)
					   {
						   //setDropDowns(queryTitle);
							workflowExecuteGetCountQuery(jsonResponse.executionQueryResults[i].queryId,0);
						
					   }
					}

			}
	   }

}


function removeFromDropDown(qTitle)
{
	var dropDowns= document.getElementsByTagName("select");
	for(var i=0;i<dropDowns.length;i++)
	{
	   if(dropDowns[i].name=="countQueryDropDown")
	   {
		    var executedQuery=dropDowns[i].options.length;
			if(executedQuery >0)
			{
			  for(var j=0;j<executedQuery;j++)
			  {
				  if(dropDowns[i].options[j].text==qTitle)
				  {
					dropDowns[i].options[j]=null;
				  }
			  }
			}
	   }
	}
}

function setDropDowns(queryTitle)
{
	var numOfRows =document.getElementById("table1").rows.length;
	var queryId;		
			for(var count = 0; count < numOfRows; count++)
			{
				var title=document.getElementById("identifier_"+count);
				if(title.value==queryTitle)
				{
							
							var object=title.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var queryId=tdChildCollection[2].value;//object.childNodes[0].id;//objecid
				            var execId= tdChildCollection[3].value;
				 }
				
			}

    //if(execId==0 || execId=="") 
  //{	
	
	 var dropDowns= document.getElementsByTagName("select");
	for(var i=0;i<dropDowns.length;i++)
	{
	   
	   if(dropDowns[i].name=="countQueryDropDown")
	 {
		   var bool=false;
		   var executedQuery=dropDowns[i].options.length;
			if(executedQuery >0)
			{
				for(var j=0;j<executedQuery;j++)
			  {
				  if(dropDowns[i].options[j].text==queryTitle)
					  bool=true;
			  }
			
			}
		  
			if(bool== false) 
			{
			  var optn = parent.window.document.createElement("OPTION");
			 optn.text=queryTitle;
			 optn.value=queryId;
			 dropDowns[i].options.add(optn);
			 dropDowns[i].disabled=false;
			}
	  }
	//}
	// Add query to hidden dropdown
        var add=true;
	    var hiddenDropDown=document.getElementById("executedCountQuery");
	    var num=hiddenDropDown.options.length;
		if(num>0)
		{
		  for(var t=0;t<num;t++)
		  {
		   if(hiddenDropDown.options[t].text==queryTitle)
			  add=false;
		   
		  }
		
		}
		 
		if(add==true) 
		{
		 var optn = parent.window.document.createElement("OPTION");
	     optn.text=queryTitle;
	     optn.value=queryId;
	     hiddenDropDown.options.add(optn);
	     hiddenDropDown.disabled=false;
		}
  }

}

function workflowExecuteGetCountQuery(queryId,executionLogId)
{
	//setCancelFlag(queryId);
	var projectId=document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&state=start&executionLogId="+executionLogId+"&selectedProject="+projectId+"&workflowId="+document.getElementById("id").value+"&queryId="+queryId;
	

	var request=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	var handlerFunction = getReadyStateHandler(request,workflowResponseHandler,true); 
	request.onreadystatechange = handlerFunction; 
	request.open("POST",url,true);    
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	request.send("");
}

function executeGetDataQuery(dataQueryId)
{
  

	var countQueryId = document.getElementById("countQueryDropDown_"+dataQueryId);
    if(countQueryId.selectedIndex== -1)
	{
	  alert("please first execute a get count query");
	}
	else
	{
      var countqId =countQueryId.options[countQueryId.selectedIndex].value;
	  var hiddnid=  document.getElementById("dataQueryId");
	  var hid=   document.getElementById("countQueryId") 
	   if(hiddnid!=null)
	  {
	    hiddnid.value=dataQueryId;
	    hid.value=countqId;
	  }

	 //send Ajax request for validation
	  	 var request = newXMLHTTPReq();		
		 var handlerFunction = getReadyStateHandler(request,displayValidationMesage,true);
		 request.onreadystatechange = handlerFunction;	
		 var actionURL = "buttonClicked=ViewResults";		
	      var url = "ValidateQuery.do?dataQueryId="+dataQueryId+"&countQueryId="+countqId;
		 request.open("POST",url,true);	
		 request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);	
	  }
}


  
	function displayValidationMesage(response)
	{ 
		
	  if(response=="ViewResults")
	 {
	  	var dataQueryId= document.getElementById("dataQueryId").value;
	  	var countQueryId= document.getElementById("countQueryId").value;
	  	var identifier=document.getElementById("queryIdForRow_"+countQueryId);
		var object=identifier.parentNode;
		var tdChildCollection=object.getElementsByTagName('input');
		var executinIDElement=tdChildCollection[3].id;
		var exId= document.getElementById(executinIDElement).value;
		var projectId=document.getElementById("selectedProject").value;
	    var workflowId=document.getElementById("id").value;
		document.forms[0].action="\QueryResultsView.do?dataQueryId="+dataQueryId+"&queryExecutionId="+exId+"&selectedProject="+projectId+"&workflowId="+workflowId;
	   document.forms[0].submit();
	 }
	  
	}
  

var setCountDropDown = false;
function workflowResponseHandler(response)
{

		  var jsonResponse = eval('('+ response+')');
          var hasValue = false;
          if(jsonResponse.executionQueryResults!=null)
          {
             var num = jsonResponse.executionQueryResults.length; 
		
				for(var i=0;i<num;i++)
				{
					 var queryId = jsonResponse.executionQueryResults[i].queryId;
					 var queryResult = jsonResponse.executionQueryResults[i].queryResult;
					 var status = jsonResponse.executionQueryResults[i].status;
					 var executionLogId = jsonResponse.executionQueryResults[i].executionLogId;

						if(queryResult!=-1)
						{
							var identifier=document.getElementById("queryIdForRow_"+queryId);
							var object=identifier.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var qTitle = tdChildCollection[1].value;
							var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
							var selectedquery=selectedqueryId.split("_");
							queryIndex=selectedquery[1];
							//for setting the execution id

							var queryExecId=tdChildCollection[3].id;
							if(queryExecId!=null&&queryExecId!=undefined)
							{
							   /*if(document.getElementById(queryExecId).value==0 ||   
									 document.getElementById(queryExecId).value=='' || document.getElementById(queryExecId).value==null)
								{
								  setDropDowns(qTitle);
								}*/ 
								if(setCountDropDown == false && queryResult>0)
								{
									setDropDowns(qTitle);
									setCountDropDown=true;
								}
								if(queryResult==0)
								{
									removeFromDropDown(qTitle);								
								}
								 document.getElementById(queryExecId).value=executionLogId;
	
							}

							if(document.getElementById("cancelajaxcall_"+queryIndex).value=='false')
							{
								var lableObject=document.getElementById("label_"+queryIndex);
								if(lableObject!=null)
								{
									object.removeChild(lableObject);
								}
								object.appendChild(createLabel(queryResult,queryIndex));
							}
								
						}
						if((document.getElementById("cancel_"+queryIndex)==null)&&(document.getElementById("cancelajaxcall_"+queryIndex).value=='false'))
						{
							changeLinkToCancel(queryId,executionLogId);
						}
					
						if((status!="Completed"||status=="Cancelled")&&document.getElementById("cancelajaxcall_"+queryIndex).value=='false')
						{

							workflowExecuteGetCountQuery(queryId,executionLogId);
						}
						
						if((status=="Completed"||status=="Cancelled"))
						{
							changeExecuteLinkToExecute(queryId,0);
		
						}
						
						//workflowExecuteGetCountQuery(queryId,executionLogId);
					}
          } 

}
function cancelWorkflow()
{
	document.forms[0].action="\ShowDashboard.do"
	document.forms[0].submit();
}
function getCountdata()
{
	document.forms[0].forwardTo.value= "loadQueryPage";
	document.forms[0].action="SaveWorkflow.do?submittedFor=ForwardTo&nextPageOf=queryGetCount";
	document.forms[0].submit();
}
function getPatientdata()
{
	document.forms[0].forwardTo.value= "loadQueryPage";
	document.forms[0].action="SaveWorkflow.do?submittedFor=ForwardTo&nextPageOf=queryWizard";
	document.forms[0].submit();
}
//-->
</script>
<script type="text/javascript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
<script type="text/javascript" src="wz_tooltip.js"></script>

<body>
<html:form action="SaveWorkflow" >
 <input type="hidden" name="dataQueryId" id="dataQueryId" value="">
 <input type="hidden" name="countQueryId" id="countQueryId" value="">
<html:hidden property="operation" styleId="operation" value="${requestScope.operation}"/>
<html:hidden property="id" styleId="id" value="${requestScope.id}"/>
<select name="queryId" id="queryId" style="display:none">
</select>
<select name="queryTitle" id="queryTitle" style="display:none">
</select>
<select name="queryType" id="queryType" style="display:none">
</select>
<select name="executedCountQuery" id="executedCountQuery" style="display:none">
</select>
<html:hidden property="forwardTo"/>
<c:set var="query_type_data" value="<%=qType_GetData%>" scope="page"/>
<c:set var="query_type_count" value="<%=qType_GetCount%>" scope="page"/>
<input type="hidden" name="isdone" value="true" id="isdone">
 <input type="button" name="btn" id="btn" onclick="updateUI()" style="display:none">

<table width="99%" border="0" valign="top"  cellpadding="0" cellspacing="0" style="padding-left:10px;">
<tr>
	<td>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
<tr>
	<logic:equal name="workflowForm" property="operation" value="">
		<td height="28" background="images/advancequery/bg_content_header.gif"><img src="images/advancequery/t_new_workflow.gif" alt="New Workflow" width="124" height="26" hspace="5" vspace="0">
		</td>
	</logic:equal>
		<logic:equal name="workflowForm" property="operation" value="search">
		<td height="28" background="images/advancequery/bg_content_header.gif"><img src="images/advancequery/t_edit_workflow.gif" alt="New Workflow" width="110" height="26" hspace="5" vspace="0">
		</td>
	</logic:equal>
	<logic:equal name="workflowForm" property="operation" value="edit">
		<td height="28" background="images/advancequery/bg_content_header.gif"><img src="images/advancequery/t_edit_workflow.gif" alt="New Workflow" width="110" height="26" hspace="5" vspace="0">
		</td>
	</logic:equal>


</tr>
<tr>
<td >
<table width="100%" border="0" cellspacing="0" cellpadding="4">
<tr>
<div id="errordiv" >
<table cellspacing="0" cellpadding="3" border="0">
<tbody>
<tr>
<td> </td>
<td class="messagetexterror">
<div id="errormessage">
<%@ include file="/pages/content/common/ActionErrors.jsp" %>
</div>
</td>
</tr>
</tbody>
</table>
</div>
</tr>
	<tr>
		<td height="25"  style="padding-left:10px;"><span class="red_star">*</span> <span class="small_txt_grey">Denotes mandatory fields</span></td>
	</tr>
	<tr>
		<td style="padding-bottom:10px;padding-left:10px;">
			<span class="content_txt_bold"><bean:message key="workflow.name"/></span><span class="red_star">*</span>:<span class="content_txt">
			<html:text styleId="name" property="name" styleClass="textfield_undefined" size="80"/>
			 &nbsp;&nbsp;</span>
		</td>
	</tr>
	<tr>
<td>

<table width="100%" border="0" cellspacing="0" cellpadding="0"   >
	<tr>
	<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="td_subtitle">
				<td height="25" class="blue_title" style="padding-left:4">Queries
				</td>
			</tr>
			<tr class="td_greydottedline_horizontal">
				<td  >
				</td>
			</tr>
		</table>
	</td>
	</tr>
	<tr>
	<td>
	<table  width="100%" border="0" cellspacing="0" cellpadding="0"  >
		<tr>
			<td width="160px"  class="tr_color_lgrey" style="border-right:1px solid #dddddd;" valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="5" colspan="3" valign="middle">
						</td>
					 </tr>
					 <tr>
						<td valign="middle">&nbsp;
						</td>
						<td colspan="2" valign="bottom" class="blue_title">Add Existing Query
						</td>
					</tr>
					 <tr>
					   <td valign="middle">&nbsp;</td>
					   <td valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
					<td valign="middle"><a href="#" class="blacklink" onClick="showPopUp('myQueriesForWorkFlow')"><bean:message
						key="query.myqueries" /></a></td>
					 </tr>
					<tr>
						<td valign="middle">&nbsp;</td>
						<td valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
						<td valign="middle"><a href="#" class="blacklink" onClick="showPopUp('sharedQueriesForWorkFlow')"><bean:message key="query.sharedqueries"/></a></td>
					</tr>
		  
					<tr>
						 <td valign="middle">&nbsp;</td>
						<td colspan="2" >&nbsp;</td>
					 </tr>
					<tr>
						 <td width="12" valign="middle">&nbsp;</td>
						<td colspan="2" valign="bottom" class="blue_title">Add New Query</td>
					</tr>
					<tr>
						<td width="12" valign="middle">&nbsp;</td>
						<td width="10" valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
						 <td valign="middle"><a href="javascript:getCountdata()" class="blacklink"><bean:message key="query.getcount"/></a></td>
					</tr>
					<tr>
						<td width="12" valign="middle">&nbsp;</td>
						<td width="10" valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
						<td valign="middle"><a href="javascript:getPatientdata()" class="blacklink"><bean:message key="query.getpatientdata"/></a></td>
					</tr>
					<tr>
						<td valign="middle">&nbsp;</td>
						<td colspan="2" >&nbsp;</td>
					</tr>
				</table>
			</td>

			<td valign="top">
				<table width="100%" border="0" cellpadding="3" cellspacing="0">
				<tr>
					<td valign="middle">
						<table  border="0" cellspacing="0" cellpadding="0" valign="middle">
							<tr>
							<td>
							<div id="buttonStatus">

							</div>
							</td>
							</tr>
						</table>
					</td>
					<td >
						<table  border="0" cellpadding="4" cellspacing="0" align="right">
                          <tr>
                            <td align="right"  nowrap ><span class="content_txt_bold"><bean:message  key="workflow.project"/></span>&nbsp;
							 <SELECT name="selectedProject" id="selectedProject" class="texttype" onchange="showResetCountPopup()">
							   <option VALUE="-1">Unspecified</option>
								<c:forEach var="project" items="${requestScope.projectsNameValueBeanList}">
									<OPTION VALUE="${project.value}">${project.name}
								</c:forEach>
							</SELECT>
                            </td>
							<td>&nbsp;</td>
                            <td width="90" align="right" valign="middle" ><a href="javascript:runWorkflow()" class="bluelink"><bean:message key="workflow.runworkflow"/></a></td>
                          </tr>
                        </table>
					</td>
				</tr>
				</table>
				<table width="100%" border="0" cellpadding="2" cellspacing="0">
				<tr>
				<td>
					<table width="100%" border="0" cellpadding="2" cellspacing="1"  bgcolor="#EAEAEA">
					  <tr class="td_bgcolor_grey">
					  		<td width="10" height="25" valign="middle" >&nbsp;
							</td>
							<td width="10" height="25" valign="middle" >&nbsp;
							</td>
							</td>

							<td valign="middle" class="grid_header_text"><bean:message key="workflow.queryTitle"/></td>
							<td width="65" valign="middle" class="grid_header_text"><bean:message key="workflow.type"/></td>
							<td width="10%" valign="middle" class="grid_header_text">Select Query</td>
							
							<td width="84" valign="middle" class="grid_header_text"><bean:message key="workflow.patientcount"/> </td>
							<td valign="middle" class="grid_header_text" width="125">&nbsp;</td>
							<!--<td width="55" valign="middle" class="grid_header_text"><bean:message key="workflow.reorder"/></td>-->
					  </tr>
						   <tbody id="table1">
						   <logic:notEmpty name="workflowForm" property="selectedqueryId">
						   			<logic:iterate id="singleQueryId" name="workflowForm" property="selectedqueryId" indexId="queryIndex" >

									<tr bgcolor="#ffffff" class="td_bgcolor_white" height="22">
									

									 <c:set var="qtype" value="${workflowForm.displayQueryType[queryIndex]}"/>
										  <logic:equal name="query_type_data" value="${qtype}">
						   				<td class="content_txt" width="10" valign="top">
												
						   					<c:set var="chkId">chk_<c:out value="${queryIndex}"/></c:set>
						   					<html:checkbox property="chkbox" styleId="checkbox_${queryIndex}"
											onclick="javascript:setCheckboxCount()" disabled="true"></html:checkbox>
						   				</td>
										</logic:equal>
										<logic:notEqual name="query_type_data" value="${qtype}">
											<td class="content_txt" width="10" valign="top">
												
						   					<c:set var="chkId">chk_<c:out value="${queryIndex}"/></c:set>
						   					<html:checkbox property="chkbox" styleId="checkbox_${queryIndex}"
											onclick="javascript:setCheckboxCount()" disabled="false"></html:checkbox>
						   				</td>
										</logic:notEqual>
									 <td  width="10" valign="middle" valign="top" style="padding:1px 5px 0 5px">
												<img src="images/advancequery/ic_notrun06.gif" alt=""  align="absmiddle"
												id="notStarted_${queryIndex}">
									   </td>

  										<td class="content_txt" valign="top">
						   					<html:hidden property="displayQueryTitle" styleId="displayQueryTitle_${queryIndex}" value="${workflowForm.displayQueryTitle[queryIndex]}"
						   					/>
											${workflowForm.displayQueryTitle[queryIndex]}
											
											<html:hidden property="expression" styleId="expression_${queryIndex}" value="${workflowForm.expression[queryIndex]}"
						   					/>
						   				</td>


											
										<td class="content_txt" valign="top">
											<html:hidden property="queryTypeControl" styleId="queryTypeControl_${queryIndex}" value="${workflowForm.queryTypeControl[queryIndex]}"/>
											${workflowForm.displayQueryType[queryIndex]}
										</td>
										<td class="content_txt"  valign="top">
										 
										 <logic:equal name="query_type_data" value="${qtype}">
										 <select name="countQueryDropDown" disabled id="countQueryDropDown_${workflowForm.identifier[queryIndex]}" style="width:120;">
										<!-- <logic:notEmpty name="workflowForm" property="selectedqueryId">
						   			       <logic:iterate id="singleQueryId" name="workflowForm" property="selectedqueryId" indexId="qIndx" >
										  <logic:equal name="query_type_count" value="${workflowForm.displayQueryType[qIndx]}">
										 <OPTION VALUE="${workflowForm.identifier[qIndx]}">${workflowForm.displayQueryTitle[qIndx]}</OPTION></logic:equal></logic:iterate></logic:notEmpty>--></select>
                                         </logic:equal>
										  </td>

										<td class="content_txt" valign="top">
											
											<input type="hidden" name="selectedqueryId" id="selectedqueryId_${queryIndex}" value="${workflowForm.selectedqueryId[queryIndex]}"/>						
											<!-- <input type="hidden" name="identifier" id="identifier_${workflowForm.identifier[queryIndex]}" value="${workflowForm.identifier[queryIndex]}"/> -->
											 <input type="hidden" name="identifier" id="identifier_${queryIndex}" value="${workflowForm.displayQueryTitle[queryIndex]}"/>
											 <input type="hidden" name="queryIdForRow" id="queryIdForRow_${workflowForm.queryIdForRow[queryIndex]}" value="${workflowForm.queryIdForRow[queryIndex]}"/>
											 <input type="hidden" name="queryExecId" id="queryExecId_${queryIndex}" value="${workflowForm.queryExecId[queryIndex]}"/>

										</td>
										<td width="125" valign="top">
										<table >
										<tbody>
										<tr>
										
                                             <logic:equal name="query_type_data" value="${qtype}">
										  <td width="100" valign="top">
											<html:link styleId="execute_${queryIndex}" href="javascript:executeGetDataQuery('${workflowForm.identifier[queryIndex]}')" styleClass="bluelink"
											>
												View Results
											</html:link>
										</td>
										</logic:equal>
										
										<logic:notEqual name="query_type_data" value="${qtype}">
                                        <td valign="top" width="100">
											<html:link styleId="execute_${queryIndex}" href="#" styleClass="bluelink" onclick="javascript:test('${workflowForm.displayQueryTitle[queryIndex]}')"
											>
												Execute
											</html:link>
										</td>
                                        </logic:notEqual>
										<td valign="top">
											<html:hidden property="operands" styleId="operands_${queryIndex}"  value="${workflowForm.operands[queryIndex]}"/>
											<html:hidden property="operators" styleId="operators_${queryIndex}" value="${workflowForm.operators[queryIndex]}"/>
											<html:hidden property="displayQueryType" styleId="displayQueryType_${queryIndex}" value="${workflowForm.displayQueryType[queryIndex]}"/>
											<html:hidden property="queryExecId" styleId="queryExecId_${queryIndex}" value="${workflowForm.queryExecId[queryIndex]}"/>
										</td>
											<td valign="top" width="25">
											<html:link styleId="delete_${queryIndex}" href="javascript:deleteWorkflowItem(${queryIndex})" styleClass="bluelink">
												Delete
											</html:link>

														
											<html:hidden property="cancelajaxcall" styleId="cancelajaxcall_${queryIndex}" value="false"/>
								
											</td>
											</tr>
											</tbody>
											</table>
										</td>

									<!--<td  align="center"><table border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td><img id="up_${queryIndex}" src="images/advancequery/ic_up.gif" onMouseOver="Tip('Move Up')"></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img id="down_"${queryIndex}" src="images/advancequery/ic_down.gif" onMouseOver="Tip('Move Down')" ></td>
                                      </tr>
                                    </table>
									</td>
									-->
						   			</tr>
									</logic:iterate>
								</logic:notEmpty>
						   </tbody>
                    </table>
				</td>
				</tr>
				</table>
			</td>
		</tr>
</table>
</td>
</tr>
<tr class="td_bgcolor_grey"><td height="1"></td></tr>
</table>
<table border="0" cellspacing="0" cellpadding="2" height="30">
	
	<tr>
		<td align="left" width="65" valign="middle"><a href="javascript:submitWorflow()">
		<img src="images/advancequery/b_save.gif" alt="Save" width="55" height="23" border="0"></a></td>
		<td width="76" align="left" valign="middle"><a href="javascript:cancelWorkflow()"><img src="images/advancequery/b_cancel.gif" alt="Cancel" width="66" height="23" border='0' align="absmiddle">
		</td>
	</tr>
</table>
</td>
	</tr>
</table>



</td>
</tr>
</table>

</td>
</tr>
</table>
<table width="100%">
	<tr>
		<td height="30"></td>
	</tr>
</table>
</html:form>
<script type="text/javascript">
function setButtons()
{
	//var buttonStatusDiv=document.getElementById("buttonStatusDiv");
	var buttonStatus=document.getElementById("buttonStatus");


		buttonStatus.innerHTML= '<table cellpadding="0" cellspacing="0"><tr><td align="left" ><img align="absmiddle" src="images/advancequery/b_union_inact.gif" alt="Union"  border="0">'+
      ' <td width="108" align="middle"><img align="absmiddle" src="images/advancequery/b_intersection_inact.gif" alt="Intersection" border="0"></td><td align="left"><img align="absmiddle" src="images/advancequery/b_minus_inact.gif" alt="Minus"  border="0"></td> </tr></table>';
	


}setButtons();


function updateOpenerUI() 
{ 
  if(document.getElementById)
	{ 
         elm =document.getElementById("btn");
     } 

	if (document.all)
		{
			elm.fireEvent('onclick');
		}
	else
		{
		var clickEvent = window.document.createEvent('MouseEvent');
        clickEvent.initEvent('click', false, true);
        elm.dispatchEvent(clickEvent);
		}
 }updateOpenerUI();
 setCheckboxCount();
 function forwardToWorkflow()
{
	
	
	<%		
		String id =(String) request.getAttribute("worflowId");
	%>
	
	
	if(<%=id%>!=null)
	{
		
		document.forms[0].action ="SearchObject.do?pageOf=pageOfWorkflow&id="+<%=id%>;
		document.forms[0].submit();

	}
}forwardToWorkflow();
function setPreviousProject()
{
	previousProject=document.getElementById('selectedProject').value;
}setPreviousProject();

function retrieveCounts()
{
	var rows=parent.window.document.getElementById("table1").rows.length;
	for(var i=0;i<rows;i++)
	{
		var executionId=parent.window.document.getElementById("queryExecId_"+i).value;
		var id=parent.window.document.getElementById("queryExecId_"+i).id;
		var object=parent.window.document.getElementById("queryExecId_"+i).parentNode;//parent.window.id.parentNode;
		var tdChildCollection=object.getElementsByTagName('input');
		var queryIdForRow=tdChildCollection[2].id;
		var queryId=parent.window.document.getElementById(queryIdForRow).value;
		if(executionId!='0' && executionId!="")
		{
			workflowExecuteGetCountQuery(queryId,executionId);
		}
	}
}retrieveCounts();
</script>
</body>
