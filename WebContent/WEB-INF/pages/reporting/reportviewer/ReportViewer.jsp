<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="format_not_supported"
	value="%{getEscText('format_not_supported')}" />

<style>
.mainViewerDiv a:visited, .mainViewerDiv a:hover, .mainViewerDiv a:active, .mainViewerDiv a:link 
{
    color: rgb(29, 89, 135);
    font: 8px;
}
</style>
<script type="text/javascript">
	var format_not_supported = '<ps:property value="format_not_supported"  escapeHtml="false" escapeJavaScript="true"/>'; 

	var innerLayoutSettings = {
		name : "innerLayoutViewer",
		center__paneSelector : ".inner-centerViewer",
		west__paneSelector : ".inner-westViewer",
		east__paneSelector : ".inner-eastViewer",
		north__paneSelector : ".inner-northViewer",
		south__paneSelector : ".inner-southViewer",
		north__size : 45,
		east__size : 1,
		west__size : 180,
		center__size : 600,
		south__size : 0,
		west__closable : true,
		west__resizable : true,
		east__closable : false,
		east__resizable : false,
		north__closable : true,
		north__resizable : true,
		south__closable : false,
		south__resizable : false

	};

	$(document).ready(
			function() {
				innerLayoutViewer = $('#reportViewerDiv_${_pageRef}').layout(
						innerLayoutSettings);
				$.struts2_jquery
						.require("ReportViewer.js", null, jQuery.contextPath
								+ "/path/js/reporting/reportviewer/");
				document.getElementById("reportViewerDiv_"+_pageRef).style.height = $("#mainTabs").height()-50+"px";
				document.getElementById("innerCenterViewer_"+_pageRef).style.height = $("#mainTabs").height()-50+"px";
				document.getElementById("westDiv_"+_pageRef).style.height = $("#mainTabs").height()-50+"px";
				document.getElementById("reportViewerLoadHtml_"+_pageRef).style.height = $("#mainTabs").height()-50+"px";
				$("#westDiv_"+_pageRef).trigger("resize")
			});
</script>

<ps:if test='${folder != null}'>
	<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
</ps:if>
<div id="reportViewerDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="mainViewerDiv"
	style="position: relative; height: 555px; width: 100%;">
	<div class="inner-northViewer" style="padding: 1" id="northDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
		<ps:include value="NorthViewer.jsp"></ps:include>
	</div>
	<div class="inner-westViewer" style="padding: 0;overflow: auto;" id="westDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
		<ps:include value="WestViewer.jsp"></ps:include>
	</div>
	<div class="inner-centerViewer" style="padding:0;overflow-x:scroll;overflow-y:hidden;" id="innerCenterViewer_${_pageRef}">
		<ps:include value="CenterViewer.jsp"></ps:include>
	</div>
	<div class="inner-eastViewer" style="padding:0"></div>
	<div id="southDivChecker" class="inner-southViewer" style="padding:0"></div>
</div>
<ps:form id="reportViewerFrm_${_pageRef}">
</ps:form>




