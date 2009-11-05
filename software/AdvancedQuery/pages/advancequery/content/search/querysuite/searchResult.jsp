<c:set var="queryNameLike" value='${requestScope.queryNameLike}'/>
<input type="hidden" name="searchString" id="searchString" value='${requestScope.queryNameLike}'/>
<table border="0" width="100%">
<script  src="jss/advancequery/wz_tooltip.js"></script>

<tr>
<td width="33%"><td/>
<td width="33%">


</td>
<td align="right" width="30%" valign="top">
		
		<logic:equal name="queryNameLike"  value="">
			<input type="text"  align="left"  height="20" id="queryNameLike" valign="top"  name="queryNameLike" class="small_txt_grey"
			value="Enter search term"  onfocus="setTextOnFocus()" onKeyDown="return enableEnterKey(event)"
			 onBlur="javascript:setsearchText()" />

		</logic:equal>
		<logic:notEqual name="queryNameLike"  value="">
		<input type="text" align="left"   height="20" id="queryNameLike" valign="top"  name="queryNameLike" class="small_txt_grey"
		value="${requestScope.queryNameLike}"  onfocus="setTextOnFocus()" onKeyDown="return enableEnterKey(event)"  onblur="javascript:setsearchText()" onKeyUp="return keyup(event)" />
		</logic:notEqual>
</td>
<td width="2%" align="right"  valign="top">
	<a href="javascript:addfilter()"><img src="images/advancequery/filter.png"  height="20" border="0" align="right" valign="top" id="filter" name="filter" onMouseOver="Tip('Add filter')"></a>

</td>
<td align="right"  valign="top"  width="2%" height="20" >

						<!--<div id="buttonStatus">

							</div>
							-->
						<div id="removeFilter">

						</div>


</td>

</tr>
</table>