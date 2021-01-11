<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<body>
<ps:set name="drilColAlreadyUsed_var" 	value="%{getEscText('reportsMismatch.valueInUse')}"/>

 <ps:form id="relCrtFrm_${_pageRef}"  useHiddenProps="true">
		<ps:hidden name="updates" id="updateRelCrt_${_pageRef}"></ps:hidden>
		<ps:hidden id="misRepMisID_${_pageRef}" 			name="repMismatchParamVO.REP_MISMATCH_ID"/>
		<ps:hidden id="misRepRefID_${_pageRef}" 			name="repMismatchParamVO.REP_REFERENCE"/>
		<ps:hidden id="misCrtCodeID_${_pageRef}" 			name="repMismatchParamVO.CRITERIA_CODE"/>
 </ps:form>
<ps:url id="urlReportsMismatchRelCrtList" escapeAmp="false" action="ReportsMismatchListAction_loadReportsMismatchRelCriteriaList" namespace="/path/reportsMismatch">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
   <ps:param name="repMismatchParamVO.REP_MISMATCH_ID" 		    value="repMismatchParamVO.REP_MISMATCH_ID"></ps:param>
   <ps:param name="repMismatchParamVO.REP_REFERENCE" 			value="repMismatchParamVO.REP_REFERENCE"></ps:param>
   <ps:param name="repMismatchParamVO.CRITERIA_CODE"    		value="repMismatchParamVO.CRITERIA_CODE"></ps:param>
</ps:url>
<div>
	<psjg:grid
		id               ="reportsMismatchRelatedCriteriaGrid_${_pageRef}"
	    href             ="%{urlReportsMismatchRelCrtList}"
	    dataType         ="json"
		pager            ="true"	navigator="true" navigatorSearch="false"
		navigatorEdit	 ="false" navigatorAdd="true" navigatorDelete="true" 	caption 		 =" "
		navigatorRefresh ="true" viewrecords="true"
		delfunc			 ="deleteRelatedCrt" 
		editinline 	     ="true"
		addfunc			 ="addRelCriteria"
		editurl			 ="%{urlReportsMismatchRelCrtList}"
		sortable         ="false"
		gridModel        ="gridModel"
		pagerButtons	 ="false"
	  	shrinkToFit      ="true">
	     <psjg:gridColumn name="repMismatchIntraCriteriaVO.REP_MISMATCH_ID" index="repMismatchColumnVO.REP_MISMATCH_ID" id="repMismatchColumnVO.REP_MISMATCH_ID"  title="REPID" colType="text"  editable="false"  search="false" hidden="true"/>  	
	     <psjg:gridColumn name="repMismatchIntraCriteriaVO.RELATED_CRITERIA" id="RELATED_CRITERIA" 
	     index="repMismatchIntraCriteriaVO.RELATED_CRITERIA" title="%{getText('reportsMismatch.reportCriteria')}"	
	     editable="true" sortable="false" colType="text" editoptions="{dataEvents: [{ type: 'change', fn: function(e) { checkRelatedCriteriaDup()}}]}"/>  	
	</psjg:grid>
	</div>
</body>
<script type="text/javascript">

var drilColAlreadyUsed =  '<ps:property value="drilColAlreadyUsed_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


$("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid("setGridWidth",680);	
$("#reportsMismatchRelatedCriteriaGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide();      
$.struts2_jquery.require("ReportsMismatchRelatedCrtList.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/reportsmismatch/");

</script>
</html>