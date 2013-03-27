<%@page import="edu.wustl.query.bizlogic.DashboardBizLogic"%><META
	HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/multiSelectUsingCombo.tld" prefix="mCombo" %>
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
<%@ page import="edu.wustl.query.beans.DashboardBean"%>
<head>
<!-- dhtmlx Grid/tree Grid -->
<script type="text/javascript" src="jss/advQuery/queryModule.js"></script>
<script type="text/javascript" src="jss/advQuery/wz_tooltip.js"></script>
<script type="text/javascript" src="jss/tag-popup.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/advQuery/styleSheet.css" />
<link rel="stylesheet" type="text/css" href="css/advQuery/tag-popup.css" />
<link rel="STYLESHEET" type="text/css"
	href="dhtmlx_suite/css/dhtmlxgrid.css">
<link rel="STYLESHEET" type="text/css"
	href="dhtmlx_suite/css/dhtmlxtree.css">
<script src="dhtmlx_suite/js/dhtmlxcommon.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="dhtmlx_suite/dhtml_pop/css/dhtmlxgrid_dhx_skyblue.css" />
<script src="dhtmlx_suite/js/dhtmlxgrid.js"></script>
<script src="dhtmlx_suite/js/dhtmlxtree.js"></script>
<script src="dhtmlx_suite/ext/dhtmlxgrid_filter.js"></script>
<link rel="STYLESHEET" type="text/css"
	href=" dhtmlx_suite/ext/dhtmlxgrid_pgn_bricks.css"/>
<script src="dhtmlx_suite/ext/dhtmlxgrid_pgn.js"></script>
<script src="dhtmlx_suite/js/dhtmlxgridcell.js"></script>
<script src="dhtmlx_suite/dhtml_pop/js/spec_dhtmlXTreeGrid.js"></script>  

<!-- Combo box -->
<script>var imgsrc="/images/de/";</script>
<script language="JavaScript" type="text/javascript"
	src="javascripts/de/scr.js"></script>
 
<link rel="stylesheet" type="text/css"
	href="css/clinicalstudyext-all.css" />
<script language="JavaScript" type="text/javascript"
	src="javascripts/de/ajax.js"></script>
<script type='text/JavaScript' src='jss/advQuery/scwcalendar.js'></script>
<script>
function QueryWizard()
{
	var rand_no = Math.random();
	document.forms[0].action='QueryWizard.do?random='+rand_no;
	document.forms[0].submit();
}
window.onload = function() {  
	<%
		String tagId =(String) request.getAttribute("tagId");
		if(tagId != null){ 
	%>
			doInitGrid('<%=tagId%>');
			doOnRowSelected('<%=tagId%>');
   	<%
		} else { 
	%>
   			ajaxQueryGridInitCall("QueryGridInitAction.do");
			doInitGrid();
   	<%}%>
	document.getElementById('protocolCoordinatorIds').style.marginLeft= "15px";
	document.getElementById('addButton_coord').style.marginLeft= "21px";
	document.getElementById('removeButton_coord').style.marginLeft= "21px";
	initCombo();
}  
function f()
{
	searchDivTag=document.getElementById('searchDiv');
	searchDivTag.style.height = (document.body.clientHeight-105) + 'px';
}
var grid;
function doInitGrid(tagId)
{
	grid = new dhtmlXGridObject('mygrid_container');
	grid.setImagePath("dhtmlx_suite/dhtml_pop/imgs/");
 	grid.setHeader("My Folders");
 	grid.setInitWidthsP("100");
 	grid.setColAlign("left");
 	grid.setSkin("dhx_skyblue"); // (xp, mt, gray, light, clear, modern)
 	grid.enableRowsHover(true,'grid_hover')
 	grid.setEditable(false);
   	grid.attachEvent("onRowSelect", doOnRowSelected);
 	grid.init();
 	grid.load ("TagGridInItAction.do",function() {
  	   grid.selectRowById(tagId);
  	});
}

function doOnRowSelected(rId)
{
	ajaxQueryGridInitCall("QueryGridInitAction.do?tagId="+rId);
	document.getElementById('myQueries').className='btn1';
	document.getElementById('allQueries').className='btn1';
	document.getElementById('sharedQueries').className='btn1';
}
 
var xmlHttpobj = false;
function ajaxQueryGridInitCall(url) {
	
 if (window.XMLHttpRequest)
 { 
  	xmlHttpobj=new XMLHttpRequest();
 }
 else
 { 
  	xmlHttpobj=new ActiveXObject("Microsoft.XMLHTTP");
 }
	  
	xmlHttpobj.onreadystatechange = showGrid;
	xmlHttpobj.open("POST", url,false);
	xmlHttpobj.send(null);
}
var responseString;
var queryGrid;
function showGrid() {
	if (xmlHttpobj.readyState == 4) 
	{
		 responseString =xmlHttpobj.responseText;
		 queryGrid = new dhtmlXGridObject('mygrid_right_container');
		 queryGrid.setImagePath("dhtmlx_suite/dhtml_pop/imgs/");
		 queryGrid.setHeader(",<b>ID,<b>Title,<b>Results,<b>Executed On,<b>Owner,<b>Actions ");
		 queryGrid.attachHeader("#rspan,#numeric_filter,#text_filter,#text_filter,#rspan,#select_filter,#rspan"); 
		 queryGrid.setInitWidthsP("3,5,*,20,11,12,10");
		 queryGrid.setColAlign("center,center,left,left,center,center,left");
		 queryGrid.setColTypes("txt,txt,txt,txt,txt,txt,txt");
		 queryGrid.setColSorting("str,int,str,str,str,str");
		 queryGrid.setSkin("dhx_skyblue"); // (xp, mt, gray, light, clear, modern)
		 queryGrid.enableRowsHover(true,'grid_hover')
		 queryGrid.setEditable(false);
		 queryGrid.enableTooltips("false,false,false,false,false,false,false");
		 queryGrid.clearAll(true);
		 queryGrid.init();
		 queryGrid.enablePaging(true,20,10,"pagingArea",true);
		 queryGrid.setPagingSkin("bricks");
		 queryGrid.loadXMLString(responseString); 
		 document.getElementById('messageDiv').style.display = "none";
	}
}

function setHeader(isQueryChecked)
{
	if(isQueryChecked == true){		 
		document.getElementById("poupHeader").textContent ="Assign the query(s) to folder";
		document.getElementById("poupHeader").innerText ="Assign the query(s) to folder";
	}else{
		document.getElementById("poupHeader").textContent ="Share the folder(s) with user";
		document.getElementById("poupHeader").innerText ="Share the folder(s) with user";
	} 
}
</script>

 
 
<body onunload="doInitOnLoad();" onresize='f()'>
	<%
		boolean mac = false;
		Object os = request.getHeader("user-agent");
		if (os != null && os.toString().toLowerCase().indexOf("mac") != -1) {
			mac = true;
		}
		String selectText = "--Select--";
		String queryId = "5";
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
	%>

<script>

function checkForValidation()
{
	var tdId = "multiSelectId";
	var displayStyle = 	document.getElementById(tdId).style.display;
	if(displayStyle == "block")
	{
		var coords = document.getElementById('protocolCoordinatorIds');
		if(coords.options.length == 0)
		{
			alert("Please select atleast one user from the dropdown");
		}
		else
		{
			ajaxShareTagFunctionCall("ShareTagAction.do","Select at least one existing folder.") 
		}
	}
}
function initCombo(){
	var myUrl= 'ShareQueryAjax.do?';
	var ds = new Ext.data.Store({proxy: new Ext.data.HttpProxy({url: myUrl}),
	reader: new Ext.data.JsonReader({root: 'row',totalProperty: 'totalCount',id: 'id'}, [{name: 'id', mapping: 'id'},{name: 'excerpt', mapping: 'field'}])});
	var combo = new Ext.form.ComboBox({store: ds,hiddenName: 'CB_coord',displayField:'excerpt',valueField: 'id',typeAhead: 'false',pageSize:15,forceSelection: 'true',queryParam : 'query',mode: 'remote',triggerAction: 'all',minChars : 3,queryDelay:500,lazyInit:true,emptyText:'--Select--',valueNotFoundText:'',selectOnFocus:'true',applyTo: 'coord'});

	combo.on("expand", function() {
	if(Ext.isIE || Ext.isIE7)
	{
		combo.list.setStyle("width", "250");
		combo.innerList.setStyle("width", "250");
	}else{
		combo.list.setStyle("width", "250");
		combo.innerList.setStyle("width", "250");
	}
	}, {single: true});
	ds.on('load',function(){
		if (this.getAt(0) != null && this.getAt(0).get('excerpt')) 
		{combo.typeAheadDelay=50;
		} else {combo.typeAheadDelay=60000}
		});
	}
</script>
 
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
				<td style="padding-left:10px;" class="savedQueryHeading">
						<div id="errorDiv" ><html:errors /></div>
						<div id="messageDiv" style="font-size: 0.9em; font-family: verdana; display:none"> <bean:message key="query.deletedSuccessfully.message"/></div>
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
			<td height="7px;"> </td>
			<tr>
			</tr>
		</table>
<table width='100%' height='100%' cellpadding='0' cellspacing='0' border='0'
			align='center'>
<tr>
<td>
		<div id="left">
			<table class="tags" width="100%" cellspacing="0" cellpadding="0"
				border="0">
 
				<tbody>
					<tr>
						<td><div style="height: 30px; width: 166px;" id="toolbarObj1">
									<input type="button" value="My Queries" id="myQueries" title ="My Queries"
										onclick="submitTheForm('QueryGridInitAction.do?pageOf=myQueries',this.id);"
										class="activebtn">
							</div>
						</td>
					</tr>
					<tr>
						<td><div style="height: 30px; width: 166px;" id="toolbarObj2">
									<input type="button" value="All Queries" id="allQueries" title="All Queries"
										onclick="submitTheForm('QueryGridInitAction.do?pageOf=allQueries',this.id);"
										class="btn1">
							</div>
						</td>
					</tr>
					<tr>
						<td><div style="height: 30px; width: 166px;" id="toolbarObj3">
									<input type="button" value="Shared Queries" id="sharedQueries" title="Shared Queries"
										onclick="submitTheForm('QueryGridInitAction.do?pageOf=sharedQueries',this.id);"
										class="btn1">
							</div>
						</td>
					</tr>
					<tr>
						<td height=100%><div id="mygrid_container" height="26.5em" width="97%"></div></td>
					</tr>
				</tbody>
			</table>

		</div>
 
		<div id="wrapper">
			<div id="mainContent">
				<!--POPUP-->
				<div id="blanket" style="display: none;"></div>
				<div id="popUpDiv" style="display: none; top: 100px; left: 210.5px;">
					<a onclick="popup('popUpDiv')"><img style="float: right; cursor:pointer;"
						height='23' width='24' title="Close" src='images/advQuery/close_button.gif'
						border='0'> </a>
					 
					<table class=" manage tags" width="100%" cellspacing="0"
						cellpadding="0" border="0">
							<tr valign="center" height="35" bgcolor="#d5e8ff">
								<td id="poupHeader" width="28%" align="left">
									<p>
										&nbsp&nbsp&nbsp&nbsp<b> <%=popupHeader%></b>
									</p>
								</td>
							</tr>
							<tr>
								<td align="left">
									<div id="treegridbox"
											style="width: 530px; height: 170px; background-color: white;"></div>
								</td>
							</tr>
							<tr>
								<td  align="left">
									<p>
											<label id="newTagLabel" width="28%" align="left"
											style="margin-left :20px; font-size: .82em; font-family: verdana;"><b> <%=popupText%>
											: </b> </label> <input type="text" id="newTagName" name="newTagName"
											size="17" onclick="this.value='';" maxlength="50" />
										 	<label id="shareLabel" width="28%" align="left"
											style="margin-left :20px; font-size: .82em; font-family: verdana;"><b> Share to users :
					 						</b> </label>
									</p>
								</td>
							</tr>
							<tr>
								<td  align="left">
									<div id="multiSelectId" class="black_ar_new" style="display:none; margin-left:35px">
											&nbsp&nbsp&nbsp<mCombo:multiSelectUsingCombo identifier="coord" styleClass="black_ar_new"  size="20" addButtonOnClick="moveOptions('coord','protocolCoordinatorIds', 'add')" removeButtonOnClick="moveOptions('protocolCoordinatorIds','coord', 'edit')" selectIdentifier="protocolCoordinatorIds" collection="<%=(List)request.getAttribute("selectedCoordinators")%>"/>
								 
									</div>
								</td>
							</tr>
							<tr>
								<td height="10px;"> </td>
							</tr>
							<tr>
								<td>	 
									<p> 			
									<%
 String	assignTarget = "ajaxAssignTagFunctionCall('AssignTagAction.do','"+popupAssignMessage+"','"+popupAssignConditionMessage+"')";
 									%>
 										&nbsp&nbsp&nbsp
										<input type="button" id="assignButton" value="ASSIGN" title="Assign" onclick="<%=assignTarget%> "
							onkeydown="<%=assignTarget%> " class="btn3">
				 
 										&nbsp&nbsp
										<input type="button"  id="shareButton" value="SHARE FOLDER" title="Share Folder (Folder will be visible to the users you choose)" onclick="checkForValidation()"
												onkeydown="checkForValidation()" style="width:120px; display:none" class="btn3">
										<img id="loadingImg" style="float:left; padding-left:5px; display:none;"
												height='25px' width='120px' src='images/advQuery/loading_circle.gif'
										border='0'>
									</p>
								</td>
							</tr>
					</table>
				</div>
			</div>
 

			<div id="right">
				<table width="100%" cellpadding='0' cellspacing='0' border='0'>
						<tr>
							<td>
								<div id="mygrid_right_container" height="31em" width="100%"></div>
								<div id="pagingArea"></div>
							</td>
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
<script>

 

</script>
