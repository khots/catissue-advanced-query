<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<script src="jss/advancequery/queryModule.js"></script>
<script  src="dhtml_comp/js/dhtmlXCommon.js"></script>
<script  src="dhtml_comp/js/dhtmlXGrid.js"></script>		
<script  src="dhtml_comp/js/dhtmlXGridCell.js"></script>	
<script  src="dhtml_comp/js/dhtmlXGrid_mcol.js"></script>
<script  src="dhtml_comp/js/dhtmlwindow.js"></script>	
<script  src="dhtml_comp/js/modal.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css" />

<script>
function openPopupWindow()
{
      confidentialitywindow=dhtmlmodal.open('CIDER', 'iframe', 'Forward.do','CIDER Confidentiality Terms & Conditions', 'width=700px,height=430px,center=1,resize=0,scrolling=1')
   	 
}
</script>
<%
String formAction = Constants.DefineSearchResultsViewJSPAction;
           boolean mac = false;
	        Object os = request.getHeader("user-agent");
			if(os!=null && os.toString().toLowerCase().indexOf("mac")!=-1)
			{
			    mac = true;
			}
	String height = "480";		
	if(mac)
	{
	  height="480";
    }
%>

<table border="0" width="100%" cellspacing="0" cellpadding="0" style="height:85%;">
<tr >
<td style="padding:0 5px 10px 5px;" valign="top">

<table border="0" width="100%" cellspacing="0" cellpadding="0"  id="table1"  class="login_box_bg" height="100%">
	<tr class="table_header_query" >
					<td colspan='3' height="26">
					<img border='0' src="images/advancequery/t_gpd_view_results.gif">
					</td>
				</tr>
<html:form method="GET" action="<%=formAction%>">
   <input type="hidden" name="isWorkflow" id="isWorkflow" value="true">
    <input type="hidden" name="currentPage" id="currentPage" value="resultsView">
	 <input type="hidden" name="isQuery" id="isQuery" value="true">
		<tr>
		<!--<tr>

		<td  height="10"	></td>
		</tr>-->
		<!--<tr >	
			<!--<td width="33%" align="center" valign="top" background="images/advancequery/top_bg_wiz.gif">
				<img src="images/advancequery/1_inactive.gif" /> <!-- width="118" height="25" /-->
			<!--</td>
			<td width="33%" align="center" valign="top" background="images/advancequery/top_bg_wiz.gif">
				<img src="images/advancequery/2_inactive.gif" /> <!-- width="199" height="38" /-->
			<!--</td>
			<td width="33%" align="center" valign="top" height="29" background="images/advancequery/top_bg_wiz.gif">
				<img src="images/advancequery/3_active.gif" /> <!--  width="139" height="38" /-->
			<!--</td>-->
		<!--<td>	
		<table border="0" cellpadding="0" cellspacing="0" width="100%" background="images/advancequery/top_bg_wiz.gif" height="36" >
		<tr>	<td width="33%" align="center" valign="top"    >
				<img src="images/advancequery/define_filters_inactive.gif"/> <!-- width="118" height="25" /-->
			<!--</td>
			<td width="33%" align="center" valign="top">
				<img src="images/advancequery/2_inactive.gif" /> <!-- width="199" height="38" /-->
			<!--</td>
			<td width="33%" align="center"  valign="middle" >
				<img src="images/advancequery/3_active.gif" /> <!--  width="139" height="38" /-->
			<!--</td></tr>
			</table>
		</td>
		</tr>-->
<tr >
		<td colspan = "2"valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >
	<tr>
	<td class = "info_msg"  colspan="2" valign="middle">
		<div id = "exportMessageDiv" style = "display:none;padding-left:10px;padding-bottom:5px;padding-top:5px" >
			Request for data download has been submitted.	
		</div>
			</td>
</tr>
<tr>
<td style = "padding-right:10px;" width = "25%">&nbsp;</td><td align="left" valign="middle" style="padding-left:5px;" height="25"><a href="javascript:openPopupWindow()" class="bluelink"><img  alt="Export" src="images/advancequery/ic_excel.gif" border="0" hspace="3" align="absmiddle" style="padding-right:5px;"/>Export</a></td>
</tr>
</table>
</td></tr>
<tr  valign="top" height="90%">
<td>
<table border="0" cellspacing="0" cellpadding="0" height="98%" width="100%" bordercolor="red" id="table1">
	<tr height="100%" width="100%">
	<td width="5px">&nbsp;</td>
		<td width="25%" valign="top" style="padding:0px 0px 0px 5px; border: 1px solid #cccccc;" >
			<iframe id="<%=Constants.TREE_VIEW_FRAME%>" src="<%=Constants.QUERY_TREE_VIEW_ACTION%>?pageOf=pageOfQueryResults" scrolling="auto" frameborder="0" width="100%" height="100%" >
				Your Browser doesn't support IFrames.
			</iframe>
		</td>
		<td width="10px">&nbsp;</td>
		<td width="75%" valign="top" height="100%"  style="padding:0px 0px 0px 5px; border: 1px solid #cccccc;" >
			<iframe name="<%=Constants.GRID_DATA_VIEW_FRAME%>" src="" <%=Constants.VIEW_TYPE%>=<%=Constants.SPREADSHEET_VIEW%>" scrolling="auto" frameborder="0" width="100%" height="100%">
				Your Browser doesn't support IFrames.
			</iframe>
		</td>
		<td  style="width:10px;*width:1px;">&nbsp;</td>
	</tr>
</table>
</td>
</tr>
<tr>
					
					<td valign="middle" style="padding-bottom:9px;">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr valign="middle">
					 <td width="50%" align="left" colspan="2">
					  <table border="0" cellspacing="0" cellpadding="0"  >
						<tr>
							<td style="padding-left:5px;" valign="top"><a href="javascript:showWorkFlowWizard();"><img  alt="Back to Workflow" src="images/advancequery/b_back_to_workflow.gif" border="0" /></a></td>
						</tr>
					 </table>
					</td>
							
					<td width="50%" align="right">
					<!-- <table border="0" cellspacing="0" cellpadding="0">
					  <tr>
						
						<td valign="top" align="right" style="padding-right:5px; " ><a href="javascript:previousFromDefineResults('ViewResults');"><img alt= "<< Redefine Filters" src="images/advancequery/b_redefine_filter.gif" border="0"  
		     /></a></td>
						<td valign="top" align="right" style="padding-right:5px; "><a href="javascript:redefineResultsView();"><img alt="<< Redefine Results View" src="images/advancequery/b_redefine_results_view.gif" border="0" /></a></td>
						</tr>
					</table> -->
					 </td>
					 <td></td>
					</tr>
				</table>   
				</td>
					</tr>
					
</table>
</td>
</tr>
</html:form>
</table>
