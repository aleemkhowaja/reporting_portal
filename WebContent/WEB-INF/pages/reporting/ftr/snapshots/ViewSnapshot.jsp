<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<script type="text/javascript">
$(document).ready(function() 
{
	var snpFormat = $("#format_"+_pageRef).val();
	var csvSep= $("#csvSeparatorId_"+_pageRef).val();
	
	var headFoot;
	if ($("#noHeadAndFoot_" + _pageRef).attr("checked") != null) 
	{
		headFoot = "Y";
	} 
	else 
	{
		headFoot = "N";
	}
	var snpUrl = jQuery.contextPath+ "/path/snapshotParameter/SnapshotInformationMaintAction_loadSnapshot.action?code="
	+$("#viewRepSnpId_"+_pageRef).val()+"&updates="+$("#snpModify_"+_pageRef).val()
	+"&repSnapshotInformationCO.SNP_FORMAT="+snpFormat+
	"&repSnapshotInformationCO.CSV_SEPARATOR="+csvSep+"&repSnapshotInformationCO.SHOW_HEAD_FOOT="+headFoot;
	$("#snpDataDiv_${_pageRef}").load(snpUrl);
});
</script>

<ps:form id="viewSnpFrm_${_pageRef}" useHiddenProps="true">
 <ps:hidden name="snpChanges" id="viewSnpChanges_${_pageRef}"></ps:hidden>
 <ps:hidden id="viewRepSnpId_${_pageRef}" name="code"></ps:hidden>
 <ps:hidden id="snpModify_${_pageRef}" name="updates"></ps:hidden>
</ps:form>


<div id="snpDataDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
</div>




