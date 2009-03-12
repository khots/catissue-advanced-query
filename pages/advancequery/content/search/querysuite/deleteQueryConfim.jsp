<%@ page import="java.util.*"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<link rel="stylesheet" type="text/css" href="css/advancequery/inside.css" />
<script src="jss/ajax.js"></script>	
<script type="text/JavaScript">
function confirmDelete()
{
	var index=document.getElementById("index").value;
	parent.window.deleteQuery(index);
	parent.pvwindow.hide();
}
function notdelete()
{
	parent.pvwindow.hide();
}
</script>
<html>
<html:hidden property="index" styleId="index" value="${requestScope.index}"/>
<body>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align = "left">
  <tr align = "center">
    <td style = "padding-left:10px;padding-right:10px" align="center" valign="middle"><table width="100%" height="90" border="0" align="center" cellpadding="0" cellspacing="0" class="dynamic_table_bg">
      <tr>
        <td height="23" align="center" class="content_txt">Are you sure you want to delete this query?</td>
      </tr>
      <tr>
        <td height="35" align="center"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width = "60"><a href="javascript:confirmDelete()"><img src="images/advancequery/b_yes.gif" alt="Yes" width="50" height="23" border="0" /></a></td>
            <td><a href="javascript:notdelete()"><img src="images/advancequery/b_no.gif" alt="No" width="43" height="23" border="0" /></a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</body>
</html>