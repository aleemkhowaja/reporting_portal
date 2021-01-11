<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<html>
<script type="text/javascript">
$(document).ready(function () 
		{
			$.subscribe('selectRowFn', function(event,data) 
			{
				rowid = (event["originalEvent"])["id"];
				myObject = $("#colTemplGridDet").jqGrid('getRowData',rowid);
				$("#NEW_TEMPL_CODE").val(myObject["TEMPLATE_CODE"]);

				$("#briefName1").val(myObject["BRIEF_NAME1"]);
				$("#briefName2").val(myObject["BRIEF_NAME2"]);
				$("#PRINT_PAPER_ORIENTATION").val(myObject["PRINT_PAPER_ORIENTATION"]);

				if(myObject["PRINT_PAPER_SIZE"]==9)
					$('input:radio[name=PRINT_PAPER_SIZE]')[0].checked = true;
				else if(myObject["PRINT_PAPER_SIZE"]==8)
					$('input:radio[name=PRINT_PAPER_SIZE]')[1].checked = true;

				if(myObject["PRINT_PAPER_ORIENTATION"]==1)
					$('input:radio[name=PRINT_PAPER_ORIENTATION]')[0].checked = true;
				else if(myObject["PRINT_PAPER_ORIENTATION"]==2)
					$('input:radio[name=PRINT_PAPER_ORIENTATION]')[1].checked = true;
				$("#modeColTempl").val("open");
			});
		}
		);

function openColT()
{
	$('#lookupDialog').dialog('close');
	return false;
}
</script>
<head>
<title>Insert title here</title>
</head>
<body>
<ps:form id="colTmpltFrm">
	<ps:textfield name="LINE_NBR" id="line_number"></ps:textfield>
	<ps:url id="urlGrid" action="loadGrid"
		namespace="/path/colTemplateMaintReport" />
	<psjg:grid id="colTemplGridDet" gridModel="gridModel"
		caption="%{getText('reporting.template')}" dataType="json"
		href="%{urlGrid}" pager="true" navigator="true" navigatorSearch="true"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
		navigatorRefresh="false" viewrecords="true" rowNum="15"
		rowList="5,10,15,20" onSelectRowTopics="selectRowFn" sortable="true">

		<psjg:gridColumn name="TEMPLATE_CODE" index="TEMPLATE_CODE"
			id="TEMPLATE_CODE" width="3" title="%{getText('reporting.code')}"
			colType="number" editable="false" sortable="true" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" />
		<psjg:gridColumn name="BRIEF_NAME1" index="BRIEF_NAME1"
			id="BRIEF_NAME1" width="7" title="%{getText('reporting.desc1')}"
			colType="text" editable="false" sortable="true"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  />
		<psjg:gridColumn name="BRIEF_NAME2" index="BRIEF_NAME2"
			id="BRIEF_NAME2" width="7" title="%{getText('reporting.desc2')}"
			colType="text" editable="false" sortable="true"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  />

		<psjg:gridColumn name="PRINT_PAPER_SIZE" index="PRINT_PAPER_SIZE"
			id="PRINT_PAPER_SIZE" width="7" title="%{getText('reporting.desc2')}"
			colType="text" editable="false" sortable="true"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  />
		<psjg:gridColumn name="PRINT_PAPER_ORIENTATION" index="PRINT_PAPER_ORIENTATION"
			id="PRINT_PAPER_ORIENTATION" width="7" title="%{getText('reporting.desc2')}"
			colType="text" editable="false" sortable="true"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  />
	</psjg:grid>
	<td align="center" bgcolor="">
	<ps:submit id="submitColTemplDetBtn"
		onclick="return openColT();" value="%{getText('reporting.save')}"></ps:submit></td>
	<ps:hidden name="mode" id="modeColTempl"></ps:hidden>

</ps:form>
</body>
</html>