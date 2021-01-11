<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<ps:set name="reportingDelAllLines_var" 	value="%{getEscText('reporting.deleteAllLines')}"/>
<ps:set name="reportingDelAll_var" 			value="%{getEscText('reporting.deleteAll')}"/>
<script>
var reportingDelAllLines = '<ps:property value="reportingDelAllLines_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reportingDelAll 	 = '<ps:property value="reportingDelAll_var"  escapeHtml="false" escapeJavaScript="true"/>';    




/*function printHashContent()
{
						var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_printContentHash?_pageRef="+_pageRef+"&update="+${update}
						$.ajax({
						 url: url,
				         type:"post",
						 dataType:"json",
						 success: function(param)
						 {   					
						 }
				    });
}*/

</script>

<div id="innerLayoutRepMis" style="overflow-x: hidden; overflow-y: auto">


		
<ps:collapsgroup id='repMisCollapsGrp_${_pageRef}'>
<ps:collapspanel id='reportsMismatchListMaintDiv_id_${_pageRef}'  key="reportsMismatch.mismatchFilter">
			<%@include file="ReportsMismatchMaint.jsp"%>
</ps:collapspanel>

	
<ps:collapspanel id='sort6mismatch_${_pageRef}' key="reportsMismatch.mismatchParameters">
		<%@include file="ReportsMismatchListGrid.jsp"%>
</ps:collapspanel>
</ps:collapsgroup>
	<div>
		<ptt:toolBar id="reportsMisBar${_pageRef}">
			<psj:submit type="button" button="true" buttonIcon="ui-icon-disk"
				onclick="submitFormRepMis()">
				<ps:text name="reporting.save"></ps:text>
			</psj:submit>
		</ptt:toolBar>
	</div>

</div>
<%--
<div>
		<ptt:toolBar id="reportsMisBar${_pageRef}">
			<psj:submit type="button" button="true" buttonIcon="ui-icon-disk"
				onclick="printHashContent()">
				<ps:text name="Print Hash"></ps:text>
			</psj:submit>
		</ptt:toolBar>
	</div>--%>
<script  type="text/javascript">
    $.struts2_jquery.require("ReportsMismatchList.js,ReportsMismatchMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/reportsmismatch/");
</script>