<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page language="java" isELIgnored="false"%>
<html>
<head>
<script src="jss/advancequery/queryModule.js"></script>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<script>
   function updateIQuery(chkbx,entityId)
   {
	   chkbx.disabled=true;;
	   var request = newXMLHTTPReq();	
	   var actionURL = "entityId="+entityId;
	   var handlerFunction = getReadyStateHandler(request,responseHandler,true);
       request.onreadystatechange = handlerFunction;
	   var url = "DefineSearchResultsView.do";
	   request.open("POST",url,true);	
	   request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	   request.send(actionURL);	
   }
  function responseHandler(response)
  {
	  tree.deleteChildItems(0);   
	  tree.loadXML(response,deleteFile);
	  tree.closeAllItems(0);
	  tree.openAllItems(0);
	 
  }
  function deleteFile()
  {

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


<html:hidden property="currentPage" value=""/>
	<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="385" bordercolorlight="#000000" id="table11">
					<tr valign="top" class="row" height="1%" width="100">
						<td colspan="2"  valign="top" style="border-top: 1px solid #cccccc; border-left:1px solid #cccccc;border-bottom:0px solid #cccccc;border-right:1px solid #cccccc;" background="images/advancequery/bg_content_header.gif" height="31">
							
							<img src="images/advancequery/t_select_object.gif" hspace="3" />				
						</td>
						</tr>
					<tr valign="top" class="row" width="100" height="385">
						<td height="385" width="100" colspan="2" id='resultSetTd' class="tdWithoutTopBorder" bordercolor="#cccccc">
							<div id="resultSet"  style="border : padding : 4px; width   : 230px; height :380px; overflow : auto; ">
                              <table> 
								<logic:iterate id="entity" name="entityList">        
 								 
								  <% String disabled = null;
								     String checked = null;
								   %>
								 <logic:iterate id="mainEntity"      name="<%=Constants.MAIN_ENTITY_LIST%>">
								 <logic:equal name="entity" property="id" value="${mainEntity.id}">
								     <% disabled = "disabled";
									    checked =  "checked";
									  %>

                                  </logic:equal>
								
								  </logic:iterate> 
								  <tr><td>
												<input type="checkbox"   onclick="updateIQuery(this,${entity.id});" value="${entity.id}" <%=disabled%> <%=checked%>
												><span class="content_txt"> <bean:write name="entity" property="name"/></span> </tr></td>
								</logic:iterate>
                               </table>
							  </div>
					       
		</td>
	</tr>														
</table>
</body>
</html>