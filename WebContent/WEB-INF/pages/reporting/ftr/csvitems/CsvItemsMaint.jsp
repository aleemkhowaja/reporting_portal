<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<ps:form useHiddenProps="true" id="csvItemsMaintFormId_${_pageRef}"
	namespace="/path/csvItems" validate="true">
	<table >	
			  <tr>
				<td>
	<psj:datepicker id="DATE_UPDATED_${_pageRef}" name="csvItemsCO.cbkRptLineVO.DATE_UPDATED" cssStyle="display:none" timepicker="true" timepickerFormat="hh:mm:ss"></psj:datepicker>
	</td>
	</tr>
	</table>
	<ps:set name="ivcrud_${_pageRef}" value="iv_crud" />
	<table width="100%" class="headerPortionContent ui-widget-content" cellspacing="1">
		<tr>
			<td nowrap="nowrap">
				<TABLE CELLPADDING="0" CELLSPACING="8">

					<TR>
						<td align="left">
							<ps:label key="reportName" for="reportName_${_pageRef}" />
						</td>
						<td width="80px">
							<psj:livesearch id="reportRef_${_pageRef}"
								name="csvItemsCO.cbkRptLineVO.REP_REF" mode="text"
								readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_retReports.action?_pageRef=${_pageRef}"
								searchElement="" autoSearch="true"
								resultList="PROG_REF:lookuptxt_reportRef_${_pageRef},REPORT_NAME:reportName_${_pageRef}"
								parameter="reportRef:lookuptxt_reportRef_${_pageRef},_pageRef:_pageRef"
								dependencySrc="${pageContext.request.contextPath}/path/csvItems/CsvItemsMaintAction_applyDependencyByRepRef"
								dependency="reportName_${_pageRef}:csvItemsCO.reportName,lookuptxt_reportRef_${_pageRef}:csvItemsCO.cbkRptLineVO.REP_REF,DATE_UPDATED_${_pageRef}:csvItemsCO.cbkRptLineVO.DATE_UPDATED"
								onOk="loadCsvItemsByRep()"
								afterDepEvent="loadCsvItemsByRep()"
								>
							</psj:livesearch>

						</td>
						<td>
							<ps:textfield id="reportName_${_pageRef}"
								name="csvItemsCO.reportName" mode="character" tabindex="1"
								size="30" maxlength="30" readonly="true" required="true" />
						</td>
					</TR>
				</TABLE>


			</td>
		</tr>

	</table>

</ps:form>
<script type="text/javascript">
//$("#gview_csvItemsByRepListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
$(document).ready(

		function() {
		
				//$("#csvItemsMaintFormId_" + _pageRef).processAfterValid("saveCsvItems");
		
			$.struts2_jquery.require("CsvItemsMaint.js,CsvItemsList.js", null,
					jQuery.contextPath + "/path/js/reporting/ftr/csvitems/");
			$("#csvItemsMaintFormId_" + _pageRef).processAfterValid(
					"csvItemsMaint_processSubmit", {});
		});
</script>