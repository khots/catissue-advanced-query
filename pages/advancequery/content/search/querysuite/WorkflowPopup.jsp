<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">

<%-- TagLibs --%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%-- Imports --%>
<%@
	page language="java" contentType="text/html"
	import="edu.wustl.query.util.global.Constants, org.apache.struts.Globals"
%>
<%@ page import="org.apache.struts.action.ActionMessages, edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<script>
function closePopup()
{
	var checkboxArray=document.getElementsByName('chkbox');
	clearSelBoxList(top.opener.document.getElementById("queryId"));
	clearSelBoxList(top.opener.document.getElementById("queryTitle"));
	clearSelBoxList(top.opener.document.getElementById("queryType"));
	
	if(checkboxArray!=null)
	{
			var numOfRows =checkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
				
					var id = 'checkbox_'+count;
				
					if(document.getElementById(id).checked)
					{
						addOption(top.opener.document.getElementById("queryId"),document.getElementById("queryIdControl_"+count).value,count);
						addOption(top.opener.document.getElementById("queryTitle"),""+document.getElementById("queryTitleControl_"+count).value,count);
						addOption(top.opener.document.getElementById("queryType"),""+document.getElementById("queryTypeControl_"+count).value,count);
					}
					
			}
	}
	updateOpenerUI();
	window.close();
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
         elm = top.opener.document.getElementById("btn");
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
	var optn = top.opener.document.createElement("OPTION");
	optn.text=theText;
	optn.value=theValue;
	theSel.options.add(optn);	
}

</script>
<head>
<title>CIDER: Clinical Investigation Data Exploration Repository</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/JavaScript">
<script language="JavaScript" type="text/javascript" src="jss/advancequery/queryModule.js"></script>
<script type="text/javascript" src="jss/advancequery/wz_tooltip.js"></script>
<link href="css/advancequery/workflow.css" rel="stylesheet" type="text/css" />
<!--


function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
<script type="text/javascript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body onLoad="MM_preloadImages('images/advancequery/m_home_act.gif')" onunload='closeWaitPage()'>

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
  height="500";
}
String message = null; 
String popupMessage = (String)request.getAttribute(Constants.POPUP_MESSAGE);
int queryCount = 0;%>
<html:messages id="messageKey" message="true" >
<% message = messageKey;    %>
</html:messages>
<html:form action="SaveWorkflow">
<table width="100%" border="0" cellspacing="0" cellpadding="4">
			<tr >
				<td colspan='5' ><html:errors /></td>
			</tr>
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
      <tr>
        <td width="20%" align="left" valign="middle" background="images/advancequery/bg_content_header.gif"><img src="images/advancequery/t_explore.gif" alt="Explore" width="64" height="26"></td>
        <td width="1" height="28" valign="middle" background="images/advancequery/bg_content_header.gif" class="td_bgcolor_grey" ></td>
        <td height="2" background="images/advancequery/bg_content_header.gif" ><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="images/advancequery/t_myqueries.gif" alt="My Queries " width="99" height="26"></td>
            <td class="grey_bold_big"> (5) </td>
          </tr>
        </table></td>
        <td align="right" background="images/advancequery/bg_content_header.gif" ><A href="javascript: self.close ()"><img src="images/advancequery/close_window.gif" alt="Close Window" width="87" height="28" border="0"></a></td>
	
      </tr>

      <tr>
        <td width="22%" rowspan="2" align="center" valign="top"><table width="99%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="5" colspan="3" valign="middle"></td>
          </tr>
          <tr>
            <td width="12" valign="middle">&nbsp;</td>
            <td colspan="2" valign="bottom" class="blue_title">Queries</td>
          </tr>
          <tr>
            <td width="12" valign="middle">&nbsp;</td>
            <td width="18" align="left" valign="top"><img src="images/advancequery/ic_folder.gif" alt="explore" width="16" height="16"  align="absmiddle"></td>
            <td valign="top"><a href="#" class="blacklink"><b>My Queries  (5)
            </b></a></td>
          </tr>
          <tr>
            <td width="12" valign="middle">&nbsp;</td>
            <td align="left" valign="top"><img src="images/advancequery/ic_folder.gif" alt="explore" width="16" height="16"  align="absmiddle"></td>
            <td valign="top"><a href="#" class="blacklink">Shared Queries  <b>(100)
            </b></a></td>
          </tr>

        </table></td>
        <td width="1" rowspan="2" valign="middle" class="td_bgcolor_grey" ></td>
        <td align="left" valign="middle">&nbsp;<a href="#" class="bluelink">Delete</a>&nbsp;<span class="content_txt">|</span>&nbsp;<a href="javascript:closePopup()" class="bluelink">Add to Workflow</a></td>
        <td align="right" valign="middle">
          <table border="0" cellspacing="0" cellpadding="4">
            <tr>
              <td class="content_txt">Show :</td>
              <td><select name="select" class="textfield_undefined">
                <option>All</option>
                <option>Get Count</option>
                <option>Get Patient Data</option>
              </select></td>
            </tr>
          </table>
    </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="top"><table width="99%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EAEAEA">
          <tr>
            <td><table width="100%" border="0" cellpadding="2" cellspacing="1">
              <tr class="td_bgcolor_grey">
                <td width="10" height="25" valign="middle" ><input type="checkbox" name="checkbox8" value="checkbox">                </td>

                <td valign="middle" class="grid_header_text">Query Title</td>
                <td width="111" valign="middle" class="grid_header_text">Query Type</td>
                </tr>
					<div  id="searchDiv">
							<c:set var="parameterizedQueryCollection" value="${saveQueryForm.parameterizedQueryCollection}" />
							<jsp:useBean id="parameterizedQueryCollection" type="java.util.Collection" />
					
							<c:forEach items="${parameterizedQueryCollection}" var="parameterizedQuery" varStatus="queries">
							<jsp:useBean id="parameterizedQuery" type="edu.wustl.common.querysuite.queryobject.IParameterizedQuery" />
							

									<%String target = "executeQuery('"+parameterizedQuery.getId()+"')"; 
									  String queryId=parameterizedQuery.getId()+"";
									  String title = parameterizedQuery.getName();
									  String newTitle = Utility.getQueryTitle(title);
									  
									  String tooltip = Utility.getTooltip(title);
									  String function = "Tip('"+tooltip+"', WIDTH, 700)";
									  queryCount++;
									%>
									<tr bgcolor="#FFFFFF">
									<td height="25" valign="top">
										<c:set var="checkboxControl">checkbox_<%=queryCount%></c:set>
										<jsp:useBean id="checkboxControl" type="java.lang.String"/>

									<html:checkbox property="chkbox" styleId="<%=checkboxControl%>"/>
									
									<td height="25" valign="top" class="content_txt" >
										<%=newTitle%>
									</td>
									  <td height="25" valign="top" class="content_txt">Get Count</td>
									 
									 </td>
									 	<c:set var="queryTitleControlId">queryTitleControl_<%=queryCount%></c:set>
										<jsp:useBean id="queryTitleControlId" type="java.lang.String"/>
										<html:hidden property="queryTitleControl" styleId="<%=queryTitleControlId%>"
										value="<%=newTitle%>"/>

										<c:set var="queryIdControl">queryIdControl_<%=queryCount%></c:set>
										<jsp:useBean id="queryIdControl" type="java.lang.String"/>
										<html:hidden property="queryIdControl" styleId="<%=queryIdControl%>"
										value="<%=queryId%>"/>

										
										<c:set var="queryTypeControl">queryTypeControl_<%=queryCount%></c:set>
										<jsp:useBean id="queryTypeControl" type="java.lang.String"/>
										<html:hidden property="queryTypeControl" styleId="<%=queryTypeControl%>"
										value="Get Count"/>

								</tr>
							</c:forEach>
					</div>

                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td width="20%" align="center" valign="top">&nbsp;</td>
        <td width="1" valign="middle" class="td_bgcolor_grey" ></td>
        <td height="5" colspan="2" ></td>
      </tr>
      <tr>
        <td height="1" colspan="4" align="right" valign="middle" class="td_bgcolor_grey"></td>
      </tr>
      <tr>
        <td height="30" align="left" valign="middle" class="tr_color_lgrey">&nbsp;</td>
        <td height="30" align="left" valign="middle" class="tr_color_lgrey"></td>
        <td height="30" colspan="2" align="left" valign="middle" class="tr_color_lgrey"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="13">&nbsp;</td>
            <td width="125"><span class="content_txt">Show Last: </span>
                <select name="select2" class="textfield_undefined">
                  <option>25</option>
              </select></td>
            <td  align="center" class="content_txt">Displaying 1-25 of 140 </td>
            <td align="right" valign="middle"><span class="orange_title">1</span><span class="content_txt"> | </span><a href="#" class="bluelink">2</a> <span class="content_txt">|</span> <a href="#" class="bluelink">3</a> <span class="content_txt">|</span> <a href="#" class="bluelink">4</a> <span class="content_txt">|</span> <a href="#" class="bluelink">5</a> <span class="content_txt">|</span> <a href="#" class="bluelink">&gt;&gt;</a><img src="images/advancequery/spacer.gif" width="5" height="5" align="absmiddle"></td>
            </tr>
        </table></td>
        </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>

