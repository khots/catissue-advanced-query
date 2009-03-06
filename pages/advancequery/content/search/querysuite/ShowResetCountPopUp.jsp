<%@ page import="java.util.*"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css" />
<script  src="jss/advancequery/workflows.js"></script>
<script src="jss/ajax.js"></script>	
<script type="text/JavaScript">
function abortCountExecution()
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
    	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
		var selectedquery=selectedqueryId.split("_");
		var queryIndex=selectedquery[1];
		if(executionId!='0' && executionId!="")
		{
			//alert("queryIdForRow="+queryIdForRow);

			//cancelGetCountQuery(queryId,executionId);
			//removeresultCount(queryId);
			parent.window.cancelGetCountQuery(queryId,executionId, 'true') ;
		}
	}
	//alert("ends");
	//updateOpenerUI();
	//alert("removeCountResults1");

	cancelChnageProjectRequest();
}

function cancelChnageProjectRequest()
{
	parent.pvwindow.hide();
}
/*

function updateOpenerUI() { 
	parent.window.removeCountResults() ;
	var elm;
//  if(document.getElementById) { 
         elm = parent.window.document.getElementById("btn1");
    //   } 

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
     } 



function cancelGetCountQuery(queryId,executionLogId)
{	

	var identifier=parent.window.document.getElementById("queryIdForRow_"+queryId);
	var object=parent.window.document.getElementById("queryIdForRow_"+queryId).parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
	var selectedquery=selectedqueryId.split("_");

	index=selectedquery[1];

	parent.window.document.getElementById("cancelajaxcall_"+index).value='true';
	
	var projectId=parent.window.document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&queryId="+queryId+'&state='+'cancel'+"&executionLogId="+executionLogId+"&workflowId="+parent.window.document.getElementById("id").value+"&selectedProject="+projectId;
	changeExecuteLinkToExecute(queryId,0);
	//removeresultCount(queryId);
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
          if(jsonResponse.executionQueryResults!=null)
          {
             var num = jsonResponse.executionQueryResults.length; 
				for(var i=0;i<num;i++)
				{
					var queryId = jsonResponse.executionQueryResults[i].queryId;
					var identifier=parent.window.document.getElementById("queryIdForRow_"+queryId);
					var object=parent.window.document.getElementById("queryIdForRow_"+queryId).parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
					var tdChildCollection=object.getElementsByTagName('input');

					var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
					var selectedquery=selectedqueryId.split("_");
					
					index=selectedquery[1];
					parent.window.document.getElementById("cancelajaxcall_"+index).value='false';

				}
		  }
}

function changeExecuteLinkToExecute(queryId,executionLogId)
{
	var identifier=parent.window.document.getElementById("queryIdForRow_"+queryId);
	var object=parent.window.document.getElementById("queryIdForRow_"+queryId).parentNode;
	var tdChildCollection=object.getElementsByTagName('input');

	var selectedqueryId=tdChildCollection[0].id;
	var selectedquery=selectedqueryId.split("_");
	
	index=selectedquery[1];
	
	var object2=parent.window.document.getElementById("cancel_"+index);

	//To get the query title field of document
	var queryTitle=parent.window.document.getElementById("displayQueryTitle_"+index).value;

	if(object2!=null)
	{
		var parentIObj=object2.parentNode;
		parentIObj.removeChild(object2);
		parentIObj.appendChild(createLink("Execute ","execute_"+index,"javascript:executeGetCountQuery('"+queryTitle+"','"+0+"')"));
		
	}


}
function removeresultCount(queryId)
{
	var identifier=parent.window.document.getElementById("queryIdForRow_"+queryId);
	var object=parent.window.document.getElementById("queryIdForRow_"+queryId).parentNode;
	var tdChildCollection=object.getElementsByTagName('input');

	var selectedqueryId=tdChildCollection[0].id;
	var selectedquery=selectedqueryId.split("_");
	
	index=selectedquery[1];
	//alert("index="+index);
	var lableObject=parent.window.document.getElementById("label_"+index);
	//alert("lableObject id ="+lableObject.id);

		lableObject.innerHTML="";


	alert(lableObject.innerHTML);
	alert(lableObject.innerHTML);

}

function createLink(displayValue,text,url)
{
	var link=parent.window.document.createElement('a');
	link.className="bluelink";
	link.href=url;
	link.id=text;
	var text=createTextElement(displayValue);
	link.appendChild(text);
	return link;
}

function createTextElement(text)
{
	var textnode=parent.window.document.createTextNode(text);
	return textnode;
}*/

</script>
<html>
<body>
<body>
<table width="96%" height="100%" border="0" cellpadding="0" cellspacing="0" align = "right">
  <tr align = "center">
    <td style = "padding-left:10px;padding-right:10px" align="center" valign="middle"><table width="100%" height="90" border="0" align="center" cellpadding="0" cellspacing="0" class="dynamic_table_bg">
      <tr>
        <td height="23" align="center" class="content_txt">Selecting another project will erase the query counts for the current project. Do you want to continue?</td>
      </tr>
      <tr>
        <td height="35" align="center"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width = "60"><a href="javascript:abortCountExecution()"><img src="images/advancequery/b_yes.gif" alt="Yes" width="50" height="23" border="0" /></a></td>
            <td><a href="javascript:cancelChnageProjectRequest()"><img src="images/advancequery/b_no.gif" alt="No" width="43" height="23" border="0" /></a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</body>
</html>