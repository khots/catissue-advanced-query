
function onAddToCart() {
	var isChecked = updateHiddenFields();	
	var isCheckAllAcrossAllChecked = false;

    if(isChecked) {
    	var action = "ShoppingCart.do";    	
		if(isQueryModule == "true") {
			action = "AddDeleteCart.do";			
		} 
		
		var url = action + "?operation=add&pageNum="+pageNum+"&isCheckAllAcrossAllChecked="+isCheckAllAcrossAllChecked;
	
		var request = newXMLHTTPReq();
		request.onreadystatechange = getReadyStateHandler(request, displayStatus, true);
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		request.send(getCheckedField());
		
	} else {
		alert("Please select at least one checkbox");
	}
}

function getCheckedField() {
	var params = "";
	for(i = 0; i < rowCount; i++) {
		var cbvalue = document.getElementById("" + i);		
		if(!cbvalue.disabled) {
			params += cbvalue.name+"="+cbvalue.value+"&";
		}		
	}
	
	return params;
}

function displayStatus(response) {
	document.getElementById('errorStatus').innerHTML = response;
}

function getData()
{	//ajax call to get update data from server
	var param = "updateSessionData=updateSessionData";
	var url = "ValidateQuery.do";
	
	var request = newXMLHTTPReq();
	request.onreadystatechange = getReadyStateHandler(request,displayValidationMessage,true);
	request.open("POST", url, true);
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.send(param);
}

function displayValidationMessage(message)
{
	//var message contains space " " if the message is not to be shown.
	if (message != null && message == " ") 		// do not show popup
	{
		onAddToCart();
	} else {
		var isChecked = updateHiddenFields(); // if atleast one check box is checked.
		if (isChecked) {
			var r = confirm(message);
			if (r == true) {
				onAddToCart();
			}
		} else {
			alert("Please select at least one checkbox");
		}
	}
}
	
function onExport() {
	var isChecked = updateHiddenFields();	
	var isCheckAllAcrossAllChecked = !isChecked;
    
	var action = "SpreadsheetExport.do?pageNum="+pageNum+"&isCheckAllAcrossAllChecked="+isCheckAllAcrossAllChecked ;
	document.forms[0].operation.value="export";
	document.forms[0].action = action;
	document.forms[0].target = "_self";
	document.forms[0].submit();		
	window.setTimeout(removeLoading, 500)	
}

function removeLoading() {
	var superiframe=document.getElementById('superiframe');
	superiframe.className="HiddenFrame";
}

//function that is called on click of Define View button for the configuration of search results
function onSimpleConfigure()
{
	action="ConfigureSimpleQuery.do?pageOf=pageOfSimpleQueryInterface";
	document.forms[0].action = action;
	document.forms[0].target = "_self";
	document.forms[0].submit();
}

function onAdvanceConfigure()
{
	action="ConfigureAdvanceSearchView.do?pageOf=pageOfQueryResults";
	document.forms[0].action = action;
	document.forms[0].target = "_self";
	document.forms[0].submit();
}
function onQueryResultsConfigure()
{

	 action="DefineQueryResultsView.do?pageOf=pageOfQueryModule";
	 document.forms[0].action = action;
	 document.forms[0].target = "_self"
	 document.forms[0].submit();
}
function onRedefineSimpleQuery()
{
	action="SimpleQueryInterface.do?pageOf=pageOfSimpleQueryInterface&operation=redefine";
	document.forms[0].action = action;
	document.forms[0].target = "_self";
	document.forms[0].submit();
}
function onRedefineAdvanceQuery()
{
	action="AdvanceQueryInterface.do?pageOf=pageOfAdvanceQueryInterface&operation=redefine";
	document.forms[0].action = action;
	document.forms[0].target = "_self";
	document.forms[0].submit();
}
function onRedefineDAGQuery()
{
	waitCursor();
	document.forms[0].action='SearchCategory.do?currentPage=resultsView';
	document.forms[0].target = "_self";
	document.forms[0].submit();
	hideCursor();
}
var selected;

function addCheckBoxValuesToArray(checkBoxName)
{
	var theForm = document.forms[0];
    selected = new Array();

    for(var i = 0, j = 0; i < theForm.elements.length; i++)
    {
 	  	if(theForm[i].type == 'checkbox' && theForm[i].checked == true)
	        selected[j++] = theForm[i].value;
	}
}

   //Commented out By Baljeet...

function callAction(action)
{
	document.forms[0].action = action;
	document.forms[0].submit();
}
	
	
function getSpecimenIdList()
{
	var checkedRows = mygrid.getCheckedRows(0);
	var idColumn = getIDColumnsForSpecimen();
	var n = checkedRows.split(",");
	var specIds = "";
	
	for(var i = 0; i < n.length; i++)	{
		specIds = specIds + mygrid.cellById(n[i],  idColumn).getValue()+ ",";
	}
	return specIds;
}

// for pop-up div
function addToSpecimenList() {
	var idSpecimenPresent = isIDColumnsForSpecimen();
	if(idSpecimenPresent == null || idSpecimenPresent == '') {
		alert('Specimen:Id missing in the grid! Please redefine the view and include Specimen:Id to use the Specimen List feature');
	} else {
		var checkedRows = mygrid.getCheckedRows(0);
		if(checkedRows != "") {
			ajaxTreeGridInitCall('Are you sure you want to delete this specimen from the list?',
									'List contains specimens, Are you sure to delete the selected list?',
									'SpecimenListTag','SpecimenListTagItem');
		} else {
			alert("Please select atleast one specimen");
		}
	}
 }

function onResponseSet(response) 
{	
	var specimenIDS = response;
	giveCall('AssignTagAction.do?entityTag=SpecimenListTag&entityTagItem=SpecimenListTagItem',
			'Select at least one existing list or create a new list.',
			'No list has been selected to assign.', specimenIDS);
}

function assignToSpecimenList()
{
	var specimenIDS = getSpecimenIdList();

	if(isCheckAllPagesChecked)
	{
		var isCheckAllAcrossAllChecked = document.getElementById('checkAll').checked;
		var specId =  getIDColumnsForSpecimen();
		var parameter = "specIndex="+specId+"&operation=add&pageNum="+pageNum+"&isCheckAllAcrossAllChecked="+isCheckAllAcrossAllChecked;
		var url = "CatissueCommonAjaxAction.do?type=getSpecimenIds&"+parameter;

		request = newXMLHTTPReq();
		request.onreadystatechange = getReadyStateHandler(request,onResponseSet,true);	;	
		request.open("POST", url, true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send("");
	} else {		
		giveCall('AssignTagAction.do?entityTag=SpecimenListTag&entityTagItem=SpecimenListTagItem&isCheckAllAcrossAllChecked='+isCheckAllPagesChecked,
					'Select at least one existing list or create a new list.','No list has been selected to assign.',specimenIDS);
	}
	
}

function giveCall(url, msg, msg1, id)
{		
	document.getElementById('objCheckbox').checked = true;
	document.getElementById('objCheckbox').value = id;
	ajaxAssignTagFunctionCall(url, msg, msg1);
}