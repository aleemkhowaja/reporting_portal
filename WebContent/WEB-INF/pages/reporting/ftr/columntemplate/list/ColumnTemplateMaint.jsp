<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<!-- created by zahi, column template, 27/08/2011  -->
<!-- check templateList.jsp, TemplateMaintReport.jsp -->

<ps:set name="edit_key_var" 	value="%{getEscText('colTemplate.edit_key')}"/>
<ps:set name="add_key_var" 		value="%{getEscText('colTemplate.add_key')}"/>

<html>
<head>
<title>Insert title here</title>
</head>
<script type="text/javascript">
var edit_key 		= '<ps:property value="edit_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var add_key 		= '<ps:property value="add_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

function editColumns(sel_row_index)
{
	var colCode = $("#templColGrid").jqGrid('getCell', sel_row_index, 'TEMPLATE_CODE');
	var lineNbr = $("#templColGrid").jqGrid('getCell', sel_row_index, 'LINE_NBR');
	
	var mySrc="${pageContext.request.contextPath}/path/colTemplateMaintReport/editColumnTemplate.action?mode=edit&newCode="+colCode + "&lineNbr=" + lineNbr;
	var dialogOptions = {title: edit_key, autoOpen: false, height:600, width:500 ,modal: true};
	 /* $('#lineDialog').load(mySrc);
	  $('#lineDialog').dialog(dialogOptions);
	  $('#lineDialog').dialog('open');*/
	 
	    $.get(mySrc, {} , function( param )
		 	{
	    	  window.setTimeout($('#colDialog').html(param) ,300);
	    	  $('#colDialog').html(param) ;
	    	  $('#colDialog').dialog(dialogOptions);
	    	  //$('#colDialog').val("onCloseTopics","dialogclosetopic");
			  $('#colDialog').dialog('open');
			},"html");
}
function addTemplateCols()
{
	var colCode = $("#NEW_TEMPL_CODE").val();
	
	if(colCode=="0" || colCode=="" )
	{
		alert("please enter a valid template code")
		return;
	}
	//$("#templColGrid").jqGrid('getCell', sel_row_index, 'TEMPLATE_CODE');
	//var lineNbr = $("#templColGrid").jqGrid('getCell', sel_row_index, 'LINE_NBR');
	var mySrc="${pageContext.request.contextPath}/path/colTemplateMaintReport/addColumnTemplate.action?mode=add&newCode="+colCode;
	var dialogOptions = {title: add_key, autoOpen: false, height:600, width:500 ,modal: true};
	 /* $('#lineDialog').load(mySrc);
	  $('#lineDialog').dialog(dialogOptions);
	  $('#lineDialog').dialog('open');*/
	 
	    $.get(mySrc, {} , function( param )
		 	{
	    	  window.setTimeout($('#colDialog').html(param) ,300);
	    	  $('#colDialog').html(param) ;
	    	  $('#colDialog').dialog(dialogOptions);
	    	  //$('#colDialog').val("onCloseTopics","dialogclosetopic");
			  $('#colDialog').dialog('open');
			},"html");
}
function editTemplateLines()
{	
	$('#testDialog').dialog('open');
}

function lookupDialog()
{	
	$('#lookupDialog').dialog('open');
}

$(document).ready(function() {
	$.subscribe('dialogclosetopic', function(event,ui) {
		var code = $("#NEW_TEMPL_CODE").val();
		$("#templColGrid").jqGrid('setGridParam',{url:"${pageContext.request.contextPath}/path/colTemplateMaintReport/openColumnDet.action?clear=0&newCode="+code,page:1}).trigger("reloadGrid");
	});
	$.subscribe('dialogcloselookuptopic', function(event,ui) {
		var code = $("#NEW_TEMPL_CODE").val();
		$("#templColGrid").jqGrid('setGridParam',{url:"${pageContext.request.contextPath}/path/colTemplateMaintReport/openColumnDet.action?clear=1&newCode="+code,page:1}).trigger("reloadGrid");
	});
});
</script>
<body>
<div id='colTmpltDialog'></div>
<div id="colTmpltList">
<ps:form
	namespace="/path/colTemplateMaintReport">
	<table>
		<tr>
			<td><b><ps:label value="%{getText('reporting.code')}" /></b></td>
			<td><ps:textfield name="NEW_TEMPL_CODE" id="NEW_TEMPL_CODE" 
			></ps:textfield></td>
		</tr>
		<tr>
			<td><b><ps:label value="%{getText('template.title')}" /></b> <ps:textfield
				name="briefName1" id="briefName1"></ps:textfield></td>
			<td></td>
		</tr>
		<tr>
			<td><b><ps:label value="%{getText('template.title')}" /></b> <ps:textfield
				name="briefName2" id="briefName2"></ps:textfield></td>
			<td></td>
		</tr>
	</table>

	<table>
		<tr>
			<td><ps:label value="%{getText('template.paperSize')}" /></td>
			<td></td>
			<td><ps:label value="%{getText('designer.orientation')}" /></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><ps:radio id="rPaperSize" list="#{'9':'A4','8':'A3'}"
				name="PRINT_PAPER_SIZE">

			</ps:radio></td>
			<td></td>
			<td><ps:radio id="rOrientation"
				list="#{'1':'Landscape','2':'Portrait'}"
				name="PRINT_PAPER_ORIENTATION"></ps:radio></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<div id="details"><input type="button"
		onclick="lookupDialog()" /> <ps:url id="gdUrl" var="urlLineTag"
		action="openColumnDet?clear=1" namespace="/path/colTemplateMaintReport">
		<ps:param name="newCode" value="0"></ps:param>
	</ps:url> 
	<psjg:grid id="templColGrid" editurl="%{delLinesTag}"
		editfunc="editColumns" gridModel="gridModel"
		caption="%{getText('line.lineTitle')}" dataType="json"
		href="%{urlLineTag}" pager="true" navigator="true"
		navigatorSearch="false" rowNum="15" rowList="5,10,15,20"
		viewrecords="true" navigatorEdit="true"
		navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
		addfunc="addTemplateCols" onSelectRowTopics="loadGL">

		<psjg:gridColumn name="LINE_NBR" id="LINE_NBR" width="70"
			title="%{getText('line.lineNbr')}" colType="number" editable="false"
			sortable="false" key="true" />
		<psjg:gridColumn name="BRIEF_NAME_ENG" id="BRIEF_NAME_ENG" width="500"
			title="%{getText('reporting.desc1')}" colType="text" editable="false"
			sortable="false" key="true" />
		<psjg:gridColumn name="BRIEF_NAME_ARAB" id="BRIEF_NAME_ARAB"
			width="500" title="%{getText('reporting.desc2')}" colType="text"
			editable="false" sortable="false" />
		<psjg:gridColumn name="COL_TYPE" id="COL_TYPE" width="70"
			title="%{getText('line.printed')}" colType="text" editable="false"
			sortable="false" />
		<psjg:gridColumn name="FROM_DATE" id="FROM_DATE" width="70"
			title="%{getText('line.format')}" colType="text" editable="false"
			sortable="false" />
		<psjg:gridColumn name="TO_DATE" id="TO_DATE" width="70"
			title="%{getText('line.bold')}" colType="text" editable="false"
			sortable="false" />
		<psjg:gridColumn name="COMP" id="COMP" width="70"
			title="%{getText('line.displayZero')}" colType="text"
			editable="false" sortable="false" />
		<psjg:gridColumn name="ALL_CRITERIA" id="ALL_CRITERIA" width="70"
			title="%{getText('line.displayLineTotalZero')}" colType="text"
			hidden="true" />
		<psjg:gridColumn name="COMP" id="COMP"
			title="%{getText('line.round')}" colType="text" hidden="true" />
		<psjg:gridColumn name="FROM_BRANCH" id="FROM_BRANCH"
			title="%{getText('line.addDesc1')}" colType="text" hidden="true" />
		<psjg:gridColumn name="TO_BRANCH" id="TO_BRANCH"
			title="%{getText('line.addDesc2')}" colType="text" hidden="true" />
		<psjg:gridColumn name="FROM_CY" id="FROM_CY" width="90"
			title="%{getText('line.currency')}" colType="text" />
		<psjg:gridColumn name="TO_CY" id="TO_CY"
			title="%{getText('line.currency')}" colType="text" hidden="true" />
		<psjg:gridColumn name="FROM_DIV" id="FROM_DIV" width="70"
			title="%{getText('line.printed')}" colType="number" hidden="true" />
		<psjg:gridColumn name="TO_DIV" id="TO_DIV" width="90"
			title="%{getText('line.currency')}" colType="number" hidden="true" />
		<psjg:gridColumn name="FROM_DEPT" id="FROM_DEPT"
			title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
		<psjg:gridColumn name="TO_DEPT" id="TO_DEPT"
			title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
		<psjg:gridColumn name="FROM_UNIT" id="FROM_UNIT"
			title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
		<psjg:gridColumn name="TO_UNIT" id="TO_UNIT" width="70"
			title="%{getText('line.format')}" colType="text" editable="false"
			sortable="false" hidden="true" />
		<psjg:gridColumn name="BOLD" id="BOLD" width="70"
			title="%{getText('line.format')}" colType="text" editable="false"
			sortable="false" hidden="true" />
		<psjg:gridColumn name="DISP_COL_TOT_ZERO" id="DISP_COL_TOT_ZERO"
			width="70" title="%{getText('line.format')}" colType="text"
			editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="TEMPLATE_CODE" id="TEMPLATE_CODE"
			width="70" title="%{getText('line.format')}" colType="text"
			editable="false" sortable="false" hidden="true" />	

</psjg:grid></div>
	<div class="clearfix">
	<ps:url id="reportUrl"
		action="openLine.action" namespace="/path/colTemplateMaintReport" />
	<div id="colDialog"></div>
	
	<!--<sj:dialog id="colDialog" href="%{reportUrl}" autoOpen="false"
		dataType="html" width="600" height="500" modal="true" title="Test"
		onCloseTopics="dialogclosetopic" />-->
	
	
	<sj:dialog id="lookupDialog" href="%{reportUrl}" autoOpen="false"
		dataType="html" width="600" height="500" modal="true" title="Lookup"
		onCloseTopics="dialogcloselookuptopic" />
	</div>
</ps:form></div>

</body>
<script type="text/javascript">

</script>
</html>