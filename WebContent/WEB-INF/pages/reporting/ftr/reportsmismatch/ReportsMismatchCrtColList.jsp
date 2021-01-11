<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%><%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<script>
$(document).ready(function(){
			_showProgressBar(true);	
			params ={};
			params["progRef"]=$("#misRepRefID_"+_pageRef).val();
			params["mismatchId"]=$("#misRepMisID_"+_pageRef).val();
			params["from"]="<%=RepConstantsCommon.MISMATCH_CRITERIA_COLUMN%>";
			params["_pageRef"]=_pageRef;
			params["crtColOrRelCol"]=${crtColOrRelCol}
			var refreshUrl=jQuery.contextPath+'/path/ftrCommon/FtrCommonAction_previewReportDesign.action?'
			$('#crtColPreview_'+_pageRef).load(refreshUrl, params, function(param)
			 {
			 		$("#crtColPreview_"+_pageRef).html(param);
			 		_showProgressBar(false);	
			});
	});
	

</script>
<ps:set name="errorColCrt_var" 			value="%{getEscText('reportsmismatchinvalidcrt_key')}"/>
<ps:set name="drilColAlreadyUsed_var" 	value="%{getEscText('reportsMismatch.valueInUse')}"/>

 <ps:form id="crtColListFrm_${_pageRef}"  useHiddenProps="true">
		<ps:hidden name="updates" id="updateCrtColList_${_pageRef}"></ps:hidden>
		<ps:hidden id="misRepMisID_${_pageRef}" 			name="repMismatchParamVO.REP_MISMATCH_ID"/>
		<ps:hidden id="misRepRefID_${_pageRef}" 			name="repMismatchParamVO.REP_REFERENCE"/>
		<ps:hidden id="misCrtCodeID_${_pageRef}" 			name="repMismatchParamVO.CRITERIA_CODE"/>
 </ps:form>
<ps:url id="urlReportsMismatchCrtList" escapeAmp="false" action="ReportsMismatchListAction_loadReportsMismatchRelatedColsList" namespace="/path/reportsMismatch">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
   <ps:param name="repMismatchParamVO.REP_MISMATCH_ID" 	value="repMismatchParamVO.REP_MISMATCH_ID"></ps:param>
   <ps:param name="repMismatchParamVO.COLUMN_TYPE" 		value="repMismatchParamVO.COLUMN_TYPE"></ps:param>
   <ps:param name="repMismatchParamVO.CRITERIA_COLUMN" 	value="repMismatchParamVO.CRITERIA_COLUMN"></ps:param>
   <ps:param name="crtColOrRelCol" 						value="crtColOrRelCol"></ps:param>
   <ps:param name="repMismatchParamVO.REP_REFERENCE" 	value="repMismatchParamVO.REP_REFERENCE"></ps:param>
   <ps:param name="repMismatchParamVO.CRITERIA_CODE"    value="repMismatchParamVO.CRITERIA_CODE"></ps:param>
</ps:url>
<div>
	<psjg:grid
		id               ="reportsMismatchCrtList_${_pageRef}"
	    href             ="%{urlReportsMismatchCrtList}"
	    dataType         ="json"
		pager            ="true"	navigator="true" navigatorSearch="false"
		navigatorEdit	 ="false" navigatorAdd="true" navigatorDelete="true" 	caption 		 =" "
		navigatorRefresh ="true" viewrecords="true"
		delfunc			 ="deleteCrtCol" 
		editinline 	     ="true"
		addfunc			 ="addCrtCol"
		editurl			 ="%{urlReportsMismatchCrtList}"
		sortable         ="false"
		gridModel        ="gridModel"
		pagerButtons	 ="false"
	  	shrinkToFit      ="true">
	     <psjg:gridColumn name="repMismatchColumnVO.REP_MISMATCH_ID" index="repMismatchColumnVO.REP_MISMATCH_ID" id="repMismatchColumnVO.REP_MISMATCH_ID"  title="REPID" colType="text"  editable="false"  search="false" hidden="true"/>  	
	     <psjg:gridColumn name="repMismatchColumnVO.RELATED_COLUMN" id="RELATED_COLUMN" index="repMismatchColumnVO.RELATED_COLUMN"
	      title="%{getText('reportsMismatch.criteriaColumn')}"	editable="true" sortable="false" colType="liveSearch"
		  searchElement="repMismatchColumnVO.RELATED_COLUMN"
		  editoptions="{dataEvents: [{ type: 'change', fn: function(e) { checkRelCols()}}],readonly: 'readonly'}"	 	
		  dataAction="${pageContext.request.contextPath}/path/reportsMismatch/ReportsMismatchListAction_criteriaColumnLookup.action?_pageRef=${_pageRef}" 
		  resultList="repMismatchColumnVO.REP_MISMATCH_ID:repMismatchColumnVO.REP_MISMATCH_ID,repMismatchColumnVO.RELATED_COLUMN:repMismatchColumnVO.RELATED_COLUMN_lookuptxt,repMismatchColumnVO.COLUMN_TYPE:repMismatchColumnVO.COLUMN_TYPE,TECH_COL_NAME:TECH_COL_NAME"			  	
		  align="center"/>
		 <psjg:gridColumn  name="repMismatchColumnVO.COLUMN_TYPE" index="repMismatchColumnVO.COLUMN_TYPE" id="repMismatchColumnVO.COLUMN_TYPE" title="Type" colType="text" editable="false"     search="false" hidden="true"/>  	
		 <psjg:gridColumn  name="TECH_COL_NAME" index="TECH_COL_NAME" id="TECH_COL_NAME" title="TECH_COL_NAME" colType="text" editable="false"     search="false" hidden="true"/>  	
	</psjg:grid>
	</div>
<div style="height: 100px"></div>
<ps:collapsgroup id="sortModCrtCol_${_pageRef}">
			<ps:collapspanel id="repMisCrtColCollaps_${_pageRef}"  key="reportsMismatch.reportPreview">
		<div id="crtColPreview_<ps:property value="_pageRef" escapeHtml="true"/>">
		</div>
</ps:collapspanel>
</ps:collapsgroup>
<script type="text/javascript">
var errorColCrt = "${errorColCrt_var}";
var drilColAlreadyUsed =  "${drilColAlreadyUsed_var}";

function addCrtCol()
{
	if(${crtColOrRelCol}==1)//from criteria column
	{
	 	 recordsCount = $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getGridParam','records');
	 	 if(recordsCount==0)
	 	 {
	 	 		$("#reportsMismatchCrtList_"+_pageRef).jqGrid('addInlineRow', {});
	 	 }
 	}
 	else if(${crtColOrRelCol}==2)
 	{
 		$("#reportsMismatchCrtList_"+_pageRef).jqGrid('addInlineRow', {});
 	}
}



	
$('#reportsMismatchCrtList_'+_pageRef ).jqGrid("setGridWidth",870);	
$("#reportsMismatchCrtList_"+_pageRef+" div.ui-jqgrid-titlebar").hide();      
$.struts2_jquery.require("ReportsMismatchCrtColList.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/reportsmismatch/");

</script>
</html>