<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<ps:set name="fromToCompareAlert_var" 			value="%{getEscText('tmplProc.compareFromToAlert')}"/>
<ps:set name="checkDatesAlert_var" 				value="%{getEscText('tmplProc.checkDatesAlert')}"/>
<ps:set name="tmplProcDoneAlert_var" 			value="%{getEscText('tmplProc.tmplProcDoneAlert')}"/>
<ps:set name="tmplProcFailedAlert_var" 			value="%{getEscText('tmplProc.tmplProcFailedAlert')}"/>
<ps:set name="colTmplProcDoneAlert_var" 		value="%{getEscText('tmplProc.colTmplProcDoneAlert')}"/>
<ps:set name="requiredFieldsAlert_var" 			value="%{getEscText('reporting.requiredFieldsAlert')}"/>
<ps:set name="colTmplProcFailedAlert_var" 		value="%{getEscText('tmplProc.colTmplProcFailedAlert')}"/>
<ps:set name="colTmpProcValidDate_var" 		    value="%{getEscText('reporting.enterValidDate')}"/>

<html>
	<script type="text/javascript">
var fromToCompareAlert 			= '<ps:property value="fromToCompareAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkDatesAlert 			= '<ps:property value="checkDatesAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var tmplProcDoneAlert 			= '<ps:property value="tmplProcDoneAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var tmplProcFailedAlert 		= '<ps:property value="tmplProcFailedAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var colTmplProcDoneAlert 		= '<ps:property value="colTmplProcDoneAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var requiredFieldsAlert 		= '<ps:property value="requiredFieldsAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var colTmplProcFailedAlert 		= '<ps:property value="colTmplProcFailedAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var colTmpProcValidDate			= '<ps:property value="colTmpProcValidDate_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(
	function() {$.struts2_jquery.require("TemplateProcess.js", null,jQuery.contextPath + "/path/js/reporting/ftr/templateProcess/");
	rep_templProcess_readyFunc();
});

</script>
	<body>
		<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
		<ps:collapsgroup id="TmplProcCollapsGrp_${_pageRef}">
			<ps:collapspanel id='templateProccessDiv_${_pageRef}'  key="tmplProc.templateProc">
			<ps:form id="tmplProcFormId" useHiddenProps="true"
				name="tmplProcFormId" action="proc_runTemplProcess"
				namespace="/path/templateProcess" method="POST">
				<table width="100%" class="headerPortionContent ui-widget-content" border="0">
					<tr>
						<td align="right">
							<ps:label value="%{getText('tempProcess.from')}" />
						</td>
						<td width="80px" height="50px">
							<psj:livesearch id="fromTempl" name="tmplProcCO.fromTempl"
								mode="number" readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/templateProcess/proc_loadTemplLkp"
								searchElement=""
								resultList="CODE:lookuptxt_fromTempl,BRIEF_DESC_ENG:fromTemplStr"
								afterDepEvent="checkTemplDiff(1)"
								parameter="code:lookuptxt_fromTempl"
								dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_applyTemplDependency.action"
								dependency="fromTemplStr:tmplProcCO.fromTemplStr,lookuptxt_fromTempl:tmplProcCO.fromTempl">
							</psj:livesearch>
						</td>
						<td>
							<ps:textfield id="fromTemplStr" name="tmplProcCO.fromTemplStr"
								readonly="true" tabindex="-1"></ps:textfield>
						</td>
						<td>
							<ps:label value="%{getText('tempProcess.to')}" />
						</td>
						<td width="80px">
							<psj:livesearch id="toTempl" name="tmplProcCO.toTempl"
								mode="number" readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/templateProcess/proc_loadTemplLkp"
								searchElement=""
								resultList="CODE:lookuptxt_toTempl,BRIEF_DESC_ENG:toTemplStr"
								afterDepEvent="checkTemplDiff(2)"
								parameter="code:lookuptxt_toTempl"
								dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_applyTemplDependency.action"
								dependency="toTemplStr:tmplProcCO.fromTemplStr,lookuptxt_toTempl:tmplProcCO.fromTempl">
							</psj:livesearch>
						</td>
						<td>
							<ps:textfield id="toTemplStr" name="tmplProcCO.toTemplStr"
								readonly="true" tabindex="-1"></ps:textfield>
						</td>
					</tr>
				</table>
			</ps:form>
			<ptt:toolBar id="templProcTlBar">
				<psj:submit type="button" button="true" buttonIcon="ui-icon-disk"
					onclick="runTemplProc()">
					<ps:text name="tmplProc.run"></ps:text>
				</psj:submit>
			</ptt:toolBar>

		</ps:collapspanel>
		<ps:collapspanel id='tmplProccLogsDiv_${_pageRef}'  key="tmplProc.templateProc">			
			<ps:url id="urlGrid" action="proc_loadTmplProcLogsGrid"
				namespace="/path/templateProcess">
				<ps:param name="_pageRef" value="_pageRef"></ps:param>
			</ps:url>
			<psjg:grid id="tmplProccLogsGrid_${_pageRef}" dataType="json"
				href="%{urlGrid}" gridModel="gridModel" pager="true"
				navigatorSearchOptions="{closeOnEscape: false, closeAfterSearch: false, multipleSearch: false}"
				rowNum="10" rowList="5,10,15,20" viewrecords="true" navigator="true"
				navigatorAdd="false" navigatorEdit="false" navigatorSearch="false"
				navigatorDelete="false" navigatorRefresh="false">
				<psjg:gridColumn name="glstmpltVO.CODE"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="glstmpltVO.CODE" colType="number" title="%{getText('reporting.code')}" width="10"
					align="center" />
				<psjg:gridColumn name="glstmpltVO.BRIEF_NAME_ENG"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="glstmpltVO.BRIEF_NAME_ENG" colType="text"
					title="%{getText('reporting.briefNameEnglish')}" width="40" align="center" />
				<psjg:gridColumn name="status"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="status" colType="text" title="%{getText('template.status')}" width="10"
					align="center" />
				<psjg:gridColumn name="procOsMessage"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="procOsMessage" colType="text" title="%{getText('template.procOsMessage')}"
					width="40" align="center" />
			</psjg:grid>
		</ps:collapspanel>
		<ps:collapspanel id='colTemplateProccessDiv_${_pageRef}'  key="tmplProc.colTemplateProc">			
			<ps:form id="colTmplProcFormId" useHiddenProps="true"
				name="colTmplProcFormId" action="proc_runColTemplProcess"
				namespace="/path/templateProcess" method="POST">
				<table width="100%" height="140px"
					class="headerPortionContent ui-widget-content" border="0">
					<tr>
						<td align="right">
							<ps:label value="%{getText('tempProcess.from')}" />
						</td>
						<td width="80px">
							<psj:livesearch id="fromColTempl" name="tmplProcCO.fromTempl"
								mode="number" readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/templateProcess/proc_loadColTemplLkp"
								searchElement=""
								resultList="CODE:lookuptxt_fromColTempl,BRIEF_DESC_ENG:fromColTemplStr"
								afterDepEvent="checkColTemplDiff(1)"
								parameter="code:lookuptxt_fromColTempl"
								dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_applyColTemplDependency.action"
								dependency="fromColTemplStr:tmplProcCO.fromTemplStr,lookuptxt_fromColTempl:tmplProcCO.fromTempl">
							</psj:livesearch>
						</td>
						<td>
							<ps:textfield id="fromColTemplStr" name="tmplProcCO.fromTemplStr"
								readonly="true" tabindex="-1"></ps:textfield>
						</td>
						<td align="right">
							<ps:label value="%{getText('tempProcess.to')}" />
						</td>
						<td width="80px">
							<psj:livesearch id="toColTempl" name="tmplProcCO.toTempl"
								mode="number" readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/templateProcess/proc_loadColTemplLkp"
								searchElement=""
								resultList="CODE:lookuptxt_toColTempl,BRIEF_DESC_ENG:toColTemplStr"
								afterDepEvent="checkColTemplDiff(2)"
								parameter="code:lookuptxt_toColTempl"
								dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_applyColTemplDependency.action"
								dependency="toColTemplStr:tmplProcCO.fromTemplStr,lookuptxt_toColTempl:tmplProcCO.fromTempl">
							</psj:livesearch>
						</td>
						<td>
							<ps:textfield id="toColTemplStr" name="tmplProcCO.toTemplStr"
								readonly="true" tabindex="-1"></ps:textfield>
						</td>
					</tr>
					<tr>
						<td align="right">
							<ps:label value="%{getText('tmplProc.procType')}" />
						</td>
						<td colspan="2">
							<ps:select list="procTypeArrList" listKey="VALUE_CODE"
								listValue="VALUE_DESC" name="tmplProcCO.procType"
								id="procTypeComboId_${_pageRef}" onchange="disableDates()"
								parameter="updates:procTypeComboId_${_pageRef}"
								dependency="procTypeComboId_${_pageRef}:updates"
			    				dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_showHidePeriodicDates"/>
						</td>
						<td align="right">
							<ps:label value="%{getText('tmplProc.asOfDate')}" id="asOfDateLbl" />
						</td>
						<td colspan="2">
							<psj:datepicker name="tmplProcCO.asOfDate" id="asOfDate"
								buttonImageOnly="true"></psj:datepicker>
						</td>
					</tr>
					<tr>
						<td align="right">
							<ps:label value="%{getText('reporting.fromDate')}" id="fromDateLbl" />
						</td>
						<td colspan="2">
							<psj:datepicker name="tmplProcCO.fromDate" id="fromDate"
								disabled="true" onchange="checkDates(this)"
								buttonImageOnly="true"></psj:datepicker>
						</td>

						<td align="right">
							<ps:label value="%{getText('tmplProc.toDate')}" id="toDateLbl"/>
						</td>
						<td colspan="2">
							<psj:datepicker name="tmplProcCO.toDate" id="toDate"
								disabled="true" onchange="checkDates(this)"
								buttonImageOnly="true"></psj:datepicker>
						</td>
					</tr>
						<tr>
						<td align="right">
							<ps:label value="%{getText('reporting.periodicDate')}"  id="periodicDateLbl"/>
						</td>
						<td>
							<ps:textfield size="6"  id="periodicDate" name="tmplProcCO.periodicDate"
								 maxlength="6" mode="number" nbFormat="######" 
								 minValue="0"></ps:textfield>
						</td>

						<td >
							<ps:select list="periodTypeArrList" listKey="VALUE_CODE"
								listValue="VALUE_DESC" name="tmplProcCO.periodType"
								id="periodTypeComboId" />
						</td>
					</tr> 
				</table>
			</ps:form>
			<ptt:toolBar id="colTemplProcTlBar">
				<psj:submit type="button" button="true" buttonIcon="ui-icon-disk"
					onclick="runColTemplProc()">
					<ps:text name="tmplProc.run"></ps:text>
				</psj:submit>
			</ptt:toolBar>
		</ps:collapspanel>
		<ps:collapspanel id='colTmplProcLogsDiv_${_pageRef}'  key="tmplProc.colTemplateProc">	
			<ps:url id="urlGrid" action="proc_loadColumnTmplProcLogsGrid"
				namespace="/path/templateProcess">
				<ps:param name="_pageRef" value="_pageRef"></ps:param>
			</ps:url>
			<psjg:grid id="colTmplProcLogsGrid_${_pageRef}" dataType="json"
				href="%{urlGrid}" gridModel="gridModel" pager="true"
				navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
				rowNum="10" rowList="5,10,15,20" viewrecords="true" navigator="true"
				navigatorAdd="false" navigatorEdit="false" navigatorSearch="false"
				navigatorDelete="false" navigatorRefresh="false">

				<psjg:gridColumn name="colmnTmpltVO.CODE"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="colmnTmpltVO.CODE" colType="number" title="%{getText('reporting.code')}" width="10"
					align="center" />
				<psjg:gridColumn name="colmnTmpltVO.BRIEF_NAME_ENG"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="colmnTmpltVO.BRIEF_NAME_ENG" colType="text"
					title="%{getText('reporting.briefNameEnglish')}" width="40" align="center" />
				<psjg:gridColumn name="status"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="status" colType="text" title="%{getText('template.status')}" width="10"
					align="center" />
				<psjg:gridColumn name="procOsMessage"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="procOsMessage" colType="text" title="%{getText('template.procOsMessage')}"
					width="40" align="center" />
			</psjg:grid>
		</ps:collapspanel>
		<ps:collapspanel id='templateProccessAllDiv_${_pageRef}'  key="tmplProc.colAndTemplateProc">	
			<ptt:toolBar id="procTlBar">
				<psj:submit type="button" button="true" buttonIcon="ui-icon-disk"
					onclick="runAllProc()">
					<ps:text name="tmplProc.runAll"></ps:text>
				</psj:submit>
			</ptt:toolBar>
		</ps:collapspanel>
</ps:collapsgroup>
	</body>
</html>

