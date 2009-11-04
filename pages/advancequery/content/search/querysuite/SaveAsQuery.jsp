<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/advancequery/catissue_suite.css" />
<link href="<%=request.getContextPath()%>/css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
<script type="text/javascript" src="<%=request.getContextPath()%>/jss/jquery-1.3.2.js"></script>
<script>
jQuery().ready(function(){
	
	
	var title=unescape(parent.document.getElementById('saveAsquerytitle').value);
	<%
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	Date date=new Date();
	String stringDate=dateFormat.format(date);
	%>
	document.getElementById("queryTitle").value=title+"_"+'<%=stringDate%>';

	});
function saveAs()
{
	var  remaneAs=document.getElementById('queryTitle').value;
	var  oldName=unescape(parent.document.getElementById('saveAsquerytitle'));
	var oldId=parent.document.getElementById("saveAsqueryId").value;

			$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/SaveAsQuery.do?renameAs="+remaneAs+"&oldName="+oldName+"&oldId="+oldId,
			dataType: 'json',
			success: function(josnresult){
				 document.getElementById("errormessage").innerHTML="";
				 if(josnresult.errormessage!=null)
					 {
						   document.getElementById("errormessage").innerHTML=josnresult.errormessage;
					 }
					 else
					{
						//document.getElementById("errormessage").innerHTML=josnresult.querySavedMsg;
						clearSelBoxList(parent.window.document.getElementById("queryId"));
						clearSelBoxList(parent.window.document.getElementById("queryTitle"));
						clearSelBoxList(parent.window.document.getElementById("queryType"));
						addOption(parent.window.document.getElementById("queryId"),""+josnresult.queryId,josnresult.queryId);
						addOption(parent.window.document.getElementById("queryTitle"),josnresult.renameAs,josnresult.renameAs);
						addOption(parent.window.document.getElementById("queryType"),"Count","Count");
						parent.window.addQuery();
						cancel();
					}


			}
			
			});
}
function cancel()
{
	parent.window.saveAsWindow.hide();
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

function addOption(theSel, theText, theValue)
{
	var optn = parent.window.document.createElement("OPTION");
	optn.text=theText;
	optn.value=theValue;
	theSel.options.add(optn);	
}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save Query As</title>
</head>
<body>

	<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
		<tr><td><table width="100%"  cellspacing="0" cellpadding="0" align="center"  >
			<tr valign="top">
				<td class="messagetexterror">
					<div id="errormessage">&nbsp;
					</div>
					
				</td>
			</tr>
		</table></td></tr>
		<tr  valign="top"><td colspan="2" style="padding-left:10px;padding-right:10px">
			<span class="content_txt_bold"><bean:message key="getcountquery.name"/></span><span class="red_star">*</span>:<span  class="content_txt">
			<input type="text" size="60" class="textfield_undefined" id="queryTitle" name="queryTitle" /></span>
		</td></tr>
	
	<tr width="100%"  >
				<td valign="bottom"  width="100%" colspan="2">
					<table   cellpadding="0" cellspacing="0"  width="100%"  valign="bottom"  >
						<tr>
							<td  width="100%" class="leftpadding">
								<table border="0" cellpadding="0" cellspacing="0"  width="100%"   valign="bottom">
									
									<tr >
										<td valign="bottom"  width="100%"><a href="javascript:saveAs()">
											<img id="saveAsCopyImg" src="<%=request.getContextPath()%>/images/advancequery/b_ok.gif" alt="Save" border="0"></a>
											<a  href="javascript:cancel()"><img id="cancelCopyImg" alt="Cancel" src="<%=request.getContextPath()%>/images/advancequery/b_cancel.gif"  border="0"			></a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>			


	</table>

</body>
</html> 
