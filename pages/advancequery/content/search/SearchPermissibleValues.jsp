<!-- author amit_doshi -->
<%@ page import="edu.wustl.query.util.global.Constants"%>	
<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>

<%@page import="edu.wustl.common.vocab.IVocabulary"%>
<%@page import="edu.wustl.common.vocab.utility.VocabUtil"%>
<%@page import="edu.wustl.query.domain.SelectedConcept"%>

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
	<%String srcVocabName=VocabUtil.getVocabProperties().getProperty("source.vocab.name");
	String srcVocabVersion = VocabUtil.getVocabProperties().getProperty("source.vocab.version"); 
	String sourceVocabMessage =(String)request.getSession().getAttribute(Constants.MESSAGE_SRC_VOCAB); 
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
				var vocabName=selectedPvsCheckedBoxIdArray[j].split(":"); // selectedPvsCheckedBoxIdArray[j]  format MED1.0:52014
				
				if(vocabName.length>1) // No need to include Root node in list make because its has id as only MED name not CONCEPTCODE:Permissible Value
				{
					
					if(set_mode=="Searching") 
					{
						var conceptDetail=document.getElementById("srh_"+selectedPvsCheckedBoxIdArray[j]).value;
						createRows(vocabName[0],selectedPvsCheckedBoxIdArray[j],conceptDetail);
					}
					else if(set_mode=="Mapping")
					{
						var conceptDetail=document.getElementById(selectedPvsCheckedBoxIdArray[j]).value;
						createRows(vocabName[0],selectedPvsCheckedBoxIdArray[j],conceptDetail);
					}
				}
			}catch(e){}
		
	}
	}
	if(selectedPvs.length>0) //Check to enable 'OK'  button
	{
		document.getElementById("deactivateDiv").innerHTML="<a href='javascript:addPvsToCondition();'><img id='okImage' src='images/advancequery/b_ok.gif' border='0' alt='OK' width='44' height='23'></a>"
	}
}
// method is used to create the rows in the talbe
function createRows(vocabName,selectedPvsCheckedBoxId,conceptDetail)
{
					var table = document.getElementById('selectedPermValues_'+vocabName);
					var lastRow = table.rows.length;
					var row = table.insertRow(lastRow); 
					if(lastRow>=0)
						{
						document.getElementById('selectedPermValues_Div_'+vocabName).style.display = '';;
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
						
						
						var chkBox = createNamedElement('input','checkboxName_'+vocabName);
						chkBox.setAttribute('type', 'checkbox');
						//chkBox.setAttribute('name', 'checkboxName_'+vocabName[0]);
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
		var vocabName=selectedPvs[j].split(":");
		if(vocabName.length>1) // No need to include Root node in list make because its has id as only MED name not CONCEPTCODE:Permissible Value
		{
			var checkedObjArray = new Array();
			var table = document.getElementById('selectedPermValues_'+vocabName[0]);
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
				document.getElementById("pvSelectedCB_"+vocabName[0]).checked=false;
				document.getElementById('selectedPermValues_Div_'+vocabName[0]).style.display = 'none';
						
			}
				
		}
	}catch(e){}
	}
		deleteFromArray(checkedObjtodelete,selectedPvs);
		selectedPvs=removeElementsFromArray(selectedPvs, isNullOrUndefined);
		if(selectedPvs.length==0)
			{
				document.getElementById("deactivateDiv").innerHTML="<a href='javascript:doNothing();'><img id='okImage' src='images/advancequery/b_ok_inactive.gif' border='0' alt='OK' width='44' height='23'></a>"
			}
		
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
				pvNameList=pvNameList+selectedIdFromListWithoutCode+"#";
				pvConceptCodeList=pvConceptCodeList+selectedPvs[k]+"#";
				//required to store in session
				pvNameListWithCode=pvNameListWithCode+document.getElementById(selectedPvs[k]).value+"#";
			}					
		}catch(e)
		{}
			
	}
		sendValueToParent(pvConceptCodeList,pvNameListWithCode,pvNameList)
}
// this method will send the selected values to the parent window from open child window
function sendValueToParent(pvConceptCodeList,pvNameListWithCode,pvNameList)
{
		
		var request = newXMLHTTPReq(); 
		var param = "ConceptCodes"+"="+pvConceptCodeList+"&ConceptName="+pvNameListWithCode;
		var actionUrl="SelectedPermissibleValue.do";
		request.onreadystatechange=function(){setSelectedConceptCodes(request,pvConceptCodeList,pvNameList)};
		request.open("POST",actionUrl,true);
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		request.send(param);
}
function setSelectedConceptCodes(request,pvConceptCodeList,pvNameList)
{
	if(request.readyState == 4)  
	{	
		if(request.status == 200)
		{
			var responseTextValue =  request.responseText;	
			parent.window.getValueFromChild(pvConceptCodeList,pvNameList);
			parent.pvwindow.hide();
			return false;
		}
	}
}
//This method will be called when user clicks on the vocabulary check box
function refreshWindow(vocabCheckBoxId,vocabName,vocabVer,vocabURN)
{
	
	if(set_mode=="Mapping")
	{
		
		var vocabDisplayName=document.getElementById("hidden_"+vocabName+":"+vocabVer).value;
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
		//var selectedCheckedBoxVocabDivID=selectedCheckedBoxVocabValue.substring(0,selectedCheckedBoxVocabValue.indexOf(" "));
		if(document.getElementById(vocabCheckBoxId).checked)
		{
			var innerData=document.getElementById(selectedCheckedBoxVocabDivID).innerHTML;
			 if(document.getElementById(selectedCheckedBoxVocabDivID).style.display == 'none' && innerData.length==0)
				{
					label.innerHTML="Please Wait.....";
					waitCursor();
					
					 document.getElementById(selectedCheckedBoxVocabDivID).style.display = '';
					 // send request only first time when user click on the check box for other click  just hide and show the div 
					var param = "selectedCheckBox"+"="+vocabName+"#"+vocabVer+"#"+vocabURN+"#"+vocabDisplayName;
					var actionUrl="SearchMappedPV.do";
					request.onreadystatechange=function(){setSelectedVocabDataInDIV(request,selectedCheckedBoxVocabDivID)};
					request.open("POST",actionUrl,true);
					request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
					request.send(param);
					 
				}
				else if(innerData.length>0)
				{
				  document.getElementById(selectedCheckedBoxVocabDivID).style.display = '';
				  
				}
				 
		}
		else if (! document.getElementById(vocabCheckBoxId).checked)
		{
				 document.getElementById(selectedCheckedBoxVocabDivID).style.display = 'none';
				
		}
			
		/* code to support one vocab at a time  START*/
		var elements=document.getElementsByName("vocabNameAndVersionCheckbox");
		for(var i=0;i<elements.length;i++)
		{
			if(elements[i].id!=vocabCheckBoxId)
			{
				document.getElementById("main_div_"+elements[i].id).style.display = 'none';
				uncheckAllAndDeleteFromArray(elements[i].id.replace("vocab_",""));
				//need to delete all the rows of right hand side selected list
				var tableid =document.getElementById("selectedPermValues_"+elements[i].value.replace(":","@"));
				for(var j=0;j<tableid.rows.length;j++)
				{
					tableid.rows[j].myRow.one.checked=1;
				}
					deleteSelectedPvsRow();
			}
			else
			{
				document.getElementById("main_div_"+elements[i].id).style.display = '';
			}
				
		}
		/* to support one vocab at a time  ENDED*/
		
	}
}
/* used to set the data in the div based the which checked box clicked */
function setSelectedVocabDataInDIV(request,selectedCheckedBoxVocabDivID)
{
	if(request.readyState == 4)  
	{	  		
		if(request.status == 200)
		{	
			var responseTextValue =  request.responseText;	
			var searchHTML=responseTextValue.split("MSG$-$");
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
		}
	}
};
// this method will used to refresh the MED div
function refreshMEDDiv(medCheckBoxId)
{
		
	if (document.getElementById(medCheckBoxId).checked==true)
	{
		document.getElementById("main_div_"+medCheckBoxId).style.display = '';
	} 
	else 
	{
		document.getElementById("main_div_"+medCheckBoxId).style.display = 'none';
	}
}
 /*store all the ids of selected checkbox in the array*/
function getCheckedBoxId(checkedBoxId)
{
 	if(document.getElementById(checkedBoxId).checked)
	{
		checkedBoxId=checkedBoxId.replace("srh_","");//if ids for search result 
		selectedPvsCheckedBoxIdArray[selectedPvsCheckedBox]=checkedBoxId;
		selectedPvsCheckedBox++;
	}
	else if( !document.getElementById(checkedBoxId).checked)
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
			deleteValueFromArray(e2[i].id,selectedPvsCheckedBoxIdArray)
		}
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

var searchRequest = newXMLHTTPReq(); 
var operationAborted=false;
function serachForTermInVocab(operation)
{
	var searchTerm=document.getElementById("searchtextfield").value;
	void(d=document);
	var vocabCheckboxes=d.getElementsByName("vocabNameAndVersionCheckbox");
	
	var targetVocabsForSearchTerm="";
			
			for(i=0;i<vocabCheckboxes.length;i++)
			{
				if(vocabCheckboxes[i].checked==1)
				{
					var vocabDisplayName=document.getElementById("hidden_"+vocabCheckboxes[i].value).value;
					targetVocabsForSearchTerm=targetVocabsForSearchTerm+vocabCheckboxes[i].value+":"+vocabDisplayName+"@";
					uncheckAllAndDeleteFromArray(vocabCheckboxes[i].id.replace("vocab_",""));
				}
			}
	var message="Please enter the search term.";
	if(! checkForSplChar(searchTerm)  && checkForEmptyText(searchTerm,message) && isVocabSelected(targetVocabsForSearchTerm))
	{
	
			label=document.getElementById("searhLabel");
			var searchAbortButtonDiv=document.getElementById("searchAbortButtonDiv");
		    label.innerHTML="  Searching .... Please Wait";
			if(operation=="search") // if operation is search change the button to abort and set the flag
			{
				searchAbortButtonDiv.innerHTML="<a  href=\"javascript:serachForTermInVocab('abort');\"><img src='images/advancequery/b_abort.gif' border='0' alt='abort' ></a>";
				operationAborted=false;
			}
			else // if operation is abort change the button to search and set the flag
			{
				searchAbortButtonDiv.innerHTML="<a  href=\"javascript:serachForTermInVocab('search');\"><img src='images/advancequery/b_go.gif' border='0' alt='abort' ></a>";
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
			var param = "searchTerm="+searchTerm+"&targetVocabsForSearchTerm="+targetVocabsForSearchTerm+"&operation="+operation;
			var actionUrl="SearchPermissibleValues.do";
			searchRequest.onreadystatechange=function(){getSearchTermResult(searchRequest)};
			searchRequest.open("POST",actionUrl,true);
			searchRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			searchRequest.send(param);
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
				searchHTML=responseTextValue.split("MSG$-$");
				document.getElementById("divForSearchingMode").innerHTML=searchHTML[0];
				label.innerHTML=searchHTML[1];
			}
			else
			{
				// if user has  aborted the request then set the aborted successfully message to message label
				label.innerHTML=responseTextValue;
			}
			var searchAbortButtonDiv=document.getElementById("searchAbortButtonDiv");
			searchAbortButtonDiv.innerHTML="<a  href=\"javascript:serachForTermInVocab('search');\"><img src='images/advancequery/b_go.gif' border='0' alt='abort' ></a>";
			hideCursor();
		}
	}
};

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
function restoreDefault()
{
	set_mode="Mapping";
	document.getElementById("divForMappingMode").style.display = '';
	document.getElementById("divForSearchingMode").style.display = 'none';
	document.getElementById("divForSearchingMode").innerHTML="";
	document.getElementById("searchtextfield").value="";
	label.innerHTML="";
	
	var targetVocabsForMappingMode="";
	void(d=document);
	void(vocabCheckboxes=d.getElementsByName("vocabNameAndVersionCheckbox"));
	for(l=0;l<vocabCheckboxes.length;l++)
	{
		var selectedCheckedBoxVocabDivID="main_div_"+vocabCheckboxes[l].id;
		vocabNameAndVer=vocabCheckboxes[l].value.split(':');
		if(vocabNameAndVer[0].equalsIgnoreCase('<%=srcVocabName%>') && vocabNameAndVer[1].equalsIgnoreCase('<%=srcVocabVersion%>' ) )
		{
			vocabCheckboxes[l].checked=1;
			document.getElementById(selectedCheckedBoxVocabDivID).style.display = '';
			uncheckAllAndDeleteFromArray(vocabNameAndVer[0]+vocabNameAndVer[1]);
		}
		else
		{
			vocabCheckboxes[l].checked=0;
			document.getElementById(selectedCheckedBoxVocabDivID).style.display = 'none';
			uncheckAllAndDeleteFromArray(vocabNameAndVer[0]+vocabNameAndVer[1]);
		}
	}
	selectedPvsCheckedBoxIdArray=new Array();
};
function editSelectedPV()
{
	label=document.getElementById("searhLabel");
	<%List<SelectedConcept> selectedConcepts = (ArrayList)request.getSession().getAttribute(Constants.SELECTED_CONCEPT_LIST);
	if(selectedConcepts!=null)
	{	
		for (SelectedConcept selectedConcept : selectedConcepts)
		{
			String vocabName=selectedConcept.getVocabName()+"@"+selectedConcept.getVocabVersion();
			String medConcept=selectedConcept.getMedCode();
			String selectedCheckboxId=vocabName+":"+medConcept;
			String conceptDetail=selectedConcept.getConceptCode()+":"+selectedConcept.getConceptName();
			%>
			createRows('<%=vocabName%>','<%=selectedCheckboxId%>','<%=conceptDetail%>');

			<%
			
		}%>
		if(selectedPvs.length>0) //Check to enable 'OK'  button
		{
			document.getElementById("deactivateDiv").innerHTML="<a href='javascript:addPvsToCondition();'><img id='okImage' src='images/advancequery/b_ok.gif' border='0' alt='OK' width='44' height='23'></a>";
		}
	<%}
		if(sourceVocabMessage!=null)
		{
		%>
			//set the messgage if concept code greater then configured value
			label.innerHTML='<%=sourceVocabMessage%>';
		<%}%>
};

</script>
</head>
<body onLoad="Reload();editSelectedPV();">
<table width="100%"  border="0" cellspacing="0" cellpadding="7"><tr><td>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td colspan="3" >
		<table >
			<tr>
			<td class="content_txt_bold" nowrap>Select Vocabulary: &nbsp;&nbsp;</td>
			<c:set var="srcVocabName" value="<%=srcVocabName%>"/>
			<c:set var="srcVocabVer" value="<%=srcVocabVersion%>"/>

			<logic:iterate name="Vocabulries" id="vocabs">
					<c:choose>
						<c:when test="${vocabs.name eq srcVocabName}">
							<c:if test="${vocabs.version eq srcVocabVer}" >
								<td><input type="radio"  name="vocabNameAndVersionCheckbox" id="vocab_${vocabs.name}${vocabs.version}" value="${vocabs.name}:${vocabs.version}"   
								onclick= "refreshWindow(this.id,'${vocabs.name}','${vocabs.version}','${vocabs.vocabURN}');" checked='true'></td><td class="content_txt">${vocabs.displayName}&nbsp;&nbsp;&nbsp;
								<input type="hidden"id="hidden_${vocabs.name}:${vocabs.version}" value="${vocabs.displayName}"/>

								</td>			
								
							</c:if>
						</c:when>
						<c:otherwise>
								<td><input type="radio"  name="vocabNameAndVersionCheckbox" id="vocab_${vocabs.name}${vocabs.version}" value="${vocabs.name}:${vocabs.version}"   
								onclick= "refreshWindow(this.id,'${vocabs.name}','${vocabs.version}','${vocabs.vocabURN}');"></td><td class="content_txt">${vocabs.displayName}&nbsp;&nbsp;&nbsp;
								<input type="hidden"id="hidden_${vocabs.name}:${vocabs.version}" value="${vocabs.displayName}"/>
								</td>		
						</c:otherwise>
				</c:choose>
			
			</logic:iterate>
		
		</tr>
		</table>
		</td>
	</tr>
	<tr>
	    <td height="35" valign="top" colspan="3">
			<table height="30" border="0" cellpadding="0" cellspacing="0">
		     <tr><td><label><input name="searchtextfield" type="text" class="texttype" id="searchtextfield" value=""></label></td>
				<td>&nbsp;</td>
				<td><div id="searchAbortButtonDiv"><a href="javascript:serachForTermInVocab('search');"><img src="images/advancequery/b_go.gif" border="0" alt="Go"></a></div></td>
				<!--<td><a  href="javascript:serachForTermInVocab('search');"><img src="images/advancequery/b_go.gif" border='0' alt="Go" ></a>
				<a  href="javascript:serachForTermInVocab('abort');"><img src="images/advancequery/b_abort.gif" border='0' alt="abort" ></a>
				</td>-->
				<td style="padding-left:10px"><a href='javascript:doNothing();' onclick='restoreDefault();' ><img src="images/advancequery/b_restore_defaults.gif" border='0' alt="Restore Default" ></a></td>
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
		<td width="400px">
	
		<table cellpadding="0" cellspacing="0" width="400px" style="height: 380px; border:1px solid Silver;">
		<tr>
			<td class="grid_header_text" height="25" bgcolor="#EAEAEA" style="padding-left:7px;">Search Result</td> 
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
			<table cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" border="0">
			<tr>
				<td id="searhLabel" class="content_txt"  align="left" style="padding-left:10px;color:blue">&nbsp;</td>  
				<!-- <td width="48%" height="20" valign="middle" bgcolor="#FFFFFF" class="grid_header_text" style="padding-left:7px;">List View </td>  -->
				<!--<td width="48%" height="20" valign="middle" bgcolor="#FFFFFF" class="grid_header_text" style="padding-left:7px;">Tree View </td> -->
			</tr>
			<tr>
				<td valign="top" style="padding:0 0 7px 7px;">
					<!-- for tree view we required this width: 260px; height: 300px;  -->
					<div  id="divForMappingMode" style="width: 380px; height: 330px; border:1px solid Silver; overflow:auto;"  >
					<table border="0" cellpadding="0" cellspacing="0">
					
					<logic:iterate name="Vocabulries" id="vocabs">
					<c:choose>
						<c:when test="${vocabs.name eq srcVocabName}">
							<c:if test="${vocabs.version eq srcVocabVer}" >
								<tr>
								<td valign="top">
									<div id="main_div_vocab_${vocabs.name}${vocabs.version}" style="">
									<% String treeData = (String)request.getSession().getAttribute(Constants.MED_PV_HTML); %>
									<%=treeData%>
									</div>
								</td>
								</tr>
							</c:if>
						</c:when>
						<c:otherwise>
								<tr>
								  <td valign="top">
									<div id="main_div_vocab_${vocabs.name}${vocabs.version}" style="display:none"></div>
								 </td>
								</tr>
						</c:otherwise>
				</c:choose>
			
			</logic:iterate>
								
					</table>
					</div>
				
						<!-- for tree view we required this width: 260px; height: 300px;  -->
					<div  id="divForSearchingMode" style="width: 380px; height: 330px; border:1px solid Silver; overflow:auto;display:none"  >
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
		<td align="center" valign="middle">
			<table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="22"><a href="javascript:addPermissibleValuesToList();"><img src="images/advancequery/b_add.gif" alt="Add" width="63" height="18" vspace="2" border="0" align="absmiddle" /></a></td>
                          </tr>
                          <tr>
                            <td height="22"><a href="javascript:deleteSelectedPvsRow();"><img src="images/advancequery/b_remove.gif" alt="Remove" width="63" height="18" vspace="2" border="0"></a></td>
                          </tr>
                      </table>
		</td> 
		<td width="260px" valign="top">
			<!-- for tree view we required this width: 260px; height: 300px;  -->
			<table cellpadding="0" cellspacing="0" width="380px" style="height:385px; border:1px solid Silver;">
				<tr>
					<td class="grid_header_text" height="25" bgcolor="#EAEAEA" style="padding-left:7px;">Selected Permissible Values </td> 
				</tr>
				<tr>
					<td bgcolor="#FFFFFF">
					<table bgcolor="#FFFFFF">
					<tr>
					<td style="padding:5px;">
						<div style="width: 380px; height:340px; border:1px solid Silver; overflow:auto;"  >
						<table cellpadding="0" cellspacing="0"  border="0">
						<logic:iterate name="Vocabulries" id="vocabs">
							<tr><td><div id = "selectedPermValues_Div_${vocabs.name}@${vocabs.version}" style="display:none">
						
							<table border = "0" id = "" cellpadding ="0" cellspacing ="1">
								<tr>
									<td> <table border = "0" id = "" cellpadding ="0" cellspacing ="1">  <tr>
									<td align="left" align="absmiddle">
										<a id="image_${vocabs.name}@${vocabs.version}" onclick="javascript:showHide('selectedPermValues_${vocabs.name}@${vocabs.version}','image_${vocabs.name}@${vocabs.version}')">
										<img  src="images/advancequery/nolines_minus.gif" align="absmiddle"/></a>
									</td>
									<td  align="middle"><input type="checkbox" id="pvSelectedCB_${vocabs.name}@${vocabs.version}" onclick="checkedUncheckedAllPvs('${vocabs.name}@${vocabs.version}')"> </td>
									<td  align="middle" class="grid_header_text" >&nbsp;&nbsp;${vocabs.displayName}</td>
									</tr></table></td>
								</tr>	
								<tr>
									<td>
									<table  id = "selectedPermValues_${vocabs.name}@${vocabs.version}" border = "0" cellpadding ="1.5" cellspacing ="1">
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
            <td height="35"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left"><div id="deactivateDiv"><a href="javascript:doNothing();"><img id="okImage" src="images/advancequery/b_ok_inactive.gif" border="0" alt="OK" width="44" height="23"></a></div></td>
               <!-- <td width="76" align="right"><a href="#" onclick= "cancelWindow()"><img src="images/advancequery/b_ok_inactive.gif" border="0" alt="Cancel" width="66" height="23"></a></td> -->
              </tr>
            </table></td>
    </tr> 

</table></td></tr>
</table>
</body>
</html>
