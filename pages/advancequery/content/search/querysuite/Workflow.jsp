<html>
<head>
<title>CIDER: Clinical Investigation Data Exploration Repository</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


<link href="css/advancequery/workflow.css" rel="stylesheet" type="text/css" />
<script src="jss/advancequery/workflows.js"></script>	
<script type="text/JavaScript">
<!--


function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function showPopUp(pageOf){
	//window.open("QueryAction.do?pageOf="+pageOf+"&queryId=queryId&queryTitle=queryTitle&queryType=queryType","left=400,top=400,width=600,height=600,modal=yes");

	window.open("QueryAction.do?pageOf="+pageOf+'&queryId=queryId&queryTitle=queryTitle&queryType=queryType','','height=365,width=530,center=1,scrollbars=1,resizable=0,modal=yes');
}

function updateUI()
{
	addQuery();
}

function unionQueries()
{
		//alert('in update');
	addCQToList("Union");
}

function intersectQueries()
{
	addCQToList("Intersection");
}

function minusQueries()
{
	addCQToList("minus");
}

function addCQToList(operation)
{
//	alert('in add cq');
	var queryIdsToAdd='';
	var queryControls=document.getElementsByName("selectedqueryId");
//	alert('queryControls='+queryControls.length);
	var queryCount=0;
	if(queryControls!=null && queryControls!=undefined)
	{
		queryCount=queryControls.length;
	}

	for(var counter=0;counter<queryCount;counter++)
	{
		var checkboxControl=document.getElementById("checkbox_"+(counter));
		if(checkboxControl!=null && checkboxControl!=undefined && checkboxControl.checked==true)
		{
			queryIdsToAdd=queryIdsToAdd+","+counter;
		}
	}
//	alert('queryIdsToAdd='+queryIdsToAdd);
	createCQ(queryIdsToAdd,operation,queryCount);		
}

function createCQ(queryIdsToAdd,operation,queryCount)
{
	var queryIds=queryIdsToAdd.split(",");
	var operandsTdContent="";
	var cqTitle="";
	var cqQueryId="";
	for(var counter=0;counter<queryIds.length;counter++)
	{
		if(queryIds[counter]!=null && queryIds[counter]!='')
		{
			if( cqTitle=='')
			{
				cqTitle=document.getElementById("displayQueryTitle_"+queryIds[counter]).value;
			}
			else
			{
				cqTitle=cqTitle+" "+operation+" "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value;
			}
			if(cqQueryId=='')
			{
				cqQueryId="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
			}
			else
			{
				cqQueryId=cqQueryId+"_"+operation+"_("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=operandsTdContent+"_("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
			}
		}
	}
	var cqType="Composite Query";
	var cqId="";
	
	var rowContents=new Array(5);
	rowContents[0]=createCheckBox("chkbox","checkbox_"+queryCount,'');
	rowContents[1]=createTextElement(cqTitle);
	rowContents[2]=createTextElement(cqType);
	rowContents[3]=createTextElement(operandsTdContent);
	rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+queryCount,operandsTdContent);

	/*alert('rowContents[0]='+rowContents[0]);
	alert('rowContents[1]='+rowContents[1]);
	alert('rowContents[2]='+rowContents[2]);
	alert('rowContents[3]='+rowContents[3]);
	alert('rowContents[4]='+rowContents[4]);*/
	
	var operatorsTdContent='Union';
//alert('before call');
	//create a table containing tbody with id "table1"
	addRowToTable("table1",rowContents,operandsTdContent,operatorsTdContent);	
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

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body onLoad="MM_preloadImages('images/m_home_act.gif')">
<script type="text/javascript" src="wz_tooltip.js"></script>

<form name="form1" method="post" action="">

<div id="welcome_links">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="375" align="left">&nbsp;</td>
    <td align="left" nowrap class="small_txt_black">Welcome &lt;First Name&gt; &lt;Last Name&gt; </td>
    <td align="right" nowrap><a href="#" class="greylink">Report a Problem</a> <span class="content_txt">|</span> <a href="#" class="greylink">Contact Us</a> <span class="content_txt">|</span> <a href="#" class="greylink">Help</a>&nbsp;<span class="content_txt">|</span>&nbsp;<a href="#" class="greylink">Logout</a>&nbsp;</td>
  </tr>
</table>
</div>

<div class="wrapper">
  <div id="header">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/bg_header.gif">
      <tr>
        <td><img src="images/advancequery/s_logo.gif" alt="CIDER: Clinical Investigation Data Exploration Repository" width="375" height="100"></td>
        <td width="325" align="right"><img src="images/advancequery/header_collage.jpg" width="325" height="100"></td>
      </tr>
    </table>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td valign="top">&nbsp;</td>
      <td height="29" align="right" valign="middle"><a href="#" class="bluelink">User Accounts</a>&nbsp;|&nbsp;<a href="#"><img src="images/advancequery/b_new_search.gif" alt="New Search" width="90" height="23" hspace="0" vspace="3" border="0" align="absmiddle" /></a></td>
      <td valign="top">&nbsp;</td>
    </tr>
    <tr>
      <td width="10" valign="top">&nbsp;</td>
      <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">
          <tr>
            <td height="28" background="images/advancequery/bg_content_header.gif"><img src="images/advancequery/t_new_workflow.gif" alt="New Workflow" width="124" height="26" hspace="5" vspace="0"></td>
          </tr>
          <tr>
            <td align="center" valign="top"><table width="98%" border="0" cellspacing="0" cellpadding="0">

                <tr>
                  <td align="left" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="5"></td>
                    </tr>
                    <tr>
                      <td height="25"><p><span class="red_star">*</span> <span class="small_txt_grey">Denotes mandatory fields</span></p>                        </td>
                    </tr>
                    <tr>
                      <td height="25">&nbsp;
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><span class="content_txt_bold">Workflow Name</span><span class="red_star">*</span>:<span class="content_txt">
                            <input name="textfield222" type="text" class="textfield_undefined" value="Lung Cancer Queries " size="80" >
                            &nbsp;&nbsp;</span></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="10">&nbsp;</td>
                    </tr>
                  </table>

                  </td>
                </tr>
                <tr>
                  <td align="left" valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  class="login_box_bg">

                        <tr>
                          <td colspan="4" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="4">
                            <tr class="td_subtitle">
                              <td height="25" class="blue_title">Queries</td>
                            </tr>

                          </table></td>
                        </tr>
                      <tr>
                        <td width="17%" rowspan="2" align="center" valign="top" class="tr_color_lgrey"><table width="99%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="5" colspan="3" valign="middle"></td>
                          </tr>
                          <tr>
                            <td valign="middle">&nbsp;</td>
                            <td colspan="2" valign="bottom" class="blue_title">Add Existing Query</td>
                          </tr>
                          <tr>
                            <td valign="middle">&nbsp;</td>
                            <td valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
                            <td valign="middle"><a href="#" class="blacklink" onClick="showPopUp('myQueriesForWorkFlow')"><b>From My Queries</b></a></td>
                          </tr>
                          <tr>
                            <td valign="middle">&nbsp;</td>
                            <td valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
                            <td valign="middle"><a href="#" class="blacklink" onClick="showPopUp('publicQueryForWorkFlow')">From Shared Queries</a></td>
                          </tr>
                          
                          <tr>
                            <td valign="middle">&nbsp;</td>
                            <td colspan="2" valign="bottom" class="blue_title">&nbsp;</td>
                          </tr>
                          <tr>
                            <td width="12" valign="middle">&nbsp;</td>
                            <td colspan="2" valign="bottom" class="blue_title">Add New Query</td>
                          </tr>
                          <tr>
                            <td width="12" valign="middle">&nbsp;</td>
                            <td width="10" valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
                            <td valign="middle"><a href="#" class="blacklink">Get Count</a></td>
                          </tr>
                          <tr>
                            <td width="12" valign="middle">&nbsp;</td>
                            <td width="10" valign="middle"><img src="images/advancequery/ic_arrow_small.gif" alt=""  align="absmiddle"></td>
                            <td valign="middle"><a href="#" class="blacklink">Get Patient Data</a></td>
                          </tr>
                          <tr>
                            <td valign="middle">&nbsp;</td>
                            <td colspan="2" valign="bottom" class="blue_title">&nbsp;</td>
                          </tr>
                        </table></td>
                        <td width="1" valign="middle" class="td_bgcolor_grey" ></td>
                        <td height="33" ><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="2" ></td>
                              <td width="2" ></td>
                              <td align="left"><a href="javascript:unionQueries()"><img src="images/advancequery/b_union-copy.gif" alt="Union" width="60" height="23" border="0"></a></td>
                              <td width="116" align="center"><a href="javascript:intersectQueries()"><img src="images/advancequery/b_intersection.gif" alt="Intersection" width="96" height="23" border="0"></a></td>
                              <td align="right"><a href="javascript:minusQueries()"><img src="images/advancequery/b_minus.gif" alt="Minus" width="63" height="23" border="0"></a></td>
                            </tr>
                        </table></td>
                        <td align="right"><table border="0" cellpadding="4" cellspacing="0">
                          <tr>
                            <td align="right"><span class="content_txt_bold">Select Project:
                              <select name="select2" class="texttype">
                                    <option>Lung Cancer Project</option>
                                  </select>
                            </span></td>
                            <td width="167" align="right" valign="middle" ><a href="#" class="bluelink">Execute Get Count Queries</a>&nbsp;</td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td width="1" valign="middle" class="td_bgcolor_grey" ></td>
                        <td colspan="2" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          </table>
                            <table width="99%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EAEAEA">
								<select name="queryId" id="queryId" style="display:none">
								</select>
								<select name="queryTitle" id="queryTitle" style="display:none">
								</select>
								<select name="queryType" id="queryType" style="display:none">
								</select>
								<input type="button" name="btn" id="btn" onclick="updateUI()" style="display:none">
                              <tr>
                                <td><table width="100%" border="0" cellpadding="2" cellspacing="1">
                                  <tr class="td_bgcolor_grey">
                                    <td width="10" height="25" valign="middle" ><input type="checkbox" name="checkbox8" value="checkbox">                                    </td>

                                    <td valign="middle" class="grid_header_text">Query Title</td>
                                    <td width="111" valign="middle" class="grid_header_text">Type</td>
                                    <td width="100" valign="middle" class="grid_header_text"> Select Object </td>
                                    <td width="75" valign="middle" class="grid_header_text">Result Count </td>
                                    <td width="90" valign="middle" class="grid_header_text">&nbsp;</td>
                                    <td width="55" valign="middle" class="grid_header_text">Re-order</td>
                                  </tr>
											<tbody id="table1">
		
											</tbody>
									
       

                                  

                                  </tr>

                                 

                                  </tr>

                                </table>

                                </td>
                              </tr>
                          </table></td>
                      </tr>
                      <tr>
                        <td width="17%"  class="tr_color_lgrey">&nbsp;</td>
                        <td width="1" valign="middle" class="td_bgcolor_grey" ></td>
                        <td height="5" colspan="2" >&nbsp;</td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td height="40" align="left" valign="middle" ><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="left"><a href="SaveWorkflow.do?operation=add"><img src="images/advancequery/b_save.gif" alt="Save" width="55" height="23"></a></td>
                      <td width="76" align="right"><img src="images/advancequery/b_cancel.gif" alt="Cancel" width="66" height="23"></td>
                    </tr>
                  </table></td>
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
  <div class="push"></div>
</div>
<div class="footer">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/bg_footer.gif">
    <tr>
      <td><img src="images/advancequery/logo_washu.gif" alt="Washington University in St. Louis" width="294" height="43"></td>
      <td align="right"><img src="images/advancequery/logo_bjc.gif" alt="BJC HealthCare" width="153" height="43"></td>
    </tr>
  </table>
</div>

</form>

</body>
</html>

