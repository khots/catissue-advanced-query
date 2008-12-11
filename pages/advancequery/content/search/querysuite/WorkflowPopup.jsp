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
<script language="JavaScript" type="text/javascript" src="jss/advancequery/newReleaseMsg.js"></script>
<script>
function closePopup()
{
	var checkboxArray=document.getElementsByName('chkbox');
	clearSelBoxList(parent.window.document.getElementById("queryId"));
	clearSelBoxList(parent.window.document.getElementById("queryTitle"));
	clearSelBoxList(parent.window.document.getElementById("queryType"));
	
	if(checkboxArray!=null)
	{
			var numOfRows =checkboxArray.length;
		
			for(var count = 1; count <= numOfRows; count++)
			{
				
					var id = 'checkbox_'+count;
				
					if(document.getElementById(id).checked)
					{
						addOption(parent.window.document.getElementById("queryId"),document.getElementById("queryIdControl_"+count).value,count);
						addOption(parent.window.document.getElementById("queryTitle"),""+document.getElementById("queryTitleControl_"+count).value,count);
						addOption(parent.window.document.getElementById("queryType"),""+document.getElementById("queryTypeControl_"+count).value,count);
					}
					
			}
	}
	updateOpenerUI();
	closeModalWindow();

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
            <td class="grey_bold_big"> </td>
          </tr>
        </table></td>
        <td align="right" background="images/advancequery/bg_content_header.gif" ></td>
	
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
            <td valign="top"><a href="javascript:showNextReleaseMsg()" class="blacklink"><bean:message key="workflow.myqueries"/> 
            </a></td>
          </tr>
          <tr>
            <td width="12" valign="middle">&nbsp;</td>
            <td align="left" valign="top"><img src="images/advancequery/ic_folder.gif" alt="explore" width="16" height="16"  align="absmiddle"></td>
            <td valign="top"><a href="javascript:showNextReleaseMsg()" class="blacklink"><bean:message key="workflow.sharedqueries"/></a></td>
          </tr>

        </table></td>
        <td width="1" rowspan="2" valign="middle" class="td_bgcolor_grey" ></td>
        <td align="left" valign="middle">&nbsp;<a href="javascript:showNextReleaseMsg()" class="bluelink">Delete</a>&nbsp;<span class="content_txt">|</span>&nbsp;<a href="javascript:closePopup()" class="bluelink"><bean:message key="workflow.add"/></a></td>
        <td align="right" valign="middle">
          <table border="0" cellspacing="0" cellpadding="4">
            <tr>
              <td class="content_txt"><bean:message key="workflow.show"/></td>
              <td><select name="select" class="textfield_undefined" disabled="true">
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

                <td valign="middle" class="grid_header_text"><bean:message key="workflow.queryTitle"/></td>
                <td width="111" valign="middle" class="grid_header_text"><bean:message key="workflow.querytype"/></td>
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
            <td width="125" align="left" class="content_txt">Show Last:&nbsp;
                <select name="select2" class="textfield_undefined" disabled="true">
                  <option>25</option>
              </select></td>
          <td  align="center" class="content_txt"></td> 
            
            </tr>
        </table></td>
        </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>

