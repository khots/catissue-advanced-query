function showTipOnWFPage(obj,tooltipValue)
{
	obj.title = ""+tooltipValue;
	var browser=navigator.appName;
	if(browser=="Microsoft Internet Explorer")
	{
		showStatus(""+tooltipValue);
	}
	else
	{
		obj.title = ""+tooltipValue;
	}
}
/*This methode enables or disabled the butons */
function setButtons()
{
	//var buttonStatusDiv=document.getElementById("buttonStatusDiv");
	var buttonStatus=document.getElementById("buttonStatus");


		//buttonStatus.innerHTML= "<table cellpadding='0' cellspacing='0' border='0' width='100%' ><tr width='100%'><td align='left' ><img align='absmiddle' src='images/advancequery/b_union_inact.gif' alt='Union' border='0'>&nbsp;</td><td  align='middle'><img align='absmiddle' src='images/advancequery/b_intersection_inact.gif' alt='Intersection'  border='0'>&nbsp;</td><td align='left'><img align='absmiddle' src='images/advancequery/b_minus_inact.gif' alt='Minus'  border='0'></td></tr></table>";
		buttonStatus.innerHTML= "<table cellpadding='0' cellspacing='0' width='100%'><tr width='100%'><td align='right' width='20%'><img align='absmiddle'id='unionImg' src='images/advancequery/b_union_inact.gif'  alt='Union' border='0'>&nbsp;</td><td align='center' width='20%'><img align='absmiddle' id='intersectionImag'  src='images/advancequery/b_intersection_inact.gif' alt='Intersection'  border='0'>&nbsp;</td><td  align='left' width='15%'><img align='absmiddle'  src='images/advancequery/b_minus_inact.gif' id='minusImg'  alt='Minus'  border='0'>&nbsp;</td></tr></table>";
	


}

/*shows new Count Query page*/
function getCountdata()
{
	document.forms[0].forwardTo.value= "loadQueryPage";
	document.forms[0].action="SaveWorkflow.do?submittedFor=ForwardTo&nextPageOf=queryGetCount";
	document.forms[0].submit();
}
/*shows new data Query page*/
function getPatientdata()
{
	document.forms[0].forwardTo.value= "loadQueryPage";
	document.forms[0].action="SaveWorkflow.do?submittedFor=ForwardTo&nextPageOf=queryWizard";
	document.forms[0].submit();
}
/*hide the divs on page load*/
var sliderIntervalForCount = 0;
var sliderIntervalForData = 0;
function showDiv(elementid)
{
	if(elementid=='DataDiv')
	{
		  var x = document.getElementById("datahideShow");
		  var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);
		   //$("#DataDiv").slideDown(200);
		 // $('#showHideData').click(function(){hideDiv('DataDiv')});
		  SlideDownRun("DataDiv","dataTable");


	}
	else
	{
		  var x = document.getElementById("counthideShow");
		  var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);
		 // $("#CountDiv").slideDown(200);
		// $('#showHideCount').click(function(){hideDiv('CountDiv')});

		 SlideDownRun("CountDiv","countTable");



	}
	return;

}
function hideDiv(elementid)
{
	if(elementid=='DataDiv')
		{
			  var x = document.getElementById("datahideShow");
			  var v = x.getAttribute("src");
			  v = "images/advancequery/arrow_close.png";
			  x.setAttribute("src", v);
			//  $("#DataDiv").slideUp(200);
				//$('#showHideData').click(function(){showDiv('DataDiv')});

			//SlideUpRun("DataDiv");
			  $("#DataDiv").animate({ height: 65}, 300);


		}
		else
		{
			  var x = document.getElementById("counthideShow");
			  var v = x.getAttribute("src");
			  v = "images/advancequery/arrow_close.png";
			  x.setAttribute("src", v);
			//  $("#CountDiv").slideUp(200);
			//	 $('#showHideCount').click(function(){showDiv('CountDiv')});

			//SlideUpRun("CountDiv");
				  $("#CountDiv").animate({ height:139}, 300);


		}
				return;

}
//initializes loading div
function initializeLoadingDiv()
{
	document.getElementById("loading").className = "loading-visible";
}
//clears loading div
function clearLoadingDiv()
{
		var loading=document.getElementById("loading");
		if(loading.className=="loading-visible")
		{
			loading.className = "loading-invisible";
		}

}
/*
 * changes failed/Completed/notRun/Cancelled to progressive
 * */
function imageForProgressiveCounts(index)
{
  var x = document.getElementById("notStarted_"+index);
   if(x!=null && x!=undefined)
	{
	  var v = x.getAttribute("src");
	  v = "images/advancequery/inprogress09.gif";
	  x.setAttribute("src", v);
	 //x.setAttribute("onMouseOver","Tip('In Progress')");
		x.onmouseover=function chnageToolTip(x){ showTipOnWFPage(this,'In Progress'); };
	}

}
/*
 * changes failed/in progress/notRun/Cancelled to Completed
 * */
function imageForCompletedCounts(index)
{
	var x = document.getElementById("notStarted_"+index);
	  if(x!=null && x!=undefined)
	{
		var v = x.getAttribute("src");
		v = "images/advancequery/ic_complete05.gif";
		x.setAttribute("src", v);
		//x.setAttribute("onMouseOver","Tip('Completed')");
		x.onmouseover=function chnageToolTip(x){ showTipOnWFPage(this,'Completed'); };
	}


}
/*
 * changes failed/in progress/notRun/Cancelled to not run
 * */
function imageForNotRunning(index)
{

  var x = document.getElementById("notStarted_"+index);
  if(x!=null && x!=undefined)
{
	  var v = x.getAttribute("src");
	  v = "images/advancequery/ic_notrun06.gif";
	  x.setAttribute("src", v);
		x.onmouseover=function chnageToolTip(x){ showTipOnWFPage(this,'Not Run'); };
	}

}
/*
 * changes failed/Completed/notRun/in progress to Cancelled
 * */
function imageForCancelled(index)
{
  var x = document.getElementById("notStarted_"+index);
    if(x!=null && x!=undefined)
	{
	  var v = x.getAttribute("src");
	  v = "images/advancequery/ic_wf_cancel.gif";
	  x.setAttribute("src", v);
	  x.onmouseover=function chnageToolTip(x){showTipOnWFPage(this,'Cancelled'); };
	}

}
/*
 * changes Cancelled/Completed/notRun/in progress to  failed
 * */
function imageForFailed(index)
{
  var x = document.getElementById("notStarted_"+index);
  if(x!=null && x!=undefined)
	{
	  var v = x.getAttribute("src");
	  v = "images/advancequery/ic_wf_error.gif";
	  x.setAttribute("src", v);
	 x.onmouseover=function chnageToolTip(x){ showTipOnWFPage(this,'Failed'); };
	}

}
function imageForCompletedWithWarning(index)
{
  var x = document.getElementById("notStarted_"+index);
  if(x!=null && x!=undefined)
	{
	  var v = x.getAttribute("src");
	  //TODO set the new image
	  v = "images/advancequery/ic_complete-warning2.gif";
	  x.setAttribute("src", v);
	 x.onmouseover=function chnageToolTip(x){ showTipOnWFPage(this,'Completed with warning'); };
	}

}

function disableDeleteLink(index)
{
	var deleteLink=document.getElementById("delete_"+index);
	deleteLink.href='#';
	deleteLink.className="greylink2";
}
function enableDeleteLink(index)
{
	var deleteLink=document.getElementById("delete_"+index);
	deleteLink.href="javascript:deleteWorkflowItem('"+index+"')";
	deleteLink.className="bluelink";
}


/*shows existing queries in popup */
function showPopUp(pageOf,queryType)
{
	var url='QueryAction.do?pageOf='+pageOf+'&queryId=queryId&queryTitle=queryTitle&queryType='+queryType;
	pvwindow=dhtmlmodal.open('Select queries', 'iframe', url,'Select Queries', 'width=908px,height=415px,center=1,resize=1,scrolling=0');

}
function updateUI()
{
	addQuery();
}
function addQuery()
{
	var queryIds=document.getElementById("queryId").options;
	var queryTitles=document.getElementById("queryTitle").options;
	var queryTypes=document.getElementById("queryType").options;
	var countQueries=document.getElementById("countTable").rows.length;
	var dataQueries=document.getElementById("dataTable").rows.length;
	var presentQueryIds=parent.window.document.getElementsByName("queryIdForRow");
	var presentQueryTitle=parent.window.document.getElementsByName("displayQueryTitle");
	var presentQueryType=parent.window.document.getElementsByName("displayQueryType");


	var dataQueriesCount=0;
	for(var counter=0;counter<queryIds.length;counter++)
	{

			if(getText(queryTypes[counter])=="Data")
			{
				var rowContents=new Array(8);
				rowContents[0]=createTextElement("");//document.createElement("td");//createCheckBox("chkbox","checkDatabox_"+(dataQueriesCount+dataQueries),'',(dataQueriesCount+dataQueries),true);
				//rowContents[1]=document.createElement("td");
				rowContents[1]=createTextElement(getText(queryTitles[counter]));
				rowContents[2]=createHiddenElement("displayQueryType","displayDataQueryType_"+(dataQueriesCount+dataQueries),getText(queryTypes[counter]));//createTextElement(getText(queryTypes[counter]));
				rowContents[3]=createHiddenElement("displayQueryTitle","displayDataQueryTitle_"+(dataQueriesCount+dataQueries),getText(queryTitles[counter]));
				rowContents[4]=createHiddenElement("expression","dataQueryExpression_"+(dataQueriesCount+dataQueries),getText(queryIds[counter]));
				rowContents[5]=createHiddenElement("selectedqueryId","selectedDataqueryId_"+(dataQueriesCount+dataQueries),getText(queryIds[counter]));

				//rowContents[6]=createHiddenElement("workflowItemId","dataWorkflowItemId_"+(dataQueriesCount+dataQueries),""));
				rowContents[6]=getText(queryTitles[counter]);
				rowContents[7]=getText(queryTypes[counter]);
				dataQueriesCount++;

				addDataQueryToTable("dataTable",rowContents);


			}


	}

	var countQueriesCount=0;
	for(var counter=0;counter<queryIds.length;counter++)
	{
			if(getText(queryTypes[counter])=="Count")
			{
				var rowContents=new Array(10);
				rowContents[0]=createCheckBox("chkbox","checkbox_"+(countQueriesCount+countQueries),'',(countQueriesCount+countQueries),false);
				var trImgDown=createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+(countQueriesCount+countQueries));
				rowContents[1]=trImgDown;
				rowContents[2]=createTextElement(getText(queryTitles[counter]));
				rowContents[3]=createHiddenElement("displayQueryType","displayQueryType_"+(countQueriesCount+countQueries),getText(queryTypes[counter]));
				rowContents[4]=createHiddenElement("displayQueryTitle","displayQueryTitle_"+(countQueriesCount+countQueries),getText(queryTitles[counter]));
				rowContents[5]=createLabel("",countQueriesCount+countQueries);
				rowContents[6]=createHiddenElement("expression","expression_"+(countQueriesCount+countQueries),getText(queryIds[counter]));
				rowContents[7]=createHiddenElement("selectedqueryId","selectedqueryId_"+(countQueriesCount+countQueries),getText(queryIds[counter]));

				rowContents[8]=getText(queryTitles[counter]);
				rowContents[9]=getText(queryTypes[counter]);
				countQueriesCount++;
				addCountQueryToTable("countTable",rowContents,getText(queryIds[counter]));

			}

	}
	setQueryCount();

}

// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addDataQueryToTable(tableId,columnContents)
{
	if(!isAlreadyPresentDataQuery(tableId,columnContents[6],columnContents[7]))
	{
		var tdWidth=new Array("2%","91%","","","","");
		var alignmentArray=new Array("middle","","","","","");
		var	tdStyleClass=new Array("td_grey_start","td_grey_text","","","","");


		var tableObj=document.getElementById(tableId);
		var rowObj=document.createElement("tr");
		rowObj.className="tr_bgcolor_white";
		rowObj.width="100%";
		var columnCount=columnContents.length;
		var columnObj;
		var queryCount=0;
		var queryControls=tableObj.rows;
		if(queryControls!=null && queryControls!=undefined)
		{
			queryCount=queryControls.length;
		}
		columnObj=document.createElement("td");
		columnObj.innerHTML="&nbsp;";
		columnObj.align=alignmentArray[0];
		columnObj.className=tdStyleClass[0];
		columnObj.width=tdWidth[0];
		rowObj.appendChild(columnObj);
		rowObj.id="dataTableRow"+queryCount;


		for(var counter=1;counter<columnCount-2;counter++)
		{

			if(columnContents[counter]!=null&&columnContents[counter]!=undefined)
			{
				columnObj=document.createElement("td");

				if(tdWidth[counter]!="")
				{
					columnObj.width=tdWidth[counter];
					if(alignmentArray[counter]!="")
					{

						columnObj.align=alignmentArray[counter];
					}
					if(tdStyleClass[counter]!="")
					{

						columnObj.className=tdStyleClass[counter];
					}
				}
				else
				{
					columnObj.style.display="none";
				}
				columnObj.appendChild(columnContents[counter]);
				//columnObj.className="content_txt";
				if(columnContents[counter].name=="selectedqueryId")
				{
					id=columnContents[counter].value;
					columnObj.appendChild(createHiddenElement("identifier","dataIdentifier_"+queryCount,columnContents[6]));
					columnObj.appendChild(createHiddenElement("queryIdForRow","dataQueryIdForRow_"+id,id));
					columnObj.appendChild(createHiddenElement("queryExecId","dataQueryExecId_"+queryCount,0));
				}
				rowObj.appendChild(columnObj);
			}
		}

		//Create all the hidden controls and add them to a "td"
		var operandsTd=document.createElement("td");
		operandsTd.className="td_grey";
		operandsTd.width="6%";
		operandsTd.align="middle";
		var tble1=document.createElement("table");
		var tbody1=document.createElement("tbody");
		var operandsTr=document.createElement("tr");
		operandsTr.className="tr_bgcolor_white";
		var operandsTd1=document.createElement("td");
		operandsTd1.width="3%";
		operandsTd1.align="middle";
		var operandsTd3=document.createElement("td");
		operandsTd3.width="3%";
		operandsTd3.align="middle";


		var t =	escape(columnContents[6]);
		operandsTd1.appendChild(createLink("View Results ","images/advancequery/execute-button-1.PNG","executeData_"+queryCount,"javascript:executeGetDataQuery('"+id+"')"));
		operandsTd3.appendChild(createLink("Delete","images/advancequery/ic_delete.gif ","deleteData_"+queryCount,"javascript:deleteWorkflowItem('"+queryCount+"','"+columnContents[7]+"')"));

		operandsTr.appendChild(operandsTd1);
		operandsTr.appendChild(operandsTd3);
		tbody1.appendChild(operandsTr);
		tble1.appendChild(tbody1);
		operandsTd.appendChild(tble1);
		rowObj.appendChild(operandsTd);

		tableObj.appendChild(rowObj);
		document.getElementById("isdone").value="false";
	}
	else
	{
		document.getElementById("sameQueryTitle").value=columnContents[6];
		dhtmlmodal.open('Same Query title', 'iframe', './pages/advancequery/content/search/querysuite/QueryTitlePopup.jsp','Query title', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
	}
}

// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addCountQueryToTable(tableId,columnContents,saveAsforqueryId)
{

	if(!isAlreadyPresent(tableId,columnContents[8],columnContents[9]))
	{
		//var tdWidth=var myCars=["1%","2%","80%","0%","0%","10%","0%","0%"];
		var tdWidth=new Array("2%","3%","78%","","","10%","","");
		var alignmentArray=new Array("middle","middle","","","","middle","","");
		var	tdStyleClass=new Array("td_grey_start","td_grey","td_grey_text","","","td_grey","","");


		var tableObj=document.getElementById(tableId);
		var rowObj=document.createElement("tr");
		rowObj.className="tr_bgcolor_white";
		rowObj.width="100%";
		var columnCount=columnContents.length;
		var columnObj;
		var queryCount=0;
		var queryControls=tableObj.rows;
		if(queryControls!=null && queryControls!=undefined)
		{
			queryCount=queryControls.length;
		}
		rowObj.id="countRow"+queryCount;
		for(var counter=0;counter<columnCount-2;counter++)
		{

			if(columnContents[counter]!=null)
			{
				columnObj=document.createElement("td");
				if(tdWidth[counter]!="")
				{
					columnObj.width=tdWidth[counter];
					if(alignmentArray[counter]!=""){columnObj.align=alignmentArray[counter];}
					if(tdStyleClass[counter]!="")
					{

						columnObj.className=tdStyleClass[counter];
					}
				}
				else
				{
					columnObj.style.display="none";
				}
				//columnObj.className="content_txt";
				columnObj.appendChild(columnContents[counter]);
				if(columnContents[counter].name=="selectedqueryId")
				{
					id=columnContents[counter].value;
					columnObj.appendChild(createHiddenElement("identifier","identifier_"+queryCount,columnContents[8]));
					columnObj.appendChild(createHiddenElement("queryIdForRow","queryIdForRow_"+id,id));
					columnObj.appendChild(createHiddenElement("queryExecId","queryExecId_"+queryCount,0));
				}
				rowObj.appendChild(columnObj);
			}
		}


		//Create all the hidden controls and add them to a "td"
		var operandsTd=document.createElement("td");
		operandsTd.className="td_grey";
		operandsTd.width="6%";
		operandsTd.align="middle";
		var tble1=document.createElement("table");
		var tbody1=document.createElement("tbody");
		var operandsTr=document.createElement("tr");
		operandsTr.className="tr_bgcolor_white";
		var operandsTd1=document.createElement("td");
		operandsTd1.width="3%";
		operandsTd1.align="middle";
		var operandsTd3=document.createElement("td");
		operandsTd3.width="3%";
		operandsTd3.align="middle";


		var t =	escape(columnContents[8]);

		operandsTd1.appendChild(createLink("Execute ","images/advancequery/execute-button-1.PNG","execute_"+queryCount,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
		operandsTd3.appendChild(createLink("Delete","images/advancequery/ic_delete.gif ","delete_"+queryCount,"javascript:deleteWorkflowItem('"+queryCount+"','"+columnContents[9]+"')"));

		operandsTd3.appendChild(createHiddenElement("cancelajaxcall","cancelajaxcall_"+(queryCount),'false'));
		if(saveAsforqueryId!="")
		{
			var opt=document.createElement("td");
			opt.align="middle";
			opt.appendChild(createLink("Create Copy","images/advancequery/saveAs.png","saveAs_"+queryCount,"javascript:saveAs('"+saveAsforqueryId+"','"+escape(t)+"')"));
			operandsTr.appendChild(opt);
		}
		else
		{
					var opt=document.createElement("td");
					operandsTr.appendChild(opt);

		}

		operandsTr.appendChild(operandsTd1);
		operandsTr.appendChild(operandsTd3);
		tbody1.appendChild(operandsTr);
		tble1.appendChild(tbody1);
		operandsTd.appendChild(tble1);
		rowObj.appendChild(operandsTd);

		tableObj.appendChild(rowObj);
		document.getElementById("isdone").value="false";
	}
	else
	{
		document.getElementById("sameQueryTitle").value=columnContents[8];
		dhtmlmodal.open('Same Query title', 'iframe', './pages/advancequery/content/search/querysuite/QueryTitlePopup.jsp','Query title', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
	}
}

// This method adds a row to the table with id "tableId"
function isAlreadyPresent(tableId,queryTitle,queryType)
{

			var alreadyPresent=false;
			var numOfRows =document.getElementById(tableId).rows.length;
			for(var count = 0; count < numOfRows; count++)
			{

			//queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,(columnContents[7]));
		//queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,(columnContents[8]));
				var title=document.getElementById("identifier_"+count);
				var type=document.getElementById("displayQueryType_"+count).value;
				if(title.value==queryTitle&&type==queryType)
				{
					alreadyPresent=true;
				}
			}

return alreadyPresent;
}
// This method adds a row to the table with id "tableId"
function isAlreadyPresentDataQuery(tableId,queryTitle,queryType)
{
			var alreadyPresent=false;
			var numOfRows =document.getElementById(tableId).rows.length;
			for(var count = 0; count < numOfRows; count++)
			{

			//queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,(columnContents[7]));
		//queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,(columnContents[8]));
				var title=document.getElementById("dataIdentifier_"+count);
				var type=document.getElementById("displayDataQueryType_"+count).value;
				if(title.value==queryTitle&&type==queryType)
				{
					alreadyPresent=true;
				}
			}

return alreadyPresent;
}
/*creates hidden element*/
function createHiddenElement(name,id,content)
{
	var hiddenControl=document.createElement('input');
	hiddenControl.type="hidden";
	hiddenControl.id=id;
	hiddenControl.name=name;
	hiddenControl.value=content;
	return hiddenControl;
}
/*create Element*/
function createTextElement(text)
{
	var textnode=document.createTextNode(text);
	return textnode;
}
function createCheckBox(name,id,displayValue,count,disable)
{
		var chkbox=document.createElement("input");
		var text=document.createTextNode(displayValue);
		chkbox.type="checkbox";
		chkbox.id=id;
		chkbox.name=name;
		if(disable==true)
		{
			chkbox.disabled = true;
		}
		chkbox.onclick=function addEvent(){
			setCheckboxCount();
			setSelectedCheckBoxes();

		}

		return chkbox;
}
function createLabel(name,index)
{
		var label=document.createElement("label");
		label.id="label_"+index;
		label.innerHTML='&nbsp;';
		//label.appendChild(createLatestParameterLink(index));
		//label.innerHTML='&nbsp;<a href="#" class="jTip" align="right" id="latestParameterLink_'+index+'"></a>';

		return label;
}
/*for cq tool tip to show parameters is not needed
so added label without href in it
*/
function createLabelCQ(name,index)
{
		var label=document.createElement("label");
		label.id="labelForCQ_"+index;
		label.innerHTML='&nbsp;';
		return label;
}
/*function initailzeLatestParameterLink(index)
{
	var latestParameterLink=document.getElementById('latestParameterLink_'+index);
	latestParameterLink.href="#";
	latestParameterLink.innerHTML="";


}*/
function createDiv(innerTxt,index)
{
		var label=document.createElement("div");
		label.id="div_"+index;
		label.innerHTML=innerTxt;
		return label;
}

function createLink(displayValue,imagePath,text,url)
{
	var link=document.createElement('a');
	//link.className="bluelink";
	link.href=url;
	link.id=text;
	var image=document.createElement("img");
	image.setAttribute("src",imagePath);
	image.setAttribute("border",0);
	image.setAttribute("id",text+"img");
	image.onmouseover=function abc(image){ showTipOnWFPage(this,displayValue); };
	link.appendChild(image);
	return link;
}


function getText(control)
{
       var browser=navigator.appName;
       if(browser=="Microsoft Internet Explorer")
       {
    	   return control.innerText;
       }
       else if(navigator.userAgent.indexOf('Safari')!=-1)
       {
    	   return control.innerText;
       }
       else
       {
    	   return control.textContent;
       }
}

function createImageElement(srcPath,imageId)
{
	var image=document.createElement("img");
	image.setAttribute("src",srcPath);
	image.setAttribute("id",imageId);
	//image.setAttribute("onMouseOver","Tip('Not Started')");
	image.onmouseover=function abc(image){showTipOnWFPage(this,'Not Run'); };
	return image;
}
//manipulate currently selected checkboxes and enable disable
//operation  buttons accordingly
function setCheckboxCount()
{
	var checkboxArray=document.getElementById("countTable").rows;
	var selectedCheckboxes=0;
	if(checkboxArray!=null)
	{
			var numOfRows =checkboxArray.length;
			for(var count =0; count < numOfRows; count++)
			{
				var id ='checkbox_'+count;
				if(document.getElementById(id).checked)
				{

					selectedCheckboxes++;
				}
			}
	}
	if(selectedCheckboxes==2)
	{
		enableButtons();
	}
	else
	{
		disableButtons();
	}

}

//disables operation buttons
function disableButtons()
{
	var buttonStatus=document.getElementById("buttonStatus");
	if(buttonStatus!=null)
	{
		  while (buttonStatus.childNodes[0])
		 {

			buttonStatus.innerHTML='';
		}
			//buttonStatus.innerHTML="<table cellpadding='0' cellspacing='0' width='100%'><tr width='100%'><td align='left' ><img align='absmiddle' src='images/advancequery/b_union_inact.gif' alt='Union' border='0'>&nbsp;</td><td  align='middle'><img align='absmiddle' src='images/advancequery/b_intersection_inact.gif' alt='Intersection'  border='0'>&nbsp;</td><td align='left'><img align='absmiddle' src='images/advancequery/b_minus_inact.gif' alt='Minus'  border='0'></td></tr></table>";
			buttonStatus.innerHTML= "<table cellpadding='0' cellspacing='0' width='100%'><tr width='100%'><td align='right' width='20%'><img align='absmiddle' src='images/advancequery/b_union_inact.gif' id='unionImg'  alt='Union' border='0'>&nbsp;</td><td  align='center' width='20%'><img align='absmiddle' id='intersectionImag'    src='images/advancequery/b_intersection_inact.gif' alt='Intersection'  border='0'>&nbsp;</td><td  align='left' width='15%'><img align='absmiddle' id='minusImg'  src='images/advancequery/b_minus_inact.gif' alt='Minus'  border='0'>&nbsp;</td></tr></table>";
		
	}
}
/*Thhis method sets the array of checkboxes selected on UI
This is useful while performing ' a Minus b  '
or  b Minus a ' kind of queries */
function setSelectedCheckBoxes()
{

	var queryCount=document.getElementById("countTable").rows.length;
		for(var counter=0;counter<queryCount;counter++)
			{
				var checkboxControl=document.getElementById("checkbox_"+(counter));
				if(checkboxControl.checked==true)
				{
						var index=indexOf(orderOfCheckBoxClick,counter);
						if(index==-1)
						{
							orderOfCheckBoxClick.push(counter);
						}
				}
				else
				{
					var index1=indexOf(orderOfCheckBoxClick,counter);
						if(index1!=-1)
						{
							orderOfCheckBoxClick.splice(indexOf(orderOfCheckBoxClick,counter),1);
						}

				}
			}



}
function indexOf(scripts,counter)
{
	var index=-1;
	for(var i=0;i<orderOfCheckBoxClick.length;i++)
	{
		if(orderOfCheckBoxClick[i]==counter)
		{
			index=i;
		}
	}
	return index;
}
/*enables operation  buttons*/
function enableButtons()
{
	var buttonStatus=document.getElementById("buttonStatus");
	if(buttonStatus!=null)
	{

		  while (buttonStatus.childNodes[0])
		 {

			buttonStatus.innerHTML='';
		}
		buttonStatus.innerHTML="<table cellpadding='0' cellspacing='0' width='100%'><tr width='100%'><td align='right' width='20%'><a href='javascript:unionQueries()'><img align='absmiddle'  id='unionActImg' src='images/advancequery/b_union-copy.gif' onmouseover='showTipOnWFPage(this,\"Union operation\")' alt='Union' border='0'></a>&nbsp;</td><td  align='center' width='20%'><a href='javascript:intersectQueries()'><img align='absmiddle'    id='intersectionactImg' onmouseover='showTipOnWFPage(this,\"Intersection operation\")' src='images/advancequery/b_intersection.gif' alt='Intersection'  border='0'></a>&nbsp;</td><td  align='left' width='15%'><a href='javascript:minusQueries()'><img align='absmiddle' id='minausactimg'  onmouseover='showTipOnWFPage(this,\"Minus operation\")' src='images/advancequery/b_minus.gif' alt='Minus'  border='0'></a>&nbsp;</td></tr></table>";
	}

}
///generates query id list to add in cq
function addCQToList(operation)
{

	var queryIdsToAdd='';
	var queryCount=0;
	queryCount=document.getElementById("countTable").rows.length;
	var selectedQueryCount=0;

	for (i=0;i<orderOfCheckBoxClick.length;i++)
	{
		queryIdsToAdd=queryIdsToAdd+","+orderOfCheckBoxClick[i] ;
		selectedQueryCount=selectedQueryCount+1;
	}

	if(queryIdsToAdd!=""&& selectedQueryCount==2)
	{

		createCQ(queryIdsToAdd,operation,queryCount);
		uncheckselectedCheckBoxes();
		setCheckboxCount();

	}
}
//create cq for given queryIds To Add
function createCQ(queryIdsToAdd,operation,queryCount)
{
	var queryIds=queryIdsToAdd.split(",");
	var operandsTdContent="";
	var displayCqTitle="";
	var cqTitle="";
	var cqQueryId="";
	var operandsCounter=0;
	var expression="";
	for(var counter=0;counter<queryIds.length;counter++)
	{
		if(queryIds[counter]!=null && queryIds[counter]!='')
		{
			if( displayCqTitle=='')
			{
				displayCqTitle="[ Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+" ]";
				cqTitle="[ Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+" ]";
			}
			else
			{
				displayCqTitle=displayCqTitle+" <b>"+operation+"</b> "+"[ Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+" ]";
				cqTitle=cqTitle+" "+operation+" "+"[ Query : "+document.getElementById("displayQueryTitle_"+queryIds[counter]).value+" ]";
			}
			if(cqQueryId=='')
			{
				cqQueryId="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=document.getElementById("selectedqueryId_"+queryIds[counter]).value;
				expression=document.getElementById("expression_"+queryIds[counter]).value;

			}
			else
			{
				cqQueryId="("+document.getElementById("selectedqueryId_"+queryIds[counter]).value+")";
				operandsTdContent=operandsTdContent+"_"+document.getElementById("selectedqueryId_"+queryIds[counter]).value;
				expression=expression+"_"+document.getElementById("expression_"+queryIds[counter]).value+"_"+shortOperator(operation);
			}
			operandsCounter=operandsCounter+1;
		}
	}
	var cqType="Count";
	var cqId="";

	var rowContents=new Array(10);
	rowContents[0]=createCheckBox("chkbox","checkbox_"+queryCount,'',queryCount,false);
	rowContents[1]=createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+queryCount);
	rowContents[2]=createDiv(displayCqTitle,queryCount);//createTextElement(cqTitle);
	rowContents[3]=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,cqType);//createTextElement(getText(queryTypes[counter]));
	rowContents[4]=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,cqTitle);
	rowContents[5]=createLabelCQ("",queryCount);
	rowContents[6]=createHiddenElement("expression","expression_"+queryCount,expression);
	rowContents[7]=createHiddenElement("selectedqueryId","selectedqueryId_"+queryCount,operandsTdContent);
	rowContents[8]=cqTitle;
	rowContents[9]=cqType;



	//create a table containing tbody with id "table1"
	addCountQueryToTable("countTable",rowContents,"");
	setQueryCount();
}
//ends cq creation here

//deselects the check boxes once the operation is performed
function uncheckselectedCheckBoxes()
{
	var queryCount=0;
	queryCount=document.getElementById("countTable").rows.length;
		for(var counter=0;counter<queryCount;counter++)
			{
				var checkboxControl=document.getElementById("checkbox_"+(counter));
				if(checkboxControl!=null && checkboxControl!=undefined && checkboxControl.checked==true)
				{
					checkboxControl.checked=false;
				}
			}
		//also delete array contents
		orderOfCheckBoxClick.splice(0,orderOfCheckBoxClick.length);
}


/*return value for the operation used wile creating expression*/
function shortOperator(operation)
{
	if(operation=='Union')
		return "+";

	if(operation=='Intersection')
		return "*";

	if(operation=='Minus')
		return "-";

	return "";//for NONE
}
/*save workflow*/
function submitWorflow()
{
	document.forms[0].action="SaveWorkflow.do";
	document.forms[0].forwardTo.value="";

	document.forms[0].submit();
}
//Shows Delete Query Comfirmation popup
function deleteWorkflowItem(index,type)
{
	document.getElementById("itemToDelete").value=index;
	document.getElementById("typeOfItemToDelete").value=type;
	var url="./pages/advancequery/content/search/querysuite/deleteQueryConfim.jsp";
	pvwindow=dhtmlmodal.open('Delete Query1', 'iframe', url,'Delete Query', 'width=400px,height=120px,center=1,resize=1,scrolling=0');


}
//reset rows after deletion for count query
function deleteQuery(index)
{
	var dependentQueries=checkFordependentQueries(index);

	if(dependentQueries!=true)
	{
		var checkboxControl=document.getElementById("checkbox_"+(index));

		if(checkboxControl!=null && checkboxControl!=undefined)
		{

		//also remove from the array of checked chkboxes
			//removeSelectedCheckBoxes(index);

			var table=document.getElementById("countTable");
			var oldNoOfRows=document.getElementById("countTable").rows.length;

			var deleteQuery =document.getElementById('selectedqueryId_'+index);
			var object=deleteQuery.parentNode;
			var tdChildCollection=object.getElementsByTagName('input');
			var deleteQueryId=tdChildCollection[2].value;

			var i=index;
			++i;
			for( ;i<=(oldNoOfRows-1);i++)
			{

				document.getElementById("checkbox_"+i).id="checkbox_"+(i-1);

				document.getElementById("displayQueryTitle_"+i).id="displayQueryTitle_"+(i-1);
				document.getElementById("selectedqueryId_"+i).id="selectedqueryId_"+(i-1);
				document.getElementById("displayQueryType_"+i).id="displayQueryType_"+(i-1);

				if(document.getElementById("execute_"+i)!=null)
				{
					document.getElementById("execute_"+i).id="execute_"+(i-1);
				}
				if(document.getElementById("cancel_"+i)!=null)
				{
					document.getElementById("cancel_"+i).id="cancel_"+(i-1);
				}
				document.getElementById("delete_"+i).href="javascript:deleteWorkflowItem('"+(i-1)+"')"
				document.getElementById("delete_"+i).id="delete_"+(i-1);
				document.getElementById("cancelajaxcall_"+i).id="cancelajaxcall_"+(i-1);
				document.getElementById("identifier_"+i).id="identifier_"+(i-1);
				if(document.getElementById("label_"+i)!=null)
				{
					document.getElementById("label_"+i).id="label_"+(i-1);
				}
					if(document.getElementById("labelForCQ_"+i)!=null)
				{
					document.getElementById("labelForCQ_"+i).id="labelForCQ_"+(i-1);
				}
				document.getElementById("notStarted_"+i).id="notStarted_"+(i-1);
				document.getElementById("expression_"+i).id="expression_"+(i-1);

					//replace(scripts,i);
			}

			table.deleteRow(index);
			document.getElementById("isdone").value="false";

		}

		reSetDropDowns(deleteQueryId);
		setCheckboxCount();
		uncheckselectedCheckBoxes();
		setQueryCount();
		}
		else
		{
			pvwindow1=dhtmlmodal.open('Delete Query2', 'iframe', './depentQueryPopup.jsp','Delete Query', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
		}
}
//delete query for given index
function deleteDataQuery(index)
{

			var table=document.getElementById("dataTable");
			var oldNoOfRows=document.getElementById("dataTable").rows.length;

			var deleteQuery =document.getElementById('selectedDataqueryId_'+index);
			var object=deleteQuery.parentNode;
			var tdChildCollection=object.getElementsByTagName('input');
			var deleteQueryId=tdChildCollection[2].value;

			var i=index;
			++i;
			for( ;i<=(oldNoOfRows-1);i++)
			{



				document.getElementById("displayDataQueryTitle_"+i).id="displayDataQueryTitle_"+(i-1);
				document.getElementById("selectedDataqueryId_"+i).id="selectedDataqueryId_"+(i-1);
				document.getElementById("displayDataQueryType_"+i).id="displayDataQueryType_"+(i-1);
				document.getElementById("executeData_"+i).id="execute_"+(i-1);
				document.getElementById("deleteData_"+i).href="javascript:deleteWorkflowItem('"+(i-1)+"')"
				document.getElementById("deleteData_"+i).id="deleteData_"+(i-1);
				document.getElementById("dataIdentifier_"+i).id="dataIdentifier_"+(i-1);


			}

			table.deleteRow(index);
			document.getElementById("isdone").value="false";

}
/* ------------------------------------------------------------------
 * Function - checkFordependentQueries()
 * This function checks if the current query is a part of atleast one
 * other joined query in the current workflow page.
 *
 * Input - index - The index to the current query's postfix expression
 *
 * Returns -
 * a) true	- if atleast one query containing the current query is found
 * b) false	- if no query is found that contains the current query.
 *  ------------------------------------------------------------------
 */
function checkFordependentQueries(index)
{

	var input=document.getElementById("expression_"+index).value;
	var rows=document.getElementById("countTable").rows.length;
	for(var i=0;i<rows;i++)
	{
		var exp=document.getElementById("expression_"+i).value;
		if (exp == input)
		{
			continue;
		}
		while(exp.indexOf(input) != - 1)
		{
			var startingPlace = exp.indexOf(input);
			var endingPlace = startingPlace + input.length;
			if (startingPlace > 0 && exp.substring(startingPlace-1,startingPlace) != '_')
			{
				//return false;
			}
			else if (endingPlace < exp.length && exp.substring(endingPlace,endingPlace+1)!= "_" )
			{
				//return false;
			}
			else
			{
				return true;
			}

			if (exp.substring(startingPlace,exp.length).indexOf('_') != -1)
			{
				endingPlace = endingPlace + exp.substring(startingPlace,exp.length).indexOf('_')-1;
			}
			exp = exp.substring(endingPlace, exp.length);
		}
	}

	return false;
}
//remove Count Query From drop down as the query is deleted
function  reSetDropDowns(queryId)
{

 // var dropDowns= parent.window.document.getElementsByTagName("select");
//	 for(var i=0;i<dropDowns.length;i++)
//	{
	var dropDowns=document.getElementById("countQueryDropDown");
      if(dropDowns.name=="countQueryDropDown")
	  {
			var index=-1;
			var executedQuery=dropDowns.options.length;
			if(executedQuery >0)
			{
				for(var j=0;j<executedQuery;j++)
			  {
				  if(dropDowns.options[j].value==queryId)
					 index=j;
			  }

			}

			 if(index!=-1)
			   dropDowns.remove(index);
			 // if(dropDowns.options.length==0)
				//  dropDowns.disabled=true;
	  }
//	  }

//	}


	 var hiddenDropDown=parent.window.document.getElementById("executedCountQuery");
	 if(hiddenDropDown!=null)
	 {
		  var optns=hiddenDropDown.options.length;
		  var ind=-1;

			if(optns >0)
			{
				for(var j=0;j<optns;j++)
			  {
				  if(hiddenDropDown.options[j].value==queryId)
					 ind=j;
			  }

			}

		   if(ind!=-1)
			 hiddenDropDown.remove(ind);
			 // if(hiddenDropDown.options.length==0)
			//	 hiddenDropDown.disabled=true;
	 }


}
//redirect to dashboard when cancel is clicked
function cancelWorkflow()
{
	document.forms[0].action="\ShowDashboard.do"
	document.forms[0].submit();
}
//SET THE ISDONE FLAG FOR THE checking any update is done on UI
function setIsDoneOnWorkflowNameChange()
{
	if(document.getElementById("name").value=="")
	{
		document.getElementById("isdone").value='false';
	}
	setIsDoneForId();
}
function setIsDoneForId()
{
	if(document.getElementById("id").value==null||document.getElementById("id").value==undefined
			||document.getElementById("id").value=="")
	{
		document.getElementById("isdone").value='false';
	}
}
/*reset  the latestParameterLink for given query title*/
function setlatestExecutionLink(queryTitle)
{
	queryTitle=unescape(unescape(queryTitle));

	var rows =document.getElementById("countTable").rows.length;
	for(var i = 0; i < rows; i++)
	{
		var title=document.getElementById("identifier_"+i);
		var type=document.getElementById("displayQueryType_"+i).value;
		if(title.value==queryTitle&&type=="Count")
		{
				var lableObject=document.getElementById("label_"+i);
				var labelForCQ=document.getElementById("labelForCQ_"+i);
				if(lableObject!=null && lableObject!= undefined)
				{
					lableObject.innerHTML="&nbsp;"
				}
				else if(labelForCQ!=null && labelForCQ!= undefined)
				{
					labelForCQ.innerHTML="&nbsp;"
				}
		}
	}
}
/*reset all the latestParameterLink
 * clears al the counts on project change*/
function setAlllatestExecutionLink()
{

	var rows =document.getElementById("countTable").rows.length;
	for(var i = 0; i < rows; i++)
	{
		var lableObject=document.getElementById("label_"+i);
		var labelForCQ=document.getElementById("labelForCQ_"+i);
		if(lableObject!=null && lableObject!= undefined)
		{
			lableObject.innerHTML="&nbsp;"
		}
		else if(labelForCQ!=null && labelForCQ!= undefined)
		{
			labelForCQ.innerHTML="&nbsp;"
		}


	}
}
//execute count query
function executeGetCountQuery(queryTitle,executionLogId)
{

	setlatestExecutionLink(queryTitle);
	initializeLoadingDiv();

	//setCancelFlag(queryTitle);
	queryTitle=unescape(queryTitle);
	queryTitle=unescape(queryTitle);
	setIsDoneOnWorkflowNameChange();

	if(document.getElementById("isdone").value=='false')
	{
		/*var rows=document.getElementById("table1").rows.length;

		var workflowName=document.getElementById("name").value;

		var workflowName=document.getElementById("name").value;
		var description=document.getElementById("wfDescription").value;
		var identifier="";
		var displayQueryTitle="";
				var expression="";
		for(i=0;i<rows;i++)
		{

			displayQueryTitle=displayQueryTitle+"&displayQueryTitle="+document.getElementById("displayQueryTitle_"+i).value;
			expression=expression+document.getElementById("expression_"+i).value+",";
			var title=document.getElementById("identifier_"+i);
			var object=title.parentNode;
			var tdChildCollection=object.getElementsByTagName('input');
			var queryId=tdChildCollection[2].id;
			identifier=identifier+document.getElementById(queryId).value+",";
		}*/
	saveWorkflow(queryTitle);

	}
	else
	{
		queryTitle=unescape(unescape(queryTitle));
		var numOfRows =document.getElementById("countTable").rows.length;
			for(var count = 0; count < numOfRows; count++)
			{

				var title=document.getElementById("identifier_"+count);
				var type=document.getElementById("displayQueryType_"+count).value;
				if(title.value==queryTitle&&type=="Count")
				{
					var object=title.parentNode;
					var tdChildCollection=object.getElementsByTagName('input');
					var queryId=tdChildCollection[2].id;//object.childNodes[0].id;//object.id;
					//DropDowns(queryTitle);
					//removed for PQ in Wf
					//workflowExecuteGetCountQuery(document.getElementById(queryId).value,executionLogId);
					showPQExecutionPopUp(document.getElementById(queryId).value);
				}
			}
	}
	setCountDropDown = false;
}
//this method save workflow
//if any changes are there before executing a query
//if Query title is given then saving is done before query execution
//else if title is empty or null then execution of workflow is assumed
function saveWorkflow(queryTitle)
{

		var url="SaveWorkflowAjaxHandler.do"/*?workflowName="+workflowName+displayQueryTitle+"&workflowId="+document.getElementById("id").value+"&queryTitle="+encodeURIComponent(queryTitle)+"&identifier="+identifier+"&expression="+ encodeURIComponent(expression)+"&description="+description;*/
		var str = $("form").serialize();
		if(queryTitle!=null &&queryTitle!="")
		{
			queryTitle=unescape(unescape(queryTitle));
			str=str+'&requestForQueryTitle='+encodeURIComponent(queryTitle);

				$.ajax({
			type: "POST",
			url: "SaveWorkflowAjaxHandler.do",
			data: str,
			dataType: 'json',
			success: function(josnresult){
				clearLoadingDiv();
				responseHandler(josnresult);

			},
			error: function(){//alert("error occured while execute GetCount Query");
							}
			});
		}
		else
		{
				$.ajax({
			type: "POST",
			url: "SaveWorkflowAjaxHandler.do",
			data: str,
			dataType: 'json',
			success: function(josnresult){
				runWorkflowResponseHandler(josnresult);

			},
			error: function(){//alert("error occured while execute GetCount Query");
							}
			});

		}

}
function responseHandler(jsonResponse)
{
		//set error message
	 if(jsonResponse.errormessage!=null)
     {
           document.getElementById("errormessage").innerHTML=jsonResponse.errormessage;
		   clearLoadingDiv();
	 }

	//set workflow id
	 if(jsonResponse.workflowId!="")
     {
		 document.getElementById("id").value=jsonResponse.workflowId;
		  document.getElementById("operation").value="edit";

	 }
	 if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
		jsonResponse.errormessage==""))
	{
		document.getElementById("isdone").value=true;

	}

	//set the name identifier mapping
	if(jsonResponse.executionQueryResults!=null)
	  {
		 var num = jsonResponse.executionQueryResults.length;

			for(var i=0;i<num;i++)
			{
				 var queryTitle = jsonResponse.executionQueryResults[i].queryTitle;

				//var nameIdentifier=document.getElementById("table1").rows;
				var numOfRows =document.getElementById("countTable").rows.length;
				for(var count = 0; count < numOfRows; count++)
				{

					var title=document.getElementById("identifier_"+count);
					var type=document.getElementById("displayQueryType_"+count).value;
					if(title.value==queryTitle&&type=="Count")
					{
						var object=title.parentNode;
						var tdChildCollection=object.getElementsByTagName('input');
						var queryId=tdChildCollection[2].id;
						document.getElementById(queryId).value=jsonResponse.executionQueryResults[i].queryId;
						document.getElementById(queryId).id="queryIdForRow_"+jsonResponse.executionQueryResults[i].queryId;

					}
				}



					if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
						jsonResponse.errormessage==""))
					{

					   if(jsonResponse.queryTitle!=null&&jsonResponse.queryTitle==queryTitle)
					   {
						   //setDropDowns(queryTitle);
						   //removed for PQ in Wf
							//workflowExecuteGetCountQuery(jsonResponse.executionQueryResults[i].queryId,0);
							showPQExecutionPopUp(jsonResponse.executionQueryResults[i].queryId);

					   }
					}

			}
	   }

}

function showPQExecutionPopUp(queryId)
{
	var projectId=document.getElementById("selectedProject").value;
	var url;
	if(queryId=="-1")// executed for workflow
	{
		document.getElementById("executeType").value="executeWorkFlow";
		url='RetrieveParamQueries.do?workflowId='+document.getElementById("id").value+"&selectedProject="+projectId+"&executeType=executeWorkFlow";
	}
	else
	{
		document.getElementById("executeType").value="";
		document.getElementById("requestForId").value=queryId;
		url='RetrieveParamQueries.do?queryId='+queryId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId;
	}


		$.ajax({
		type: "POST",
		url: url,
		dataType: 'json',
		success: function(jsonResponse){
			showPQPopUpResponseHandler(jsonResponse);
		},
		error: function(jsonResponse){
			//alert("error occured in showing parameterized popup");
		}
		});


}
function showPQPopUpResponseHandler(jsonResponse)
{
	if(jsonResponse!=null&&jsonResponse!="")
	{
		if(jsonResponse.showPopup=="showPopup")
		{
				 var queryId=jsonResponse.queryId;
				 //call the default execution with popup
				var executeType=document.getElementById("executeType").value;
				 var projectId=document.getElementById("selectedProject").value;
				 var url='ExecutePQsInWorkflow.do?queryId='+queryId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId+"&executeType="+executeType;
		$.ajax({
		type: "POST",
		url: url,
		dataType: 'json',
		success: function(jsonResponse){
			executePQsInWorkflowResponseHandler(jsonResponse);
		},
		error: function(jsonResponse){
			//alert("error occured show PQP Up ResponseHandler");
		}
		});


		}
		else
		 {
			 //call the default execution without popup
			 workflowExecuteGetCountQuery(jsonResponse.queryId,0);
		 }
	}


}

function executePQsInWorkflowResponseHandler(jsonResponse)
{
	if(jsonResponse!=null&&jsonResponse!="")
	{
		if(jsonResponse.result.queryIdString!="")
		{
			var projectId=document.getElementById("selectedProject").value;
			var url='LoadingParameters.do?queryIdString='+jsonResponse.result.queryIdString+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId;
			if(jsonResponse.result.queryIdString==undefined)
			{
				pvwindow3=dhtmlmodal.open('Parameterized Count Query', 'iframe', url,'Parameterized Count Query', 'width=950px,height=450px,center=1,resize=1,scrolling=1');
			}
			else
			{
					pvwindow3=dhtmlmodal.open('Parameterized Count Queries', 'iframe', url,'Parameterized Count Queries', 'width=950px,height=400px,center=1,resize=1,scrolling=1');

			}
		}

			workflowResponseHandler(jsonResponse);

	}
}
function workflowResponseHandler(jsonResponse)
{
          if(jsonResponse.result.executionQueryResults!=null)
          {
             var num = jsonResponse.result.executionQueryResults.length;
				for(var i=0;i<num;i++)
				{
					 var queryId = jsonResponse.result.executionQueryResults[i].queryId;
					 var queryResult = jsonResponse.result.executionQueryResults[i].queryResult;
					 var status = jsonResponse.result.executionQueryResults[i].status;
					 var executionLogId = jsonResponse.result.executionQueryResults[i].executionLogId;
						if(queryResult!=-1)
						{
							var identifier=document.getElementById("queryIdForRow_"+queryId);
							var object=identifier.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var qTitle = tdChildCollection[1].value;
							var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
							var selectedquery=selectedqueryId.split("_");
							queryIndex=selectedquery[1];
							//for setting the execution id

							var queryExecId=tdChildCollection[3].id;
							if(queryExecId!=null&&queryExecId!=undefined)
							{
							//removed To be changed by rinku /rukhsana
							 /*  if(document.getElementById(queryExecId).value==0 ||
									 document.getElementById(queryExecId).value=='' || document.getElementById(queryExecId).value==null)
								{
								  // setCountDropDown=false;
								  //setDropDowns(qTitle);
								} */

								if( queryResult>0&&jsonResponse.result.projectId==document.getElementById('selectedProject').value&&document.getElementById('selectedProject').value!=-1)
								{
									setDropDowns(qTitle,queryId);
									//setCountDropDown=true;
								}
								if(queryResult==0)
								{
									removeFromDropDown(queryId);
								}
								 document.getElementById(queryExecId).value=executionLogId;

							}
							if(document.getElementById("cancelajaxcall_"+queryIndex).value=='false'&&
								jsonResponse.result.projectId==document.getElementById('selectedProject').value)
							{
								var lableObject=document.getElementById("label_"+queryIndex);
								var cqlableObject=document.getElementById("labelForCQ_"+queryIndex);

								if(lableObject!=null)
								{
									var latestParameterLink=document.getElementById('latestParameterLink_'+queryIndex);
									if(latestParameterLink==null||latestParameterLink==undefined)
									{
											var url="ShowParamRecentQueries.do?queryId=" + queryId+"&queryExecutionId=" + executionLogId+"&pageOf=pageOfRecentQuery&AjaxRequest=Yes";
											var dd="latestParameterLink_"+queryIndex;

											lableObject.innerHTML='&nbsp;<a href="'+url+'" title="Query Execution Details" rel="'+url+'"  id="'+dd+'">'+queryResult+'</a>';

											reInatailizeJtip('latestParameterLink_'+queryIndex);
										//JT_init();

									}
									else
									{
											latestParameterLink.innerHTML=queryResult;
									}

								}
								else if(cqlableObject!=null && cqlableObject!=undefined)
								{
									//for CQ
									cqlableObject.innerHTML="&nbsp;"+queryResult;
								}


							}

						}


						if(status!="Completed"&&status!="Cancelled"&&status!="Query Failed"&&document.getElementById("cancelajaxcall_"+queryIndex).value=='false'
							&&status!="COMPLETED_WITH_WARNING"&& jsonResponse.result.projectId==document.getElementById('selectedProject').value)
						{
							workflowExecuteGetCountQuery(queryId,executionLogId);

						}

						if((status=="Completed"||status=="Cancelled"||status=="Query Failed"||status=="COMPLETED_WITH_WARNING")
							&&jsonResponse.result.projectId==document.getElementById('selectedProject').value
							&&document.getElementById("cancelajaxcall_"+queryIndex).value=='false')
						{
							changeExecuteLinkToExecute(queryId,0,status);

						}
						else if(jsonResponse.result.projectId==document.getElementById('selectedProject').value)
						{
							changeLinkToCancel(queryId,executionLogId);
						}
					}
          }
		  clearLoadingDiv();

}


function workflowExecuteGetCountQuery(queryId,executionLogId)
{
	var projectId=document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&state=start&executionLogId="+executionLogId+"&selectedProject="+projectId+"&workflowId="+document.getElementById("id").value+"&queryId="+queryId;
		$.ajax({
		type: "POST",
		url: url,
		data:"AjaxRequest=yes",
		dataType: 'json',
		success: function(jsonResponse){
			workflowResponseHandler(jsonResponse);
		},
		error: function(jsonResponse){
			//alert("error occured workflowExecuteGetCountQuery");
		}
		});

}

function changeLinkToCancel(queryId,executionLogId)
{

	var identifier=document.getElementById("queryIdForRow_"+queryId);
	var object=identifier.parentNode;
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;
	var selectedquery=selectedqueryId.split("_");

	index=selectedquery[1];
	var object=document.getElementById("execute_"+index);

	//To get the query title field of document
	var queryTitle=document.getElementById("displayQueryTitle_"+index).value;

	if(object!=null&&object!=undefined)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Cancel Execution","images/advancequery/cancel.png","cancel_"+index,"javascript:cancelGetCountQuery('"+queryId+"','"+executionLogId+"','"+false+"')"));
		disableDeleteLink(index);
	}
	 imageForProgressiveCounts(index);
}
function changeExecuteLinkToExecute(queryId,executionLogId,status)
{
	var identifier=document.getElementById("queryIdForRow_"+queryId);
	var object=identifier.parentNode;
	var tdChildCollection=object.getElementsByTagName('input');

	var selectedqueryId=tdChildCollection[0].id;
	var selectedquery=selectedqueryId.split("_");

	index=selectedquery[1];
	var object=document.getElementById("cancel_"+index);

	//To get the query title field of document
	var queryTitle=document.getElementById("displayQueryTitle_"+index).value;
	 var t =	escape(queryTitle);
	if(object!=null)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Execute ","images/advancequery/execute-button-1.PNG","execute_"+index,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
		enableDeleteLink(index);
	}
	if(status=='Completed')
	{
		imageForCompletedCounts(index);
	}
	 else if(status=='Cancelled')
	{
		imageForCancelled(index);
	}
		if(status=='Query Failed')
	{
		imageForFailed(index);
	}
		else if(status=='COMPLETED_WITH_WARNING')
		{
			imageForCompletedWithWarning(index);
		}
}

function removeFromDropDown(queryId)
{
	var dropDowns= document.getElementsByTagName("select");
	for(var i=0;i<dropDowns.length;i++)
	{
	   if(dropDowns[i].name=="countQueryDropDown")
	   {
		    var executedQuery=dropDowns[i].options.length;
			if(executedQuery >0)
			{
			  for(var j=0;j<executedQuery;j++)
			  {
					if(dropDowns[i].options[j]!=null&& dropDowns[i].options[j]!=undefined)
				    {
					  if(dropDowns[i].options[j].value==queryId)
					  {
						dropDowns[i].options[j]=null;
					  }
					}
			  }
			}
	   }
	}
}
function setDropDowns(queryTitle,queryId)
{

//	var numOfRows =document.getElementById("table1").rows.length;
//
//	 var dropDowns= document.getElementsByTagName("select");
//	for(var i=0;i<dropDowns.length;i++)
//	{
//
//	   if(dropDowns[i].name=="countQueryDropDown")
//	 {
	var dropDowns =document.getElementById("countQueryDropDown");

	if(dropDowns!=null &dropDowns!=undefined )
	{
		   var bool=false;
		   var executedQuery=dropDowns.options.length;
			if(executedQuery >0)
			{
				for(var j=0;j<executedQuery;j++)
			  {

				  if(dropDowns.options[j].value==queryId)
				  {

					  bool=true;
				  }
			  }

			}

			if(bool== false)
			{
			  var optn = parent.window.document.createElement("OPTION");
			 optn.text=queryTitle;
			 optn.value=queryId;
			 optn.title=queryTitle;
			 dropDowns.options.add(optn);
			 dropDowns.disabled=false;
			}
	}
	 // }

	// Add query to hidden dropdown
        var add=true;
	    var hiddenDropDown=document.getElementById("executedCountQuery");
	    var num=hiddenDropDown.options.length;
		if(num>0)
		{
		  for(var t=0;t<num;t++)
		  {

		   if(hiddenDropDown.options[t].value==queryId)
			  add=false;

		  }

		}

		if(add==true)
		{
		 var optn = parent.window.document.createElement("OPTION");
	     optn.text=queryTitle;
	     optn.value=queryId;
	     optn.title=queryTitle;
	     hiddenDropDown.options.add(optn);
	     hiddenDropDown.disabled=false;
		}
 // }
}
//cancel execution of count query
function cancelGetCountQuery(queryId,executionLogId,removeExecutedCount)
{

	var identifier=document.getElementById("queryIdForRow_"+queryId);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
	var selectedquery=selectedqueryId.split("_");

	index=selectedquery[1];

	document.getElementById("cancelajaxcall_"+index).value='true';

	var projectId=document.getElementById("selectedProject").value;
	var url="WorkflowAjaxHandler.do?operation=execute&queryId="+queryId+'&state='+'cancel'+"&executionLogId="+executionLogId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId+"&removeExecutedCount="+removeExecutedCount;

		$.ajax({
		type: "POST",
		url: url,
		dataType: 'json',
		data:"AjaxRequest=yes",
		success: function(jsonResponse){
			cancelExecuteQuery(jsonResponse);
		},
		error: function(jsonResponse){
			//alert("error occured cancel query execution");
		}
		});



}
//cancel execution of count query handler
function cancelExecuteQuery(jsonResponse)
{
		  if(jsonResponse.removeExecutedCount!=null)
		  {
			var queryId = jsonResponse.queryId;
			var identifier=document.getElementById("queryIdForRow_"+queryId);
			var object=identifier.parentNode;
			var tdChildCollection=object.getElementsByTagName('input');
			var selectedqueryId=tdChildCollection[0].id;
			var selectedquery=selectedqueryId.split("_");
			index=selectedquery[1];
			removeCountResults1(index);
		  }

		  	if(jsonResponse.queryId!=null)
			{
				var queryId = jsonResponse.queryId;
				var queryId = jsonResponse.queryId;
				var identifier=document.getElementById("queryIdForRow_"+queryId);
				var object=identifier.parentNode;
				var tdChildCollection=object.getElementsByTagName('input');
				var selectedqueryId=tdChildCollection[0].id;
				var selectedquery=selectedqueryId.split("_");
				index=selectedquery[1];
				changeExecuteLinkToExecute(queryId,0,'Cancelled');
				document.getElementById("cancelajaxcall_"+index).value='false';

			}


}
//populate previously executed project
function setPreviousProject()
{
	previousProject=document.getElementById('selectedProject').value;
}
//set the counts on page load
function retrieveCounts()
{

	var rows=document.getElementById("countTable").rows.length;
	if(initalizeflagforLoadingDiv())
	{
		initializeLoadingDiv();
	}
	for(var i=0;i<rows;i++)
	{
		var executionId=document.getElementById("queryExecId_"+i).value;
		var id=document.getElementById("queryExecId_"+i).id;
		var object=document.getElementById("queryExecId_"+i).parentNode;
		var tdChildCollection=object.getElementsByTagName('input');
		var queryIdForRow=tdChildCollection[2].id;
		var queryId=document.getElementById(queryIdForRow).value;
		if(executionId!='0' && executionId!="")
		{
			workflowExecuteGetCountQuery(queryId,executionId);
		}
	}
}//used for not executed query when switches from the
//getdata/count/view result
// if project selected is one for whilch loading div is not required
function initalizeflagforLoadingDiv()
{
	var rows=document.getElementById("countTable").rows.length;

	if(rows>0)
	{
			for(var i=0;i<rows;i++)
			{
				var executionId=document.getElementById("queryExecId_"+i).value;
				if(executionId!='0' && executionId!="")
				{
					return true;

				}
			}
	}
	return false;
}


//this methos udsed in running workflow
function runWorkflow()
{
	if(document.getElementById("countTable").rows.length==0)
	{
		///alert("Add some queries to the workflow");
		//var url		='pages/advancequery/content/search/querysuite/emptyWorkflow.html';
		//	emptwf	= dhtmlmodal.open('Empty workflow', 'iframe', url,'Empty workflow', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
			return;
	}
	initializeLoadingDiv();
	setAlllatestExecutionLink();
	setIsDoneOnWorkflowNameChange();
	if(document.getElementById("isdone").value=='false')
	{
		saveWorkflow("");
	}
	else
	{
		//runSavedWorkflow();
		 showPQExecutionPopUp("-1");
		 	document.getElementById("requestForId").value=-1;
	}


}
function runWorkflowResponseHandler(jsonResponse)
{

	 if(jsonResponse.errormessage!=null)
	 {
		   document.getElementById("errormessage").innerHTML=jsonResponse.errormessage;
		   clearLoadingDiv();
	 }

	//set workflow id
	 if(jsonResponse.workflowId!="")
     {
		 document.getElementById("id").value=jsonResponse.workflowId;
		 document.getElementById("operation").value="edit";

	 }
	 if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
		jsonResponse.errormessage==""))
	{
		document.getElementById("isdone").value=true;

	}


	//set the name identifier mapping
	if(jsonResponse.executionQueryResults!=null)
	  {
		 var num = jsonResponse.executionQueryResults.length;
			for(var i=0;i<num;i++)
			{
				 var queryTitle = jsonResponse.executionQueryResults[i].queryTitle;


				//var nameIdentifier=document.getElementById("table1").rows;
					var numOfRows =document.getElementById("countTable").rows.length;
					for(var count = 0; count < numOfRows; count++)
					{

						var title=document.getElementById("identifier_"+count);
						if(title.value==queryTitle)
						{
							var object=title.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var queryId=tdChildCollection[2].id;
							document.getElementById(queryId).value=jsonResponse.executionQueryResults[i].queryId;
							document.getElementById(queryId).id="queryIdForRow_"+jsonResponse.executionQueryResults[i].queryId;

						}
					}



			}
			if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
						jsonResponse.errormessage==""))
					{
						 //runSavedWorkflow();
						  showPQExecutionPopUp("-1");
					}
	   }

}
//For data Query Execution
function executeGetDataQuery(dataQueryId)
{

	var countQueryId = document.getElementById("countQueryDropDown");
    if(countQueryId.selectedIndex== -1)
	{
	  //alert("please first execute a get count query");
	  dhtmlmodal.open('Execute Discover patient cohorts query', 'iframe', './pages/advancequery/content/search/querysuite/executeGetCountPopup.jsp','Execute  Discover patient cohorts query', 'width=400px,height=120px,center=1,resize=1,scrolling=1');
	  return;
	}
	var selectedProject = document.getElementById('selectedProject').value;
	if(selectedProject==-1)
		{
			var url		='pages/advancequery/content/search/querysuite/SelectProjectPopUp.html';
			pvwindow	= dhtmlmodal.open('Select a Project', 'iframe', url,'Select a Project','width=400px,height=50px,center=1,resize=0,scrolling=1,menubar=0,toolbar=0');
			return;
		}
	else
	{
		  var countqId =countQueryId.options[countQueryId.selectedIndex].value;
		  var hiddnid=  document.getElementById("dataQueryId");
		  var hid=   document.getElementById("countQueryId")
		   if(hiddnid!=null)
		  {
			hiddnid.value=dataQueryId;
			hid.value=countqId;
		  }

		 setIsDoneOnWorkflowNameChange();

		if(document.getElementById("isdone").value=='false')
		{
			var str = $("form").serialize();
				$.ajax({
				type: "POST",
				url: "SaveWorkflowAjaxHandler.do",
				data: str,
				success: function(){
					showPqDataPopUp(dataQueryId);

				}

				});
		}
		else
		{
			showPqDataPopUp(dataQueryId);
		}

	  }
}
//shows poup for data Query execution
function showPqDataPopUp(queryId)
{

	 //call the default execution with popup
	 var projectId=document.getElementById("selectedProject").value;
	 var url='ExecuteDataPQ.do?queryId='+queryId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId;

		$.ajax({
			type: "POST",
			url: url,
			dataType: 'json',
			success: function(josnresult){
				executeDataPQExecutionHandler(josnresult);

			},
			error: function(){
				//alert("error occured while execute GetCount Query");
				}
			});

}

function executeDataPQExecutionHandler(jsonResponse)
{
   	var projectId=document.getElementById("selectedProject").value;
	var workflowId=document.getElementById("id").value;

	if(jsonResponse!=null&&jsonResponse!="")
	{
		//alert(jsonResponse.result.queryIdString);
		//alert("jsonResponse.result.queryIdString!="+jsonResponse.result.queryIdString!="");
		if(jsonResponse.result.queryIdString!="")
		{
			if(jsonResponse.result.isParametrized==true)
			{
			//htmlContent=jsonResponse.result.HTMLContents;
				//var url='ParamerizedQueryPopUp.do?queryIdString='+jsonResponse.result.queryIdString+'&selectedProject='+projectId+'&workflowId='+workflowId+'&queryType=Data';

				var projectId=document.getElementById("selectedProject").value;
				var url='LoadingParameters.do?queryIdString='+jsonResponse.result.queryIdString+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId+'&queryType=Data';
				//pvwindow3=dhtmlmodal.open('Parameterized Queries', 'iframe', url,'Parameterized Queries', 'width=900px,height=325px,center=1,resize=1,scrolling=1');


				pvwindow4 = dhtmlmodal.open('Parameterized Data Query', 'iframe', url,'Parameterized Data Query', 'width=900px,height=310px,center=1,resize=1,scrolling=1');
			}
			else
			{
				//TO DO Verify corretness ... ask pavan
				var dataQueryId = jsonResponse.result.queryIdString;
				var countqId= document.getElementById("countQueryId").value;

				 var actionURL = "buttonClicked=ViewResults";
			     var url = "ValidateQuery.do?dataQueryId="+dataQueryId+"&countQueryId="+countqId+'&selectedProject='+projectId+'&workflowId='+workflowId;
				 $.ajax({
					type: "POST",
					url: url,
					data: actionURL,
					success: function(result){
						displayValidationMesage(result);

					},
					error: function(){
						//alert("error occured while execute GetCount Query");
						}
					});
			}

		}
	}

	function displayValidationMesage(response)
	{

	  if(response=="ViewResults")
	  {
	  	var dataQueryId= document.getElementById("dataQueryId").value;
	  	var countQueryId= document.getElementById("countQueryId").value;
	  	var identifier=document.getElementById("queryIdForRow_"+countQueryId);
		var object=identifier.parentNode;
		var tdChildCollection=object.getElementsByTagName('input');
		var executinIDElement=tdChildCollection[3].id;
		var exId= document.getElementById(executinIDElement).value;
		var projectId=document.getElementById("selectedProject").value;
	    var workflowId=document.getElementById("id").value;
		var workflowName =document.getElementById("name").value;
		document.forms[0].action="\QueryResultsView.do?dataQueryId="+dataQueryId+"&queryExecutionId="+exId+"&selectedProject="+projectId+"&workflowId="+workflowId+"&workflowName="+workflowName;
	   document.forms[0].submit();
	 }

	}
}
function showResetCountPopup()
{
	document.getElementById("executedForProject").value=document.getElementById('selectedProject').value;;
	onProjectChange();

}

// added for showing counts on changing project
function onProjectChange()
{
		if(document.getElementById("id").value!=""&&document.getElementById("id").value!=null
		&&document.getElementById("id").value!=undefined)
	{
		initializeLoadingDiv();
		initUI();
		setIsDoneOnWorkflowNameChange();
		if(document.getElementById("isdone").value=='false')
		{
				var str = $("form").serialize();
				$.ajax({
				type: "POST",
				url: "SaveWorkflowAjaxHandler.do",
				url: "SaveWorkflowAjaxHandler.do",
				data: str,
				dataType: 'json',
				success: function(josnresult){
					projectChangeHandler(josnresult);

				},
				error: function(){
					//alert("error occured while changing project");
					}
				});
		}
		else
		{
			getCountsForChangedProject();
		}
	}
}
function projectChangeHandler(jsonResponse)
{

	 if(jsonResponse.errormessage!=null)
	 {
		   document.getElementById("errormessage").innerHTML=jsonResponse.errormessage;
		   clearLoadingDiv();
	 }

	//set workflow id
	 if(jsonResponse.workflowId!="")
     {
		 document.getElementById("id").value=jsonResponse.workflowId;
		 document.getElementById("operation").value="edit";

	 }
	 if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
		jsonResponse.errormessage==""))
	{
		document.getElementById("isdone").value=true;

	}


	//set the name identifier mapping
	if(jsonResponse.executionQueryResults!=null)
	  {
		 var num = jsonResponse.executionQueryResults.length;
			for(var i=0;i<num;i++)
			{
				 var queryTitle = jsonResponse.executionQueryResults[i].queryTitle;


				//var nameIdentifier=document.getElementById("table1").rows;
					var numOfRows =document.getElementById("countTable").rows.length;
					for(var count = 0; count < numOfRows; count++)
					{

						var title=document.getElementById("identifier_"+count);
						if(title.value==queryTitle)
						{
							var object=title.parentNode;
							var tdChildCollection=object.getElementsByTagName('input');
							var queryId=tdChildCollection[2].id;
							document.getElementById(queryId).value=jsonResponse.executionQueryResults[i].queryId;
							document.getElementById(queryId).id="queryIdForRow_"+jsonResponse.executionQueryResults[i].queryId;

						}
					}


					if((jsonResponse.workflowId!=null||jsonResponse.workflowId!="")&&(jsonResponse.errormessage==null||
						jsonResponse.errormessage==""))
					{
						 getCountsForChangedProject();
					}

			}
	   }

}

function  getCountsForChangedProject()
{
	var projectId=document.getElementById("selectedProject").value;
	var url="ProjectChangeAjaxHandler.do?workflowId="+document.getElementById("id").value+"&selectedProject="+projectId;
	$.ajax({
		type: "POST",
		url: url,
		dataType: 'json',
		success: function(josnresult){
			getCountsForChangedProjectResponseHandler(josnresult);

		},
		error: function(){
			//alert("error occured while execute GetCount Query");
			}
		});
}
function getCountsForChangedProjectResponseHandler(jsonResponse)
{
	 // var jsonResponse = eval('('+ response+')');
	setAlllatestExecutionLink();
	   if(jsonResponse.executionQueryResults!=null&&jsonResponse.executionQueryResults!="")
       {
		    var num = jsonResponse.executionQueryResults.length;

				for(var i=0;i<num;i++)
				{
					var queryId = jsonResponse.executionQueryResults[i].queryId;
					var queryResult = jsonResponse.executionQueryResults[i].queryResult;
					var status = jsonResponse.executionQueryResults[i].status;
					var executionLogId = jsonResponse.executionQueryResults[i].executionLogId;
					var identifier=document.getElementById("queryIdForRow_"+queryId);
					if(identifier!=null&&identifier!=undefined)
					{
						var object=identifier.parentNode;

						var tdChildCollection=object.getElementsByTagName('input');
						var qTitle = tdChildCollection[1].value;
						var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
						var selectedquery=selectedqueryId.split("_");
						queryIndex=selectedquery[1];
						//removeCountResults1(queryIndex);
						if(executionLogId!="0" && executionLogId!="")
						{
							document.getElementById("cancelajaxcall_"+queryIndex).value='false';
							workflowExecuteGetCountQuery(queryId,executionLogId);
						}
					}
					else
					{
						clearLoadingDiv();
					}
				}

	   }

		else
		{
			clearLoadingDiv();
		}
}
//clears al the counts on project change
function removeAllCounts()
{
	var rows=document.getElementById("countTable").rows.length;
	for(var i=0;i<rows;i++)
	{

		var lableObject=document.getElementById("label_"+i);
		var labelForCQ=document.getElementById("labelForCQ_"+i);

		if(lableObject!=null && lableObject!= undefined)
		{
			lableObject.innerHTML="&nbsp;"
		}
		else if(labelForCQ!=null && labelForCQ!= undefined)
		{
			labelForCQ.innerHTML="&nbsp;"
		}
	}
}
//reinitailizes UI on project change
function initUI()
{

	var rows=document.getElementById("countTable").rows.length;
	for(var i=0;i<rows;i++)
	{
		changeCancelLinkExecute(i);
	}
	clearSelBoxList();
}
//clrea executed queriees drop down on project change
function clearSelBoxList()
{
	var dropDown= document.getElementById("countQueryDropDown");


	 if(dropDown!=null&dropDown!=undefined)
	{
		if(dropDown != null)
		 {
			while(dropDown.length > 0)
			 {
				dropDown.remove(dropDown.length - 1);
			  }
			// dropDown.disabled=true;
		  }
	}

}
//change cancel link to execute
function changeCancelLinkExecute(index)
{

		var queryTitle=document.getElementById("displayQueryTitle_"+index).value;
			var object=document.getElementById("cancel_"+index);
	//document.getElementById("cancelajaxcall_"+index).value='false';
	 var t =	escape(queryTitle);
	if(object!=null)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Execute ","images/advancequery/execute-button-1.PNG","execute_"+index,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
		enableDeleteLink(index);
	}
	imageForNotRunning(index);
}

function showParameter(obj,url)
{
	obj.href=url;
}
/*set number of queries*/
function setQueryCount()
{
	var noOfCountQueries=document.getElementById("countTable").rows.length;
	document.getElementById("countNum").innerHTML="&nbsp;("+noOfCountQueries+")";


	var noOfDataQueries=document.getElementById("dataTable").rows.length;
	document.getElementById("dataNum").innerHTML="&nbsp;("+noOfDataQueries+")";

}

function SlideUpRun(obj)
{
 //  slider = document.getElementById(obj);
  // slider.style.height = '83px';
  var windowHeight=( screen.height * 8.1)/100;

  $("#"+obj).animate({ height: windowHeight}, 300);

}

function SlideDownRun(obj,tableObj)
{
   slider = document.getElementById(obj);
  // alert(document.getElementById(tableObj).rows.length);
   if( document.getElementById(tableObj).rows.length>=3)
	{
		//slider.style.height = '100%';
		  $("#"+obj).animate({ height:'100%'}, 300);


	}
}

function getbrowserName()
{
	var ua=	 navigator.userAgent.toLowerCase()
	var browserName;

	if ( ua.indexOf( "opera" ) != -1 )
	{
		browserName = "opera";
	}
	else if ( ua.indexOf( "msie" ) != -1 )
	{
		browserName = "msie";
	}
	else if ( ua.indexOf( "safari" ) != -1 )
		{
	browserName = "safari";
	}
	else if ( ua.indexOf( "mozilla" ) != -1 )
	{
		if ( ua.indexOf( "firefox" ) != -1 )
		{
		browserName = "firefox";
		}
		else
		{
		browserName = "mozilla";
		}
	}
		return browserName;
}

function setTextAreasize()
{
	var browserName=getbrowserName();

	if(browserName=='firefox'||browserName=='mozilla')
	{
		document.getElementById("wfDescription").rows="1";
	}

}
function reInatailizeJtip(obj){
	//$("#"+obj).hover(function(){JT_show(this.href,this.id,this.name)},function(){$('#JT').remove()})
      ///     .click(function(){return false});
	$("#"+obj).cluetip({
  cluetipClass: 'jtip',
  dropShadow: false,
  sticky: true,
 width:'600px',
height:'200px',
 closeText: '<img src="images/cross.gif" title="Close" border="0" />',
showTitle: true,
closePosition: 'title',
titleAttribute:   'title',
    arrows:           true,
	waitImage:        true,
	mouseOutClose: true,
	ajaxCache:        false,


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
	$('#paramHeaderDiv').toggle(function(){hideDivOnPopup('paramDiv')},function(){showDivOnPopup('paramDiv')});
	$('#constrHeaderDiv').toggle(function(){showDivOnPopup('constrDiv')},function(){hideDivOnPopup('constrDiv')});

	hideAllDivs();
	createAlternateSripts();
	},
	truncate:         0,
	  cursor:           ''

});
}
function SlideDownRunOnPopup(obj)
{
	   slider = document.getElementById(obj);
	     slider.style.display="block";
}
function hideAllDivs()
{
	var constrDiv=document.getElementById("constrDiv");
	if(constrDiv!=null&&constrDiv!=undefined)
	{
		constrDiv.style.display="none";
	}

}
function hideDivOnPopup(elementid)
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
function showDivOnPopup(elementid)
{
	if(elementid=='constrDiv')
	{
		  var x = document.getElementById("constrImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);

		  SlideDownRunOnPopup("constrDiv");


	}
	else
	{
		  var x = document.getElementById("paramImg");
		 var v = x.getAttribute("src");
		  v = "images/advancequery/arrow_open.png";
		  x.setAttribute("src", v);

		  SlideDownRunOnPopup("paramDiv");



	}
	return;
}
/*function saveAs(saveAsforqueryId,title)
{

	document.getElementById('saveAsquerytitle').value=title;
	document.getElementById('saveAsqueryId').value=saveAsforqueryId;
	saveAsWindow=dhtmlmodal.open('save as query', 'iframe', './pages/advancequery/content/search/querysuite/SaveAsQuery.jsp','Create copy of query ', 'width=400px,height=130px,center=1,resize=1,scrolling=0');

}*/
//this method creates the alternate sripts.
function createAlternateSripts()
{
	if(document.getElementById('constrDiv')!=null&&document.getElementById('constrDiv')!=undefined)
	{
		$("#constrDiv:table tr:even").addClass("rowBGGreyColor1");
	}

}
/*creates copy of query*/
function createCopy()
{
	var remaneAs=document.getElementById('createCopy').value;
	var oldId=document.getElementById('saveAsqueryId').value;

	$.ajax({
				type: "POST",
				url: "SaveAsQuery.do?renameAs="+unescape(remaneAs)+"&oldId="+oldId,
				dataType: 'json',
				success: function(josnresult)
				{
					 document.getElementById("errormessage").innerHTML="";
					 if(josnresult.errormessage!=null)
					 {
						   document.getElementById("errormessage").innerHTML=josnresult.errormessage;
						   unblockUI();
					 }
					 else
					 {
						clearSelBox(document.getElementById("queryId"));
						clearSelBox(document.getElementById("queryTitle"));
						clearSelBox(document.getElementById("queryType"));
						addOption(document.getElementById("queryId"),""+josnresult.queryId,josnresult.queryId);
						addOption(document.getElementById("queryTitle"),josnresult.renameAs,josnresult.renameAs);
						addOption(document.getElementById("queryType"),"Count","Count");
						addQuery();
						unblockUI();
					 }
				}

			});

}
/*unblocks UI*/
function unblockUI()
{
	$.unblockUI();
}
/*adds data in thesel select box*/
function addOption(theSel, theText, theValue)
{
	var optn = parent.window.document.createElement("OPTION");
	optn.text=theText;
	optn.value=theValue;
	theSel.options.add(optn);
}
/*clears the selboxObj*/
function clearSelBox(selBoxObj)
{
	if(selBoxObj != null)
	 {
		while(selBoxObj.length > 0)
		 {
			selBoxObj.remove(selBoxObj.length - 1);
		  }
	  }
}
/*enables enter key*/
function enableEnterKey(e)
{
     var key;
     if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox
	if(key==13)
	{
		createCopy();
	}
	else
	{
		  return (key != 13);
	}

}
