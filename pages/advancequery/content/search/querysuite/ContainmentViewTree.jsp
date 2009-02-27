<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.query.util.global.Utility"%>
<%@ page import="edu.wustl.query.util.global.Variables"%>
<%@ page import="edu.wustl.common.tree.QueryTreeNodeData"%>
<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="edu.wustl.common.beans.NameValueBean"%>
<%@ page language="java" isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
</head>
	<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
	<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTree.css">
	<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmXTreeCommon.js"></script>
	<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlXTree.js"></script>
	<script  src="dhtml_comp/jss/dhtmlXCommon.js"></script>
	<script src="jss/advancequery/script.js"></script>
	<%
	String callAction=Constants.CONFIGURE_GRID_VIEW_ACTION;
	CategorySearchForm form = (CategorySearchForm)request.getAttribute("categorySearchForm");
	String currentSelectedNodeInTree = form.getCurrentSelectedNodeInTree();
	String showSelected = "false";
	List selectedColumnNameValueBeanList = form.getSelectedColumnNameValueBeanList();

 //  String xmlString = (String)request.getSession().getAttribute("xmlString"); 

   String fileName = (String)request.getAttribute("fileName");
   System.out.println("File name is :"+fileName);

   %>
	

<html:form method="GET" action="<%=callAction%>">
<html:hidden property="operation" value=""/>
<body>
<table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF"  bordercolorlight="#000000" >
	<tr>
	
		<td valign="top"    style="padding: 5px;">
			<div id="treeBox" style="overflow:auto;height:378;  padding: 5px;" class="biglistbox"></div>
		</td>
		
		   <td align="left" valign="middle" >
			<table width="90" align="center" border="0">
			 <tr><td align="center"><img src="images/advancequery/b_add.gif"  align="absmiddle" hspace="3" property="shiftRight" styleId ="shiftRight" onclick="moveOptionsRight(document.forms[0].columnNames,document.forms[0].selectedColumnNames);"/></td></tr>
			
			<tr><td align="center"><img src="images/advancequery/b_remove.gif"  align="absmiddle" hspace="3" onclick="moveOptionsLeft(document.forms[0].selectedColumnNames, document.forms[0].columnNames);"/></td></tr>
			</table>
		</td>
		
		<td valign="top" >
<!-- Mandar : 434 : for tooltip -->
		<table>
		<tr>
		<td style="padding: 5px;">
		   <div id="emptyList" style="height:388;overflow:hidden;" class="biglistbox"> 
		   <select name="heg" style="height:388;width:250;"  multiple="true">
				<option value="">
			</select></div>	
			</td>
			<td >
		 <div id="elementList" style="OVERFLOW: auto;  height:388; padding:1px; bgcolor:#000000;" class="biglistbox"  onscroll="OnDivScroll();"> 
			 <select  id="lstAttributeNames" name="selectedColumnNames" class="textfield_undefined" size="22" multiple="true" style="" onfocus="OnSelectFocus();">
			  <logic:iterate id="columnNameValue" name="selectedColumnNameValueBeanList">	
				<option value="${columnNameValue.value}"/><span class="content_txt"> <bean:write name="columnNameValue" property="name"/></span>
              </logic:iterate> 
			</select>
		   </div></td>
		   <td>
		     <table>		
			 <tr>
			 <td class="content_txt">Re-order</td>
			 </tr>
				<tr><td align="center"><img src="images/advancequery/ic_up.gif" align="absmiddle"  onclick="moveUp(document.forms[0].selectedColumnNames);"/>  
			</td></tr>
			<tr><td align="center"><img src="images/advancequery/ic_down.gif"  align="absmiddle" onclick="moveDown(document.forms[0].selectedColumnNames);"/></td></tr>
			</table></td>
			</tr>
			</table>
		</td>
		
</tr>
</table>
</body>
</html:form>

<script> 
      
   if( "<%= selectedColumnNameValueBeanList.size() %>" == 0)
   {
      document.getElementById("emptyList").style.display="block";
      document.getElementById("elementList").style.display="none";
   }
   else
   {
      
	  document.getElementById("emptyList").style.display="none";
      document.getElementById("elementList").style.display="block";
   }
</script>

<SCRIPT LANGUAGE="JavaScript">

   function OnDivScroll()
{
   	var lstAttributeNames = document.getElementById("lstAttributeNames");

    //The following two points achieves two things while scrolling
    //a) On horizontal scrolling: To avoid vertical
    //   scroll bar in select box when the size of 
    //   the selectbox is 8 and the count of items
    //   in selectbox is greater than 8.
    //b) On vertical scrolling: To view all the items in selectbox

    //Check if items in selectbox is greater than 8, 
    //if so then making the size of the selectbox to count of
    //items in selectbox,so that vertival scrollbar
    // won't appear in selectbox
    if (lstAttributeNames.options.length > 22)
    {
          
		lstAttributeNames.size=lstAttributeNames.options.length;
    }
    else
    {
        lstAttributeNames.size=22;
    }
}
	
	function OnSelectFocus()
{
    //On focus of Selectbox, making scroll position 
    //of DIV to very left i.e 0 if it is not. The reason behind
    //is, in this scenario we are fixing the size of Selectbox 
    //to 8 and if the size of items in Selecbox is greater than 8 
    //and to implement downarrow key and uparrow key 
    //functionality, the vertical scrollbar in selectbox will
    //be visible if the horizontal scrollbar of DIV is exremely right.
    if (document.getElementById("elementList").scrollLeft != 0)
    {
        document.getElementById("elementList").scrollLeft = 0;
    }

    var lstCollegeNames = document.getElementById('lstAttributeNames');
    //Checks if count of items in Selectbox is greater 
    //than 8, if yes then making the size of the selectbox to 8.
    //So that on pressing of downarrow key or uparrowkey, 
    //the selected item should also scroll up or down as expected.
    if( lstCollegeNames.options.length > 22)
    {
        lstCollegeNames.focus();
        lstCollegeNames.size=22;
    }
  // removeBorder();
}
	
	function addOption(theSel, theText, theValue)
    {
	    var newOpt = new Option(theText, theValue);
	    var selLength = theSel.length;
	    var exists="false";
	    for(var i=0;i<selLength; i++)
	    {
			if(theSel.options[i].value==theValue)// || theSel.options[i].indexof("class") != 0 || theSel.options[i].indexog("root")!=0)
				{
					exists="true";
					break;
				}
	    }
	    if(exists=="false")
		    theSel.options[selLength] = newOpt;
	}
	
   
    function moveOptionsRight(theSelFrom, theSelTo)
    {
		
	   document.getElementById("emptyList").style.display="none";
       document.getElementById("elementList").style.display="block";
	      
		var list=tree.getAllChecked(); 
		var selectedAttrs = list.split(",");
	    var selLength = selectedAttrs.length;
	    var selectedText = new Array();
	    var selectedValues = new Array();
	    var selectedCount = 0;
	    if(list=="")
		{
			alert("Please select column name.");
		} else
		{
			var i;
		  
			// Find the selected Options in reverse order
			// and delete them from the 'from' Select.
			for(i=selLength-1; i>=0; i--)
			{
				var selectedOption = selectedAttrs[i];
				if(selectedOption.indexOf("root") != 0)
				{
					if(selectedOption.indexOf("class") != 0)
					{
						var nodetext = tree.getItemText(selectedOption);
						var parentId = tree.getParentId(selectedOption);
						var parentNodeText = tree.getItemText(parentId);
						if(parentNodeText == "")
						{
						   continue;
						}
						var displaySelectedColumn = parentNodeText + " : " + nodetext;
						selectedText[selectedCount] = displaySelectedColumn;
						selectedValues[selectedCount] = selectedOption;
						//deleteOption(theSelFrom, i);
						selectedCount++;
					}
				}
			}
		      // Add the selected text/values in reverse order.
			// This will add the Options to the 'to' Select
			// in the same order as they were in the 'from' Select.
			
			for(i=selectedCount-1; i>=0; i--)
			{
				addOption(theSelTo, selectedText[i], selectedValues[i]);
			}
		}
      /*  if(document.getElementById("lstAttributeNames").offsetWidth >=260)
		   {
		     e=document.getElementById("elementList");
             e.style.border =1+'px solid';
			}
			else
			{
			  e=document.getElementById("elementList");
             e.style.border =0+'px solid';
			
			} */
	     OnDivScroll();
	}
   	function moveOptionsLeft(theSelFrom, theSelTo)
	{
		var selLength = theSelFrom.length;
		var selectedCount = 0;
		var i;
		for(i=selLength-1; i>=0; i--)
		{
		    if(theSelFrom.options[i].selected)
		    {
		    	selectedCount++;
	  			deleteOption(theSelFrom, i);
    		}
		}
		 if(theSelFrom.length == 0)
		 {
		     document.getElementById("emptyList").style.display="block";
             document.getElementById("elementList").style.display="none";
		 }
		  
		if(selectedCount==0)
			alert("Please select column name.");
		//removeBorder();
	}
	function deleteOption(theSel, theIndex)
    { 
	    var selLength = theSel.length;
	    if(selLength>0)
	    {
			var selItem = theSel.options[theIndex].value;
			tree.setCheck(selItem,false);
			theSel.options[theIndex] = null;
		
	    }
   	}
 	
 
  /* function removeBorder()
	{
	    e=document.getElementById("elementList");
		if(document.getElementById("lstAttributeNames").offsetWidth >=260)
		{
		  e.style.border =0+'px solid';
      	}
	    else
		{
		  e.style.border =0+'px solid';
		}
	}*/
	
	function moveUpAllSelected(theSelFrom)
	{
		var selLength = theSelFrom.length;
		var selectedCount = 0;
		var i;
		for(i=selLength-1; i>=0; i--)
		{
		    if(theSelFrom.options[i].selected)
		    {
		    	selectedCount++;
				moveUpOneByOne(theSelFrom,i);
			}
		}
	}
	function moveUpOneByOne(obj,index)
	{
	  var currernt;
	  var reverse;
	  var currerntValue;
	  var reverseValue;
	  //obj.options[obj.options.selectedIndex].
	  if(index > 0)
	  {
	    current = obj.options[index].text;
	    currentValue = obj.options[index].value;
	    reverse = obj.options[index-1].text;
	    reverseValue = obj.options[index-1].value;
	    obj.options[index].text = reverse;
	    obj.options[index].value = reverseValue;
	    obj.options[index-1].text = current;
	    obj.options[index-1].value = currentValue;
	    self.focus();
	    index--;
	  }
	}
    function typeChange(namesArray,valuesArray)
    { 
	    var columnsList = "columnNames";
	    ele = document.getElementById(columnsList);
	    //To Clear the Combo Box
	    ele.options.length = 0;
				
	    //ele.options[0] = new Option('-- Select --','-1');
	    var j=0;
	    //Populating the corresponding Combo Box
	    for(i=0;i<namesArray.length;i++)
	    {
	    	ele.options[j++] = new Option(namesArray[i],valuesArray[i]);
	    }
    }


	function moveUp(obj)
	{
	  var currernt;
	  var reverse;
	  var currerntValue;
	  var reverseValue;
	  
	  if(obj.options[obj.options.selectedIndex].index > 0)
	  {
	    current = obj.options[obj.options.selectedIndex].text;
	    currentValue = obj.options[obj.options.selectedIndex].value;
	    reverse = obj.options[obj.options[obj.options.selectedIndex].index-1].text;
	    reverseValue = obj.options[obj.options[obj.options.selectedIndex].index-1].value;
	    obj.options[obj.options.selectedIndex].text = reverse;
	    obj.options[obj.options.selectedIndex].value = reverseValue;
	    obj.options[obj.options[obj.options.selectedIndex].index-1].text = current;
	    obj.options[obj.options[obj.options.selectedIndex].index-1].value = currentValue;
	    self.focus();
	    obj.options.selectedIndex--;
	  }
	}
	
	function moveDown(obj)
	{
	  var currernt;
	  var reverse;
	  var currerntValue;
	  var reverseValue;
	  if(obj.options[obj.options.selectedIndex].index != obj.length-1)
	  {
	    current = obj.options[obj.options.selectedIndex].text;
	    currentValue = obj.options[obj.options.selectedIndex].value;
	    reverse = obj.options[obj.options[obj.options.selectedIndex].index+1].text;
	    reverseValue = obj.options[obj.options[obj.options.selectedIndex].index+1].value;
	    obj.options[obj.options.selectedIndex].text = reverse;
	    obj.options[obj.options.selectedIndex].value = reverseValue;
	    obj.options[obj.options[obj.options.selectedIndex].index+1].text = current;
	    obj.options[obj.options[obj.options.selectedIndex].index+1].value = currentValue;
	    self.focus();
	    obj.options.selectedIndex++;
	  }
	}	
    function selectOptions(element)
	{
		for(i=0;i<element.length;i++) 
		{
			element.options[i].selected=true;
		}
	}
    function onSubmit(theSelTo,operation)
	{		
		if(operation == 'finish')
		{
			if(theSelTo.length==0)
			{
				alert("We need to add atleast one column to define view");
				return ;
			}
			selectOptions(document.forms[0].selectedColumnNames);
		}
		document.forms[0].operation.value = operation;
		document.forms[0].action =  "ConfigureGridView.do";	
		document.forms[0].submit();		
	}
	
</script>
    <script>
	var tree;	 
function initTreeView()
{
		tree=new dhtmlXTreeObject("treeBox","100%","100%",0);
		tree.setImagePath("dhtml_comp/imgs/");
		tree.setOnClickHandler();	
		tree.enableCheckBoxes(1);
	    tree.enableThreeStateCheckboxes(true);
		tree.loadXML('<%= fileName %>',deleteFile);
      //select previously selected items
 <%
    if(selectedColumnNameValueBeanList!=null)
  {
	for(int i=0;i<selectedColumnNameValueBeanList.size();i++) {
	NameValueBean nameValueBean = (NameValueBean)selectedColumnNameValueBeanList.get(i);
	String name = nameValueBean.getName();
	String value = nameValueBean.getValue();
  %>
	    tree.setCheck("<%=value%>",true);
		tree.openItem("<%=value%>");
<%  
   }//end of for
  }%> //end of if
		
}


function deleteFile()
{
 
  
}

function shiftRight()
{
	var list=tree.getAllChecked(); 
	alert(list);
}
</script>
<script> initTreeView(); </script>