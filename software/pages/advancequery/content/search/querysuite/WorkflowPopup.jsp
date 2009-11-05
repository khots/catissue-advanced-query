<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">

<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
%>
<%-- Imports --%>
<link href="css/cider.css" rel="stylesheet" type="text/css" />
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants, org.apache.struts.Globals"
%>
<%@ page language="java" isELIgnored="false"%>
<%@ page import="org.apache.struts.action.ActionMessages, edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.common.querysuite.queryobject.impl.AbstractQuery"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<script language="JavaScript" type="text/javascript" src="jss/advancequery/search.js"></script>
<script  src="dhtml_comp/js/dhtmlXCommon.js"></script>
<script  src="dhtml_comp/js/dhtmlXTabbar.js"></script>
<script  src="dhtml_comp/js/dhtmlXTabBar_start.js"></script>
<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTabbar.css">
<link rel='STYLESHEET' type='text/css' href='dhtml_comp/css/style.css'>

<script>
var tabbar;
var mainTabstyle="width:870px; height:340px;";
var browser=navigator.appName;
		 function my_func(idn,ido){
              
						
				
						if(idn=='sharedqueries')
						{
							//tabbar.setTabActive("sharedqueries");
							retrieveMyQueries('sharedQueriesForWorkFlow');
							
							
						}
						else
						{
							
							// tabbar.setTabActive("myqyeries");
							 retrieveMyQueries('myQueriesForWorkFlow');
							
						}
					
						
			return true;
			
				
            }
function closePopup()
{

	var numberofSameQueries=1;
	//reset same Query title 
	document.getElementById("sameQueryTitle").value="";
	document.getElementById("numberOfSameQueryTitle").value=numberofSameQueries;

	var checkboxArray=document.getElementsByName('chkbox');
	var sharedchkboxArray=document.getElementsByName('sharedchkbox');
	clearSelBoxList(parent.window.document.getElementById("queryId"));
	clearSelBoxList(parent.window.document.getElementById("queryTitle"));
	clearSelBoxList(parent.window.document.getElementById("queryType"));


	var isOptionAdded=0;

	if(checkboxArray!=null)
	{
			var numOfRows =checkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
					var id = 'chkbox_'+count;
					
				if(document.getElementById(id).checked)
				{
					document.getElementById("chkbox_"+count).checked=false;
					//checks that query already exists in parent window
					if(checkAlreadyPresent(document.getElementById("queryIdControl_"+count).value)==false)
					{
						addOption(parent.window.document.getElementById("queryId"),document.getElementById("queryIdControl_"+count).value,document.getElementById("queryIdControl_"+count).value);

						addOption(parent.window.document.getElementById("queryTitle"),""+document.getElementById("queryTitleControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
						addOption(parent.window.document.getElementById("queryType"),""+document.getElementById("queryTypeControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
       
						  //add selected count query to dropdowns of parent window
					/*	if((document.getElementById("queryTypeControl_"+count).value)=="GetCount")
					  {
						 var countQDD= parent.window.document.getElementsByName("countQueryDropDown");
						  for(var i=0;i<countQDD.length;i++) 
						 {
							  addOption(countQDD[i],document.getElementById("queryTitleControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
						 }
					  } */

					}
					else
					{
						//document.getElementById("sharedchkbox"+count+1).disabled=true;
						if(document.getElementById("sameQueryTitle").value!=""
						&&document.getElementById("sameQueryTitle").value!=null
						&&document.getElementById("sameQueryTitle").value!=undefined)
						{							    
							document.getElementById("sameQueryTitle").value=document.getElementById("sameQueryTitle").value+","+(document.getElementById("queryTitleControl_"+count).value);
							numberofSameQueries=++numberofSameQueries;
							document.getElementById("numberOfSameQueryTitle").value=numberofSameQueries	;
							isOptionAdded=isOptionAdded+1;
							document.getElementById("type").value=document.getElementById("queryTypeControl_"+count).value;
						}
						else
						{
							document.getElementById("sameQueryTitle").value=document.getElementById("queryTitleControl_"+count).value;
								isOptionAdded=isOptionAdded+1;
								document.getElementById("type").value=document.getElementById("queryTypeControl_"+count).value;

						}

							
					}


				}	
			}
	

	}

		if(sharedchkboxArray!=null)
	{
			var numOfRows =sharedchkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
					var id = 'sharedchkbox_'+count;

				if(document.getElementById(id).checked)
				{
					document.getElementById("sharedchkbox_"+count).checked=false;

					//checks that query already exists in parent window
					if(checkAlreadyPresent(document.getElementById("queryIdControl_"+count).value)==false)
					{
						addOption(parent.window.document.getElementById("queryId"),document.getElementById("queryIdControl_"+count).value,document.getElementById("queryIdControl_"+count).value);

						addOption(parent.window.document.getElementById("queryTitle"),""+document.getElementById("queryTitleControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
						addOption(parent.window.document.getElementById("queryType"),""+document.getElementById("queryTypeControl_"+count).value,document.getElementById("queryIdControl_"+count).value);
       
					}
					else
					{
						if(document.getElementById("sameQueryTitle").value!=""
						&&document.getElementById("sameQueryTitle").value!=null
						&&document.getElementById("sameQueryTitle").value!=undefined)
						{							    
							document.getElementById("sameQueryTitle").value=document.getElementById("sameQueryTitle").value+","+(document.getElementById("queryTitleControl_"+count).value);
							numberofSameQueries=++numberofSameQueries;
							document.getElementById("numberOfSameQueryTitle").value=numberofSameQueries	;
								isOptionAdded=isOptionAdded+1;
								document.getElementById("type").value=document.getElementById("queryTypeControl_"+count).value;
						}
						else
						{
							document.getElementById("sameQueryTitle").value=document.getElementById("queryTitleControl_"+count).value;
								isOptionAdded=isOptionAdded+1;
								document.getElementById("type").value=document.getElementById("queryTypeControl_"+count).value;

						}

					}


				}	
			}
	

	}

	if(document.getElementById("sameQueryTitle").value!="")
	{
		samequerytitleWindow=dhtmlmodal.open('Query title', 'iframe', './pages/advancequery/content/search/querysuite/QueryTitlePopup.jsp','Query Information', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
		//document.getElementById("sameQueryTitle").value="";
	}

	updateOpenerUI();
	if(isOptionAdded==0)
	{
		closeModalWindow();
	}
	setCheckAllBoxCount();
}
function setCheckAllBoxCount()
{
	document.getElementById("selectAllCheckbox2").checked=false;
	setCheckBoxCount();
}
function setCheckBoxCount()
{
	var checkboxArray = document.getElementsByName('chkbox');
	var sharedchkbox = document.getElementsByName('sharedchkbox');
	var selectedCheckboxes=0;
	if(checkboxArray!=null)
	{
		var numOfRows =checkboxArray.length;
		for(var count = 1; count <= numOfRows; count++)
		{
			var id = 'chkbox_'+count;
			if(document.getElementById(id).checked)
			{
				selectedCheckboxes++;
			}
			if(selectedCheckboxes>0)
				break;
		}
	}
	
	if(sharedchkbox!=null)
	{
		var numOfRows =sharedchkbox.length;
		for(var count = 1; count <= numOfRows; count++)
		{
			var id = 'sharedchkbox_'+count;
			if(document.getElementById(id).checked)
			{
				selectedCheckboxes++;
			}
			if(selectedCheckboxes>0)
				break;
		}
	}
	if(selectedCheckboxes>0)
		enableLinkAddtoWF();
	else
		disableLinkAddtoWF();
}

function enableLinkAddtoWF()
{
	document.getElementById("addToWorkflow").innerHTML = '<a id="addToWFLink1" href="javascript:closePopup()" class="bluelink"><bean:message key="workflow.add"/></a>';
}

function disableLinkAddtoWF()
{
	document.getElementById("addToWorkflow").innerHTML = '<a id="addToWFLink2" href="#" class="greylink2" disabled="true"><bean:message key="workflow.add"/></a>';
}

function closeModalWindow()
{
	parent.pvwindow.hide();

}
function clearSelBoxList(selBoxObj)
{	
	if(selBoxObj != null)
	 {
		while(selBoxObj.length > 0)
		 {
			selBoxObj.remove(selBoxObj.length - 1);
		  }
	  }
}


function updateOpenerUI() { 
  if(document.getElementById) { 
         elm = parent.window.document.getElementById("btn");
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
     } 



function selectAllOptions(control)
	{
		for(i=control.options.length-1;i>=0;i--)
		{
			control.options[i].selected=true;
		}	
	}

function addOption(theSel, theText, theValue)
{
	var optn = parent.window.document.createElement("OPTION");
	optn.text=theText;
	optn.value=theValue;
	theSel.options.add(optn);	
}

function selectAllCheckboxes(selectAllCheckboxesId)
{ 
	//alert("checkboxControl.checked");
	var checkboxControl=document.getElementById(selectAllCheckboxesId);
	//alert("checkboxControl.checked"+checkboxControl.checked);
	if(checkboxControl!=null && checkboxControl!=undefined)// && checkboxControl.checked==true)
	{
			if(checkboxControl.checked)
				enableLinkAddtoWF();
			else
				disableLinkAddtoWF();

			if(selectAllCheckboxesId=='selectAllCheckbox1')
			{
				
				setCheckedBoxes('chkbox',checkboxControl);
			}
			if(selectAllCheckboxesId=='selectAllCheckbox2')
			{
				
				setCheckedBoxes('sharedchkbox',checkboxControl);
			}


	}

}
function setCheckedBoxes(selectedForQueries,checkboxControl)
{
	var checkboxArray=document.getElementsByName(selectedForQueries);
				var numOfRows =checkboxArray.length;

				for(var count = 1; count <= numOfRows; count++)
				{

					document.getElementById(selectedForQueries+'_'+count).checked=checkboxControl.checked;		
				}
}
function checkAlreadyPresent(itemToadd)
{
		
	var noOfRows=parent.window.document.getElementById("countTable").rows.length
	var alreadyExist=false;

		for(var idCount=0;idCount<noOfRows; idCount++)
		{
			if(parent.window.document.getElementById('selectedqueryId_'+idCount).value==itemToadd)
			{	
				alreadyExist=true;
			}	
		}	

		noOfRows=parent.window.document.getElementById("dataTable").rows.length
		for(var idCount=0;idCount<noOfRows; idCount++)
		{
			if(parent.window.document.getElementById('selectedDataqueryId_'+idCount).value==itemToadd)
			{	
				alreadyExist=true;
			}	
		}	
		return alreadyExist;
}
function changeResPerPage(controlId)
{

	var resultsPerPage=document.getElementById(controlId).value;
	//resizeWindow(resultsPerPage);
	var queryNameLike='${requestScope.queryNameLike}';
	var queryType='${requestScope.queryType}';
	var url='RetrieveQueryAction.do?pageOf=${requestScope.pageOf}&requestFor=nextPage&pageNum=1&numResultsPerPage='+resultsPerPage+'&queryNameLike='+escape(queryNameLike)+"&queryType="+queryType;
	document.forms[0].action=url;
	document.forms[0].submit();	
}
/*function resizeWindow(resultsPerPage)
{
	if(resultsPerPage==5)
	{
		parent.pvwindow.setSize(.7*900,.7 *380);
	}
	else if(resultsPerPage==15)
	{
					parent.pvwindow.setSize(930,1.1*400);

	}
	else
	{
				parent.pvwindow.setSize(900,395);
	}
}*/

function retrieveMyQueries(pageOf)
{
	enableRemoveFilter();
	var searchString='${requestScope.queryNameLike}';
	var queryType='${requestScope.queryType}';
	var url='RetrieveQueryAction.do?pageOf='+pageOf+'&pageNum=1&queryNameLike='+searchString+"&queryType="+queryType;
	document.forms[0].action=url;
	document.forms[0].submit();	
}
/*
 * for Searching Query by name String
 * */
function searchQuery()
{
		enableRemoveFilter();
//	var resultsPerPage=document.getElementById(controlId).value;
	//if(document.getElementById('isSearchStringChanged').value=='true')
	//{
		var searchString=document.getElementById('queryNameLike').value;
		var queryType='${requestScope.queryType}';
		var url='RetrieveQueryAction.do?pageOf=${requestScope.pageOf}&queryNameLike='+escape(searchString)+"&queryType="+queryType;
		document.forms[0].action=url;
		document.forms[0].submit();	
	//}
}

</script>
<head>
<title>CIDER: Clinical Investigation Data Exploration Repository</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="JavaScript"></script>
<script type="javascript" src="jss/advancequery/wz_tooltip.js"></script>
<link href="css/advancequery/workflow.css" rel="stylesheet" type="text/css" />
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body>

<% 
boolean mac = false;
Object os = request.getHeader("user-agent");
if(os!=null && os.toString().toLowerCase().indexOf("mac")!=-1)
{
	mac = true;
}
String height = "100%";		
if(mac)
{
  height="350";
}
String message = null; 
String popupMessage = (String)request.getAttribute(Constants.POPUP_MESSAGE);
int queryCount = 0;
%>

<html:form action="SaveWorkflow">
<table width="100%" border="0" cellspacing="0" cellpadding="4" height="100%">
<tr>
 <input type="hidden" name="sameQueryTitle" id="sameQueryTitle" value="">
  <input type="hidden" name="type" id="type" value="">

  <input type="hidden" name="numberOfSameQueryTitle" id="numberOfSameQueryTitle" value="0">
	<td>
		<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0" class="login_box_bg">
			<tr >
			

				<td valign="top" background="images/advancequery/bg_content_header.gif" colspan="4">
				<c:set var="query_type_data" value="myQueriesForWorkFlow"/>
				 <c:set var="qtype" value="${requestScope.pageOf}"/>
				  <logic:equal name="query_type_data" value="${qtype}">
					<img id="myQyerytabImg" src="images/advancequery/t_myqueries.gif" alt="My Queries" width="99" height="26" valign="absmiddle">
				  </logic:equal>
				   <logic:notEqual name="query_type_data" value="${qtype}">
						<img id="sharedQuerytabImg"  src="images/advancequery/t_shared_queries.gif" alt="My Queries " width="125" height="26" valign="absmiddle">
				   </logic:notEqual>
				</td>

			</tr>

			<tr>
				<td>
					<script>document.write('<div  id="a_tabbar" style="'+mainTabstyle+'">')</script>
				</td>

			</tr>

<tr>
	<td>
			<table width="100%" height="100%" border="0"><tr><td>
			<div id='html_2'  style="width:850px; height:310px;">

					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="25px">
						<tr>

						<td id="addToWorkflow" align="left" valign="top"><a href="#" id="addToWFLink2" class="greylink2" disabled="true"><bean:message key="workflow.add"/></a></td>

						<td align="right"  valign="top">
							<%@ include file="/pages/advancequery/content/search/querysuite/searchResult.jsp" %>
						</td>
						</tr>
					</table>

				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					
				<tr>
					<td colspan="4">
	
					<table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#EAEAEA">
						  <tr class="td_bgcolor_grey">
										<td width="10" height="25" valign="middle" ><input id="selectAllCheckbox2"   type="checkbox" name="checkbox9" value="checkbox" onClick="javascript:selectAllCheckboxes('selectAllCheckbox2')">                 
										</td>

							<td valign="middle" class="grid_header_text" colspan="2"><bean:message key="workflow.queryTitle"/></td>
							<td width="111" valign="middle" class="grid_header_text"><bean:message key="workflow.querytype"/></td>
							</tr>

									<%
											int sharedQueryCount=0;
										%>	
								
							<c:forEach var="query" items="${saveQueryForm.parameterizedQueryCollection}">
										<%
											 sharedQueryCount++;
										%>								
								<tr  bgcolor="#FFFFFF">
									<td width="10" height="25" valign="middle" >
									<c:set var="checkboxControl">sharedchkbox_<%=sharedQueryCount%></c:set>
									<input type="checkbox" name="sharedchkbox" onclick="javascript:setCheckBoxCount()" id="${checkboxControl}"/>
									</td>
									<td height="25" valign="top" class="content_txt" colspan="2">
									<c:set var="queryTitleControlId">queryTitleControl_<%=sharedQueryCount%></c:set>
															
										<html:hidden property="queryTitleControl" styleId="${queryTitleControlId}"
										value="${query.name}"/>
										${query.name}
									</td>
									<td height="25" valign="top" class="content_txt">${query.type}</td>	

								<c:set var="queryIdControl">queryIdControl_<%=sharedQueryCount%></c:set>
								<html:hidden property="queryIdControl" styleId="${queryIdControl}"
								value='${query.id}'/>

																								
								<c:set var="queryTypeControl">queryTypeControl_<%=sharedQueryCount%></c:set>
							
								<html:hidden property="queryTypeControl" styleId="${queryTypeControl}"
								value='${query.type}'/>
									  		
								</tr>	

							 </c:forEach>
							 <!--if no query to display-->
							 <logic:equal name="totalPages" value="0">
									<tr bgcolor="#FFFFFF">
										<td class="content_txt_bold" style="padding-left:5px;" valign="top" colspan="4">
										<bean:message key="meassges.emptyquery"/>
										</td>	
									</tr>
							 </logic:equal>
				
						</table>
					</td>
					</tr>
				</table>

			</div>
				</td></tr></table>
		<!-- end tabs-->
	</td>

</tr>

<!--start -->


			<tr valign="bottom">
			<td colspan="3" class="tr_color_lgrey" valign="bottom">

			<table height="*" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="tr_color_lgrey">					
						<td class="content_txt_bold" width="200">						  
								<table>
									<tr>
										<td class="content_txt_bold" style="padding-left:5px;">
											<bean:message key="userSearch.resultsPerPage"/>
										</td>
										<td>
											<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${sessionScope.numResultsPerPage}">
												<html:options collection="resultsPerPageOptions" labelProperty="name" property="value"/>
											</html:select>
										</td>
									</tr>
								</table>
							
						  </td>
						<td class="content_txt_bold" align="center">
							<bean:message key="userSearch.showing"/> ${sessionScope.pageNum} <bean:message key="userSearch.of"/>  <c:out value="${sessionScope.totalPages}"></c:out>
						</td>	
						<td width="15" align="right">
							
							<logic:greaterEqual name="firstPageNum" value="${requestScope.numOfPageNums+1}">	
								<a class="bluelinkNoUnderline" href="RetrieveQueryAction.do?requestFor=nextPage&pageOf=${requestScope.pageOf}&pageNum=${requestScope.firstPageNum-requestScope.numOfPageNums}&firstPageNum=${requestScope.firstPageNum-5}&lastPageNum=${requestScope.lastPageNum-5}&queryNameLike=${requestScope.queryNameLike}&queryType=${requestScope.queryType}"> 
									<< 			    						    	
								</a>							
							</logic:greaterEqual>	
						</td>
						<td class="content_txt" width="100" align="center" nowrap>
							<div ID="links">
								<c:set var="pageOf" value="${requestScope.pageOf}"/>  
								<jsp:useBean id="pageOf" type="java.lang.String"/>									
								<c:set var="totalPages" value="${sessionScope.totalPages}"/>										 									
								<jsp:useBean id="totalPages" type="java.lang.Integer"/>																														
								<c:forEach var="pageCoutner" begin= "${requestScope.firstPageNum}" end="${requestScope.lastPageNum}">
									<c:set var="linkURL">
										RetrieveQueryAction.do?pageOf=<c:out value="${pageOf}"/>&requestFor=nextPage&pageNum=<c:out value="${pageCoutner}"/>
										&queryNameLike=<c:out  value="${queryNameLike}"/>&queryType=${requestScope.queryType}
									</c:set>
									<jsp:useBean id="linkURL" type="java.lang.String"/>
									<c:if test="${sessionScope.pageNum == pageCoutner}">
									<span class="content_txt_bold">
										<c:out value="${pageCoutner}"/> 
									</span>
									</c:if>
									<c:if test="${sessionScope.pageNum != pageCoutner}">
										<a class="bluelink" href="<%=linkURL%>"><c:out value="${pageCoutner}"/></a>
									</c:if>
									<c:if test="${pageCoutner < requestScope.lastPageNum}">
										|&nbsp;
									</c:if>
								</c:forEach>    
							</div>  
						</td>	
						<td width="15" style="padding-right:5px;"align="right">
							<logic:lessEqual name="lastPageNum" value="${sessionScope.totalPages-1}">
								<a class="bluelinkNoUnderline" href="RetrieveQueryAction.do?requestFor=nextPage&pageOf=${requestScope.pageOf}&pageNum=${requestScope.lastPageNum+1}&firstPageNum=${requestScope.firstPageNum+5}&lastPageNum=${requestScope.lastPageNum+5}&queryNameLike=${requestScope.queryNameLike}&queryType=${requestScope.queryType}"> 
						  		  	>> 
								</a>
							</logic:lessEqual>
							
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

<script>
defaultdisableButtons();
</script>

<!-- if no query -->


			<script>
			tabbar=new dhtmlXTabBar("a_tabbar","top");
            tabbar.setImagePath("dhtml_comp/imgs/");

          //  tabbar.setSkinColors("#FCFBFC","#F4F3EE");
			tabbar.setSkinColors("#FFFFFF","#FFFFFF");
			tabbar.addTab("myqyeries","<div id='myqueytab'> My Queries("+'${requestScope.myQueriesCount}'+")</div>" ,"200px");
            tabbar.addTab("sharedqueries"," <div id='sharedquerytab'>  Shared Queries("+'${requestScope.sharedQueriesCount}'+")</div>","200px");


			tabbar.setContent("myqyeries","html_2");
            tabbar.setContent("sharedqueries","html_2");
			if('${requestScope.pageOf}'=='myQueriesForWorkFlow')
			{
				tabbar.setTabActive("myqyeries");
			}
			else
			{
				tabbar.setTabActive("sharedqueries");
			}

			//tabbar.setSkin("modern");
			tabbar.setOnSelectHandler(my_func);
		//	tabbar.setSkinColors("white","#D9EEFC");
			//tabbar.onClick(my_func());
     

</script>
<!--
<tabbar  hrefmode="iframes">
    <row>
        <tab id="myqyeries" width='100px' href="retrieveMyQueries('sharedQueriesForWorkFlow')">sharedQueries</tab>
        <tab id="sharedqueries" width='100px' selected="1" href="retrieveMyQueries('myQueriesForWorkFlow')" onClick="retrieveMyQueries('myQueriesForWorkFlow')">myQueries</tab>
    </row>
</tabbar>-->
</html:form>
</body>
