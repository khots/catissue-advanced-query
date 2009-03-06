
// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addRowToTable(tableId,columnContents,operandsTdContent,operatorsTdContent,expr)
{
	var tableObj=document.getElementById(tableId);
	var rowObj=document.createElement("tr");
	rowObj.height="22";
	rowObj.className="td_bgcolor_white";
	var columnObj;
	var columnCount=columnContents.length;
	var id=0;

	var queryControls=document.getElementById("table1").rows;
	var queryCount=0;
	if(queryControls!=null && queryControls!=undefined)
	{
		queryCount=queryControls.length;
	}
	//To add icons showing the status

	//var imgObj=document.createElement("td");
	//imgObj.width="20";
	//var trImgDown=createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+queryCount);
	//imgObj.appendChild(trImgDown);
	//rowObj.appendChild(imgObj);

	for(var counter=0;counter<columnCount-2;counter++)
	{
		if(columnContents[counter]!=null)
		{
			columnObj=document.createElement("td");
			//columnObj.className="aligntop";
			if(counter==0)
			{
				columnObj.width="10";//set width for checkbox control

			}

			columnObj.className="aligntop";
			if(counter==1)
			{
				columnObj.className="workflowicon";//set width for checkbox control

			}
			columnObj.appendChild(columnContents[counter]);
			if(columnContents[counter].name=="selectedqueryId")
			{
				id=columnContents[counter].value;
				columnObj.appendChild(createHiddenElement("identifier","identifier_"+queryCount,columnContents[7]));
				columnObj.appendChild(createHiddenElement("queryIdForRow","queryIdForRow_"+id,id));
				columnObj.appendChild(createHiddenElement("queryExecId","queryExecId_"+queryCount,0));
			}
			rowObj.appendChild(columnObj);
		}
	}
		
	//Create all the hidden controls and add them to a "td"
	var operandsTd=document.createElement("td");
	operandsTd.className="aligntop";
	operandsTd.width="100"
	//operandsTd.className="aligntop";
	
	var tble1=document.createElement("table");
	tble1.depth="3";
	var tbody1=document.createElement("tbody");
	tbody1.depth="5";
	var operandsTr=document.createElement("tr");
	var operandsTd1=document.createElement("td");
	operandsTd1.className="aligntop";
	//operandsTd1.className="aligntop";
	operandsTd1.width="100";
	var operandsTd2=document.createElement("td");
	operandsTd2.className="aligntop";

	//operandsTd2.className="aligntop";
	var operandsTd3=document.createElement("td");
	operandsTd3.className="aligntop";
	//operandsTd3.className="aligntop";


	
	var operandsControl=createHiddenElement("operands","operands_"+queryCount,operandsTdContent);	
	operandsTd2.appendChild(operandsControl);
	
	var operatorControl=createHiddenElement("operators","operators_"+queryCount,operatorsTdContent);
	operandsTd2.appendChild(operatorControl);
	
	var queryTitleControl;
	var queryTypeControl;
    var exprControl;

	queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,(columnContents[7]));
	queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,(columnContents[8]));
	exprControl=createHiddenElement("expression","expression_"+queryCount,expr);

	operandsTd2.appendChild(queryTitleControl);
	operandsTd2.appendChild(queryTypeControl);
	operandsTd2.appendChild(exprControl);
	operandsTd2.width="4";
	var query_type=columnContents[8];
	if(query_type=="Data")
     operandsTd1.appendChild(createLink("View Results ","execute_"+queryCount,"javascript:executeGetDataQuery('"+id+"')"));
	else
	{
		 var t =	escape(columnContents[7]);
		 operandsTd1.appendChild(createLink("Execute ","execute_"+queryCount,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
	}
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
	document.getElementById("isdone").value="false";
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
	var presentQueryIds=parent.window.document.getElementsByName("queryIdForRow");
	var presentQueryTitle=parent.window.document.getElementsByName("displayQueryTitle");
	var presentQueryType=parent.window.document.getElementsByName("displayQueryType");
	


	for(var counter=0;counter<queryIds.length;counter++)
	{
		
		var operandsTdContent="";
		var rowContents=new Array(9);
		if(getText(queryTypes[counter])=="Data") 
		{
			rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter+queryCount),'',(counter+queryCount),true);
		}
		else
		{
			rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter+queryCount),'',(counter+queryCount),false);
		}
		operandsTdContent=getText(queryIds[counter]);
		var trImgDown=createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+counter);
		rowContents[1]=trImgDown;
		rowContents[2]=createTextElement(getText(queryTitles[counter]));
		rowContents[3]=createTextElement(getText(queryTypes[counter]));
		//rowContents[3]=createHiddenElement("cancelajaxcall","cancelajaxcall_"+(counter+queryCount),'false');
		 if(getText(queryTypes[counter])=="Data") 
		  rowContents[5]=getSelectObjectControl(queryIds[counter],presentQueryIds,presentQueryTitle,presentQueryType,queryIds,queryTitles,queryTypes);
		 else
          rowContents[5]=createTextElement("");
	 	rowContents[6]=createHiddenElement("selectedqueryId","selectedqueryId_"+(counter+queryCount),getText(queryIds[counter]));
//		rowContents[4].appendChild(createHiddenElement("identifier","identifier_"+getText(queryIds[counter]),getText(queryIds[counter])));
		rowContents[7]=getText(queryTitles[counter]);
		rowContents[8]=getText(queryTypes[counter]);
		var operatorsTdContent="None";	
		//create a table containing tbody with id "table1"
		addRowToTable("table1",rowContents,operandsTdContent,operatorsTdContent,getText(queryIds[counter]));

	}

}

function getSelectObjectControl(thisqID,pId,pTitle,pType,queryIds,queryTitles,queryTypes)
{
	 var booln="true" ;
	var selectObject=parent.window.document.createElement("select");
	selectObject.style.width="120";
	selectObject.name="countQueryDropDown";
	selectObject.id="countQueryDropDown_"+thisqID.value;
	selectObject.disabled=true;

	var dropDown= parent.window.document.getElementById("executedCountQuery");
    var executedQuery=dropDown.options.length;
    	if(executedQuery >0)
    	{
    		selectObject.disabled=false;
    		for(var i=0;i<executedQuery;i++)
    	  {
    		  var optn = parent.window.document.createElement("OPTION");
    		   optn.text= dropDown.options[i].text;
    		   optn.value= dropDown.options[i].value;
    		   selectObject.options.add(optn);
    	  }
    	
    	}
	
/*	for(var i=0;i<pId.length;i++)
	{
    
	  
	  
	  if(pType[i].value=="GetCount")
	 {
	   
	    for(var t=0;t<queryIds.length;t++)
	 {
	    if(queryIds[t].value == pId[i].value)
		{
		  booln="false";
		}
	  
	  }
       if(booln=="true")
	 {  
       var optn = parent.window.document.createElement("OPTION");
	   optn.text=pTitle[i].value;
	   optn.value=pId[i].value;
	   selectObject.options.add(optn);
	 }
     }
   }
	
	
	for(var i=0;i<queryIds.length;i++)
   {
    if(getText(queryTypes[i])=="GetCount")
	   {
	  var optn = parent.window.document.createElement("OPTION");
	  optn.text=getText(queryTitles[i]);
	  optn.value=queryIds[i].value;
	  selectObject.options.add(optn);
   }
   } */
	
	return selectObject;
}


function createImageElement(srcPath,imageId)
{
	var image=document.createElement("img");
	image.setAttribute("src",srcPath);
	image.setAttribute("id",imageId);
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
		}

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
}


function  reSetDropDowns(queryTitle)
{

  var dropDowns= parent.window.document.getElementsByTagName("select");
	 for(var i=0;i<dropDowns.length;i++)
	{
      if(dropDowns[i].name=="countQueryDropDown")
	  {
			var index=-1;
			var executedQuery=dropDowns[i].options.length;
			if(executedQuery >0)
			{
				for(var j=0;j<executedQuery;j++)
			  {
				  if(dropDowns[i].options[j].text==queryTitle)
					 index=j;
			  }
			
			}
		  
			 if(index!=-1)
			   dropDowns[i].remove(index);
			  if(dropDowns[i].options.length==0)
				  dropDowns[i].disabled=true;
	  }

	}
	
    
	 var hiddenDropDown=parent.window.document.getElementById("executedCountQuery");
	 if(hiddenDropDown!=null)
	 {
		  var optns=hiddenDropDown.options.length;
		  var ind=-1;
			
			if(optns >0)
			{
				for(var j=0;j<optns;j++)
			  {
				  if(hiddenDropDown.options[j].text==queryTitle)
					 ind=j;
			  }
			
			}
		  
		   if(ind!=-1) 
			 hiddenDropDown.remove(ind);
			  if(hiddenDropDown.options.length==0)
				 hiddenDropDown.disabled=true; 
	 }
	  
	     
}

function deleteWorkflowItem(index)
{
	var dependentQueries=checkFordependentQueries(index);

	if(dependentQueries!=true)
	{
		//dhtmlmodal.open('delete Queries', 'iframe', './pages/advancequery/content/search/querysuite/deleteQueryConfim.jsp','Delete Query', 'width=400px,height=150px,center=1,resize=1,scrolling=1');
		var checkboxControl=document.getElementById("checkbox_"+(index));

		if(checkboxControl!=null && checkboxControl!=undefined)
		{
			var table=document.getElementById("table1");
			var oldNoOfRows=document.getElementById("table1").rows.length;
			var deleteQuery =document.getElementById('displayQueryTitle_'+index).value;
			var i=index;
			++i;
			for( ;i<=(oldNoOfRows-1);i++)
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
				document.getElementById("identifier_"+i).id="identifier_"+(i-1);
				if(document.getElementById("label_"+i)!=null)
				{
					document.getElementById("label_"+i).id="label_"+(i-1);
				}
				document.getElementById("notStarted_"+i).id="notStarted_"+(i-1);
				document.getElementById("expression_"+i).id="expression_"+(i-1);
			}
			
			table.deleteRow(index);
		}
	
		 reSetDropDowns(deleteQuery);
		setCheckboxCount();
	}
	else
	{
		dhtmlmodal.open('delete Queries', 'iframe', './pages/advancequery/content/search/querysuite/depentQueryPopup.jsp','Delete Query', 'width=400px,height=150px,center=1,resize=1,scrolling=1');
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
	var buttonStatus=document.getElementById("buttonStatus");
	if(buttonStatus!=null)
	{

		  while (buttonStatus.childNodes[0])
		 {
			
			//alert(buttonStatus.childNodes[0].innerHTML);
			buttonStatus.innerHTML='';
			// buttonStatus.removeChild(buttonStatus.childNodes[0]);
		}
		buttonStatus.innerHTML="<table cellpadding='0' cellspacing='0' ><tr><td align='left' ><a href='javascript:unionQueries()'><img align='absmiddle' src='images/advancequery/b_union-copy.gif' alt='Union' border='0'></a></td><td width='108' align='middle'><a href='javascript:intersectQueries()'><img align='absmiddle' src='images/advancequery/b_intersection.gif' alt='Intersection'  border='0'></a></td><td  align='left'><a href='javascript:minusQueries()'><img align='absmiddle' src='images/advancequery/b_minus.gif' alt='Minus'  border='0'></a></td></tr></table>";
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
			buttonStatus.innerHTML="<table cellpadding='0' cellspacing='0' ><tr><td align='left' ><img align='absmiddle' src='images/advancequery/b_union_inact.gif' alt='Union' border='0'></td><td width='108' align='middle'><img align='absmiddle' src='images/advancequery/b_intersection_inact.gif' alt='Intersection'  border='0'></td><td align='left'><img align='absmiddle' src='images/advancequery/b_minus_inact.gif' alt='Minus'  border='0'></td></tr></table>";
		
	}
}
function setOnclickEventOnDeselect(chckCount,selected)
{

	if(selected)
	{
		numOfChkSelected--;
	}
	
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

	var identifier=document.getElementById("queryIdForRow_"+queryId);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
	var selectedquery=selectedqueryId.split("_");
	
	index=selectedquery[1];
	
	var object=document.getElementById("execute_"+index);	

	//To get the query title field of document
	var queryTitle=document.getElementById("displayQueryTitle_"+index).value;
	//document.getElementById("cancelajaxcall_"+index).value='false';
	
	if(object!=null&&object!=undefined)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Cancel ","cancel_"+index,"javascript:cancelGetCountQuery('"+queryId+"','"+executionLogId+"','"+false+"')"));
		disableDeleteLink(index);
	}
	 imageForProgressiveCounts(index);
}

function changeExecuteLinkToExecute(queryId,executionLogId)
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
	//document.getElementById("cancelajaxcall_"+index).value='false';
	 var t =	escape(queryTitle);
	if(object!=null)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Execute ","execute_"+index,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
		enableDeleteLink(index);
	}
	imageForCompletedCounts(index);
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

function checkFordependentQueries(index)
{
	var expression=document.getElementById("expression_"+index).value;
	//var queryIds=selectedQueryId.split(",");
	//alert("index" +index);
	//alert("queryIds ="+queryIds);
	var rows=document.getElementById("table1").rows.length;
	for(var i=0;i<rows;i++)
	{
		var idsToCompare=document.getElementById("expression_"+i).value;
		///alert("idsToCompare =" +idsToCompare);
			if(i!=index && document.getElementById("displayQueryType_"+i).value!='Data' )
			{
				//alert(" i =" +i);
				//for(var counter=0;counter<queryIds.length;counter++)
				//{
					//alert(" queryIds[counter] =" +queryIds[counter]);
					var queryIdPosition=idsToCompare.indexOf(expression);
					if(queryIdPosition!=-1)
					{
						return true;
					}
				//}
			}
	}
	return false;
}

function imageForProgressiveCounts(index)
{


  var x = document.getElementById("notStarted_"+index);
  var v = x.getAttribute("src");
  v = "images/advancequery/inprogress09.gif";
  x.setAttribute("src", v);	

}

function imageForCompletedCounts(index)
{
	var x = document.getElementById("notStarted_"+index);
	var v = x.getAttribute("src");
	v = "images/advancequery/ic_complete05.gif";
	x.setAttribute("src", v);	
}


