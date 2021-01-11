<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<ps:set name="notAllowed_var" 				value="%{getEscText('upDown.notAllowed')}"/>
<ps:set name="uploadAlert_var" 				value="%{getEscText('upDown.uploadAlert')}"/>
<ps:set name="argProcSaved_var" 			value="%{getEscText('upDown.argProcSaved')}"/>
<ps:set name="missingFeReq_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="nonDeletedReport_var" 		value="%{getEscText('reporting.nonDeletedReport')}"/>
<ps:set name="nonEditableReport_var" 		value="%{getEscText('reporting.nonEditableReport')}"/>
<ps:set name="downloadTitle_var" 			value="%{getEscText('reporting.downloadTitle')}"/>
<ps:set name="importTitle_var" 				value="%{getEscText('reporting.importTitle')}"/>
<ps:set name="selectRowAlert_var" 			value="%{getEscText('reporting.selectRowAlert')}"/>
<ps:set name="nonDownloadableReport_var" 	value="%{getEscText('reporting.nonDownloadableReport')}"/>
<ps:set name="replaceRepByUpload_var" 		value="%{getEscText('upDown.replaceRepByUpload')}"/>
<ps:set name="repUplHypUsage_var" 			value="%{getEscText('reporting.repHypUsage')}"/>
<ps:set name="delHypRepAccessAlert_var" 	value="%{getEscText('reporting.delRepAccessAlert')}"/>

<ps:set name="repHypUpdateAccessAlert_var" 	value="%{getEscText('reporting.repUpdateAccessAlert')}"/>
<ps:set name="nonDelSubRep_var" 			value="%{getEscText('upDown.nonDelSubRep')}"/>
<ps:set name="updateAllAlert_var" 			value="%{getEscText('upDown.upldateAllAlert')}"/>
<ps:set name="empSubRep_var" 				value="%{getEscText('upDown.subRep')}"/>
<ps:set name="empRepClt_var" 				value="%{getEscText('upDown.empRepClt')}"/>
<ps:set name="hasHyperlinkAlert_var" 		value="%{getEscText('reporting.hasHyperlinkAlert')}"/>
<ps:set name="showInDesignerAlert_var" 		value="%{getEscText('upDown.showInDesignerAlert')}"/>
<ps:set name="wrongArgsOrder_var" 			value="%{getEscText('upDown.wrongArgsOrder')}"/>
<ps:set name="checkOptAxsAlert_var" 		value="%{getEscText('upDown.checkOptAxsAlert')}"/>
<ps:set name="schedUplRepUsage_var" 		value="%{getEscText('reporting.schedRepUsage')}"/>
<ps:set name="repSaveAsAccessAlert_var" 	value="%{getEscText('reporting.repSaveAsAccessAlert')}"/>
<ps:set name="repNoHeadAndFoot_var" 		value="%{getEscText('reporting.noHeadAndFoot')}"/>
<ps:set name="repNoHeaders_var" 			value="%{getEscText('reporting.noHeaders')}"/>
<ps:set name="missingColReq_var" 			value="%{getEscText('upDown.imgColMissing')}"/>

<ps:set name="missingExt_var" 				value="%{getEscText('reporting.missingExt')}"/>
<ps:set name="paramsOk_var" 				value="%{getEscText('reporting.ok')}"/>
<ps:set name="paramsCancel_var" 			value="%{getEscText('reporting.cancel')}"/>
<ps:set name="repNotEditable_var" 			value="%{getEscText('reporting.repNotEditable')}"/>
<ps:set name="repCantSaveUnmodifiable_var" 	value="%{getEscText('reporting.repCantSaveUnmodifiable')}"/>
<ps:set name="repUsedInSched_var" 			value="%{getEscText('reporting.repUsedInSched')}"/>
<ps:set name="cltRepeated_var" 				value="%{getEscText('upDown.cltRepeated')}"/>
<ps:set name="fillZipFileAlert_var" 		value="%{getEscText('reporting.fillZipFile')}"/>
<ps:set name="checkWhenNoData_var" 			value="%{getEscText('reporting.checkWhenNoData')}"/>
<ps:set name="replaceConfirm_var" 			value="%{getEscText('reporting.allSchedHypDel')}"/>
<ps:set name="uploadSuccess_var" 			value="%{getEscText('reporting.uploadSuccess')}"/>
<ps:set name="queriesNotFilled_var" 	    value="%{getEscText('reporting.queriesNotFilled')}"/>
<ps:set name="updateVersionedReport_var"	value="%{getEscText('reporting.updateVersionedReport')}"/>
<ps:set name="updateVersionedReport_var"	value="%{getEscText('reporting.updateVersionedReport')}"/>
<ps:set name="repBeingMetadata_var" 		value="%{getEscText('reporting_repBeingMetadata')}"/>

<html>
	<script type="text/javascript">
var notAllowed 				= '<ps:property value="notAllowed_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var uploadAlert 			= '<ps:property value="uploadAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var argProcSaved 			= '<ps:property value="argProcSaved_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingFeReq 			= '<ps:property value="missingFeReq_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var nonDeletedReport 		= '<ps:property value="nonDeletedReport_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var nonEditableReport 		= '<ps:property value="nonEditableReport_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var downloadTitle 			= '<ps:property value="downloadTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var importTitle 			= '<ps:property value="importTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectRowAlert 			= '<ps:property value="selectRowAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var nonDownloadableReport 	= '<ps:property value="nonDownloadableReport_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var replaceRepByUpload 		= '<ps:property value="replaceRepByUpload_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repUplHypUsage 			= '<ps:property value="repUplHypUsage_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var delHypRepAccessAlert 	= '<ps:property value="delHypRepAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var fillZipFileAlert 		= '<ps:property value="fillZipFileAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

var missingExt 				= '<ps:property value="missingExt_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

var repHypUpdateAccessAlert = '<ps:property value="repHypUpdateAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var nonDelSubRep 			= '<ps:property value="nonDelSubRep_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var updateAllAlert 			= '<ps:property value="updateAllAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var empSubRep 				= '<ps:property value="empSubRep_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var empRepClt 				= '<ps:property value="empRepClt_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

var hasHyperlinkAlert 		= '<ps:property value="hasHyperlinkAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var showInDesignerAlert 	= '<ps:property value="showInDesignerAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var wrongArgsOrder 			= '<ps:property value="wrongArgsOrder_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkOptAxsAlert 		= '<ps:property value="checkOptAxsAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var schedUplRepUsage 		= '<ps:property value="schedUplRepUsage_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repSaveAsAccessAlert 	= '<ps:property value="repSaveAsAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repNoHeadAndFoot 		= '<ps:property value="repNoHeadAndFoot_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repNoHeaders 			= '<ps:property value="repNoHeaders_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingColReq 			= '<ps:property value="missingColReq_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var paramsOk 				= '<ps:property value="paramsOk_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var paramsCancel 			= '<ps:property value="paramsCancel_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repNotEditable			= '<ps:property value="repNotEditable_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repCantSaveUnmodifiable	= '<ps:property value="repCantSaveUnmodifiable_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repUsedInSched			= '<ps:property value="repUsedInSched_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var cltRepeated 			= '<ps:property value="cltRepeated_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkWhenNoDataAlert 		= '<ps:property value="checkWhenNoData_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var replaceConfirm			= '<ps:property value="replaceConfirm_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var uploadSuccess			='<ps:property value="uploadSuccess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var queriesNotFilled		='<ps:property value="queriesNotFilled_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var updateVersionedReport   = '<ps:property value="updateVersionedReport_var"  escapeHtml="false" escapeJavaScript="true"/>';
var repBeingMetadata	    ='<ps:property value="repBeingMetadata_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


$(document)
		.ready(
				function() {
				
					$.struts2_jquery.require("UploadDownloadReport.js", null,jQuery.contextPath + "/path/js/reporting/uploadDownloadReports/");				
					UploadDwnReadyFunc();
				});
</script>

	<div class="clearfix">
		<ps:url id="savaAsUrl" namespace="/path/designer"
			action="reportsList_openSaveAsDlg?calledFrom=1&_pageRef=${_pageRef}" />
		<psj:dialog id="saveAsDialog_${_pageRef}" href="%{savaAsUrl}"
			autoOpen="false" dataType="html" modal="true" width="800"
			height="330" title="%{getText('opt.SaveAs')}"
			onCloseTopics="closeSaveAsDlg" />
	</div>

	<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:hidden id="isSyb_${_pageRef}" name="isSyb"></ps:hidden>
	<ps:hidden id="cltRepFlag_${_pageRef}" name="cltRepFlag"></ps:hidden>
	
			
	<ps:collapsgroup id="upDownCollapsGrp_${_pageRef}">
			<ps:collapspanel id="upDownCollaps_${_pageRef}"  key="reports.list_key">			
			<ps:url id="urlGrid" action="reportsList_loadGrid"
				namespace="/path/designer">
				<ps:param name="_pageRef" value="_pageRef"></ps:param>
			</ps:url>
			<psjg:grid id="upDownGrid_${_pageRef}" dataType="json"
				href="%{urlGrid}" gridModel="gridModel" pager="true" rowNum="5"
				delfunc="deleteUpDownReport" addfunc="newUpload"
				rowList="5,10,20,40,60,80,100" viewrecords="true" navigator="true"
				ondblclick="loadReportAndForm()"
				navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true,multipleSearch: true}"
				navigatorAdd="true" navigatorEdit="false"
				onCompleteTopics="emptyUpDownTrx"
				onGridCompleteTopics="addDownloadBtn"
				multiselect="false"
				>
				<psjg:gridColumn name="repIdCheckBox"  id="repIdCheckBox_${_pageRef}" 
					 searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   
					 index="repIdCheckBox" colType="checkbox" title="%{getText('upDown.ExpRep')}" 
					 align="center" formatter="checkbox"  sortable="false" 
					 edittype="checkbox"  width="35" editable="true" hidden="false" search="false"
					 formatoptions="{disabled : false}" editoptions="{value:'Yes:No'}"/>
				<psjg:gridColumn name="REPORT_ID"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="REPORT_ID" colType="number" title="%{getText('reportId')}"
					sortable="true" width="25" />
			    		
				<psjg:gridColumn name="REPORT_NAME"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="REPORT_NAME" colType="text" title="%{getText('reportName')}"
					sortable="true" />
				<psjg:gridColumn name="PROG_REF"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="PROG_REF" colType="text" title="%{getText('progRef')}"
					sortable="true" />
				<psjg:gridColumn name="PARENT_REF"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="PARENT_REF" colType="text" title="parentRef" sortable="true"
					hidden="true" />
				<psjg:gridColumn name="PARENT_REF_STR"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="PARENT_REF_STR" colType="text" title="parentRefStr"
					sortable="true" hidden="true" />
				<psjg:gridColumn name="APP_NAME"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="APP_NAME" colType="text" title="%{getText('reporting.applicationName')}" sortable="true"
					width="50" />
				<psjg:gridColumn name="OLD_REPORT_ID"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="OLD_REPORT_ID" colType="number" title="%{getText('reporting.repInfoId')}"
					sortable="true" width="40" />					
				<psjg:gridColumn name="CONN_ID"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="CONN_ID" colType="number" title="CONN_ID" sortable="true"
					width="25" hidden="true" />
				<psjg:gridColumn name="DEFAULT_FORMAT"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="DEFAULT_FORMAT" colType="text" title="DEFAULT_FORMAT"
					sortable="true" width="25" hidden="true" />
					
				<psjg:gridColumn name="DOWNLOADABLE_FLAG"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="DOWNLOADABLE_FLAG" colType="number" title="DOWNLOADABLE_FLAG" sortable="true"
					width="25" hidden="true" />	


			</psjg:grid>
		</ps:collapspanel>
	</ps:collapsgroup>
	
	 <div id="exportDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	 <div id="importDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>

	<div>
		<h1 class="headerPortionContent ui-widget ui-state-default">
			<ps:label value="%{getText('upDown.UploadDetails')}" />
		</h1>
	</div>
	<div class="headerPortion" id="upDownMaintDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
		<%@include file="UploadDownloadReportMaint.jsp"%>
	</div>
	<div>
		<psj:submit button="true" onclick="uploadReport();" type="button">
			<ps:text name="upDown.Upload" />
		</psj:submit>
	</div>
	<div id='actionErrorDiv'>
	</div>
	
	<br />
	<ps:collapspanel id='uploadDownloadRepClient_${_pageRef}'  key="upDown.cltName">	
		<div id="uploadDownloadRepClientInnerDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	</ps:collapspanel>
	<br />
	
	<ps:collapspanel id='uploadDownloadSubRep_${_pageRef}'  key="subRep.subReport">		
		<div id="uploadDownloadSubRepInnerDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	</ps:collapspanel>
	<br />

	<ps:collapspanel id='paramsDiv_${_pageRef}'  key="designer.arguments">	
		<div id="paramsInnerDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	</ps:collapspanel>
	<br />

	<ps:collapspanel id='procsDiv_${_pageRef}'  key="upDown.Procs">	
		<div id="procsInnerDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	</ps:collapspanel>
	<br />
	
	<ps:collapspanel id='imagesDiv_${_pageRef}'  key="upDown.images">	
		<div id="imagesInnerDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	</ps:collapspanel>
	<br />
	

	<ps:collapspanel id='uploadDownloadHashTbl_${_pageRef}'  key="upDown.hashTbl">	
		<div id="uploadDownloadHashTblInnerDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	</ps:collapspanel>
	<br />

	<div id="upDownToolBarDivId" style="display: none">
		<ptt:toolBar id="upDownToolBar">
			<psj:submit button="true" buttonIcon="ui-icon-disk"
				onclick="saveProc()">
				<ps:text name="reporting.save"></ps:text>
			</psj:submit>
			<psj:submit button="true" buttonIcon="ui-icon-disk"
				id="saveAsBtn_${_pageRef}" onclick="saveProc(1)">
				<ps:text name="opt.SaveAs"></ps:text>
			</psj:submit>
			
			<psj:submit button="true" buttonIcon="ui-icon-disk"
				id="translateBtn_${_pageRef}" onclick="openTranslate()">
				<ps:text name='upDown.translate'></ps:text>
			</psj:submit>
			
		</ptt:toolBar>
	</div>




	<ps:form id="downloadFormIds_${_pageRef}" namespace="/path/designer"
		action="upDownReport_downloadReport.action" useHiddenProps="true">
		
		</ps:form>

 	<ps:form id="fullDownloadFormIds_${_pageRef}" namespace="/path/designer" 
		action="upDownReport_startExportProcess.action" useHiddenProps="true">
		
		</ps:form>

	<script type="text/javascript">
	

$("#upDownGrid_" + _pageRef).jqGrid('filterToolbar', {
	searchOnEnter : true
});
</script>