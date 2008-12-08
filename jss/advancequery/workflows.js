
// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addRowToTable(tableId,columnContents,operandsTdContent,operatorsTdContent)
{
	/*alert('7 operandsTdContent='+operandsTdContent);
		alert('8 operatorsTdContent='+operatorsTdContent);*/

	var tableObj=document.getElementById(tableId);
	//alert('11 tableObj='+tableObj);
	var rowObj=document.createElement("tr");
//	alert('13 rowObj='+rowObj);
	rowObj.className="content_txt";
//	alert('15 rowObj='+rowObj);
	var columnObj;
	var columnCount=columnContents.length;
	//alert('18 columnCount='+columnCount);
	for(var counter=0;counter<columnCount;counter++)
	{
	//	alert('21 counter='+counter);
		if(columnContents[counter]!=null)
		{
			columnObj=document.createElement("td");
	//		alert('24 columnObj='+columnObj);
			columnObj.className="td_align";
		///	alert('26 columnObj='+columnObj);
	//		alert('27 columnContents[counter]='+columnContents[counter]);
			columnObj.appendChild(columnContents[counter]);
		//	alert('29 columnObj='+columnObj);
			rowObj.appendChild(columnObj);
		//	alert('31 rowObj='+rowObj);
		}
	}
	
	//Create all the hidden controls and add them to a "td"
	var operandsTd=document.createElement("td");
//alert('37 operandsTd='+operandsTd);
	var queryControls=document.getElementsByName("operands");
	//alert('39 queryControls='+queryControls);
	var queryCount=0;
	if(queryControls!=null && queryControls!=undefined)
	{
		queryCount=queryControls.length;
	}
//alert('45 queryCount='+queryCount);
	var operandsControl=createHiddenElement("operands","operands_"+queryCount,operandsTdContent);	
//alert('47 operandsControl='+operandsControl);
	operandsTd.appendChild(operandsControl);
	
	var operatorControl=createHiddenElement("operators","operators_"+queryCount,operatorsTdContent);
	//alert('51 operatorControl='+operatorControl);
	operandsTd.appendChild(operatorControl);
	
	var queryTitleControl;
	var queryTypeControl;
//	alert('hasInnerText()='+hasInnerText());
	/*if(!hasInnerText())
	{*
		alert('in if'+columnContents[1]);*/
			queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,getText(columnContents[1]));
			queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,getText(columnContents[2]));
//	}
/*	else
	{
		alert('in else'+columnContents[1]);
			queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,columnContents[1].innerTEXT);
			queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,columnContents[2].innerTEXT);
	}*/
	
	//alert('55 queryTitleControl='+queryTitleControl);
	operandsTd.appendChild(queryTitleControl);
	operandsTd.appendChild(queryTypeControl);
			
	var trImgup=new Image ( );
	trImgup.src = "images/advancequery/ic_up.gif";
	operandsTd.appendChild(trImgup);
	var trImgDown=new Image ( );
	trImgDown.src = "images/advancequery/ic_down.gif";
	operandsTd.appendChild(trImgDown);
	rowObj.appendChild(operandsTd);
	
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
	
	for(var counter=0;counter<queryIds.length;counter++)
	{
		var operandsTdContent="";
		var rowContents=new Array(5);
		rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter),'');
		//alert('hasInnerText(='+hasInnerText());
/*		if(!hasInnerText())
		{
			alert('in if'); */
			operandsTdContent=queryIds[counter].textContent;
			rowContents[1]=createTextElement(getText(queryTitles[counter]));
			rowContents[2]=createTextElement(getText(queryTypes[counter]));
			rowContents[3]=createTextElement(getText(queryIds[counter]));
			rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+counter,getText(queryIds[counter]));
	//	}
	/*	else
		{
			alert('in else');
			alert('queryTitles[counter].innerText='+queryTitles[counter].innerText);
			operandsTdContent=queryIds[counter].innerText;
			rowContents[1]=createTextElement(queryTitles[counter].innerText);
			rowContents[2]=createTextElement(queryTypes[counter].innerText);
			rowContents[3]=createTextElement(queryIds[counter].innerText);
			rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+counter,queryIds[counter].innerText);
		}*/
		
		var operatorsTdContent="NONE";
	
		//create a table containing tbody with id "table1"
		addRowToTable("table1",rowContents,operandsTdContent,operatorsTdContent);	
	}
}

function createHiddenElement(name,id,content)
{
	var hiddenControl=document.createElement('input');
	hiddenControl.type="hidden";
	hiddenControl.id=id;
	hiddenControl.name=name;
	hiddenControl.value=content;
//	alert('hiddenControl type ='+hiddenControl.type);
	//alert('hiddenControl id ='+hiddenControl.id);
	//alert('hiddenControl value ='+hiddenControl.value);
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

function getText(control)
{
	if (document.all)
	{
	return control.innerText;
	}
	else 
	{
	return control.textContent;
	}
}


