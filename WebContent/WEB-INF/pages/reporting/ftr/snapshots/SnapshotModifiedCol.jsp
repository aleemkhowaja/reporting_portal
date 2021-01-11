<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>

<ps:set name="modColAlreadyUsed_var" 		value="%{getEscText('snapshots.valueInUse')}"/>

<html>
<script>
	var modColAlreadyUsed 		= '<ps:property value="modColAlreadyUsed_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

	$(document).ready(function(){
			_showProgressBar(true);	
			params ={};
			params["progRef"]=$("#colModifProgRef_"+_pageRef).val();
			params["from"]="<%=RepConstantsCommon.SNAPSHOT_MODIFIED%>";
			params["repId"]=$("#repId_"+_pageRef).val();
			params["_pageRef"]=_pageRef;
			var refreshUrl=jQuery.contextPath+'/path/ftrCommon/FtrCommonAction_previewReportDesign.action?'
			$('#modColDivPreview_'+_pageRef).load(refreshUrl, params, function(param)
			 {
			 		$("#modColDivPreview_"+_pageRef).html(param);
			 		_showProgressBar(false);	
			});
	});
	

</script>
<body>
<ps:url id="urlSnapshotParameterModifiedColumnGrid" escapeAmp="false" action="SnapshotModifiedColumnAction_loadSnapshotModColGrid" namespace="/path/snapshotParameter">
   <ps:param name="_pageRef" value="_pageRef"></ps:param> 
   <ps:param name="repSnapshotParamVO.SNAPSHOT_FREQUENCY" 	value="repSnapshotParamVO.SNAPSHOT_FREQUENCY"></ps:param>
   <ps:param name="repSnapshotParamVO.REP_REFERENCE" 		value="repSnapshotParamVO.REP_REFERENCE"></ps:param>
   <ps:param name="repSnapshotParamCO.progRefOld" 			value="repSnapshotParamCO.progRefOld"></ps:param>
   <ps:param name="repSnapshotParamCO.freqOld" 				value="repSnapshotParamCO.freqOld"></ps:param>
</ps:url>
<div>
	<psjg:grid
		id               ="snapshotParameterModifiedColumnGridTbl_Id_${_pageRef}"
	    href             ="%{urlSnapshotParameterModifiedColumnGrid}"
	    dataType         ="json"
		pager            ="true"	navigator="true" navigatorSearch="false"
		navigatorEdit	 ="false" navigatorAdd="true" navigatorDelete="true"
		caption 		 =" "
		navigatorRefresh ="true" viewrecords="true" 
		delfunc			 ="deleteModSnCol"
		editinline 	     ="true"
		addfunc			 ="addModCol"
		editurl="%{urlSnapshotParameterModifiedColumnGrid}"
		sortable         ="false"
		gridModel        ="gridModel"
		pagerButtons="false"
	  	shrinkToFit      ="true">
	     <psjg:gridColumn name="repSnapshotModifyColumnVO.REP_ID" id="repSnapshotModifyColumnVO.REP_ID"  title="REPID" colType="number"  editable="false"  search="false" hidden="true"/>  	
	     <psjg:gridColumn name="repSnapshotModifyColumnVO.COLUMN_MODIFY" id="COLUMN_MODIFY" index="repSnapshotModifyColumnVO.COLUMN_MODIFY" 
			 	title="%{getText('snapshots.field')}" editable="true" sortable="false" colType="liveSearch" searchElement="repSnapshotModifyColumnVO.COLUMN_MODIFY"
			  	dataAction="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotModifiedColumnAction_reportLookup.action?_pageRef=${_pageRef}" 
			  	resultList="repSnapshotModifyColumnVO.REP_ID:repSnapshotModifyColumnVO.REP_ID,repSnapshotModifyColumnVO.COLUMN_MODIFY:repSnapshotModifyColumnVO.COLUMN_MODIFY_lookuptxt,repSnapshotModifyColumnVO.COLUMN_TYPE:repSnapshotModifyColumnVO.COLUMN_TYPE,colTechName:colTechName"
			  	editoptions="{dataEvents: [{ type: 'change', fn: function(e) { checkModCol()}}],readonly: 'readonly'}"			  	
			  	align="center"/>
		 <psjg:gridColumn name="colTechName"	id="colTechName" title="colTechName" colType="text" editable="false"     search="false" hidden="true"/>  	
		 <psjg:gridColumn name="repSnapshotModifyColumnVO.COLUMN_TYPE"	id="repSnapshotModifyColumnVO.COLUMN_TYPE" title="Type" colType="text" editable="false"     search="false" hidden="true"/>  	
	</psjg:grid>
</div>
<div style="height: 100px"></div>
	<ps:collapsgroup id='snpModColCollapsGrp_${_pageRef}'>
		<ps:collapspanel id='snpModColCollaps_${_pageRef}'  key="snapshots.reportPreview">
			<div id="modColDivPreview_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
		</ps:collapspanel>
	</ps:collapsgroup>
	
 <ps:form id="modColForm_${_pageRef}" action="SnapshotModifiedColumnAction_saveModifiedColumns" namespace="/path/snapshotParameter" useHiddenProps="true">
		<ps:hidden name="updateModColumns" id="updateModColumns_${_pageRef}"></ps:hidden>
		<ps:hidden id="colModifFreq_${_pageRef}" 	name="repSnapshotParamVO.SNAPSHOT_FREQUENCY"/>
		<ps:hidden id="colModifProgRef_${_pageRef}" name="repSnapshotParamVO.REP_REFERENCE"/>
		<ps:hidden id="repId_${_pageRef}" 			name="repSnapshotParamVO.REP_ID"/>
 </ps:form>
</body>
	<script type="text/javascript">
       $('#snapshotParameterModifiedColumnGridTbl_Id_'+_pageRef ).jqGrid("setGridWidth",870);	
       $("#gview_snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
       $.struts2_jquery.require("SnapshotModifiedCol.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/snapshots/");       
	</script>
</html>