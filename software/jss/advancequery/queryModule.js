	/*This is the wrapper function over show_calendar() that allows to select the date only if the operator is Not 'ANY'
	opratorListId : Id of the date-operators list box
	formNameAndDateFieldId : A string that contains the form name & date field's id
	isSecondDateField : A boolean variable that tells whether the date field is first or second
	*/
	function onDate(operatorListId,formNameAndDateFieldId,isSecondDateField)
	{
	var dateCombo = document.getElementById(operatorListId);
	
	if(dateCombo.options[dateCombo.selectedIndex].value != "Any")
	{
		if(!isSecondDateField)
		{
			show_calendar(formNameAndDateFieldId,null,null,'MM-DD-YYYY')
		}
		else
		{
			if(dateCombo.options[dateCombo.selectedIndex].value == "Between" || dateCombo.options[dateCombo.selectedIndex].value == "Not Between")
			{
				show_calendar(formNameAndDateFieldId,null,null,'MM-DD-YYYY');
			}
		}
	}
   }
    var noOfNodes = 0;  
	var parentTreeNode = "";  
	//var displayLabel="";
	//var parentnode = 1;
	  function getTreeNodeChildren(nodeId)
	  {
	   if(noOfNodes >= tree_expansion_Limit)   //if tree expansion limit has been reached only generate spread sheet do not proceed further
	   {
		   buildSpreadsheet(nodeId);
		   return;
	   }
		 
		 var NodeId = nodeId;
		 if(nodeId!="")  //if(nodeId.toString().indexOf("Label")!=-1) //if Label node is cliked
		{
		  
		 
		 if(parentTreeNode!="")
		 {
		   var img1 = "ic_folder.gif";  //closednode
		   var img2 = "folderOpen.gif"; //opennode
		   var img3 = "ic_small_folder.gif"; //node with no children
		   resultTree.setItemImage2(parentTreeNode,img3,img2,img1);
		  }
		  
		  
		  
		  parentTreeNode = nodeId;
		  //Ajax request for expanding the tree on node click 
		  var url = "BuildQueryOutputTree.do";
		  var img1 = "wait_ax.gif";  //closednode
		  var img2 = "wait_ax.gif"; //opennode
		  var img3 = "wait_ax.gif"; //node without children
		   resultTree.setItemImage2(parentTreeNode, img2,img1,img3);
		   resultTree.deleteChildItems(parentTreeNode);
		  //build SpreadSheet
		   buildSpreadsheet(nodeId);
			
		}
			else
		  {
			   
			   var url = "BuildQueryOutputTreeAjaxHandler.do"; 
    		
		  }
	
			
		    var request = newXMLHTTPReq();			
			var actionURL;
			var handlerFunction = getReadyStateHandler(request,showTreeNodeChildren,true);	
			request.onreadystatechange = handlerFunction; 
			actionURL = "nodeId=" + NodeId;
			request.open("POST",url,true);	
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send(actionURL); 
		
 }

  function showTreeNodeChildren(response)
  {
	if(response=="wait" || response=="WAIT_FOR_NEXT_RECORD")
	{
	  getTreeNodeChildren("");
	}
	
   else
	{
		if(response == "NO_RECORDS_FOUND" || response == "TOO_FEW_RECORDS")
		{
			  var treeRow = document.getElementById("treeRow");
			  treeRow.style.display='none';
			  return;
		}
		if(response == "NO_MORE_RECORDS")
		{
		   var img1 = "ic_folder.gif";  //closednode
		   var img2 = "folderOpen.gif"; //opennode
		   var img3 = "ic_small_folder.gif";
		   resultTree.setItemImage2(parentTreeNode,img3,img2,img1);
		   return;  
		}  
		
		if(response=="completed")
		{
		   var img1 = "ic_folder.gif";  //closednode
		   var img2 = "folderOpen.gif"; //opennode
		   var img3 = "ic_small_folder.gif"; //node with no children
		   resultTree.setItemImage2(parentTreeNode,img3,img2,img1);
		   return;
		}
		
		else
			{
				 var jsonResponse = eval('('+ response+')');
				 if(jsonResponse.childrenNodes!=null)
				 {
					var num = jsonResponse.childrenNodes.length; 
				  for(var i=0;i<num;i++)
				  {
					var status = jsonResponse.childrenNodes[i].status;
					var nodeId = jsonResponse.childrenNodes[i].identifier;
					var displayName = jsonResponse.childrenNodes[i].displayName;
					var parentId = jsonResponse.childrenNodes[i].parentId;
					//Add children to result output tree 
					var displayLabel =  "<span class=\"content_txt\">"+displayName+"</span>";
					resultTree.insertNewChild(parentId,nodeId,displayLabel,0,"ic_small_folder.gif","ic_small_folder.gif","ic_small_folder.gif","");
					resultTree.setItemText(nodeId,displayLabel,displayName);
					noOfNodes++;
					if(noOfNodes >= tree_expansion_Limit)
					 {
					  var msgDiv =parent.document.getElementById("treeMessageDiv");
					  msgDiv.innerHTML="The tree is showing the maximum allowed records, cannot expand the tree further.";
					  var img1 = "ic_folder.gif";  //closednode
					  var img2 = "folderOpen.gif";
					  resultTree.setItemImage(parentTreeNode,img2,img1);
					  return;
					}
				    
				  }
				   
				  if(status!=null && status=="LAST_RECORD")
				 {
				 			var img1 = "ic_folder.gif";  //closednode
							var img2 = "folderOpen.gif";
						    resultTree.setItemImage(parentTreeNode,img2,img1);
							return;
				 }
				
					getTreeNodeChildren("");
				 
				}
			  }
	}
 }
	
	var addedNodes = "";
	var isFirtHit = true;
	function treeNodeClicked(id)
	{
		alert("in treeNodeClicked"+treeNodeClicked);
		
		
		if(id.indexOf('_NULL') == -1)
		{
			var aa = id.split("::");		
			var nodes = addedNodes.split(",");
			var isNodeAdded = false;
			if(nodes != "")
			{
			for(i=0; i<nodes.length; i++)
				{
					if(nodes[i] == id)
					{
						isNodeAdded = true;
						break;
					}
				}
			}
			if(!isNodeAdded)
			{
				
				var request = newXMLHTTPReq();			
				var actionURL;
				var handlerFunction = getReadyStateHandler(request,showChildNodes,true);	
				request.onreadystatechange = handlerFunction;				
				actionURL = "nodeId=" + id;				
				var url = "BuildQueryOutputTree.do";
				<!-- Open connection to servlet -->
				request.open("POST",url,true);	
				request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
				request.send(actionURL);	
				addedNodes = addedNodes + ","+id;
			}
		}
		isFirtHit = false;
		if(!isFirtHit)
		{
			//alert(isFirtHit);
		  buildSpreadsheet(id);
		  
		}
		isFirtHit = false;
	}
	function buildSpreadsheet(id)
	{
        
		window.parent.frames['gridFrame'].location = "ShowGrid.do?pageOf=pageOfQueryModule&nodeId="+id;
	}
	 function showWorkFlowWizard()
 {
   document.forms[0].action="RedirectToWorkflow.do"
   document.forms[0].submit();
 }
	
	

	function clearTree(id,treeNum)
	{
		tree[treeNum].deleteChildItems(id);
		addedNodes = "";
	}
	function showPopUp()
	{
		//var project	= document.forms['categorySearchForm'].selectedProject.value;
		var url		='GetCountPopUp.do?queryTitle='+encodeURIComponent((document.getElementById("queryTitle1").value));
		pvwindow	=dhtmlmodal.open('Get Count', 'iframe', url,'Search Result', 'width=600px,height=175px,center=1,resize=0,scrolling=1,menubar=0,toolbar=0');
	}
	function setProjectData(dropdown,formName)
	{
		var selectedProject = dropdown.options[dropdown.selectedIndex].value;
		var selectedProjectName = dropdown.options[dropdown.selectedIndex].text;		
		document.forms[formName].selectedProject.value = selectedProject;
		document.forms[formName].selectedProjectName.value = selectedProjectName;
	}
	function getCountAjaxAction(executionId)
	{
		var project				= document.forms['form2'].selectedProject.value;
		var isNewQuery			= document.forms['form2'].isNewQuery.value;
		var abortExecution		= document.forms['form2'].abortExecution.value;
		var executeQuery=parent.window.document.getElementById("executeQuery").value;
		if(abortExecution == "true")
			return;

		if(isNewQuery =="true")
		{
			setStatusProcessing();
			var url	="GetCountAjaxHandler.do?executionId="+executionId+"&selectedProject="+project+"&isNewQuery=true"+"&executeQuery="+executeQuery;
		}
		else
		{
			var url	="GetCountAjaxHandler.do?executionId="+executionId+"&selectedProject="+project+"&isNewQuery=false"+"&executeQuery="+executeQuery;
		}
		var request	=newXMLHTTPReq();
		if(request == null)
		{
			alert ("Your browser does not support AJAX!");
			return;
		}
		var handlerFunction = getReadyStateHandler(request,getCountResponseHandler,true); 
		request.onreadystatechange = handlerFunction; 
		request.open("POST",url,true);    
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send("");
	}
	function setStatusProcessing()
	{
		var StatusObject 		= document.getElementById("StatusId");
		document.getElementById("countResult").style.display = 'none';
		StatusObject.innerHTML	= "Processing";
	}
	function getCountResponseHandler(response)
	{
		if(response=="queryException")	//If some exception occurs at any point,  control will be transfered to  getCountExceptionHandler()
		{
			var executionId = document.forms['form2'].executionId.value ;
			getCountExceptionHandler(executionId);
			return;
		}
		if(response=="wait")	//this will keep waiting and calling ajaxactionwith -1 as first request till point , until execution Id donot get initialized at the back-end
		{
			var StatusObject 		= document.getElementById("StatusId");
			StatusObject.innerHTML	= "Processing";
			getCountAjaxAction(-1);
			return;
		}
		var isNewQuery 		= document.forms['form2'].isNewQuery.value;
		var getCountObj		=document.getElementById("form3");
		var abort_notifyObj =document.getElementById("form1");
		
		//making getcount= inactive, abort=active, notify=active
		getCountObj.innerHTML='<img src="images/advancequery/b_get_count_inact.gif" alt="Get Count" width="84" height="23" onclick="">';
		if(isNewQuery=="true")
			abort_notifyObj.innerHTML='<a id="abortExecutionlink" href="javascript:abortExecutionAjaxAction();"><img border="0" src="images/advancequery/b_cancel.gif" alt="Cancel"></a>&nbsp;<img border="0" id="execInBackInact" src="images/advancequery/b_notify_me_inact.gif" alt="Execute in Background">';
		document.forms['form2'].isNewQuery.value="false";
		
		  var executionId  		= null;
		  var StatusObject 		= document.getElementById("StatusId");
		  var CountObject  		= document.getElementById("CountId");
		  var abortExecution	= document.forms['form2'].abortExecution.value;
		  var notify 			= document.forms['form2'].notify.value;
		  var jsonResponse 		= eval('('+ response+')');
	          if(jsonResponse.resultObject!=null)
	          {
				var queryCount	= jsonResponse.resultObject.queryCount;
				var status		= jsonResponse.resultObject.status;
				executionId		= jsonResponse.resultObject.executionId;
				document.forms['form2'].executionId.value = executionId;		 
						 	 
						
				if(isNewQuery=="true" && abortExecution=='false')
				{
					var NoteObject 		= document.getElementById("NoteId");
					var selectedProject	= document.forms['form2'].selectedProject.value;
					var queryTitle 		= jsonResponse.resultObject.queryTitle;
					if(selectedProject!="")
					{
						var project_name 	 = document.forms['form2'].selectedProjectName.value;;
						NoteObject.innerHTML = 'Note: The query "'+queryTitle+'" is executed for project "'+project_name+'". The results will be filtered based on the project rules.';
					}
					else
					{
						NoteObject.innerHTML = 'Note: The query "'+queryTitle+'" is executed without selecting a project, so results from all facilities are included in the count.  If you would want to execute this query for a specific project, you can select the project below and the results will be filtered based on the project rules.';
					}
				}
				if(queryCount!=-1)
				{
					if(StatusObject!=null)
					{
						StatusObject.innerHTML= status;
					}
					if(CountObject!=null)
					{
						CountObject.innerHTML= queryCount;
					}
				}	
	          }
	          
			if(status!="Completed" && abortExecution=="false" && notify=="false" && status!="Query Failed" &&status!="COMPLETED_WITH_WARNING")
			{
				if(status=='In Progress')
				{
				  abort_notifyObj.innerHTML='<a id="abortExecutionlink" href="javascript:abortExecutionAjaxAction();"><img src="images/advancequery/b_cancel.gif" alt="Cancel" border="0" onclick=""></a>&nbsp;<a id="execInBack" href="javascript:retrieveRecentQueries();"><img border="0"  src="images/advancequery/b_notify_me.gif" alt="Execute in Background" onclick=""></a>';
				}
				getCountAjaxAction(executionId);
			}
			else
			{
				if(abortExecution=="true")
					StatusObject.innerHTML="Cancelled";

				document.getElementById("countResult").style.display = 'block';
					
				//making getcount=active , abort=inactive, notify=inactive
				document.forms['form2'].isNewQuery.value="true";	
				getCountObj.innerHTML='<a id="getCountlink" href="javascript:newGetCountAjaxAction('+executionId+');"><img border="0" src="images/advancequery/b_get_count.gif" alt="Get Count" width="84" height="23"></a>';
				abort_notifyObj.innerHTML='<img id="inActiveCancel" src="images/advancequery/b_cancel_inact.gif" alt="Cancel" onclick="">&nbsp;<img id="execInBackInact" src="images/advancequery/b_notify_me_inact.gif" alt="Execute in Background" onclick="">';
			}
	}
	function abortExecutionAjaxAction()
	{
		document.forms['form2'].abortExecution.value="true";
		var executionId = document.forms['form2'].executionId.value;
		var url			="GetCountAjaxHandler.do?executionId="+executionId+"&abortExecution=true&isNewQuery=false";
		var request		=newXMLHTTPReq();
		if(request == null)
		{
			alert ("Your browser does not support AJAX!");
			return;
		}
		var handlerFunction			=getReadyStateHandler(request,abortExecutionResponseHandler,true); 
		request.onreadystatechange	=handlerFunction; 
		request.open("POST",url,true);    
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send("");
	}
	function abortExecutionResponseHandler(response)
	{
		if(response=="queryException")
		{
			var executionId = document.forms['form2'].executionId.value ;
			getCountExceptionHandler(executionId);
			return;
		}
		var abortExecution		= document.forms['form2'].abortExecution.value;
		var executionId			= document.forms['form2'].executionId.value;
		var StatusObject		= document.getElementById("StatusId");
		StatusObject.innerHTML	= "Cancelled";
		
		//making getcount=active, abort=inactive, notify=inactive
		var getCountObj		=document.getElementById("form3");
		var abort_notifyObj =document.getElementById("form1");
		document.forms['form2'].isNewQuery.value="true";
		//document.forms['form2'].abortExecution.value="false";
		getCountObj.innerHTML='<a id="getCountlink"  href="javascript:newGetCountAjaxAction('+executionId+');"><img border="0" src="images/advancequery/b_get_count.gif" alt="Get Count" width="84" height="23"></a>';
		abort_notifyObj.innerHTML='<img id="inactiveCancel" src="images/advancequery/b_cancel_inact.gif" alt="Cancel" onclick="">&nbsp;<img id="execInBackInact" src="images/advancequery/b_notify_me_inact.gif" alt="Execute in Background" onclick="">';
	}
	function getCountExceptionHandler(executionId)
	{
		var StatusObject 		= document.getElementById("StatusId");
		if(StatusObject!=null)
			{
				StatusObject.innerHTML= "Error Occured";
			}
		
		//making getcount=active, abort=inactive, notify=inactive
		var getCountObj		=document.getElementById("form3");
		var abort_notifyObj =document.getElementById("form1");
		document.forms['form2'].isNewQuery.value="true";
		getCountObj.innerHTML		='<a id="getCountLink" href="javascript:newGetCountAjaxAction('+executionId+');"><img border="0" src="images/advancequery/b_get_count.gif" alt="Get Count" width="84" height="23"></a>';
		abort_notifyObj.innerHTML='<img src="images/advancequery/b_cancel_inact.gif" alt="Cancel" onclick="">&nbsp;<img src="images/advancequery/b_notify_me_inact.gif" alt="Execute in Background" onclick="">';
	}
	function newGetCountAjaxAction(executionId)
	{
		//abortExecution.value will be made false ,only when its a new get count query fired on the pop-up
		document.forms['form2'].abortExecution.value="false";
		getCountAjaxAction(executionId);
	}
	function showSpreadsheetData(columnDataStr)
	{
		var columnData = columnDataStr.split("&");
		var columns = columnData[0];	
		var data = columnData[1];	
		var columnNames = columns.split(",");
		var width ="";
		var colDataTypes1 = ""
		var colTypes1 = "";
		if(columns != 'Entity Name, Count')
		{
			var width =180 +",";
			var colDataTypes1 = "ch,"
			var colTypes1 = "ch,";
		}
		for(i=0; i<columnNames.length; i++)
		{
			var name = columnNames[i];
			if(!name == "")
			{
				width = width + "180,"
				colDataTypes1 = colDataTypes1 + "ro,";
				colTypes1 = colTypes1 +"str,";
			}		
		}		
		mygrid.clearAll();
		mygrid.setHeader(columns);
		mygrid.setInitWidths(width);
		mygrid.setColTypes(colDataTypes1);
		mygrid.setColSorting(colTypes1);
	//	mygrid.enableAutoHeigth(true);
		mygrid.init();
		var myData = data.split("|");
		for(var row=0;row<myData.length;row++)
		{
			if(row != "")
			{
				if(columns == 'Entity Name, Count')
				{
					data = myData[row];
				}
				else
				{
					data = "0,"+myData[row];
				}
				mygrid.addRow(row+1,data,row+1);
			}
		}	
	}

	function expand()
	{			
		//Bug Fixed :- 12511 (amit_doshi)
	  switchObj = document.getElementById('image');
	  dataObj = document.getElementById('collapsableTable');
      var resultSetDiv = document.getElementById('resultSet');
	  var advancedSearchHeaderTd = document.forms[0].elements['advancedSearchHeaderTd'];
	  var advancedSearchHeaderTd = document.getElementById('advancedSearchHeaderTd');
	  var imageContainer = document.getElementById('imageContainer');
        
		 	   
	   if(dataObj.style.display != 'none') //Clicked on - image
		{
			advancedSearchHeaderTd.style.borderBottom = "1px solid #cccccc";
            imageContainer.style.borderBottom = "1px solid #cccccc";
			dataObj.style.display = 'none';				
			switchObj.innerHTML = '<img src="images/advancequery/nolines_plus.gif" id="plusImage" border="0"/>';
		
			resultSetDiv.style.height = "490"+'px';
		}
		else  							   //Clicked on + image
		{
           switchObj.innerHTML = '<img src="images/advancequery/nolines_minus.gif" id="minusImage" border="0" />';
			advancedSearchHeaderTd.style.borderBottom = "0";
			imageContainer.style.borderBottom = "0";
			if(navigator.appName == "Microsoft Internet Explorer")
			{					
				dataObj.style.display = 'block';
			}
			else
			{
				dataObj.style.display = 'table-row';
				dataObj.style.display = 'block';
			}
			resultSetDiv.style.height = "400"+'px';
			
		}
	}
	
	function operatorChanged(rowId,dataType)
	{
		
		var textBoxId = rowId+"_textBox1";
		var calendarId1 = rowId+"_calendar1";
		var textBoxId0 = rowId+"_textBox";
		var calendarId0 = "calendarImg";
		var opId =  rowId+"_combobox";

		var combobox2 = rowId + "_combobox2";

		
		if(document.getElementById(textBoxId0))
		{
			document.getElementById(textBoxId0).value = "";
		}
		if(document.all)
		{
			var op = document.getElementById(opId).value;
		}
		else if(document.layers)
		{
			var op = document.getElementById(opId).value;
		} 
		else if(document.forms[0].name=='saveQueryForm')
		{
            var op = document.forms['saveQueryForm'].elements[opId].value;   
		}
		else if(document.forms[0].name=='fetchQueryForm')
		{
			op = document.forms['fetchQueryForm'].elements[opId].value;
		}
		else
		{
			op = document.forms['categorySearchForm'].elements[opId].value;
		}	
	    if(op == "Is Null" || op== "Is Not Null")
		{
			document.getElementById(textBoxId0).value = "";
			document.getElementById(textBoxId0).disabled = true;
			if(dataType == "true")
			{
				document.getElementById(calendarId0).disabled = true;
			}	
		} 	
		if(op == "In" || op== "Not In")
		{
			
		}
		else
		{
			document.getElementById(textBoxId0).disabled = false;
			if(dataType == "true")
			{
				document.getElementById(calendarId0).disabled = false;
			}	
		}
		if(op == "Is Null" || op== "Is Not Null")
		{
			document.getElementById(textBoxId0).disabled= true;
		} 
		else
		{
			if(document.getElementById(textBoxId0))
			{
				document.getElementById(textBoxId0).disabled= false;
			}
		}
		if(op == "Between")
		{
			if(document.all) 
			{
				document.getElementById(textBoxId0).value = "";
				document.getElementById(textBoxId).style.display="block";		
				if(dataType == "true")
				{
					document.getElementById(calendarId1).style.display="block";		
				}
			} 
			else if(document.layers) 
			{
				document.elements[textBoxId0].value = "";
				document.elements[textBoxId].visibility="visible";
			}
			else
			{
				document.getElementById(textBoxId0).value = "";
				var textBoxId1 = document.getElementById(textBoxId);
				textBoxId1.style.display="block";
				if(dataType == "true")
				{
					var calId = document.getElementById(calendarId1);
					calId.style.display="block";
				}
				else
				{
					var comboboxID = document.getElementById(combobox2);
					comboboxID.style.display="block";
				}
			}	
		}
		else if(op == "In" || op== "Not In")
		{
			if(document.all)
			{
				document.getElementById(textBoxId).style.display="none";		
				if(dataType == "true")
				{
					document.getElementById(calendarId1).style.display="none";	
				}
			}
			else if(document.layers)
			{
				document.elements[textBoxId].visibility="none";
			}
			else
			{
				var textBoxId1 = document.getElementById(textBoxId);
				if(textBoxId1)
				{
					textBoxId1.style.display="none";
				}
				if(dataType == "true")
				{
					var calId = document.getElementById(calendarId1);
					calId.style.display="none";
				}
			}	
		}	
		else 
		{
			if(document.all) 
			{
				document.getElementById(textBoxId).style.display="none";		
				if(dataType == "true")
				{
					document.getElementById(calendarId1).style.display="none";	
				}
			}
			else if(document.layers)
			{
				document.elements[textBoxId].visibility="none";
			} 
			else
			{
				var textBoxId1 = document.getElementById(textBoxId);
				textBoxId1.style.display="none";
				if(dataType == "true")
				{
					var calId = document.getElementById(calendarId1);
					calId.style.display="none";
				}
				else
				{
					var comboboxID = document.getElementById(combobox2);
					comboboxID.style.display="none";
				}
			}	
		}
	}
	function changeIdOperator(componentId,compIdofID)
	{
		var nameOperator = componentId + "_combobox";
		var idOperator = compIdofID + "_combobox";	 //id operator component for hidden attribute is textbox 
		var op = document.getElementById(nameOperator).value;
		var idOp = document.getElementById(idOperator);
		idOp.value = op;
	}
	function expandCollapseDag()
	{
	}
	function setFocusOnSearchButton(e)
	{
		if (!e) var e = window.event
		if (e.keyCode) code = e.keyCode;
		else if (e.which) code = e.which;
		
		if(code == 13)
		{
			var platform = navigator.platform.toLowerCase();
			if (platform.indexOf("mac") == -1)
			{						
				document.getElementById('searchButton').focus();
			}	
		} 
		else return true;
		
	}
  
	function checkForSplChar(textFieldValue)
	{
	     var splChar= new Array(',','!','@','#','$','%','^','&','*','(',')','_','-','+','=','|','{','[',']',',',':',';','/','?','.','<','>','~','`','*','"','\\'); 
	     splCharsize = splChar.length;
	     //alert("textFieldValue "+textFieldValue);
	   	for( i=0; i < splCharsize ; i++)
		{ 
		   
		 //  alert('<%= splChar[i] %>');
		   if( textFieldValue.indexOf( splChar[i] ) != -1)
		  {
		    alert("Special Character are not allowed for search");
			return true;
		  }
	   }
	 
		  return false;
	}
	
	
	function retriveSearchedEntities(url,nameOfFormToPost,currentPage, key) 
	{
		waitCursor();		
		var textFieldValue = document.forms[0].textField.value;
		
		var request = newXMLHTTPReq();		
		var classCheckStatus = document.forms[0].classChecked.checked;
		var attributeCheckStatus = document.forms[0].attributeChecked.checked;
		var radioCheckStatus;
		var actionURL;
		
		// Bug #5131: Setting 'text' mode as default
		radioCheckStatus="text_radioButton";
		/*
		if(document.forms[0].selected[0].checked)
			radioCheckStatus = "text_radioButton";
		else if(document.forms[0].selected[1].checked)
			radioCheckStatus = "conceptCode_radioButton";
		*/
      	 if( checkForSplChar(textFieldValue))
		{
		   hideCursor();
		    return;
		 }
		if(currentPage == 'null' || currentPage== "editQuery")
		{
			var handlerFunction = getReadyStateHandler(request,onResponseUpdate,true);
			actionURL = "textField=" + textFieldValue + "&attributeChecked=" + attributeCheckStatus + "&classChecked=" + classCheckStatus +"&selected=" + radioCheckStatus+"&currentPage=AddLimits&key="+key;
		}
		else
		{
			actionURL = "textField=" + textFieldValue + "&attributeChecked=" + attributeCheckStatus + "&classChecked=" + classCheckStatus + "&selected=" + radioCheckStatus +"&currentPage=DefineResultsView";
			var handlerFunction = getReadyStateHandler(request,showEntityListOnDefineViewPage,true);
		}
		request.onreadystatechange = handlerFunction;
				
		
		if(!(classCheckStatus || attributeCheckStatus) ) 
		{
			alert("Please choose at least one option for metadata search from advanced options ");
			onResponseUpdate(" ", textFieldValue, attributeChecked);
		}
		else if(textFieldValue == "")
		{
			alert("Please Enter the String to search.");
			onResponseUpdate(" ", textFieldValue, attributeChecked);
		}
		else if(radioCheckStatus == null)
		{
			alert("Please select any of the radio button : 'based on' criteria");
			onResponseUpdate(" ", textFieldValue, attributeChecked);
		}
		else
		{
			request.open("POST",url,true);	
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send(actionURL);
		}
	}
	function showEntityListOnDefineViewPage(text)
	{
		var element = document.getElementById('resultSet');
		if(text.indexOf("No result found") != -1)
		{
			element.innerHTML =text;
		} 
		else
		{
			var listOfEntities = text.split("!&&!");
			var row ='<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1">';
			for(i=1; i<listOfEntities.length; i++)
			{
				var e = listOfEntities[i];			
				var nameIdDescription = e.split("|");		
				var name = nameIdDescription[0];
				var id = nameIdDescription[1];				
				var description = nameIdDescription[2];
				var functionCall = "addNodeToView('"+id+"')";		
				var entityName = "<font color=#0000CC>"+name +"</font>";
				var tooltipFunction = "Tip('"+description+"', WIDTH, 200)";				
				row = row+'<tr><td style="padding-left:10px;"><a  class="bluelink" id="'+ name+"_link" +'" onmouseover="'+tooltipFunction+'"  href="javascript:'+functionCall+'">' +name+ '</a></td></tr>';
				
			}
			row = row+'</table>';		
			element.innerHTML =row;
		}
		hideCursor();
	}
	
	function onResponseUpdate(text)
	{
		
		var element = document.getElementById('resultSet');
		if(text.indexOf("No result found") != -1)
		{
			element.innerHTML =text;
		} 
		else
		{
		  
			var listOfEntities = text.split("!&&!");
			var length = listOfEntities.length;
			var temp = listOfEntities[length-1].split("*&*");
			listOfEntities[length-1] = temp[0];
			var key = temp[1];
			var textField = temp[2];
			var attributeChecked = temp[3];
			var permissibleValuesChecked = temp[4];
			var row ='<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1">';
			for(i=1; i<listOfEntities.length; i++)
			{
				var e = listOfEntities[i];			
				var nameIdDescription = e.split("|");		
				var name = nameIdDescription[0];
				var id = nameIdDescription[1];				
				var description = nameIdDescription[2];
				var functionCall = "retriveEntityInformation('loadDefineSearchRules.do','categorySearchForm','"+id+"','"+textField+"','"+attributeChecked+"','"+permissibleValuesChecked+"')";		
				var entityName = "<font color=#0000CC>"+name +"</font>";
				//var tooltipFunction = "Tip('"+description+"', WIDTH, 200)";					
				row = row+'<tr><td style="padding-left:10px;" title="'+description+'"><a  class="bluelink" id="'+ name+"_link" +'" href="javascript:'+functionCall+'">' +name+ '</a></td></tr>';
			}			
			row = row+'</table>';		
			element.innerHTML =row;
			
			if (key != null && key == 13 && listOfEntities.length == 2)
			{
				var e = listOfEntities[1];	
				var nameIdDescription = e.split("|");	
				var id = nameIdDescription[1];		
				retriveEntityInformation('loadDefineSearchRules.do','categorySearchForm', id, textField, attributeChecked, permissibleValuesChecked);	
			}
		}
		hideCursor();
	}
	function retriveEntityInformation(url,nameOfFormToPost,entityName, textField, attributeChecked, permissibleValuesChecked) 
	{	
		waitCursor();
		var pageOf= document.getElementById("pageOf").value;
		var request = newXMLHTTPReq();			
		var actionURL;
		var handlerFunction = getReadyStateHandler(request,showEntityInformation,true);	
		request.onreadystatechange = handlerFunction;	
		actionURL = "entityName=" + entityName + "&textField=" + textField + "&attributeChecked=" + attributeChecked + "&permissibleValuesChecked=" + permissibleValuesChecked+ "&pageOf="+pageOf;				
		request.open("POST",url,true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);		
	} 
	function showEntityInformation(text)
	{	
	    var row = document.getElementById('validationMessagesRow');
		var rowMsg = document.getElementById('rowMsg');
		row.innerHTML = "";	
		row.style.display = 'none';
		rowMsg.style.display = 'none';
		var element = document.getElementById('addLimitsSection');
		var elementDiv = document.getElementById('addLimits');
		var addLimitsMsgElement = document.getElementById('AddLimitsMsgRow');
		
		var resoln= screen.width; 
		element.style.height="240px";
		if(resoln<=800)
		{
		  element.style.width="512px";
		}
		if(text.indexOf("####") != -1)
		{
			var htmlArray = text.split('####');
			addLimitsMsgElement.style.display = 'block';
			addLimitsMsgElement.innerHTML = htmlArray[0];
			var addLimitsButtonElement = document.getElementById('AddLimitsButtonRow');
			addLimitsButtonElement.innerHTML = htmlArray[1];
			elementDiv.innerHTML =htmlArray[2];
		} else 
		{
			elementDiv.innerHTML = "";
			row.style.display = 'block';
			rowMsg.style.display = 'block';
			row.innerHTML = text;
			self.scrollTo(0,0);
		}
		hideCursor();
	}

	function createQueryStringForSavedTQ (nameOfFormToPost,totalCFCount,queryId)
	{
		var strToCreateTQObject = "";
		for(i=0;i<totalCFCount;i++)
		{
			var isTimestampFielsId = "isTimeStamp_"+i;
			var tQpId = i+"_combobox";
			var tQchkBoxId = i+"_checkbox";
			var tQtextboxId = "Calendar"+i+"_textBox";
			var tQunitId = "Calendar"+i+"_combobox1";
			var tQtextboxId1 = "Calendar"+i+"_textBox1";
			var tQunitId1 = "Calendar"+i+"_combobox2";
			
			var checkCalendar = document.getElementById(tQtextboxId);
			if(checkCalendar == null)
			{
				tQtextboxId = i+"_textBox";
				tQunitId = i+"_combobox1";
				tQtextboxId1 = i+"_textBox1";
				tQunitId1 = i+"_combobox2";
			}
			
			
			if(queryId!="null" && queryId!="")
			{
				isTimestampFielsId = "isTimeStamp_"+i+"_"+queryId;
				 tQpId = i+"_"+queryId+"_combobox";
				 tQchkBoxId = i+"_"+queryId+"_checkbox";
				 tQtextboxId ="Calendar"+ i+"_"+queryId+"_textBox";
				 tQunitId = "Calendar"+i+"_"+queryId+"_combobox1";
				 tQtextboxId1 = "Calendar"+i+"_"+queryId+"_textBox1";
				 tQunitId1 = "Calendar"+i+"_"+queryId+"_combobox2";
				 checkCalendar = document.getElementById(tQtextboxId);
				 if(checkCalendar == null)
				{
					tQtextboxId = i+"_"+queryId+"_textBox";
					 tQunitId = i+"_"+queryId+"_combobox1";
					 tQtextboxId1 = i+"_"+queryId+"_textBox1";
					 tQunitId1 = i+"_"+queryId+"_combobox2";
				}
			}
		  
		  var tQchkBox = document.getElementById(tQchkBoxId).checked;
		  
             if(tQchkBox==true)
					 
			{
				
			var isTimestamp = document.getElementById(isTimestampFielsId).value;
			var tQop = document.getElementById(tQpId).value;
		    var tQtextbox = document.getElementById(tQtextboxId).value;
		   
		   if(tQop == "Between")	
		  {
			 tQtextbox1 = document.getElementById(tQtextboxId1).value;
			 if(tQtextbox1 != "" && tQtextbox == "")
			 { 
				tQtextbox = "missingTwoValues";
			 }
		  }
           strToCreateTQObject = strToCreateTQObject+"@#condition#@"+i+"##"+tQop+"##"+tQtextbox;
		
			if(isTimestamp == 'false' && document.getElementById(tQunitId)!=null)
		    {
		       var tQunit = document.getElementById(tQunitId).value;
			   strToCreateTQObject = strToCreateTQObject + "##"+tQunit;
			}
			if(tQop == "Between")	
		   {
               
                
				var tQtextbox1 = document.getElementById(tQtextboxId1).value;
				if(tQtextbox1 == "" && tQtextbox != "")
			    { 
					tQtextbox1 = "missingTwoValues";
				}
				strToCreateTQObject = strToCreateTQObject+"##"+tQtextbox1;
				var tQunit1 = document.getElementById(tQunitId1);
				if(isTimestamp == 'false' && tQunit1!=null)
			   {
					strToCreateTQObject = strToCreateTQObject+"##"+tQunit1.value;
			   }
				
		   }
	     }
		}
		return strToCreateTQObject;
	}
	
	function createQueryStringForSavedQuery(nameOfFormToPost, entityName , attributesList,callingFrom)
	{
		var strToCreateQueyObject ="";
		var attribute = attributesList.split("!&&!");
		for(i=1; i<attribute.length; i++)
		{
			var opId =  attribute[i]+"_combobox";
			var textBoxId = attribute[i]+"_textBox";
			var textBoxId1 = attribute[i]+"_textBox1";
			var enumBox = attribute[i]+"_enumeratedvaluescombobox";
			
			//var radioButtonFalse = attribute[i]+"_radioButton_false";
			if(navigator.appName == "Microsoft Internet Explorer")
			{					
				var op = document.getElementById(opId).value;
				if(document.getElementById(enumBox))
				{
					enumValue = document.getElementById(enumBox).value;
				}
			}
			else
			{
				var op = document.forms[nameOfFormToPost].elements[opId].value;
				if(document.forms[nameOfFormToPost].elements[enumBox])
				{
					enumValue = document.forms[nameOfFormToPost].elements[enumBox].value;	
				}
			}		
			if(op != "Between")
			{
				if (document.getElementById(textBoxId))
				{
					textId = document.getElementById(textBoxId).value;		
					/*if(textId != "")
					{*/
						if(op == "In" || op =="Not In")
						{
							var valString = "";
							var inVals = textId.split(",");
							for(g=0; g<inVals.length; g++)
							{
								if(inVals[g] != "")
								{
									/*QueryModuleConstants.QUERY_VALUES_DELIMITER="&DEL_VAL&" */
									valString = valString  + "&DEL_VAL&" + inVals[g];
								}
							}
							if(valString == "")
								strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + " " +"!&&!";
							else
								strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + valString +"!&&!";
						} else 
						{
							if(textId == "")
								strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + " " +"!&&!";
							else
								strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + textId +"!&&!";
						}
					//}
				}
				var ob = null;
				if(navigator.appName == "Microsoft Internet Explorer")
				{
					if(document.getElementById(enumBox))
						ob =  document.getElementById(enumBox);
				}
				else
				{
					if(document.forms[nameOfFormToPost].elements[enumBox])
						ob = document.forms[nameOfFormToPost].elements[enumBox];
				}	

				if(ob!=null)
				{
					if(ob.value != "")
					{
						var values = "";
					   if(ob.size==1 && ob.selectedIndex!=-1 )	
					  {
					    var selectedValue = ob.options[ob.selectedIndex].value;
							/*QueryModuleConstants.QUERY_VALUES_DELIMITER="&DEL_VAL&" */
						values = values + "&DEL_VAL&" +  selectedValue;
						//ob.options[ob.selectedIndex].selected = false;
                       // ob.selectedIndex = -1;
					  }
					else
				  {		
					 for(index1=0;index1<ob.length; index1++)
					{
							if(ob.options[index1].selected)
							{
							var selectedValue = ob.options[index1].value;
 						    values = values + "&DEL_VAL&" +  selectedValue;
							}
					}
				  }
						
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + values +"!&&!";
					}
					else if(ob.value == "")
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + " " +"!&&!";
					}
				}
				var radioButtonTrue = attribute[i]+"_radioButton_true";
			    var radioButtonFalse = attribute[i]+"_radioButton_false";
				if(document.getElementById(radioButtonTrue) != null  || document.getElementById(radioButtonFalse)!= null)
				{
					var objTrue = document.getElementById(radioButtonTrue);
					var objFalse = document.getElementById(radioButtonFalse);
					if(objTrue.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + 'true' +"!&&!";
					}
					else if(objFalse.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + 'false' +"!&&!";
					}
					else if(!objTrue.checked && !objFalse.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + " " +"!&&!";
					}
				}
				else
				{
				   if(callingFrom=='addLimit')
					{
				 	 var row = document.getElementById('validationMessagesRow');
				 	 row.innerHTML = "";
					}
					
				}
			}
			if(op == "Between")
			{
				if(document.getElementById(textBoxId1))
				{
					textId1 = document.getElementById(textBoxId1).value;
				}
				if (document.getElementById(textBoxId))
				{
					textId = document.getElementById(textBoxId).value;		
				}
				if(textId != "" && textId1 == "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + textId +"!*=*!"+"missingTwoValues"+"!&&!";
				}
				if(textId1 != "" && textId == "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + "missingTwoValues" +"!*=*!"+"textId1"+"!&&!";
				}
				if(textId != "" && textId1!= "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + textId +"!*=*!"+textId1+"!&&!";
				}
				if(textId == "" && textId1== "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + " " +"!*=*!"+" "+"!&&!";
				}
			}
			if(op == "Is Null" || op == "Is Not Null")
			{
				strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op +"!&&!";
			}
		}
		
           return strToCreateQueyObject;
	}

    function createQueryStringForExecution(nameOfFormToPost,attributesList,callingFrom)
	{
	    var strToCreateQueyObject ="";
		var attribute = attributesList.split("!&&!");
		for(i=1; i<attribute.length; i++)
		{
			//Remove the Query Id from each attribute list
           var arr = new Array();
		   arr = attribute[i].split("_");

		   var attributeId = "";
		   var arrLength = arr.length;
		   for(var j=0; j < arrLength-2; j++)
		   {
               attributeId = attributeId + arr[j];
			   attributeId = attributeId +"_";
           }
		   attributeId = attributeId + arr[arrLength-2];
		   var opId =  attribute[i]+"_combobox";
		   var textBoxId =  attribute[i]+"_textBox";
		   var textBoxId1 =  attribute[i]+"_textBox1";
		   var enumBox =  attribute[i]+"_enumeratedvaluescombobox";
						
			//var radioButtonFalse = attribute[i]+"_radioButton_false";
			if(navigator.appName == "Microsoft Internet Explorer")
			{					
				var op = document.getElementById(opId).value;
				if(document.getElementById(enumBox))
				{
					enumValue = document.getElementById(enumBox).value;
				}
			}
			else
			{
				var op = document.forms[nameOfFormToPost].elements[opId].value;
				if(document.forms[nameOfFormToPost].elements[enumBox])
				{
					enumValue = document.forms[nameOfFormToPost].elements[enumBox].value;	
				}
			}	
			if(op != "Between")
			{
				if (document.getElementById(textBoxId))
				{
					
					textId = document.getElementById(textBoxId).value;	
					if(textId != "")
					{
						if(op == "In" || op =="Not In")
						{
							var valString = "";
							var inVals = textId.split(",");
							for(g=0; g<inVals.length; g++)
							{
								if(inVals[g] != "")
								{
									/*QueryModuleConstants.QUERY_VALUES_DELIMITER="&DEL_VAL&" */
									valString = valString  + "&DEL_VAL&" + inVals[g];
								}
							}
							strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + valString +"!&&!";
						} else 
						{
							strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + textId +"!&&!";
						}
					}
				}
				var ob = null;
				if(navigator.appName == "Microsoft Internet Explorer")
				{
					if(document.getElementById(enumBox))
						ob =  document.getElementById(enumBox);
				}
				else
				{
					if(document.forms[nameOfFormToPost].elements[enumBox])
						ob = document.forms[nameOfFormToPost].elements[enumBox];
				}	

				if(ob != null)
				{
					//var isselected[];
					var index1 = 0;
					if(ob.value != "")
					{
						var values = "";
						for(index1=0;index1<ob.length; index1++)
						{
							if(ob.options[index1].selected)
							{
							var selectedValue = ob.options[index1].value;
 						    values = values + "&DEL_VAL&" +  selectedValue;
							}
						}
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + values +"!&&!";
					}
				}
				var radioButtonTrue =  attribute[i]+"_radioButton_true";
			    var radioButtonFalse =  attribute[i]+"_radioButton_false";
				if(document.getElementById(radioButtonTrue) != null  || document.getElementById(radioButtonFalse)!= null)
				{
					var objTrue = document.getElementById(radioButtonTrue);
					var objFalse = document.getElementById(radioButtonFalse);
					if(objTrue.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + 'true' +"!&&!";
					}
					else if(objFalse.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + 'false' +"!&&!";
					}
				}
				else
				{
				  
				  /*Commented out by Baljeet .... not sure why it is required */
				  /*  
				  
				  if(callingFrom=='addLimit')
					{
				 	 var row = document.getElementById('validationMessagesRow');
				 	 row.innerHTML = "";
					}

					*/
				}
			}
			if(op == "Between")
			{
				if(document.getElementById(textBoxId1))
				{
					textId1 = document.getElementById(textBoxId1).value;
				}
				if (document.getElementById(textBoxId))
				{
					textId = document.getElementById(textBoxId).value;		
				}
				if(textId != "" && textId1 == "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + textId +"!*=*!"+"missingTwoValues"+"!&&!";
				}
				if(textId1 != "" && textId == "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + "missingTwoValues" +"!*=*!"+"textId1"+"!&&!";
				}
				if(textId != "" && textId1!= "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op + "!*=*!" + textId +"!*=*!"+textId1+"!&&!";
				}
			}
			if(op == "Is Null" || op == "Is Not Null")
			{
				strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attributeId + "!*=*!" + op +"!&&!";
			}
		}
	    return strToCreateQueyObject;

	}
	function createQueryString(nameOfFormToPost,attributesList,callingFrom)
    {
         //waitCursor();
		var strToCreateQueyObject ="";
		var attribute = attributesList.split("!&&!");
		for(i=1; i<attribute.length; i++)
		{
			var opId =  attribute[i]+"_combobox";
			
			var textBoxId = attribute[i]+"_textBox";
			
			var textBoxId1 = attribute[i]+"_textBox1";
			var enumBox = attribute[i]+"_enumeratedvaluescombobox";
						
			//var radioButtonFalse = attribute[i]+"_radioButton_false";
			if(navigator.appName == "Microsoft Internet Explorer")
			{					
				var op = document.getElementById(opId).value;
				if(document.getElementById(enumBox))
				{
					enumValue = document.getElementById(enumBox).value;
				}
			}
			else
			{
				var op = document.forms[nameOfFormToPost].elements[opId].value;
				if(document.forms[nameOfFormToPost].elements[enumBox])
				{
					enumValue = document.forms[nameOfFormToPost].elements[enumBox].value;	
				}
			}	
			if(op != "Between")
			{
				if (document.getElementById(textBoxId))
				{
					textId = document.getElementById(textBoxId).value;	
					if(textId != "")
					{
						if(op == "In" || op =="Not In")
						{
							var valString = "";
							var inVals = textId.split(",");
							for(g=0; g<inVals.length; g++)
							{
								if(inVals[g] != "")
								{
									/*QueryModuleConstants.QUERY_VALUES_DELIMITER="&DEL_VAL&" */
									valString = valString  + "&DEL_VAL&" + inVals[g];
								}
							}
						 strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + valString +"!&&!";

						} else 
						{
							strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + textId +"!&&!";
						}
					}
				}
				var ob = null;
				if(navigator.appName == "Microsoft Internet Explorer")
				{
					if(document.getElementById(enumBox))
						ob =  document.getElementById(enumBox);
				}
				else
				{
					if(document.forms[nameOfFormToPost].elements[enumBox])
						ob = document.forms[nameOfFormToPost].elements[enumBox];
				}	

				if(ob != null)
				{
					if(ob.value != "")
					{
					  var values = "";
					  if(ob.size==1 && ob.selectedIndex!=-1 )	
					  {
					    var selectedValue = ob.options[ob.selectedIndex].value;
							/*QueryModuleConstants.QUERY_VALUES_DELIMITER="&DEL_VAL&" */
						values = values + "&DEL_VAL&" +  selectedValue;
						//ob.options[ob.selectedIndex].selected = false;
                       // ob.selectedIndex = -1;
					  }
					else
				  {		
					 for(index1=0;index1<ob.length; index1++)
					{
							if(ob.options[index1].selected)
							{
							var selectedValue = ob.options[index1].value;
 						    values = values + "&DEL_VAL&" +  selectedValue;
							}
					}
				  }
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + values +"!&&!";
					}
				}
				var radioButtonTrue = attribute[i]+"_radioButton_true";
			    var radioButtonFalse = attribute[i]+"_radioButton_false";
				if(document.getElementById(radioButtonTrue) != null  || document.getElementById(radioButtonFalse)!= null)
				{
					var objTrue = document.getElementById(radioButtonTrue);
					var objFalse = document.getElementById(radioButtonFalse);
					if(objTrue.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + 'true' +"!&&!";
					}
					else if(objFalse.checked)
					{
						strToCreateQueyObject = strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + 'false' +"!&&!";
					}
				}
				else
				{
				   if(callingFrom=='addLimit')
					{
				 	 var row = document.getElementById('validationMessagesRow');
				 	 row.innerHTML = "";
					}
					
				}
			}
			if(op == "Between")
			{
				if(document.getElementById(textBoxId1))
				{
					textId1 = document.getElementById(textBoxId1).value;
				}
				if (document.getElementById(textBoxId))
				{
					textId = document.getElementById(textBoxId).value;		
				}
				if(textId != "" && textId1 == "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + textId +"!*=*!"+"missingTwoValues"+"!&&!";
				}
				if(textId1 != "" && textId == "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + "missingTwoValues" +"!*=*!"+"textId1"+"!&&!";
				}
				if(textId != "" && textId1!= "")
				{
					strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op + "!*=*!" + textId +"!*=*!"+textId1+"!&&!";
				}
			}
			if(op == "Is Null" || op == "Is Not Null")
			{
				strToCreateQueyObject =  strToCreateQueyObject + "@#condition#@"+ attribute[i] + "!*=*!" + op +"!&&!";
			}
		}
	         return strToCreateQueyObject;
          
         
         } 

	function produceQuery(isEditLimit, url,nameOfFormToPost, entityName , attributesList) 
	{
		       var strToCreateQueyObject = createQueryString(nameOfFormToPost,attributesList,'addLimit');
 	  
	/*	if(navigator.appName == "Microsoft Internet Explorer")
		{
			
			if(isTopButton)
			{
				var isEditLimit = document.getElementById('TopAddLimitButton').value;
			}
			else 
			{
				var isEditLimit = document.getElementById('BottomAddLimitButton').value;
			}
	
		}else
		{
		    alert(document.forms[nameOfFormToPost].elements["TopAddLimitButton"].value);
			if(isTopButton)
		    {
				var isEditLimit = document.forms[nameOfFormToPost].elements['TopAddLimitButton'].value;
			}
			else 
			{
				var isEditLimit = document.forms[nameOfFormToPost].elements["BottomAddLimitButton"].value;
			}
		}*/
		if(isEditLimit == 'Add Limit')
		{	
			//document.applets[0].addExpression(strToCreateQueyObject,entityName);
			addLimit(strToCreateQueyObject,entityName);
		}
		else if(isEditLimit == 'Edit Limit')
		{
			//document.applets[0].editExpression(strToCreateQueyObject,entityName);
			editLimits(strToCreateQueyObject,entityName);
			
		}
		setQueryUpdatedFlag();		
	    hideCursor();
		/**
		* Bug Fixed:- 11290 (User unable to add the same permissible entity twice.)
		*The value of compIdOfID will be store when user click on icon
		* componentIdOfID is the id of the hidden ID field , We need to reset the Ids to add new Med 
		* entity in the DAG panel
		*/
		
		try{
		var componentIdOfID=compIdOfID + "_textBox";
		var EntityIds = document.getElementById(componentIdOfID);
//		EntityIds.value ="";
		}catch(e){}
			
	}
	
	function setQueryUpdatedFlag()
	{
		document.getElementById("isQueryUpdated").value="true";
	}
		
	function viewSearchResults()
	{
        waitCursor();
		callFlexMethod();
     	if(interfaceObj.isDAGEmpty())
		{
			var message = 	"<span class='error_msg'>Graph must have atleast one node.</span>";
			showValidationMessages(message)
		}
		else
		{
		 search();
		}
		 hideCursor();
	//	var errorMessage = document.applets[0].getSearchResults();
	/*	if(errorMessage == null)
		{
			 showViewSearchResultsJsp();
		}
		else if (errorMessage == "<li><font color=\"red\">showErrorPage</font></li>")
		{
			showErrorPage();
		}
		else
		{
			showValidationMessages(errorMessage);
		}
      */  
	}
	function showValidationMessages(text)
	{
		
		var rowId= 'validationMessagesRow';
		var textBoxId1 = document.getElementById("rowMsg");
		var element = document.getElementById('validationMessages');
		var row = document.getElementById(rowId);
		var titleError;
		if(document.getElementById("titleError")!=null)
		{
		  var titleErrorId	= 'titleError';
		  titleError		= document.getElementById(titleErrorId);
		  titleError.innerHTML = "";
	   }
		closeWaitPage();
		row.innerHTML = "";
		if(text == "")
		{
			if(document.all)
			{
				document.getElementById("validationMessagesRow").style.display="none";		
			} 
			else if(document.layers) 
			{
				document.elements['validationMessagesRow'].visibility="none";
			}
			else 
			{
				row.style.display = 'none';		
			}	
		}
		else
		{	
			if(text=="Query title is mandatory." || text=="Query with same name already exists."
				|| text=="Query Title contains invalid character(s).")
				{	titleError.style.display = 'block';
					titleError.innerHTML = text;
				}
			else
				{
					var element = document.getElementById('addLimitsSection');
					if(element!=null)
					{
					  element.style.height="210px";
					}
					row.style.height="30px";
					textBoxId1.style.display='block';
					row.style.display = 'block';
					row.innerHTML = text;
				}
				self.scrollTo(0,0);
		}	
	}
	function showErrorPage()
	{
		document.forms['categorySearchForm'].action='ViewSearchResultsJSPAction.do';
		document.forms['categorySearchForm'].nextOperation.value = "showErrorPage";
		document.forms['categorySearchForm'].submit();	
	}
	function showViewSearchResultsJsp()
	{
	//	showPopUp();
	//	document.forms['categorySearchForm'].action='ViewSearchResultsJSPAction.do';
	//	document.forms['categorySearchForm'].submit();			
	}
	
	function produceSavedQuery(queryId,event)
	{
	  var isCountQuery= document.getElementById("isCountQuery");
	  if(isCountQuery!=null)
	  {
	  	 	var	totalEntitiesId = "totalentities";
			var	totalCFId ="totalCF";
			var	attributeListId = "attributesList";
			var	queryStringId ="queryString";
			var	strToFormTQId ="strToFormTQ";
			var	conditionListId ="conditionList";

		  if(queryId!="null" && queryId!="") //Since queryId is String hence checking as "null"(String)
		  {
    		    totalEntitiesId = totalEntitiesId + "_" +queryId;
                totalCFId  = totalCFId + "_" + queryId;
                attributeListId = attributeListId + "_" + queryId;
				strToFormTQId = strToFormTQId + "_" + queryId;
				conditionListId = conditionListId + "_" +queryId;
		  }
		  
		 var totalentities = document.getElementById(totalEntitiesId).value;
		 var totalCFCount = document.getElementById(totalCFId).value;
		 var numberOfEntities = totalentities.split("!&&!");
		 var strquery='';
         var count = numberOfEntities.length;
		 var displayParam="";
		 for(i=0;i<count-1 ;i++)
		 {
		    var entityName = numberOfEntities[i];
			var attributesListComponent = numberOfEntities[i]+"_attributeList";
			var attributelistElement = document.getElementById(attributesListComponent)
			if(attributelistElement!=null) //if entity has viewable attributes
			{
				 var attributesList = document.getElementById(attributesListComponent).value;
				 var checkboxes = attributesList.split("!&&!"); 
				 for(j=1;j<checkboxes.length;j++)
				 {
	        		var comp = checkboxes[j]+'_checkbox';
					var val = document.getElementById(comp).checked;
					if(val==true)
					{
					   //create parameter list for the displayName of parameter to be used in 'ShowAll' Ajax Request
                          displayLabelId = checkboxes[j]+"_displayName";
						 // alert("displayLabelId"+displayLabelId);
						  //alert("document.getElementById(displayLabelId)"+document.getElementById(displayLabelId));
						  if(document.getElementById(displayLabelId)!=null) 
						    displayParam = displayParam+"&"+displayLabelId+"="+document.getElementById(displayLabelId).value;
						
						strquery = strquery + checkboxes[j] +";" ;                             
					}

	             }
			  }
		 } 
		 document.getElementById("display_Labels").value= displayParam;

		//This statment assigns value to the form attribute in the document
		 var strvalu = document.getElementById(queryStringId);
         strvalu.value =  strquery;
        //Assigning value to hidden element as parameter used in show_all case
        document.getElementById("query_String").value = strquery;
     	var entityName="";
        var frmName = document.forms[0].name;
        var list = document.getElementById(attributeListId).value;
    	var buildquerystr =  createQueryStringForSavedQuery(frmName, entityName , list,frmName);
		
		if(totalCFCount != 0)
		{
			var buildTQstr = createQueryStringForSavedTQ(frmName,totalCFCount,queryId);
			document.getElementById(strToFormTQId).value = buildTQstr;
		}
        document.getElementById(conditionListId).value = buildquerystr;
	}

      if(event=="showAll")
	  {
	    submitShowAll();
	  }
	  else if(event=="save")
		   {
	          submitSave();
		   }

}

function submitShowAll()
{

        var isChecked =  document.getElementById('showAll').checked;
		var conditionListId = "conditionList";
        var queryId= document.getElementById("queryId").value;
		if(queryId!=null && queryId!="null" && queryId!="")
	   {
	       conditionListId = conditionListId+"_"+queryId;
	   }
		var request = newXMLHTTPReq();			
		var actionURL="query_String="+document.getElementById('query_String').value
		+"&conditionList="+encodeURIComponent(document.getElementById(conditionListId).value);
		var param = document.getElementById('display_Labels').value;
		actionURL = actionURL+param;
		var handlerFunction = getReadyStateHandler(request,showAttributesHandler,true);	
		request.onreadystatechange = handlerFunction; 
		var url = "ShowParamQueryAttributes.do?isChecked="+isChecked;
		request.open("POST",url,true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);
}

 function submitSave()
 {
     document.forms[0].target= "_parent";
	 document.forms[0].submit();
 }
	
	
	
	
	function ExecuteSavedQuery(queryId)
	{
		showHourGlass();
		 var frmName = document.forms[0].name;
         var  attributeList  = "attributesList"+"_"+queryId;
		
         var actionURL = null;
		 if(document.getElementById(attributeList) != null)
		{
		   var list = document.getElementById(attributeList).value;
		   var buildquerystr =  createQueryStringForExecution(frmName, list,frmName);
         
		    var conditionList = "conditionList"+"_"+queryId;
		   document.getElementById(conditionList).value = buildquerystr;
		 
		   actionURL = "parameterizedConditionList=" + encodeURIComponent(buildquerystr);
			var totalCFId = "totalCF"+ "_"+queryId;
		    var totalCFCount = document.getElementById(totalCFId).value;
		    if(totalCFCount != 0)
		    {
			  var buildTQStr = createQueryStringForExcecuteSavedTQ(frmName,totalCFCount,queryId);
			  var strToFormTQId = "strToFormTQ"+"_"+queryId;

			  document.getElementById(strToFormTQId).value = buildTQStr;
		      actionURL = actionURL + "&parameterizedConditionForTQ="+encodeURIComponent(buildTQStr);
		    }
			 // var projectId=document.getElementById("selectedProject").value;
	

		 }
			var dropdown=document.getElementById("selectedProject");
			var selectedProject = dropdown.options[dropdown.selectedIndex].value;
			actionURL = actionURL + "&selectedProject="+encodeURIComponent(selectedProject);

		  //Making Ajax call here to update the IQuery with parameterized conditions
		  var request = newXMLHTTPReq();			
		  if(request == null)
		  {
				alert ("Your browser does not support AJAX!");
				return;
		  }
          var handlerFunction = getReadyStateHandler(request,queryUpdationHandler,true);	
          request.onreadystatechange = handlerFunction;
		  var url = "UpdateQueryAction.do";
		  request.open("POST",url,true);	
		  request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		  request.send(actionURL);
  
		  //document.forms[0].submit();
    }

	function queryUpdationHandler(text)
	{
		hideHourGlass();
      if(text == "Get Count")
	  {
    	  var url	='GetCountPopUp.do?queryTitle='+encodeURIComponent((document.getElementById("queryLabel").innerHTML))+'&selectedProject='+document.getElementById("selectedProject").value; //?selectedProject='+project;
		 pvwindow	=dhtmlmodal.open('Get Count', 'iframe', url,'Search Result', 'width=600px,height=175px,center=1,resize=0,scrolling=1,menubar=0,toolbar=0');
	  }
	  else
	  {
		var msgdiv = document.getElementById("validationmsg");
		msgdiv.innerHTML = text;
	  }
    }
    
	function createQueryStringForExcecuteSavedTQ (nameOfFormToPost,totalCFCount,queryId)
	{
      	var strToCreateTQObject = "";
		
		for(i=0;i<totalCFCount;i++)
		{ 
             var isTimestampFielsId = "isTimeStamp_"+i+"_"+queryId;
			var	tQpId = i+"_"+queryId+"_combobox";
			var	tQtextboxId ="Calendar"+ i+"_"+queryId+"_textBox";
			var	tQunitId = "Calendar"+i+"_"+queryId+"_combobox1";
			var	tQtextboxId1 = "Calendar"+i+"_"+queryId+"_textBox1";
			var	tQunitId1 = "Calendar"+i+"_"+queryId+"_combobox2";
			var checkCalendar = document.getElementById(tQtextboxId);

			if(checkCalendar == null)
			{
				tQtextboxId = i+"_"+queryId+"_textBox";
				tQunitId = i+"_"+queryId+"_combobox1";
				tQtextboxId1 = i+"_"+queryId+"_textBox1";
				tQunitId1 = i+"_"+queryId+"_combobox2";
			}
	
       var isTimestamp = document.getElementById(isTimestampFielsId).value;
	      var tQop = document.getElementById(tQpId).value;
		var tQtextbox = document.getElementById(tQtextboxId).value;
		  var tQtextbox1 = "";
		  if(tQop == "Between")	
		  {
			 tQtextbox1 = document.getElementById(tQtextboxId1).value;
			 if(tQtextbox1 != "" && tQtextbox == "")
			 { 
				tQtextbox = "missingTwoValues";
			 }
		  }

		  strToCreateTQObject = strToCreateTQObject+"@#condition#@"+i+"##"+tQop+"##"+tQtextbox;
			if(isTimestamp == 'false' && document.getElementById(tQunitId)!=null)
		    {
		       var tQunit = document.getElementById(tQunitId).value;
			   strToCreateTQObject = strToCreateTQObject + "##"+tQunit;
			}
			if(tQop == "Between")	
		   {
				tQtextbox1 = document.getElementById(tQtextboxId1).value;
				if(tQtextbox1 == "" && tQtextbox != "")
			    { 
					tQtextbox1 = "missingTwoValues";
				}
				strToCreateTQObject = strToCreateTQObject+"##"+tQtextbox1;
				var tQunit1 = document.getElementById(tQunitId1);
				if(isTimestamp == 'false' && tQunit1!=null)
			   {
					strToCreateTQObject = strToCreateTQObject+"##"+tQunit1.value;
			   }
				
		   }
	     }
		return strToCreateTQObject;
	}
	
	function enableDisplayFieldForVIAttr(frm, textfield,hiddenchkbox_id)
	{
	   var fieldName = textfield+'_displayName';
       var sts =  document.getElementById(fieldName).disabled;
	   var chksid=hiddenchkbox_id+"_checkbox";

         if(sts==true)
		{
           document.getElementById(fieldName).disabled=false;
		   document.getElementById(chksid).checked=true;
		}
          else
		{
            document.getElementById(fieldName).disabled=true;
			document.getElementById(chksid).checked=false;
		
		}
	}	
	function enableDisplayField(frm, textfield)
	{
	   var fieldName = textfield+'_displayName';
       var sts =  document.getElementById(fieldName).disabled;
          if(sts==true)
           document.getElementById(fieldName).disabled=false;
          else
            document.getElementById(fieldName).disabled=true;
	}
	
 	function saveQuerySubmitForm(frm,action)
 	{
 	  if(action=='preview')
 	  {
 	    frm.action="/previewExecuteQuery.do?action=preview";
 	    frm.submit();
 	  }
 	  else
 	  { 
 	    frm.action="/saveQuery.do";
 	  }
 	}
	function saveClientQueryToServer(action)
	{
		 var saveAs=document.getElementById("isSaveAs").value;
		 var displaySaveAsElem = document.getElementById("isDisplaySaveAs");
		 var displaySaveAs="";
         if(displaySaveAsElem!=null)
		{
		   displaySaveAs = displaySaveAsElem.value;
		}
		
		var isUpdatedQuery = document.getElementById("isQueryUpdated").value;
		if(action=='next')
		{
			callFlexMethod();
						
			if(interfaceObj.isDAGEmpty())
			{
				//showValidationMessages("<li><font color='red'>Graph must have atleast one node.</font></li>")
				var message = 	"<span class='error_msg'>Graph must have atleast one node.</span>";
				showValidationMessages(message);
			}
			else
			{
			   defineSearchResultsView();
			}
		}
		else if(action=='saveDefineView')
		{
			waitCursor();
			var pageof="";
			var workflow="";
			 
			 if(document.getElementById("isWorkflow")!=null)
			  workflow=document.getElementById("isWorkflow").value;
			 if(document.getElementById("pageOf")!=null) 
              pageof=document.getElementById("pageOf").value; 
			 
			 var element = document.forms[0].selectedColumnNames;
			 var selectedColumnNamesList='';
			 for(i=0;i<element.length;i++) 
			{
				if(i==(element.length-1))
					selectedColumnNamesList = selectedColumnNamesList + element.options[i].value;
				else
					selectedColumnNamesList = selectedColumnNamesList + element.options[i].value + ',';
			}
            var url = "DefineView.do?isWorkflow="+workflow+"&pageOf="+pageof+"&saveAs="+saveAs+"&isQueryUpdated="+isUpdatedQuery;
			
			 //window.open('','SaveQuery','height=315,width=800');
			// pvwindow	= dhtmlmodal.open('Query Information', 'iframe', url,'Query Information', 'width=800px,height=454px,center=1,resize=0,scrolling=1,menubar=0,toolbar=0');
    		document.forms[0].action = url;
			//document.forms[0].target = "SaveQuery";
			document.forms[0].submit();
			// document.forms[0].target='_self';
 		   	/*	if (platform.indexOf("mac") != -1)
			{
		    	
				NewWindow(url,'name',screen.width,screen.height,'yes');
				
		    }
		    else
		    {
				
		    	NewWindow(url,'name','870','600','yes');
				
		    } */
			hideCursor();
	
		}
		  else if(action=='save')
		{
		    waitCursor();
			var pageof="";
			var workflow="";
			 
			 if(document.getElementById("isWorkflow")!=null)
			  workflow=document.getElementById("isWorkflow").value;
			 if(document.getElementById("pageOf")!=null) 
			{
				 pageof=document.getElementById("pageOf").value; 
			} 
			var url = "LoadSaveQueryPage.do?isWorkflow="+workflow+"&pageOf="+pageof+"&saveAs="+saveAs+"&isQueryUpdated="+isUpdatedQuery+"&isDisplaySaveAs="+displaySaveAs;
			document.forms[0].action = url;
    		document.forms[0].submit();
			hideCursor();
		
		}
      
	}
	function showAlertBox(text)
	{
		if(text != "")
		{
			alert(text);
		}
		else
		{
			var url = "LoadSaveQueryPage.do";
			platform = navigator.platform.toLowerCase();
		    if (platform.indexOf("mac") != -1)
			{
		    	NewWindow(url,'name',screen.width,screen.height,'yes');
		    }
		    else
		    {
		    	NewWindow(url,'name','800','600','yes');
		    }
		}
	}

	
	function redefineResultsView()
	{
	   
	
	}
	
	
	
	function defineSearchResultsView()
	{
		 
		  showHourGlass();
			 var request = newXMLHTTPReq();
			 var isUpdatedQuery = document.getElementById("isQueryUpdated").value;			
	         var handlerFunction = getReadyStateHandler(request,displayValidationMessage,true);	
	         request.onreadystatechange = handlerFunction;
		 	 var url='ValidateDefineView.do?pageOf=DefineFilter&isWorkflow=true&isQueryUpdated='+isUpdatedQuery;
			 var actionURL="";
		     request.open("POST",url,true);	
		     request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			  request.send(actionURL);	
		  hideHourGlass();
						
	}
	function showAddLimitsPage()
	{
		document.forms['categorySearchForm'].action='SearchCategory.do';
		document.forms['categorySearchForm'].currentPage.value = "AddLimits222";
		document.forms['categorySearchForm'].submit();
	}
	
	function redefineResultsView()
	{
	   var action ="DefineSearchResultsView.do";
	   document.forms[0].action=action;
	   document.forms[0].submit(); 
	  	
	}
	
	function cancelFromSaveQueryPage(pageOf)
	{
	   var isworkflow=null;
	   var isQueryUpdated = document.getElementById("isQueryUpdated").value
	   if(document.getElementById("workflow")!=null)
	   {	
	      isworkflow = document.getElementById("workflow").value;
	   }
      
	    if(pageOf=="Get Count")
	   {
         document.forms[0].queryname.value=document.getElementById("queryname").value;
		 document.forms[0].isQuery.value = "true"; 
		 document.forms[0].currentPage.value = "CountQuery";
		 document.forms[0].action='SearchCategory.do?workflow='+isworkflow+'&queryname='+document.getElementById("queryname").value+'&isSaveAs='+document.getElementById("isDisplaySaveAs").value+'&queryId='+document.getElementById("queryId").value+'&isQueryUpdated='+isQueryUpdated;
		 
	   }
	   else
	   {
		   document.forms[0].isQuery.value = "true"; 
		   document.forms[0].currentPage.value = "DataQuery";
		   document.forms[0].action="SearchCategory.do?workflow="+isworkflow+"&isQueryUpdated="+isQueryUpdated+'&isSaveAs='+document.getElementById("isSaveAs").value+'&queryId='+document.getElementById("queryId").value;
	   }

	   document.forms[0].submit();

	}
    
	function previousFromDefineResults(pageOf)
	{
		//waitCursor();
		var isQueryUpdated = document.getElementById("isQueryUpdated").value
		var wrkflow="";
		var action="";
		if(document.getElementById("workflow")!=null)
		{
		   wrkflow = document.getElementById("workflow").value;
		   if(pageOf=='SaveQuery')
		  {
			 action ="DefineSearchResultsView.do?workflow="+wrkflow+"&isQueryUpdated="+isQueryUpdated;
		  }
		  else
		  {
		    action ="SearchCategory.do?workflow="+wrkflow+"&isQueryUpdated="+isQueryUpdated;          
		  }
		}
	   else
         var action="SearchCategory.do?isQueryUpdated="+isQueryUpdated;

		document.forms[0].action=action;
		document.forms[0].isQuery.value = "true";  // change for flex
		if(pageOf=="ViewResults")
		{
		 
		  document.forms[0].currentPage.value = "resultsView";
		}
		if(pageOf=="DefineResultsView") 
	  	{ 
	    	
			document.forms[0].currentPage.value = "prevToAddLimits";
		  // select selected column names in listbox
		   var selectedColumns = document.forms[0].selectedColumnNames;
		   if(selectedColumns!=null && selectedColumns.length!=0)
			{
					selectOptions(document.forms[0].selectedColumnNames);
			}
		}
       
		if(pageOf == 'SaveQuery') 
	    {
          var getCountPage = document.getElementById("isCountQuery");
		  if(getCountPage!=null)
		 {
		   document.forms[0].currentPage.value = "CountQuery";
    	 }
		 else 
	    {
		  if(document.getElementById("workflow")!=null)
			document.forms[0].action="DefineSearchResultsView.do?&isQueryUpdated="+isQueryUpdated;
		}
	   }

	////////////
		document.forms[0].submit();
		//hideCursor();
	}
	function setIncludeDescriptionValue()
	{
      var isClassChecked = document.forms[0].classChecked.checked;
	  var isArrtibuteChecked = document.forms[0].attributeChecked.checked;
	  var permissibleValuesChecked = document.forms[0].permissibleValuesChecked.checked;
	  var conceptCodeSelected =  document.forms[0].selected[1].checked;
	  if(isClassChecked==true || isArrtibuteChecked==true)
		{
		  if(permissibleValuesChecked == true || conceptCodeSelected == true )
			{
			   document.forms[0].includeDescriptionChecked.checked = false;
		      document.forms[0].includeDescriptionChecked.disabled = true;
			} else
			{
		          document.forms[0].includeDescriptionChecked.disabled = false;
			}
		}
	   else
		{
	       document.forms[0].includeDescriptionChecked.checked = false;
           document.forms[0].includeDescriptionChecked.disabled = true;
		}

	}
	var radio="";
	var toggleRadio ="";
	function resetOptionButton(id,currentObj)
	{ 
		
		//variable radio keeps track of which radio button is selected (True/False) and 
		// variable toggleRadio maintains the status of the radiobutton clicked (selected/not selected)
		if (id != radio) // if previous object and current object are not same
		{
			radio = id;						// set the current object id;
			toggleRadio = "false";			// default value of radio button
		}
		if (toggleRadio == "false")			//if radiobutton is deselected
		{
			currentObj.checked = true;		// select it
			toggleRadio = "true";			//set toggleRadio to radio button status
		}
		else							//if toggleRadio == "true" i.e. if radiobutton is selected
		{			
			currentObj.checked = false;		// deselect it
			toggleRadio = "false";			//set toggleRadio to radio button status
		}		
		
	}

	function radioButtonSelected(element)
	{
		if(element.value == 'conceptCode_radioButton')
		{
		  document.forms[0].includeDescriptionChecked.checked = false;
		  document.forms[0].includeDescriptionChecked.disabled = true;
		} else
		{
			 var permissibleValuesChecked = document.forms[0].permissibleValuesChecked.checked;
			 if(permissibleValuesChecked == true)
				{
			  	    document.forms[0].includeDescriptionChecked.checked = false;
					document.forms[0].includeDescriptionChecked.disabled = true;
				}
				else		
					  document.forms[0].includeDescriptionChecked.disabled = false;
		}
	}
function permissibleValuesSelected(element)
{
	var conceptCodeSelected =  document.forms[0].selected[1].checked;
	if(element.checked == true)
		{
		   document.forms[0].includeDescriptionChecked.checked = false;
		   document.forms[0].includeDescriptionChecked.disabled = true;
		} else
		{
			if(conceptCodeSelected == true)
			{
		  document.forms[0].includeDescriptionChecked.disabled = true;
			}
			else
			{
		  document.forms[0].includeDescriptionChecked.disabled = false;
			}
		}
  }

function CheckDagContent()
{
  callFlexMethod();
						
		if(interfaceObj.isDAGEmpty())
	   {
			  return true;
			
	   }
			else
	  {
	    	  
			  return false;
	  }
}

  
//---Flex Call
var interfaceObj;

	function callFlexMethod()
	{
		if(navigator.appName.indexOf("Microsoft") != -1)
		{
			interfaceObj = window["DAG"];				
		}
		else
		{
			interfaceObj = document["DAG"];
		}
	}

var jsReady = false;

// ——- functions called by ActionScript ——-
// called to check if the page has initialized and JavaScript is available
	function isReady()
	{
		return jsReady;
	}
// called by the onload event of the <body> tag
	function pageInit()
	 {
	// Record that JavaScript is ready to go.
		jsReady = true;
	}

	function addLimit(strToCreateQueyObject,entityName)
	{	
		callFlexMethod();
		interfaceObj.createNode(strToCreateQueyObject,entityName);
	}

	function editLimits(strToCreateQueyObject,entityName)
	{	
		callFlexMethod();
		interfaceObj.editLimit(strToCreateQueyObject,entityName);
	}
	
	window.onload=function(){
		pageInit();
	}
	
	 function search()
	 {
		  showPopUp();
		 callFlexMethod();
		 interfaceObj.searchResult();
	 }
	
	function addNodeToView(nodesStr)
	{	
		callFlexMethod();
		interfaceObj.addNodeToView(nodesStr);
	}
	
	/*This function is called form QueryListView.jsp. It invokes the FetchAndExecuteQueryAction*/
	function executeQuery(queryId)
	{
		showWaitPage();
		document.getElementById('queryId').value=queryId;
		document.forms[0].submit();
	} 
	
	/* This function is called from QueryListView.jsp It invokes FetchQueryAction */
	function editQuery(queryId)
	{
		var action="FetchQuery.do?queryId="+queryId;
		document.forms[0].action = action;
		window.open(action,"_top");
	} 
	/*This function is called form QueryListView.jsp. Pops up for confirmation while deleting the query*/
	function deleteQueryPopup(queryId, popupMessage)
	{
		var r=confirm(popupMessage);
		if (r==true)
		{
			deleteQuery(queryId);
		}

	} 
	
	function deleteQuery(queryId)
	{
		action="DeleteQuery.do?queryId="+queryId;
		document.forms[0].action = action;
		document.forms[0].submit();
	}  
	
	function openDecisionMakingPage()
	{
		action="OpenDecisionMakingPage.do";
		document.forms[0].action = action;
		document.forms[0].submit();
	}
	function proceedClicked()
	{		
		var radioObj = document.forms[0].options;
		var option = "";
		var radioLength = radioObj.length;
		
		for(var i = 0; i < radioLength; i++) 
		{
			if(radioObj[i].checked)
			{
				option =  radioObj[i].value;
			}
		}		
		if(option == "")
		{
			alert("Please select the option to proceed");
		}
		else if(option == 'redefineQuery')
		{
			onRedefineQueryOption();			
		}
		else
		{
			showWaitPage();
			document.forms[0].submit();			
		}
	}	
	
	function onRedefineQueryOption()
	{
		waitCursor();
		document.forms[0].action='SearchCategory.do?currentPage=resultsView';
		document.forms[0].submit();
		hideCursor();
	}
	function checkItDefault()
	{
		document.forms[0].options[1].checked = "true";
	}
	function showMainObjectNotPresentMessage()
	{
		var request = newXMLHTTPReq();			
		var actionURL;
		var handlerFunction = getReadyStateHandler(request,showValidationMessages,true);	
		request.onreadystatechange = handlerFunction;				
		var url = "QueryMessageAction.do";
		<!-- Open connection to servlet -->
		request.open("POST",url,true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);	
	}
	
	function selectOptions(element)
	{
		for(i=0;i<element.length;i++) 
		{
			element.options[i].selected=true;
		}
	}
var confirmAnswer;
	function preValidateQuery(text, isSaveAs)
	{
		
	   document.getElementById("isSaveAs").value=isSaveAs;
	   var status=document.getElementById("sharedStatus").value
		var isupdated=document.getElementById("isQueryUpdated").value
		if(status=="NOT_SHARED_BUT_USED" && isSaveAs!="true" && text!="cancel")
		{
		    if(isupdated=="true")
			{
				var url = './pages/advancequery/content/search/querysuite/ConfirmationPopUpOkCancel.jsp?messageKey=query.updateQuery';
				pvwindow=dhtmlmodal.open('Update Query', 'iframe', url,'Update Query', 'width=450px,height=105px,center=1,resize=1,scrolling=0');
				pvwindow.onclose=function()
				{
					if(confirmAnswer=="true")
					{
						validateQuery(text);
					}
					return true
				}
			}
			else
			{
				validateQuery(text);
			}

		}
		
		else
		{
			validateQuery(text);
		}
	}
	 
	function validateQuery(text)
	{	
		showHourGlass();
		var request = newXMLHTTPReq();			
		var handlerFunction = getReadyStateHandler(request,displayValidationMessage,true);	
		request.onreadystatechange = handlerFunction;			
		 var actionURL = "buttonClicked=" + text;		
		if(text=='saveDefineView' )
		{			
		  	 var selectedColumns = document.forms['categorySearchForm'].selectedColumnNames;
			 if(selectedColumns.length==0)
			{
				 hideHourGlass();
				alert("We need to add atleast one column to define view testing123");
				return ;
			}
			
			//showWaitPage();
			selectOptions(document.forms[0].selectedColumnNames);
			//document.forms['categorySearchForm'].action = "SearchDefineViewResults.do";	
			//document.forms['categorySearchForm'].submit();
			 var url = "ValidateQuery.do?pageOf="+(document.getElementById("pageOf").value);
		  request.open("POST",url,true);	
		  request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		  request.send(actionURL);	
		}
		else
		{
		
		  if(text=="cancel")	
			{
				var isWorkflow = document.getElementById("isWorkflow").value;
				var forwardTo="";
				if(isWorkflow=='true')
					forwardTo="RedirectToWorkflow.do";
				else
				{
					forwardTo="ShowDashboard.do";
					document.forms[0].requestFrom.value="MyQueries"; 
				}
				document.forms[0].action = forwardTo;
				document.forms[0].submit(); 
				return ;
			}
		  if (text != "save")
		  {
	//		showWaitPage();
		  }
	   var project;
		     if(document.getElementById("queryTitle1")!=null)
		     {
		      if( document.forms['categorySearchForm'].selectedProject!=null)
		       var project = document.forms['categorySearchForm'].selectedProject.value;
		      var url = "ValidateQuery.do?queyTitle="+encodeURIComponent((document.getElementById("queryTitle1").value))+"&selectedProject="+project;
		     }
		     else
		       var url = "ValidateQuery.do?pageOf="+(document.getElementById("pageOf").value);
		  request.open("POST",url,true);	
		  request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		  request.send(actionURL);		
		}
		
	}
	function cancel_GPD_query()
	{

		forwardTo="ShowDashboard.do";
		document.forms[0].requestFrom.value="MyQueries"; 

		document.forms[0].action = forwardTo;
		document.forms[0].submit(); 
	}
	function displayValidationMessage(text)
	{		
		
       if(text == "DefineView")
		{
		  waitCursor();
		 var isworkflow=document.getElementById("isWorkflow");
		 if(isworkflow!=null)
		    document.forms['categorySearchForm'].action='DefineSearchResultsView.do?isWorkflow='+isworkflow.value;
		   else
		    document.forms['categorySearchForm'].action='DefineSearchResultsView.do';
		  document.forms['categorySearchForm'].submit();
		  hideCursor();		
		}
        else
	  {
		  if (text == "save" || text == 'saveDefineView')
		 {
			  saveClientQueryToServer(text);
		 }
		  else
		 {
			if (text == "search")
			{
				hideHourGlass();
				//showValidationMessages("We are searching for your query. Please wait...");
				viewSearchResults();
			}			
			else 
			{
				if(text != "")
				{		
					hideHourGlass();
					showValidationMessages(text);
				}		
			}
		 }
	  }
	}

	var myWindow;
	function showWaitPage()
	{			
		var popupContent = "<table width='400' height='200' border='0' cellpadding='1' cellspacing='0' bgcolor='#CCCCCC'> <tr><td><table width='100%' height='200' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'><tr><td height='85' align='center' valign='top'><img src='images/advancequery/loading_bg.jpg' alt='Query Search' width='400' height='85' /></td></tr>"+
		"<tr>"+
        "<td align='center' valign='top'><img src='images/advancequery/loading.gif' alt='Loading...' width='50' height='50' vspace='5' /></td>"+
      "</tr>"+
    "</table></td></tr></table>";
		myWindow=window.open('','','left=400,top=400,width=420,height=230,modal=yes');
		myWindow.document.write(popupContent);
		myWindow.focus();
	}

	function closeWaitPage()
	{
		if ((myWindow != null) && (!myWindow.closed))
		{
			myWindow.close();
		}
	}
	 // changed the view port size for VI (added by amit_doshi)
	    var viewportwidth;
		var viewportheight;
	function openPermissibleValuesConfigWindow(componentId,entityName,entityId,componentIdOfID)
	{
	  
	   viEntityName=entityName;
	   compId= componentId;
	   compIdOfID=componentIdOfID;
	   getConceptValues();
	   editVocabURN="NULL";
	   if(conceptCodes.length>0)
	   {
			vocabCodeDetail=conceptCodes[0].split("ID_DEL");
			editVocabURN=vocabCodeDetail[0];
	   }
	  /* var width=(screen.width * 90 )/100;
	   var height=( screen.height * 68)/100;*/
	   
	  
 
			 // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
			 
			 if (typeof window.innerWidth != 'undefined')
			 {
			      viewportwidth = window.innerWidth,
			      viewportheight = window.innerHeight
			 }
			 
			// IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)

			 else if (typeof document.documentElement != 'undefined'
			     && typeof document.documentElement.clientWidth !=
			     'undefined' && document.documentElement.clientWidth != 0)
			 {
			       viewportwidth = document.documentElement.clientWidth,
			       viewportheight = document.documentElement.clientHeight
			 }
			 
			 // older versions of IE
			 
			 else
			 {
			       viewportwidth = document.getElementsByTagName('body')[0].clientWidth,
			       viewportheight = document.getElementsByTagName('body')[0].clientHeight
			 }
			  var width=(viewportwidth * 94 )/100;
	          var height=( viewportheight * 80)/100;
			  if(viewportheight<768)
					height=( viewportheight * 86)/100;
				
	   pvwindow=dhtmlmodal.open('Search Permissible Values', 'iframe', 'LoadingVocabularies.do?entityId='+entityId,'Search Permissible Values for \"'+entityName+'\"', 'width='+width+' height='+height+',center=1,resize=0,scrolling=1');
	}
	/** if Concept already selected and again VI pop is open then
	 * need to open VI pop up in edit mode 
	 * @return
	 */
	function  getConceptValues()
	{
		conceptCodes=new Array();
		var componentId=compId + "_enumeratedvaluescombobox";
		var listboxName = document.getElementById(componentId);
		for(i=0 ; i < listboxName.options.length ; i++)
			{
				conceptCodes[i]=listboxName.options[i].id.trim();
			}
	};
	function getValueFromChild(pvConceptCodeList,pvNameList)
	{
			var componentId=compId + "_enumeratedvaluescombobox";
			var componentIdOfID=compIdOfID + "_textBox";
			var listboxName = document.getElementById(componentId);
			//set the concept code to the ID attribute
			var permValuesWithCode=pvConceptCodeList.split("D#C");
			var permValuesNames = pvNameList.split("D#N");
			var medConceptCodeList=new Array();
			var j=0;
			var pvValueList="";
			var listboxLength = 1;
			if(permValuesNames.length<=5)
		    {
			  if(permValuesNames.length <= 1)
			 {
			   listboxLength = 1;   
			 }
			 else
			 {
			   listboxLength = permValuesNames.length-1;
			 }
			}
					
			else
		    { listboxLength = 5;}
            listboxName.size = listboxLength;
            
			if(listboxName.size ==1)
            {
            	listboxName.multiple = false;
            }else{
			
			   listboxName.multiple = true;
			}


			for(i=0;i<permValuesWithCode.length;i++)
			{
				if(permValuesWithCode[i]!="")
				{
						var urnAndCode=permValuesWithCode[i].split("ID_DEL");
						medConceptCodeList[j]=urnAndCode[1];  //concept code
						j++;
				}
			}
			// clear list box
			for( k=0 ; k < listboxName.options.length ; k++)
			{
				listboxName.options[k] = null;
			}
			var index = 0;
			listboxName.options.length=0; //bug fixed : 11418
		
			for( l=0;l < permValuesNames.length-1;l++)
			{
				
				var urnAndCode=permValuesWithCode[l].split("ID_DEL");
				
				optionID=urnAndCode[0]+"ID_DEL"+urnAndCode[1]+"ID_DEL"+permValuesNames[index];
				listboxName.options[index] = new Option(permValuesNames[index], optionID,true, true); 
				listboxName.options[index].title =permValuesNames[index];
				listboxName.options[index].id = optionID;
				listboxName.options[index].value= optionID;
				index++;
			}
			medConceptCodeList=medConceptCodeList.unique();
			document.getElementById(componentIdOfID).value = medConceptCodeList;
	}	
	
			//returns the unique elements of the array. because we need to made predicate of query for MED concept code.
			  Array.prototype.unique = function () {
			var r = new Array();
			o:for(var i = 0, n = this.length; i < n; i++)
			{
				for(var x = 0, y = r.length; x < y; x++)
				{
					if(r[x]==this[i])
					{
						continue o;
					}
				}
				r[r.length] = this[i];
			}
			return r;
			}
	function changeId(componentId,compIdofID)
	{
		var nameComp = componentId + "_enumeratedvaluescombobox";
		var idtext = compIdofID + "_textBox";
		var listboxName = document.getElementById(nameComp);
		var idTextBox = document.getElementById(idtext);
		idTextBox.value = "";
		for(i=0 ; i < listboxName.options.length ; i++)
		{
			if(listboxName.options[i].selected == true)
			{
				var listBoxId=listboxName.options[i].id.split("ID_DEL"); // need to set only the ids of the med concept 
				// URN + Concept Code+ Concept Name
				idTextBox.value = idTextBox.value +listBoxId[1] +",";
			}
		}
		 idTextBox.value = idTextBox.value.substring(0,idTextBox.value.lastIndexOf(','));
	}
	/** Added by Amit Doshi **/
	String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
	}

	function checkForEmptyText(textString,message)
	{
		//string should not contains the space only
		textString=textString.trim(); 
		if(textString.length>0)
		{
				return true;
		}
		alert(message);
		return false;
	}
 
	function showAttributes1(element)
	{
		var isChecked=element.checked;
		//Get the queryString for parameterized attributes
		var queryId= document.getElementById("queryId").value; 
       
        //produceSavedQuery(queryId,"showAll");
		submitShowAll();

	}
	
	function showAttributes(element)
	{
		var isChecked=element.checked;
		//Get the queryString for parameterized attributes
		var queryId= document.getElementById("queryId").value; 
       
        produceSavedQuery(queryId,"showAll");

	}

	function showAttributesHandler(response)
	{
		/*var htmlArray = response.split('####');
		var strmsg =""+htmlArray[0]; 
		if(strmsg == "errorMessage")
		{
		  var isChecked = document.getElementById('showAll').checked;
           document.getElementById('showAll').checked = !isChecked;
		   document.getElementById("Error_msg").innerHTML = htmlArray[1];
		   self.scrollTo(0,0); 
    	}
		else
		{	*/
		  var parameterListDiv = document.getElementById("parameterlist");
		  parameterListDiv.innerHTML = response;//htmlArray[1];
		//}
	}
	function getAllCategories(url)
	{
		var request = newXMLHTTPReq();			
		var actionURL;
		var handlerFunction = getReadyStateHandler(request,onCategoryResponse,true);	
		request.onreadystatechange = handlerFunction;	
		request.open("POST",url,true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);		
	}
	
	function searchEntity(url)
	{
		var request = newXMLHTTPReq();			
		var actionURL;
		//var handlerFunction = getReadyStateHandler(request,onCategoryResponse,true);	
		//request.onreadystatechange = handlerFunction;	
		request.open("POST",url,true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);		
	}
	
	function onCategoryResponse(text)
	{
		var element = document.getElementById('categorySet');
		if(text.indexOf("No result found") != -1)
		{
			element.innerHTML =text;
		} 
		else
		{
			var listOfEntities = text.split("!&&!");
			var length = listOfEntities.length;
			var row ='<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1">';
			for(i=0; i<listOfEntities.length; i++)
			{
				var e = listOfEntities[i];		
				var nameIdDescription = e.split("|");		
				var name = nameIdDescription[0];
				var id = nameIdDescription[1];				
				var description = nameIdDescription[2];
				var functionCall = "retriveCategoryInformation('loadDefineSearchRules.do','categorySearchForm','"+id+"')";		
				var entityName = "<font color=#0000CC>"+name +"</font>";
				//var tooltipFunction = "Tip('"+description+"', WIDTH, 200)";				
				row = row+'<tr><td style="padding-left:10px;" ><a  class="bluelink"  href="javascript:'+functionCall+'">' +name+ '</a></td></tr>';
			}			
			row = row+'</table>';		
			element.innerHTML =row;
			
			/*if (key != null && key == 13 && listOfEntities.length == 2)
			{
				var e = listOfEntities[1];	
				var nameIdDescription = e.split("|");	
				var id = nameIdDescription[1];		
				retriveEntityInformation('loadDefineSearchRules.do','categorySearchForm', id, textField, attributeChecked, permissibleValuesChecked);	
			}*/
		}
		hideCursor();
	}
	function retriveCategoryInformation(url,nameOfFormToPost,entityName) 
	{	
		var pageOf= document.getElementById("pageOf").value;
		var request = newXMLHTTPReq();			
		var actionURL;
		var handlerFunction = getReadyStateHandler(request,showEntityInformation,true);	
		request.onreadystatechange = handlerFunction;	
		actionURL = "entityName=" + entityName + "&pageOf="+pageOf+"&category="+'category';				
		request.open("POST",url,true);	
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		request.send(actionURL);		
	} 
	function clearDag()
	{
		callFlexMethod();
		interfaceObj.clearDag();
	}
	
		function validateInput(obj, event)
	{
		var textValue  = obj.value;
		if(event.shiftKey==true)
		{
		 var nextKeyPressedKeyCode;
		 if(window.event)
		 {

			nextKeyPressedKeyCode = window.event.keyCode; //IE
		 }
		else
        {

			nextKeyPressedKeyCode = event.which; //firefox  
		}
	//if shift key is pressed then, this is range for capital letters
		if(!(nextKeyPressedKeyCode >= 65 && nextKeyPressedKeyCode <=90))
		{
			alert("Special characters are not allowed");
			var temp = textValue;
			temp =  temp.replace(/[^a-zA-Z 0-9]+/g,'');
			obj.value = temp;
        }
     
	}
	else
	{
		//This is the case when shift key is pressed and released very quickly
		var keycode ;
		 if(window.event)
		 {

			keycode = window.event.keyCode; //IE
		 }
		else
        {

			keycode = event.which; //firefox  
		}
		var splChar= new Array(',','!','@','#','$','%','^','&','*','(',')','_','+','=','|','{','[',']',',',':',';','?','<','>','~','`','*','"','\\'); 
		if(keycode!=13 && keycode!=16)
		{
			for(var i=0; i < splChar.length; i++)
			{
				if(textValue.indexOf( splChar[i] ) != -1)
				{
					alert("Special Characters are not allowed"); 
					obj.value = textValue.replace(/[^a-zA-Z 0-9.-/]+/g,'');
							
					break;
				}
			}
		}
    }
}

	function validateAndSaveQuery(queryId)
{
	var queryTitle = document.getElementById("title").value;
	if(queryTitle=="")
	{
	   document.getElementById("Error_msg").innerHTML = "Query title is mandatory.";
       self.scrollTo(0,0); 
	   return;
	 }
	var request = newXMLHTTPReq();			
	var handlerFunction = getReadyStateHandler(request,queryTitleHandler,true);	
	request.onreadystatechange = handlerFunction;			
	var actionURL = "";		
			
	var url = "ValidateQueryTitle.do?queryId="+queryId+"&queryTitle="+encodeURIComponent(queryTitle);
			 
			request.open("POST",url,true);	
			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			request.send(actionURL);	
		
	}
	
	function queryTitleHandler(text)
	{
		if(text!="")
		{
			self.scrollTo(0,0);
			//show validation message
			document.getElementById("Error_msg").innerHTML = text;
		    return;
		}
		showHourGlass();
		var queryId= document.getElementById("queryId").value;
		produceSavedQuery(queryId,"save");  
	}
