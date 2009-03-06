<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CIDER: Clinical Investigation Data Exploration Repository</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/JavaScript">
<!--

function submitForm()
{
	document.forms[0].submit();
	parent.document.getElementById("exportMessageDiv").style.display="block";
	parent.confidentialitywindow.hide();
	
}

function closeWindow()
{
	parent.confidentialitywindow.hide();
}

function isAgreeForTerms(element)
{
	if(element.checked)
	{
		document.getElementById("isAgree").style.display="block";
		document.getElementById("isNotAgree").style.display="none";
	}
	else
	{
		document.getElementById("isNotAgree").style.display="block";
		document.getElementById("isAgree").style.display="none";
	}
}


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
<link href="css/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body onLoad="MM_preloadImages('images/m_home_act.gif')">
<form name = "exportDataForm" id = "exportDataForm" action = "CiderExportData.do">
<script type="text/javascript" src="wz_tooltip.js"></script>
<div id="welcome_links"></div>

<div class="wrapper">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td valign="top">&nbsp;</td>
      <td align="right" valign="middle">&nbsp;</td>
      <td valign="top">&nbsp;</td>
    </tr>
    <tr>
      <td width="10" valign="top">&nbsp;</td>
      <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
          <tr>
            <td align="center" valign="top"><table width="98%" border="0" cellspacing="0" cellpadding="3">

                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td align="center" valign="middle" nowrap class="dynamic_table_bg">
				  <table  border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td><img src="images/ic_fatal_error.gif" alt="Application has encountered a fatal error. Please contact administrator" width="48" height="43" align="absmiddle"></td>
    <td class="grey_bold_big">You are about to download the patient data. <br>
System will take time in exporting this data to a file. <br> 
Once the file is ready to download, you will be notified via an email.
</td>
  </tr>
</table>

				  </td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td  align="left" valign="top" class="grey_bold_big">Terms and Conditions</td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td style="padding-left:10px;" align="left" valign="top"  class="content_txt" >
				  <div class="conditions">		
				  <ol>
						Data Download Terms and Conditions: 
						The data set you are about to download may contain confidential patient information from CIDER. By s and downloading the data from CIDER, you agree to all of the following:
						<li>	Not transfer CIDER data to any third party other than staff for whom you are directly responsible unless a data use agreement is in place.</li>
						<li>	To certify the protection of any downloaded CIDER data file as well as any data files derived from the downloaded file.</li>
						 <li>	To promptly report to appropriate authority, if data is compromised or lost.</li>
				  </ol>	
				  </div>				  </td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td align="left" valign="top" class="content_txt" ><input type="checkbox" id= "checkbox" name="checkbox" value="checkbox" onclick = "isAgreeForTerms(this)">&nbsp;I agree with the above mentioned terms and conditions.</td>
                </tr>
                <tr>
                  <td height="10px"></td>
                </tr>
                <tr>
                  <td align="left" valign="middle" >
					<table cellspacing = "0" cellpadding= "0" colspan = "2">
					<tr>
						<td width = "54">
						<div id = "isNotAgree" style = "display:block" >
						  <img src="images/b_ok_inactive.gif" alt="OK" width="44" height="23">
						  </div>
						  <div id = "isAgree" style = "display:none">
								<a href="javascript:submitForm()"><img border = "0" src="images/b_ok.gif" alt="OK" width="44" height="23"></a>
						  </div>
						  </td>
						  <td>
						   <a href="javascript:closeWindow()"><img border = "0" src="images/b_cancel.gif" alt="Cancel" width="66" height="23"></a>
						  </td>
					</tr>
					</table>
					</td>

                </tr>
               <tr>
                  <td height="10px"></td>
                </tr>
            </table></td>
          </tr>
      </table></td>
      <td width="10" valign="top">&nbsp;</td>
    </tr>
    <tr>
      <td valign="top">&nbsp;</td>
      <td valign="top">&nbsp;</td>
      <td valign="top">&nbsp;</td>
    </tr>
  </table>
</div>
</form>
</body>
</html>

