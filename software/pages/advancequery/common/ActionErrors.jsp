<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.apache.struts.action.Action"%>
<%@ page import="org.apache.struts.action.ActionError"%>
<%@ page import="edu.wustl.common.util.global.ApplicationProperties"%>
<%@ page import="org.apache.struts.action.ActionErrors"%>
<%@ page language="java" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null }">
	<table border="0" cellspacing="0" cellpadding="3">
		<tr>
			<td class="messagetexterror" nowrap>
			<html:errors /></td>
		</tr>
	</table>
</c:if>
<html:messages id="messageKey" message="true" header="messages.header"
	footer="messages.footer">
	<table border="0" cellpadding="3" cellspacing="3">
		<tr>
			<td><img src="images/advancequery/uIEnhancementImages/error-green.gif"
				alt="successful messages" width="16" height="16">
			</td>
			<td class="messagetextsuccess"><%=messageKey%></td>
		</tr>
	</table>
</html:messages>
</html>

