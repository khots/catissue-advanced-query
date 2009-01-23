
// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addRowToTable(tableId,columnContents,operandsTdContent,operatorsTdContent)
{
	var tableObj=document.getElementById(tableId);
	var rowObj=document.createElement("tr");
	rowObj.height="22";
	rowObj.className="td_bgcolor_white";
	var columnObj;
	var columnCount=columnContents.length;
	var id=0;
	for(var counter=0;counter<columnCount-2;counter++)
	{
		if(columnContents[counter]!=null)
		{
			columnObj=document.createElement("td");
			if(counter==0)
			{
				columnObj.width="10";//set width for checkbox control

			}
			columnObj.className="content_txt";
			columnObj.appendChild(columnContents[counter]);
			if(columnContents[counter].name=="selectedqueryId")
			{
				id=columnContents[counter].value;
			}
			rowObj.appendChild(columnObj);
		}
	}
		
	//Create all the hidden controls and add them to a "td"
	var operandsTd=document.createElement("td");
	operandsTd.width="100"
	
	var tble1=document.createElement("table");
	tble1.depth="3";
	var tbody1=document.createElement("tbody");
	tbody1.depth="5";
	var operandsTr=document.createElement("tr");
	var operandsTd1=document.createElement("td");
	var operandsTd2=document.createElement("td");
	var operandsTd3=document.createElement("td");

	var queryControls=document.getElementById("table1").rows;
	var queryCount=0;
	if(queryControls!=null && queryControls!=undefined)
	{
		queryCount=queryControls.length;
	}
	
	var operandsControl=createHiddenElement("operands","operands_"+queryCount,operandsTdContent);	
	operandsTd2.appendChild(operandsControl);
	
	var operatorControl=createHiddenElement("operators","operators_"+queryCount,operatorsTdContent);
	operandsTd2.appendChild(operatorControl);
	
	var queryTitleControl;
	var queryTypeControl;
	queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,(columnContents[5]));
	queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,(columnContents[6]));
	operandsTd2.appendChild(queryTitleControl);
	operandsTd2.appendChild(queryTypeControl);
	operandsTd2.width="4";
	operandsTd1.appendChild(createLink("Execute ","execute_"+queryCount,"javascript:executeGetCountQuery('"+id+"','"+0+"')"));
	operandsTd3.appendChild(createLink("Delete ","delete_"+queryCount,"javascript:deleteWorkflowItem('"+queryCount+"')"));
	operandsTd3.appendChild(createHiddenElement("cancelajaxcall","cancelajaxcall_"+(queryCount),'false'));

	operandsTr.appendChild(operandsTd1);
	operandsTr.appendChild(operandsTd2);
	operandsTr.appendChild(operandsTd3);
	tbody1.appendChild(operandsTr);
	tble1.appendChild(tbody1);
	operandsTd.appendChild(tble1);

	rowObj.appendChild(operandsTd);
	
	/*var reorderTd=document.createElement("td");
	reorderTd.align="center";

	var tble2=document.createElement("table");
	var tbody2=document.createElement("tbody");
	tble2.cellspacing="0";
	tble2.cellpadding="0"

	var trInsideTd=document.createElement("tr");
	var reorderTd1=document.createElement("td");
	var reorderTd2=document.createElement("td");
	var reorderTd3=document.createElement("td");

	var trImgup=createImageElement("images/advancequery/ic_up.gif","up_"+queryCount);//new Image();
	//trImgup.src = "images/advancequery/ic_up.gif";
	trImgup.onMouseOver="Tip('Move Up')";
	reorderTd1.appendChild(trImgup);
	reorderTd2.width="2";
	var trImgDown=createImageElement("images/advancequery/ic_down.gif","down_"+queryCount);//new Image( );
	//trImgDown.src = "images/advancequery/ic_down.gif";
	//trImgDown.onMouseOver="Tip('Move Down')";
	reorderTd3.appendChild(trImgDown);
	
	trInsideTd.appendChild(reorderTd1);
	trInsideTd.appendChild(reorderTd2);
	trInsideTd.appendChild(reorderTd3);
	tbody2.appendChild(trInsideTd);
	tble2.appendChild(tbody2);
	reorderTd.appendChild(tble2);
	rowObj.appendChild(reorderTd);*/
	tableObj.appendChild(rowObj);
}

function hasInnerText()
{
	var hasInnerText =(document.getElementsByTagName("body")[0].innerText != undefined) ? true : false;
	return hasInnerText;
}

function addQuery()
{
	var queryIds=document.getElementById("queryId").options;
	var queryTitles=document.getElementById("queryTitle").options;
	var queryTypes=document.getElementById("queryType").options;
	var queryCount=document.getElementById("table1").rows.length;


	for(var counter=0;counter<queryIds.length;counter++)
	{
		
		var operandsTdContent="";
		var rowContents=new Array(7);
		rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter+queryCount),'',(counter+queryCount));
		operandsTdContent=getText(queryIds[counter]);
		rowContents[1]=createTextElement(getText(queryTitles[counter]));
		rowContents[2]=createTextElement(getText(queryTypes[counter]));
		//rowContents[3]=createHiddenElement("cancelajaxcall","cancelajaxcall_"+(counter+queryCount),'false');
		rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+(counter+queryCount),getText(queryIds[counter]));
		rowContents[4].appendChild(createHiddenElement("identifier","identifier_"+getText(queryIds[counter]),getText(queryIds[counter])));
		rowContents[5]=getText(queryTitles[counter]);
		rowContents[6]=getText(queryTypes[counter]);
		var operatorsTdContent="None";
	
		//create a table containing tbody with id "table1"
		addRowToTable("table1",rowContents,operandsTdContent,operatorsTdContent);	
	}

}

function getSelectObjectControl()
{
	var selectObject=parent.window.document.createElement("select");
	var optn = parent.window.document.createElement("OPTION");
	optn.text="person";
	optn.value="person";
	selectObject.options.add(optn);
	selectObject.name="selectObject";
	return selectObject;
}

function createImageElement(srcPath,imageId)
{
	var image=document.createElement("img");
	image.setAttribute("src",srcPath);
	image.setAttribute("id",imageId);
	image.setAttribute("id",imageId);

	//image.setAttribute("border","0");
	//image.setAttribute("align","absmiddle");
	return image;
}

function createHiddenElement(name,id,content)
{
	var hiddenControl=document.createElement('input');
	hiddenControl.type="hidden";
	hiddenControl.id=id;
	hiddenControl.name=name;
	hiddenControl.value=content;
	return hiddenControl;
}

function createTextElement(text)
{
	var textnode=document.createTextNode(text);
	return textnode;
}

function createCheckBox(name,id,displayValue,count)
{
		var chkbox=document.createElement("input");
		var text=document.createTextNode(displayValue);
		chkbox.type="checkbox";
		chkbox.id=id;
		chkbox.name=name;
		chkbox.onclick=function addEvent(){
			
			setCheckboxCount();
		}
//			.setOnclickHandler("setCheckboxCount2");

		//chkbox.setAttribute("onclick","javascript:setCheckboxCount2();");
	//	chkbox.dhx_attachEvent("onRowDblClicked",func);
		
		return chkbox;
}
function createLabel(name,index)
{
		var label=document.createElement("label");
		label.id="label_"+index;
		label.appendChild(createTextElement(name));
		return label;
}


function createLink(displayValue,text,url)
{
	var link=document.createElement('a');
	link.className="bluelink";
	link.href=url;
	link.id=text;
	var text=createTextElement(displayValue);
	link.appendChild(text);
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
	/*if (document.all)
	{
	return control.innerText;
	}
	else 
	{
	return control.textContent;
	}*/
}

function deleteWorkflowItem(index)
{
	var checkboxControl=document.getElementById("checkbox_"+(index));
	if(checkboxControl!=null && checkboxControl!=undefined && checkboxControl.checked==true)
	{
		var table=document.getElementById("table1");
		var oldNoOfRows=document.getElementById("table1").rows.length;
		var i=index;
		++i;
		for(;i<=oldNoOfRows-1;i++)
		{
			document.getElementById("checkbox_"+i).id="checkbox_"+(i-1);
			document.getElementById("displayQueryTitle_"+i).id="displayQueryTitle_"+(i-1);
			//document.getElementById("queryTypeControl_"+i).id="queryTypeControl_"+(i-1);
			document.getElementById("selectedqueryId_"+i).id="selectedqueryId_"+(i-1);
			document.getElementById("operands_"+i).id="operands_"+(i-1);
			document.getElementById("operators_"+i).id="operators_"+(i-1);
			document.getElementById("displayQueryType_"+i).id="displayQueryType_"+(i-1);
		/*	if(document.getElementById("execute_"+i)!=null)
			{
				document.getElementById("execute_"+i).href="javascript:executeGetCountQuery('"+(i-1)+"')"
			}
			if(document.getElementById("cancel_"+i)!=null)
			{
				document.getElementById("cancel_"+i).href="javascript:cancelGetCountQuery('"+(i-1)+"','"+executionLogId+"')";

			}*/
		
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
			if(document.getElementById("label_"+i)!=null)
			{
				document.getElementById("label_"+i).id="label_"+(i-1);
			}

		}
	table.deleteRow(index);
	}
	else
	{
		alert("No check box is selected to delete");
	}
}

function setCheckboxCount()
{	
	
	var checkboxArray=document.getElementById("table1").rows;
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
function enableButtons()
{
	//var buttonStatusDiv=document.getElementById("buttonStatusDiv");
	var buttonStatus=document.getElementById("buttonStatus");
	//alert(document.getElementById("buttonStatus"));
	if(buttonStatus!=null)
	{
		//alert(numOfChkSelected);
		
		  while (buttonStatus.childNodes[0])
		 {
			
			//alert(buttonStatus.childNodes[0].innerHTML);
			buttonStatus.innerHTML='';
			// buttonStatus.removeChild(buttonStatus.childNodes[0]);
		}
		buttonStatus.innerHTML="<td align='left' width='70'><a href='javascript:unionQueries()'><img align='absmiddle' src='images/advancequery/b_union-copy.gif' alt='Union' width='60' height='23' border='0'></a></td><td width='106' align='left'><a href='javascript:intersectQueries()'><img align='absmiddle' src='images/advancequery/b_intersection.gif' alt='Intersection' width='96' height='23' border='0'></a></td><td width='73' align='left'><a href='javascript:minusQueries()'><img align='absmiddle' src='images/advancequery/b_minus.gif' alt='Minus' width='63' height='23' border='0'></a></td>";
		//buttonStatus.removeChild(document.getElementById("buttonStatusDiv"));
		//buttonStatus.appendChild(createTextElement("<div id='buttonStatusDiv'><td align='left' width='70'><a href='javascript:unionQueries()'><img align='absmiddle' src='images/advancequery/b_union-copy.gif' alt='Union' width='60' height='23' border='0'></a></td><td width='106' align='left'><a href='javascript:intersectQueries()'><img align='absmiddle' src='images/advancequery/b_intersection.gif' alt='Intersection' width='96' height='23' border='0'></a></td><td width='73' align='left'><a href='javascript:minusQueries()'><img align='absmiddle' src='images/advancequery/b_minus.gif' alt='Minus' width='63' height='23' border='0'></a></td></div>"));
	}
	
}
function disableButtons()
{
	//var buttonStatusDiv=document.getElementById("buttonStatusDiv");
	var buttonStatus=document.getElementById("buttonStatus");
	//alert(document.getElementById("buttonStatus"));
	if(buttonStatus!=null)
	{
		//alert(numOfChkSelected);
		
		  while (buttonStatus.childNodes[0])
		 {
			
			//alert(buttonStatus.childNodes[0].innerHTML);
			buttonStatus.innerHTML='';
			// buttonStatus.removeChild(buttonStatus.childNodes[0]);
		}
			buttonStatus.innerHTML="<td align='left' width='70'><img align='absmiddle' src='images/advancequery/b_union_inact.gif' alt='Union' width='60' height='23' border='0'></td><td width='106' align='left'><img align='absmiddle' src='images/advancequery/b_intersection_inact.gif' alt='Intersection' width='96' height='23' border='0'></td><td width='73' align='left'><img align='absmiddle' src='images/advancequery/b_minus_inact.gif' alt='Minus' width='63' height='23' border='0'></td>";
		
	}
}
function setOnclickEventOnDeselect(chckCount,selected)
{

	if(selected)
	{
		numOfChkSelected--;
	}
	//alert("unchecked");
	//alert("unchecked numOfChkSelected "+numOfChkSelected);
	
	var chckbox=document.getElementById("checkbox_"+chckCount);
		chckbox.onclick=function addEvent2(){
			setCheckboxCount();
		}
	//chckbox.setAttribute("onclick","javascript:setCheckboxCount("+chckCount+")");
	if(numOfChkSelected!=2)
	{
		
		disableButtons();
	}

}
function changeLinkToCancel(queryId,executionLogId)
{

	var identifier=document.getElementById("identifier_"+queryId);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var selectedqueryId=object.id;
	
	var selectedquery=selectedqueryId.split("_");
	index=selectedquery[1];

	var object=document.getElementById("execute_"+index);		
	if(object!=null&&object!=undefined)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Cancel ","cancel_"+index,"javascript:cancelGetCountQuery('"+queryId+"','"+executionLogId+"')"));
	}
}

function changeExecuteLinkToExecute(queryId,executionLogId)
{

	var identifier=document.getElementById("identifier_"+queryId);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var selectedqueryId=object.id;
	
	var selectedquery=selectedqueryId.split("_");
	index=selectedquery[1];
	
	var object=document.getElementById("cancel_"+index);		
	if(object!=null)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Execute ","execute_"+index,"javascript:executeGetCountQuery('"+queryId+"','"+0+"')"));
		
	}
}
