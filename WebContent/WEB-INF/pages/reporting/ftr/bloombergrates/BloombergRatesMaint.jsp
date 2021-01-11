<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<ps:set name="missingExt_var" 			value="%{getEscText('blrgRates.missingExtTxt')}"/>

<ps:form useHiddenProps="true" id="bloombergRatesMaintFormId_${_pageRef}"  namespace="/path/bloombergRates" method="POST" enctype="multipart/form-data">
<ps:set name="ivcrud_${_pageRef}" value="iv_crud" />
<table width="100%" class="headerPortionContent ui-widget-content" cellspacing="10">
	<tr>
  	<td width="10%">
		<ps:text name="blrgRates.file" />
	</td>
	<td>
      	<ps:file name="upload"  id="upload_${_pageRef}"/>
   	</td>
	</tr>
	<tr>
		<td>
				<div>
		<psj:submit button="true" onclick="uploadBlmbrgRates();" type="button">
			<ps:text name="upDown.Upload" />
		</psj:submit>
	</div>
		</td>
		<td>
		</td>
	</tr>
	
</table>


	<div id='actionErrorDiv'>
	</div>
	
</ps:form>
<script type="text/javascript">

var missingExt 				= '<ps:property value="missingExt_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
$(document).ready(function() {					
							$.struts2_jquery.require("BloombergRatesMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/bloombergrates/");
						});
</script>