
// This metod adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addRowToTable(tableId,columnContents)
{
	var tableObj=document.getElementById(tableId);
//	var rows=tableObj.rows;
	var rowObj=document.createElement("tr");
	rowObj.className="tr_bg_color";
	var columnObj;
	var columnCount=columnContents.length;
	
	for(var counter=0;counter<columnCount;counter++)
	{
		if(columnContents[counter]!=null)
		{
			columnObj=document.createElement("td");
			columnObj.className="td_align";
			columnObj.appendChild(columnContents[counter]);
			rowObj.appendChild(columnObj);
		}
	}
			var trImgup=new Image ( );
			trImgup.src = "images/advancequery/ic_up.gif";
			rowObj.appendChild(trImgup);

			var trImgDown=new Image ( );
			trImgDown.src = "images/advancequery/ic_down.gif";
			rowObj.appendChild(trImgDown);

	tableObj.appendChild(rowObj);
}

function addQuery()
{
	
	var queryIds=document.getElementById("queryId").options;
	var queryTitles=document.getElementById("queryTitle").options;
	var queryTypes=document.getElementById("queryType").options;
	
	for(var counter=0;counter<queryIds.length;counter++)
	{
		
		var rowContents=new Array(5);
		rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter+1),'');
		alert(queryTitles[counter].text);
		rowContents[1]=createTextElement(queryTitles[counter].text);
		rowContents[2]=createTextElement(queryTypes[counter].text);
		rowContents[3]=createTextElement(queryIds[counter].text);
		rowContents[4]=createHiddenElement("selectedqueryId","selectedqueryId_"+counter,queryIds[counter].text);
	
		//create a table containing tbody with id "table1"
		addRowToTable("table1",rowContents);	
	}
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
	//	alert('createTextElement');
	var textnode=document.createTextNode(text);
	return textnode;
}

function createCheckBox(name,id,displayValue)
{
//		alert('createCheckBox');
		var chkbox=document.createElement("input");

		var text=document.createTextNode(displayValue);
		chkbox.type="checkbox";
		//chkbox.value=displayValue;
		return chkbox;
}
