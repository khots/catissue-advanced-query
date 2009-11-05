<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html>
<head>
<% String pageOf = (String)request.getAttribute("pageOf"); %>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<script src="jss/advancequery/queryModule.js">
</script>
</head>
<body onload="checkItDefault()" onunload='closeWaitPage()'>
<html:form method="GET" action="OpenDecisionMakingPage.do">
<html:hidden property="noOfResults" value="" />
<input type="hidden" name="isQuery" value="true">
<table border="0" width="100%" cellspacing="0" cellpadding="0"  bordercolor="#000000" id="table2" >		
	<tr>	
		<td width="33%" align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
			<img src="images/advancequery/1_inactive.gif" /> 
		</td>
		<td width="33%" align="center" background="images/advancequery/top_bg_wiz.gif" valign="top">
			<img src="images/advancequery/2_inactive.gif" /> 
		</td>
		<td width="33%" align="center" background="images/advancequery/top_bg_wiz.gif" valign="middle" height="36">
			<img src="images/advancequery/3_active.gif" /> 
		</td>
	</tr>
	<tr height="5%" >
		<td width="23%" height="5%" colspan="3">&nbsp;
	</td>
	</tr>
	<tr id="radio_view" colspan="3">
	  <td colspan="3" align="center" valign="top" >
	    <table><tr><td>
			<table  border="0" width="80%" align="center" cellspacing="0" cellpadding="3"  style="border: 1px solid #cccccc;">	
				<tr>
					<td height="10" colspan="2">
						</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left" class="content_txt">
						<html:errors/>
					</td>
				</tr>
				<tr>
				<td class="formMessagewithoutcolor" >&nbsp;
						</td>
					<td  class="content_txt">
						<bean:message key="query.options.message"/>
					</td>
				</tr>
				
				<tr>
				<td>&nbsp;</td>
					<td class="content_txt">
						<%--html:radio property="options" value="redefineQuery" /><bean:message key="query.options.redefine.query"/--%>
						<input type="radio" name="options"  value="redefineQuery" \>
						<bean:message key="query.options.redefine.query"/>
					</td>
				</tr>
				<tr>
				<td class="formMessagewithoutcolor">&nbsp;
						</td>
					<td class="content_txt">
						<%--html:radio property="options" value="viewLimitedRecords" /><bean:message key="query.options.view.few.results"/><bean:message key="query.options.note"/--%>
						<input type="radio" name="options"  value="viewLimitedRecords" checked="checked"> <bean:message key="query.options.view.few.results"/><br>&nbsp; &nbsp; &nbsp; &nbsp;<bean:message key="query.options.note"/>
					</td>
				</tr>
				<tr>
				<td class="formMessagewithoutcolor">&nbsp;
						</td>
					<td class="content_txt">
						<%--html:radio property="options" value="viewAllRecords" /><bean:message key="query.options.all.records"/--%>
						<input type="radio" name="options" value="viewAllRecords"> <bean:message key="query.options.all.records"/>
					</td>
				</tr>
			<tr>
					<td height="10" colspan="2">
						</td>
				</tr>
			</table></td></tr>
			<tr  valign="center" align="middle" ><td valign="center" height="40">
			<img src="images/advancequery/b_proceed.gif" onclick="proceedClicked()" />
			</td></tr></table>
		</td>
	</tr>		
	 <tr valign="top"><td>&nbsp;</td></tr>
	 <tr  valign="center" align="middle" >
		<logic:equal name="pageOf" value="DefineView">
		   <script> document.forms[0].action="SearchDefineViewResults.do"; </script>
        </logic:equal>
	  </tr>
</table>
</body>
</html:form>
</html>