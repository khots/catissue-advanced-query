<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" isELIgnored="false"%>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="10" valign="top">&nbsp;</td>
    <td valign="top">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
	      <tr>
		    <td height="28" background="images/bg_content_header.gif">
				<logic:equal name="workflowForm" property="operation" value="edit">
					<img src="images/advancequery/t_edit_workflow.gif" alt="New Workflow" width="110" height="26" hspace="5" vspace="0">
				</logic:equal>
				<logic:equal name="workflowForm" property="operation" value="add">
					<img src="images/advancequery/t_create_workflow.gif" alt="New Workflow" width="130" height="26" hspace="5" vspace="0">
				</logic:equal>					
			</td>
	      </tr>
		  <tr>
			<td align="left" valign="top" class="content_txt" style="padding-left:10px">
				<br/>
				
				 <logic:notEmpty name="workflowForm" property="operation">
				 <span class="content_txt_bold">
					<logic:equal name="workflowForm" property="operation" value="edit">
					<bean:message key="workflow.edit.success"/>
					</logic:equal>
					<logic:equal name="workflowForm" property="operation" value="add">
					<bean:message key="workflow.save.success"/>
					</logic:equal>
				</logic:notEmpty>
				</span>
				<br/>
				<br/>
				<p><bean:message key="message.helpDeskNumber"/></p>
				<br/>
			</td>
		  </tr>
	    </table>
   </td>
  <td width="10" valign="top">&nbsp;</td>
</tr>
<tr>
    <td valign="top">&nbsp;</td>
    <td valign="top">&nbsp;</td>
    <td valign="top">&nbsp;</td>
</tr>
</table>