<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>

<ps:set name="checkProgRefAlert_var" 		value="%{getEscText('fcr.checkProgRefAlert')}"/>
<ps:set name="missingAlert_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="existingSched_var" 			value="%{getEscText('reporting.schedRepUsage')}"/>

<script type="text/javascript">
var checkProgRefAlert 		= '<ps:property value="checkProgRefAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingAlert 			= '<ps:property value="missingAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var existingSched			= '<ps:property value="existingSched_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(
		function() {
			$("#fcrTempCodeStr_" + _pageRef).attr('readonly', true);
			$("#frcColTempCodeStr_" + _pageRef).attr('readonly', true);
			$("#parentRefStr_" + _pageRef).attr('readonly', true);

			$("#fcrGridId_" + _pageRef).subscribe(
					'emptyFrcHidden',
					function(event, data) {
						$(
								"#fcrDetFormId_" + _pageRef + " #auditTrxNbr_"
										+ _pageRef).val("");
						$(
								"#fcrDetFormId_" + _pageRef + " #auditObj_"
										+ _pageRef).val("");
					});
		});
</script>


<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<ps:url id="urlFcrTag" action="fcr_loadFcrList" namespace="/path/fcrRep" />
<psjg:grid id="fcrGridId_${_pageRef}" gridModel="gridModel"
	dataType="json" href="%{urlFcrTag}" pager="true" navigator="true"
	navigatorSearch="true" viewrecords="true"
	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
	navigatorEdit="false" navigatorAdd="false" navigatorDelete="true"
	caption=" " addfunc="emptyFcrForm" delfunc="deleteFcr"
	ondblclick="rep_fcr_selectRowFn()" navigatorRefresh="true" hiddengrid="true"
	rowNum="5" rowList="5,10,15,20" sortable="true"
	onCompleteTopics="emptyFrcHidden">
	<psjg:gridColumn name="ftrOptVO.BRIEF_NAME_ENG" id="briefNameEng"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		title="%{getText('reporting.descEng')}" colType="text"
		editable="false" sortable="false" />
	<psjg:gridColumn name="ftrOptVO.BRIEF_NAME_ARAB" id="briefNameAr"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		title="%{getText('reporting.descAr')}" colType="text" editable="false"
		sortable="false" />
	<psjg:gridColumn name="ftrOptVO.PROG_REF" id="progRef"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		title="%{getText('progRef')}" colType="text" editable="false"
		sortable="false" />
	<psjg:gridColumn name="ftrOptVO.DISP_ORDER" id="displOrder"
		searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
		title="%{getText('reporting.order')}" colType="number"
		editable="false" sortable="false" />
	<psjg:gridColumn name="PARENT_REF_STR" id="parRefStr"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		title="%{getText('reporting.parentRef')}" colType="text" />
	<psjg:gridColumn name="TMPLT_CODE_STR" id="templCodeStr"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		title="%{getText('reporting.template')}" colType="text" />
	<psjg:gridColumn name="COLUMN_CODE_STR" id="columnCodeStr"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		title="%{getText('fcr.colCode')}" colType="text" />

	<psjg:gridColumn name="ftrOptVO.CODE" id="code"
		title="%{getText('reporting.code')}" colType="number" editable="false"
		sortable="false" key="true" hidden="true" />
	<psjg:gridColumn name="ftrOptVO.PARENT_REF" id="parentRef"
		title="%{getText('reporting.parentRef')}" colType="text"
		editable="false" sortable="false" hidden="true" />
	<psjg:gridColumn name="ftrOptVO.TMPLT_CODE" id="tmplCode"
		title="%{getText('reporting.template')}" colType="number"
		editable="false" sortable="false" hidden="true" />
	<psjg:gridColumn name="ftrOptVO.COLUMN_CODE" id="colCode"
		title="%{getText('fcr.colCode')}" colType="number" hidden="true" />
	<psjg:gridColumn name="ftrOptVO.APP_NAME" id="appName"
		title="%{getText('fcr.AppName')}" colType="text" hidden="true" />
</psjg:grid>


<div>
	<h1 class="headerPortionContent ui-widget ui-state-default">
		<ps:label value="%{getText('frc.fcrDet')}" />
	</h1>
</div>
<div class="headerPortion" id="fcrDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
	<%@include file="FcrMaint.jsp"%>
</div>

<script type="text/javascript">
$.struts2_jquery.require("Fcr.js", null, jQuery.contextPath
		+ "/path/js/reporting/fcr/");
$("#fcrGridId_" + _pageRef).jqGrid('filterToolbar', {
	searchOnEnter : true
});
$("#gview_fcrGridId_" + _pageRef + " div.ui-jqgrid-titlebar").hide();
</script>
