<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="edu.wustl.query.util.global.CompositeQueryOperations"%>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />
<script  src="jss/advancequery/workflows.js"></script>
<script  src="jss/advancequery/wz_tooltip.js"></script>
<script src="jss/ajax.js"></script>	
<script type="text/JavaScript">

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function showPopUp(pageOf)
{
	var url='QueryAction.do?pageOf='+pageOf+'&queryId=queryId&queryTitle=queryTitle&queryType=queryType';
	pvwindow=dhtmlmodal.open('Select queries', 'iframe', url,'Select queries', 'width=930px,height=430px,center=1,resize=1,scrolling=1');
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
	}		
}

function createCQ(queryIdsToAdd,operation,queryCount)
{
	var queryIds=queryIdsToAdd.split(",");
	var operandsTdContent="";
	var cqTitle="";
	var cqQueryId="";
	var operandsCounter=0;
	for(var counter=0;counter<queryIds.length;counter++)
	{
		if(queryIds[counter]!=null && queryIds[counter]!='')
		{
			if( cqTitle=='')
			{
				cqTitle="[Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+"]";
			}
			else
			{
				cqTitle=cqTitle+"  "+operation+"  "+ "[Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+"] ";
			}
			if(cqQueryId=='')
			{
				cqQueryId="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=document.getElementById("selectedqueryId_"+queryIds[counter]).value;
			}
			else
			{
				cqQueryId=cqQueryId+"_"+operation+"_("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=operandsTdContent+"_"+document.getElementById("selectedqueryId_"+queryIds[counter]).value;
			}
			operandsCounter=operandsCounter+1;
		}
	}
	var cqType="Operation";
	var cqId="";

	var rowContents=new Array(7);
	rowContents[0]=createCheckBox("chkbox","checkbox_"+queryCount,'',queryCount);
	rowContents[1]=createTextElement(cqTitle);
	rowContents[2]=createTextElement(cqType);
	//rowContents[3]=createTextElement(operandsTdContent);
	//rowContents[3]=getSelectObjectControl();
	rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+queryCount,operandsTdContent);
	//rowContents[4].appendChild(createHiddenElement("identifier","identifier_"+operandsTdContent,operandsTdContent));
	rowContents[5]=cqTitle;
	rowContents[6]=cqType;
	
	var operatorsTdContent=operation;
	//uncommented for the underscore separated operation string 
	for(var counter=0;counter<operandsCounter-2;counter++)
	{
		operatorsTdContent=operatorsTdContent+"_"+operation;
	}
	//create a table containing tbody with id "table1"
	addRowToTable("table1",rowContents,operandsTdContent,operatorsTdContent);	
}

function submitWorflow()
{
	document.forms[0].submit();
}
function cancelGetCountQuery(queryId,executionLogId)
{	
	var identifier=document.getElementById("identifier_"+queryId);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
	var selectedquery=selectedqueryId.split("_");
	
	index=selectedquery[1];

	document.getElementById("cancelajaxcall_"+index).value='true';
	var ID=document.getElementById("selectedqueryId_"+index).value;

	var url="WorkflowAjaxHandler.do?operation=execute&queryId="+queryId+'&state='+'cancel'+"&ID="+ID+"&executionLogId="+executionLogId;
	changeExecuteLinkToExecute(queryId,0);
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
					var identifier=document.getElementById("identifier_"+queryId);
					var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
					var tdChildCollection=object.getElementsByTagName('input');

					var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
					var selectedquery=selectedqueryId.split("_");
					
					index=selectedquery[1];
					document.getElementById("cancelajaxcall_"+index).value='false';

				}
		  }
}

function executeGetCountQuery(queryId,executionLogId)
{
	var ID=queryId;//document.getElementById("selectedqueryId_"+queryId).value;
	//var identifier=(document.getElementById("selectedqueryId_"+queryId).childNode).value;
	var projectId=document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&queryId="+queryId+'&state='+'start'+"&ID="+ID+"&executionLogId="+executionLogId+"&selectedProject="+projectId;
	

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

function responseHandler(response)
{
	  var jsonResponse = eval('('+ response+')');
          var hasValue = false;
          if(jsonResponse.executionQueryResults!=null)
          {
             var num = jsonResponse.executionQueryResults.length; 
				for(var i=0;i<num;i++)
				{
					 var queryId = jsonResponse.executionQueryResults[i].queryId;
					 //var queryIndex = jsonResponse.executionQueryResults[i].queryIndex;
					 var queryResult = jsonResponse.executionQueryResults[i].queryResult;
					 var status = jsonResponse.executionQueryResults[i].status;
					 var executionLogId = jsonResponse.executionQueryResults[i].executionLogId;
					 var queryIndex=-1; 	 
					if(queryResult!=-1)
					{
						var identifier=document.getElementById("identifier_"+queryId);
						var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
						var tdChildCollection=object.getElementsByTagName('input');

						var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
						var selectedquery=selectedqueryId.split("_");
						
						queryIndex=selectedquery[1];
						
						//var parentIObj=object.parentNode;
						var lableObject=document.getElementById("label_"+queryIndex);
						if(lableObject!=null)
						{
							object.removeChild(lableObject);
						}
						object.appendChild(createLabel(queryResult,queryIndex));
						
					}
					
					if((document.getElementById("cancel_"+queryIndex)==null)&&(document.getElementById("cancelajaxcall_"+queryIndex).value=='false'))
					{
						changeLinkToCancel(queryId,executionLogId);
					}
				
					if((status!="Completed")&&document.getElementById("cancelajaxcall_"+queryIndex).value=='false')
					{
						executeGetCountQuery(queryId,executionLogId);
						

					}
					
					if((status=="Completed"))
					{
						
						changeExecuteLinkToExecute(queryId,0);

					}
				
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
	document.forms[0].action="SaveWorkflow.do?submittedFor=ForwardTo&nextpageOf=queryGetCount";
	document.forms[0].submit();
}
function getPatientdata()
{
	document.forms[0].forwardTo.value= "loadQueryPage";
	document.forms[0].action="SaveWorkflow.do?submittedFor=ForwardTo&nextpageOf=queryWizard";
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
<%@ include file="/pages/advancequery/common/ActionErrors.jsp" %>

<html:form action="SaveWorkflow" >

<html:hidden property="operation" styleId="operation" value="${requestScope.operation}"/>
<html:hidden property="id" styleId="id" value="${requestScope.id}"/>
<select name="queryId" id="queryId"  style="display:none">
	<logic:notEmpty name="workflowForm" property="queryId">
	<logic:iterate id="singleQueryId" name="workflowForm" property="queryId" indexId="queryIndex" >
		<OPTION VALUE="${workflowForm.queryId[queryIndex]}">${workflowForm.queryId[queryIndex]}</OPTION>
	</logic:iterate>
	</logic:notEmpty>
</select>
<select name="queryTitle" id="queryTitle"  style="display:none">
	<logic:notEmpty name="workflowForm" property="queryId">
	<logic:iterate id="singleQueryId" name="workflowForm" property="queryTitle" indexId="queryIndex" >
		<OPTION VALUE="${workflowForm.queryTitle[queryIndex]}">${workflowForm.queryTitle[queryIndex]}</OPTION>
	</logic:iterate>
	</logic:notEmpty>
</select>
<select name="queryType" id="queryType"  style="display:none">
<logic:notEmpty name="workflowForm" property="queryId">
	<logic:iterate id="singleQueryId" name="workflowForm" property="queryType" indexId="queryIndex" >
		<OPTION VALUE="${workflowForm.queryType[queryIndex]}">${workflowForm.queryType[queryIndex]}</OPTION>
	</logic:iterate>
	</logic:notEmpty>
</select>
<html:hidden property="forwardTo"/>

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
		<td height="25"><span class="red_star">*</span> <span class="small_txt_grey">Denotes mandatory fields</span></td>
	</tr>
	<tr>
		<td style="padding-bottom:10px;">
			<span class="content_txt_bold"><bean:message key="workflow.name"/></span><span class="red_star">*</span>:<span class="content_txt">
			<html:text styleId="name" property="name" styleClass="textfield_undefined" size="80"/>
			 &nbsp;&nbsp;</span>
		</td>
	</tr>
</table>

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
						<td valign="middle"><a href="#" class="blacklink" onClick="showPopUp('publicQueryForWorkFlow')"><bean:message key="query.sharedqueries"/></a></td>
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
                            <td align="right"  nowrap><span class="content_txt_bold"><bean:message  key="workflow.project"/></span>&nbsp;
							 <SELECT name="selectedProject" id="selectedProject" class="texttype" >
							   <option VALUE="-1">Select..</option>
								<c:forEach var="project" items="${requestScope.projectsNameValueBeanList}">
									<OPTION VALUE="${project.value}">${project.name}
								</c:forEach>
							</SELECT>
                            </td>
                            <td width="90" align="left" valign="middle" ><a href="javascript:showNextReleaseMsg()" class="bluelink"><bean:message key="workflow.runworkflow"/></a></td>
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
							</td>

							<td valign="middle" class="grid_header_text"><bean:message key="workflow.queryTitle"/></td>
							<td width="111" valign="middle" class="grid_header_text"><bean:message key="workflow.type"/></td>
							
							<td width="100" valign="middle" class="grid_header_text"><bean:message key="workflow.patientcount"/> </td>
							<td width="90" valign="middle" class="grid_header_text">&nbsp;</td>
							<!--<td width="55" valign="middle" class="grid_header_text"><bean:message key="workflow.reorder"/></td>-->
					  </tr>
						   <tbody id="table1">
						   <logic:notEmpty name="workflowForm" property="selectedqueryId">
						   			<logic:iterate id="singleQueryId" name="workflowForm" property="selectedqueryId" indexId="queryIndex" >

									<tr bgcolor="#ffffff" class="td_bgcolor_white" height="22">
						   				<td class="content_txt" width="10">
						   					<c:set var="chkId">chk_<c:out value="${queryIndex}"/></c:set>
						   					<html:checkbox property="chkbox" styleId="checkbox_${queryIndex}"
											onclick="javascript:setCheckboxCount()"></html:checkbox>
						   				</td>
  										<td class="content_txt">
						   					<html:hidden property="displayQueryTitle" styleId="displayQueryTitle_${queryIndex}" value="${workflowForm.displayQueryTitle[queryIndex]}"
						   					/>
											${workflowForm.displayQueryTitle[queryIndex]}
						   				</td>


											
										<td class="content_txt">
											<html:hidden property="queryTypeControl" styleId="queryTypeControl_${queryIndex}" value="${workflowForm.queryTypeControl[queryIndex]}"/>
											${workflowForm.displayQueryType[queryIndex]}
										</td>


										<td class="content_txt">
											
											<input type="hidden" name="selectedqueryId" id="selectedqueryId_${queryIndex}" value="${workflowForm.selectedqueryId[queryIndex]}"/>						
											<input type="hidden" name="identifier" id="identifier_${workflowForm.identifier[queryIndex]}" value="${workflowForm.identifier[queryIndex]}"/>

										</td>
										<td width="100">
										<table >
										<tbody>
										<tr>
										<td>
											<html:link styleId="execute_${queryIndex}" href="javascript:executeGetCountQuery('${workflowForm.identifier[queryIndex]}','0')" styleClass="bluelink"
											>
												Execute
											</html:link>
										</td>
										<td>
											<html:hidden property="operands" styleId="operands_${queryIndex}"  value="${workflowForm.operands[queryIndex]}"/>
											<html:hidden property="operators" styleId="operators_${queryIndex}" value="${workflowForm.operators[queryIndex]}"/>
											<html:hidden property="displayQueryType" styleId="displayQueryType_${queryIndex}" value="${workflowForm.displayQueryType[queryIndex]}"/>
										</td>
											<td>
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
</script>
</body>
