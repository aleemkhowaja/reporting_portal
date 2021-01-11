<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<ps:set name="autoImpWarnings_var" 	value="%{getEscText('reporting.autoImpWarnings')}"/>
<ps:set name="autoImpSuccess_var" 	value="%{getEscText('reporting.autoImpSuccess')}"/>
<ps:set name="autoImpUnsuccess_var"	value="%{getEscText('reporting.autoImpUnsuccess')}"/>
<ps:set name="replaceConfirmAuto_var" 	value="%{getEscText('reporting.allSchedHypDel')}"/>
<ps:set name="checkUncheckUseExtOpt_var" 	value="%{getEscText('checkUncheckUseExtOpt')}"/>
<script>
var autoImpWarnings  = '<ps:property value="autoImpWarnings_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var autoImpSuccess   = '<ps:property value="autoImpSuccess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var autoImpUnsuccess = '<ps:property value="autoImpUnsuccess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var replaceConfirmAuto='<ps:property value="replaceConfirmAuto_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkUncheckUseExtOpt = '<ps:property value="checkUncheckUseExtOpt_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(
		function() {
			$.struts2_jquery.require("AutomatedImportList.js", null,jQuery.contextPath + "/path/js/reporting/uploadDownloadReports/");
			automatedImportReadyFunc();
		});		
$("#automatedImportFormId_"+_pageRef).processAfterValid("rep_autoImp_findRepFiles",{});
</script>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<div id="autoImpRepFrm_<ps:property value="_pageRef" escapeHtml="true"/>" class="connectedSortable ui-helper-reset" >
		<div class="collapsibleContainer">	
			<%@include file="AutomatedImportMaint.jsp"%>
		</div>
</div>
<div style="overflow-x: hidden" id="autoImpDiv_<ps:property value="_pageRef" escapeHtml="true"/>" >
<ps:form id="autImpFrm_${_pageRef}" name="auImpForm" action="AutomatedImportReportAction_loadAutomatedImportGrid" namespace="/path/automatedImport">
		<ps:url id="urlAutoImportGrid" escapeAmp="false" action="AutomatedImportReportAction_loadAutomatedImportGrid" namespace="/path/automatedImport">
		   <ps:param name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psjg:grid
			id               ="autoImportRepGrid_${_pageRef}"
		    href             ="%{urlAutoImportGrid}"
		    dataType         ="json"
			pager            ="true"	navigator="true" navigatorSearch="false"
			navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
			caption =" "
			navigatorRefresh ="true" viewrecords="true" 
			editinline="true"
			editurl="%{urlAutoImportGrid}"
			sortable="false"
			gridModel="gridModel"
		  	shrinkToFit="true"
		  	multiselect="true"
		    pagerButtons="false"
		  	disableEditableFocus="true"
		  	onGridCompleteTopics="checkAllUseOptAxs">
		  	    <psjg:gridColumn name="ZIP_FILE_NAME" width="300" index="ZIP_FILE_NAME" id="ZIP_FILE_NAME" title="%{getText('reporting.zipFileName')}" colType="text" 
		  	    editable="false" sortable="false"/>
				<psjg:gridColumn name="ACTION" index="ACTION" width="100" id="ACTION_${_pageRef}" title="%{getText('reporting.action')}" colType="select" 
				edittype="select"  editable="true" sortable="false" search="false" formatter="select" 
				editoptions="{ value:function() {  return loadCombo('${pageContext.request.contextPath}/path/automatedImport/AutomatedImportReportAction_loadAutomatedImpCmb.action','actionCmb', 'VALUE_CODE', 'VALUE_DESC');}}"/>		         
				<psjg:gridColumn name="USE_EXISTING_OPT" index="USE_EXISTING_OPT" id="USE_EXISTING_OPT" 
				title="%{getText('reporting.skipOptAxs')}" formatter="checkbox" colType="checkbox" align="center" 
				edittype="checkbox" editable="true" formatoptions="{disabled : false}" sortable="false" search="false"/>    
				<psjg:gridColumn name="UPDATE_VERSION_IF_EQUAL" index="UPDATE_VERSION_IF_EQUAL" id="UPDATE_VERSION_IF_EQUAL" 
				title="%{getText('reporting.updateVersionIfEqual')}" formatter="checkbox" sortable="false" colType="checkbox" align="center" 
				edittype="checkbox" editable="true" formatoptions="{disabled : false}"  search="false"/>      
				<psjg:gridColumn name="SKIP_TRANSLATION" index="SKIP_TRANSLATION" id="SKIP_TRANSLATION" 
				title="%{getText('reporting.skipTrans')}" formatter="checkbox" sortable="false" colType="checkbox" align="center" 
				edittype="checkbox" editable="true" formatoptions="{disabled : false}"  search="false"/>
		</psjg:grid>
</ps:form>
</div>
<div>
<table width="100%">
		<tr>
			<td width="15%" nowrap="nowrap" align="left">
				<psj:a cssStyle="width:50%" id="uploadAutoImp_${_pageRef}" button="true" onclick="rep_autoImp_startImportProcess()">
					<ps:text name="upDown.Upload" />
				</psj:a>
			</td>
			<td></td>
		</tr>
</table>
</div>
<script>
    $("#gview_autoImportRepGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
</script>