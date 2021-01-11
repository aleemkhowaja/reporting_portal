<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>
<!---->
<!--<script type="text/javascript">-->
<!--$(document).ready(function() {					-->
<!--							$.struts2_jquery.require("FileExportImportMaint.js" ,null,jQuery.contextPath+"/path/js/fileexportimport/");-->
<!--							$("#fileExportImportMaintFormId_"+_pageRef).processAfterValid("fileExportImportMaint_processSubmit",{});-->
<!--						});-->
<!--</script>-->
<script type="text/javascript">
$(document).ready(function() 
{
	$("#fileMainForm_"+_pageRef).processAfterValid("saveFiles");	
});

</script>
<ps:form id="fileMainForm_${_pageRef}" useHiddenProps="true" validate="true" namespace="/path/fileExportImport">
	<ps:hidden id="mode_${_pageRef}" name="mode"></ps:hidden>

	<ps:hidden id="FILE_NAME_${_pageRef}" name="irp_file_mainCO.irp_file_mainVO.FILE_NAME"></ps:hidden>
	<ps:hidden id="DATE_UPDATED_${_pageRef}" name="irp_file_mainCO.irp_file_mainVO.DATE_UPDATED"/>
 	<ps:hidden id="FILE_ID_${_pageRef}" name="irp_file_mainCO.irp_file_mainVO.FILE_ID"/>
 
		<table class="headerPortionContent ui-widget-content" width="100%" cellspacing="0" cellpadding="5" border="0">
	  		<tr>
	  		
	  				<td nowrap="nowrap" width="50%" colspan ="2">
						<ps:file name="upload"  label="%{getText('reporting.file')}" id="upload"  size="80" tabindex="15"/>
			    	</td>
	  		</tr>
  		</table>
	<ps:hidden name="updates" id="updates_${_pageRef}"></ps:hidden>
	<ps:hidden name="updates_1" id="updates_1_${_pageRef}"></ps:hidden>
	<ps:hidden name="updates_2" id="updates_2_${_pageRef}"></ps:hidden>
</ps:form>
