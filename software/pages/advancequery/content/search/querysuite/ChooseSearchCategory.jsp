<!-- Page Modified by (amit_doshi) Bug Fixed :- 12511 -->

<%@ page import="edu.wustl.query.actionforms.CategorySearchForm"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<script src="jss/advancequery/queryModule.js"></script>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<script  src="dhtml_comp/js/dhtmlXCommon.js"></script>
<script  src="dhtml_comp/js/dhtmlXTabbar.js"></script>
<script  src="dhtml_comp/js/dhtmlXTabBar_start.js"></script>
<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTabbar.css">
<link rel='STYLESHEET' type='text/css' href='dhtml_comp/css/style.css'>
<link rel='STYLESHEET' type='text/css' href='dhtml_comp/css/inside.css'>
<script>
<%
String height = "";
String currentPage = (String)request.getAttribute(Constants.CURRENT_PAGE);
System.out.println("currentPage"+currentPage);
	String formName = Constants.categorySearchForm ;
	String SearchCategory = Constants.SearchCategory ;
	Boolean iseditedQuery=false;
	if(request.getAttribute(Constants.IS_EDITED_QUERY)!=null || (request.getParameter(Constants.IS_EDITED_QUERY)!=null && request.getParameter(Constants.IS_EDITED_QUERY).equalsIgnoreCase("true")))
	{
		iseditedQuery = true;
	}
%>

var tabbar;
var confirmAnswer;
var tabSelectedByDefault;
var mainTabstyle="width:265px; height:650px;";
var searchDivstyle="width:259px;height:490px;overflow:auto;border-top:1px solid #cccccc;";

var browser=navigator.appName;
var contentHeight = "630px";
var contentWidth = "267px";
	if(navigator.userAgent.indexOf('Safari')!=-1)
		 {
			mainTabstyle="width:275px; height:660px;";
			searchDivstyle="width:265px;height:490px;overflow:auto;";
		 }
	if(navigator.appName == "Microsoft Internet Explorer")
		{
			contentHeight = "631px";
			<%
				height = "60%";
			%>
		}
		else
		{
			<%
				height ="600" ;
			%>
		}

	function checkKey(event) 
	{
		var platform = navigator.platform.toLowerCase();
		var key;
		if (event.keyCode) key = event.keyCode;
			else if (event.which) key = event.which;
		if (platform.indexOf("mac") != -1)
		{				
			if (key == 13) { event.returnValue=false; } 
		}
		else
		{
		    event.returnValue=true;
		}
		retrieveEntities(key);
	}

	function retrieveEntities(key)
	{
		var string = document.forms[0].textField.value;
		if (string == "")
		{
			var element = document.getElementById('resultSet');
			element.innerHTML = string;
			return ;
		}
		retriveSearchedEntities('<%= SearchCategory %>','<%=formName%>','<%=currentPage%>', key);
		return ;
	}
	function defaultsearchTab(tabbar,isDisableOther)
	{
		//selectedTab = "searchTab";
		tabSelectedByDefault = true;
		switchToTab("searchTab");
		tabbar.setTabActive("searchTab");
		if(isDisableOther)
		{
			tabbar.disableTab('categoryTab',true); 
		}
	}
	function defaultcategoryTab(tabbar,isDisableOther)
	{
		tabSelectedByDefault = true;
		switchToTab("categoryTab");
		tabbar.setTabActive("categoryTab");
		getAllCategories("Category.do?isQuery="+'<%=isQuery%>');
		if(isDisableOther)
		{
			tabbar.disableTab('searchTab',true); 
		}
	}
	function categoryTab(tabbar)
	{
		var dataQuery = document.getElementById("pageOf").value
				if(tabbar.getActiveTab()!="categoryTab")
				{
				   var isDagEmpty = CheckDagContent();
				   var answer= true;
				   var url		='./pages/advancequery/content/search/querysuite/ConfirmationPopUpYesNo.jsp?messageKey=query.clearDag';
				   if(!isDagEmpty)	
					{
					   pvwindow=dhtmlmodal.open('Clear DAG', 'iframe', url,'Clear DAG', 'width=450px,height=105px,center=1,resize=1,scrolling=0');
						pvwindow.onclose=function()
						{
							if(confirmAnswer=="true")
							{
								changeActiveTabToCategory(tabbar);
								tabbar.setTabActive("categoryTab");
							}
	  						return true;
						}
					}
					else
				 	 {
						changeActiveTabToCategory(tabbar);
						return true;
				 	 }
				 	 
			 	}
			
		return false;
	}
	function searchTab(tabbar)
	{
		if(tabbar.getActiveTab()!="searchTab")
		{
		   var isDagEmpty = CheckDagContent();
		   var answer= true;
		   var url		='./pages/advancequery/content/search/querysuite/ConfirmationPopUpYesNo.jsp?messageKey=query.clearDag'; 
		  if(!isDagEmpty)
			{
			  pvwindow=dhtmlmodal.open('Clear DAG', 'iframe', url,'Clear DAG ', 'width=450px,height=105px,center=1,resize=1,scrolling=0');
			  pvwindow.onclose=function()
			  {
				  if(confirmAnswer=="true")
					{
					  changeActiveTabToEntities(tabbar);
					  tabbar.setTabActive("searchTab");
					}
					return true;
			  }
			}
		  else
		  {
			  changeActiveTabToEntities(tabbar);  
			  return true;
		  }
           // answer = confirm ("Query in DAG will be cleared. Do you want to search entities?")
		  
		}
		return false;
	}

function changeActiveTabToCategory(tabbar)
{
	switchToTab("categoryTab");
	getAllCategories("Category.do");
	clearDag();
}

function changeActiveTabToEntities(tabbar)
{
	switchToTab("searchTab");
	searchEntity("SearchEntity.do");
	clearDag();
}	

function my_func(idn,ido){
               
						
				if( ! tabSelectedByDefault)
				{
						if(idn=='categoryTab')
						{
							return categoryTab(tabbar);
							
						}
						else
						{
							return searchTab(tabbar);
						}
					
						
				}
				tabSelectedByDefault = false;
				return true;
            };
	
	//This function will the called while switching between Tabs
	function switchToTab(selectedTab)
	{
		var msgRow = document.getElementById('AddLimitsMsgRow');
		//var catTab = document.getElementById('categorySet');
		if(msgRow != null)
		{
			msgRow.innerHTML = " <table border='0' width='100%' height='28' background='images/advancequery/bg_content_header.gif' cellspacing='0' cellpadding='0'>"+
            "<tr width='100%'><td style='border-bottom: 1px solid #cccccc;padding-left:5px;' valign='middle' class='PageSubTitle' colspan='8' >Define Limits </td></tr></table>";
		}
		var validationMsgRow = document.getElementById('validationMessagesRow');
		if(validationMsgRow != null)
		{
			validationMsgRow.innerHTML = "";
		}
		var addLimitsSection = document.getElementById('addLimits');
		if(addLimitsSection != null)
		{
			addLimitsSection.innerHTML = "";
		}
		var addLimitsButton = document.getElementById('AddLimitsButtonRow');
		if(addLimitsButton != null)
		{
			addLimitsButton.innerHTML = "";
		}

	}
	
</script>
</head>
<%
boolean mac = false;
Object os = request.getHeader("user-agent");
if(os!=null && os.toString().toLowerCase().indexOf("mac")!=-1)
{
	mac = true;
}
if (mac)
{
%>
<body onkeypress="checkKey(event)" >
<%
}
else
{
%>
<body onkeyup="checkKey(event)" >
<% } %>
<html:errors />
<html:form method="GET" action="SearchCategory.do" focus="textField">
<html:hidden property="currentPage" value=""/>
<input type="hidden" name="isEditedQuery" id="isEditedQuery" value="<%=iseditedQuery%>"/>
<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%">
  <table summary="" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<script>document.write('<div  id="a_tabbar" style="'+mainTabstyle+'">')</script>
			</td>
            <td>
			<div id='html_1' style="width:100%;">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr> 
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr> 
					<td>&nbsp;</td>
				<td  colspan="2" valign="middle" class="small_txt_grey1"  height="30">
									<span valign="middle"><bean:message key="query.selectCategory.msg"/></span>		
					</td>
				<td>&nbsp;</td>
				</tr>
				<tr> 
					<td>&nbsp;</td>
					<td colspan="2" id='categorySetTd'  bordercolor="#cccccc" width="100%" >
								<div id="categorySet"  style="padding :0px; width :100%; height : 552px;overflow : auto; border-top:1px solid #cccccc; "></div>
							 </td>
					<td>&nbsp;</td>
				</tr>
				</table>
			</div>
			</td> 
          <td>
		   <div id='html_2' style="width:100%;">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr> 
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr> 
					<td>&nbsp;</td>
					<td  width="70%" valign="middle" ><html:text styleId="textSearch" property="textField" styleClass="textfield_undefined" onkeydown="setFocusOnSearchButton(event)" size="30"/></td>
					<td  width="44" valign="middle" align="left" style="padding-left:4px;" >
						<a id="goButton"  href="javascript:retriveSearchedEntities('<%= SearchCategory %>','<%=formName%>','<%=currentPage%>')">
							<img border="0" alt="Go" src="images/advancequery/b_go_blue.gif" align="absmiddle" hspace="3" /></a>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr> 
					<td>&nbsp;</td>
					<td colspan="2" class="small_txt_grey" height="20"><bean:message key="query.chooseCategoryLable"/></td>
					<td>&nbsp;</td>
				</tr>
				<tr id="collapsableHeader" valign="top"   height="20">
						<td>&nbsp;</td>
						<td id="advancedSearchHeaderTd" valign="middle" style="border-top: 1px solid #cccccc; border-left:1px solid #cccccc;padding-bottom:4px;" class="table_header_query" height="29">
							<span class="PageSubTitle" valign="middle"><bean:message key="query.advancedOptions"/></span>		
						</td>
						<td id="imageContainer" valign="middle" align="right" style="border-top: 1px solid #cccccc; border-right:1px solid #cccccc;" background="images/advancequery/bg_content_header.gif" >
							<a id="image" onClick="expand()" style="display:block"><img src="images/advancequery/nolines_plus.gif" id="plusImage"/></a></td>
						<td>&nbsp;</td>
				</tr>
				<tr valign="top" >
						<td>&nbsp;</td>
						<td  colspan="2" >
						<div id="collapsableTable" style="display:none;height:50">
						<table  border="0"   cellspacing="1" cellpadding="2"   width="100%" class="login_box_bg">
								<tr id="class_view">
									<td  class="content_txt"  valign="top" ><html:checkbox  property="classChecked" styleId="classCheckbox" onclick="setIncludeDescriptionValue()" value='<%=Constants.ON%>'>&nbsp; <bean:message key="query.class"/></html:checkbox></td>
								</tr>
								<tr id="attribute_view" >
									<td class="content_txt" valign="top"><html:checkbox  property="attributeChecked" styleId="attributeCheckbox" onclick="setIncludeDescriptionValue()" value='<%=Constants.ON%>' >&nbsp; <bean:message key="query.attribute"/></html:checkbox></td>
								</tr>
								<tr id="radio_view" >
									<td class="standardTextQuery">
										<!-- Bug #5131: Removing the radios until Concept Code search is fixed  -->
										<!-- html:radio property="selected" value="text_radioButton" onclick="radioButtonSelected(this)"/--><!--bean:message key="query.text"/-->
										<!-- html:radio property="selected" value="conceptCode_radioButton" onclick="radioButtonSelected(this)" disabled="true" /--><!--bean:message key="query.conceptCode"/-->
									</td>
								</tr>											
						</table>
						<div>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr valign="top"   height="10" >
						<td >&nbsp;</td>
						<td colspan="2"  valign="middle"  class="small_txt_grey1" >
							Select an object to query
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr valign="top"   height="20" >
						<td >&nbsp;</td>
						<td width="100" colspan="2" id='resultSetTd'>
						<script>document.write('<div  id="resultSet" style="'+searchDivstyle+'">')</script>
						</div>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			</td>
        </tr>
	</table>

	</html:form>
<script>
			tabbar=new dhtmlXTabBar("a_tabbar","top");
            tabbar.setImagePath("dhtml_comp/imgs/");

          //  tabbar.setSkinColors("#FCFBFC","#F4F3EE");
			tabbar.setSkinColors("#FFFFFF","#FFFFFF");
			tabbar.addTab("categoryTab"," <span id='simple_tab'> Simple </span> ","120");
            tabbar.addTab("searchTab","  <span id='Advanced_tab'> Advanced </span>","120px");
			tabbar.setContent("categoryTab","html_1");
            tabbar.setContent("searchTab","html_2");
            tabbar.setTabActive("categoryTab");
			tabbar.setSize(contentWidth,contentHeight,true);
			//tabbar.setSkin("modern");
			tabbar.setOnSelectHandler(my_func);
		//	tabbar.setSkinColors("white","#D9EEFC");
			//tabbar.onClick(my_func);
       
			
</script>

<script>
 /*var resultSetTd = document.getElementById("resultSetTd");
 resultSetTd.style.height="565px";*/
 var dataQuery = document.getElementById("pageOf").value
 var isCategoryQuery=<%=request.getAttribute(Constants.IS_Category)%>
 if(isCategoryQuery!=null)
{ 
	if(isCategoryQuery)
	{
		 defaultcategoryTab(tabbar,<%=iseditedQuery%>);
	}
	else
	{
		defaultsearchTab(tabbar,<%=iseditedQuery%>);		
	}
}
else
{
  defaultcategoryTab(tabbar,false);
}

</script>
</body>
</html>
