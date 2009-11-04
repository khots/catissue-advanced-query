<!-- dataList and columnList are to be set in the main JSP file -->
<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXGrid.css"/>
<link rel="stylesheet" type="text/css" href="css/catissue_suite.css" />
<style type="text/css" media="screen"> 
#gridbox
 {
	  width:100%;
	  height:100%; 
	  border:0 solid blue;
	  background-color:#d7d7d7;
	  overflow:hidden;
  }
 </style>
<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="java.util.List" %>

<script  src="dhtml_comp/js/dhtmlXCommon.js"></script>
<script  src="dhtml_comp/js/dhtmlXGrid.js"></script>		
<script  src="dhtml_comp/js/dhtmlXGridCell.js"></script>
<script language="JavaScript" type="text/javascript" src="jss/advancequery/javaScript.js"></script>

<%@ page import="java.util.HashMap,java.util.Map,edu.wustl.common.beans.QueryResultObjectData"%>
<script>

	// ------------------------------  FUNCTION SECTION END
	<%
	// Patch ID: SimpleSearchEdit_9 
	// Getting Hypelink map from the request that will be used for further processing.
	// Patch ID: 4270_2
	// getting hyperlinkColumnMap from session instead of request, so that it will persiste when the records per page drop down changed or page number changed.
	// Value for this map will be set in SimpleSearchAction
		Map hyperlinkColumnMap = (Map)session.getAttribute(Constants.HYPERLINK_COLUMN_MAP);
		if (hyperlinkColumnMap==null)
			hyperlinkColumnMap = new HashMap();
		List<String> columnList = (List<String>) session.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);
		if(columnList==null)
			columnList = (List<String>) request.getAttribute(Constants.SPREADSHEET_COLUMN_LIST);

	//	List dataList = (List) request.getAttribute(Constants.PAGINATION_DATA_LIST);
		String[] selectedColumnNames =null;
		if(columnList!=null)
		{
			selectedColumnNames = new String[columnList.size()];
			columnList.toArray(selectedColumnNames);
		}
	%>
	
	//alert("ColList "+" <%= columnList%>");

	var columns = <%="\""%><%int col;%><%for(col=0;col<(columnList.size()-1);col++){%><%=columnList.get(col)%>,<%}%><%=columnList.get(col)%><%="\""%>;
    var colWidth = "<%=edu.wustl.query.util.global.Utility.getColumnWidth(columnList)%>";
    var gridWidth ="<%=edu.wustl.query.util.global.Utility.getGridWidth(columnList)%>"+"px";
        
	/*
	document.write("<hr>myData[0] : "+myData[0]+"<hr>");
	document.write("<hr>columns : "+columns+"<hr>");
	document.write("<hr>colDataTypes : "+colDataTypes+"<hr>");
	document.write("<hr>colWidth : "+colWidth+"<hr>");
	*/
 </script>



<table width="100%" cellpadding="0" cellspacing="0" valign="top" border="0" height="100%">
	<tr valign="top" height="100%" width="100%">
		<td valign="top" height="100%" width="100%">
			<!-- 
				Patch ID: Bug#3090_25
				Description: The height of the gridbox is increased to eliminate the blank space
							 when there are less records to be shown.
							
							 The height of the pixel is reduced, because in lower resolution
							 screen, the div runs out of the frame, and there was no way to scroll
							 down, as we had disabled the scrollbar.
			-->
			<div id='gridbox' height="100%" width="100%">
			</div>
		</td>
	</tr>	
</table>

<script>
	mygrid = new dhtmlXGridObject('gridbox');
	mygrid.setImagePath("dhtml_comp/imgs/");

     	
  // alert("columns"+columns);
	mygrid.setStyle("font-family: Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold;color: #000000;background-color: #E2E2E2; border-left-width: 1px;border-left-color: #CCCCCC; border-top-width: 1px;border-top-color: #CCCCCC; border-bottom-color: #CCCCCC; border-top-width: 1px;border-bottom-color: #CCCCCC; border-right-width: 1px;border-right-color: #CCCCCC; text-align:left;padding-left:10px;");
	mygrid.setHeader(columns);
	mygrid.setEditable("FALSE");
	mygrid.enableAutoHeigth(true);
	mygrid.objBox.style.overflowX = "hidden";
    mygrid.objBox.style.overflowY = "hidden";
	
    if(navigator.userAgent.toString().toLowerCase().indexOf("firefox")!= -1)
    {
       <% if(columnList.size()<=10) { %>
         var colWidthP = "<%=edu.wustl.query.util.global.Utility.getColumnWidthP(columnList)%>";
         mygrid.setInitWidthsP(colWidthP);
         mygrid.entBox.style.width="100%";

        <% } else { %>
           mygrid.setInitWidths(colWidth);
           mygrid.entBox.style.width=gridWidth;
        <%}%>
    }
  else
  {
     mygrid.setInitWidths(colWidth);
     mygrid.entBox.style.width=gridWidth;
  }
    mygrid.enableRowsHover(true,'grid_hover')
	mygrid.enableMultiselect(true);
	//mygrid.chNoState = "0";
	//mygrid.setColAlign("left,left")
	//mygrid.enableMultiselect(true)
	mygrid.setSkin("light");
	mygrid.enableAlterCss("even","uneven");
	mygrid.init();
	//mygrid.splitAt(1);
	/*
	mygrid.loadXML("dhtmlxgrid/grid.xml");
	//clears the dummy data and refreshes the grid.
	mygrid.clearAll();
	*/
	mygrid.setDelimiter('<%=Constants.DHTMLXGRID_DELIMETER%>');
    getNextRecord("database");
	//fix for grid display on IE for first time.
	mygrid.setSizes();

  function getNextRecord(getRecordsFrom)
  {
	
	  var request = newXMLHTTPReq();			
      var handlerFunction = getReadyStateHandler(request,gridAjaxHandler,true);	
      request.onreadystatechange = handlerFunction;
	  var url="ShowGridAjaxHandler.do";
	  var actionURL="getRecordsFrom="+getRecordsFrom;
	  request.open("POST",url,true);	
	  request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	  request.send(actionURL);	 
 }
  
function gridAjaxHandler(response)
{
	
	//alert("response:"+response);
    var getRecordsFrom = "database";
    if(response=="wait")
	{
	  getNextRecord(getRecordsFrom);
	}
	else
	{
	if(response == "NO_RECORDS_FOUND" || response == "TOO_FEW_RECORDS")
	{
	 var tt = document.getElementById("loadingImage");
	 tt.style.display='none';
     var spreadSheetRow = document.getElementById("speradSheetRow");
     speradSheetRow.style.display='none';
 
	 var msgRow =  document.getElementById("MsgRow");
	 msgRow.style.display = 'block' ;
     //alert("spreadsheet:  NO Record!!");
	  return;
	}
	if(response=="NO_MORE_RECORDS")
	 {
       // alert("spreadsheet built!!") ;
		var tt = document.getElementById("loadingImage");
		tt.style.display='none';
		return;		
	 }
	 else
	 {
		var responseArray =  response.split('!####!');

		if(responseArray.length>=2)
		{
            if(responseArray[0]=="NOT_ALL")
            {
            	getRecordsFrom ="action";
            }
	   }


       var jsonResponse = eval('('+ responseArray[1]+')');
	   if(jsonResponse.records!=null)
	  {
		   var num = jsonResponse.records.length; 
		   for(var i=0;i<num;i++)
		  {
			  // var status = jsonResponse.records[i].status.toString();
			   var row = jsonResponse.records[i].row.toString();
                
			  if(row!=null) 
		     {	  
			   if(jsonResponse.records[i].colTypes!=null)  //first record
    		    {
        		    var colDataTypes = jsonResponse.records[i].colTypes;
					   if(colDataTypes!=null)
					{
                     	colDataTypes=colDataTypes.toString();
						mygrid.setColSorting(colDataTypes); 
						while(colDataTypes.indexOf("str") !=-1)
						{
							colDataTypes=colDataTypes.replace(/str/,"ro");
						}
						mygrid.setColTypes(colDataTypes); 
					}
    		    }
    			var next_row_num = mygrid.getRowsNum()+1;
				mygrid.addRow(next_row_num,row,next_row_num);
			 }
		  }
		
	          getNextRecord(getRecordsFrom);		
		   
        }
	 
  }
 }
 }
</script>

<%
	columnList.remove(0);
%>
 
