<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="misatchProcess_var" 			value="%{getEscText('misProc.misatchProcess')}"/>

<script type="text/javascript">
var misatchProcessTitle 			= '<ps:property value="misatchProcess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
</script>


<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />


<div id="innerLayoutMisProc" style="overflow-x: hidden; overflow-y: auto">
<ps:collapsgroup id='misProcsort4_${_pageRef}'>
	<ps:collapspanel id='misProcDiv_${_pageRef}'  key="misProc.misatchProcess">			
			<%@include file="ReportsMismatchProcessMaint.jsp"%>
</ps:collapspanel>
</ps:collapsgroup>
</div>


<div id="misProcBtnDiv">
	<table width="100%">

		<tr>
			<td colspan="4">
				<psj:a id="processBtn_${_pageRef}" button="true"
					onclick="goProcess();">
					<ps:label key="misProc.process" />
				</psj:a>

				<psj:a id="resetBtn_${_pageRef}" button="true"
					onclick="resetMisProc();">
					<ps:label key="reporting.resetQry" />
				</psj:a>
			</td>
		</tr>
	</table>

</div>


<table CELLPADDING="0" CELLSPACING="5" width="100%">
	<tr>
		<td width="20%">
		
			<span id="misProcCrtGridLbl_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label  cssClass="boldLabel" value="%{getText('template.relatedReports')}" /> </span>
			<span id="misProcRepGridLbl_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label  cssClass="boldLabel" value="%{getText('reportsMismatch.relatedCriteria')}" /> </span>
			
			<div id="misProcGrid_<ps:property value="_pageRef" escapeHtml="true"/>">
				<%@include file="ReportsMismatchProcessGrid.jsp"%>
			</div>
		</td>
		<td>
			<div id="misProcResultDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
		</td>
	</tr>
</table>


<script type="text/javascript">


$(document).ready(
	function() 
	{
		$.struts2_jquery.require("ReportsMismatchProcess.js", null,jQuery.contextPath +"/path/js/reporting/ftr/reportsmismatch/");
		
		showHideCrtLkpLbl("1",false)
		
		$("#lookuptxt_crtProgRefLkp_" + _pageRef).attr('readonly', true);
	});
</script>



