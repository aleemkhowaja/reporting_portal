<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>

<ps:set name="drilColAlreadyUsed_var" 		value="%{getEscText('snapshots.valueInUse')}"/>

<html>
<script>
	var drilColAlreadyUsed 		= '<ps:property value="drilColAlreadyUsed_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

	$(document).ready(function(){
			_showProgressBar(true);	
			params ={};
			params["progRef"]=$("#colDrilProgRef_"+_pageRef).val();
			params["repId"]=$("#repId_"+_pageRef).val();
			params["from"]="<%=RepConstantsCommon.SNAPSHOT_DRILLDOWN%>";
			params["_pageRef"]=_pageRef;
			var refreshUrl=jQuery.contextPath+'/path/ftrCommon/FtrCommonAction_previewReportDesign.action?'
			$('#drilColDivPreview_'+_pageRef).load(refreshUrl, params, function(param)
			 {
			 		$("#drilColDivPreview_"+_pageRef).html(param);
			 		_showProgressBar(false);	
			});
			
	});
	
	

</script>
<body>
<ps:url id="urlSnapshotParameterDrilColumnGrid" escapeAmp="false" action="SnapshotDrilldownColumnAction_loadSnapshotDrilColGrid" namespace="/path/snapshotParameter">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
   <ps:param name="repSnapshotParamVO.SNAPSHOT_FREQUENCY" 	value="repSnapshotParamVO.SNAPSHOT_FREQUENCY"></ps:param>
   <ps:param name="repSnapshotParamVO.REP_REFERENCE" 		value="repSnapshotParamVO.REP_REFERENCE"></ps:param>
   <ps:param name="repSnapshotParamCO.progRefOld" 			value="repSnapshotParamCO.progRefOld"></ps:param>
   <ps:param name="repSnapshotParamCO.freqOld" 				value="repSnapshotParamCO.freqOld"></ps:param>
</ps:url>
<div>
	<psjg:grid
		id               ="snapshotParameterDrilColumnGridTbl_Id_${_pageRef}"
	    href             ="%{urlSnapshotParameterDrilColumnGrid}"
	    dataType         ="json"
		pager            ="true"	navigator="true" navigatorSearch="false"
		navigatorEdit	 ="false" navigatorAdd="true" navigatorDelete="true"
		caption 		 =" "
		navigatorRefresh ="true" viewrecords="true"
		delfunc			 ="deleteDrilSnCol"
		editinline 	     ="true"
		addfunc			 ="addDrilCol"
		editurl="%{urlSnapshotParameterDrilColumnGrid}"
		sortable         ="false"
		gridModel        ="gridModel"
		pagerButtons="false"
	  	shrinkToFit      ="true">
	     <psjg:gridColumn name="repSnapshotDrilColVO.REP_ID" id="repSnapshotDrilColVO.REP_ID"  title="REPID" colType="text"  editable="false"  search="false" hidden="true"/>  	
	     <psjg:gridColumn name="repSnapshotDrilColVO.COLUMN_DRILLDOWN" id="COLUMN_DRILLDOWN" index="repSnapshotDrilColVO.COLUMN_DRILLDOWN"
	     	 title="%{getText('snapshots.field')}"	editable="true" sortable="false" colType="liveSearch" 
			  	searchElement="repSnapshotDrilColVO.COLUMN_DRILLDOWN"
			  	editoptions="{dataEvents: [{ type: 'change', fn: function(e) { checkDrilCol()}}],readonly: 'readonly'}"			  	
			  	dataAction="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotDrilldownColumnAction_reportLookup.action?_pageRef=${_pageRef}" 
			  	resultList="repSnapshotDrilColVO.REP_ID:repSnapshotDrilColVO.REP_ID,repSnapshotDrilColVO.COLUMN_DRILLDOWN:repSnapshotDrilColVO.COLUMN_DRILLDOWN_lookuptxt,repSnapshotDrilColVO.COLUMN_TYPE:repSnapshotDrilColVO.COLUMN_TYPE,TECH_COL_NAME:TECH_COL_NAME"			  	
			  	
			  	align="center"/>
		 <psjg:gridColumn name="TECH_COL_NAME"	id="TECH_COL_NAME" title="TECH_COL_NAME" colType="text" editable="false"     search="false" hidden="true"/>  	
		 <psjg:gridColumn name="repSnapshotDrilColVO.COLUMN_TYPE"	id="repSnapshotDrilColVO.COLUMN_TYPE" title="Type" colType="text" editable="false"     search="false" hidden="true"/>  	
	</psjg:grid>
	</div>
<div style="height: 100px"></div>

	<ps:collapsgroup id="sortSnDril_${_pageRef}">
			<ps:collapspanel id="sortSnDrilCollpas_${_pageRef}"  key="snapshots.reportPreview">
				<div id="drilColDivPreview_${_pageRef}"></div>
			</ps:collapspanel>
	</ps:collapsgroup>
	
 <ps:form id="drilColForm_${_pageRef}" action="SnapshotDrilldownColumnAction_saveDrilldownColumns" namespace="/path/snapshotParameter" useHiddenProps="true">
		<ps:hidden name="updateDrilColumns" id="updateDrilColumns_${_pageRef}"></ps:hidden>
		<ps:hidden id="colDrilFreq_${_pageRef}" 		name="repSnapshotParamVO.SNAPSHOT_FREQUENCY"/>
		<ps:hidden id="colDrilProgRef_${_pageRef}" 		name="repSnapshotParamVO.REP_REFERENCE"/>
		<ps:hidden id="repId_${_pageRef}" 				name="repSnapshotParamVO.REP_ID"/>
 </ps:form>
</body>
	<script type="text/javascript">
       $('#snapshotParameterDrilColumnGridTbl_Id_'+_pageRef ).jqGrid("setGridWidth",870);	
       $("#gview_snapshotParameterDrilColumnGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();      
       $.struts2_jquery.require("SnapshotDrilldownCol.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/snapshots/");
	</script>
</html>