
// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addRowToTable(tableId,columnContents,operandsTdContent,operatorsTdContent)
{

	var tableObj=document.getElementById(tableId);
	var rowObj=document.createElement("tr");
	rowObj.className="td_bgcolor_white";
	var columnObj;
	var columnCount=columnContents.length;
	for(var counter=0;counter<columnCount-2;counter++)
	{
		if(columnContents[counter]!=null)
		{
			columnObj=document.createElement("td");
			columnObj.className="content_txt";
			columnObj.appendChild(columnContents[counter]);
			rowObj.appendChild(columnObj);
		}
	}
		
	//Create all the hidden controls and add them to a "td"
	var operandsTd=document.createElement("td");

	
	var tble1=document.createElement("table");
	var tbody1=document.createElement("tbody");
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
	operandsTd1.appendChild(createLink("Execute ","execute_"+queryCount,"javascript:executeGetCountQuery('"+queryCount+"')"));
	operandsTd3.appendChild(createLink("Delete ","delete_"+queryCount,"javascript:deleteWorkflowItem('"+queryCount+"')"));

	operandsTr.appendChild(operandsTd1);
	operandsTr.appendChild(operandsTd2);
	operandsTr.appendChild(operandsTd3);
	tbody1.appendChild(operandsTr);
	tble1.appendChild(tbody1);
	operandsTd.appendChild(tble1);

	rowObj.appendChild(operandsTd);
	
	var reorderTd=document.createElement("td");
	reorderTd.align="center";

	var tble2=document.createElement("table");
	var tbody2=document.createElement("tbody");
	tble2.cellspacing="0";
	tble2.cellpadding="0"

	var trInsideTd=document.createElement("tr");
	var reorderTd1=document.createElement("td");
	var reorderTd2=document.createElement("td");
	var reorderTd3=document.createElement("td");

	var trImgup=new Image();
	trImgup.src = "images/advancequery/ic_up.gif";
	reorderTd1.appendChild(trImgup);
	reorderTd2.width="2";
	var trImgDown=new Image( );
	trImgDown.src = "images/advancequery/ic_down.gif";
	reorderTd3.appendChild(trImgDown);
	
	trInsideTd.appendChild(reorderTd1);
	trInsideTd.appendChild(reorderTd2);
	trInsideTd.appendChild(reorderTd3);
	tbody2.appendChild(trInsideTd);
	tble2.appendChild(tbody2);
	reorderTd.appendChild(tble2);
	rowObj.appendChild(reorderTd);
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
		rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter+queryCount),'');
		operandsTdContent=getText(queryIds[counter]);	
		rowContents[1]=createTextElement(getText(queryTitles[counter]));
		rowContents[2]=createTextElement(getText(queryTypes[counter]));
		//rowContents[3]=getSelectObjectControl();
		rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+(counter+queryCount),getText(queryIds[counter]));
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

function createImageElement(srcPath)
{
	var image=document.createElement("img");
	image.setAttribute("src",srcPath);
	image.setAttribute("border","0");
	image.setAttribute("align","absmiddle");
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

function createCheckBox(name,id,displayValue)
{
		var chkbox=document.createElement("input");
		var text=document.createTextNode(displayValue);
		chkbox.type="checkbox";
		chkbox.id=id;
		chkbox.name=name;
		return chkbox;
}

function createLink(displayValue,test,url)
{
	var link=document.createElement('a');
	link.className="bluelink";
	link.href=url;
	link.id=test;
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
			document.getElementById("execute_"+i).href="javascript:executeGetCountQuery('"+(i-1)+"')"
			document.getElementById("delete_"+i).href="javascript:deleteWorkflowItem('"+(i-1)+"')"
			document.getElementById("execute_"+i).id="execute_"+(i-1);
			document.getElementById("delete_"+i).id="delete_"+(i-1);
		}
	table.deleteRow(index);
	}
	else
	{
		alert("No check box is selected to delete");
	}
}

