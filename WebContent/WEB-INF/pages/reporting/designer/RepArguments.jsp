<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="wrongArgsOrder_var" 		value="%{getEscText('upDown.wrongArgsOrder')}"/>

<html>
<head>
<script type="text/javascript">
 
var wrongArgsOrder 		= '<ps:property value="wrongArgsOrder_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

function closeRepParams()
{

	var argsOrder = $("#argumentsGrid_"+_pageRef).jqGrid('getCol','ARGUMENT_ORDER');//argumentOrder  ARGUMENT_ORDER
	argsOrder.sort();
	
	for(var i=0;i<argsOrder.length;i++)
	{
		if(argsOrder[i]!=i+1)
		{
			_showErrorMsg(wrongArgsOrder,info_msg_title,350,120);
			return;
		}
	}

    $("#paramDialog").dialog('close');
	return false;
}
</script>
</head>

<body> 
<table width="100%">
	<tr>
		<td>
			<jsp:include page="./query/Arguments.jsp"></jsp:include>
		</td>
	</tr>
</table>
<table cellspacing="20" align="center" id="closeParamTbl_${_pageRef}">
	<tr>
		<td valign="bottom">
			<div class="headerPortion">
				<psj:submit button="true" onclick="return closeRepParams()" type="button">
  					<ps:text name="reporting.close"/>
  				</psj:submit>
			</div>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
if(_pageRef=="RD00UD")
{

	document.getElementById("closeParamTbl_"+_pageRef).style.display="none";
}
else
{
	document.getElementById("closeParamTbl_"+_pageRef).style.display="inline";
}

</script>
</html>