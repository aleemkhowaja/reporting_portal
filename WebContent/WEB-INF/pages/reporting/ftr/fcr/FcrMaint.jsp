<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	$("#fcrDetFormId_" + _pageRef).processAfterValid("newFcr");
});
</script>

<ps:form id="fcrDetFormId_${_pageRef}" action="fcr_validateFcr"
	namespace="/path/fcrRep" method="POST" useHiddenProps="true"
	validate="true">
	<ps:hidden name="fcrCO.ftrOptVO.CODE" id="fcrCode_${_pageRef}"></ps:hidden>
	<div>
		<table class="headerPortionContent ui-widget-content" border="0"
			width="60%">
			<tr>
				<td>
					<ps:label key="reporting.descEng" for="descEng_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="fcrCO.ftrOptVO.BRIEF_NAME_ENG"
						id="descEng_${_pageRef}" maxlength="100" required="true"></ps:textfield>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label key="reporting.descAr" for="descAr_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="fcrCO.ftrOptVO.BRIEF_NAME_ARAB"
						id="descAr_${_pageRef}" maxlength="100" required="true"  onlyArabic="true"></ps:textfield>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label key="progRef" for="fcrProgRef_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="fcrCO.ftrOptVO.PROG_REF"
						id="fcrProgRef_${_pageRef}" maxlength="9"
						onblur="checkProgRef(this)" onkeyup="checkInputVal()"
						required="true"></ps:textfield>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label key="reporting.order" for="fcrOrder_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="fcrCO.ftrOptVO.DISP_ORDER"
						id="fcrOrder_${_pageRef}" mode="number" minValue="1"
						nbFormat="######" maxlength="4" size="37" required="true"></ps:textfield>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label key="reporting.parentRef"
						for="lookuptxt_fcrParentRef_${_pageRef}" />
				</td>
				<td nowrap="nowrap">
					<table width="100%">
						<tr>
							<td width="80px">
								<psj:livesearch id="fcrParentRef_${_pageRef}"
									name="fcrCO.ftrOptVO.PARENT_REF" mode="text"
									readOnlyMode="false"
									actionName="${pageContext.request.contextPath}/path/fcrRep/fcr_loadPRefLkp"
									searchElement=""
									resultList="PROG_REF:lookuptxt_fcrParentRef_${_pageRef},PROG_DESC:parentRefStr_${_pageRef}"
									parameter="updates:lookuptxt_fcrParentRef_${_pageRef}"
									dependencySrc="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByPRefId.action"
									dependency="parentRefStr_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_fcrParentRef_${_pageRef}:commonDetVO.CODE_STR"
									required="true">
								</psj:livesearch>
							</td>
							<td>
								<ps:textfield id="parentRefStr_${_pageRef}"
									name="fcrCO.PARENT_REF_STR" readonly="true"></ps:textfield>
							</td>

						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label key="reporting.template"
						for="lookuptxt_fcrTempCode_${_pageRef}" />
				</td>
				<td nowrap="nowrap">
					<table width="100%">
						<tr>
							<td width="80px">
								<psj:livesearch id="fcrTempCode_${_pageRef}"
									name="fcrCO.ftrOptVO.TMPLT_CODE" mode="number"
									readOnlyMode="false"
									actionName="${pageContext.request.contextPath}/path/templateProcess/proc_loadTemplLkp"
									searchElement=""
									resultList="CODE:lookuptxt_fcrTempCode_${_pageRef},BRIEF_DESC_ENG:fcrTempCodeStr_${_pageRef}"
									parameter="code:lookuptxt_fcrTempCode_${_pageRef}"
									dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_applyTemplDependency.action"
									dependency="fcrTempCodeStr_${_pageRef}:tmplProcCO.fromTemplStr,lookuptxt_fcrTempCode_${_pageRef}:tmplProcCO.fromTempl"
									required="true">
								</psj:livesearch>
							</td>
							<td>
								<ps:textfield id="fcrTempCodeStr_${_pageRef}"
									name="fcrCO.TMPLT_CODE_STR" readonly="true"></ps:textfield>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label for="lookuptxt_frcColTempCode_${_pageRef}"
						key="fcr.colCode" />
				</td>
				<td nowrap="nowrap">
					<table width="100%">
						<tr>
							<td width="80px">
								<psj:livesearch id="frcColTempCode_${_pageRef}"
									name="fcrCO.ftrOptVO.COLUMN_CODE" mode="number"
									readOnlyMode="false"
									actionName="${pageContext.request.contextPath}/path/templateProcess/proc_loadColTemplLkp"
									searchElement=""
									resultList="CODE:lookuptxt_frcColTempCode_${_pageRef},BRIEF_DESC_ENG:frcColTempCodeStr_${_pageRef}"
									parameter="code:lookuptxt_frcColTempCode_${_pageRef}"
									dependencySrc="${pageContext.request.contextPath}/path/templateProcess/proc_applyColTemplDependency.action"
									dependency="frcColTempCodeStr_${_pageRef}:tmplProcCO.fromTemplStr,lookuptxt_frcColTempCode_${_pageRef}:tmplProcCO.fromTempl"
									required="true">
								</psj:livesearch>
							</td>
							<td>
								<ps:textfield id="frcColTempCodeStr_${_pageRef}"
									name="fcrCO.COLUMN_CODE_STR" readonly="true"></ps:textfield>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label value="%{getText('reporting.ReportType')}"
						required="true" />
				</td>
				<td>
					<ps:select name="fcrCO.ftrOptVO.REP_TYPE" id="repType_${_pageRef}"
						list="reportTypesList" listKey="VALUE_CODE" listValue="VALUE_DESC"
						onchange="showHideElements(this.value)">
					</ps:select>
				</td>
			</tr>
			<tr>
				<td align="left"><ps:label value="%{getText('line.currency')}"/></td>
				<td>
					<table width="100%">
						<tr>
							<td width="30%">
								<psj:livesearch
									id="csvLookUp_${_pageRef}" 
									name="fcrCO.ftrOptVO.REP_CURRENCY"
									readOnlyMode  ="false"
									actionName="${pageContext.request.contextPath}/pathdesktop/currencyLookup_constructLookup"
									searchElement="CURRENCY_CODE" 
									resultList="CURRENCY_CODE:lookuptxt_csvLookUp_${_pageRef},BRIEF_DESC_ENG:currencyStr_${_pageRef}"
									mode="number" maxlength="3"
									parameter="CURRENCY_CODE:lookuptxt_csvLookUp_${_pageRef}"
						            dependencySrc="${pageContext.request.contextPath}/pathdesktop/CurrencyDependencyAction_dependencyByCurrencyCode.action?displayMsg=1"
						            dependency="lookuptxt_csvLookUp_${_pageRef}:currency.CURRENCY_CODE,currencyStr_${_pageRef}:currency.BRIEF_DESC_ENG">
								</psj:livesearch>	
							</td>
							<td width="70%">
								<ps:textfield id="currencyStr_${_pageRef}" name="fcrCO.CURRENCY_STR" disabled="true" readonly="true"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr id="grpByDetTr_${_pageRef}">
				<td>
					<ps:label id="groupByDetails_${_pageRef}"
						value="%{getText('reporting.GroupCriteria')}" required="true" />
				</td>
				<td>
					<ps:select name="fcrCO.ftrOptVO.GROUP_BY" id="groupBy_${_pageRef}"
						list="groupByList" listKey="VALUE_CODE" listValue="VALUE_DESC">
					</ps:select>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label key="reporting_generated_file_name" for="generatedFileName_${_pageRef}"></ps:label>
				</td>
				<td>
					<ps:textarea id="generatedFileName_${_pageRef}" name="fcrCO.ftrOptVO.GENERATED_FILE_NAME"  maxlength="500" />
				</td>
			</tr>			
			<tr>
				<td>
					<ps:label value="%{getText('reporting.RowToColumn')}"
						required="true" />
				</td>
				<td>
					<ps:checkbox id="rowToColumn_${_pageRef}" name="fcrCO.valueFlag">
					</ps:checkbox>
				</td>
			</tr>
			</td>
			</tr>
		</table>
	</div>
	<ps:hidden name="fcrCO.ftrOptVO.DATE_UPDATED"
		id="DATE_UPDATED_${_pageRef}"></ps:hidden>


	<ptt:toolBar id="fcrToolbar">
		<psj:submit button="true" buttonIcon="ui-icon-disk">
			<ps:label key="reporting.save"></ps:label>
		</psj:submit>
	</ptt:toolBar>
</ps:form>
