
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
	//rowObj.bgcolor="#ffffff";
	rowObj.className="td_bgcolor_white";
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
			columnObj.className="content_txt";
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
	operandsTd.appendChild(createLink("Execute ","#"));
	operandsTd.appendChild(createLink("Delete ","#"));
	rowObj.appendChild(operandsTd);
	
	var reorderTd=document.createElement("td");
	var trImgup=new Image ( );
	trImgup.src = "images/advancequery/ic_up.gif";
	reorderTd.appendChild(trImgup);
	var trImgDown=new Image ( );
	trImgDown.src = "images/advancequery/ic_down.gif";
	reorderTd.appendChild(trImgDown);
//	rowObj.appendChild(document.createElement("td"));
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
	
	for(var counter=0;counter<queryIds.length;counter++)
	{
		var operandsTdContent="";
		var rowContents=new Array(6);
		rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter),'');
		operandsTdContent=getText(queryIds[counter]);	
		rowContents[1]=createTextElement(getText(queryTitles[counter]));
		rowContents[2]=createTextElement(getText(queryTypes[counter]));
		rowContents[3]=getSelectObjectControl();
		rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+counter,getText(queryIds[counter]));
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
//	alert('hiddenControl type ='+hiddenControl.type);
//	alert("hiddenControl.name="name);
//	alert('hiddenControl id ='+hiddenControl.id);
//	alert('hiddenControl value ='+hiddenControl.value);
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

function createLink(displayValue,url)
{
	var link=document.createElement('a');
	link.className="bluelink";
	link.href=url;
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


