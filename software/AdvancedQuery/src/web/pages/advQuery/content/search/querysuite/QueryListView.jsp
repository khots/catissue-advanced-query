<%@page import="edu.wustl.query.bizlogic.DashboardBizLogic"%><META
	HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%-- Imports --%>
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.AQConstants,org.apache.struts.Globals"%>
<%@ page
	import="org.apache.struts.action.ActionMessages,edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.AQConstants"%>
<%@ page import="edu.wustl.common.tags.domain.Tag"%>
<%@ page import="edu.wustl.query.actionForm.SaveQueryForm"%>
<%@ page
	import="edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface,edu.wustl.cab2b.client.ui.query.ClientQueryBuilder,edu.wustl.query.flex.dag.DAGConstant,edu.wustl.common.querysuite.queryobject.IQuery,edu.wustl.common.querysuite.queryobject.impl.Query,edu.wustl.common.querysuite.queryobject.IParameterizedQuery"%>
<%@ page
	import="java.util.*,java.text.DateFormat,java.text.SimpleDateFormat"%>
<%@ page import="edu.wustl.query.beans.DashBoardBean"%>
<head>
<script language="JavaScript" type="text/javascript"
	src="jss/advQuery/queryModule.js"></script>
<script type="text/javascript" src="jss/advQuery/wz_tooltip.js"></script>
<script type="text/javascript" src="jss/tag-popup.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/advQuery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advQuery/tag-popup.css" />
<link rel="STYLESHEET" type="text/css"
	href="dhtmlx_suite/dhtml_pop/css/dhtmlXTree.css">
<script src="dhtmlx_suite/dhtml_pop/js/dhtmlXCommon.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="dhtmlx_suite/dhtml_pop/css/dhtmlXGrid.css" />
<link rel="STYLESHEET" type="text/css"
	href="dhtmlx_suite/dhtml_pop/css/dhtmlxgrid_dhx_skyblue.css" />
<script src="dhtmlx_suite/dhtml_pop/js/dhtmlx.js"></script>
<script src="dhtmlx_suite/dhtml_pop/js/dhtmlXTree.js"></script>
<script src="dhtmlx_suite/dhtml_pop/js/dhtmlXTreeCommon.js"></script>
<script src="dhtmlx_suite/dhtml_pop/js/dhtmlXGridCell.js"></script>
<script src="dhtmlx_suite/dhtml_pop/js/dhtmlXTreeGrid.js"></script>
<script>
function QueryWizard()
{
	var rand_no = Math.random();
	document.forms[0].action='QueryWizard.do?random='+rand_no;
	document.forms[0].submit();
}
window.onload = function() {        
   	doInitGrid();	
    }  
function f()
{
	searchDivTag=document.getElementById('searchDiv');
	searchDivTag.style.height = (document.body.clientHeight-105) + 'px';
}
var grid;
function doInitGrid()
{
	grid = new dhtmlXGridObject('mygrid_container');
	grid.setImagePath("dhtmlx_suite/dhtml_pop/imgs/");
 	grid.setHeader("My Folders");
 	grid.setInitWidths("175");
 	grid.setColAlign("left");
 	grid.setSkin("dhx_skyblue"); // (xp, mt, gray, light, clear, modern)
 	grid.enableRowsHover(true, "activebtn");
 	grid.setEditable(false);
   	grid.attachEvent("onRowSelect", doOnRowSelected);
 	grid.init();
 	grid.load ("TagGridInItAction.do");
  
}
function doOnRowSelected(rId)
{
	submitTagName(rId);	 
}

</script>
<body onunload="doInitOnLoad();" onresize='f()'>
	<%
		boolean mac = false;
		Object os = request.getHeader("user-agent");
		if (os != null && os.toString().toLowerCase().indexOf("mac") != -1) {
			mac = true;
		}
		String message = null;
		String entityTag="QueryTag";
		String entityTagItem="QueryTagItem";
		String popupHeader=(String) request.getAttribute(AQConstants.POPUP_HEADER);
		String popupDeleteMessage=(String) request.getAttribute(AQConstants.POPUP_DELETE_QUERY_MESSAGE);
		String popupAssignMessage=(String) request.getAttribute(AQConstants.POPUP_ASSIGN_MESSAGE);
		String popupAssignConditionMessage=(String) request.getAttribute(AQConstants.POPUP_ASSIGN_QMESSAGE);
		String popupFolderDeleteMessage=(String) request.getAttribute(AQConstants. POPUP_DELETE_QUERY_FOLDER_MESSAGE);
		String popupMessage = (String) request
				.getAttribute(AQConstants.POPUP_MESSAGE);
		String popupText = (String) request
				.getAttribute(AQConstants.POPUP_TEXT);
		String queryOption = (String) request.getAttribute("queryOption");
		Object formObj = request.getAttribute("saveQueryForm");
		boolean isWhite = false;
		String displayStyle = "display:block";
       	SaveQueryForm saveQueryForm = (SaveQueryForm) formObj;

		Collection<IParameterizedQuery> iParameterizedQueryCollection = saveQueryForm
				.getParameterizedQueryCollection();
		HttpSession newSession = request.getSession();
		if (iParameterizedQueryCollection != null) {
			newSession.setAttribute("parameterizedQueryCollection",
					iParameterizedQueryCollection);
		} else {
			newSession.setAttribute("parameterizedQueryCollection", null);
		}
		if (iParameterizedQueryCollection.size() == 0) {
			displayStyle = "display:none";
		} else {
			displayStyle = "display:block";
		}
		Map<Long, DashBoardBean> executedOnMap = saveQueryForm
				.getDashBoardDetailsMap();
		int queryCount = 0;
	%>
 

	<html:messages id="messageKey" message="true">
		<%
			message = messageKey;
		%>
	</html:messages>
	<html:form styleId='saveQueryForm'
		action='<%=AQConstants.FETCH_QUERY_ACTION%>'
		style="margin:0;padding:0;">
		<table width='100%' cellpadding='0' cellspacing='0' border='0'
			align='center'>
			<!-- style="width:100%;height:100%;overflow:auto"-->

			<tr valign="center" class="bgImage">
				<td width="50%">&nbsp; <img
					src="images/advQuery/ic_saved_queries.gif" id="savedQueryMenu"
					alt="Saved Queries" width="38" height="48" hspace="5"
					align="absmiddle" /> <span class="savedQueryHeading"> <bean:message
							key="query.savedQueries.label" /> </span></td>

				<td width="1" valign="middle" class="bgImage" align="left"><img
					src="images/advQuery/dot.gif" width="1" height="25" /></td>
			</tr>
			<tr>


				<td class="savedQueryHeading">
					<p>
						<html:errors />
				</td>
				<td>
					<div>
						<%
 						String	organizeTarget = "ajaxTreeGridInitCall('"+popupDeleteMessage+"','"+popupFolderDeleteMessage+"','"+entityTag+"','"+entityTagItem+"')";
 %>
						<input type="button" value="ORGANIZE"
							onclick="<%=organizeTarget%> " title ="Organize"  class="btn"> <input
							type="button" value="CREATE NEW QUERY" title ="Create New Query" onclick="QueryWizard()"
							class="btn2">
				</td>

			</tr>
		</table>
<table width='100%' height='100%' cellpadding='0' cellspacing='0' border='0'
			align='center'>
<tr>
<td>
		<div id="left">
			<table class="tags" width="100%" cellspacing="0" cellpadding="0"
				border="0">
<%
	String myQueryClass;
	String allQueryClass;
	String sharedQueryClass;
	if (queryOption == "myQueries" || queryOption.equals("myQueries"))
	{
		myQueryClass ="activebtn";
		allQueryClass="btn1";
		sharedQueryClass="btn1";
	}
	else if (queryOption == "allQueries"|| queryOption.equals("allQueries"))
	{
		myQueryClass = "btn1";
		allQueryClass="activebtn";
		sharedQueryClass="btn1";
	}
	else if (queryOption == "sharedQueries" || queryOption.equals("sharedQueries"))
	{
		myQueryClass ="btn1" ;
		allQueryClass="btn1";
		sharedQueryClass="activebtn";
	}else
	{
		myQueryClass = "btn1";
		allQueryClass="btn1";
		sharedQueryClass="btn1";
	}	
		%>
				<tbody>
					<tr>
						<td><div style="height: 30px; width: 166px;" id="toolbarObj1">
								<input type="button" value="My Queries" title ="My Queries"
									onclick="submitTheForm('ShowQueryDashboardAction.do?pageOf=myQueries',this);"
									class=<%=myQueryClass%>>
							</div>
						</td>
					</tr>
					<tr>
						<td><div style="height: 30px; width: 166px;" id="toolbarObj2">
								<input type="button" value="All Queries"  title="All Queries"
									onclick="submitTheForm('ShowQueryDashboardAction.do?pageOf=allQueries',this);"
									class=<%=allQueryClass%>>
							</div>
						</td>
					</tr>
					<tr>
						<td><div style="height: 30px; width: 166px;" id="toolbarObj3">
								<input type="button" value="Shared Queries" title="Shared Queries"
									onclick="submitTheForm('ShowQueryDashboardAction.do?pageOf=sharedQueries',this);"
									class=<%=sharedQueryClass%>>
							</div>
						</td>
					</tr>
					<tr>
						<td><div id="mygrid_container"
								style="width: 174px; height: 350px;"></div></td>
					</tr>
				</tbody>
			</table>

		</div>
 
		<div id="wrapper">
			<div id="mainContent">
				<!--POPUP-->
				<div id="blanket" style="display: none;"></div>
				<div id="popUpDiv" style="display: none; top: 100px; left: 210.5px;">

					<a onclick="popup('popUpDiv')"><img style="float: right;"
						height='23' width='24' title="Close" src='images/advQuery/close_button.gif'
						border='0'> </a>
					<table class=" manage tags" width="100%" cellspacing="0"
						cellpadding="5" border="0">

						<tbody>
							<tr valign="center" height="35" bgcolor="#d5e8ff">
								<td width="28%" align="left"
									style="font-size: .82em; font-family: verdana;">
									<p>
										&nbsp&nbsp&nbsp&nbsp<b> <%=popupHeader%></b>
									</p>
								</td>
							</tr>
					</table>


					<div id="treegridbox"
						style="width: 530px; height: 237px; background-color: white;"></div>




					<p>
						&nbsp&nbsp&nbsp<label width="28%" align="left"
							style="font-size: .82em; font-family: verdana;"><b> <%=popupText%>
								: </b> </label> <input type="text" id="newTagName" name="newTagName"
							size="20" onclick="this.value='';" maxlength="50" /><br>
					</p>
					<p>
						<%
 String	assignTarget = "ajaxAssignTagFunctionCall('AssignTagAction.do','"+popupAssignMessage+"','"+popupAssignConditionMessage+"')";
 %>
						<input type="button" value="ASSIGN" title="Assign" onclick="<%=assignTarget%> "
							onkeydown="<%=assignTarget%> " class="btn3">
					</p>
				</div>
			</div>
 

			<div id="right">
				<table width="100%" cellpadding='0' cellspacing='0' border='0'>

					<tr style="height: 100%;">
						<td>
							<!--style="width:100%; height:100%; overflow:auto; border:1px solid #FF0000; "-->
							<div id="searchDiv">
								<table cellpadding='2' cellspacing='0' border='0' width='100%'
									class='savedqueries'>
									<%
										if (displayStyle.equals("display:block")) {
									%>
									<tr valign="center" height='30' bgcolor="#d5e8ff">
										<%
											} else {
										%>
									
									<tr valign="center" height='30' bgcolor="#d5e8ff"
										style="<%=displayStyle%>">
										<%
											}
										%>
										<td width='2%' align="left"
											style="font-size: 1.2em; font-family: verdana;"><b><bean:message
													key="sr.no" /> </b></td>
										<td width='31%' align="left" class="savedQueryHeading"
											style="font-size: 1.2em; font-family: verdana;"><b>
												<bean:message key="query.title" /> </b></td>
										<td width='16%' align="left" class="savedQueryHeading"
											style="font-size: 1.2em; font-family: verdana;"><b><bean:message
													key="query.results" /> </b></td>
										<td class="savedQueryHeading" width="10%" align='left'
											style="font-size: 1.2em; font-family: verdana;"><b><bean:message
													key="query.executedOn" /> </b></td>
										<td class="savedQueryHeading" align='left'
											style="font-size: 1.2em; font-family: verdana;"><b><bean:message
													key="query.owner" /> </b></td>
										<td align='left' width="13%" class="savedQueryHeading"
											style="font-size: 1.2em; font-family: verdana;"><b><bean:message
													key="query.actions" /> </b></td>
									</tr>
									<c:set var="parameterizedQueryCollection"
										value="${saveQueryForm.parameterizedQueryCollection}" />
									<jsp:useBean id="parameterizedQueryCollection"
										type="java.util.Collection" />

									<c:forEach items="${parameterizedQueryCollection}"
										var="parameterizedQuery" varStatus="queries">
										<jsp:useBean id="parameterizedQuery"
											type="edu.wustl.common.querysuite.queryobject.IParameterizedQuery" />

										<%
											String cssClass = new String();
													if (isWhite == false) {
														cssClass = "white";
														isWhite = true;
													} else {
														cssClass = "bgImageForColumns";
														isWhite = false;
													}
										%>
										<div id="tableDiv">
											<tr class="<%=cssClass%>">
												<%
													String target = "executeQuery('"
																	+ parameterizedQuery.getId() + "')";
															String title = parameterizedQuery.getName();
															String newTitle = Utility.getQueryTitle(title);
															if (newTitle.length() >= 50) {
																newTitle = newTitle.substring(0, 50) + "...";
															}
															title = "Title : " + title + " ";
															title = title + " | Description : "
																	+ parameterizedQuery.getDescription();
															String tooltip = Utility.getTooltip(title);
															String function = "Tip('" + tooltip + "', WIDTH, 300)";
															queryCount++;
												%>

												<td valign="top" align="left" height='20' width='2%'
													style="padding-left: 0.7em; padding-top: 5px; font-size: 1.1em; font-family: verdana;"><span
													class="savedQueryHeading"> <!--img src="images/savedQuery.bmp"/-->
														<%=queryCount%> </span></td>
												<%
													DashBoardBean dashBoardBean = executedOnMap
																	.get(parameterizedQuery.getId());
															String rootEntityName = dashBoardBean.getRootEntityName();
															String noOfRecords = dashBoardBean.getCountOfRootRecords();
															String executedOn = dashBoardBean.getExecutedOn();
															String ownerName = dashBoardBean.getOwnerName();
												%>
												<td valign="center" height='20' width="31%" align="left"><input
													type="checkbox" name="objCheckbox"
													value="<%=String.valueOf(parameterizedQuery.getId())%>" />

													<html:link
														style="padding-left: 0.2em; * padding-left: 5px; font-size: 1em; font-family: verdana;"
														href='#' onclick='<%=target%>' onmouseover="<%=function%>"><%=newTitle%></html:link>
												</td>
												<td width="16%" align="left"
													style="padding-left: 0.2em; * padding-left: 5px; font-size: 1em; font-family: verdana;">
													<%=rootEntityName%> (<%=noOfRecords%>)</td>
												<td valign="center" height='20' width="10%" align="left"
													style="padding-left: 0.2em; * padding-left: 2px; font-size: 1em; font-family: verdana;"
													nowrap="true"><%=executedOn%></td>
												<td valign="center" height='20' width="10%" align="left"
													style="padding-left: 0.2em; * padding-left: 5px; font-size: 1em; font-family: verdana;">
													<%=ownerName%></td>

												<td valign="center" height='20' align="left"
													style="font-size: 1em; font-family: verdana;">
													<%
														target = "editQuery('" + parameterizedQuery.getId() + "')";
													%> &nbsp <A onclick="<%=target%>"
													onmouseover="Tip('Edit', WIDTH,36)" border="0"> <IMG
														src="images/advQuery/application_edit.png"
														id="editBtn_<%=parameterizedQuery.getId()%>" border="0">
														&nbsp;&nbsp; <%
 	target = "executeQuery('" + parameterizedQuery.getId()
 					+ "')";
 %> <A onclick="<%=target%>" onmouseover="Tip('Execute', WIDTH,44)"
														border="0"> <IMG
															src="images/advQuery/execute-button-1.PNG"
															id="executeBtn_<%=parameterizedQuery.getId()%>"
															border="0"> </A>&nbsp;&nbsp; <%
 	target = "deleteQueryPopup('" + parameterizedQuery.getId()
 					+ "','" + popupMessage + "')";
 			if (queryOption != null && queryOption.equals("myQueries")) {
 %> <A onclick="<%=target%>" border="0"> <IMG
															src="images/advQuery/cancel.png"
															id="deleteBtn_<%=parameterizedQuery.getId()%>" border="0">
													</A> <%
 	}
 %>
												
												</td>

											</tr>
									</c:forEach>
									</div>
								</table>


							</div>
					</tr>
				</table>
			</div>

		</div>
</td>
</tr> 
</table>

		<html:hidden styleId="queryId" property="queryId" />
	</html:form>
</body>
