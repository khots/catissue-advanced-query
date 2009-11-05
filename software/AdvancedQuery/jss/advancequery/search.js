
function enableEnterKey(e)
{
     var key;     
     if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox     
//setStatusButton();
	if(key==13)
	{
		searchQuery();
	}
	else
	{
		  return (key != 13);
	}
    
}
/*function keyup()
{
      
	setFilter();

		  return true;
	
}*/
function setsearchText()
{
	if(document.getElementById('queryNameLike').value=="")
	{
		document.getElementById('queryNameLike').value="Enter search term";
		disablRemoveFilter();
	}
	/*else
	{
		enableButtons();
	}*/
}
function enableButtons()
{

	var buttonStatus=document.getElementById("filterimg");
	if(buttonStatus!=null)

		buttonStatus.innerHTML='<a href="javascript:removeFilter()">'
		+'<img src="images/advancequery/filter-delete.png" height="20" border="0" align="right" valign="top" id="resultFilter" name="resultFilter"></a>';
		addRemoveFilterToolTip();


}
function enableRemoveFilter()
{

	var buttonStatus=document.getElementById("removeFilter");
	if(buttonStatus!=null)

		buttonStatus.innerHTML='<a href="javascript:removeFilter()"><img src="images/advancequery/filter-delete.png" height="20" border="0" align="right" valign="top" id="resultFilter" name="resultFilter"></a>';
		addRemoveFilterToolTip();


}
function disablRemoveFilter()
{

		var buttonStatus=document.getElementById("removeFilter");

		buttonStatus.innerHTML='<img src="images/advancequery/filter-delete-disabled.png"  width="23" height="20" border="0" align="right" valign="top" id="resultFilter" name="resultFilter">';
		addRemoveFilterToolTip();
	

}
function addRemoveFilterToolTip()
{
  var x = document.getElementById("resultFilter");
    if(x!=null && x!=undefined)
	{
	  x.onmouseover=function chnageToolTip(x){ Tip('Remove filter'); };
	}

}
/*
function defaultdisableButtons()
{
	alert("1");
	var searchString=documnet.getElementById('searchString');
	if(searchString==null ||searchString=="")
	{
		disableButtons();
	}
	else
	{
		 enableButtons();
	}

}
*/
function setFilter()
{
	var queryNameLike=escape(document.getElementById('queryNameLike').value);

	if(queryNameLike == null || queryNameLike =="")
	{
		disablRemoveFilter();
	}
	else
	{
		enableRemoveFilter();
	}
}
function setTextOnFocus()
{
	if(document.getElementById('queryNameLike').value==""||
		document.getElementById('queryNameLike').value=="Enter search term")
	{
		document.getElementById('queryNameLike').value="";
	}

}
function defaultdisableButtons()
{

	var searchString=document.getElementById('searchString').value;
	if(searchString==null || searchString=="" || searchString=="Enter search term")
	{
		disablRemoveFilter();
	}
	else
	{
		 enableRemoveFilter();
	}

}
/*remove filter  and set the origianl List */
function removeFilter()
{
	var filterimg=document.getElementById("filterimg");
	if(filterimg!=null)

	filterimg.innerHTML='<a href="javascript:addfilter()"><img src="images/advancequery/filter.png"  height="20" border="0" align="right" valign="top" id="filter" name="filter"></a>';
	document.getElementById('queryNameLike').value="";
	 searchQuery();


}
function addfilter()
{
	setTextOnFocus();
	searchQuery();
}
