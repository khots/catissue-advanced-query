<!-- author amit_doshi -->
<%@ page import="edu.wustl.query.util.global.Constants"%>	
<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>

<%@page import="edu.wustl.common.vocab.IVocabulary"%>
<%@page import="edu.wustl.common.vocab.utility.VocabUtil"%>
<%@page import="edu.wustl.query.util.global.VIProperties"%>
<%@page import="edu.wustl.vi.enums.VISearchAlgorithm"%>

<%@ page language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
<head>
	
<title></title>
	<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTree.css">
	<link rel="stylesheet" type="text/css" href="css/advancequery/catissue_suite.css" />
	<link href="css/advancequery/inside.css" rel="stylesheet" type="text/css" media="screen">
 <script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlXCommon.js"></script>
	<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmXTreeCommon.js"></script>
	<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlXTree.js"></script>
	<script src="jss/advancequery/ajax.js" type="text/javascript"> </script>
	<script src="jss/advancequery/VI.js" type="text/javascript"> </script>
	<script src="jss/advancequery/queryModule.js" type="text/javascript"> </script>
	<script>
	<%
	String srcVocabURN = VIProperties.sourceVocabUrn;
	String sourceVocabMessage =(String)request.getSession().getAttribute(Constants.SRC_VOCAB_MESSAGE); 
	%>
	// selectedPvs will store that appears right side of the page
	selectedPvs =new Array();
	// selectedPvsCheckedBoxIdArray will store the  selected PVs from vocabulary that will deleted after clicked on add and stored in selectedPvs
	selectedPvsCheckedBoxIdArray =new Array();
	selectedPvsCheckedBox=0;
	numberOfPvs=0;
	//variable is used to set the current status of mode
	set_mode="Mapping";
	var label;
	var pervVocabCheckboxId="vocab_"+'<%=srcVocabURN%>';
	var searchImg="<img src='images/advancequery/loading_msg.gif' border='0' alt='Searching...' >   ";
	/*to close the model widow*/
function cancelWindow()
{
	parent.pvwindow.hide();
}
	
//When user click on the add button to add permissible values in the selected list
function addPermissibleValuesToList()
{	
	
 var checkedNode=0;
//Inserting first cell as checkbox for deleting selected rows
 for(j=0;j<selectedPvsCheckedBoxIdArray.length;j++)
	{
	if(!selectedPvs.inArray(selectedPvsCheckedBoxIdArray[j]) && selectedPvsCheckedBoxIdArray[j]!='' && selectedPvsCheckedBoxIdArray[j]!='undefined'  )
	{
		try{
				var vocabIdDetails=selectedPvsCheckedBoxIdArray[j].split('<%=Constants.ID_DEL%>'); // selectedPvsCheckedBoxIdArray[j]  format MED1.0:52014
				
				if(vocabIdDetails.length>1) // No need to include Root node in list make because its has id as only MED name not CONCEPTCODE:Permissible Value
				{
					
					if(set_mode=="Searching") 
					{
						var conceptDetail=document.getElementById("srh_"+selectedPvsCheckedBoxIdArray[j]).value;
						createRows(vocabIdDetails[0],selectedPvsCheckedBoxIdArray[j],conceptDetail);
					}
					else if(set_mode=="Mapping")
					{
						var conceptDetail=document.getElementById(selectedPvsCheckedBoxIdArray[j]).value;
						createRows(vocabIdDetails[0],selectedPvsCheckedBoxIdArray[j],conceptDetail);
					}
				}
			}catch(e){}
		
	}
	}
}
// method is used to create the rows in the talbe
function createRows(vocabURN,selectedPvsCheckedBoxId,conceptDetail)
{
				
					var table = document.getElementById('selectedPermValues_'+vocabURN);
					var lastRow = table.rows.length;
					var row = table.insertRow(lastRow); 
					if(lastRow>=0)
						{
						document.getElementById('selectedPermValues_Div_'+vocabURN).style.display = '';;
						}
						
						//inserting cell as checkbox
						var cell1 = row.insertCell(0);
						cell1.className="black_ar_td_dy"; 
						cell1.innerHTML= "&nbsp;&nbsp;&nbsp;";
						//inserting cell as checkbox
						var cell2 = row.insertCell(1);
						cell2.className="black_ar_td_dy"; 
						cell2.innerHTML= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						
						//inserting cell as checkbox
						var cell3 = row.insertCell(2);
						cell3.className="black_ar_td_dy"; 
						
						
						var chkBox = createNamedElement('input','checkboxName_'+vocabURN);
						chkBox.setAttribute('type', 'checkbox');
						chkBox.setAttribute('id', selectedPvsCheckedBoxId);
						chkBox.setAttribute('value',conceptDetail );
						cell3.className="black_ar_td_dy"; 
						cell3.align = 'left';
						cell3.appendChild(chkBox);
						
						//Inserting 2nd cell as textBox
						var cell4 = row.insertCell(3);
						cell4.className="black_ar_td_dy"; 
						cell4.align = 'left';
						
						conceptName=conceptDetail.substring(conceptDetail.indexOf(":")+1); // to display name only
						cell4.innerHTML= conceptName;
							
						row.myRow = new rowObj(chkBox);
						
						selectedPvs[numberOfPvs]=selectedPvsCheckedBoxId;
						numberOfPvs++;
						
						//uncheckAllSelectedPVs(vocabName[0]);
				
}
function rowObj(one)
{
	this.one=one;
}
/*method is used to delete the selected row as well all row in one go*/
function deleteSelectedPvsRow()
{
	
	var checkedObjtodelete=new Array();
	var cCount = 0;
	for(j=0;j<selectedPvs.length;j++)
	{
	try{
		var vocabIdDetails=selectedPvs[j].split('<%=Constants.ID_DEL%>');
		if(vocabIdDetails.length>1) // No need to include Root node in list make because its has id as only MED name not CONCEPTCODE:Permissible Value
		{
			var checkedObjArray = new Array();
			var vocabURN=vocabIdDetails[0];
			var table = document.getElementById('selectedPermValues_'+vocabURN);
			for (var i=0; i<table.rows.length; i++)
			{		
				if (table.rows[i].myRow && table.rows[i].myRow.one.getAttribute('type') == 'checkbox' && table.rows[i].myRow.one.checked)
				{
					checkedObjArray[cCount] = table.rows[i];
					checkedObjtodelete[cCount]=table.rows[i].myRow.one.getAttribute('id');
					deleteFromArray(checkedObjtodelete,selectedPvs);
					cCount++;
				}
			}
			deleteRows(checkedObjArray);
			cCount=0;
			if(table.rows.length==0)
			{
				document.getElementById("pvSelectedCB_"+vocabURN).checked=false;
				document.getElementById('selectedPermValues_Div_'+vocabURN).style.display = 'none';
						
			}
				
		}
	}catch(e){}
	}
		deleteFromArray(checkedObjtodelete,selectedPvs);
		selectedPvs=removeElementsFromArray(selectedPvs, isNullOrUndefined);
}
//method is used to delete the rows from the table
function deleteRows(rowObjArray)
{
	for (var i=0; i<rowObjArray.length; i++) 
	{
	   var rIndex = rowObjArray[i].sectionRowIndex;
		rowObjArray[i].parentNode.deleteRow(rIndex);
	}
}
	
//method called when user clicks on OK button
function addPvsToCondition()
{
	var pvConceptCodeList="";
	var pvNameListWithCode="";
	var pvNameList="";
	for(var k=0;k<selectedPvs.length;k++)
	{
		try
		{
			if(document.getElementById(selectedPvs[k]).value!='undefined');
			{
				var selectedIdFromList=document.getElementById(selectedPvs[k]).value;
				var selectedIdFromListWithoutCode=selectedIdFromList.substring(selectedIdFromList.indexOf(":")+1);
				//require for UI javascript and set the values to parent window
				pvNameList=pvNameList+selectedIdFromListWithoutCode.trim()+"D#N";
				pvConceptCodeList=pvConceptCodeList+selectedPvs[k]+"D#C";
				//required to store in session
				pvNameListWithCode=pvNameListWithCode+document.getElementById(selectedPvs[k]).value+"D#C";
			}					
		}catch(e)
		{}
			
	}
		sendValueToParent(pvConceptCodeList,pvNameListWithCode,pvNameList)
}
// this method will send the selected values to the parent window from open child window
function sendValueToParent(pvConceptCodeList,pvNameListWithCode,pvNameList)
{
		parent.window.getValueFromChild(pvConceptCodeList,pvNameList);
		parent.pvwindow.hide();
}

//This method will be called when user clicks on the vocabulary check box
function getMappingsOfConcepts(vocabCheckBoxId,vocabURN)
{
		continueMapping=true;
		if(! isSelectedPVListEmpty() && pervVocabCheckboxId!=vocabCheckBoxId) //Bug Fixed : #11767
		{
			continueMapping=confirm("All the selected Permissible Values will be removed. Do you want to continue? ");  
		}
		if(! continueMapping)
		{
			document.getElementById(pervVocabCheckboxId).checked=true;
		}
	if(set_mode=="Mapping")
	{
		var vocabDisplayName=document.getElementById("hidden_"+vocabURN).value;
		document.getElementById("divForMappingMode").style.display = '';
		document.getElementById("divForSearchingMode").style.display = 'none';
		var request = newXMLHTTPReq(); 
		var selectedCheckBox="";
		if(request == null)
		{
			alert ("Your browser does not support AJAX!");
			return;
		}
		var selectedCheckedBoxVocabDivID="main_div_"+vocabCheckBoxId;
		
		if(document.getElementById(vocabCheckBoxId).checked && continueMapping )
		{
				 var innerData=document.getElementById(selectedCheckedBoxVocabDivID).innerHTML.trim();
				 
				 if(document.getElementById(selectedCheckedBoxVocabDivID).style.display == 'none' && innerData.length==0)
					{
					
						label.innerHTML=searchImg+"Loading Permissible Values...";
						waitCursor();
						
						 document.getElementById(selectedCheckedBoxVocabDivID).style.display = '';
						 // send request only first time when user click on the check box for other click  just hide and show the div 
						
						var param = "selectedCheckBox"+"="+vocabURN;
						var actionUrl="SearchMappedPV.do";
						request.onreadystatechange=function(){setMappedConceptsToVocabDIV(request,selectedCheckedBoxVocabDivID)};
						request.open("POST",actionUrl,true);
						request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
						request.send(param);
						
						 
					}
					else if(innerData.length>0)
					{
					  document.getElementById(selectedCheckedBoxVocabDivID).style.display = '';
					  document.getElementById("divForMappingMode").scrollTop=0;
					  document.getElementById("divForMappingMode").scrollLeft=0;
					}
					
			pervVocabCheckboxId=vocabCheckBoxId;
				 
		}
		else if (! document.getElementById(vocabCheckBoxId).checked)
		{
				 document.getElementById(selectedCheckedBoxVocabDivID).style.display = 'none';
				
		}
	}
	if(continueMapping)
		{
		var elements=document.getElementsByName("vocabNameAndVersionCheckbox");
				for(var i=0;i<elements.length;i++)
				{
					if(elements[i].id==vocabCheckBoxId)
					{
						document.getElementById("main_div_"+elements[i].id).style.display = '';
					}
					else
					{
						var vURN=elements[i].id.replace("vocab_","");
						document.getElementById("main_div_"+elements[i].id).style.display = 'none';
						uncheckAllAndDeleteFromArray(vURN);
						//need to delete all the rows of right hand side selected list
						tableid =document.getElementById("selectedPermValues_"+vURN);
						for(var j=0;j<tableid.rows.length;j++)
						{
							tableid.rows[j].myRow.one.checked=1;
						}
						deleteSelectedPvsRow();
					}
						
				}
		}
	
}
function isSelectedPVListEmpty()
{
		var elements=document.getElementsByName("vocabNameAndVersionCheckbox");
		for(var i=0;i<elements.length;i++)
		{
				var vURN=elements[i].id.replace("vocab_","");
				var tableid =document.getElementById("selectedPermValues_"+vURN);
				if(tableid.rows.length>0)
				{
					return false;
				}
		}
		return true;
}
/* used to set the data in the div based the which checked box clicked */
function setMappedConceptsToVocabDIV(request,selectedCheckedBoxVocabDivID)
{
	if(request.readyState == 4)  
	{	  		
		if(request.status == 200)
		{	
			var responseTextValue =  request.responseText;	
			var searchHTML=responseTextValue.split('<%=Constants.MSG_DEL%>');
			if(searchHTML.length>1)
			{
				document.getElementById(selectedCheckedBoxVocabDivID).innerHTML=searchHTML[0];
				label.innerHTML=searchHTML[1];
			//document.getElementById(selectedCheckedBoxVocabDivID).innerHTML=responseTextValue;
			//label.innerHTML="";
			}
			else
			{
				document.getElementById(selectedCheckedBoxVocabDivID).innerHTML=responseTextValue;
				label.innerHTML="";
			}
			hideCursor();
			document.getElementById("divForMappingMode").scrollTop=0;
			document.getElementById("divForMappingMode").scrollLeft=0;
			tt_Hide(); // to hide the tooltip on the already shown concept concepts
			}
	}
};

 /*store all the ids of selected checkbox in the array*/
function getCheckedBoxId(checkedBoxId)
{
 	if(document.getElementById(checkedBoxId).checked)
	{
		checkedBoxId=checkedBoxId.replace("srh_","");//if ids for search result 
		selectedPvsCheckedBoxIdArray[selectedPvsCheckedBox]=checkedBoxId;
		selectedPvsCheckedBox++;
	}
	else if(document.getElementById(checkedBoxId).checked==0)
	{
		checkedBoxId=checkedBoxId.replace("srh_","");//if ids for search result
		deleteValueFromArray(checkedBoxId,selectedPvsCheckedBoxIdArray);
		
	}
}

/********************  CHECKED and UNCHECKED METHOS for CHECKBOXES ************************/
/*when user click on the root node ro checkbox of the Vocabulary*/
function setStatusOfAllCheckBox(rootCheckedBoxId)
{
	if(document.getElementById(rootCheckedBoxId).checked)
	{
		rootCheckedBoxId=rootCheckedBoxId.replace("root_","");
		checkallAndAddToArray(rootCheckedBoxId)
	}
	else
	{
		rootCheckedBoxId=rootCheckedBoxId.replace("root_","");
		uncheckAllAndDeleteFromArray(rootCheckedBoxId)
	}
}
 
 /* to unchecked all uncheckAllSelectedPVs */
function uncheckAllSelectedPVs(rootCheckedBoxId)
{
	void(d2=document);
	void(e2=d2.getElementsByName(rootCheckedBoxId));
	for(i=0;i<e2.length;i++)
	{
		void(e2[i].checked=0)
	}
}
 /* to unchecked all checkAllPVs */
function checkAllPVs(rootCheckedBoxId)
{
	void(d2=document);
	void(e2=d2.getElementsByName(rootCheckedBoxId));
	for(i=0;i<e2.length;i++)
	{
		void(e2[i].checked=1)
	}
}
 /* to checked all checkboxes and srote selected values in the array*/
function checkallAndAddToArray(rootCheckedBoxId)
{
	void(d=document);
	void(el=d.getElementsByName(rootCheckedBoxId));
	for(i=0;i<el.length;i++)
	{
		if(el[i].disabled==false)
		{
		void(el[i].checked=1)
		getCheckedBoxId(el[i].id);
		}
	}
}
/* to unchecked all checkboxes and delete values from array */
function uncheckAllAndDeleteFromArray(rootCheckedBoxId)
{

		void(d2=document);
		void(e2=d2.getElementsByName(rootCheckedBoxId));
		for(var i=0;i<e2.length;i++)
		{
			void(e2[i].checked=0)
			//deleteValueFromArray(e2[i].id,selectedPvsCheckedBoxIdArray); Changes regarding Bug Fixed :11810
		}
		selectedPvsCheckedBoxIdArray=new Array(); //delete all selected PVs by assignin new array
}
	
function checkedUncheckedAllPvs(vocabName)
{
	if(document.getElementById("pvSelectedCB_"+vocabName).checked)
	{
		checkAllPVs('checkboxName_'+vocabName);
	}
	else
	{
		uncheckAllSelectedPVs('checkboxName_'+vocabName);
	}
}
	
/***************************************************** Searching  Term Methods*******************************/
//method to search for the given term in vocabularies

var searchRequest;
var operationAborted;
function serachForTermInVocab(operation)
{
	 searchRequest= newXMLHTTPReq(); 
	 operationAborted=false;
	var searchTerm=document.getElementById("searchtextfield").value;
	//var findExactMatch=document.getElementById("searchCriteria").checked;
	void(d=document);
	var vocabCheckboxes=d.getElementsByName("vocabNameAndVersionCheckbox");
	var criteria=getSelectedSearchCriteria();
	var currentcheckedBoxID;
	var targetVocabsForSearchTerm="";
			
			for(i=0;i<vocabCheckboxes.length;i++)
			{
				if(vocabCheckboxes[i].checked==1)
				{
					var vocabURN=vocabCheckboxes[i].id.replace("vocab_","");
					var vocabDisplayName=document.getElementById("hidden_"+vocabURN).value;
					targetVocabsForSearchTerm=targetVocabsForSearchTerm+vocabURN+"##"+vocabDisplayName+"@";
					uncheckAllAndDeleteFromArray(vocabURN);
					currentcheckedBoxID=vocabCheckboxes[i].id;
					
				}
			}
	var message="Please enter the search term.";
	if(checkForEmptyText(searchTerm,message) && isVocabSelected(targetVocabsForSearchTerm))
	{
	
			label=document.getElementById("searhLabel");
			var searchAbortButtonDiv=document.getElementById("searchAbortButtonDiv");
		    label.innerHTML=searchImg+"Searching for the Permissible Values that match your search criteria.";
			if(operation=="search") // if operation is search change the button to abort and set the flag
			{
				searchAbortButtonDiv.innerHTML="<a  href=\"javascript:serachForTermInVocab('abort');\"><img src='images/advancequery/b_abort.gif' border='0' alt='abort' ></a>";
				operationAborted=false;
			}
			else // if operation is abort change the button to search and set the flag
			{
				searchAbortButtonDiv.innerHTML="<a  href=\"javascript:serachForTermInVocab('search');\"><img src='images/advancequery/b_go.gif' border='0' alt='Search' ></a>";
				searchRequest.abort();
				operationAborted=true;
			}
			
		    waitCursor();
			if(searchRequest == null)
			{
				alert ("Your browser does not support AJAX!");
				return;
			}
			// send request only first time when user click on the check box for other click  just hide and show the div 
			searchTerm=encodeURIComponent(searchTerm); /* This is required because user can enter any term which can contains spl char like & ,% and ^ etc*/ 
			var param = "searchTerm="+searchTerm+"&targetVocabsForSearchTerm="+targetVocabsForSearchTerm+"&operation="+operation+"&searchCriteria="+criteria;
			var actionUrl="SearchPermissibleValues.do";
			searchRequest.onreadystatechange=function(){getSearchTermResult(searchRequest)};
			searchRequest.open("POST",actionUrl,true);
			searchRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			searchRequest.send(param);
			pervVocabCheckboxId=currentcheckedBoxID;
			
	}
}
	
function getSearchTermResult(searchRequest)
 {
	if(searchRequest.readyState == 4)  
	{	  		
		if(searchRequest.status == 200)
		{	
			var responseTextValue =  searchRequest.responseText;
			set_mode="Searching"
			document.getElementById("divForMappingMode").style.display = 'none';
			document.getElementById("divForSearchingMode").style.display = '';
			if( ! operationAborted)
			{ 
				// if user has not aborted the requrest then set the html result to div and clear the search message from message label
				searchHTML=responseTextValue.split('<%=Constants.MSG_DEL%>');
				if(searchHTML.length>1)
				{
					document.getElementById("divForSearchingMode").innerHTML=searchHTML[0];
					document.getElementById("divForSearchingMode").scrollTop=0;
					document.getElementById("divForSearchingMode").scrollLeft=0;
					label.innerHTML=searchHTML[1];
				}
				else
				{
					document.getElementById("divForSearchingMode").innerHTML=responseTextValue;
					document.getElementById("divForSearchingMode").scrollTop=0;
					document.getElementById("divForSearchingMode").scrollLeft=0;
					label.innerHTML="";
				}
			}
			else
			{
				// if user has  aborted the request then set the aborted successfully message to message label
				label.innerHTML=responseTextValue;
			}
			var searchAbortButtonDiv=document.getElementById("searchAbortButtonDiv");
			searchAbortButtonDiv.innerHTML="<a  href=\"javascript:serachForTermInVocab('search');\"><img src='images/advancequery/b_go.gif' border='0' alt='Search' ></a>";
			hideCursor();
			tt_Hide(); // to hide the tooltip on the already shown concept concepts
		}
	}
};
/* this method returns the selected criteria for search*/
function getSelectedSearchCriteria()
{
	var searchCriteria=document.getElementsByName("searchCriteria");
	var criteria="";
	for(i=0;i<searchCriteria.length;i++)
			{
				if(searchCriteria[i].checked==1)
				{
					criteria=document.getElementById(searchCriteria[i].id).value;
					break;
				}	
			}
	return criteria;
}
/* this method will be used in case of multiple vocabulary*/
function isVocabSelected(targetVocabsForSearchTerm)
{
	if(targetVocabsForSearchTerm.length>0)
	{
		return true;
	}
	else
	{
		alert("Please select atleat one Vocabulary.");
		return false;
	}
}
/*this method will be called when user clicks on restore default*/
function restoreDefaultVocab()
{
	var reset=confirm("Do you want to load the default vocabulary ? ");  
	if(reset)
	{
		if(parent.conceptCodes.length>0) //edit mode
		{
			restoreDefault();
			var checkBoxId="vocab_"+'<%=srcVocabURN%>';
			var selectedCheckedBoxVocabDivID="main_div_"+checkBoxId;
			document.getElementById(selectedCheckedBoxVocabDivID).style.display = 'none';
			getMappingsOfConcepts(checkBoxId,'<%=srcVocabURN%>');
		}
		else
		{
			restoreDefault();
		}
	}
}
function restoreDefault()
{
	set_mode="Mapping";
	document.getElementById("divForMappingMode").style.display = '';
	document.getElementById("divForMappingMode").scrollTop=0;
	document.getElementById("divForMappingMode").scrollLeft=0;
	document.getElementById("divForSearchingMode").style.display = 'none';
	document.getElementById("divForSearchingMode").innerHTML="";
	document.getElementById("searchtextfield").value="";
	document.getElementById("findAnyWord").checked=true;
	label.innerHTML="";
	pervVocabCheckboxId="vocab_"+'<%=srcVocabURN%>';
	<%if(sourceVocabMessage!=null)
	{%>
		//set the messgage if concept code greater then configured value
		label.innerHTML='<%=sourceVocabMessage%>';
	<%}%>
	var targetVocabsForMappingMode="";
	void(d=document);
	void(vocabCheckboxes=d.getElementsByName("vocabNameAndVersionCheckbox"));
	for(l=0;l<vocabCheckboxes.length;l++)
	{
		var selectedCheckedBoxVocabDivID="main_div_"+vocabCheckboxes[l].id;
		var vocabURN=vocabCheckboxes[l].id.replace("vocab_","");
		if(vocabURN.equalsIgnoreCase('<%=srcVocabURN%>') )
		{
			vocabCheckboxes[l].checked=1;
			document.getElementById(selectedCheckedBoxVocabDivID).style.display = '';
			uncheckAllAndDeleteFromArray(vocabURN);
		}
		else
		{
			vocabCheckboxes[l].checked=0;
			document.getElementById(selectedCheckedBoxVocabDivID).style.display = 'none';
			uncheckAllAndDeleteFromArray(vocabURN);
		}
		
		tableid =document.getElementById("selectedPermValues_"+vocabURN);
		for(var j=0;j<tableid.rows.length;j++)
		{
					tableid.rows[j].myRow.one.checked=1;
		}
		deleteSelectedPvsRow();
	}
	selectedPvsCheckedBoxIdArray=new Array();
	selectedPvsCheckedBox=0;
	numberOfPvs=0;
	set_mode="Mapping";
};
/*this method is used when MED Entity open in edit mode from Edit Query*/
function editSelectedPV()
{
	label=document.getElementById("searhLabel");
	<%if(sourceVocabMessage!=null && !sourceVocabMessage.equals("null"))
	{%>
		//set the messgage if concept code greater then configured value
		label.innerHTML='<%=sourceVocabMessage%>';
	<%}%>
	/* if the VI pop is opened in edit mode*/
	var conceptCodesArray=parent.conceptCodes;
	for(k=0;k<conceptCodesArray.length;k++)
	{

		var conceptDetailWithURN=conceptCodesArray[k].split('<%=Constants.ID_DEL%>');
		var vocabURN=conceptDetailWithURN[0];
		var conceptCode=conceptDetailWithURN[1];
		var conceptName=conceptDetailWithURN[2];
		var conceptDetail=":"+conceptName;
		var checkboxId=vocabURN+'<%=Constants.ID_DEL%>'+conceptCode;
		createRows(vocabURN,checkboxId,conceptDetail);
		document.getElementById("vocab_"+vocabURN).checked=true;
		pervVocabCheckboxId="vocab_"+vocabURN;
	}
};

/*  To support different window resolution */
	   var windowWidth=(screen.width * 90 )/100;
	   var windowHeight=( screen.height * 65)/100;
	   divWidth=windowWidth*44/100;
	   divHeight=windowHeight*64/100;
	   if(screen.height>768)
	   {
		divHeight=divHeight+42;
	   }
/* This will search the form upon pressing enter key */   
function keypress(e) // Bug Fixed # 11683
{
   var KeyID = (window.event) ? event.keyCode : e.keyCode;
   if(KeyID == 13)
	{
	   serachForTermInVocab('search');
	}
}   

</script>
</head>
<body onLoad="editSelectedPV();">
<script src="jss/advancequery/wz_tooltip.js" type="text/javascript"> </script>
<table width="100%"   border="0" cellspacing="0" cellpadding="4"><tr><td>
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td class="content_txt_bold" width="5%" nowrap>Select Vocabulary:</td>
		<td colspan="2" >
		<table cellpadding="1" cellspacing="0" >
			<tr>
			<c:set var="srcVocabURN" value="<%=srcVocabURN%>"/>
			<logic:iterate name="Vocabulries" id="vocabs">
			<c:set var="urn" value="${vocabs.vocabURN}" />
					<c:choose>
						<c:when test="${vocabs.vocabURN eq srcVocabURN}">
								<td><input type="radio"  name="vocabNameAndVersionCheckbox" id="vocab_${vocabs.vocabURN}" value="${vocabs.name}:${vocabs.version}"   
								onclick= "getMappingsOfConcepts(this.id,'${vocabs.vocabURN}');" checked='true'></td><td class="content_txt">&nbsp;&nbsp;${vocabs.displayName}&nbsp;&nbsp;&nbsp;
								<input type="hidden"id="hidden_${vocabs.vocabURN}" value="${vocabs.displayName}"/>

								</td>			
						</c:when>
						<c:otherwise>
								<td><input type="radio"  name="vocabNameAndVersionCheckbox" id="vocab_${vocabs.vocabURN}" value="${vocabs.name}:${vocabs.version}"   
								onclick= "getMappingsOfConcepts(this.id,'${vocabs.vocabURN}');"></td><td class="content_txt">&nbsp;&nbsp;${vocabs.displayName}&nbsp;&nbsp;&nbsp;
								<input type="hidden"id="hidden_${vocabs.vocabURN}" value="${vocabs.displayName}"/>
								</td>
						</c:otherwise>
				</c:choose>
			
			</logic:iterate>
		
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="3" height='7px' class="td_greydottedline_horizontal">
		</td>
	</tr>
	<tr>
	<td class="content_txt_bold"  width="5%" nowrap>Select Criteria:</td>
	<td colspan="2" valign="top" height="20px" >
		<table cellpadding="0" cellspacing="0" ><tr>
			<td><input type="radio"	name="searchCriteria" id="findAnyWord" value='<%=VISearchAlgorithm.ANY_WORD%>' checked='true' /></td><td class="content_txt"  >&nbsp;&nbsp;Any Word&nbsp;&nbsp;&nbsp;</td>
			<td><input type="radio" name="searchCriteria" id="findExactPhrase" value='<%=VISearchAlgorithm.EXACT_PHRASE%>' /></td><td class="content_txt"  >&nbsp;&nbsp;Exact Phrase&nbsp;&nbsp;&nbsp;</td>
		</tr>
		</table>
	</td>
  </tr>
	<tr>
	<td class="content_txt_bold"  width="5%"  nowrap>Search:</td>
	    <td height="30" valign="top" colspan="2">
			<table height="30" border="0" cellpadding="0" cellspacing="0">
		      <tr>
			  <td><label><input name="searchtextfield" type="text" class="texttype" id="searchtextfield" value="" onkeyup="keypress(event)"></label></td>
				<td>&nbsp;</td>
				<td><div id="searchAbortButtonDiv"><a href="javascript:serachForTermInVocab('search');"><img src="images/advancequery/b_go.gif" border="0" alt="Search"></a></div></td>
				<!--<td><a  href="javascript:serachForTermInVocab('search');"><img src="images/advancequery/b_go.gif" border='0' alt="Go" ></a>
				<a  href="javascript:serachForTermInVocab('abort');"><img src="images/advancequery/b_abort.gif" border='0' alt="abort" ></a>
				</td>-->
				<td style="padding-left:10px"><a href='javascript:doNothing();' onclick='restoreDefaultVocab();' ><img src="images/advancequery/b_restore_defaults.gif" border='0' alt="Reloads the Permissible Values from default Vocabulary." ></a></td>
				<!--<td style="padding-left:10px"><input type="checkbox" id="findExactMatch" value="findExactMatch"/></td><td class="content_txt"  >Exact Match</td>-->
				<!-- <td id="searhLabel" class="content_txt"  align="right" style="padding-left:10px;color:blue">&nbsp;</td>  -->
				</tr>
			</table>
		</td>
		
	</tr>
  </tr>
  
  
  <tr>
		<td colspan="3" height='7px' class="td_greydottedline_horizontal">
		</td>
  </tr>
  <tr>
		<td colspan="3" height="23px" id="searhLabel" class="content_txt"  align="left" style="padding-left:10px;color:blue">&nbsp;</td>  
  </td>
  </tr>
</table>

<!-- Header end -->
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="0">
  <tr>
		<td width="45%"  >
	
		<table cellpadding="0" cellspacing="0" width="100%"   style=" border:1px solid Silver;">
		<tr>
			<td class="grid_header_text" height="25" bgcolor="#EAEAEA" style="padding-left:7px;">Search Result</td> 
		</tr>
		<tr>
			<td bgcolor="#FFFFFF" width="100%"  >
			<table width="100%"   cellpadding="0" cellspacing="0"  border="0">
			<!--<tr height="20px">
				<td height="20px"   id="searhLabel" class="content_txt"  align="left" style="padding-left:10px;color:blue">&nbsp;</td>  
				<!-- <td width="48%" height="20" valign="middle" bgcolor="#FFFFFF" class="grid_header_text" style="padding-left:7px;">List View </td>  -->
				<!--<td width="48%" height="20" valign="middle" bgcolor="#FFFFFF" class="grid_header_text" style="padding-left:7px;">Tree View </td> -->
			<!-- </tr> -->
			<tr>
				<td valign="top"  width="100%"  >
					<!-- for tree view we required this width: 260px; height: 300px;  -->
					<script>document.write('<div  id="divForMappingMode" style=" width: '+divWidth+'px; height:'+divHeight+'px ;overflow:auto;"  >')</script>
					<table  border="0" cellpadding="0" cellspacing="0" width="" >
					<logic:iterate name="Vocabulries" id="vocabs">
					<c:set var="urn" value="${vocabs.vocabURN}" />
					<c:choose>
						<c:when test="${vocabs.vocabURN eq srcVocabURN}">
								<tr>
								<td valign="top">
									<% 
									String srcHTML = (String)request.getSession().getAttribute(Constants.PV_HTML+(String)pageContext.getAttribute("urn")); 
									String style="";
									if(srcHTML==null)
									{
									style="display:none";
									srcHTML="";
									}
									else{
										String srcHTMLArray[]=srcHTML.split(Constants.MSG_DEL);
										srcHTML=srcHTMLArray[0];
										}
									%>
								<div id="main_div_vocab_${vocabs.vocabURN}" style='<%=style%>'><%=srcHTML%></div>
								</td>
								</tr>
						</c:when>
						<c:otherwise>
								<tr>
								  <td valign="top">
									 <% String trgHTML = (String)request.getSession().getAttribute(Constants.PV_HTML+(String)pageContext.getAttribute("urn")); 
									String style="";
									if(trgHTML==null)
									{
									style="display:none";
									trgHTML="";
									}%> 
									<div id="main_div_vocab_${vocabs.vocabURN}"style='<%=style%>'><%=trgHTML%></div>
								 </td>
								</tr>
						</c:otherwise>
				</c:choose>
			
			</logic:iterate>
								
					</table>
					</div>
				
						<!-- for tree view we required this width: 260px; height: 300px;  -->
					<script>document.write('<div  id="divForSearchingMode" style=" width: '+divWidth+'px; height:'+divHeight+'px ;  overflow:auto;display:none"  >')</script>
				<!--	<div  id="divForSearchingMode" width="100%"   style="border:1px solid Silver; overflow:auto;display:none"  >-->
					</div>
				</td>
				<!--<td valign="top" style="padding:0 7px 7px 7px;">
						<div style="width: 250px; height: 300px; overflow:auto;border:1px solid Silver;" >
						<table style="width: 240px; height: 290px;">
						<tr>
							<td class="content_txt" align="right" style="padding-left:0px" nowrap>This feature will be available in next Iteration </td> 
						</tr>
						</table>
						</div>
				</td> -->
			</tr>
			</table>
			</td>
		</tr>
			
		</table>
		</td>
		<td align="center" valign="middle" width="10%"  >
			<table width="100%"    border="0"  >
                          <tr valign="middle" align="center" >
                            <td height="22" valign="bottom" align="center"><a href="javascript:addPermissibleValuesToList();"><img src="images/advancequery/b_add.gif" alt="Add" width="63" height="18" vspace="2" border="0" align="absmiddle" /></a></td>
                          </tr>
						  
                          <tr valign="middle" align="center" >
                            <td height="22" valign="top" align="center"><a href="javascript:deleteSelectedPvsRow();"><img src="images/advancequery/b_remove.gif" alt="Remove" width="63" height="18" vspace="2" border="0"></a></td>
                          </tr>
             </table>
		</td> 
		<td  valign="top" width="45%"  >
			<!-- for tree view we required this width: 260px; height: 300px;  -->
			<table cellpadding="0" cellspacing="0" width="100%"   style=" border:1px solid Silver;">
				<tr>
					<td class="grid_header_text" height="25" bgcolor="#EAEAEA" style="padding-left:7px;">Selected Permissible Values </td> 
				</tr>
				<!--<tr height="20px">
				<td height="20px" id="searhLabel222" class="content_txt"  align="left" style="padding-left:10px;color:blue">&nbsp;</td>  
				
			</tr> -->
				<tr>
					<td bgcolor="#FFFFFF">
					<table bgcolor="#FFFFFF" width="100%">
					<tr>
					<td valign="top">
						<script>document.write('<div  id="divForSearchingMode" style=" width: '+divWidth+'px; height:'+divHeight+'px ;  overflow:auto;">')</script>
						<!-- <div width="100%"   style=" border:1px solid Silver; overflow:auto;"  >-->
						<table  cellpadding="0" cellspacing="0"  border="0" width="">
						<logic:iterate name="Vocabulries" id="vocabs">
							<tr><td valign="top"><div id = "selectedPermValues_Div_${vocabs.vocabURN}" style="display:none">
						
							<table border = "0" id = "" cellpadding ="0" cellspacing ="1" >
								<tr>
									<td> <table border = "0" id = "" cellpadding ="0" cellspacing ="1">  <tr>
									<td align="left" align="absmiddle">
										<a id="imageSEL_${vocabs.vocabURN}" onclick="javascript:showHide('selectedPermValues_${vocabs.vocabURN}','imageSEL_${vocabs.vocabURN}')">
										<img  src="images/advancequery/nolines_minus.gif" align="absmiddle"/></a>
									</td>
									<td  align="middle"><input type="checkbox" id="pvSelectedCB_${vocabs.vocabURN}" onclick="checkedUncheckedAllPvs('${vocabs.vocabURN}')"> </td>
									<td  align="middle" class="grid_header_text" >&nbsp;&nbsp;${vocabs.displayName}</td>
									</tr></table></td>
								</tr>	
								<tr>
									<td>
									<table  id = "selectedPermValues_${vocabs.vocabURN}" border = "0" cellpadding ="1.5" cellspacing ="1">
									</table>
									</td>
								</tr>
							</table>
							</div></td>
						  </tr>
						</logic:iterate>
						</table>
						</div>
					</td>
					</tr>
					</table>
					</td>
				</tr>
			</table>
		
		</td>
	</tr>
    <tr>
            <td ><table border="0" cellspacing="0" cellpadding="0">
              <tr>
			 <td align="left"><div id="deactivateDiv"> <a href='javascript:addPvsToCondition();'><img id='okImage' src='images/advancequery/b_ok.gif' border='0' alt='OK' width='44' height='23'></a></div></td>
                <!--  In each case ok button should be enabled because Requirement has changed, user should able to add any entity without  limit
				<td align="left"><div id="deactivateDiv"><a href="javascript:doNothing();"><img id="okImage" src="images/advancequery/b_ok_inactive.gif" border="0" alt="OK" width="44" height="23"></a></div></td> -->
               <!-- <td width="76" align="right"><a href="#" onclick= "cancelWindow()"><img src="images/advancequery/b_ok_inactive.gif" border="0" alt="Cancel" width="66" height="23"></a></td> -->
              </tr>
            </table></td>
    </tr> 

</table></td></tr>
</table>
</body>
</html>
