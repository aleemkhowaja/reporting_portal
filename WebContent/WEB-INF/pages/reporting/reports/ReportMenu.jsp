<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@page import="com.path.bo.common.ConstantsCommon"%>
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>

<ps:set name="reloadData_var" 				value="%{getEscText('preview.reloadData')}"/>
<ps:set name="reloadTitle_var" 				value="%{getEscText('preview.reloadTitle')}"/>
<ps:set name="resetAlert_var" 				value="%{getEscText('preview.resetAlert')}"/>
<ps:set name="resetTitle_var" 				value="%{getEscText('preview.reset')}"/>
<ps:set name="grpExist_var" 				value="%{getEscText('group.grpExist')}"/>
<ps:set name="pagesRangeMsg_var" 			value="%{getEscText('report.pagesRangeAlert')}"/>
<ps:set name="repMenuNoHeadAndFoot_var" 	value="%{getEscText('reporting.noHeadAndFoot')}"/>
<ps:set name="repMenuNoHeaders_var" 		value="%{getEscText('reporting.noHeaders')}"/>
<ps:set name="summarizedABD_var" 			value="%{getEscText('reporting.summarizedABD')}"/>
<ps:set name="detailedABD_var" 			value="%{getEscText('reporting.detailedABD')}"/>
<ps:set name="rtvAlert_var" 			value="%{getEscText('reporting.rtvAlert')}"/>
<ps:set name="tradeDteAsOf_var" 		value="%{getEscText('reporting.tradeDteAsOf')}"/>
<ps:set name="valueDteAsOf_var" 		value="%{getEscText('reporting.valueDteAsOf')}"/>
<ps:set name="reportRef_var" 			value="%{getEscText('reporting.reportRef')}"/>
<ps:set name="notExecuted_var"  		value="%{getEscText('reporting.notExecuted')}"/>
<ps:set name="notExistTxtFormula_var"  		value="%{getEscText('reporting.notExistTxtFormula')}"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/path/js/reporting/reports/ReportMenu.js"></script>
<script type="text/javascript">
var menu                    = '<ps:property value="menu"  escapeHtml="false" escapeJavaScript="true"/>'; 
var htmlPageRef             = '<ps:property value="htmlPageRef"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reloadData 				= '<ps:property value="reloadData_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reloadTitle 			= '<ps:property value="reloadTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var resetAlert 				= '<ps:property value="resetAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var resetTitle 				= '<ps:property value="resetTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var grpExist 				= '<ps:property value="grpExist_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var pagesRangeMsg 			= '<ps:property value="pagesRangeMsg_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repMenuNoHeadAndFoot 	= '<ps:property value="repMenuNoHeadAndFoot_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repMenuNoHeaders		= '<ps:property value="repMenuNoHeaders_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var summarizedABD			= '<ps:property value="summarizedABD_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var detailedABD				= '<ps:property value="detailedABD_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var rtvAlert				= '<ps:property value="rtvAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var tradeDteAsOf			= '<ps:property value="tradeDteAsOf_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var valueDteAsOf			= '<ps:property value="valueDteAsOf_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reportRef   			= '<ps:property value="reportRef_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var notExecuted 			= '<ps:property value="notExecuted_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var notExistTxtFormula 		= '<ps:property value="notExistTxtFormula_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var optR0025				= "<%=RepConstantsCommon.REP_FTR_R0025%>";
var optFcrSummarized		= "<%=RepConstantsCommon.REP_FCR_SUMMARIZED%>";
var optFcrDetailed			= "<%=RepConstantsCommon.REP_FCR_DETAILED%>";
var fcrExt					= "<%=ConstantsCommon.OPT_FCR_EXTENSION%>";
var updateMode				= "<%=ConstantsCommon.UPDATE_MODE%>";
var insertMode				= "<%=ConstantsCommon.CREATE_MODE%>";



$(document).ready(function() 
{
	rep_menu_docReadyFunc();
});


</script>
<div class="clearfix">
	<ps:url id="orderPrevRepUrl"  namespace="/path/repCommon" action="commonReporting_openPrevOrder">
		<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="orderPrevRepDlg_${htmlPageRef}" 
		href="%{orderPrevRepUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="700"
	    title="%{getText('preview.sort')}"
	    height="250"
	/>    
</div>

<div class="clearfix">
	<ps:url id="filterPrevRepUrl"  namespace="/path/repCommon" action="commonReporting_openPrevFilter">
		<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="filterPrevRepDlg_${htmlPageRef}" 
		href="%{filterPrevRepUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="800"
	    title="%{getText('reporting.filter')}"
	    height="320"
	/>    
</div>

<div class="clearfix">
	<ps:url id="grpPrevRepUrl"  namespace="/path/repCommon" action="commonReporting_openPrevGrp">
		<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="grpPrevRepDlg_${htmlPageRef}" 
		href="%{grpPrevRepUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    title="%{getText('preview.group')}"
	    width="800"
	    height="400"
	/>    
</div>

<div class="clearfix">
	<ps:url id="saveSnpUrl"  namespace="/path/snapshotParameter" action="SnapshotParameterMaintAction_openSaveSnapshot" escapeAmp="false">
		<ps:param name="_pageRef" value="_pageRef"> </ps:param>
		<ps:param name="htmlPageRef" value="htmlPageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="saveSnpShtDlg_${htmlPageRef}" 
		href="%{saveSnpUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true"  
	    closeOnEscape="false"
	    width="500"
	    title="%{getText('saveSnapshot_key')}"
	    height="200"
	/>    
</div>

<div class="clearfix">
	<ps:url id="argsFiltersUrl"  namespace="/path/repCommon" action="commonReporting_openArgsFilters">
		<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="argsFilterDlg_${htmlPageRef}" 
		href="%{argsFiltersUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    title="%{getText('reporting.argFilters')}"
	    width="480"
	    height="400"
	    buttons="{\"Save\":{text:\"%{getText(\"reporting.save\")}\",id:\"save_btn_%{htmlPageRef}\",click: function(){rep_filters_saveLoadFilter(1)}},\"Cancel\":{text:\"%{getText(\"reporting.cancel\")}\",id:\"cancel_btn_%{htmlPageRef}\",click: function(){rep_filters_refillIdIfAny();}}}"
	    
	/>    
</div>


<div style="overflow:hidden">
<div style="overflow: auto">
<table width="100%" id="mainTbl_${htmlPageRef}">
<tr><td>
<ps:hidden id="auditTrxNbr_${_pageRef}" name="auditTrxNbr" value="${_pageRef}"/>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

	
		<ps:collapsgroup id="repMenuSort_${htmlPageRef}">
			<ps:collapspanel id="paramsDivCollapsible_${htmlPageRef}"  key="reporting.paramsLbl">			
			
			<div id="paramsDiv_${htmlPageRef}">
			</div>
			</ps:collapspanel>
			</ps:collapsgroup>
</td></tr>
</table>
<table width="100%">
<tr><td>	
		<table width="100%" cellpadding="10"> 
		<tr id="trOperDivId_${_pageRef}">
			<td width="5%">
				<psj:submit button="true" type="button" onclick="return openSortRep('${htmlPageRef}');">
		  				<ps:text name="preview.sort"/>
		 		</psj:submit>
			</td>
			<td width="5%">
				<psj:submit button="true" type="button" onclick="return openFilterRep('${htmlPageRef}');">
		  				<ps:text name="preview.filter"/>
		 		</psj:submit>
			</td>
			<td width="5%">
				<psj:submit button="true" type="button" onclick="return openGrpRep('${htmlPageRef}');">
		  				<ps:text name="preview.group"/>
		 		</psj:submit>
			</td>
			<td width="5%">
			</td>
			<td width="60%" align="center">
				<table>
					<tr>
						<td>
							<ps:text name="fromPage"></ps:text>
						</td>
						<td>
							<ps:textfield id="fromPage_${htmlPageRef}" name="fromPage" size="4" maxlength="4" mode="number" minValue="0"></ps:textfield>
						</td>
						<td>
							<ps:text name="toPage"></ps:text>
						</td>
						<td>
							<ps:textfield id="toPage_${htmlPageRef}" name="toPage" size="4" maxlength="4" mode="number" minValue="0"></ps:textfield>
						</td>
						<td>							
							<psj:submit button="true" type="button" allowCust="true" id="retrieveBtn_${htmlPageRef}" onclick="return refreshRepMenuData('${htmlPageRef}');">
		  						<ps:label key="retrieve_key"/>
		 					</psj:submit>
		 				</td>
		 				<td>
							<psj:submit button="true" type="button" cssStyle="width:100%" onclick="return resetReportMenuData('${htmlPageRef}');">
			  				 	<ps:text name="preview.reset"/>
			 				</psj:submit>
						</td>
						<td>	
			 				<psj:submit button="true" id="filterRepId_${htmlPageRef}" type="button" cssStyle="width:100%" onclick="openArgFilters('${htmlPageRef}');">
			  				 	<ps:text name="reporting.argFilters"/>
			 				</psj:submit>
						</td>
		 			</tr>
		 		</table>
			</td>
			
			<td width="5%">
				<psj:submit button="true" type="button" onclick="openSaveSnapshot('${htmlPageRef}');" id="saveSnpBtn_${htmlPageRef}" name="saveSnpBtn">
		  				<ps:text name="saveSnapshot_key"/>
		 		</psj:submit>
			</td>
			
			
			<td  width="5%" align="right">
				<psj:submit button="true" type="button" allowCust="true" id="exportBtn_${htmlPageRef}" onclick=" rep_checkSnapshotExist('${htmlPageRef}' ,true)">
		  			<ps:label key="export_key"/>
		 		</psj:submit>				
			</td>
			<td width="5%">
				<psj:submit progRef="<%=ConstantsCommon.OPT_VERIFY_BTN%>" button="true" type="button" onclick="verifyReport()"  id="verifyBtn_${htmlPageRef}">
				   <ps:text name="reporting.verify"></ps:text>
				</psj:submit>
			</td>
			<td width="5%" align="right" id="tdPrint_${htmlPageRef}" style="display: none">
				<psj:submit button="true" type="button" onclick="printIframeContents('${htmlPageRef}')">
					<ps:text name="print_key" />
				</psj:submit>
			</td>
		</tr>
		</table>
</td></tr>
</table>
</div>
<div>
<table width="100%">
	<tr>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
		<td colspan="1" width="10%"></td>
	</tr>
	<tr>
		<td colspan="10"></td>
		<td colspan="1" style="display: none">
			<ps:textfield id="paginationCount_${htmlPageRef}"  readonly="true" style="display: none" name="PaginationCount" mode="number" size="1" minValue="0"></ps:textfield>
		</td> 
		<td colspan="1" style="display: none">
			<ps:textfield id="currentPage_${htmlPageRef}"  readonly="true" style="display: none" name="CurrentPage" mode="number" size="1" minValue="0"></ps:textfield>
		</td>
		<td colspan="1" id="tdPaginationPrevious_${htmlPageRef}"style="display: none">
			<psj:submit id="btnPrevious_${htmlPageRef}" button="true" type="button" onclick="rep_paginationPreviousNext('${htmlPageRef}','P');">
				<ps:text name="Previous_key" />
			</psj:submit>
		</td>
		<td colspan="1" id="tdPaginationNext_${htmlPageRef}"style="display: none">
			<psj:submit id="btnNext_${htmlPageRef}"  button="true" type="button" onclick="rep_paginationPreviousNext('${htmlPageRef}','N');">
				<ps:text name="next_key" />
			</psj:submit>
		</td>
	</tr>
</table>
</div>
<ps:hidden id="iframeLoaded_${htmlPageRef}"> </ps:hidden>
<iframe id="iframId_${htmlPageRef}" style="display:none;"  name="reportPrevIframe_${htmlPageRef}" onLoad="fillIframeLoadedInput('${htmlPageRef}')" frameborder="0" width="100%" ></iframe>
</div>