<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>

<%@ page import="edu.wustl.query.util.global.AQConstants"%>
<%@ page import="edu.wustl.common.query.queryobject.impl.QueryTreeNodeData"%>
<html >
<head>
	<link rel="stylesheet" type="text/css" href="css/advQuery/styleSheet.css" />
	<title>DHTML Tree samples. dhtmlXTree - Action handlers</title>
	<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/css/dhtmlxtree.css">
	<script language="JavaScript" type="text/javascript" src="dhtmlx_suite/js/dhtmxcommon.js"></script>
	<script language="JavaScript" type="text/javascript" src="dhtmlx_suite/js/dhtmlxtree.js"></script>
	<script language="JavaScript" type="text/javascript" src="jss/advQuery/javaScript.js"></script>
	<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/css/dhtmlxgrid.css"/>
	<link rel="STYLESHEET" type="text/css" href="dhtmlx_suite/css/dhtmlxtree.css">
	<script  src="dhtmlx_suite/js/dhtmlxcommon.js"></script>
    <script type="text/javascript" src="jss/advQuery/ajax.js"></script>
<script src="jss/advQuery/queryModule.js"></script>

<script type="text/javascript">
function divHeight(treeNumber, currentNumber)
{

	var treeName = "treebox" + currentNumber;
  if(navigator.appName == "Microsoft Internet Explorer")
  {
	  var divHt = 100 / treeNumber;
	  divHt = divHt + "%";
    document.getElementById("table1").style.height="100%";
    document.getElementById("treebox0").style.height=divHt;
      }
  else
  {
	var divHt = ((document.body.clientHeight - 8)/ treeNumber)+'px';
	divHt = divHt + "px";
    document.getElementById("table1").style.height=(document.body.clientHeight - 3)+'px';//"444px";
	document.getElementById(treeName).style.height=(document.body.clientHeight - 8)+'px';;
	}
}
//style="position: relative;zoom: 1;"
</script>

</head>
<%
	Long trees = (Long)request.getSession().getAttribute("noOfTrees");
int noOfTrees = trees.intValue();
%>
 <script>

var trees = new Array();
function initTreeView()
{


var treeNo = 0;
		<%String rootNodeIdOfFirstTree = "";
			boolean isrootNodeIdOfFirstTree = false;
		for(int i=0;i<noOfTrees;i++)
		{

			String divId = "treebox"+i;
			String treeDataId = AQConstants.TREE_DATA+"_"+i;%>
			divHeight(<%=noOfTrees%>, <%=i%>);
			trees[treeNo]=new dhtmlXTreeObject("<%=divId%>","100%","100%",0);
			trees[treeNo].setImagePath("dhtmlx_suite/imgs/");
			trees[treeNo].setOnClickHandler(treeNodeClicked);
			<%List treeData = (List)request.getAttribute(treeDataId);
					if(treeData != null && treeData.size() != 0)
						{
							Iterator itr  = treeData.iterator();
							String nodeColapseCode = "";
							while(itr.hasNext())
							{
								QueryTreeNodeData data = (QueryTreeNodeData) itr.next();
								String parentId = "0";
								if(!data.getParentIdentifier().equals("0"))
								{
									parentId = data.getParentIdentifier().toString();
								}
								String nodeId = data.getIdentifier().toString();
								if(!isrootNodeIdOfFirstTree)
								{
									rootNodeIdOfFirstTree = nodeId;
									isrootNodeIdOfFirstTree = true;
								}
								String img = "results.gif";
								if(nodeId.endsWith(AQConstants.LABEL_TREE_NODE))
								{
									 img = "ic_folder.gif";
								}
								if (parentId.equals("0"))
								{
									nodeColapseCode += "tree.closeAllItems('" + nodeId + "');";
								}%>
			trees[treeNo].insertNewChild("<%=parentId%>","<%=nodeId%>","<%=data.getDisplayName()%>",0,"<%=img%>","<%=img%>","<%=img%>","");
			trees[treeNo].setUserData("<%=nodeId%>","<%=nodeId%>","<%=data%>");
			trees[treeNo].setItemText("<%=nodeId%>","<%=data.getDisplayName()%>","<%=data.getDisplayName()%>");
			<%}
			}%>
treeNo = treeNo + 1;
		<%}%>
		trees[0].selectItem("<%=rootNodeIdOfFirstTree%>",true);

}
</script>
<%
	boolean mac = false;
	        Object os = request.getHeader("user-agent");
	if(os!=null && os.toString().toLowerCase().indexOf("mac")!=-1)
	{
	    mac = true;
	}
	String height = "100%";
	if(mac)
	{
	  height="300";
    }
%>
<body onload="initTreeView()">
<html:errors />
<%
	String formAction = AQConstants.DefineSearchResultsViewJSPAction;
%>
<html:form method="GET" action="<%=formAction%>">
<html:hidden property="currentPage" value=""/>
<html:hidden property="stringToCreateQueryObject" value="" />

<table id="table1" border="0"  cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="width:100%;" bordercolorlight="#000000" class='tbBordersAllbordersBlack'>
	<tr>
		<td valign="top" width="90%" height="100%">
			<%  for(int i=0;i<noOfTrees;i++) {
			String divId = "treebox"+i;
			%>

				<div id="<%=divId%>"  style="width:100%;background-color:white;overflow:auto;">
				</div>
			<% } %>
		</td>
	</tr>
</table>

</html:form>
</body>
</html>