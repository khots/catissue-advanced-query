
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
<%@ page import="edu.wustl.query.actionForm.SaveQueryForm"%>
<%@ page
	import="edu.wustl.cab2b.client.ui.query.IClientQueryBuilderInterface,edu.wustl.cab2b.client.ui.query.ClientQueryBuilder,edu.wustl.query.flex.dag.DAGConstant,edu.wustl.common.querysuite.queryobject.IQuery,edu.wustl.common.querysuite.queryobject.impl.Query,edu.wustl.common.querysuite.queryobject.IParameterizedQuery"%>
<%@ page
	import="java.util.*,java.text.DateFormat,java.text.SimpleDateFormat"%>
<%@ page import="edu.wustl.query.beans.DashBoardBean"%>
<script>
function QueryWizard()
{
	var rand_no = Math.random();
	document.forms[0].action='QueryWizard.do?random='+rand_no;
	document.forms[0].submit();
}

function f()
{
	searchDivTag=document.getElementById('searchDiv');
	searchDivTag.style.height = (document.body.clientHeight-105) + 'px';
}
</script>

<head>

<script language="JavaScript" type="text/javascript"
	src="jss/advQuery/queryModule.js"></script>
<script type="text/javascript" src="jss/advQuery/wz_tooltip.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/advQuery/styleSheet.css" />


</head>

<body onunload='closeWaitPage()' onresize='f()'>

<%
boolean mac = false;
Object os = request.getHeader("user-agent");
if(os!=null && os.toString().toLowerCase().indexOf("mac")!=-1)
{
	mac = true;
}

String message = null;
String popupMessage = (String)request.getAttribute(AQConstants.POPUP_MESSAGE);
String queryOption = (String)request.getAttribute("queryOption");
Object formObj = request.getAttribute("saveQueryForm");
boolean isWhite = false;
String displayStyle= "display:block";

SaveQueryForm saveQueryForm = (SaveQueryForm)formObj;

Collection<IParameterizedQuery> iParameterizedQueryCollection = saveQueryForm.getParameterizedQueryCollection();
HttpSession newSession = request.getSession();
if(iParameterizedQueryCollection != null)
{
	newSession.setAttribute("parameterizedQueryCollection",iParameterizedQueryCollection);
}
else
{
	newSession.setAttribute("parameterizedQueryCollection",null);
}
if(iParameterizedQueryCollection.size() == 0)
{
	displayStyle = "display:none";
}
else
{
	displayStyle = "display:block";
}
Map<Long,DashBoardBean> executedOnMap = saveQueryForm.getDashBoardDetailsMap();
int queryCount = 0;%>
<html:messages id="messageKey" message="true">
	<% message = messageKey;    %>
</html:messages>
<html:form styleId='saveQueryForm'
	action='<%=AQConstants.FETCH_QUERY_ACTION%>'
	style="margin:0;padding:0;">
	<table width='100%' cellpadding='0' cellspacing='0' border='0'
		align='center'>
		<!-- style="width:100%;height:100%;overflow:auto"-->

		<tr valign="center" class="bgImage">
			<td width="17%">&nbsp; <img
				src="images/advQuery/ic_saved_queries.gif" id="savedQueryMenu"
				alt="Saved Queries" width="38" height="48" hspace="5"
				align="absmiddle" /> <span class="savedQueryHeading"> <bean:message
				key="query.savedQueries.label" /> </span></td>
			<td align="left" width="20%" class="bgImage"></td>
			<td width="130" align="left" class="bgImage"><span
				class="savedQueryHeading"><%= message %> </span></td>
			<td width="1" valign="middle" class="bgImage" align="left"><img
				src="images/advQuery/dot.gif" width="1" height="25" /></td>
			<td align="right" class="bgImage"><!--a href="javascript:QueryWizard()"> <img src="images/add.gif" width="125" height="20" /> </a-->
			<img src="images/advQuery/add.gif" id="addNewQuery" width="125"
				height="18" onclick="QueryWizard()" /></td>
			<td height='30' align='right' style="padding-left: 65px;"><span
				class="savedQueryHeading"> <b><bean:message
				key="filter.message" /></b> </span></td>
			<td
				style="padding-left: 10px; font-size: 0.89em; font-family: verdana;"
				align="left"><select name="combobox1"
				style="width: 150px; display: block; height: 18; font-size: 0.8em; font-family: verdana;"
				valign="top" id="comboboxId1" onChange="queryOptionChanged(form)">
				<%
					if(queryOption == null)
					{
						%>
				<option name="myQueries"
					style="font-size: 0.9em; font-family: verdana;" value="MyQueries"><b><bean:message
					key="my.queries" /></b></option>
				<option name="allQueries" value="AllQueries"
					style="font-size: 0.9em; font-family: verdana;" SELECTED><bean:message
					key="all.queries" /></option>
				<option name="allQueries" value="SharedQueries"
					style="font-size: 0.9em; font-family: verdana;"><bean:message
					key="shared.queries" /></option>
				<%
					}
					else if(queryOption.equals("myQueries"))
					{
						%>
				<option name="myQueries"
					style="font-size: 0.9em; font-family: verdana;" value="MyQueries"
					SELECTED><bean:message key="my.queries" /></option>
				<option name="allQueries"
					style="font-size: 0.9em; font-family: verdana;" value="AllQueries"><bean:message
					key="all.queries" /></option>
				<option name="allQueries"
					style="font-size: 0.9em; font-family: verdana;"
					value="SharedQueries"><bean:message key="shared.queries" /></option>
				<%
					}
					else if(queryOption.equals("allQueries"))
					{
						%>
				<option name="myQueries"
					style="font-size: 0.9em; font-family: verdana;" value="MyQueries"><bean:message
					key="my.queries" /></option>
				<option name="allQueries"
					style="font-size: 0.9em; font-family: verdana;" value="AllQueries"
					SELECTED><bean:message key="all.queries" /></option>
				<option name="allQueries"
					style="font-size: 0.9em; font-family: verdana;"
					value="SharedQueries"><bean:message key="shared.queries" /></option>
				<%
					}
					else if(queryOption.equals("sharedQueries"))
					{
						%>
				<option name="myQueries"
					style="font-size: 0.9em; font-family: verdana;" value="MyQueries"><bean:message
					key="my.queries" /></option>
				<option name="allQueries"
					style="font-size: 0.9em; font-family: verdana;" value="AllQueries"><bean:message
					key="all.queries" /></option>
				<option name="allQueries"
					style="font-size: 0.9em; font-family: verdana;"
					value="SharedQueries" SELECTED><bean:message
					key="shared.queries" /></option>
				<%
					}
				%>
			</select></td>


		</tr>

		<tr>
			<td>&nbsp;</td>
			<td colspan='5' class="savedQueryHeading"><html:errors /></td>

		</tr>
	</table>
	<table width="100%" cellpadding='0' cellspacing='0' border='0'>

		<tr style="height: 100%;">
			<td><!--style="width:100%; height:100%; overflow:auto; border:1px solid #FF0000; "-->
			<div id="searchDiv">
			<table cellpadding='2' cellspacing='0' border='0' width='100%'
				class='savedqueries'>
				<%if(displayStyle.equals("display:block"))
							{%>
				<tr valign="center" height='30' bgcolor="#d5e8ff">
					<%}
							else
							{%>
					<tr valign="center" height='30' bgcolor="#d5e8ff"
						style="<%=displayStyle%>">
						<%}%>
						<td width='2%' align="left"
							style="font-size: 1.2em; font-family: verdana;"><b><bean:message
							key="sr.no" /></b></td>
						<td width='31%' align="left" class="savedQueryHeading"
							style="font-size: 1.2em; font-family: verdana;"><b> <bean:message
							key="query.title" /></b></td>
						<td width='16%' align="left" class="savedQueryHeading"
							style="font-size: 1.2em; font-family: verdana;"><b><bean:message
							key="query.results" /></b></td>
						<td class="savedQueryHeading" width="10%" align='left'
							style="font-size: 1.2em; font-family: verdana;"><b><bean:message
							key="query.executedOn" /></b></td>
						<td class="savedQueryHeading" align='left'
							style="font-size: 1.2em; font-family: verdana;"><b><bean:message
							key="query.owner" /></b></td>
						<td align='left' width="13%" class="savedQueryHeading"
							style="font-size: 1.2em; font-family: verdana;"><b><bean:message
							key="query.actions" /></b></td>
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
									if(isWhite == false)
									{
										cssClass = "white";
										isWhite = true;
									}
									else
									{
										cssClass="bgImageForColumns";
										isWhite=false;
									}
								%>
						<tr class="<%=cssClass%>">
							<%String target = "executeQuery('"+parameterizedQuery.getId()+"')";
									  String title = parameterizedQuery.getName();
									  String newTitle = Utility.getQueryTitle(title);

									  title = "Title : "+title+" ";
									  title = title+" | Description : "+parameterizedQuery.getDescription();
									  String tooltip = Utility.getTooltip(title);
									  String function = "Tip('"+tooltip+"', WIDTH, 300)";
									  queryCount++;
									%>

							<td valign="top" align="left" height='20' width='2%'
								style="padding-left: 0.7em; padding-top: 5px; font-size: 1.1em; font-family: verdana;"><span
								class="savedQueryHeading"> <!--img src="images/savedQuery.bmp"/-->
							<%=queryCount%> </span></td>
							<%
										DashBoardBean dashBoardBean = executedOnMap.get(parameterizedQuery.getId());
										String rootEntityName= dashBoardBean.getRootEntityName() ;
										String noOfRecords = dashBoardBean.getCountOfRootRecords();
										String executedOn = dashBoardBean.getExecutedOn();
										String ownerName = dashBoardBean.getOwnerName();
									%>
							<td valign="center" height='20' width="31%" align="left"
								style="padding-left: 0.2em; * padding-left: 5px; font-size: 1em; font-family: verdana;"
								onmouseover="<%=function%>"><%=newTitle%></td>
							<td width="16%" align="left"
								style="padding-left: 0.2em; * padding-left: 5px; font-size: 1em; font-family: verdana;">
							<%=rootEntityName %> (<%=noOfRecords%>)</td>
							<td valign="center" height='20' width="10%" align="left"
								style="padding-left: 0.2em; * padding-left: 2px; font-size: 1em; font-family: verdana;"
								nowrap="true"><%= executedOn %></td>
							<td valign="center" height='20' width="10%" align="left"
								style="padding-left: 0.2em; * padding-left: 5px; font-size: 1em; font-family: verdana;">
							<%= ownerName%></td>

							<td valign="center" height='20' align="left"
								style="font-size: 1em; font-family: verdana;">
							<%
											target = "editQuery('"+parameterizedQuery.getId()+"')";

										%> &nbsp <A onclick="<%=target%>"
								onmouseover="Tip('Edit', WIDTH,36)" border="0"> <IMG
								src="images/advQuery/application_edit.png"
								id="editBtn_<%=parameterizedQuery.getId()%>" border="0"> </A>&nbsp;&nbsp;

							<%target = "executeQuery('"+parameterizedQuery.getId()+"')"; %> <A
								onclick="<%=target%>" onmouseover="Tip('Execute', WIDTH,44)"
								border="0"> <IMG src="images/advQuery/execute-button-1.PNG"
								id="executeBtn_<%=parameterizedQuery.getId()%>" border="0">
							</A>&nbsp;&nbsp; <%target = "deleteQueryPopup('"+parameterizedQuery.getId()+"','"+popupMessage+"')";
										if(queryOption!= null && queryOption.equals("myQueries"))
										{%> <A onclick="<%=target%>" border="0"> <IMG
								src="images/advQuery/cancel.png"
								id="deleteBtn_<%=parameterizedQuery.getId()%>" border="0">
							</A> <%}%>
							</td>

						</tr>
					</c:forEach>
			</table>
			</div>
			<script>
						searchDivTag=document.getElementById('searchDiv');
						searchDivTag.style.height = (document.body.clientHeight-105) + 'px';
					</script></td>
		</tr>
	</table>
	</td>
	</tr>
	</table>

	<html:hidden styleId="queryId" property="queryId" />
</html:form>
</body>
