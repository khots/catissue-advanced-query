
// This method adds a row to the table with id "tableId"
// The contents of the row are provided in the array "columnContents"
// "columnContents" contains controls to be added to be added to each column
function addRowToTable(tableId,columnContents,operandsTdContent,operatorsTdContent,expr)
{
	if(!isAlreadyPresent(columnContents))
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
					 columnObj.width="10";

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
		operandsTd.width="125";
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
		var query_type=columnContents[8];
		if(query_type=="Data")
		 operandsTd1.appendChild(createLink("View Results ","execute_"+queryCount,"javascript:executeGetDataQuery('"+id+"')"));
		else
		{
			 var t =	escape(columnContents[7]);
			 operandsTd1.appendChild(createLink("Execute ","execute_"+queryCount,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
		}
			operandsTd1.className="aligntop";

		operandsTd3.appendChild(createLink("Delete ","delete_"+queryCount,"javascript:deleteWorkflowItem('"+queryCount+"')"));
		operandsTd3.className="aligntop";
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
	else
	{
		document.getElementById("sameQueryTitle").value=columnContents[7]
		dhtmlmodal.open('Same Query title', 'iframe', './pages/advancequery/content/search/querysuite/QueryTitlePopup.jsp','Query title', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
	}
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
			
			rowContents[1]=document.createElement("td");
		}
		else
		{
			rowContents[0]=createCheckBox("chkbox","checkbox_"+(counter+queryCount),'',(counter+queryCount),false);
			var trImgDown=createImageElement("images/advancequery/ic_notrun06.gif","notStarted_"+(counter+queryCount));
			rowContents[1]=trImgDown;
		}
		operandsTdContent=getText(queryIds[counter]);

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
   selectObject.onmouseover=function showTipOnMouseOver(){showTip(this.id);};
									
	
	return selectObject;
}


function createImageElement(srcPath,imageId)
{
	var image=document.createElement("img");
	image.setAttribute("src",srcPath);
	image.setAttribute("id",imageId);
	//image.setAttribute("onMouseOver","Tip('Not Started')");
	image.onmouseover=function abc(image){ Tip('Not Run'); };
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
			//	alert("addEvent");
			setCheckboxCount();
			setSelectedCheckBoxes();

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


function  reSetDropDowns(queryId)
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
				  if(dropDowns[i].options[j].value==queryId)
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
				  if(hiddenDropDown.options[j].value==queryId)
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
	var url='DeleteQueryPopup.do?index='+index;
	pvwindow=dhtmlmodal.open('Delete Query1', 'iframe', url,'Delete Query', 'width=400px,height=120px,center=1,resize=1,scrolling=0');

}

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

			var table=document.getElementById("table1");
			var oldNoOfRows=document.getElementById("table1").rows.length;

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

					//replace(scripts,i);
			}

			table.deleteRow(index);
			document.getElementById("isdone").value="false";

		}
		
		reSetDropDowns(deleteQueryId);
		setCheckboxCount();
		uncheckselectedCheckBoxes();
		}
		else
		{
			pvwindow1=dhtmlmodal.open('Delete Query2', 'iframe', './pages/advancequery/content/search/querysuite/depentQueryPopup.jsp','Delete Query', 'width=400px,height=120px,center=1,resize=1,scrolling=0');
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
function indexOf(scripts,counter)
{
	var index=-1;
	for(var i=0;i<scripts.length;i++)
	{
		if(scripts[i]==counter)
		{
			index=i;
		}
	}
	return index;
}
/*Thhis method sets the array of checkboxes selected on UI 
This is useful while performing ' a Minus b  ' 
or  b Minus a ' kind of queries */
function setSelectedCheckBoxes()
{

	var queryCount=document.getElementById("table1").rows.length;
		for(var counter=0;counter<queryCount;counter++)
			{
				var checkboxControl=document.getElementById("checkbox_"+(counter));
				if(checkboxControl.checked==true)
				{
						var index=indexOf(scripts,counter);
						if(index==-1)
						{
							scripts.push(counter);
						}
				}
				else
				{
					var index1=indexOf(scripts,counter);
						if(index1!=-1)
						{
							scripts.splice(indexOf(scripts,counter),1); 
						}

				}
			}

	/*var chkbox=document.getElementById('checkbox_'+index);
	if(chkbox!=null && chkbox!=undefined)
	{
		if(chkbox.checked)
		{
			scripts.push(index);
		}
		else
		{
			scripts.splice(scripts.indexOf(index),1); 
		}
	}*/


}
/*function removeSelectedCheckBoxes(index)
{

		var chkbox=document.getElementById('checkbox_'+index);
	if(chkbox!=null && chkbox!=undefined&&chkbox.checked)
	{
		scripts.splice(scripts.indexOf(index),1); 
	}


}*/
/*function replace(arrayName,replaceTo)
{
	alert("replaceTo"+(replaceTo-1));
	var chkbox=document.getElementById('checkbox_'+(replaceTo-1));

	//alert(chkbox!=null && chkbox!=undefined);
	//add new event
	if(chkbox!=null && chkbox!=undefined)
	{
		chkbox.onclick=function addEvent4(){	
	//	alert("addEvent4");
		alert("chkbox "+chkbox.id);
		var selectedquery=(chkbox.id).split("_");
	
		var index=selectedquery[1];
	//	alert("chkbox "+chkbox.value);
		//alert("index" +index);
		//alert("replaceTo "+(replaceTo-1));
			setCheckboxCount(index);
			setSelectedCheckBoxes(index);

		}
		if(chkbox.checked)
		{
		  //for(var i=0; i<arrayName.length;i++ )
		 // {  
		////replace if added in the array to new index
		//alert("arrayName[i]="+arrayName[i] + "replaceTo="+replaceTo);
			//if(arrayName[i]==replaceTo)
			//  {
				//alert("replacing");
				//arrayName.splice(i,1,replaceTo-1);    
				var replaceWith=--replaceTo;
				arrayName.splice(scripts.indexOf(replaceTo),1,replaceWith);  ;
			//  }
		 // }
		}
	}

}*/
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
	//alert("identifier "+identifier);
	var object=identifier.parentNode;//document.getElementById("selectedqueryId_"+queryIndex);
	//alert("object "+object);
	var tdChildCollection=object.getElementsByTagName('input');
	var selectedqueryId=tdChildCollection[0].id;//object.childNodes[0].id;//object.id;
	var selectedquery=selectedqueryId.split("_");
	
	index=selectedquery[1];
	//alert("index "+index);
	var object=document.getElementById("execute_"+index);	
	//alert("object "+object);

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
	//document.getElementById("cancelajaxcall_"+index).value='false';
	 var t =	escape(queryTitle);
	if(object!=null)
	{
		var parentIObj=object.parentNode;
		parentIObj.removeChild(object);
		parentIObj.appendChild(createLink("Execute ","execute_"+index,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
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
}
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
		parentIObj.appendChild(createLink("Execute ","execute_"+index,"javascript:executeGetCountQuery('"+escape(t)+"','"+0+"')"));
		enableDeleteLink(index);
	}
	imageForNotRunning(index);
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
	var rows=document.getElementById("table1").rows.length;
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
		x.onmouseover=function chnageToolTip(x){ Tip('In Progress'); };
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
		x.onmouseover=function chnageToolTip(x){ Tip('Completed'); };
	}


}
/*
 * changes failed/in progress/notRun/Cancelled to not run 
 * */
function imageForNotRunning(index)
{

	//alert("document.getElementById("+document.getElementById("notStarted_"+index));
  var x = document.getElementById("notStarted_"+index);
  if(x!=null && x!=undefined)
{
	  var v = x.getAttribute("src");
	  v = "images/advancequery/ic_notrun06.gif";
	  x.setAttribute("src", v);	
	 //x.setAttribute("onMouseOver","Tip('In Progress')");
		x.onmouseover=function chnageToolTip(x){ Tip('Not Run'); };
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
	  x.onmouseover=function chnageToolTip(x){ Tip('Cancelled'); };
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
	 x.onmouseover=function chnageToolTip(x){ Tip('Failed'); };
	}

}

function addEvent3(){
			setCheckboxCount();
			setSelectedCheckBoxes();
}

function isAlreadyPresent(columnContents)
{
			var alreadyPresent=false;
			var numOfRows =document.getElementById("table1").rows.length;
			for(var count = 0; count < numOfRows; count++)
			{

			//queryTitleControl=createHiddenElement("displayQueryTitle","displayQueryTitle_"+queryCount,(columnContents[7]));
		//queryTypeControl=createHiddenElement("displayQueryType","displayQueryType_"+queryCount,(columnContents[8]));
				var title=document.getElementById("identifier_"+count);
				var type=document.getElementById("displayQueryType_"+count).value;
				if(title.value==columnContents[7]&&type==columnContents[8])
				{
					alreadyPresent=true;
				}
			}

return alreadyPresent;
}

//used for not executed query when switches from the 
//getdata/count/view result 
// if project selected is one for whilch loading div is not required
function initalizeflagforLoadingDiv()
{
	var rows=document.getElementById("table1").rows.length;

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
function showPQPopUp1(queryId)
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
	//pvwindow=dhtmlmodal.open('parameterized queries', 'iframe', url,'parameterized queries', 'width=930px,height=400px,center=1,resize=1,scrolling=1');
	
	var request	=newXMLHTTPReq();
	if(request == null)
	{
		alert ("Your browser does not support AJAX!");
		return;
	}
	/*var handlerFunction = getReadyStateHandler(request,showPQPopUpHandler1,true); 
	request.onreadystatechange = handlerFunction; 
	request.open("GET",url,true);    
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.send("");*/
				var request=newXMLHTTPReq();
			if(request == null)
			{
				alert ("Your browser does not support AJAX!");
				return;
			}
			var handlerFunction = getReadyStateHandler(request,showPQPopUpHandler1,true); 
			request.onreadystatechange = handlerFunction; 
			request.open("POST",url,true);    
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send("");
}
function showPQPopUpHandler1(response)
{
//alert("response in showPQPopUpHandler1 " +response);
	if(response!=null&&response!="")
	{
		var jsonResponse = eval('('+ response+')');
		if(jsonResponse.showPopup=="showPopup")
		{
				 var queryId=jsonResponse.queryId;
				 //call the default execution with popup 
				 var projectId=document.getElementById("selectedProject").value;
				 var url='ExecutePQsInWorkflow.do?queryId='+queryId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId;
		/*	var request	=newXMLHTTPReq();
			if(request == null)
			{
				alert ("Your browser does not support AJAX!");
				return;
			}
				var handlerFunction = getReadyStateHandler(request,executePQsInWorkflowHandler1,true); 
				request.onreadystatechange = handlerFunction; 
				request.open("GET",url,true);    
				request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				request.send("");*/
			var request=newXMLHTTPReq();
			if(request == null)
			{
				alert ("Your browser does not support AJAX!");
				return;
			}
			var handlerFunction = getReadyStateHandler(request,executePQsInWorkflowHandler1,true); 
			request.onreadystatechange = handlerFunction; 
			request.open("POST",url,true);    
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send("");
			  //pvwindow=dhtmlmodal.open('parameterized queries', 'iframe', url,'parameterized queries', 'width=930px,height=400px,center=1,resize=1,scrolling=1');

		}
		else
		 {
			 //call the default execution without popup
			 workflowExecuteGetCountQuery(jsonResponse.queryId,0);
		 }
	}
	

}
function executePQsInWorkflowHandler1(response)
{
	//alert(" in executePQsInWorkflowHandler1" +response);
	//alert("in executePQsInWorkflowHandler ");
	var jsonResponse = eval('('+ response+')');
	//alert("jsonResponse" +jsonResponse);
	if(jsonResponse!=null&&jsonResponse!="")
	{
		//alert(jsonResponse.result.queryIdString);
		//alert("jsonResponse.result.queryIdString!="+jsonResponse.result.queryIdString!="");
		if(jsonResponse.result.queryIdString!="")
		{	
			//htmlContent=jsonResponse.result.HTMLContents;
			//alert("in if");
			var url='ParamerizedQueryPopUp.do?queryIdString='+jsonResponse.result.queryIdString;
			pvwindow3=dhtmlmodal.open('Parameterized Queries', 'iframe', url,'Parameterized Queries', 'width=900px,height=310px,center=1,resize=1,scrolling=1');
		
		}
		//alert("response" +response);
		workflowResponseHandler(response);
	
	}
		

}


function showPqDataPopUp(queryId)
{
//alert("response in showPQPopUpHandler1 " +response);
	
	
	
				 //var queryId=jsonResponse.queryId;
				 //call the default execution with popup 
				 var projectId=document.getElementById("selectedProject").value;
				 var url='ExecuteDataPQ.do?queryId='+queryId+"&workflowId="+document.getElementById("id").value+"&selectedProject="+projectId;
				 	  
		/*	var request	=newXMLHTTPReq();
			if(request == null)
			{
				alert ("Your browser does not support AJAX!");
				return;
			}
				var handlerFunction = getReadyStateHandler(request,executePQsInWorkflowHandler1,true); 
				request.onreadystatechange = handlerFunction; 
				request.open("GET",url,true);    
				request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				request.send("");*/
			var request=newXMLHTTPReq();
			if(request == null)
			{
				alert ("Your browser does not support AJAX!");
				return;
			}
			var handlerFunction = getReadyStateHandler(request,executeDataPQHandler1,true); 
			request.onreadystatechange = handlerFunction; 
			request.open("POST",url,true);    
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send("");
			  //pvwindow=dhtmlmodal.open('parameterized queries', 'iframe', url,'parameterized queries', 'width=930px,height=400px,center=1,resize=1,scrolling=1');

		
			 //call the default execution without popup
		
		
	
	

}
function executeDataPQHandler1(response)
{
   	var projectId=document.getElementById("selectedProject").value;
	var workflowId=document.getElementById("id").value;

	var jsonResponse = eval('('+ response+')');

	if(jsonResponse!=null&&jsonResponse!="")
	{
		//alert(jsonResponse.result.queryIdString);
		//alert("jsonResponse.result.queryIdString!="+jsonResponse.result.queryIdString!="");
		if(jsonResponse.result.queryIdString!="")
		{	
			if(jsonResponse.result.isParametrized==true)
			{
			//htmlContent=jsonResponse.result.HTMLContents;
				var url='ParamerizedQueryPopUp.do?queryIdString='+jsonResponse.result.queryIdString+'&selectedProject='+projectId+'&workflowId='+workflowId+'&queryType=Data';

				pvwindow4 = dhtmlmodal.open('Parameterized data Queries', 'iframe', url,'Parameterized data Queries', 'width=900px,height=310px,center=1,resize=1,scrolling=1');
			}
			else
			{
				var dataQueryId = jsonResponse.result.queryIdString;
				var countqId= document.getElementById("countQueryId").value;
			  	 var request = newXMLHTTPReq();		
				 var handlerFunction = getReadyStateHandler(request,displayValidationMesage,true);
				 request.onreadystatechange = handlerFunction;	
				 var actionURL = "buttonClicked=ViewResults";		
			     var url = "ValidateQuery.do?dataQueryId="+dataQueryId+"&countQueryId="+countqId+'&selectedProject='+projectId+'&workflowId='+workflowId;
				 request.open("POST",url,true);	
				 request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
				request.send(actionURL);	
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
