<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>


<ps:form useHiddenProps="true"
	id="snapshotParameterMaintFormId_${_pageRef}"
	namespace="/path/snapshotParameter">
	<table class="headerPortionContent ui-widget-content" width="100%">
		<tr>
			<td width="15%" nowrap="nowrap" align="left">
				<ps:label key="snapshots.enableFileGn" />
			</td>
			<td width="5%" nowrap="nowrap">
				<ps:checkbox name="btrControlCO.sitcomEnableYn"
					id="snEnDis_${_pageRef}"
					parameter="btrControlCO.sitcomEnableYn:snEnDis_${_pageRef}"
					onchange="_showProgressBar(true)"
					dependency="snEnDis_${_pageRef}:btrControlCO.sitcomEnableYn,sitcomTxt_${_pageRef}:btrControlCO.btrControlVO.SITCOM_TEXT"
					dependencySrc="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotParameterMaintAction_snEnFileGenDependency"
					afterDepEvent="adjustReadability();_showProgressBar(false)">
				</ps:checkbox>
			</td>
			<td width="5%" nowrap="nowrap" align="left">
				<ps:label key='reporting.label' />
			</td>
			<td width="15%" nowrap="nowrap" align="left">
				<ps:textfield size="20" name="btrControlCO.btrControlVO.SITCOM_TEXT" maxlength="10"
					id="sitcomTxt_${_pageRef}" onchange="markChanged()"></ps:textfield>
			</td>
			<td></td>
		</tr>
	</table>
	<ps:hidden name="updates" id="updateSnParameter_${_pageRef}"></ps:hidden>
</ps:form>


<ps:set name="errorPathValidation_var" 		value="%{getEscText('reporting.pathValidation')}"/>
<ps:set name="enteredPathCorrect_var" 		value="%{getEscText('template.enteredLocation')}"/>

<script type="text/javascript">
	var errorPathValidation 			= '<ps:property value="errorPathValidation_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var enteredPathCorrect 				= '<ps:property value="enteredPathCorrect_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(
		function() {
			$.struts2_jquery.require("SnapshotParameterMaint.js", null,
					jQuery.contextPath + "/path/js/reporting/ftr/snapshots/");
			$("#snapshotParameterMaintFormId_" + _pageRef).processAfterValid(
					"snapshotParameterMaint_processSubmit", {});
		});
		
		function adjustReadability()
		{
			var srcURL=jQuery.contextPath + "/path/snapshotParameter/SnapshotParameterMaintAction_reloadDivGrid.action";
			var params={};
			params["_pageRef"]      =_pageRef;
			params["btrControlCO.sitcomEnableYn"] = $("#visibilityParam_"+_pageRef).val();
			$("#snapshotGrid_"+_pageRef).load(srcURL, params, function()
			 {
				$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setGridParam',
					{
						url :jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_loadSnapshotParameterGrid?_pageRef="+_pageRef
					}).trigger("reloadGrid");
			 });		
		}
		
</script>