
//function to update hidden fields as per check box selections.
function updateHiddenFields() {		
	var isChecked = false;
	var checkedRows = mygrid.getCheckedRows(0);
	
	if(checkedRows.length > 0) {
	    isChecked = true;
		var cb = checkedRows.split(",");		
		
		for(i = 0; i < rowCount; i++) {
			var cbvalue = document.getElementById("" + i);
			if(mygrid.cells(i, 0).isChecked()) {
				cbvalue.value = "1";
				cbvalue.disabled = false;
			} else {
				cbvalue.value = "0";
				cbvalue.disabled = true;
			}
		}
	} 		
	return isChecked;
}

function viewSPR(id)
{
	var url = "ViewSurgicalPathologyReport.do?operation=viewSPR&pageOf=gridViewReport&reportId="+id+"&flow=viewReport";
	platform = navigator.platform.toLowerCase();
    if (platform.indexOf("mac") != -1) {
    	NewWindow(url,'name',screen.width,screen.height,'yes');
    } else {
    	NewWindow(url,'name','700','600','yes');
    }
	hideCursor();
}

/************************************ Query Grid **************************************/
var mygrid = new dhtmlXGridObject('gridbox'); ;
var rowCount = 0;
var totalCount = 0;
function initQueryGrid() {	
	
	mygrid.setImagePath("newDhtmlx/imgs/");	
	mygrid.setHeader(gridDataJson.columns.join());
	mygrid.attachHeader(filters);
	mygrid.enableAutoHeigth(false);
	mygrid.setColTypes(colDataTypes);
	mygrid.setInitWidths(colWidth);			 
	mygrid.setSkin("dhx_skyblue");		
	mygrid.init();			
	
	mygrid.enableRowsHover(true, 'grid_hover')
	mygrid.enableMultiselect(true);
	mygrid.setColSorting(colTypes);		
	mygrid.enableAlterCss("even", "uneven");
	mygrid.enablePaging(true, recordPerPage, null, "pagingArea", false);
	mygrid.setPagingSkin("toolbar", "dhx_skyblue")		
	mygrid.setEditable(!checkAllPages);
	
	mygrid.splitAt(1);
	var jsonData = gridDataJson.gridData;
	mygrid.parse(jsonData, "json");
	rowCount = jsonData.rows.length;
	totalCount = jsonData.total_count;
	createHiddenElement();
	window.setTimeout(addRecordPerPageOption, 500)
	
	checkBoxCell = getElementsByClassName('hdrcell');
	checkBoxCell.style.paddingLeft = "0px";
	gridBOxTag.style.width = (gridBOxTag.offsetWidth - 6 ) + "px"
}

var sortIndex;
var sortDirection = "asc";
mygrid.attachEvent("onBeforeSorting",function(ind, type, direction){
	sortIndex = ind;
	sortDirection = direction;
	this.setSortImgState(true, ind, direction);   //set a correct sorting image	
	this.clearAll();  
	return true;   
});

mygrid.attachEvent("onFilterStart", function(ind){
	mygrid.clearAll();	
	return false;
})
	
mygrid.attachEvent("onBeforePageChanged", function(ind, count){
	var option = mygrid.aToolBar.getListOptionSelected('perpagenum').split("_");
	recordPerPage = option[1];
	pageNum = count;
	getGridData();
	return true;
});

function getGridData() {	
	var param = "Data=" + getJsonForFilter() + "&pageNum="+pageNum+"&recordPerPage="+recordPerPage;
	var url = "QueryGridFilter.do";
	var xmlhttp = newXMLHTTPReq();
	
	xmlhttp.onreadystatechange =  function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var json = eval('(' + xmlhttp.responseText + ')');
				setColumn(json.columns)
				mygrid.parse(json.gridData, "json");
				rowCount = json.gridData.rows.length;
				createHiddenElement();
				checkBoxCell = getElementsByClassName('hdrcell');
				checkBoxCell.getElementsByTagName('input')[0].checked = false; 
			}				  		
		}
	xmlhttp.open("POST", url,  true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(param);
}

function getJsonForFilter() {
	var columns = []
	var values = []
	var j = 0;
	for(i = 1; i < mygrid.getColumnsNum(); i++) {
		var value = mygrid.getFilterElement(i).value;
		if(value != "") {
			columns[j] = mygrid.getColumnLabel(i, 0);
			values[j++] = value;				
		}
	}
	var sortColumn = sortIndex ? mygrid.getColumnLabel(sortIndex, 0): "";
	sortDirection = sortDirection == "asc" ? sortDirection: "desc";
	
	return JSON.stringify({"columns": columns, "values": values, "sortColumn": sortColumn ,"sortDir": sortDirection});
}

function createHiddenElement() {
	var cont = document.getElementById("hiddenBox")
	cont.innerHTML  = '';
	for(var row = 0; row < rowCount; row++)
	{						
		var el = document.createElement("input");
		el.type = "hidden";
		el.id = row;
		el.name = "value1(CHK_" + row + ")";
		
		cont.appendChild(el);					
	}	
}

function setColumn(columnList) {
			
	for(i = 1; i < columnList.length; i++) {
		var columnLabel = mygrid.getColumnLabel(i, 0)
		if( columnLabel != columnList[i] ) {
			mygrid.insertColumn(i, columnList[i], 'ro', "100");
		}
	}	
}

function addRecordPerPageOption() {		
	toolbar = mygrid.aToolBar;
	toolbar.setWidth('perpagenum', 130);
	var  opt = [10, 50, 100, 500, 1000];
	
	for(i = 5; i < 35; i += 5) {
		toolbar.removeListOption('perpagenum', 'perpagenum_'+i);
	}
	
	for(i = 0; i < opt.length; i++) {
		toolbar.addListOption('perpagenum', 'perpagenum_'+ opt[i], NaN, "button", opt[i]+" "+ mygrid.i18n.paging.perpage);
	}

	toolbar.setListOptionSelected('perpagenum', 'perpagenum_' + recordPerPage);
	toolbar.addText("blanck_space",10,"");
	toolbar.addText("total_count_txt",11 ," Total Records : "+totalCount);
}

function getElementsByClassName(className) {
	if (document.getElementsByClassName) { 		
		return document.getElementsByClassName('hdrcell')[1]; 
	} else { 		
		var els = (gridBOxTag.getElementsByTagName('td'))[1].childNodes;			
		return els[0];			
	} 
}