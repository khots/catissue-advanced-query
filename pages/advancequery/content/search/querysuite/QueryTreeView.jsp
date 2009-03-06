<%@ page import="edu.wustl.query.actionForm.CategorySearchForm"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>

<%@ page import="edu.wustl.query.util.global.Constants"%>
<%@ page import="edu.wustl.common.tree.QueryTreeNodeData"%>
<html >
<head>
	<link rel="stylesheet" type="text/css" href="css/advancequery/styleSheet.css" />
	<title>DHTML Tree samples. dhtmlXTree - Action handlers</title>
	<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTree.css">
	<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmXTreeCommon.js"></script>
	<script language="JavaScript" type="text/javascript" src="dhtml_comp/js/dhtmlXTree.js"></script>
	<script language="JavaScript" type="text/javascript" src="jss/advancequery/javaScript.js"></script>
	<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXGrid.css"/>
	<link rel="STYLESHEET" type="text/css" href="dhtml_comp/css/dhtmlXTree.css">
	<script  src="dhtml_comp/jss/dhtmlXCommon.js"></script>
    <script type="text/javascript" src="jss/advancequery/ajax.js"></script> 
<script src="jss/advancequery/queryModule.js"></script>

<script type="text/javascript">
function divHeight(treeNumber, currentNumber)
{
	
	var treeName = "treebox" + currentNumber;
 
	  var divHt = 100 / treeNumber;
	  divHt = divHt + "%";
      document.getElementById("table1").style.height="100%";
      document.getElementById(treeName).style.height=divHt;
     
  
}
//style="position: relative;zoom: 1;"
</script> 
<style>
 #aaa{
    width: 100%;
  }

</style>
</head>
<%
Long trees = (Long)request.getSession().getAttribute("noOfTrees");
int noOfTrees = trees.intValue();

%>
 <script>

//var trees = new Array();
var resultTree;
function initTreeView()
{
	
	
var treeNo = 0;
		<%  
			String rootNodeIdOfFirstTree = "";
			boolean isrootNodeIdOfFirstTree = false;
		for(int i=0;i<noOfTrees;i++) 
		{
			
			String divId = "treebox"+i;
			String treeDataId = Constants.TREE_DATA+"_"+i;
			%>
			divHeight(<%=noOfTrees%>, <%=i%>);
			resultTree=new dhtmlXTreeObject(<%=divId%>,"100%","100%",0);
			resultTree.setImagePath("dhtml_comp/imgs/");
			//resultTree.setStyle("font-family: Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold;color: #000000;background-color: #E2E2E2;");
			resultTree.setOnClickHandler(getTreeNodeChildren);
			<%
			
			Vector treeData = (Vector)request.getAttribute(treeDataId);
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
								if(nodeId.endsWith(Constants.LABEL_TREE_NODE))
								{
									 img = "ic_folder.gif";
								}
								if (parentId.equals("0"))
								{
									nodeColapseCode += "tree.closeAllItems('" + nodeId + "');";
								}
			%>
			resultTree.insertNewChild("<%=parentId%>","<%=nodeId%>","<%=data.getDisplayName()%>",0,"<%=img%>","<%=img%>","<%=img%>","");
			resultTree.setUserData("<%=nodeId%>","<%=nodeId%>","<%=data%>");	
			resultTree.setItemText("<%=nodeId%>","<%=data.getDisplayName()%>","<%=data.getDisplayName()%>");
			<%	
							}
			}	%>
treeNo = treeNo + 1;						
		<%}
	%>	
		
	//Note : automatic node selection of first node is commented
	resultTree.selectItem("<%=rootNodeIdOfFirstTree%>",true);
	
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
	String formAction = Constants.DefineSearchResultsViewJSPAction;
%>
<html:form method="GET" action="<%=formAction%>" > 
<html:hidden property="currentPage" value=""/>
<html:hidden property="stringToCreateQueryObject" value="" />

<table  id="table1" border="0"  cellspacing="0" cellpadding="0" style="width:100%">
	<tr>
		<td valign="top" style="padding-top:10px;">
		<%  for(int i=0;i<noOfTrees;i++) {
			String divId = "treebox"+i;
			%>

				<div class="content_txt" id="<%=divId%>"  style="width:100%;background-color:white;">
				</div>
			<% } %>
		</td>
	</tr>									
</table>
	
</html:form>
</body>
</html> 