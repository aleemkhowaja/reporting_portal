<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>


<script type="text/javascript">
var PROG_REF_D00 			= '<ps:property value="PROG_REF_D00"  escapeHtml="false" escapeJavaScript="true"/>'; 
var htmlPageRef 	= '<ps:property value="htmlPageRef"  escapeHtml="false" escapeJavaScript="true"/>'; 
$(document).ready(
	function() 
	{
		$.struts2_jquery.require("FileExportImportDynParams.js", null,jQuery.contextPath+ "/path/js/reporting/ftr/fileexportimport/");
		rep_fileExpImp_DynParamReady()
	});
</script>



<div id="dynParamDiv_${htmlPageRef}" style="width: 1080px; height: 320px; overflow: auto; overflow-x: hidden;"></div>

<iframe id="expImpIframId_${htmlPageRef}" style="display:none;"  name="expImpRetIframe_${htmlPageRef}" frameborder="0" width="100%" ></iframe>