<%@ taglib prefix="s" uri="/struts-tags" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="notAllowed_var" 		value="%{getEscText('uploadImg.notAllowed')}"/>
<ps:set name="extAlert_var" 		value="%{getEscText('uploadImg.extAlert')}"/>
<ps:set name="existsError_var" 		value="%{getEscText('uploadImg.existsError')}"/>
<ps:set name="sizeError_var" 		value="%{getEscText('uploadImg.sizeError')}"/>
<ps:set name="sizeWarning_var" 		value="%{getEscText('uploadImg.sizeWarning')}"/>


<html>

<script type="text/javascript">
	var notAllowed = '<ps:property value="notAllowed_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var extAlert = '<ps:property value="extAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var sizeWarning = '<ps:property value="sizeWarning_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var sizeError = '<ps:property value="sizeError_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var existsError = '<ps:property value="existsError_var"  escapeHtml="false" escapeJavaScript="true"/>'

	$(document).ready(
			function() {
				$.struts2_jquery.require("UploadImage.js", null,
						jQuery.contextPath
								+ "/path/js/reporting/uploadDownloadReports/");
			});
</script>



<body>
 
     <ps:form id="uploadFrm" namespace="/path/designer" method="POST" enctype="multipart/form-data">
         <ps:file name="upload" label="%{getText('reporting.file')}" id="upload"  size="60"/>
     </ps:form>
     
     <table align="right">
	 <tr>
		 <td >
		 		<psj:submit button="true" onclick="uploadImg()" >
					<ps:text name="reporting.ok"></ps:text>
				</psj:submit>
		 </td>
		 <td>
		 		<psj:submit button="true" onclick="cancelUploadImg()" >
					<ps:text name="reporting.cancel"></ps:text>
				</psj:submit>
		 </td>
	 </tr>
 </table>
 
 </body>
</html>