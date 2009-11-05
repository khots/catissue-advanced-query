<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/nlevelcombo.tld" prefix="ncombo"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="edu.wustl.common.beans.RecentQueriesBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page language="java" isELIgnored="false"%>
<%@page import="edu.wustl.query.util.global.Constants"%>
<%-- Imports --%>
<link href="css/cider.css" rel="stylesheet" type="text/css" />

<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXGrid.css"/>
<script  src="dhtml_comp/js/dhtmlXCommon.js"></script>
<script  src="dhtml_comp/js/dhtmlXGrid.js"></script>
<script  src="dhtml_comp/js/dhtmlXGridCell.js"></script>
<script  src="dhtml_comp/js/dhtmlXGrid_mcol.js"></script>
<script  src="dhtml_comp/js/dhtmlxgrid_filter.js"></script>
<script type="text/javascript" src="jss/advancequery/ajax.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlwindow.js"></script>
<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/modal.js"></script>
<script  src="dhtml_comp/js/dhtmlXGrid_excell_link.js"></script>
<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
<script language="JavaScript" type="text/javascript" src="jss/advancequery/search.js"></script>
<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="jss/jquery-1.3.2.js"></script>

<!-- for tool tip -->
	<link href="css/advancequery/jquery.cluetip.css" rel="stylesheet" type="text/css" />
	<script src="jss/advancequery/jquery.cluetip.js" type="text/javascript"></script>
	<script src="jss/advancequery/jquery.hoverIntent.js" type="text/javascript"></script>



<script>


function showDiv(elementid)
{
	if(elementid=='constrDiv')
	{
		  var x = document.getElementById("constrImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);
		  
		  SlideDownRun("constrDiv","dataTable");


	}
	else
	{
		  var x = document.getElementById("paramImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);
		 
		 SlideDownRun("paramDiv","countTable");



	}	
	return;

} 
function hideDiv(elementid)
{
	if(elementid=='constrDiv')
		{
		 var x = document.getElementById("constrImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_close.png";
		  x.setAttribute("src", v);
	
	 	document.getElementById("constrDiv").style.display="none";
	 return;
	 
		}
		else
		{
			var x = document.getElementById("paramImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_close.png";
		  x.setAttribute("src", v);
		document.getElementById("paramDiv").style.display="none";

		return;
		}
				

}
function hideAllDivs()
{
	var constrDiv=document.getElementById("constrDiv");
	if(constrDiv!=null&&constrDiv!=undefined)
	{
		constrDiv.style.display="none";
	}

}


function SlideDownRun(obj,tableObj)
{
   slider = document.getElementById(obj);
   slider.style.display="block";

}
	//$("document").ready(init_grid);
	var myData =${requestScope.msgBoardItemList};
	var columns =${requestScope.columns};
	var colWidth =${requestScope.colWidth};
	var isWidthInPercent=${requestScope.isWidthInPercent};
	var colTypes =${requestScope.colTypes};
	var colDataTypes =${requestScope.colDataTypes};
function callParentWindow(i)
{
	var cl = mygrid.cells(i,6);
	var queryExecutionId = cl.getValue();
	var c2 = mygrid.cells(i,8);
	var queryId = c2.getValue();
	var queryTitle = mygrid.cells(i,1).getValue();
	//window.parent.showParametrizeQueryPopup(queryTitle,queryId,queryExecutionId);


}
function cancel(i)
{
		var queryexecutionID=mygrid.cells(i,6).getValue();
		var hasPrivilege=mygrid.cells(i,7).getValue();
		cancelExecution(queryexecutionID,i,hasPrivilege);
}

function init_grid()
{
	var funcName = "rowClick";

	mygrid = new dhtmlXGridObject('gridbox');
	mygrid.setImagePath("dhtml_comp/imgs/");
	mygrid.setStyle("font-family: Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold;color: #000000;background-color: #E2E2E2; border-left-width: 1px;border-left-color: #CCCCCC; border-top-width: 1px;border-top-color: #CCCCCC;border-bottom-color: #CCCCCC; border-bottom-width: 1px; border-right-width: 1px;border-right-color: #CCCCCC; text-align:left;padding-left:5px;padding-top:1px;padding-bottom:1px;");
	mygrid.setHeader(columns);
	mygrid.enableResizing(columns);
	mygrid.setEditable("false");
	mygrid.enableAutoHeigth(true);
	mygrid.enableAlterCss("even","uneven");
    mygrid.enableRowsHover(true,'grid_hover')
	// Bug Fixed : - 12548 ( added by amit_doshi @ 24 July 2009)
	mygrid.objBox.style.overflowX = "hidden";
	mygrid.objBox.style.overflowY = "hidden";

	if(isWidthInPercent)
	{
		mygrid.setInitWidthsP(colWidth+",0");
	}
	else
	{
		mygrid.setInitWidths(colWidth);
	}

	mygrid.setColTypes(colDataTypes);

	mygrid.setColSorting(colTypes);
	mygrid.init();

	for(var row=0;row<myData.length;row++)
	{
		mygrid.addRow(row+1,myData[row],row+1);
		mygrid.setRowTextStyle(row+1,"font-family: Arial, Helvetica, sans-serif;font-size: 12px;padding-left:5px;color: #000000;border-left-width: 1px;border-left-color: #CCCCCC;  border-bottom-color: #CCCCCC; border-bottom-color: #CCCCCC; border-right-width: 1px;border-right-color: #CCCCCC;Cursor: default;");
	}

	
	//mygrid.setOnRowDblClickedHandler(funcName);
	//mygrid.setOnRowSelectHandler(funcName);
	// :To hide ID columns by kalpana
	function getIDColumns()
		{
			var hiddenColumnNumbers = new Array();
			//var hiddenColStr=${requestScope.identifierFieldIndex}
			//var hiddenColumnNumbers=hiddenColStr.split(",");

			/*for(var i=0;i<hiddenColStr.length;i++)
			{
				hiddenColumnNumbers[i]=hiddenColStr[i];
			}*/
			hiddenColumnNumbers[0]=5;
			hiddenColumnNumbers[0]=6;
			hiddenColumnNumbers[1]=7;
			hiddenColumnNumbers[1]=8;
			return hiddenColumnNumbers;
		}

	// :To hide ID columns
		var hideCols = getIDColumns();

		for(i=0;i<hideCols.length;i++)
		{
			mygrid.setHeaderCol(hideCols[i],"");
			mygrid.setColumnHidden(hideCols[i],true);
		}
	mygrid.setSizes();
	
	//mygrid.enableTooltips('false');
	initializeAjaxCall();

}

function rowClick(id)
{
	//alert(id);
	/*var colid ='${requestScope.identifierFieldIndex}';
	var cl = mygrid.cells(id,colid);
	var searchId = cl.getValue();
	var url = "SearchObject.do?pageOf=pageOfWorkflow&operation=search&id="+searchId;
	window.open(url,"_top");
	*/
	var cl = mygrid.cells(id,6);
	var queryExecutionId = cl.getValue();
	var c2 = mygrid.cells(id,8);
	var queryId = c2.getValue();
	var queryTitle = mygrid.cells(id,1).getValue();
//	var url = "ExecuteQuery.do?queryId="+queryId+"&queryExecutionId="+queryExecutionId;
//	pvwindow4 = dhtmlmodal.open('View Parameters for Query', 'iframe', url,'View Parameters for query -'+queryTitle, 'width=600px,height=100px,center=1,resize=1,scrolling=1');
	//window.parent.showParametrizeQueryPopup(queryTitle,queryId,queryExecutionId);

}


window.onload=function loadPageContents()
{
	init_grid();
initalizeTooltip();
}
function initalizeTooltip()
{

$("a.jt").cluetip({
  cluetipClass: 'jtip',
  dropShadow: false, 
  sticky: true,
 width:'600px',
height:'200px',
 closeText: '<img src="images/cross.gif" title="Close" border="0" />',
showTitle: true,

  arrows: true,
	waitImage:        true,
	mouseOutClose: true,
	   titleAttribute:   'title',
closePosition: 'title',

hoverIntent: {    
                      sensitivity:  3,
              			  interval:     200,
              			  timeout:      0
    },
	fx: {             
                      open:       'fadeIn', // can be 'show' or 'slideDown' or 'fadeIn'
                      openSpeed:  '1'
    },
		    // function to run just after clueTip is shown. 
    onShow:           function(ct, ci){
	$('#paramHeaderDiv').toggle(function(){hideDiv('paramDiv')},function(){showDiv('paramDiv')});
	$('#constrHeaderDiv').toggle(function(){showDiv('constrDiv')},function(){hideDiv('constrDiv')});

	hideAllDivs();
	createAlternateSripts();
	},
truncate:         0,
	  cursor:           ''
});

}
//this method creates the alternate sripts.
function createAlternateSripts()
{
	if(document.getElementById('constrDiv')!=null&&document.getElementById('constrDiv')!=undefined)
	{
		$("#constrDiv:table tr:even").addClass("rowBGGreyColor1");
	}

}
function changeResPerPage(controlId)
{
	var pageOf ='${requestScope.pageOf}';
	var queryNameLike='${requestScope.queryNameLike}';
	var showLast=document.getElementById(controlId).value;
	var url='RetrieveRecentQueries.do?requestFor=nextPage&showLast='+showLast+'&pageOf='+pageOf+'&queryNameLike='+escape(queryNameLike);
	document.forms[0].action=url;
	document.forms[0].submit();
}
function searchQuery()
{
//	var resultsPerPage=document.getElementById(controlId).value;
	//if(document.getElementById('isSearchStringChanged').value=='true')
	//{
		enableRemoveFilter();
		var searchString=document.getElementById('queryNameLike').value;
		var url='RetrieveRecentQueries.do?pageOf=${requestScope.pageOf}&queryNameLike='+escape(searchString);
		document.forms[0].action=url;
		document.forms[0].submit();
	//}
}
function initializeAjaxCall()
{
	var noOfRows=mygrid.getRowsNum();

	for(var i=1;i<=noOfRows;i++)
	{
		var status=mygrid.cells(i,5).getValue();
		changestatusIcon(status,i);
	}

	for(var i=1;i<=noOfRows;i++)
	{

		var status=mygrid.cells(i,5).getValue();
		var hasPrivilege=mygrid.cells(i,7).getValue();
		//TODO
		//changestatusIcon(status,i);
		if(status!="Completed"&&status!="Cancelled"&&status!="Query Failed")
		{
			var queryexecutionID=mygrid.cells(i,6).getValue();

			recentQueryAjaxCall(queryexecutionID,i,hasPrivilege);


		}
	}
}

function recentQueryAjaxCall(executionLogId,index,hasPrivilege)
{
	var url="RecentQueriesAjaxHandler.do?executionLogId="+executionLogId+"&index="+index+"&queryPrivilege="+hasPrivilege;
	var request=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	var handlerFunction = getReadyStateHandler(request,responseHandler,true);
	request.onreadystatechange = handlerFunction;
	request.open("POST",url,true);
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.send("");
}


function responseHandler(response)
{
	  var jsonResponse = eval('('+ response+')');
          if(jsonResponse.resultObject!=null)
          {
			var queryCount = jsonResponse.resultObject.queryCount;
			var status = jsonResponse.resultObject.status;
			var executionId = jsonResponse.resultObject.executionId;
			var index = jsonResponse.resultObject.index;
			var hasPrivilege = jsonResponse.resultObject.queryPrivilege;
			if(queryCount!=-1)
			{
				var StatusObject=mygrid.cells(index,5);//document.getElementById("StatusId_"+index);

				var CountObject=mygrid.cells(index,2);//document.getElementById("CountId_"+index);

					var statusValue=StatusObject.getValue();

					if(status=="Completed"||status=="Cancelled"||status=="Query Failed")
					{

						mygrid.cells(index,5).setCValue(status);
						imageForView(index);
					}

					mygrid.cells(index,2).setCValue(queryCount);
			}
			changestatusIcon(status,index);
			if(status!="Completed"&&status!="Cancelled"&&status!="Query Failed")
			  {

					recentQueryAjaxCall(executionId,index,hasPrivilege);
			  }
			 else
			 {
				 //TO DO
				 //	disableCancelLink(index);
			 }


          }

}
function changestatusIcon(status,index)
{


	 if(status=='Completed')
	{
		imageForCompletedCounts(index);
	}
	 else if(status=='Cancelled')
	{
		imageForCancelled(index);
	}
	else if(status=='Query Failed')
	{
		imageForFailed(index);
	}
	else if(status=='In Progress')
	{
		imageForProgressiveCounts(index);
	}
	else
	{
		imageForCompletedWithWarning(index);
	}
}
function cancelExecution(queryExecutionId,index,hasPrivilege)
{
	//TO DO
	//disableCancelLink(index);
	var url="RecentQueriesAjaxHandler.do?executionLogId="+queryExecutionId+"&index="+index+"&queryPrivilege="+hasPrivilege
		+"&cancelExecution="+'Constants.CANCEL_EXECUTION';
	var request=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	var handlerFunction = getReadyStateHandler(request,responseHandler,true);
	request.onreadystatechange = handlerFunction;
	request.open("POST",url,true);
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.send("");
}

function imageForCancelled(index)
{
  var x = mygrid.cells(index,0);//document.getElementById("notStarted_"+index);
  mygrid.cells(index,0).setCValue("<div ><img src='images/advancequery/ic_wf_cancel.gif' title='Cancelled'></div>");
}

function imageForProgressiveCounts(index)
{
  var x =mygrid.cells(index,0);
  mygrid.cells(index,0).setCValue("<div ><img src='images/advancequery/inprogress09.gif' title='In Progress'></div>");
}

function imageForFailed(index)
{
  var x =mygrid.cells(index,0);
  mygrid.cells(index,0).setCValue("<div ><img src='images/advancequery/ic_wf_error.gif' title='Failed'></div>");
}

function imageForCompletedCounts(index)
{
	var x = mygrid.cells(index,0);
  mygrid.cells(index,0).setCValue("<div ><img src='images/advancequery/ic_complete05.gif' title='Completed'></div>");
}
function imageForCompletedWithWarning(index)
{
	var x =mygrid.cells(index,0);
	mygrid.cells(index,0).setCValue("<div ><img src='images/advancequery/ic_complete-warning2.gif' title='Completed with warning'></div>");


}

function disableCancelLink(index)
{
	var deleteLink=mygrid.cells(index,4);//document.getElementById("cancel_"+index);
	if(deleteLink!=null&&deleteLink!=undefined)
	{
		deleteLink.href='#';
		deleteLink.className="greylink2";
	}
}

function imageForView(index)
{
/*  var x = mygrid.cells(index,4);
  var queryId =mygrid.cells(index,8).getValue(); ;
  var queryexecutionID=mygrid.cells(index,6).getValue();
  mygrid.cells(index,4).setCValue("<a href='ShowParamRecentQueries.do?width=500&queryId=" + queryId
	  + "&queryExecutionId=" + queryexecutionID
	  + "&pageOf=<%=Constants.PAGE_OF_RECENT_QUERIES %>' class='jTip' align='right' id='abcd" + index + "'>"
	  + "<img src='images/advancequery/application_form.png' alt='View Parameters' hspace='0' border='0' align='left'/></a>");
	 */
	  $("#cancel"+index).hide();
  // JT_init();
}

</script>
<script src="jss/jtip.js" type="text/javascript"></script>
<%int count = 0;%>
<c:set var="queryNameLike" value="${requestScope.queryNameLike}"/>
	<html:form action="RetrieveRecentQueries">
<%@ include file="/pages/advancequery/content/search/querysuite/searchResult.jsp" %>

<logic:notEqual name="records" value="0">
 <table width="100%" cellpadding="0" cellspacing="0" valign="top" border="0">
 <tr valign="top" height="28px" background="images/advancequery/bg_content_header.gif">
<td width="100%" align='right' background="images/advancequery/bg_content_header.gif">
			<table width="100%" height="28px" border="0" cellpadding="0" cellspacing="0" align='right' background="images/advancequery/bg_content_header.gif">
			<tr width="100%" height="28px" background="images/advancequery/bg_content_header.gif">
				<td align="right" height="28px" style="padding-right:5px;" background="images/advancequery/bg_content_header.gif"><span class="content_txt_bold">Show Items:</span>
				<html:select property="value(numResultsPerPage)" styleId="numResultsPerPage" onchange="changeResPerPage('numResultsPerPage')" value="${requestScope.numResultsPerPage}" styleClass="textfield_undefined">
							 <c:forEach var="item" items="${requestScope.resultsPerPageOptions}" varStatus="i">
									<html:option value="${item}">${item}</html:option>
							 </c:forEach>

					</html:select>
				</td>
		</tr>
		 </table>
</td>
</tr>
		<tr height="100%">
			<td valign="top" height="100%" width="100%">
				<div id='gridbox' width='100%' height="100%" style='overflow: hidden'></div>
			</td>
	</tr>

</table>

</logic:notEqual>
<table width="100%" cellpadding="4" cellspacing="0">
<logic:equal name="records" value="0">

					<td class="content_txt_bold" style="padding-left:5px;" valign="top">
					<bean:message key="meassges.emptyquery"/>
					</td>
</logic:equal >
</table>
<script>
defaultdisableButtons();
</script>
	</html:form>
</body>
