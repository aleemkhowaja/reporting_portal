<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>


<ps:form useHiddenProps="true" id="reportsMismatchMaintFormId_${_pageRef}" namespace="/path/reportsMismatch">
	<table class="headerPortionContent" width="100%">
		<tr>
			<td width="15%" nowrap="nowrap" align="left">
				<ps:label key="reportsMismatch.mismatchType" />
			</td>
			<td width="15%" nowrap="nowrap">
					<ps:select list="misTypeList" listKey="VALUE_CODE" 
							listValue="VALUE_DESC"
							name=""
							id="misTypeListComboId_${_pageRef}" />
			</td>
			<td width="15%">
				<psj:a id="filterRepMis_${_pageRef}" button="true" onclick="loadRepMisGrid();">
					<ps:text name="reportsMismatch.filter" />
				</psj:a>
			</td>
			<td></td>
		</tr>
	</table>
	<ps:hidden name="updates" id="updateRepMis_${_pageRef}"></ps:hidden>
	<ps:hidden id="mismatchType_${_pageRef}" name="mismatchType"></ps:hidden>
</ps:form>

<script type="text/javascript">


$(document).ready(function() {					
							$.struts2_jquery.require("ReportsMismatchMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/reportsmismatch/");
							$("#reportsMismatchMaintFormId_"+_pageRef).processAfterValid("reportsMismatchMaint_processSubmit",{});
						});
						

</script>