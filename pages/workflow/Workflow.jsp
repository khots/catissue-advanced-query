<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<head>
Workflow - Create New
</head>
<body>
<html:form action='/WorkflowAction1.do'>
<br> <br> Workflow Name <br>
							<html:text styleClass="black_ar"
								maxlength="255" size="30" styleId="name"
								property="name" /> 
<br> <br>
									<html:submit
										styleClass="blue_ar_b">
										<bean:message key="buttons.submit" />
									</html:submit>
</html:form>
</body>


