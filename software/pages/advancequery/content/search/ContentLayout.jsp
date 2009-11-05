<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title><tiles:getAsString name="title" ignore="true"/></title>
<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
<script src="jss/advancequery/script.js" type="text/javascript"></script>
<script src="jss/advancequery/overlib_mini.js" type="text/javascript"></script>
<script src="jss/advancequery/calender.js" type="text/javascript"></script>

</head>
<body>


<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
<tr height="100%">
	<td width="100%" valign="top">
    	<!-- target of anchor to skip menus -->
        <tiles:insert attribute="content"></tiles:insert>
	</td>
</tr>

</table>
</body>
</html>
