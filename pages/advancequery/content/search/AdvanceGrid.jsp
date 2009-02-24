<!-- dataList and columnList are to be set in the main JSP file -->
<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXGrid.css"/>
<link rel="stylesheet" type="text/css" href="css/catissue_suite.css" />
<style type="text/css" media="screen"> 
  #gridbox
  {
	  width:100%;
	  height:100%; 
	  border:0 red;
	  background-color:#d7d7d7;
	  overflow:hidden;
  }
 </style>
<%@ page import="edu.wustl.query.util.global.Constants"%>

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
			
	%>
	
	var myData = [<%int i;%><%for (i=0;i<(dataList.size()-1);i++){%>
					<%
	// Patch ID: SimpleSearchEdit_10
	// Calling utility method by passing the hyperlink map. 
	// Changing delimeter for the dhtml grid

						List row = (List)dataList.get(i);
					  	int j;
					%>
					<%="\""%><%for (j=0;j < (row.size()-1);j++){%><%=Utility.toNewGridFormatWithHref(row,hyperlinkColumnMap,j)+Constants.DHTMLXGRID_DELIMETER%><%}%><%=Utility.toNewGridFormatWithHref(row,hyperlinkColumnMap,j)%><%="\""%>,<%}%>
					<%
						List row = (List)dataList.get(i);
					  	int j;
					%>
					<%="\""%><%for (j=0;j < (row.size()-1);j++){%><%=Utility.toNewGridFormatWithHref(row,hyperlinkColumnMap,j)+Constants.DHTMLXGRID_DELIMETER%><%}%><%=Utility.toNewGridFormatWithHref(row,hyperlinkColumnMap,j)%><%="\""%>
				];

	var columns = <%="\""%><%int col;%><%for(col=0;col<(columnList.size()-1);col++){%><%=columnList.get(col)%>,<%}%><%=columnList.get(col)%><%="\""%>;
    var colWidth = "<%=Utility.getColumnWidth(columnList)%>";
	var colTypes = <%="\""%><%=Variables.prepareColTypes(dataList,false)%><%="\""%>;
	var colDataTypes = colTypes;
	
	while(colDataTypes.indexOf("str") !=-1)
	{
		colDataTypes=colDataTypes.replace(/str/,"ro");
	}
	
	/*
	document.write("<hr>myData[0] : "+myData[0]+"<hr>");
	document.write("<hr>columns : "+columns+"<hr>");
	document.write("<hr>colDataTypes : "+colDataTypes+"<hr>");
	document.write("<hr>colWidth : "+colWidth+"<hr>");
	*/
	
	
		
	
</script>



<table width="100%" valign="top" border="0" height="100%">
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
			
			<div id='gridbox' >
			</div>
		</td>
	</tr>	
</table>

<script>
	mygrid = new dhtmlXGridObject('gridbox');
	mygrid.setImagePath("dhtml_comp/imgs/");

     if(useFunction == "search"){
       mygrid.entBox.style.width="100%";
	 }	
	

	mygrid.setHeader(columns);
	//mygrid.setEditable("FALSE");
	mygrid.enableAutoHeigth(false);
	
   if(navigator.userAgent.toString().toLowerCase().indexOf("firefox")!= -1)
  {
     <% if(columnList.size()<=10) { %>
       var colWidthP = "<%=Utility.getColumnWidthP(columnList)%>";
       mygrid.setInitWidthsP(colWidthP);

      <% } else { %>
         mygrid.setInitWidths(colWidth);
      <%}%>
  }
  else
  {
     mygrid.setInitWidths(colWidth);
  }

	mygrid.setColTypes(colDataTypes);
    mygrid.enableRowsHover(true,'grid_hover')
	mygrid.enableMultiselect(true);
	//mygrid.chNoState = "0";
	//mygrid.setColAlign("left,left")
	mygrid.setColSorting(colTypes);
	//mygrid.enableMultiselect(true)
	mygrid.setSkin("light");
	mygrid.enableAlterCss("even","uneven");

    mygrid.setEditable(true);
	
	mygrid.init();
	//mygrid.splitAt(1);
	/*
	mygrid.loadXML("dhtmlxgrid/grid.xml");
	//clears the dummy data and refreshes the grid.
	mygrid.clearAll();
	*/
	mygrid.setDelimiter('<%=Constants.DHTMLXGRID_DELIMETER%>');

	for(var row=0;row<myData.length;row++)
	{
		data = myData[row];
		mygrid.addRow(row+1,data,row+1);
	}
	
	
	//fix for grid display on IE for first time.
	mygrid.setSizes();
</script>
<%
	columnList.remove(0);
%>