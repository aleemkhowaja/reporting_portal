<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<html>
<head>	
<script type="text/javascript">
function returnSelection()
{
	var valueType = $("input[type='radio']").filter(":checked").val();
	var calculationType = document.getElementById('calcType').options[document.getElementById('calcType').selectedIndex].text;

	if(valueType == 1)
	{
		obj.calculation = calculationType;
		params = {};
		paramStr = JSON.stringify(obj);
		paramStr = "{"+ "obj:"+paramStr + "}";
		params["fieldInfo"] = paramStr;
		params["fieldClass"] = $('#calcType').val();
		var url = '${pageContext.request.contextPath}/path/designer/reportDesign_createVariable.action';
		
		$.ajax({
		  url: url,
		  dataType: 'json',
		  data: params,
		  async: false,
		  success: function(json){
		  	if(obj.calculation)
				varclass = $('#calcType').val();	
			else
				varclass = obj.varClass;

		  	var pattern;
			if( varclass == "java.util.Date" )
				pattern = "pattern=\"dd/MM/yyyy\"";
			else
				pattern = "";
			
		  	zHtml = "<input field=\"true\" name=\""+obj.feName+"\"  type=\"text\" readOnly=\"true\" value=\"$V{"+json["variable"].varName+"}\" class=\""+varclass+"\" "+pattern+" style=\"font-size: 9px;\"/>";
		  }
		});
	}
	$('#variableDialog').dialog('close');
	return false;
}

function closeVarDialog() 
{
	$('#variableDialog').dialog('close');
	return false;
}
</script>
</head>

<body> 
<form>
<table cellspacing="5" width="100%">
<tr>
<td>
	<table align="left" cellspacing="10"> 	
		<tr><td><ps:label value="%{'What kind of value do you want to display?'}"></ps:label></td></tr>
		<tr><td><ps:radio id="valueType" list="valueTypes" listKey="code" listValue="description" name="" value="0"></ps:radio></td></tr>
		<tr><td><ps:select id="calcType" list="calculationTypes" listValue="description" listKey="value"></ps:select></td></tr>
	</table>
</td>
</tr>
<tr>
<td>
	<table align="right">
		<tr>
			<td>
				<psj:submit onclick="return returnSelection();" button="true" type="button">
					<ps:text name="reporting.ok"/>
			    </psj:submit>
			</td>
			<td>
				<psj:submit onclick="return closeVarDialog();" button="true" type="button">
					<ps:text name="reporting.cancel"/>
				</psj:submit>
			</td>
		</tr>
	</table>
</td>
</tr>
</table>
</form>
</body>
</html>