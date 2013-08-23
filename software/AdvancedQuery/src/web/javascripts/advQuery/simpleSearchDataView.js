function onAddToCart() { 
	checkRowStatus(); 
	 
    if(checkedRowIds.length > 0) { 
    	var action = "ShoppingCart.do";
		if(isQueryModule == "true") {
			action = "AddDeleteCart.do";			
		} 
		var gridDataJson = getJsonfromGridData();
		var url = action + "?operation=add&pageNum="+mygrid.currentPage+"&isCheckAllAcrossAllChecked=false";
	
		var request = newXMLHTTPReq(); 
		request.onreadystatechange = getReadyStateHandler(request, displayStatus, true);
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		request.send(getCheckedField()+"&gridDataJson="+gridDataJson);
		window.setTimeout(unCheckGridRows, 500);
	} else {
		alert("Please select at least one checkbox");
	}   
}

function getCheckedField() {
	checkRowStatus();
	var params = "";
	for(var i=0; i<checkedRowIds.length; i++){
		params += "value1(CHK_" + i + ")=1&";
	}
	return params;
}

function displayStatus(response) {
	var test = new String(response);
	if(test.indexOf("messagetextsuccess")>=0){
		document.getElementById("errorStatus").style.color = "green";
		document.getElementById('errorStatus').innerHTML = "Records has been sucessfully added to list";
	} else {
		document.getElementById("errorStatus").style.color = "red";
		document.getElementById('errorStatus').innerHTML = "Cannot add in to List as view of List is different";
	}
	document.getElementById('errorStatus').innerHTML = response;
}

function getData()
{	//ajax call to get update data from server
	var param = "updateSessionData=updateSessionData";
	var url = "ValidateQuery.do";
	
	var request = newXMLHTTPReq();
	request.onreadystatechange = getReadyStateHandler(request, displayValidationMessage, true);
	request.open("POST", url, true);
	request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	request.send(param);
}

function displayValidationMessage(message)
{	document.getElementById("messageDiv").style.display = "none";
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
	
function onExport(form) {	var iframe = document.createElement("iframe");	iframe.setAttribute("id", "upload_iframe");	iframe.setAttribute("name", "upload_iframe");	iframe.setAttribute("width", "0");	iframe.setAttribute("height", "0"); 	iframe.setAttribute("border", "0");	iframe.setAttribute("style", "width: 0; height: 0; border: none;");			form.parentNode.appendChild(iframe);	window.frames['upload_iframe'].name = "upload_iframe";	iframeId = document.getElementById("upload_iframe");	// Add event...		var eventHandler = function () {       	if (iframeId.detachEvent) {		iframeId.detachEvent("onload", eventHandler);	} else {		iframeId.removeEventListener("load", eventHandler, false);	}        	// Message from server...        if (iframeId.contentDocument) {		content = iframeId.contentDocument.body.innerHTML;		alert(content);       	} else if (iframeId.contentWindow) {		content = iframeId.contentWindow.document.body.innerHTML;		alert(content);        } else if (iframeId.document) {           	content = iframeId.document.body.innerHTML;		alert(content);        }        	// Del the iframe...        	setTimeout('iframeId.parentNode.removeChild(iframeId)', 250);    	}	if (iframeId.addEventListener) {		iframeId.addEventListener("load", eventHandler, true);	}		if (iframeId.attachEvent) {		iframeId.attachEvent("onload", eventHandler);	}	// Set properties of form...	checkRowStatus();	var jsonData;	var isCheckAllAcrossAllChecked = false	if(checkedRowIds.length == 0){		isCheckAllAcrossAllChecked = true;		if(mygrid.getRowsNum() < fetchRecordSize)		{			jsonData = getAllDatafromGrid();			isCheckAllAcrossAllChecked = false;		}	}else{		jsonData = getJsonfromGridData();	}	 	document.getElementById('jsonData').value = jsonData;	var action = "QueryDataExport.do?pageNum="+mygrid.currentPage+"&isCheckAllAcrossAllChecked="+isCheckAllAcrossAllChecked;	 	form.setAttribute("target", "upload_iframe");	form.setAttribute("action", action);	form.setAttribute("method", "post");	form.setAttribute("operation.value","export");			// Submit the form...	form.submit();	window.setTimeout(removeLoading, 500);		if(checkedRowIds.length > 0){		window.setTimeout(unCheckGridRows, 500);	}}
function getJsonfromGridData(){
	var jsonData;	
	checkedRowIds.sort(function(a,b){return a-b;});	
	jsonData = "[";
	for(var i = 0; i < checkedRowIds.length; i++){
		var data = mygridData.rows[checkedRowIds[i]].data.join();
		jsonData += "\"[" + data.substring(1) + "]\",";
	}
	jsonData += "]"
	return jsonData;	
}

function getAllDatafromGrid(){	
	var jsonData;	
	jsonData = "[";	
	for(var i = 0; i < mygridData.rows.length; i++){		
		var data = mygridData.rows[i].data.join();		
		jsonData += "\"[" + data.substring(1) + "]\",";
	}	
	jsonData += "]"	
	return jsonData;
}

function removeLoading() {
	var superiframe = document.getElementById('superiframe');
	superiframe.className = "HiddenFrame";
}

function unCheckGridRows(){	
	for(var i=0; i<checkedRowIds.length; i++){
		var cell = mygrid.cells(checkedRowIds[i], 0).cell.childNodes[0];			
		(new eXcell_ch(cell.parentNode)).changeState(); 
	}
	var checkBoxCell = getCheckBox();
	checkBoxCell.childNodes[0].checked = false;
	checkedAllPages = new Array();
	checkedRowIds = new Array();
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

function callAction(action)
{
	document.forms[0].action = action;
	document.forms[0].submit();
}
	
function getSpecimenIdList()
{
	checkRowStatus();
	var specIds = "";
	if(checkedRowIds.length > 0) {
		for(var i = 0; i < checkedRowIds.length; i++)	{
			specIds = specIds + mygrid.cellById(checkedRowIds[i],  specimentIdColIndex).getValue()+ ",";
		}
	}
	return specIds;
}

// for pop-up div
function addToSpecimenList() {
	
	if(specimentIdColIndex == -1) {
		alert('Specimen:Id missing in the grid! Please redefine the view and include Specimen:Id to use the Specimen List feature');
	} else {
		document.getElementById("assignListbtn").style.display="block";
		document.getElementById("loadingImg").style.display="none";
		ajaxTreeGridInitCall('Are you sure you want to delete this specimen from the list?',
									'List contains specimens, Are you sure to delete the selected list?',
									'SpecimenListTag','SpecimenListTagItem');
		
	}
 }

function onResponseSet(response) 
{	
	var specimenIds = response;
	assignSpecimens('AssignTagAction.do?entityTag=SpecimenListTag&entityTagItem=SpecimenListTagItem',
			'Select at least one existing list or create a new list.',
			'No list has been selected to assign.', specimenIds);
}

function assignToSpecimenList()
{	document.getElementById("loadingImg").style.display="block";
	var specimenIds = getSpecimenIdList();
	if(specimenIds == "")
	{		
		var specIds = getSpecIds(specimentIdColIndex);
		/*var specId =  specimentIdColIndex;
		var parameter = "specIndex="+specId+"&operation=add&pageNum="+pageNum+"&isCheckAllAcrossAllChecked=true";
		var url = "CatissueCommonAjaxAction.do?type=setSpecimenIds&"+parameter;

		request = newXMLHTTPReq();
		request.onreadystatechange = getReadyStateHandler(request,onResponseSet,true);	;	
		request.open("POST", url, true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send("specIds="+specIds);*/
		assignSpecimens('AssignTagAction.do?entityTag=SpecimenListTag&entityTagItem=SpecimenListTagItem',
				'Select at least one existing list or create a new list.',
				'No list has been selected to assign.', specIds);
	} else {		
		assignSpecimens('AssignTagAction.do?entityTag=SpecimenListTag&entityTagItem=SpecimenListTagItem&isCheckAllAcrossAllChecked=false',
					'Select at least one existing list or create a new list.','No list has been selected to assign.', specimenIds);
		unCheckGridRows();
	}
	 
}

function getSpecIds(specimentIdColIndex){
	var specIds	= "";
	for(var i = 0; i < mygridData.rows.length; i++){
		var data = mygridData.rows[i].data[specimentIdColIndex];
		specIds = specIds + data.replace(/\s/g, ",")
	}
	return specIds;	
}
function assignSpecimens(url, msg, msg1, id)
{	
	document.getElementById('objCheckbox').checked = true;
	document.getElementById('objCheckbox').value = id;
	ajaxAssignTagFunctionCall(url, msg, msg1);
}
