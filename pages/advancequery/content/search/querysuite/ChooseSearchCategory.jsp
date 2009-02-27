<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<script src="jss/advancequery/queryModule.js"></script>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<script>
<%
String height = "";
String currentPage = (String)request.getAttribute(Constants.CURRENT_PAGE);
System.out.println("currentPage         "+currentPage);
	String formName = Constants.categorySearchForm ;
	String SearchCategory = Constants.SearchCategory ;
%>
	if(navigator.appName == "Microsoft Internet Explorer")
		{
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
	<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%" bordercolorlight="#000000" id="table11">
		<tr>
			<td valign="top"> 
				<table border="0" width="100%" valign="top" cellpadding="0" cellspacing="0" >
					
					<tr bordercolorlight="#000000" >
						<td>&nbsp;</td>
						<td width="75%" valign="middle" ><html:text property="textField" styleClass="textfield_undefined" onkeydown="setFocusOnSearchButton(event)" size="30"/></td>
						<td width="25%" valign="middle" align="left" style="padding-left:4px;" >
						<a href="javascript:retriveSearchedEntities('<%= SearchCategory %>','<%=formName%>','<%=currentPage%>')">
							<img border="0" alt="Go" src="images/advancequery/b_go_blue.gif" width="44" align="absmiddle" hspace="3" />
						</a>
					    						
						</td>
					</tr>
					<tr >
					    <td>&nbsp;</td>
						<td  align="left" valign="top" colspan="3" class="small_txt_grey"><bean:message key="query.chooseCategoryLable"/></font></td>
					</tr>
					<tr id="collapsableHeader" valign="top"  width="97%" height="20">
						<td valign="top"   ></td>
						<td id="advancedSearchHeaderTd" valign="top" style="border-top: 1px solid #cccccc; border-left:1px solid #cccccc;" background="images/advancequery/bg_content_header.gif" height="29">
							<img src="images/advancequery/t_adv_option.gif" />									
						</td>
						<th id="imageContainer" valign="middle" align="right" style="border-top: 1px solid #cccccc; border-right:1px solid #cccccc;" background="images/advancequery/bg_content_header.gif" >
							<a id="image" onClick="expand()" style="display:block"> <img src="images/advancequery/nolines_plus.gif" /> </a>
						</th>
						<td  >&nbsp;</td>
					</tr>
					<tr valign="top" ><td valign="top" id="td1">&nbsp;</td>
						<td  colspan="2"><table  border="0" class="collapsableTable" style="display:none" width="100%" cellspacing="1" cellpadding="2"  id="collapsableTable" >
								<tr id="class_view">
									<td colspan="2" class="content_txt" valign="top" ><html:checkbox  property="classChecked" onclick="setIncludeDescriptionValue()" value='<%=Constants.ON%>'>&nbsp; <bean:message key="query.class"/></html:checkbox></td>
								</tr>
								<tr id="attribute_view" >
									<td class="content_txt" valign="top"><html:checkbox  property="attributeChecked" onclick="setIncludeDescriptionValue()" value='<%=Constants.ON%>' >&nbsp; <bean:message key="query.attribute"/></html:checkbox></td>
								</tr>
								<tr id="permissible_view" >
									<td class="content_txt" valign="top"><html:checkbox property="permissibleValuesChecked" disabled="true" onclick="permissibleValuesSelected(this)" value='<%=Constants.ON%>'>&nbsp; <bean:message key="query.permissibleValues"/></html:checkbox></td>
								</tr>
								<tr id="description_view" >
									<td class="content_txt" valign="top"><html:checkbox  property="includeDescriptionChecked" value='<%=Constants.ON%>'>&nbsp; <bean:message key="query.includeDescription"/> </html:checkbox></td>
								</tr>
								<tr id="radio_view" >
									<td class="standardTextQuery">
										<!-- Bug #5131: Removing the radios until Concept Code search is fixed  -->
										<!-- html:radio property="selected" value="text_radioButton" onclick="radioButtonSelected(this)"/--><!--bean:message key="query.text"/-->
										<!-- html:radio property="selected" value="conceptCode_radioButton" onclick="radioButtonSelected(this)" disabled="true" /--><!--bean:message key="query.conceptCode"/-->
									</td>
								</tr>											
							</table>
						</td>
						<td >&nbsp;</td>
					</tr>
					<tr>
					<td colspan="4" height="5"></td>
					<tr valign="top" class="row" width="98%" >
						<td >&nbsp;</td>
						<td colspan="2"  valign="top" style="border-top: 1px solid #cccccc; border-left:1px solid #cccccc;border-bottom:0px solid #cccccc;border-right:1px solid #cccccc;" background="images/advancequery/bg_content_header.gif" height="30">
							
							<img src="images/advancequery/t_search_results.gif" />		
						</td>
						<td  height="1%">&nbsp;</td>
					</tr>
					<tr valign="top" class="row" width="100" >
						<td width="100" >&nbsp;</td>
						<td height="100%" width="100" colspan="2" id='resultSetTd' class="tdWithoutTopBorder" bordercolor="#cccccc">
							<div id="resultSet"  style="border : padding : 4px; width : 230px; height : 550px; overflow : auto; "></div>
						</td>
						<td >&nbsp;</td>
					</tr>
					
			</table>
		</td>
	</tr>														
</table>
</html:form>
</body>
</html>