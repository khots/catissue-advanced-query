<!-- author amit_doshi -->
<%@ page import="edu.wustl.query.util.global.Constants"%>	
<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>

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

	<script>
	
	// selectedPvs will store 
	selectedPvs =new Array();
	// selectedPvsCheckedBoxIdArray will store the  selected PVs from vocabulary that will deleted after clicked on add and stored in selectedPvs
	selectedPvsCheckedBoxIdArray =new Array();
	selectedPvsCheckedBox=0;
	numberOfPvs=0;
	vocabIndex=0;
	
	/*to close the model widow*/
	function cancelWindow()
	{
		parent.pvwindow.hide();
	}
	//When user click on the add button to add permissible values in the selected list
	function addPermValues()
	{	
		
	var checkedNode=0;
	//Inserting first cell as checkbox for deleting selected rows
	for(j=0;j<selectedPvsCheckedBoxIdArray.length;j++)
		{
		if(!selectedPvs.inArray(selectedPvsCheckedBoxIdArray[j]) && selectedPvsCheckedBoxIdArray[j]!=''  )
			{
			try{
				var vocabName=selectedPvsCheckedBoxIdArray[j].split(":");
				if(vocabName.length>1) // No need to include Root node in list make because its has id as only MED name not CONCEPTCODE:Permissible Value
				{
						
				var table = document.getElementById('selectedPermValues_'+vocabName[0]);
				var lastRow = table.rows.length;
				var row = table.insertRow(lastRow); 
				if(lastRow>0)
					{
					document.getElementById('selectedPermValues_Div_'+vocabName[0]).style.display = '';;
					}
					
					//Inserting 0nd cell as td
					var cell0 = row.insertCell(0);
					cell0.className="black_ar_tt"; 
					cell0.innerHTML= '&nbsp';
					//inserting cell as checkbox
					var cell1 = row.insertCell(1);
					cell1.className="black_ar_tt";
					var chkBox = document.createElement('input');
					chkBox.setAttribute('type', 'checkbox');
					chkBox.setAttribute('name', 'checkboxName_'+vocabName[0]);
					chkBox.setAttribute('id', selectedPvsCheckedBoxIdArray[j]);
					chkBox.setAttribute('value', document.getElementById(selectedPvsCheckedBoxIdArray[j]).value);
					cell1.align = 'center';
					cell1.appendChild(chkBox);

					//Inserting 2nd cell as textBox
					var cell2 = row.insertCell(2);
					cell2.className="black_ar_tt"; 
					cell2.innerHTML= document.getElementById(selectedPvsCheckedBoxIdArray[j]).value;
						
					row.myRow = new rowObj(chkBox);
					
					selectedPvs[numberOfPvs]=selectedPvsCheckedBoxIdArray[j];
					numberOfPvs++;
					
					uncheckAllSelectedPVs(vocabName[0]);
				}
			 }catch(e){}
					
			}
		}
	}
	function rowObj(one)
	{
	this.one=one;
	}

Array.prototype.inArray = function (value,caseSensitive)
		// Returns true if the passed value is found in the
		// array. Returns false if it is not.
		{
			var i;
			for (i=0; i < this.length; i++)
			{
				// use === to check for Matches. ie., identical (===),
				if(caseSensitive)
				{ //performs match even the string is case sensitive
					if (this[i].toLowerCase() == value.toLowerCase()) {
					return true;
					}
				}else{
					if (this[i] == value) {
					return true;
					}
				}
			}
		return false;
		};
	/*method is used to delete the selected row as well all row in one go*/
	function deleteSelectedPvsRow()
	{
		var checkedObjArray = new Array();
		var checkedObjtodelete=new Array();
		var cCount = 0;
		
		for(j=0;j<selectedPvs.length;j++)
		{
		try{
			var vocabName=selectedPvs[j].split(":");
			if(vocabName.length>1) // No need to include Root node in list make because its has id as only MED name not CONCEPTCODE:Permissible Value
			{
			
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
				if(table.rows.length==1)
				{
						document.getElementById('selectedPermValues_Div_'+vocabName[0]).style.display = 'none';
						document.getElementById("pvSelectedCB_"+vocabName[0]).checked=false;
				}
				
			}
		}catch(e){}
		}
		 deleteFromArray(checkedObjtodelete,selectedPvs);
		 deleteFromArray(checkedObjtodelete,selectedPvsCheckedBoxIdArray);
		
		
	}
	function deleteRows(rowObjArray)
	{
		
		for (var i=0; i<rowObjArray.length; i++) {
		   var rIndex = rowObjArray[i].sectionRowIndex;
		rowObjArray[i].parentNode.deleteRow(rIndex);
	   }
	}
	function deleteFromArray(deleteObj,array)
	{
		
		for(var i=0;i<array.length;i++)
		{
			
			if( deleteObj.inArray(array[i]))
			{
				delete array[i];
			}
		}
	}
		//method called when user clicks on OK button
	function addPvsToCondition()
	{
		var pvList="";
		var pvNameList="";
		for(var k=0;k<selectedPvs.length;k++)
		{
				try
				{
				if(document.getElementById(selectedPvs[k]).value!='undefined');
					{
						pvNameList=pvNameList+document.getElementById(selectedPvs[k]).value.split(':')[1]+",";
						pvList=pvList+selectedPvs[k]+"#";
					}
					
				}catch(e)
				{
				}
			
		}
		
		sendValueToParent(pvList,pvNameList)
	}
	// this method will send the selected values to the parent window from open child window
	function sendValueToParent(pvList,pvNameList)
    {
       parent.window.getValueFromChild(pvList,pvNameList);

        parent.pvwindow.hide();

        return false;
   }
   //This method will be called when user clicks on the vocabulary check box
   function refreshWindow(vocabCheckBoxId)
   {
   
		var request = newXMLHTTPReq(); 
		var selectedCheckBox="";
		if(request == null)
		{
			alert ("Your browser does not support AJAX!");
			return;
		}
			
			var selectedCheckedBoxVocabValue=document.getElementById(vocabCheckBoxId).value;
			var selectedCheckedBoxVocabDivID=selectedCheckedBoxVocabValue.substring(0,selectedCheckedBoxVocabValue.indexOf(" "));
			if(document.getElementById(vocabCheckBoxId).checked)
			{
				 if(document.getElementById("div_"+selectedCheckedBoxVocabDivID).style.display == 'none')
				 {
					 document.getElementById("div_"+selectedCheckedBoxVocabDivID).style.display = '';
				 }
				 else
				 {
					// send request only first time when user click on the check box for other click  just hide and show the div 
					var param = "selectedCheckBox"+"="+selectedCheckedBoxVocabValue;
					var actionUrl="SearchPermissibleValues.do";
					request.onreadystatechange=function(){setSelectedVocabDataInDIV(request,selectedCheckedBoxVocabValue)};
					request.open("POST",actionUrl,true);
					request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
					request.send(param);
				 }
			}
			else if (! document.getElementById(vocabCheckBoxId).checked)
			{
				
				 document.getElementById("div_"+selectedCheckedBoxVocabDivID).style.display = 'none';
			}
		
		
	
   }
	 /* used to set the data in the div based the which checked box clicked */
	 function setSelectedVocabDataInDIV(request,selectedCheckedBoxVocabValue)
	 {
		
		if(request.readyState == 4)  
		{	  		
			if(request.status == 200)
			{	
			var responseTextValue =  request.responseText;	
			var selectedCheckedBoxVocabDivID=selectedCheckedBoxVocabValue.substring(0,selectedCheckedBoxVocabValue.indexOf(" "));
			document.getElementById("div_"+selectedCheckedBoxVocabDivID).innerHTML=responseTextValue;
			}
		}
	 };
 
	 /*store all the ids of selected checkbox in the array*/
	 function getCheckedBoxId(checkedBoxId)
	 {
		if(document.getElementById(checkedBoxId).checked)
		{
			selectedPvsCheckedBoxIdArray[selectedPvsCheckedBox]=checkedBoxId;
			selectedPvsCheckedBox++;
		}
	 }

	 /*when user click on the root node ro checkbox of the Vocabulary*/
	 function setStatusOfAllCheckBox(rootCheckedBoxId)
	 {
		
		if(document.getElementById(rootCheckedBoxId).checked)
		{
				checkallAndAddToArray(rootCheckedBoxId)
		}
		else
		{
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
			void(el[i].checked=1)
			
			getCheckedBoxId(el[i].id);
		}
	}
	/* to unchecked all checkboxes and delete values from array */
	function uncheckAllAndDeleteFromArray(rootCheckedBoxId)
	{
		
		void(d2=document);
		void(e2=d2.getElementsByName(rootCheckedBoxId));
		for(i=0;i<e2.length;i++)
		{
			void(e2[i].checked=0)
			
			deleteValueFromArray(e2[i].id,selectedPvsCheckedBoxIdArray)
			
		}
	}
	function deleteValueFromArray(value,array)
	{
			for(var i=0;i<array.length;i++)
				{
					
					if( array.inArray(value))
					{
						delete array[i];
					}
				}
	}
	/** method to show hide div*/
	function showHide(elementid,imageId)
	{
		switchObj = document.getElementById(imageId);
		if (document.getElementById(elementid).style.display == 'none')
		{
			document.getElementById(elementid).style.display = '';
			switchObj.innerHTML = '<img src="images/advancequery/nolines_minus.gif"/>';
		} else {
			document.getElementById(elementid).style.display = 'none';
			switchObj.innerHTML = '<img src="images/advancequery/nolines_plus.gif"/>';
			
		}
	}
	
	function refreshMEDDiv(medCheckBoxId)
	{
		
		if (document.getElementById(medCheckBoxId).checked==true)
		{
			document.getElementById("medDivId").style.display = '';
		} 
		else 
		{
			document.getElementById("medDivId").style.display = 'none';
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
	</script>
</head>

<body>
<link rel='STYLESHEET' type='text/css' href='../common/style.css'>
<table width="100%"  border="0" cellspacing="0" cellpadding="4"><tr><td>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0"  class="login_box_bg">
	<tr>
		<td colspan="3" class="content_txt_bold" valign="middle">Select Vocabulary:&nbsp;&nbsp;
		<%
			List<String> vocabs1 = (ArrayList)request.getSession().getAttribute("Vocabulries");
			String srcVocabName=Variables.properties.getProperty("sourceVocabularyName");
			for(int i=0;i<vocabs1.size();i++)
			{
				
				String vacabNameWithoutVer=vocabs1.get(i).substring(0,vocabs1.get(i).indexOf(" "));
				String vacabName=vocabs1.get(i);
				if(vacabNameWithoutVer.equalsIgnoreCase(srcVocabName))
				{
					
				
				%>
	  	<input  type="checkbox" checked="true"  id="<%=vacabName%>" value="<%=vacabName%>"  onclick= "refreshMEDDiv(this.id);"><span class="content_txt" valign="middle">&nbsp;<%=vocabs1.get(i).toUpperCase()%> &nbsp;&nbsp;&nbsp;&nbsp;</span>
		<%}else{%>
		<input type="checkbox"  id="<%=vacabName%>" value="<%=vacabName%>" onclick= "refreshWindow(this.id);"><font class="content_txt" valign="middle">&nbsp;<%=vocabs1.get(i).toUpperCase()%>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<%}
		}%>
		</td>
	</tr>
	<tr>
		<td colspan="3" ></td>
	</tr>
	<tr>
	    <td height="35" valign="top">
			<table height="30" border="0" cellpadding="0" cellspacing="0">
		     <tr><td><label><input name="textfield" type="text" class="texttype" id="textfield" value="Black"></label></td>
				<td>&nbsp;</td>
				<td><label><img src="images/advancequery/b_go.gif" alt="Go" width="44" height="23"></label></td>
			</tr>
			</table>
		</td>
	</tr>
  </tr>
	<tr>
		<td colspan="3" >
		<table width="98%" border="0" cellspacing="0" cellpadding="0" class="td_greydottedline_horizontal"> 
          <tr>
				<td valign="top" >&nbsp;</td>
          </tr>
         </table>
	     </td>
	</tr>
	<tr>
		<td >
	
		<table style="width: 400px; height: 300px;border :1px solid Silver;">
		<tr>
			<td class="grid_header_text" height="25" bgcolor="#EAEAEA">Search Result </td> 
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
			<table bgcolor="#FFFFFF">
			<tr>
				<td width="48%" height="20" valign="middle" bgcolor="#FFFFFF" class="grid_header_text" >List View </td> 
				<td width="48%" height="20" valign="middle" bgcolor="#FFFFFF" class="grid_header_text" >Tree View </td> 
			</tr>
			<tr>
				<td valign="top">
					<div style="width: 260px; height: 300px;border :1px solid Silver; overflow:auto;"  >
					<table>
					
						<tr>
							
							<td valign="top">
								<div id="medDivId" style="width:250;">
								<% String treeData = (String)request.getSession().getAttribute(Constants.MED_PV_HTML); %>
								<%=treeData%>
								</div>
							</td>
							
						</tr>
								<%for(int i=0;i<vocabs1.size();i++)
								{
						
								String vacabNameWithoutVer=vocabs1.get(i).substring(0,vocabs1.get(i).indexOf(" "));
								String vacabName=vocabs1.get(i);
								if( ! vacabNameWithoutVer.equalsIgnoreCase(srcVocabName))
								{%>
						<tr>
							  <td valign="top">
								<div id="div_<%=vacabNameWithoutVer%>" style="width:250;">
								
								</div>
							 </td>
						</tr>
								<%}}%>
					</table>
					</div>
				</td>
				<td valign="top">
						<div style="width: 260px; height: 300px; overflow:auto;"  >
						<table style="width: 250px; height: 300px;border :1px solid Silver;">
						<tr>
							
						</tr>
						</table>
						</div>
				</td>
			</tr>
			</table>
			</td>
		</tr>
			
		</table>
		
		<td>
			<table>
				<tr>
					
						<td align="center" valign="middle"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="22"><a href="#" onclick="addPermValues();"><img src="images/advancequery/b_add.gif" alt="Add" width="63" height="18" vspace="2" border="0" align="absmiddle" /></a></td>
                          </tr>
                          <tr>
                            <td height="22"><a href="#" onclick="deleteSelectedPvsRow();"><img src="images/advancequery/b_remove.gif" alt="Remove" width="63" height="18" vspace="2" border="0"></a></td>
                          </tr>
                      </table></td>
					
				<tr>
			</table>
		</td> 
		<td>
			
			<table>
				<tr>
					<td class="grid_header_text" height="25" bgcolor="#EAEAEA">Selected Permissible Values </td> 
				</tr>
				<tr>
					<td bgcolor="#FFFFFF">
					<table bgcolor="#FFFFFF">
					<tr>
					<td>
						<div style="width: 250px; height:330px;border :1px solid Silver; overflow:auto;"  >
						<table>
							<%for(int i=0;i<vocabs1.size();i++)
								{
						
								String vacabNameWithoutVer=vocabs1.get(i).substring(0,vocabs1.get(i).indexOf(" "));
								String vacabName=vocabs1.get(i);
								System.out.println(vacabNameWithoutVer.toUpperCase());
								%>
							<tr><td><div id = "selectedPermValues_Div_<%=vacabNameWithoutVer.toUpperCase()%>" style="display:none">
							<table border = "0" id = "selectedPermValues_<%=vacabNameWithoutVer.toUpperCase()%>" cellpadding ="3" cellspacing ="0" width="100%">
								<tr>
									
									<td  align="left"><input type="checkbox" id="pvSelectedCB_<%=vacabNameWithoutVer.toUpperCase()%>" onclick="checkedUncheckedAllPvs('<%=vacabNameWithoutVer.toUpperCase()%>')"> </td>
									<td  align="left" class="grid_header_text"><%=vacabNameWithoutVer.toUpperCase()%></td>
								</tr>	
							</table></div>	
							</td></tr>
							<%}%>
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
            <td height="35" ><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left" ><a href="#" onclick="addPvsToCondition();"><img src="images/advancequery/b_ok.gif" border="0" alt="OK" width="44" height="23"></a></td>
                <td width="76" align="right"><a href="#" onclick= "cancelWindow()"><img src="images/advancequery/b_cancel.gif" border="0" alt="Cancel" width="66" height="23"></a></td>
              </tr>
            </table></td>
    </tr> 

</table></td></tr>
</table>
</body>
</html>
