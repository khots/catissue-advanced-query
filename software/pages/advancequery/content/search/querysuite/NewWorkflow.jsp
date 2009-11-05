<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="edu.wustl.query.util.global.CompositeQueryOperations"%>
<%@ page import="edu.wustl.query.enums.QueryType" %>
<%@page import="edu.wustl.query.util.global.Constants"%>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />
<script  src="jss/advancequery/Newworkflows.js"></script>
<script type="text/javascript" src="jss/javaScript.js"></script>

<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<!-- for jquery -->
	<script type="text/javascript" src="jss/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="jss/jquery.corners.js"></script>
	<link href="css/advancequery/jquery.cluetip.css" rel="stylesheet" type="text/css" />
	<script src="jss/advancequery/jquery.cluetip.js" type="text/javascript"></script>
	<script src="jss/advancequery/jquery.hoverIntent.js" type="text/javascript"></script>
	<script src="jss/advancequery/blockUI.js" type="text/javascript"></script>
<!--ends-->
<script type="text/JavaScript">
//Thhis array maintainds the check box click order
var orderOfCheckBoxClick=new Array();

<%
String qType_GetCount= (QueryType.GET_COUNT).type;
String qType_GetData= (QueryType.GET_DATA).type;
%>
/*to prelaod the images*/
jQuery.preloadImages = function()
{
  for(var i = 0; i<arguments.length; i++)
  {
    jQuery("<img>").attr("src", arguments[i]);
  }
}

jQuery().ready(function(){

	$.preloadImages(['images/advancequery/inprogress09.gif', 'images/advancequery/ic_complete05.gif','images/advancequery/ic_notrun06.gif',
		'images/advancequery/ic_wf_cancel.gif','images/advancequery/ic_wf_error.gif','images/advancequery/arrow_close.png',
		'images/advancequery/arrow_open.png','images/advancequery/ic_delete.gif','images/advancequery/run_workflow_blue.gif'
	,'images/advancequery/load.gif','images/advancequery/b_add_new.gif','images/advancequery/b_add_existing.gif','images/advancequery/b_save.gif',
		'images/advancequery/b_cancel.gif']);

setTextAreasize();
	//JT_init();
	//$('#showHideCount').click(function(){showDiv('CountDiv')});
//	$('#showHideData').click(function(){showDiv('DataDiv')});
$('#showHideCount').toggle(function(){showDiv('CountDiv')},function(){hideDiv('CountDiv')});
$('#showHideData').toggle(function(){showDiv('DataDiv')},function(){hideDiv('DataDiv')});
	$('.headerDiv').corner("10px");
	$('.outerDiv').corner("10px");//.parent().css('padding', '1px').corner();
	$('.outerDiv1').corner("10px");
	//	$('.roundcornerbottom').corner('bottom');s
	//	$('.roundedcornerinner').corner('20px');

	//removed as not working in IE
	//$('#submitWorkflow').attr('href',"javascript:submitWorflow()");
	//$('#cancel').attr('href',"javascript:cancelWorkflow()");

	  var submitControl= document.getElementById('submitWorkflow');
	  submitControl.href="javascript:submitWorflow()";

    var cancelControl = document.getElementById('cancel');
	cancelControl.href="javascript:cancelWorkflow()";

	  var addNewDataQuery = document.getElementById('addNewDataQuery');
	addNewDataQuery.href="javascript:getPatientdata()";

	 var addNewCountQuery = document.getElementById('addNewCountQuery');
	addNewCountQuery.href="javascript:getCountdata()";

	var showDataQueries = document.getElementById('showDataQueries');
	showDataQueries.href="javascript:showPopUp('myQueriesForWorkFlow','Data')";

	var showCountQueries = document.getElementById('showCountQueries');
	showCountQueries.href="javascript:showPopUp('myQueriesForWorkFlow','Count')";

	//ADDING EVENTS RELATED TO THE drop down
	$('#selectedProject').mouseover(function(){showTip('selectedProject');});
	$('#selectedProject').change(function(){showResetCountPopup();});
	$('#selectedProject').mouseout(function(){hideTip('selectedProject');});

	//adding events on drop down containing executed Queries
	$('#countQueryDropDown').mouseover(function(){showTip('countQueryDropDown');});
	$('#countQueryDropDown').mouseout(function(){hideTip('countQueryDropDown');});

	var runWorkflow = document.getElementById('runWorkflow');
	runWorkflow.href="javascript:runWorkflow()";

	//showHide('CountDiv');
	//showHide('DataDiv');
	setButtons();
	populateUI();
	setPreviousProject();
	retrieveCounts();
	setQueryCount();

	/*	$('#loading').ajaxStart(function() {
	$(this).show();
	}).ajaxStop(function() {
	$(this).hide();
	});*/
	//JT_init();
	});
/*This method loads the contents for saved workflow*/
function populateUI()
{
	if('${workflowForm.id}'!=null&&'${workflowForm.id}'!=""&&'${workflowForm.id}'!="0")
	{
		document.getElementById("id").value='${workflowForm.id}';
		document.getElementById("operation").value="edit";
	}
	/*if('${workflowForm.operation}'!=null&&'${workflowForm.operation}'!="")
	{
		document.getElementById("operation").value='${workflowForm.operation}';
	}*/

	<logic:notEmpty name="workflowForm" property="displayQueryType">
	<logic:iterate id="queryType" name="workflowForm" property="displayQueryType" indexId="queryIndex" >
		<c:choose>
			<c:when test="${queryType=='Data'}">

			var dataTable=document.getElementById("dataTable");
			var rowCount=dataTable.rows.length;
			var rowObj=document.createElement("tr");
			rowObj.className="tr_bgcolor_white";
			rowObj.width="100%";
			rowObj.id="dataTableRow"+rowCount;



		/*	var td1=document.createElement("td");
			td1.width="2%";
			td1.align="middle";
			td1.appendChild(createCheckBox("chkbox","datacheckbox_"+rowCount,'',rowCount,true));
			rowObj.appendChild(td1);*/


			var td11=document.createElement("td");
			td11.innerHTML="&nbsp;";
			td11.width="2%";
			td11.className="td_grey_start";
			rowObj.appendChild(td11);


			var td2=document.createElement("td");
			td2.width="91%";
			td2.className="td_grey_text";
			//td2.className="content_txt";
			td2.appendChild(createTextElement('${workflowForm.displayQueryTitle[queryIndex]}'));
			rowObj.appendChild(td2);

			var td3=document.createElement("td");
			//td3.width="0%";
			td3.style.display="none";
			td3.appendChild(createHiddenElement("displayQueryType","displayDataQueryType_"+rowCount,'${queryType}'));
			rowObj.appendChild(td3);

			var td4=document.createElement("td");
			td4.style.display="none";
			//td4.width="0%";
			td4.appendChild(createHiddenElement("displayQueryTitle","displayDataQueryTitle_"+rowCount,'${workflowForm.displayQueryTitle[queryIndex]}'));
			rowObj.appendChild(td4);

			var td5=document.createElement("td");
			td5.style.display="none";
			//td5.width="0%";
			td5.appendChild(createHiddenElement("expression","dataQueryExpression_"+rowCount,'${workflowForm.expression[queryIndex]}'));
			rowObj.appendChild(td5);

			//td6.appendChild(createHiddenElement("workflowItemId","dataWorkflowItemId_"+rowCount,'${workflowForm.workflowItemId[queryIndex]}'));
			//rowObj.appendChild(td6);

			var td7=document.createElement("td");
			td7.style.display="none";
			//td7.width="0%";
			td7.appendChild(createHiddenElement("selectedqueryId","selectedDataqueryId_"+rowCount,'${workflowForm.selectedqueryId[queryIndex]}'));
			td7.appendChild(createHiddenElement("identifier","dataIdentifier_"+rowCount,'${workflowForm.displayQueryTitle[queryIndex]}'));
			td7.appendChild(createHiddenElement("queryIdForRow","dataQueryIdForRow_${workflowForm.queryIdForRow[queryIndex]}",'${workflowForm.queryIdForRow[queryIndex]}'));
			td7.appendChild(createHiddenElement("queryExecId","dataQueryExecId_"+rowCount,'${workflowForm.queryExecId[queryIndex]}'));

			rowObj.appendChild(td7);
			dataTable.appendChild(rowObj);

		var operandsTd=document.createElement("td");
		operandsTd.className="td_grey";
		operandsTd.align="middle";
		operandsTd.width="6%";
		//operandsTd.className="aligntop";

		var tble1=document.createElement("table");
		var tbody1=document.createElement("tbody");
		var operandsTr=document.createElement("tr");
		operandsTr.className="tr_bgcolor_white";
		var operandsTd1=document.createElement("td");
		operandsTd1.align="middle";
		operandsTd1.width="3%";
		var operandsTd3=document.createElement("td");
		operandsTd3.width="3%";
		operandsTd3.align="middle";



		operandsTd1.appendChild(createLink("View Results ","images/advancequery/execute-button-1.PNG","executeData_"+rowCount,"javascript:executeGetDataQuery('"+'${workflowForm.queryIdForRow[queryIndex]}'+"')"));
	// href removed and image is added
	//operandsTd3.appendChild(createLink("Delete ","deleteData_"+rowCount,"javascript:deleteWorkflowItem('"+rowCount+"','"+'${queryType}'+"')"));
		operandsTd3.appendChild(createLink("Delete","images/advancequery/ic_delete.gif ","deleteData_"+rowCount,"javascript:deleteWorkflowItem('"+rowCount+"','"+'${queryType}'+"')"));

		operandsTr.appendChild(operandsTd1);
		operandsTr.appendChild(operandsTd3);
		tbody1.appendChild(operandsTr);
		tble1.appendChild(tbody1);
		operandsTd.appendChild(tble1);
		rowObj.appendChild(operandsTd);

			</c:when>

			<c:otherwise>
					var countTable=document.getElementById('countTable');
					var rowCount=countTable.rows.length;
					var rowObj=document.createElement("tr");
					rowObj.className="tr_bgcolor_white";
					rowObj.width="100%";
					rowObj.id="countRow"+rowCount;
					
					var td1=document.createElement("td");
					td1.width="2%";
					td1.align="middle";
					td1.className="td_grey_start";
					td1.appendChild(createCheckBox("chkbox","checkbox_"+rowCount,'',rowCount,false));
					rowObj.appendChild(td1);

					var td2=document.createElement("td");
					td2.width="3%";
					td2.className="td_grey";
					td2.align="middle";
					td2.appendChild(createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+rowCount));
					rowObj.appendChild(td2);

					var td3=document.createElement("td");
					td3.width="78%";
					td3.className="td_grey_text";
					td3.appendChild(createTextElement('${workflowForm.displayQueryTitle[queryIndex]}'));
					rowObj.appendChild(td3);



					var td5=document.createElement("td");
				   //td5.width="0%";
					 td5.style.display="none"; td5.appendChild(createHiddenElement("displayQueryType","displayQueryType_"+rowCount,'${workflowForm.displayQueryType[queryIndex]}'));
					rowObj.appendChild(td5);

					var td6=document.createElement("td");
				   //td6.width="0%";
				   td6.appendChild(createHiddenElement("displayQueryTitle","displayQueryTitle_"+rowCount,'${workflowForm.displayQueryTitle[queryIndex]}'));
				   td6.style.display="none";
					rowObj.appendChild(td6);

					var td7=document.createElement("td");
					td7.width="10%";
					td7.align="middle";
					td7.className="td_grey";
					if('${workflowForm.expression[queryIndex]}'.indexOf('_')!=-1)
					{
							td7.appendChild(createLabelCQ("",rowCount));
					}
					else
					{
						td7.appendChild(createLabel("",rowCount));
					}

					rowObj.appendChild(td7);

					var td8=document.createElement("td");
				    td8.style.display="none";
					td8.appendChild(createHiddenElement("expression","expression_"+rowCount,'${workflowForm.expression[queryIndex]}'));
					td8.style.display="none";
					rowObj.appendChild(td8);

					//td9.appendChild(createHiddenElement("workflowItemId","workflowItemId_"+rowCount,'${workflowForm.workflowItemId[queryIndex]}'));
					//rowObj.appendChild(td9);

					var td4=document.createElement("td");
					td4.style.display="none";
					td4.appendChild(createHiddenElement("selectedqueryId","selectedqueryId_"+rowCount,'${workflowForm.selectedqueryId[queryIndex]}'));
					td4.appendChild(createHiddenElement("identifier","identifier_"+rowCount,'${workflowForm.displayQueryTitle[queryIndex]}'));
					td4.appendChild(createHiddenElement("queryIdForRow","queryIdForRow_${workflowForm.queryIdForRow[queryIndex]}",'${workflowForm.queryIdForRow[queryIndex]}'));
					td4.appendChild(createHiddenElement("queryExecId","queryExecId_"+rowCount,'${workflowForm.queryExecId[queryIndex]}'));
					rowObj.appendChild(td4);



				var operandsTd=document.createElement("td");
				operandsTd.className="td_grey";
				operandsTd.align="middle";
				operandsTd.width="6%";
				var tble1=document.createElement("table");

				var tbody1=document.createElement("tbody");
				var operandsTr=document.createElement("tr");
				operandsTr.className="tr_bgcolor_white";
				var operandsTd1=document.createElement("td");
				operandsTd1.align="middle";
				var operandsTd3=document.createElement("td");
				operandsTd3.align="middle";


				var t =	escape('${workflowForm.displayQueryTitle[queryIndex]}');
				operandsTd1.appendChild(createLink("Execute ","images/advancequery/execute-button-1.PNG","execute_"+rowCount,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
				operandsTd1.width="3%";
				operandsTd3.appendChild(createLink("Delete","images/advancequery/ic_delete.gif ","delete_"+rowCount,"javascript:deleteWorkflowItem('"+rowCount+"','"+'${workflowForm.displayQueryType[queryIndex]}'+"')"));
				operandsTd3.width="3%";

				operandsTd3.appendChild(createHiddenElement("cancelajaxcall","cancelajaxcall_"+(rowCount),'false'));
				if('${workflowForm.expression[queryIndex]}'.indexOf('_')==-1)
				{
					var opt=document.createElement("td");
					opt.appendChild(createLink("Create Copy ","images/advancequery/saveAs.png","saveAs_"+rowCount,"javascript:saveAs('"+'${workflowForm.expression[queryIndex]}'+"','"+escape(t)+"')"));
					operandsTr.appendChild(opt);
				}
				else
				{
					var opt=document.createElement("td");
					operandsTr.appendChild(opt);

				}
				operandsTr.appendChild(operandsTd1);
				operandsTr.appendChild(operandsTd3);
				tbody1.appendChild(operandsTr);
				tble1.appendChild(tbody1);
				operandsTd.appendChild(tble1);
				rowObj.appendChild(operandsTd);

					countTable.appendChild(rowObj);

			</c:otherwise>
		</c:choose>
	</logic:iterate>
	</logic:notEmpty>

}
//Creating composte Query Starts here
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
function saveAs(saveAsforqueryId,title)
{
	document.getElementById("createCopy").value="";
	document.getElementById('saveAsquerytitle').value=title;
	document.getElementById('saveAsqueryId').value=saveAsforqueryId;
	$.blockUI( { message: $('#saveAsWindow'), css: {
                //top:  ($(window).height() - 500) /2 + 'px',
               // left: ($(window).width() - 500) /2 + 'px',
               width: '400px' ,
			  height: '100px'


            } });
	$('.blockOverlay').css('background', 'url(images/ui-bg_diagonals-thick_20_666666_40x40.png)');
	<%
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	Date date=new Date();
	String stringDate=dateFormat.format(date);
	%>
	document.getElementById("createCopy").value=unescape(title)+"_"+'<%=stringDate%>';
}


</script>
<body>


	<table border="0"   cellpadding="0" cellspacing="0" class="contentLayout">
		<tr>

				<td valign="top" height="100%">
					<table width="100%" class="login_box_bg" border="0"  cellpadding="0" cellspacing="0" height="100%" >

						<tr >
							<td valign="top" height="100%">
									<html:form action="SaveWorkflow" method="POST">
									<input type="hidden" name="saveAsquerytitle" id="saveAsquerytitle" value="">
									<input type="hidden" name="saveAsqueryId" id="saveAsqueryId" value="">
									<input type="hidden" name="requestForId" id="requestForId" value="">
									<input type="hidden" name="executeType" id="executeType" value="">
									<input type="hidden" name="dataQueryId" id="dataQueryId" value="">
									<input type="hidden" name="countQueryId" id="countQueryId" value="">
									<input type="hidden" name="allQueriesCondStr" id="allQueriesCondStr" value="">
									<html:hidden property="executedForProject" styleId="executedForProject" value="${workflowForm.executedForProject}"/>
									<input type="hidden" name="numberOfSameQueryTitle" id="numberOfSameQueryTitle" value="1">
									<input type="hidden" name="sameQueryTitle" id="sameQueryTitle" value="">
									<html:hidden property="operation" styleId="operation" value="${requestScope.operation}"/>
									<html:hidden property="id" styleId="id" value="${requestScope.id}"/>
									<input type="hidden" name="itemToDelete" id="itemToDelete" value="">
									<input type="hidden" name="typeOfItemToDelete" id="typeOfItemToDelete" value="">
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
									<c:set var="latestProject" value="${workflowForm.executedForProject}" scope="page"/>
									<input type="hidden" name="isdone" value="true" id="isdone">
									<input type="button" name="btn" id="btn" onclick="updateUI()" style="display:none">

									<table valign="top" border="0"  valign="top" cellpadding="0" cellspacing="0" width="100%" height="100%">
	<!-- proper -->
										<tr width="100%">
											<logic:equal name="workflowForm" property="operation" value="">
												<td width="100%" colspan="4" width="10%" class="table_header_query" height="28"  NOWRAP>
													<span class="PageHeaderTitle">New Workflow</span>
												</td>
											</logic:equal>
											<logic:equal name="workflowForm" property="operation" value="add">
												<td colspan="4" width="10%" class="table_header_query" height="28" NOWRAP>
												<span class="PageHeaderTitle">New Workflow</span>
												</td>
											</logic:equal>
											<logic:equal name="workflowForm" property="operation" value="search">
												<td colspan="4" width="10%" class="table_header_query" height="28" NOWRAP>
													<span class="PageHeaderTitle">Edit Workflow</span>
												</td>
											</logic:equal>
											<logic:equal name="workflowForm" property="operation" value="edit">
													<td colspan="4" width="10%" class="table_header_query" height="28"  NOWRAP>
														<span class="PageHeaderTitle">Edit Workflow</span>
													</td>
											</logic:equal>
										</tr>

													<!--for emplty space-->


										<tr valign="top">
											<td colspan="4">
												<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
													<tr  ><td  height="10px">
														<div id="errordiv" >
															<table cellspacing="0" cellpadding="0" border="0">
																<tbody>
																	<tr>
																		<td class="messagetexterror" colspan="4">
																			<div id="errormessage">
																			<%@ include file="/pages/content/common/ActionErrors.jsp" %>
																			</div>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</td></tr>
												</table>
											</td>
										</tr>



										<tr valign="top" >
											<td colspan="4" valign="top" height="15%">
												<table border="0" width="100%" valign="top">
													<tr>
														<td valign="top" colspan="1" NOWRAP  width="10%">
															<span class="content_txt_bold leftpadding" ><bean:message key="workflowPage.name"/><span class="red_star">*</span></span>
														</td>
														<td style="padding-left:5px;padding-right:3px" colspan="2" NOWRAP width="60%">
															<html:text styleId="name" property="name" styleClass="workflowTitleText" />
														</td>
														<td width="30%"></td>

													</tr>
													<tr height="5"><td colspan="4"></td></tr>
													<tr>
														<td valign="top" colspan="1"  width="10%">
															<span class="content_txt_bold leftpadding" width="30"><bean:message key="workflow.decription" />:</span>
														</td>
														<td colspan="2" style="padding-left:5px;padding-right:3px" valign="top" width="60%">
															 <html:textarea styleId="wfDescription" property="wfDescription"  styleClass="workflowtextarea"/>
														</td>
														<td  width="30%" ></td>
													</tr>
													<tr height="5"><td colspan="4"></td></tr>

													<tr>

														<td  align="left" colspan="1"  width="10%" NOWRAP><span class="content_txt_bold leftpadding"><bean:message  key="workflow.project"/></span>
														</td>

														<td colspan="2"  style="padding-left:5px;padding-right:3px"  width="60%" >
															<SELECT name="selectedProject" id="selectedProject" class="selectfieldmaxsize">
															   <option title="Unspecified" VALUE="-1">Unspecified</option>
																<c:forEach var="project" items="${requestScope.projectsNameValueBeanList}">
																 <logic:equal name="latestProject"  value="${project.value}">
																	<OPTION title="${project.name}" VALUE="${project.value}" selected="selected">${project.name}</OPTION>
																 </logic:equal>
																 <logic:notEqual name="latestProject"  value="${project.value}">
																	<OPTION VALUE="${project.value}" title="${project.name}">${project.name}</OPTION>
																</logic:notEqual>
																</c:forEach>
															</SELECT>
														</td>

														<td  width="30%" colspan="1" align="right" style="padding-right:3px">
															<a id="runWorkflow" href="#" class="blacklink"><img src="images/advancequery/run_workflow_blue.gif"   id="runWfImg"  border="0"></a>
														</td>
													</tr>
												</table>
											</td>
										</tr>

										<tr height="5">
											<td colspan="4"></td>
										</tr>
	<!-- ends proper -->

										<tr width="100%" valign="top">
											<td>
												<table width="100%" cellpadding="0" border="0" cellspacing="0" >
													<tr width="100%" valign="top">
														<td colspan="4" valign="top">
															<table cellpadding="0" border="0" cellspacing="0"  width="100%" valign="top">
																<tr valign="top">
																	<td>
																		<table cellpadding="0" border="0" cellspacing="0"  width="100%" valign="top">
																			<tr>
																				<td colspan="4" style="padding-left:5px;padding-right:5px" valign="top" >
																					<div class="outerDiv1">
																						<table width="100%"  border="0" cellpadding="0" cellspacing="2" >
																							<tr width="100%" >
																								<td colspan="4" width="100%" >
																									<div class="outerDiv">
																										<table width="100%" cellpadding="0" cellspacing="0"><!-- 1 -->
																											<tr>
																												<td colspan="4" >
																													<div id="countouterDiv">
																														<table width="100%" cellpadding="0" cellspacing="0"  border="0">
																															<tr width="100%">
																																<td colspan="4">
																																	<div class="headerDiv">
																																		<table width="100%" style="padding-top:2px;padding-bottom:2px"  cellpadding="0" cellspacing="0" border="0">
																																			<tr>
																																				<td  width="26%">
																																					<div id="showHideCount">
																																						<table  valign="middle" cellpadding="0" cellspacing="0" border="0">
																																							<tr>
																																								<td class="showhandcursor blueHeader" valign="middle"><img id="counthideShow" src="images/advancequery/arrow_close.png"  border="0">&nbsp;
																																								</td>
																																								<td class="showhandcursor blueHeader" >
																																								<bean:message
																																									key="count.title" />

																																								</td>

																																								<td class="showhandcursor blueHeader" valign="middle"><div id="countNum"></div></td>
																																							</tr>
																																						</table>
																																					</div>
																																				</td>

																																				<!-- button div-->
																																				<td  valign="middle"   width="51%" valign="top" align="right" >
																																					<table  width="100%" cellspacing="0" cellpadding="0"  valign="top" border="0">
																																						<tr width="100%">
																																							<td width="45%"  align="middle" valign="top">
																																								<table valign="top"  cellspacing="0" cellpadding="0" border="0">
																																									<tr>
																																										<td align="middle" >
																																											<div id="loading" class="loading-invisible" valign="top">
																																												<table cellpadding="0" cellspacing="0" valign="top">
																																													<tr align="middle" >
																																													<td width="100%" align="middle" valign="top">
																																														<img alt="Loading" src="images/advancequery/load.gif" >
																																													</td>
																																													</tr>
																																												</table>
																																											</div>
																																										</td>
																																									</tr>
																																								</table>
																																							</td>
																																							<td width="55%" align="right">
																																							<div id="buttonStatus">

																																							</div>
																																							</td>

																																						</tr>
																																					</table>
																																				</td>

																																			<!-- new Count Query-->
																																					<td valign="middle" width="11%" align="right">
																																						<a id="addNewCountQuery" href="#" class="bluelink" ><img alt="Add new query" id="addNewCountimg"  src="images/advancequery/b_add_new.gif" border="0"></a>
																																					</td>

																																					<td width="12%" valign="middle" align="middle">
																																						<a id="showCountQueries" href="#" class="bluelink"><img alt="Add new query" id="addExistingCountimg"  src="images/advancequery/b_add_existing.gif" border="0"></a>
																																					</td>
																																				</tr>
																																		</table>

																																	</div>
																																</td>
																																</tr>

																																<tr ><td><table border="0" cellpadding="0" cellspacing="2"><tr><td></td></tr></table></td> </tr>

<!--  -->
																																<tr>
																																		<td colspan="4" align="middle">
																																			<table cellpadding="0" cellspacing="0" width="98%" bgcolor="white" >
																																				<tr  >
																																					<td colspan="4" ><div id="CountDiv" style="height:139px;overflow:auto;">

																																								<table width="100%" cellpadding="0" cellspacing="0" border="0" height="3px"  class="roundedcornerinner" >
																																									<tr class="headertr">
																																										<td  class="grid_header_text" width="1%">
																																										</td>
																																										<td  class="grid_header_text" width="2%">
																																										</td>
																																										<td  align="center" width="80%" class="actions">
																																											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="workflow.queryTitle"/>
																																										</td>

																																										<td class="actions"  width="10%" colspan="5" align="middle">
																																											<bean:message key="workflow.patientcount"/>
																																										</td>

																																										<td align="middle" width="6%"  class="actions" colspan="5">
																																										<bean:message key="actions"/>

																																										</td>
																																									</tr>

																																								</table>


																																								<table cellpadding="0" cellspacing="0" border="0" width="100%"
																																									 bgcolor="white" border="0">
																																								 <tbody id="countTable">

																																								 </tbody>
																																								 </table>
																																						</div>
																																					</td>
																																				</tr>
																																			</table>
																																		</td>
																																	</tr>
<tr><td><table ><tr><td></td></tr></table></td></tr>
<tr><td><table ><tr><td></td></tr></table></td></tr>
<!--  -->

																															</table>



																													</div>
																												</td>
																											</tr>
																										</table><!--  1-->
																									</div>
																								</td>
																							</tr>
																						</table>

																					</div>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
														</td>
													</tr >

<tr ><td><table border="0" cellpadding="0" cellspacing="4"><tr><td></td></tr></table></td> </tr>

													<tr  width="100%" valign="top">
														<td colspan="4">
															<table cellpadding="0" border="0" cellspacing="0"  width="100%">
																<tr>
																	<td>
																		<table cellpadding="0" border="0" cellspacing="0"  width="100%" valign="top">
																			<tr>
																				<td colspan="4" style="padding-left:5px;padding-right:5px" valign="top" >
																					<div class="outerDiv1">
																						<table width="100%"  border="0" cellpadding="0" cellspacing="2" >
																							<tr width="100%" >
																								<td colspan="4" width="100%" >
																									<div class="outerDiv">
																										<table width="100%" cellpadding="0" cellspacing="0">
																											<tr>
																												<td colspan="4" >
																													<div id="countouterDiv">
																														<table width="100%" cellpadding="0" cellspacing="0"  border="0">
																															<tr width="100%">
																																<td colspan="4">
																																	<div class="headerDiv">
																																		<table width="100%"  cellpadding="0" cellspacing="0" border="0" style="padding-top:3px;padding-bottom:3px" >
																																			<tr>
																																				<td width="25%" valign="middle" align="left">
																																					<table cellpadding="0" cellspacing="0" width="100%" border="0">
																																						<tr>
																																							<td width="100%">
																																								<div id="showHideData" >
																																										<table cellpadding="0" cellspacing="0" border="0">
																																										<tr>
																																											<td class="showhandcursor blueHeader" >
																																												<img id="datahideShow" src="images/advancequery/arrow_close.png"  border="0">&nbsp;
																																											</td>
																																											<td class="showhandcursor blueHeader" >
																																												<bean:message
																																												key="data.title" />

																																											</td>

																																											<td class="showhandcursor blueHeader"><div id="dataNum"></div></td>
																																										</tr>
																																										</table>
																																									</div>
																																								</td>
																																							</tr>
																																						</table>
																																				</td>
																																				<td  width="52%" valign="middle" align="" class="blueHeader">
																																					<!--TO DO bean:message
																																								key="query.myqueries"-->
																																						Run Against:
																																					 <select name="countQueryDropDown"  id="countQueryDropDown" class="selecttextfield" >&nbsp;
																																				</td>
																																				<td  width="11%" valign="middle" align="right">
																																					<a id="addNewDataQuery" href="#" class="bluelink"><img alt="Add new query"  id="addNewDataQueryImg"  src="images/advancequery/b_add_new.gif" border="0"></a>
																																				</td>
																				
																																				<td  width="12%" valign="middle" align="middle"><a id="showDataQueries" href="#" class="bluelink"><img alt="Add new query" id="addexistingdataQueryImg"  src="images/advancequery/b_add_existing.gif" border="0"></a>
																																				</td>
																																			</tr>
																																		</table>
																																	</div>
																																</td>
																															</tr>

																															<tr ><td><table border="0" cellpadding="0" cellspacing="2"><tr><td></td></tr></table></td> </tr>

																															<tr>
																																<td colspan="4" align="middle">
																																	<table cellpadding="0" cellspacing="0" width="98%" bgcolor="white" >
																																		<tr>
																																			<td colspan="4">
																																				<div id="DataDiv" 	style="height:65px;overflow: auto;">
																																					<table width="100%" cellpadding="0" cellspacing="0" border="0" height="3px" class="roundedcornerinner">
																																						<tr class="headertr" height="1%">
																																							<td  class="actions" width="1%">
																																							</td>
																																							<td  class="actions" width="2%">
																																							</td>
																																							<td  align="center" width="91%" class="actions">
																																								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="workflow.queryTitle"/>
																																							</td>



																																							<td align="middle" width="6%"  class="actions" colspan="5">
																																								<bean:message key="actions"/>
																																							</td>
																																						</tr>

																																					</table>


																																					<table cellpadding="0" cellspacing="0" border="0" width="100%" bgcolor="white" border="0">
																																						 <tbody id="dataTable">

																																						 </tbody>
																																					</table>
																																				</div>
																																			</td>
																																		</tr>
																																	</table>
																																</td>
																															</tr>
<tr><td><table ><tr><td></td></tr></table></td></tr>
<tr><td><table ><tr><td></td></tr></table></td></tr>
																														</table>
																														<!--here-->



																													</div>
																												</td>
																											</tr>
																										</table>

																									</div>
																								</td>
																							</tr>
																						</table>

																					</div>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
														</td>
													</tr >
												</table>
											</td>
										</tr>
							</table>

<!-- ----------- -->
</html:form>
	</td>
	</tr>

	<tr ><td><table border="0" cellpadding="0" cellspacing="2"><tr><td></td></tr></table></td> </tr>


<!--   --------- -->
			<tr width="100%" valign="bottom" >
				<td valign="bottom"  width="100%" colspan="3">
					<table   cellpadding="0" cellspacing="0"  width="100%"  valign="bottom"  >
						<tr>
							<td  width="100%" class="leftpadding">
								<table border="0" cellpadding="0" cellspacing="0"  width="100%"   valign="bottom">

									<tr >
										<td valign="bottom"  width="100%"><a id="submitWorkflow" href="#">
											<img src="images/advancequery/b_save.gif" id="saveWFImg"  border="0"></a>
											<a id="cancel" href="#"><img  id="cancelImg"  src="images/advancequery/b_cancel.gif"  border="0"																						></a>
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
<div id="saveAsWindow"  style="display:none;">
<table  width="100%" bgcolor="white" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
	<tr width="100%" valign="top">
		<td colspan="2"><table  border="0" cellspacing="0" width="100%" cellpadding="0"><tr width="100%">

		<td style="padding-left:2px; padding-top:2px;" class="drag-handle">Create copy of query
		</td>

		<td  class="drag-handle" style="padding-right:2px; padding-top:2px;" align="right"><a id="closeUI" href="javascript:unblockUI()">
			<img src="images/advancequery/uIEnhancementImages/close.gif"  border="0"></a>
		</td>

		<tr></table></td>

	</tr>
	<tr ><td><table border="0" cellpadding="0" cellspacing="2"><tr><td></td></tr></table></td> </tr>

		<tr style="padding-top:5px" width="100%" class="content_txt_bold" valign="middle" >
			<td  style="padding-left:10px" valign="middle" >
				<bean:message key="getcountquery.name"/><span class="red_star" style="padding-left:2px;padding-right:2px">*</span>:
			</td>
			<td valign="middle"  style="padding-left:2px;padding-right:2px">
				<input type="text" onKeyDown="return enableEnterKey(event)" size="45" class="textfield_undefined" id="createCopy" name="createCopy" />
			</td>
		</tr>

	<tr ><td><table border="0" cellpadding="0" cellspacing="2"><tr><td></td></tr></table></td> </tr>
	<tr width="100%"  >
		<td valign="bottom"  width="100%" colspan="2">
			<table   cellpadding="0" cellspacing="0"  width="100%"  valign="bottom"  >
				<tr>
					<td  width="100%" class="leftpadding">
						<table border="0" cellpadding="0" cellspacing="0"  width="100%"   valign="bottom">

							<tr >
								<td valign="bottom"  width="100%"><a href="javascript:createCopy()">
									<img src="<%=request.getContextPath()%>/images/advancequery/b_ok.gif" alt="Save" border="0"></a>
									<a  href="javascript:unblockUI()"><img alt="Cancel" src="<%=request.getContextPath()%>/images/advancequery/b_cancel.gif"  border="0"			></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>


	</table>
</div>
</body>

