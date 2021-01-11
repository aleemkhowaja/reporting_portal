<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<script type="text/javascript">

</script>

<ps:form id="repMisProcForm_${_pageRef}" useHiddenProps="true">
	<ps:hidden id="crtVal_${_pageRef}" name="misProcCO.CRITERIA" />
	<ps:hidden id="repMisId_${_pageRef}" name="misProcCO.REP_MISMATCH_ID" />
	<table class="headerPortionContent" width="100%">
		<tr>
			<td width="10%" nowrap="nowrap" align="left">
				<ps:label key="reportsMismatch.mismatchType" />
			</td>
			<td width="40%" nowrap="nowrap">
				<ps:select list="misTypeList" listKey="VALUE_CODE"
					listValue="VALUE_DESC" name="misProcCO.TYPE"
					id="misTypeListComboId_${_pageRef}" onchange="showHideCrtLkp(this,true)" />
			</td>

			<td width="10%">
				<ps:label key='misProc.date' />
			</td>
			<td width="40%">
				<span id="spanMisProcDate_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker
						size="30" name="misProcCO.PERIOD" id="misProcDate_${_pageRef}"
						buttonImageOnly="true" displayFormat="mm/yy" /> </span>
			</td>
		</tr>
		<tr>
			<td>
				<span id="misProcCrt_${_pageRef}"><ps:label
						value="%{getText('criteria.criteriaTitle')}" /> </span>
				<span id="misProcRep_${_pageRef}"><ps:label
						value="%{getText('reporting.report')}" /> </span>
			</td>
			<td>
				<psj:livesearch id="crtProgRefLkp_${_pageRef}"
					name="misProcCO.CRT_PROGREF" mode="text" readOnlyMode="false"
					onOk="relaodMisProcGridAndProcess()"
					actionName="${pageContext.request.contextPath}/path/reportsMismatch/repMismProctAction_loadCrtProgRefLkp.action"
					searchElement=""
					paramList="misProcCO.TYPE:misTypeListComboId_${_pageRef}"
					resultList="CRT_PROGREF:lookuptxt_crtProgRefLkp_${_pageRef},REP_MISMATCH_ID:repMisId_${_pageRef},CRITERIA:crtVal_${_pageRef}">
				</psj:livesearch>

			</td>

			<td nowrap="nowrap">
				<ps:label key='snapshots.reportFrequency' />
			</td>
			<td>
				<ps:select cssStyle="width:195" list="reportFrequencyList"
					listKey="VALUE_CODE" listValue="VALUE_DESC"
					name="misProcCO.FREQUENCY" id="repMisProcFreqListId_${_pageRef}" />
			</td>
		</tr>
	</table>
</ps:form>
