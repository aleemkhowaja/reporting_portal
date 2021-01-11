<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="repHypUsage_var" 				value="%{getEscText('reporting.repHypUsage')}"/>
<ps:set name="subRepUsage_var" 				value="%{getEscText('reporting.subRepUsage')}"/>
<ps:set name="schedRepUsage_var" 			value="%{getEscText('reporting.schedRepUsage')}"/>
<ps:set name="delRepAccessAlert_var" 		value="%{getEscText('reporting.delRepAccessAlert')}"/>
<ps:set name="repUpdateAccessAlert_var" 	value="%{getEscText('reporting.repUpdateAccessAlert')}"/>
<ps:set name="repNotEditable_var" 		value="%{getEscText('reporting.repNotEditable')}"/>
<ps:set name="repBeingMetadata_var" 		value="%{getEscText('reporting_repBeingMetadata')}"/>

<html>
	<head>

		<script type="text/javascript">
		var repHypUsage 			= '<ps:property value="repHypUsage_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		var subRepUsage 			= '<ps:property value="subRepUsage_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		var schedRepUsage 			= '<ps:property value="schedRepUsage_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		var delRepAccessAlert 		= '<ps:property value="delRepAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		var repUpdateAccessAlert 	= '<ps:property value="repUpdateAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		var repNotEditable			= '<ps:property value="repNotEditable_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		var repBeingMetadata	    = '<ps:property value="repBeingMetadata_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		
		
		
		$(document).ready(function() 
		{
			$.struts2_jquery.require("ReportsList.js", null,jQuery.contextPath + "/path/js/reporting/designer/");	
			$("#reportsGrid").subscribe('emptyRepTrx', function(event, data) {
				$("#repListFrm_" + _pageRef + " #auditTrxNbr_" + _pageRef).val("")
			});
		});
		
		</script>
	</head>

	<body>

		<div id=gridContentRepLst>
			<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
			<ps:url id="urlGrid" action="reportsList_loadGrid"
				namespace="/path/designer" />
			<psjg:grid id="reportsGrid" dataType="json" href="%{urlGrid}"
				gridModel="gridModel" pager="true" rowNum="10" filter="true"
				delfunc="deleteReport" rowList="5,10,15,20" viewrecords="true"
				navigator="true" onErrorTopics="loadError"
				navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true,multipleSearch: true}"
				navigatorAdd="false" navigatorEdit="false" multiselect="true"
				onCompleteTopics="emptyRepTrx">
				<psjg:gridColumn name="REPORT_ID"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="REPORT_ID" colType="number" title="%{getText('reportId')}"
					sortable="true" search="true" width="25" />
				<psjg:gridColumn name="REPORT_NAME"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="REPORT_NAME" colType="text" title="%{getText('reportName')}"
					sortable="true" search="true" />
				<psjg:gridColumn name="PROG_REF"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="PROG_REF" colType="text" title="%{getText('progRef')}"
					sortable="true" search="true" />
				<psjg:gridColumn name="OLD_REPORT_ID"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="OLD_REPORT_ID" colType="number" title="OLD_REPORT_ID"
					sortable="true" search="true" width="25" hidden="true" />
				<psjg:gridColumn name="APP_NAME"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="APP_NAME" colType="text" title="%{getText('reporting.applicationName')}" sortable="true"
					width="25" hidden="false" />
			</psjg:grid>
			<table align="right" cellspacing="10">
				<tr>
					<td>
						<psj:submit button="true" onclick="return checkEditorLoading();"
							type="button">
							<ps:text name="reporting.open" />
						</psj:submit>
					</td>
					<td>
						<psj:submit button="true" onclick="return closeDialog();"
							type="button">
							<ps:text name="reporting.cancel" />
						</psj:submit>
					</td>
				</tr>
			</table>
		</div>


		<ps:form id="repListFrm_${_pageRef}"
			action="reportsList_openReportsList" namespace="/path/designer"
			useHiddenProps="true">
			<ps:hidden name="updates" id="updateRepList_${_pageRef}"></ps:hidden>
			<ps:hidden id="auditPreCall_${_pageRef}" value="repPreAuditCall()"></ps:hidden>
		</ps:form>

	</body>
	<script type="text/javascript">
$("#reportsGrid").jqGrid('filterToolbar', {
	searchOnEnter : true
});
</script>
</html>
