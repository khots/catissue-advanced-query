<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="edu.wustl.query.util.global.AQConstants"%>
<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" isELIgnored="false" %>
<html>
<head>
<script src="jss/advQuery/queryModule.js"></script>
<%
	String formAction = AQConstants.DefineSearchResultsViewJSPAction;
	boolean mac = false;
	Object os = request.getHeader("user-agent");
	if (os != null && os.toString().toLowerCase().indexOf("mac") != -1) {
		mac = true;
	}
	CategorySearchForm categorySearchForm = (CategorySearchForm)request.getAttribute("categorySearchForm");
	//boolean hideTree = categorySearchForm.isHideTree();
%>

<body>
<table cellpadding='0' cellspacing='0' border='0' align='center'
	style="width: 100%; height: 100%; overflow: none">
	<html:form method="GET" action="<%=formAction%>"
		style="margin:0;padding:0;height:100%">
		<tr height="1%">
			<td width="33%" align="center" class="bgWizardImage"><img
				src="images/advQuery/1_inactive.gif" /> <!-- width="118" height="25" /-->
			</td>
			<td width="33%" align="center" class="bgWizardImage"><img
				src="images/advQuery/2_inactive.gif" /> <!-- width="199" height="38" /-->
			</td>
			<td width="33%" align="center" class="bgWizardImage"><img
				src="images/advQuery/3_active.gif" /> <!--  width="139" height="38" /-->
			</td>
		</tr>
		<tr height="98%" valign="top">
			<td valign="top" width="100%" colspan="3" height="100">
			<div id="resultView"
				style="height: 100%; width: 100%; overflow: none">
			<table border="0" height="100%" width="100%">
				<tr height="100%">


				<logic:equal name="hideTreeChkVal" value="false">

					<td colspan="1" valign="top" border="0" width="22%">
					<table border="0" height="100%" width="100%">
						<tr height="99%">
							<td><iframe id="<%=AQConstants.TREE_VIEW_FRAME%>"
								src="<%=AQConstants.QUERY_TREE_VIEW_ACTION%>?pageOf=pageOfQueryResults"
								scrolling="auto" frameborder="0" width="100%" height="100%">
							Your Browser doesn't support IFrames. </iframe></td>
						</tr>
						<tr bgcolor="#DFE9F3" valign="top" height="1%">
							<td colspan="0" valign="top">
							<table border="0" width="100%" cellspacing="0" cellpadding="0"
								background="images/advQuery/bot_bg_wiz.gif" height="24">
								<tr height="1%" valign="bottom">
									<td valign="bottom" align="left" width="10%"><img
										src="images/advQuery/b_save.gif" id="saveResultImgId"
										width="51" hspace="0" vspace="0"
										onclick="saveClientQueryToServer('save')" /></td>
									<td align="right" valign="bottom"><img
										src="images/advQuery/b_prev.gif" id="prevResultImgId"
										width="72" hspace="0" vspace="0"
										onclick="defineSearchResultsView()" /></td>


								</tr>

							</table>
							</td>
						</tr>
					</table>
					</td>
					</logic:equal>
					<td colspan="3" rowspan="2" valign="top" width="85%"><iframe
						name="<%=AQConstants.GRID_DATA_VIEW_FRAME%>"
						src="<%=AQConstants.QUERY_GRID_VIEW_ACTION%>?pageOf=pageOfQueryModule"
						<%=AQConstants.VIEW_TYPE%>=<%=AQConstants.SPREADSHEET_VIEW%>
						" scrolling="auto" frameborder="0" width="100%" height="100%">
					Your Browser doesn't support IFrames. </iframe></td>
				</tr>
			</table>
			</div>
			<script>
				resultViewTag = document.getElementById('resultView')
						resultViewTag.style.height = (document.body.clientHeight - 87)  + 'px';
				</script></td>
		</tr>
	</html:form>
</table>
</body>