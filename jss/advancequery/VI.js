/* Author amit_doshi */
/* common methods */

/*This methos will delete the null ' ' or undefined values from the array */
function removeElementsFromArray(someArray, filter) {
    var newArray = [];
    for(var index = 0; index < someArray.length; index++) {
        if(filter(someArray[index]) == false) {
            newArray.push(someArray[index]);
        }
    }
    return newArray;
}

// then provide one or more filter functions that will 
// filter out the elements based on some condition:
function isNullOrUndefined(item) {
    return (item == null || typeof(item) == "undefined");
}

// Added by amit need to create this method because on IE we cant create dyanamic element by name and we require name element to checkall and uncheckedall
function createNamedElement(type, name) {
   var element = null;
   // Try the IE way; this fails on standards-compliant browsers
   try {
      element = document.createElement('<'+type+' name="'+name+'">');
   } catch (e) {
   }
   if (!element || element.nodeName != type.toUpperCase()) {
      // Non-IE browser; use canonical method to create named element
      element = document.createElement(type);
      element.name = name;
   }
   return element;
}

/*this method will  check the element is present in the given array or not */
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

/*this method will  delete all the element from  array which are present in deleteObj*/
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
/*this method will  delete  the value from array */
function deleteValueFromArray(value,array)
	{
		
			for(var i=0;i<array.length;i++)
				{
					
					if( array[i]==value)
					{
						delete array[i];
					}
				}
	}
/* This method will check that the given two sring are equlas or not .. and not considering the case of string */	
String.prototype.equalsIgnoreCase=function(arg)
 {               
       return (new String(this.toLowerCase())==(new String(arg)).toLowerCase());
 }
 
  /* method to show hide div*/
function showHide(elementid,imageId)
{
		switchObj = document.getElementById(imageId);
		if (document.getElementById(elementid).style.display == 'none')
		{
			document.getElementById(elementid).style.display = '';
			switchObj.innerHTML = '<img src="images/advancequery/nolines_minus.gif" align="absmiddle"/>';
		} else {
			document.getElementById(elementid).style.display = 'none';
				switchObj.innerHTML = '<img src="images/advancequery/nolines_plus.gif" align="absmiddle"/>';
			
		}
	}
	


function MyReload()
{
		if(navigator.userAgent.indexOf('Safari')!=-1)
		{
		 window.location.reload(true);
		}
} 
function Reload()
{
	if(!parent.refresh)
	{
			MyReload();
			parent.refresh=true; // to solve the problem in safari page is not getting refreshed
	}
	
}
function doNothing()
{

}



