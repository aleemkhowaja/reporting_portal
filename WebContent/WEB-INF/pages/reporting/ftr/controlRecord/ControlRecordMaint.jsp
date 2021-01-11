<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<html>
<body> 
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

<div class="headerPortion" id="controlRecDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
<%@include file="ControlRecordMaintFrm.jsp"%>
</div>


</body>
<script type="text/javascript">
$(document).ready(function() 
{
	$.struts2_jquery.require("ControlRecordMaint.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/controlRecord/");
});		
</script>
</html>
